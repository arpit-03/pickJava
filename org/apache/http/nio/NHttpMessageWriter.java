package org.apache.http.nio;

import java.io.IOException;
import org.apache.http.HttpException;

public interface NHttpMessageWriter<T extends org.apache.http.HttpMessage> {
  void reset();
  
  void write(T paramT) throws IOException, HttpException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/NHttpMessageWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */