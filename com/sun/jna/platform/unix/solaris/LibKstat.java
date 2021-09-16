/*     */ package com.sun.jna.platform.unix.solaris;
/*     */ 
/*     */ import com.sun.jna.Library;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.Union;
/*     */ import java.util.Arrays;
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
/*     */ public interface LibKstat
/*     */   extends Library
/*     */ {
/*  43 */   public static final LibKstat INSTANCE = (LibKstat)Native.loadLibrary("kstat", LibKstat.class);
/*     */   
/*     */   public static final byte KSTAT_TYPE_RAW = 0;
/*     */   
/*     */   public static final byte KSTAT_TYPE_NAMED = 1;
/*     */   
/*     */   public static final byte KSTAT_TYPE_INTR = 2;
/*     */   
/*     */   public static final byte KSTAT_TYPE_IO = 3;
/*     */   
/*     */   public static final byte KSTAT_TYPE_TIMER = 4;
/*     */   
/*     */   public static final byte KSTAT_DATA_CHAR = 0;
/*     */   
/*     */   public static final byte KSTAT_DATA_INT32 = 1;
/*     */   
/*     */   public static final byte KSTAT_DATA_UINT32 = 2;
/*     */   
/*     */   public static final byte KSTAT_DATA_INT64 = 3;
/*     */   
/*     */   public static final byte KSTAT_DATA_UINT64 = 4;
/*     */   
/*     */   public static final byte KSTAT_DATA_STRING = 9;
/*     */   
/*     */   public static final int KSTAT_INTR_HARD = 0;
/*     */   
/*     */   public static final int KSTAT_INTR_SOFT = 1;
/*     */   
/*     */   public static final int KSTAT_INTR_WATCHDOG = 2;
/*     */   
/*     */   public static final int KSTAT_INTR_SPURIOUS = 3;
/*     */   
/*     */   public static final int KSTAT_INTR_MULTSVC = 4;
/*     */   
/*     */   public static final int KSTAT_NUM_INTRS = 5;
/*     */   
/*     */   public static final int KSTAT_STRLEN = 31;
/*     */   
/*     */   public static final int EAGAIN = 11;
/*     */ 
/*     */   
/*     */   KstatCtl kstat_open();
/*     */ 
/*     */   
/*     */   int kstat_close(KstatCtl paramKstatCtl);
/*     */ 
/*     */   
/*     */   int kstat_chain_update(KstatCtl paramKstatCtl);
/*     */ 
/*     */   
/*     */   int kstat_read(KstatCtl paramKstatCtl, Kstat paramKstat, Pointer paramPointer);
/*     */ 
/*     */   
/*     */   int kstat_write(KstatCtl paramKstatCtl, Kstat paramKstat, Pointer paramPointer);
/*     */   
/*     */   Kstat kstat_lookup(KstatCtl paramKstatCtl, String paramString1, int paramInt, String paramString2);
/*     */   
/*     */   Pointer kstat_data_lookup(Kstat paramKstat, String paramString);
/*     */   
/*     */   public static class Kstat
/*     */     extends Structure
/*     */   {
/*     */     public long ks_crtime;
/*     */     public Pointer ks_next;
/*     */     public int ks_kid;
/* 108 */     public byte[] ks_module = new byte[31];
/*     */     
/*     */     public byte ks_resv;
/*     */     
/*     */     public int ks_instance;
/*     */     
/* 114 */     public byte[] ks_name = new byte[31];
/*     */     
/*     */     public byte ks_type;
/*     */     
/* 118 */     public byte[] ks_class = new byte[31];
/*     */     
/*     */     public byte ks_flags;
/*     */     
/*     */     public Pointer ks_data;
/*     */     
/*     */     public int ks_ndata;
/*     */     
/*     */     public long ks_data_size;
/*     */     
/*     */     public long ks_snaptime;
/*     */     
/*     */     public int ks_update;
/*     */     
/*     */     public Pointer ks_private;
/*     */     
/*     */     public int ks_snapshot;
/*     */     
/*     */     public Pointer ks_lock;
/*     */ 
/*     */     
/*     */     public Kstat next() {
/* 140 */       if (this.ks_next == null) {
/* 141 */         return null;
/*     */       }
/* 143 */       Kstat n = new Kstat();
/* 144 */       n.useMemory(this.ks_next);
/* 145 */       n.read();
/* 146 */       return n;
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 151 */       return Arrays.asList(new String[] { "ks_crtime", "ks_next", "ks_kid", "ks_module", "ks_resv", "ks_instance", "ks_name", "ks_type", "ks_class", "ks_flags", "ks_data", "ks_ndata", "ks_data_size", "ks_snaptime", "ks_update", "ks_private", "ks_snapshot", "ks_lock" });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class KstatNamed
/*     */     extends Structure
/*     */   {
/* 162 */     public byte[] name = new byte[31];
/*     */     public byte data_type;
/*     */     public UNION value;
/*     */     
/*     */     public KstatNamed() {}
/*     */     
/*     */     public static class UNION
/*     */       extends Union {
/* 170 */       public byte[] charc = new byte[16];
/*     */       
/*     */       public int i32;
/*     */       
/*     */       public int ui32;
/*     */       
/*     */       public long i64;
/*     */       
/*     */       public long ui64;
/*     */       
/*     */       public STR str;
/*     */       
/*     */       public static class STR
/*     */         extends Structure
/*     */       {
/*     */         public Pointer addr;
/*     */         public int len;
/*     */         
/*     */         protected List<String> getFieldOrder()
/*     */         {
/* 190 */           return Arrays.asList(new String[] { "addr", "len" }); } } } public static class STR extends Structure { protected List<String> getFieldOrder() { return Arrays.asList(new String[] { "addr", "len" }); }
/*     */ 
/*     */ 
/*     */       
/*     */       public Pointer addr;
/*     */       
/*     */       public int len; }
/*     */ 
/*     */     
/*     */     public KstatNamed(Pointer p) {
/* 200 */       super(p);
/* 201 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     public void read() {
/* 206 */       super.read();
/* 207 */       switch (this.data_type) {
/*     */         case 0:
/* 209 */           this.value.setType(byte[].class);
/*     */           break;
/*     */         case 9:
/* 212 */           this.value.setType(UNION.STR.class);
/*     */           break;
/*     */         case 1:
/*     */         case 2:
/* 216 */           this.value.setType(int.class);
/*     */           break;
/*     */         case 3:
/*     */         case 4:
/* 220 */           this.value.setType(long.class);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 225 */       this.value.read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 230 */       return Arrays.asList(new String[] { "name", "data_type", "value" });
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
/*     */   public static class KstatIntr
/*     */     extends Structure
/*     */   {
/* 245 */     public int[] intrs = new int[5];
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 249 */       return Arrays.asList(new String[] { "intrs" });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class KstatTimer
/*     */     extends Structure
/*     */   {
/* 259 */     public byte[] name = new byte[31];
/*     */     
/*     */     public byte resv;
/*     */     
/*     */     public long num_events;
/*     */     
/*     */     public long elapsed_time;
/*     */     
/*     */     public long min_time;
/*     */     
/*     */     public long max_time;
/*     */     
/*     */     public long start_time;
/*     */     
/*     */     public long stop_time;
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 277 */       return Arrays.asList(new String[] { "name", "resv", "num_events", "elapsed_time", "min_time", "max_time", "start_time", "stop_time" });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class KstatIO
/*     */     extends Structure
/*     */   {
/*     */     public long nread;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long nwritten;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int reads;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int writes;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long wtime;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long wlentime;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long wlastupdate;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long rtime;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long rlentime;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long rlastupdate;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int wcnt;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int rcnt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KstatIO() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KstatIO(Pointer p) {
/* 359 */       super(p);
/* 360 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 365 */       return Arrays.asList(new String[] { "nread", "nwritten", "reads", "writes", "wtime", "wlentime", "wlastupdate", "rtime", "rlentime", "rlastupdate", "wcnt", "rcnt" });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class KstatCtl
/*     */     extends Structure
/*     */   {
/*     */     public int kc_chain_id;
/*     */     
/*     */     public LibKstat.Kstat kc_chain;
/*     */     
/*     */     public int kc_kd;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 380 */       return Arrays.asList(new String[] { "kc_chain_id", "kc_chain", "kc_kd" });
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/unix/solaris/LibKstat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */