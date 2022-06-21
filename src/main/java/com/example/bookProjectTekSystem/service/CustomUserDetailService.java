package com.example.bookProjectTekSystem.service;

import com.example.bookProjectTekSystem.model.CustomUserDetail;
import com.example.bookProjectTekSystem.model.User;
import com.example.bookProjectTekSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);

        user.orElseThrow(()-> new UsernameNotFoundException("User not found"));
        UserDetails userDetails = user.map(CustomUserDetail::new).get();

        Authentication authentication= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) ;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return user.map(CustomUserDetail::new).get();
    }

    public Optional<User> loadUser(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        return user;
    }
}

