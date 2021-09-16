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
/*     */ public class LineFunction2D
/*     */   implements Function2D, Serializable
/*     */ {
/*     */   private double a;
/*     */   private double b;
/*     */   
/*     */   public LineFunction2D(double a, double b) {
/*  67 */     this.a = a;
/*  68 */     this.b = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getIntercept() {
/*  79 */     return this.a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSlope() {
/*  90 */     return this.b;
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
/* 102 */     return this.a + this.b * x;
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
/* 114 */     if (!(obj instanceof LineFunction2D)) {
/* 115 */       return false;
/*     */     }
/* 117 */     LineFunction2D that = (LineFunction2D)obj;
/* 118 */     if (this.a != that.a) {
/* 119 */       return false;
/*     */     }
/* 121 */     if (this.b != that.b) {
/* 122 */       return false;
/*     */     }
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 134 */     int result = 29;
/* 135 */     result = HashUtilities.hashCode(result, this.a);
/* 136 */     result = HashUtilities.hashCode(result, this.b);
/* 137 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/function/LineFunction2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */