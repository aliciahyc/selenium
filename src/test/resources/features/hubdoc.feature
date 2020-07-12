Feature: Use the hubdoc to manage my documents
  As a hubdoc user
  I should be able to upload my document
  I should be able to process my document

  Background:
    When I login with email "h_yanchun@hotmail.com" and password "Abcde123!"

  @test
  Scenario: process a document
    Then I mark document as paid
    Then I add a note "Notes for testing"
    Then I view document
    Then I download document
    Then I delete document
    Then I logout

  # this scenario will fail due to SHA-256 integrity and Content Security Policy prevent
  @test
  Scenario: upload a document
    Then I upload document "Foxglove Studios Invoices_CA.pdf"
    Then I logout
