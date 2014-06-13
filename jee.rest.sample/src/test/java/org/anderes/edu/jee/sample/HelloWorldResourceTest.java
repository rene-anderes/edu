package org.anderes.edu.jee.sample;

import static javax.ws.rs.core.MediaType.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static javax.ws.rs.core.Response.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;

import org.junit.Test;

public class HelloWorldResourceTest {
	
	final UriBuilder uri = UriBuilder.fromPath("rest-sample")
			.scheme("http").host("localhost").port(8088).path("services").path("helloworld");
	
	@Test
	public void shouldBeTextResponse() {
		
		// given
		final Client client = ClientBuilder.newClient();
		final WebTarget target = client.target(uri);
	
		// when
		final Response response = target.request(TEXT_PLAIN).buildGet().invoke();
		
		// then
		assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(Status.OK.getStatusCode()));
		assertThat(response.getMediaType(), is(MediaType.TEXT_PLAIN_TYPE));
		assertThat(response.hasEntity(), is(true));
		assertThat(response.readEntity(String.class), is("Hello World"));

	}
	
	@Test
	public void shouldBeHtmlResponse() {
		
		// given
		final Client client = ClientBuilder.newClient();
		final WebTarget target = client.target(uri);
	
		// when
		final Response response = target.request(TEXT_HTML).buildGet().invoke();
		
		// then
		assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(Status.OK.getStatusCode()));
		assertThat(response.getMediaType(), is(MediaType.TEXT_HTML_TYPE));
		assertThat(response.hasEntity(), is(true));
		assertThat(response.readEntity(String.class), is("<!DOCTYPE html><html><head><title>Meldung</title></head><body><p>Hello World</p></body></html>"));

	}
	
	@Test 
	public void shouldBeStoreTheMessage() {

		// given
		final Client client = ClientBuilder.newClient();
		final WebTarget target = client.target(uri);
		
		// when
		final Variant variant = new Variant(TEXT_PLAIN_TYPE, "de_CH", "UTF-8");
		final Entity<String> entity = Entity.entity("Information", variant);

		final Response response = target.request().put(entity);
		assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(Status.NO_CONTENT.getStatusCode()));
	}
}
