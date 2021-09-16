package org.apache.http.nio.protocol;

import org.apache.http.HttpRequest;

public interface HttpAsyncRequestHandlerMapper {
  HttpAsyncRequestHandler<?> lookup(HttpRequest paramHttpRequest);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/protocol/HttpAsyncRequestHandlerMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */