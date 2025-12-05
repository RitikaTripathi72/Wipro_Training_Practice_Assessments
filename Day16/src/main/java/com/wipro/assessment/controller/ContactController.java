package com.wipro.assessment.controller;

import com.wipro.assessment.dao.*;
import com.wipro.assessment.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller for Contact management. Mapped to /contacts.
 */
@WebServlet("/contacts")
public class ContactController extends HttpServlet {
    private ContactDAO contactDAO;
    private static final String LIST_PAGE = "/views/contactList.jsp";
    private static final String FORM_PAGE = "/views/contactForm.jsp";

    @Override
    public void init() throws ServletException {
        contactDAO = new ContactDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list"; // Default action

        try {
            switch (action) {
                case "new":
                case "edit":
                    showForm(request, response);
                    break;
                case "delete":
                    deleteContact(request, response);
                    break;
                case "list":
                default:
                    listContacts(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred: " + ex.getMessage());
            listContacts(request, response); // Redirect to list with error
        }
    }

    // Handles listing of all contacts (User Story 1)
    private void listContacts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Contact> contactList = contactDAO.getAllContacts();
        request.setAttribute("contactList", contactList);
        // Forward to the JSP for display
        request.getRequestDispatcher(LIST_PAGE).forward(request, response);
    }

    // Handles showing the form for Add/Edit
    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Contact existingContact = contactDAO.getContactById(id);
            request.setAttribute("contact", existingContact); // Pass existing contact to JSP
        }
        // Forward to the JSP form
        request.getRequestDispatcher(FORM_PAGE).forward(request, response);
    }

    // Handles deletion (User Story 3)
    private void deleteContact(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (contactDAO.deleteContact(id)) {
            request.getSession().setAttribute("message", "Contact successfully deleted!");
        } else {
            request.getSession().setAttribute("error", "Error: Contact ID " + id + " not found for deletion.");
        }
        // Redirect to prevent double submission/refresh issues
        response.sendRedirect(request.getContextPath() + "/contacts");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insert".equals(action) || "update".equals(action)) {
            saveContact(request, response);
        } else if ("delete".equals(action)) {
            // Re-route POST delete requests (e.g., from a button) to the doGet handler
            doGet(request, response);
        } else {
            // Default to listing if action is unknown
            listContacts(request, response);
        }
    }

    // Handles Add (Insert) and Modify (Update)
    private void saveContact(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get parameters
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        
        String message = null;
        String error = null;

        // Simple validation
        if (name == null || name.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {
            error = "Error: Name and Phone are required fields.";
            request.getSession().setAttribute("error", error); // User Story 4
            response.sendRedirect(request.getContextPath() + "/contacts?action=edit&id=" + idParam);
            return;
        }

        Contact contact = null;
        boolean isUpdate = idParam != null && !idParam.trim().isEmpty();

        if (isUpdate) {
            // Update Existing Contact (User Story 2)
            contact = new Contact(Integer.parseInt(idParam), name, phone, email);
            if (contactDAO.updateContact(contact)) {
                message = "Contact successfully modified!";
            } else {
                error = "Error: Could not find contact to update.";
            }
        } else {
            // Insert New Contact
            contact = new Contact(0, name, phone, email); // ID will be set by DAO
            Contact result = contactDAO.addContact(contact);
            if (result != null) {
                message = "Contact successfully added!"; // User Story 5
            } else {
                error = "Error: Failed to add contact. Check required fields."; // User Story 4
            }
        }

        // Set message/error in session for redirection
        if (message != null) {
            request.getSession().setAttribute("message", message);
        }
        if (error != null) {
            request.getSession().setAttribute("error", error);
        }

        // Redirect to list view
        response.sendRedirect(request.getContextPath() + "/contacts");
    }
}