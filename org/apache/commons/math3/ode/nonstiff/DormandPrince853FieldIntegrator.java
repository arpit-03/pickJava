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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DormandPrince853FieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends EmbeddedRungeKuttaFieldIntegrator<T>
/*     */ {
/*     */   private static final String METHOD_NAME = "Dormand-Prince 8 (5, 3)";
/*     */   private final T e1_01;
/*     */   private final T e1_06;
/*     */   private final T e1_07;
/*     */   private final T e1_08;
/*     */   private final T e1_09;
/*     */   private final T e1_10;
/*     */   private final T e1_11;
/*     */   private final T e1_12;
/*     */   private final T e2_01;
/*     */   private final T e2_06;
/*     */   private final T e2_07;
/*     */   private final T e2_08;
/*     */   private final T e2_09;
/*     */   private final T e2_10;
/*     */   private final T e2_11;
/*     */   private final T e2_12;
/*     */   
/*     */   public DormandPrince853FieldIntegrator(Field<T> field, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
/* 137 */     super(field, "Dormand-Prince 8 (5, 3)", 12, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */     
/* 139 */     this.e1_01 = fraction(1.16092271E8D, 8.84846592E9D);
/* 140 */     this.e1_06 = fraction(-1871647.0D, 1527680.0D);
/* 141 */     this.e1_07 = fraction(-6.9799717E7D, 1.4079366E8D);
/* 142 */     this.e1_08 = fraction(1.230164450203E12D, 7.39113984E11D);
/* 143 */     this.e1_09 = fraction(-1.980813971228885E15D, 5.654156025964544E15D);
/* 144 */     this.e1_10 = fraction(4.64500805E8D, 1.389975552E9D);
/* 145 */     this.e1_11 = fraction(1.606764981773E12D, 1.9613062656E13D);
/* 146 */     this.e1_12 = fraction(-137909.0D, 6168960.0D);
/* 147 */     this.e2_01 = fraction(-364463.0D, 1920240.0D);
/* 148 */     this.e2_06 = fraction(3399327.0D, 763840.0D);
/* 149 */     this.e2_07 = fraction(6.6578432E7D, 3.5198415E7D);
/* 150 */     this.e2_08 = fraction(-1.674902723E9D, 2.887164E8D);
/* 151 */     this.e2_09 = fraction(-7.4684743568175E13D, 1.76692375811392E14D);
/* 152 */     this.e2_10 = fraction(-734375.0D, 4826304.0D);
/* 153 */     this.e2_11 = fraction(1.71414593E8D, 8.512614E8D);
/* 154 */     this.e2_12 = fraction(69869.0D, 3084480.0D);
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
/*     */   public DormandPrince853FieldIntegrator(Field<T> field, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/* 173 */     super(field, "Dormand-Prince 8 (5, 3)", 12, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */     
/* 175 */     this.e1_01 = fraction(1.16092271E8D, 8.84846592E9D);
/* 176 */     this.e1_06 = fraction(-1871647.0D, 1527680.0D);
/* 177 */     this.e1_07 = fraction(-6.9799717E7D, 1.4079366E8D);
/* 178 */     this.e1_08 = fraction(1.230164450203E12D, 7.39113984E11D);
/* 179 */     this.e1_09 = fraction(-1.980813971228885E15D, 5.654156025964544E15D);
/* 180 */     this.e1_10 = fraction(4.64500805E8D, 1.389975552E9D);
/* 181 */     this.e1_11 = fraction(1.606764981773E12D, 1.9613062656E13D);
/* 182 */     this.e1_12 = fraction(-137909.0D, 6168960.0D);
/* 183 */     this.e2_01 = fraction(-364463.0D, 1920240.0D);
/* 184 */     this.e2_06 = fraction(3399327.0D, 763840.0D);
/* 185 */     this.e2_07 = fraction(6.6578432E7D, 3.5198415E7D);
/* 186 */     this.e2_08 = fraction(-1.674902723E9D, 2.887164E8D);
/* 187 */     this.e2_09 = fraction(-7.4684743568175E13D, 1.76692375811392E14D);
/* 188 */     this.e2_10 = fraction(-734375.0D, 4826304.0D);
/* 189 */     this.e2_11 = fraction(1.71414593E8D, 8.512614E8D);
/* 190 */     this.e2_12 = fraction(69869.0D, 3084480.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] getC() {
/* 196 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)getField().getOne()).multiply(6)).sqrt();
/*     */     
/* 198 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 15);
/* 199 */     arrayOfRealFieldElement[0] = (RealFieldElement)((RealFieldElement)realFieldElement.add(-6.0D)).divide(-67.5D);
/* 200 */     arrayOfRealFieldElement[1] = (RealFieldElement)((RealFieldElement)realFieldElement.add(-6.0D)).divide(-45.0D);
/* 201 */     arrayOfRealFieldElement[2] = (RealFieldElement)((RealFieldElement)realFieldElement.add(-6.0D)).divide(-30.0D);
/* 202 */     arrayOfRealFieldElement[3] = (RealFieldElement)((RealFieldElement)realFieldElement.add(6.0D)).divide(30.0D);
/* 203 */     arrayOfRealFieldElement[4] = (RealFieldElement)fraction(1, 3);
/* 204 */     arrayOfRealFieldElement[5] = (RealFieldElement)fraction(1, 4);
/* 205 */     arrayOfRealFieldElement[6] = (RealFieldElement)fraction(4, 13);
/* 206 */     arrayOfRealFieldElement[7] = (RealFieldElement)fraction(127, 195);
/* 207 */     arrayOfRealFieldElement[8] = (RealFieldElement)fraction(3, 5);
/* 208 */     arrayOfRealFieldElement[9] = (RealFieldElement)fraction(6, 7);
/* 209 */     arrayOfRealFieldElement[10] = (RealFieldElement)getField().getOne();
/* 210 */     arrayOfRealFieldElement[11] = (RealFieldElement)getField().getOne();
/* 211 */     arrayOfRealFieldElement[12] = (RealFieldElement)fraction(1.0D, 10.0D);
/* 212 */     arrayOfRealFieldElement[13] = (RealFieldElement)fraction(1.0D, 5.0D);
/* 213 */     arrayOfRealFieldElement[14] = (RealFieldElement)fraction(7.0D, 9.0D);
/*     */     
/* 215 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T[][] getA() {
/* 222 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)getField().getOne()).multiply(6)).sqrt();
/*     */     
/* 224 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), 15, -1);
/* 225 */     for (int i = 0; i < arrayOfRealFieldElement.length; i++) {
/* 226 */       arrayOfRealFieldElement[i] = (RealFieldElement[])MathArrays.buildArray(getField(), i + 1);
/*     */     }
/*     */     
/* 229 */     arrayOfRealFieldElement[0][0] = (RealFieldElement)((RealFieldElement)realFieldElement.add(-6.0D)).divide(-67.5D);
/*     */     
/* 231 */     arrayOfRealFieldElement[1][0] = (RealFieldElement)((RealFieldElement)realFieldElement.add(-6.0D)).divide(-180.0D);
/* 232 */     arrayOfRealFieldElement[1][1] = (RealFieldElement)((RealFieldElement)realFieldElement.add(-6.0D)).divide(-60.0D);
/*     */     
/* 234 */     arrayOfRealFieldElement[2][0] = (RealFieldElement)((RealFieldElement)realFieldElement.add(-6.0D)).divide(-120.0D);
/* 235 */     arrayOfRealFieldElement[2][1] = (RealFieldElement)getField().getZero();
/* 236 */     arrayOfRealFieldElement[2][2] = (RealFieldElement)((RealFieldElement)realFieldElement.add(-6.0D)).divide(-40.0D);
/*     */     
/* 238 */     arrayOfRealFieldElement[3][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(107)).add(462.0D)).divide(3000.0D);
/* 239 */     arrayOfRealFieldElement[3][1] = (RealFieldElement)getField().getZero();
/* 240 */     arrayOfRealFieldElement[3][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(197)).add(402.0D)).divide(-1000.0D);
/* 241 */     arrayOfRealFieldElement[3][3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(73)).add(168.0D)).divide(375.0D);
/*     */     
/* 243 */     arrayOfRealFieldElement[4][0] = (RealFieldElement)fraction(1, 27);
/* 244 */     arrayOfRealFieldElement[4][1] = (RealFieldElement)getField().getZero();
/* 245 */     arrayOfRealFieldElement[4][2] = (RealFieldElement)getField().getZero();
/* 246 */     arrayOfRealFieldElement[4][3] = (RealFieldElement)((RealFieldElement)realFieldElement.add(16.0D)).divide(108.0D);
/* 247 */     arrayOfRealFieldElement[4][4] = (RealFieldElement)((RealFieldElement)realFieldElement.add(-16.0D)).divide(-108.0D);
/*     */     
/* 249 */     arrayOfRealFieldElement[5][0] = (RealFieldElement)fraction(19, 512);
/* 250 */     arrayOfRealFieldElement[5][1] = (RealFieldElement)getField().getZero();
/* 251 */     arrayOfRealFieldElement[5][2] = (RealFieldElement)getField().getZero();
/* 252 */     arrayOfRealFieldElement[5][3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(23)).add(118.0D)).divide(1024.0D);
/* 253 */     arrayOfRealFieldElement[5][4] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-23)).add(118.0D)).divide(1024.0D);
/* 254 */     arrayOfRealFieldElement[5][5] = (RealFieldElement)fraction(-9, 512);
/*     */     
/* 256 */     arrayOfRealFieldElement[6][0] = (RealFieldElement)fraction(13772, 371293);
/* 257 */     arrayOfRealFieldElement[6][1] = (RealFieldElement)getField().getZero();
/* 258 */     arrayOfRealFieldElement[6][2] = (RealFieldElement)getField().getZero();
/* 259 */     arrayOfRealFieldElement[6][3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(4784)).add(51544.0D)).divide(371293.0D);
/* 260 */     arrayOfRealFieldElement[6][4] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-4784)).add(51544.0D)).divide(371293.0D);
/* 261 */     arrayOfRealFieldElement[6][5] = (RealFieldElement)fraction(-5688, 371293);
/* 262 */     arrayOfRealFieldElement[6][6] = (RealFieldElement)fraction(3072, 371293);
/*     */     
/* 264 */     arrayOfRealFieldElement[7][0] = (RealFieldElement)fraction(5.8656157643E10D, 9.3983540625E10D);
/* 265 */     arrayOfRealFieldElement[7][1] = (RealFieldElement)getField().getZero();
/* 266 */     arrayOfRealFieldElement[7][2] = (RealFieldElement)getField().getZero();
/* 267 */     arrayOfRealFieldElement[7][3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-3.18801444819E11D)).add(-1.324889724104E12D)).divide(6.265569375E11D);
/* 268 */     arrayOfRealFieldElement[7][4] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(3.18801444819E11D)).add(-1.324889724104E12D)).divide(6.265569375E11D);
/* 269 */     arrayOfRealFieldElement[7][5] = (RealFieldElement)fraction(9.6044563816E10D, 3.480871875E9D);
/* 270 */     arrayOfRealFieldElement[7][6] = (RealFieldElement)fraction(5.682451879168E12D, 2.81950621875E11D);
/* 271 */     arrayOfRealFieldElement[7][7] = (RealFieldElement)fraction(-1.65125654E8D, 3796875.0D);
/*     */     
/* 273 */     arrayOfRealFieldElement[8][0] = (RealFieldElement)fraction(8909899.0D, 1.8653125E7D);
/* 274 */     arrayOfRealFieldElement[8][1] = (RealFieldElement)getField().getZero();
/* 275 */     arrayOfRealFieldElement[8][2] = (RealFieldElement)getField().getZero();
/* 276 */     arrayOfRealFieldElement[8][3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-1137963.0D)).add(-4521408.0D)).divide(2937500.0D);
/* 277 */     arrayOfRealFieldElement[8][4] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(1137963.0D)).add(-4521408.0D)).divide(2937500.0D);
/* 278 */     arrayOfRealFieldElement[8][5] = (RealFieldElement)fraction(9.6663078E7D, 4553125.0D);
/* 279 */     arrayOfRealFieldElement[8][6] = (RealFieldElement)fraction(2.107245056E9D, 1.37915625E8D);
/* 280 */     arrayOfRealFieldElement[8][7] = (RealFieldElement)fraction(-4.913652016E9D, 1.47609375E8D);
/* 281 */     arrayOfRealFieldElement[8][8] = (RealFieldElement)fraction(-7.889427E7D, 3.880452869E9D);
/*     */     
/* 283 */     arrayOfRealFieldElement[9][0] = (RealFieldElement)fraction(-2.0401265806E10D, 2.1769653311E10D);
/* 284 */     arrayOfRealFieldElement[9][1] = (RealFieldElement)getField().getZero();
/* 285 */     arrayOfRealFieldElement[9][2] = (RealFieldElement)getField().getZero();
/* 286 */     arrayOfRealFieldElement[9][3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(94326.0D)).add(354216.0D)).divide(112847.0D);
/* 287 */     arrayOfRealFieldElement[9][4] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-94326.0D)).add(354216.0D)).divide(112847.0D);
/* 288 */     arrayOfRealFieldElement[9][5] = (RealFieldElement)fraction(-4.3306765128E10D, 5.313852383E9D);
/* 289 */     arrayOfRealFieldElement[9][6] = (RealFieldElement)fraction(-2.0866708358144E13D, 1.126708119789E12D);
/* 290 */     arrayOfRealFieldElement[9][7] = (RealFieldElement)fraction(1.488600343802E13D, 6.54632330667E11D);
/* 291 */     arrayOfRealFieldElement[9][8] = (RealFieldElement)fraction(3.5290686222309376E16D, 1.4152473387134412E16D);
/* 292 */     arrayOfRealFieldElement[9][9] = (RealFieldElement)fraction(-1.477884375E9D, 4.85066827E8D);
/*     */     
/* 294 */     arrayOfRealFieldElement[10][0] = (RealFieldElement)fraction(3.9815761E7D, 1.7514443E7D);
/* 295 */     arrayOfRealFieldElement[10][1] = (RealFieldElement)getField().getZero();
/* 296 */     arrayOfRealFieldElement[10][2] = (RealFieldElement)getField().getZero();
/* 297 */     arrayOfRealFieldElement[10][3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(-960905.0D)).add(-3457480.0D)).divide(551636.0D);
/* 298 */     arrayOfRealFieldElement[10][4] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.multiply(960905.0D)).add(-3457480.0D)).divide(551636.0D);
/* 299 */     arrayOfRealFieldElement[10][5] = (RealFieldElement)fraction(-8.44554132E8D, 4.7026969E7D);
/* 300 */     arrayOfRealFieldElement[10][6] = (RealFieldElement)fraction(8.444996352E9D, 3.02158619E8D);
/* 301 */     arrayOfRealFieldElement[10][7] = (RealFieldElement)fraction(-2.509602342E9D, 8.77790785E8D);
/* 302 */     arrayOfRealFieldElement[10][8] = (RealFieldElement)fraction(-2.8388795297996248E16D, 3.199510091356783E15D);
/* 303 */     arrayOfRealFieldElement[10][9] = (RealFieldElement)fraction(2.2671625E8D, 1.8341897E7D);
/* 304 */     arrayOfRealFieldElement[10][10] = (RealFieldElement)fraction(1.371316744E9D, 2.131383595E9D);
/*     */ 
/*     */ 
/*     */     
/* 308 */     arrayOfRealFieldElement[11][0] = (RealFieldElement)fraction(104257.0D, 1920240.0D);
/* 309 */     arrayOfRealFieldElement[11][1] = (RealFieldElement)getField().getZero();
/* 310 */     arrayOfRealFieldElement[11][2] = (RealFieldElement)getField().getZero();
/* 311 */     arrayOfRealFieldElement[11][3] = (RealFieldElement)getField().getZero();
/* 312 */     arrayOfRealFieldElement[11][4] = (RealFieldElement)getField().getZero();
/* 313 */     arrayOfRealFieldElement[11][5] = (RealFieldElement)fraction(3399327.0D, 763840.0D);
/* 314 */     arrayOfRealFieldElement[11][6] = (RealFieldElement)fraction(6.6578432E7D, 3.5198415E7D);
/* 315 */     arrayOfRealFieldElement[11][7] = (RealFieldElement)fraction(-1.674902723E9D, 2.887164E8D);
/* 316 */     arrayOfRealFieldElement[11][8] = (RealFieldElement)fraction(5.4980371265625E13D, 1.76692375811392E14D);
/* 317 */     arrayOfRealFieldElement[11][9] = (RealFieldElement)fraction(-734375.0D, 4826304.0D);
/* 318 */     arrayOfRealFieldElement[11][10] = (RealFieldElement)fraction(1.71414593E8D, 8.512614E8D);
/* 319 */     arrayOfRealFieldElement[11][11] = (RealFieldElement)fraction(137909.0D, 3084480.0D);
/*     */ 
/*     */     
/* 322 */     arrayOfRealFieldElement[12][0] = (RealFieldElement)fraction(1.3481885573E10D, 2.4003E11D);
/* 323 */     arrayOfRealFieldElement[12][1] = (RealFieldElement)getField().getZero();
/* 324 */     arrayOfRealFieldElement[12][2] = (RealFieldElement)getField().getZero();
/* 325 */     arrayOfRealFieldElement[12][3] = (RealFieldElement)getField().getZero();
/* 326 */     arrayOfRealFieldElement[12][4] = (RealFieldElement)getField().getZero();
/* 327 */     arrayOfRealFieldElement[12][5] = (RealFieldElement)getField().getZero();
/* 328 */     arrayOfRealFieldElement[12][6] = (RealFieldElement)fraction(1.39418837528E11D, 5.49975234375E11D);
/* 329 */     arrayOfRealFieldElement[12][7] = (RealFieldElement)fraction(-1.1108320068443E13D, 4.51119375E13D);
/* 330 */     arrayOfRealFieldElement[12][8] = (RealFieldElement)fraction(-1.769651421925959E15D, 1.424938514608E16D);
/* 331 */     arrayOfRealFieldElement[12][9] = (RealFieldElement)fraction(5.7799439E7D, 3.77055E8D);
/* 332 */     arrayOfRealFieldElement[12][10] = (RealFieldElement)fraction(7.93322643029E11D, 9.673425E13D);
/* 333 */     arrayOfRealFieldElement[12][11] = (RealFieldElement)fraction(1.458939311E9D, 1.9278E11D);
/* 334 */     arrayOfRealFieldElement[12][12] = (RealFieldElement)fraction(-4149.0D, 500000.0D);
/*     */     
/* 336 */     arrayOfRealFieldElement[13][0] = (RealFieldElement)fraction(1.595561272731E12D, 5.01202735E13D);
/* 337 */     arrayOfRealFieldElement[13][1] = (RealFieldElement)getField().getZero();
/* 338 */     arrayOfRealFieldElement[13][2] = (RealFieldElement)getField().getZero();
/* 339 */     arrayOfRealFieldElement[13][3] = (RealFieldElement)getField().getZero();
/* 340 */     arrayOfRealFieldElement[13][4] = (RealFieldElement)getField().getZero();
/* 341 */     arrayOfRealFieldElement[13][5] = (RealFieldElement)fraction(9.75183916491E11D, 3.445768803125E13D);
/* 342 */     arrayOfRealFieldElement[13][6] = (RealFieldElement)fraction(3.8492013932672E13D, 7.18912673015625E14D);
/* 343 */     arrayOfRealFieldElement[13][7] = (RealFieldElement)fraction(-1.114881286517557E15D, 2.02987107675E16D);
/* 344 */     arrayOfRealFieldElement[13][8] = (RealFieldElement)getField().getZero();
/* 345 */     arrayOfRealFieldElement[13][9] = (RealFieldElement)getField().getZero();
/* 346 */     arrayOfRealFieldElement[13][10] = (RealFieldElement)fraction(-2.538710946863E12D, 2.343122786125E16D);
/* 347 */     arrayOfRealFieldElement[13][11] = (RealFieldElement)fraction(8.824659001E9D, 2.306671678125E13D);
/* 348 */     arrayOfRealFieldElement[13][12] = (RealFieldElement)fraction(-1.1518334563E10D, 3.38311846125E13D);
/* 349 */     arrayOfRealFieldElement[13][13] = (RealFieldElement)fraction(1.912306948E9D, 1.3532473845E10D);
/*     */     
/* 351 */     arrayOfRealFieldElement[14][0] = (RealFieldElement)fraction(-1.3613986967E10D, 3.1741908048E10D);
/* 352 */     arrayOfRealFieldElement[14][1] = (RealFieldElement)getField().getZero();
/* 353 */     arrayOfRealFieldElement[14][2] = (RealFieldElement)getField().getZero();
/* 354 */     arrayOfRealFieldElement[14][3] = (RealFieldElement)getField().getZero();
/* 355 */     arrayOfRealFieldElement[14][4] = (RealFieldElement)getField().getZero();
/* 356 */     arrayOfRealFieldElement[14][5] = (RealFieldElement)fraction(-4.755612631E9D, 1.012344804E9D);
/* 357 */     arrayOfRealFieldElement[14][6] = (RealFieldElement)fraction(4.2939257944576E13D, 5.588559685701E12D);
/* 358 */     arrayOfRealFieldElement[14][7] = (RealFieldElement)fraction(7.7881972900277E13D, 1.9140370552944E13D);
/* 359 */     arrayOfRealFieldElement[14][8] = (RealFieldElement)fraction(2.2719829234375E13D, 6.3689648654052E13D);
/* 360 */     arrayOfRealFieldElement[14][9] = (RealFieldElement)getField().getZero();
/* 361 */     arrayOfRealFieldElement[14][10] = (RealFieldElement)getField().getZero();
/* 362 */     arrayOfRealFieldElement[14][11] = (RealFieldElement)getField().getZero();
/* 363 */     arrayOfRealFieldElement[14][12] = (RealFieldElement)fraction(-1.199007803E9D, 8.57031517296E11D);
/* 364 */     arrayOfRealFieldElement[14][13] = (RealFieldElement)fraction(1.57882067E11D, 5.3564469831E10D);
/* 365 */     arrayOfRealFieldElement[14][14] = (RealFieldElement)fraction(-2.90468882375E11D, 3.1741908048E10D);
/*     */     
/* 367 */     return (T[][])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] getB() {
/* 373 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 16);
/* 374 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(104257, 1920240);
/* 375 */     arrayOfRealFieldElement[1] = (RealFieldElement)getField().getZero();
/* 376 */     arrayOfRealFieldElement[2] = (RealFieldElement)getField().getZero();
/* 377 */     arrayOfRealFieldElement[3] = (RealFieldElement)getField().getZero();
/* 378 */     arrayOfRealFieldElement[4] = (RealFieldElement)getField().getZero();
/* 379 */     arrayOfRealFieldElement[5] = (RealFieldElement)fraction(3399327.0D, 763840.0D);
/* 380 */     arrayOfRealFieldElement[6] = (RealFieldElement)fraction(6.6578432E7D, 3.5198415E7D);
/* 381 */     arrayOfRealFieldElement[7] = (RealFieldElement)fraction(-1.674902723E9D, 2.887164E8D);
/* 382 */     arrayOfRealFieldElement[8] = (RealFieldElement)fraction(5.4980371265625E13D, 1.76692375811392E14D);
/* 383 */     arrayOfRealFieldElement[9] = (RealFieldElement)fraction(-734375.0D, 4826304.0D);
/* 384 */     arrayOfRealFieldElement[10] = (RealFieldElement)fraction(1.71414593E8D, 8.512614E8D);
/* 385 */     arrayOfRealFieldElement[11] = (RealFieldElement)fraction(137909.0D, 3084480.0D);
/* 386 */     arrayOfRealFieldElement[12] = (RealFieldElement)getField().getZero();
/* 387 */     arrayOfRealFieldElement[13] = (RealFieldElement)getField().getZero();
/* 388 */     arrayOfRealFieldElement[14] = (RealFieldElement)getField().getZero();
/* 389 */     arrayOfRealFieldElement[15] = (RealFieldElement)getField().getZero();
/* 390 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DormandPrince853FieldStepInterpolator<T> createInterpolator(boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> mapper) {
/* 399 */     return new DormandPrince853FieldStepInterpolator<T>(getField(), forward, yDotK, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, mapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrder() {
/* 408 */     return 8;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected T estimateError(T[][] yDotK, T[] y0, T[] y1, T h) {
/* 414 */     RealFieldElement realFieldElement1 = (RealFieldElement)h.getField().getZero();
/* 415 */     RealFieldElement realFieldElement2 = (RealFieldElement)h.getField().getZero();
/*     */     
/* 417 */     for (int j = 0; j < this.mainSetDimension; j++) {
/* 418 */       RealFieldElement realFieldElement4 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)yDotK[0][j].multiply(this.e1_01)).add(yDotK[5][j].multiply(this.e1_06))).add(yDotK[6][j].multiply(this.e1_07))).add(yDotK[7][j].multiply(this.e1_08))).add(yDotK[8][j].multiply(this.e1_09))).add(yDotK[9][j].multiply(this.e1_10))).add(yDotK[10][j].multiply(this.e1_11))).add(yDotK[11][j].multiply(this.e1_12));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 426 */       RealFieldElement realFieldElement5 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)yDotK[0][j].multiply(this.e2_01)).add(yDotK[5][j].multiply(this.e2_06))).add(yDotK[6][j].multiply(this.e2_07))).add(yDotK[7][j].multiply(this.e2_08))).add(yDotK[8][j].multiply(this.e2_09))).add(yDotK[9][j].multiply(this.e2_10))).add(yDotK[10][j].multiply(this.e2_11))).add(yDotK[11][j].multiply(this.e2_12));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 435 */       RealFieldElement realFieldElement6 = MathUtils.max((RealFieldElement)y0[j].abs(), (RealFieldElement)y1[j].abs());
/* 436 */       RealFieldElement realFieldElement7 = (this.vecAbsoluteTolerance == null) ? (RealFieldElement)((RealFieldElement)realFieldElement6.multiply(this.scalRelativeTolerance)).add(this.scalAbsoluteTolerance) : (RealFieldElement)((RealFieldElement)realFieldElement6.multiply(this.vecRelativeTolerance[j])).add(this.vecAbsoluteTolerance[j]);
/*     */ 
/*     */       
/* 439 */       RealFieldElement realFieldElement8 = (RealFieldElement)realFieldElement4.divide(realFieldElement7);
/* 440 */       realFieldElement1 = (RealFieldElement)realFieldElement1.add(realFieldElement8.multiply(realFieldElement8));
/* 441 */       RealFieldElement realFieldElement9 = (RealFieldElement)realFieldElement5.divide(realFieldElement7);
/* 442 */       realFieldElement2 = (RealFieldElement)realFieldElement2.add(realFieldElement9.multiply(realFieldElement9));
/*     */     } 
/*     */     
/* 445 */     RealFieldElement realFieldElement3 = (RealFieldElement)realFieldElement1.add(realFieldElement2.multiply(0.01D));
/* 446 */     if (realFieldElement3.getReal() <= 0.0D) {
/* 447 */       realFieldElement3 = (RealFieldElement)h.getField().getOne();
/*     */     }
/*     */     
/* 450 */     return (T)((RealFieldElement)((RealFieldElement)h.abs()).multiply(realFieldElement1)).divide(((RealFieldElement)realFieldElement3.multiply(this.mainSetDimension)).sqrt());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/DormandPrince853FieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */