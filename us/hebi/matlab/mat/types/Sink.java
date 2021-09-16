package us.hebi.matlab.mat.types;

import java.io.Closeable;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.Deflater;

public interface Sink extends Closeable {
  Sink nativeOrder();
  
  Sink order(ByteOrder paramByteOrder);
  
  ByteOrder order();
  
  long position() throws IOException;
  
  void position(long paramLong) throws IOException;
  
  void writeByte(byte paramByte) throws IOException;
  
  void writeBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  void writeShort(short paramShort) throws IOException;
  
  void writeShorts(short[] paramArrayOfshort, int paramInt1, int paramInt2) throws IOException;
  
  void writeInt(int paramInt) throws IOException;
  
  void writeInts(int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException;
  
  void writeLong(long paramLong) throws IOException;
  
  void writeLongs(long[] paramArrayOflong, int paramInt1, int paramInt2) throws IOException;
  
  void writeFloat(float paramFloat) throws IOException;
  
  void writeFloats(float[] paramArrayOffloat, int paramInt1, int paramInt2) throws IOException;
  
  void writeDouble(double paramDouble) throws IOException;
  
  void writeDoubles(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws IOException;
  
  Sink writeDeflated(Deflater paramDeflater);
  
  void writeByteBuffer(ByteBuffer paramByteBuffer) throws IOException;
  
  void writeInputStream(InputStream paramInputStream, long paramLong) throws IOException;
  
  void writeDataInput(DataInput paramDataInput, long paramLong) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Sink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */