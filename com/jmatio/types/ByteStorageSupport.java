package com.jmatio.types;

public interface ByteStorageSupport<T extends Number> {
  int getBytesAllocated();
  
  T buldFromBytes(byte[] paramArrayOfbyte);
  
  byte[] getByteArray(T paramT);
  
  Class<?> getStorageClazz();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/ByteStorageSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */