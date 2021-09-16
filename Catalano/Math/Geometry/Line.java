/*     */ package Catalano.Math.Geometry;
/*     */ 
/*     */ import Catalano.Core.FloatPoint;
/*     */ import Catalano.Core.IntPoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Line
/*     */ {
/*     */   private float k;
/*     */   private float b;
/*     */   
/*     */   public Line(IntPoint start, IntPoint end) {
/*  38 */     if (start.equals(end)) {
/*     */       try {
/*  40 */         throw new Exception("Start point of the line cannot be the same as its end point.");
/*  41 */       } catch (Exception e) {
/*  42 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  46 */     float d = (end.x - start.x);
/*     */ 
/*     */     
/*  49 */     this.k = (end.y - start.y) / d;
/*  50 */     this.b = Float.isInfinite(this.k) ? start.x : (start.y - this.k * start.x);
/*     */   }
/*     */   
/*     */   public Line(float slope, float intercept) {
/*  54 */     this.k = slope;
/*  55 */     this.b = intercept;
/*     */   }
/*     */ 
/*     */   
/*     */   private Line(float radius, float theta, boolean unused) {
/*  60 */     theta *= 0.017453292F;
/*     */     
/*  62 */     float sine = (float)Math.sin(theta), cosine = (float)Math.cos(theta);
/*  63 */     FloatPoint pt1 = new FloatPoint(radius * cosine, radius * sine);
/*     */ 
/*     */     
/*  66 */     this.k = -cosine / sine;
/*     */     
/*  68 */     if (!Float.isInfinite(this.k)) {
/*     */       
/*  70 */       this.b = pt1.y - this.k * pt1.x;
/*     */     }
/*     */     else {
/*     */       
/*  74 */       this.b = Math.abs(radius);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Line(IntPoint point, float theta) {
/*  79 */     theta *= 0.017453292F;
/*     */     
/*  81 */     this.k = (float)(-1.0D / Math.tan(theta));
/*     */     
/*  83 */     if (!Float.isInfinite(this.k)) {
/*     */       
/*  85 */       this.b = point.y - this.k * point.x;
/*     */     }
/*     */     else {
/*     */       
/*  89 */       this.b = point.x;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isVertical() {
/*  94 */     return Float.isInfinite(this.k);
/*     */   }
/*     */   
/*     */   public boolean isHorizontal() {
/*  98 */     return (this.k == 0.0F);
/*     */   }
/*     */   
/*     */   public float getSlope() {
/* 102 */     return this.k;
/*     */   }
/*     */   
/*     */   public float getIntercept() {
/* 106 */     return this.b;
/*     */   }
/*     */   
/*     */   public static Line FromPoints(IntPoint p1, IntPoint p2) {
/* 110 */     return new Line(p1, p2);
/*     */   }
/*     */   
/*     */   public static Line FromSlopeIntercept(float slope, float intercept) {
/* 114 */     return new Line(slope, intercept);
/*     */   }
/*     */   
/*     */   public static Line FromRTheta(float radius, float theta) {
/* 118 */     return new Line(radius, theta, false);
/*     */   }
/*     */   
/*     */   public static Line FromPointTheta(IntPoint p, float theta) {
/* 122 */     return new Line(p, theta);
/*     */   }
/*     */   
/*     */   public float GetAngleBetweenLines(Line secondLine) {
/* 126 */     float k2 = secondLine.k;
/*     */     
/* 128 */     boolean isVertical1 = isVertical();
/* 129 */     boolean isVertical2 = secondLine.isVertical();
/*     */ 
/*     */     
/* 132 */     if (this.k == k2 || (isVertical1 && isVertical2)) {
/* 133 */       return 0.0F;
/*     */     }
/* 135 */     float angle = 0.0F;
/*     */     
/* 137 */     if (!isVertical1 && !isVertical2) {
/* 138 */       float tanPhi = ((k2 > this.k) ? (k2 - this.k) : (this.k - k2)) / (1.0F + this.k * k2);
/* 139 */       angle = (float)Math.atan(tanPhi);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 144 */     else if (isVertical1) {
/* 145 */       angle = (float)(1.5707963267948966D - Math.atan(k2) * Math.signum(k2));
/*     */     } else {
/*     */       
/* 148 */       angle = (float)(1.5707963267948966D - Math.atan(this.k) * Math.signum(this.k));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 153 */     angle *= 57.29578F;
/*     */     
/* 155 */     if (angle < 0.0F) {
/* 156 */       angle = -angle;
/*     */     }
/* 158 */     return angle;
/*     */   }
/*     */ 
/*     */   
/*     */   public float DistanceToPoint(IntPoint point) {
/*     */     float distance;
/* 164 */     if (!isVertical()) {
/*     */       
/* 166 */       float div = (float)Math.sqrt((this.k * this.k + 1.0F));
/* 167 */       distance = Math.abs((this.k * point.x + this.b - point.y) / div);
/*     */     }
/*     */     else {
/*     */       
/* 171 */       distance = Math.abs(this.b - point.x);
/*     */     } 
/*     */     
/* 174 */     return distance;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/Line.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */