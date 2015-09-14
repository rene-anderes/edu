package org.anderes.edu.jee.rest.sample;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import static javax.ws.rs.core.MediaType.*;

@Produces(TEXT_HTML)
@Provider
public class TextToHTMLWriter implements MessageBodyWriter<String> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,	Annotation[] annotations, MediaType mediaType) {
		return mediaType.equals(MediaType.TEXT_HTML_TYPE) && (type == String.class);
	}

	@Override
	public long getSize(String t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public void writeTo(String text, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html><html><head><title>Meldung</title></head><body><p>").append(text).append("</p></body></html>");
		entityStream.write(sb.toString().getBytes());
	}

}
