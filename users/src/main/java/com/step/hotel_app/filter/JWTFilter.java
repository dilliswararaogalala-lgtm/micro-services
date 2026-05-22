package com.step.hotel_app.filter;

import com.step.hotel_app.repository.UserRepository;
import com.step.hotel_app.service.AppUserDetailService;
import com.step.hotel_app.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTService jWTService;
    private final AppUserDetailService userDetailsService;

    public JWTFilter(JWTService jWTService, AppUserDetailService userDetailService) {
        this.jWTService = jWTService;
        this.userDetailsService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            token = token.substring(7);
            String userName = jWTService.getUser(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if(jWTService.isValidToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (Throwable e){
            logger.error(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
