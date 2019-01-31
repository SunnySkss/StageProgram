package com.example.medrec_1.slider_demo;

public class LikeVedioResponse {

    /**
     * token :
     * message : Already Added
     * messagetype : Failed
     */

    private String token;
    private String message;
    private String messagetype;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }
}
