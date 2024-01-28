package com.example.springsocial.util;
import javax.servlet.http.HttpServletRequest;
public class UrlUtils {
    public static String getSiteURL(HttpServletRequest request) {
        String scheme = request.getScheme();   // http or https
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        StringBuilder siteURL = new StringBuilder();
        siteURL.append(scheme).append("://").append(serverName);

        // Append the port only if it's not the default for the scheme
        if (("http".equals(scheme) && serverPort != 80) || ("https".equals(scheme) && serverPort != 443)) {
            siteURL.append(":").append(serverPort);
        }

        return siteURL.toString();
    }


}
