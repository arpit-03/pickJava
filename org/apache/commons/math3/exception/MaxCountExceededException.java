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
/*    */ public class MaxCountExceededException
/*    */   extends MathIllegalStateException
/*    */ {
/*    */   private static final long serialVersionUID = 4330003017885151975L;
/*    */   private final Number max;
/*    */   
/*    */   public MaxCountExceededException(Number max) {
/* 41 */     this((Localizable)LocalizedFormats.MAX_COUNT_EXCEEDED, max, new Object[0]);
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
/*    */   public MaxCountExceededException(Localizable specific, Number max, Object... args) {
/* 53 */     getContext().addMessage(specific, new Object[] { max, args });
/* 54 */     this.max = max;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Number getMax() {
/* 61 */     return this.max;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/MaxCountExceededException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */