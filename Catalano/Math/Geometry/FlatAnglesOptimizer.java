/*     */ package Catalano.Math.Geometry;
/*     */ 
/*     */ import Catalano.Core.IntPoint;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlatAnglesOptimizer
/*     */   implements IShapeOptimizer
/*     */ {
/*  36 */   private float maxAngleToKeep = 160.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FlatAnglesOptimizer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FlatAnglesOptimizer(float maxAngleToKeep) {
/*  50 */     this.maxAngleToKeep = maxAngleToKeep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxAngleToKeep() {
/*  58 */     return this.maxAngleToKeep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxAngleToKeep(float maxAngleToKeep) {
/*  66 */     this.maxAngleToKeep = Math.min(180.0F, Math.max(140.0F, maxAngleToKeep));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IntPoint> OptimizeShape(List<IntPoint> shape) {
/*  73 */     ArrayList<IntPoint> optimizedShape = new ArrayList<>();
/*     */     
/*  75 */     if (shape.size() <= 3) {
/*     */       
/*  77 */       optimizedShape.addAll(shape);
/*     */     } else {
/*     */       
/*  80 */       float angle = 0.0F;
/*     */ 
/*     */       
/*  83 */       optimizedShape.add(shape.get(0));
/*  84 */       optimizedShape.add(shape.get(1));
/*  85 */       int pointsInOptimizedHull = 2;
/*     */       
/*  87 */       for (int i = 2, n = shape.size(); i < n; i++) {
/*     */         
/*  89 */         optimizedShape.add(shape.get(i));
/*  90 */         pointsInOptimizedHull++;
/*     */ 
/*     */         
/*  93 */         angle = GeometryTools.GetAngleBetweenVectors(optimizedShape.get(pointsInOptimizedHull - 2), 
/*  94 */             optimizedShape.get(pointsInOptimizedHull - 3), optimizedShape.get(pointsInOptimizedHull - 1));
/*     */         
/*  96 */         if (angle > this.maxAngleToKeep && (
/*  97 */           pointsInOptimizedHull > 3 || i < n - 1)) {
/*     */           
/*  99 */           optimizedShape.remove(pointsInOptimizedHull - 2);
/* 100 */           pointsInOptimizedHull--;
/*     */         } 
/*     */       } 
/*     */       
/* 104 */       if (pointsInOptimizedHull > 3) {
/*     */         
/* 106 */         angle = GeometryTools.GetAngleBetweenVectors(optimizedShape.get(pointsInOptimizedHull - 1), 
/* 107 */             optimizedShape.get(pointsInOptimizedHull - 2), optimizedShape.get(0));
/*     */         
/* 109 */         if (angle > this.maxAngleToKeep) {
/* 110 */           optimizedShape.remove(pointsInOptimizedHull - 1);
/* 111 */           pointsInOptimizedHull--;
/*     */         } 
/*     */         
/* 114 */         if (pointsInOptimizedHull > 3) {
/*     */           
/* 116 */           angle = GeometryTools.GetAngleBetweenVectors(optimizedShape.get(0), 
/* 117 */               optimizedShape.get(pointsInOptimizedHull - 1), optimizedShape.get(1));
/*     */           
/* 119 */           if (angle > this.maxAngleToKeep) {
/* 120 */             optimizedShape.remove(0);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 125 */     return optimizedShape;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/FlatAnglesOptimizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */