/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vector
/*     */   implements Serializable
/*     */ {
/*     */   private double x;
/*     */   private double y;
/*     */   
/*     */   public Vector(double x, double y) {
/*  68 */     this.x = x;
/*  69 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/*  78 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/*  87 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLength() {
/*  96 */     return Math.sqrt(this.x * this.x + this.y * this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAngle() {
/* 105 */     return Math.atan2(this.y, this.x);
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
/* 117 */     if (obj == this) {
/* 118 */       return true;
/*     */     }
/* 120 */     if (!(obj instanceof Vector)) {
/* 121 */       return false;
/*     */     }
/* 123 */     Vector that = (Vector)obj;
/* 124 */     if (this.x != that.x) {
/* 125 */       return false;
/*     */     }
/* 127 */     if (this.y != that.y) {
/* 128 */       return false;
/*     */     }
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     int result = 193;
/* 141 */     long temp = Double.doubleToLongBits(this.x);
/* 142 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 143 */     temp = Double.doubleToLongBits(this.y);
/* 144 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 145 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/Vector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */