package us.hebi.matlab.mat.types;

public interface Char extends Array {
  CharSequence asCharSequence();
  
  String getString();
  
  String getRow(int paramInt);
  
  char getChar(int paramInt);
  
  char getChar(int paramInt1, int paramInt2);
  
  char getChar(int[] paramArrayOfint);
  
  void setChar(int paramInt, char paramChar);
  
  void setChar(int paramInt1, int paramInt2, char paramChar);
  
  void setChar(int[] paramArrayOfint, char paramChar);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Char.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */