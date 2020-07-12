package hubdoc;

import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.util.*;

public class hubdocSenario {
  protected WebDriver driver;

  public void login(String email, String password) {
    String webDriverLocation = "chromedriver";
    String homePageUrl = "https://app.hubdoc.com/";
    System.setProperty("webdriver.chrome.driver", webDriverLocation);

    //launch chrome webdriver
    driver = new ChromeDriver();
    driver.get(homePageUrl);
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    WebElement signInButton = driver.findElement(By.id("btn-signin"));
    new WebDriverWait(driver, 10).
        until(ExpectedConditions.textToBePresentInElement(signInButton, "Sign In Securely"));

    //login with email and password
    WebElement emailTextField = driver.findElement(By.id("email"));
    emailTextField.sendKeys(email);
    WebElement passwordTextField = driver.findElement(By.id("password"));
    passwordTextField.sendKeys(password);
    signInButton.click();
    WebElement uploadButton = driver.findElement(By.id("add-receipt"));
    new WebDriverWait(driver, 10).
        until(ExpectedConditions.visibilityOf(uploadButton));
  }

  // this test will failed because of SHA-256 integrity and Content Security Policy prevent
  // user to modify the html element and submit a file (script injection prevention)
  public void uploadDocument(String docName) {
    //get number of documents in All Documents folder
    WebElement allDocumentCountElement = driver.findElement(By.cssSelector("div.nav-label[title='All Documents'] > span.doc-count"));
    String allDocumentCountString = allDocumentCountElement.getText();
    int start = allDocumentCountString.indexOf("(");
    int end = allDocumentCountString.indexOf(")");
    String numString = allDocumentCountString.substring(start+1, end);
    int numOld = Integer.parseInt(numString);

    //click on Upload Document button
    WebElement uploadButton = driver.findElement(By.id("add-receipt"));
    uploadButton.click();
    WebElement browseButton = driver.findElement(By.id("uploadifive-file-upload"));
    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(browseButton));

    //locate input element for file uploading and change it to be interactive with ui user
    WebElement fileUpload = driver.findElement(By.id("file-upload"));
    ((JavascriptExecutor)driver).executeScript("arguments[0]. setAttribute('style', 'display: block;')", fileUpload);

    //send document absolute path to file-upload input and submit the form
    File doc = new File("src/test/java/assets/" + docName);
    fileUpload.sendKeys(doc.getAbsolutePath());
    fileUpload.submit();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    //verify document  is added by comparing the numbers of documents in All Document folder
    selectFirstDocument();
    allDocumentCountElement = driver.findElement(By.cssSelector("div.nav-label[title='All Documents'] > span.doc-count"));
    String allDocumentCountStringNew = allDocumentCountElement.getText();
    start = allDocumentCountStringNew.indexOf("(");
    end = allDocumentCountStringNew.indexOf(")");
    String numStringNew = allDocumentCountStringNew.substring(start+1, end);
    int numNew = Integer.parseInt(numStringNew);

    //will failed at this step because fail to upload new document
    Assert.assertEquals(numNew, numOld + 1);
    logout();
  }

  public void markAsPaid(){
    //locate the first document item on All tag
    selectFirstDocument();

    //locate elements related to mark as paid function
    WebElement paidStripe = driver.findElement(By.id("paid-stripe"));
    String originalClass = paidStripe.getAttribute("class");
    WebElement markAsPaid = driver.findElement(By.id("mark-as-paid"));

    //remove paid stripe if it displays
    if(!originalClass.contains("display-none"))
      markAsPaid.click();

      //add paid stripe
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      markAsPaid = driver.findElement(By.id("mark-as-paid"));
      markAsPaid.click();
      String finalStyle = paidStripe.getAttribute("style");
      Assert.assertEquals("display: block;", finalStyle);

  }

  public void addNotes(String notes){
    //locate the first document item on All tag
    selectFirstDocument();

    //add notes
    WebElement addNotesButton = driver.findElement(By.id("add-notes"));
    WebElement addNotesTextField = driver.findElement(By.id("notes"));

    //make text field interactive
    if(!addNotesTextField.getAttribute("style").equals("display: block;"))
      addNotesButton.click();
    WebElement textarea = driver.findElement(By.tagName("textarea"));
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    textarea.clear();
    textarea.sendKeys(notes);
    WebElement saveButton = driver.findElement(By.cssSelector("button.btn-save"));
    saveButton.click();

    //refresh page to get the new notes
    driver.navigate().refresh();
    selectFirstDocument();
    textarea = driver.findElement(By.tagName("textarea"));
    Assert.assertEquals("Notes for testing", textarea.getText());
  }

  public void viewLargerDocument(){
    //locate the first document item on All tag
    selectFirstDocument();

    //get info on old window before click on view button
    String previousWindow = driver.getWindowHandle();
    String previousUrl = driver.getCurrentUrl();
    WebElement allDocuments = driver.findElement(By.cssSelector("a[title = 'View large']"));
    allDocuments.click();

    //switch to new window
    Set<String> windows = driver.getWindowHandles();
    String[] currentWindows = windows.toArray(new String[2]);
    driver.switchTo().window(currentWindows[1]);

    //verify new window
    String currentUrl = driver.getCurrentUrl();
    Assert.assertTrue(currentUrl.contains(previousUrl));
    driver.close();

    //switch back to previous window
    driver.switchTo().window(previousWindow);
    Assert.assertEquals(driver.getCurrentUrl(), previousUrl);

  }

  public void downloadDocument(){
    selectFirstDocument();
    WebElement downloadButton = driver.findElement(By.cssSelector("a[title = 'Download']"));
    downloadButton.click();

  }

  public void deleteDocument(){
    //get number of documents in trash folder
    WebElement trashCountElement = driver.findElement(By.cssSelector("div.nav-label[title='Trash'] > span.doc-count"));
    String trashCountString = trashCountElement.getText();
    int start = trashCountString.indexOf("(");
    int end = trashCountString.indexOf(")");
    String numString = trashCountString.substring(start+1, end);
    int numOld = Integer.parseInt(numString);

    //delete the first document
    selectFirstDocument();
    WebElement deleteButton = driver.findElement(By.cssSelector("a[title = 'Delete']"));
    deleteButton.click();
    WebElement deleteInfo = driver.findElement(By.cssSelector("p.modal-text"));
    Assert.assertTrue(deleteInfo.getText().contains("will move it to the Trash folder"));
    WebElement deleteOk = driver.findElement(By.cssSelector("a.btn-primary"));
    deleteOk.click();

    //verify document  is deleted by comparing the numbers of documents in trash folder
    WebElement trashCountElementNew = driver.findElement(By.cssSelector("div.nav-label[title='Trash'] > span.doc-count"));
    String trashCountStringNew = trashCountElementNew.getText();
    start = trashCountStringNew.indexOf("(");
    end = trashCountStringNew.indexOf(")");
    String numStringNew = trashCountStringNew.substring(start+1, end);
    int numNew = Integer.parseInt(numStringNew);
    Assert.assertEquals(numNew, numOld+1);
    logout();
  }

  private void selectFirstDocument(){
    //locate the first document item on All tag
    WebElement allDocuments = driver.findElement(By.cssSelector("div[title = 'All Documents']"));
    allDocuments.click();
    WebElement documentItem = driver.findElement(By.cssSelector("div#document-items > div.drag"));
    documentItem.click();
    WebElement documentImage = driver.findElement(By.id("document-preview-content-hook"));
    new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(documentImage));
  }

  public void logout(){
    WebElement userProfile = driver.findElement(By.id("account-dropdown-list"));
    userProfile.click();
    WebElement logout = driver.findElement(By.id("logout-link"));
    logout.click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    Assert.assertEquals("https://app.hubdoc.com/login", driver.getCurrentUrl());
    driver.quit();
  }
}

