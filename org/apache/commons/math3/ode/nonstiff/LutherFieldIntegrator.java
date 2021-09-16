/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.ode.FieldEquationsMapper;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
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
/*     */ public class LutherFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldIntegrator<T>
/*     */ {
/*     */   public LutherFieldIntegrator(Field<T> field, T step) {
/*  70 */     super(field, "Luther", step);
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] getC() {
/*  75 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)getField().getZero()).add(21.0D)).sqrt();
/*  76 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 6);
/*  77 */     arrayOfRealFieldElement[0] = (RealFieldElement)getField().getOne();
/*  78 */     arrayOfRealFieldElement[1] = (RealFieldElement)fraction(1, 2);
/*  79 */     arrayOfRealFieldElement[2] = (RealFieldElement)fraction(2, 3);
/*  80 */     arrayOfRealFieldElement[3] = (RealFieldElement)((RealFieldElement)realFieldElement.subtract(7.0D)).divide(-14.0D);
/*  81 */     arrayOfRealFieldElement[4] = (RealFieldElement)((RealFieldElement)realFieldElement.add(7.0D)).divide(14.0D);
/*  82 */     arrayOfRealFieldElement[5] = (RealFieldElement)getField().getOne();
/*  83 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public T[][] getA() {
/*  88 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)getField().getZero()).add(21.0D)).sqrt();
/*  89 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), 6, -1);
/*  90 */     for (int i = 0; i < arrayOfRealFieldElement.length; i++) {
/*  91 */       arrayOfRealFieldElement[i] = (RealFieldElement[])MathArrays.buildArray(getField(), i + 1);
/*     */     }
/*  93 */     arrayOfRealFieldElement[0][0] = (RealFieldElement)getField().getOne();
/*  94 */     arrayOfRealFieldElement[1][0] = (RealFieldElement)fraction(3, 8);
/*  95 */     arrayOfRealFieldElement[1][1] = (RealFieldElement)fraction(1, 8);
/*  96 */     arrayOfRealFieldElement[2][0] = (RealFieldElement)fraction(8, 27);
/*  97 */     arrayOfRealFieldElement[2][1] = (RealFieldElement)fraction(2, 27);
/*  98 */     arrayOfRealFieldElement[2][2] = arrayOfRealFieldElement[2][0];
/*  99 */     arrayOfRealFieldElement[3][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(9)).add(-21.0D)).divide(392.0D);
/* 100 */     arrayOfRealFieldElement[3][1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(8)).add(-56.0D)).divide(392.0D);
/* 101 */     arrayOfRealFieldElement[3][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-48)).add(336.0D)).divide(392.0D);
/* 102 */     arrayOfRealFieldElement[3][3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(3)).add(-63.0D)).divide(392.0D);
/* 103 */     arrayOfRealFieldElement[4][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-255)).add(-1155.0D)).divide(1960.0D);
/* 104 */     arrayOfRealFieldElement[4][1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-40)).add(-280.0D)).divide(1960.0D);
/* 105 */     arrayOfRealFieldElement[4][2] = (RealFieldElement)((RealFieldElement)realFieldElement.multiply(-320)).divide(1960.0D);
/* 106 */     arrayOfRealFieldElement[4][3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(363)).add(63.0D)).divide(1960.0D);
/* 107 */     arrayOfRealFieldElement[4][4] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(392)).add(2352.0D)).divide(1960.0D);
/* 108 */     arrayOfRealFieldElement[5][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(105)).add(330.0D)).divide(180.0D);
/* 109 */     arrayOfRealFieldElement[5][1] = (RealFieldElement)fraction(2, 3);
/* 110 */     arrayOfRealFieldElement[5][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(280)).add(-200.0D)).divide(180.0D);
/* 111 */     arrayOfRealFieldElement[5][3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-189)).add(126.0D)).divide(180.0D);
/* 112 */     arrayOfRealFieldElement[5][4] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-126)).add(-686.0D)).divide(180.0D);
/* 113 */     arrayOfRealFieldElement[5][5] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-70)).add(490.0D)).divide(180.0D);
/* 114 */     return (T[][])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] getB() {
/* 120 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 7);
/* 121 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(1, 20);
/* 122 */     arrayOfRealFieldElement[1] = (RealFieldElement)getField().getZero();
/* 123 */     arrayOfRealFieldElement[2] = (RealFieldElement)fraction(16, 45);
/* 124 */     arrayOfRealFieldElement[3] = (RealFieldElement)getField().getZero();
/* 125 */     arrayOfRealFieldElement[4] = (RealFieldElement)fraction(49, 180);
/* 126 */     arrayOfRealFieldElement[5] = arrayOfRealFieldElement[4];
/* 127 */     arrayOfRealFieldElement[6] = arrayOfRealFieldElement[0];
/*     */     
/* 129 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LutherFieldStepInterpolator<T> createInterpolator(boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> mapper) {
/* 140 */     return new LutherFieldStepInterpolator<T>(getField(), forward, yDotK, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, mapper);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/LutherFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */