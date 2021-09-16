package us.hebi.matlab.mat.types;

import java.util.List;

public interface Struct extends Array {
  List<String> getFieldNames();
  
  Array[] remove(String paramString);
  
  Matrix getMatrix(String paramString);
  
  Sparse getSparse(String paramString);
  
  Char getChar(String paramString);
  
  Struct getStruct(String paramString);
  
  ObjectStruct getObject(String paramString);
  
  Cell getCell(String paramString);
  
  <T extends Array> T get(String paramString);
  
  Struct set(String paramString, Array paramArray);
  
  Matrix getMatrix(String paramString, int paramInt);
  
  Matrix getMatrix(String paramString, int paramInt1, int paramInt2);
  
  Matrix getMatrix(String paramString, int[] paramArrayOfint);
  
  Sparse getSparse(String paramString, int paramInt);
  
  Sparse getSparse(String paramString, int paramInt1, int paramInt2);
  
  Sparse getSparse(String paramString, int[] paramArrayOfint);
  
  Char getChar(String paramString, int paramInt);
  
  Char getChar(String paramString, int paramInt1, int paramInt2);
  
  Char getChar(String paramString, int[] paramArrayOfint);
  
  Struct getStruct(String paramString, int paramInt);
  
  Struct getStruct(String paramString, int paramInt1, int paramInt2);
  
  Struct getStruct(String paramString, int[] paramArrayOfint);
  
  ObjectStruct getObject(String paramString, int paramInt);
  
  ObjectStruct getObject(String paramString, int paramInt1, int paramInt2);
  
  ObjectStruct getObject(String paramString, int[] paramArrayOfint);
  
  Cell getCell(String paramString, int paramInt);
  
  Cell getCell(String paramString, int paramInt1, int paramInt2);
  
  Cell getCell(String paramString, int[] paramArrayOfint);
  
  <T extends Array> T get(String paramString, int paramInt);
  
  <T extends Array> T get(String paramString, int paramInt1, int paramInt2);
  
  <T extends Array> T get(String paramString, int[] paramArrayOfint);
  
  Struct set(String paramString, int paramInt, Array paramArray);
  
  Struct set(String paramString, int paramInt1, int paramInt2, Array paramArray);
  
  Struct set(String paramString, int[] paramArrayOfint, Array paramArray);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Struct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */