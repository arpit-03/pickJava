package org.apache.http.io;

import java.io.IOException;
import org.apache.http.HttpException;

public interface HttpMessageWriter<T extends org.apache.http.HttpMessage> {
  void write(T paramT) throws IOException, HttpException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/io/HttpMessageWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */