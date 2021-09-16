/*    */ package org.apache.commons.math3.ml.neuralnet.sofm.util;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.function.Logistic;
/*    */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*    */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
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
/*    */ public class QuasiSigmoidDecayFunction
/*    */ {
/*    */   private final Logistic sigmoid;
/*    */   private final double scale;
/*    */   
/*    */   public QuasiSigmoidDecayFunction(double initValue, double slope, long numCall) {
/* 56 */     if (initValue <= 0.0D) {
/* 57 */       throw new NotStrictlyPositiveException(Double.valueOf(initValue));
/*    */     }
/* 59 */     if (slope >= 0.0D) {
/* 60 */       throw new NumberIsTooLargeException(Double.valueOf(slope), Integer.valueOf(0), false);
/*    */     }
/* 62 */     if (numCall <= 1L) {
/* 63 */       throw new NotStrictlyPositiveException(Long.valueOf(numCall));
/*    */     }
/*    */     
/* 66 */     double k = initValue;
/* 67 */     double m = numCall;
/* 68 */     double b = 4.0D * slope / initValue;
/* 69 */     double q = 1.0D;
/* 70 */     double a = 0.0D;
/* 71 */     double n = 1.0D;
/* 72 */     this.sigmoid = new Logistic(k, m, b, 1.0D, 0.0D, 1.0D);
/*    */     
/* 74 */     double y0 = this.sigmoid.value(0.0D);
/* 75 */     this.scale = k / y0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double value(long numCall) {
/* 85 */     return this.scale * this.sigmoid.value(numCall);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/sofm/util/QuasiSigmoidDecayFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */