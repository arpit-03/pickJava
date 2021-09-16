/*     */ package org.apache.commons.math3.analysis.integration.gauss;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
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
/*     */ public class GaussIntegratorFactory
/*     */ {
/*  33 */   private final BaseRuleFactory<Double> legendre = new LegendreRuleFactory();
/*     */   
/*  35 */   private final BaseRuleFactory<BigDecimal> legendreHighPrecision = new LegendreHighPrecisionRuleFactory();
/*     */   
/*  37 */   private final BaseRuleFactory<Double> hermite = new HermiteRuleFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GaussIntegrator legendre(int numberOfPoints) {
/*  50 */     return new GaussIntegrator(getRule((BaseRuleFactory)this.legendre, numberOfPoints));
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
/*     */   public GaussIntegrator legendre(int numberOfPoints, double lowerBound, double upperBound) throws NotStrictlyPositiveException {
/*  69 */     return new GaussIntegrator(transform(getRule((BaseRuleFactory)this.legendre, numberOfPoints), lowerBound, upperBound));
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
/*     */   public GaussIntegrator legendreHighPrecision(int numberOfPoints) throws NotStrictlyPositiveException {
/*  86 */     return new GaussIntegrator(getRule((BaseRuleFactory)this.legendreHighPrecision, numberOfPoints));
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
/*     */   public GaussIntegrator legendreHighPrecision(int numberOfPoints, double lowerBound, double upperBound) throws NotStrictlyPositiveException {
/* 104 */     return new GaussIntegrator(transform(getRule((BaseRuleFactory)this.legendreHighPrecision, numberOfPoints), lowerBound, upperBound));
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
/*     */   public SymmetricGaussIntegrator hermite(int numberOfPoints) {
/* 123 */     return new SymmetricGaussIntegrator(getRule((BaseRuleFactory)this.hermite, numberOfPoints));
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
/*     */   private static Pair<double[], double[]> getRule(BaseRuleFactory<? extends Number> factory, int numberOfPoints) throws NotStrictlyPositiveException, DimensionMismatchException {
/* 137 */     return factory.getRule(numberOfPoints);
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
/*     */   private static Pair<double[], double[]> transform(Pair<double[], double[]> rule, double a, double b) {
/* 153 */     double[] points = (double[])rule.getFirst();
/* 154 */     double[] weights = (double[])rule.getSecond();
/*     */ 
/*     */     
/* 157 */     double scale = (b - a) / 2.0D;
/* 158 */     double shift = a + scale;
/*     */     
/* 160 */     for (int i = 0; i < points.length; i++) {
/* 161 */       points[i] = points[i] * scale + shift;
/* 162 */       weights[i] = weights[i] * scale;
/*     */     } 
/*     */     
/* 165 */     return new Pair(points, weights);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/gauss/GaussIntegratorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */