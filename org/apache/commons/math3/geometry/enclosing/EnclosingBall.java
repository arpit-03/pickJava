/*     */ package org.apache.commons.math3.geometry.enclosing;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Space;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnclosingBall<S extends Space, P extends Point<S>>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20140126L;
/*     */   private final P center;
/*     */   private final double radius;
/*     */   private final P[] support;
/*     */   
/*     */   public EnclosingBall(P center, double radius, P... support) {
/*  52 */     this.center = center;
/*  53 */     this.radius = radius;
/*  54 */     this.support = (P[])support.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public P getCenter() {
/*  61 */     return this.center;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRadius() {
/*  68 */     return this.radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public P[] getSupport() {
/*  75 */     return (P[])this.support.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSupportSize() {
/*  82 */     return this.support.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(P point) {
/*  90 */     return (point.distance((Point)this.center) <= this.radius);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(P point, double margin) {
/* 100 */     return (point.distance((Point)this.center) <= this.radius + margin);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/enclosing/EnclosingBall.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */