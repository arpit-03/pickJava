package com.jmatio.io;

import java.io.IOException;
import java.nio.ByteBuffer;

interface DataOutputStream {
  int size() throws IOException;
  
  ByteBuffer getByteBuffer() throws IOException;
  
  void write(ByteBuffer paramByteBuffer) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/DataOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */