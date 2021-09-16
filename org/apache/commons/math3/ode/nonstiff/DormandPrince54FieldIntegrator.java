/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.ode.FieldEquationsMapper;
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
/*     */ public class DormandPrince54FieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends EmbeddedRungeKuttaFieldIntegrator<T>
/*     */ {
/*     */   private static final String METHOD_NAME = "Dormand-Prince 5(4)";
/*     */   private final T e1;
/*     */   private final T e3;
/*     */   private final T e4;
/*     */   private final T e5;
/*     */   private final T e6;
/*     */   private final T e7;
/*     */   
/*     */   public DormandPrince54FieldIntegrator(Field<T> field, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
/*  96 */     super(field, "Dormand-Prince 5(4)", 6, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */     
/*  98 */     this.e1 = fraction(71, 57600);
/*  99 */     this.e3 = fraction(-71, 16695);
/* 100 */     this.e4 = fraction(71, 1920);
/* 101 */     this.e5 = fraction(-17253, 339200);
/* 102 */     this.e6 = fraction(22, 525);
/* 103 */     this.e7 = fraction(-1, 40);
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
/*     */   public DormandPrince54FieldIntegrator(Field<T> field, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/* 122 */     super(field, "Dormand-Prince 5(4)", 6, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */     
/* 124 */     this.e1 = fraction(71, 57600);
/* 125 */     this.e3 = fraction(-71, 16695);
/* 126 */     this.e4 = fraction(71, 1920);
/* 127 */     this.e5 = fraction(-17253, 339200);
/* 128 */     this.e6 = fraction(22, 525);
/* 129 */     this.e7 = fraction(-1, 40);
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] getC() {
/* 134 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 6);
/* 135 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(1, 5);
/* 136 */     arrayOfRealFieldElement[1] = (RealFieldElement)fraction(3, 10);
/* 137 */     arrayOfRealFieldElement[2] = (RealFieldElement)fraction(4, 5);
/* 138 */     arrayOfRealFieldElement[3] = (RealFieldElement)fraction(8, 9);
/* 139 */     arrayOfRealFieldElement[4] = (RealFieldElement)getField().getOne();
/* 140 */     arrayOfRealFieldElement[5] = (RealFieldElement)getField().getOne();
/* 141 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public T[][] getA() {
/* 146 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), 6, -1);
/* 147 */     for (int i = 0; i < arrayOfRealFieldElement.length; i++) {
/* 148 */       arrayOfRealFieldElement[i] = (RealFieldElement[])MathArrays.buildArray(getField(), i + 1);
/*     */     }
/* 150 */     arrayOfRealFieldElement[0][0] = (RealFieldElement)fraction(1, 5);
/* 151 */     arrayOfRealFieldElement[1][0] = (RealFieldElement)fraction(3, 40);
/* 152 */     arrayOfRealFieldElement[1][1] = (RealFieldElement)fraction(9, 40);
/* 153 */     arrayOfRealFieldElement[2][0] = (RealFieldElement)fraction(44, 45);
/* 154 */     arrayOfRealFieldElement[2][1] = (RealFieldElement)fraction(-56, 15);
/* 155 */     arrayOfRealFieldElement[2][2] = (RealFieldElement)fraction(32, 9);
/* 156 */     arrayOfRealFieldElement[3][0] = (RealFieldElement)fraction(19372, 6561);
/* 157 */     arrayOfRealFieldElement[3][1] = (RealFieldElement)fraction(-25360, 2187);
/* 158 */     arrayOfRealFieldElement[3][2] = (RealFieldElement)fraction(64448, 6561);
/* 159 */     arrayOfRealFieldElement[3][3] = (RealFieldElement)fraction(-212, 729);
/* 160 */     arrayOfRealFieldElement[4][0] = (RealFieldElement)fraction(9017, 3168);
/* 161 */     arrayOfRealFieldElement[4][1] = (RealFieldElement)fraction(-355, 33);
/* 162 */     arrayOfRealFieldElement[4][2] = (RealFieldElement)fraction(46732, 5247);
/* 163 */     arrayOfRealFieldElement[4][3] = (RealFieldElement)fraction(49, 176);
/* 164 */     arrayOfRealFieldElement[4][4] = (RealFieldElement)fraction(-5103, 18656);
/* 165 */     arrayOfRealFieldElement[5][0] = (RealFieldElement)fraction(35, 384);
/* 166 */     arrayOfRealFieldElement[5][1] = (RealFieldElement)getField().getZero();
/* 167 */     arrayOfRealFieldElement[5][2] = (RealFieldElement)fraction(500, 1113);
/* 168 */     arrayOfRealFieldElement[5][3] = (RealFieldElement)fraction(125, 192);
/* 169 */     arrayOfRealFieldElement[5][4] = (RealFieldElement)fraction(-2187, 6784);
/* 170 */     arrayOfRealFieldElement[5][5] = (RealFieldElement)fraction(11, 84);
/* 171 */     return (T[][])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] getB() {
/* 176 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 7);
/* 177 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(35, 384);
/* 178 */     arrayOfRealFieldElement[1] = (RealFieldElement)getField().getZero();
/* 179 */     arrayOfRealFieldElement[2] = (RealFieldElement)fraction(500, 1113);
/* 180 */     arrayOfRealFieldElement[3] = (RealFieldElement)fraction(125, 192);
/* 181 */     arrayOfRealFieldElement[4] = (RealFieldElement)fraction(-2187, 6784);
/* 182 */     arrayOfRealFieldElement[5] = (RealFieldElement)fraction(11, 84);
/* 183 */     arrayOfRealFieldElement[6] = (RealFieldElement)getField().getZero();
/* 184 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DormandPrince54FieldStepInterpolator<T> createInterpolator(boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> mapper) {
/* 193 */     return new DormandPrince54FieldStepInterpolator<T>(getField(), forward, yDotK, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, mapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrder() {
/* 202 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T estimateError(T[][] yDotK, T[] y0, T[] y1, T h) {
/* 209 */     RealFieldElement realFieldElement = (RealFieldElement)getField().getZero();
/*     */     
/* 211 */     for (int j = 0; j < this.mainSetDimension; j++) {
/* 212 */       RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)yDotK[0][j].multiply(this.e1)).add(yDotK[2][j].multiply(this.e3))).add(yDotK[3][j].multiply(this.e4))).add(yDotK[4][j].multiply(this.e5))).add(yDotK[5][j].multiply(this.e6))).add(yDotK[6][j].multiply(this.e7));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 219 */       RealFieldElement realFieldElement2 = MathUtils.max((RealFieldElement)y0[j].abs(), (RealFieldElement)y1[j].abs());
/* 220 */       RealFieldElement realFieldElement3 = (this.vecAbsoluteTolerance == null) ? (RealFieldElement)((RealFieldElement)realFieldElement2.multiply(this.scalRelativeTolerance)).add(this.scalAbsoluteTolerance) : (RealFieldElement)((RealFieldElement)realFieldElement2.multiply(this.vecRelativeTolerance[j])).add(this.vecAbsoluteTolerance[j]);
/*     */ 
/*     */       
/* 223 */       RealFieldElement realFieldElement4 = (RealFieldElement)((RealFieldElement)h.multiply(realFieldElement1)).divide(realFieldElement3);
/* 224 */       realFieldElement = (RealFieldElement)realFieldElement.add(realFieldElement4.multiply(realFieldElement4));
/*     */     } 
/*     */ 
/*     */     
/* 228 */     return (T)((RealFieldElement)realFieldElement.divide(this.mainSetDimension)).sqrt();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/DormandPrince54FieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */