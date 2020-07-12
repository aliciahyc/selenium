package hubdoc;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class hubdocStepDefinition {
  hubdocSenario scenario = new hubdocSenario();

  @When( "^I login with email \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void uploadDoc(String email, String password) {
    scenario.login(email, password);
  }

  @When( "^I upload document \"([^\"]*)\"$")
  public void uploadDocument(String docName) {
      scenario.uploadDocument(docName);
  }

  @And( "^I mark document as paid$")
  public void markAsPaid() {
    scenario.markAsPaid();
  }

  @Then( "^I add a note \"([^\"]*)\"$")
  public void addNotes(String notes) {
    scenario.addNotes(notes);
  }

  @Then( "^I view document$")
  public void viewDocument() {
    scenario.viewLargerDocument();
  }

  @Then( "^I download document$")
  public void downloadDocument() {
    scenario.downloadDocument();
  }

  @Then( "^I delete document$")
  public void deleteDocument() {
    scenario.deleteDocument();
  }

  @Then( "^I logout$")
  public void userLogout() {
    scenario.logout();
  }
}
