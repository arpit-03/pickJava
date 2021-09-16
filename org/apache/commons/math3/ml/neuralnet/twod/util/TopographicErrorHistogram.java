/*    */ package org.apache.commons.math3.ml.neuralnet.twod.util;
/*    */ 
/*    */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
/*    */ import org.apache.commons.math3.ml.neuralnet.MapUtils;
/*    */ import org.apache.commons.math3.ml.neuralnet.Network;
/*    */ import org.apache.commons.math3.ml.neuralnet.Neuron;
/*    */ import org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D;
/*    */ import org.apache.commons.math3.util.Pair;
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
/*    */ public class TopographicErrorHistogram
/*    */   implements MapDataVisualization
/*    */ {
/*    */   private final DistanceMeasure distance;
/*    */   private final boolean relativeCount;
/*    */   
/*    */   public TopographicErrorHistogram(boolean relativeCount, DistanceMeasure distance) {
/* 47 */     this.relativeCount = relativeCount;
/* 48 */     this.distance = distance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] computeImage(NeuronSquareMesh2D map, Iterable<double[]> data) {
/* 54 */     int nR = map.getNumberOfRows();
/* 55 */     int nC = map.getNumberOfColumns();
/*    */     
/* 57 */     Network net = map.getNetwork();
/* 58 */     LocationFinder finder = new LocationFinder(map);
/*    */ 
/*    */     
/* 61 */     int[][] hit = new int[nR][nC];
/*    */     
/* 63 */     double[][] error = new double[nR][nC];
/*    */     
/* 65 */     for (double[] sample : data) {
/* 66 */       Pair<Neuron, Neuron> p = MapUtils.findBestAndSecondBest(sample, (Iterable)map, this.distance);
/* 67 */       Neuron best = (Neuron)p.getFirst();
/*    */       
/* 69 */       LocationFinder.Location loc = finder.getLocation(best);
/* 70 */       int row = loc.getRow();
/* 71 */       int col = loc.getColumn();
/* 72 */       hit[row][col] = hit[row][col] + 1;
/*    */       
/* 74 */       if (!net.getNeighbours(best).contains(p.getSecond()))
/*    */       {
/*    */         
/* 77 */         error[row][col] = error[row][col] + 1.0D;
/*    */       }
/*    */     } 
/*    */     
/* 81 */     if (this.relativeCount) {
/* 82 */       for (int r = 0; r < nR; r++) {
/* 83 */         for (int c = 0; c < nC; c++) {
/* 84 */           error[r][c] = error[r][c] / hit[r][c];
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 89 */     return error;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/twod/util/TopographicErrorHistogram.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */