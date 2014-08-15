<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head></head>
<body>
	<h1>(REST POST API Spec)</h1>
	<h3>Recursos, URIs e métodos disponí­veis</h3>
	<h4>Recurso: post (coleção)</h4>
	<ul>
		<li><code>HEAD /post</code></li>
		<li><code>POST /post</code></li>
		<ul>
			<li><code>FormParam : msg</code></li>
			<li><code>FormParam : author</code></li>
		</ul>
		<li><code>GET /post</code></li>
	</ul>
	<h4>Recurso: post (entidade individual)</h4>
	<ul>
		<li><code>GET /post/:id</code></li>
		<li><code>HEAD /post/:id</code></li>
		<li><code>PUT /post/:id</code></li>
		<ul>
			<li><code>FormParam : novaMsg</code></li>
		</ul>
		<li><code>DELETE /post/:id</code></li>
	</ul>
	<h4>Recurso: comment (coleção)</h4>
	<ul>
		<li><code>HEAD /post/:id/comment</code></li>
		<li><code>GET /post/:id/comment</code></li>
		<li><code>POST /post/:id/comment</code></li>
		<ul>
			<li><code>FormParam : author</code></li>
			<li><code>FormParam : msg</code></li>
		</ul>
	</ul>
	<h4>Recurso: comment (entidade)</h4>
	<ul>
		<li><code>GET /post/:id/comment/:id</code></li>
		<li><code>HEAD /post/:id/comment/:id</code></li>
		<li><code>DELETE /post/:id/comment/:id</code></li>
		<li><code>PUT /post/:id/comment/:id</code></li>
		<ul>
			<li><code>FormParam : novaMsg</code></li>
		</ul>
	</ul>
</body>
</html>