package dev.badbird.library.util;

public class SearchUtils {
    public record EnumSearchResult<T>(T value, boolean exactMatch){}
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> EnumSearchResult<T> findEnumByName(String name, Class<T> enumClazz) { // welcome to generic hell!
        String s = name.replace("-", "").toLowerCase();
        Enum<T> similar = null;
        for (Enum<T> value : enumClazz.getEnumConstants()) {
            String nameVal = value.toString() // we'll override tostring in each enum
                    .replace("-", "")
                    .toLowerCase();
            if (nameVal.equals(s) || value.name().equalsIgnoreCase(s)) {
                return new EnumSearchResult(value, true);
            }
            else if (similar(nameVal, name)) {
                similar = value;
            }
        }
        if (similar != null) return new EnumSearchResult(similar, false);
        return null;
    }

    public static boolean similar(String a, String b) {
        if (a.equalsIgnoreCase(b)) return true;
        return a.startsWith(b) || b.startsWith(a) || a.endsWith(b) || b.endsWith(a);
    }
}
