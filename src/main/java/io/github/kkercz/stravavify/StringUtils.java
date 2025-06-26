package io.github.kkercz.stravavify;

import java.util.List;

public class StringUtils {

    public static String cleanUp(String name) {
        return name
                .replaceAll("\\s*\\(.*\\)", "") // "Title (radio edit)" -> "Title"
                .replaceAll("\\s+-.*$", ""); // "Title - radio edit" -> "Title"
    }

    public static String joinWithLimit(List<String> values) {
        return joinWithLimit(values, ", ", 40);
    }

    public static String joinWithLimit(List<String> values, String sep, int limit) {
        StringBuilder result = new StringBuilder(values.getFirst());
        boolean skipped = false;
        for (int i = 1; i < values.size(); i++) {
            int nextValue = values.get(i).length();
            if (result.length() + nextValue + sep.length() <= limit) {
                result.append(sep).append(values.get(i));
            } else {
                skipped = true;
            }
        }
        return result.append(skipped ? sep + "..." : "").toString();
    }
}