package rest.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Gzip compression/decompression gzip only allows a single file to be
 * compressed
 * 
 */
public class GzipUtil
{

	/**
	 * Compress file to gzip file using gzip compression
	 * 
	 * @param file
	 * @param gzip
	 * @throws IOException
	 */
	public static void compress(File file, File gzip) throws IOException
	{
		FileInputStream input = new FileInputStream(file);
		GZIPOutputStream output = new GZIPOutputStream(new FileOutputStream(gzip));
		byte[] buffer = new byte[1024];
		int len;
		while ((len = input.read(buffer)) != -1)
		{
			output.write(buffer, 0, len);
		}

		output.close();
		input.close();
	}

	/**
	 * Decompress file using gzip compression
	 * 
	 * @param file
	 *            , uncompressed file
	 * @param gzip
	 *            , compressed file to be decompressed
	 * @throws IOException
	 */
	public static void decompress(File file, File gzip) throws IOException
	{
		GZIPInputStream input = new GZIPInputStream(new FileInputStream(gzip));
		FileOutputStream output = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int len;
		while ((len = input.read(buffer)) != -1)
		{
			output.write(buffer, 0, len);
		}

		output.close();
		input.close();
	}

}
