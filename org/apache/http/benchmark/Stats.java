/*     */ package org.apache.http.benchmark;
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
/*     */ public class Stats
/*     */ {
/*  37 */   private long startTime = -1L;
/*  38 */   private long finishTime = -1L;
/*  39 */   private int successCount = 0;
/*  40 */   private int failureCount = 0;
/*  41 */   private int writeErrors = 0;
/*  42 */   private int keepAliveCount = 0;
/*  43 */   private String serverName = null;
/*  44 */   private long totalBytesRecv = 0L;
/*  45 */   private long totalBytesSent = 0L;
/*  46 */   private long contentLength = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  53 */     this.startTime = System.nanoTime();
/*     */   }
/*     */   
/*     */   public void finish() {
/*  57 */     this.finishTime = System.nanoTime();
/*     */   }
/*     */   
/*     */   public long getFinishTime() {
/*  61 */     return this.finishTime;
/*     */   }
/*     */   
/*     */   public long getStartTime() {
/*  65 */     return this.startTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDuration() {
/*  76 */     return this.finishTime - this.startTime;
/*     */   }
/*     */   
/*     */   public void incSuccessCount() {
/*  80 */     this.successCount++;
/*     */   }
/*     */   
/*     */   public int getSuccessCount() {
/*  84 */     return this.successCount;
/*     */   }
/*     */   
/*     */   public void incFailureCount() {
/*  88 */     this.failureCount++;
/*     */   }
/*     */   
/*     */   public int getFailureCount() {
/*  92 */     return this.failureCount;
/*     */   }
/*     */   
/*     */   public void incWriteErrors() {
/*  96 */     this.writeErrors++;
/*     */   }
/*     */   
/*     */   public int getWriteErrors() {
/* 100 */     return this.writeErrors;
/*     */   }
/*     */   
/*     */   public void incKeepAliveCount() {
/* 104 */     this.keepAliveCount++;
/*     */   }
/*     */   
/*     */   public int getKeepAliveCount() {
/* 108 */     return this.keepAliveCount;
/*     */   }
/*     */   
/*     */   public long getTotalBytesRecv() {
/* 112 */     return this.totalBytesRecv;
/*     */   }
/*     */   
/*     */   public void incTotalBytesRecv(long n) {
/* 116 */     this.totalBytesRecv += n;
/*     */   }
/*     */   
/*     */   public long getTotalBytesSent() {
/* 120 */     return this.totalBytesSent;
/*     */   }
/*     */   
/*     */   public void incTotalBytesSent(long n) {
/* 124 */     this.totalBytesSent += n;
/*     */   }
/*     */   
/*     */   public long getContentLength() {
/* 128 */     return this.contentLength;
/*     */   }
/*     */   
/*     */   public void setContentLength(long contentLength) {
/* 132 */     this.contentLength = contentLength;
/*     */   }
/*     */   
/*     */   public String getServerName() {
/* 136 */     return this.serverName;
/*     */   }
/*     */   
/*     */   public void setServerName(String serverName) {
/* 140 */     this.serverName = serverName;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/benchmark/Stats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */