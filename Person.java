package backedClasses;

import org.json.JSONException;
import org.json.JSONObject;


public class Person {
	private String fName,lName,id;
	
	public Person(String fName, String lName, String id)
	{
		this.id=id;
		this.fName=fName;
		this.lName=lName;
	}
	
	public JSONObject getJSON() 
	{
		JSONObject obj = new JSONObject();
		try {
			obj.put("FirstName:", this.fName);
			obj.put("LastName:", this.lName);
			obj.put("ID:", this.id);
			return obj;
		} catch (JSONException e) {
			return obj;
			
		}
		
	}

}
