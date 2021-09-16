package org.apache.http.entity.mime.content;

public interface ContentDescriptor {
  String getMimeType();
  
  String getMediaType();
  
  String getSubType();
  
  String getCharset();
  
  String getTransferEncoding();
  
  long getContentLength();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/entity/mime/content/ContentDescriptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */