package com.sun.jna.platform.win32.COM;

import com.sun.jna.platform.win32.Variant;

public interface IEnumVariant extends IUnknown {
  IEnumVariant Clone();
  
  Variant.VARIANT[] Next(int paramInt);
  
  void Reset();
  
  void Skip(int paramInt);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/IEnumVariant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */