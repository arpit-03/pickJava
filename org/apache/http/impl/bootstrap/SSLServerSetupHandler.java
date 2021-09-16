package org.apache.http.impl.bootstrap;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocket;

public interface SSLServerSetupHandler {
  void initialize(SSLServerSocket paramSSLServerSocket) throws SSLException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/bootstrap/SSLServerSetupHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */