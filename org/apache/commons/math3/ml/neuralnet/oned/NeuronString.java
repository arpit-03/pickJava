/*     */ package org.apache.commons.math3.ml.neuralnet.oned;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.ml.neuralnet.FeatureInitializer;
/*     */ import org.apache.commons.math3.ml.neuralnet.Network;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NeuronString
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Network network;
/*     */   private final int size;
/*     */   private final boolean wrap;
/*     */   private final long[] identifiers;
/*     */   
/*     */   NeuronString(boolean wrap, double[][] featuresList) {
/*  60 */     this.size = featuresList.length;
/*     */     
/*  62 */     if (this.size < 2) {
/*  63 */       throw new NumberIsTooSmallException(Integer.valueOf(this.size), Integer.valueOf(2), true);
/*     */     }
/*     */     
/*  66 */     this.wrap = wrap;
/*     */     
/*  68 */     int fLen = (featuresList[0]).length;
/*  69 */     this.network = new Network(0L, fLen);
/*  70 */     this.identifiers = new long[this.size];
/*     */ 
/*     */     
/*  73 */     for (int i = 0; i < this.size; i++) {
/*  74 */       this.identifiers[i] = this.network.createNeuron(featuresList[i]);
/*     */     }
/*     */ 
/*     */     
/*  78 */     createLinks();
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
/*     */   public NeuronString(int num, boolean wrap, FeatureInitializer[] featureInit) {
/* 103 */     if (num < 2) {
/* 104 */       throw new NumberIsTooSmallException(Integer.valueOf(num), Integer.valueOf(2), true);
/*     */     }
/*     */     
/* 107 */     this.size = num;
/* 108 */     this.wrap = wrap;
/* 109 */     this.identifiers = new long[num];
/*     */     
/* 111 */     int fLen = featureInit.length;
/* 112 */     this.network = new Network(0L, fLen);
/*     */ 
/*     */     
/* 115 */     for (int i = 0; i < num; i++) {
/* 116 */       double[] features = new double[fLen];
/* 117 */       for (int fIndex = 0; fIndex < fLen; fIndex++) {
/* 118 */         features[fIndex] = featureInit[fIndex].value();
/*     */       }
/* 120 */       this.identifiers[i] = this.network.createNeuron(features);
/*     */     } 
/*     */ 
/*     */     
/* 124 */     createLinks();
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
/*     */   public Network getNetwork() {
/* 137 */     return this.network;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 146 */     return this.size;
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
/*     */   public double[] getFeatures(int i) {
/* 158 */     if (i < 0 || i >= this.size)
/*     */     {
/* 160 */       throw new OutOfRangeException(Integer.valueOf(i), Integer.valueOf(0), Integer.valueOf(this.size - 1));
/*     */     }
/*     */     
/* 163 */     return this.network.getNeuron(this.identifiers[i]).getFeatures();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createLinks() {
/*     */     int i;
/* 170 */     for (i = 0; i < this.size - 1; i++) {
/* 171 */       this.network.addLink(this.network.getNeuron(i), this.network.getNeuron((i + 1)));
/*     */     }
/* 173 */     for (i = this.size - 1; i > 0; i--) {
/* 174 */       this.network.addLink(this.network.getNeuron(i), this.network.getNeuron((i - 1)));
/*     */     }
/* 176 */     if (this.wrap) {
/* 177 */       this.network.addLink(this.network.getNeuron(0L), this.network.getNeuron((this.size - 1)));
/* 178 */       this.network.addLink(this.network.getNeuron((this.size - 1)), this.network.getNeuron(0L));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) {
/* 188 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 197 */     double[][] featuresList = new double[this.size][];
/* 198 */     for (int i = 0; i < this.size; i++) {
/* 199 */       featuresList[i] = getFeatures(i);
/*     */     }
/*     */     
/* 202 */     return new SerializationProxy(this.wrap, featuresList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SerializationProxy
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 20130226L;
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean wrap;
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[][] featuresList;
/*     */ 
/*     */ 
/*     */     
/*     */     SerializationProxy(boolean wrap, double[][] featuresList) {
/* 224 */       this.wrap = wrap;
/* 225 */       this.featuresList = featuresList;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 234 */       return new NeuronString(this.wrap, this.featuresList);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/oned/NeuronString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */