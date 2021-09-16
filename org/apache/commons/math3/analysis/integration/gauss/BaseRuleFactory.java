/*     */ package org.apache.commons.math3.analysis.integration.gauss;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ public abstract class BaseRuleFactory<T extends Number>
/*     */ {
/*  38 */   private final Map<Integer, Pair<T[], T[]>> pointsAndWeights = new TreeMap<Integer, Pair<T[], T[]>>();
/*     */ 
/*     */   
/*  41 */   private final Map<Integer, Pair<double[], double[]>> pointsAndWeightsDouble = new TreeMap<Integer, Pair<double[], double[]>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pair<double[], double[]> getRule(int numberOfPoints) throws NotStrictlyPositiveException, DimensionMismatchException {
/*  57 */     if (numberOfPoints <= 0) {
/*  58 */       throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(numberOfPoints));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  63 */     Pair<double[], double[]> cached = this.pointsAndWeightsDouble.get(Integer.valueOf(numberOfPoints));
/*     */     
/*  65 */     if (cached == null) {
/*     */ 
/*     */ 
/*     */       
/*  69 */       Pair<T[], T[]> rule = getRuleInternal(numberOfPoints);
/*  70 */       cached = convertToDouble(rule);
/*     */ 
/*     */       
/*  73 */       this.pointsAndWeightsDouble.put(Integer.valueOf(numberOfPoints), cached);
/*     */     } 
/*     */ 
/*     */     
/*  77 */     return new Pair(((double[])cached.getFirst()).clone(), ((double[])cached.getSecond()).clone());
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
/*     */   protected synchronized Pair<T[], T[]> getRuleInternal(int numberOfPoints) throws DimensionMismatchException {
/*  94 */     Pair<T[], T[]> rule = this.pointsAndWeights.get(Integer.valueOf(numberOfPoints));
/*  95 */     if (rule == null) {
/*  96 */       addRule(computeRule(numberOfPoints));
/*     */       
/*  98 */       return getRuleInternal(numberOfPoints);
/*     */     } 
/* 100 */     return rule;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRule(Pair<T[], T[]> rule) throws DimensionMismatchException {
/* 111 */     if (((Number[])rule.getFirst()).length != ((Number[])rule.getSecond()).length) {
/* 112 */       throw new DimensionMismatchException(((Number[])rule.getFirst()).length, ((Number[])rule.getSecond()).length);
/*     */     }
/*     */ 
/*     */     
/* 116 */     this.pointsAndWeights.put(Integer.valueOf(((Number[])rule.getFirst()).length), rule);
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
/*     */   protected abstract Pair<T[], T[]> computeRule(int paramInt) throws DimensionMismatchException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T extends Number> Pair<double[], double[]> convertToDouble(Pair<T[], T[]> rule) {
/* 139 */     Number[] arrayOfNumber1 = (Number[])rule.getFirst();
/* 140 */     Number[] arrayOfNumber2 = (Number[])rule.getSecond();
/*     */     
/* 142 */     int len = arrayOfNumber1.length;
/* 143 */     double[] pD = new double[len];
/* 144 */     double[] wD = new double[len];
/*     */     
/* 146 */     for (int i = 0; i < len; i++) {
/* 147 */       pD[i] = arrayOfNumber1[i].doubleValue();
/* 148 */       wD[i] = arrayOfNumber2[i].doubleValue();
/*     */     } 
/*     */     
/* 151 */     return new Pair(pD, wD);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/gauss/BaseRuleFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */