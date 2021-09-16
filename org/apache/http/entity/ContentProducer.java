package org.apache.http.entity;

import java.io.IOException;
import java.io.OutputStream;

public interface ContentProducer {
  void writeTo(OutputStream paramOutputStream) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/entity/ContentProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */