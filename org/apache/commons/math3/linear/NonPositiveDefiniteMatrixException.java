/*    */ package org.apache.commons.math3.linear;
/*    */ 
/*    */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*    */ import org.apache.commons.math3.exception.util.ExceptionContext;
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
/*    */ public class NonPositiveDefiniteMatrixException
/*    */   extends NumberIsTooSmallException
/*    */ {
/*    */   private static final long serialVersionUID = 1641613838113738061L;
/*    */   private final int index;
/*    */   private final double threshold;
/*    */   
/*    */   public NonPositiveDefiniteMatrixException(double wrong, int index, double threshold) {
/* 46 */     super(Double.valueOf(wrong), Double.valueOf(threshold), false);
/* 47 */     this.index = index;
/* 48 */     this.threshold = threshold;
/*    */     
/* 50 */     ExceptionContext context = getContext();
/* 51 */     context.addMessage((Localizable)LocalizedFormats.NOT_POSITIVE_DEFINITE_MATRIX, new Object[0]);
/* 52 */     context.addMessage((Localizable)LocalizedFormats.ARRAY_ELEMENT, new Object[] { Double.valueOf(wrong), Integer.valueOf(index) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRow() {
/* 59 */     return this.index;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getColumn() {
/* 65 */     return this.index;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getThreshold() {
/* 71 */     return this.threshold;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/NonPositiveDefiniteMatrixException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */