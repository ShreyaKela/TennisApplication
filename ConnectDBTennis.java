package dbConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import com.mysql.cj.xdevapi.JsonArray;

import backedClasses.Person;
public class ConnectDBTennis {
	

		private static final String DB_URL="jdbc:mysql://localhost/Tennis";
		private static final String USER="shreya";
		private static final String Pass="Vectors@11";
		private Connection conn;
		private Statement stmt;
		private String userId;
		
		
		public ConnectDBTennis(String userId)
		{
			Connection conn=null;
			Statement stmt=null;	
			this.userId=userId;
		}
		public ConnectDBTennis() {}
		
		
		public String getStudentData() 
		{
			String response="Database Returned ";
			if(userId.equals("anuja"))
			try {
				//Register JDBC driver
				Class.forName("com.mysql.cj.jdbc.Driver");
				//open a connection
				conn = DriverManager.getConnection(DB_URL,USER,Pass);
				//Execute a query
				stmt=conn.createStatement();
				String sql="select FirstName from Person";
				ResultSet resultSet =stmt.executeQuery(sql);
				//Iterate result set
				while(resultSet.next()) 
				{
				response= response+" "+ resultSet.getString("FirstName");	
				}
			}
			catch(Exception e) 
			{
				response =String.valueOf(e);
			}
			else
				response="INTRUDER ALERT !!!";
			return response.toUpperCase();
		}
		
		public String getPersonDetails(String uname, String pwd)
		{
			String response="User not Found";
			//List<JSONObject> persons;
			//JsonArray persons = new JsonArray();
			//persons = new List<JSONObject>();
		
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL,USER,Pass);
				//Execute a query
				Statement stmt =conn.createStatement();
				//String sql="select Person.ID, FirstName, LastName from Person where ID IN (select ID from Logins where UserName='"+uname+"' and Password='"+pwd+"');";
				String sql = "CALL SP_Authenticate('"+ uname+"','"+pwd+"');";
				ResultSet resultSet =stmt.executeQuery(sql);
				//Person p;
				if(resultSet.getFetchSize()<2)
				while(resultSet.next()) 
				{
					JSONObject obj = new JSONObject();
					obj.put("ID",resultSet.getString("ID"));
					obj.put("Level",resultSet.getString("level"));
					obj.put("FirstName",resultSet.getString("level"));
					obj.put("LastName",resultSet.getString("level"));
					response=obj.toString();
					//p = new Person(resultSet.getString("FirstName"),resultSet.getString("LastName"),resultSet.getString("Person.ID"));
					//response= String.valueOf(p.getJSON());	
				}
				else
					response ="Multiple users found";		
			}
			catch(Exception e) 
			{
				response =String.valueOf(e);
		
			}
			
			return response;
			}

		public String getStudentDetails()
		{
			String response="No studens";
			ArrayList<JSONObject> persons = new ArrayList<JSONObject>(); 
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL,USER,Pass);
				//Execute a query
				Statement stmt =conn.createStatement();
				String sql="select ID, FirstName, LastName from Person where ID like ('S%');";
				ResultSet resultSet =stmt.executeQuery(sql);
				Person p;
				while(resultSet.next()) 
				{
					p = new Person(resultSet.getString("FirstName"),resultSet.getString("LastName"),resultSet.getString("ID"));
					persons.add(p.getJSON());
				}
					response =persons.toString();  //"Multiple users found";		
			}
			catch(Exception e) 
			{
				response =String.valueOf(e);
			}		
			return response;
		}


		/*public String GetBatchList(String Username, String password, String id)
		{
			String response="No batches found";
			
			
			ArrayList<JSONObject> batches = new ArrayList<JSONObject>(); 
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL,USER,Pass);
				//Execute a query
				Statement stmt =conn.createStatement();
				String sql = "CALL SP_getBatches('"+ Username+"','"+password+"','"+id+"');";
				ResultSet resultSet =stmt.executeQuery(sql);
				//Person p;
				JSONObject SingleBatch = new JSONObject();
				JSONObject BatchList=new JSONObject();
				while(resultSet.next()) 
				{
					SingleBatch=new JSONObject();
					SingleBatch.put("Location", resultSet.getString("Location"));
					SingleBatch.put("Days",resultSet.getString("Days"));
					SingleBatch.put("BatchLevel",resultSet.getString("Level"));
					String replacement ="";
					BatchList.put(resultSet.getString("BatchID"),  SingleBatch.toString());
					//p = new Person(resultSet.getString("FirstName"),resultSet.getString("LastName"),resultSet.getString("ID"));
					//persons.add(p.getJSON());
					//batches.add(obj);
				}
				JSONObject resp = new JSONObject();
				resp.put("Batches", BatchList.toString());
				//JSONString respStr =(JSONString)resp.put("Batches",BatchList.toString());
				//String finalstring = respStr.toString();
				//System.out.print(finalstring);//"Multiple users found";		
				//response= respStr.replaceAll("\\\","");
			}
			catch(Exception e) 
			{
				response =String.valueOf(e);
			}

			return response;
		}*/

		
		
		
		public String GetBatchList(String Username, String password, String id)
		{
			String response="No batches found";
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL,USER,Pass);
				//Execute a query
				Statement stmt =conn.createStatement();
				String sql = "CALL SP_getBatches('"+ Username+"','"+password+"','"+id+"');";
				ResultSet resultSet =stmt.executeQuery(sql);
				//Person p;
				JSONObject SingleBatch = new JSONObject();
				JSONObject BatchList=new JSONObject();
				while(resultSet.next()) 
				{
					SingleBatch=new JSONObject();
					SingleBatch.put("Location", resultSet.getString("Location"));
					SingleBatch.put("Days",resultSet.getString("Days"));
					SingleBatch.put("BatchLevel",resultSet.getString("Level"));
					BatchList.put(resultSet.getString("BatchID"),  SingleBatch.toString());
				}
				JSONArray batches = new JSONArray(); ; 
				JSONArray jsonArray = new JSONArray(BatchList); 
				int len = jsonArray.length();
				if (jsonArray != null) { 
				   for (int i=0;i<len;i++)
					   batches.put(jsonArray.get(i));		       
				   }
				response = batches.toString();
			}
			catch(Exception e) 
			{
				response =String.valueOf(e);
			}
			return response;
		}


		public String GetBatchStudents(String Username, String password, int BatchId)
		{
			
			String response="No students found for this batch.";
			ArrayList<JSONObject> BatchStudents = new ArrayList<JSONObject>(); 
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL,USER,Pass);
				//Execute a query
				Statement stmt =conn.createStatement();
				String sql = "CALL SP_getStudentBatchwise('"+ Username+"','"+password+"',"+BatchId+");";
				ResultSet resultSet =stmt.executeQuery(sql);
				//Person p;
				JSONObject obj;
				while(resultSet.next()) 
				{
					obj=new JSONObject();
					obj.put("ID",resultSet.getString("ID"));
					obj.put("FirstName",resultSet.getString("FirstName"));
					obj.put("LastName",resultSet.getString("LastName"));
					BatchStudents.add(obj);
				}
					response =BatchStudents.toString();  //"Multiple users found";		
			}
			catch(Exception e) 
			{
				response =String.valueOf(e);
			}
			System.out.print(response);	
			return response;
		}
		
		public String GetCoachBatchListforAttendance(String Username, String password, JSONArray[][] myarray, Date todaydate )
		{
			
			String response="No students found for this batch.";
			ArrayList<JSONObject> BatchStudents = new ArrayList<JSONObject>(); 
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL,USER,Pass);
				//Execute a query
				Statement stmt =conn.createStatement();
				String sql = " ";
				ResultSet resultSet =stmt.executeQuery(sql);
				//Person p;
				JSONObject obj;
				while(resultSet.next()) 
				{
					obj=new JSONObject();
					obj.put("ID",resultSet.getString("ID"));
					obj.put("FirstName",resultSet.getString("FirstName"));
					obj.put("LastName",resultSet.getString("LastName"));
					BatchStudents.add(obj);
				}
					response =BatchStudents.toString();  //"Multiple users found";		
			}
			catch(Exception e) 
			{
				response =String.valueOf(e);
			}
			System.out.print(response);	
			return response;
		}
}