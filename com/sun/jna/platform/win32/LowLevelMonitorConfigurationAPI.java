/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.platform.EnumUtils;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface LowLevelMonitorConfigurationAPI
/*     */ {
/*     */   public static class MC_TIMING_REPORT
/*     */     extends Structure
/*     */   {
/*  44 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dwHorizontalFrequencyInHZ", "dwVerticalFrequencyInHZ", "bTimingStatusByte" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwHorizontalFrequencyInHZ;
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwVerticalFrequencyInHZ;
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.BYTE bTimingStatusByte;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  63 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum MC_VCP_CODE_TYPE
/*     */   {
/*  76 */     MC_MOMENTARY,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     MC_SET_PARAMETER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static class ByReference
/*     */       extends com.sun.jna.ptr.ByReference
/*     */     {
/*     */       public ByReference() {
/*  92 */         super(4);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public ByReference(LowLevelMonitorConfigurationAPI.MC_VCP_CODE_TYPE value) {
/* 100 */         super(4);
/* 101 */         setValue(value);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void setValue(LowLevelMonitorConfigurationAPI.MC_VCP_CODE_TYPE value) {
/* 109 */         getPointer().setInt(0L, EnumUtils.toInteger(value));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public LowLevelMonitorConfigurationAPI.MC_VCP_CODE_TYPE getValue() {
/* 117 */         return (LowLevelMonitorConfigurationAPI.MC_VCP_CODE_TYPE)EnumUtils.fromInteger(getPointer().getInt(0L), LowLevelMonitorConfigurationAPI.MC_VCP_CODE_TYPE.class);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/LowLevelMonitorConfigurationAPI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */