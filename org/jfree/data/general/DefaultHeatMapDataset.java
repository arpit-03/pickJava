/*     */ package org.jfree.data.general;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.data.DataUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultHeatMapDataset
/*     */   extends AbstractDataset
/*     */   implements HeatMapDataset, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private int xSamples;
/*     */   private int ySamples;
/*     */   private double minX;
/*     */   private double maxX;
/*     */   private double minY;
/*     */   private double maxY;
/*     */   private double[][] zValues;
/*     */   
/*     */   public DefaultHeatMapDataset(int xSamples, int ySamples, double minX, double maxX, double minY, double maxY) {
/*  90 */     if (xSamples < 1) {
/*  91 */       throw new IllegalArgumentException("Requires 'xSamples' > 0");
/*     */     }
/*  93 */     if (ySamples < 1) {
/*  94 */       throw new IllegalArgumentException("Requires 'ySamples' > 0");
/*     */     }
/*  96 */     if (Double.isInfinite(minX) || Double.isNaN(minX)) {
/*  97 */       throw new IllegalArgumentException("'minX' cannot be INF or NaN.");
/*     */     }
/*  99 */     if (Double.isInfinite(maxX) || Double.isNaN(maxX)) {
/* 100 */       throw new IllegalArgumentException("'maxX' cannot be INF or NaN.");
/*     */     }
/* 102 */     if (Double.isInfinite(minY) || Double.isNaN(minY)) {
/* 103 */       throw new IllegalArgumentException("'minY' cannot be INF or NaN.");
/*     */     }
/* 105 */     if (Double.isInfinite(maxY) || Double.isNaN(maxY)) {
/* 106 */       throw new IllegalArgumentException("'maxY' cannot be INF or NaN.");
/*     */     }
/*     */     
/* 109 */     this.xSamples = xSamples;
/* 110 */     this.ySamples = ySamples;
/* 111 */     this.minX = minX;
/* 112 */     this.maxX = maxX;
/* 113 */     this.minY = minY;
/* 114 */     this.maxY = maxY;
/* 115 */     this.zValues = new double[xSamples][];
/* 116 */     for (int x = 0; x < xSamples; x++) {
/* 117 */       this.zValues[x] = new double[ySamples];
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
/*     */   public int getXSampleCount() {
/* 130 */     return this.xSamples;
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
/*     */   public int getYSampleCount() {
/* 142 */     return this.ySamples;
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
/*     */   public double getMinimumXValue() {
/* 154 */     return this.minX;
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
/*     */   public double getMaximumXValue() {
/* 166 */     return this.maxX;
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
/*     */   public double getMinimumYValue() {
/* 178 */     return this.minY;
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
/*     */   public double getMaximumYValue() {
/* 190 */     return this.maxY;
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
/*     */   public double getXValue(int xIndex) {
/* 202 */     double x = this.minX + (this.maxX - this.minX) * xIndex / this.xSamples;
/*     */     
/* 204 */     return x;
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
/*     */   public double getYValue(int yIndex) {
/* 216 */     double y = this.minY + (this.maxY - this.minY) * yIndex / this.ySamples;
/*     */     
/* 218 */     return y;
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
/*     */   public double getZValue(int xIndex, int yIndex) {
/* 232 */     return this.zValues[xIndex][yIndex];
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
/*     */   public Number getZ(int xIndex, int yIndex) {
/* 248 */     return new Double(getZValue(xIndex, yIndex));
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
/*     */   public void setZValue(int xIndex, int yIndex, double z) {
/* 260 */     setZValue(xIndex, yIndex, z, true);
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
/*     */   public void setZValue(int xIndex, int yIndex, double z, boolean notify) {
/* 273 */     this.zValues[xIndex][yIndex] = z;
/* 274 */     if (notify) {
/* 275 */       fireDatasetChanged();
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
/*     */   public boolean equals(Object obj) {
/* 288 */     if (obj == this) {
/* 289 */       return true;
/*     */     }
/* 291 */     if (!(obj instanceof DefaultHeatMapDataset)) {
/* 292 */       return false;
/*     */     }
/* 294 */     DefaultHeatMapDataset that = (DefaultHeatMapDataset)obj;
/* 295 */     if (this.xSamples != that.xSamples) {
/* 296 */       return false;
/*     */     }
/* 298 */     if (this.ySamples != that.ySamples) {
/* 299 */       return false;
/*     */     }
/* 301 */     if (this.minX != that.minX) {
/* 302 */       return false;
/*     */     }
/* 304 */     if (this.maxX != that.maxX) {
/* 305 */       return false;
/*     */     }
/* 307 */     if (this.minY != that.minY) {
/* 308 */       return false;
/*     */     }
/* 310 */     if (this.maxY != that.maxY) {
/* 311 */       return false;
/*     */     }
/* 313 */     if (!DataUtilities.equal(this.zValues, that.zValues)) {
/* 314 */       return false;
/*     */     }
/*     */     
/* 317 */     return true;
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
/* 330 */     DefaultHeatMapDataset clone = (DefaultHeatMapDataset)super.clone();
/* 331 */     clone.zValues = DataUtilities.clone(this.zValues);
/* 332 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/DefaultHeatMapDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */