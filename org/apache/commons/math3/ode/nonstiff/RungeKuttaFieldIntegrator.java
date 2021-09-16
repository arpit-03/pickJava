/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.ode.AbstractFieldIntegrator;
/*     */ import org.apache.commons.math3.ode.FieldEquationsMapper;
/*     */ import org.apache.commons.math3.ode.FieldExpandableODE;
/*     */ import org.apache.commons.math3.ode.FieldODEState;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
/*     */ import org.apache.commons.math3.ode.FirstOrderFieldDifferentialEquations;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RungeKuttaFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends AbstractFieldIntegrator<T>
/*     */   implements FieldButcherArrayProvider<T>
/*     */ {
/*     */   private final T[] c;
/*     */   private final T[][] a;
/*     */   private final T[] b;
/*     */   private final T step;
/*     */   
/*     */   protected RungeKuttaFieldIntegrator(Field<T> field, String name, T step) {
/*  84 */     super(field, name);
/*  85 */     this.c = getC();
/*  86 */     this.a = getA();
/*  87 */     this.b = getB();
/*  88 */     this.step = (T)step.abs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T fraction(int p, int q) {
/*  97 */     return (T)((RealFieldElement)((RealFieldElement)getField().getZero()).add(p)).divide(q);
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
/*     */   protected abstract RungeKuttaFieldStepInterpolator<T> createInterpolator(boolean paramBoolean, T[][] paramArrayOfT, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative1, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative2, FieldEquationsMapper<T> paramFieldEquationsMapper);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldODEStateAndDerivative<T> integrate(FieldExpandableODE<T> equations, FieldODEState<T> initialState, T finalTime) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
/* 119 */     sanityChecks(initialState, (RealFieldElement)finalTime);
/* 120 */     RealFieldElement realFieldElement = initialState.getTime();
/* 121 */     RealFieldElement[] arrayOfRealFieldElement1 = equations.getMapper().mapState(initialState);
/* 122 */     setStepStart(initIntegration(equations, realFieldElement, arrayOfRealFieldElement1, (RealFieldElement)finalTime));
/* 123 */     boolean forward = (((RealFieldElement)finalTime.subtract(initialState.getTime())).getReal() > 0.0D);
/*     */ 
/*     */     
/* 126 */     int stages = this.c.length + 1;
/* 127 */     RealFieldElement[] arrayOfRealFieldElement2 = arrayOfRealFieldElement1;
/* 128 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), stages, -1);
/* 129 */     RealFieldElement[] arrayOfRealFieldElement3 = (RealFieldElement[])MathArrays.buildArray(getField(), arrayOfRealFieldElement1.length);
/*     */ 
/*     */     
/* 132 */     if (forward) {
/* 133 */       if (((RealFieldElement)((RealFieldElement)getStepStart().getTime().add(this.step)).subtract(finalTime)).getReal() >= 0.0D) {
/* 134 */         setStepSize((RealFieldElement)finalTime.subtract(getStepStart().getTime()));
/*     */       } else {
/* 136 */         setStepSize((RealFieldElement)this.step);
/*     */       }
/*     */     
/* 139 */     } else if (((RealFieldElement)((RealFieldElement)getStepStart().getTime().subtract(this.step)).subtract(finalTime)).getReal() <= 0.0D) {
/* 140 */       setStepSize((RealFieldElement)finalTime.subtract(getStepStart().getTime()));
/*     */     } else {
/* 142 */       setStepSize((RealFieldElement)this.step.negate());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 147 */     setIsLastStep(false);
/*     */ 
/*     */     
/*     */     do {
/* 151 */       arrayOfRealFieldElement2 = equations.getMapper().mapState((FieldODEState)getStepStart());
/* 152 */       arrayOfRealFieldElement[0] = equations.getMapper().mapDerivative(getStepStart());
/*     */ 
/*     */       
/* 155 */       for (int k = 1; k < stages; k++) {
/*     */         
/* 157 */         for (int i = 0; i < arrayOfRealFieldElement1.length; i++) {
/* 158 */           RealFieldElement realFieldElement3 = (RealFieldElement)arrayOfRealFieldElement[0][i].multiply(this.a[k - 1][0]);
/* 159 */           for (int l = 1; l < k; l++) {
/* 160 */             realFieldElement3 = (RealFieldElement)realFieldElement3.add(arrayOfRealFieldElement[l][i].multiply(this.a[k - 1][l]));
/*     */           }
/* 162 */           arrayOfRealFieldElement3[i] = (RealFieldElement)arrayOfRealFieldElement2[i].add(getStepSize().multiply(realFieldElement3));
/*     */         } 
/*     */         
/* 165 */         arrayOfRealFieldElement[k] = computeDerivatives((RealFieldElement)getStepStart().getTime().add(getStepSize().multiply(this.c[k - 1])), arrayOfRealFieldElement3);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 170 */       for (int j = 0; j < arrayOfRealFieldElement1.length; j++) {
/* 171 */         RealFieldElement realFieldElement3 = (RealFieldElement)arrayOfRealFieldElement[0][j].multiply(this.b[0]);
/* 172 */         for (int l = 1; l < stages; l++) {
/* 173 */           realFieldElement3 = (RealFieldElement)realFieldElement3.add(arrayOfRealFieldElement[l][j].multiply(this.b[l]));
/*     */         }
/* 175 */         arrayOfRealFieldElement3[j] = (RealFieldElement)arrayOfRealFieldElement2[j].add(getStepSize().multiply(realFieldElement3));
/*     */       } 
/* 177 */       RealFieldElement realFieldElement1 = (RealFieldElement)getStepStart().getTime().add(getStepSize());
/* 178 */       RealFieldElement[] arrayOfRealFieldElement4 = computeDerivatives(realFieldElement1, arrayOfRealFieldElement3);
/* 179 */       FieldODEStateAndDerivative<T> stateTmp = new FieldODEStateAndDerivative(realFieldElement1, arrayOfRealFieldElement3, arrayOfRealFieldElement4);
/*     */ 
/*     */       
/* 182 */       System.arraycopy(arrayOfRealFieldElement3, 0, arrayOfRealFieldElement2, 0, arrayOfRealFieldElement1.length);
/* 183 */       setStepStart(acceptStep(createInterpolator(forward, (T[][])arrayOfRealFieldElement, getStepStart(), stateTmp, equations.getMapper()), (RealFieldElement)finalTime));
/*     */ 
/*     */       
/* 186 */       if (isLastStep()) {
/*     */         continue;
/*     */       }
/* 189 */       RealFieldElement realFieldElement2 = (RealFieldElement)getStepStart().getTime().add(getStepSize());
/* 190 */       boolean nextIsLast = forward ? ((((RealFieldElement)realFieldElement2.subtract(finalTime)).getReal() >= 0.0D)) : ((((RealFieldElement)realFieldElement2.subtract(finalTime)).getReal() <= 0.0D));
/*     */ 
/*     */       
/* 193 */       if (!nextIsLast)
/* 194 */         continue;  setStepSize((RealFieldElement)finalTime.subtract(getStepStart().getTime()));
/*     */ 
/*     */     
/*     */     }
/* 198 */     while (!isLastStep());
/*     */     
/* 200 */     FieldODEStateAndDerivative<T> finalState = getStepStart();
/* 201 */     setStepStart(null);
/* 202 */     setStepSize(null);
/* 203 */     return finalState;
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
/*     */   public T[] singleStep(FirstOrderFieldDifferentialEquations<T> equations, T t0, T[] y0, T t) {
/* 236 */     RealFieldElement[] arrayOfRealFieldElement1 = (RealFieldElement[])y0.clone();
/* 237 */     int stages = this.c.length + 1;
/* 238 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), stages, -1);
/* 239 */     RealFieldElement[] arrayOfRealFieldElement2 = (RealFieldElement[])y0.clone();
/*     */ 
/*     */     
/* 242 */     RealFieldElement realFieldElement = (RealFieldElement)t.subtract(t0);
/* 243 */     arrayOfRealFieldElement[0] = equations.computeDerivatives((RealFieldElement)t0, arrayOfRealFieldElement1);
/*     */ 
/*     */     
/* 246 */     for (int k = 1; k < stages; k++) {
/*     */       
/* 248 */       for (int i = 0; i < y0.length; i++) {
/* 249 */         RealFieldElement realFieldElement1 = (RealFieldElement)arrayOfRealFieldElement[0][i].multiply(this.a[k - 1][0]);
/* 250 */         for (int l = 1; l < k; l++) {
/* 251 */           realFieldElement1 = (RealFieldElement)realFieldElement1.add(arrayOfRealFieldElement[l][i].multiply(this.a[k - 1][l]));
/*     */         }
/* 253 */         arrayOfRealFieldElement2[i] = (RealFieldElement)arrayOfRealFieldElement1[i].add(realFieldElement.multiply(realFieldElement1));
/*     */       } 
/*     */       
/* 256 */       arrayOfRealFieldElement[k] = equations.computeDerivatives((RealFieldElement)t0.add(realFieldElement.multiply(this.c[k - 1])), arrayOfRealFieldElement2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 261 */     for (int j = 0; j < y0.length; j++) {
/* 262 */       RealFieldElement realFieldElement1 = (RealFieldElement)arrayOfRealFieldElement[0][j].multiply(this.b[0]);
/* 263 */       for (int l = 1; l < stages; l++) {
/* 264 */         realFieldElement1 = (RealFieldElement)realFieldElement1.add(arrayOfRealFieldElement[l][j].multiply(this.b[l]));
/*     */       }
/* 266 */       arrayOfRealFieldElement1[j] = (RealFieldElement)arrayOfRealFieldElement1[j].add(realFieldElement.multiply(realFieldElement1));
/*     */     } 
/*     */     
/* 269 */     return (T[])arrayOfRealFieldElement1;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/RungeKuttaFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */