package com.acl.biblioteca.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseUser {
    private int status;
    private String statusText;
    private String message;
    private String role;
    private String username;
    private String error;
}
