/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.fraction.BigFraction;
/*     */ import org.apache.commons.math3.fraction.BigFractionField;
/*     */ import org.apache.commons.math3.fraction.FractionConversionException;
/*     */ import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.FieldMatrix;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KolmogorovSmirnovDistribution
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4670676796862967187L;
/*     */   private int n;
/*     */   
/*     */   public KolmogorovSmirnovDistribution(int n) throws NotStrictlyPositiveException {
/*  88 */     if (n <= 0) {
/*  89 */       throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_NUMBER_OF_SAMPLES, Integer.valueOf(n));
/*     */     }
/*     */     
/*  92 */     this.n = n;
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
/*     */   public double cdf(double d) throws MathArithmeticException {
/* 111 */     return cdf(d, false);
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
/*     */   public double cdfExact(double d) throws MathArithmeticException {
/* 131 */     return cdf(d, true);
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
/*     */   public double cdf(double d, boolean exact) throws MathArithmeticException {
/* 153 */     double ninv = 1.0D / this.n;
/* 154 */     double ninvhalf = 0.5D * ninv;
/*     */     
/* 156 */     if (d <= ninvhalf)
/*     */     {
/* 158 */       return 0.0D;
/*     */     }
/* 160 */     if (ninvhalf < d && d <= ninv) {
/*     */       
/* 162 */       double res = 1.0D;
/* 163 */       double f = 2.0D * d - ninv;
/*     */ 
/*     */       
/* 166 */       for (int i = 1; i <= this.n; i++) {
/* 167 */         res *= i * f;
/*     */       }
/*     */       
/* 170 */       return res;
/*     */     } 
/* 172 */     if (1.0D - ninv <= d && d < 1.0D)
/*     */     {
/* 174 */       return 1.0D - 2.0D * FastMath.pow(1.0D - d, this.n);
/*     */     }
/* 176 */     if (1.0D <= d)
/*     */     {
/* 178 */       return 1.0D;
/*     */     }
/*     */     
/* 181 */     return exact ? exactK(d) : roundedK(d);
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
/*     */   private double exactK(double d) throws MathArithmeticException {
/* 198 */     int k = (int)FastMath.ceil(this.n * d);
/*     */     
/* 200 */     FieldMatrix<BigFraction> H = createH(d);
/* 201 */     FieldMatrix<BigFraction> Hpower = H.power(this.n);
/*     */     
/* 203 */     BigFraction pFrac = (BigFraction)Hpower.getEntry(k - 1, k - 1);
/*     */     
/* 205 */     for (int i = 1; i <= this.n; i++) {
/* 206 */       pFrac = pFrac.multiply(i).divide(this.n);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     return pFrac.bigDecimalValue(20, 4).doubleValue();
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
/*     */   private double roundedK(double d) throws MathArithmeticException {
/* 230 */     int k = (int)FastMath.ceil(this.n * d);
/* 231 */     FieldMatrix<BigFraction> HBigFraction = createH(d);
/* 232 */     int m = HBigFraction.getRowDimension();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     Array2DRowRealMatrix array2DRowRealMatrix = new Array2DRowRealMatrix(m, m);
/*     */     
/* 240 */     for (int i = 0; i < m; i++) {
/* 241 */       for (int n = 0; n < m; n++) {
/* 242 */         array2DRowRealMatrix.setEntry(i, n, ((BigFraction)HBigFraction.getEntry(i, n)).doubleValue());
/*     */       }
/*     */     } 
/*     */     
/* 246 */     RealMatrix Hpower = array2DRowRealMatrix.power(this.n);
/*     */     
/* 248 */     double pFrac = Hpower.getEntry(k - 1, k - 1);
/*     */     
/* 250 */     for (int j = 1; j <= this.n; j++) {
/* 251 */       pFrac *= j / this.n;
/*     */     }
/*     */     
/* 254 */     return pFrac;
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
/*     */   private FieldMatrix<BigFraction> createH(double d) throws NumberIsTooLargeException, FractionConversionException {
/* 271 */     int k = (int)FastMath.ceil(this.n * d);
/*     */     
/* 273 */     int m = 2 * k - 1;
/* 274 */     double hDouble = k - this.n * d;
/*     */     
/* 276 */     if (hDouble >= 1.0D) {
/* 277 */       throw new NumberIsTooLargeException(Double.valueOf(hDouble), Double.valueOf(1.0D), false);
/*     */     }
/*     */     
/* 280 */     BigFraction h = null;
/*     */     
/*     */     try {
/* 283 */       h = new BigFraction(hDouble, 1.0E-20D, 10000);
/* 284 */     } catch (FractionConversionException e1) {
/*     */       try {
/* 286 */         h = new BigFraction(hDouble, 1.0E-10D, 10000);
/* 287 */       } catch (FractionConversionException e2) {
/* 288 */         h = new BigFraction(hDouble, 1.0E-5D, 10000);
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     BigFraction[][] Hdata = new BigFraction[m][m];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     for (int i = 0; i < m; i++) {
/* 298 */       for (int n = 0; n < m; n++) {
/* 299 */         if (i - n + 1 < 0) {
/* 300 */           Hdata[i][n] = BigFraction.ZERO;
/*     */         } else {
/* 302 */           Hdata[i][n] = BigFraction.ONE;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     BigFraction[] hPowers = new BigFraction[m];
/* 312 */     hPowers[0] = h; int j;
/* 313 */     for (j = 1; j < m; j++) {
/* 314 */       hPowers[j] = h.multiply(hPowers[j - 1]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     for (j = 0; j < m; j++) {
/* 321 */       Hdata[j][0] = Hdata[j][0].subtract(hPowers[j]);
/* 322 */       Hdata[m - 1][j] = Hdata[m - 1][j].subtract(hPowers[m - j - 1]);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     if (h.compareTo(BigFraction.ONE_HALF) == 1) {
/* 331 */       Hdata[m - 1][0] = Hdata[m - 1][0].add(h.multiply(2).subtract(1).pow(m));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     for (j = 0; j < m; j++) {
/* 346 */       for (int n = 0; n < j + 1; n++) {
/* 347 */         if (j - n + 1 > 0) {
/* 348 */           for (int g = 2; g <= j - n + 1; g++) {
/* 349 */             Hdata[j][n] = Hdata[j][n].divide(g);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 355 */     return (FieldMatrix<BigFraction>)new Array2DRowFieldMatrix((Field)BigFractionField.getInstance(), (FieldElement[][])Hdata);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/KolmogorovSmirnovDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */