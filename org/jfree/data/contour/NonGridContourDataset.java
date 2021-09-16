/*     */ package org.jfree.data.contour;
/*     */ 
/*     */ import org.jfree.data.Range;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NonGridContourDataset
/*     */   extends DefaultContourDataset
/*     */ {
/*     */   static final int DEFAULT_NUM_X = 50;
/*     */   static final int DEFAULT_NUM_Y = 50;
/*     */   static final int DEFAULT_POWER = 4;
/*     */   
/*     */   public NonGridContourDataset() {}
/*     */   
/*     */   public NonGridContourDataset(String seriesName, Object[] xData, Object[] yData, Object[] zData) {
/*  87 */     super(seriesName, xData, yData, zData);
/*  88 */     buildGrid(50, 50, 4);
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
/*     */   public NonGridContourDataset(String seriesName, Object[] xData, Object[] yData, Object[] zData, int numX, int numY, int power) {
/* 106 */     super(seriesName, xData, yData, zData);
/* 107 */     buildGrid(numX, numY, power);
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
/*     */   protected void buildGrid(int numX, int numY, int power) {
/* 123 */     int numValues = numX * numY;
/* 124 */     double[] xGrid = new double[numValues];
/* 125 */     double[] yGrid = new double[numValues];
/* 126 */     double[] zGrid = new double[numValues];
/*     */ 
/*     */     
/* 129 */     double xMin = 1.0E20D;
/* 130 */     for (int k = 0; k < this.xValues.length; k++) {
/* 131 */       xMin = Math.min(xMin, this.xValues[k].doubleValue());
/*     */     }
/*     */     
/* 134 */     double xMax = -1.0E20D;
/* 135 */     for (int j = 0; j < this.xValues.length; j++) {
/* 136 */       xMax = Math.max(xMax, this.xValues[j].doubleValue());
/*     */     }
/*     */     
/* 139 */     double yMin = 1.0E20D;
/* 140 */     for (int m = 0; m < this.yValues.length; m++) {
/* 141 */       yMin = Math.min(yMin, this.yValues[m].doubleValue());
/*     */     }
/*     */     
/* 144 */     double yMax = -1.0E20D;
/* 145 */     for (int n = 0; n < this.yValues.length; n++) {
/* 146 */       yMax = Math.max(yMax, this.yValues[n].doubleValue());
/*     */     }
/*     */     
/* 149 */     Range xRange = new Range(xMin, xMax);
/* 150 */     Range yRange = new Range(yMin, yMax);
/*     */     
/* 152 */     xRange.getLength();
/* 153 */     yRange.getLength();
/*     */ 
/*     */     
/* 156 */     double dxGrid = xRange.getLength() / (numX - 1);
/* 157 */     double dyGrid = yRange.getLength() / (numY - 1);
/*     */ 
/*     */     
/* 160 */     double x = 0.0D;
/* 161 */     for (int i = 0; i < numX; i++) {
/* 162 */       if (i == 0) {
/* 163 */         x = xMin;
/*     */       } else {
/*     */         
/* 166 */         x += dxGrid;
/*     */       } 
/* 168 */       double y = 0.0D;
/* 169 */       for (int i1 = 0; i1 < numY; i1++) {
/* 170 */         int i2 = numY * i + i1;
/* 171 */         xGrid[i2] = x;
/* 172 */         if (i1 == 0) {
/* 173 */           y = yMin;
/*     */         } else {
/*     */           
/* 176 */           y += dyGrid;
/*     */         } 
/* 178 */         yGrid[i2] = y;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 183 */     for (int kGrid = 0; kGrid < xGrid.length; kGrid++) {
/* 184 */       double dTotal = 0.0D;
/* 185 */       zGrid[kGrid] = 0.0D;
/* 186 */       for (int i1 = 0; i1 < this.xValues.length; i1++) {
/* 187 */         double xPt = this.xValues[i1].doubleValue();
/* 188 */         double yPt = this.yValues[i1].doubleValue();
/* 189 */         double d = distance(xPt, yPt, xGrid[kGrid], yGrid[kGrid]);
/* 190 */         if (power != 1) {
/* 191 */           d = Math.pow(d, power);
/*     */         }
/* 193 */         d = Math.sqrt(d);
/* 194 */         if (d > 0.0D) {
/* 195 */           d = 1.0D / d;
/*     */         }
/*     */         else {
/*     */           
/* 199 */           d = 1.0E20D;
/*     */         } 
/* 201 */         if (this.zValues[i1] != null)
/*     */         {
/* 203 */           zGrid[kGrid] = zGrid[kGrid] + this.zValues[i1].doubleValue() * d;
/*     */         }
/* 205 */         dTotal += d;
/*     */       } 
/* 207 */       zGrid[kGrid] = zGrid[kGrid] / dTotal;
/*     */     } 
/*     */ 
/*     */     
/* 211 */     initialize(
/* 212 */         formObjectArray(xGrid), formObjectArray(yGrid), 
/* 213 */         formObjectArray(zGrid));
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
/*     */   protected double distance(double xDataPt, double yDataPt, double xGrdPt, double yGrdPt) {
/* 232 */     double dx = xDataPt - xGrdPt;
/* 233 */     double dy = yDataPt - yGrdPt;
/* 234 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/contour/NonGridContourDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */