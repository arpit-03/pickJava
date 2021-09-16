/*     */ package org.apache.commons.math3.stat.inference;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.math3.distribution.FDistribution;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
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
/*     */ public class OneWayAnova
/*     */ {
/*     */   public double anovaFValue(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException {
/*  90 */     AnovaStats a = anovaStats(categoryData);
/*  91 */     return a.F;
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
/*     */   public double anovaPValue(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException, ConvergenceException, MaxCountExceededException {
/* 127 */     AnovaStats a = anovaStats(categoryData);
/*     */ 
/*     */     
/* 130 */     FDistribution fdist = new FDistribution(null, a.dfbg, a.dfwg);
/* 131 */     return 1.0D - fdist.cumulativeProbability(a.F);
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
/*     */   public double anovaPValue(Collection<SummaryStatistics> categoryData, boolean allowOneElementData) throws NullArgumentException, DimensionMismatchException, ConvergenceException, MaxCountExceededException {
/* 170 */     AnovaStats a = anovaStats(categoryData, allowOneElementData);
/*     */     
/* 172 */     FDistribution fdist = new FDistribution(null, a.dfbg, a.dfwg);
/* 173 */     return 1.0D - fdist.cumulativeProbability(a.F);
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
/*     */   private AnovaStats anovaStats(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException {
/* 195 */     MathUtils.checkNotNull(categoryData);
/*     */     
/* 197 */     Collection<SummaryStatistics> categoryDataSummaryStatistics = new ArrayList<SummaryStatistics>(categoryData.size());
/*     */ 
/*     */ 
/*     */     
/* 201 */     for (double[] data : categoryData) {
/* 202 */       SummaryStatistics dataSummaryStatistics = new SummaryStatistics();
/* 203 */       categoryDataSummaryStatistics.add(dataSummaryStatistics);
/* 204 */       for (double val : data) {
/* 205 */         dataSummaryStatistics.addValue(val);
/*     */       }
/*     */     } 
/*     */     
/* 209 */     return anovaStats(categoryDataSummaryStatistics, false);
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
/*     */   public boolean anovaTest(Collection<double[]> categoryData, double alpha) throws NullArgumentException, DimensionMismatchException, OutOfRangeException, ConvergenceException, MaxCountExceededException {
/* 252 */     if (alpha <= 0.0D || alpha > 0.5D) {
/* 253 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5D));
/*     */     }
/*     */ 
/*     */     
/* 257 */     return (anovaPValue(categoryData) < alpha);
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
/*     */   private AnovaStats anovaStats(Collection<SummaryStatistics> categoryData, boolean allowOneElementData) throws NullArgumentException, DimensionMismatchException {
/* 278 */     MathUtils.checkNotNull(categoryData);
/*     */     
/* 280 */     if (!allowOneElementData) {
/*     */       
/* 282 */       if (categoryData.size() < 2) {
/* 283 */         throw new DimensionMismatchException(LocalizedFormats.TWO_OR_MORE_CATEGORIES_REQUIRED, categoryData.size(), 2);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 288 */       for (SummaryStatistics array : categoryData) {
/* 289 */         if (array.getN() <= 1L) {
/* 290 */           throw new DimensionMismatchException(LocalizedFormats.TWO_OR_MORE_VALUES_IN_CATEGORY_REQUIRED, (int)array.getN(), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 296 */     int dfwg = 0;
/* 297 */     double sswg = 0.0D;
/* 298 */     double totsum = 0.0D;
/* 299 */     double totsumsq = 0.0D;
/* 300 */     int totnum = 0;
/*     */     
/* 302 */     for (SummaryStatistics data : categoryData) {
/*     */       
/* 304 */       double sum = data.getSum();
/* 305 */       double sumsq = data.getSumsq();
/* 306 */       int num = (int)data.getN();
/* 307 */       totnum += num;
/* 308 */       totsum += sum;
/* 309 */       totsumsq += sumsq;
/*     */       
/* 311 */       dfwg += num - 1;
/* 312 */       double ss = sumsq - sum * sum / num;
/* 313 */       sswg += ss;
/*     */     } 
/*     */     
/* 316 */     double sst = totsumsq - totsum * totsum / totnum;
/* 317 */     double ssbg = sst - sswg;
/* 318 */     int dfbg = categoryData.size() - 1;
/* 319 */     double msbg = ssbg / dfbg;
/* 320 */     double mswg = sswg / dfwg;
/* 321 */     double F = msbg / mswg;
/*     */     
/* 323 */     return new AnovaStats(dfbg, dfwg, F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class AnovaStats
/*     */   {
/*     */     private final int dfbg;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int dfwg;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private AnovaStats(int dfbg, int dfwg, double F) {
/* 349 */       this.dfbg = dfbg;
/* 350 */       this.dfwg = dfwg;
/* 351 */       this.F = F;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/inference/OneWayAnova.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */