package org.apache.http.auth;

import java.security.Principal;

public interface Credentials {
  Principal getUserPrincipal();
  
  String getPassword();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/auth/Credentials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */