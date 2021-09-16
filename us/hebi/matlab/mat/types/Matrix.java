package us.hebi.matlab.mat.types;

public interface Matrix extends Array {
  boolean isLogical();
  
  boolean isComplex();
  
  boolean getBoolean(int paramInt);
  
  byte getByte(int paramInt);
  
  short getShort(int paramInt);
  
  int getInt(int paramInt);
  
  long getLong(int paramInt);
  
  float getFloat(int paramInt);
  
  double getDouble(int paramInt);
  
  void setBoolean(int paramInt, boolean paramBoolean);
  
  void setByte(int paramInt, byte paramByte);
  
  void setShort(int paramInt, short paramShort);
  
  void setInt(int paramInt1, int paramInt2);
  
  void setLong(int paramInt, long paramLong);
  
  void setFloat(int paramInt, float paramFloat);
  
  void setDouble(int paramInt, double paramDouble);
  
  boolean getBoolean(int paramInt1, int paramInt2);
  
  byte getByte(int paramInt1, int paramInt2);
  
  short getShort(int paramInt1, int paramInt2);
  
  int getInt(int paramInt1, int paramInt2);
  
  long getLong(int paramInt1, int paramInt2);
  
  float getFloat(int paramInt1, int paramInt2);
  
  double getDouble(int paramInt1, int paramInt2);
  
  void setBoolean(int paramInt1, int paramInt2, boolean paramBoolean);
  
  void setByte(int paramInt1, int paramInt2, byte paramByte);
  
  void setShort(int paramInt1, int paramInt2, short paramShort);
  
  void setInt(int paramInt1, int paramInt2, int paramInt3);
  
  void setLong(int paramInt1, int paramInt2, long paramLong);
  
  void setFloat(int paramInt1, int paramInt2, float paramFloat);
  
  void setDouble(int paramInt1, int paramInt2, double paramDouble);
  
  boolean getBoolean(int[] paramArrayOfint);
  
  byte getByte(int[] paramArrayOfint);
  
  short getShort(int[] paramArrayOfint);
  
  int getInt(int[] paramArrayOfint);
  
  long getLong(int[] paramArrayOfint);
  
  float getFloat(int[] paramArrayOfint);
  
  double getDouble(int[] paramArrayOfint);
  
  void setBoolean(int[] paramArrayOfint, boolean paramBoolean);
  
  void setByte(int[] paramArrayOfint, byte paramByte);
  
  void setShort(int[] paramArrayOfint, short paramShort);
  
  void setInt(int[] paramArrayOfint, int paramInt);
  
  void setLong(int[] paramArrayOfint, long paramLong);
  
  void setFloat(int[] paramArrayOfint, float paramFloat);
  
  void setDouble(int[] paramArrayOfint, double paramDouble);
  
  byte getImaginaryByte(int paramInt);
  
  short getImaginaryShort(int paramInt);
  
  int getImaginaryInt(int paramInt);
  
  long getImaginaryLong(int paramInt);
  
  float getImaginaryFloat(int paramInt);
  
  double getImaginaryDouble(int paramInt);
  
  void setImaginaryByte(int paramInt, byte paramByte);
  
  void setImaginaryShort(int paramInt, short paramShort);
  
  void setImaginaryInt(int paramInt1, int paramInt2);
  
  void setImaginaryLong(int paramInt, long paramLong);
  
  void setImaginaryFloat(int paramInt, float paramFloat);
  
  void setImaginaryDouble(int paramInt, double paramDouble);
  
  byte getImaginaryByte(int paramInt1, int paramInt2);
  
  short getImaginaryShort(int paramInt1, int paramInt2);
  
  int getImaginaryInt(int paramInt1, int paramInt2);
  
  long getImaginaryLong(int paramInt1, int paramInt2);
  
  float getImaginaryFloat(int paramInt1, int paramInt2);
  
  double getImaginaryDouble(int paramInt1, int paramInt2);
  
  void setImaginaryByte(int paramInt1, int paramInt2, byte paramByte);
  
  void setImaginaryShort(int paramInt1, int paramInt2, short paramShort);
  
  void setImaginaryInt(int paramInt1, int paramInt2, int paramInt3);
  
  void setImaginaryLong(int paramInt1, int paramInt2, long paramLong);
  
  void setImaginaryFloat(int paramInt1, int paramInt2, float paramFloat);
  
  void setImaginaryDouble(int paramInt1, int paramInt2, double paramDouble);
  
  byte getImaginaryByte(int[] paramArrayOfint);
  
  short getImaginaryShort(int[] paramArrayOfint);
  
  int getImaginaryInt(int[] paramArrayOfint);
  
  long getImaginaryLong(int[] paramArrayOfint);
  
  float getImaginaryFloat(int[] paramArrayOfint);
  
  double getImaginaryDouble(int[] paramArrayOfint);
  
  void setImaginaryByte(int[] paramArrayOfint, byte paramByte);
  
  void setImaginaryShort(int[] paramArrayOfint, short paramShort);
  
  void setImaginaryInt(int[] paramArrayOfint, int paramInt);
  
  void setImaginaryLong(int[] paramArrayOfint, long paramLong);
  
  void setImaginaryFloat(int[] paramArrayOfint, float paramFloat);
  
  void setImaginaryDouble(int[] paramArrayOfint, double paramDouble);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Matrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */