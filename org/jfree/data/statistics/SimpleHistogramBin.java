/*     */ package org.jfree.data.statistics;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleHistogramBin
/*     */   implements Comparable, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3480862537505941742L;
/*     */   private double lowerBound;
/*     */   private double upperBound;
/*     */   private boolean includeLowerBound;
/*     */   private boolean includeUpperBound;
/*     */   private int itemCount;
/*     */   
/*     */   public SimpleHistogramBin(double lowerBound, double upperBound) {
/*  84 */     this(lowerBound, upperBound, true, true);
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
/*     */ 
/*     */   
/*     */   public SimpleHistogramBin(double lowerBound, double upperBound, boolean includeLowerBound, boolean includeUpperBound) {
/*  98 */     if (lowerBound >= upperBound) {
/*  99 */       throw new IllegalArgumentException("Invalid bounds");
/*     */     }
/* 101 */     this.lowerBound = lowerBound;
/* 102 */     this.upperBound = upperBound;
/* 103 */     this.includeLowerBound = includeLowerBound;
/* 104 */     this.includeUpperBound = includeUpperBound;
/* 105 */     this.itemCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLowerBound() {
/* 114 */     return this.lowerBound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getUpperBound() {
/* 123 */     return this.upperBound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 132 */     return this.itemCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemCount(int count) {
/* 141 */     this.itemCount = count;
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
/*     */   public boolean accepts(double value) {
/* 153 */     if (Double.isNaN(value)) {
/* 154 */       return false;
/*     */     }
/* 156 */     if (value < this.lowerBound) {
/* 157 */       return false;
/*     */     }
/* 159 */     if (value > this.upperBound) {
/* 160 */       return false;
/*     */     }
/* 162 */     if (value == this.lowerBound) {
/* 163 */       return this.includeLowerBound;
/*     */     }
/* 165 */     if (value == this.upperBound) {
/* 166 */       return this.includeUpperBound;
/*     */     }
/* 168 */     return true;
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
/*     */   public boolean overlapsWith(SimpleHistogramBin bin) {
/* 180 */     if (this.upperBound < bin.lowerBound) {
/* 181 */       return false;
/*     */     }
/* 183 */     if (this.lowerBound > bin.upperBound) {
/* 184 */       return false;
/*     */     }
/* 186 */     if (this.upperBound == bin.lowerBound) {
/* 187 */       return (this.includeUpperBound && bin.includeLowerBound);
/*     */     }
/* 189 */     if (this.lowerBound == bin.upperBound) {
/* 190 */       return (this.includeLowerBound && bin.includeUpperBound);
/*     */     }
/* 192 */     return true;
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
/*     */ 
/*     */   
/*     */   public int compareTo(Object obj) {
/* 206 */     if (!(obj instanceof SimpleHistogramBin)) {
/* 207 */       return 0;
/*     */     }
/* 209 */     SimpleHistogramBin bin = (SimpleHistogramBin)obj;
/* 210 */     if (this.lowerBound < bin.lowerBound) {
/* 211 */       return -1;
/*     */     }
/* 213 */     if (this.lowerBound > bin.lowerBound) {
/* 214 */       return 1;
/*     */     }
/*     */     
/* 217 */     if (this.upperBound < bin.upperBound) {
/* 218 */       return -1;
/*     */     }
/* 220 */     if (this.upperBound > bin.upperBound) {
/* 221 */       return 1;
/*     */     }
/* 223 */     return 0;
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
/* 235 */     if (!(obj instanceof SimpleHistogramBin)) {
/* 236 */       return false;
/*     */     }
/* 238 */     SimpleHistogramBin that = (SimpleHistogramBin)obj;
/* 239 */     if (this.lowerBound != that.lowerBound) {
/* 240 */       return false;
/*     */     }
/* 242 */     if (this.upperBound != that.upperBound) {
/* 243 */       return false;
/*     */     }
/* 245 */     if (this.includeLowerBound != that.includeLowerBound) {
/* 246 */       return false;
/*     */     }
/* 248 */     if (this.includeUpperBound != that.includeUpperBound) {
/* 249 */       return false;
/*     */     }
/* 251 */     if (this.itemCount != that.itemCount) {
/* 252 */       return false;
/*     */     }
/* 254 */     return true;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 266 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/SimpleHistogramBin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */