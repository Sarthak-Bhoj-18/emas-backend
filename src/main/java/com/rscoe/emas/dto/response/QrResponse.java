package com.rscoe.emas.dto.response;

public class QrResponse {

    private String token;

    public QrResponse(){}

    public QrResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}