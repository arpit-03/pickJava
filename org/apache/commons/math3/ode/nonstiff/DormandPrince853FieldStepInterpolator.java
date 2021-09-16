/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
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
/*     */ class DormandPrince853FieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldStepInterpolator<T>
/*     */ {
/*     */   private final T[][] d;
/*     */   
/*     */   DormandPrince853FieldStepInterpolator(Field<T> field, boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> mapper) {
/*  62 */     super(field, forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
/*     */ 
/*     */ 
/*     */     
/*  66 */     this.d = (T[][])MathArrays.buildArray(field, 7, 16);
/*     */ 
/*     */     
/*  69 */     this.d[0][0] = fraction(field, 104257.0D, 1920240.0D);
/*  70 */     this.d[0][1] = (T)field.getZero();
/*  71 */     this.d[0][2] = (T)field.getZero();
/*  72 */     this.d[0][3] = (T)field.getZero();
/*  73 */     this.d[0][4] = (T)field.getZero();
/*  74 */     this.d[0][5] = fraction(field, 3399327.0D, 763840.0D);
/*  75 */     this.d[0][6] = fraction(field, 6.6578432E7D, 3.5198415E7D);
/*  76 */     this.d[0][7] = fraction(field, -1.674902723E9D, 2.887164E8D);
/*  77 */     this.d[0][8] = fraction(field, 5.4980371265625E13D, 1.76692375811392E14D);
/*  78 */     this.d[0][9] = fraction(field, -734375.0D, 4826304.0D);
/*  79 */     this.d[0][10] = fraction(field, 1.71414593E8D, 8.512614E8D);
/*  80 */     this.d[0][11] = fraction(field, 137909.0D, 3084480.0D);
/*  81 */     this.d[0][12] = (T)field.getZero();
/*  82 */     this.d[0][13] = (T)field.getZero();
/*  83 */     this.d[0][14] = (T)field.getZero();
/*  84 */     this.d[0][15] = (T)field.getZero();
/*     */     
/*  86 */     this.d[1][0] = (T)((RealFieldElement)this.d[0][0].negate()).add(1.0D);
/*  87 */     this.d[1][1] = (T)this.d[0][1].negate();
/*  88 */     this.d[1][2] = (T)this.d[0][2].negate();
/*  89 */     this.d[1][3] = (T)this.d[0][3].negate();
/*  90 */     this.d[1][4] = (T)this.d[0][4].negate();
/*  91 */     this.d[1][5] = (T)this.d[0][5].negate();
/*  92 */     this.d[1][6] = (T)this.d[0][6].negate();
/*  93 */     this.d[1][7] = (T)this.d[0][7].negate();
/*  94 */     this.d[1][8] = (T)this.d[0][8].negate();
/*  95 */     this.d[1][9] = (T)this.d[0][9].negate();
/*  96 */     this.d[1][10] = (T)this.d[0][10].negate();
/*  97 */     this.d[1][11] = (T)this.d[0][11].negate();
/*  98 */     this.d[1][12] = (T)this.d[0][12].negate();
/*  99 */     this.d[1][13] = (T)this.d[0][13].negate();
/* 100 */     this.d[1][14] = (T)this.d[0][14].negate();
/* 101 */     this.d[1][15] = (T)this.d[0][15].negate();
/*     */     
/* 103 */     this.d[2][0] = (T)((RealFieldElement)this.d[0][0].multiply(2)).subtract(1.0D);
/* 104 */     this.d[2][1] = (T)this.d[0][1].multiply(2);
/* 105 */     this.d[2][2] = (T)this.d[0][2].multiply(2);
/* 106 */     this.d[2][3] = (T)this.d[0][3].multiply(2);
/* 107 */     this.d[2][4] = (T)this.d[0][4].multiply(2);
/* 108 */     this.d[2][5] = (T)this.d[0][5].multiply(2);
/* 109 */     this.d[2][6] = (T)this.d[0][6].multiply(2);
/* 110 */     this.d[2][7] = (T)this.d[0][7].multiply(2);
/* 111 */     this.d[2][8] = (T)this.d[0][8].multiply(2);
/* 112 */     this.d[2][9] = (T)this.d[0][9].multiply(2);
/* 113 */     this.d[2][10] = (T)this.d[0][10].multiply(2);
/* 114 */     this.d[2][11] = (T)this.d[0][11].multiply(2);
/* 115 */     this.d[2][12] = (T)((RealFieldElement)this.d[0][12].multiply(2)).subtract(1.0D);
/* 116 */     this.d[2][13] = (T)this.d[0][13].multiply(2);
/* 117 */     this.d[2][14] = (T)this.d[0][14].multiply(2);
/* 118 */     this.d[2][15] = (T)this.d[0][15].multiply(2);
/*     */     
/* 120 */     this.d[3][0] = fraction(field, -1.7751989329E10D, 2.10607656E9D);
/* 121 */     this.d[3][1] = (T)field.getZero();
/* 122 */     this.d[3][2] = (T)field.getZero();
/* 123 */     this.d[3][3] = (T)field.getZero();
/* 124 */     this.d[3][4] = (T)field.getZero();
/* 125 */     this.d[3][5] = fraction(field, 4.272954039E9D, 7.53986464E9D);
/* 126 */     this.d[3][6] = fraction(field, -1.18476319744E11D, 3.8604839385E10D);
/* 127 */     this.d[3][7] = fraction(field, 7.55123450731E11D, 3.166577316E11D);
/* 128 */     this.d[3][8] = fraction(field, 3.6923844612348283E18D, 1.7441304416342505E18D);
/* 129 */     this.d[3][9] = fraction(field, -4.612609375E9D, 5.293382976E9D);
/* 130 */     this.d[3][10] = fraction(field, 2.091772278379E12D, 9.336445866E11D);
/* 131 */     this.d[3][11] = fraction(field, 2.136624137E9D, 3.38298912E9D);
/* 132 */     this.d[3][12] = fraction(field, -126493.0D, 1421424.0D);
/* 133 */     this.d[3][13] = fraction(field, 9.835E7D, 5419179.0D);
/* 134 */     this.d[3][14] = fraction(field, -1.8878125E7D, 2053168.0D);
/* 135 */     this.d[3][15] = fraction(field, -1.944542619E9D, 4.38351368E8D);
/*     */     
/* 137 */     this.d[4][0] = fraction(field, 3.2941697297E10D, 3.15911484E9D);
/* 138 */     this.d[4][1] = (T)field.getZero();
/* 139 */     this.d[4][2] = (T)field.getZero();
/* 140 */     this.d[4][3] = (T)field.getZero();
/* 141 */     this.d[4][4] = (T)field.getZero();
/* 142 */     this.d[4][5] = fraction(field, 4.56696183123E11D, 1.88496616E9D);
/* 143 */     this.d[4][6] = fraction(field, 1.9132610714624E13D, 1.15814518155E11D);
/* 144 */     this.d[4][7] = fraction(field, -1.77904688592943E14D, 4.749865974E11D);
/* 145 */     this.d[4][8] = fraction(field, -4.8211399418367652E18D, 2.18016305204281312E17D);
/* 146 */     this.d[4][9] = fraction(field, 3.0702015625E10D, 3.970037232E9D);
/* 147 */     this.d[4][10] = fraction(field, -8.5916079474274E13D, 2.8009337598E12D);
/* 148 */     this.d[4][11] = fraction(field, -5.919468007E9D, 6.3431046E8D);
/* 149 */     this.d[4][12] = fraction(field, 2479159.0D, 157936.0D);
/* 150 */     this.d[4][13] = fraction(field, -1.875E7D, 602131.0D);
/* 151 */     this.d[4][14] = fraction(field, -1.9203125E7D, 2053168.0D);
/* 152 */     this.d[4][15] = fraction(field, 1.5700361463E10D, 4.38351368E8D);
/*     */     
/* 154 */     this.d[5][0] = fraction(field, 1.2627015655E10D, 6.31822968E8D);
/* 155 */     this.d[5][1] = (T)field.getZero();
/* 156 */     this.d[5][2] = (T)field.getZero();
/* 157 */     this.d[5][3] = (T)field.getZero();
/* 158 */     this.d[5][4] = (T)field.getZero();
/* 159 */     this.d[5][5] = fraction(field, -7.2955222965E10D, 1.88496616E8D);
/* 160 */     this.d[5][6] = fraction(field, -1.314574495232E13D, 6.9488710893E10D);
/* 161 */     this.d[5][7] = fraction(field, 3.0084216194513E13D, 5.6998391688E10D);
/* 162 */     this.d[5][8] = fraction(field, -2.9685876100664064E17D, 2.5648977082856624E16D);
/* 163 */     this.d[5][9] = fraction(field, 5.69140625E8D, 8.2709109E7D);
/* 164 */     this.d[5][10] = fraction(field, -1.8684190637E10D, 1.8672891732E10D);
/* 165 */     this.d[5][11] = fraction(field, 6.9644045E7D, 8.9549712E7D);
/* 166 */     this.d[5][12] = fraction(field, -1.1847025E7D, 4264272.0D);
/* 167 */     this.d[5][13] = fraction(field, -9.7865E8D, 1.6257537E7D);
/* 168 */     this.d[5][14] = fraction(field, 5.19371875E8D, 6159504.0D);
/* 169 */     this.d[5][15] = fraction(field, 5.256837225E9D, 4.38351368E8D);
/*     */     
/* 171 */     this.d[6][0] = fraction(field, -4.50944925E8D, 1.7550638E7D);
/* 172 */     this.d[6][1] = (T)field.getZero();
/* 173 */     this.d[6][2] = (T)field.getZero();
/* 174 */     this.d[6][3] = (T)field.getZero();
/* 175 */     this.d[6][4] = (T)field.getZero();
/* 176 */     this.d[6][5] = fraction(field, -1.4532122925E10D, 9.4248308E7D);
/* 177 */     this.d[6][6] = fraction(field, -5.958769664E11D, 2.573655959E9D);
/* 178 */     this.d[6][7] = fraction(field, 1.88748653015E11D, 5.27762886E8D);
/* 179 */     this.d[6][8] = fraction(field, 2.5454854581152343E18D, 2.7252038150535164E16D);
/* 180 */     this.d[6][9] = fraction(field, -1.376953125E9D, 3.6759604E7D);
/* 181 */     this.d[6][10] = fraction(field, 5.3995596795E10D, 5.18691437E8D);
/* 182 */     this.d[6][11] = fraction(field, 2.10311225E8D, 7047894.0D);
/* 183 */     this.d[6][12] = fraction(field, -1718875.0D, 39484.0D);
/* 184 */     this.d[6][13] = fraction(field, 5.8E7D, 602131.0D);
/* 185 */     this.d[6][14] = fraction(field, -1546875.0D, 39484.0D);
/* 186 */     this.d[6][15] = fraction(field, -1.262172375E9D, 8429834.0D);
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
/*     */   protected DormandPrince853FieldStepInterpolator<T> create(Field<T> newField, boolean newForward, T[][] newYDotK, FieldODEStateAndDerivative<T> newGlobalPreviousState, FieldODEStateAndDerivative<T> newGlobalCurrentState, FieldODEStateAndDerivative<T> newSoftPreviousState, FieldODEStateAndDerivative<T> newSoftCurrentState, FieldEquationsMapper<T> newMapper) {
/* 198 */     return new DormandPrince853FieldStepInterpolator(newField, newForward, newYDotK, newGlobalPreviousState, newGlobalCurrentState, newSoftPreviousState, newSoftCurrentState, newMapper);
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
/*     */   private T fraction(Field<T> field, double p, double q) {
/* 211 */     return (T)((RealFieldElement)((RealFieldElement)field.getZero()).add(p)).divide(q);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FieldODEStateAndDerivative<T> computeInterpolatedStateAndDerivatives(FieldEquationsMapper<T> mapper, T time, T theta, T thetaH, T oneMinusThetaH) throws MaxCountExceededException {
/*     */     T[] interpolatedState, interpolatedDerivatives;
/* 222 */     RealFieldElement realFieldElement1 = (RealFieldElement)time.getField().getOne();
/* 223 */     RealFieldElement realFieldElement2 = (RealFieldElement)realFieldElement1.subtract(theta);
/* 224 */     RealFieldElement realFieldElement3 = (RealFieldElement)theta.multiply(2);
/* 225 */     RealFieldElement realFieldElement4 = (RealFieldElement)theta.multiply(theta);
/* 226 */     RealFieldElement realFieldElement5 = (RealFieldElement)realFieldElement1.subtract(realFieldElement3);
/* 227 */     RealFieldElement realFieldElement6 = (RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-3)).add(2.0D));
/* 228 */     RealFieldElement realFieldElement7 = (RealFieldElement)realFieldElement3.multiply(((RealFieldElement)theta.multiply(realFieldElement3.subtract(3.0D))).add(1.0D));
/* 229 */     RealFieldElement realFieldElement8 = (RealFieldElement)realFieldElement4.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(5)).subtract(8.0D))).add(3.0D));
/* 230 */     RealFieldElement realFieldElement9 = (RealFieldElement)realFieldElement4.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-6)).add(15.0D))).subtract(12.0D))).add(3.0D));
/* 231 */     RealFieldElement realFieldElement10 = (RealFieldElement)realFieldElement4.multiply(theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-7)).add(18.0D))).subtract(15.0D))).add(4.0D)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     if (getGlobalPreviousState() != null && theta.getReal() <= 0.5D) {
/* 237 */       T f0 = thetaH;
/* 238 */       RealFieldElement realFieldElement11 = (RealFieldElement)f0.multiply(realFieldElement2);
/* 239 */       RealFieldElement realFieldElement12 = (RealFieldElement)realFieldElement11.multiply(theta);
/* 240 */       RealFieldElement realFieldElement13 = (RealFieldElement)realFieldElement12.multiply(realFieldElement2);
/* 241 */       RealFieldElement realFieldElement14 = (RealFieldElement)realFieldElement13.multiply(theta);
/* 242 */       RealFieldElement realFieldElement15 = (RealFieldElement)realFieldElement14.multiply(realFieldElement2);
/* 243 */       RealFieldElement realFieldElement16 = (RealFieldElement)realFieldElement15.multiply(theta);
/* 244 */       RealFieldElement[] arrayOfRealFieldElement1 = (RealFieldElement[])MathArrays.buildArray(time.getField(), 16);
/* 245 */       RealFieldElement[] arrayOfRealFieldElement2 = (RealFieldElement[])MathArrays.buildArray(time.getField(), 16);
/* 246 */       for (int i = 0; i < arrayOfRealFieldElement1.length; i++) {
/* 247 */         arrayOfRealFieldElement1[i] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)f0.multiply(this.d[0][i])).add(realFieldElement11.multiply(this.d[1][i]))).add(realFieldElement12.multiply(this.d[2][i]))).add(realFieldElement13.multiply(this.d[3][i]))).add(realFieldElement14.multiply(this.d[4][i]))).add(realFieldElement15.multiply(this.d[5][i]))).add(realFieldElement16.multiply(this.d[6][i]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 254 */         arrayOfRealFieldElement2[i] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.d[0][i].add(realFieldElement5.multiply(this.d[1][i]))).add(realFieldElement6.multiply(this.d[2][i]))).add(realFieldElement7.multiply(this.d[3][i]))).add(realFieldElement8.multiply(this.d[4][i]))).add(realFieldElement9.multiply(this.d[5][i]))).add(realFieldElement10.multiply(this.d[6][i]));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 262 */       interpolatedState = previousStateLinearCombination((T[])new RealFieldElement[] { arrayOfRealFieldElement1[0], arrayOfRealFieldElement1[1], arrayOfRealFieldElement1[2], arrayOfRealFieldElement1[3], arrayOfRealFieldElement1[4], arrayOfRealFieldElement1[5], arrayOfRealFieldElement1[6], arrayOfRealFieldElement1[7], arrayOfRealFieldElement1[8], arrayOfRealFieldElement1[9], arrayOfRealFieldElement1[10], arrayOfRealFieldElement1[11], arrayOfRealFieldElement1[12], arrayOfRealFieldElement1[13], arrayOfRealFieldElement1[14], arrayOfRealFieldElement1[15] });
/*     */       
/* 264 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { arrayOfRealFieldElement2[0], arrayOfRealFieldElement2[1], arrayOfRealFieldElement2[2], arrayOfRealFieldElement2[3], arrayOfRealFieldElement2[4], arrayOfRealFieldElement2[5], arrayOfRealFieldElement2[6], arrayOfRealFieldElement2[7], arrayOfRealFieldElement2[8], arrayOfRealFieldElement2[9], arrayOfRealFieldElement2[10], arrayOfRealFieldElement2[11], arrayOfRealFieldElement2[12], arrayOfRealFieldElement2[13], arrayOfRealFieldElement2[14], arrayOfRealFieldElement2[15] });
/*     */     } else {
/*     */       
/* 267 */       RealFieldElement realFieldElement11 = (RealFieldElement)oneMinusThetaH.negate();
/* 268 */       RealFieldElement realFieldElement12 = (RealFieldElement)((RealFieldElement)realFieldElement11.multiply(theta)).negate();
/* 269 */       RealFieldElement realFieldElement13 = (RealFieldElement)realFieldElement12.multiply(theta);
/* 270 */       RealFieldElement realFieldElement14 = (RealFieldElement)realFieldElement13.multiply(realFieldElement2);
/* 271 */       RealFieldElement realFieldElement15 = (RealFieldElement)realFieldElement14.multiply(theta);
/* 272 */       RealFieldElement realFieldElement16 = (RealFieldElement)realFieldElement15.multiply(realFieldElement2);
/* 273 */       RealFieldElement realFieldElement17 = (RealFieldElement)realFieldElement16.multiply(theta);
/* 274 */       RealFieldElement[] arrayOfRealFieldElement1 = (RealFieldElement[])MathArrays.buildArray(time.getField(), 16);
/* 275 */       RealFieldElement[] arrayOfRealFieldElement2 = (RealFieldElement[])MathArrays.buildArray(time.getField(), 16);
/* 276 */       for (int i = 0; i < arrayOfRealFieldElement1.length; i++) {
/* 277 */         arrayOfRealFieldElement1[i] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement11.multiply(this.d[0][i])).add(realFieldElement12.multiply(this.d[1][i]))).add(realFieldElement13.multiply(this.d[2][i]))).add(realFieldElement14.multiply(this.d[3][i]))).add(realFieldElement15.multiply(this.d[4][i]))).add(realFieldElement16.multiply(this.d[5][i]))).add(realFieldElement17.multiply(this.d[6][i]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 284 */         arrayOfRealFieldElement2[i] = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.d[0][i].add(realFieldElement5.multiply(this.d[1][i]))).add(realFieldElement6.multiply(this.d[2][i]))).add(realFieldElement7.multiply(this.d[3][i]))).add(realFieldElement8.multiply(this.d[4][i]))).add(realFieldElement9.multiply(this.d[5][i]))).add(realFieldElement10.multiply(this.d[6][i]));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       interpolatedState = currentStateLinearCombination((T[])new RealFieldElement[] { arrayOfRealFieldElement1[0], arrayOfRealFieldElement1[1], arrayOfRealFieldElement1[2], arrayOfRealFieldElement1[3], arrayOfRealFieldElement1[4], arrayOfRealFieldElement1[5], arrayOfRealFieldElement1[6], arrayOfRealFieldElement1[7], arrayOfRealFieldElement1[8], arrayOfRealFieldElement1[9], arrayOfRealFieldElement1[10], arrayOfRealFieldElement1[11], arrayOfRealFieldElement1[12], arrayOfRealFieldElement1[13], arrayOfRealFieldElement1[14], arrayOfRealFieldElement1[15] });
/*     */       
/* 294 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { arrayOfRealFieldElement2[0], arrayOfRealFieldElement2[1], arrayOfRealFieldElement2[2], arrayOfRealFieldElement2[3], arrayOfRealFieldElement2[4], arrayOfRealFieldElement2[5], arrayOfRealFieldElement2[6], arrayOfRealFieldElement2[7], arrayOfRealFieldElement2[8], arrayOfRealFieldElement2[9], arrayOfRealFieldElement2[10], arrayOfRealFieldElement2[11], arrayOfRealFieldElement2[12], arrayOfRealFieldElement2[13], arrayOfRealFieldElement2[14], arrayOfRealFieldElement2[15] });
/*     */     } 
/*     */ 
/*     */     
/* 298 */     return new FieldODEStateAndDerivative((RealFieldElement)time, (RealFieldElement[])interpolatedState, (RealFieldElement[])interpolatedDerivatives);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/DormandPrince853FieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */