package com.acl.biblioteca;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Response {
    private int status;
    private String error;
    private String message;
}
