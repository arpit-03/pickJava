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
/*     */ public class FloatRange
/*     */   implements Serializable
/*     */ {
/*     */   float min;
/*     */   float max;
/*     */   
/*     */   public FloatRange() {}
/*     */   
/*     */   public FloatRange(float min, float max) {
/*  48 */     this.min = min;
/*  49 */     this.max = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMin() {
/*  57 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMin(float min) {
/*  65 */     this.min = min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMax() {
/*  73 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMax(float max) {
/*  81 */     this.max = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float length() {
/*  89 */     return this.max - this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInside(float x) {
/*  98 */     return (x >= this.min && x <= this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean IsOverlapping(FloatRange range) {
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
/*     */   public DoubleRange toDoubleRange() {
/* 124 */     return new DoubleRange(this.min, this.max);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 129 */     if (obj.getClass().isAssignableFrom(FloatRange.class)) {
/* 130 */       FloatRange range = (FloatRange)obj;
/* 131 */       return (this.min == range.getMin() && this.max == range.getMax());
/*     */     } 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     int hash = 3;
/* 141 */     hash = 19 * hash + Float.floatToIntBits(this.min);
/* 142 */     hash += 19 * hash + Float.floatToIntBits(this.max);
/* 143 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 148 */     return "Minimum: " + this.min + " Maximum: " + this.max;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/FloatRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */