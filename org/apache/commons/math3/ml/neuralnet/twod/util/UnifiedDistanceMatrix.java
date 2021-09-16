/*     */ package org.apache.commons.math3.ml.neuralnet.twod.util;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
/*     */ import org.apache.commons.math3.ml.neuralnet.Network;
/*     */ import org.apache.commons.math3.ml.neuralnet.Neuron;
/*     */ import org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D;
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
/*     */ 
/*     */ public class UnifiedDistanceMatrix
/*     */   implements MapVisualization
/*     */ {
/*     */   private final boolean individualDistances;
/*     */   private final DistanceMeasure distance;
/*     */   
/*     */   public UnifiedDistanceMatrix(boolean individualDistances, DistanceMeasure distance) {
/*  59 */     this.individualDistances = individualDistances;
/*  60 */     this.distance = distance;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[][] computeImage(NeuronSquareMesh2D map) {
/*  65 */     if (this.individualDistances) {
/*  66 */       return individualDistances(map);
/*     */     }
/*  68 */     return averageDistances(map);
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
/*     */   private double[][] individualDistances(NeuronSquareMesh2D map) {
/*  84 */     int numRows = map.getNumberOfRows();
/*  85 */     int numCols = map.getNumberOfColumns();
/*     */     
/*  87 */     double[][] uMatrix = new double[numRows * 2 + 1][numCols * 2 + 1];
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */     
/*  93 */     for (i = 0; i < numRows; i++) {
/*     */       
/*  95 */       int iR = 2 * i + 1;
/*     */       
/*  97 */       for (int j = 0; j < numCols; j++) {
/*     */         
/*  99 */         int jR = 2 * j + 1;
/*     */         
/* 101 */         double[] current = map.getNeuron(i, j).getFeatures();
/*     */ 
/*     */ 
/*     */         
/* 105 */         Neuron neighbour = map.getNeuron(i, j, NeuronSquareMesh2D.HorizontalDirection.RIGHT, NeuronSquareMesh2D.VerticalDirection.CENTER);
/*     */ 
/*     */         
/* 108 */         if (neighbour != null) {
/* 109 */           uMatrix[iR][jR + 1] = this.distance.compute(current, neighbour.getFeatures());
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 114 */         neighbour = map.getNeuron(i, j, NeuronSquareMesh2D.HorizontalDirection.CENTER, NeuronSquareMesh2D.VerticalDirection.DOWN);
/*     */ 
/*     */         
/* 117 */         if (neighbour != null) {
/* 118 */           uMatrix[iR + 1][jR] = this.distance.compute(current, neighbour.getFeatures());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     for (i = 0; i < numRows; i++) {
/*     */       
/* 131 */       int iR = 2 * i + 1;
/*     */       
/* 133 */       for (int j = 0; j < numCols; j++) {
/*     */         
/* 135 */         int jR = 2 * j + 1;
/*     */         
/* 137 */         Neuron current = map.getNeuron(i, j);
/* 138 */         Neuron right = map.getNeuron(i, j, NeuronSquareMesh2D.HorizontalDirection.RIGHT, NeuronSquareMesh2D.VerticalDirection.CENTER);
/*     */ 
/*     */         
/* 141 */         Neuron bottom = map.getNeuron(i, j, NeuronSquareMesh2D.HorizontalDirection.CENTER, NeuronSquareMesh2D.VerticalDirection.DOWN);
/*     */ 
/*     */         
/* 144 */         Neuron bottomRight = map.getNeuron(i, j, NeuronSquareMesh2D.HorizontalDirection.RIGHT, NeuronSquareMesh2D.VerticalDirection.DOWN);
/*     */ 
/*     */ 
/*     */         
/* 148 */         double current2BottomRight = (bottomRight == null) ? 0.0D : this.distance.compute(current.getFeatures(), bottomRight.getFeatures());
/*     */ 
/*     */ 
/*     */         
/* 152 */         double right2Bottom = (right == null || bottom == null) ? 0.0D : this.distance.compute(right.getFeatures(), bottom.getFeatures());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 159 */         uMatrix[iR + 1][jR + 1] = 0.5D * (current2BottomRight + right2Bottom);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 164 */     int lastRow = uMatrix.length - 1;
/* 165 */     uMatrix[0] = uMatrix[lastRow];
/*     */ 
/*     */ 
/*     */     
/* 169 */     int lastCol = (uMatrix[0]).length - 1;
/* 170 */     for (int r = 0; r < lastRow; r++) {
/* 171 */       uMatrix[r][0] = uMatrix[r][lastCol];
/*     */     }
/*     */     
/* 174 */     return uMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[][] averageDistances(NeuronSquareMesh2D map) {
/* 184 */     int numRows = map.getNumberOfRows();
/* 185 */     int numCols = map.getNumberOfColumns();
/* 186 */     double[][] uMatrix = new double[numRows][numCols];
/*     */     
/* 188 */     Network net = map.getNetwork();
/*     */     
/* 190 */     for (int i = 0; i < numRows; i++) {
/* 191 */       for (int j = 0; j < numCols; j++) {
/* 192 */         Neuron neuron = map.getNeuron(i, j);
/* 193 */         Collection<Neuron> neighbours = net.getNeighbours(neuron);
/* 194 */         double[] features = neuron.getFeatures();
/*     */         
/* 196 */         double d = 0.0D;
/* 197 */         int count = 0;
/* 198 */         for (Neuron n : neighbours) {
/* 199 */           count++;
/* 200 */           d += this.distance.compute(features, n.getFeatures());
/*     */         } 
/*     */         
/* 203 */         uMatrix[i][j] = d / count;
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     return uMatrix;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/twod/util/UnifiedDistanceMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */