/*     */ package org.apache.commons.math3.stat.inference;
/*     */ 
/*     */ import org.apache.commons.math3.distribution.NormalDistribution;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
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
/*     */ public class MannWhitneyUTest
/*     */ {
/*     */   private NaturalRanking naturalRanking;
/*     */   
/*     */   public MannWhitneyUTest() {
/*  44 */     this.naturalRanking = new NaturalRanking(NaNStrategy.FIXED, TiesStrategy.AVERAGE);
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
/*     */   public MannWhitneyUTest(NaNStrategy nanStrategy, TiesStrategy tiesStrategy) {
/*  59 */     this.naturalRanking = new NaturalRanking(nanStrategy, tiesStrategy);
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
/*     */   private void ensureDataConformance(double[] x, double[] y) throws NullArgumentException, NoDataException {
/*  73 */     if (x == null || y == null)
/*     */     {
/*  75 */       throw new NullArgumentException();
/*     */     }
/*  77 */     if (x.length == 0 || y.length == 0)
/*     */     {
/*  79 */       throw new NoDataException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] concatenateSamples(double[] x, double[] y) {
/*  89 */     double[] z = new double[x.length + y.length];
/*     */     
/*  91 */     System.arraycopy(x, 0, z, 0, x.length);
/*  92 */     System.arraycopy(y, 0, z, x.length, y.length);
/*     */     
/*  94 */     return z;
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
/*     */   public double mannWhitneyU(double[] x, double[] y) throws NullArgumentException, NoDataException {
/* 128 */     ensureDataConformance(x, y);
/*     */     
/* 130 */     double[] z = concatenateSamples(x, y);
/* 131 */     double[] ranks = this.naturalRanking.rank(z);
/*     */     
/* 133 */     double sumRankX = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     for (int i = 0; i < x.length; i++) {
/* 140 */       sumRankX += ranks[i];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     double U1 = sumRankX - (x.length * (x.length + 1) / 2L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     double U2 = (x.length * y.length) - U1;
/*     */     
/* 154 */     return FastMath.max(U1, U2);
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
/*     */   private double calculateAsymptoticPValue(double Umin, int n1, int n2) throws ConvergenceException, MaxCountExceededException {
/* 175 */     long n1n2prod = n1 * n2;
/*     */ 
/*     */     
/* 178 */     double EU = n1n2prod / 2.0D;
/* 179 */     double VarU = (n1n2prod * (n1 + n2 + 1)) / 12.0D;
/*     */     
/* 181 */     double z = (Umin - EU) / FastMath.sqrt(VarU);
/*     */ 
/*     */ 
/*     */     
/* 185 */     NormalDistribution standardNormal = new NormalDistribution(null, 0.0D, 1.0D);
/*     */     
/* 187 */     return 2.0D * standardNormal.cumulativeProbability(z);
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
/*     */   public double mannWhitneyUTest(double[] x, double[] y) throws NullArgumentException, NoDataException, ConvergenceException, MaxCountExceededException {
/* 226 */     ensureDataConformance(x, y);
/*     */     
/* 228 */     double Umax = mannWhitneyU(x, y);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     double Umin = (x.length * y.length) - Umax;
/*     */     
/* 235 */     return calculateAsymptoticPValue(Umin, x.length, y.length);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/inference/MannWhitneyUTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */