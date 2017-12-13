package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dal.SecurityManager;

/**
* <h1>WS</h1>
* This class defines a Web Service.
* It uses SecurityManager for Db access.
* It exposes 2 methods for saving/getting solutions to sokoban levels
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
@Path("/service")
public class WS {
	SecurityManager manager = new SecurityManager();
	
	
	/**
	* This method gets the solution by levelName.
	* @param levelName
	* @return Response.
	*/
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
	@Path("{levelName}")
	public Response getSolution(@PathParam("levelName") String levelName){
		
		if(levelName == null){
			return Response.ok("level name required").build();
		}
		String solution = manager.getSolution(levelName);
		if(solution == null){
			return Response.ok("Not Found", MediaType.TEXT_PLAIN).build();
		}
		
		return Response.ok(solution, MediaType.TEXT_PLAIN).build();
	}
	
	/**
	* This method sets a solution.
	* @param levelName
	* @param solution.
	* @return Response.
	*/
	@GET
	@Consumes({"text/plain,text/html"})
	@Path("{levelName}/{solution}")
	public Response insertSolution(@PathParam("levelName") String levelName,
							   @PathParam("solution") String solution){
		if(levelName == null || solution == null){
			return Response.status(Response.Status.BAD_REQUEST).entity("null")
					.type(MediaType.TEXT_PLAIN).build();
		}
		boolean success;
		success = manager.insertSolution(solution, levelName);
		if(success){
			return Response.ok("Solution saved", MediaType.TEXT_PLAIN).build();
		}
		return Response.ok().build();
	}
}
