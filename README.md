loans
=====

#### 1. Requirements
Goal
Create a simple micro-lending app similar to one of our existing products.

Business requirements

    User can apply for loan by selecting amount and term followed by clicking "Apply for Loan" button.
    Loan application risk analysis is performed. Risk is considered too high if:
        the attempt to take loan is made after from 00:00 to 6:00 AM with max possible amount.
        reached max applications (e.g. 3) per day from a single IP.
    Loan is issued if there are no risks associated with the application. If so, client is navigated to "My Loans" page. However, if risk is surrounding the application, client sees "rejection" message.
    On "My Loans" page a client should be able to extend a loan by clicking "Extend" button. Loan term gets extended for one week, interest gets increased by a factor of 1.5.
    The whole history of loans is visible for clients, including loan extensions.

Technical requirements

    Backend in Java 6+, XML-less Spring, Hibernate.
    Ajax-style web, no server-side templating, only JavaScript (any framework).
    Code is production-ready, covered with unit tests.
    Acceptance tests for the happy path scenario included.
    Ability to run application from the command line.

What gets evaluated
- Requirements
- Code quality (both production and test)
- UX
- How simple it is to run the application (embedded DB/embedded container)


#### 2. Build and Run

> git clone https://github.com/Mxas/debts.git

> cd debts

> mvn clean install

> mvn integration-test -Pintegration-test

> mvn integration-test -Pweb-integration-test 

> mvn jetty:run

Check in browser 
http://localhost:8080/

#### 3. On-line 

http://loan-a.mxas.cloudbees.net/

 build management and CI
https://grandcentral.cloudbees.com/

#### 4. Loan's

- Max possible amount: 5000 ;
- Min amount: 200 ;
- Max term: 100 ;
- Min term: 8 ;
- Client is mandatory.

#### 5. Testing

- Unit Tests. Runs almost after each maven goal. It's includes fast tests, which do not need application context and do not need DB.
- Integration Tests. Runs explicitly in maven command line including profile name in integration-test phase. For these tests it is necessary to ran with context and have DB (test cases runs in "Rollback" mode, it flush DB transaction, but do not commit's data to DB).
- Web Integration Tests. Runs also explicitly in maven command line including profile name in integration-test phase. These test cases runs when application is deployed on server. In test environment must be installed Mozilla Firefox  browser.

#### 5. Architecture

1. DB + DB model (Entities);
2. DAO
3. Logic services (model)
4. Controllers
5. View (html+JS)

#### 5. Technologies

- Spring 3
- Hibernate
- AngularJS
- Selenium
- Maven, JUnit, H2, tomcat/jetty, cpsuite and many others...
