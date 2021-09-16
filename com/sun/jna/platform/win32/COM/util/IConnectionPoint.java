package com.sun.jna.platform.win32.COM.util;

import com.sun.jna.platform.win32.COM.COMException;

public interface IConnectionPoint {
  IComEventCallbackCookie advise(Class<?> paramClass, IComEventCallbackListener paramIComEventCallbackListener) throws COMException;
  
  void unadvise(Class<?> paramClass, IComEventCallbackCookie paramIComEventCallbackCookie);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/util/IConnectionPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */