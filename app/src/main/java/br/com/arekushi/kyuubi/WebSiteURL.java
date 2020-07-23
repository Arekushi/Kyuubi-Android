package br.com.arekushi.kyuubi;

import android.util.Patterns;

public class WebSiteURL {

    public static String checkURL(String URL) {
        boolean isAddress = Patterns.WEB_URL.matcher(URL).matches();

        if(isAddress) {
            return String.format("https://%s", URL.toLowerCase());

        } else {
            return String.format("https://www.google.com/search?q=%s", URL);
        }
    }

}
