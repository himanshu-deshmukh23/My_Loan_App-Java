<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Profile</title>
    <style>
        body {
            background-color: #2b2b2b;
            color: #f5f5f5;
            font-family: Arial, sans-serif;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #ffa500;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table, th, td {
            border: 1px solid #555;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #444;
        }
        tr:nth-child(even) {
            background-color: #333;
        }
        tr:hover {
            background-color: #555;
        }
		.button {
		    padding: 10px 20px;
		    color: #fff;
		    background-color: #ffa500;
		            border: none;
		            cursor: pointer;
		            text-align: center;
		        }
		        .button:hover {
		            background-color: #ff7f00;
		        }
        .approve-button {
            background-color: #5cb85c;
        }
        .approve-button:hover {
            background-color: #4cae4c;
        }
        .reject-button {
            background-color: #d9534f;
        }
        .reject-button:hover {
            background-color: #c9302c;
        }
        .status-buttons {
            display: flex;
            gap: 10px;
        }
        .logout-button {
            margin-top: 20px;
            background-color: #d9534f;
            color: #fff;
            text-align: center;
            display: block;
            width: 100px;
            margin-left: auto;
            margin-right: auto;
            padding: 10px 20px;
            text-decoration: none;
            border: none;
            cursor: pointer;
        }
        .logout-button:hover {
            background-color: #c9302c;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Admin Dashboard</h1>
    <table>
        <thead>
        <tr>
            <th>Username</th>
            <th>Loan Amount</th>
            <th>Loan Terms (Months)</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="loan" items="${loans}">
            <tr>
                <td>${loan.user.username}</td>
                <td>${loan.loanAmount}</td>
                <td>${loan.durationMonths}</td>
                <td>${loan.status}</td>
                <td class="status-buttons">
                    <form action="approveLoan" method="post">
                        <input type="hidden" name="loanId" value="${loan.loanId}">
                        <button type="submit" class="button approve-button">Approve</button>
                    </form>
                    <form action="rejectLoan" method="post">
                        <input type="hidden" name="loanId" value="${loan.loanId}">
                        <button type="submit" class="button reject-button">Reject</button>
                    </form>
                    <form action="deleteLoan" method="post">
                        <input type="hidden" name="loanId" value="${loan.loanId}">
                        <button type="submit" class="button">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form action="logout" method="post">
        <button type="submit" class="logout-button">Logout</button>
    </form>
</div>
</body>
</html>
