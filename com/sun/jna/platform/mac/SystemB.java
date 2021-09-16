/*     */ package com.sun.jna.platform.mac;
/*     */ 
/*     */ import com.sun.jna.Library;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.ptr.LongByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
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
/*     */ public interface SystemB
/*     */   extends Library
/*     */ {
/*  43 */   public static final SystemB INSTANCE = (SystemB)Native.loadLibrary("System", SystemB.class);
/*     */   
/*     */   public static final int HOST_LOAD_INFO = 1;
/*     */   
/*     */   public static final int HOST_VM_INFO = 2;
/*     */   
/*     */   public static final int HOST_CPU_LOAD_INFO = 3;
/*     */   
/*     */   public static final int HOST_VM_INFO64 = 4;
/*     */   
/*     */   public static final int CPU_STATE_MAX = 4;
/*     */   
/*     */   public static final int CPU_STATE_USER = 0;
/*     */   
/*     */   public static final int CPU_STATE_SYSTEM = 1;
/*     */   
/*     */   public static final int CPU_STATE_IDLE = 2;
/*     */   
/*     */   public static final int CPU_STATE_NICE = 3;
/*     */   
/*     */   public static final int PROCESSOR_BASIC_INFO = 1;
/*     */   public static final int PROCESSOR_CPU_LOAD_INFO = 2;
/*  65 */   public static final int UINT64_SIZE = Native.getNativeSize(long.class); int mach_host_self(); int mach_task_self(); int host_page_size(int paramInt, LongByReference paramLongByReference); int host_statistics(int paramInt1, int paramInt2, Structure paramStructure, IntByReference paramIntByReference); int host_statistics64(int paramInt1, int paramInt2, Structure paramStructure, IntByReference paramIntByReference);
/*  66 */   public static final int INT_SIZE = Native.getNativeSize(int.class); int sysctl(int[] paramArrayOfint, int paramInt1, Pointer paramPointer1, IntByReference paramIntByReference, Pointer paramPointer2, int paramInt2); int sysctlbyname(String paramString, Pointer paramPointer1, IntByReference paramIntByReference, Pointer paramPointer2, int paramInt); int sysctlnametomib(String paramString, Pointer paramPointer, IntByReference paramIntByReference);
/*     */   int host_processor_info(int paramInt1, int paramInt2, IntByReference paramIntByReference1, PointerByReference paramPointerByReference, IntByReference paramIntByReference2);
/*     */   int getloadavg(double[] paramArrayOfdouble, int paramInt);
/*  69 */   public static class HostCpuLoadInfo extends Structure { public static final List<String> FIELDS = createFieldsOrder("cpu_ticks");
/*  70 */     public int[] cpu_ticks = new int[4];
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  74 */       return FIELDS;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class HostLoadInfo extends Structure {
/*  79 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "avenrun", "mach_factor" });
/*  80 */     public int[] avenrun = new int[3];
/*  81 */     public int[] mach_factor = new int[3];
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  85 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class VMStatistics extends Structure {
/*  90 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "free_count", "active_count", "inactive_count", "wire_count", "zero_fill_count", "reactivations", "pageins", "pageouts", "faults", "cow_faults", "lookups", "hits", "purgeable_count", "purges", "speculative_count" });
/*     */     
/*     */     public int free_count;
/*     */     
/*     */     public int active_count;
/*     */     
/*     */     public int inactive_count;
/*     */     
/*     */     public int wire_count;
/*     */     
/*     */     public int zero_fill_count;
/*     */     
/*     */     public int reactivations;
/*     */     
/*     */     public int pageins;
/*     */     public int pageouts;
/*     */     public int faults;
/*     */     public int cow_faults;
/*     */     public int lookups;
/*     */     public int hits;
/*     */     public int purgeable_count;
/*     */     public int purges;
/*     */     public int speculative_count;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 115 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class VMStatistics64 extends Structure {
/* 120 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "free_count", "active_count", "inactive_count", "wire_count", "zero_fill_count", "reactivations", "pageins", "pageouts", "faults", "cow_faults", "lookups", "hits", "purges", "purgeable_count", "speculative_count", "decompressions", "compressions", "swapins", "swapouts", "compressor_page_count", "throttled_count", "external_page_count", "internal_page_count", "total_uncompressed_pages_in_compressor" });
/*     */     
/*     */     public int free_count;
/*     */     
/*     */     public int active_count;
/*     */     
/*     */     public int inactive_count;
/*     */     
/*     */     public int wire_count;
/*     */     
/*     */     public long zero_fill_count;
/*     */     
/*     */     public long reactivations;
/*     */     
/*     */     public long pageins;
/*     */     
/*     */     public long pageouts;
/*     */     
/*     */     public long faults;
/*     */     
/*     */     public long cow_faults;
/*     */     
/*     */     public long lookups;
/*     */     
/*     */     public long hits;
/*     */     
/*     */     public long purges;
/*     */     
/*     */     public int purgeable_count;
/*     */     
/*     */     public int speculative_count;
/*     */     
/*     */     public long decompressions;
/*     */     
/*     */     public long compressions;
/*     */     
/*     */     public long swapins;
/*     */     
/*     */     public long swapouts;
/*     */     
/*     */     public int compressor_page_count;
/*     */     public int throttled_count;
/*     */     public int external_page_count;
/*     */     public int internal_page_count;
/*     */     public long total_uncompressed_pages_in_compressor;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 167 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/mac/SystemB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */