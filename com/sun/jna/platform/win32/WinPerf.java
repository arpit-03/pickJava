/*    */ package com.sun.jna.platform.win32;public interface WinPerf { public static final int PERF_NO_INSTANCES = -1; public static final int PERF_SIZE_DWORD = 0; public static final int PERF_SIZE_LARGE = 256; public static final int PERF_SIZE_ZERO = 512; public static final int PERF_SIZE_VARIABLE_LEN = 768; public static final int PERF_TYPE_NUMBER = 0; public static final int PERF_TYPE_COUNTER = 1024; public static final int PERF_TYPE_TEXT = 2048; public static final int PERF_TYPE_ZERO = 3072; public static final int PERF_NUMBER_HEX = 0; public static final int PERF_NUMBER_DECIMAL = 65536; public static final int PERF_NUMBER_DEC_1000 = 131072; public static final int PERF_COUNTER_VALUE = 0; public static final int PERF_COUNTER_RATE = 65536; public static final int PERF_COUNTER_FRACTION = 131072; public static final int PERF_COUNTER_BASE = 196608; public static final int PERF_COUNTER_ELAPSED = 262144; public static final int PERF_COUNTER_QUEUELEN = 327680; public static final int PERF_COUNTER_HISTOGRAM = 393216; public static final int PERF_COUNTER_PRECISION = 458752; public static final int PERF_TEXT_UNICODE = 0; public static final int PERF_TEXT_ASCII = 65536; public static final int PERF_TIMER_TICK = 0; public static final int PERF_TIMER_100NS = 1048576; public static final int PERF_OBJECT_TIMER = 2097152; public static final int PERF_DELTA_COUNTER = 4194304; public static final int PERF_DELTA_BASE = 8388608; public static final int PERF_INVERSE_COUNTER = 16777216; public static final int PERF_MULTI_COUNTER = 33554432; public static final int PERF_DISPLAY_NO_SUFFIX = 0; public static final int PERF_DISPLAY_PER_SEC = 268435456; public static final int PERF_DISPLAY_PERCENT = 536870912; public static final int PERF_DISPLAY_SECONDS = 805306368; public static final int PERF_DISPLAY_NOSHOW = 1073741824; public static final int PERF_COUNTER_COUNTER = 272696320; public static final int PERF_COUNTER_TIMER = 541132032; public static final int PERF_COUNTER_QUEUELEN_TYPE = 4523008; public static final int PERF_COUNTER_LARGE_QUEUELEN_TYPE = 4523264; public static final int PERF_COUNTER_100NS_QUEUELEN_TYPE = 5571840; public static final int PERF_COUNTER_OBJ_TIME_QUEUELEN_TYPE = 6620416; public static final int PERF_COUNTER_BULK_COUNT = 272696576; public static final int PERF_COUNTER_TEXT = 2816;
/*    */   public static final int PERF_COUNTER_RAWCOUNT = 65536;
/*    */   public static final int PERF_COUNTER_LARGE_RAWCOUNT = 65792;
/*    */   public static final int PERF_COUNTER_RAWCOUNT_HEX = 0;
/*    */   public static final int PERF_COUNTER_LARGE_RAWCOUNT_HEX = 256;
/*    */   public static final int PERF_SAMPLE_FRACTION = 549585920;
/*    */   public static final int PERF_SAMPLE_COUNTER = 4260864;
/*    */   public static final int PERF_COUNTER_NODATA = 1073742336;
/*    */   public static final int PERF_COUNTER_TIMER_INV = 557909248;
/*    */   public static final int PERF_SAMPLE_BASE = 1073939457;
/*    */   public static final int PERF_AVERAGE_TIMER = 805438464;
/*    */   public static final int PERF_AVERAGE_BASE = 1073939458;
/*    */   public static final int PERF_AVERAGE_BULK = 1073874176;
/*    */   public static final int PERF_OBJ_TIME_TIMER = 543229184;
/*    */   public static final int PERF_100NSEC_TIMER = 542180608;
/*    */   public static final int PERF_100NSEC_TIMER_INV = 558957824;
/*    */   public static final int PERF_COUNTER_MULTI_TIMER = 574686464;
/*    */   public static final int PERF_COUNTER_MULTI_TIMER_INV = 591463680;
/*    */   public static final int PERF_COUNTER_MULTI_BASE = 1107494144;
/*    */   public static final int PERF_100NSEC_MULTI_TIMER = 575735040;
/*    */   public static final int PERF_100NSEC_MULTI_TIMER_INV = 592512256;
/*    */   public static final int PERF_RAW_FRACTION = 537003008;
/*    */   public static final int PERF_LARGE_RAW_FRACTION = 537003264;
/*    */   public static final int PERF_RAW_BASE = 1073939459;
/*    */   public static final int PERF_LARGE_RAW_BASE = 1073939712;
/*    */   public static final int PERF_ELAPSED_TIME = 807666944;
/*    */   public static final int PERF_COUNTER_HISTOGRAM_TYPE = -2147483648;
/*    */   public static final int PERF_COUNTER_DELTA = 4195328;
/*    */   public static final int PERF_COUNTER_LARGE_DELTA = 4195584;
/*    */   public static final int PERF_PRECISION_SYSTEM_TIMER = 541525248;
/*    */   public static final int PERF_PRECISION_100NS_TIMER = 542573824;
/*    */   public static final int PERF_PRECISION_OBJECT_TIMER = 543622400;
/*    */   public static final int PERF_PRECISION_TIMESTAMP = 1073939712;
/*    */   public static final int PERF_DETAIL_NOVICE = 100;
/*    */   public static final int PERF_DETAIL_ADVANCED = 200;
/*    */   public static final int PERF_DETAIL_EXPERT = 300;
/*    */   public static final int PERF_DETAIL_WIZARD = 400;
/*    */   public static final int PERF_NO_UNIQUE_ID = -1;
/*    */   public static final int PERF_QUERY_OBJECTS = -2147483648;
/*    */   public static final int PERF_QUERY_GLOBAL = -2147483647;
/*    */   public static final int PERF_QUERY_COSTLY = -2147483646;
/*    */   
/* 43 */   public static class PERF_DATA_BLOCK extends Structure { public static final List<String> FIELDS = createFieldsOrder(new String[] { "Signature", "LittleEndian", "Version", "Revision", "TotalByteLength", "HeaderLength", "NumObjectTypes", "DefaultObject", "SystemTime", "PerfTime", "PerfFreq", "PerfTime100nSec", "SystemNameLength", "SystemNameOffset" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 50 */     public char[] Signature = new char[4];
/*    */     public int LittleEndian;
/*    */     public int Version;
/*    */     public int Revision;
/*    */     public int TotalByteLength;
/*    */     public int HeaderLength;
/*    */     public int NumObjectTypes;
/*    */     public int DefaultObject;
/* 58 */     public WinBase.SYSTEMTIME SystemTime = new WinBase.SYSTEMTIME();
/* 59 */     public WinNT.LARGE_INTEGER PerfTime = new WinNT.LARGE_INTEGER();
/* 60 */     public WinNT.LARGE_INTEGER PerfFreq = new WinNT.LARGE_INTEGER();
/* 61 */     public WinNT.LARGE_INTEGER PerfTime100nSec = new WinNT.LARGE_INTEGER();
/*    */     
/*    */     public int SystemNameLength;
/*    */     public int SystemNameOffset;
/*    */     
/*    */     protected List<String> getFieldOrder() {
/* 67 */       return FIELDS;
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class PERF_INSTANCE_DEFINITION
/*    */     extends Structure
/*    */   {
/* 76 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "ByteLength", "ParentObjectTitleIndex", "ParentObjectInstance", "UniqueID", "NameOffset", "NameLength" });
/*    */     
/*    */     public int ByteLength;
/*    */     
/*    */     public int ParentObjectTitleIndex;
/*    */     
/*    */     public int ParentObjectInstance;
/*    */     
/*    */     public int UniqueID;
/*    */     public int NameOffset;
/*    */     public int NameLength;
/*    */     
/*    */     protected List<String> getFieldOrder() {
/* 89 */       return FIELDS;
/*    */     }
/*    */   } }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/WinPerf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */