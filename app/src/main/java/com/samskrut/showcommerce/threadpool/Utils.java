package com.samskrut.showcommerce.threadpool;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
	
	static int counta=0;
    public static void CopyStream(InputStream is, OutputStream os)
    {
    	counta++;
        final int buffer_size=1024;
        try
        {
        	
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              //Read byte from input stream
            	
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              
              //Write byte from output stream
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}