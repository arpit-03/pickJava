/*     */ package org.apache.commons.math3.geometry.euclidean.oned;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Interval
/*     */ {
/*     */   private final double lower;
/*     */   private final double upper;
/*     */   
/*     */   public Interval(double lower, double upper) {
/*  41 */     if (upper < lower) {
/*  42 */       throw new NumberIsTooSmallException(LocalizedFormats.ENDPOINTS_NOT_AN_INTERVAL, Double.valueOf(upper), Double.valueOf(lower), true);
/*     */     }
/*     */     
/*  45 */     this.lower = lower;
/*  46 */     this.upper = upper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInf() {
/*  54 */     return this.lower;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double getLower() {
/*  63 */     return getInf();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSup() {
/*  71 */     return this.upper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double getUpper() {
/*  80 */     return getSup();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSize() {
/*  88 */     return this.upper - this.lower;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double getLength() {
/*  97 */     return getSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBarycenter() {
/* 105 */     return 0.5D * (this.lower + this.upper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double getMidPoint() {
/* 114 */     return getBarycenter();
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
/*     */   public Region.Location checkPoint(double point, double tolerance) {
/* 126 */     if (point < this.lower - tolerance || point > this.upper + tolerance)
/* 127 */       return Region.Location.OUTSIDE; 
/* 128 */     if (point > this.lower + tolerance && point < this.upper - tolerance) {
/* 129 */       return Region.Location.INSIDE;
/*     */     }
/* 131 */     return Region.Location.BOUNDARY;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/oned/Interval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */