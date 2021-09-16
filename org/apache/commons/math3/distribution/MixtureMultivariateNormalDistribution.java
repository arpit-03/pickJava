/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.util.Pair;
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
/*     */ public class MixtureMultivariateNormalDistribution
/*     */   extends MixtureMultivariateRealDistribution<MultivariateNormalDistribution>
/*     */ {
/*     */   public MixtureMultivariateNormalDistribution(double[] weights, double[][] means, double[][][] covariances) {
/*  55 */     super(createComponents(weights, means, covariances));
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
/*     */   public MixtureMultivariateNormalDistribution(List<Pair<Double, MultivariateNormalDistribution>> components) {
/*  73 */     super(components);
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
/*     */   public MixtureMultivariateNormalDistribution(RandomGenerator rng, List<Pair<Double, MultivariateNormalDistribution>> components) throws NotPositiveException, DimensionMismatchException {
/*  89 */     super(rng, components);
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
/*     */   private static List<Pair<Double, MultivariateNormalDistribution>> createComponents(double[] weights, double[][] means, double[][][] covariances) {
/* 101 */     List<Pair<Double, MultivariateNormalDistribution>> mvns = new ArrayList<Pair<Double, MultivariateNormalDistribution>>(weights.length);
/*     */ 
/*     */     
/* 104 */     for (int i = 0; i < weights.length; i++) {
/* 105 */       MultivariateNormalDistribution dist = new MultivariateNormalDistribution(means[i], covariances[i]);
/*     */ 
/*     */       
/* 108 */       mvns.add(new Pair(Double.valueOf(weights[i]), dist));
/*     */     } 
/*     */     
/* 111 */     return mvns;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/MixtureMultivariateNormalDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */