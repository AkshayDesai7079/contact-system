package com.example.contactmanagerapplication.services;

import com.example.contactmanagerapplication.model.User;
import com.example.contactmanagerapplication.repository.UserRepository;
import com.example.contactmanagerapplication.userdetails.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findUserByEmail(username);

        System.out.println(user);

        if (user!=null){
            return new MyUserDetails(user);
        }

        return null;
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new User("akshay","akshay@123",new ArrayList<>());
//    }
}
