package rest.server.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import rest.server.utils.GzipUtil;

@Path("/file")
@Component("FileUploadServlet")
public class FileUploadServlet
{
	private static Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response test()
	{
		return Response.ok("Did you forget the filename?").build();
	}

	@GET
	@Path("/{filename}")
	@Produces("application/octet-stream")
	public Response downloadFile(@PathParam("filename") String filename)
	{
		logger.info("Retrieving file=" + filename);
		File file;
		try
		{
			file = new File(System.getProperty("user.dir") + "/files/" + filename);
			ResponseBuilder response = Response.ok(file);
			response.header("Content-Disposition", "attachment; filename=" + file.getName());
			return response.build();
		} catch (Exception e)
		{
			logger.error("Error retrieving file=" + filename, e);
			return Response.serverError().build();
		}

	}

	@POST
	@Path("/{filename}")
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.TEXT_PLAIN)
	public Response uploadFile(@PathParam("filename") String filename, @QueryParam("decompress") boolean decompress, InputStream uploadedInputStream)
	{
		try
		{
			String filePath = System.getProperty("user.dir") + "/files/" + filename;
			java.nio.file.Path path = FileSystems.getDefault().getPath(filePath);

			Files.copy(uploadedInputStream, path);

			long size = Files.size(path);

			logger.info("Uploading file=" + filename + ", size=" + size + " bytes");
			if (decompress)
			{
				File decompressFile = new File(filename);
				GzipUtil.decompress(decompressFile, new File(filePath));
			}

			return Response.created(URI.create(URLEncoder.encode(filePath, "UTF-8"))).build();

		} catch (IOException e)
		{
			logger.error("Error uploading file=" + filename, e);
			return Response.status(Status.CONFLICT).build();
		}
	}
}
