package org.apache.http;

import org.apache.http.protocol.HttpContext;

public interface HttpResponseFactory {
  HttpResponse newHttpResponse(ProtocolVersion paramProtocolVersion, int paramInt, HttpContext paramHttpContext);
  
  HttpResponse newHttpResponse(StatusLine paramStatusLine, HttpContext paramHttpContext);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/HttpResponseFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */