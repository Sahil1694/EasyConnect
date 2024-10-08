package com.Smart_Contact_Manager.Smart_Contact_Manager.config;


import com.Smart_Contact_Manager.Smart_Contact_Manager.services.impl.SecurityCustomUserDetailService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {


//    //user create and login using spring security
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user1 = User
//                .withUsername("sahil")
//                .password("12345")
//                .roles("ADMIN","USER")
//                .build();
//
//        UserDetails user2 = User
//                .withUsername("Sejal")
//                .password("12345")
//                .roles("USER" , "ADMIN")
//                .build();
//
//        var inMemoryUserDetailsManager  = new InMemoryUserDetailsManager(user1 , user2);
//        return inMemoryUserDetailsManager;
  //  }

    @Autowired
    private SecurityCustomUserDetailService userDetailsService;

    @Autowired
    private OAuthenticationSuccessHandler handler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    //Configure Authentication Provider Spring Security
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user detail service ka object:
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //Congigure the http security
        //Configure the URL which Should be public and which should be private
        httpSecurity.authorizeHttpRequests(authorize ->{
//                authorize.requestMatchers("/home","/register" , "/services").permitAll()
                authorize.requestMatchers("/user/**").authenticated();
                authorize.anyRequest().permitAll();
     });


        //form Defaulft login page
        httpSecurity.formLogin(formLogin->{

            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            //formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            formLogin.failureHandler(authFailureHandler);
        });


            httpSecurity.csrf(AbstractHttpConfigurer::disable);
            httpSecurity.logout(logoutForm->{
                logoutForm.logoutUrl("/do-logout");
                logoutForm.logoutSuccessUrl("/login?logout=true");  //redirect to login page
            });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
            //oauth configuration
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
