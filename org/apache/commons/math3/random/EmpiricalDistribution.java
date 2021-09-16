/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.distribution.AbstractRealDistribution;
/*     */ import org.apache.commons.math3.distribution.ConstantRealDistribution;
/*     */ import org.apache.commons.math3.distribution.NormalDistribution;
/*     */ import org.apache.commons.math3.distribution.RealDistribution;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.stat.descriptive.StatisticalSummary;
/*     */ import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmpiricalDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final int DEFAULT_BIN_COUNT = 1000;
/*     */   private static final String FILE_CHARSET = "US-ASCII";
/*     */   private static final long serialVersionUID = 5729073523949762654L;
/*     */   protected final RandomDataGenerator randomData;
/*     */   private final List<SummaryStatistics> binStats;
/* 121 */   private SummaryStatistics sampleStats = null;
/*     */ 
/*     */   
/* 124 */   private double max = Double.NEGATIVE_INFINITY;
/*     */ 
/*     */   
/* 127 */   private double min = Double.POSITIVE_INFINITY;
/*     */ 
/*     */   
/* 130 */   private double delta = 0.0D;
/*     */ 
/*     */   
/*     */   private final int binCount;
/*     */ 
/*     */   
/*     */   private boolean loaded = false;
/*     */ 
/*     */   
/* 139 */   private double[] upperBounds = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EmpiricalDistribution() {
/* 145 */     this(1000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EmpiricalDistribution(int binCount) {
/* 155 */     this(binCount, new RandomDataGenerator());
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
/*     */   public EmpiricalDistribution(int binCount, RandomGenerator generator) {
/* 168 */     this(binCount, new RandomDataGenerator(generator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EmpiricalDistribution(RandomGenerator generator) {
/* 179 */     this(1000, generator);
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
/*     */   @Deprecated
/*     */   public EmpiricalDistribution(int binCount, RandomDataImpl randomData) {
/* 193 */     this(binCount, randomData.getDelegate());
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
/*     */   @Deprecated
/*     */   public EmpiricalDistribution(RandomDataImpl randomData) {
/* 206 */     this(1000, randomData);
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
/*     */   private EmpiricalDistribution(int binCount, RandomDataGenerator randomData) {
/* 219 */     super(randomData.getRandomGenerator());
/* 220 */     if (binCount <= 0) {
/* 221 */       throw new NotStrictlyPositiveException(Integer.valueOf(binCount));
/*     */     }
/* 223 */     this.binCount = binCount;
/* 224 */     this.randomData = randomData;
/* 225 */     this.binStats = new ArrayList<SummaryStatistics>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(double[] in) throws NullArgumentException {
/* 236 */     DataAdapter da = new ArrayDataAdapter(in);
/*     */     try {
/* 238 */       da.computeStats();
/*     */       
/* 240 */       fillBinStats(new ArrayDataAdapter(in));
/* 241 */     } catch (IOException ex) {
/*     */       
/* 243 */       throw new MathInternalError();
/*     */     } 
/* 245 */     this.loaded = true;
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
/*     */   public void load(URL url) throws IOException, NullArgumentException, ZeroException {
/* 262 */     MathUtils.checkNotNull(url);
/* 263 */     Charset charset = Charset.forName("US-ASCII");
/* 264 */     BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), charset));
/*     */     
/*     */     try {
/* 267 */       DataAdapter da = new StreamDataAdapter(in);
/* 268 */       da.computeStats();
/* 269 */       if (this.sampleStats.getN() == 0L) {
/* 270 */         throw new ZeroException(LocalizedFormats.URL_CONTAINS_NO_DATA, new Object[] { url });
/*     */       }
/*     */       
/* 273 */       in = new BufferedReader(new InputStreamReader(url.openStream(), charset));
/* 274 */       fillBinStats(new StreamDataAdapter(in));
/* 275 */       this.loaded = true;
/*     */     } finally {
/*     */       try {
/* 278 */         in.close();
/* 279 */       } catch (IOException iOException) {}
/*     */     } 
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
/*     */   public void load(File file) throws IOException, NullArgumentException {
/* 296 */     MathUtils.checkNotNull(file);
/* 297 */     Charset charset = Charset.forName("US-ASCII");
/* 298 */     InputStream is = new FileInputStream(file);
/* 299 */     BufferedReader in = new BufferedReader(new InputStreamReader(is, charset));
/*     */     try {
/* 301 */       DataAdapter da = new StreamDataAdapter(in);
/* 302 */       da.computeStats();
/*     */       
/* 304 */       is = new FileInputStream(file);
/* 305 */       in = new BufferedReader(new InputStreamReader(is, charset));
/* 306 */       fillBinStats(new StreamDataAdapter(in));
/* 307 */       this.loaded = true;
/*     */     } finally {
/*     */       try {
/* 310 */         in.close();
/* 311 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private abstract class DataAdapter
/*     */   {
/*     */     private DataAdapter() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract void computeBinStats() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract void computeStats() throws IOException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class StreamDataAdapter
/*     */     extends DataAdapter
/*     */   {
/*     */     private BufferedReader inputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     StreamDataAdapter(BufferedReader in) {
/* 354 */       this.inputStream = in;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void computeBinStats() throws IOException {
/* 360 */       String str = null;
/* 361 */       double val = 0.0D;
/* 362 */       while ((str = this.inputStream.readLine()) != null) {
/* 363 */         val = Double.parseDouble(str);
/* 364 */         SummaryStatistics stats = EmpiricalDistribution.this.binStats.get(EmpiricalDistribution.this.findBin(val));
/* 365 */         stats.addValue(val);
/*     */       } 
/*     */       
/* 368 */       this.inputStream.close();
/* 369 */       this.inputStream = null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void computeStats() throws IOException {
/* 375 */       String str = null;
/* 376 */       double val = 0.0D;
/* 377 */       EmpiricalDistribution.this.sampleStats = new SummaryStatistics();
/* 378 */       while ((str = this.inputStream.readLine()) != null) {
/* 379 */         val = Double.parseDouble(str);
/* 380 */         EmpiricalDistribution.this.sampleStats.addValue(val);
/*     */       } 
/* 382 */       this.inputStream.close();
/* 383 */       this.inputStream = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ArrayDataAdapter
/*     */     extends DataAdapter
/*     */   {
/*     */     private double[] inputArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ArrayDataAdapter(double[] in) throws NullArgumentException {
/* 403 */       MathUtils.checkNotNull(in);
/* 404 */       this.inputArray = in;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void computeStats() throws IOException {
/* 410 */       EmpiricalDistribution.this.sampleStats = new SummaryStatistics();
/* 411 */       for (int i = 0; i < this.inputArray.length; i++) {
/* 412 */         EmpiricalDistribution.this.sampleStats.addValue(this.inputArray[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void computeBinStats() throws IOException {
/* 419 */       for (int i = 0; i < this.inputArray.length; i++) {
/* 420 */         SummaryStatistics stats = EmpiricalDistribution.this.binStats.get(EmpiricalDistribution.this.findBin(this.inputArray[i]));
/*     */         
/* 422 */         stats.addValue(this.inputArray[i]);
/*     */       } 
/*     */     }
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
/*     */   private void fillBinStats(DataAdapter da) throws IOException {
/* 436 */     this.min = this.sampleStats.getMin();
/* 437 */     this.max = this.sampleStats.getMax();
/* 438 */     this.delta = (this.max - this.min) / this.binCount;
/*     */ 
/*     */     
/* 441 */     if (!this.binStats.isEmpty())
/* 442 */       this.binStats.clear(); 
/*     */     int i;
/* 444 */     for (i = 0; i < this.binCount; i++) {
/* 445 */       SummaryStatistics stats = new SummaryStatistics();
/* 446 */       this.binStats.add(i, stats);
/*     */     } 
/*     */ 
/*     */     
/* 450 */     da.computeBinStats();
/*     */ 
/*     */     
/* 453 */     this.upperBounds = new double[this.binCount];
/* 454 */     this.upperBounds[0] = ((SummaryStatistics)this.binStats.get(0)).getN() / this.sampleStats.getN();
/*     */     
/* 456 */     for (i = 1; i < this.binCount - 1; i++) {
/* 457 */       this.upperBounds[i] = this.upperBounds[i - 1] + ((SummaryStatistics)this.binStats.get(i)).getN() / this.sampleStats.getN();
/*     */     }
/*     */     
/* 460 */     this.upperBounds[this.binCount - 1] = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int findBin(double value) {
/* 470 */     return FastMath.min(FastMath.max((int)FastMath.ceil((value - this.min) / this.delta) - 1, 0), this.binCount - 1);
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
/*     */   public double getNextValue() throws MathIllegalStateException {
/* 484 */     if (!this.loaded) {
/* 485 */       throw new MathIllegalStateException(LocalizedFormats.DISTRIBUTION_NOT_LOADED, new Object[0]);
/*     */     }
/*     */     
/* 488 */     return sample();
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
/*     */   public StatisticalSummary getSampleStats() {
/* 500 */     return (StatisticalSummary)this.sampleStats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBinCount() {
/* 509 */     return this.binCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<SummaryStatistics> getBinStats() {
/* 520 */     return this.binStats;
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
/*     */   public double[] getUpperBounds() {
/* 537 */     double[] binUpperBounds = new double[this.binCount];
/* 538 */     for (int i = 0; i < this.binCount - 1; i++) {
/* 539 */       binUpperBounds[i] = this.min + this.delta * (i + 1);
/*     */     }
/* 541 */     binUpperBounds[this.binCount - 1] = this.max;
/* 542 */     return binUpperBounds;
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
/*     */   public double[] getGeneratorUpperBounds() {
/* 562 */     int len = this.upperBounds.length;
/* 563 */     double[] out = new double[len];
/* 564 */     System.arraycopy(this.upperBounds, 0, out, 0, len);
/* 565 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLoaded() {
/* 574 */     return this.loaded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reSeed(long seed) {
/* 584 */     this.randomData.reSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double probability(double x) {
/* 595 */     return 0.0D;
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
/*     */   public double density(double x) {
/* 613 */     if (x < this.min || x > this.max) {
/* 614 */       return 0.0D;
/*     */     }
/* 616 */     int binIndex = findBin(x);
/* 617 */     RealDistribution kernel = getKernel(this.binStats.get(binIndex));
/* 618 */     return kernel.density(x) * pB(binIndex) / kB(binIndex);
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
/*     */   public double cumulativeProbability(double x) {
/* 637 */     if (x < this.min)
/* 638 */       return 0.0D; 
/* 639 */     if (x >= this.max) {
/* 640 */       return 1.0D;
/*     */     }
/* 642 */     int binIndex = findBin(x);
/* 643 */     double pBminus = pBminus(binIndex);
/* 644 */     double pB = pB(binIndex);
/* 645 */     RealDistribution kernel = k(x);
/* 646 */     if (kernel instanceof ConstantRealDistribution) {
/* 647 */       if (x < kernel.getNumericalMean()) {
/* 648 */         return pBminus;
/*     */       }
/* 650 */       return pBminus + pB;
/*     */     } 
/*     */     
/* 653 */     double[] binBounds = getUpperBounds();
/* 654 */     double kB = kB(binIndex);
/* 655 */     double lower = (binIndex == 0) ? this.min : binBounds[binIndex - 1];
/* 656 */     double withinBinCum = (kernel.cumulativeProbability(x) - kernel.cumulativeProbability(lower)) / kB;
/*     */     
/* 658 */     return pBminus + pB * withinBinCum;
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
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 683 */     if (p < 0.0D || p > 1.0D) {
/* 684 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/* 687 */     if (p == 0.0D) {
/* 688 */       return getSupportLowerBound();
/*     */     }
/*     */     
/* 691 */     if (p == 1.0D) {
/* 692 */       return getSupportUpperBound();
/*     */     }
/*     */     
/* 695 */     int i = 0;
/* 696 */     while (cumBinP(i) < p) {
/* 697 */       i++;
/*     */     }
/*     */     
/* 700 */     RealDistribution kernel = getKernel(this.binStats.get(i));
/* 701 */     double kB = kB(i);
/* 702 */     double[] binBounds = getUpperBounds();
/* 703 */     double lower = (i == 0) ? this.min : binBounds[i - 1];
/* 704 */     double kBminus = kernel.cumulativeProbability(lower);
/* 705 */     double pB = pB(i);
/* 706 */     double pBminus = pBminus(i);
/* 707 */     double pCrit = p - pBminus;
/* 708 */     if (pCrit <= 0.0D) {
/* 709 */       return lower;
/*     */     }
/* 711 */     return kernel.inverseCumulativeProbability(kBminus + pCrit * kB / pB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 719 */     return this.sampleStats.getMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 727 */     return this.sampleStats.getVariance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSupportLowerBound() {
/* 735 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSupportUpperBound() {
/* 743 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 751 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 759 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportConnected() {
/* 767 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reseedRandomGenerator(long seed) {
/* 776 */     this.randomData.reSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double pB(int i) {
/* 786 */     return (i == 0) ? this.upperBounds[0] : (this.upperBounds[i] - this.upperBounds[i - 1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double pBminus(int i) {
/* 797 */     return (i == 0) ? 0.0D : this.upperBounds[i - 1];
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
/*     */   private double kB(int i) {
/* 809 */     double[] binBounds = getUpperBounds();
/* 810 */     RealDistribution kernel = getKernel(this.binStats.get(i));
/* 811 */     return (i == 0) ? kernel.cumulativeProbability(this.min, binBounds[0]) : kernel.cumulativeProbability(binBounds[i - 1], binBounds[i]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RealDistribution k(double x) {
/* 822 */     int binIndex = findBin(x);
/* 823 */     return getKernel(this.binStats.get(binIndex));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double cumBinP(int binIndex) {
/* 833 */     return this.upperBounds[binIndex];
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
/*     */   protected RealDistribution getKernel(SummaryStatistics bStats) {
/* 845 */     if (bStats.getN() == 1L || bStats.getVariance() == 0.0D) {
/* 846 */       return (RealDistribution)new ConstantRealDistribution(bStats.getMean());
/*     */     }
/* 848 */     return (RealDistribution)new NormalDistribution(this.randomData.getRandomGenerator(), bStats.getMean(), bStats.getStandardDeviation(), 1.0E-9D);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/EmpiricalDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */