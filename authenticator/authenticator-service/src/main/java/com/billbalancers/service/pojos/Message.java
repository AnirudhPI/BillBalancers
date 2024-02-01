package com.billbalancers.service.pojos;

import lombok.Getter;

@Getter
public class Message {

    private String message = null;

    public Message(String message){
        this.message = message;
    }

}
