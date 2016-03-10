package org.anderes.edu.jee.sample;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.*;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Dieser test läuft erst, wenn die Applikation auf dem entsprechenden
 * JEE Container deployed ist. Die Variable 'uri' muss mit den Angaben
 * des Servers überein stimmen.
 */
@Ignore("Dieser Test funktioniert dann, wenn das Projekt auf dem Server deployed ist")
public class HelloWorldResourceIT {
	
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
		assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(OK.getStatusCode()));
		assertThat(response.getMediaType(), is(TEXT_PLAIN_TYPE));
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
		assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(OK.getStatusCode()));
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
		assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(NO_CONTENT.getStatusCode()));
	}
}
