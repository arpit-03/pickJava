package org.apache.http;

import java.util.Iterator;

public interface HeaderIterator extends Iterator<Object> {
  boolean hasNext();
  
  Header nextHeader();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/HeaderIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */