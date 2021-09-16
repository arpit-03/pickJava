package org.apache.http;

public interface RequestLine {
  String getMethod();
  
  ProtocolVersion getProtocolVersion();
  
  String getUri();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/RequestLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */