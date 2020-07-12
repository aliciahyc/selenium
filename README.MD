# hubdoc

Web automation test project setup and running steps
Web automation test project test cases

* Build Management - Maven 
* Test Frameworks - Cucumber, Junit, Selenium
* Web Browser - Google Chrome Version 83.0.4103

### Project components ###

* Feature File - Written in Simple English . Supplies data for test cases.
* Step Definitions - All Steps in feature files are mapped to steps in step definitions. Step definitions 
					 can only call scenario classes.
* Scenario - Contains business logic and assertions. 

### How do I set up the project? ###

1. download the project from github
2. Use IntelliJ or other Java IDE to open the project
3. Dependencies and configurations on pom.xml file

### Where can I find the test scenarios of this project? ###

Test scenarios directory:
src/test/resources/features/hubdoc.feature

### What should I know before I run the  project? ###

1. Scenario: upload a document will fail and the browser needs to close manually
    Reason: 
    <input id="file-upload" name="file_upload" type="file" multiple="true" style="display: none;">
    This file_upload element is not interactive with ui user. Therefore selenium is not able to use sendKeys() to 
    upload the document.
    After change the element to be interactive (style="display: block;"), SHA-256 integrity and Content Security 
    Policy prevent the submission of the upload_file (script injection prevention).
2. Before runs Scenario: process a document, make sure there is at least one document in the "All Document" folder
    with "all" tag. 
3. Scenario: process a document - step: Then I download document
    This step needs to add assertion. Based on the test environment, specify the download destination directory, verify
    if the directory has the new downloaded document. 

### How do I run the project? ###

On the terminal:
step 1: navigate to the project directory
step 2: In the project directory run the following maven command: 
    mvn -B clean test -Dbrowser=chrome -Dcucumber.options=src/test/resources/features/hubdoc.feature -Dtags=@test  
    
### How do I analyse the test result after running the project? ###   
    
**Test passed example:**

Jul. 12, 2020 3:39:42 P.M. org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
    When I login with email "h_yanchun@hotmail.com" and password "Abcde123!" # hubdocStepDefinition.uploadDoc(String,String)

  @test2
  Scenario: process a document            # src/test/resources/features/hubdoc.feature:14
    Then I mark document as paid          # hubdocStepDefinition.markAsPaid()
    Then I add a note "Notes for testing" # hubdocStepDefinition.addNotes(String)
    Then I view document                  # hubdocStepDefinition.viewDocument()
    Then I download document              # hubdocStepDefinition.downloadDocument()
    Then I delete document                # hubdocStepDefinition.deleteDocument()

1 Scenarios (1 passed)
6 Steps (6 passed)
0m16.420s

[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 16.583 s - in hubdoc.hubdocTestRunner

**Test failed example:**

Failed scenarios:
src/test/resources/features/hubdoc.feature:10 # upload a document

1 Scenarios (1 failed)
2 Steps (1 failed, 1 passed)
0m10.381s

java.lang.AssertionError: expected:<4> but was:<5>
        at org.junit.Assert.fail(Assert.java:88)
        at org.junit.Assert.failNotEquals(Assert.java:834)
        at org.junit.Assert.assertEquals(Assert.java:645)
        at org.junit.Assert.assertEquals(Assert.java:631)
        at hubdoc.hubdocSenario.uploadDocument(hubdocSenario.java:80)
        at hubdoc.hubdocStepDefinition.uploadDocument(hubdocStepDefinition.java:17)
        at ✽.I upload document "Foxglove Studios Invoices_CA.pdf"(src/test/resources/features/hubdoc.feature:11)

[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 10.555 s <<< FAILURE! - in hubdoc.hubdocTestRunner

### What test cases should be added to the project? ### 

Test Suite: Process documents
Test cases:
1. Abstract information from document (verify the provider, price amount and date)
2. Edit document (Transaction details, mark as paid, previous document, next document)
3. Edit document image (zoom in, zoom out, rotate left, rotate right, reset, add tag)
4. Document state (all, processing, review, failed, archive)
5. Sort documents (Bill date, Due date, Upload date, Amount)

Test Suite: Folder management
Test cases:
1. Custom folders (add, rename, delete)
2. Manage files (move files, download all files, export to CSV )
2. Default folders

Test Suite: Account Management
Test cases:
1. Add accounts
2. Manage accounts
3. Hide accounts
4. Users
5. Organizations
6. Billing information
7. Integrations
8. Profile & Security

Test Suite: Search documents
Test cases:
1. Search by date
2. Search by amount
3. Search by supplier
4. Search by words
5. Search by document type

Test Suite: What's new
Test cases: verify urls are working
