/*     */ package org.apache.commons.math3.ml.neuralnet;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
/*     */ import org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D;
/*     */ import org.apache.commons.math3.util.Pair;
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
/*     */ public class MapUtils
/*     */ {
/*     */   public static Neuron findBest(double[] features, Iterable<Neuron> neurons, DistanceMeasure distance) {
/*  59 */     Neuron best = null;
/*  60 */     double min = Double.POSITIVE_INFINITY;
/*  61 */     for (Neuron n : neurons) {
/*  62 */       double d = distance.compute(n.getFeatures(), features);
/*  63 */       if (d < min) {
/*  64 */         min = d;
/*  65 */         best = n;
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     return best;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Pair<Neuron, Neuron> findBestAndSecondBest(double[] features, Iterable<Neuron> neurons, DistanceMeasure distance) {
/*  88 */     Neuron[] best = { null, null };
/*  89 */     double[] min = { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY };
/*     */     
/*  91 */     for (Neuron n : neurons) {
/*  92 */       double d = distance.compute(n.getFeatures(), features);
/*  93 */       if (d < min[0]) {
/*     */         
/*  95 */         min[1] = min[0];
/*  96 */         best[1] = best[0];
/*     */ 
/*     */         
/*  99 */         min[0] = d;
/* 100 */         best[0] = n; continue;
/* 101 */       }  if (d < min[1]) {
/*     */         
/* 103 */         min[1] = d;
/* 104 */         best[1] = n;
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     return new Pair(best[0], best[1]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Neuron[] sort(double[] features, Iterable<Neuron> neurons, DistanceMeasure distance) {
/* 133 */     List<PairNeuronDouble> list = new ArrayList<PairNeuronDouble>();
/*     */     
/* 135 */     for (Neuron n : neurons) {
/* 136 */       double d = distance.compute(n.getFeatures(), features);
/* 137 */       list.add(new PairNeuronDouble(n, d));
/*     */     } 
/*     */     
/* 140 */     Collections.sort(list, PairNeuronDouble.COMPARATOR);
/*     */     
/* 142 */     int len = list.size();
/* 143 */     Neuron[] sorted = new Neuron[len];
/*     */     
/* 145 */     for (int i = 0; i < len; i++) {
/* 146 */       sorted[i] = ((PairNeuronDouble)list.get(i)).getNeuron();
/*     */     }
/* 148 */     return sorted;
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
/*     */   public static double[][] computeU(NeuronSquareMesh2D map, DistanceMeasure distance) {
/* 162 */     int numRows = map.getNumberOfRows();
/* 163 */     int numCols = map.getNumberOfColumns();
/* 164 */     double[][] uMatrix = new double[numRows][numCols];
/*     */     
/* 166 */     Network net = map.getNetwork();
/*     */     
/* 168 */     for (int i = 0; i < numRows; i++) {
/* 169 */       for (int j = 0; j < numCols; j++) {
/* 170 */         Neuron neuron = map.getNeuron(i, j);
/* 171 */         Collection<Neuron> neighbours = net.getNeighbours(neuron);
/* 172 */         double[] features = neuron.getFeatures();
/*     */         
/* 174 */         double d = 0.0D;
/* 175 */         int count = 0;
/* 176 */         for (Neuron n : neighbours) {
/* 177 */           count++;
/* 178 */           d += distance.compute(features, n.getFeatures());
/*     */         } 
/*     */         
/* 181 */         uMatrix[i][j] = d / count;
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     return uMatrix;
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
/*     */   public static int[][] computeHitHistogram(Iterable<double[]> data, NeuronSquareMesh2D map, DistanceMeasure distance) {
/* 199 */     HashMap<Neuron, Integer> hit = new HashMap<Neuron, Integer>();
/* 200 */     Network net = map.getNetwork();
/*     */     
/* 202 */     for (double[] f : data) {
/* 203 */       Neuron best = findBest(f, net, distance);
/* 204 */       Integer count = hit.get(best);
/* 205 */       if (count == null) {
/* 206 */         hit.put(best, Integer.valueOf(1)); continue;
/*     */       } 
/* 208 */       hit.put(best, Integer.valueOf(count.intValue() + 1));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 213 */     int numRows = map.getNumberOfRows();
/* 214 */     int numCols = map.getNumberOfColumns();
/* 215 */     int[][] histo = new int[numRows][numCols];
/*     */     
/* 217 */     for (int i = 0; i < numRows; i++) {
/* 218 */       for (int j = 0; j < numCols; j++) {
/* 219 */         Neuron neuron = map.getNeuron(i, j);
/* 220 */         Integer count = hit.get(neuron);
/* 221 */         if (count == null) {
/* 222 */           histo[i][j] = 0;
/*     */         } else {
/* 224 */           histo[i][j] = count.intValue();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 229 */     return histo;
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
/*     */   
/*     */   public static double computeQuantizationError(Iterable<double[]> data, Iterable<Neuron> neurons, DistanceMeasure distance) {
/* 246 */     double d = 0.0D;
/* 247 */     int count = 0;
/* 248 */     for (double[] f : data) {
/* 249 */       count++;
/* 250 */       d += distance.compute(f, findBest(f, neurons, distance).getFeatures());
/*     */     } 
/*     */     
/* 253 */     if (count == 0) {
/* 254 */       throw new NoDataException();
/*     */     }
/*     */     
/* 257 */     return d / count;
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
/*     */   
/*     */   public static double computeTopographicError(Iterable<double[]> data, Network net, DistanceMeasure distance) {
/* 274 */     int notAdjacentCount = 0;
/* 275 */     int count = 0;
/* 276 */     for (double[] f : data) {
/* 277 */       count++;
/* 278 */       Pair<Neuron, Neuron> p = findBestAndSecondBest(f, net, distance);
/* 279 */       if (!net.getNeighbours((Neuron)p.getFirst()).contains(p.getSecond()))
/*     */       {
/*     */         
/* 282 */         notAdjacentCount++;
/*     */       }
/*     */     } 
/*     */     
/* 286 */     if (count == 0) {
/* 287 */       throw new NoDataException();
/*     */     }
/*     */     
/* 290 */     return notAdjacentCount / count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PairNeuronDouble
/*     */   {
/* 298 */     static final Comparator<PairNeuronDouble> COMPARATOR = new Comparator<PairNeuronDouble>()
/*     */       {
/*     */         
/*     */         public int compare(MapUtils.PairNeuronDouble o1, MapUtils.PairNeuronDouble o2)
/*     */         {
/* 303 */           return Double.compare(o1.value, o2.value);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/*     */     private final Neuron neuron;
/*     */ 
/*     */     
/*     */     private final double value;
/*     */ 
/*     */     
/*     */     PairNeuronDouble(Neuron neuron, double value) {
/* 316 */       this.neuron = neuron;
/* 317 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public Neuron getNeuron() {
/* 322 */       return this.neuron;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/MapUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */