# IMDb Minimalist Clone

The purpose of this project is to create a minimalist copy of IMDb using 
Object-Oriented Programming (OOP) concepts in Java. The application should include a 
functional interface that performs various tasks, such as adding or removing a 
production from the favorite list, viewing the details of a production,
creating different types of users, or creating and resolving requests. 
There are three types of users: Regular, Admin, and Contributor. 

Five design patterns were used:
- **Singleton** to create the main IMDB class.
- **Builder** to create the Information class.
- **Observer** to implement the notification system.
- **Strategy** to implement the experience system.
- **Factory** to easily create new users.

For the graphical interface, **Swing** was used. For video manipulation, 
the **JavaFX** plugin was used. When logging in, the user can choose whether 
they want to use the terminal or the interface. 
Regardless of the choice, the following flow occurs:

### Flow

1. **Login Page**: 
   - The user will input their email and password.
   - The user will be notified if either of them is incorrect.

2. **Menu Page**: 
   - The user can choose from several commands to interact with the application:
     - **View all productions**: Displays a list of all productions in the system, which can be 
sorted by genre and number of ratings.
     - **View all actors**: Displays a list of all actors in the system, which can be sorted by name.
     - **Search bar**: Users can search for a specific production or actor.
     - **Favorites list**: Users have a list of favorite films/TV shows where they can add or remove items.
     - **Regular users** can also add ratings to a production or modify their review if it already exists.
     - **Contributors and regular users** can create requests, which the admin team can resolve.
     - **Admins and contributors** can add, modify, or delete a production in the system.
     - **Admins** can also delete users or add new ones.

3. **Main Page**:
   - The main page provides access to the menu, movie/TV show recommendations, 
a dashboard with received notifications, and a settings button where users can modify their personal data.
   - Each production displayed will include a plot, directors, actors, release year, 
and other useful information. Ratings from other users will also be displayed.
   - When creating a request, the user will select what is needed and attach a short description.
   - Each user can earn experience points (XP) based on their actions in the application.

### Additional Information:
- The system will notify users about the actions they perform in the application.
- Admins and contributors will be able to interact with productions 
(add, modify, or delete them), as well as manage user data.

