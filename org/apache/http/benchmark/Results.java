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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Results
/*     */ {
/*     */   String serverName;
/*     */   String hostName;
/*     */   int hostPort;
/*     */   String documentPath;
/*  53 */   long contentLength = -1L; int concurrencyLevel; long totalTimeNano; long successCount;
/*     */   long failureCount;
/*     */   
/*     */   public String getServerName() {
/*  57 */     return this.serverName;
/*     */   }
/*     */   long writeErrors; long keepAliveCount; long totalBytesRcvd; long totalBytesSent; long totalBytes;
/*     */   public String getHostName() {
/*  61 */     return this.hostName;
/*     */   }
/*     */   
/*     */   public int getHostPort() {
/*  65 */     return this.hostPort;
/*     */   }
/*     */   
/*     */   public String getDocumentPath() {
/*  69 */     return this.documentPath;
/*     */   }
/*     */   
/*     */   public long getContentLength() {
/*  73 */     return this.contentLength;
/*     */   }
/*     */   
/*     */   public int getConcurrencyLevel() {
/*  77 */     return this.concurrencyLevel;
/*     */   }
/*     */   
/*     */   public long getTotalTimeNano() {
/*  81 */     return this.totalTimeNano;
/*     */   }
/*     */   
/*     */   public long getSuccessCount() {
/*  85 */     return this.successCount;
/*     */   }
/*     */   
/*     */   public long getFailureCount() {
/*  89 */     return this.failureCount;
/*     */   }
/*     */   
/*     */   public long getWriteErrors() {
/*  93 */     return this.writeErrors;
/*     */   }
/*     */   
/*     */   public long getKeepAliveCount() {
/*  97 */     return this.keepAliveCount;
/*     */   }
/*     */   
/*     */   public long getTotalBytesRcvd() {
/* 101 */     return this.totalBytesRcvd;
/*     */   }
/*     */   
/*     */   public long getTotalBytesSent() {
/* 105 */     return this.totalBytesSent;
/*     */   }
/*     */   
/*     */   public long getTotalBytes() {
/* 109 */     return this.totalBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     StringBuilder builder = new StringBuilder();
/* 115 */     builder.append("[serverName=").append(this.serverName).append(", hostName=").append(this.hostName).append(", hostPort=").append(this.hostPort).append(", documentPath=").append(this.documentPath).append(", contentLength=").append(this.contentLength).append(", concurrencyLevel=").append(this.concurrencyLevel).append(", totalTimeNano=").append(this.totalTimeNano).append(", successCount=").append(this.successCount).append(", failureCount=").append(this.failureCount).append(", writeErrors=").append(this.writeErrors).append(", keepAliveCount=").append(this.keepAliveCount).append(", totalBytesRcvd=").append(this.totalBytesRcvd).append(", totalBytesSent=").append(this.totalBytesSent).append(", totalBytes=").append(this.totalBytes).append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/benchmark/Results.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */