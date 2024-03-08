import java.sql.*;
class Database {

    private Connection conn = null;

    private Statement stmt = null;

    private ResultSet rs = null;

    private String tablename = "userList";

    private String username = "root";

    private String password  = "root";

    private String url = "jdbc:mysql://localhost:3306/Project";

    public static void main(String[] args) {

        //starting the server
        Database database = new Database();
        database.startSession(database.username, database.password, database.url);

        
        //testing
        User user = new User();
        UserOperations userOperations = new UserOperations();

        user = userOperations.getUserInput();
        database.addUser(user);
        
        // user = database.searchUsingEmail("current@gmail.com");
        // userOperations.printUserDetails(user);
    }
    
    boolean addUser(User user) {
        try {
            stmt.execute(generateCommand_to_addUser(user));
            return true;
        } catch (Exception e) {
            handleExceptions(e);
            return false;
        }
    }

    //Generates the command for SQL to add a user to the table
    String generateCommand_to_addUser(User user) {
        StringBuilder format = new StringBuilder();
        format.append("insert into ");
        format.append(tablename);
        format.append(" values ( ");
    
        format.append(user.phoneNo);
        format.append(" ,\"");
        format.append(user.email);
        format.append("\" , \"");
        format.append(user.name);
        format.append("\" , ");
        format.append(user.age);
        format.append(" , \"");
        format.append(user.gender);
        format.append("\" , \"");
        format.append(user.address);
        format.append("\" ); ");
        
        return format.toString();
    }


    /**
     * Performs search for the given details of user
     * Allows serching using either PhoneNumber or Email
     */
    User searchUsingPhone(long phone) {
        //Converts the phone number into a User class object and calls the search method for further operation
        User user = new User();
        user.phoneNo = phone;
        return search(user);
    }
    User searchUsingEmail(String email) {
        //Converts the Email into a User class object and calls the search method for further operation
        User user = new User();
        user.phoneNo = 0;
        user.email = email;
        return search(user);
    }
    User search(User user) {
        /**
         * Takes User object and searches into the Database
         * only uses phone number and email to search
         */

        String searchType = "";
        String searchValue = "";

        //decides whether phone or email to be used for searching
        if(user.phoneNo != 0) {
            searchType = "PhoneNumber";
            searchValue = Long.toString(user.phoneNo);
        }
        else {
            searchType = "Email";
            searchValue = "\"" + user.email + "\"";
        }

        //creates statement and executes
        String statement = createSearchFormat(searchType,searchValue);
        try {
            stmt.execute(statement);
            rs = stmt.getResultSet();
        } catch(Exception e ) { handleExceptions(e); }  

        //get the user details from resultset and pass it back
        user = resultSet_to_User();
        return user;
    }

    //Generates a string for executing the search query
    String createSearchFormat(String searchType, String searchValue) {
        StringBuilder statement = new StringBuilder();

        statement.append("select * from ");
        statement.append(tablename);
        statement.append(" where ");
        statement.append(searchType);
        statement.append(" = ");
        statement.append(searchValue);
        statement.append(";");

        return statement.toString(); 
    }


    //Starts the connection between the driver and the server
    boolean startSession(String username,String password,String url)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,username,password);
            stmt = conn.createStatement();
            return true;
        } catch (Exception e) {
            handleExceptions(e);
            return false;
        }

    }

    //Terminates the session
    boolean endSession() {
        try {
            conn.close();
            return true;
        } catch (Exception e) {
            handleExceptions(e);
            return false;
        }
    }
    
    //Converts ResultSet into User Objects
    User resultSet_to_User() {
        if(rs == null)
        return null;
        User user = new User();
        
        try {
            rs.next();
            user.phoneNo = rs.getLong("PhoneNumber");
            user.age = (byte)rs.getInt("age");
            user.name = rs.getString("Name");
            user.address = rs.getString("Address");
            user.gender = rs.getString("gender").charAt(0);
            user.email = rs.getString("Email");
            
        } catch(Exception e) { handleExceptions(e);}

        //Extract data from Resultset and feed into user object
        
        return user;
    }
    
    void handleExceptions(Exception exception) {
        System.out.println("Error" +  exception);
    }
}