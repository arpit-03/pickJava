/*    */ package com.sun.jna.platform.win32;
/*    */ 
/*    */ import com.sun.jna.Native;
/*    */ import com.sun.jna.Pointer;
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
/*    */ public interface Shlwapi
/*    */   extends StdCallLibrary
/*    */ {
/* 37 */   public static final Shlwapi INSTANCE = (Shlwapi)Native.loadLibrary("Shlwapi", Shlwapi.class, W32APIOptions.DEFAULT_OPTIONS);
/*    */   
/*    */   WinNT.HRESULT StrRetToStr(PointerByReference paramPointerByReference1, Pointer paramPointer, PointerByReference paramPointerByReference2);
/*    */   
/*    */   boolean PathIsUNC(String paramString);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Shlwapi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */