package org.apache.http.nio.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;

@Deprecated
public interface NHttpResponseTrigger {
  void submitResponse(HttpResponse paramHttpResponse);
  
  void handleException(HttpException paramHttpException);
  
  void handleException(IOException paramIOException);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/protocol/NHttpResponseTrigger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */