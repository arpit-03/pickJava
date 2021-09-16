package org.apache.http.nio.entity;

import java.io.Closeable;
import java.io.IOException;
import org.apache.http.nio.ContentEncoder;
import org.apache.http.nio.IOControl;

public interface HttpAsyncContentProducer extends Closeable {
  void produceContent(ContentEncoder paramContentEncoder, IOControl paramIOControl) throws IOException;
  
  boolean isRepeatable();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/entity/HttpAsyncContentProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */