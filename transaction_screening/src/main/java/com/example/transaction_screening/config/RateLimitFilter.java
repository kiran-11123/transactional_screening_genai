package com.example.transaction_screening.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.Filter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.io.IOException;
import java.time.Duration;

@Component
@Order(1)
public class RateLimitFilter {


    private Map<String,Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createBucket() {
         Bandwidth bandwidth = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));

         return Bucket.builder().addLimit(bandwidth).build();
    }

     
    public void doFilter(ServletRequest request , ServletResponse response , FilterChain chain) throws IOException, ServletException{
          
        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        String clientIp = httpRequest.getRemoteAddr();

           Bucket bucket = buckets.computeIfAbsent(
                clientIp,
                ip -> createBucket()
        );

        if(bucket.tryConsume(1)){
            chain.doFilter(request, response);
        }else {

            httpResponse.setStatus(
                    HttpStatus.TOO_MANY_REQUESTS.value()
            );

            httpResponse.setContentType("application/json");

            httpResponse.getWriter().write(
                    """
                    {
                        "status": 429,
                        "message": "Too many requests. Please try again later."
                    }
                    """
            );
        }


    }

}
