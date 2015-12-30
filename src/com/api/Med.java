package com.api;

import java.io.File;
import java.io.FileReader;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/med")
public class Med {

	String filename = "/home/nookskill/weedprotection.owl";
	
	@Path("{m}")
	@GET
	@Produces("application/json")
	public Response getMedicine( @PathParam("m") int m ) throws JSONException {
		
		JSONObject jsonObject = new JSONObject();
		
		//OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		Model model = ModelFactory.createDefaultModel();
		String med = "";
		String weed = getWeed(m);
		try{
			File file = new File(filename);
			FileReader reader = new FileReader(file);
			model.read(reader,null);
			String sparqlQuery = 
						"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
						"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
						"PREFIX weed: <http://www.semanticweb.org/beer/ontologies/2558/10/weed#>\n" +
						"\n" +
						"SELECT ?x \n" +
								"where { ?x rdf:type weed:ยา.?x weed:hasProtected weed:"+weed+" }";
			Query query = QueryFactory.create(sparqlQuery);
			QueryExecution qe = QueryExecutionFactory.create(query,model);
			ResultSet results = qe.execSelect();
			
			while(results.hasNext()){
				QuerySolution qs  = results.nextSolution();
					RDFNode node = qs.get("x");
					String text = "";
					text = node.asNode().getLocalName();
					med = med+getMedName(text);
					if(results.hasNext())
						med = med+",";
					
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		jsonObject.put("medicine", med);
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}

	private String getMedName(String text) {
		Model model = ModelFactory.createDefaultModel();
		String name = "";
		try{
			File file = new File(filename);
			FileReader reader = new FileReader(file);
			model.read(reader,null);
			String sparqlQuery = 
							"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
							"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
							"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
							"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
							"PREFIX weed: <http://www.semanticweb.org/beer/ontologies/2558/10/weed#>\n" +
							"\n" +
							"SELECT ?x \n" +
							"where {?x rdf:type weed:ชื่อการค้า.?x weed:hasName weed:"+text+"}";		
			Query query = QueryFactory.create(sparqlQuery);
			QueryExecution qe = QueryExecutionFactory.create(query,model);
			ResultSet results = qe.execSelect();
			
			while(results.hasNext()){
				QuerySolution qs  = results.nextSolution();
					RDFNode node = qs.get("x");
					String str = "";
					str = node.asNode().getLocalName();
					name = name+str;
					if(results.hasNext())
						name = name+",";
			}
		} catch(Exception e){
				e.printStackTrace();
		}
		return name;
	}

	private String getWeed(int m) {
        switch (m) {
        case 1: return "กกขนาก";
        case 2: return "กกทราย";
        case 3: return "กะเม็ง";
        case 4: return "ขาเขียด";
        case 5: return "เซ่งใบมน";
        case 6: return "ถั่วลิสงนา";
        case 7: return "เทียนนา";
        case 8: return "ปอวัชพืช";
        case 9: return "ผักโขมไร้หนาม";
        case 10: return "ผักงวงช้าง";
        case 11: return "ผักบุ้ง";
        case 12: return "ผักเบี้ยหิน";
        case 13: return "ผักปราบนา";
        case 14: return "ผักปอดนา";
        case 15: return "ผักแว่น";
        case 16: return "สะอึก";
        case 17: return "โสนคางคก";
        case 18: return "โสนหางไก่";
        case 19: return "หญ้าข้าวนก";
        case 20: return "หญ้าดอกขาว";
        case 21: return "หญ้าแดง";
        case 22: return "หญ้าตีนกา";
        case 23: return "หญ้าตีนนก";
        case 24: return "หญ้านกสีชมพู";
        case 25: return "หญ้าปากควาย";
        case 26: return "หญ้าแพรก";
        case 27: return "หญ้ารังนก";
        case 28: return "หญ้าหางหมาจิ้งจอก";
        case 29: return "หญ้าชะกาดน้ำเค็ม";
        case 30: return "หญ้าชันกาศ";
        case 31: return "หญ้ากุศลา";
        case 32: return "หนวดปลาดุก";
        case 33: return "ข้าววัชพืช";
        }
		return null;
	}


}
