# Learn microservice
- SpringBoot
- TDD
- Microservice
- Agile method
-----------------
## User Story 01
As a user of the application, I want to be presented with a random
multiplication that I can solve online—not too easy, so I can use mental
calculation and make my brain work every day.

TODO:
1. Create a basic service with the business logic.
2. Create a basic API endpoint to access this service
(REST API).
3. Create a basic web page to ask the users to solve that
calculation

------------------
## User story 02
As a user of the application, i want it to show me my last attempts, so i can
see how good or bad i’m doing over time.

TODO:
1. Store all instances of the MultiplicationResultAttempt
class. That way, we can extract them later.
2. Expose a new REST endpoint to get the latest attempts
for a given user.
3. Create a new service (business logic) to retrieve those
attempts.
4. Show that attempts’ history to the users on the web
page after they send a new one.