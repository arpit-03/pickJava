/*     */ package org.apache.commons.math3.ml.neuralnet;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.function.Constant;
/*     */ import org.apache.commons.math3.distribution.RealDistribution;
/*     */ import org.apache.commons.math3.distribution.UniformRealDistribution;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
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
/*     */ public class FeatureInitializerFactory
/*     */ {
/*     */   public static FeatureInitializer uniform(RandomGenerator rng, double min, double max) {
/*  51 */     return randomize((RealDistribution)new UniformRealDistribution(rng, min, max), function((UnivariateFunction)new Constant(0.0D), 0.0D, 0.0D));
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
/*     */   public static FeatureInitializer uniform(double min, double max) {
/*  67 */     return randomize((RealDistribution)new UniformRealDistribution(min, max), function((UnivariateFunction)new Constant(0.0D), 0.0D, 0.0D));
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
/*     */   public static FeatureInitializer function(final UnivariateFunction f, final double init, final double inc) {
/*  84 */     return new FeatureInitializer()
/*     */       {
/*  86 */         private double arg = init;
/*     */ 
/*     */         
/*     */         public double value() {
/*  90 */           double result = f.value(this.arg);
/*  91 */           this.arg += inc;
/*  92 */           return result;
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
/*     */   public static FeatureInitializer randomize(final RealDistribution random, final FeatureInitializer orig) {
/* 107 */     return new FeatureInitializer()
/*     */       {
/*     */         public double value() {
/* 110 */           return orig.value() + random.sample();
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/FeatureInitializerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */