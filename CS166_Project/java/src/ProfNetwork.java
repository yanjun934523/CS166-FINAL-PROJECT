/*
 * Yihua Hao & Yanjun Guan
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 * 
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.time.*;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class ProfNetwork {

   // reference to physical database connection.
   private Connection _connection = null;

   public String current_user = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of Messenger
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public ProfNetwork (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end ProfNetwork

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQueryAndPrintResult (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and returns the results as
    * a list of records. Each record in turn is a list of attribute values
    *
    * @param query the input query string
    * @return the query result as a list of records
    * @throws java.sql.SQLException when failed to execute the query
    */
   public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and saves the data returned by the query.
      boolean outputHeader = false;
      List<List<String>> result  = new ArrayList<List<String>>();
      while (rs.next()){
          List<String> record = new ArrayList<String>();
         for (int i=1; i<=numCol; ++i)
            record.add(rs.getString (i));
         result.add(record);
      }//end while
      stmt.close ();
      return result;
   }//end executeQueryAndReturnResult

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and returns the number of results
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
       // creates a statement object
       Statement stmt = this._connection.createStatement ();

       // issues the query instruction
       ResultSet rs = stmt.executeQuery (query);

       int rowCount = 0;

       // iterates through the result set and count nuber of results.
       if(rs.next()){
          rowCount++;
       }//end while
       stmt.close ();
       return rowCount;
   }

   /**
    * Method to fetch the last value from sequence. This
    * method issues the query to the DBMS and returns the current
    * value of sequence used for autogenerated keys
    *
    * @param sequence name of the DB sequence
    * @return current value of a sequence
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int getCurrSeqVal(String sequence) throws SQLException {
	Statement stmt = this._connection.createStatement ();

	ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
	if (rs.next())
		return rs.getInt(1);
	return -1;
   }

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            ProfNetwork.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if

      Greeting();
      ProfNetwork esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the Messenger object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new ProfNetwork (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
            System.out.println("MAIN MENU");
            System.out.println("---------");
            System.out.println("1. Create user");
            System.out.println("2. Log in");
            System.out.println("9. < EXIT");
            String authorisedUser = null;
            switch (readChoice()){
               case 1: CreateUser(esql); break;
               case 2: authorisedUser = LogIn(esql); break;
               case 9: keepon = false; break;
               default : System.out.println("Unrecognized choice!"); break;
            }//end switch
            if (authorisedUser != null) {
              boolean usermenu = true;
              while(usermenu) {
                System.out.println("MAIN MENU");
                System.out.println("---------");
                System.out.println("1. Goto Friend List");
                System.out.println("2. Update Profile");
                System.out.println("3. Write a new message");
                System.out.println("4. Send Friend Request");
                System.out.println("5. Change Password");
                System.out.println("6. Search people");
                System.out.println("7. View connection request");
                System.out.println("8. View message");
                System.out.println("9. Log out");
                switch (readChoice()){
                   case 1: FriendList(esql); break;
                   case 2: UpdateProfile(esql); break;
                   case 3: NewMessage(esql); break;
                   case 4: SendRequest(esql); break;
                   case 5: ChangePassword(esql); break;
                   case 6: SearchPeople(esql); break;
                   case 7: ConnectionRequest(esql); break;
                   case 8: ViewMessage(esql); break;
                   case 9: usermenu = false; 
                   esql.current_user = null;  
                   break;
                   default : System.out.println("Unrecognized choice!"); break;
                }
              }
            }
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main

   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   /*
    * Creates a new user with privided login, passowrd and phoneNum
    * An empty block and contact list would be generated and associated with a user
    **/
   public static void CreateUser(ProfNetwork esql){
      try{
         System.out.print("\tEnter user login: ");
         String login = in.readLine();
         System.out.print("\tEnter user password: ");
         String password = in.readLine();
         System.out.print("\tEnter user email: ");
         String email = in.readLine();

	 //Creating empty contact\block lists for a user
	 String query = String.format("INSERT INTO USR (userId, password, email) VALUES ('%s','%s','%s')", login, password, email);

         esql.executeUpdate(query);
         System.out.println ("User successfully created!");
      }catch(Exception e){
         System.err.println (e.getMessage ());
      }
   }//end

   /*
    * Check log in credentials for an existing user
    * @return User login or null is the user does not exist
    **/
   public static String LogIn(ProfNetwork esql){
      try{
         System.out.print("\tEnter user login: ");
         String login = in.readLine();
         System.out.print("\tEnter user password: ");
         String password = in.readLine();

         String query = String.format("SELECT * FROM USR WHERE userId = '%s' AND password = '%s'", login, password);
         int userNum = esql.executeQuery(query);
	   if (userNum > 0){
         esql.current_user = login;
		   return login;
      }
         return null;
      }catch(Exception e){
         System.err.println (e.getMessage ());
         return null;
      }
   }//end

// Rest of the functions definition go in here
   public static void ChangePassword(ProfNetwork esql){
      try{
         System.out.print("\tEnter user password: ");
         String password = in.readLine();

         String query = String.format("SELECT * FROM USR WHERE userId = '%s' AND password = '%s'", esql.current_user, password);
         int userNum = esql.executeQuery(query);
         
         if (userNum > 0){
		   System.out.print("\tEnter new password: ");
         String new_password = in.readLine();
         System.out.print("\tReenter new password: ");
         String new_password2 = in.readLine();
         if(new_password.equals(new_password2)){
            query = String.format("UPDATE USR SET password = '%s' WHERE userId = '%s'", new_password, esql.current_user);
            esql.executeUpdate(query);
            System.out.println ("User password changed!");
         }
      }
         
      }catch(Exception e){
         System.err.println(e.getMessage ());
         //return null;
      }
      
   }//end

   public static void SearchPeople(ProfNetwork esql){
      try{
		   System.out.print("\tEnter people's name: ");
         String PeopleName = in.readLine();
         
         String query = String.format("SELECT U.name, U.email FROM USR U WHERE U.name ='%s'", PeopleName);
         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         for(int i = 0; i < result.size(); i++){
            List<String> item = result.get(i);
            System.out.println(item);
         }
      }catch(Exception e){
         System.err.println (e.getMessage ());
      }
      
   }//end

   public static void FriendList(ProfNetwork esql){
      try{
		   String query = String.format("SELECT C.connectionId FROM CONNECTION_USR C WHERE C.userId = '%s' AND C.status = 'Accept' "+
            "UNION SELECT C1.userId FROM CONNECTION_USR C1 WHERE C1.connectionId = '%s' AND C1.status = 'Accept';", esql.current_user,esql.current_user);
         int friend_num = esql.executeQueryAndPrintResult(query);
         
      }catch(Exception e){
         System.err.println (e.getMessage ());
      }
      
   }//end

   //UpdateProfile(esql)
   public static void UpdateProfile(ProfNetwork esql){
      try{
         System.out.print("\tEnter your email: ");
         String email = in.readLine();
         System.out.print("\tEnter your birthday: ");
         String birthday = in.readLine();
         String query = String.format("UPDATE USR SET email = '%s', dateOfBirth = '%s' WHERE userId = '%s'", email, birthday, esql.current_user);
         esql.executeUpdate(query);

         System.out.print("\tEnter your company: ");
         String company = in.readLine();
         System.out.print("\tEnter your role: ");
         String role = in.readLine();
         System.out.print("\tEnter your location: ");
         String location = in.readLine();
         query = String.format("UPDATE WORK_EXPR SET company = '%s', role = '%s', location = '%s' WHERE userId = '%s'", company, role, location, esql.current_user);
         esql.executeUpdate(query);

         System.out.print("\tEnter your instituition name: ");
         String instituition_name = in.readLine();
         System.out.print("\tEnter your major: ");
         String major = in.readLine();
         System.out.print("\tEnter your degree: ");
         String degree = in.readLine();
         query = String.format("UPDATE EDUCATIONAL_DETAILS SET instituitionName = '%s', major = '%s', degree = '%s' WHERE userId = '%s'", instituition_name, major, degree, esql.current_user);
         esql.executeUpdate(query);
         
      
         
      }catch(Exception e){
         System.err.println(e.getMessage ());
         //return null;
      }
      
   }//end

   //NewMessage(esql)
   public static void NewMessage(ProfNetwork esql){
      try{
         System.out.print("\tEnter message receiver ID: ");
         String receiver_id = in.readLine();
         System.out.print("\tEnter message contents: ");
         String content = in.readLine();
         System.out.print("\tSend message right now? \n1. Yes\n2:No ");
         if(readChoice()==1){
            String query = String.format("INSERT INTO MESSAGE(senderId, receiverId, contents, sendTime, deleteStatus, status) Values('%s', '%s', '%s','%s', '%t', '%x','%s')",
               esql.current_user, receiver_id, content, LocalDateTime.now(), 0, "Sent");
         esql.executeUpdate(query);
         }else{
            String query = String.format("INSERT INTO MESSAGE(senderId, receiverId, contents, status) Values('%s', '%s', '%s','%s')",
               esql.current_user, receiver_id, content, "Draft");
         esql.executeUpdate(query);
         }
      
         
      }catch(Exception e){
         System.err.println(e.getMessage ());
         //return null;
      }
      
   }//end

   //SendRequest
   public static void SendRequest(ProfNetwork esql){
      try{
         System.out.print("\tEnter the user ID you want to connect with: ");
         String receiver_id = in.readLine();
         String query = String.format("INSERT INTO CONNECTION_USR(userId, connectionId, status) Values('%s', '%s', '%s')",
               esql.current_user, receiver_id, "Request");
         esql.executeUpdate(query);
         
      
         
      }catch(Exception e){
         System.err.println(e.getMessage ());
         //return null;
      }
      
   }//end

   //ConnectionRequest
   public static void ConnectionRequest(ProfNetwork esql){
      try{
         //String query = String.format();
         //esql.executeUpdate(query);
         
      }catch(Exception e){
         System.err.println(e.getMessage ());
         //return null;
      }
      
   }//end

   //view message
   public static void ViewMessage(ProfNetwork esql){
      try{
         String query = String.format("SELECT M.contents FROM MESSAGE M WHERE M.receiverId = '%s' AND M.status = Sent", esql.current_user);
         int i = esql.executeQueryAndPrintResult(query);
         query = String.format("UPDATE MESSAGE SET status = Read WHERE receiverId = '%s'", esql.current_user);
         esql.executeUpdate(query);

         System.out.print("\tDo you want delete message?\n1.Yes\n2.No ");
         if(readChoice()==1){
            System.out.print("\tEnter the message Id you want delete: ");
            String msid = in.readLine();
            int messstatus = 0;
            query = String.format("SELECT status FROM MESSAGE WHERE msgId = '%s'", msid);
            List<List<String>> temp = esql.executeQueryAndReturnResult(query);
            if(temp.equals("0")){
               messstatus = 2;
            }else{
               messstatus = 3;
            }

            query = String.format("UPDATE MESSAGE SET deleteStatus = '%x' WHERE msgId = '%s';",messstatus,msid);
            esql.executeUpdate(query);
         }
      }catch(Exception e){
         System.err.println(e.getMessage ());
         //return null;
      }
      
   }//end

}//end ProfNetwork
