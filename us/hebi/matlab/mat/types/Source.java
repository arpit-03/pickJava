package us.hebi.matlab.mat.types;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public interface Source extends Closeable {
  Source order(ByteOrder paramByteOrder);
  
  ByteOrder order();
  
  long getPosition();
  
  byte readByte() throws IOException;
  
  void readBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  short readShort() throws IOException;
  
  void readShorts(short[] paramArrayOfshort, int paramInt1, int paramInt2) throws IOException;
  
  int readInt() throws IOException;
  
  void readInts(int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException;
  
  long readLong() throws IOException;
  
  void readLongs(long[] paramArrayOflong, int paramInt1, int paramInt2) throws IOException;
  
  float readFloat() throws IOException;
  
  void readFloats(float[] paramArrayOffloat, int paramInt1, int paramInt2) throws IOException;
  
  double readDouble() throws IOException;
  
  void readDoubles(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws IOException;
  
  Source readInflated(int paramInt1, int paramInt2) throws IOException;
  
  boolean isMutatedByChildren();
  
  void readByteBuffer(ByteBuffer paramByteBuffer) throws IOException;
  
  void skip(long paramLong) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Source.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */