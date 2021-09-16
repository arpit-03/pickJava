/*      */ package org.apache.commons.math3.stat.descriptive.rank;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*      */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.stat.descriptive.AbstractUnivariateStatistic;
/*      */ import org.apache.commons.math3.stat.descriptive.UnivariateStatistic;
/*      */ import org.apache.commons.math3.stat.ranking.NaNStrategy;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.KthSelector;
/*      */ import org.apache.commons.math3.util.MathArrays;
/*      */ import org.apache.commons.math3.util.MathUtils;
/*      */ import org.apache.commons.math3.util.MedianOf3PivotingStrategy;
/*      */ import org.apache.commons.math3.util.PivotingStrategyInterface;
/*      */ import org.apache.commons.math3.util.Precision;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Percentile
/*      */   extends AbstractUnivariateStatistic
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -8091216485095130416L;
/*      */   private static final int MAX_CACHED_LEVELS = 10;
/*      */   private static final int PIVOTS_HEAP_LENGTH = 512;
/*      */   private final KthSelector kthSelector;
/*      */   private final EstimationType estimationType;
/*      */   private final NaNStrategy nanStrategy;
/*      */   private double quantile;
/*      */   private int[] cachedPivots;
/*      */   
/*      */   public Percentile() {
/*  137 */     this(50.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Percentile(double quantile) throws MathIllegalArgumentException {
/*  152 */     this(quantile, EstimationType.LEGACY, NaNStrategy.REMOVED, new KthSelector((PivotingStrategyInterface)new MedianOf3PivotingStrategy()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Percentile(Percentile original) throws NullArgumentException {
/*  165 */     MathUtils.checkNotNull(original);
/*  166 */     this.estimationType = original.getEstimationType();
/*  167 */     this.nanStrategy = original.getNaNStrategy();
/*  168 */     this.kthSelector = original.getKthSelector();
/*      */     
/*  170 */     setData(original.getDataRef());
/*  171 */     if (original.cachedPivots != null) {
/*  172 */       System.arraycopy(original.cachedPivots, 0, this.cachedPivots, 0, original.cachedPivots.length);
/*      */     }
/*  174 */     setQuantile(original.quantile);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Percentile(double quantile, EstimationType estimationType, NaNStrategy nanStrategy, KthSelector kthSelector) throws MathIllegalArgumentException {
/*  194 */     setQuantile(quantile);
/*  195 */     this.cachedPivots = null;
/*  196 */     MathUtils.checkNotNull(estimationType);
/*  197 */     MathUtils.checkNotNull(nanStrategy);
/*  198 */     MathUtils.checkNotNull(kthSelector);
/*  199 */     this.estimationType = estimationType;
/*  200 */     this.nanStrategy = nanStrategy;
/*  201 */     this.kthSelector = kthSelector;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setData(double[] values) {
/*  207 */     if (values == null) {
/*  208 */       this.cachedPivots = null;
/*      */     } else {
/*  210 */       this.cachedPivots = new int[512];
/*  211 */       Arrays.fill(this.cachedPivots, -1);
/*      */     } 
/*  213 */     super.setData(values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setData(double[] values, int begin, int length) throws MathIllegalArgumentException {
/*  220 */     if (values == null) {
/*  221 */       this.cachedPivots = null;
/*      */     } else {
/*  223 */       this.cachedPivots = new int[512];
/*  224 */       Arrays.fill(this.cachedPivots, -1);
/*      */     } 
/*  226 */     super.setData(values, begin, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double evaluate(double p) throws MathIllegalArgumentException {
/*  241 */     return evaluate(getDataRef(), p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double evaluate(double[] values, double p) throws MathIllegalArgumentException {
/*  272 */     test(values, 0, 0);
/*  273 */     return evaluate(values, 0, values.length, p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double evaluate(double[] values, int start, int length) throws MathIllegalArgumentException {
/*  302 */     return evaluate(values, start, length, this.quantile);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double evaluate(double[] values, int begin, int length, double p) throws MathIllegalArgumentException {
/*  339 */     test(values, begin, length);
/*  340 */     if (p > 100.0D || p <= 0.0D) {
/*  341 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUNDS_QUANTILE_VALUE, Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(100));
/*      */     }
/*      */     
/*  344 */     if (length == 0) {
/*  345 */       return Double.NaN;
/*      */     }
/*  347 */     if (length == 1) {
/*  348 */       return values[begin];
/*      */     }
/*      */     
/*  351 */     double[] work = getWorkArray(values, begin, length);
/*  352 */     int[] pivotsHeap = getPivots(values);
/*  353 */     return (work.length == 0) ? Double.NaN : this.estimationType.evaluate(work, pivotsHeap, p, this.kthSelector);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   int medianOf3(double[] work, int begin, int end) {
/*  374 */     return (new MedianOf3PivotingStrategy()).pivotIndex(work, begin, end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getQuantile() {
/*  385 */     return this.quantile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQuantile(double p) throws MathIllegalArgumentException {
/*  397 */     if (p <= 0.0D || p > 100.0D) {
/*  398 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUNDS_QUANTILE_VALUE, Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(100));
/*      */     }
/*      */     
/*  401 */     this.quantile = p;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Percentile copy() {
/*  409 */     return new Percentile(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void copy(Percentile source, Percentile dest) throws MathUnsupportedOperationException {
/*  426 */     throw new MathUnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double[] getWorkArray(double[] values, int begin, int length) {
/*      */     double[] work;
/*  444 */     if (values == getDataRef())
/*  445 */     { work = getDataRef(); }
/*      */     else
/*  447 */     { switch (this.nanStrategy)
/*      */       { case MAXIMAL:
/*  449 */           work = replaceAndSlice(values, begin, length, Double.NaN, Double.POSITIVE_INFINITY);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  466 */           return work;case MINIMAL: work = replaceAndSlice(values, begin, length, Double.NaN, Double.NEGATIVE_INFINITY); return work;case REMOVED: work = removeAndSlice(values, begin, length, Double.NaN); return work;case FAILED: work = copyOf(values, begin, length); MathArrays.checkNotNaN(work); return work; }  work = copyOf(values, begin, length); }  return work;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double[] copyOf(double[] values, int begin, int length) {
/*  478 */     MathArrays.verifyValues(values, begin, length);
/*  479 */     return MathArrays.copyOfRange(values, begin, begin + length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double[] replaceAndSlice(double[] values, int begin, int length, double original, double replacement) {
/*  496 */     double[] temp = copyOf(values, begin, length);
/*  497 */     for (int i = 0; i < length; i++) {
/*  498 */       temp[i] = Precision.equalsIncludingNaN(original, temp[i]) ? replacement : temp[i];
/*      */     }
/*      */     
/*  501 */     return temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double[] removeAndSlice(double[] values, int begin, int length, double removedValue) {
/*      */     double[] temp;
/*  516 */     MathArrays.verifyValues(values, begin, length);
/*      */ 
/*      */     
/*  519 */     BitSet bits = new BitSet(length);
/*  520 */     for (int i = begin; i < begin + length; i++) {
/*  521 */       if (Precision.equalsIncludingNaN(removedValue, values[i])) {
/*  522 */         bits.set(i - begin);
/*      */       }
/*      */     } 
/*      */     
/*  526 */     if (bits.isEmpty()) {
/*  527 */       temp = copyOf(values, begin, length);
/*  528 */     } else if (bits.cardinality() == length) {
/*  529 */       temp = new double[0];
/*      */     } else {
/*  531 */       temp = new double[length - bits.cardinality()];
/*  532 */       int start = begin;
/*  533 */       int dest = 0;
/*  534 */       int nextOne = -1;
/*  535 */       int bitSetPtr = 0;
/*  536 */       while ((nextOne = bits.nextSetBit(bitSetPtr)) != -1) {
/*  537 */         int lengthToCopy = nextOne - bitSetPtr;
/*  538 */         System.arraycopy(values, start, temp, dest, lengthToCopy);
/*  539 */         dest += lengthToCopy;
/*  540 */         start = begin + (bitSetPtr = bits.nextClearBit(nextOne));
/*      */       } 
/*      */       
/*  543 */       if (start < begin + length) {
/*  544 */         System.arraycopy(values, start, temp, dest, begin + length - start);
/*      */       }
/*      */     } 
/*  547 */     return temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] getPivots(double[] values) {
/*      */     int[] pivotsHeap;
/*  558 */     if (values == getDataRef()) {
/*  559 */       pivotsHeap = this.cachedPivots;
/*      */     } else {
/*  561 */       pivotsHeap = new int[512];
/*  562 */       Arrays.fill(pivotsHeap, -1);
/*      */     } 
/*  564 */     return pivotsHeap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EstimationType getEstimationType() {
/*  573 */     return this.estimationType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Percentile withEstimationType(EstimationType newEstimationType) {
/*  598 */     return new Percentile(this.quantile, newEstimationType, this.nanStrategy, this.kthSelector);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NaNStrategy getNaNStrategy() {
/*  606 */     return this.nanStrategy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Percentile withNaNStrategy(NaNStrategy newNaNStrategy) {
/*  631 */     return new Percentile(this.quantile, this.estimationType, newNaNStrategy, this.kthSelector);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KthSelector getKthSelector() {
/*  639 */     return this.kthSelector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PivotingStrategyInterface getPivotingStrategy() {
/*  647 */     return this.kthSelector.getPivotingStrategy();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Percentile withKthSelector(KthSelector newKthSelector) {
/*  672 */     return new Percentile(this.quantile, this.estimationType, this.nanStrategy, newKthSelector);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum EstimationType
/*      */   {
/*  724 */     LEGACY("Legacy Apache Commons Math")
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*      */       protected double index(double p, int length)
/*      */       {
/*  731 */         double minLimit = 0.0D;
/*  732 */         double maxLimit = 1.0D;
/*  733 */         return (Double.compare(p, 0.0D) == 0) ? 0.0D : ((Double.compare(p, 1.0D) == 0) ? length : (p * (length + 1)));
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  746 */     R_1("R-1")
/*      */     {
/*      */       protected double index(double p, int length)
/*      */       {
/*  750 */         double minLimit = 0.0D;
/*  751 */         return (Double.compare(p, 0.0D) == 0) ? 0.0D : (length * p + 0.5D);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected double estimate(double[] values, int[] pivotsHeap, double pos, int length, KthSelector selector) {
/*  761 */         return super.estimate(values, pivotsHeap, FastMath.ceil(pos - 0.5D), length, selector);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  775 */     R_2("R-2")
/*      */     {
/*      */       protected double index(double p, int length)
/*      */       {
/*  779 */         double minLimit = 0.0D;
/*  780 */         double maxLimit = 1.0D;
/*  781 */         return (Double.compare(p, 1.0D) == 0) ? length : ((Double.compare(p, 0.0D) == 0) ? 0.0D : (length * p + 0.5D));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected double estimate(double[] values, int[] pivotsHeap, double pos, int length, KthSelector selector) {
/*  793 */         double low = super.estimate(values, pivotsHeap, FastMath.ceil(pos - 0.5D), length, selector);
/*      */         
/*  795 */         double high = super.estimate(values, pivotsHeap, FastMath.floor(pos + 0.5D), length, selector);
/*      */         
/*  797 */         return (low + high) / 2.0D;
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  809 */     R_3("R-3")
/*      */     {
/*      */       protected double index(double p, int length) {
/*  812 */         double minLimit = 0.5D / length;
/*  813 */         return (Double.compare(p, minLimit) <= 0) ? 0.0D : FastMath.rint(length * p);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  829 */     R_4("R-4")
/*      */     {
/*      */       protected double index(double p, int length) {
/*  832 */         double minLimit = 1.0D / length;
/*  833 */         double maxLimit = 1.0D;
/*  834 */         return (Double.compare(p, minLimit) < 0) ? 0.0D : ((Double.compare(p, 1.0D) == 0) ? length : (length * p));
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  850 */     R_5("R-5")
/*      */     {
/*      */       protected double index(double p, int length)
/*      */       {
/*  854 */         double minLimit = 0.5D / length;
/*  855 */         double maxLimit = (length - 0.5D) / length;
/*  856 */         return (Double.compare(p, minLimit) < 0) ? 0.0D : ((Double.compare(p, maxLimit) >= 0) ? length : (length * p + 0.5D));
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  878 */     R_6("R-6")
/*      */     {
/*      */       protected double index(double p, int length)
/*      */       {
/*  882 */         double minLimit = 1.0D / (length + 1);
/*  883 */         double maxLimit = 1.0D * length / (length + 1);
/*  884 */         return (Double.compare(p, minLimit) < 0) ? 0.0D : ((Double.compare(p, maxLimit) >= 0) ? length : ((length + 1) * p));
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  902 */     R_7("R-7")
/*      */     {
/*      */       protected double index(double p, int length) {
/*  905 */         double minLimit = 0.0D;
/*  906 */         double maxLimit = 1.0D;
/*  907 */         return (Double.compare(p, 0.0D) == 0) ? 0.0D : ((Double.compare(p, 1.0D) == 0) ? length : (1.0D + (length - 1) * p));
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  928 */     R_8("R-8")
/*      */     {
/*      */       protected double index(double p, int length) {
/*  931 */         double minLimit = 0.6666666666666666D / (length + 0.3333333333333333D);
/*  932 */         double maxLimit = (length - 0.3333333333333333D) / (length + 0.3333333333333333D);
/*      */         
/*  934 */         return (Double.compare(p, minLimit) < 0) ? 0.0D : ((Double.compare(p, maxLimit) >= 0) ? length : ((length + 0.3333333333333333D) * p + 0.3333333333333333D));
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  951 */     R_9("R-9")
/*      */     {
/*      */       protected double index(double p, int length) {
/*  954 */         double minLimit = 0.625D / (length + 0.25D);
/*  955 */         double maxLimit = (length - 0.375D) / (length + 0.25D);
/*  956 */         return (Double.compare(p, minLimit) < 0) ? 0.0D : ((Double.compare(p, maxLimit) >= 0) ? length : ((length + 0.25D) * p + 0.375D));
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final String name;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EstimationType(String type) {
/*  973 */       this.name = type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected double estimate(double[] work, int[] pivotsHeap, double pos, int length, KthSelector selector) {
/* 1004 */       double fpos = FastMath.floor(pos);
/* 1005 */       int intPos = (int)fpos;
/* 1006 */       double dif = pos - fpos;
/*      */       
/* 1008 */       if (pos < 1.0D) {
/* 1009 */         return selector.select(work, pivotsHeap, 0);
/*      */       }
/* 1011 */       if (pos >= length) {
/* 1012 */         return selector.select(work, pivotsHeap, length - 1);
/*      */       }
/*      */       
/* 1015 */       double lower = selector.select(work, pivotsHeap, intPos - 1);
/* 1016 */       double upper = selector.select(work, pivotsHeap, intPos);
/* 1017 */       return lower + dif * (upper - lower);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected double evaluate(double[] work, int[] pivotsHeap, double p, KthSelector selector) {
/* 1037 */       MathUtils.checkNotNull(work);
/* 1038 */       if (p > 100.0D || p <= 0.0D) {
/* 1039 */         throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUNDS_QUANTILE_VALUE, Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(100));
/*      */       }
/*      */       
/* 1042 */       return estimate(work, pivotsHeap, index(p / 100.0D, work.length), work.length, selector);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double evaluate(double[] work, double p, KthSelector selector) {
/* 1060 */       return evaluate(work, null, p, selector);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String getName() {
/* 1069 */       return this.name;
/*      */     }
/*      */     
/*      */     protected abstract double index(double param1Double, int param1Int);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/rank/Percentile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */