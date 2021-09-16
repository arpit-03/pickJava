package org.apache.http;

import java.io.IOException;
import java.net.Socket;

public interface HttpConnectionFactory<T extends HttpConnection> {
  T createConnection(Socket paramSocket) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/HttpConnectionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */