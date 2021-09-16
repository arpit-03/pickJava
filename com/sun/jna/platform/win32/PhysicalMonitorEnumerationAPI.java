/*    */ package com.sun.jna.platform.win32;
/*    */ 
/*    */ import com.sun.jna.Structure;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface PhysicalMonitorEnumerationAPI
/*    */ {
/*    */   public static final int PHYSICAL_MONITOR_DESCRIPTION_SIZE = 128;
/*    */   
/*    */   public static class PHYSICAL_MONITOR
/*    */     extends Structure
/*    */   {
/* 58 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "hPhysicalMonitor", "szPhysicalMonitorDescription" });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public WinNT.HANDLE hPhysicalMonitor;
/*    */ 
/*    */ 
/*    */     
/* 67 */     public char[] szPhysicalMonitorDescription = new char[128];
/*    */ 
/*    */     
/*    */     protected List<String> getFieldOrder() {
/* 71 */       return FIELDS;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/PhysicalMonitorEnumerationAPI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */