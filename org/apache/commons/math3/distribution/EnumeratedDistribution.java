/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NotANumberException;
/*     */ import org.apache.commons.math3.exception.NotFiniteNumberException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.util.MathArrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnumeratedDistribution<T>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20123308L;
/*     */   protected final RandomGenerator random;
/*     */   private final List<T> singletons;
/*     */   private final double[] probabilities;
/*     */   private final double[] cumulativeProbabilities;
/*     */   
/*     */   public EnumeratedDistribution(List<Pair<T, Double>> pmf) throws NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
/* 101 */     this((RandomGenerator)new Well19937c(), pmf);
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
/*     */   public EnumeratedDistribution(RandomGenerator rng, List<Pair<T, Double>> pmf) throws NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
/* 118 */     this.random = rng;
/*     */     
/* 120 */     this.singletons = new ArrayList<T>(pmf.size());
/* 121 */     double[] probs = new double[pmf.size()];
/*     */     
/* 123 */     for (int i = 0; i < pmf.size(); i++) {
/* 124 */       Pair<T, Double> sample = pmf.get(i);
/* 125 */       this.singletons.add((T)sample.getKey());
/* 126 */       double p = ((Double)sample.getValue()).doubleValue();
/* 127 */       if (p < 0.0D) {
/* 128 */         throw new NotPositiveException((Number)sample.getValue());
/*     */       }
/* 130 */       if (Double.isInfinite(p)) {
/* 131 */         throw new NotFiniteNumberException(Double.valueOf(p), new Object[0]);
/*     */       }
/* 133 */       if (Double.isNaN(p)) {
/* 134 */         throw new NotANumberException();
/*     */       }
/* 136 */       probs[i] = p;
/*     */     } 
/*     */     
/* 139 */     this.probabilities = MathArrays.normalizeArray(probs, 1.0D);
/*     */     
/* 141 */     this.cumulativeProbabilities = new double[this.probabilities.length];
/* 142 */     double sum = 0.0D;
/* 143 */     for (int j = 0; j < this.probabilities.length; j++) {
/* 144 */       sum += this.probabilities[j];
/* 145 */       this.cumulativeProbabilities[j] = sum;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reseedRandomGenerator(long seed) {
/* 155 */     this.random.setSeed(seed);
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
/*     */   double probability(T x) {
/* 171 */     double probability = 0.0D;
/*     */     
/* 173 */     for (int i = 0; i < this.probabilities.length; i++) {
/* 174 */       if ((x == null && this.singletons.get(i) == null) || (x != null && x.equals(this.singletons.get(i))))
/*     */       {
/* 176 */         probability += this.probabilities[i];
/*     */       }
/*     */     } 
/*     */     
/* 180 */     return probability;
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
/*     */   public List<Pair<T, Double>> getPmf() {
/* 194 */     List<Pair<T, Double>> samples = new ArrayList<Pair<T, Double>>(this.probabilities.length);
/*     */     
/* 196 */     for (int i = 0; i < this.probabilities.length; i++) {
/* 197 */       samples.add(new Pair(this.singletons.get(i), Double.valueOf(this.probabilities[i])));
/*     */     }
/*     */     
/* 200 */     return samples;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T sample() {
/* 209 */     double randomValue = this.random.nextDouble();
/*     */     
/* 211 */     int index = Arrays.binarySearch(this.cumulativeProbabilities, randomValue);
/* 212 */     if (index < 0) {
/* 213 */       index = -index - 1;
/*     */     }
/*     */     
/* 216 */     if (index >= 0 && index < this.probabilities.length && randomValue < this.cumulativeProbabilities[index])
/*     */     {
/*     */       
/* 219 */       return this.singletons.get(index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     return this.singletons.get(this.singletons.size() - 1);
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
/*     */   public Object[] sample(int sampleSize) throws NotStrictlyPositiveException {
/* 237 */     if (sampleSize <= 0) {
/* 238 */       throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES, Integer.valueOf(sampleSize));
/*     */     }
/*     */ 
/*     */     
/* 242 */     Object[] out = new Object[sampleSize];
/*     */     
/* 244 */     for (int i = 0; i < sampleSize; i++) {
/* 245 */       out[i] = sample();
/*     */     }
/*     */     
/* 248 */     return out;
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
/*     */   public T[] sample(int sampleSize, T[] array) throws NotStrictlyPositiveException {
/*     */     T[] out;
/* 266 */     if (sampleSize <= 0) {
/* 267 */       throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES, Integer.valueOf(sampleSize));
/*     */     }
/*     */     
/* 270 */     if (array == null) {
/* 271 */       throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 275 */     if (array.length < sampleSize) {
/*     */       
/* 277 */       T[] unchecked = (T[])Array.newInstance(array.getClass().getComponentType(), sampleSize);
/* 278 */       out = unchecked;
/*     */     } else {
/* 280 */       out = array;
/*     */     } 
/*     */     
/* 283 */     for (int i = 0; i < sampleSize; i++) {
/* 284 */       out[i] = sample();
/*     */     }
/*     */     
/* 287 */     return out;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/EnumeratedDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */