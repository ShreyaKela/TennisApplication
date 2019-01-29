package TennisClasses;

import dbConn.ConnectDBTennis;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



@Path("/hi")
public class AuthenticateUser
{
	
	private ConnectDBTennis dbCon;
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{name}")
	public Response hello(@PathParam("name")String name)
	{
		String resp="hey"+name;
		return Response.status(200).entity(resp).build();
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public Response LoginUser(String params)
	{
		System.out.print(params);
		String response="Empty";
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(params);//JSONWriter.class.
			ConnectDBTennis dbCon = new ConnectDBTennis();
			response=dbCon.getPersonDetails(String.valueOf(obj.get("Username")),String.valueOf(obj.get("Password")));
			}catch(Exception e) {response =String.valueOf(e);}
	return Response.status(200).entity(response).build();
	
	}
	
	@POST
	@Path("/getStudents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetStudents()
	{
		String response="Empty";
		try {
			//JSONParser parser = new JSONParser();
			//JSONObject obj = (JSONObject) parser.parse(params);//JSONWriter.class.
			dbCon= new ConnectDBTennis();
			response=dbCon.getStudentDetails();
			}catch(Exception e)
			{
				response =String.valueOf(e);}
				return Response.status(200).entity(response).build();
	
		}

	
	@POST
	@Path("/getBatches")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetBatcheList(String params)
	{
		System.out.print(params);
		String response="Empty";
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(params);//JSONWriter.class.
			dbCon= new ConnectDBTennis();
			response=dbCon.GetBatchList(String.valueOf(obj.get("Username")),String.valueOf(obj.get("Password")),String.valueOf(obj.get("ID")));
			}catch(Exception e)
			{
				response =String.valueOf(e);}
				return Response.status(200).entity(response).build();
	
		}
	
	@POST
	@Path("/getBatcheStudents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetBatchStudents(String params)
	{
		System.out.print(params);
		String response="Empty";
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(params);//JSONWriter.class.
			dbCon= new ConnectDBTennis();
			response=dbCon.GetBatchStudents(String.valueOf(obj.get("Username")),String.valueOf(obj.get("Password")),Integer.parseInt((String)obj.get("BatchID")));
			}catch(Exception e)
			{
				response =String.valueOf(e);}
				return Response.status(200).entity(response).build();
	
		}
	

	@POST
	@Path("/getCoachlistforAttendance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetCoachlistforAttendance(String params)
	{
		System.out.print(params);
		String response="Empty";
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(params);//JSONWriter.class.
			dbCon= new ConnectDBTennis();
			response=dbCon.GetBatchStudents(String.valueOf(obj.get("Username")),String.valueOf(obj.get("Password")),Integer.parseInt((String)obj.get("BatchID")));
			}catch(Exception e)
			{
				response =String.valueOf(e);}
				return Response.status(200).entity(response).build();
	
		}
	

	
	
	

}
