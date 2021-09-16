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
/*     */ public class EnumeratedIntegerDistribution
/*     */   extends AbstractIntegerDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 20130308L;
/*     */   protected final EnumeratedDistribution<Integer> innerDistribution;
/*     */   
/*     */   public EnumeratedIntegerDistribution(int[] singletons, double[] probabilities) throws DimensionMismatchException, NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
/*  78 */     this((RandomGenerator)new Well19937c(), singletons, probabilities);
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
/*     */   public EnumeratedIntegerDistribution(RandomGenerator rng, int[] singletons, double[] probabilities) throws DimensionMismatchException, NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
/*  99 */     super(rng);
/* 100 */     this.innerDistribution = new EnumeratedDistribution<Integer>(rng, createDistribution(singletons, probabilities));
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
/*     */   public EnumeratedIntegerDistribution(RandomGenerator rng, int[] data) {
/* 113 */     super(rng);
/* 114 */     Map<Integer, Integer> dataMap = new HashMap<Integer, Integer>();
/* 115 */     for (int value : data) {
/* 116 */       Integer count = dataMap.get(Integer.valueOf(value));
/* 117 */       if (count == null) {
/* 118 */         count = Integer.valueOf(0);
/*     */       }
/* 120 */       dataMap.put(Integer.valueOf(value), count = Integer.valueOf(count.intValue() + 1));
/*     */     } 
/* 122 */     int massPoints = dataMap.size();
/* 123 */     double denom = data.length;
/* 124 */     int[] values = new int[massPoints];
/* 125 */     double[] probabilities = new double[massPoints];
/* 126 */     int index = 0;
/* 127 */     for (Map.Entry<Integer, Integer> entry : dataMap.entrySet()) {
/* 128 */       values[index] = ((Integer)entry.getKey()).intValue();
/* 129 */       probabilities[index] = ((Integer)entry.getValue()).intValue() / denom;
/* 130 */       index++;
/*     */     } 
/* 132 */     this.innerDistribution = new EnumeratedDistribution<Integer>(rng, createDistribution(values, probabilities));
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
/*     */   public EnumeratedIntegerDistribution(int[] data) {
/* 144 */     this((RandomGenerator)new Well19937c(), data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Pair<Integer, Double>> createDistribution(int[] singletons, double[] probabilities) {
/* 155 */     if (singletons.length != probabilities.length) {
/* 156 */       throw new DimensionMismatchException(probabilities.length, singletons.length);
/*     */     }
/*     */     
/* 159 */     List<Pair<Integer, Double>> samples = new ArrayList<Pair<Integer, Double>>(singletons.length);
/*     */     
/* 161 */     for (int i = 0; i < singletons.length; i++) {
/* 162 */       samples.add(new Pair(Integer.valueOf(singletons[i]), Double.valueOf(probabilities[i])));
/*     */     }
/* 164 */     return samples;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double probability(int x) {
/* 172 */     return this.innerDistribution.probability(Integer.valueOf(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(int x) {
/* 179 */     double probability = 0.0D;
/*     */     
/* 181 */     for (Pair<Integer, Double> sample : this.innerDistribution.getPmf()) {
/* 182 */       if (((Integer)sample.getKey()).intValue() <= x) {
/* 183 */         probability += ((Double)sample.getValue()).doubleValue();
/*     */       }
/*     */     } 
/*     */     
/* 187 */     return probability;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 196 */     double mean = 0.0D;
/*     */     
/* 198 */     for (Pair<Integer, Double> sample : this.innerDistribution.getPmf()) {
/* 199 */       mean += ((Double)sample.getValue()).doubleValue() * ((Integer)sample.getKey()).intValue();
/*     */     }
/*     */     
/* 202 */     return mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 211 */     double mean = 0.0D;
/* 212 */     double meanOfSquares = 0.0D;
/*     */     
/* 214 */     for (Pair<Integer, Double> sample : this.innerDistribution.getPmf()) {
/* 215 */       mean += ((Double)sample.getValue()).doubleValue() * ((Integer)sample.getKey()).intValue();
/* 216 */       meanOfSquares += ((Double)sample.getValue()).doubleValue() * ((Integer)sample.getKey()).intValue() * ((Integer)sample.getKey()).intValue();
/*     */     } 
/*     */     
/* 219 */     return meanOfSquares - mean * mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSupportLowerBound() {
/* 230 */     int min = Integer.MAX_VALUE;
/* 231 */     for (Pair<Integer, Double> sample : this.innerDistribution.getPmf()) {
/* 232 */       if (((Integer)sample.getKey()).intValue() < min && ((Double)sample.getValue()).doubleValue() > 0.0D) {
/* 233 */         min = ((Integer)sample.getKey()).intValue();
/*     */       }
/*     */     } 
/*     */     
/* 237 */     return min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSupportUpperBound() {
/* 248 */     int max = Integer.MIN_VALUE;
/* 249 */     for (Pair<Integer, Double> sample : this.innerDistribution.getPmf()) {
/* 250 */       if (((Integer)sample.getKey()).intValue() > max && ((Double)sample.getValue()).doubleValue() > 0.0D) {
/* 251 */         max = ((Integer)sample.getKey()).intValue();
/*     */       }
/*     */     } 
/*     */     
/* 255 */     return max;
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
/* 266 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sample() {
/* 274 */     return ((Integer)this.innerDistribution.sample()).intValue();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/EnumeratedIntegerDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */