/*     */ package org.jfree.data.statistics;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeanAndStandardDeviation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7413468697315721515L;
/*     */   private Number mean;
/*     */   private Number standardDeviation;
/*     */   
/*     */   public MeanAndStandardDeviation(double mean, double standardDeviation) {
/*  73 */     this(new Double(mean), new Double(standardDeviation));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MeanAndStandardDeviation(Number mean, Number standardDeviation) {
/*  84 */     this.mean = mean;
/*  85 */     this.standardDeviation = standardDeviation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getMean() {
/*  94 */     return this.mean;
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
/*     */   public double getMeanValue() {
/* 108 */     double result = Double.NaN;
/* 109 */     if (this.mean != null) {
/* 110 */       result = this.mean.doubleValue();
/*     */     }
/* 112 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getStandardDeviation() {
/* 121 */     return this.standardDeviation;
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
/*     */   public double getStandardDeviationValue() {
/* 134 */     double result = Double.NaN;
/* 135 */     if (this.standardDeviation != null) {
/* 136 */       result = this.standardDeviation.doubleValue();
/*     */     }
/* 138 */     return result;
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
/*     */   public boolean equals(Object obj) {
/* 150 */     if (obj == this) {
/* 151 */       return true;
/*     */     }
/* 153 */     if (!(obj instanceof MeanAndStandardDeviation)) {
/* 154 */       return false;
/*     */     }
/* 156 */     MeanAndStandardDeviation that = (MeanAndStandardDeviation)obj;
/* 157 */     if (!ObjectUtilities.equal(this.mean, that.mean)) {
/* 158 */       return false;
/*     */     }
/* 160 */     if (!ObjectUtilities.equal(this.standardDeviation, that.standardDeviation))
/*     */     {
/*     */       
/* 163 */       return false;
/*     */     }
/* 165 */     return true;
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
/*     */   public String toString() {
/* 177 */     return "[" + this.mean + ", " + this.standardDeviation + "]";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/MeanAndStandardDeviation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */