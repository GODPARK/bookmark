package com.pjh.bookmark.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger("SecurityConfig");
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Value("${cors.allow.env}")
    private String corsEnv;

    @Value("#{'${cors.allow.domain.dev.list}'.split(',')}")
    private ArrayList<String> corsDevList;

    @Value("#{'${cors.allow.domain.company.list}'.split(',')}")
    private ArrayList<String> corsCompanyList;

    @Value("#{'${cors.allow.domain.prod.list}'.split(',')}")
    private ArrayList<String> corsProdList;

    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/image/**",
                "/META-INF/resources/**",
                "/font/**",
                "/fonts/**",
                "/img/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**"
        );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        ArrayList<String> corsAllowList = new ArrayList<>();
        logger.info(this.corsEnv);
        if (this.corsEnv.equals("dev")) {
            corsAllowList.addAll(this.corsDevList);
        }
        else if (this.corsEnv.equals("company")) {
            corsAllowList.addAll(this.corsCompanyList);
        }
        else if (this.corsEnv.equals("prod")) {
            corsAllowList.addAll(this.corsProdList);
        }
        else if (this.corsEnv.equals("all")) {
            corsAllowList.addAll(this.corsDevList);
            corsAllowList.addAll(this.corsCompanyList);
            corsAllowList.addAll(this.corsProdList);
        }
        else {
            corsAllowList.add("http://localhost");
        }
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(corsAllowList);
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","OPTIONS","PATCH"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("auth_token","Content-Type"));
        this.logger.debug(corsConfiguration.toString());
        if (corsConfiguration.getAllowedOrigins() != null) {
            this.logger.info(corsConfiguration.getAllowedOrigins().toString());
        }
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
