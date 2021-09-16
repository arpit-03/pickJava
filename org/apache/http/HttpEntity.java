package org.apache.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface HttpEntity {
  boolean isRepeatable();
  
  boolean isChunked();
  
  long getContentLength();
  
  Header getContentType();
  
  Header getContentEncoding();
  
  InputStream getContent() throws IOException, UnsupportedOperationException;
  
  void writeTo(OutputStream paramOutputStream) throws IOException;
  
  boolean isStreaming();
  
  @Deprecated
  void consumeContent() throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/HttpEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */