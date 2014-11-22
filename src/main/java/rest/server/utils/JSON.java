package rest.server.utils;

import java.io.StringReader;
import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * Utilities for (de)serializing Java <-> JSON
 * Only needed by JUnit tests since jax-rs automatically does this for web resources
 *
 */
public class JSON 
{
	
	public static String serialize(Object object) throws Exception 
	{
		StringWriter writer = new StringWriter();
		new ObjectMapper().writeValue(writer, object);
		return writer.toString();
	}

	public static Object deserialize(String json, Class<?> type) throws Exception 
	{
		return new ObjectMapper().readValue(new StringReader(json), type);
	}
}
