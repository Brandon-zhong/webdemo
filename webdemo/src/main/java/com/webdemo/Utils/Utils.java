package com.webdemo.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by brandon on 2018/3/3.
 *
 * @author brandon
 */
public class Utils {

    public static String getCookieValueByName(Cookie[] cookies, String name) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                String value = cookie.getValue();
                return value;
            }
        }
        return "";
    }

    public static void putTokenToCooike(String token, HttpServletResponse response) {
        response.addCookie(new Cookie("Authorization", "Bearer" + token));
    }
}
