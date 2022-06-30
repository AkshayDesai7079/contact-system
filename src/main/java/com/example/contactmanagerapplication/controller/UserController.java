package com.example.contactmanagerapplication.controller;

import com.example.contactmanagerapplication.jwt.JwtAuthRequest;
import com.example.contactmanagerapplication.jwt.JwtAuthResponse;
import com.example.contactmanagerapplication.model.User;
import com.example.contactmanagerapplication.services.CustomUserDetailsService;
import com.example.contactmanagerapplication.services.UserService;
import com.example.contactmanagerapplication.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    private Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     *
     * @param jwtRequest
     * generating the token with username and password
     * @return
     * @throws UsernameNotFoundException
     */

    @PostMapping(value = "/generate-token")
    public ResponseEntity<JwtAuthResponse> getToken(@RequestBody JwtAuthRequest jwtRequest) throws UsernameNotFoundException {

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new UsernameNotFoundException("Bad Credential");
        }

        UserDetails userDetails=this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        String token=this.jwtUtil.generateToken(userDetails);

        return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);
    }

    /**
     *
     * @param user
     * registering the user
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<User>registerUser(@Valid @RequestBody User user){

        if (user!=null){
            logger.debug("Registering user... ");
            User newUser=userService.addUser(user);
            logger.info("user added successfully");
            return ResponseEntity.ok(newUser);
        }
        else {
            logger.error("Registering failed... ");
            throw new RuntimeException();
        }

    }

}
