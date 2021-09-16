/*     */ package org.apache.commons.math3.ode;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.util.Localizable;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JacobianMatrices
/*     */ {
/*     */   private ExpandableStatefulODE efode;
/*     */   private int index;
/*     */   private MainStateJacobianProvider jode;
/*     */   private ParameterizedODE pode;
/*     */   private int stateDim;
/*     */   private ParameterConfiguration[] selectedParameters;
/*     */   private List<ParameterJacobianProvider> jacobianProviders;
/*     */   private int paramDim;
/*     */   private boolean dirtyParameter;
/*     */   private double[] matricesData;
/*     */   
/*     */   public JacobianMatrices(FirstOrderDifferentialEquations fode, double[] hY, String... parameters) throws DimensionMismatchException {
/* 106 */     this(new MainStateJacobianWrapper(fode, hY), parameters);
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
/*     */   public JacobianMatrices(MainStateJacobianProvider jode, String... parameters) {
/* 124 */     this.efode = null;
/* 125 */     this.index = -1;
/*     */     
/* 127 */     this.jode = jode;
/* 128 */     this.pode = null;
/*     */     
/* 130 */     this.stateDim = jode.getDimension();
/*     */     
/* 132 */     if (parameters == null) {
/* 133 */       this.selectedParameters = null;
/* 134 */       this.paramDim = 0;
/*     */     } else {
/* 136 */       this.selectedParameters = new ParameterConfiguration[parameters.length];
/* 137 */       for (int j = 0; j < parameters.length; j++) {
/* 138 */         this.selectedParameters[j] = new ParameterConfiguration(parameters[j], Double.NaN);
/*     */       }
/* 140 */       this.paramDim = parameters.length;
/*     */     } 
/* 142 */     this.dirtyParameter = false;
/*     */     
/* 144 */     this.jacobianProviders = new ArrayList<ParameterJacobianProvider>();
/*     */ 
/*     */ 
/*     */     
/* 148 */     this.matricesData = new double[(this.stateDim + this.paramDim) * this.stateDim];
/* 149 */     for (int i = 0; i < this.stateDim; i++) {
/* 150 */       this.matricesData[i * (this.stateDim + 1)] = 1.0D;
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
/*     */   public void registerVariationalEquations(ExpandableStatefulODE expandable) throws DimensionMismatchException, MismatchedEquations {
/* 167 */     FirstOrderDifferentialEquations ode = (this.jode instanceof MainStateJacobianWrapper) ? ((MainStateJacobianWrapper)this.jode).ode : this.jode;
/*     */ 
/*     */     
/* 170 */     if (expandable.getPrimary() != ode) {
/* 171 */       throw new MismatchedEquations();
/*     */     }
/*     */     
/* 174 */     this.efode = expandable;
/* 175 */     this.index = this.efode.addSecondaryEquations(new JacobiansSecondaryEquations());
/* 176 */     this.efode.setSecondaryState(this.index, this.matricesData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addParameterJacobianProvider(ParameterJacobianProvider provider) {
/* 184 */     this.jacobianProviders.add(provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameterizedODE(ParameterizedODE parameterizedOde) {
/* 191 */     this.pode = parameterizedOde;
/* 192 */     this.dirtyParameter = true;
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
/*     */   public void setParameterStep(String parameter, double hP) throws UnknownParameterException {
/* 215 */     for (ParameterConfiguration param : this.selectedParameters) {
/* 216 */       if (parameter.equals(param.getParameterName())) {
/* 217 */         param.setHP(hP);
/* 218 */         this.dirtyParameter = true;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 223 */     throw new UnknownParameterException(parameter);
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
/*     */   public void setInitialMainStateJacobian(double[][] dYdY0) throws DimensionMismatchException {
/* 239 */     checkDimension(this.stateDim, dYdY0);
/* 240 */     checkDimension(this.stateDim, dYdY0[0]);
/*     */ 
/*     */     
/* 243 */     int i = 0;
/* 244 */     for (double[] row : dYdY0) {
/* 245 */       System.arraycopy(row, 0, this.matricesData, i, this.stateDim);
/* 246 */       i += this.stateDim;
/*     */     } 
/*     */     
/* 249 */     if (this.efode != null) {
/* 250 */       this.efode.setSecondaryState(this.index, this.matricesData);
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
/*     */   public void setInitialParameterJacobian(String pName, double[] dYdP) throws UnknownParameterException, DimensionMismatchException {
/* 269 */     checkDimension(this.stateDim, dYdP);
/*     */ 
/*     */     
/* 272 */     int i = this.stateDim * this.stateDim;
/* 273 */     for (ParameterConfiguration param : this.selectedParameters) {
/* 274 */       if (pName.equals(param.getParameterName())) {
/* 275 */         System.arraycopy(dYdP, 0, this.matricesData, i, this.stateDim);
/* 276 */         if (this.efode != null) {
/* 277 */           this.efode.setSecondaryState(this.index, this.matricesData);
/*     */         }
/*     */         return;
/*     */       } 
/* 281 */       i += this.stateDim;
/*     */     } 
/*     */     
/* 284 */     throw new UnknownParameterException(pName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getCurrentMainSetJacobian(double[][] dYdY0) {
/* 294 */     double[] p = this.efode.getSecondaryState(this.index);
/*     */     
/* 296 */     int j = 0;
/* 297 */     for (int i = 0; i < this.stateDim; i++) {
/* 298 */       System.arraycopy(p, j, dYdY0[i], 0, this.stateDim);
/* 299 */       j += this.stateDim;
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
/*     */   public void getCurrentParameterJacobian(String pName, double[] dYdP) {
/* 311 */     double[] p = this.efode.getSecondaryState(this.index);
/*     */     
/* 313 */     int i = this.stateDim * this.stateDim;
/* 314 */     for (ParameterConfiguration param : this.selectedParameters) {
/* 315 */       if (param.getParameterName().equals(pName)) {
/* 316 */         System.arraycopy(p, i, dYdP, 0, this.stateDim);
/*     */         return;
/*     */       } 
/* 319 */       i += this.stateDim;
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
/*     */   private void checkDimension(int expected, Object array) throws DimensionMismatchException {
/* 331 */     int arrayDimension = (array == null) ? 0 : Array.getLength(array);
/* 332 */     if (arrayDimension != expected) {
/* 333 */       throw new DimensionMismatchException(arrayDimension, expected);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class JacobiansSecondaryEquations
/*     */     implements SecondaryEquations
/*     */   {
/*     */     private JacobiansSecondaryEquations() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public int getDimension() {
/* 347 */       return JacobianMatrices.this.stateDim * (JacobianMatrices.this.stateDim + JacobianMatrices.this.paramDim);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void computeDerivatives(double t, double[] y, double[] yDot, double[] z, double[] zDot) throws MaxCountExceededException, DimensionMismatchException {
/* 356 */       if (JacobianMatrices.this.dirtyParameter && JacobianMatrices.this.paramDim != 0) {
/* 357 */         JacobianMatrices.this.jacobianProviders.add(new ParameterJacobianWrapper(JacobianMatrices.this.jode, JacobianMatrices.this.pode, JacobianMatrices.this.selectedParameters));
/* 358 */         JacobianMatrices.this.dirtyParameter = false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 365 */       double[][] dFdY = new double[JacobianMatrices.this.stateDim][JacobianMatrices.this.stateDim];
/* 366 */       JacobianMatrices.this.jode.computeMainStateJacobian(t, y, yDot, dFdY);
/*     */ 
/*     */       
/* 369 */       for (int i = 0; i < JacobianMatrices.this.stateDim; i++) {
/* 370 */         double[] dFdYi = dFdY[i];
/* 371 */         for (int j = 0; j < JacobianMatrices.this.stateDim; j++) {
/* 372 */           double s = 0.0D;
/* 373 */           int startIndex = j;
/* 374 */           int zIndex = startIndex;
/* 375 */           for (int l = 0; l < JacobianMatrices.this.stateDim; l++) {
/* 376 */             s += dFdYi[l] * z[zIndex];
/* 377 */             zIndex += JacobianMatrices.this.stateDim;
/*     */           } 
/* 379 */           zDot[startIndex + i * JacobianMatrices.this.stateDim] = s;
/*     */         } 
/*     */       } 
/*     */       
/* 383 */       if (JacobianMatrices.this.paramDim != 0) {
/*     */         
/* 385 */         double[] dFdP = new double[JacobianMatrices.this.stateDim];
/* 386 */         int startIndex = JacobianMatrices.this.stateDim * JacobianMatrices.this.stateDim;
/* 387 */         for (ParameterConfiguration param : JacobianMatrices.this.selectedParameters) {
/* 388 */           boolean found = false;
/* 389 */           for (int k = 0; !found && k < JacobianMatrices.this.jacobianProviders.size(); k++) {
/* 390 */             ParameterJacobianProvider provider = JacobianMatrices.this.jacobianProviders.get(k);
/* 391 */             if (provider.isSupported(param.getParameterName())) {
/* 392 */               provider.computeParameterJacobian(t, y, yDot, param.getParameterName(), dFdP);
/*     */               
/* 394 */               for (int j = 0; j < JacobianMatrices.this.stateDim; j++) {
/* 395 */                 double[] dFdYi = dFdY[j];
/* 396 */                 int zIndex = startIndex;
/* 397 */                 double s = dFdP[j];
/* 398 */                 for (int l = 0; l < JacobianMatrices.this.stateDim; l++) {
/* 399 */                   s += dFdYi[l] * z[zIndex];
/* 400 */                   zIndex++;
/*     */                 } 
/* 402 */                 zDot[startIndex + j] = s;
/*     */               } 
/* 404 */               found = true;
/*     */             } 
/*     */           } 
/* 407 */           if (!found) {
/* 408 */             Arrays.fill(zDot, startIndex, startIndex + JacobianMatrices.this.stateDim, 0.0D);
/*     */           }
/* 410 */           startIndex += JacobianMatrices.this.stateDim;
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
/*     */   private static class MainStateJacobianWrapper
/*     */     implements MainStateJacobianProvider
/*     */   {
/*     */     private final FirstOrderDifferentialEquations ode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] hY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MainStateJacobianWrapper(FirstOrderDifferentialEquations ode, double[] hY) throws DimensionMismatchException {
/* 437 */       this.ode = ode;
/* 438 */       this.hY = (double[])hY.clone();
/* 439 */       if (hY.length != ode.getDimension()) {
/* 440 */         throw new DimensionMismatchException(ode.getDimension(), hY.length);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int getDimension() {
/* 446 */       return this.ode.getDimension();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
/* 452 */       this.ode.computeDerivatives(t, y, yDot);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void computeMainStateJacobian(double t, double[] y, double[] yDot, double[][] dFdY) throws MaxCountExceededException, DimensionMismatchException {
/* 459 */       int n = this.ode.getDimension();
/* 460 */       double[] tmpDot = new double[n];
/*     */       
/* 462 */       for (int j = 0; j < n; j++) {
/* 463 */         double savedYj = y[j];
/* 464 */         y[j] = y[j] + this.hY[j];
/* 465 */         this.ode.computeDerivatives(t, y, tmpDot);
/* 466 */         for (int i = 0; i < n; i++) {
/* 467 */           dFdY[i][j] = (tmpDot[i] - yDot[i]) / this.hY[j];
/*     */         }
/* 469 */         y[j] = savedYj;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MismatchedEquations
/*     */     extends MathIllegalArgumentException
/*     */   {
/*     */     private static final long serialVersionUID = 20120902L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MismatchedEquations() {
/* 486 */       super((Localizable)LocalizedFormats.UNMATCHED_ODE_IN_EXPANDED_SET, new Object[0]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/JacobianMatrices.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */