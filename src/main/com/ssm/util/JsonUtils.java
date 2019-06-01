package ssm.util;

import java.util.HashMap;
import java.util.Map;

public class JsonUtils {


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("123", 1);
        map.put("456", 2);
        map.put("789", 3);
        String reuslt = toJson(map);
        System.out.println(reuslt);

    }


    public static String toJson(Map<String, Object> map){{
        String result = "";
        String tem  = "";
        for (String key : map.keySet()) {
            String jsonkey = "{\"" + key + "\":";
            long  value = (long)map.get(key);
            tem = jsonkey + value + "},";
            result += tem;
        }
        return "["  + result.substring(0, result.length()-1) + "]";
    }

    }

}
