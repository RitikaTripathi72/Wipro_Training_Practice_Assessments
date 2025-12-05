package com.contact.manager;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles all contact management operations.
 * * Maps:
 * - /view  : Displays the welcome page, the list of contacts, and the 'Add Contact' button. (User Stories 1, 2, 5)
 * - /add   : Handles form submission (POST) and displays the form (GET). (User Stories 3, 4)
 */
public class ContactManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 3. Please use proper collection to store the contact information.
    // Using a ConcurrentHashMap for thread-safe, in-memory storage.
    private static final Map<Integer, Contact> CONTACT_STORE = new ConcurrentHashMap<>();
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(1);
    
    @Override
    public void init() throws ServletException {
        // Initialize with sample data for demonstration
        CONTACT_STORE.put(ID_COUNTER.getAndIncrement(), new Contact(1, "John Doe", "john@example.com", "555-1234"));
        CONTACT_STORE.put(ID_COUNTER.getAndIncrement(), new Contact(2, "Jane Smith", "jane@example.com", "555-5678"));
    }

    /**
     * Handles requests for the main view (/view) or displaying the add form (/add GET).
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Render the header (includes responsiveness setup)
        renderHeader(out, "Contact Manager");

        if (path == null || path.equals("/view")) {
            renderContactList(out, CONTACT_STORE.values(), request); // Pass request object
        } else if (path.equals("/add")) {
            renderAddForm(out, request); // Pass request object
        }

        // Render the footer
        renderFooter(out);
    }

    /**
     * Handles contact submission (only mapped to /add POST).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null && path.equals("/add")) {
            // 4. As a user I should be able to enter the contact information in the form.
            
            // 1. Get parameters
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            // 2. Simple validation (real apps need more robust validation)
            if (name != null && !name.trim().isEmpty() && email != null && phone != null) {
                // 3. Create and store new contact
                int newId = ID_COUNTER.getAndIncrement();
                Contact newContact = new Contact(newId, name, email, phone);
                CONTACT_STORE.put(newId, newContact);
                
                // Redirect back to the main list after successful addition (PRG pattern)
                // Use the context path and the new simple servlet mapping
                response.sendRedirect(request.getContextPath() + "/contacts/view");
                return;
            } else {
                // Handle incomplete form submission
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                renderHeader(out, "Add Contact Error");
                out.println("<div class='p-6 bg-red-100 border border-red-400 text-red-700 rounded-lg max-w-lg mx-auto mt-8'>");
                out.println("<p>Error: Please fill in all required fields.</p>");
                // Changed relative link to use dynamic full path
                out.println("<a href='" + request.getContextPath() + "/contacts/view' class='text-blue-600 hover:underline mt-4 block'>Go back to list</a>");
                out.println("</div>");
                renderFooter(out);
                return;
            }
        }
        // Default to showing the list if /add is somehow called with POST incorrectly
        doGet(request, response);
    }
    
    // --- View Rendering Methods (User Stories 1, 2, 5) ---
    
    private void renderHeader(PrintWriter out, String title) {
        // 1. Please make the application responsive. (Using Tailwind CSS CDN)
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>" + title + "</title>");
        out.println("    <script src='https://cdn.tailwindcss.com'></script>");
        out.println("    <style>");
        out.println("        body { font-family: 'Inter', sans-serif; background-color: #f4f7f9; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body class='min-h-screen p-4 sm:p-8'>");
        out.println("    <div class='max-w-4xl mx-auto'>");
        out.println("        <header class='bg-white shadow-lg p-6 rounded-xl mb-8'>");
        out.println("            <h1 class='text-4xl font-extrabold text-indigo-700 tracking-tight mb-2'>" + title + "</h1>");
        // 1. As a user I should be able to see the welcome page of the contact manager .
        out.println("            <p class='text-gray-500'>Manage your contacts efficiently.</p>");
        out.println("        </header>");
        out.println("        <main>");
    }

    private void renderFooter(PrintWriter out) {
        out.println("        </main>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    private void renderContactList(PrintWriter out, Collection<Contact> contacts, HttpServletRequest request) {
        // The servlet path is now always ContextRoot + /contacts
        String servletPathPrefix = request.getContextPath() + "/contacts";

        // 2. As a user I should be able to see the add contact button on the document.
        // 5. As a user I should be able to see all my added contacts
        
        // Add Contact Button: Now uses dynamic servletPathPrefix
        out.println("<div class='flex justify-end mb-6'>");
        out.println("    <a href='" + servletPathPrefix + "/add' class='bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-4 rounded-lg shadow-md transition duration-300 transform hover:scale-105'>+ Add New Contact</a>");
        out.println("</div>");

        out.println("<h2 class='text-2xl font-bold text-gray-800 mb-4'>Your Contacts (" + contacts.size() + ")</h2>");
        
        if (contacts.isEmpty()) {
            out.println("<p class='text-gray-500 bg-white p-6 rounded-lg shadow-sm'>You have no contacts yet. Click 'Add New Contact' to begin!</p>");
        } else {
            out.println("<div class='grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6'>");
            for (Contact contact : contacts) {
                out.println("<div class='bg-white p-6 rounded-xl shadow-lg border-t-4 border-indigo-500'>");
                out.println("    <h3 class='text-xl font-bold text-gray-900 mb-2'>" + contact.getName() + "</h3>");
                out.println("    <p class='text-gray-600 mb-1'><span class='font-medium text-indigo-500'>Email:</span> " + contact.getEmail() + "</p>");
                out.println("    <p class='text-gray-600'><span class='font-medium text-indigo-500'>Phone:</span> " + contact.getPhone() + "</p>");
                out.println("    <p class='text-xs text-gray-400 mt-2'>ID: " + contact.getId() + "</p>");
                out.println("</div>");
            }
            out.println("</div>");
        }
    }
    
    private void renderAddForm(PrintWriter out, HttpServletRequest request) {
        // The servlet path is now always ContextRoot + /contacts
        String servletPathPrefix = request.getContextPath() + "/contacts";
        
        // 3. As a user I should be able to see the add contact form after clicking the add contact button.
        out.println("<div class='bg-white p-6 sm:p-10 rounded-xl shadow-lg max-w-xl mx-auto'>");
        out.println("    <h2 class='text-3xl font-bold text-gray-800 mb-6'>Add New Contact</h2>");
        
        // The form action is set to dynamic servlet path to ensure correct POST submission
        out.println("    <form action='" + servletPathPrefix + "/add' method='POST' class='space-y-6'>");
        
        // Name Field
        out.println("        <div>");
        out.println("            <label for='name' class='block text-sm font-medium text-gray-700 mb-1'>Full Name *</label>");
        out.println("            <input type='text' id='name' name='name' required class='w-full p-3 border border-gray-300 rounded-lg focus:ring-indigo-500 focus:border-indigo-500 transition duration-150' placeholder='e.g., Alex Johnson'>");
        out.println("        </div>");
        
        // Email Field
        out.println("        <div>");
        out.println("            <label for='email' class='block text-sm font-medium text-gray-700 mb-1'>Email Address *</label>");
        out.println("            <input type='email' id='email' name='email' required class='w-full p-3 border border-gray-300 rounded-lg focus:ring-indigo-500 focus:border-indigo-500 transition duration-150' placeholder='e.g., alex@work.com'>");
        out.println("        </div>");
        
        // Phone Field
        out.println("        <div>");
        out.println("            <label for='phone' class='block text-sm font-medium text-gray-700 mb-1'>Phone Number *</label>");
        out.println("            <input type='tel' id='phone' name='phone' required class='w-full p-3 border border-gray-300 rounded-lg focus:ring-indigo-500 focus:border-indigo-500 transition duration-150' placeholder='e.g., 555-9876'>");
        out.println("        </div>");
        
        // Submission Buttons
        out.println("        <div class='flex flex-col sm:flex-row justify-between pt-4 space-y-4 sm:space-y-0 sm:space-x-4'>");
        // Cancel Link: Now uses dynamic servlet path
        out.println("            <a href='" + servletPathPrefix + "/view' class='text-center py-3 px-6 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition duration-150'>Cancel</a>");
        out.println("            <button type='submit' class='py-3 px-6 bg-green-600 hover:bg-green-700 text-white font-semibold rounded-lg shadow-md transition duration-150 transform hover:scale-[1.01]'>Save Contact</button>");
        out.println("        </div>");
        
        out.println("    </form>");
        out.println("</div>");
    }
}
