/*    */ package org.apache.commons.math3.ml.neuralnet.twod.util;
/*    */ 
/*    */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
/*    */ import org.apache.commons.math3.ml.neuralnet.MapUtils;
/*    */ import org.apache.commons.math3.ml.neuralnet.Neuron;
/*    */ import org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HitHistogram
/*    */   implements MapDataVisualization
/*    */ {
/*    */   private final DistanceMeasure distance;
/*    */   private final boolean normalizeCount;
/*    */   
/*    */   public HitHistogram(boolean normalizeCount, DistanceMeasure distance) {
/* 45 */     this.normalizeCount = normalizeCount;
/* 46 */     this.distance = distance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] computeImage(NeuronSquareMesh2D map, Iterable<double[]> data) {
/* 52 */     int nR = map.getNumberOfRows();
/* 53 */     int nC = map.getNumberOfColumns();
/*    */     
/* 55 */     LocationFinder finder = new LocationFinder(map);
/*    */ 
/*    */     
/* 58 */     int numSamples = 0;
/*    */     
/* 60 */     double[][] hit = new double[nR][nC];
/*    */     
/* 62 */     for (double[] sample : data) {
/* 63 */       Neuron best = MapUtils.findBest(sample, (Iterable)map, this.distance);
/*    */       
/* 65 */       LocationFinder.Location loc = finder.getLocation(best);
/* 66 */       int row = loc.getRow();
/* 67 */       int col = loc.getColumn();
/* 68 */       hit[row][col] = hit[row][col] + 1.0D;
/*    */       
/* 70 */       numSamples++;
/*    */     } 
/*    */     
/* 73 */     if (this.normalizeCount) {
/* 74 */       for (int r = 0; r < nR; r++) {
/* 75 */         for (int c = 0; c < nC; c++) {
/* 76 */           hit[r][c] = hit[r][c] / numSamples;
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 81 */     return hit;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/twod/util/HitHistogram.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */