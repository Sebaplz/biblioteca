package com.acl.biblioteca.response;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class Response {
    private int status;
    private String statusText;
    private String message;
    private String error;
}
