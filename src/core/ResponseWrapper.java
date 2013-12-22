package core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper {

	private MyPrintWriter tmpWriter;   
	
	private ByteArrayOutputStream output;   
	
	public ResponseWrapper(HttpServletResponse response) {
		super(response);
	    output = new ByteArrayOutputStream();   
	    tmpWriter = new MyPrintWriter(output);   
	}
	
	public void finalize() throws Throwable {   
        super.finalize();   
        output.close();   
        tmpWriter.close();   
	} 	
	
    public String getContent() {   
	    tmpWriter.flush();   //刷新该流的缓冲，详看java.io.Writer.flush()   
		String s = tmpWriter.getByteArrayOutputStream().toString();   
		//此处可根据需要进行对输出流以及Writer的重置操作   
		//比如tmpWriter.getByteArrayOutputStream().reset()   
		return s;   
	}	
	
    @Override
	public PrintWriter getWriter() throws IOException {
		return tmpWriter;
	}

    public void close() throws IOException {
    	tmpWriter.close();
    }
    
	private static class MyPrintWriter extends PrintWriter {   
	    ByteArrayOutputStream myOutput;   //此即为存放response输入流的对象   
	  
	    public MyPrintWriter(ByteArrayOutputStream output) {   
	        super(output);   
	        myOutput = output;   
        }   
	    
	    public ByteArrayOutputStream getByteArrayOutputStream() {   
	        return myOutput;   
	    }   
	 }	

}
