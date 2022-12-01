package com.mascotappspring.demo;

import com.mascotappspring.demo.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MascotappSpringApplication {
    @Autowired
    private UsuarioServicio usuarioServ;

	public static void main(String[] args) {
		SpringApplication.run(MascotappSpringApplication.class, args);
	}
        
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
            .userDetailsService(usuarioServ)
            .passwordEncoder (new BCryptPasswordEncoder());        
        }

}
