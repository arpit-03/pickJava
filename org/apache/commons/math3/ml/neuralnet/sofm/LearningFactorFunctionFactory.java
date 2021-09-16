/*     */ package org.apache.commons.math3.ml.neuralnet.sofm;
/*     */ 
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.ml.neuralnet.sofm.util.ExponentialDecayFunction;
/*     */ import org.apache.commons.math3.ml.neuralnet.sofm.util.QuasiSigmoidDecayFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LearningFactorFunctionFactory
/*     */ {
/*     */   public static LearningFactorFunction exponentialDecay(final double initValue, final double valueAtNumCall, final long numCall) {
/*  60 */     if (initValue <= 0.0D || initValue > 1.0D)
/*     */     {
/*  62 */       throw new OutOfRangeException(Double.valueOf(initValue), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/*  65 */     return new LearningFactorFunction()
/*     */       {
/*  67 */         private final ExponentialDecayFunction decay = new ExponentialDecayFunction(initValue, valueAtNumCall, numCall);
/*     */ 
/*     */ 
/*     */         
/*     */         public double value(long n) {
/*  72 */           return this.decay.value(n);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LearningFactorFunction quasiSigmoidDecay(final double initValue, final double slope, final long numCall) {
/* 101 */     if (initValue <= 0.0D || initValue > 1.0D)
/*     */     {
/* 103 */       throw new OutOfRangeException(Double.valueOf(initValue), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/* 106 */     return new LearningFactorFunction()
/*     */       {
/* 108 */         private final QuasiSigmoidDecayFunction decay = new QuasiSigmoidDecayFunction(initValue, slope, numCall);
/*     */ 
/*     */ 
/*     */         
/*     */         public double value(long n) {
/* 113 */           return this.decay.value(n);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/sofm/LearningFactorFunctionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */