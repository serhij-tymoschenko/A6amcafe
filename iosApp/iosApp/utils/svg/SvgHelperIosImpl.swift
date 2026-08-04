import Foundation
import UIKit
import Shared

class SvgHelperIosImpl: SvgProvider {

    func getSvg(
        imageUrl: String,
        colors: SelectedColors,
        onReady: @escaping (Shared?) -> Void
    ) {
        Task {
            // 1. Validate URL
            guard let url = URL(string: imageUrl) else {
                print("❌ Invalid SVG URL string: \(imageUrl)")
                return
            }

            // 2. Fetch raw SVG XML
            let data: Data
            do {
                data = try await SvgLoader.fetchOriginalSvgData(from: url)
            } catch {
                print("❌ SVG fetch failed: \(error)")
                return
            }

            guard !Task.isCancelled else { return }

            // 3. Heavy rasterization on background queue
            guard let uiImage = await SvgLoader.loadImageAsync(
                from: data,
                selectedColors: colors
            ) else {
                print("❌ SVG rasterization failed for: \(imageUrl)")
                return
            }

            guard !Task.isCancelled else { return }

            await MainActor.run {
                onReady(SvgConverter().toComposeImageBitmap(image: uiImage))
            }
        }
    }
}