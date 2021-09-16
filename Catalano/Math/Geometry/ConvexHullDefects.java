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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConvexHullDefects
/*     */ {
/*     */   private double minimumDepth;
/*     */   
/*     */   public ConvexHullDefects(double minimumDepth) {
/*  43 */     this.minimumDepth = minimumDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinimumDepth() {
/*  51 */     return this.minimumDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinimumDepth(double minimumDepth) {
/*  59 */     this.minimumDepth = minimumDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ConvexityDefect> FindDefects(List<IntPoint> contour, List<IntPoint> convexHull) {
/*     */     try {
/*  70 */       if (contour.size() < 4) throw new Exception("Point sequence size should have at least 4 points."); 
/*  71 */       if (convexHull.size() < 3) throw new Exception("Convex hull must have at least 3 points."); 
/*  72 */     } catch (Exception e) {
/*  73 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/*  77 */     int[] indexes = new int[convexHull.size()];
/*  78 */     for (int i = 0, j = 0; i < contour.size(); i++) {
/*  79 */       if (convexHull.contains(contour.get(i))) {
/*  80 */         indexes[j++] = i;
/*     */       }
/*     */     } 
/*     */     
/*  84 */     List<ConvexityDefect> defects = new ArrayList<>();
/*     */ 
/*     */     
/*  87 */     for (int k = 0; k < indexes.length - 1; k++) {
/*  88 */       ConvexityDefect current = ExtractDefect(contour, indexes[k], indexes[k + 1]);
/*     */       
/*  90 */       if (current.getDepth() > this.minimumDepth)
/*     */       {
/*  92 */         defects.add(current);
/*     */       }
/*     */     } 
/*  95 */     return defects;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConvexityDefect ExtractDefect(List<IntPoint> contour, int startIndex, int endIndex) {
/* 100 */     IntPoint start = contour.get(startIndex);
/* 101 */     IntPoint end = contour.get(endIndex);
/*     */     
/* 103 */     Line line = Line.FromPoints(start, end);
/*     */     
/* 105 */     double maxDepth = 0.0D;
/* 106 */     int maxIndex = 0;
/*     */     
/* 108 */     for (int i = startIndex; i < endIndex; i++) {
/* 109 */       double d = line.DistanceToPoint(contour.get(i));
/*     */       
/* 111 */       if (d > maxDepth) {
/* 112 */         maxDepth = d;
/* 113 */         maxIndex = i;
/*     */       } 
/*     */     } 
/* 116 */     return new ConvexityDefect(contour.get(maxIndex), startIndex, endIndex, maxDepth);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/ConvexHullDefects.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */