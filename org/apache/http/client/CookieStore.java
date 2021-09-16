package org.apache.http.client;

import java.util.Date;
import java.util.List;
import org.apache.http.cookie.Cookie;

public interface CookieStore {
  void addCookie(Cookie paramCookie);
  
  List<Cookie> getCookies();
  
  boolean clearExpired(Date paramDate);
  
  void clear();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/CookieStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */