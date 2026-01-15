@ResetPassword
Feature: Reset Password Controller

  Scenario Outline: Admin Reset Password Validation
    Given Admin sets "<AuthType>"
    And Admin creates reset password request with "<ScenarioName>"
    When Admin calls login HTTPS method with endpoint
    Then Admin validates response

    Examples: 
      | ScenarioName                           | AuthType     |
      | valid email and new password           | Bearer Token |
      | valid email and old password           | No Auth      |
      | special characters in password         | Bearer Token |
      | invalid baseURL                        | Bearer Token |
      | invalid endpoint                       | Bearer Token |
      | valid data and invalid content type    | Bearer Token |
      | invalid method                         | Bearer Token |
      | invalid email                          | Bearer Token |