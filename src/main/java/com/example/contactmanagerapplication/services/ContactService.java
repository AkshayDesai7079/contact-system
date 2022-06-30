package com.example.contactmanagerapplication.services;

import com.example.contactmanagerapplication.repository.ContactRepository;
import com.example.contactmanagerapplication.repository.UserRepository;
import com.example.contactmanagerapplication.model.Contact;
import com.example.contactmanagerapplication.model.User;
import com.example.contactmanagerapplication.exception.ContactNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private Logger logger= LoggerFactory.getLogger(ContactService.class);
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    public Contact addContact(Contact contact) throws ContactNotFound {

        int uid=contact.getUser().getId();
        String email=contact.getUser().getEmail();
        String passWord=contact.getUser().getPassword();


        User user=userRepository.findUserById(uid);

        int userId=user.getId();
        String userEmail=user.getEmail();
        String userPassword=user.getPassword();


        if (uid==userId && email.contains(userEmail) && passWord.equals(userPassword)){

            logger.info("Contact Added successfully");
            return contactRepository.save(contact);
        }
        else {
            logger.error("Contact not added successfully");
            throw new ContactNotFound("user id and password not found");
        }

    }

    public List<Contact>findContactsByUserId(int uid) throws ContactNotFound {
        User user=userRepository.findUserById(uid);

        if (user!=null){
            logger.info("user id found");
            return contactRepository.findContactsByUserId(user.getId());
        }else {
            logger.error("user id not found");
            throw new ContactNotFound("user not found");
        }
    }

    public List<Contact>findContactsByUserEmail(String email) throws ContactNotFound {
        User user=userRepository.findUserByEmail(email);

        System.out.println(user);
        if (user!=null){
            logger.info("user id found");
            List<Contact> searchedContacts = new ArrayList<>();
            contactRepository.findAll().stream().forEach(contact -> {
                if (contact.getUser().getEmail().equalsIgnoreCase(user.getEmail())) {
                    searchedContacts.add(contact);
                }
            });
            return searchedContacts;
        }else {
            logger.error("user id not found");
            throw new ContactNotFound("user not found");
        }
    }

    public List<Contact>findContactByMatchingCharacter(String name) throws ContactNotFound {
         if (name!=null){
             return contactRepository.findContactsByNameContaining(name);
         }
         else {
             throw new ContactNotFound("Name not found with this character");
         }
    }

    public List<Contact>findContactByName(String name) throws ContactNotFound {
        if (name!=null){
            return contactRepository.findContactsByName(name);
        }else {
            throw new ContactNotFound("contact not found with this name");
        }

    }

    public void deleteContact(int cid){

        contactRepository.deleteById(cid);
    }

    public List<Contact> findContactsNameWithSorting(Integer pageNo,Integer pageSize,String name){

        Pageable pageable= PageRequest.of(pageNo,pageSize, Sort.by(name));

        Page<Contact>page=contactRepository.findAll(pageable);

        if (page.hasContent()){
            return page.getContent();
        }else {
            return new ArrayList<Contact>();
        }
    }


    public Contact updateContact(Contact contact,int cid) throws ContactNotFound {
        Contact contact1=contactRepository.findContactByCid(cid);
        if (contact1!=null){

            contact1.setName(contact.getName());
            contact1.setPhone(contact.getPhone());

            logger.info("contact updated successfully");
            return contactRepository.save(contact1);

        }else {
            logger.error("contact could be updated");
            throw new ContactNotFound("contact not updated");
        }
    }




}
