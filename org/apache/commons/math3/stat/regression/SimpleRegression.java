/*     */ package org.apache.commons.math3.stat.regression;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.distribution.TDistribution;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleRegression
/*     */   implements Serializable, UpdatingMultipleLinearRegression
/*     */ {
/*     */   private static final long serialVersionUID = -3004689053607543335L;
/*  69 */   private double sumX = 0.0D;
/*     */ 
/*     */   
/*  72 */   private double sumXX = 0.0D;
/*     */ 
/*     */   
/*  75 */   private double sumY = 0.0D;
/*     */ 
/*     */   
/*  78 */   private double sumYY = 0.0D;
/*     */ 
/*     */   
/*  81 */   private double sumXY = 0.0D;
/*     */ 
/*     */   
/*  84 */   private long n = 0L;
/*     */ 
/*     */   
/*  87 */   private double xbar = 0.0D;
/*     */ 
/*     */   
/*  90 */   private double ybar = 0.0D;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean hasIntercept;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleRegression() {
/* 100 */     this(true);
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
/*     */   public SimpleRegression(boolean includeIntercept) {
/* 115 */     this.hasIntercept = includeIntercept;
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
/*     */   public void addData(double x, double y) {
/* 132 */     if (this.n == 0L) {
/* 133 */       this.xbar = x;
/* 134 */       this.ybar = y;
/*     */     }
/* 136 */     else if (this.hasIntercept) {
/* 137 */       double fact1 = 1.0D + this.n;
/* 138 */       double fact2 = this.n / (1.0D + this.n);
/* 139 */       double dx = x - this.xbar;
/* 140 */       double dy = y - this.ybar;
/* 141 */       this.sumXX += dx * dx * fact2;
/* 142 */       this.sumYY += dy * dy * fact2;
/* 143 */       this.sumXY += dx * dy * fact2;
/* 144 */       this.xbar += dx / fact1;
/* 145 */       this.ybar += dy / fact1;
/*     */     } 
/*     */     
/* 148 */     if (!this.hasIntercept) {
/* 149 */       this.sumXX += x * x;
/* 150 */       this.sumYY += y * y;
/* 151 */       this.sumXY += x * y;
/*     */     } 
/* 153 */     this.sumX += x;
/* 154 */     this.sumY += y;
/* 155 */     this.n++;
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
/*     */   public void append(SimpleRegression reg) {
/* 173 */     if (this.n == 0L) {
/* 174 */       this.xbar = reg.xbar;
/* 175 */       this.ybar = reg.ybar;
/* 176 */       this.sumXX = reg.sumXX;
/* 177 */       this.sumYY = reg.sumYY;
/* 178 */       this.sumXY = reg.sumXY;
/*     */     }
/* 180 */     else if (this.hasIntercept) {
/* 181 */       double fact1 = reg.n / (reg.n + this.n);
/* 182 */       double fact2 = (this.n * reg.n) / (reg.n + this.n);
/* 183 */       double dx = reg.xbar - this.xbar;
/* 184 */       double dy = reg.ybar - this.ybar;
/* 185 */       this.sumXX += reg.sumXX + dx * dx * fact2;
/* 186 */       this.sumYY += reg.sumYY + dy * dy * fact2;
/* 187 */       this.sumXY += reg.sumXY + dx * dy * fact2;
/* 188 */       this.xbar += dx * fact1;
/* 189 */       this.ybar += dy * fact1;
/*     */     } else {
/* 191 */       this.sumXX += reg.sumXX;
/* 192 */       this.sumYY += reg.sumYY;
/* 193 */       this.sumXY += reg.sumXY;
/*     */     } 
/*     */     
/* 196 */     this.sumX += reg.sumX;
/* 197 */     this.sumY += reg.sumY;
/* 198 */     this.n += reg.n;
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
/*     */   public void removeData(double x, double y) {
/* 215 */     if (this.n > 0L) {
/* 216 */       if (this.hasIntercept) {
/* 217 */         double fact1 = this.n - 1.0D;
/* 218 */         double fact2 = this.n / (this.n - 1.0D);
/* 219 */         double dx = x - this.xbar;
/* 220 */         double dy = y - this.ybar;
/* 221 */         this.sumXX -= dx * dx * fact2;
/* 222 */         this.sumYY -= dy * dy * fact2;
/* 223 */         this.sumXY -= dx * dy * fact2;
/* 224 */         this.xbar -= dx / fact1;
/* 225 */         this.ybar -= dy / fact1;
/*     */       } else {
/* 227 */         double fact1 = this.n - 1.0D;
/* 228 */         this.sumXX -= x * x;
/* 229 */         this.sumYY -= y * y;
/* 230 */         this.sumXY -= x * y;
/* 231 */         this.xbar -= x / fact1;
/* 232 */         this.ybar -= y / fact1;
/*     */       } 
/* 234 */       this.sumX -= x;
/* 235 */       this.sumY -= y;
/* 236 */       this.n--;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addData(double[][] data) throws ModelSpecificationException {
/* 259 */     for (int i = 0; i < data.length; i++) {
/* 260 */       if ((data[i]).length < 2) {
/* 261 */         throw new ModelSpecificationException(LocalizedFormats.INVALID_REGRESSION_OBSERVATION, new Object[] { Integer.valueOf((data[i]).length), Integer.valueOf(2) });
/*     */       }
/*     */       
/* 264 */       addData(data[i][0], data[i][1]);
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
/*     */   public void addObservation(double[] x, double y) throws ModelSpecificationException {
/* 278 */     if (x == null || x.length == 0) {
/* 279 */       throw new ModelSpecificationException(LocalizedFormats.INVALID_REGRESSION_OBSERVATION, new Object[] { Integer.valueOf((x != null) ? x.length : 0), Integer.valueOf(1) });
/*     */     }
/* 281 */     addData(x[0], y);
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
/*     */   public void addObservations(double[][] x, double[] y) throws ModelSpecificationException {
/* 295 */     if (x == null || y == null || x.length != y.length) {
/* 296 */       throw new ModelSpecificationException(LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, new Object[] { Integer.valueOf((x == null) ? 0 : x.length), Integer.valueOf((y == null) ? 0 : y.length) });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 301 */     boolean obsOk = true; int i;
/* 302 */     for (i = 0; i < x.length; i++) {
/* 303 */       if (x[i] == null || (x[i]).length == 0) {
/* 304 */         obsOk = false;
/*     */       }
/*     */     } 
/* 307 */     if (!obsOk) {
/* 308 */       throw new ModelSpecificationException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, new Object[] { Integer.valueOf(0), Integer.valueOf(1) });
/*     */     }
/*     */ 
/*     */     
/* 312 */     for (i = 0; i < x.length; i++) {
/* 313 */       addData(x[i][0], y[i]);
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
/*     */   
/*     */   public void removeData(double[][] data) {
/* 331 */     for (int i = 0; i < data.length && this.n > 0L; i++) {
/* 332 */       removeData(data[i][0], data[i][1]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 340 */     this.sumX = 0.0D;
/* 341 */     this.sumXX = 0.0D;
/* 342 */     this.sumY = 0.0D;
/* 343 */     this.sumYY = 0.0D;
/* 344 */     this.sumXY = 0.0D;
/* 345 */     this.n = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 354 */     return this.n;
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
/*     */   public double predict(double x) {
/* 375 */     double b1 = getSlope();
/* 376 */     if (this.hasIntercept) {
/* 377 */       return getIntercept(b1) + b1 * x;
/*     */     }
/* 379 */     return b1 * x;
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
/*     */   public double getIntercept() {
/* 402 */     return this.hasIntercept ? getIntercept(getSlope()) : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIntercept() {
/* 412 */     return this.hasIntercept;
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
/*     */   public double getSlope() {
/* 432 */     if (this.n < 2L) {
/* 433 */       return Double.NaN;
/*     */     }
/* 435 */     if (FastMath.abs(this.sumXX) < 4.9E-323D) {
/* 436 */       return Double.NaN;
/*     */     }
/* 438 */     return this.sumXY / this.sumXX;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSumSquaredErrors() {
/* 471 */     return FastMath.max(0.0D, this.sumYY - this.sumXY * this.sumXY / this.sumXX);
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
/*     */   public double getTotalSumSquares() {
/* 485 */     if (this.n < 2L) {
/* 486 */       return Double.NaN;
/*     */     }
/* 488 */     return this.sumYY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXSumSquares() {
/* 499 */     if (this.n < 2L) {
/* 500 */       return Double.NaN;
/*     */     }
/* 502 */     return this.sumXX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSumOfCrossProducts() {
/* 511 */     return this.sumXY;
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
/*     */   public double getRegressionSumSquares() {
/* 531 */     return getRegressionSumSquares(getSlope());
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
/*     */   public double getMeanSquareError() {
/* 545 */     if (this.n < 3L) {
/* 546 */       return Double.NaN;
/*     */     }
/* 548 */     return this.hasIntercept ? (getSumSquaredErrors() / (this.n - 2L)) : (getSumSquaredErrors() / (this.n - 1L));
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
/*     */   public double getR() {
/* 566 */     double b1 = getSlope();
/* 567 */     double result = FastMath.sqrt(getRSquare());
/* 568 */     if (b1 < 0.0D) {
/* 569 */       result = -result;
/*     */     }
/* 571 */     return result;
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
/*     */   public double getRSquare() {
/* 589 */     double ssto = getTotalSumSquares();
/* 590 */     return (ssto - getSumSquaredErrors()) / ssto;
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
/*     */   public double getInterceptStdErr() {
/* 606 */     if (!this.hasIntercept) {
/* 607 */       return Double.NaN;
/*     */     }
/* 609 */     return FastMath.sqrt(getMeanSquareError() * (1.0D / this.n + this.xbar * this.xbar / this.sumXX));
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
/*     */   public double getSlopeStdErr() {
/* 625 */     return FastMath.sqrt(getMeanSquareError() / this.sumXX);
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
/*     */   public double getSlopeConfidenceInterval() throws OutOfRangeException {
/* 651 */     return getSlopeConfidenceInterval(0.05D);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSlopeConfidenceInterval(double alpha) throws OutOfRangeException {
/* 687 */     if (this.n < 3L) {
/* 688 */       return Double.NaN;
/*     */     }
/* 690 */     if (alpha >= 1.0D || alpha <= 0.0D) {
/* 691 */       throw new OutOfRangeException(LocalizedFormats.SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */ 
/*     */     
/* 695 */     TDistribution distribution = new TDistribution((this.n - 2L));
/* 696 */     return getSlopeStdErr() * distribution.inverseCumulativeProbability(1.0D - alpha / 2.0D);
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
/*     */   public double getSignificance() {
/* 723 */     if (this.n < 3L) {
/* 724 */       return Double.NaN;
/*     */     }
/*     */     
/* 727 */     TDistribution distribution = new TDistribution((this.n - 2L));
/* 728 */     return 2.0D * (1.0D - distribution.cumulativeProbability(FastMath.abs(getSlope()) / getSlopeStdErr()));
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
/*     */   private double getIntercept(double slope) {
/* 743 */     if (this.hasIntercept) {
/* 744 */       return (this.sumY - slope * this.sumX) / this.n;
/*     */     }
/* 746 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getRegressionSumSquares(double slope) {
/* 756 */     return slope * slope * this.sumXX;
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
/*     */   public RegressionResults regress() throws ModelSpecificationException, NoDataException {
/* 772 */     if (this.hasIntercept) {
/* 773 */       if (this.n < 3L) {
/* 774 */         throw new NoDataException(LocalizedFormats.NOT_ENOUGH_DATA_REGRESSION);
/*     */       }
/* 776 */       if (FastMath.abs(this.sumXX) > Precision.SAFE_MIN) {
/* 777 */         double[] arrayOfDouble3 = { getIntercept(), getSlope() };
/* 778 */         double mse = getMeanSquareError();
/* 779 */         double _syy = this.sumYY + this.sumY * this.sumY / this.n;
/* 780 */         double[] arrayOfDouble4 = { mse * (this.xbar * this.xbar / this.sumXX + 1.0D / this.n), -this.xbar * mse / this.sumXX, mse / this.sumXX };
/* 781 */         return new RegressionResults(arrayOfDouble3, new double[][] { arrayOfDouble4 }, true, this.n, 2, this.sumY, _syy, getSumSquaredErrors(), true, false);
/*     */       } 
/*     */       
/* 784 */       double[] arrayOfDouble1 = { this.sumY / this.n, Double.NaN };
/*     */       
/* 786 */       double[] arrayOfDouble2 = { this.ybar / (this.n - 1.0D), Double.NaN, Double.NaN };
/* 787 */       return new RegressionResults(arrayOfDouble1, new double[][] { arrayOfDouble2 }, true, this.n, 1, this.sumY, this.sumYY, getSumSquaredErrors(), true, false);
/*     */     } 
/*     */ 
/*     */     
/* 791 */     if (this.n < 2L) {
/* 792 */       throw new NoDataException(LocalizedFormats.NOT_ENOUGH_DATA_REGRESSION);
/*     */     }
/* 794 */     if (!Double.isNaN(this.sumXX)) {
/* 795 */       double[] arrayOfDouble1 = { getMeanSquareError() / this.sumXX };
/* 796 */       double[] arrayOfDouble2 = { this.sumXY / this.sumXX };
/* 797 */       return new RegressionResults(arrayOfDouble2, new double[][] { arrayOfDouble1 }, true, this.n, 1, this.sumY, this.sumYY, getSumSquaredErrors(), false, false);
/*     */     } 
/*     */     
/* 800 */     double[] vcv = { Double.NaN };
/* 801 */     double[] params = { Double.NaN };
/* 802 */     return new RegressionResults(params, new double[][] { vcv }, true, this.n, 1, Double.NaN, Double.NaN, Double.NaN, false, false);
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
/*     */   public RegressionResults regress(int[] variablesToInclude) throws MathIllegalArgumentException {
/* 817 */     if (variablesToInclude == null || variablesToInclude.length == 0) {
/* 818 */       throw new MathIllegalArgumentException(LocalizedFormats.ARRAY_ZERO_LENGTH_OR_NULL_NOT_ALLOWED, new Object[0]);
/*     */     }
/* 820 */     if (variablesToInclude.length > 2 || (variablesToInclude.length > 1 && !this.hasIntercept)) {
/* 821 */       throw new ModelSpecificationException(LocalizedFormats.ARRAY_SIZE_EXCEEDS_MAX_VARIABLES, new Object[] { Integer.valueOf((variablesToInclude.length > 1 && !this.hasIntercept) ? 1 : 2) });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 826 */     if (this.hasIntercept) {
/* 827 */       if (variablesToInclude.length == 2) {
/* 828 */         if (variablesToInclude[0] == 1)
/* 829 */           throw new ModelSpecificationException(LocalizedFormats.NOT_INCREASING_SEQUENCE, new Object[0]); 
/* 830 */         if (variablesToInclude[0] != 0) {
/* 831 */           throw new OutOfRangeException(Integer.valueOf(variablesToInclude[0]), Integer.valueOf(0), Integer.valueOf(1));
/*     */         }
/* 833 */         if (variablesToInclude[1] != 1) {
/* 834 */           throw new OutOfRangeException(Integer.valueOf(variablesToInclude[0]), Integer.valueOf(0), Integer.valueOf(1));
/*     */         }
/* 836 */         return regress();
/*     */       } 
/* 838 */       if (variablesToInclude[0] != 1 && variablesToInclude[0] != 0) {
/* 839 */         throw new OutOfRangeException(Integer.valueOf(variablesToInclude[0]), Integer.valueOf(0), Integer.valueOf(1));
/*     */       }
/* 841 */       double _mean = this.sumY * this.sumY / this.n;
/* 842 */       double _syy = this.sumYY + _mean;
/* 843 */       if (variablesToInclude[0] == 0) {
/*     */         
/* 845 */         double[] vcv = { this.sumYY / ((this.n - 1L) * this.n) };
/* 846 */         double[] params = { this.ybar };
/* 847 */         return new RegressionResults(params, new double[][] { vcv }, true, this.n, 1, this.sumY, _syy + _mean, this.sumYY, true, false);
/*     */       } 
/*     */ 
/*     */       
/* 851 */       if (variablesToInclude[0] == 1)
/*     */       {
/* 853 */         double _sxx = this.sumXX + this.sumX * this.sumX / this.n;
/* 854 */         double _sxy = this.sumXY + this.sumX * this.sumY / this.n;
/* 855 */         double _sse = FastMath.max(0.0D, _syy - _sxy * _sxy / _sxx);
/* 856 */         double _mse = _sse / (this.n - 1L);
/* 857 */         if (!Double.isNaN(_sxx)) {
/* 858 */           double[] arrayOfDouble1 = { _mse / _sxx };
/* 859 */           double[] arrayOfDouble2 = { _sxy / _sxx };
/* 860 */           return new RegressionResults(arrayOfDouble2, new double[][] { arrayOfDouble1 }, true, this.n, 1, this.sumY, _syy, _sse, false, false);
/*     */         } 
/*     */ 
/*     */         
/* 864 */         double[] vcv = { Double.NaN };
/* 865 */         double[] params = { Double.NaN };
/* 866 */         return new RegressionResults(params, new double[][] { vcv }, true, this.n, 1, Double.NaN, Double.NaN, Double.NaN, false, false);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 873 */       if (variablesToInclude[0] != 0) {
/* 874 */         throw new OutOfRangeException(Integer.valueOf(variablesToInclude[0]), Integer.valueOf(0), Integer.valueOf(0));
/*     */       }
/* 876 */       return regress();
/*     */     } 
/*     */     
/* 879 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/regression/SimpleRegression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */