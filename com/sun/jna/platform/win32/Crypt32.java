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
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Crypt32
/*    */   extends StdCallLibrary
/*    */ {
/* 40 */   public static final Crypt32 INSTANCE = (Crypt32)Native.loadLibrary("Crypt32", Crypt32.class, W32APIOptions.DEFAULT_OPTIONS);
/*    */   
/*    */   boolean CryptProtectData(WinCrypt.DATA_BLOB paramDATA_BLOB1, String paramString, WinCrypt.DATA_BLOB paramDATA_BLOB2, Pointer paramPointer, WinCrypt.CRYPTPROTECT_PROMPTSTRUCT paramCRYPTPROTECT_PROMPTSTRUCT, int paramInt, WinCrypt.DATA_BLOB paramDATA_BLOB3);
/*    */   
/*    */   boolean CryptUnprotectData(WinCrypt.DATA_BLOB paramDATA_BLOB1, PointerByReference paramPointerByReference, WinCrypt.DATA_BLOB paramDATA_BLOB2, Pointer paramPointer, WinCrypt.CRYPTPROTECT_PROMPTSTRUCT paramCRYPTPROTECT_PROMPTSTRUCT, int paramInt, WinCrypt.DATA_BLOB paramDATA_BLOB3);
/*    */   
/*    */   boolean CertAddEncodedCertificateToSystemStore(String paramString, Pointer paramPointer, int paramInt);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Crypt32.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */