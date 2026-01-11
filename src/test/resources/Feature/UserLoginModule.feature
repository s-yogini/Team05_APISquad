Feature: Admin Login Controller

  This feature covers all scenarios related to admin authentication,
  including login, forgot password, logout, and password reset.

  Background:
    Given Admin sets No Auth

 
  # Token (Login) Scenarios
  
  Scenario: Admin generates token with valid credentials
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 201 Created with auto-generated token

  Scenario: Admin generates token with invalid method
    Given Admin creates request with valid credentials
    When Admin calls GET HTTPS method with POST endpoint
    Then Admin receives 405 Method Not Allowed

  Scenario: Admin generates token with invalid base URL
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with invalid base URL
    Then Admin receives 400 Bad Request

  Scenario: Admin generates token with invalid content type
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with invalid content type
    Then Admin receives 415 Unsupported Media Type

  Scenario: Admin generates token with invalid endpoint
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with invalid endpoint
    Then Admin receives 401 Unauthorized

  Scenario: Admin generates token with special characters in email
    Given Admin creates request with special characters in email
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Bad Credentials" and false success

  Scenario: Admin generates token with special characters in password
    Given Admin creates request with special characters in password
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Bad Credentials" and false success

  Scenario: Admin generates token with numbers in email
    Given Admin creates request with numbers in email
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Bad Credentials" and false success

  Scenario: Admin generates token with numbers in password
    Given Admin creates request with numbers in password
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Bad Credentials" and false success

  Scenario: Admin generates token with null password
    Given Admin creates request with null password
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Password is Mandatory" and false success

  Scenario: Admin generates token with null email
    Given Admin creates request with null email
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Email Id is Mandatory" and false success

  Scenario: Admin generates token with null body
    Given Admin creates request with null body
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 400 Bad Request


  # Forgot Password Scenarios
 
  Scenario: Admin generates token for forgot password with valid credentials
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 201 Created with auto-generated token and email sent to registered email ID

  Scenario: Admin generates token for forgot password with invalid endpoint
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with invalid endpoint
    Then Admin receives 404 Not Found

  Scenario: Admin generates token for forgot password with special characters in email
    Given Admin creates request with special characters in email
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Invalid email"

  Scenario: Admin generates token for forgot password with invalid admin email
    Given Admin creates request with invalid email
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Invalid email"

  Scenario: Admin generates token for forgot password with null body
    Given Admin creates request with null body
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Email Id is Mandatory" and false success

  Scenario: Admin generates token for forgot password with invalid content type
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with invalid content type
    Then Admin receives 415 Unsupported Media Type

  # Logout Scenarios

  Scenario: Admin logout with valid token
    Given Admin sets authorization to bearer token and creates request
    When Admin calls GET HTTPS method with valid endpoint
    Then Admin receives 200 OK with response "Logout successful"

  Scenario: Admin logout with invalid base URL
    Given Admin sets authorization to bearer token and creates request
    When Admin calls GET HTTPS method with invalid base URL
    Then Admin receives 404 Not Found

  Scenario: Admin logout with invalid endpoint
    Given Admin sets authorization to bearer token and creates request
    When Admin calls GET HTTPS method with invalid endpoint
    Then Admin receives 404 Not Found

  Scenario: Admin logout with invalid method
    Given Admin sets authorization to bearer token and creates request
    When Admin calls POST HTTPS method with invalid endpoint
    Then Admin receives 405 Method Not Allowed

  Scenario: Admin logout without auth
    Given Admin sets No Auth and creates request
    When Admin calls GET HTTPS method with valid endpoint
    Then Admin receives 401 Unauthorized

  Scenario: Admin logout with old token after logout
    Given Admin sets authorization to bearer old token and creates request
    When Admin calls GET HTTPS method with valid endpoint
    Then Admin receives 401 Unauthorized


  # Reset Password Scenarios
  
  Scenario: Admin resets password with valid data
    Given Admin sets authorization to bearer token
    Given Admin creates request with valid email and new password
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 200 OK with response "Password saved" and true

  Scenario: Admin login with old password after reset
    Given Admin sets No Auth
    Given Admin creates request with valid email and old password
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 Unauthorized

  Scenario: Admin resets password with special characters in password
    Given Admin sets authorization to bearer token
    Given Admin creates request with new password containing special characters
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 400 Bad Request

  Scenario: Admin resets password with invalid base URL
    Given Admin sets authorization to bearer token
    Given Admin creates request with valid email and new password
    When Admin calls POST HTTPS method with invalid base URL
    Then Admin receives 404 Not Found

  Scenario: Admin resets password with invalid endpoint
    Given Admin sets authorization to bearer token
    Given Admin creates request with valid email and new password
    When Admin calls POST HTTPS method with invalid endpoint
    Then Admin receives 404 Not Found

  Scenario: Admin resets password with invalid content type
    Given Admin sets authorization to bearer token
    Given Admin creates request with valid data and invalid content type
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 415 Unsupported Media Type

  Scenario: Admin resets password with invalid method
    Given Admin sets authorization to bearer token
    Given Admin creates request with valid email and new password
    When Admin calls GET HTTPS method with valid endpoint
    Then Admin receives 405 Method Not Allowed

  Scenario: Admin resets password with invalid email
    Given Admin sets authorization to bearer token
    Given Admin creates request with new password and invalid email
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 400 Bad Request with response "Invalid"
