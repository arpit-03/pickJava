/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.ode.FieldEquationsMapper;
/*     */ import org.apache.commons.math3.ode.FieldExpandableODE;
/*     */ import org.apache.commons.math3.ode.FieldODEState;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
/*     */ import org.apache.commons.math3.util.MathArrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EmbeddedRungeKuttaFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends AdaptiveStepsizeFieldIntegrator<T>
/*     */   implements FieldButcherArrayProvider<T>
/*     */ {
/*     */   private final int fsal;
/*     */   private final T[] c;
/*     */   private final T[][] a;
/*     */   private final T[] b;
/*     */   private final T exp;
/*     */   private T safety;
/*     */   private T minReduction;
/*     */   private T maxGrowth;
/*     */   
/*     */   protected EmbeddedRungeKuttaFieldIntegrator(Field<T> field, String name, int fsal, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
/* 117 */     super(field, name, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */     
/* 119 */     this.fsal = fsal;
/* 120 */     this.c = getC();
/* 121 */     this.a = getA();
/* 122 */     this.b = getB();
/*     */     
/* 124 */     this.exp = (T)((RealFieldElement)field.getOne()).divide(-getOrder());
/*     */ 
/*     */     
/* 127 */     setSafety((T)((RealFieldElement)field.getZero()).add(0.9D));
/* 128 */     setMinReduction((T)((RealFieldElement)field.getZero()).add(0.2D));
/* 129 */     setMaxGrowth((T)((RealFieldElement)field.getZero()).add(10.0D));
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
/*     */   protected EmbeddedRungeKuttaFieldIntegrator(Field<T> field, String name, int fsal, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/* 150 */     super(field, name, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */     
/* 152 */     this.fsal = fsal;
/* 153 */     this.c = getC();
/* 154 */     this.a = getA();
/* 155 */     this.b = getB();
/*     */     
/* 157 */     this.exp = (T)((RealFieldElement)field.getOne()).divide(-getOrder());
/*     */ 
/*     */     
/* 160 */     setSafety((T)((RealFieldElement)field.getZero()).add(0.9D));
/* 161 */     setMinReduction((T)((RealFieldElement)field.getZero()).add(0.2D));
/* 162 */     setMaxGrowth((T)((RealFieldElement)field.getZero()).add(10.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T fraction(int p, int q) {
/* 172 */     return (T)((RealFieldElement)((RealFieldElement)getField().getOne()).multiply(p)).divide(q);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T fraction(double p, double q) {
/* 181 */     return (T)((RealFieldElement)((RealFieldElement)getField().getOne()).multiply(p)).divide(q);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract RungeKuttaFieldStepInterpolator<T> createInterpolator(boolean paramBoolean, T[][] paramArrayOfT, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative1, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative2, FieldEquationsMapper<T> paramFieldEquationsMapper);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getOrder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getSafety() {
/* 205 */     return this.safety;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSafety(T safety) {
/* 212 */     this.safety = safety;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldODEStateAndDerivative<T> integrate(FieldExpandableODE<T> equations, FieldODEState<T> initialState, T finalTime) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
/* 221 */     sanityChecks(initialState, finalTime);
/* 222 */     RealFieldElement realFieldElement1 = initialState.getTime();
/* 223 */     RealFieldElement[] arrayOfRealFieldElement1 = equations.getMapper().mapState(initialState);
/* 224 */     setStepStart(initIntegration(equations, realFieldElement1, arrayOfRealFieldElement1, (RealFieldElement)finalTime));
/* 225 */     boolean forward = (((RealFieldElement)finalTime.subtract(initialState.getTime())).getReal() > 0.0D);
/*     */ 
/*     */     
/* 228 */     int stages = this.c.length + 1;
/* 229 */     RealFieldElement[] arrayOfRealFieldElement2 = arrayOfRealFieldElement1;
/* 230 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), stages, -1);
/* 231 */     RealFieldElement[] arrayOfRealFieldElement3 = (RealFieldElement[])MathArrays.buildArray(getField(), arrayOfRealFieldElement1.length);
/*     */ 
/*     */     
/* 234 */     RealFieldElement realFieldElement2 = (RealFieldElement)getField().getZero();
/* 235 */     boolean firstTime = true;
/*     */ 
/*     */     
/* 238 */     setIsLastStep(false);
/*     */ 
/*     */     
/*     */     do {
/* 242 */       RealFieldElement realFieldElement3 = (RealFieldElement)((RealFieldElement)getField().getZero()).add(10.0D);
/* 243 */       while (((RealFieldElement)realFieldElement3.subtract(1.0D)).getReal() >= 0.0D) {
/*     */ 
/*     */         
/* 246 */         arrayOfRealFieldElement2 = equations.getMapper().mapState((FieldODEState)getStepStart());
/* 247 */         arrayOfRealFieldElement[0] = equations.getMapper().mapDerivative(getStepStart());
/*     */         
/* 249 */         if (firstTime) {
/* 250 */           RealFieldElement[] arrayOfRealFieldElement5 = (RealFieldElement[])MathArrays.buildArray(getField(), this.mainSetDimension);
/* 251 */           if (this.vecAbsoluteTolerance == null) {
/* 252 */             for (int i = 0; i < arrayOfRealFieldElement5.length; i++) {
/* 253 */               arrayOfRealFieldElement5[i] = (RealFieldElement)((RealFieldElement)((RealFieldElement)arrayOfRealFieldElement2[i].abs()).multiply(this.scalRelativeTolerance)).add(this.scalAbsoluteTolerance);
/*     */             }
/*     */           } else {
/* 256 */             for (int i = 0; i < arrayOfRealFieldElement5.length; i++) {
/* 257 */               arrayOfRealFieldElement5[i] = (RealFieldElement)((RealFieldElement)((RealFieldElement)arrayOfRealFieldElement2[i].abs()).multiply(this.vecRelativeTolerance[i])).add(this.vecAbsoluteTolerance[i]);
/*     */             }
/*     */           } 
/* 260 */           realFieldElement2 = (RealFieldElement)initializeStep(forward, getOrder(), (T[])arrayOfRealFieldElement5, getStepStart(), equations.getMapper());
/* 261 */           firstTime = false;
/*     */         } 
/*     */         
/* 264 */         setStepSize(realFieldElement2);
/* 265 */         if (forward) {
/* 266 */           if (((RealFieldElement)((RealFieldElement)getStepStart().getTime().add(getStepSize())).subtract(finalTime)).getReal() >= 0.0D) {
/* 267 */             setStepSize((RealFieldElement)finalTime.subtract(getStepStart().getTime()));
/*     */           }
/*     */         }
/* 270 */         else if (((RealFieldElement)((RealFieldElement)getStepStart().getTime().add(getStepSize())).subtract(finalTime)).getReal() <= 0.0D) {
/* 271 */           setStepSize((RealFieldElement)finalTime.subtract(getStepStart().getTime()));
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 276 */         for (int k = 1; k < stages; k++) {
/*     */           
/* 278 */           for (int i = 0; i < arrayOfRealFieldElement1.length; i++) {
/* 279 */             RealFieldElement realFieldElement = (RealFieldElement)arrayOfRealFieldElement[0][i].multiply(this.a[k - 1][0]);
/* 280 */             for (int l = 1; l < k; l++) {
/* 281 */               realFieldElement = (RealFieldElement)realFieldElement.add(arrayOfRealFieldElement[l][i].multiply(this.a[k - 1][l]));
/*     */             }
/* 283 */             arrayOfRealFieldElement3[i] = (RealFieldElement)arrayOfRealFieldElement2[i].add(getStepSize().multiply(realFieldElement));
/*     */           } 
/*     */           
/* 286 */           arrayOfRealFieldElement[k] = computeDerivatives((RealFieldElement)getStepStart().getTime().add(getStepSize().multiply(this.c[k - 1])), arrayOfRealFieldElement3);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 291 */         for (int j = 0; j < arrayOfRealFieldElement1.length; j++) {
/* 292 */           RealFieldElement realFieldElement = (RealFieldElement)arrayOfRealFieldElement[0][j].multiply(this.b[0]);
/* 293 */           for (int l = 1; l < stages; l++) {
/* 294 */             realFieldElement = (RealFieldElement)realFieldElement.add(arrayOfRealFieldElement[l][j].multiply(this.b[l]));
/*     */           }
/* 296 */           arrayOfRealFieldElement3[j] = (RealFieldElement)arrayOfRealFieldElement2[j].add(getStepSize().multiply(realFieldElement));
/*     */         } 
/*     */ 
/*     */         
/* 300 */         realFieldElement3 = (RealFieldElement)estimateError((T[][])arrayOfRealFieldElement, (T[])arrayOfRealFieldElement2, (T[])arrayOfRealFieldElement3, (T)getStepSize());
/* 301 */         if (((RealFieldElement)realFieldElement3.subtract(1.0D)).getReal() >= 0.0D) {
/*     */           
/* 303 */           RealFieldElement realFieldElement = MathUtils.min((RealFieldElement)this.maxGrowth, MathUtils.max((RealFieldElement)this.minReduction, (RealFieldElement)this.safety.multiply(realFieldElement3.pow(this.exp))));
/*     */           
/* 305 */           realFieldElement2 = (RealFieldElement)filterStep((T)getStepSize().multiply(realFieldElement), forward, false);
/*     */         } 
/*     */       } 
/*     */       
/* 309 */       RealFieldElement realFieldElement4 = (RealFieldElement)getStepStart().getTime().add(getStepSize());
/* 310 */       RealFieldElement[] arrayOfRealFieldElement4 = (this.fsal >= 0) ? arrayOfRealFieldElement[this.fsal] : computeDerivatives(realFieldElement4, arrayOfRealFieldElement3);
/* 311 */       FieldODEStateAndDerivative<T> stateTmp = new FieldODEStateAndDerivative(realFieldElement4, arrayOfRealFieldElement3, arrayOfRealFieldElement4);
/*     */ 
/*     */       
/* 314 */       System.arraycopy(arrayOfRealFieldElement3, 0, arrayOfRealFieldElement2, 0, arrayOfRealFieldElement1.length);
/* 315 */       setStepStart(acceptStep(createInterpolator(forward, (T[][])arrayOfRealFieldElement, getStepStart(), stateTmp, equations.getMapper()), (RealFieldElement)finalTime));
/*     */ 
/*     */       
/* 318 */       if (isLastStep()) {
/*     */         continue;
/*     */       }
/* 321 */       RealFieldElement realFieldElement5 = MathUtils.min((RealFieldElement)this.maxGrowth, MathUtils.max((RealFieldElement)this.minReduction, (RealFieldElement)this.safety.multiply(realFieldElement3.pow(this.exp))));
/*     */       
/* 323 */       RealFieldElement realFieldElement6 = (RealFieldElement)getStepSize().multiply(realFieldElement5);
/* 324 */       RealFieldElement realFieldElement7 = (RealFieldElement)getStepStart().getTime().add(realFieldElement6);
/* 325 */       boolean nextIsLast = forward ? ((((RealFieldElement)realFieldElement7.subtract(finalTime)).getReal() >= 0.0D)) : ((((RealFieldElement)realFieldElement7.subtract(finalTime)).getReal() <= 0.0D));
/*     */ 
/*     */       
/* 328 */       realFieldElement2 = (RealFieldElement)filterStep((T)realFieldElement6, forward, nextIsLast);
/*     */       
/* 330 */       RealFieldElement realFieldElement8 = (RealFieldElement)getStepStart().getTime().add(realFieldElement2);
/* 331 */       boolean filteredNextIsLast = forward ? ((((RealFieldElement)realFieldElement8.subtract(finalTime)).getReal() >= 0.0D)) : ((((RealFieldElement)realFieldElement8.subtract(finalTime)).getReal() <= 0.0D));
/*     */ 
/*     */       
/* 334 */       if (!filteredNextIsLast)
/* 335 */         continue;  realFieldElement2 = (RealFieldElement)finalTime.subtract(getStepStart().getTime());
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 340 */     while (!isLastStep());
/*     */     
/* 342 */     FieldODEStateAndDerivative<T> finalState = getStepStart();
/* 343 */     resetInternalState();
/* 344 */     return finalState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getMinReduction() {
/* 352 */     return this.minReduction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinReduction(T minReduction) {
/* 359 */     this.minReduction = minReduction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getMaxGrowth() {
/* 366 */     return this.maxGrowth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxGrowth(T maxGrowth) {
/* 373 */     this.maxGrowth = maxGrowth;
/*     */   }
/*     */   
/*     */   protected abstract T estimateError(T[][] paramArrayOfT, T[] paramArrayOfT1, T[] paramArrayOfT2, T paramT);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/EmbeddedRungeKuttaFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */