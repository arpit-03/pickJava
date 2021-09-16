package com.sun.jna;

public interface TypeMapper {
  FromNativeConverter getFromNativeConverter(Class<?> paramClass);
  
  ToNativeConverter getToNativeConverter(Class<?> paramClass);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/TypeMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */