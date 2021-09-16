/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.math3.distribution.IntegerDistribution;
/*     */ import org.apache.commons.math3.distribution.RealDistribution;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NotANumberException;
/*     */ import org.apache.commons.math3.exception.NotFiniteNumberException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class RandomDataImpl
/*     */   implements RandomData, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -626730818244969716L;
/*     */   private final RandomDataGenerator delegate;
/*     */   
/*     */   public RandomDataImpl() {
/* 114 */     this.delegate = new RandomDataGenerator();
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
/*     */   public RandomDataImpl(RandomGenerator rand) {
/* 126 */     this.delegate = new RandomDataGenerator(rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   RandomDataGenerator getDelegate() {
/* 135 */     return this.delegate;
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
/* 155 */     return this.delegate.nextHexString(len);
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt(int lower, int upper) throws NumberIsTooLargeException {
/* 160 */     return this.delegate.nextInt(lower, upper);
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong(long lower, long upper) throws NumberIsTooLargeException {
/* 165 */     return this.delegate.nextLong(lower, upper);
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
/*     */   public String nextSecureHexString(int len) throws NotStrictlyPositiveException {
/* 185 */     return this.delegate.nextSecureHexString(len);
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextSecureInt(int lower, int upper) throws NumberIsTooLargeException {
/* 190 */     return this.delegate.nextSecureInt(lower, upper);
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextSecureLong(long lower, long upper) throws NumberIsTooLargeException {
/* 195 */     return this.delegate.nextSecureLong(lower, upper);
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
/*     */   public long nextPoisson(double mean) throws NotStrictlyPositiveException {
/* 212 */     return this.delegate.nextPoisson(mean);
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextGaussian(double mu, double sigma) throws NotStrictlyPositiveException {
/* 217 */     return this.delegate.nextGaussian(mu, sigma);
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
/* 232 */     return this.delegate.nextExponential(mean);
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
/*     */   public double nextUniform(double lower, double upper) throws NumberIsTooLargeException, NotFiniteNumberException, NotANumberException {
/* 247 */     return this.delegate.nextUniform(lower, upper);
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
/*     */   public double nextUniform(double lower, double upper, boolean lowerInclusive) throws NumberIsTooLargeException, NotFiniteNumberException, NotANumberException {
/* 264 */     return this.delegate.nextUniform(lower, upper, lowerInclusive);
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
/*     */   public double nextBeta(double alpha, double beta) {
/* 278 */     return this.delegate.nextBeta(alpha, beta);
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
/*     */   public int nextBinomial(int numberOfTrials, double probabilityOfSuccess) {
/* 292 */     return this.delegate.nextBinomial(numberOfTrials, probabilityOfSuccess);
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
/*     */   public double nextCauchy(double median, double scale) {
/* 306 */     return this.delegate.nextCauchy(median, scale);
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
/*     */   public double nextChiSquare(double df) {
/* 319 */     return this.delegate.nextChiSquare(df);
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
/*     */   public double nextF(double numeratorDf, double denominatorDf) throws NotStrictlyPositiveException {
/* 335 */     return this.delegate.nextF(numeratorDf, denominatorDf);
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
/* 362 */     return this.delegate.nextGamma(shape, scale);
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
/*     */   public int nextHypergeometric(int populationSize, int numberOfSuccesses, int sampleSize) throws NotPositiveException, NotStrictlyPositiveException, NumberIsTooLargeException {
/* 382 */     return this.delegate.nextHypergeometric(populationSize, numberOfSuccesses, sampleSize);
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
/*     */   public int nextPascal(int r, double p) throws NotStrictlyPositiveException, OutOfRangeException {
/* 400 */     return this.delegate.nextPascal(r, p);
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
/*     */   public double nextT(double df) throws NotStrictlyPositiveException {
/* 414 */     return this.delegate.nextT(df);
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
/*     */   public double nextWeibull(double shape, double scale) throws NotStrictlyPositiveException {
/* 430 */     return this.delegate.nextWeibull(shape, scale);
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
/*     */   public int nextZipf(int numberOfElements, double exponent) throws NotStrictlyPositiveException {
/* 446 */     return this.delegate.nextZipf(numberOfElements, exponent);
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
/* 460 */     this.delegate.reSeed(seed);
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
/* 471 */     this.delegate.reSeedSecure();
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
/*     */   public void reSeedSecure(long seed) {
/* 484 */     this.delegate.reSeedSecure(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reSeed() {
/* 492 */     this.delegate.reSeed();
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
/*     */   public void setSecureAlgorithm(String algorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 517 */     this.delegate.setSecureAlgorithm(algorithm, provider);
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
/*     */   public int[] nextPermutation(int n, int k) throws NotStrictlyPositiveException, NumberIsTooLargeException {
/* 531 */     return this.delegate.nextPermutation(n, k);
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
/*     */   public Object[] nextSample(Collection<?> c, int k) throws NotStrictlyPositiveException, NumberIsTooLargeException {
/* 549 */     return this.delegate.nextSample(c, k);
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
/*     */   @Deprecated
/*     */   public double nextInversionDeviate(RealDistribution distribution) throws MathIllegalArgumentException {
/* 565 */     return distribution.inverseCumulativeProbability(nextUniform(0.0D, 1.0D));
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
/*     */   @Deprecated
/*     */   public int nextInversionDeviate(IntegerDistribution distribution) throws MathIllegalArgumentException {
/* 582 */     return distribution.inverseCumulativeProbability(nextUniform(0.0D, 1.0D));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/RandomDataImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */