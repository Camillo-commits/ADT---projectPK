package com.adbt.adbtproject.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    public JwtResponse(String jwtToken) {
        this.jwttoken = jwtToken;
    }
    public String getToken() {
        return this.jwttoken;
    }

}

