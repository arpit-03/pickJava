package com.sun.jna.platform.win32.COM;

import com.sun.jna.platform.win32.Guid;

public interface IPersist extends IUnknown {
  Guid.CLSID GetClassID();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/IPersist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */