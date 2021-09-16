/*    */ package org.joda.time.tz;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ import org.joda.time.DateTimeZone;
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
/*    */ public final class UTCProvider
/*    */   implements Provider
/*    */ {
/* 36 */   private static final Set<String> AVAILABLE_IDS = Collections.singleton("UTC");
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
/*    */   public DateTimeZone getZone(String paramString) {
/* 50 */     if ("UTC".equalsIgnoreCase(paramString)) {
/* 51 */       return DateTimeZone.UTC;
/*    */     }
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getAvailableIDs() {
/* 60 */     return AVAILABLE_IDS;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/tz/UTCProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */