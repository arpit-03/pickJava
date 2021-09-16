package org.apache.http.io;

public interface HttpMessageWriterFactory<T extends org.apache.http.HttpMessage> {
  HttpMessageWriter<T> create(SessionOutputBuffer paramSessionOutputBuffer);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/io/HttpMessageWriterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */