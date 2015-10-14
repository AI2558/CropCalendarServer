package com.api;
 
/**
 * @author Supayut
 */
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
 
@Path("/ctofservice")
public class CtoFService {
	@GET
	@Produces("application/json")
	public Response convertCtoF() {
		JSONObject jsonObject = new JSONObject();
		Double fahrenheit;
		Double celsius = 36.8;
		fahrenheit = ((celsius * 9) / 5) + 32;
		jsonObject.put("C Value", celsius);
		jsonObject.put("F Value", fahrenheit); 
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}
 
	@Path("{c}")
	@GET
	@Produces("application/json")
	public Response convertCtoFfromInput(@PathParam("c") Double c) {
		JSONObject jsonObject = new JSONObject();
		Double fahrenheit;
		Double celsius = c;
		fahrenheit = ((celsius * 9) / 5) + 32;
		jsonObject.put("C Value", celsius);
		jsonObject.put("F Value", fahrenheit); 
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}
}