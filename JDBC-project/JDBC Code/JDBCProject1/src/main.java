import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */



public class main {
    
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are
    //strings, but that won't always be the case.
    static final String displayFormat="%-20s%-20s%-20s%-20s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    public static void menu()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("How would you like to break... i mean operate on our database?");
        System.out.println("1) Display all writing groups");
        System.out.println("2) List all the data for a group specified by the user");
        System.out.println("3) List all publishers");
        System.out.println("4) List all the data for a publisher specified by the user");
        System.out.println("5) List all book titles");
        System.out.println("6) List all the data for a book specified by the user");
        System.out.println("7) Insert a new book");
        System.out.println("8) Insert a new publisher and update all books published by one publisher to be \n" +
                               "published by the new publisher. ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        
        switch(choice)
        {
            case 1:
                listWritingGroups();
                break;
            case 2:
                listGroupData();
                break;
            case 3:
                listPublishers();
                break;
            case 4:
                System.out.println("You chose 4");
                break;
            case 5:
                System.out.println("You chose 5");
                break;
            case 6:
                System.out.println("You chose 6");
                break;
        }
    }
    public static void listWritingGroups(){
        System.out.println("Displaying all writing groups...");
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver"); //Register JDBC driver
            conn = DriverManager.getConnection(DB_URL);          //Open a connection
            stmt = conn.createStatement(); // create statement   //Execute a query
            String sql;
            sql = "SELECT GroupName FROM WRITINGGROUPS";   //gets the group name of all writing groups
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("GroupName");               //Extract data from result set
            int counter = 1;
            while (rs.next()) {
                //Retrieve by column name
                String groupName = counter+") "+ rs.getString("GroupName");
                //Display values
                System.out.println((groupName));
                counter++;
            }

            //Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }
    public static void listGroupData(){
        System.out.println("Displaying all data on specified group...");
        Scanner in = new Scanner(System.in);
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        
        System.out.println("Please enter a group name");
        String group = in.nextLine();
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver"); //Register JDBC driver
            conn = DriverManager.getConnection(DB_URL);          //Open a connection
            stmt = conn.createStatement(); // create statement   //Execute a query
            String sql;
            
            //CHANGE NAT TO group
            sql = "SELECT * FROM WRITINGGROUPS WHERE GROUPNAME LIKE '%"+ group +"%'";   //gets the group name of all writing groups
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");               //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                String groupName = rs.getString("GroupName");
                String headWriter = rs.getString("HeadWriter");
                int year = rs.getInt("yearformed");
                String subject = rs.getString("Subject");
                //Display values
                System.out.printf(displayFormat,
                        dispNull(groupName), dispNull(headWriter), year, dispNull(subject));
            }

            //Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        
    }
    public static void listPublishers(){
        System.out.println("Listing publishers...");
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver"); //Register JDBC driver
            conn = DriverManager.getConnection(DB_URL);          //Open a connection
            stmt = conn.createStatement(); // create statement   //Execute a query
            String sql;
            
            //CHANGE NAT TO group
            sql = "SELECT publisherName FROM Publishers";   //gets the group name of all writing groups
            ResultSet rs = stmt.executeQuery(sql);
           
            System.out.println("Publishers");               //Extract data from result set
            int counter = 1;
            while (rs.next()) {
                //Retrieve by column name
                String publisher = counter+") "+rs.getString("PublisherName");
                //Display values
                System.out.println((publisher));
                counter++;
            }

            //Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }
    public static void main(String[] args) {
        

        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        //System.out.print("Name of the database (not the user account): ");
        //DBNAME = in.nextLine();
        //System.out.print("Database user name: ");
        //USER = in.nextLine();
        //System.out.print("Database password: ");
        //PASS = in.nextLine();
        //Constructing the database URL connection string
        DB_URL = DB_URL + "JDBC" + ";user="+ "andrew" + ";password=" + "password";
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        System.out.println("Accessing "+DBNAME+" as "+USER);
        menu();
        System.out.println();
        System.out.println("IGNORE EVERYTHING BELOW THIS");
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT BookTitle, PublisherName, YearPublished, NumberPages FROM Books";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            System.out.printf(displayFormat, "Title", "Publisher", "Year Published", "#Pages");
            while (rs.next()) {
                //Retrieve by column name
                String title = rs.getString("BookTitle");
                String pub = rs.getString("PublisherName");
                String year = rs.getString("YearPublished");
                String pages = rs.getString("NumberPages");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(title), dispNull(pub), dispNull(year), dispNull(pages));
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        
        
        
        System.out.println("Goodbye!");
    }//end main
}//end FirstExample}