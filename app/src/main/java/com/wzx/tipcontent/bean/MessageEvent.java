package com.wzx.tipcontent.bean;

public  class MessageEvent {
    private String message;
    public MessageEvent(String message){
        this.message=message;
    }
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
