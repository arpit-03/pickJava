/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.security.SecureRandom;
/*    */ import java.util.Formatter;
/*    */ import java.util.Locale;
/*    */ import org.apache.http.annotation.Contract;
/*    */ import org.apache.http.annotation.ThreadingBehavior;
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
/*    */ @Contract(threading = ThreadingBehavior.SAFE)
/*    */ class BasicIdGenerator
/*    */ {
/*    */   private final String hostname;
/*    */   private final SecureRandom rnd;
/*    */   private long count;
/*    */   
/*    */   public BasicIdGenerator() {
/*    */     String str;
/*    */     try {
/* 53 */       str = InetAddress.getLocalHost().getHostName();
/* 54 */     } catch (UnknownHostException ex) {
/* 55 */       str = "localhost";
/*    */     } 
/* 57 */     this.hostname = str;
/*    */     try {
/* 59 */       this.rnd = SecureRandom.getInstance("SHA1PRNG");
/* 60 */     } catch (NoSuchAlgorithmException ex) {
/* 61 */       throw new Error(ex);
/*    */     } 
/* 63 */     this.rnd.setSeed(System.currentTimeMillis());
/*    */   }
/*    */   
/*    */   public synchronized void generate(StringBuilder buffer) {
/* 67 */     this.count++;
/* 68 */     int rndnum = this.rnd.nextInt();
/* 69 */     buffer.append(System.currentTimeMillis());
/* 70 */     buffer.append('.');
/* 71 */     Formatter formatter = new Formatter(buffer, Locale.US);
/* 72 */     formatter.format("%1$016x-%2$08x", new Object[] { Long.valueOf(this.count), Integer.valueOf(rndnum) });
/* 73 */     formatter.close();
/* 74 */     buffer.append('.');
/* 75 */     buffer.append(this.hostname);
/*    */   }
/*    */   
/*    */   public String generate() {
/* 79 */     StringBuilder buffer = new StringBuilder();
/* 80 */     generate(buffer);
/* 81 */     return buffer.toString();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/BasicIdGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */