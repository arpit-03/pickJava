/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.ode.AbstractIntegrator;
/*     */ import org.apache.commons.math3.ode.ExpandableStatefulODE;
/*     */ import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RungeKuttaIntegrator
/*     */   extends AbstractIntegrator
/*     */ {
/*     */   private final double[] c;
/*     */   private final double[][] a;
/*     */   private final double[] b;
/*     */   private final RungeKuttaStepInterpolator prototype;
/*     */   private final double step;
/*     */   
/*     */   protected RungeKuttaIntegrator(String name, double[] c, double[][] a, double[] b, RungeKuttaStepInterpolator prototype, double step) {
/*  85 */     super(name);
/*  86 */     this.c = c;
/*  87 */     this.a = a;
/*  88 */     this.b = b;
/*  89 */     this.prototype = prototype;
/*  90 */     this.step = FastMath.abs(step);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void integrate(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
/*  99 */     sanityChecks(equations, t);
/* 100 */     setEquations(equations);
/* 101 */     boolean forward = (t > equations.getTime());
/*     */ 
/*     */     
/* 104 */     double[] y0 = equations.getCompleteState();
/* 105 */     double[] y = (double[])y0.clone();
/* 106 */     int stages = this.c.length + 1;
/* 107 */     double[][] yDotK = new double[stages][];
/* 108 */     for (int i = 0; i < stages; i++) {
/* 109 */       yDotK[i] = new double[y0.length];
/*     */     }
/* 111 */     double[] yTmp = (double[])y0.clone();
/* 112 */     double[] yDotTmp = new double[y0.length];
/*     */ 
/*     */     
/* 115 */     RungeKuttaStepInterpolator interpolator = (RungeKuttaStepInterpolator)this.prototype.copy();
/* 116 */     interpolator.reinitialize(this, yTmp, yDotK, forward, equations.getPrimaryMapper(), equations.getSecondaryMappers());
/*     */     
/* 118 */     interpolator.storeTime(equations.getTime());
/*     */ 
/*     */     
/* 121 */     this.stepStart = equations.getTime();
/* 122 */     if (forward) {
/* 123 */       if (this.stepStart + this.step >= t) {
/* 124 */         this.stepSize = t - this.stepStart;
/*     */       } else {
/* 126 */         this.stepSize = this.step;
/*     */       }
/*     */     
/* 129 */     } else if (this.stepStart - this.step <= t) {
/* 130 */       this.stepSize = t - this.stepStart;
/*     */     } else {
/* 132 */       this.stepSize = -this.step;
/*     */     } 
/*     */     
/* 135 */     initIntegration(equations.getTime(), y0, t);
/*     */ 
/*     */     
/* 138 */     this.isLastStep = false;
/*     */     
/*     */     do {
/* 141 */       interpolator.shift();
/*     */ 
/*     */       
/* 144 */       computeDerivatives(this.stepStart, y, yDotK[0]);
/*     */ 
/*     */       
/* 147 */       for (int k = 1; k < stages; k++) {
/*     */         
/* 149 */         for (int m = 0; m < y0.length; m++) {
/* 150 */           double sum = this.a[k - 1][0] * yDotK[0][m];
/* 151 */           for (int l = 1; l < k; l++) {
/* 152 */             sum += this.a[k - 1][l] * yDotK[l][m];
/*     */           }
/* 154 */           yTmp[m] = y[m] + this.stepSize * sum;
/*     */         } 
/*     */         
/* 157 */         computeDerivatives(this.stepStart + this.c[k - 1] * this.stepSize, yTmp, yDotK[k]);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 162 */       for (int j = 0; j < y0.length; j++) {
/* 163 */         double sum = this.b[0] * yDotK[0][j];
/* 164 */         for (int l = 1; l < stages; l++) {
/* 165 */           sum += this.b[l] * yDotK[l][j];
/*     */         }
/* 167 */         yTmp[j] = y[j] + this.stepSize * sum;
/*     */       } 
/*     */ 
/*     */       
/* 171 */       interpolator.storeTime(this.stepStart + this.stepSize);
/* 172 */       System.arraycopy(yTmp, 0, y, 0, y0.length);
/* 173 */       System.arraycopy(yDotK[stages - 1], 0, yDotTmp, 0, y0.length);
/* 174 */       this.stepStart = acceptStep(interpolator, y, yDotTmp, t);
/*     */       
/* 176 */       if (this.isLastStep) {
/*     */         continue;
/*     */       }
/* 179 */       interpolator.storeTime(this.stepStart);
/*     */ 
/*     */       
/* 182 */       double nextT = this.stepStart + this.stepSize;
/* 183 */       boolean nextIsLast = forward ? ((nextT >= t)) : ((nextT <= t));
/* 184 */       if (!nextIsLast)
/* 185 */         continue;  this.stepSize = t - this.stepStart;
/*     */ 
/*     */     
/*     */     }
/* 189 */     while (!this.isLastStep);
/*     */ 
/*     */     
/* 192 */     equations.setTime(this.stepStart);
/* 193 */     equations.setCompleteState(y);
/*     */     
/* 195 */     this.stepStart = Double.NaN;
/* 196 */     this.stepSize = Double.NaN;
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
/*     */   public double[] singleStep(FirstOrderDifferentialEquations equations, double t0, double[] y0, double t) {
/* 229 */     double[] y = (double[])y0.clone();
/* 230 */     int stages = this.c.length + 1;
/* 231 */     double[][] yDotK = new double[stages][];
/* 232 */     for (int i = 0; i < stages; i++) {
/* 233 */       yDotK[i] = new double[y0.length];
/*     */     }
/* 235 */     double[] yTmp = (double[])y0.clone();
/*     */ 
/*     */     
/* 238 */     double h = t - t0;
/* 239 */     equations.computeDerivatives(t0, y, yDotK[0]);
/*     */ 
/*     */     
/* 242 */     for (int k = 1; k < stages; k++) {
/*     */       
/* 244 */       for (int m = 0; m < y0.length; m++) {
/* 245 */         double sum = this.a[k - 1][0] * yDotK[0][m];
/* 246 */         for (int l = 1; l < k; l++) {
/* 247 */           sum += this.a[k - 1][l] * yDotK[l][m];
/*     */         }
/* 249 */         yTmp[m] = y[m] + h * sum;
/*     */       } 
/*     */       
/* 252 */       equations.computeDerivatives(t0 + this.c[k - 1] * h, yTmp, yDotK[k]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 257 */     for (int j = 0; j < y0.length; j++) {
/* 258 */       double sum = this.b[0] * yDotK[0][j];
/* 259 */       for (int l = 1; l < stages; l++) {
/* 260 */         sum += this.b[l] * yDotK[l][j];
/*     */       }
/* 262 */       y[j] = y[j] + h * sum;
/*     */     } 
/*     */     
/* 265 */     return y;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/RungeKuttaIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */