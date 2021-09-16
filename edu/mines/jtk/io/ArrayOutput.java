package edu.mines.jtk.io;

import java.io.DataOutput;
import java.io.IOException;

public interface ArrayOutput extends DataOutput {
  void write(int paramInt) throws IOException;
  
  void write(byte[] paramArrayOfbyte) throws IOException;
  
  void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  void writeBoolean(boolean paramBoolean) throws IOException;
  
  void writeByte(int paramInt) throws IOException;
  
  void writeShort(int paramInt) throws IOException;
  
  void writeChar(int paramInt) throws IOException;
  
  void writeInt(int paramInt) throws IOException;
  
  void writeLong(long paramLong) throws IOException;
  
  void writeFloat(float paramFloat) throws IOException;
  
  void writeDouble(double paramDouble) throws IOException;
  
  void writeBytes(String paramString) throws IOException;
  
  void writeChars(String paramString) throws IOException;
  
  void writeUTF(String paramString) throws IOException;
  
  void writeBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  void writeBytes(byte[] paramArrayOfbyte) throws IOException;
  
  void writeBytes(byte[][] paramArrayOfbyte) throws IOException;
  
  void writeBytes(byte[][][] paramArrayOfbyte) throws IOException;
  
  void writeChars(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException;
  
  void writeChars(char[] paramArrayOfchar) throws IOException;
  
  void writeChars(char[][] paramArrayOfchar) throws IOException;
  
  void writeChars(char[][][] paramArrayOfchar) throws IOException;
  
  void writeShorts(short[] paramArrayOfshort, int paramInt1, int paramInt2) throws IOException;
  
  void writeShorts(short[] paramArrayOfshort) throws IOException;
  
  void writeShorts(short[][] paramArrayOfshort) throws IOException;
  
  void writeShorts(short[][][] paramArrayOfshort) throws IOException;
  
  void writeInts(int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException;
  
  void writeInts(int[] paramArrayOfint) throws IOException;
  
  void writeInts(int[][] paramArrayOfint) throws IOException;
  
  void writeInts(int[][][] paramArrayOfint) throws IOException;
  
  void writeLongs(long[] paramArrayOflong, int paramInt1, int paramInt2) throws IOException;
  
  void writeLongs(long[] paramArrayOflong) throws IOException;
  
  void writeLongs(long[][] paramArrayOflong) throws IOException;
  
  void writeLongs(long[][][] paramArrayOflong) throws IOException;
  
  void writeFloats(float[] paramArrayOffloat, int paramInt1, int paramInt2) throws IOException;
  
  void writeFloats(float[] paramArrayOffloat) throws IOException;
  
  void writeFloats(float[][] paramArrayOffloat) throws IOException;
  
  void writeFloats(float[][][] paramArrayOffloat) throws IOException;
  
  void writeDoubles(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws IOException;
  
  void writeDoubles(double[] paramArrayOfdouble) throws IOException;
  
  void writeDoubles(double[][] paramArrayOfdouble) throws IOException;
  
  void writeDoubles(double[][][] paramArrayOfdouble) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/io/ArrayOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */