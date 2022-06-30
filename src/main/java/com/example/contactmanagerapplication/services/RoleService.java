package com.example.contactmanagerapplication.services;

import com.example.contactmanagerapplication.model.Role;
import com.example.contactmanagerapplication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Set<Role> findRoleByIdsIn(List<Integer> roleIdList){
        return new HashSet<>(roleRepository.findByIdIsIn(roleIdList));
    }

}
