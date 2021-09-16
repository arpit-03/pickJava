/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*     */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableVectorFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.CombinatoricsUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HermiteInterpolator
/*     */   implements UnivariateDifferentiableVectorFunction
/*     */ {
/*  62 */   private final List<Double> abscissae = new ArrayList<Double>();
/*  63 */   private final List<double[]> topDiagonal = (List)new ArrayList<double>();
/*  64 */   private final List<double[]> bottomDiagonal = (List)new ArrayList<double>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSamplePoint(double x, double[]... value) throws ZeroException, MathArithmeticException {
/*  89 */     for (int i = 0; i < value.length; i++) {
/*     */       
/*  91 */       double[] y = (double[])value[i].clone();
/*  92 */       if (i > 1) {
/*  93 */         double inv = 1.0D / CombinatoricsUtils.factorial(i);
/*  94 */         for (int k = 0; k < y.length; k++) {
/*  95 */           y[k] = y[k] * inv;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 100 */       int n = this.abscissae.size();
/* 101 */       this.bottomDiagonal.add(n - i, y);
/* 102 */       double[] bottom0 = y;
/* 103 */       for (int j = i; j < n; j++) {
/* 104 */         double[] bottom1 = this.bottomDiagonal.get(n - j + 1);
/* 105 */         double inv = 1.0D / (x - ((Double)this.abscissae.get(n - j + 1)).doubleValue());
/* 106 */         if (Double.isInfinite(inv)) {
/* 107 */           throw new ZeroException(LocalizedFormats.DUPLICATED_ABSCISSA_DIVISION_BY_ZERO, new Object[] { Double.valueOf(x) });
/*     */         }
/* 109 */         for (int k = 0; k < y.length; k++) {
/* 110 */           bottom1[k] = inv * (bottom0[k] - bottom1[k]);
/*     */         }
/* 112 */         bottom0 = bottom1;
/*     */       } 
/*     */ 
/*     */       
/* 116 */       this.topDiagonal.add(bottom0.clone());
/*     */ 
/*     */       
/* 119 */       this.abscissae.add(Double.valueOf(x));
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
/*     */   public PolynomialFunction[] getPolynomials() throws NoDataException {
/* 133 */     checkInterpolation();
/*     */ 
/*     */     
/* 136 */     PolynomialFunction zero = polynomial(new double[] { 0.0D });
/* 137 */     PolynomialFunction[] polynomials = new PolynomialFunction[((double[])this.topDiagonal.get(0)).length];
/* 138 */     for (int i = 0; i < polynomials.length; i++) {
/* 139 */       polynomials[i] = zero;
/*     */     }
/* 141 */     PolynomialFunction coeff = polynomial(new double[] { 1.0D });
/*     */ 
/*     */     
/* 144 */     for (int j = 0; j < this.topDiagonal.size(); j++) {
/* 145 */       double[] tdi = this.topDiagonal.get(j);
/* 146 */       for (int k = 0; k < polynomials.length; k++) {
/* 147 */         polynomials[k] = polynomials[k].add(coeff.multiply(polynomial(new double[] { tdi[k] })));
/*     */       } 
/* 149 */       coeff = coeff.multiply(polynomial(new double[] { -((Double)this.abscissae.get(j)).doubleValue(), 1.0D }));
/*     */     } 
/*     */     
/* 152 */     return polynomials;
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
/*     */   public double[] value(double x) throws NoDataException {
/* 171 */     checkInterpolation();
/*     */     
/* 173 */     double[] value = new double[((double[])this.topDiagonal.get(0)).length];
/* 174 */     double valueCoeff = 1.0D;
/* 175 */     for (int i = 0; i < this.topDiagonal.size(); i++) {
/* 176 */       double[] dividedDifference = this.topDiagonal.get(i);
/* 177 */       for (int k = 0; k < value.length; k++) {
/* 178 */         value[k] = value[k] + dividedDifference[k] * valueCoeff;
/*     */       }
/* 180 */       double deltaX = x - ((Double)this.abscissae.get(i)).doubleValue();
/* 181 */       valueCoeff *= deltaX;
/*     */     } 
/*     */     
/* 184 */     return value;
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
/*     */   public DerivativeStructure[] value(DerivativeStructure x) throws NoDataException {
/* 203 */     checkInterpolation();
/*     */     
/* 205 */     DerivativeStructure[] value = new DerivativeStructure[((double[])this.topDiagonal.get(0)).length];
/* 206 */     Arrays.fill((Object[])value, x.getField().getZero());
/* 207 */     DerivativeStructure valueCoeff = (DerivativeStructure)x.getField().getOne();
/* 208 */     for (int i = 0; i < this.topDiagonal.size(); i++) {
/* 209 */       double[] dividedDifference = this.topDiagonal.get(i);
/* 210 */       for (int k = 0; k < value.length; k++) {
/* 211 */         value[k] = value[k].add(valueCoeff.multiply(dividedDifference[k]));
/*     */       }
/* 213 */       DerivativeStructure deltaX = x.subtract(((Double)this.abscissae.get(i)).doubleValue());
/* 214 */       valueCoeff = valueCoeff.multiply(deltaX);
/*     */     } 
/*     */     
/* 217 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkInterpolation() throws NoDataException {
/* 226 */     if (this.abscissae.isEmpty()) {
/* 227 */       throw new NoDataException(LocalizedFormats.EMPTY_INTERPOLATION_SAMPLE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PolynomialFunction polynomial(double... c) {
/* 236 */     return new PolynomialFunction(c);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/HermiteInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */