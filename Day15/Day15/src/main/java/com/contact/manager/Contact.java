package com.contact.manager;

public class Contact {
	private String name;
    private String email;
    private String phone;
    private int id; // Unique ID for demonstration purposes

    // Constructor
    public Contact(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

}
