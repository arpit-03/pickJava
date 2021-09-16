/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import org.apache.commons.math3.ode.EquationsMapper;
/*     */ import org.apache.commons.math3.ode.sampling.AbstractStepInterpolator;
/*     */ import org.apache.commons.math3.ode.sampling.StepInterpolator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GraggBulirschStoerStepInterpolator
/*     */   extends AbstractStepInterpolator
/*     */ {
/*     */   private static final long serialVersionUID = 20110928L;
/*     */   private double[] y0Dot;
/*     */   private double[] y1;
/*     */   private double[] y1Dot;
/*     */   private double[][] yMidDots;
/*     */   private double[][] polynomials;
/*     */   private double[] errfac;
/*     */   private int currentDegree;
/*     */   
/*     */   public GraggBulirschStoerStepInterpolator() {
/* 113 */     this.y0Dot = null;
/* 114 */     this.y1 = null;
/* 115 */     this.y1Dot = null;
/* 116 */     this.yMidDots = (double[][])null;
/* 117 */     resetTables(-1);
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
/*     */   GraggBulirschStoerStepInterpolator(double[] y, double[] y0Dot, double[] y1, double[] y1Dot, double[][] yMidDots, boolean forward, EquationsMapper primaryMapper, EquationsMapper[] secondaryMappers) {
/* 142 */     super(y, forward, primaryMapper, secondaryMappers);
/* 143 */     this.y0Dot = y0Dot;
/* 144 */     this.y1 = y1;
/* 145 */     this.y1Dot = y1Dot;
/* 146 */     this.yMidDots = yMidDots;
/*     */     
/* 148 */     resetTables(yMidDots.length + 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GraggBulirschStoerStepInterpolator(GraggBulirschStoerStepInterpolator interpolator) {
/* 159 */     super(interpolator);
/*     */     
/* 161 */     int dimension = this.currentState.length;
/*     */ 
/*     */ 
/*     */     
/* 165 */     this.y0Dot = null;
/* 166 */     this.y1 = null;
/* 167 */     this.y1Dot = null;
/* 168 */     this.yMidDots = (double[][])null;
/*     */ 
/*     */     
/* 171 */     if (interpolator.polynomials == null) {
/* 172 */       this.polynomials = (double[][])null;
/* 173 */       this.currentDegree = -1;
/*     */     } else {
/* 175 */       resetTables(interpolator.currentDegree);
/* 176 */       for (int i = 0; i < this.polynomials.length; i++) {
/* 177 */         this.polynomials[i] = new double[dimension];
/* 178 */         System.arraycopy(interpolator.polynomials[i], 0, this.polynomials[i], 0, dimension);
/*     */       } 
/*     */       
/* 181 */       this.currentDegree = interpolator.currentDegree;
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
/*     */   private void resetTables(int maxDegree) {
/* 193 */     if (maxDegree < 0) {
/* 194 */       this.polynomials = (double[][])null;
/* 195 */       this.errfac = null;
/* 196 */       this.currentDegree = -1;
/*     */     } else {
/*     */       
/* 199 */       double[][] newPols = new double[maxDegree + 1][];
/* 200 */       if (this.polynomials != null) {
/* 201 */         System.arraycopy(this.polynomials, 0, newPols, 0, this.polynomials.length);
/* 202 */         for (int i = this.polynomials.length; i < newPols.length; i++) {
/* 203 */           newPols[i] = new double[this.currentState.length];
/*     */         }
/*     */       } else {
/* 206 */         for (int i = 0; i < newPols.length; i++) {
/* 207 */           newPols[i] = new double[this.currentState.length];
/*     */         }
/*     */       } 
/* 210 */       this.polynomials = newPols;
/*     */ 
/*     */       
/* 213 */       if (maxDegree <= 4) {
/* 214 */         this.errfac = null;
/*     */       } else {
/* 216 */         this.errfac = new double[maxDegree - 4];
/* 217 */         for (int i = 0; i < this.errfac.length; i++) {
/* 218 */           int ip5 = i + 5;
/* 219 */           this.errfac[i] = 1.0D / (ip5 * ip5);
/* 220 */           double e = 0.5D * FastMath.sqrt((i + 1) / ip5);
/* 221 */           for (int j = 0; j <= i; j++) {
/* 222 */             this.errfac[i] = this.errfac[i] * e / (j + 1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 227 */       this.currentDegree = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StepInterpolator doCopy() {
/* 236 */     return (StepInterpolator)new GraggBulirschStoerStepInterpolator(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void computeCoefficients(int mu, double h) {
/* 246 */     if (this.polynomials == null || this.polynomials.length <= mu + 4) {
/* 247 */       resetTables(mu + 4);
/*     */     }
/*     */     
/* 250 */     this.currentDegree = mu + 4;
/*     */     
/* 252 */     for (int i = 0; i < this.currentState.length; i++) {
/*     */       
/* 254 */       double yp0 = h * this.y0Dot[i];
/* 255 */       double yp1 = h * this.y1Dot[i];
/* 256 */       double ydiff = this.y1[i] - this.currentState[i];
/* 257 */       double aspl = ydiff - yp1;
/* 258 */       double bspl = yp0 - ydiff;
/*     */       
/* 260 */       this.polynomials[0][i] = this.currentState[i];
/* 261 */       this.polynomials[1][i] = ydiff;
/* 262 */       this.polynomials[2][i] = aspl;
/* 263 */       this.polynomials[3][i] = bspl;
/*     */       
/* 265 */       if (mu < 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 270 */       double ph0 = 0.5D * (this.currentState[i] + this.y1[i]) + 0.125D * (aspl + bspl);
/* 271 */       this.polynomials[4][i] = 16.0D * (this.yMidDots[0][i] - ph0);
/*     */       
/* 273 */       if (mu > 0) {
/* 274 */         double ph1 = ydiff + 0.25D * (aspl - bspl);
/* 275 */         this.polynomials[5][i] = 16.0D * (this.yMidDots[1][i] - ph1);
/*     */         
/* 277 */         if (mu > 1) {
/* 278 */           double ph2 = yp1 - yp0;
/* 279 */           this.polynomials[6][i] = 16.0D * (this.yMidDots[2][i] - ph2 + this.polynomials[4][i]);
/*     */           
/* 281 */           if (mu > 2) {
/* 282 */             double ph3 = 6.0D * (bspl - aspl);
/* 283 */             this.polynomials[7][i] = 16.0D * (this.yMidDots[3][i] - ph3 + 3.0D * this.polynomials[5][i]);
/*     */             
/* 285 */             for (int j = 4; j <= mu; j++) {
/* 286 */               double fac1 = 0.5D * j * (j - 1);
/* 287 */               double fac2 = 2.0D * fac1 * (j - 2) * (j - 3);
/* 288 */               this.polynomials[j + 4][i] = 16.0D * (this.yMidDots[j][i] + fac1 * this.polynomials[j + 2][i] - fac2 * this.polynomials[j][i]);
/*     */             } 
/*     */           } 
/*     */         } 
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
/*     */   public double estimateError(double[] scale) {
/* 304 */     double error = 0.0D;
/* 305 */     if (this.currentDegree >= 5) {
/* 306 */       for (int i = 0; i < scale.length; i++) {
/* 307 */         double e = this.polynomials[this.currentDegree][i] / scale[i];
/* 308 */         error += e * e;
/*     */       } 
/* 310 */       error = FastMath.sqrt(error / scale.length) * this.errfac[this.currentDegree - 5];
/*     */     } 
/* 312 */     return error;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
/* 320 */     int dimension = this.currentState.length;
/*     */     
/* 322 */     double oneMinusTheta = 1.0D - theta;
/* 323 */     double theta05 = theta - 0.5D;
/* 324 */     double tOmT = theta * oneMinusTheta;
/* 325 */     double t4 = tOmT * tOmT;
/* 326 */     double t4Dot = 2.0D * tOmT * (1.0D - 2.0D * theta);
/* 327 */     double dot1 = 1.0D / this.h;
/* 328 */     double dot2 = theta * (2.0D - 3.0D * theta) / this.h;
/* 329 */     double dot3 = ((3.0D * theta - 4.0D) * theta + 1.0D) / this.h;
/*     */     
/* 331 */     for (int i = 0; i < dimension; i++) {
/*     */       
/* 333 */       double p0 = this.polynomials[0][i];
/* 334 */       double p1 = this.polynomials[1][i];
/* 335 */       double p2 = this.polynomials[2][i];
/* 336 */       double p3 = this.polynomials[3][i];
/* 337 */       this.interpolatedState[i] = p0 + theta * (p1 + oneMinusTheta * (p2 * theta + p3 * oneMinusTheta));
/* 338 */       this.interpolatedDerivatives[i] = dot1 * p1 + dot2 * p2 + dot3 * p3;
/*     */       
/* 340 */       if (this.currentDegree > 3) {
/* 341 */         double cDot = 0.0D;
/* 342 */         double c = this.polynomials[this.currentDegree][i];
/* 343 */         for (int j = this.currentDegree - 1; j > 3; j--) {
/* 344 */           double d = 1.0D / (j - 3);
/* 345 */           cDot = d * (theta05 * cDot + c);
/* 346 */           c = this.polynomials[j][i] + c * d * theta05;
/*     */         } 
/* 348 */         this.interpolatedState[i] = this.interpolatedState[i] + t4 * c;
/* 349 */         this.interpolatedDerivatives[i] = this.interpolatedDerivatives[i] + (t4 * cDot + t4Dot * c) / this.h;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 354 */     if (this.h == 0.0D)
/*     */     {
/*     */       
/* 357 */       System.arraycopy(this.yMidDots[1], 0, this.interpolatedDerivatives, 0, dimension);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 367 */     int dimension = (this.currentState == null) ? -1 : this.currentState.length;
/*     */ 
/*     */     
/* 370 */     writeBaseExternal(out);
/*     */ 
/*     */     
/* 373 */     out.writeInt(this.currentDegree);
/* 374 */     for (int k = 0; k <= this.currentDegree; k++) {
/* 375 */       for (int l = 0; l < dimension; l++) {
/* 376 */         out.writeDouble(this.polynomials[k][l]);
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
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 388 */     double t = readBaseExternal(in);
/* 389 */     int dimension = (this.currentState == null) ? -1 : this.currentState.length;
/*     */ 
/*     */     
/* 392 */     int degree = in.readInt();
/* 393 */     resetTables(degree);
/* 394 */     this.currentDegree = degree;
/*     */     
/* 396 */     for (int k = 0; k <= this.currentDegree; k++) {
/* 397 */       for (int l = 0; l < dimension; l++) {
/* 398 */         this.polynomials[k][l] = in.readDouble();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 403 */     setInterpolatedTime(t);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/GraggBulirschStoerStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */