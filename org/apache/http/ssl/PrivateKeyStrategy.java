package org.apache.http.ssl;

import java.net.Socket;
import java.util.Map;

public interface PrivateKeyStrategy {
  String chooseAlias(Map<String, PrivateKeyDetails> paramMap, Socket paramSocket);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/ssl/PrivateKeyStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */