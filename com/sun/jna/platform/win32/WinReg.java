/*    */ package com.sun.jna.platform.win32;
/*    */ 
/*    */ import com.sun.jna.Pointer;
/*    */ import com.sun.jna.ptr.ByReference;
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
/*    */ public interface WinReg
/*    */ {
/*    */   public static class HKEY
/*    */     extends WinNT.HANDLE
/*    */   {
/*    */     public HKEY() {}
/*    */     
/*    */     public HKEY(Pointer p) {
/* 41 */       super(p); } public HKEY(int value) {
/* 42 */       super(new Pointer(value));
/*    */     } }
/*    */   
/*    */   public static class HKEYByReference extends ByReference {
/*    */     public HKEYByReference() {
/* 47 */       this(null);
/*    */     }
/*    */     
/*    */     public HKEYByReference(WinReg.HKEY h) {
/* 51 */       super(Pointer.SIZE);
/* 52 */       setValue(h);
/*    */     }
/*    */     
/*    */     public void setValue(WinReg.HKEY h) {
/* 56 */       getPointer().setPointer(0L, (h != null) ? h.getPointer() : null);
/*    */     }
/*    */     
/*    */     public WinReg.HKEY getValue() {
/* 60 */       Pointer p = getPointer().getPointer(0L);
/* 61 */       if (p == null)
/* 62 */         return null; 
/* 63 */       if (WinBase.INVALID_HANDLE_VALUE.getPointer().equals(p))
/* 64 */         return (WinReg.HKEY)WinBase.INVALID_HANDLE_VALUE; 
/* 65 */       WinReg.HKEY h = new WinReg.HKEY();
/* 66 */       h.setPointer(p);
/* 67 */       return h;
/*    */     }
/*    */   }
/*    */   
/* 71 */   public static final HKEY HKEY_CLASSES_ROOT = new HKEY(-2147483648);
/* 72 */   public static final HKEY HKEY_CURRENT_USER = new HKEY(-2147483647);
/* 73 */   public static final HKEY HKEY_LOCAL_MACHINE = new HKEY(-2147483646);
/* 74 */   public static final HKEY HKEY_USERS = new HKEY(-2147483645);
/* 75 */   public static final HKEY HKEY_PERFORMANCE_DATA = new HKEY(-2147483644);
/* 76 */   public static final HKEY HKEY_PERFORMANCE_TEXT = new HKEY(-2147483568);
/* 77 */   public static final HKEY HKEY_PERFORMANCE_NLSTEXT = new HKEY(-2147483552);
/* 78 */   public static final HKEY HKEY_CURRENT_CONFIG = new HKEY(-2147483643);
/* 79 */   public static final HKEY HKEY_DYN_DATA = new HKEY(-2147483642);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/WinReg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */