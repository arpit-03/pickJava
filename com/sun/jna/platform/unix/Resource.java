/*     */ package com.sun.jna.platform.unix;
/*     */ 
/*     */ import com.sun.jna.Structure;
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
/*     */ public interface Resource
/*     */ {
/*     */   public static final int RLIMIT_CPU = 0;
/*     */   public static final int RLIMIT_FSIZE = 1;
/*     */   public static final int RLIMIT_DATA = 2;
/*     */   public static final int RLIMIT_STACK = 3;
/*     */   public static final int RLIMIT_CORE = 4;
/*     */   public static final int RLIMIT_RSS = 5;
/*     */   public static final int RLIMIT_NOFILE = 7;
/*     */   public static final int RLIMIT_AS = 9;
/*     */   public static final int RLIMIT_NPROC = 6;
/*     */   public static final int RLIMIT_MEMLOCK = 8;
/*     */   public static final int RLIMIT_LOCKS = 10;
/*     */   public static final int RLIMIT_SIGPENDING = 11;
/*     */   public static final int RLIMIT_MSGQUEUE = 12;
/*     */   public static final int RLIMIT_NICE = 13;
/*     */   public static final int RLIMIT_RTPRIO = 14;
/*     */   public static final int RLIMIT_RTTIME = 15;
/*     */   public static final int RLIMIT_NLIMITS = 16;
/*     */   
/*     */   int getrlimit(int paramInt, Rlimit paramRlimit);
/*     */   
/*     */   int setrlimit(int paramInt, Rlimit paramRlimit);
/*     */   
/*     */   public static class Rlimit
/*     */     extends Structure
/*     */   {
/*  96 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "rlim_cur", "rlim_max" });
/*     */ 
/*     */     
/*     */     public long rlim_cur;
/*     */ 
/*     */     
/*     */     public long rlim_max;
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 106 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/unix/Resource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */