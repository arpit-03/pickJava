/*     */ package org.apache.commons.math3.geometry.spherical.oned;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
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
/*     */ public class Arc
/*     */ {
/*     */   private final double lower;
/*     */   private final double upper;
/*     */   private final double middle;
/*     */   private final double tolerance;
/*     */   
/*     */   public Arc(double lower, double upper, double tolerance) throws NumberIsTooLargeException {
/*  63 */     this.tolerance = tolerance;
/*  64 */     if (Precision.equals(lower, upper, 0) || upper - lower >= 6.283185307179586D) {
/*     */       
/*  66 */       this.lower = 0.0D;
/*  67 */       this.upper = 6.283185307179586D;
/*  68 */       this.middle = Math.PI;
/*  69 */     } else if (lower <= upper) {
/*  70 */       this.lower = MathUtils.normalizeAngle(lower, Math.PI);
/*  71 */       this.upper = this.lower + upper - lower;
/*  72 */       this.middle = 0.5D * (this.lower + this.upper);
/*     */     } else {
/*  74 */       throw new NumberIsTooLargeException(LocalizedFormats.ENDPOINTS_NOT_AN_INTERVAL, Double.valueOf(lower), Double.valueOf(upper), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInf() {
/*  84 */     return this.lower;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSup() {
/*  92 */     return this.upper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSize() {
/*  99 */     return this.upper - this.lower;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBarycenter() {
/* 106 */     return this.middle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTolerance() {
/* 113 */     return this.tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Region.Location checkPoint(double point) {
/* 122 */     double normalizedPoint = MathUtils.normalizeAngle(point, this.middle);
/* 123 */     if (normalizedPoint < this.lower - this.tolerance || normalizedPoint > this.upper + this.tolerance)
/* 124 */       return Region.Location.OUTSIDE; 
/* 125 */     if (normalizedPoint > this.lower + this.tolerance && normalizedPoint < this.upper - this.tolerance) {
/* 126 */       return Region.Location.INSIDE;
/*     */     }
/* 128 */     return (getSize() >= 6.283185307179586D - this.tolerance) ? Region.Location.INSIDE : Region.Location.BOUNDARY;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/oned/Arc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */