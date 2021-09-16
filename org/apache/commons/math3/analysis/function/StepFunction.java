/*    */ package org.apache.commons.math3.analysis.function;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*    */ import org.apache.commons.math3.exception.NoDataException;
/*    */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*    */ import org.apache.commons.math3.exception.NullArgumentException;
/*    */ import org.apache.commons.math3.util.MathArrays;
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
/*    */ public class StepFunction
/*    */   implements UnivariateFunction
/*    */ {
/*    */   private final double[] abscissa;
/*    */   private final double[] ordinate;
/*    */   
/*    */   public StepFunction(double[] x, double[] y) throws NullArgumentException, NoDataException, DimensionMismatchException, NonMonotonicSequenceException {
/* 65 */     if (x == null || y == null)
/*    */     {
/* 67 */       throw new NullArgumentException();
/*    */     }
/* 69 */     if (x.length == 0 || y.length == 0)
/*    */     {
/* 71 */       throw new NoDataException();
/*    */     }
/* 73 */     if (y.length != x.length) {
/* 74 */       throw new DimensionMismatchException(y.length, x.length);
/*    */     }
/* 76 */     MathArrays.checkOrder(x);
/*    */     
/* 78 */     this.abscissa = MathArrays.copyOf(x);
/* 79 */     this.ordinate = MathArrays.copyOf(y);
/*    */   }
/*    */ 
/*    */   
/*    */   public double value(double x) {
/* 84 */     int index = Arrays.binarySearch(this.abscissa, x);
/* 85 */     double fx = 0.0D;
/*    */     
/* 87 */     if (index < -1) {
/*    */       
/* 89 */       fx = this.ordinate[-index - 2];
/* 90 */     } else if (index >= 0) {
/*    */       
/* 92 */       fx = this.ordinate[index];
/*    */     }
/*    */     else {
/*    */       
/* 96 */       fx = this.ordinate[0];
/*    */     } 
/*    */     
/* 99 */     return fx;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/StepFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */