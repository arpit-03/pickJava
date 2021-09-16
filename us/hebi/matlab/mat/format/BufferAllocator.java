package us.hebi.matlab.mat.format;

import java.nio.ByteBuffer;

public interface BufferAllocator {
  ByteBuffer allocate(int paramInt);
  
  void release(ByteBuffer paramByteBuffer);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/BufferAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */