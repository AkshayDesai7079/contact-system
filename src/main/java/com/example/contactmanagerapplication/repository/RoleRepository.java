package com.example.contactmanagerapplication.repository;

import com.example.contactmanagerapplication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    List<Role> findByIdIsIn(List<Integer> roleIdList);
}

