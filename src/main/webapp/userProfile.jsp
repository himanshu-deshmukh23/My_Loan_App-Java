<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #121212;
            color: #ffffff;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: #1e1e1e;
            box-shadow: 0 0 10px rgba(0,0,0,0.5);
        }
        h1, h2 {
            color: #ffffff;
        }
        form {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 10px;
        }
        input[type="text"] {
            padding: 10px;
            margin-bottom: 10px;
            width: calc(100% - 22px);
            border: 1px solid #444;
            border-radius: 4px;
            background-color: #333;
            color: #fff;
        }
        button {
            padding: 10px 20px;
            background-color: #5cb85c;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #4cae4c;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #444;
            text-align: left;
        }
        th {
            background-color: #333;
        }
        tr:nth-child(even) {
            background-color: #2a2a2a;
        }
        .logout-button {
            margin-top: 20px;
            background-color: #d9534f;
        }
        .logout-button:hover {
            background-color: #c9302c;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Hello ${user.username}!</h1><br>
		<h2>Apply for loan</h2>
        <form action="applyLoan" method="post">
            <label>Loan Amount: <input type="text" name="loanAmount"></label><br>
            <label>Duration (months): <input type="text" name="durationMonths"></label><br>
            <input type="hidden" name="userId" value="${user.userId}">
            <button type="submit">Apply</button>
        </form>

        <h2>My Loans</h2>
        <table>
            <thead>
                <tr>
                    <th>Loan Amount</th>
                    <th>Duration (months)</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="loan" items="${loans}">
                    <tr>
                        <td>${loan.loanAmount}</td>
                        <td>${loan.durationMonths}</td>
                        <td>${loan.status}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <h2>Pending Repayments</h2>
        <table>
            <thead>
                <tr>
                    <th>Loan ID</th>
                    <th>Repayment Amount</th>
                    <th>Due Date</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="repayment" items="${repayments}">
                    <c:if test="${repayment.repaymentStatus != 'paid'}">
                        <tr>
                            <td>${repayment.loanId}</td>
                            <td>${repayment.amount}</td>
                            <td>${repayment.dueDate}</td>
                            <td>${repayment.repaymentStatus}</td>
                            <td>
                                <form action="payRepayment" method="post" style="display:inline;">
                                    <input type="hidden" name="repaymentId" value="${repayment.getId()}">
                                    <button type="submit">Pay</button>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
        
        <form action="logout" method="post">
            <button type="submit" class="logout-button">Logout</button>
        </form>
    </div>
</body>
</html>
