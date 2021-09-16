/*    */ package org.apache.http.client.utils;
/*    */ 
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
/*    */ 
/*    */ @Deprecated
/*    */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*    */ public class Punycode
/*    */ {
/*    */   private static final Idn impl;
/*    */   
/*    */   static {
/*    */     Idn idn;
/*    */     try {
/* 47 */       idn = new JdkIdn();
/* 48 */     } catch (Exception e) {
/* 49 */       idn = new Rfc3492Idn();
/*    */     } 
/* 51 */     impl = idn;
/*    */   }
/*    */   
/*    */   public static String toUnicode(String punycode) {
/* 55 */     return impl.toUnicode(punycode);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/utils/Punycode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */