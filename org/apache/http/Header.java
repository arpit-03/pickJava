package org.apache.http;

public interface Header extends NameValuePair {
  HeaderElement[] getElements() throws ParseException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/Header.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */