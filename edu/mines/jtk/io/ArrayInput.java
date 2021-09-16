package edu.mines.jtk.io;

import java.io.DataInput;
import java.io.IOException;

public interface ArrayInput extends DataInput {
  void readFully(byte[] paramArrayOfbyte) throws IOException;
  
  void readFully(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  int skipBytes(int paramInt) throws IOException;
  
  boolean readBoolean() throws IOException;
  
  byte readByte() throws IOException;
  
  int readUnsignedByte() throws IOException;
  
  short readShort() throws IOException;
  
  int readUnsignedShort() throws IOException;
  
  char readChar() throws IOException;
  
  int readInt() throws IOException;
  
  long readLong() throws IOException;
  
  float readFloat() throws IOException;
  
  double readDouble() throws IOException;
  
  String readLine() throws IOException;
  
  String readUTF() throws IOException;
  
  void readBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  void readBytes(byte[] paramArrayOfbyte) throws IOException;
  
  void readBytes(byte[][] paramArrayOfbyte) throws IOException;
  
  void readBytes(byte[][][] paramArrayOfbyte) throws IOException;
  
  void readChars(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException;
  
  void readChars(char[] paramArrayOfchar) throws IOException;
  
  void readChars(char[][] paramArrayOfchar) throws IOException;
  
  void readChars(char[][][] paramArrayOfchar) throws IOException;
  
  void readShorts(short[] paramArrayOfshort, int paramInt1, int paramInt2) throws IOException;
  
  void readShorts(short[] paramArrayOfshort) throws IOException;
  
  void readShorts(short[][] paramArrayOfshort) throws IOException;
  
  void readShorts(short[][][] paramArrayOfshort) throws IOException;
  
  void readInts(int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException;
  
  void readInts(int[] paramArrayOfint) throws IOException;
  
  void readInts(int[][] paramArrayOfint) throws IOException;
  
  void readInts(int[][][] paramArrayOfint) throws IOException;
  
  void readLongs(long[] paramArrayOflong, int paramInt1, int paramInt2) throws IOException;
  
  void readLongs(long[] paramArrayOflong) throws IOException;
  
  void readLongs(long[][] paramArrayOflong) throws IOException;
  
  void readLongs(long[][][] paramArrayOflong) throws IOException;
  
  void readFloats(float[] paramArrayOffloat, int paramInt1, int paramInt2) throws IOException;
  
  void readFloats(float[] paramArrayOffloat) throws IOException;
  
  void readFloats(float[][] paramArrayOffloat) throws IOException;
  
  void readFloats(float[][][] paramArrayOffloat) throws IOException;
  
  void readDoubles(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws IOException;
  
  void readDoubles(double[] paramArrayOfdouble) throws IOException;
  
  void readDoubles(double[][] paramArrayOfdouble) throws IOException;
  
  void readDoubles(double[][][] paramArrayOfdouble) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/io/ArrayInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */