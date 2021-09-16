package org.apache.http;

import java.io.IOException;
import org.apache.http.protocol.HttpContext;

public interface HttpResponseInterceptor {
  void process(HttpResponse paramHttpResponse, HttpContext paramHttpContext) throws HttpException, IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/HttpResponseInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */