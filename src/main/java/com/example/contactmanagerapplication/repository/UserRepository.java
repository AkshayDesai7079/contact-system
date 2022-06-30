package com.example.contactmanagerapplication.repository;

import com.example.contactmanagerapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserById(int id);

    User findUserByEmail(String username);


}
