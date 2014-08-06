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
@Path("/")
public class Index {

	private String indexHTML = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><!doctype html><html><head></head><body><h1>(REST POST API Spec)</h1><h3>Recursos, URIs e métodos disponíveis </h3><h4>Recurso: post (coleção)</h4><ul><li><code>HEAD /post</code></li><li><code>POST /post</code></li><ul><li><code>PARAMETRO msg</code></li></ul><li><code>GET /post</code></li></ul><h4>Recurso: post (entidade individual)</h4><ul><li><code>GET /post/:id</code></li><li><code>HEAD /post/:id</code></li><li><code>PUT /post/:id</code></li><ul><li><code>PARAMETRO novaMsg</code></li></ul><li><code>DELETE /post/:id</code></li></ul><h4>Recurso: comment (coleção)</h4><ul><li><code>HEAD /post/:id/comment</code></li><li><code>GET /post/:id/comment</code></li><li><code>POST /post/:id/comment</code></li><ul><li><code>PARAMETRO msg</code></li></ul></ul><h4>Recurso: comment (entidade)</h4><ul><li><code>GET /post/:id/comment/:id</code></li><li><code>HEAD /post/:id/comment/:id</code></li><li><code>DELETE /post/:id/comment/:id</code></li><li><code>PUT /post/:id/comment/:id</code></li><ul><li><code>PARAMETRO novaMsg</code></li></ul></ul></body></html>";

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response index() {
		try {
			WebService.posts = Arrays.asList(WebService.mapper.readValue(
					new FileReader(new File(WebService.FILE)), Post[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.ok().entity(indexHTML).build();
	}
}
