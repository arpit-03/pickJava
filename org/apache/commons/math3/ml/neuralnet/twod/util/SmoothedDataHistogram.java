/*    */ package org.apache.commons.math3.ml.neuralnet.twod.util;
/*    */ 
/*    */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SmoothedDataHistogram
/*    */   implements MapDataVisualization
/*    */ {
/*    */   private final int smoothingBins;
/*    */   private final DistanceMeasure distance;
/*    */   private final double membershipNormalization;
/*    */   
/*    */   public SmoothedDataHistogram(int smoothingBins, DistanceMeasure distance) {
/* 50 */     this.smoothingBins = smoothingBins;
/* 51 */     this.distance = distance;
/*    */     
/* 53 */     double sum = 0.0D;
/* 54 */     for (int i = 0; i < smoothingBins; i++) {
/* 55 */       sum += (smoothingBins - i);
/*    */     }
/*    */     
/* 58 */     this.membershipNormalization = 1.0D / sum;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] computeImage(NeuronSquareMesh2D map, Iterable<double[]> data) {
/* 70 */     int nR = map.getNumberOfRows();
/* 71 */     int nC = map.getNumberOfColumns();
/*    */     
/* 73 */     int mapSize = nR * nC;
/* 74 */     if (mapSize < this.smoothingBins) {
/* 75 */       throw new NumberIsTooSmallException(Integer.valueOf(mapSize), Integer.valueOf(this.smoothingBins), true);
/*    */     }
/*    */     
/* 78 */     LocationFinder finder = new LocationFinder(map);
/*    */ 
/*    */     
/* 81 */     double[][] histo = new double[nR][nC];
/*    */     
/* 83 */     for (double[] sample : data) {
/* 84 */       Neuron[] sorted = MapUtils.sort(sample, (Iterable)map.getNetwork(), this.distance);
/*    */ 
/*    */       
/* 87 */       for (int i = 0; i < this.smoothingBins; i++) {
/* 88 */         LocationFinder.Location loc = finder.getLocation(sorted[i]);
/* 89 */         int row = loc.getRow();
/* 90 */         int col = loc.getColumn();
/* 91 */         histo[row][col] = histo[row][col] + (this.smoothingBins - i) * this.membershipNormalization;
/*    */       } 
/*    */     } 
/*    */     
/* 95 */     return histo;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/twod/util/SmoothedDataHistogram.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */