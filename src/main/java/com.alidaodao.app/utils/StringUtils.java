package com.alidaodao.app.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


public class StringUtils {


    public static String paramToQueryString(Map<String, String> params, String charset) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder paramString = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> p : params.entrySet()) {
            String key = p.getKey();
            String value = p.getValue();

            if (!first) {
                paramString.append("&");
            }
            paramString.append(urlEncode(key, charset));
            if (value != null) {
                paramString.append("=").append(urlEncode(value, charset));
            }
            first = false;
        }
        return paramString.toString();
    }


    public static String urlEncode(String value, String encoding) {
        if (value == null) {
            return "";
        } else {
            try {
                String encoded = URLEncoder.encode(value, encoding);
                return encoded.replace("+", "%20").replace("*", "%2A").replace("~", "%7E").replace("/", "%2F");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("", e);
            }
        }
    }
}
