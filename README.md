# holotopia-backend
Backend of the end assignment for the minor Advanced Java

## Setup Instructions
The following steps need to be completed after cloning the repo.

### Setting up the database
1. Create a local mySQL database for the project
2. Create a Java class called ``DatabaseConstants`` in ``nhl.stenden.util``.
3. Paste the following code in the ``nhl.stenden.util.constants.DatabaseConstants``:
```java
package nhl.stenden.util;
  
  public class DatabaseConstants extends UtilClass {
  
      public static final String USERNAME = "root";
      public static final String PASSWORD = "";
      public static final String URL = "jdbc:mysql://localhost:3306/holotopia";
  }
```
4. Replace the values of the database constants with the info of your local database in ```nhl.stenden.util.constants.DatabaseConstants```
