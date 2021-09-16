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
/*     */ public class HighamHall54FieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends EmbeddedRungeKuttaFieldIntegrator<T>
/*     */ {
/*     */   private static final String METHOD_NAME = "Higham-Hall 5(4)";
/*     */   private final T[] e;
/*     */   
/*     */   public HighamHall54FieldIntegrator(Field<T> field, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
/*  67 */     super(field, "Higham-Hall 5(4)", -1, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */     
/*  69 */     this.e = (T[])MathArrays.buildArray(field, 7);
/*  70 */     this.e[0] = fraction(-1, 20);
/*  71 */     this.e[1] = (T)field.getZero();
/*  72 */     this.e[2] = fraction(81, 160);
/*  73 */     this.e[3] = fraction(-6, 5);
/*  74 */     this.e[4] = fraction(25, 32);
/*  75 */     this.e[5] = fraction(1, 16);
/*  76 */     this.e[6] = fraction(-1, 10);
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
/*     */   public HighamHall54FieldIntegrator(Field<T> field, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/*  95 */     super(field, "Higham-Hall 5(4)", -1, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */     
/*  97 */     this.e = (T[])MathArrays.buildArray(field, 7);
/*  98 */     this.e[0] = fraction(-1, 20);
/*  99 */     this.e[1] = (T)field.getZero();
/* 100 */     this.e[2] = fraction(81, 160);
/* 101 */     this.e[3] = fraction(-6, 5);
/* 102 */     this.e[4] = fraction(25, 32);
/* 103 */     this.e[5] = fraction(1, 16);
/* 104 */     this.e[6] = fraction(-1, 10);
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] getC() {
/* 109 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 6);
/* 110 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(2, 9);
/* 111 */     arrayOfRealFieldElement[1] = (RealFieldElement)fraction(1, 3);
/* 112 */     arrayOfRealFieldElement[2] = (RealFieldElement)fraction(1, 2);
/* 113 */     arrayOfRealFieldElement[3] = (RealFieldElement)fraction(3, 5);
/* 114 */     arrayOfRealFieldElement[4] = (RealFieldElement)getField().getOne();
/* 115 */     arrayOfRealFieldElement[5] = (RealFieldElement)getField().getOne();
/* 116 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public T[][] getA() {
/* 121 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), 6, -1);
/* 122 */     for (int i = 0; i < arrayOfRealFieldElement.length; i++) {
/* 123 */       arrayOfRealFieldElement[i] = (RealFieldElement[])MathArrays.buildArray(getField(), i + 1);
/*     */     }
/* 125 */     arrayOfRealFieldElement[0][0] = (RealFieldElement)fraction(2, 9);
/* 126 */     arrayOfRealFieldElement[1][0] = (RealFieldElement)fraction(1, 12);
/* 127 */     arrayOfRealFieldElement[1][1] = (RealFieldElement)fraction(1, 4);
/* 128 */     arrayOfRealFieldElement[2][0] = (RealFieldElement)fraction(1, 8);
/* 129 */     arrayOfRealFieldElement[2][1] = (RealFieldElement)getField().getZero();
/* 130 */     arrayOfRealFieldElement[2][2] = (RealFieldElement)fraction(3, 8);
/* 131 */     arrayOfRealFieldElement[3][0] = (RealFieldElement)fraction(91, 500);
/* 132 */     arrayOfRealFieldElement[3][1] = (RealFieldElement)fraction(-27, 100);
/* 133 */     arrayOfRealFieldElement[3][2] = (RealFieldElement)fraction(78, 125);
/* 134 */     arrayOfRealFieldElement[3][3] = (RealFieldElement)fraction(8, 125);
/* 135 */     arrayOfRealFieldElement[4][0] = (RealFieldElement)fraction(-11, 20);
/* 136 */     arrayOfRealFieldElement[4][1] = (RealFieldElement)fraction(27, 20);
/* 137 */     arrayOfRealFieldElement[4][2] = (RealFieldElement)fraction(12, 5);
/* 138 */     arrayOfRealFieldElement[4][3] = (RealFieldElement)fraction(-36, 5);
/* 139 */     arrayOfRealFieldElement[4][4] = (RealFieldElement)fraction(5, 1);
/* 140 */     arrayOfRealFieldElement[5][0] = (RealFieldElement)fraction(1, 12);
/* 141 */     arrayOfRealFieldElement[5][1] = (RealFieldElement)getField().getZero();
/* 142 */     arrayOfRealFieldElement[5][2] = (RealFieldElement)fraction(27, 32);
/* 143 */     arrayOfRealFieldElement[5][3] = (RealFieldElement)fraction(-4, 3);
/* 144 */     arrayOfRealFieldElement[5][4] = (RealFieldElement)fraction(125, 96);
/* 145 */     arrayOfRealFieldElement[5][5] = (RealFieldElement)fraction(5, 48);
/* 146 */     return (T[][])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] getB() {
/* 151 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 7);
/* 152 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(1, 12);
/* 153 */     arrayOfRealFieldElement[1] = (RealFieldElement)getField().getZero();
/* 154 */     arrayOfRealFieldElement[2] = (RealFieldElement)fraction(27, 32);
/* 155 */     arrayOfRealFieldElement[3] = (RealFieldElement)fraction(-4, 3);
/* 156 */     arrayOfRealFieldElement[4] = (RealFieldElement)fraction(125, 96);
/* 157 */     arrayOfRealFieldElement[5] = (RealFieldElement)fraction(5, 48);
/* 158 */     arrayOfRealFieldElement[6] = (RealFieldElement)getField().getZero();
/* 159 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HighamHall54FieldStepInterpolator<T> createInterpolator(boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> mapper) {
/* 168 */     return new HighamHall54FieldStepInterpolator<T>(getField(), forward, yDotK, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, mapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrder() {
/* 177 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T estimateError(T[][] yDotK, T[] y0, T[] y1, T h) {
/* 184 */     RealFieldElement realFieldElement = (RealFieldElement)getField().getZero();
/*     */     
/* 186 */     for (int j = 0; j < this.mainSetDimension; j++) {
/* 187 */       RealFieldElement realFieldElement1 = (RealFieldElement)yDotK[0][j].multiply(this.e[0]);
/* 188 */       for (int l = 1; l < this.e.length; l++) {
/* 189 */         realFieldElement1 = (RealFieldElement)realFieldElement1.add(yDotK[l][j].multiply(this.e[l]));
/*     */       }
/*     */       
/* 192 */       RealFieldElement realFieldElement2 = MathUtils.max((RealFieldElement)y0[j].abs(), (RealFieldElement)y1[j].abs());
/* 193 */       RealFieldElement realFieldElement3 = (this.vecAbsoluteTolerance == null) ? (RealFieldElement)((RealFieldElement)realFieldElement2.multiply(this.scalRelativeTolerance)).add(this.scalAbsoluteTolerance) : (RealFieldElement)((RealFieldElement)realFieldElement2.multiply(this.vecRelativeTolerance[j])).add(this.vecAbsoluteTolerance[j]);
/*     */ 
/*     */       
/* 196 */       RealFieldElement realFieldElement4 = (RealFieldElement)((RealFieldElement)h.multiply(realFieldElement1)).divide(realFieldElement3);
/* 197 */       realFieldElement = (RealFieldElement)realFieldElement.add(realFieldElement4.multiply(realFieldElement4));
/*     */     } 
/*     */ 
/*     */     
/* 201 */     return (T)((RealFieldElement)realFieldElement.divide(this.mainSetDimension)).sqrt();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/HighamHall54FieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */