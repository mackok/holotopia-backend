# holotopia-backend
Backend of the end assignment for the minor Advanced Java

## Setup Instructions
The following steps need to be completed after cloning the repo.

### Setting up the database
1. Create a local mySQL database for the project
2. Create a Java class called ``DatabaseConstants`` in ``nhl.stenden.util.constants``.
3. Paste the following code in the ``nhl.stenden.util.constants.DatabaseConstants``:
```java
package nhl.stenden.util.constants;

import nhl.stenden.util.UtilClass;

/**
 * Class that contains constants related to the database.
 */
public class DatabaseConstants extends UtilClass {

    public DatabaseConstants(){
        super();
    }

    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static final String URL = "jdbc:mysql://localhost:3306/holotopia";
}
```
4. Replace the values of the database constants with the info of your local database in ```nhl.stenden.util.constants.DatabaseConstants```
5. Run the application so the database tables will be initialized
6. Stop the application and insert the hololive members from the directory ``holotopia-backend/sql/hololive-member.sql``
into your database
7. Run the application again and wait till all required video information has been added to the database*
8. Create and fill the ``users`` table in the database using the sql file in ``holotopia-backend/sql/users.sql``**

*The application will check whether the videos from a hololive member (youtube channel) are already in the database.
If not, it will gather all videos with their required information and insert it into the database. Since there are no
videos in the database yet, this is necessary in order to fill the database with the videos from the hololive members.

**This table is used to store user roles related to security.

##Security Instructions
After starting the application you are required to login to access the REST API. Below is a list of accounts, which were added 
in step 8 of ``setting up the database``, that can be used to access the API.

``/holotopia_backend_war/login`` is the endpoint where you can login using one of the following accounts:
- <b>Admin</b> (username: admin, password: admin)
- <b>App</b> (username: app, password: app)

The user ``Admin`` can access all endpoints while the user ``App`` can only access the endpoints that are necessary for
the frontend of the application. After logging in you receive a ``json-token`` in the header which should be used as a
bearer token in the ``x-www-form-urlencoded`` part of the body when making API calls.


