package com.pjh.bookmark.component;

import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoding implements PasswordEncoder {


    private PasswordEncoder passwordEncoder;

    public PasswordEncoding(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public PasswordEncoding(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence rawPw) {
        return passwordEncoder.encode(rawPw);
    }

    @Override
    public boolean matches(CharSequence rawPw, String encodedPassword){
        return passwordEncoder.matches(rawPw,encodedPassword);
    }
}
