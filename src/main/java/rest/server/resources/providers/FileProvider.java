package rest.server.resources.providers;

import java.io.File;
import java.io.FileInputStream;
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

import org.springframework.stereotype.Component;

@Provider
@Component
@Produces({"application/octet-stream"})
public class FileProvider implements MessageBodyWriter<File>
{

	public FileProvider()
	{
	}

	public long getSize(File t, Class<?> type, Type genericType, Annotation annotations[], MediaType mediaType)
	{
		return -1;
	}

	public boolean isWriteable(Class<?> type, Type genericType, Annotation annotations[], MediaType mediaType)
	{
		if (type.equals(File.class))
		{
			return true;
		}

		return false;
	}

	public void writeTo(File t, Class<?> type, Type genericType, Annotation annotations[], MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
	{
		FileInputStream fis = new FileInputStream(t);

		byte[] buf = new byte[2048];
		int read = 0;
		while ((read = fis.read(buf)) > 0)
		{
			entityStream.write(buf, 0, read);
		}

		fis.close();
	}
}
