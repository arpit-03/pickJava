package org.apache.http.nio;

import java.io.IOException;
import org.apache.http.HttpException;

@Deprecated
public interface NHttpClientHandler {
  void connected(NHttpClientConnection paramNHttpClientConnection, Object paramObject);
  
  void requestReady(NHttpClientConnection paramNHttpClientConnection);
  
  void responseReceived(NHttpClientConnection paramNHttpClientConnection);
  
  void inputReady(NHttpClientConnection paramNHttpClientConnection, ContentDecoder paramContentDecoder);
  
  void outputReady(NHttpClientConnection paramNHttpClientConnection, ContentEncoder paramContentEncoder);
  
  void exception(NHttpClientConnection paramNHttpClientConnection, IOException paramIOException);
  
  void exception(NHttpClientConnection paramNHttpClientConnection, HttpException paramHttpException);
  
  void timeout(NHttpClientConnection paramNHttpClientConnection);
  
  void closed(NHttpClientConnection paramNHttpClientConnection);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/NHttpClientHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */