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
/*     */ public class PowerFunction2D
/*     */   implements Function2D, Serializable
/*     */ {
/*     */   private double a;
/*     */   private double b;
/*     */   
/*     */   public PowerFunction2D(double a, double b) {
/*  66 */     this.a = a;
/*  67 */     this.b = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getA() {
/*  78 */     return this.a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getB() {
/*  89 */     return this.b;
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
/* 101 */     return this.a * Math.pow(x, this.b);
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
/* 113 */     if (!(obj instanceof PowerFunction2D)) {
/* 114 */       return false;
/*     */     }
/* 116 */     PowerFunction2D that = (PowerFunction2D)obj;
/* 117 */     if (this.a != that.a) {
/* 118 */       return false;
/*     */     }
/* 120 */     if (this.b != that.b) {
/* 121 */       return false;
/*     */     }
/* 123 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 133 */     int result = 29;
/* 134 */     result = HashUtilities.hashCode(result, this.a);
/* 135 */     result = HashUtilities.hashCode(result, this.b);
/* 136 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/function/PowerFunction2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */