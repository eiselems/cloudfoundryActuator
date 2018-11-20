package com.example.marcuseisele.actuatorissue;

import org.apache.http.message.BasicHeader;
import org.springframework.util.Base64Utils;

public class BasicAuthEncoder {

    /**
     * This method encodes user & password and adds the Basic keyword
     * @param user String Basic Auth username
     * @param password String Basic Auth password
     * @return String returns the encoded String with the encoded user, password and added keyword Basic
     */
    public static String createBasicAuthHeaderValue(String user, String password) {
        byte[] encoded = Base64Utils.encode((user + ":" + password).getBytes());
        return "Basic " +  new String(encoded);
    }

    /**
     * This method creates a ready to use HttpHeader
     * @param user String Basic Auth User
     * @param password String Basic Auth password
     * @return Header returns a ready to use Basic Auth Http Header
     */
    public static BasicHeader createBasicAuthHeader(String user, String password) {
        return new BasicHeader("Authorization", createBasicAuthHeaderValue(user, password));
    }
}