package rest.server.resources.providers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Provider
@Component
@Produces({ MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, "text/csv" })
public class StringProvider implements MessageBodyWriter<String>
{

	public StringProvider()
	{

	}

	public long getSize(String t, Class<?> type, Type genericType, Annotation annotations[], MediaType mediaType)
	{
		return -1;
	}

	public boolean isWriteable(Class<?> type, Type genericType, Annotation annotations[], MediaType mediaType)
	{
		return true;
	}

	public void writeTo(String t, Class<?> type, Type genericType, Annotation annotations[], MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
	{
		try
		{
			entityStream.write(t.getBytes("UTF-8"));
		} catch (Exception ex)
		{
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
}
