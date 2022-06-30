package com.example.contactmanagerapplication.services;

import com.example.contactmanagerapplication.config.SecurityConfiguration;
import com.example.contactmanagerapplication.model.Role;
import com.example.contactmanagerapplication.repository.UserRepository;
import com.example.contactmanagerapplication.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class UserService {

    private Logger logger= LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public User addUser(User user)throws UsernameNotFoundException {

        if (userRepository.findUserByEmail(user.getEmail())==null){
            user.setPassword(securityConfiguration.passwordEncoder().encode(user.getPassword()));
            Set<Role> roles=roleService.findRoleByIdsIn(user.getUserRole());
            user.setRoles(roles);
            return userRepository.save(user);
        }else {
            throw new UsernameNotFoundException("username not found"+user.getEmail());
        }
    }
}
