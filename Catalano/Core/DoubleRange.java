/*     */ package Catalano.Core;
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
/*     */ public class DoubleRange
/*     */   implements Serializable
/*     */ {
/*     */   double min;
/*     */   double max;
/*     */   
/*     */   public DoubleRange() {}
/*     */   
/*     */   public DoubleRange(double min, double max) {
/*  48 */     this.min = min;
/*  49 */     this.max = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMin() {
/*  57 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMin(double min) {
/*  65 */     this.min = min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMax() {
/*  73 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMax(double max) {
/*  81 */     this.max = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/*  89 */     return this.max - this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInside(double x) {
/*  98 */     return (x >= this.min && x <= this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean IsOverlapping(DoubleRange range) {
/* 107 */     return !(!isInside(range.min) && !isInside(range.max) && 
/* 108 */       !range.isInside(this.min) && !range.isInside(this.max));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntRange toIntRange() {
/* 116 */     return new IntRange((int)this.min, (int)this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatRange toFloatRange() {
/* 124 */     return new FloatRange((float)this.min, (float)this.max);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 129 */     if (obj.getClass().isAssignableFrom(DoubleRange.class)) {
/* 130 */       DoubleRange range = (DoubleRange)obj;
/* 131 */       return (this.min == range.getMin() && this.max == range.getMax());
/*     */     } 
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 138 */     int hash = 5;
/* 139 */     hash = 61 * hash + (int)(Double.doubleToLongBits(this.min) ^ Double.doubleToLongBits(this.min) >>> 32L);
/* 140 */     hash += 61 * hash + (int)(Double.doubleToLongBits(this.max) ^ Double.doubleToLongBits(this.max) >>> 32L);
/* 141 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 146 */     return "Minimum: " + this.min + " Maximum: " + this.max;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/DoubleRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */