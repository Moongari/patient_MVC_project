package com.moon.patient_mvc.security;


import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        logger.info("============================SECURITY======================================");
        // Nous allons utiliser l'encodage de password
        PasswordEncoder passwordEncoder = passwordEncoder();
        String passWordUser = passwordEncoder.encode("1234");
        String passWordAdmin = passwordEncoder.encode("2030");

/*        logger.info("PassWord user1 {}",passWordUser);
        logger.info("PassWord admin {}",passWordAdmin);*/

        logger.info("==================================================================");
        auth.inMemoryAuthentication().withUser("user1").password(passWordUser).roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password(passWordAdmin).roles("USER","ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        // il est possible de creer sa propre page
        //http.formLogin().loginPage("/login");

        // ceci veut dire que toute requete devra s'authentifier
        http.authorizeHttpRequests().anyRequest().authenticated();

        //afin de bloquer les urls en fonction des roles seul l'admin peut acceder a ces pages
        http.authorizeRequests().antMatchers("/delete/**","/edit/**","/save/**","/formPatient/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/index/**").hasRole("USER");
        http.exceptionHandling().accessDeniedPage("/notAccessPage");


    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
