package com.adbt.adbtproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private static final String ADMIN = "ADMIN";

    private static final String USER = "USER";

    private static final String WORKER = "WORKER";

    private static final String SHIFT_MANAGER = "SHIFT_MANAGER";


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/worker").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/api/users").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/users/*").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, "/api/users/*").hasRole(ADMIN)

                .antMatchers(HttpMethod.GET, "/api/analytics/*").hasAnyRole(ADMIN, SHIFT_MANAGER)

                .antMatchers(HttpMethod.POST, "/api/centres/*").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, "/api/centres").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, "/api/centres/*").hasRole(ADMIN)

                .antMatchers(HttpMethod.GET, "/api/category/*").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/category/*").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/api/category/*").hasRole(ADMIN)

                .antMatchers(HttpMethod.GET, "/api/items").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/items/*").hasAnyRole(ADMIN, SHIFT_MANAGER)
                .antMatchers(HttpMethod.POST, "/api/items").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, "/api/items/*").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/api/items/*").hasRole(ADMIN)

                .antMatchers(HttpMethod.POST, "/api/orders").hasRole(USER)
                .antMatchers(HttpMethod.GET, "/api/orders/todo").hasAnyRole(WORKER, SHIFT_MANAGER, ADMIN)
                .antMatchers(HttpMethod.PATCH, "/api/orders/retrieved/*").hasAnyRole(ADMIN, SHIFT_MANAGER)
                .antMatchers(HttpMethod.POST, "/api/orders/*").hasAnyRole(ADMIN, SHIFT_MANAGER)
                .antMatchers(HttpMethod.PATCH, "/api/orders/collected/*").hasAnyRole(ADMIN, SHIFT_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/orders/worker/*").hasAnyRole(ADMIN, SHIFT_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/orders/active/worker/*").hasAnyRole(WORKER, SHIFT_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/orders/*/assign/*").hasAnyRole(WORKER, SHIFT_MANAGER)

                .antMatchers(HttpMethod.GET, "/api/warehouse/*").authenticated()
                .antMatchers(HttpMethod.GET, "/api/warehouse/*").authenticated()

                .anyRequest().hasAuthority(ADMIN)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter((new JwtAuthorizationFilter(authenticationManager())));


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(true)
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

   /* @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/

}