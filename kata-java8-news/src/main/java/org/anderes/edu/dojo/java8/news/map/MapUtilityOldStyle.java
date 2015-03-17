package org.anderes.edu.dojo.java8.news.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.apache.commons.lang3.StringUtils.*;

public abstract class MapUtilityOldStyle {

    public static Map<String, String[]> buildMapFromStringArray(final String[] valueArray) {
        final Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String value : valueArray) {
            if (containsNone(value, ".")) {
                continue;
            }
            final String table = substringBefore(value, ".");
            final String column = substringAfter(value, ".");
            
            if (map.containsKey(table)) {
                map.get(table).add(column);
            } else {
                List<String> list = new ArrayList<String>();
                list.add(column);
                map.put(table, list);
            }
        }
        final Map<String, String[]> returnValue = new HashMap<String, String[]>(map.size());
        for (String tablename : map.keySet()) {
            returnValue.put(tablename, map.get(tablename).toArray(new String[0]));
        }
        return returnValue;
    }
}
