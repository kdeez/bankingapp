package rest.server.resources.providers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Provider
@Component
@Produces("text/html")
public class StreamProvider implements MessageBodyWriter<StreamingOutput>
{

	public StreamProvider()
	{

	}

	public long getSize(StreamingOutput t, Class<?> type, Type genericType, Annotation annotations[], MediaType mediaType)
	{
		return -1;
	}

	public boolean isWriteable(Class<?> type, Type genericType, Annotation annotations[], MediaType mediaType)
	{
		for (Class<?> typez : type.getInterfaces())
		{
			if (typez.equals(StreamingOutput.class))
			{
				return true;
			}
		}
		return false;
	}

	public void writeTo(StreamingOutput t, Class<?> type, Type genericType, Annotation annotations[], MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
	{
		t.write(entityStream);
	}
}
