package com.example.bookProjectTekSystem.serviceTest;
;
import com.example.bookProjectTekSystem.model.User;

import com.example.bookProjectTekSystem.repository.UserRepository;

import com.example.bookProjectTekSystem.service.CustomUserDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CUserDetailServiceTest {

    @InjectMocks
    private CustomUserDetailService customUserDetailService;

    @Mock
    private UserRepository userRepository;

 @Test
    public void getUserByEmailTest(){
     User user1 = new User();
     user1.setId(1);
     user1.setEmail("ava@gmail.com");
     user1.setFirstName("Ava");
     user1.setLastName("shah");
     user1.setPassword("ava123");





     when(userRepository.findUserByEmail("ava@gmail.com")).thenReturn(Optional.of(user1));
     assertEquals("ava@gmail.com", customUserDetailService.loadUserByUsername(user1.getEmail()).getUsername());

 }




}
