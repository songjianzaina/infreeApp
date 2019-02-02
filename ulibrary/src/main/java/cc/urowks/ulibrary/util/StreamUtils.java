package cc.urowks.ulibrary.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	/**
	 * 流转字符串
	 * @param inputStream
	 * @throws IOException 
	 */
	public static String streamToString(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bytes=new byte[1024];
		int len=0;

			while((len=is.read(bytes))!=-1){
				bos.write(bytes, 0, len);
			}
	String result = bos.toString();
	is.close();
	bos.close();
	return result;
	}
	/**
	 * 关流
	 * @param stream
	 */
	public static void close(Closeable stream) {
		if(stream!=null){
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
