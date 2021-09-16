/*     */ package org.apache.commons.math3.geometry.spherical.oned;
/*     */ 
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Space;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S1Point
/*     */   implements Point<Sphere1D>
/*     */ {
/*  33 */   public static final S1Point NaN = new S1Point(Double.NaN, Vector2D.NaN);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 20131218L;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double alpha;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Vector2D vector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S1Point(double alpha) {
/*  51 */     this(MathUtils.normalizeAngle(alpha, Math.PI), new Vector2D(FastMath.cos(alpha), FastMath.sin(alpha)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private S1Point(double alpha, Vector2D vector) {
/*  60 */     this.alpha = alpha;
/*  61 */     this.vector = vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAlpha() {
/*  69 */     return this.alpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D getVector() {
/*  76 */     return this.vector;
/*     */   }
/*     */ 
/*     */   
/*     */   public Space getSpace() {
/*  81 */     return Sphere1D.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNaN() {
/*  86 */     return Double.isNaN(this.alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public double distance(Point<Sphere1D> point) {
/*  91 */     return distance(this, (S1Point)point);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double distance(S1Point p1, S1Point p2) {
/* 100 */     return Vector2D.angle(p1.vector, p2.vector);
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
/*     */   public boolean equals(Object other) {
/* 125 */     if (this == other) {
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     if (other instanceof S1Point) {
/* 130 */       S1Point rhs = (S1Point)other;
/* 131 */       if (rhs.isNaN()) {
/* 132 */         return isNaN();
/*     */       }
/*     */       
/* 135 */       return (this.alpha == rhs.alpha);
/*     */     } 
/*     */     
/* 138 */     return false;
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
/*     */   public int hashCode() {
/* 151 */     if (isNaN()) {
/* 152 */       return 542;
/*     */     }
/* 154 */     return 1759 * MathUtils.hash(this.alpha);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/oned/S1Point.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */