package org.apache.http.protocol;

public interface HttpContext {
  public static final String RESERVED_PREFIX = "http.";
  
  Object getAttribute(String paramString);
  
  void setAttribute(String paramString, Object paramObject);
  
  Object removeAttribute(String paramString);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/protocol/HttpContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */