/*     */ package org.jfree.data.contour;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.xy.AbstractXYZDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultContourDataset
/*     */   extends AbstractXYZDataset
/*     */   implements ContourDataset
/*     */ {
/*  71 */   protected Comparable seriesKey = null;
/*     */ 
/*     */   
/*  74 */   protected Number[] xValues = null;
/*     */ 
/*     */   
/*  77 */   protected Number[] yValues = null;
/*     */ 
/*     */   
/*  80 */   protected Number[] zValues = null;
/*     */ 
/*     */   
/*  83 */   protected int[] xIndex = null;
/*     */ 
/*     */   
/*  86 */   boolean[] dateAxis = new boolean[3];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultContourDataset() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultContourDataset(Comparable seriesKey, Object[] xData, Object[] yData, Object[] zData) {
/* 108 */     this.seriesKey = seriesKey;
/* 109 */     initialize(xData, yData, zData);
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
/*     */   public void initialize(Object[] xData, Object[] yData, Object[] zData) {
/* 123 */     this.xValues = (Number[])new Double[xData.length];
/* 124 */     this.yValues = (Number[])new Double[yData.length];
/* 125 */     this.zValues = (Number[])new Double[zData.length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     Vector<Integer> tmpVector = new Vector();
/* 136 */     double x = 1.123452E31D;
/* 137 */     for (int k = 0; k < this.xValues.length; k++) {
/* 138 */       if (xData[k] != null) {
/*     */         Number xNumber;
/* 140 */         if (xData[k] instanceof Number) {
/* 141 */           xNumber = (Number)xData[k];
/*     */         }
/* 143 */         else if (xData[k] instanceof Date) {
/* 144 */           this.dateAxis[0] = true;
/* 145 */           Date xDate = (Date)xData[k];
/* 146 */           xNumber = new Long(xDate.getTime());
/*     */         } else {
/*     */           
/* 149 */           xNumber = new Integer(0);
/*     */         } 
/* 151 */         this.xValues[k] = new Double(xNumber.doubleValue());
/*     */ 
/*     */ 
/*     */         
/* 155 */         if (x != this.xValues[k].doubleValue()) {
/* 156 */           tmpVector.add(new Integer(k));
/*     */           
/* 158 */           x = this.xValues[k].doubleValue();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 164 */     Object[] inttmp = tmpVector.toArray();
/* 165 */     this.xIndex = new int[inttmp.length];
/*     */ 
/*     */     
/* 168 */     for (int i = 0; i < inttmp.length; i++) {
/* 169 */       this.xIndex[i] = ((Integer)inttmp[i]).intValue();
/*     */     }
/* 171 */     for (int j = 0; j < this.yValues.length; j++) {
/*     */       
/* 173 */       this.yValues[j] = (Double)yData[j];
/* 174 */       if (zData[j] != null) {
/* 175 */         this.zValues[j] = (Double)zData[j];
/*     */       }
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
/*     */   public static Object[][] formObjectArray(double[][] data) {
/* 188 */     Double[][] arrayOfDouble = new Double[data.length][(data[0]).length];
/*     */     
/* 190 */     for (int i = 0; i < arrayOfDouble.length; i++) {
/* 191 */       for (int j = 0; j < (arrayOfDouble[i]).length; j++) {
/* 192 */         arrayOfDouble[i][j] = new Double(data[i][j]);
/*     */       }
/*     */     } 
/* 195 */     return (Object[][])arrayOfDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] formObjectArray(double[] data) {
/* 206 */     Double[] arrayOfDouble = new Double[data.length];
/* 207 */     for (int i = 0; i < arrayOfDouble.length; i++) {
/* 208 */       arrayOfDouble[i] = new Double(data[i]);
/*     */     }
/* 210 */     return (Object[])arrayOfDouble;
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
/*     */   public int getItemCount(int series) {
/* 223 */     if (series > 0) {
/* 224 */       throw new IllegalArgumentException("Only one series for contour");
/*     */     }
/* 226 */     return this.zValues.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxZValue() {
/* 236 */     double zMax = -1.0E20D;
/* 237 */     for (int k = 0; k < this.zValues.length; k++) {
/* 238 */       if (this.zValues[k] != null) {
/* 239 */         zMax = Math.max(zMax, this.zValues[k].doubleValue());
/*     */       }
/*     */     } 
/* 242 */     return zMax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinZValue() {
/* 252 */     double zMin = 1.0E20D;
/* 253 */     for (int k = 0; k < this.zValues.length; k++) {
/* 254 */       if (this.zValues[k] != null) {
/* 255 */         zMin = Math.min(zMin, this.zValues[k].doubleValue());
/*     */       }
/*     */     } 
/* 258 */     return zMin;
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
/*     */   public Range getZValueRange(Range x, Range y) {
/* 272 */     double minX = x.getLowerBound();
/* 273 */     double minY = y.getLowerBound();
/* 274 */     double maxX = x.getUpperBound();
/* 275 */     double maxY = y.getUpperBound();
/*     */     
/* 277 */     double zMin = 1.0E20D;
/* 278 */     double zMax = -1.0E20D;
/* 279 */     for (int k = 0; k < this.zValues.length; k++) {
/* 280 */       if (this.xValues[k].doubleValue() >= minX && this.xValues[k]
/* 281 */         .doubleValue() <= maxX && this.yValues[k]
/* 282 */         .doubleValue() >= minY && this.yValues[k]
/* 283 */         .doubleValue() <= maxY && 
/* 284 */         this.zValues[k] != null) {
/* 285 */         zMin = Math.min(zMin, this.zValues[k].doubleValue());
/* 286 */         zMax = Math.max(zMax, this.zValues[k].doubleValue());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 291 */     return new Range(zMin, zMax);
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
/*     */   public double getMinZValue(double minX, double minY, double maxX, double maxY) {
/* 309 */     double zMin = 1.0E20D;
/* 310 */     for (int k = 0; k < this.zValues.length; k++) {
/* 311 */       if (this.zValues[k] != null) {
/* 312 */         zMin = Math.min(zMin, this.zValues[k].doubleValue());
/*     */       }
/*     */     } 
/* 315 */     return zMin;
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
/*     */   public int getSeriesCount() {
/* 328 */     return 1;
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
/*     */   public Comparable getSeriesKey(int series) {
/* 342 */     if (series > 0) {
/* 343 */       throw new IllegalArgumentException("Only one series for contour");
/*     */     }
/* 345 */     return this.seriesKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getXIndices() {
/* 355 */     return this.xIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number[] getXValues() {
/* 365 */     return this.xValues;
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
/*     */   public Number getX(int series, int item) {
/* 379 */     if (series > 0) {
/* 380 */       throw new IllegalArgumentException("Only one series for contour");
/*     */     }
/* 382 */     return this.xValues[item];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getXValue(int item) {
/* 393 */     return this.xValues[item];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number[] getYValues() {
/* 403 */     return this.yValues;
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
/*     */   public Number getY(int series, int item) {
/* 417 */     if (series > 0) {
/* 418 */       throw new IllegalArgumentException("Only one series for contour");
/*     */     }
/* 420 */     return this.yValues[item];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number[] getZValues() {
/* 430 */     return this.zValues;
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
/*     */   public Number getZ(int series, int item) {
/* 444 */     if (series > 0) {
/* 445 */       throw new IllegalArgumentException("Only one series for contour");
/*     */     }
/* 447 */     return this.zValues[item];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] indexX() {
/* 457 */     int[] index = new int[this.xValues.length];
/* 458 */     for (int k = 0; k < index.length; k++) {
/* 459 */       index[k] = indexX(k);
/*     */     }
/* 461 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexX(int k) {
/* 472 */     int i = Arrays.binarySearch(this.xIndex, k);
/* 473 */     if (i >= 0) {
/* 474 */       return i;
/*     */     }
/*     */     
/* 477 */     return -1 * i - 2;
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
/*     */   public int indexY(int k) {
/* 490 */     return k / this.xValues.length;
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
/*     */   public int indexZ(int i, int j) {
/* 502 */     return this.xValues.length * j + i;
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
/*     */   public boolean isDateAxis(int axisNumber) {
/* 514 */     if (axisNumber < 0 || axisNumber > 2) {
/* 515 */       return false;
/*     */     }
/* 517 */     return this.dateAxis[axisNumber];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeriesKeys(Comparable[] seriesKeys) {
/* 526 */     if (seriesKeys.length > 1) {
/* 527 */       throw new IllegalArgumentException("Contours only support one series");
/*     */     }
/*     */     
/* 530 */     this.seriesKey = seriesKeys[0];
/* 531 */     fireDatasetChanged();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/contour/DefaultContourDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */