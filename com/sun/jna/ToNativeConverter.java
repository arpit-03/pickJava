package com.sun.jna;

public interface ToNativeConverter {
  Object toNative(Object paramObject, ToNativeContext paramToNativeContext);
  
  Class<?> nativeType();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/ToNativeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */