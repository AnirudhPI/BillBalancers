package com.billbalancers.service.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message {

    private String message = null;
    private Long ID = null;

    public Message(String message){
        this.message = message;
    }

}
