/*     */ package org.apache.http.benchmark;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import org.apache.http.HttpHost;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResultProcessor
/*     */ {
/*  35 */   static NumberFormat nf2 = NumberFormat.getInstance();
/*  36 */   static NumberFormat nf3 = NumberFormat.getInstance();
/*  37 */   static NumberFormat nf6 = NumberFormat.getInstance();
/*     */   
/*     */   static {
/*  40 */     nf2.setMaximumFractionDigits(2);
/*  41 */     nf2.setMinimumFractionDigits(2);
/*  42 */     nf3.setMaximumFractionDigits(3);
/*  43 */     nf3.setMinimumFractionDigits(3);
/*  44 */     nf6.setMaximumFractionDigits(6);
/*  45 */     nf6.setMinimumFractionDigits(6);
/*     */   }
/*     */   
/*     */   static Results collectResults(BenchmarkWorker[] workers, HttpHost host, String uri) {
/*  49 */     long totalTimeNano = 0L;
/*  50 */     long successCount = 0L;
/*  51 */     long failureCount = 0L;
/*  52 */     long writeErrors = 0L;
/*  53 */     long keepAliveCount = 0L;
/*  54 */     long totalBytesRcvd = 0L;
/*  55 */     long totalBytesSent = 0L;
/*     */     
/*  57 */     Stats stats = workers[0].getStats();
/*     */     
/*  59 */     for (BenchmarkWorker worker : workers) {
/*  60 */       Stats s = worker.getStats();
/*  61 */       totalTimeNano += s.getDuration();
/*  62 */       successCount += s.getSuccessCount();
/*  63 */       failureCount += s.getFailureCount();
/*  64 */       writeErrors += s.getWriteErrors();
/*  65 */       keepAliveCount += s.getKeepAliveCount();
/*  66 */       totalBytesRcvd += s.getTotalBytesRecv();
/*  67 */       totalBytesSent += s.getTotalBytesSent();
/*     */     } 
/*     */     
/*  70 */     Results results = new Results();
/*  71 */     results.serverName = stats.getServerName();
/*  72 */     results.hostName = host.getHostName();
/*  73 */     results.hostPort = (host.getPort() > 0) ? host.getPort() : (host.getSchemeName().equalsIgnoreCase("https") ? 443 : 80);
/*     */     
/*  75 */     results.documentPath = uri;
/*  76 */     results.contentLength = stats.getContentLength();
/*  77 */     results.concurrencyLevel = workers.length;
/*  78 */     results.totalTimeNano = totalTimeNano;
/*  79 */     results.successCount = successCount;
/*  80 */     results.failureCount = failureCount;
/*  81 */     results.writeErrors = writeErrors;
/*  82 */     results.keepAliveCount = keepAliveCount;
/*  83 */     results.totalBytesRcvd = totalBytesRcvd;
/*  84 */     results.totalBytesSent = totalBytesSent;
/*  85 */     results.totalBytes = totalBytesRcvd + ((totalBytesSent > 0L) ? totalBytesSent : 0L);
/*  86 */     return results;
/*     */   }
/*     */   
/*     */   static void printResults(Results results) {
/*  90 */     int threads = results.getConcurrencyLevel();
/*  91 */     double totalTimeMs = (results.getTotalTimeNano() / threads / 1000000L);
/*  92 */     double timePerReqMs = totalTimeMs / results.getSuccessCount();
/*  93 */     double totalTimeSec = totalTimeMs / 1000.0D;
/*  94 */     double reqsPerSec = results.getSuccessCount() / totalTimeSec;
/*     */     
/*  96 */     System.out.println("\nServer Software:\t\t" + results.getServerName());
/*  97 */     System.out.println("Server Hostname:\t\t" + results.getHostName());
/*  98 */     System.out.println("Server Port:\t\t\t" + Integer.valueOf(results.getHostPort()));
/*  99 */     System.out.println("Document Path:\t\t\t" + results.getDocumentPath());
/* 100 */     System.out.println("Document Length:\t\t" + results.getContentLength() + " bytes\n");
/* 101 */     System.out.println("Concurrency Level:\t\t" + results.getConcurrencyLevel());
/* 102 */     System.out.println("Time taken for tests:\t\t" + nf6.format(totalTimeSec) + " seconds");
/* 103 */     System.out.println("Complete requests:\t\t" + results.getSuccessCount());
/* 104 */     System.out.println("Failed requests:\t\t" + results.getFailureCount());
/* 105 */     System.out.println("Write errors:\t\t\t" + results.getWriteErrors());
/* 106 */     System.out.println("Kept alive:\t\t\t" + results.getKeepAliveCount());
/* 107 */     System.out.println("Total transferred:\t\t" + results.getTotalBytes() + " bytes");
/* 108 */     System.out.println("Requests per second:\t\t" + nf2.format(reqsPerSec) + " [#/sec] (mean)");
/* 109 */     System.out.println("Time per request:\t\t" + nf3.format(timePerReqMs * results.getConcurrencyLevel()) + " [ms] (mean)");
/*     */     
/* 111 */     System.out.println("Time per request:\t\t" + nf3.format(timePerReqMs) + " [ms] (mean, across all concurrent requests)");
/*     */     
/* 113 */     System.out.println("Transfer rate:\t\t\t" + nf2.format((results.getTotalBytesRcvd() / 1000L) / totalTimeSec) + " [Kbytes/sec] received");
/*     */     
/* 115 */     System.out.println("\t\t\t\t" + ((results.getTotalBytesSent() > 0L) ? nf2.format((results.getTotalBytesSent() / 1000L) / totalTimeSec) : (String)Integer.valueOf(-1)) + " kb/s sent");
/*     */ 
/*     */     
/* 118 */     System.out.println("\t\t\t\t" + nf2.format((results.getTotalBytes() / 1000L) / totalTimeSec) + " kb/s total");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/benchmark/ResultProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */