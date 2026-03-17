SimplePractice UI SDET Assessment
   
   Overview:
   This repository contains an automated UI test for the SimplePractice plataform.
   The test covers a Happy Path Scenario for creating a new clint using Selenium WebDriver.

   The purpose of this proect is to demstrate:
   *UI automation skills
   *Stable locator strategies
   *Explicit waits for synchronization
   *Test structure using TestNG
   *Browser driver management using WebDriverManager

   Tech Stack
   The following tools and frameworks were used in this implementation:
   *Java
   *TestNG
   *WebDriverManager
   *Maven

   Test cenario:
   The automated test validates the following workflow:
   1.-Navigate to the SimplePractice login page.
   2.- Log in uing valid credentials
   3.-Open the create (+) menu
   4.-Select "New Client"
   5.-Enter the minimum required client information
      *First Name
      *Last Name
      *State
   6.-Submit the client creation form
   7.-Navigate to the "Clients" page
   8.-Verify that the newly created client appears in the clients list

   Project Files:
   *CreateClientTest.java
    Selenium automated test that covers the client creation Happy Path.
   *TASK_CHECKLIST.md
    Checklist of Happy Path scenarios for the Tasks feature based on exploratory Testing.
   *pom.xml
    Maven configuration file containing proect dependencies.

   Setup Instructions:
   1.- Clone the repository
     *CreateClientTest.java

   2.- Open the Project uing JAVA IDE such as:
    *Eclipse
    *IntelliJ
    *VSCode

   Ensure the project is recognized as a Maven Project

   3.- Instal dependencies
    Dependencies are managed by Maven and will be installed automatically fron the pom.xml

  Main dependencies include:
   *Selenium WebDriver
   *TestNG
   *WebDriverManager

   4.-Configure Credentials:
    Open the file:
     CreateClientTest.java
     Replace the placeholder values with valid credentials provided for the test enviroment:
       String username = "ANY_EMAIL";
       tring password = "ANY_PASSWORD";

   5.-Running the Test:
    Run the test using TESTNG.
    The test will:
     1.-Launch Chrome
     2.-Log into the application
     3.-Create a new client
     4.-Navigate to the Clients Page
     5.-Verify taht the client was successfully created

  NOTES:
   *Explicit waits are used to improve test stability.
   *Dynamc clint names are generated to avoid conflicts with existing data.
   *WebDriverManager automatically manage browser drivers.
   *The test focuses on the Happy Path Scenario as required in the assessment.
   *Future Improvements:
     1.-Implement Page Object Model(POM)
     2.-Add test data management
     3.-Improve locator strategy with dedicated classes

  ADDITIONAL NOTE: Due to current hardware limitations, I was not able to execute the automation locally.
                   However, the script has been tructured following Selenium and TestNG best Practices, and it includes the necessary elements to demostrate my approach to UI test automation.
                   
