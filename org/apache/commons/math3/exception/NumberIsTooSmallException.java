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
/*    */ public class NumberIsTooSmallException
/*    */   extends MathIllegalNumberException
/*    */ {
/*    */   private static final long serialVersionUID = -6100997100383932834L;
/*    */   private final Number min;
/*    */   private final boolean boundIsAllowed;
/*    */   
/*    */   public NumberIsTooSmallException(Number wrong, Number min, boolean boundIsAllowed) {
/* 49 */     this(boundIsAllowed ? (Localizable)LocalizedFormats.NUMBER_TOO_SMALL : (Localizable)LocalizedFormats.NUMBER_TOO_SMALL_BOUND_EXCLUDED, wrong, min, boundIsAllowed);
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
/*    */   
/*    */   public NumberIsTooSmallException(Localizable specific, Number wrong, Number min, boolean boundIsAllowed) {
/* 67 */     super(specific, wrong, new Object[] { min });
/*    */     
/* 69 */     this.min = min;
/* 70 */     this.boundIsAllowed = boundIsAllowed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getBoundIsAllowed() {
/* 77 */     return this.boundIsAllowed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Number getMin() {
/* 84 */     return this.min;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/NumberIsTooSmallException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */