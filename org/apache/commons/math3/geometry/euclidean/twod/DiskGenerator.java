/*     */ package org.apache.commons.math3.geometry.euclidean.twod;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.fraction.BigFraction;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.enclosing.EnclosingBall;
/*     */ import org.apache.commons.math3.geometry.enclosing.SupportBallGenerator;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiskGenerator
/*     */   implements SupportBallGenerator<Euclidean2D, Vector2D>
/*     */ {
/*     */   public EnclosingBall<Euclidean2D, Vector2D> ballOnSupport(List<Vector2D> support) {
/*  34 */     if (support.size() < 1) {
/*  35 */       return new EnclosingBall((Point)Vector2D.ZERO, Double.NEGATIVE_INFINITY, (Point[])new Vector2D[0]);
/*     */     }
/*  37 */     Vector2D vA = support.get(0);
/*  38 */     if (support.size() < 2) {
/*  39 */       return new EnclosingBall((Point)vA, 0.0D, (Point[])new Vector2D[] { vA });
/*     */     }
/*  41 */     Vector2D vB = support.get(1);
/*  42 */     if (support.size() < 3) {
/*  43 */       return new EnclosingBall((Point)new Vector2D(0.5D, vA, 0.5D, vB), 0.5D * vA.distance(vB), (Point[])new Vector2D[] { vA, vB });
/*     */     }
/*     */ 
/*     */     
/*  47 */     Vector2D vC = support.get(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     BigFraction[] c2 = { new BigFraction(vA.getX()), new BigFraction(vB.getX()), new BigFraction(vC.getX()) };
/*     */ 
/*     */     
/*  72 */     BigFraction[] c3 = { new BigFraction(vA.getY()), new BigFraction(vB.getY()), new BigFraction(vC.getY()) };
/*     */ 
/*     */     
/*  75 */     BigFraction[] c1 = { c2[0].multiply(c2[0]).add(c3[0].multiply(c3[0])), c2[1].multiply(c2[1]).add(c3[1].multiply(c3[1])), c2[2].multiply(c2[2]).add(c3[2].multiply(c3[2])) };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     BigFraction twoM11 = minor(c2, c3).multiply(2);
/*  81 */     BigFraction m12 = minor(c1, c3);
/*  82 */     BigFraction m13 = minor(c1, c2);
/*  83 */     BigFraction centerX = m12.divide(twoM11);
/*  84 */     BigFraction centerY = m13.divide(twoM11).negate();
/*  85 */     BigFraction dx = c2[0].subtract(centerX);
/*  86 */     BigFraction dy = c3[0].subtract(centerY);
/*  87 */     BigFraction r2 = dx.multiply(dx).add(dy.multiply(dy));
/*  88 */     return new EnclosingBall((Point)new Vector2D(centerX.doubleValue(), centerY.doubleValue()), FastMath.sqrt(r2.doubleValue()), (Point[])new Vector2D[] { vA, vB, vC });
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
/*     */   private BigFraction minor(BigFraction[] c1, BigFraction[] c2) {
/* 103 */     return c2[0].multiply(c1[2].subtract(c1[1])).add(c2[1].multiply(c1[0].subtract(c1[2]))).add(c2[2].multiply(c1[1].subtract(c1[0])));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/DiskGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */