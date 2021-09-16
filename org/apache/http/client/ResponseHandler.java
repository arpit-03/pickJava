package org.apache.http.client;

import java.io.IOException;
import org.apache.http.HttpResponse;

public interface ResponseHandler<T> {
  T handleResponse(HttpResponse paramHttpResponse) throws ClientProtocolException, IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/ResponseHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */