====================================================================================================
Walmart Ticket Service Homework
Shova Maya Gurung (sgurung)
04/01/2016
====================================================================================================

This application implements a simple ticket service that facilitates the discovery, temporary hold, 
and final reservation of seats within a high-demand performance venue. The venue consists of 4 
different levels with different capacities for each level and prices for each seat in the level. 
Each VenueLevel consists of an id, name, number of rows, number of seats in a row, price and seats.
Each Seat consists of an id, code (e.g. 1A01 representing a combination of venue level id, row code 
and seat id in 2-digit format), row code (e.g. A, B , C and so on - each row in a venue level is 
assigned an alphabet code), status (e.g. V=VACANT, H=HOLD or R=RESERVED) and venue level id. Each 
Customer is has an id and email. Each SeatHold consists of an id, customer, venue level id, expired
(e.g. true or false) and list of seat ids. Each SeatReservation consists of an id, customer, total 
price, reservation timestamp, confirmation code and list of seat ids.

The application provides the following functionalities:
a. Find the number of seats available within a specific venue by seating level
   If the venue level provided is invalid, the system will return the number of seats available in all
   venue levels. Available seats are the ones with status=V (VACANT) not H(HOLD) or R (RESERVED).
b. Find and hold best available seats on behalf of a customer given minimum and maximum requested levels
   This is implemented using the First Fit Algorithm where sequentially go through the seats and find the 
   first available seats in a row that can accommodate the requested number of seats.  The following
   assumptions have been made:
     i.   The seats are assigned sequentially starting from Row A and so on. The best seats are the ones 
          which are closer to the stage, meaning seats in Row A are the best ones, followed by seats in 
          Row 2 and so on.
     ii.  If row does not have enough available seats to accodommate the request, the seats in the next 
          row are held. 
     iii. The maximum number of seats a customer can hold for a single request is equal to the number of 
          seats in a row for a specific venue level. 
   The details about the seat hold are stored in a SeatHold object and returned. SeatHold objects have 
   expiration date assigned to it. The seat hold expiration time is configurable (config.properties file, 
   currently set to 120 seconds). The expired seat holds are deleted.
c. Reserve and commit a specific group of held seats for a customer
   The seats requested to be hold are reserved for the customer and a confirmation code is returned. 
d. Display seats in a specific venue level
e. Display all existing venue levels
f. Display all seat holds
g. Display all seat reservations


---------------------------------------------------------------------------------------------------------
Build Project
---------------------------------------------------------------------------------------------------------

1. To try out the build, issue the following command from within the project directory:
   
   mvn compile
   
2. To test the application, issue the following command from within the project directory:

   mvn test
   
3. To package the code in a jar file, issue the following command from within the project directory:

   mvn package
   
   This will creat the ticketservice-1.0.jar file.
   
4. To install the project's jar file to the local repository, issue the following command from within 
   the project directory:
 
   mvn clean install
   

---------------------------------------------------------------------------------------------------------
Execute the Application
---------------------------------------------------------------------------------------------------------

1. To execute the application, issue the following command from within the project directory:

   java -cp target/ticketservice-1.0.jar com.walmart.ticketservice.WalmartTicketServiceApplication

   A list of menu options will be displayed on the screen. Users can choose an option and execute certain 
   functionality.
   



