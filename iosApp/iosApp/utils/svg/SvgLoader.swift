import Foundation
import UIKit
import SDWebImage
import SDWebImageSVGCoder
import Shared

final class SvgLoader {

    static func fetchOriginalSvgData(from url: URL) async throws -> Data {
        let cacheKey = SDWebImageManager.shared.cacheKey(for: url)

        let cachedData: Data? = await withCheckedContinuation { continuation in
            workQueue.async {
                let data = SDImageCache.shared.diskImageData(forKey: cacheKey)
                continuation.resume(returning: (data?.isEmpty == false) ? data : nil)
            }
        }

        if let cachedData {
            return cachedData
        }

        // URLSession already suspends properly off-main; no explicit dispatch needed here.
        let (fetchedData, _) = try await URLSession.shared.data(from: url)

        workQueue.async {
            SDImageCache.shared.storeImageData(toDisk: fetchedData, forKey: cacheKey)
        }

        return fetchedData
    }

    /// Synchronous — call only off the main thread (see `loadImage` / `loadImageAsync` below).
    /// Always sanitizes, recolors, decodes, and rasterizes at the SVG's native size.
    static func image(
        from svgData: Data,
        selectedColors: SelectedColors? = nil
    ) -> UIImage? {

        guard let rawString = String(data: svgData, encoding: .utf8), !rawString.isEmpty else {
            print("❌ SVG is not valid UTF8")
            return nil
        }

        let sanitized = SvgSanitizer.sanitize(rawString)
        let colors = selectedColors ?? SelectedColors
            .init(
                base: "#000",
                eyes: "#000",
                hair: "#000"
            )

        let recolored = ColorReplacer.replaceColors(
            svgSrc: sanitized,
            bodyColor: colors.base,
            eyesColor: colors.eyes,
            hairColor: colors.hair
        )

        let svgText = recolored.contains("<svg") ? recolored : sanitized
        if svgText != recolored {
            print("⚠️ Invalid recolored SVG, falling back to sanitized original")
        }

        guard let finalData = svgText.data(using: .utf8) else {
            print("❌ Failed UTF8 conversion")
            return nil
        }

        guard let vectorImage = SDImageSVGCoder.shared.decodedImage(with: finalData, options: nil) else {
            print("❌ SVG vector decode failed")
            return nil
        }

        var drawSize = vectorImage.size
        if drawSize.width <= 0 || drawSize.height <= 0 {
            drawSize = CGSize(width: 512, height: 512) // fallback safety frame
        }

        let format = UIGraphicsImageRendererFormat.preferred()
        format.scale = 1.0                // bypass 2x/3x retina multiplier allocations
        format.preferredRange = .standard // standard 8-bit sRGB, avoid wide-gamut overhead
        format.opaque = false             // vector art is typically transparent

        let renderer = UIGraphicsImageRenderer(size: drawSize, format: format)
        let rasterizedImage = renderer.image { _ in
            vectorImage.draw(in: CGRect(origin: .zero, size: drawSize))
        }

        return rasterizedImage
    }

    // MARK: - Background loading

    private static let workQueue = DispatchQueue(
        label: "com.mashit.svgRecolorLoader",
        qos: .userInitiated,
        attributes: .concurrent
    )

    /// Callback-based, guaranteed off-main-thread.
    static func loadImage(
        from svgData: Data,
        selectedColors: SelectedColors?,
        completion: @escaping (UIImage?) -> Void
    ) {
        workQueue.async {
            let image = autoreleasepool {
                self.image(from: svgData, selectedColors: selectedColors)
            }
            completion(image)
        }
    }

    /// async/await convenience, off the main actor.
    static func loadImageAsync(
        from svgData: Data,
        selectedColors: SelectedColors?
    ) async -> UIImage? {
        await withCheckedContinuation { continuation in
            loadImage(from: svgData, selectedColors: selectedColors) { image in
                continuation.resume(returning: image)
            }
        }
    }
}
