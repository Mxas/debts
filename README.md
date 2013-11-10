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

> mvn verify -Pintegration-test

> mvn verify -Pweb-integration-test 

> mvn jetty:run


#### 3. On-line 

http://loan-a.mxas.cloudbees.net/

 build management
https://grandcentral.cloudbees.com/