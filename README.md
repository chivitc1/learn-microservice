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

### Refactoring
TODO:
1. MultiplicationResultAttempt should include boolean indicate if it's correct or not. We'll store it and later able to query the DB
2. The service (MultiplicationServiceImpl) should
   not only return the result calculated “on the fly” but
   save it in the attempt too.
3. The client shouldn’t be able to flag an attempt as
   correct, so this field should not be read from the
   REST API but instead calculated internally.
4. The tests need to be changed to reflect our new
   circumstances
   
 ## User story 03
 As a user of the application, i want to be more motivated to participate every
 day, so i don’t give up easily.
 
 What we’ll do is assign points to every correct answer that users submit. To
 keep it simple, we’ll only give points if they send a correct answer. Instead
 of giving one point, which doesn’t feel as good, we’ll make it 10 points per
 correct answer.
 
 A leaderboard with the top scores will be shown on the page, so players
 can find themselves in the ranking and compete with others.
 We’ll create also some basic badges: Bronze (10 correct attempts),
 Silver (25 correct attempts), and Gold (50 correct attempts). The badges
 should not be extraordinarily difficult to obtain, because that wouldn’t
 motivate our users. Because the first correct attempt can be hard to
 achieve, we’ll also introduce the badge called First Correct!, to give quick
 positive feedback.
 
 We could introduce more badges in the future, some other game
 mechanics, etc. But with these basics, we already have something that may
 motivate our users to come back and keep playing, competing with their
 peers.
 
 ### Start think about our app as microservices
 We decided to move to a microservices architecture: we’ll create
 a different part of our system that’s independently deployable
 and decoupled from the previous business logic (the gamification
 microservice). 
 
 We’ll need to connect the existing Spring Boot application
 (that now we can call the multiplication microservice) with the new one
 and make sure that they can scale up independently.
 
 Having multiplication and gamification separated
 in different microservices will force you to think of a loosely-coupled
 solution: you can replicate part of the data, or have one service calling the
 other one whenever it’s needed.
 
 Having independently deployable services for the multiplication and
 gamification domains will allow us to test them separately, using their
 APIs. In the future, we could have the gamification team evolving
 their services without interfering in the development cycle of the
 multiplication team. If they need new interfaces for communication,
 they can just create fake calls or messages—therefore defining their
 API changes—and move forward. 
 
 #### Other possible services
 - Send an email to Administrator when a user exhibits a suspicious behavior (too many consecutive correct attempt)
 - Gather analytics and build statistics like correct attempts per user, per time of the day,...
 - Add social network plugin to post new correctly-solved attempt.
 
 #### Event-driven in our app
 ##### Multiplication Microservice
 - Publisher
 - Check attempt
 - Store data
 - Send event
 - Generate result 
 
 ##### Gamification Microservice
 - Subcriber
 - Check result
 - Assign points
 - Assign badges
 - Update leaderboard
 
 ##### Event
 - MultiplicationSolvedEvent
 
 ### Identify domain model of story 03
 - ScoreCard: Models one incremental set of points that a given user gets at a given time
 - Badge: An enumeration of all possible badges in the game.
 - BadgeCard: Represents a badge linked to a certain user, won at a certain time.
 - LeaderBoardRow: A position in the leaderboard that is the total score together with the user.
 - GameStats: Score and badges for a given user. It can be used for a given game iteration (one attempt’s result) or for a collection of attempts (aggregating score and badges).
 