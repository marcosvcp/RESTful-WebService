package main.java;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
*
*/
@Path("/espec")
public class Index {

	private String indexHTML = "<!doctype html><html><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><head></head><body><h1>(REST POST API Spec)</h1><h3>Recursos, URIs e métodos disponíveis </h3><h4>Recurso: post (coleção)</h4><ul><li><code>HEAD /post</code></li><li><code>POST /post</code></li><ul><li><code>PARAMETRO msg</code></li></ul><li><code>GET /post</code></li></ul><h4>Recurso: post (entidade individual)</h4><ul><li><code>GET /post/:id</code></li><li><code>HEAD /post/:id</code></li><li><code>PUT /post/:id</code></li><ul><li><code>PARAMETRO novaMsg</code></li></ul><li><code>DELETE /post/:id</code></li></ul><h4>Recurso: comment (coleção)</h4><ul><li><code>HEAD /post/:id/comment</code></li><li><code>GET /post/:id/comment</code></li><li><code>POST /post/:id/comment</code></li><ul><li><code>PARAMETRO msg</code></li></ul></ul><h4>Recurso: comment (entidade)</h4><ul><li><code>GET /post/:id/comment/:id</code></li><li><code>HEAD /post/:id/comment/:id</code></li><li><code>DELETE /post/:id/comment/:id</code></li><li><code>PUT /post/:id/comment/:id</code></li><ul><li><code>PARAMETRO novaMsg</code></li></ul></ul></body></html>";
	private String indexTODOHTML = "<!doctype html><html><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><head></head><body><h1>(TODO API Spec)</h1><h3>Recursos, URIs e métodos disponíveis</h3><h4>Recurso: Task (coleção)</h4><ul><li><code>HEAD /task</code></li><li><code>POST /task</code></li><ul><li><code>PARAMETRO title</code></li></ul><li><code>GET /task</code></li></ul><h4>Recurso: Task (entidade individual)</h4><ul><li><code>GET /task/:id</code></li><li><code>HEAD /task/:id</code></li><li><code>PUT /task/:id</code></li><ul><li><code>PARAMETRO newTitle</code></li></ul><li><code>DELETE /task/:id</code></li></ul></body></html>";

	@GET
	@Path("/post")
	@Produces(MediaType.TEXT_HTML)
	public Response index() {
		// try {
		// WebService.posts = Arrays.asList(WebService.mapper.readValue(
		// new FileReader(new File(WebService.FILE)), Post[].class));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return Response.ok().entity(indexHTML).build();
	}

	@GET
	@Path("/todolist")
	@Produces(MediaType.TEXT_HTML)
	public Response indexTODO() {
		return Response.ok().entity(indexTODOHTML).build();
	}
}
