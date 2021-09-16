/*     */ package org.jfree.chart.renderer;
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
/*     */ public class Outlier
/*     */   implements Comparable
/*     */ {
/*     */   private Point2D point;
/*     */   private double radius;
/*     */   
/*     */   public Outlier(double xCoord, double yCoord, double radius) {
/*  74 */     this.point = new Point2D.Double(xCoord - radius, yCoord - radius);
/*  75 */     this.radius = radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2D getPoint() {
/*  85 */     return this.point;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPoint(Point2D point) {
/*  95 */     this.point = point;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 105 */     return getPoint().getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 115 */     return getPoint().getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRadius() {
/* 124 */     return this.radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRadius(double radius) {
/* 133 */     this.radius = radius;
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
/*     */   public int compareTo(Object o) {
/* 147 */     Outlier outlier = (Outlier)o;
/* 148 */     Point2D p1 = getPoint();
/* 149 */     Point2D p2 = outlier.getPoint();
/* 150 */     if (p1.equals(p2)) {
/* 151 */       return 0;
/*     */     }
/* 153 */     if (p1.getX() < p2.getX() || p1.getY() < p2.getY()) {
/* 154 */       return -1;
/*     */     }
/*     */     
/* 157 */     return 1;
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
/*     */   public boolean overlaps(Outlier other) {
/* 172 */     return (other.getX() >= getX() - this.radius * 1.1D && other
/* 173 */       .getX() <= getX() + this.radius * 1.1D && other
/* 174 */       .getY() >= getY() - this.radius * 1.1D && other
/* 175 */       .getY() <= getY() + this.radius * 1.1D);
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
/* 187 */     if (obj == this) {
/* 188 */       return true;
/*     */     }
/* 190 */     if (!(obj instanceof Outlier)) {
/* 191 */       return false;
/*     */     }
/* 193 */     Outlier that = (Outlier)obj;
/* 194 */     if (!this.point.equals(that.point)) {
/* 195 */       return false;
/*     */     }
/* 197 */     if (this.radius != that.radius) {
/* 198 */       return false;
/*     */     }
/* 200 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 210 */     return "{" + getX() + "," + getY() + "}";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/Outlier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */