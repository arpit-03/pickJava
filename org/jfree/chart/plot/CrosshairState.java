/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.geom.Point2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrosshairState
/*     */ {
/*     */   private boolean calculateDistanceInDataSpace = false;
/*     */   private double anchorX;
/*     */   private double anchorY;
/*     */   private Point2D anchor;
/*     */   private double crosshairX;
/*     */   private double crosshairY;
/*     */   private int datasetIndex;
/*     */   private int domainAxisIndex;
/*     */   private int rangeAxisIndex;
/*     */   private double distance;
/*     */   
/*     */   public CrosshairState() {
/* 119 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrosshairState(boolean calculateDistanceInDataSpace) {
/* 130 */     this.calculateDistanceInDataSpace = calculateDistanceInDataSpace;
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
/*     */   public double getCrosshairDistance() {
/* 143 */     return this.distance;
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
/*     */   public void setCrosshairDistance(double distance) {
/* 157 */     this.distance = distance;
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
/*     */ 
/*     */   
/*     */   public void updateCrosshairPoint(double x, double y, double transX, double transY, PlotOrientation orientation) {
/* 182 */     updateCrosshairPoint(x, y, 0, 0, transX, transY, orientation);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCrosshairPoint(double x, double y, int domainAxisIndex, int rangeAxisIndex, double transX, double transY, PlotOrientation orientation) {
/* 209 */     if (this.anchor != null) {
/* 210 */       double d = 0.0D;
/* 211 */       if (this.calculateDistanceInDataSpace) {
/* 212 */         d = (x - this.anchorX) * (x - this.anchorX) + (y - this.anchorY) * (y - this.anchorY);
/*     */       }
/*     */       else {
/*     */         
/* 216 */         double xx = this.anchor.getX();
/* 217 */         double yy = this.anchor.getY();
/* 218 */         if (orientation == PlotOrientation.HORIZONTAL) {
/* 219 */           double temp = yy;
/* 220 */           yy = xx;
/* 221 */           xx = temp;
/*     */         } 
/* 223 */         d = (transX - xx) * (transX - xx) + (transY - yy) * (transY - yy);
/*     */       } 
/*     */ 
/*     */       
/* 227 */       if (d < this.distance) {
/* 228 */         this.crosshairX = x;
/* 229 */         this.crosshairY = y;
/* 230 */         this.domainAxisIndex = domainAxisIndex;
/* 231 */         this.rangeAxisIndex = rangeAxisIndex;
/* 232 */         this.distance = d;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCrosshairX(double candidateX) {
/* 251 */     updateCrosshairX(candidateX, 0);
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
/*     */   public void updateCrosshairX(double candidateX, int domainAxisIndex) {
/* 268 */     double d = Math.abs(candidateX - this.anchorX);
/* 269 */     if (d < this.distance) {
/* 270 */       this.crosshairX = candidateX;
/* 271 */       this.domainAxisIndex = domainAxisIndex;
/* 272 */       this.distance = d;
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
/*     */   public void updateCrosshairY(double candidateY) {
/* 290 */     updateCrosshairY(candidateY, 0);
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
/*     */   public void updateCrosshairY(double candidateY, int rangeAxisIndex) {
/* 306 */     double d = Math.abs(candidateY - this.anchorY);
/* 307 */     if (d < this.distance) {
/* 308 */       this.crosshairY = candidateY;
/* 309 */       this.rangeAxisIndex = rangeAxisIndex;
/* 310 */       this.distance = d;
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
/*     */   public Point2D getAnchor() {
/* 325 */     return this.anchor;
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
/*     */   public void setAnchor(Point2D anchor) {
/* 342 */     this.anchor = anchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAnchorX() {
/* 353 */     return this.anchorX;
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
/*     */   public void setAnchorX(double x) {
/* 366 */     this.anchorX = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAnchorY() {
/* 377 */     return this.anchorY;
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
/*     */   public void setAnchorY(double y) {
/* 390 */     this.anchorY = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCrosshairX() {
/* 401 */     return this.crosshairX;
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
/*     */   public void setCrosshairX(double x) {
/* 416 */     this.crosshairX = x;
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
/*     */   public double getCrosshairY() {
/* 428 */     return this.crosshairY;
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
/*     */   public void setCrosshairY(double y) {
/* 442 */     this.crosshairY = y;
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
/*     */   public int getDatasetIndex() {
/* 457 */     return this.datasetIndex;
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
/*     */   public void setDatasetIndex(int index) {
/* 470 */     this.datasetIndex = index;
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
/*     */   public int getDomainAxisIndex() {
/* 484 */     return this.domainAxisIndex;
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
/*     */   public int getRangeAxisIndex() {
/* 498 */     return this.rangeAxisIndex;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/CrosshairState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */