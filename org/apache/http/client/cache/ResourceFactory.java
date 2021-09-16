package org.apache.http.client.cache;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceFactory {
  Resource generate(String paramString, InputStream paramInputStream, InputLimit paramInputLimit) throws IOException;
  
  Resource copy(String paramString, Resource paramResource) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/ResourceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */