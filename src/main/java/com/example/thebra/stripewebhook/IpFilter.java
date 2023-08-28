package com.example.thebra.stripewebhook;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class IpFilter extends GenericFilterBean {

    private final List<String> allowedIpAddresses = Arrays.asList(
            "3.18.12.63",
            "3.130.192.231",
            "13.235.14.237"
            ,"13.235.122.149",
            "18.211.135.69",
            "35.154.171.200",
            "52.15.183.38",
            "54.88.130.119",
            "54.88.130.237",
            "54.187.174.169",
            "54.187.205.235",
            "54.187.216.72"
            );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isStripeWebhookEndpoint(request)) {
            String ipAddress = request.getRemoteAddr();
            if (!allowedIpAddresses.contains(ipAddress)) {
                response.getWriter().write("Access Denied"); // Customize your response here
                return;
            }
        }

        chain.doFilter(request, response);
    }
    private boolean isStripeWebhookEndpoint(ServletRequest request) {
        // Check if the request is targeting the Stripe webhook endpoint
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        return requestURI.equals("/webhooks/payment-intent-succeeded");
    }
}