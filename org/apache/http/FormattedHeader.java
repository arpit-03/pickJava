package org.apache.http;

import org.apache.http.util.CharArrayBuffer;

public interface FormattedHeader extends Header {
  CharArrayBuffer getBuffer();
  
  int getValuePos();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/FormattedHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */