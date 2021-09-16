/*     */ package org.apache.commons.math3.analysis.polynomials;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*     */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolynomialFunction
/*     */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7726511984200295583L;
/*     */   private final double[] coefficients;
/*     */   
/*     */   public PolynomialFunction(double[] c) throws NullArgumentException, NoDataException {
/*  69 */     MathUtils.checkNotNull(c);
/*  70 */     int n = c.length;
/*  71 */     if (n == 0) {
/*  72 */       throw new NoDataException(LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
/*     */     }
/*  74 */     while (n > 1 && c[n - 1] == 0.0D) {
/*  75 */       n--;
/*     */     }
/*  77 */     this.coefficients = new double[n];
/*  78 */     System.arraycopy(c, 0, this.coefficients, 0, n);
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
/*     */   public double value(double x) {
/*  93 */     return evaluate(this.coefficients, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int degree() {
/* 102 */     return this.coefficients.length - 1;
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
/*     */   public double[] getCoefficients() {
/* 114 */     return (double[])this.coefficients.clone();
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
/*     */   protected static double evaluate(double[] coefficients, double argument) throws NullArgumentException, NoDataException {
/* 129 */     MathUtils.checkNotNull(coefficients);
/* 130 */     int n = coefficients.length;
/* 131 */     if (n == 0) {
/* 132 */       throw new NoDataException(LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
/*     */     }
/* 134 */     double result = coefficients[n - 1];
/* 135 */     for (int j = n - 2; j >= 0; j--) {
/* 136 */       result = argument * result + coefficients[j];
/*     */     }
/* 138 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivativeStructure value(DerivativeStructure t) throws NullArgumentException, NoDataException {
/* 149 */     MathUtils.checkNotNull(this.coefficients);
/* 150 */     int n = this.coefficients.length;
/* 151 */     if (n == 0) {
/* 152 */       throw new NoDataException(LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
/*     */     }
/* 154 */     DerivativeStructure result = new DerivativeStructure(t.getFreeParameters(), t.getOrder(), this.coefficients[n - 1]);
/*     */     
/* 156 */     for (int j = n - 2; j >= 0; j--) {
/* 157 */       result = result.multiply(t).add(this.coefficients[j]);
/*     */     }
/* 159 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialFunction add(PolynomialFunction p) {
/* 170 */     int lowLength = FastMath.min(this.coefficients.length, p.coefficients.length);
/* 171 */     int highLength = FastMath.max(this.coefficients.length, p.coefficients.length);
/*     */ 
/*     */     
/* 174 */     double[] newCoefficients = new double[highLength];
/* 175 */     for (int i = 0; i < lowLength; i++) {
/* 176 */       newCoefficients[i] = this.coefficients[i] + p.coefficients[i];
/*     */     }
/* 178 */     System.arraycopy((this.coefficients.length < p.coefficients.length) ? p.coefficients : this.coefficients, lowLength, newCoefficients, lowLength, highLength - lowLength);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     return new PolynomialFunction(newCoefficients);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialFunction subtract(PolynomialFunction p) {
/* 195 */     int lowLength = FastMath.min(this.coefficients.length, p.coefficients.length);
/* 196 */     int highLength = FastMath.max(this.coefficients.length, p.coefficients.length);
/*     */ 
/*     */     
/* 199 */     double[] newCoefficients = new double[highLength]; int i;
/* 200 */     for (i = 0; i < lowLength; i++) {
/* 201 */       newCoefficients[i] = this.coefficients[i] - p.coefficients[i];
/*     */     }
/* 203 */     if (this.coefficients.length < p.coefficients.length) {
/* 204 */       for (i = lowLength; i < highLength; i++) {
/* 205 */         newCoefficients[i] = -p.coefficients[i];
/*     */       }
/*     */     } else {
/* 208 */       System.arraycopy(this.coefficients, lowLength, newCoefficients, lowLength, highLength - lowLength);
/*     */     } 
/*     */ 
/*     */     
/* 212 */     return new PolynomialFunction(newCoefficients);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialFunction negate() {
/* 221 */     double[] newCoefficients = new double[this.coefficients.length];
/* 222 */     for (int i = 0; i < this.coefficients.length; i++) {
/* 223 */       newCoefficients[i] = -this.coefficients[i];
/*     */     }
/* 225 */     return new PolynomialFunction(newCoefficients);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialFunction multiply(PolynomialFunction p) {
/* 235 */     double[] newCoefficients = new double[this.coefficients.length + p.coefficients.length - 1];
/*     */     
/* 237 */     for (int i = 0; i < newCoefficients.length; i++) {
/* 238 */       newCoefficients[i] = 0.0D;
/* 239 */       int j = FastMath.max(0, i + 1 - p.coefficients.length);
/* 240 */       for (; j < FastMath.min(this.coefficients.length, i + 1); 
/* 241 */         j++) {
/* 242 */         newCoefficients[i] = newCoefficients[i] + this.coefficients[j] * p.coefficients[i - j];
/*     */       }
/*     */     } 
/*     */     
/* 246 */     return new PolynomialFunction(newCoefficients);
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
/*     */   protected static double[] differentiate(double[] coefficients) throws NullArgumentException, NoDataException {
/* 259 */     MathUtils.checkNotNull(coefficients);
/* 260 */     int n = coefficients.length;
/* 261 */     if (n == 0) {
/* 262 */       throw new NoDataException(LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
/*     */     }
/* 264 */     if (n == 1) {
/* 265 */       return new double[] { 0.0D };
/*     */     }
/* 267 */     double[] result = new double[n - 1];
/* 268 */     for (int i = n - 1; i > 0; i--) {
/* 269 */       result[i - 1] = i * coefficients[i];
/*     */     }
/* 271 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialFunction polynomialDerivative() {
/* 280 */     return new PolynomialFunction(differentiate(this.coefficients));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnivariateFunction derivative() {
/* 289 */     return (UnivariateFunction)polynomialDerivative();
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
/*     */   public String toString() {
/* 309 */     StringBuilder s = new StringBuilder();
/* 310 */     if (this.coefficients[0] == 0.0D) {
/* 311 */       if (this.coefficients.length == 1) {
/* 312 */         return "0";
/*     */       }
/*     */     } else {
/* 315 */       s.append(toString(this.coefficients[0]));
/*     */     } 
/*     */     
/* 318 */     for (int i = 1; i < this.coefficients.length; i++) {
/* 319 */       if (this.coefficients[i] != 0.0D) {
/* 320 */         if (s.length() > 0) {
/* 321 */           if (this.coefficients[i] < 0.0D) {
/* 322 */             s.append(" - ");
/*     */           } else {
/* 324 */             s.append(" + ");
/*     */           }
/*     */         
/* 327 */         } else if (this.coefficients[i] < 0.0D) {
/* 328 */           s.append("-");
/*     */         } 
/*     */ 
/*     */         
/* 332 */         double absAi = FastMath.abs(this.coefficients[i]);
/* 333 */         if (absAi - 1.0D != 0.0D) {
/* 334 */           s.append(toString(absAi));
/* 335 */           s.append(' ');
/*     */         } 
/*     */         
/* 338 */         s.append("x");
/* 339 */         if (i > 1) {
/* 340 */           s.append('^');
/* 341 */           s.append(Integer.toString(i));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 346 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toString(double coeff) {
/* 356 */     String c = Double.toString(coeff);
/* 357 */     if (c.endsWith(".0")) {
/* 358 */       return c.substring(0, c.length() - 2);
/*     */     }
/* 360 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 367 */     int prime = 31;
/* 368 */     int result = 1;
/* 369 */     result = 31 * result + Arrays.hashCode(this.coefficients);
/* 370 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 376 */     if (this == obj) {
/* 377 */       return true;
/*     */     }
/* 379 */     if (!(obj instanceof PolynomialFunction)) {
/* 380 */       return false;
/*     */     }
/* 382 */     PolynomialFunction other = (PolynomialFunction)obj;
/* 383 */     if (!Arrays.equals(this.coefficients, other.coefficients)) {
/* 384 */       return false;
/*     */     }
/* 386 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Parametric
/*     */     implements ParametricUnivariateFunction
/*     */   {
/*     */     public double[] gradient(double x, double... parameters) {
/* 397 */       double[] gradient = new double[parameters.length];
/* 398 */       double xn = 1.0D;
/* 399 */       for (int i = 0; i < parameters.length; i++) {
/* 400 */         gradient[i] = xn;
/* 401 */         xn *= x;
/*     */       } 
/* 403 */       return gradient;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double value(double x, double... parameters) throws NoDataException {
/* 409 */       return PolynomialFunction.evaluate(parameters, x);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/polynomials/PolynomialFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */