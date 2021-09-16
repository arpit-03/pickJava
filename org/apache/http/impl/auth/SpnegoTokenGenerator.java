package org.apache.http.impl.auth;

import java.io.IOException;

@Deprecated
public interface SpnegoTokenGenerator {
  byte[] generateSpnegoDERObject(byte[] paramArrayOfbyte) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/auth/SpnegoTokenGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */