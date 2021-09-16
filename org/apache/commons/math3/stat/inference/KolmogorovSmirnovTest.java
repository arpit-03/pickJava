/*      */ package org.apache.commons.math3.stat.inference;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.FieldElement;
/*      */ import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
/*      */ import org.apache.commons.math3.distribution.RealDistribution;
/*      */ import org.apache.commons.math3.distribution.UniformRealDistribution;
/*      */ import org.apache.commons.math3.exception.InsufficientDataException;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.MathInternalError;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.TooManyIterationsException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.fraction.BigFraction;
/*      */ import org.apache.commons.math3.fraction.BigFractionField;
/*      */ import org.apache.commons.math3.fraction.FractionConversionException;
/*      */ import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
/*      */ import org.apache.commons.math3.linear.FieldMatrix;
/*      */ import org.apache.commons.math3.linear.MatrixUtils;
/*      */ import org.apache.commons.math3.linear.RealMatrix;
/*      */ import org.apache.commons.math3.random.JDKRandomGenerator;
/*      */ import org.apache.commons.math3.random.RandomGenerator;
/*      */ import org.apache.commons.math3.random.Well19937c;
/*      */ import org.apache.commons.math3.util.CombinatoricsUtils;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathArrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class KolmogorovSmirnovTest
/*      */ {
/*      */   protected static final int MAXIMUM_PARTIAL_SUM_COUNT = 100000;
/*      */   protected static final double KS_SUM_CAUCHY_CRITERION = 1.0E-20D;
/*      */   protected static final double PG_SUM_RELATIVE_ERROR = 1.0E-10D;
/*      */   @Deprecated
/*      */   protected static final int SMALL_SAMPLE_PRODUCT = 200;
/*      */   protected static final int LARGE_SAMPLE_PRODUCT = 10000;
/*      */   @Deprecated
/*      */   protected static final int MONTE_CARLO_ITERATIONS = 1000000;
/*      */   private final RandomGenerator rng;
/*      */   
/*      */   public KolmogorovSmirnovTest() {
/*  155 */     this.rng = (RandomGenerator)new Well19937c();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public KolmogorovSmirnovTest(RandomGenerator rng) {
/*  167 */     this.rng = rng;
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
/*      */   public double kolmogorovSmirnovTest(RealDistribution distribution, double[] data, boolean exact) {
/*  186 */     return 1.0D - cdf(kolmogorovSmirnovStatistic(distribution, data), data.length, exact);
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
/*      */   public double kolmogorovSmirnovStatistic(RealDistribution distribution, double[] data) {
/*  202 */     checkArray(data);
/*  203 */     int n = data.length;
/*  204 */     double nd = n;
/*  205 */     double[] dataCopy = new double[n];
/*  206 */     System.arraycopy(data, 0, dataCopy, 0, n);
/*  207 */     Arrays.sort(dataCopy);
/*  208 */     double d = 0.0D;
/*  209 */     for (int i = 1; i <= n; i++) {
/*  210 */       double yi = distribution.cumulativeProbability(dataCopy[i - 1]);
/*  211 */       double currD = FastMath.max(yi - (i - 1) / nd, i / nd - yi);
/*  212 */       if (currD > d) {
/*  213 */         d = currD;
/*      */       }
/*      */     } 
/*  216 */     return d;
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
/*      */   public double kolmogorovSmirnovTest(double[] x, double[] y, boolean strict) {
/*  257 */     long lengthProduct = x.length * y.length;
/*  258 */     double[] xa = null;
/*  259 */     double[] ya = null;
/*  260 */     if (lengthProduct < 10000L && hasTies(x, y)) {
/*  261 */       xa = MathArrays.copyOf(x);
/*  262 */       ya = MathArrays.copyOf(y);
/*  263 */       fixTies(xa, ya);
/*      */     } else {
/*  265 */       xa = x;
/*  266 */       ya = y;
/*      */     } 
/*  268 */     if (lengthProduct < 10000L) {
/*  269 */       return exactP(kolmogorovSmirnovStatistic(xa, ya), x.length, y.length, strict);
/*      */     }
/*  271 */     return approximateP(kolmogorovSmirnovStatistic(x, y), x.length, y.length);
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
/*      */   public double kolmogorovSmirnovTest(double[] x, double[] y) {
/*  290 */     return kolmogorovSmirnovTest(x, y, true);
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
/*      */   public double kolmogorovSmirnovStatistic(double[] x, double[] y) {
/*  308 */     return integralKolmogorovSmirnovStatistic(x, y) / (x.length * y.length);
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
/*      */   private long integralKolmogorovSmirnovStatistic(double[] x, double[] y) {
/*  327 */     checkArray(x);
/*  328 */     checkArray(y);
/*      */     
/*  330 */     double[] sx = MathArrays.copyOf(x);
/*  331 */     double[] sy = MathArrays.copyOf(y);
/*  332 */     Arrays.sort(sx);
/*  333 */     Arrays.sort(sy);
/*  334 */     int n = sx.length;
/*  335 */     int m = sy.length;
/*      */     
/*  337 */     int rankX = 0;
/*  338 */     int rankY = 0;
/*  339 */     long curD = 0L;
/*      */ 
/*      */     
/*  342 */     long supD = 0L;
/*      */     do {
/*  344 */       double z = (Double.compare(sx[rankX], sy[rankY]) <= 0) ? sx[rankX] : sy[rankY];
/*  345 */       while (rankX < n && Double.compare(sx[rankX], z) == 0) {
/*  346 */         rankX++;
/*  347 */         curD += m;
/*      */       } 
/*  349 */       while (rankY < m && Double.compare(sy[rankY], z) == 0) {
/*  350 */         rankY++;
/*  351 */         curD -= n;
/*      */       } 
/*  353 */       if (curD > supD) {
/*  354 */         supD = curD;
/*      */       }
/*  356 */       else if (-curD > supD) {
/*  357 */         supD = -curD;
/*      */       } 
/*  359 */     } while (rankX < n && rankY < m);
/*  360 */     return supD;
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
/*      */   public double kolmogorovSmirnovTest(RealDistribution distribution, double[] data) {
/*  376 */     return kolmogorovSmirnovTest(distribution, data, false);
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
/*      */   public boolean kolmogorovSmirnovTest(RealDistribution distribution, double[] data, double alpha) {
/*  392 */     if (alpha <= 0.0D || alpha > 0.5D) {
/*  393 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5D));
/*      */     }
/*  395 */     return (kolmogorovSmirnovTest(distribution, data) < alpha);
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
/*      */   public double bootstrap(double[] x, double[] y, int iterations, boolean strict) {
/*  417 */     int xLength = x.length;
/*  418 */     int yLength = y.length;
/*  419 */     double[] combined = new double[xLength + yLength];
/*  420 */     System.arraycopy(x, 0, combined, 0, xLength);
/*  421 */     System.arraycopy(y, 0, combined, xLength, yLength);
/*  422 */     EnumeratedRealDistribution dist = new EnumeratedRealDistribution(this.rng, combined);
/*  423 */     long d = integralKolmogorovSmirnovStatistic(x, y);
/*  424 */     int greaterCount = 0;
/*  425 */     int equalCount = 0;
/*      */ 
/*      */ 
/*      */     
/*  429 */     for (int i = 0; i < iterations; i++) {
/*  430 */       double[] curX = dist.sample(xLength);
/*  431 */       double[] curY = dist.sample(yLength);
/*  432 */       long curD = integralKolmogorovSmirnovStatistic(curX, curY);
/*  433 */       if (curD > d) {
/*  434 */         greaterCount++;
/*  435 */       } else if (curD == d) {
/*  436 */         equalCount++;
/*      */       } 
/*      */     } 
/*  439 */     return strict ? (greaterCount / iterations) : ((greaterCount + equalCount) / iterations);
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
/*      */   public double bootstrap(double[] x, double[] y, int iterations) {
/*  454 */     return bootstrap(x, y, iterations, true);
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
/*      */   public double cdf(double d, int n) throws MathArithmeticException {
/*  472 */     return cdf(d, n, false);
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
/*      */   public double cdfExact(double d, int n) throws MathArithmeticException {
/*  491 */     return cdf(d, n, true);
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
/*      */   public double cdf(double d, int n, boolean exact) throws MathArithmeticException {
/*  513 */     double ninv = 1.0D / n;
/*  514 */     double ninvhalf = 0.5D * ninv;
/*      */     
/*  516 */     if (d <= ninvhalf)
/*  517 */       return 0.0D; 
/*  518 */     if (ninvhalf < d && d <= ninv) {
/*  519 */       double res = 1.0D;
/*  520 */       double f = 2.0D * d - ninv;
/*      */       
/*  522 */       for (int i = 1; i <= n; i++) {
/*  523 */         res *= i * f;
/*      */       }
/*  525 */       return res;
/*  526 */     }  if (1.0D - ninv <= d && d < 1.0D)
/*  527 */       return 1.0D - 2.0D * Math.pow(1.0D - d, n); 
/*  528 */     if (1.0D <= d) {
/*  529 */       return 1.0D;
/*      */     }
/*  531 */     if (exact) {
/*  532 */       return exactK(d, n);
/*      */     }
/*  534 */     if (n <= 140) {
/*  535 */       return roundedK(d, n);
/*      */     }
/*  537 */     return pelzGood(d, n);
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
/*      */   private double exactK(double d, int n) throws MathArithmeticException {
/*  555 */     int k = (int)Math.ceil(n * d);
/*      */     
/*  557 */     FieldMatrix<BigFraction> H = createExactH(d, n);
/*  558 */     FieldMatrix<BigFraction> Hpower = H.power(n);
/*      */     
/*  560 */     BigFraction pFrac = (BigFraction)Hpower.getEntry(k - 1, k - 1);
/*      */     
/*  562 */     for (int i = 1; i <= n; i++) {
/*  563 */       pFrac = pFrac.multiply(i).divide(n);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  571 */     return pFrac.bigDecimalValue(20, 4).doubleValue();
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
/*      */   private double roundedK(double d, int n) {
/*  583 */     int k = (int)Math.ceil(n * d);
/*  584 */     RealMatrix H = createRoundedH(d, n);
/*  585 */     RealMatrix Hpower = H.power(n);
/*      */     
/*  587 */     double pFrac = Hpower.getEntry(k - 1, k - 1);
/*  588 */     for (int i = 1; i <= n; i++) {
/*  589 */       pFrac *= i / n;
/*      */     }
/*      */     
/*  592 */     return pFrac;
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
/*      */   public double pelzGood(double d, int n) {
/*  605 */     double sqrtN = FastMath.sqrt(n);
/*  606 */     double z = d * sqrtN;
/*  607 */     double z2 = d * d * n;
/*  608 */     double z4 = z2 * z2;
/*  609 */     double z6 = z4 * z2;
/*  610 */     double z8 = z4 * z4;
/*      */ 
/*      */     
/*  613 */     double ret = 0.0D;
/*      */ 
/*      */     
/*  616 */     double sum = 0.0D;
/*  617 */     double increment = 0.0D;
/*  618 */     double kTerm = 0.0D;
/*  619 */     double z2Term = 9.869604401089358D / 8.0D * z2;
/*  620 */     int k = 1;
/*  621 */     for (; k < 100000; k++) {
/*  622 */       kTerm = (2 * k - 1);
/*  623 */       increment = FastMath.exp(-z2Term * kTerm * kTerm);
/*  624 */       sum += increment;
/*  625 */       if (increment <= 1.0E-10D * sum) {
/*      */         break;
/*      */       }
/*      */     } 
/*  629 */     if (k == 100000) {
/*  630 */       throw new TooManyIterationsException(Integer.valueOf(100000));
/*      */     }
/*  632 */     ret = sum * FastMath.sqrt(6.283185307179586D) / z;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  637 */     double twoZ2 = 2.0D * z2;
/*  638 */     sum = 0.0D;
/*  639 */     kTerm = 0.0D;
/*  640 */     double kTerm2 = 0.0D;
/*  641 */     for (k = 0; k < 100000; k++) {
/*  642 */       kTerm = k + 0.5D;
/*  643 */       kTerm2 = kTerm * kTerm;
/*  644 */       increment = (9.869604401089358D * kTerm2 - z2) * FastMath.exp(-9.869604401089358D * kTerm2 / twoZ2);
/*  645 */       sum += increment;
/*  646 */       if (FastMath.abs(increment) < 1.0E-10D * FastMath.abs(sum)) {
/*      */         break;
/*      */       }
/*      */     } 
/*  650 */     if (k == 100000) {
/*  651 */       throw new TooManyIterationsException(Integer.valueOf(100000));
/*      */     }
/*  653 */     double sqrtHalfPi = FastMath.sqrt(1.5707963267948966D);
/*      */     
/*  655 */     ret += sum * sqrtHalfPi / 3.0D * z4 * sqrtN;
/*      */ 
/*      */ 
/*      */     
/*  659 */     double z4Term = 2.0D * z4;
/*  660 */     double z6Term = 6.0D * z6;
/*  661 */     z2Term = 5.0D * z2;
/*  662 */     double pi4 = 97.40909103400243D;
/*  663 */     sum = 0.0D;
/*  664 */     kTerm = 0.0D;
/*  665 */     kTerm2 = 0.0D;
/*  666 */     for (k = 0; k < 100000; k++) {
/*  667 */       kTerm = k + 0.5D;
/*  668 */       kTerm2 = kTerm * kTerm;
/*  669 */       increment = (z6Term + z4Term + 9.869604401089358D * (z4Term - z2Term) * kTerm2 + 97.40909103400243D * (1.0D - twoZ2) * kTerm2 * kTerm2) * FastMath.exp(-9.869604401089358D * kTerm2 / twoZ2);
/*      */       
/*  671 */       sum += increment;
/*  672 */       if (FastMath.abs(increment) < 1.0E-10D * FastMath.abs(sum)) {
/*      */         break;
/*      */       }
/*      */     } 
/*  676 */     if (k == 100000) {
/*  677 */       throw new TooManyIterationsException(Integer.valueOf(100000));
/*      */     }
/*  679 */     double sum2 = 0.0D;
/*  680 */     kTerm2 = 0.0D;
/*  681 */     for (k = 1; k < 100000; k++) {
/*  682 */       kTerm2 = (k * k);
/*  683 */       increment = 9.869604401089358D * kTerm2 * FastMath.exp(-9.869604401089358D * kTerm2 / twoZ2);
/*  684 */       sum2 += increment;
/*  685 */       if (FastMath.abs(increment) < 1.0E-10D * FastMath.abs(sum2)) {
/*      */         break;
/*      */       }
/*      */     } 
/*  689 */     if (k == 100000) {
/*  690 */       throw new TooManyIterationsException(Integer.valueOf(100000));
/*      */     }
/*      */     
/*  693 */     ret += sqrtHalfPi / n * (sum / 36.0D * z2 * z2 * z2 * z - sum2 / 18.0D * z2 * z);
/*      */ 
/*      */ 
/*      */     
/*  697 */     double pi6 = 961.3891935753043D;
/*  698 */     sum = 0.0D;
/*  699 */     double kTerm4 = 0.0D;
/*  700 */     double kTerm6 = 0.0D;
/*  701 */     for (k = 0; k < 100000; k++) {
/*  702 */       kTerm = k + 0.5D;
/*  703 */       kTerm2 = kTerm * kTerm;
/*  704 */       kTerm4 = kTerm2 * kTerm2;
/*  705 */       kTerm6 = kTerm4 * kTerm2;
/*  706 */       increment = (961.3891935753043D * kTerm6 * (5.0D - 30.0D * z2) + 97.40909103400243D * kTerm4 * (-60.0D * z2 + 212.0D * z4) + 9.869604401089358D * kTerm2 * (135.0D * z4 - 96.0D * z6) - 30.0D * z6 - 90.0D * z8) * FastMath.exp(-9.869604401089358D * kTerm2 / twoZ2);
/*      */ 
/*      */       
/*  709 */       sum += increment;
/*  710 */       if (FastMath.abs(increment) < 1.0E-10D * FastMath.abs(sum)) {
/*      */         break;
/*      */       }
/*      */     } 
/*  714 */     if (k == 100000) {
/*  715 */       throw new TooManyIterationsException(Integer.valueOf(100000));
/*      */     }
/*  717 */     sum2 = 0.0D;
/*  718 */     for (k = 1; k < 100000; k++) {
/*  719 */       kTerm2 = (k * k);
/*  720 */       kTerm4 = kTerm2 * kTerm2;
/*  721 */       increment = (-97.40909103400243D * kTerm4 + 29.608813203268074D * kTerm2 * z2) * FastMath.exp(-9.869604401089358D * kTerm2 / twoZ2);
/*      */       
/*  723 */       sum2 += increment;
/*  724 */       if (FastMath.abs(increment) < 1.0E-10D * FastMath.abs(sum2)) {
/*      */         break;
/*      */       }
/*      */     } 
/*  728 */     if (k == 100000) {
/*  729 */       throw new TooManyIterationsException(Integer.valueOf(100000));
/*      */     }
/*  731 */     return ret + sqrtHalfPi / sqrtN * n * (sum / 3240.0D * z6 * z4 + sum2 / 108.0D * z6);
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
/*      */   private FieldMatrix<BigFraction> createExactH(double d, int n) throws NumberIsTooLargeException, FractionConversionException {
/*  750 */     int k = (int)Math.ceil(n * d);
/*  751 */     int m = 2 * k - 1;
/*  752 */     double hDouble = k - n * d;
/*  753 */     if (hDouble >= 1.0D) {
/*  754 */       throw new NumberIsTooLargeException(Double.valueOf(hDouble), Double.valueOf(1.0D), false);
/*      */     }
/*  756 */     BigFraction h = null;
/*      */     try {
/*  758 */       h = new BigFraction(hDouble, 1.0E-20D, 10000);
/*  759 */     } catch (FractionConversionException e1) {
/*      */       try {
/*  761 */         h = new BigFraction(hDouble, 1.0E-10D, 10000);
/*  762 */       } catch (FractionConversionException e2) {
/*  763 */         h = new BigFraction(hDouble, 1.0E-5D, 10000);
/*      */       } 
/*      */     } 
/*  766 */     BigFraction[][] Hdata = new BigFraction[m][m];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  771 */     for (int i = 0; i < m; i++) {
/*  772 */       for (int i1 = 0; i1 < m; i1++) {
/*  773 */         if (i - i1 + 1 < 0) {
/*  774 */           Hdata[i][i1] = BigFraction.ZERO;
/*      */         } else {
/*  776 */           Hdata[i][i1] = BigFraction.ONE;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  785 */     BigFraction[] hPowers = new BigFraction[m];
/*  786 */     hPowers[0] = h; int j;
/*  787 */     for (j = 1; j < m; j++) {
/*  788 */       hPowers[j] = h.multiply(hPowers[j - 1]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  794 */     for (j = 0; j < m; j++) {
/*  795 */       Hdata[j][0] = Hdata[j][0].subtract(hPowers[j]);
/*  796 */       Hdata[m - 1][j] = Hdata[m - 1][j].subtract(hPowers[m - j - 1]);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  803 */     if (h.compareTo(BigFraction.ONE_HALF) == 1) {
/*  804 */       Hdata[m - 1][0] = Hdata[m - 1][0].add(h.multiply(2).subtract(1).pow(m));
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
/*  815 */     for (j = 0; j < m; j++) {
/*  816 */       for (int i1 = 0; i1 < j + 1; i1++) {
/*  817 */         if (j - i1 + 1 > 0) {
/*  818 */           for (int g = 2; g <= j - i1 + 1; g++) {
/*  819 */             Hdata[j][i1] = Hdata[j][i1].divide(g);
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*  824 */     return (FieldMatrix<BigFraction>)new Array2DRowFieldMatrix((Field)BigFractionField.getInstance(), (FieldElement[][])Hdata);
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
/*      */   private RealMatrix createRoundedH(double d, int n) throws NumberIsTooLargeException {
/*  839 */     int k = (int)Math.ceil(n * d);
/*  840 */     int m = 2 * k - 1;
/*  841 */     double h = k - n * d;
/*  842 */     if (h >= 1.0D) {
/*  843 */       throw new NumberIsTooLargeException(Double.valueOf(h), Double.valueOf(1.0D), false);
/*      */     }
/*  845 */     double[][] Hdata = new double[m][m];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  850 */     for (int i = 0; i < m; i++) {
/*  851 */       for (int i1 = 0; i1 < m; i1++) {
/*  852 */         if (i - i1 + 1 < 0) {
/*  853 */           Hdata[i][i1] = 0.0D;
/*      */         } else {
/*  855 */           Hdata[i][i1] = 1.0D;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  864 */     double[] hPowers = new double[m];
/*  865 */     hPowers[0] = h; int j;
/*  866 */     for (j = 1; j < m; j++) {
/*  867 */       hPowers[j] = h * hPowers[j - 1];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  873 */     for (j = 0; j < m; j++) {
/*  874 */       Hdata[j][0] = Hdata[j][0] - hPowers[j];
/*  875 */       Hdata[m - 1][j] = Hdata[m - 1][j] - hPowers[m - j - 1];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  882 */     if (Double.compare(h, 0.5D) > 0) {
/*  883 */       Hdata[m - 1][0] = Hdata[m - 1][0] + FastMath.pow(2.0D * h - 1.0D, m);
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
/*  894 */     for (j = 0; j < m; j++) {
/*  895 */       for (int i1 = 0; i1 < j + 1; i1++) {
/*  896 */         if (j - i1 + 1 > 0) {
/*  897 */           for (int g = 2; g <= j - i1 + 1; g++) {
/*  898 */             Hdata[j][i1] = Hdata[j][i1] / g;
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*  903 */     return MatrixUtils.createRealMatrix(Hdata);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkArray(double[] array) {
/*  914 */     if (array == null) {
/*  915 */       throw new NullArgumentException(LocalizedFormats.NULL_NOT_ALLOWED, new Object[0]);
/*      */     }
/*  917 */     if (array.length < 2) {
/*  918 */       throw new InsufficientDataException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, new Object[] { Integer.valueOf(array.length), Integer.valueOf(2) });
/*      */     }
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
/*      */   public double ksSum(double t, double tolerance, int maxIterations) {
/*  936 */     if (t == 0.0D) {
/*  937 */       return 0.0D;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  943 */     double x = -2.0D * t * t;
/*  944 */     int sign = -1;
/*  945 */     long i = 1L;
/*  946 */     double partialSum = 0.5D;
/*  947 */     double delta = 1.0D;
/*  948 */     while (delta > tolerance && i < maxIterations) {
/*  949 */       delta = FastMath.exp(x * i * i);
/*  950 */       partialSum += sign * delta;
/*  951 */       sign *= -1;
/*  952 */       i++;
/*      */     } 
/*  954 */     if (i == maxIterations) {
/*  955 */       throw new TooManyIterationsException(Integer.valueOf(maxIterations));
/*      */     }
/*  957 */     return partialSum * 2.0D;
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
/*      */   private static long calculateIntegralD(double d, int n, int m, boolean strict) {
/*  974 */     double tol = 1.0E-12D;
/*  975 */     long nm = n * m;
/*  976 */     long upperBound = (long)FastMath.ceil((d - 1.0E-12D) * nm);
/*  977 */     long lowerBound = (long)FastMath.floor((d + 1.0E-12D) * nm);
/*  978 */     if (strict && lowerBound == upperBound) {
/*  979 */       return upperBound + 1L;
/*      */     }
/*      */     
/*  982 */     return upperBound;
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
/*      */   public double exactP(double d, int n, int m, boolean strict) {
/* 1003 */     return 1.0D - n(m, n, m, n, calculateIntegralD(d, m, n, strict), strict) / CombinatoricsUtils.binomialCoefficientDouble(n + m, m);
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
/*      */   public double approximateP(double d, int n, int m) {
/* 1026 */     double dm = m;
/* 1027 */     double dn = n;
/* 1028 */     return 1.0D - ksSum(d * FastMath.sqrt(dm * dn / (dm + dn)), 1.0E-20D, 100000);
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
/*      */   static void fillBooleanArrayRandomlyWithFixedNumberTrueValues(boolean[] b, int numberOfTrueValues, RandomGenerator rng) {
/* 1045 */     Arrays.fill(b, true);
/* 1046 */     for (int k = numberOfTrueValues; k < b.length; k++) {
/* 1047 */       int r = rng.nextInt(k + 1);
/* 1048 */       b[b[r] ? r : k] = false;
/*      */     } 
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
/*      */   public double monteCarloP(double d, int n, int m, boolean strict, int iterations) {
/* 1073 */     return integralMonteCarloP(calculateIntegralD(d, n, m, strict), n, m, iterations);
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
/*      */   private double integralMonteCarloP(long d, int n, int m, int iterations) {
/* 1094 */     int nn = FastMath.max(n, m);
/* 1095 */     int mm = FastMath.min(n, m);
/* 1096 */     int sum = nn + mm;
/*      */     
/* 1098 */     int tail = 0;
/* 1099 */     boolean[] b = new boolean[sum];
/* 1100 */     for (int i = 0; i < iterations; i++) {
/* 1101 */       fillBooleanArrayRandomlyWithFixedNumberTrueValues(b, nn, this.rng);
/* 1102 */       long curD = 0L;
/* 1103 */       for (int j = 0; j < b.length; j++) {
/* 1104 */         if (b[j]) {
/* 1105 */           curD += mm;
/* 1106 */           if (curD >= d) {
/* 1107 */             tail++;
/*      */             break;
/*      */           } 
/*      */         } else {
/* 1111 */           curD -= nn;
/* 1112 */           if (curD <= -d) {
/* 1113 */             tail++;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1119 */     return tail / iterations;
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
/*      */   private static void fixTies(double[] x, double[] y) {
/* 1137 */     double[] values = MathArrays.unique(MathArrays.concatenate(new double[][] { x, y }));
/* 1138 */     if (values.length == x.length + y.length) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1143 */     double minDelta = 1.0D;
/* 1144 */     double prev = values[0];
/* 1145 */     double delta = 1.0D;
/* 1146 */     for (int i = 1; i < values.length; i++) {
/* 1147 */       delta = prev - values[i];
/* 1148 */       if (delta < minDelta) {
/* 1149 */         minDelta = delta;
/*      */       }
/* 1151 */       prev = values[i];
/*      */     } 
/* 1153 */     minDelta /= 2.0D;
/*      */ 
/*      */ 
/*      */     
/* 1157 */     UniformRealDistribution uniformRealDistribution = new UniformRealDistribution((RandomGenerator)new JDKRandomGenerator(100), -minDelta, minDelta);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1162 */     int ct = 0;
/* 1163 */     boolean ties = true;
/*      */     do {
/* 1165 */       jitter(x, (RealDistribution)uniformRealDistribution);
/* 1166 */       jitter(y, (RealDistribution)uniformRealDistribution);
/* 1167 */       ties = hasTies(x, y);
/* 1168 */       ct++;
/* 1169 */     } while (ties && ct < 1000);
/* 1170 */     if (ties) {
/* 1171 */       throw new MathInternalError();
/*      */     }
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
/*      */   private static boolean hasTies(double[] x, double[] y) {
/* 1184 */     HashSet<Double> values = new HashSet<Double>(); int i;
/* 1185 */     for (i = 0; i < x.length; i++) {
/* 1186 */       if (!values.add(Double.valueOf(x[i]))) {
/* 1187 */         return true;
/*      */       }
/*      */     } 
/* 1190 */     for (i = 0; i < y.length; i++) {
/* 1191 */       if (!values.add(Double.valueOf(y[i]))) {
/* 1192 */         return true;
/*      */       }
/*      */     } 
/* 1195 */     return false;
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
/*      */   private static void jitter(double[] data, RealDistribution dist) {
/* 1209 */     for (int i = 0; i < data.length; i++) {
/* 1210 */       data[i] = data[i] + dist.sample();
/*      */     }
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
/*      */   private static int c(int i, int j, int m, int n, long cmn, boolean strict) {
/* 1229 */     if (strict) {
/* 1230 */       return (FastMath.abs(i * n - j * m) <= cmn) ? 1 : 0;
/*      */     }
/* 1232 */     return (FastMath.abs(i * n - j * m) < cmn) ? 1 : 0;
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
/*      */   private static double n(int i, int j, int m, int n, long cnm, boolean strict) {
/* 1256 */     double[] lag = new double[n];
/* 1257 */     double last = 0.0D; int k;
/* 1258 */     for (k = 0; k < n; k++) {
/* 1259 */       lag[k] = c(0, k + 1, m, n, cnm, strict);
/*      */     }
/* 1261 */     for (k = 1; k <= i; k++) {
/* 1262 */       last = c(k, 0, m, n, cnm, strict);
/* 1263 */       for (int l = 1; l <= j; l++) {
/* 1264 */         lag[l - 1] = c(k, l, m, n, cnm, strict) * (last + lag[l - 1]);
/* 1265 */         last = lag[l - 1];
/*      */       } 
/*      */     } 
/* 1268 */     return last;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/inference/KolmogorovSmirnovTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */