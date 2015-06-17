package org.anderes.edu.dojo.java8.news.map;

import static org.apache.commons.lang3.StringUtils.containsNone;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MapUtility {

    public static Map<String, String[]> buildMapFromStringArray(final String[] valueArray) {
        final Map<String, List<String>> map = new HashMap<>();
        for (String value : valueArray) {
            if (containsNone(value, ".")) {
                continue;
            }
            final String table = substringBefore(value, ".");
            final String column = substringAfter(value, ".");
            
            map.putIfAbsent(table, new ArrayList<>());
            map.get(table).add(column);
        }
        final Map<String, String[]> returnValue = new HashMap<>(map.size());
        map.keySet().forEach(t -> {
            returnValue.put(t, map.get(t).toArray(new String[0]));
        });
        return returnValue;
    }
}
