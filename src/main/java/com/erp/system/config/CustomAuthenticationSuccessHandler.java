package com.erp.system.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        String targetUrl = determineTargetUrl(authentication);
        
        if (response.isCommitted()) {
            return;
        }
        
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();
            
            switch (authority) {
                case "ROLE_ADMIN":
                    return "/admin/dashboard";
                case "ROLE_MANAGER":
                    return "/manager/dashboard";
                case "ROLE_SALES":
                    return "/sales/dashboard";
                case "ROLE_PURCHASE":
                    return "/purchase/dashboard";
                case "ROLE_INVENTORY":
                    return "/inventory/dashboard";
                case "ROLE_FINANCE":
                    return "/finance/dashboard";
                default:
                    return "/dashboard";
            }
        }
        
        return "/dashboard";
    }
}