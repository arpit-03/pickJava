package org.apache.http.osgi.services;

public interface ProxyConfiguration {
  boolean isEnabled();
  
  String getHostname();
  
  int getPort();
  
  String getUsername();
  
  String getPassword();
  
  String[] getProxyExceptions();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/osgi/services/ProxyConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */