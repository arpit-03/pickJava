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
/*     */ public class IntRange
/*     */   implements Serializable
/*     */ {
/*     */   int min;
/*     */   int max;
/*     */   
/*     */   public IntRange() {}
/*     */   
/*     */   public IntRange(int min, int max) {
/*  48 */     this.min = min;
/*  49 */     this.max = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMin() {
/*  57 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMin(int min) {
/*  65 */     this.min = min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMax() {
/*  73 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMax(int max) {
/*  81 */     this.max = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/*  89 */     return (this.max - this.min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInside(int x) {
/*  98 */     return (x >= this.min && x <= this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean IsOverlapping(IntRange range) {
/* 107 */     return !(!isInside(range.min) && !isInside(range.max) && 
/* 108 */       !range.isInside(this.min) && !range.isInside(this.max));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatRange toFloatRange() {
/* 116 */     return new FloatRange(this.min, this.max);
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
/* 129 */     if (obj.getClass().isAssignableFrom(IntRange.class)) {
/* 130 */       IntRange range = (IntRange)obj;
/* 131 */       return (this.min == range.getMin() && this.max == range.getMax());
/*     */     } 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     int hash = 5;
/* 141 */     hash = 71 * hash + this.min;
/* 142 */     hash = 71 * hash + this.max;
/* 143 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 148 */     return "Minimum: " + this.min + " Maximum: " + this.max;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/IntRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */