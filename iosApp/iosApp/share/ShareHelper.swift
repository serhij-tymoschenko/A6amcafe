//
// Created by Serhii on 04.08.26.
//

import Foundation
import Shared

class ShareHelper: ShareProvider {

    func getSvgHelper() -> any SvgIosProvider {
        SvgHelperIosImpl.init()
    }
}