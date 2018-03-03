package hackfsu.apx.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by pranathi on 3/3/18.
 */

public class StringUtils {
    public static boolean isEmptyOrNull (@Nullable String s) {
        return isNull(s) || isEmptyString(s);
    }

    public static boolean isEmptyString (@NonNull String s) {
        return s.equals("");
    }

    public static boolean isNotEmptyAndNotNull (@Nullable String s) {
        return !isEmptyOrNull(s);
    }

    public static boolean isNull (@Nullable String s) {
        return s == null;
    }
}
