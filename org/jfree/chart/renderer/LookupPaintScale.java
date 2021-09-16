/*     */ package org.jfree.chart.renderer;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.util.PaintUtilities;
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
/*     */ public class LookupPaintScale
/*     */   implements PaintScale, PublicCloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = -5239384246251042006L;
/*     */   private double lowerBound;
/*     */   private double upperBound;
/*     */   private transient Paint defaultPaint;
/*     */   private List lookupTable;
/*     */   
/*     */   static class PaintItem
/*     */     implements Comparable, Serializable
/*     */   {
/*     */     static final long serialVersionUID = 698920578512361570L;
/*     */     double value;
/*     */     transient Paint paint;
/*     */     
/*     */     public PaintItem(double value, Paint paint) {
/*  92 */       this.value = value;
/*  93 */       this.paint = paint;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(Object obj) {
/* 105 */       PaintItem that = (PaintItem)obj;
/* 106 */       double d1 = this.value;
/* 107 */       double d2 = that.value;
/* 108 */       if (d1 > d2) {
/* 109 */         return 1;
/*     */       }
/* 111 */       if (d1 < d2) {
/* 112 */         return -1;
/*     */       }
/* 114 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 126 */       if (obj == this) {
/* 127 */         return true;
/*     */       }
/* 129 */       if (!(obj instanceof PaintItem)) {
/* 130 */         return false;
/*     */       }
/* 132 */       PaintItem that = (PaintItem)obj;
/* 133 */       if (this.value != that.value) {
/* 134 */         return false;
/*     */       }
/* 136 */       if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 137 */         return false;
/*     */       }
/* 139 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void writeObject(ObjectOutputStream stream) throws IOException {
/* 150 */       stream.defaultWriteObject();
/* 151 */       SerialUtilities.writePaint(this.paint, stream);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 164 */       stream.defaultReadObject();
/* 165 */       this.paint = SerialUtilities.readPaint(stream);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LookupPaintScale() {
/* 189 */     this(0.0D, 1.0D, Color.lightGray);
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
/*     */   public LookupPaintScale(double lowerBound, double upperBound, Paint defaultPaint) {
/* 202 */     if (lowerBound >= upperBound) {
/* 203 */       throw new IllegalArgumentException("Requires lowerBound < upperBound.");
/*     */     }
/*     */     
/* 206 */     ParamChecks.nullNotPermitted(defaultPaint, "defaultPaint");
/* 207 */     this.lowerBound = lowerBound;
/* 208 */     this.upperBound = upperBound;
/* 209 */     this.defaultPaint = defaultPaint;
/* 210 */     this.lookupTable = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getDefaultPaint() {
/* 219 */     return this.defaultPaint;
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
/*     */   public double getLowerBound() {
/* 231 */     return this.lowerBound;
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
/*     */   public double getUpperBound() {
/* 243 */     return this.upperBound;
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
/*     */   public void add(Number value, Paint paint) {
/* 257 */     add(value.doubleValue(), paint);
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
/*     */   public void add(double value, Paint paint) {
/* 271 */     PaintItem item = new PaintItem(value, paint);
/* 272 */     int index = Collections.binarySearch(this.lookupTable, item);
/* 273 */     if (index >= 0) {
/* 274 */       this.lookupTable.set(index, item);
/*     */     } else {
/*     */       
/* 277 */       this.lookupTable.add(-(index + 1), item);
/*     */     } 
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
/*     */ 
/*     */   
/*     */   public Paint getPaint(double value) {
/* 294 */     if (value < this.lowerBound) {
/* 295 */       return this.defaultPaint;
/*     */     }
/* 297 */     if (value > this.upperBound) {
/* 298 */       return this.defaultPaint;
/*     */     }
/*     */     
/* 301 */     int count = this.lookupTable.size();
/* 302 */     if (count == 0) {
/* 303 */       return this.defaultPaint;
/*     */     }
/*     */ 
/*     */     
/* 307 */     PaintItem item = this.lookupTable.get(0);
/* 308 */     if (value < item.value) {
/* 309 */       return this.defaultPaint;
/*     */     }
/*     */ 
/*     */     
/* 313 */     int low = 0;
/* 314 */     int high = this.lookupTable.size() - 1;
/* 315 */     while (high - low > 1) {
/* 316 */       int current = (low + high) / 2;
/* 317 */       item = this.lookupTable.get(current);
/* 318 */       if (value >= item.value) {
/* 319 */         low = current;
/*     */         continue;
/*     */       } 
/* 322 */       high = current;
/*     */     } 
/*     */     
/* 325 */     if (high > low) {
/* 326 */       item = this.lookupTable.get(high);
/* 327 */       if (value < item.value) {
/* 328 */         item = this.lookupTable.get(low);
/*     */       }
/*     */     } 
/* 331 */     return (item != null) ? item.paint : this.defaultPaint;
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
/*     */   public boolean equals(Object obj) {
/* 344 */     if (obj == this) {
/* 345 */       return true;
/*     */     }
/* 347 */     if (!(obj instanceof LookupPaintScale)) {
/* 348 */       return false;
/*     */     }
/* 350 */     LookupPaintScale that = (LookupPaintScale)obj;
/* 351 */     if (this.lowerBound != that.lowerBound) {
/* 352 */       return false;
/*     */     }
/* 354 */     if (this.upperBound != that.upperBound) {
/* 355 */       return false;
/*     */     }
/* 357 */     if (!PaintUtilities.equal(this.defaultPaint, that.defaultPaint)) {
/* 358 */       return false;
/*     */     }
/* 360 */     if (!this.lookupTable.equals(that.lookupTable)) {
/* 361 */       return false;
/*     */     }
/* 363 */     return true;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 376 */     LookupPaintScale clone = (LookupPaintScale)super.clone();
/* 377 */     clone.lookupTable = new ArrayList(this.lookupTable);
/* 378 */     return clone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 389 */     stream.defaultWriteObject();
/* 390 */     SerialUtilities.writePaint(this.defaultPaint, stream);
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 403 */     stream.defaultReadObject();
/* 404 */     this.defaultPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/LookupPaintScale.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */