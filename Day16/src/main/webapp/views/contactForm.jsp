<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- CRITICAL FIX: Change JSTL URI from 'http://java.sun.com/jsp/jstl/core' to 'jakarta.tags.core' --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${not empty requestScope.contact}">
                Modify Contact
            </c:when>
            <c:otherwise>
                Add New Contact
            </c:otherwise>
        </c:choose>
    </title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; background-color: #f7f9fb; }
    </style>
</head>
<body class="p-4 md:p-8">
    <div class="max-w-xl mx-auto bg-white p-8 md:p-10 rounded-xl shadow-2xl">

        <!-- Determine form title and action based on whether 'contact' is present in request scope -->
        <c:set var="isEdit" value="${not empty requestScope.contact}" />
        <c:set var="formTitle" value="${isEdit ? 'Modify Contact Details' : 'Add New Contact'}" />
        <c:set var="actionValue" value="${isEdit ? 'update' : 'insert'}" />
        <c:set var="submitText" value="${isEdit ? 'Save Changes' : 'Add Contact'}" />

        <h1 class="text-3xl font-bold text-gray-800 mb-6 border-b pb-3">${formTitle}</h1>

        <form action="contacts" method="POST" class="space-y-6">
            <!-- Hidden Fields for ID and Action -->
            <input type="hidden" name="action" value="${actionValue}">
            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${contact.id}">
            </c:if>

            <!-- Name Field -->
            <div>
                <label for="name" class="block text-sm font-medium text-gray-700 mb-1">Name (Required)</label>
                <input type="text" id="name" name="name" required
                       value="${contact.name}"
                       class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500">
            </div>

            <!-- Phone Field -->
            <div>
                <label for="phone" class="block text-sm font-medium text-gray-700 mb-1">Phone (Required)</label>
                <input type="tel" id="phone" name="phone" required
                       value="${contact.phone}"
                       class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500">
            </div>

            <!-- Email Field -->
            <div>
                <label for="email" class="block text-sm font-medium text-gray-700 mb-1">Email</label>
                <input type="email" id="email" name="email"
                       value="${contact.email}"
                       class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500">
            </div>

            <div class="flex justify-between items-center pt-4">
                <a href="contacts"
                   class="inline-flex justify-center py-2 px-4 border border-gray-300 shadow-sm text-sm font-medium rounded-lg text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-150">
                    Cancel
                </a>

                <button type="submit"
                        class="inline-flex justify-center py-2 px-6 border border-transparent shadow-md text-sm font-medium rounded-lg text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-4 focus:ring-indigo-300 transition duration-150">
                    ${submitText}
                </button>
            </div>
        </form>
    </div>
</body>
</html>