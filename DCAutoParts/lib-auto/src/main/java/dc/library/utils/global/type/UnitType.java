package dc.library.utils.global.type;

import dc.library.utils.LanguageUtils;

public enum UnitType {
    KM,//公里
    MI;//英里

    public static int getDefaultType() {
        if (LanguageUtils.isCN()) {
            return KM.ordinal();
        } else {
            return MI.ordinal();
        }

    }
}
