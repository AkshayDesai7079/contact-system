package com.example.contactmanagerapplication.controller;

import com.example.contactmanagerapplication.model.Contact;
import com.example.contactmanagerapplication.exception.ContactNotFound;
import com.example.contactmanagerapplication.services.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class ContactController {
    private Logger logger= LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    /**
     * adding contact details with proper validation
     * @param contact contact to be save
     *
     * @return contact added in database
     */

    @PostMapping("/add-contact")
    public ResponseEntity<?>addContact(@RequestBody @Valid Contact contact){
        try {
            logger.debug("Adding Contact Details");
            contactService.addContact(contact);
            return ResponseEntity.ok("Contact added successfully");
        }catch (Exception e){
            logger.error("failed operation");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * search the contacts by user id
     * @param id contacts to be search by given id
     * @return list of contacts which matching with user id
     * @throws ContactNotFound
     */
    @GetMapping("/search/by-id")
    public List<Contact> findContactsByUserId(@RequestParam("id")int id) throws ContactNotFound {
        try {
            logger.debug("contact finding...");
            return contactService.findContactsByUserId(id);
        }catch (ContactNotFound e){
            logger.error("contact details not found");
            throw new ContactNotFound("Contacts not found..");
        }
    }

    /**
     * search the contacts by email
     * @param email contacts to be search with the email
     * @return list of contacts
     * @throws ContactNotFound
     */

    @GetMapping("/search/by-email")
    public List<Contact> findContactsByUserEmail(@RequestParam("email")String email) throws ContactNotFound {
        try {
            logger.debug("contact finding...");
            return contactService.findContactsByUserEmail(email);
        }catch (ContactNotFound e){
            logger.error("contact details not found");
            throw new ContactNotFound("Contacts not found..");
        }
    }

    /**
     * search the contacts by matching the input character
     * @param name contacts to be search by input character
     * @return list of contacts by matching the input character
     * @throws ContactNotFound
     */
    @GetMapping("/search/name")
    public List<Contact>findContactsBySameCharacter(@RequestParam("word")String name) throws ContactNotFound {
        try {
            return contactService.findContactByMatchingCharacter(name);

        }catch (ContactNotFound e){
            throw new ContactNotFound("Contact not found with this name");
        }
    }

    /**
     * search the contacts by name
     * @param name contacts to be search by name
     * @return list of contacts by matching the name
     * @throws ContactNotFound
     */

    @GetMapping("/search/by-name")
    public List<Contact> findContactsByName(@RequestParam("name")String name) throws ContactNotFound {

        try {
            logger.info("contact find with name");
            return contactService.findContactByName(name);
        }catch (ContactNotFound e){
            logger.error("name not found");
            throw new ContactNotFound("contact not found with this name");
        }

    }

    /**
     * search the records with pageno and limit and also sort the records with name
     * @param pageNo display all the contacts on given page no
     * @param pageSize display all the contacts on given limit
     * @param name sort the contacts by name
     * @return display the list of contacts with sorted name
     */
    @GetMapping("/search-page")
    public List<Contact>fetchAllPatient(@RequestParam("page") Integer pageNo,@RequestParam("size") Integer pageSize,@RequestParam(value = "name",defaultValue = "name")String name){

        try {
            logger.debug("contact is sorting with name");
            List<Contact>list=contactService.findContactsNameWithSorting(pageNo,pageSize,name);
            return list;
        }catch (Exception e){
            logger.error("contact is not sorted with name");
            throw new RuntimeException();
        }
    }

    /**
     * update the contact details
     * @param contact
     * @param id
     *
     * @return contact
     * @throws ContactNotFound
     */

    @PutMapping("/update")
    public Contact updateContactDetails(@RequestBody Contact contact,@RequestParam("cid")int id) throws ContactNotFound {

        try {
            logger.debug("updating the contact details");
            return contactService.updateContact(contact,id);
        }
        catch (ContactNotFound e){
            logger.error("failed..");
            throw new ContactNotFound("Contact updation failed");
        }
    }

    /**
     * delete the contact details with id
     * @param cid
     *
     * @return
     */

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteContact(@RequestParam("cid")int cid){
        try{
            logger.debug("deleting the contact details");
            contactService.deleteContact(cid);
            return ResponseEntity.ok("Contact Deleted successfully");
        }catch (Exception e){
            logger.error("deletion failed..");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
