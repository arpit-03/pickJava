/*     */ package org.apache.commons.math3.geometry.euclidean.threed;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.fraction.BigFraction;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.geometry.enclosing.EnclosingBall;
/*     */ import org.apache.commons.math3.geometry.enclosing.SupportBallGenerator;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.DiskGenerator;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
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
/*     */ public class SphereGenerator
/*     */   implements SupportBallGenerator<Euclidean3D, Vector3D>
/*     */ {
/*     */   public EnclosingBall<Euclidean3D, Vector3D> ballOnSupport(List<Vector3D> support) {
/*  38 */     if (support.size() < 1) {
/*  39 */       return new EnclosingBall((Point)Vector3D.ZERO, Double.NEGATIVE_INFINITY, (Point[])new Vector3D[0]);
/*     */     }
/*  41 */     Vector3D vA = support.get(0);
/*  42 */     if (support.size() < 2) {
/*  43 */       return new EnclosingBall((Point)vA, 0.0D, (Point[])new Vector3D[] { vA });
/*     */     }
/*  45 */     Vector3D vB = support.get(1);
/*  46 */     if (support.size() < 3) {
/*  47 */       return new EnclosingBall((Point)new Vector3D(0.5D, vA, 0.5D, vB), 0.5D * vA.distance(vB), (Point[])new Vector3D[] { vA, vB });
/*     */     }
/*     */ 
/*     */     
/*  51 */     Vector3D vC = support.get(2);
/*  52 */     if (support.size() < 4) {
/*     */ 
/*     */       
/*  55 */       Plane p = new Plane(vA, vB, vC, 1.0E-10D * (vA.getNorm1() + vB.getNorm1() + vC.getNorm1()));
/*     */       
/*  57 */       EnclosingBall<Euclidean2D, Vector2D> disk = (new DiskGenerator()).ballOnSupport(Arrays.asList(new Vector2D[] { p.toSubSpace(vA), p.toSubSpace(vB), p.toSubSpace(vC) }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  63 */       return new EnclosingBall((Point)p.toSpace((Vector<Euclidean2D>)disk.getCenter()), disk.getRadius(), (Point[])new Vector3D[] { vA, vB, vC });
/*     */     } 
/*     */ 
/*     */     
/*  67 */     Vector3D vD = support.get(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     BigFraction[] c2 = { new BigFraction(vA.getX()), new BigFraction(vB.getX()), new BigFraction(vC.getX()), new BigFraction(vD.getX()) };
/*     */ 
/*     */ 
/*     */     
/*  95 */     BigFraction[] c3 = { new BigFraction(vA.getY()), new BigFraction(vB.getY()), new BigFraction(vC.getY()), new BigFraction(vD.getY()) };
/*     */ 
/*     */ 
/*     */     
/*  99 */     BigFraction[] c4 = { new BigFraction(vA.getZ()), new BigFraction(vB.getZ()), new BigFraction(vC.getZ()), new BigFraction(vD.getZ()) };
/*     */ 
/*     */ 
/*     */     
/* 103 */     BigFraction[] c1 = { c2[0].multiply(c2[0]).add(c3[0].multiply(c3[0])).add(c4[0].multiply(c4[0])), c2[1].multiply(c2[1]).add(c3[1].multiply(c3[1])).add(c4[1].multiply(c4[1])), c2[2].multiply(c2[2]).add(c3[2].multiply(c3[2])).add(c4[2].multiply(c4[2])), c2[3].multiply(c2[3]).add(c3[3].multiply(c3[3])).add(c4[3].multiply(c4[3])) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     BigFraction twoM11 = minor(c2, c3, c4).multiply(2);
/* 110 */     BigFraction m12 = minor(c1, c3, c4);
/* 111 */     BigFraction m13 = minor(c1, c2, c4);
/* 112 */     BigFraction m14 = minor(c1, c2, c3);
/* 113 */     BigFraction centerX = m12.divide(twoM11);
/* 114 */     BigFraction centerY = m13.divide(twoM11).negate();
/* 115 */     BigFraction centerZ = m14.divide(twoM11);
/* 116 */     BigFraction dx = c2[0].subtract(centerX);
/* 117 */     BigFraction dy = c3[0].subtract(centerY);
/* 118 */     BigFraction dz = c4[0].subtract(centerZ);
/* 119 */     BigFraction r2 = dx.multiply(dx).add(dy.multiply(dy)).add(dz.multiply(dz));
/* 120 */     return new EnclosingBall((Point)new Vector3D(centerX.doubleValue(), centerY.doubleValue(), centerZ.doubleValue()), FastMath.sqrt(r2.doubleValue()), (Point[])new Vector3D[] { vA, vB, vC, vD });
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
/*     */   private BigFraction minor(BigFraction[] c1, BigFraction[] c2, BigFraction[] c3) {
/* 138 */     return c2[0].multiply(c3[1]).multiply(c1[2].subtract(c1[3])).add(c2[0].multiply(c3[2]).multiply(c1[3].subtract(c1[1]))).add(c2[0].multiply(c3[3]).multiply(c1[1].subtract(c1[2]))).add(c2[1].multiply(c3[0]).multiply(c1[3].subtract(c1[2]))).add(c2[1].multiply(c3[2]).multiply(c1[0].subtract(c1[3]))).add(c2[1].multiply(c3[3]).multiply(c1[2].subtract(c1[0]))).add(c2[2].multiply(c3[0]).multiply(c1[1].subtract(c1[3]))).add(c2[2].multiply(c3[1]).multiply(c1[3].subtract(c1[0]))).add(c2[2].multiply(c3[3]).multiply(c1[0].subtract(c1[1]))).add(c2[3].multiply(c3[0]).multiply(c1[2].subtract(c1[1]))).add(c2[3].multiply(c3[1]).multiply(c1[0].subtract(c1[2]))).add(c2[3].multiply(c3[2]).multiply(c1[1].subtract(c1[0])));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/SphereGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */