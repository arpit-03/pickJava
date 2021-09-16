/*    */ package com.sun.jna.platform.win32;
/*    */ 
/*    */ import com.sun.jna.Native;
/*    */ import com.sun.jna.Pointer;
/*    */ import com.sun.jna.WString;
/*    */ import com.sun.jna.ptr.PointerByReference;
/*    */ import com.sun.jna.win32.StdCallLibrary;
/*    */ import com.sun.jna.win32.W32APIOptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Ole32
/*    */   extends StdCallLibrary
/*    */ {
/* 47 */   public static final Ole32 INSTANCE = (Ole32)Native.loadLibrary("Ole32", Ole32.class, W32APIOptions.DEFAULT_OPTIONS);
/*    */   public static final int COINIT_APARTMENTTHREADED = 2;
/*    */   public static final int COINIT_MULTITHREADED = 0;
/*    */   public static final int COINIT_DISABLE_OLE1DDE = 4;
/*    */   public static final int COINIT_SPEED_OVER_MEMORY = 8;
/*    */   
/*    */   WinNT.HRESULT CoCreateGuid(Guid.GUID paramGUID);
/*    */   
/*    */   WinNT.HRESULT CoCreateGuid(Guid.GUID.ByReference paramByReference);
/*    */   
/*    */   int StringFromGUID2(Guid.GUID paramGUID, char[] paramArrayOfchar, int paramInt);
/*    */   
/*    */   WinNT.HRESULT IIDFromString(String paramString, Guid.GUID paramGUID);
/*    */   
/*    */   WinNT.HRESULT CoInitialize(WinDef.LPVOID paramLPVOID);
/*    */   
/*    */   WinNT.HRESULT CoInitializeEx(Pointer paramPointer, int paramInt);
/*    */   
/*    */   void CoUninitialize();
/*    */   
/*    */   WinNT.HRESULT CoCreateInstance(Guid.GUID paramGUID1, Pointer paramPointer, int paramInt, Guid.GUID paramGUID2, PointerByReference paramPointerByReference);
/*    */   
/*    */   WinNT.HRESULT CLSIDFromProgID(String paramString, Guid.CLSID.ByReference paramByReference);
/*    */   
/*    */   WinNT.HRESULT CLSIDFromString(String paramString, Guid.CLSID.ByReference paramByReference);
/*    */   
/*    */   WinNT.HRESULT CLSIDFromString(WString paramWString, Guid.CLSID.ByReference paramByReference);
/*    */   
/*    */   Pointer CoTaskMemAlloc(long paramLong);
/*    */   
/*    */   Pointer CoTaskMemRealloc(Pointer paramPointer, long paramLong);
/*    */   
/*    */   void CoTaskMemFree(Pointer paramPointer);
/*    */   
/*    */   WinNT.HRESULT CoGetMalloc(WinDef.DWORD paramDWORD, PointerByReference paramPointerByReference);
/*    */   
/*    */   WinNT.HRESULT GetRunningObjectTable(WinDef.DWORD paramDWORD, PointerByReference paramPointerByReference);
/*    */   
/*    */   WinNT.HRESULT CreateBindCtx(WinDef.DWORD paramDWORD, PointerByReference paramPointerByReference);
/*    */   
/*    */   boolean CoIsHandlerConnected(Pointer paramPointer);
/*    */   
/*    */   WinNT.HRESULT OleInitialize(Pointer paramPointer);
/*    */   
/*    */   void OleUninitialize();
/*    */   
/*    */   WinNT.HRESULT OleFlushClipboard();
/*    */   
/*    */   WinNT.HRESULT OleRun(Pointer paramPointer);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Ole32.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */