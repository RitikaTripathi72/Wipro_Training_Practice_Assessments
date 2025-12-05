package com.wipro.assessment.dao;

import com.wipro.assessment.model.Contact;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Contact Data Access Object (DAO) using in-memory storage (List)
 * for simplicity and demonstration.
 */
public class ContactDAO {
    private static List<Contact> contacts = new ArrayList<>();
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    // Initial data for Sprint 1 "previous contacts" requirement
    static {
        // Initialize contacts by explicitly getting the ID before creating the object.
        // This structural change sometimes helps IDEs/compilers resolve static block initialization errors.
        int id1 = ID_GENERATOR.getAndIncrement();
        contacts.add(new Contact(id1, "Alice Johnson", "555-0101", "alice@example.com"));
        
        int id2 = ID_GENERATOR.getAndIncrement();
        contacts.add(new Contact(id2, "Bob Smith", "555-0102", "bob@example.com"));
    }

    /**
     * Retrieves all contacts. (User Story 1: See previous contacts)
     */
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    /**
     * Finds a contact by ID.
     */
    public Contact getContactById(int id) {
        return contacts.stream()
                       .filter(c -> c.getId() == id)
                       .findFirst()
                       .orElse(null);
    }

    /**
     * Adds a new contact. (User Story 5: Record added message)
     * @return The newly created Contact object.
     */
    public Contact addContact(Contact contact) {
        if (contact.getName() == null || contact.getName().trim().isEmpty() || contact.getPhone() == null || contact.getPhone().trim().isEmpty()) {
             // Simulating an error case if required fields are missing
             return null; // Signals failure for User Story 4
        }
        contact.setId(ID_GENERATOR.getAndIncrement());
        contacts.add(contact);
        return contact;
    }

    /**
     * Updates an existing contact. (User Story 2: Modify contacts)
     * @return true if update was successful, false otherwise.
     */
    public boolean updateContact(Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == updatedContact.getId()) {
                contacts.set(i, updatedContact);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a contact by ID. (User Story 3: Delete contact)
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteContact(int id) {
        return contacts.removeIf(c -> c.getId() == id);
    }
}