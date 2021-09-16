package us.hebi.matlab.mat.types;

public interface Opaque extends Array {
  String getObjectType();
  
  String getClassName();
  
  Array getContent();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Opaque.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */