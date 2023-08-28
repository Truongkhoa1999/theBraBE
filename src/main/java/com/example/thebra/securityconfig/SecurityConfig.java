package com.example.thebra.securityconfig;

import com.example.thebra.filters.JwtFilter;
import com.example.thebra.stripewebhook.IpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public IpFilter ipFilter() {
        return new IpFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .headers(headers ->
                        headers.contentSecurityPolicy("frame-ancestors 'self' https://identify.nordea.com/")
                )
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/order/{id}", "/api/v1/stripe/createpaymentlink", "/api/v1/stripe/paymentintents")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/customers/")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "api/v1/products/")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "api/v1/products/{id}")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "api/v1/products/updateProductSize")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "api/v1/orderItems/")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "api/v1/orderItems/{id}")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/order/")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/order/")
                .authenticated()
                .requestMatchers(HttpMethod.POST, "api/v1/orderItems/")
                .authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/stripe/")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "api/v1/orderItems/forNonUser")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/order/forNonUser")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/customers/signin")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/customers/signup")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/products/")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/products/")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/customers/pregister")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic(withDefaults()).formLogin()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}