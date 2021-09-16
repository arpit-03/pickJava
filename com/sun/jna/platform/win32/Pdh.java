/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.win32.StdCallLibrary;
/*     */ import com.sun.jna.win32.W32APIOptions;
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
/*     */ 
/*     */ public interface Pdh
/*     */   extends StdCallLibrary
/*     */ {
/*  45 */   public static final Pdh INSTANCE = (Pdh)Native.loadLibrary("Pdh", Pdh.class, W32APIOptions.DEFAULT_OPTIONS);
/*     */   
/*     */   public static final int PDH_MAX_COUNTER_NAME = 1024;
/*     */   
/*     */   public static final int PDH_MAX_INSTANCE_NAME = 1024;
/*     */   
/*     */   public static final int PDH_MAX_COUNTER_PATH = 2048;
/*     */   
/*     */   public static final int PDH_MAX_DATASOURCE_PATH = 1024;
/*     */   
/*     */   public static final int PDH_CVERSION_WIN40 = 1024;
/*     */   
/*     */   public static final int PDH_CVERSION_WIN50 = 1280;
/*     */   
/*     */   public static final int PDH_VERSION = 1283;
/*     */   
/*     */   public static final int PDH_PATH_WBEM_RESULT = 1;
/*     */   
/*     */   public static final int PDH_PATH_WBEM_INPUT = 2;
/*     */   
/*     */   public static final int PDH_FMT_RAW = 16;
/*     */   
/*     */   public static final int PDH_FMT_ANSI = 32;
/*     */   
/*     */   public static final int PDH_FMT_UNICODE = 64;
/*     */   
/*     */   public static final int PDH_FMT_LONG = 256;
/*     */   
/*     */   public static final int PDH_FMT_DOUBLE = 512;
/*     */   
/*     */   public static final int PDH_FMT_LARGE = 1024;
/*     */   public static final int PDH_FMT_NOSCALE = 4096;
/*     */   public static final int PDH_FMT_1000 = 8192;
/*     */   public static final int PDH_FMT_NODATA = 16384;
/*     */   public static final int PDH_FMT_NOCAP100 = 32768;
/*     */   public static final int PERF_DETAIL_COSTLY = 65536;
/*     */   public static final int PERF_DETAIL_STANDARD = 65535;
/*     */   
/*     */   int PdhConnectMachine(String paramString);
/*     */   
/*     */   int PdhGetDllVersion(WinDef.DWORDByReference paramDWORDByReference);
/*     */   
/*     */   int PdhOpenQuery(String paramString, BaseTSD.DWORD_PTR paramDWORD_PTR, WinNT.HANDLEByReference paramHANDLEByReference);
/*     */   
/*     */   int PdhCloseQuery(WinNT.HANDLE paramHANDLE);
/*     */   
/*     */   int PdhMakeCounterPath(PDH_COUNTER_PATH_ELEMENTS paramPDH_COUNTER_PATH_ELEMENTS, char[] paramArrayOfchar, WinDef.DWORDByReference paramDWORDByReference, int paramInt);
/*     */   
/*     */   int PdhAddCounter(WinNT.HANDLE paramHANDLE, String paramString, BaseTSD.DWORD_PTR paramDWORD_PTR, WinNT.HANDLEByReference paramHANDLEByReference);
/*     */   
/*     */   int PdhAddEnglishCounter(WinNT.HANDLE paramHANDLE, String paramString, BaseTSD.DWORD_PTR paramDWORD_PTR, WinNT.HANDLEByReference paramHANDLEByReference);
/*     */   
/*     */   int PdhRemoveCounter(WinNT.HANDLE paramHANDLE);
/*     */   
/*     */   int PdhGetRawCounterValue(WinNT.HANDLE paramHANDLE, WinDef.DWORDByReference paramDWORDByReference, PDH_RAW_COUNTER paramPDH_RAW_COUNTER);
/*     */   
/*     */   int PdhValidatePath(String paramString);
/*     */   
/*     */   int PdhCollectQueryData(WinNT.HANDLE paramHANDLE);
/*     */   
/*     */   int PdhCollectQueryDataEx(WinNT.HANDLE paramHANDLE1, int paramInt, WinNT.HANDLE paramHANDLE2);
/*     */   
/*     */   int PdhCollectQueryDataWithTime(WinNT.HANDLE paramHANDLE, WinDef.LONGLONGByReference paramLONGLONGByReference);
/*     */   
/*     */   int PdhSetQueryTimeRange(WinNT.HANDLE paramHANDLE, PDH_TIME_INFO paramPDH_TIME_INFO);
/*     */   
/*     */   public static class PDH_COUNTER_PATH_ELEMENTS
/*     */     extends Structure
/*     */   {
/* 114 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "szMachineName", "szObjectName", "szInstanceName", "szParentInstance", "dwInstanceIndex", "szCounterName" });
/*     */     
/*     */     public String szMachineName;
/*     */     
/*     */     public String szObjectName;
/*     */     
/*     */     public String szInstanceName;
/*     */     
/*     */     public String szParentInstance;
/*     */     public int dwInstanceIndex;
/*     */     public String szCounterName;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 127 */       return FIELDS;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PDH_RAW_COUNTER
/*     */     extends Structure
/*     */   {
/* 182 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "CStatus", "TimeStamp", "FirstValue", "SecondValue", "MultiCount" });
/*     */ 
/*     */     
/*     */     public int CStatus;
/*     */     
/* 187 */     public WinBase.FILETIME TimeStamp = new WinBase.FILETIME();
/*     */ 
/*     */     
/*     */     public long FirstValue;
/*     */ 
/*     */     
/*     */     public long SecondValue;
/*     */ 
/*     */     
/*     */     public int MultiCount;
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 201 */       return FIELDS;
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
/*     */   public static class PDH_TIME_INFO
/*     */     extends Structure
/*     */   {
/* 276 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "StartTime", "EndTime", "SampleCount" });
/*     */     
/*     */     public long StartTime;
/*     */     
/*     */     public long EndTime;
/*     */     
/*     */     public int SampleCount;
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 286 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Pdh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */