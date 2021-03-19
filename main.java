import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */


//LUKES
//DB_URL = DB_URL + "JDBC" + ";user="+ "andrew" + ";password=" + "password";


//ADRIAN
//DB_URL = DB_URL + "proj1" + ";user="+ "seth" + ";password=" + "password";
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
    static final String displayFormat2="%-20s%-20s%-20s%-20s%-40s%-20s\n";
    static final String displayFormat3="%-20s%-20s%-20s%-20s%-20s\n";
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
    
    public static int menu()
    {

        Scanner in = new Scanner(System.in);
        int choice = -1;
        while(choice < 0 || choice >9)
        {
            try{
                System.out.println("How would you like to break... i mean operate on our database?");
                System.out.println("0) Close program");
                System.out.println("1) Display all writing groups");
                System.out.println("2) List all the data for a group specified by the user");
                System.out.println("3) List all publishers");
                System.out.println("4) List all the data for a publisher specified by the user");
                System.out.println("5) List all book titles");
                System.out.println("6) List all the data for a book specified by the user");
                System.out.println("7) Insert a new book");
                System.out.println("8) Insert a new publisher and update all books published by one publisher to be \n" +
                                       "published by the new publisher. ");
                System.out.println("9) Delete a book from the database");
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextInt();
                if(choice < 0 || choice >9)
                    System.out.println("Enter a valid input");
            }catch(Exception e){
                System.out.println("Please enter a integer");
                choice = -1;
            }
        
         }
        

        return choice;
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
        //Constructing the database proj1 connection string
        DB_URL = DB_URL + "JDBC" + ";user="+ "andrew" + ";password=" + "password";
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        System.out.println("Accessing "+DBNAME+" as "+USER);
        
        
        System.out.println();
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
            ResultSet rs;
            String groupName;
            String book;
            String publisher;
            String group;
            String wgn;
            int count;
            boolean show;
            String title;
            String pub;    
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int year;
            int numPages;
            PreparedStatement pst;
            boolean run = true;
            while(run){
                int counter = 1;
                int choice = menu();
                if(choice == 0 ){
                    run = false;
                }
                switch(choice)
                {
                    case 0:
                        System.out.println("Exiting System");
                        break;
                    case 1:
                        System.out.println("Displaying all writing groups...");                       
                        System.out.println("GroupName");             
                        sql = "SELECT GroupName FROM WRITINGGROUPS";
                        rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            //Retrieve by column name
                            groupName = counter+") "+ rs.getString("GroupName");
                            //Display values
                            System.out.println((groupName));
                            counter++;
                        }
                        rs.close();
                        break;
                    case 2: //DONE
                        PreparedStatement ps = conn.prepareStatement("SELECT W.GROUPNAME, W.HEADWRITER, W.YEARFORMED, W.SUBJECT, B.BOOKTITLE, P.PUBLISHERNAME\n" +
                                                                        "FROM WRITINGGROUPS AS W \n" +
                                                                        "NATURAL JOIN BOOKS AS B \n" +
                                                                        "NATURAL JOIN PUBLISHERS AS P\n" +
                                                                        "WHERE GROUPNAME = ?");

                        System.out.print("Enter writing group name: ");
                        String name = br.readLine();
                        ps.setString(1, name);
                        rs = ps.executeQuery();



                          // Nat20 Stories
                        count = 0;
                        show = true;
                        while(rs.next()){
                            if(show){
                                System.out.printf(displayFormat2,"Group Name","Head Writer","Year Formed","Subject","Book Title","Publisher Name");
                                show = false;
                            }
                            groupName =rs.getString(1);
                            String writer =rs.getString(2);
                            year = rs.getInt(3);
                            String subj = rs.getString(4);
                            String bookTitle = rs.getString(5);
                            String pname = rs.getString(6);
                             System.out.printf(displayFormat2, groupName,writer,year,subj,bookTitle,pname);
                            count++;
                        }
                        if(count == 0){
                            System.out.println("Publisher name does not exist!");
                        }
                        break;
                    case 3:
                        System.out.println("Listing publishers...");
                        sql = "SELECT publisherName FROM Publishers";   //gets the group name of all writing groups
                        rs = stmt.executeQuery(sql);

                        System.out.println("Publishers"); 
                        //Extract data from result set
                        while (rs.next()) {
                            //Retrieve by column name
                            publisher = counter+") "+rs.getString("PublisherName");
                            //Display values
                            System.out.println((publisher));
                            counter++;
                        }
                        rs.close();
                        break;
                    case 4: //DONE
                        ps = conn.prepareStatement("SELECT P.PUBLISHERNAME, P.PUBLISHERADDRESS, P.PUBLISHERPHONE, P.PUBLISHEREMAIL, W.GROUPNAME, B.BOOKTITLE \n" +
                        "FROM PUBLISHERS AS P NATURAL JOIN WRITINGGROUPS AS W\n" +
                        "NATURAL JOIN BOOKS AS B\n" +
                        "WHERE PUBLISHERNAME = ?");
                        System.out.println("Displaying all data on specified publisher...");
                        System.out.println("Please enter a publisher name");
                        publisher = br.readLine();
                        ps.setString(1,publisher);
                        rs = ps.executeQuery();

                        //Wizards Publishing, 
                        count = 0;
                        show = true;
                         //Extract data from result set
                        while (rs.next()) {
                            //Retrieve by column name
                            if(show){
                                System.out.printf(displayFormat2,"Publisher Name","Publisher Address","Phone","E-mail","Writing Group","Book Titles"); 
                                show = false;
                            }
                            String publisherName = rs.getString(1);
                            String address = rs.getString(2);
                            String phone = rs.getString(3);
                            String email = rs.getString(4);
                            String writingGroup = rs.getString(5);
                            String bookTitle = rs.getString(6);                           
                            //Display values
                            System.out.printf(displayFormat2,publisherName, address, phone, email, writingGroup, bookTitle);
                            count = 1;
                        }
                        if(count == 0 ){
                            System.out.println("Publisher does not exist!");
                        }
                        rs.close();
                        break;
                    case 5:
                        System.out.println("Displaying all book titles...");
                        sql = "SELECT BookTitle FROM Books";   //gets the group name of all writing groups
                        rs = stmt.executeQuery(sql);

                        System.out.println("Book Titles");               //Extract data from result set
                        while (rs.next()) {
                            //Retrieve by column name
                            String bookTitle = counter+") "+ rs.getString("bookTitle");
                            //Display values
                            System.out.println((bookTitle));
                            counter++;
                        }
                        rs.close();
                        break;
                    case 6:
                        // The Ampersands TOR  
                        ps = conn.prepareStatement("SELECT B.BOOKTITLE, B.YEARPUBLISHED, B.NUMBERPAGES, W.GROUPNAME, P.PUBLISHERNAME \n" +
                            "FROM BOOKS AS B NATURAL JOIN PUBLISHERS AS P NATURAL JOIN WRITINGGROUPS AS W\n" +
                            "    WHERE B.BOOKTITLE = ? AND W.GROUPNAME = ?"); 
                        System.out.print("Please enter a book title:   ");
                        String bt = br.readLine();
                        ps.setString(1, bt);
                        System.out.print("Please enter a group name:   ");
                        String gn = br.readLine();
                        ps.setString(2, gn); 
                        rs = ps.executeQuery();

                        count = 0;
                        show = true;
                            //Extract data from result set
                        while (rs.next()) {
                              if(show == true){
                                  System.out.printf(displayFormat3,"Book Title","Year Published","# Pages","Group Name","Publisher");
                                  show = false;
                              }
                            //Retrieve by column name
                            String bookt = rs.getString(1);
                            year = rs.getInt(2);
                            int pages = rs.getInt(3);
                            String group_name = rs.getString(4);
                            String pub_name = rs.getString(5);
                            //Display values
                            System.out.printf(displayFormat3, bookt, year, pages, group_name, pub_name);
                            count = 1;
                        }
                        if(count == 0){
                              System.out.println("Your input does not exist");
                        }
                        rs.close();
                        break;
                    case 7:
                        //INSERT INTO BOOKS(GROUPNAME, BOOKTITLE, PUBLISHERNAME, YEARPUBLISHED, NUMBERPAGES)
                        //VALUES ('Bio Hazards','My Time in the SQL Gulag','TOR',2020,200);
                        System.out.println("Inserting book...");
                        ps = conn.prepareStatement("INSERT INTO BOOKS(PublisherName,GroupName, BookTitle, YearPublished, NumberPages)"+
                                "VALUES(?,?,?,?,?)");
                        try{

                        System.out.println("Enter the publisher's name: ");
                        String publisherName = br.readLine();
                        ps.setString(1,publisherName);

                        System.out.println("Enter the Writing group name: ");
                        groupName = br.readLine();
                        ps.setString(2,groupName);
                        System.out.println("Enter the NEW book title: ");
                        title = br.readLine();
                        ps.setString(3,title);
                        System.out.println("Enter year published (4 digits): ");
                        year = Integer.parseInt(br.readLine());
                        ps.setInt(4,year);                        
                        System.out.println("Enter the number of pages: ");
                        numPages = Integer.parseInt(br.readLine());
                        ps.setInt(5,numPages);
                                 
                        int i = ps.executeUpdate();    
                        
                        }catch(NumberFormatException d){
                            System.out.println("Please Enter the appropriate format");  
                        }
                        catch(Exception e){
                            System.out.println("There was a error");
                            System.out.println(e);
                        }
                        break;
                    case 8:
                        //STEP 1 Create a new publisher
                        //      Enter all relevant info
                        //Step 2 ask for an old publisher
                        //      validate it exists
                        //Step 3 GET EACH AND EVERY BOOK FROM OlD PUBLISHER TO NEW ONE
                    
                        //Array list to hold each row of books
                        ArrayList<String> publisherInfo = new ArrayList<String>();
                        
                        //Query all books published by old publisher
                        ps = conn.prepareStatement("SELECT BookTitle, PublisherName, YearPublished, NumberPages, GroupName "+
                                             "FROM BOOKS WHERE PublisherName = ?");
                        System.out.println("Please enter a old  publisher name");
                        String pubName = br.readLine();
                        ps.setString(1,pubName);
                        rs = ps.executeQuery();
                        
                        count = 0;
                         //Extract data from result set
                        while (rs.next()) {
                            //Retrieve by column name
                            String bookTitle = rs.getString(1);
                            publisherInfo.add(bookTitle);
                            pubName = rs.getString(2);
                            publisherInfo.add(pubName);
                            year = rs.getInt(3);
                            String y = String.valueOf(year);
                            publisherInfo.add(y);
                            numPages = rs.getInt(4);
                            String p = String.valueOf(numPages);
                            publisherInfo.add(p);
                            groupName = rs.getString(5);
                            publisherInfo.add(groupName);                   
                            count = 1;
                        }
                        if(count == 0){  // this means that the old publisher name doesn not exist
                              System.out.println("Please enter a existing publisher");
                              break;
                        }

                       // for(int x = 0; x < publisherInfo.size(); x++){
                         //   System.out.println(publisherInfo.get(x));
                       // }
                        
                        try{
                         
                            ps = conn.prepareStatement("INSERT INTO PUBLISHERS(PublisherName)"
                                    +"VALUES (?)");
                            System.out.println("Enter the new publisher’s name: ");
                            String publisherNameNew = br.readLine();
                            ps.setString(1, publisherNameNew);


                            int m = ps.executeUpdate();

                            ps = conn.prepareStatement("DELETE FROM BOOKS "+
                                                        "WHERE BOOKTITLE = ? AND GROUPNAME = ?");

                            int k = 0;
                                while(k < publisherInfo.size()){
                                        String bookTitle = publisherInfo.get(k);
                                        ps.setString(1,bookTitle);
                                        k++;
                                        //pubName = publisherInfo.get(k);              
                                        //ps.setString(2,publisherNameNew);
                                        k++;
                                        //year = Integer.parseInt(publisherInfo.get(k)) ;
                                        //ps.setInt(3,year);       
                                        k++;              
                                        //numPages = Integer.parseInt(publisherInfo.get(k));
                                        //ps.setInt(4,numPages);
                                        k++;
                                        groupName = publisherInfo.get(k);
                                        ps.setString(2,groupName);
                                        k++;         
                                        int i = ps.executeUpdate();    
                                    }

                            ps = conn.prepareStatement("INSERT INTO BOOKS(BookTitle, PublisherName, YearPublished, NumberPages, GroupName)"+
                                                        "VALUES(?,?,?,?,?)");

                            k = 0;
                                while(k < publisherInfo.size()){
                                        String bookTitle = publisherInfo.get(k);
                                        ps.setString(1,bookTitle);
                                        k++;
                                        //pubName = publisherInfo.get(k);              
                                        ps.setString(2,publisherNameNew);
                                        k++;
                                        year = Integer.parseInt(publisherInfo.get(k)) ;
                                        ps.setInt(3,year);       
                                        k++;              
                                        numPages = Integer.parseInt(publisherInfo.get(k));
                                        ps.setInt(4,numPages);
                                        k++;
                                        groupName = publisherInfo.get(k);
                                        ps.setString(5,groupName);
                                        k++;         
                                        int i = ps.executeUpdate();    
                                    }

                        }catch(NumberFormatException d){
                            System.out.println("Please Enter the appropriate format");  
                        }
                        catch(Exception e){
                            System.out.println("There was a error");
                            System.out.println(e);
                        }
                    break;
     

                    case 9:
                        try{
                        System.out.println("Deleting Book...");
                        ps = conn.prepareStatement("DELETE FROM BOOKS \n" +
                                        "    WHERE BOOKTITLE = ? AND GROUPNAME = ?");
                        //VALUES ('Bio Hazards','My Time in the SQL Gulag','TOR',2020,200);
                        System.out.println("Enter the book title: ");
                        String bookTitle = br.readLine();
                        ps.setString(1,bookTitle);
                        
                        System.out.println("Enter the Writing group name: ");
                        groupName = br.readLine();
                        ps.setString(2, groupName);                                                

                        
                            int deleted = ps.executeUpdate();
                            if(deleted == 0 ){
                                System.out.println("Nothing was deleted");
                            }
                        }catch(Exception e){
                            System.out.println("Encountered Error when Inserting book");
                            System.out.println(e);
                        }
                      
                        break;
                    }
                System.out.println();
            }
            //STEP 6: Clean-up environment

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