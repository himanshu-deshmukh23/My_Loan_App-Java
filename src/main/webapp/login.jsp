<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Personal Loan Management App</title>
    <link rel="stylesheet" href="stylesheet.css">

</head>
<body>
    <header>
        <div class="container">
            <h1>Personal Loan Management</h1>
        </div>
    </header>
    <main>
        <section class="login">
            <div class="container">
                <h2>Login</h2>
                <form action="login" method="POST">
                    <div class="form-group">
                        <label for="email">Username</label>
                        <input type="text" id="email" name="email" placeholder="Enter your username" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" placeholder="Enter your password" required>
                    </div>
                    <button type="submit" class="login-btn">Login</button>
                </form>
            </div>
        </section>
    </main>
    <footer>
        <div class="container">
            <p>&copy; 2024 Personal Loan Management App</p>
        </div>
    </footer>
</body>
</html>
