/*     */ package org.apache.commons.math3.ml.neuralnet;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.util.Precision;
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
/*     */ public class Neuron
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20130207L;
/*     */   private final long identifier;
/*     */   private final int size;
/*     */   private final AtomicReference<double[]> features;
/*  46 */   private final AtomicLong numberOfAttemptedUpdates = new AtomicLong(0L);
/*     */   
/*  48 */   private final AtomicLong numberOfSuccessfulUpdates = new AtomicLong(0L);
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
/*     */   Neuron(long identifier, double[] features) {
/*  64 */     this.identifier = identifier;
/*  65 */     this.size = features.length;
/*  66 */     this.features = new AtomicReference(features.clone());
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
/*     */   public synchronized Neuron copy() {
/*  78 */     Neuron copy = new Neuron(getIdentifier(), getFeatures());
/*     */     
/*  80 */     copy.numberOfAttemptedUpdates.set(this.numberOfAttemptedUpdates.get());
/*  81 */     copy.numberOfSuccessfulUpdates.set(this.numberOfSuccessfulUpdates.get());
/*     */     
/*  83 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getIdentifier() {
/*  92 */     return this.identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 101 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getFeatures() {
/* 110 */     return (double[])((double[])this.features.get()).clone();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean compareAndSetFeatures(double[] expect, double[] update) {
/* 143 */     if (update.length != this.size) {
/* 144 */       throw new DimensionMismatchException(update.length, this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 149 */     double[] current = this.features.get();
/* 150 */     if (!containSameValues(current, expect))
/*     */     {
/* 152 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 156 */     this.numberOfAttemptedUpdates.incrementAndGet();
/*     */     
/* 158 */     if (this.features.compareAndSet(current, update.clone())) {
/*     */       
/* 160 */       this.numberOfSuccessfulUpdates.incrementAndGet();
/* 161 */       return true;
/*     */     } 
/*     */     
/* 164 */     return false;
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
/*     */   public long getNumberOfAttemptedUpdates() {
/* 180 */     return this.numberOfAttemptedUpdates.get();
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
/*     */   public long getNumberOfSuccessfulUpdates() {
/* 195 */     return this.numberOfSuccessfulUpdates.get();
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
/*     */   private boolean containSameValues(double[] current, double[] expect) {
/* 210 */     if (expect.length != this.size) {
/* 211 */       throw new DimensionMismatchException(expect.length, this.size);
/*     */     }
/*     */     
/* 214 */     for (int i = 0; i < this.size; i++) {
/* 215 */       if (!Precision.equals(current[i], expect[i])) {
/* 216 */         return false;
/*     */       }
/*     */     } 
/* 219 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) {
/* 228 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 237 */     return new SerializationProxy(this.identifier, this.features.get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SerializationProxy
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 20130207L;
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] features;
/*     */ 
/*     */     
/*     */     private final long identifier;
/*     */ 
/*     */ 
/*     */     
/*     */     SerializationProxy(long identifier, double[] features) {
/* 258 */       this.identifier = identifier;
/* 259 */       this.features = features;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 268 */       return new Neuron(this.identifier, this.features);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/Neuron.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */