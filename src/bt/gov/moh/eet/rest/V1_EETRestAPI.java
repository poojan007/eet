package bt.gov.moh.eet.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;

@Path("v1")
public class V1_EETRestAPI {
	
	@Path("/guestentry")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response guestEntry(@QueryParam("mobileNo") String mobileNo, @QueryParam("gateCode") String gateCode) throws Exception
	{
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try {
			json = V1_EETRestAPIDao.getInstance().guestEntry(mobileNo, gateCode);
			returnString = json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).header("Access-Control-Allow-Origin", "*")
	                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
	                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
	}
	
	@Path("/guestexit")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response guestExit(@QueryParam("mobileNo") String mobileNo, @QueryParam("gateCode") String gateCode) throws Exception
	{
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try {
			json = V1_EETRestAPIDao.getInstance().guestExit(mobileNo, gateCode);
			returnString = json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).header("Access-Control-Allow-Origin", "*")
	                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
	                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
	}
}
