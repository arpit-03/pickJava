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
/*     */ public class XYCoordinate
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private double x;
/*     */   private double y;
/*     */   
/*     */   public XYCoordinate() {
/*  63 */     this(0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYCoordinate(double x, double y) {
/*  73 */     this.x = x;
/*  74 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/*  83 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/*  92 */     return this.y;
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
/* 104 */     if (obj == this) {
/* 105 */       return true;
/*     */     }
/* 107 */     if (!(obj instanceof XYCoordinate)) {
/* 108 */       return false;
/*     */     }
/* 110 */     XYCoordinate that = (XYCoordinate)obj;
/* 111 */     if (this.x != that.x) {
/* 112 */       return false;
/*     */     }
/* 114 */     if (this.y != that.y) {
/* 115 */       return false;
/*     */     }
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 127 */     int result = 193;
/* 128 */     long temp = Double.doubleToLongBits(this.x);
/* 129 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 130 */     temp = Double.doubleToLongBits(this.y);
/* 131 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 132 */     return result;
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
/* 143 */     return "(" + this.x + ", " + this.y + ")";
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
/*     */   public int compareTo(Object obj) {
/* 155 */     if (!(obj instanceof XYCoordinate)) {
/* 156 */       throw new IllegalArgumentException("Incomparable object.");
/*     */     }
/* 158 */     XYCoordinate that = (XYCoordinate)obj;
/* 159 */     if (this.x > that.x) {
/* 160 */       return 1;
/*     */     }
/* 162 */     if (this.x < that.x) {
/* 163 */       return -1;
/*     */     }
/*     */     
/* 166 */     if (this.y > that.y) {
/* 167 */       return 1;
/*     */     }
/* 169 */     if (this.y < that.y) {
/* 170 */       return -1;
/*     */     }
/*     */     
/* 173 */     return 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/XYCoordinate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */