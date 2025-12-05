
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- CRITICAL FIX: Change JSTL URI from 'http://java.sun.com/jsp/jstl/core' to 'jakarta.tags.core' --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Manager - All Contacts</title>
    <!-- Use Tailwind CSS for a clean, responsive UI -->
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; background-color: #f7f9fb; }
        .message-success { background-color: #d1fae5; color: #065f46; border-left: 5px solid #10b981; }
        .message-error { background-color: #fee2e2; color: #991b1b; border-left: 5px solid #ef4444; }
    </style>
</head>
<body class="p-4 md:p-8">
    <div class="max-w-4xl mx-auto bg-white p-6 md:p-10 rounded-xl shadow-2xl">
        <h1 class="text-3xl font-bold text-gray-800 mb-6 border-b pb-3">
            Contact Manager Sprint 2
        </h1>

        <!-- MESSAGING SYSTEM (User Stories 4 & 5) -->
        <!-- Use JSTL c:if to check for session attributes -->
        <c:if test="${not empty sessionScope.message}">
            <div class="message-success p-4 mb-6 rounded-lg text-sm transition duration-300 ease-in-out">
                <p class="font-semibold">${sessionScope.message}</p>
                <!-- Clear the session attribute immediately after display -->
                <c:remove var="message" scope="session"/>
            </div>
        </c:if>

        <c:if test="${not empty sessionScope.error}">
            <div class="message-error p-4 mb-6 rounded-lg text-sm transition duration-300 ease-in-out">
                <p class="font-semibold">${sessionScope.error}</p>
                <!-- Clear the session attribute immediately after display -->
                <c:remove var="error" scope="session"/>
            </div>
        </c:if>

        <!-- ADD CONTACT BUTTON -->
        <div class="mb-8 flex justify-end">
            <a href="contacts?action=new"
               class="px-6 py-2 bg-indigo-600 text-white font-semibold rounded-lg shadow-md hover:bg-indigo-700 transition duration-150 ease-in-out focus:outline-none focus:ring-4 focus:ring-indigo-300">
                + Add New Contact
            </a>
        </div>

        <!-- CONTACT LIST (User Story 1: See previous contacts) -->
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">
            <c:choose>
                <c:when test="${not empty contactList}">
                    Your Managed Contacts (${contactList.size()})
                </c:when>
                <c:otherwise>
                    No Contacts Found
                </c:otherwise>
            </c:choose>
        </h2>

        <div class="overflow-x-auto shadow-lg rounded-xl">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                    <tr>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Phone</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
                        <th class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                    </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                    <!-- JSTL c:forEach for iteration (The UI Optimization/Best Practice) -->
                    <c:forEach var="contact" items="${contactList}">
                        <tr class="hover:bg-gray-50 transition duration-100">
                            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${contact.id}</td>
                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">${contact.name}</td>
                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">${contact.phone}</td>
                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">${contact.email}</td>
                            <td class="px-6 py-4 whitespace-nowrap text-center text-sm font-medium">
                                <!-- Edit Link (User Story 2) -->
                                <a href="contacts?action=edit&id=${contact.id}"
                                   class="text-indigo-600 hover:text-indigo-900 mx-2 transition duration-150">Edit</a>
                                |
                                <!-- Delete Link (User Story 3) -->
                                <a href="contacts?action=delete&id=${contact.id}"
                                   onclick="return confirm('Are you sure you want to delete ${contact.name}?')"
                                   class="text-red-600 hover:text-red-900 mx-2 transition duration-150">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${empty contactList}">
            <p class="text-center text-gray-500 py-8">
                Click "Add New Contact" to get started!
            </p>
        </c:if>
    </div>
</body>
</html>