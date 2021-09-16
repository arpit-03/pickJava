/*    */ package org.apache.commons.math3.ml.neuralnet.sofm.util;
/*    */ 
/*    */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*    */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*    */ import org.apache.commons.math3.util.FastMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExponentialDecayFunction
/*    */ {
/*    */   private final double a;
/*    */   private final double oneOverB;
/*    */   
/*    */   public ExponentialDecayFunction(double initValue, double valueAtNumCall, long numCall) {
/* 57 */     if (initValue <= 0.0D) {
/* 58 */       throw new NotStrictlyPositiveException(Double.valueOf(initValue));
/*    */     }
/* 60 */     if (valueAtNumCall <= 0.0D) {
/* 61 */       throw new NotStrictlyPositiveException(Double.valueOf(valueAtNumCall));
/*    */     }
/* 63 */     if (valueAtNumCall >= initValue) {
/* 64 */       throw new NumberIsTooLargeException(Double.valueOf(valueAtNumCall), Double.valueOf(initValue), false);
/*    */     }
/* 66 */     if (numCall <= 0L) {
/* 67 */       throw new NotStrictlyPositiveException(Long.valueOf(numCall));
/*    */     }
/*    */     
/* 70 */     this.a = initValue;
/* 71 */     this.oneOverB = -FastMath.log(valueAtNumCall / initValue) / numCall;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double value(long numCall) {
/* 81 */     return this.a * FastMath.exp(-numCall * this.oneOverB);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/sofm/util/ExponentialDecayFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */