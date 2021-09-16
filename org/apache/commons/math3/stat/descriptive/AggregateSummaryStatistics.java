/*     */ package org.apache.commons.math3.stat.descriptive;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AggregateSummaryStatistics
/*     */   implements StatisticalSummary, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8207112444016386906L;
/*     */   private final SummaryStatistics statisticsPrototype;
/*     */   private final SummaryStatistics statistics;
/*     */   
/*     */   public AggregateSummaryStatistics() {
/*  78 */     this(new SummaryStatistics());
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
/*     */   public AggregateSummaryStatistics(SummaryStatistics prototypeStatistics) throws NullArgumentException {
/*  99 */     this(prototypeStatistics, (prototypeStatistics == null) ? null : new SummaryStatistics(prototypeStatistics));
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
/*     */   public AggregateSummaryStatistics(SummaryStatistics prototypeStatistics, SummaryStatistics initialStatistics) {
/* 125 */     this.statisticsPrototype = (prototypeStatistics == null) ? new SummaryStatistics() : prototypeStatistics;
/*     */     
/* 127 */     this.statistics = (initialStatistics == null) ? new SummaryStatistics() : initialStatistics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMax() {
/* 138 */     synchronized (this.statistics) {
/* 139 */       return this.statistics.getMax();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMean() {
/* 149 */     synchronized (this.statistics) {
/* 150 */       return this.statistics.getMean();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMin() {
/* 161 */     synchronized (this.statistics) {
/* 162 */       return this.statistics.getMin();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 172 */     synchronized (this.statistics) {
/* 173 */       return this.statistics.getN();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStandardDeviation() {
/* 184 */     synchronized (this.statistics) {
/* 185 */       return this.statistics.getStandardDeviation();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSum() {
/* 195 */     synchronized (this.statistics) {
/* 196 */       return this.statistics.getSum();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getVariance() {
/* 207 */     synchronized (this.statistics) {
/* 208 */       return this.statistics.getVariance();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSumOfLogs() {
/* 219 */     synchronized (this.statistics) {
/* 220 */       return this.statistics.getSumOfLogs();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGeometricMean() {
/* 231 */     synchronized (this.statistics) {
/* 232 */       return this.statistics.getGeometricMean();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSumsq() {
/* 243 */     synchronized (this.statistics) {
/* 244 */       return this.statistics.getSumsq();
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
/*     */   public double getSecondMoment() {
/* 257 */     synchronized (this.statistics) {
/* 258 */       return this.statistics.getSecondMoment();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatisticalSummary getSummary() {
/* 269 */     synchronized (this.statistics) {
/* 270 */       return new StatisticalSummaryValues(getMean(), getVariance(), getN(), getMax(), getMin(), getSum());
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
/*     */   public SummaryStatistics createContributingStatistics() {
/* 284 */     SummaryStatistics contributingStatistics = new AggregatingSummaryStatistics(this.statistics);
/*     */ 
/*     */ 
/*     */     
/* 288 */     SummaryStatistics.copy(this.statisticsPrototype, contributingStatistics);
/*     */     
/* 290 */     return contributingStatistics;
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
/*     */   public static StatisticalSummaryValues aggregate(Collection<? extends StatisticalSummary> statistics) {
/*     */     double variance;
/* 306 */     if (statistics == null) {
/* 307 */       return null;
/*     */     }
/* 309 */     Iterator<? extends StatisticalSummary> iterator = statistics.iterator();
/* 310 */     if (!iterator.hasNext()) {
/* 311 */       return null;
/*     */     }
/* 313 */     StatisticalSummary current = iterator.next();
/* 314 */     long n = current.getN();
/* 315 */     double min = current.getMin();
/* 316 */     double sum = current.getSum();
/* 317 */     double max = current.getMax();
/* 318 */     double var = current.getVariance();
/* 319 */     double m2 = var * (n - 1.0D);
/* 320 */     double mean = current.getMean();
/* 321 */     while (iterator.hasNext()) {
/* 322 */       current = iterator.next();
/* 323 */       if (current.getMin() < min || Double.isNaN(min)) {
/* 324 */         min = current.getMin();
/*     */       }
/* 326 */       if (current.getMax() > max || Double.isNaN(max)) {
/* 327 */         max = current.getMax();
/*     */       }
/* 329 */       sum += current.getSum();
/* 330 */       double oldN = n;
/* 331 */       double curN = current.getN();
/* 332 */       n = (long)(n + curN);
/* 333 */       double meanDiff = current.getMean() - mean;
/* 334 */       mean = sum / n;
/* 335 */       double curM2 = current.getVariance() * (curN - 1.0D);
/* 336 */       m2 = m2 + curM2 + meanDiff * meanDiff * oldN * curN / n;
/*     */     } 
/*     */     
/* 339 */     if (n == 0L) {
/* 340 */       variance = Double.NaN;
/* 341 */     } else if (n == 1L) {
/* 342 */       variance = 0.0D;
/*     */     } else {
/* 344 */       variance = m2 / (n - 1L);
/*     */     } 
/* 346 */     return new StatisticalSummaryValues(mean, variance, n, max, min, sum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class AggregatingSummaryStatistics
/*     */     extends SummaryStatistics
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final SummaryStatistics aggregateStatistics;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     AggregatingSummaryStatistics(SummaryStatistics aggregateStatistics) {
/* 376 */       this.aggregateStatistics = aggregateStatistics;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addValue(double value) {
/* 387 */       super.addValue(value);
/* 388 */       synchronized (this.aggregateStatistics) {
/* 389 */         this.aggregateStatistics.addValue(value);
/*     */       } 
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
/*     */     public boolean equals(Object object) {
/* 402 */       if (object == this) {
/* 403 */         return true;
/*     */       }
/* 405 */       if (!(object instanceof AggregatingSummaryStatistics)) {
/* 406 */         return false;
/*     */       }
/* 408 */       AggregatingSummaryStatistics stat = (AggregatingSummaryStatistics)object;
/* 409 */       return (super.equals(stat) && this.aggregateStatistics.equals(stat.aggregateStatistics));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 419 */       return 123 + super.hashCode() + this.aggregateStatistics.hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/AggregateSummaryStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */