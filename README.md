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
6. Stop the application and insert the hololives members from the directory ``holotopia-backend/sql/hololive-member.sql``
into your database
7. Run the application again and wait till all required video information is added to the database*

*The application will check whether the videos from a hololive member (youtube channel) are already in the database.
If not, it will gather all videos with their required information and insert it into the database. Since there are no
videos in the database yet, this is necessary in order to fill the database with the videos from the hololive members.

