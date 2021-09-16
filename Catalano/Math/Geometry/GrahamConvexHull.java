/*     */ package Catalano.Math.Geometry;
/*     */ 
/*     */ import Catalano.Core.IntPoint;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GrahamConvexHull
/*     */ {
/*     */   public List<IntPoint> FindHull(List<IntPoint> points) {
/*  53 */     List<PointToProcess> pointsToProcess = new ArrayList<>();
/*     */     
/*  55 */     for (IntPoint p : points) {
/*  56 */       pointsToProcess.add(new PointToProcess(p));
/*     */     }
/*     */     
/*  59 */     int firstCornerIndex = 0;
/*  60 */     PointToProcess firstCorner = pointsToProcess.get(0);
/*     */     int i, n;
/*  62 */     for (i = 1, n = pointsToProcess.size(); i < n; i++) {
/*  63 */       if (((PointToProcess)pointsToProcess.get(i)).x < firstCorner.x || (((PointToProcess)pointsToProcess.get(i)).x == firstCorner.x && ((PointToProcess)pointsToProcess.get(i)).y < firstCorner.y)) {
/*  64 */         firstCorner = pointsToProcess.get(i);
/*  65 */         firstCornerIndex = i;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  70 */     pointsToProcess.remove(firstCornerIndex);
/*     */ 
/*     */     
/*  73 */     for (i = 0, n = pointsToProcess.size(); i < n; i++) {
/*  74 */       int dx = ((PointToProcess)pointsToProcess.get(i)).x - firstCorner.x;
/*  75 */       int dy = ((PointToProcess)pointsToProcess.get(i)).y - firstCorner.y;
/*     */ 
/*     */       
/*  78 */       ((PointToProcess)pointsToProcess.get(i)).distance = (dx * dx + dy * dy);
/*     */       
/*  80 */       ((PointToProcess)pointsToProcess.get(i)).k = (dx == 0) ? Float.POSITIVE_INFINITY : (dy / dx);
/*     */     } 
/*     */ 
/*     */     
/*  84 */     Collections.sort(pointsToProcess);
/*     */     
/*  86 */     ArrayList<PointToProcess> convexHullTemp = new ArrayList<>();
/*     */ 
/*     */     
/*  89 */     convexHullTemp.add(firstCorner);
/*     */     
/*  91 */     convexHullTemp.add(pointsToProcess.get(0));
/*  92 */     pointsToProcess.remove(0);
/*     */     
/*  94 */     PointToProcess lastPoint = convexHullTemp.get(1);
/*  95 */     PointToProcess prevPoint = convexHullTemp.get(0);
/*     */     
/*  97 */     while (!pointsToProcess.isEmpty()) {
/*  98 */       PointToProcess newPoint = pointsToProcess.get(0);
/*     */ 
/*     */ 
/*     */       
/* 102 */       if (newPoint.k == lastPoint.k || newPoint.distance == 0.0F) {
/* 103 */         pointsToProcess.remove(0);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 108 */       if ((newPoint.x - prevPoint.x) * (lastPoint.y - newPoint.y) - (lastPoint.x - newPoint.x) * (newPoint.y - prevPoint.y) < 0) {
/*     */         
/* 110 */         convexHullTemp.add(newPoint);
/*     */         
/* 112 */         pointsToProcess.remove(0);
/*     */         
/* 114 */         prevPoint = lastPoint;
/* 115 */         lastPoint = newPoint;
/*     */         
/*     */         continue;
/*     */       } 
/* 119 */       convexHullTemp.remove(convexHullTemp.size() - 1);
/*     */       
/* 121 */       lastPoint = prevPoint;
/* 122 */       prevPoint = convexHullTemp.get(convexHullTemp.size() - 2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 127 */     ArrayList<IntPoint> convexHull = new ArrayList<>();
/*     */     
/* 129 */     for (PointToProcess p : convexHullTemp) {
/* 130 */       convexHull.add(p.toPoint());
/*     */     }
/*     */     
/* 133 */     return convexHull;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/GrahamConvexHull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */