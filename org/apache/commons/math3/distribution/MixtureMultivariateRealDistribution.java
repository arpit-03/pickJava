/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
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
/*     */ public class MixtureMultivariateRealDistribution<T extends MultivariateRealDistribution>
/*     */   extends AbstractMultivariateRealDistribution
/*     */ {
/*     */   private final double[] weight;
/*     */   private final List<T> distribution;
/*     */   
/*     */   public MixtureMultivariateRealDistribution(List<Pair<Double, T>> components) {
/*  59 */     this((RandomGenerator)new Well19937c(), components);
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
/*     */   public MixtureMultivariateRealDistribution(RandomGenerator rng, List<Pair<Double, T>> components) {
/*  74 */     super(rng, ((MultivariateRealDistribution)((Pair)components.get(0)).getSecond()).getDimension());
/*     */     
/*  76 */     int numComp = components.size();
/*  77 */     int dim = getDimension();
/*  78 */     double weightSum = 0.0D; int i;
/*  79 */     for (i = 0; i < numComp; i++) {
/*  80 */       Pair<Double, T> comp = components.get(i);
/*  81 */       if (((MultivariateRealDistribution)comp.getSecond()).getDimension() != dim) {
/*  82 */         throw new DimensionMismatchException(((MultivariateRealDistribution)comp.getSecond()).getDimension(), dim);
/*     */       }
/*  84 */       if (((Double)comp.getFirst()).doubleValue() < 0.0D) {
/*  85 */         throw new NotPositiveException((Number)comp.getFirst());
/*     */       }
/*  87 */       weightSum += ((Double)comp.getFirst()).doubleValue();
/*     */     } 
/*     */ 
/*     */     
/*  91 */     if (Double.isInfinite(weightSum)) {
/*  92 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  96 */     this.distribution = new ArrayList<T>();
/*  97 */     this.weight = new double[numComp];
/*  98 */     for (i = 0; i < numComp; i++) {
/*  99 */       Pair<Double, T> comp = components.get(i);
/* 100 */       this.weight[i] = ((Double)comp.getFirst()).doubleValue() / weightSum;
/* 101 */       this.distribution.add((T)comp.getSecond());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double[] values) {
/* 107 */     double p = 0.0D;
/* 108 */     for (int i = 0; i < this.weight.length; i++) {
/* 109 */       p += this.weight[i] * ((MultivariateRealDistribution)this.distribution.get(i)).density(values);
/*     */     }
/* 111 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] sample() {
/* 118 */     double[] vals = null;
/*     */ 
/*     */     
/* 121 */     double randomValue = this.random.nextDouble();
/* 122 */     double sum = 0.0D;
/*     */     
/* 124 */     for (int i = 0; i < this.weight.length; i++) {
/* 125 */       sum += this.weight[i];
/* 126 */       if (randomValue <= sum) {
/*     */         
/* 128 */         vals = ((MultivariateRealDistribution)this.distribution.get(i)).sample();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 133 */     if (vals == null)
/*     */     {
/*     */ 
/*     */       
/* 137 */       vals = ((MultivariateRealDistribution)this.distribution.get(this.weight.length - 1)).sample();
/*     */     }
/*     */     
/* 140 */     return vals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reseedRandomGenerator(long seed) {
/* 148 */     super.reseedRandomGenerator(seed);
/*     */     
/* 150 */     for (int i = 0; i < this.distribution.size(); i++)
/*     */     {
/*     */       
/* 153 */       ((MultivariateRealDistribution)this.distribution.get(i)).reseedRandomGenerator((i + 1) + seed);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Pair<Double, T>> getComponents() {
/* 163 */     List<Pair<Double, T>> list = new ArrayList<Pair<Double, T>>(this.weight.length);
/*     */     
/* 165 */     for (int i = 0; i < this.weight.length; i++) {
/* 166 */       list.add(new Pair(Double.valueOf(this.weight[i]), this.distribution.get(i)));
/*     */     }
/*     */     
/* 169 */     return list;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/MixtureMultivariateRealDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */