package us.hebi.matlab.mat.types;

public interface Cell extends Array {
  Matrix getMatrix(int paramInt);
  
  Matrix getMatrix(int paramInt1, int paramInt2);
  
  Matrix getMatrix(int[] paramArrayOfint);
  
  Sparse getSparse(int paramInt);
  
  Sparse getSparse(int paramInt1, int paramInt2);
  
  Sparse getSparse(int[] paramArrayOfint);
  
  Char getChar(int paramInt);
  
  Char getChar(int paramInt1, int paramInt2);
  
  Char getChar(int[] paramArrayOfint);
  
  Struct getStruct(int paramInt);
  
  Struct getStruct(int paramInt1, int paramInt2);
  
  Struct getStruct(int[] paramArrayOfint);
  
  Cell getCell(int paramInt);
  
  Cell getCell(int paramInt1, int paramInt2);
  
  Cell getCell(int[] paramArrayOfint);
  
  <T extends Array> T get(int paramInt);
  
  <T extends Array> T get(int paramInt1, int paramInt2);
  
  <T extends Array> T get(int[] paramArrayOfint);
  
  Cell set(int paramInt, Array paramArray);
  
  Cell set(int paramInt1, int paramInt2, Array paramArray);
  
  Cell set(int[] paramArrayOfint, Array paramArray);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Cell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */