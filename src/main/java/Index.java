package main.java;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.view.Viewable;

/**
*
*/
@Path("/")
public class Index {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response index() {
		return Response.ok(new Viewable("/indexpost")).build();
	}

	@GET
	@Path("/todolist")
	@Produces(MediaType.TEXT_HTML)
	public Response indexTODO() {
		return Response.ok(new Viewable("/indextodo")).build();
	}
}
