/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TriangularDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 20120112L;
/*     */   private final double a;
/*     */   private final double b;
/*     */   private final double c;
/*     */   private final double solverAbsoluteAccuracy;
/*     */   
/*     */   public TriangularDistribution(double a, double c, double b) throws NumberIsTooLargeException, NumberIsTooSmallException {
/*  67 */     this((RandomGenerator)new Well19937c(), a, c, b);
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
/*     */   public TriangularDistribution(RandomGenerator rng, double a, double c, double b) throws NumberIsTooLargeException, NumberIsTooSmallException {
/*  86 */     super(rng);
/*     */     
/*  88 */     if (a >= b) {
/*  89 */       throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Double.valueOf(a), Double.valueOf(b), false);
/*     */     }
/*     */ 
/*     */     
/*  93 */     if (c < a) {
/*  94 */       throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_TOO_SMALL, Double.valueOf(c), Double.valueOf(a), true);
/*     */     }
/*     */     
/*  97 */     if (c > b) {
/*  98 */       throw new NumberIsTooLargeException(LocalizedFormats.NUMBER_TOO_LARGE, Double.valueOf(c), Double.valueOf(b), true);
/*     */     }
/*     */ 
/*     */     
/* 102 */     this.a = a;
/* 103 */     this.c = c;
/* 104 */     this.b = b;
/* 105 */     this.solverAbsoluteAccuracy = FastMath.max(FastMath.ulp(a), FastMath.ulp(b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMode() {
/* 114 */     return this.c;
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
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 132 */     return this.solverAbsoluteAccuracy;
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
/*     */   public double density(double x) {
/* 148 */     if (x < this.a) {
/* 149 */       return 0.0D;
/*     */     }
/* 151 */     if (this.a <= x && x < this.c) {
/* 152 */       double divident = 2.0D * (x - this.a);
/* 153 */       double divisor = (this.b - this.a) * (this.c - this.a);
/* 154 */       return divident / divisor;
/*     */     } 
/* 156 */     if (x == this.c) {
/* 157 */       return 2.0D / (this.b - this.a);
/*     */     }
/* 159 */     if (this.c < x && x <= this.b) {
/* 160 */       double divident = 2.0D * (this.b - x);
/* 161 */       double divisor = (this.b - this.a) * (this.b - this.c);
/* 162 */       return divident / divisor;
/*     */     } 
/* 164 */     return 0.0D;
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
/*     */   public double cumulativeProbability(double x) {
/* 181 */     if (x < this.a) {
/* 182 */       return 0.0D;
/*     */     }
/* 184 */     if (this.a <= x && x < this.c) {
/* 185 */       double divident = (x - this.a) * (x - this.a);
/* 186 */       double divisor = (this.b - this.a) * (this.c - this.a);
/* 187 */       return divident / divisor;
/*     */     } 
/* 189 */     if (x == this.c) {
/* 190 */       return (this.c - this.a) / (this.b - this.a);
/*     */     }
/* 192 */     if (this.c < x && x <= this.b) {
/* 193 */       double divident = (this.b - x) * (this.b - x);
/* 194 */       double divisor = (this.b - this.a) * (this.b - this.c);
/* 195 */       return 1.0D - divident / divisor;
/*     */     } 
/* 197 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 207 */     return (this.a + this.b + this.c) / 3.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 217 */     return (this.a * this.a + this.b * this.b + this.c * this.c - this.a * this.b - this.a * this.c - this.b * this.c) / 18.0D;
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
/*     */   public double getSupportLowerBound() {
/* 229 */     return this.a;
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
/*     */   public double getSupportUpperBound() {
/* 241 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 251 */     return true;
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
/* 262 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 269 */     if (p < 0.0D || p > 1.0D) {
/* 270 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/* 272 */     if (p == 0.0D) {
/* 273 */       return this.a;
/*     */     }
/* 275 */     if (p == 1.0D) {
/* 276 */       return this.b;
/*     */     }
/* 278 */     if (p < (this.c - this.a) / (this.b - this.a)) {
/* 279 */       return this.a + FastMath.sqrt(p * (this.b - this.a) * (this.c - this.a));
/*     */     }
/* 281 */     return this.b - FastMath.sqrt((1.0D - p) * (this.b - this.a) * (this.b - this.c));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/TriangularDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */