<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contact Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4 text-primary">My Contact Manager</h2>

    <!-- Success/Error Message -->
    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-info alert-dismissible fade show">
            ${sessionScope.msg}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% session.removeAttribute("msg"); %>
    </c:if>

    <div class="text-end mb-3">
        <a href="<%=request.getContextPath()%>/add" class="btn btn-success">+ Add New Contact</a>
    </div>

    <table class="table table-hover table-bordered shadow-sm">
        <thead class="table-primary">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="contact" items="${listContact}">
                <tr>
                    <td><c:out value="${contact.id}" /></td>
                    <td><c:out value="${contact.name}" /></td>
                    <td><c:out value="${contact.email}" /></td>
                    <td><c:out value="${contact.phone}" /></td>
                    <td><c:out value="${contact.address}" /></td>
                    <td>
                        <a href="edit?id=<c:out value='${contact.id}' />" class="btn btn-sm btn-warning">Edit</a>
                        <a href="delete?id=<c:out value='${contact.id}' />" 
                           class="btn btn-sm btn-danger" 
                           onclick="return confirm('Are you sure you want to delete?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Contact Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4 text-primary">My Contact Manager</h2>

    <!-- Success/Error Message -->
    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-info alert-dismissible fade show">
            ${sessionScope.msg}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% session.removeAttribute("msg"); %>
    </c:if>

    <div class="text-end mb-3">
        <a href="<%=request.getContextPath()%>/add" class="btn btn-success">+ Add New Contact</a>
    </div>

    <table class="table table-hover table-bordered shadow-sm">
        <thead class="table-primary">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="contact" items="${listContact}">
                <tr>
                    <td><c:out value="${contact.id}" /></td>
                    <td><c:out value="${contact.name}" /></td>
                    <td><c:out value="${contact.email}" /></td>
                    <td><c:out value="${contact.phone}" /></td>
                    <td><c:out value="${contact.address}" /></td>
                    <td>
                        <a href="edit?id=<c:out value='${contact.id}' />" class="btn btn-sm btn-warning">Edit</a>
                        <a href="delete?id=<c:out value='${contact.id}' />" 
                           class="btn btn-sm btn-danger" 
                           onclick="return confirm('Are you sure you want to delete?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>