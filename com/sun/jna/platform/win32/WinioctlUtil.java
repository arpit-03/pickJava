/*    */ package com.sun.jna.platform.win32;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WinioctlUtil
/*    */ {
/*    */   public static int CTL_CODE(int DeviceType, int Function, int Method, int Access) {
/* 43 */     return DeviceType << 16 | Access << 14 | Function << 2 | Method;
/*    */   }
/*    */   
/* 46 */   public static final int FSCTL_GET_COMPRESSION = CTL_CODE(9, 15, 0, 0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public static final int FSCTL_SET_COMPRESSION = CTL_CODE(9, 16, 0, 3);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   public static final int FSCTL_SET_REPARSE_POINT = CTL_CODE(9, 41, 0, 0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 64 */   public static final int FSCTL_GET_REPARSE_POINT = CTL_CODE(9, 42, 0, 0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public static final int FSCTL_DELETE_REPARSE_POINT = CTL_CODE(9, 43, 0, 0);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/WinioctlUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */