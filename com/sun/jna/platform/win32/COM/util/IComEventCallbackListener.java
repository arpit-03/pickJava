package com.sun.jna.platform.win32.COM.util;

import com.sun.jna.platform.win32.COM.IDispatchCallback;

public interface IComEventCallbackListener {
  void setDispatchCallbackListener(IDispatchCallback paramIDispatchCallback);
  
  void errorReceivingCallbackEvent(String paramString, Exception paramException);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/util/IComEventCallbackListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */