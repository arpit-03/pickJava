/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NotANumberException;
/*     */ import org.apache.commons.math3.exception.NotFiniteNumberException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
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
/*     */ public class EnumeratedRealDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 20130308L;
/*     */   protected final EnumeratedDistribution<Double> innerDistribution;
/*     */   
/*     */   public EnumeratedRealDistribution(double[] singletons, double[] probabilities) throws DimensionMismatchException, NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
/*  79 */     this((RandomGenerator)new Well19937c(), singletons, probabilities);
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
/*     */   public EnumeratedRealDistribution(RandomGenerator rng, double[] singletons, double[] probabilities) throws DimensionMismatchException, NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
/* 100 */     super(rng);
/*     */     
/* 102 */     this.innerDistribution = new EnumeratedDistribution<Double>(rng, createDistribution(singletons, probabilities));
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
/*     */   public EnumeratedRealDistribution(RandomGenerator rng, double[] data) {
/* 115 */     super(rng);
/* 116 */     Map<Double, Integer> dataMap = new HashMap<Double, Integer>();
/* 117 */     for (double value : data) {
/* 118 */       Integer count = dataMap.get(Double.valueOf(value));
/* 119 */       if (count == null) {
/* 120 */         count = Integer.valueOf(0);
/*     */       }
/* 122 */       dataMap.put(Double.valueOf(value), count = Integer.valueOf(count.intValue() + 1));
/*     */     } 
/* 124 */     int massPoints = dataMap.size();
/* 125 */     double denom = data.length;
/* 126 */     double[] values = new double[massPoints];
/* 127 */     double[] probabilities = new double[massPoints];
/* 128 */     int index = 0;
/* 129 */     for (Map.Entry<Double, Integer> entry : dataMap.entrySet()) {
/* 130 */       values[index] = ((Double)entry.getKey()).doubleValue();
/* 131 */       probabilities[index] = ((Integer)entry.getValue()).intValue() / denom;
/* 132 */       index++;
/*     */     } 
/* 134 */     this.innerDistribution = new EnumeratedDistribution<Double>(rng, createDistribution(values, probabilities));
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
/*     */   public EnumeratedRealDistribution(double[] data) {
/* 146 */     this((RandomGenerator)new Well19937c(), data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Pair<Double, Double>> createDistribution(double[] singletons, double[] probabilities) {
/* 156 */     if (singletons.length != probabilities.length) {
/* 157 */       throw new DimensionMismatchException(probabilities.length, singletons.length);
/*     */     }
/*     */     
/* 160 */     List<Pair<Double, Double>> samples = new ArrayList<Pair<Double, Double>>(singletons.length);
/*     */     
/* 162 */     for (int i = 0; i < singletons.length; i++) {
/* 163 */       samples.add(new Pair(Double.valueOf(singletons[i]), Double.valueOf(probabilities[i])));
/*     */     }
/* 165 */     return samples;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double probability(double x) {
/* 174 */     return this.innerDistribution.probability(Double.valueOf(x));
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
/*     */   public double density(double x) {
/* 187 */     return probability(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/* 194 */     double probability = 0.0D;
/*     */     
/* 196 */     for (Pair<Double, Double> sample : this.innerDistribution.getPmf()) {
/* 197 */       if (((Double)sample.getKey()).doubleValue() <= x) {
/* 198 */         probability += ((Double)sample.getValue()).doubleValue();
/*     */       }
/*     */     } 
/*     */     
/* 202 */     return probability;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 210 */     if (p < 0.0D || p > 1.0D) {
/* 211 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/* 214 */     double probability = 0.0D;
/* 215 */     double x = getSupportLowerBound();
/* 216 */     for (Pair<Double, Double> sample : this.innerDistribution.getPmf()) {
/* 217 */       if (((Double)sample.getValue()).doubleValue() == 0.0D) {
/*     */         continue;
/*     */       }
/*     */       
/* 221 */       probability += ((Double)sample.getValue()).doubleValue();
/* 222 */       x = ((Double)sample.getKey()).doubleValue();
/*     */       
/* 224 */       if (probability >= p) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 229 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 238 */     double mean = 0.0D;
/*     */     
/* 240 */     for (Pair<Double, Double> sample : this.innerDistribution.getPmf()) {
/* 241 */       mean += ((Double)sample.getValue()).doubleValue() * ((Double)sample.getKey()).doubleValue();
/*     */     }
/*     */     
/* 244 */     return mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 253 */     double mean = 0.0D;
/* 254 */     double meanOfSquares = 0.0D;
/*     */     
/* 256 */     for (Pair<Double, Double> sample : this.innerDistribution.getPmf()) {
/* 257 */       mean += ((Double)sample.getValue()).doubleValue() * ((Double)sample.getKey()).doubleValue();
/* 258 */       meanOfSquares += ((Double)sample.getValue()).doubleValue() * ((Double)sample.getKey()).doubleValue() * ((Double)sample.getKey()).doubleValue();
/*     */     } 
/*     */     
/* 261 */     return meanOfSquares - mean * mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSupportLowerBound() {
/* 272 */     double min = Double.POSITIVE_INFINITY;
/* 273 */     for (Pair<Double, Double> sample : this.innerDistribution.getPmf()) {
/* 274 */       if (((Double)sample.getKey()).doubleValue() < min && ((Double)sample.getValue()).doubleValue() > 0.0D) {
/* 275 */         min = ((Double)sample.getKey()).doubleValue();
/*     */       }
/*     */     } 
/*     */     
/* 279 */     return min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSupportUpperBound() {
/* 290 */     double max = Double.NEGATIVE_INFINITY;
/* 291 */     for (Pair<Double, Double> sample : this.innerDistribution.getPmf()) {
/* 292 */       if (((Double)sample.getKey()).doubleValue() > max && ((Double)sample.getValue()).doubleValue() > 0.0D) {
/* 293 */         max = ((Double)sample.getKey()).doubleValue();
/*     */       }
/*     */     } 
/*     */     
/* 297 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 308 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 319 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportConnected() {
/* 330 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double sample() {
/* 338 */     return ((Double)this.innerDistribution.sample()).doubleValue();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/EnumeratedRealDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */