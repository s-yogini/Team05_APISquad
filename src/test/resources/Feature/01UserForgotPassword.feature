
@ForgotPassword
Feature: Forgot Password Controller

  Background:
    Given Admin sets No Auth

  @ForgotPasswordTest
  Scenario Outline: Admin forgot password
    Given Admin creates forgot password request with "<ScenarioName>"
    When Admin calls login HTTPS method with endpoint
    Then Admin validates response

    Examples:
      | ScenarioName              |
      | valid email               |
      | special chars in email    |
      | invalid email             |
      | null body                 |
      | invalid content           |
      | invalid endpoint          |