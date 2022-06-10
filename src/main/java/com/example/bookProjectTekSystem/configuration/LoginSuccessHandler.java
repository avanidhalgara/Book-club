//package com.example.bookProjectTekSystem.configuration;
//
//import com.example.bookProjectTekSystem.model.CustomUserDetail;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//@Component
//public class LoginSuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws ServletException, IOException {
//
//        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
//
//        String redirectURL = request.getContextPath();
//
//        if (userDetails.hasRole("ADMIN")) {
//            redirectURL = "/admin";
//        } else {
//            redirectURL = "/";
//        }
//
//            response.sendRedirect(redirectURL);
//
//        }
//    }

