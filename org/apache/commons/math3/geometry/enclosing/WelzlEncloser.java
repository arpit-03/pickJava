/*     */ package org.apache.commons.math3.geometry.enclosing;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WelzlEncloser<S extends Space, P extends Point<S>>
/*     */   implements Encloser<S, P>
/*     */ {
/*     */   private final double tolerance;
/*     */   private final SupportBallGenerator<S, P> generator;
/*     */   
/*     */   public WelzlEncloser(double tolerance, SupportBallGenerator<S, P> generator) {
/*  56 */     this.tolerance = tolerance;
/*  57 */     this.generator = generator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnclosingBall<S, P> enclose(Iterable<P> points) {
/*  63 */     if (points == null || !points.iterator().hasNext())
/*     */     {
/*  65 */       return this.generator.ballOnSupport(new ArrayList<P>());
/*     */     }
/*     */ 
/*     */     
/*  69 */     return pivotingBall(points);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EnclosingBall<S, P> pivotingBall(Iterable<P> points) {
/*  79 */     Point point = points.iterator().next();
/*  80 */     List<P> extreme = new ArrayList<P>(point.getSpace().getDimension() + 1);
/*  81 */     List<P> support = new ArrayList<P>(point.getSpace().getDimension() + 1);
/*     */ 
/*     */     
/*  84 */     extreme.add((P)point);
/*  85 */     EnclosingBall<S, P> ball = moveToFrontBall(extreme, extreme.size(), support);
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*  90 */       P farthest = selectFarthest(points, ball);
/*     */       
/*  92 */       if (ball.contains(farthest, this.tolerance))
/*     */       {
/*  94 */         return ball;
/*     */       }
/*     */ 
/*     */       
/*  98 */       support.clear();
/*  99 */       support.add(farthest);
/* 100 */       EnclosingBall<S, P> savedBall = ball;
/* 101 */       ball = moveToFrontBall(extreme, extreme.size(), support);
/* 102 */       if (ball.getRadius() < savedBall.getRadius())
/*     */       {
/* 104 */         throw new MathInternalError();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 109 */       extreme.add(0, farthest);
/*     */ 
/*     */       
/* 112 */       extreme.subList(ball.getSupportSize(), extreme.size()).clear();
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
/*     */   private EnclosingBall<S, P> moveToFrontBall(List<P> extreme, int nbExtreme, List<P> support) {
/* 128 */     EnclosingBall<S, P> ball = this.generator.ballOnSupport(support);
/*     */     
/* 130 */     if (ball.getSupportSize() <= ball.getCenter().getSpace().getDimension())
/*     */     {
/* 132 */       for (int i = 0; i < nbExtreme; i++) {
/* 133 */         Point point = (Point)extreme.get(i);
/* 134 */         if (!ball.contains((P)point, this.tolerance)) {
/*     */ 
/*     */ 
/*     */           
/* 138 */           support.add((P)point);
/* 139 */           ball = moveToFrontBall(extreme, i, support);
/* 140 */           support.remove(support.size() - 1);
/*     */ 
/*     */ 
/*     */           
/* 144 */           for (int j = i; j > 0; j--) {
/* 145 */             extreme.set(j, extreme.get(j - 1));
/*     */           }
/* 147 */           extreme.set(0, (P)point);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 154 */     return ball;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public P selectFarthest(Iterable<P> points, EnclosingBall<S, P> ball) {
/*     */     Point point;
/* 165 */     P center = ball.getCenter();
/* 166 */     P farthest = null;
/* 167 */     double dMax = -1.0D;
/*     */     
/* 169 */     for (Point point1 : points) {
/* 170 */       double d = point1.distance((Point)center);
/* 171 */       if (d > dMax) {
/* 172 */         point = point1;
/* 173 */         dMax = d;
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     return (P)point;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/enclosing/WelzlEncloser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */