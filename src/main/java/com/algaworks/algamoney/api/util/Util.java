package com.algaworks.algamoney.api.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Util {

    public static String passwordGenerate(String name){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(name);
    }


}
