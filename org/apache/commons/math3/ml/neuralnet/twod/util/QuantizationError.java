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
/*    */ public class QuantizationError
/*    */   implements MapDataVisualization
/*    */ {
/*    */   private final DistanceMeasure distance;
/*    */   
/*    */   public QuantizationError(DistanceMeasure distance) {
/* 39 */     this.distance = distance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] computeImage(NeuronSquareMesh2D map, Iterable<double[]> data) {
/* 45 */     int nR = map.getNumberOfRows();
/* 46 */     int nC = map.getNumberOfColumns();
/*    */     
/* 48 */     LocationFinder finder = new LocationFinder(map);
/*    */ 
/*    */     
/* 51 */     int[][] hit = new int[nR][nC];
/*    */     
/* 53 */     double[][] error = new double[nR][nC];
/*    */     
/* 55 */     for (double[] sample : data) {
/* 56 */       Neuron best = MapUtils.findBest(sample, (Iterable)map, this.distance);
/*    */       
/* 58 */       LocationFinder.Location loc = finder.getLocation(best);
/* 59 */       int row = loc.getRow();
/* 60 */       int col = loc.getColumn();
/* 61 */       hit[row][col] = hit[row][col] + 1;
/* 62 */       error[row][col] = error[row][col] + this.distance.compute(sample, best.getFeatures());
/*    */     } 
/*    */     
/* 65 */     for (int r = 0; r < nR; r++) {
/* 66 */       for (int c = 0; c < nC; c++) {
/* 67 */         int count = hit[r][c];
/* 68 */         if (count != 0) {
/* 69 */           error[r][c] = error[r][c] / count;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 74 */     return error;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/twod/util/QuantizationError.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */