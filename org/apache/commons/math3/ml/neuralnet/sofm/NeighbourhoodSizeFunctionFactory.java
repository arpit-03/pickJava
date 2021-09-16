/*     */ package org.apache.commons.math3.ml.neuralnet.sofm;
/*     */ 
/*     */ import org.apache.commons.math3.ml.neuralnet.sofm.util.ExponentialDecayFunction;
/*     */ import org.apache.commons.math3.ml.neuralnet.sofm.util.QuasiSigmoidDecayFunction;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public class NeighbourhoodSizeFunctionFactory
/*     */ {
/*     */   public static NeighbourhoodSizeFunction exponentialDecay(final double initValue, final double valueAtNumCall, final long numCall) {
/*  60 */     return new NeighbourhoodSizeFunction()
/*     */       {
/*  62 */         private final ExponentialDecayFunction decay = new ExponentialDecayFunction(initValue, valueAtNumCall, numCall);
/*     */ 
/*     */ 
/*     */         
/*     */         public int value(long n) {
/*  67 */           return (int)FastMath.rint(this.decay.value(n));
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
/*     */   public static NeighbourhoodSizeFunction quasiSigmoidDecay(final double initValue, final double slope, final long numCall) {
/*  96 */     return new NeighbourhoodSizeFunction()
/*     */       {
/*  98 */         private final QuasiSigmoidDecayFunction decay = new QuasiSigmoidDecayFunction(initValue, slope, numCall);
/*     */ 
/*     */ 
/*     */         
/*     */         public int value(long n) {
/* 103 */           return (int)FastMath.rint(this.decay.value(n));
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/sofm/NeighbourhoodSizeFunctionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */