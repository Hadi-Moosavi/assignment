# Expense Tracker
This is a simple expense tracker service which users can add their accounts, expense/income categories, and financial transactions
from those accounts and in the aforementioned category and get insight into their financial behavior.
## Overview

The project consist of the following modules:

- auth-server
- expense-tracker

### Auth Server
Keycloak embedded in spring boot application used as auth server. H2 is used as the data source for it and when auth server
starting, realm, client and user's info read from JSON file and will be added to the database.
[Original article](https://www.baeldung.com/keycloak-embedded-in-spring-boot-app)
### Expense Tracker
Main business implemented in expense tracker. like auth server, the expense tracker uses H2 as the database. APIs secured
by JWT token, service uses auth server to validate the token and get user data.
## How To Use

To clone and run this application, you'll need `Git`, `JDK 17`, `Maven 3`, and `Doker` installed on your computer. From your command line:

```bash
# Clone this repository
$ git clone https://github.com/Hadi-Moosavi/assignment.git

# Go into the repository
$ cd assignment

# Build jar packages
$ mvn -Dmaven.test.skip=true clean package

# Build docker and run services
$ docker-compose up --build
```
auth server will run on port `6003` and the expense tracker on port `6002`.

## Calling APIs
as mentioned earlier auth server is an embedded keycloak server. two users had been defined:
- 1: User: test, Pass: 123
- 2: User: test2, Pass: pass

for calling service you must have a token. To get the token you must call `auth server` as follows:
```bash
curl --location --request POST 'http://localhost:6003/auth/realms/assignment/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=expense-tracker' \
--data-urlencode 'username=test2' \
--data-urlencode 'password=pass' \
--data-urlencode 'grant_type=password'
```
then use the access token in the authorization header of the request to `expense tracker service`.

To access swagger ui go to this URL in your browser: [localhost:6002/swagger-ui.html](localhost:6002/swagger-ui.html)

## How It Works
In `AccountController`, user can add, modify, or deactivate his account. get the list of accounts and also get account's
The current balance is here.

Then in `CategoryController` user can manage his expense or income category.

after defining the account and category now
user can add his/her transaction in `TransactionController`. transaction based on its category type is an income
transaction or expense transaction. Users can search his/her transactions based on date, category, account, and
type(income/expense).

In `SummeryController` user can get an aggregated view of his transaction. once again users can filter based on date, category, account and
type and group them by category, account, and type.


