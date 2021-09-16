/*     */ package org.apache.commons.math3.stat.descriptive;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
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
/*     */ public class StatisticalSummaryValues
/*     */   implements Serializable, StatisticalSummary
/*     */ {
/*     */   private static final long serialVersionUID = -5108854841843722536L;
/*     */   private final double mean;
/*     */   private final double variance;
/*     */   private final long n;
/*     */   private final double max;
/*     */   private final double min;
/*     */   private final double sum;
/*     */   
/*     */   public StatisticalSummaryValues(double mean, double variance, long n, double max, double min, double sum) {
/*  66 */     this.mean = mean;
/*  67 */     this.variance = variance;
/*  68 */     this.n = n;
/*  69 */     this.max = max;
/*  70 */     this.min = min;
/*  71 */     this.sum = sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMax() {
/*  78 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMean() {
/*  85 */     return this.mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMin() {
/*  92 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/*  99 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSum() {
/* 106 */     return this.sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStandardDeviation() {
/* 113 */     return FastMath.sqrt(this.variance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getVariance() {
/* 120 */     return this.variance;
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
/*     */   public boolean equals(Object object) {
/* 133 */     if (object == this) {
/* 134 */       return true;
/*     */     }
/* 136 */     if (!(object instanceof StatisticalSummaryValues)) {
/* 137 */       return false;
/*     */     }
/* 139 */     StatisticalSummaryValues stat = (StatisticalSummaryValues)object;
/* 140 */     return (Precision.equalsIncludingNaN(stat.getMax(), getMax()) && Precision.equalsIncludingNaN(stat.getMean(), getMean()) && Precision.equalsIncludingNaN(stat.getMin(), getMin()) && Precision.equalsIncludingNaN((float)stat.getN(), (float)getN()) && Precision.equalsIncludingNaN(stat.getSum(), getSum()) && Precision.equalsIncludingNaN(stat.getVariance(), getVariance()));
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
/*     */   public int hashCode() {
/* 155 */     int result = 31 + MathUtils.hash(getMax());
/* 156 */     result = result * 31 + MathUtils.hash(getMean());
/* 157 */     result = result * 31 + MathUtils.hash(getMin());
/* 158 */     result = result * 31 + MathUtils.hash(getN());
/* 159 */     result = result * 31 + MathUtils.hash(getSum());
/* 160 */     result = result * 31 + MathUtils.hash(getVariance());
/* 161 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 172 */     StringBuffer outBuffer = new StringBuffer();
/* 173 */     String endl = "\n";
/* 174 */     outBuffer.append("StatisticalSummaryValues:").append(endl);
/* 175 */     outBuffer.append("n: ").append(getN()).append(endl);
/* 176 */     outBuffer.append("min: ").append(getMin()).append(endl);
/* 177 */     outBuffer.append("max: ").append(getMax()).append(endl);
/* 178 */     outBuffer.append("mean: ").append(getMean()).append(endl);
/* 179 */     outBuffer.append("std dev: ").append(getStandardDeviation()).append(endl);
/*     */     
/* 181 */     outBuffer.append("variance: ").append(getVariance()).append(endl);
/* 182 */     outBuffer.append("sum: ").append(getSum()).append(endl);
/* 183 */     return outBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/StatisticalSummaryValues.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */