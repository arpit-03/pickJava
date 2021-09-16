/*     */ package org.apache.commons.math3.stat.inference;
/*     */ 
/*     */ import org.apache.commons.math3.distribution.NormalDistribution;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.stat.ranking.NaNStrategy;
/*     */ import org.apache.commons.math3.stat.ranking.NaturalRanking;
/*     */ import org.apache.commons.math3.stat.ranking.TiesStrategy;
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
/*     */ public class WilcoxonSignedRankTest
/*     */ {
/*     */   private NaturalRanking naturalRanking;
/*     */   
/*     */   public WilcoxonSignedRankTest() {
/*  46 */     this.naturalRanking = new NaturalRanking(NaNStrategy.FIXED, TiesStrategy.AVERAGE);
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
/*     */   public WilcoxonSignedRankTest(NaNStrategy nanStrategy, TiesStrategy tiesStrategy) {
/*  61 */     this.naturalRanking = new NaturalRanking(nanStrategy, tiesStrategy);
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
/*     */   private void ensureDataConformance(double[] x, double[] y) throws NullArgumentException, NoDataException, DimensionMismatchException {
/*  77 */     if (x == null || y == null)
/*     */     {
/*  79 */       throw new NullArgumentException();
/*     */     }
/*  81 */     if (x.length == 0 || y.length == 0)
/*     */     {
/*  83 */       throw new NoDataException();
/*     */     }
/*  85 */     if (y.length != x.length) {
/*  86 */       throw new DimensionMismatchException(y.length, x.length);
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
/*     */   private double[] calculateDifferences(double[] x, double[] y) {
/*  99 */     double[] z = new double[x.length];
/*     */     
/* 101 */     for (int i = 0; i < x.length; i++) {
/* 102 */       z[i] = y[i] - x[i];
/*     */     }
/*     */     
/* 105 */     return z;
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
/*     */   private double[] calculateAbsoluteDifferences(double[] z) throws NullArgumentException, NoDataException {
/* 119 */     if (z == null) {
/* 120 */       throw new NullArgumentException();
/*     */     }
/*     */     
/* 123 */     if (z.length == 0) {
/* 124 */       throw new NoDataException();
/*     */     }
/*     */     
/* 127 */     double[] zAbs = new double[z.length];
/*     */     
/* 129 */     for (int i = 0; i < z.length; i++) {
/* 130 */       zAbs[i] = FastMath.abs(z[i]);
/*     */     }
/*     */     
/* 133 */     return zAbs;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double wilcoxonSignedRank(double[] x, double[] y) throws NullArgumentException, NoDataException, DimensionMismatchException {
/* 174 */     ensureDataConformance(x, y);
/*     */ 
/*     */ 
/*     */     
/* 178 */     double[] z = calculateDifferences(x, y);
/* 179 */     double[] zAbs = calculateAbsoluteDifferences(z);
/*     */     
/* 181 */     double[] ranks = this.naturalRanking.rank(zAbs);
/*     */     
/* 183 */     double Wplus = 0.0D;
/*     */     
/* 185 */     for (int i = 0; i < z.length; i++) {
/* 186 */       if (z[i] > 0.0D) {
/* 187 */         Wplus += ranks[i];
/*     */       }
/*     */     } 
/*     */     
/* 191 */     int N = x.length;
/* 192 */     double Wminus = (N * (N + 1)) / 2.0D - Wplus;
/*     */     
/* 194 */     return FastMath.max(Wplus, Wminus);
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
/*     */   private double calculateExactPValue(double Wmax, int N) {
/* 210 */     int m = 1 << N;
/*     */     
/* 212 */     int largerRankSums = 0;
/*     */     
/* 214 */     for (int i = 0; i < m; i++) {
/* 215 */       int rankSum = 0;
/*     */ 
/*     */       
/* 218 */       for (int j = 0; j < N; j++) {
/*     */ 
/*     */         
/* 221 */         if ((i >> j & 0x1) == 1) {
/* 222 */           rankSum += j + 1;
/*     */         }
/*     */       } 
/*     */       
/* 226 */       if (rankSum >= Wmax) {
/* 227 */         largerRankSums++;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     return 2.0D * largerRankSums / m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double calculateAsymptoticPValue(double Wmin, int N) {
/* 245 */     double ES = (N * (N + 1)) / 4.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     double VarS = ES * (2 * N + 1) / 6.0D;
/*     */ 
/*     */     
/* 253 */     double z = (Wmin - ES - 0.5D) / FastMath.sqrt(VarS);
/*     */ 
/*     */ 
/*     */     
/* 257 */     NormalDistribution standardNormal = new NormalDistribution(null, 0.0D, 1.0D);
/*     */     
/* 259 */     return 2.0D * standardNormal.cumulativeProbability(z);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double wilcoxonSignedRankTest(double[] x, double[] y, boolean exactPValue) throws NullArgumentException, NoDataException, DimensionMismatchException, NumberIsTooLargeException, ConvergenceException, MaxCountExceededException {
/* 309 */     ensureDataConformance(x, y);
/*     */     
/* 311 */     int N = x.length;
/* 312 */     double Wmax = wilcoxonSignedRank(x, y);
/*     */     
/* 314 */     if (exactPValue && N > 30) {
/* 315 */       throw new NumberIsTooLargeException(Integer.valueOf(N), Integer.valueOf(30), true);
/*     */     }
/*     */     
/* 318 */     if (exactPValue) {
/* 319 */       return calculateExactPValue(Wmax, N);
/*     */     }
/* 321 */     double Wmin = (N * (N + 1)) / 2.0D - Wmax;
/* 322 */     return calculateAsymptoticPValue(Wmin, N);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/inference/WilcoxonSignedRankTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */