package com.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/bus")
public class Bus {
	 
	  @GET
	  @Produces("application/json")
	  public Response getdata() throws JSONException {
		
		JSONObject jsonObject = new JSONObject();
		BufferedReader br = null;
		String temp = null ;
		String dp = null;
		String rh = null;
		String bus = null;
		
		try {
			br = new BufferedReader(new FileReader("/home/nookskill/workspace/CropCalendarServer/data.txt"));
			
			String cLine;
			ArrayList<String> info= new ArrayList<String>();
			
			int numLine = 0;
			boolean chk = false;
			while ((cLine = br.readLine()) != null) {
				if(chk){
					numLine++;
					info.add(cLine);
				}
				else{
					chk = true;
				}
			}
			Random rand = new Random();
			int  n = rand.nextInt(numLine);
			String[] strArr = info.get(n).split(",");
			temp = strArr[0];
			dp = strArr[1];
			rh = strArr[2];
			bus = strArr[3];
	
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("kak");
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		
		jsonObject.put("temp", temp); 
		jsonObject.put("dp", dp);
		jsonObject.put("rh", rh);
		jsonObject.put("bus", bus);
		
		
		
		return Response.status(200).entity(jsonObject.toString()).build();
	  }

}
