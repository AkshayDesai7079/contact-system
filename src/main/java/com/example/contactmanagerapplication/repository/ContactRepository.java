package com.example.contactmanagerapplication.repository;

import com.example.contactmanagerapplication.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {

     Contact findContactByCid(int id);

     List<Contact>findContactsByUserId(int uid);

     List<Contact>findContactsByName(String name);

     List<Contact> findContactsByNameContaining(String inputString);




}
