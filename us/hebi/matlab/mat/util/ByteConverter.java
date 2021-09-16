package us.hebi.matlab.mat.util;

import java.nio.ByteOrder;

public interface ByteConverter {
  short getShort(ByteOrder paramByteOrder, byte[] paramArrayOfbyte, int paramInt);
  
  int getInt(ByteOrder paramByteOrder, byte[] paramArrayOfbyte, int paramInt);
  
  long getLong(ByteOrder paramByteOrder, byte[] paramArrayOfbyte, int paramInt);
  
  float getFloat(ByteOrder paramByteOrder, byte[] paramArrayOfbyte, int paramInt);
  
  double getDouble(ByteOrder paramByteOrder, byte[] paramArrayOfbyte, int paramInt);
  
  void putShort(short paramShort, ByteOrder paramByteOrder, byte[] paramArrayOfbyte, int paramInt);
  
  void putInt(int paramInt1, ByteOrder paramByteOrder, byte[] paramArrayOfbyte, int paramInt2);
  
  void putLong(long paramLong, ByteOrder paramByteOrder, byte[] paramArrayOfbyte, int paramInt);
  
  void putFloat(float paramFloat, ByteOrder paramByteOrder, byte[] paramArrayOfbyte, int paramInt);
  
  void putDouble(double paramDouble, ByteOrder paramByteOrder, byte[] paramArrayOfbyte, int paramInt);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/ByteConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */