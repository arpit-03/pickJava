/*     */ package org.apache.commons.math3.ml.neuralnet.sofm;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.commons.math3.analysis.function.Gaussian;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
/*     */ import org.apache.commons.math3.ml.neuralnet.MapUtils;
/*     */ import org.apache.commons.math3.ml.neuralnet.Network;
/*     */ import org.apache.commons.math3.ml.neuralnet.Neuron;
/*     */ import org.apache.commons.math3.ml.neuralnet.UpdateAction;
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
/*     */ public class KohonenUpdateAction
/*     */   implements UpdateAction
/*     */ {
/*     */   private final DistanceMeasure distance;
/*     */   private final LearningFactorFunction learningFactor;
/*     */   private final NeighbourhoodSizeFunction neighbourhoodSize;
/*  77 */   private final AtomicLong numberOfCalls = new AtomicLong(0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KohonenUpdateAction(DistanceMeasure distance, LearningFactorFunction learningFactor, NeighbourhoodSizeFunction neighbourhoodSize) {
/*  87 */     this.distance = distance;
/*  88 */     this.learningFactor = learningFactor;
/*  89 */     this.neighbourhoodSize = neighbourhoodSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Network net, double[] features) {
/*  97 */     long numCalls = this.numberOfCalls.incrementAndGet() - 1L;
/*  98 */     double currentLearning = this.learningFactor.value(numCalls);
/*  99 */     Neuron best = findAndUpdateBestNeuron(net, features, currentLearning);
/*     */ 
/*     */ 
/*     */     
/* 103 */     int currentNeighbourhood = this.neighbourhoodSize.value(numCalls);
/*     */ 
/*     */     
/* 106 */     Gaussian neighbourhoodDecay = new Gaussian(currentLearning, 0.0D, currentNeighbourhood);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     if (currentNeighbourhood > 0) {
/*     */       
/* 113 */       Collection<Neuron> neighbours = new HashSet<Neuron>();
/* 114 */       neighbours.add(best);
/*     */       
/* 116 */       HashSet<Neuron> exclude = new HashSet<Neuron>();
/* 117 */       exclude.add(best);
/*     */       
/* 119 */       int radius = 1;
/*     */       
/*     */       do {
/* 122 */         neighbours = net.getNeighbours(neighbours, exclude);
/*     */ 
/*     */         
/* 125 */         for (Neuron n : neighbours) {
/* 126 */           updateNeighbouringNeuron(n, features, neighbourhoodDecay.value(radius));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 131 */         exclude.addAll(neighbours);
/* 132 */         ++radius;
/* 133 */       } while (radius <= currentNeighbourhood);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getNumberOfCalls() {
/* 144 */     return this.numberOfCalls.get();
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
/*     */   private boolean attemptNeuronUpdate(Neuron n, double[] features, double learningRate) {
/* 159 */     double[] expect = n.getFeatures();
/* 160 */     double[] update = computeFeatures(expect, features, learningRate);
/*     */ 
/*     */ 
/*     */     
/* 164 */     return n.compareAndSetFeatures(expect, update);
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
/*     */   private void updateNeighbouringNeuron(Neuron n, double[] features, double learningRate) {
/*     */     do {
/*     */     
/* 178 */     } while (!attemptNeuronUpdate(n, features, learningRate));
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
/*     */   private Neuron findAndUpdateBestNeuron(Network net, double[] features, double learningRate) {
/*     */     while (true) {
/* 197 */       Neuron best = MapUtils.findBest(features, (Iterable)net, this.distance);
/*     */       
/* 199 */       if (attemptNeuronUpdate(best, features, learningRate)) {
/* 200 */         return best;
/*     */       }
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
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] computeFeatures(double[] current, double[] sample, double learningRate) {
/* 220 */     ArrayRealVector c = new ArrayRealVector(current, false);
/* 221 */     ArrayRealVector s = new ArrayRealVector(sample, false);
/*     */     
/* 223 */     return s.subtract((RealVector)c).mapMultiplyToSelf(learningRate).add((RealVector)c).toArray();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/sofm/KohonenUpdateAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */