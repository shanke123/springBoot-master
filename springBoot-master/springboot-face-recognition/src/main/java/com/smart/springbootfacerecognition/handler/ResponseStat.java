package com.smart.springbootfacerecognition.handler;

public enum ResponseStat {
    SUCCESS("success"),
    ERROR("error");
    private String text;
    ResponseStat(String text){
        this.text=text;
    }
    public String getText() {
        return text;
    }
}
