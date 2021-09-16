/*     */ package org.jfree.data.function;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NormalDistributionFunction2D
/*     */   implements Function2D, Serializable
/*     */ {
/*     */   private double mean;
/*     */   private double std;
/*     */   private double factor;
/*     */   private double denominator;
/*     */   
/*     */   public NormalDistributionFunction2D(double mean, double std) {
/*  77 */     if (std <= 0.0D) {
/*  78 */       throw new IllegalArgumentException("Requires 'std' > 0.");
/*     */     }
/*  80 */     this.mean = mean;
/*  81 */     this.std = std;
/*     */     
/*  83 */     this.factor = 1.0D / std * Math.sqrt(6.283185307179586D);
/*  84 */     this.denominator = 2.0D * std * std;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMean() {
/*  93 */     return this.mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStandardDeviation() {
/* 102 */     return this.std;
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
/*     */   public double getValue(double x) {
/* 114 */     double z = x - this.mean;
/* 115 */     return this.factor * Math.exp(-z * z / this.denominator);
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
/* 127 */     if (!(obj instanceof NormalDistributionFunction2D)) {
/* 128 */       return false;
/*     */     }
/* 130 */     NormalDistributionFunction2D that = (NormalDistributionFunction2D)obj;
/* 131 */     if (this.mean != that.mean) {
/* 132 */       return false;
/*     */     }
/* 134 */     if (this.std != that.std) {
/* 135 */       return false;
/*     */     }
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 147 */     int result = 29;
/* 148 */     result = HashUtilities.hashCode(result, this.mean);
/* 149 */     result = HashUtilities.hashCode(result, this.std);
/* 150 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/function/NormalDistributionFunction2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */