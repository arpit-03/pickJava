/*    */ package org.apache.commons.math3.exception;
/*    */ 
/*    */ import org.apache.commons.math3.exception.util.Localizable;
/*    */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NumberIsTooLargeException
/*    */   extends MathIllegalNumberException
/*    */ {
/*    */   private static final long serialVersionUID = 4330003017885151975L;
/*    */   private final Number max;
/*    */   private final boolean boundIsAllowed;
/*    */   
/*    */   public NumberIsTooLargeException(Number wrong, Number max, boolean boundIsAllowed) {
/* 49 */     this(boundIsAllowed ? (Localizable)LocalizedFormats.NUMBER_TOO_LARGE : (Localizable)LocalizedFormats.NUMBER_TOO_LARGE_BOUND_EXCLUDED, wrong, max, boundIsAllowed);
/*    */   }
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
/*    */   public NumberIsTooLargeException(Localizable specific, Number wrong, Number max, boolean boundIsAllowed) {
/* 66 */     super(specific, wrong, new Object[] { max });
/*    */     
/* 68 */     this.max = max;
/* 69 */     this.boundIsAllowed = boundIsAllowed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getBoundIsAllowed() {
/* 76 */     return this.boundIsAllowed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Number getMax() {
/* 83 */     return this.max;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/NumberIsTooLargeException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */