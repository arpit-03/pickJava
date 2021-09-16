/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.math3.distribution.BetaDistribution;
/*     */ import org.apache.commons.math3.distribution.BinomialDistribution;
/*     */ import org.apache.commons.math3.distribution.CauchyDistribution;
/*     */ import org.apache.commons.math3.distribution.ChiSquaredDistribution;
/*     */ import org.apache.commons.math3.distribution.ExponentialDistribution;
/*     */ import org.apache.commons.math3.distribution.FDistribution;
/*     */ import org.apache.commons.math3.distribution.GammaDistribution;
/*     */ import org.apache.commons.math3.distribution.HypergeometricDistribution;
/*     */ import org.apache.commons.math3.distribution.PascalDistribution;
/*     */ import org.apache.commons.math3.distribution.PoissonDistribution;
/*     */ import org.apache.commons.math3.distribution.TDistribution;
/*     */ import org.apache.commons.math3.distribution.UniformIntegerDistribution;
/*     */ import org.apache.commons.math3.distribution.WeibullDistribution;
/*     */ import org.apache.commons.math3.distribution.ZipfDistribution;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.NotANumberException;
/*     */ import org.apache.commons.math3.exception.NotFiniteNumberException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomDataGenerator
/*     */   implements RandomData, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -626730818244969716L;
/* 118 */   private RandomGenerator rand = null;
/*     */ 
/*     */   
/* 121 */   private RandomGenerator secRand = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomDataGenerator() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomDataGenerator(RandomGenerator rand) {
/* 142 */     this.rand = rand;
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
/*     */   public String nextHexString(int len) throws NotStrictlyPositiveException {
/* 162 */     if (len <= 0) {
/* 163 */       throw new NotStrictlyPositiveException(LocalizedFormats.LENGTH, Integer.valueOf(len));
/*     */     }
/*     */ 
/*     */     
/* 167 */     RandomGenerator ran = getRandomGenerator();
/*     */ 
/*     */     
/* 170 */     StringBuilder outBuffer = new StringBuilder();
/*     */ 
/*     */     
/* 173 */     byte[] randomBytes = new byte[len / 2 + 1];
/* 174 */     ran.nextBytes(randomBytes);
/*     */ 
/*     */     
/* 177 */     for (int i = 0; i < randomBytes.length; i++) {
/* 178 */       Integer c = Integer.valueOf(randomBytes[i]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 185 */       String hex = Integer.toHexString(c.intValue() + 128);
/*     */ 
/*     */       
/* 188 */       if (hex.length() == 1) {
/* 189 */         hex = "0" + hex;
/*     */       }
/* 191 */       outBuffer.append(hex);
/*     */     } 
/* 193 */     return outBuffer.toString().substring(0, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt(int lower, int upper) throws NumberIsTooLargeException {
/* 198 */     return (new UniformIntegerDistribution(getRandomGenerator(), lower, upper)).sample();
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong(long lower, long upper) throws NumberIsTooLargeException {
/* 203 */     if (lower >= upper) {
/* 204 */       throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Long.valueOf(lower), Long.valueOf(upper), false);
/*     */     }
/*     */     
/* 207 */     long max = upper - lower + 1L;
/* 208 */     if (max <= 0L) {
/*     */ 
/*     */       
/* 211 */       RandomGenerator rng = getRandomGenerator();
/*     */       while (true) {
/* 213 */         long r = rng.nextLong();
/* 214 */         if (r >= lower && r <= upper)
/* 215 */           return r; 
/*     */       } 
/*     */     } 
/* 218 */     if (max < 2147483647L)
/*     */     {
/* 220 */       return lower + getRandomGenerator().nextInt((int)max);
/*     */     }
/*     */     
/* 223 */     return lower + nextLong(getRandomGenerator(), max);
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
/*     */   private static long nextLong(RandomGenerator rng, long n) throws IllegalArgumentException {
/* 240 */     if (n > 0L) {
/* 241 */       byte[] byteArray = new byte[8];
/*     */ 
/*     */       
/*     */       while (true) {
/* 245 */         rng.nextBytes(byteArray);
/* 246 */         long bits = 0L;
/* 247 */         for (byte b : byteArray) {
/* 248 */           bits = bits << 8L | b & 0xFFL;
/*     */         }
/* 250 */         bits &= Long.MAX_VALUE;
/* 251 */         long val = bits % n;
/* 252 */         if (bits - val + n - 1L >= 0L)
/* 253 */           return val; 
/*     */       } 
/* 255 */     }  throw new NotStrictlyPositiveException(Long.valueOf(n));
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
/*     */   public String nextSecureHexString(int len) throws NotStrictlyPositiveException {
/* 276 */     if (len <= 0) {
/* 277 */       throw new NotStrictlyPositiveException(LocalizedFormats.LENGTH, Integer.valueOf(len));
/*     */     }
/*     */ 
/*     */     
/* 281 */     RandomGenerator secRan = getSecRan();
/* 282 */     MessageDigest alg = null;
/*     */     try {
/* 284 */       alg = MessageDigest.getInstance("SHA-1");
/* 285 */     } catch (NoSuchAlgorithmException ex) {
/*     */       
/* 287 */       throw new MathInternalError(ex);
/*     */     } 
/* 289 */     alg.reset();
/*     */ 
/*     */     
/* 292 */     int numIter = len / 40 + 1;
/*     */     
/* 294 */     StringBuilder outBuffer = new StringBuilder();
/* 295 */     for (int iter = 1; iter < numIter + 1; iter++) {
/* 296 */       byte[] randomBytes = new byte[40];
/* 297 */       secRan.nextBytes(randomBytes);
/* 298 */       alg.update(randomBytes);
/*     */ 
/*     */       
/* 301 */       byte[] hash = alg.digest();
/*     */ 
/*     */       
/* 304 */       for (int i = 0; i < hash.length; i++) {
/* 305 */         Integer c = Integer.valueOf(hash[i]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 312 */         String hex = Integer.toHexString(c.intValue() + 128);
/*     */ 
/*     */         
/* 315 */         if (hex.length() == 1) {
/* 316 */           hex = "0" + hex;
/*     */         }
/* 318 */         outBuffer.append(hex);
/*     */       } 
/*     */     } 
/* 321 */     return outBuffer.toString().substring(0, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextSecureInt(int lower, int upper) throws NumberIsTooLargeException {
/* 326 */     return (new UniformIntegerDistribution(getSecRan(), lower, upper)).sample();
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextSecureLong(long lower, long upper) throws NumberIsTooLargeException {
/* 331 */     if (lower >= upper) {
/* 332 */       throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Long.valueOf(lower), Long.valueOf(upper), false);
/*     */     }
/*     */     
/* 335 */     RandomGenerator rng = getSecRan();
/* 336 */     long max = upper - lower + 1L;
/* 337 */     if (max <= 0L)
/*     */     {
/*     */       while (true) {
/*     */         
/* 341 */         long r = rng.nextLong();
/* 342 */         if (r >= lower && r <= upper)
/* 343 */           return r; 
/*     */       } 
/*     */     }
/* 346 */     if (max < 2147483647L)
/*     */     {
/* 348 */       return lower + rng.nextInt((int)max);
/*     */     }
/*     */     
/* 351 */     return lower + nextLong(rng, max);
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
/*     */   public long nextPoisson(double mean) throws NotStrictlyPositiveException {
/* 370 */     return (new PoissonDistribution(getRandomGenerator(), mean, 1.0E-12D, 10000000)).sample();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextGaussian(double mu, double sigma) throws NotStrictlyPositiveException {
/* 377 */     if (sigma <= 0.0D) {
/* 378 */       throw new NotStrictlyPositiveException(LocalizedFormats.STANDARD_DEVIATION, Double.valueOf(sigma));
/*     */     }
/* 380 */     return sigma * getRandomGenerator().nextGaussian() + mu;
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
/*     */   public double nextExponential(double mean) throws NotStrictlyPositiveException {
/* 395 */     return (new ExponentialDistribution(getRandomGenerator(), mean, 1.0E-9D)).sample();
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
/*     */   public double nextGamma(double shape, double scale) throws NotStrictlyPositiveException {
/* 422 */     return (new GammaDistribution(getRandomGenerator(), shape, scale, 1.0E-9D)).sample();
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
/*     */   public int nextHypergeometric(int populationSize, int numberOfSuccesses, int sampleSize) throws NotPositiveException, NotStrictlyPositiveException, NumberIsTooLargeException {
/* 439 */     return (new HypergeometricDistribution(getRandomGenerator(), populationSize, numberOfSuccesses, sampleSize)).sample();
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
/*     */   public int nextPascal(int r, double p) throws NotStrictlyPositiveException, OutOfRangeException {
/* 454 */     return (new PascalDistribution(getRandomGenerator(), r, p)).sample();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextT(double df) throws NotStrictlyPositiveException {
/* 465 */     return (new TDistribution(getRandomGenerator(), df, 1.0E-9D)).sample();
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
/*     */   public double nextWeibull(double shape, double scale) throws NotStrictlyPositiveException {
/* 479 */     return (new WeibullDistribution(getRandomGenerator(), shape, scale, 1.0E-9D)).sample();
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
/*     */   public int nextZipf(int numberOfElements, double exponent) throws NotStrictlyPositiveException {
/* 493 */     return (new ZipfDistribution(getRandomGenerator(), numberOfElements, exponent)).sample();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextBeta(double alpha, double beta) {
/* 504 */     return (new BetaDistribution(getRandomGenerator(), alpha, beta, 1.0E-9D)).sample();
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
/*     */   public int nextBinomial(int numberOfTrials, double probabilityOfSuccess) {
/* 516 */     return (new BinomialDistribution(getRandomGenerator(), numberOfTrials, probabilityOfSuccess)).sample();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextCauchy(double median, double scale) {
/* 527 */     return (new CauchyDistribution(getRandomGenerator(), median, scale, 1.0E-9D)).sample();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextChiSquare(double df) {
/* 538 */     return (new ChiSquaredDistribution(getRandomGenerator(), df, 1.0E-9D)).sample();
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
/*     */   public double nextF(double numeratorDf, double denominatorDf) throws NotStrictlyPositiveException {
/* 552 */     return (new FDistribution(getRandomGenerator(), numeratorDf, denominatorDf, 1.0E-9D)).sample();
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
/*     */   public double nextUniform(double lower, double upper) throws NumberIsTooLargeException, NotFiniteNumberException, NotANumberException {
/* 571 */     return nextUniform(lower, upper, false);
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
/*     */   public double nextUniform(double lower, double upper, boolean lowerInclusive) throws NumberIsTooLargeException, NotFiniteNumberException, NotANumberException {
/* 592 */     if (lower >= upper) {
/* 593 */       throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Double.valueOf(lower), Double.valueOf(upper), false);
/*     */     }
/*     */ 
/*     */     
/* 597 */     if (Double.isInfinite(lower)) {
/* 598 */       throw new NotFiniteNumberException(LocalizedFormats.INFINITE_BOUND, Double.valueOf(lower), new Object[0]);
/*     */     }
/* 600 */     if (Double.isInfinite(upper)) {
/* 601 */       throw new NotFiniteNumberException(LocalizedFormats.INFINITE_BOUND, Double.valueOf(upper), new Object[0]);
/*     */     }
/*     */     
/* 604 */     if (Double.isNaN(lower) || Double.isNaN(upper)) {
/* 605 */       throw new NotANumberException();
/*     */     }
/*     */     
/* 608 */     RandomGenerator generator = getRandomGenerator();
/*     */ 
/*     */     
/* 611 */     double u = generator.nextDouble();
/* 612 */     while (!lowerInclusive && u <= 0.0D) {
/* 613 */       u = generator.nextDouble();
/*     */     }
/*     */     
/* 616 */     return u * upper + (1.0D - u) * lower;
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
/*     */   public int[] nextPermutation(int n, int k) throws NumberIsTooLargeException, NotStrictlyPositiveException {
/* 631 */     if (k > n) {
/* 632 */       throw new NumberIsTooLargeException(LocalizedFormats.PERMUTATION_EXCEEDS_N, Integer.valueOf(k), Integer.valueOf(n), true);
/*     */     }
/*     */     
/* 635 */     if (k <= 0) {
/* 636 */       throw new NotStrictlyPositiveException(LocalizedFormats.PERMUTATION_SIZE, Integer.valueOf(k));
/*     */     }
/*     */ 
/*     */     
/* 640 */     int[] index = MathArrays.natural(n);
/* 641 */     MathArrays.shuffle(index, getRandomGenerator());
/*     */ 
/*     */     
/* 644 */     return MathArrays.copyOf(index, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] nextSample(Collection<?> c, int k) throws NumberIsTooLargeException, NotStrictlyPositiveException {
/* 655 */     int len = c.size();
/* 656 */     if (k > len) {
/* 657 */       throw new NumberIsTooLargeException(LocalizedFormats.SAMPLE_SIZE_EXCEEDS_COLLECTION_SIZE, Integer.valueOf(k), Integer.valueOf(len), true);
/*     */     }
/*     */     
/* 660 */     if (k <= 0) {
/* 661 */       throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES, Integer.valueOf(k));
/*     */     }
/*     */     
/* 664 */     Object[] objects = c.toArray();
/* 665 */     int[] index = nextPermutation(len, k);
/* 666 */     Object[] result = new Object[k];
/* 667 */     for (int i = 0; i < k; i++) {
/* 668 */       result[i] = objects[index[i]];
/*     */     }
/* 670 */     return result;
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
/*     */   public void reSeed(long seed) {
/* 684 */     getRandomGenerator().setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reSeedSecure() {
/* 695 */     getSecRan().setSeed(System.currentTimeMillis());
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
/*     */   public void reSeedSecure(long seed) {
/* 707 */     getSecRan().setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reSeed() {
/* 715 */     getRandomGenerator().setSeed(System.currentTimeMillis() + System.identityHashCode(this));
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
/*     */   public void setSecureAlgorithm(String algorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 736 */     this.secRand = RandomGeneratorFactory.createRandomGenerator(SecureRandom.getInstance(algorithm, provider));
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
/*     */   public RandomGenerator getRandomGenerator() {
/* 751 */     if (this.rand == null) {
/* 752 */       initRan();
/*     */     }
/* 754 */     return this.rand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initRan() {
/* 762 */     this.rand = new Well19937c(System.currentTimeMillis() + System.identityHashCode(this));
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
/*     */   private RandomGenerator getSecRan() {
/* 776 */     if (this.secRand == null) {
/* 777 */       this.secRand = RandomGeneratorFactory.createRandomGenerator(new SecureRandom());
/* 778 */       this.secRand.setSeed(System.currentTimeMillis() + System.identityHashCode(this));
/*     */     } 
/* 780 */     return this.secRand;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/RandomDataGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */