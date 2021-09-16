/*    */ package org.apache.commons.math3.exception.util;
/*    */ 
/*    */ import java.util.Locale;
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
/*    */ public class DummyLocalizable
/*    */   implements Localizable
/*    */ {
/*    */   private static final long serialVersionUID = 8843275624471387299L;
/*    */   private final String source;
/*    */   
/*    */   public DummyLocalizable(String source) {
/* 38 */     this.source = source;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSourceString() {
/* 43 */     return this.source;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLocalizedString(Locale locale) {
/* 48 */     return this.source;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return this.source;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/util/DummyLocalizable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */