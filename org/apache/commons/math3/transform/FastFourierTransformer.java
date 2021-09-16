/*     */ package org.apache.commons.math3.transform;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import org.apache.commons.math3.analysis.FunctionUtils;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.complex.Complex;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.ArithmeticUtils;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public class FastFourierTransformer
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 20120210L;
/*  63 */   private static final double[] W_SUB_N_R = new double[] { 1.0D, -1.0D, 6.123233995736766E-17D, 0.7071067811865476D, 0.9238795325112867D, 0.9807852804032304D, 0.9951847266721969D, 0.9987954562051724D, 0.9996988186962042D, 0.9999247018391445D, 0.9999811752826011D, 0.9999952938095762D, 0.9999988234517019D, 0.9999997058628822D, 0.9999999264657179D, 0.9999999816164293D, 0.9999999954041073D, 0.9999999988510269D, 0.9999999997127567D, 0.9999999999281892D, 0.9999999999820472D, 0.9999999999955118D, 0.999999999998878D, 0.9999999999997194D, 0.9999999999999298D, 0.9999999999999825D, 0.9999999999999957D, 0.9999999999999989D, 0.9999999999999998D, 0.9999999999999999D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private static final double[] W_SUB_N_I = new double[] { 2.4492935982947064E-16D, -1.2246467991473532E-16D, -1.0D, -0.7071067811865475D, -0.3826834323650898D, -0.19509032201612825D, -0.0980171403295606D, -0.049067674327418015D, -0.024541228522912288D, -0.012271538285719925D, -0.006135884649154475D, -0.003067956762965976D, -0.0015339801862847655D, -7.669903187427045E-4D, -3.8349518757139556E-4D, -1.917475973107033E-4D, -9.587379909597734E-5D, -4.793689960306688E-5D, -2.396844980841822E-5D, -1.1984224905069705E-5D, -5.9921124526424275E-6D, -2.996056226334661E-6D, -1.4980281131690111E-6D, -7.490140565847157E-7D, -3.7450702829238413E-7D, -1.8725351414619535E-7D, -9.362675707309808E-8D, -4.681337853654909E-8D, -2.340668926827455E-8D, -1.1703344634137277E-8D, -5.8516723170686385E-9D, -2.9258361585343192E-9D, -1.4629180792671596E-9D, -7.314590396335798E-10D, -3.657295198167899E-10D, -1.8286475990839495E-10D, -9.143237995419748E-11D, -4.571618997709874E-11D, -2.285809498854937E-11D, -1.1429047494274685E-11D, -5.714523747137342E-12D, -2.857261873568671E-12D, -1.4286309367843356E-12D, -7.143154683921678E-13D, -3.571577341960839E-13D, -1.7857886709804195E-13D, -8.928943354902097E-14D, -4.4644716774510487E-14D, -2.2322358387255243E-14D, -1.1161179193627622E-14D, -5.580589596813811E-15D, -2.7902947984069054E-15D, -1.3951473992034527E-15D, -6.975736996017264E-16D, -3.487868498008632E-16D, -1.743934249004316E-16D, -8.71967124502158E-17D, -4.35983562251079E-17D, -2.179917811255395E-17D, -1.0899589056276974E-17D, -5.449794528138487E-18D, -2.7248972640692436E-18D, -1.3624486320346218E-18D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DftNormalization normalization;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastFourierTransformer(DftNormalization normalization) {
/* 115 */     this.normalization = normalization;
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
/*     */   private static void bitReversalShuffle2(double[] a, double[] b) {
/* 129 */     int n = a.length;
/* 130 */     assert b.length == n;
/* 131 */     int halfOfN = n >> 1;
/*     */     
/* 133 */     int j = 0;
/* 134 */     for (int i = 0; i < n; i++) {
/* 135 */       if (i < j) {
/*     */         
/* 137 */         double temp = a[i];
/* 138 */         a[i] = a[j];
/* 139 */         a[j] = temp;
/*     */         
/* 141 */         temp = b[i];
/* 142 */         b[i] = b[j];
/* 143 */         b[j] = temp;
/*     */       } 
/*     */       
/* 146 */       int k = halfOfN;
/* 147 */       while (k <= j && k > 0) {
/* 148 */         j -= k;
/* 149 */         k >>= 1;
/*     */       } 
/* 151 */       j += k;
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
/*     */   private static void normalizeTransformedData(double[][] dataRI, DftNormalization normalization, TransformType type) {
/*     */     double scaleFactor;
/*     */     int i;
/* 165 */     double[] dataR = dataRI[0];
/* 166 */     double[] dataI = dataRI[1];
/* 167 */     int n = dataR.length;
/* 168 */     assert dataI.length == n;
/*     */     
/* 170 */     switch (normalization) {
/*     */       case STANDARD:
/* 172 */         if (type == TransformType.INVERSE) {
/* 173 */           double d = 1.0D / n;
/* 174 */           for (int j = 0; j < n; j++) {
/* 175 */             dataR[j] = dataR[j] * d;
/* 176 */             dataI[j] = dataI[j] * d;
/*     */           } 
/*     */         } 
/*     */         return;
/*     */       case UNITARY:
/* 181 */         scaleFactor = 1.0D / FastMath.sqrt(n);
/* 182 */         for (i = 0; i < n; i++) {
/* 183 */           dataR[i] = dataR[i] * scaleFactor;
/* 184 */           dataI[i] = dataI[i] * scaleFactor;
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     throw new MathIllegalStateException();
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
/*     */   public static void transformInPlace(double[][] dataRI, DftNormalization normalization, TransformType type) {
/* 217 */     if (dataRI.length != 2) {
/* 218 */       throw new DimensionMismatchException(dataRI.length, 2);
/*     */     }
/* 220 */     double[] dataR = dataRI[0];
/* 221 */     double[] dataI = dataRI[1];
/* 222 */     if (dataR.length != dataI.length) {
/* 223 */       throw new DimensionMismatchException(dataI.length, dataR.length);
/*     */     }
/*     */     
/* 226 */     int n = dataR.length;
/* 227 */     if (!ArithmeticUtils.isPowerOfTwo(n)) {
/* 228 */       throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, new Object[] { Integer.valueOf(n) });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 233 */     if (n == 1)
/*     */       return; 
/* 235 */     if (n == 2) {
/* 236 */       double srcR0 = dataR[0];
/* 237 */       double srcI0 = dataI[0];
/* 238 */       double srcR1 = dataR[1];
/* 239 */       double srcI1 = dataI[1];
/*     */ 
/*     */       
/* 242 */       dataR[0] = srcR0 + srcR1;
/* 243 */       dataI[0] = srcI0 + srcI1;
/*     */       
/* 245 */       dataR[1] = srcR0 - srcR1;
/* 246 */       dataI[1] = srcI0 - srcI1;
/*     */       
/* 248 */       normalizeTransformedData(dataRI, normalization, type);
/*     */       
/*     */       return;
/*     */     } 
/* 252 */     bitReversalShuffle2(dataR, dataI);
/*     */ 
/*     */     
/* 255 */     if (type == TransformType.INVERSE) {
/* 256 */       for (int i0 = 0; i0 < n; i0 += 4) {
/* 257 */         int i1 = i0 + 1;
/* 258 */         int i2 = i0 + 2;
/* 259 */         int i3 = i0 + 3;
/*     */         
/* 261 */         double srcR0 = dataR[i0];
/* 262 */         double srcI0 = dataI[i0];
/* 263 */         double srcR1 = dataR[i2];
/* 264 */         double srcI1 = dataI[i2];
/* 265 */         double srcR2 = dataR[i1];
/* 266 */         double srcI2 = dataI[i1];
/* 267 */         double srcR3 = dataR[i3];
/* 268 */         double srcI3 = dataI[i3];
/*     */ 
/*     */ 
/*     */         
/* 272 */         dataR[i0] = srcR0 + srcR1 + srcR2 + srcR3;
/* 273 */         dataI[i0] = srcI0 + srcI1 + srcI2 + srcI3;
/*     */         
/* 275 */         dataR[i1] = srcR0 - srcR2 + srcI3 - srcI1;
/* 276 */         dataI[i1] = srcI0 - srcI2 + srcR1 - srcR3;
/*     */         
/* 278 */         dataR[i2] = srcR0 - srcR1 + srcR2 - srcR3;
/* 279 */         dataI[i2] = srcI0 - srcI1 + srcI2 - srcI3;
/*     */         
/* 281 */         dataR[i3] = srcR0 - srcR2 + srcI1 - srcI3;
/* 282 */         dataI[i3] = srcI0 - srcI2 + srcR3 - srcR1;
/*     */       } 
/*     */     } else {
/* 285 */       for (int i0 = 0; i0 < n; i0 += 4) {
/* 286 */         int i1 = i0 + 1;
/* 287 */         int i2 = i0 + 2;
/* 288 */         int i3 = i0 + 3;
/*     */         
/* 290 */         double srcR0 = dataR[i0];
/* 291 */         double srcI0 = dataI[i0];
/* 292 */         double srcR1 = dataR[i2];
/* 293 */         double srcI1 = dataI[i2];
/* 294 */         double srcR2 = dataR[i1];
/* 295 */         double srcI2 = dataI[i1];
/* 296 */         double srcR3 = dataR[i3];
/* 297 */         double srcI3 = dataI[i3];
/*     */ 
/*     */ 
/*     */         
/* 301 */         dataR[i0] = srcR0 + srcR1 + srcR2 + srcR3;
/* 302 */         dataI[i0] = srcI0 + srcI1 + srcI2 + srcI3;
/*     */         
/* 304 */         dataR[i1] = srcR0 - srcR2 + srcI1 - srcI3;
/* 305 */         dataI[i1] = srcI0 - srcI2 + srcR3 - srcR1;
/*     */         
/* 307 */         dataR[i2] = srcR0 - srcR1 + srcR2 - srcR3;
/* 308 */         dataI[i2] = srcI0 - srcI1 + srcI2 - srcI3;
/*     */         
/* 310 */         dataR[i3] = srcR0 - srcR2 + srcI3 - srcI1;
/* 311 */         dataI[i3] = srcI0 - srcI2 + srcR1 - srcR3;
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     int lastN0 = 4;
/* 316 */     int lastLogN0 = 2;
/* 317 */     while (lastN0 < n) {
/* 318 */       int n0 = lastN0 << 1;
/* 319 */       int logN0 = lastLogN0 + 1;
/* 320 */       double wSubN0R = W_SUB_N_R[logN0];
/* 321 */       double wSubN0I = W_SUB_N_I[logN0];
/* 322 */       if (type == TransformType.INVERSE) {
/* 323 */         wSubN0I = -wSubN0I;
/*     */       }
/*     */       
/*     */       int destEvenStartIndex;
/* 327 */       for (destEvenStartIndex = 0; destEvenStartIndex < n; destEvenStartIndex += n0) {
/* 328 */         int destOddStartIndex = destEvenStartIndex + lastN0;
/*     */         
/* 330 */         double wSubN0ToRR = 1.0D;
/* 331 */         double wSubN0ToRI = 0.0D;
/*     */         
/* 333 */         for (int r = 0; r < lastN0; r++) {
/* 334 */           double grR = dataR[destEvenStartIndex + r];
/* 335 */           double grI = dataI[destEvenStartIndex + r];
/* 336 */           double hrR = dataR[destOddStartIndex + r];
/* 337 */           double hrI = dataI[destOddStartIndex + r];
/*     */ 
/*     */           
/* 340 */           dataR[destEvenStartIndex + r] = grR + wSubN0ToRR * hrR - wSubN0ToRI * hrI;
/* 341 */           dataI[destEvenStartIndex + r] = grI + wSubN0ToRR * hrI + wSubN0ToRI * hrR;
/*     */           
/* 343 */           dataR[destOddStartIndex + r] = grR - wSubN0ToRR * hrR - wSubN0ToRI * hrI;
/* 344 */           dataI[destOddStartIndex + r] = grI - wSubN0ToRR * hrI + wSubN0ToRI * hrR;
/*     */ 
/*     */           
/* 347 */           double nextWsubN0ToRR = wSubN0ToRR * wSubN0R - wSubN0ToRI * wSubN0I;
/* 348 */           double nextWsubN0ToRI = wSubN0ToRR * wSubN0I + wSubN0ToRI * wSubN0R;
/* 349 */           wSubN0ToRR = nextWsubN0ToRR;
/* 350 */           wSubN0ToRI = nextWsubN0ToRI;
/*     */         } 
/*     */       } 
/*     */       
/* 354 */       lastN0 = n0;
/* 355 */       lastLogN0 = logN0;
/*     */     } 
/*     */     
/* 358 */     normalizeTransformedData(dataRI, normalization, type);
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
/*     */   public Complex[] transform(double[] f, TransformType type) {
/* 370 */     double[][] dataRI = { MathArrays.copyOf(f, f.length), new double[f.length] };
/*     */ 
/*     */ 
/*     */     
/* 374 */     transformInPlace(dataRI, this.normalization, type);
/*     */     
/* 376 */     return TransformUtils.createComplexArray(dataRI);
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
/*     */   public Complex[] transform(UnivariateFunction f, double min, double max, int n, TransformType type) {
/* 400 */     double[] data = FunctionUtils.sample(f, min, max, n);
/* 401 */     return transform(data, type);
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
/*     */   public Complex[] transform(Complex[] f, TransformType type) {
/* 413 */     double[][] dataRI = TransformUtils.createRealImaginaryArray(f);
/*     */     
/* 415 */     transformInPlace(dataRI, this.normalization, type);
/*     */     
/* 417 */     return TransformUtils.createComplexArray(dataRI);
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
/*     */   @Deprecated
/*     */   public Object mdfft(Object mdca, TransformType type) {
/* 437 */     MultiDimensionalComplexMatrix mdcm = (MultiDimensionalComplexMatrix)(new MultiDimensionalComplexMatrix(mdca)).clone();
/*     */     
/* 439 */     int[] dimensionSize = mdcm.getDimensionSizes();
/*     */     
/* 441 */     for (int i = 0; i < dimensionSize.length; i++) {
/* 442 */       mdfft(mdcm, type, i, new int[0]);
/*     */     }
/* 444 */     return mdcm.getArray();
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
/*     */   @Deprecated
/*     */   private void mdfft(MultiDimensionalComplexMatrix mdcm, TransformType type, int d, int[] subVector) {
/* 461 */     int[] dimensionSize = mdcm.getDimensionSizes();
/*     */     
/* 463 */     if (subVector.length == dimensionSize.length) {
/* 464 */       Complex[] temp = new Complex[dimensionSize[d]]; int i;
/* 465 */       for (i = 0; i < dimensionSize[d]; i++) {
/*     */         
/* 467 */         subVector[d] = i;
/* 468 */         temp[i] = mdcm.get(subVector);
/*     */       } 
/*     */       
/* 471 */       temp = transform(temp, type);
/*     */       
/* 473 */       for (i = 0; i < dimensionSize[d]; i++) {
/* 474 */         subVector[d] = i;
/* 475 */         mdcm.set(temp[i], subVector);
/*     */       } 
/*     */     } else {
/* 478 */       int[] vector = new int[subVector.length + 1];
/* 479 */       System.arraycopy(subVector, 0, vector, 0, subVector.length);
/* 480 */       if (subVector.length == d) {
/*     */ 
/*     */         
/* 483 */         vector[d] = 0;
/* 484 */         mdfft(mdcm, type, d, vector);
/*     */       } else {
/* 486 */         for (int i = 0; i < dimensionSize[subVector.length]; i++) {
/* 487 */           vector[subVector.length] = i;
/*     */           
/* 489 */           mdfft(mdcm, type, d, vector);
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
/*     */   @Deprecated
/*     */   private static class MultiDimensionalComplexMatrix
/*     */     implements Cloneable
/*     */   {
/*     */     protected int[] dimensionSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object multiDimensionalComplexArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MultiDimensionalComplexMatrix(Object multiDimensionalComplexArray) {
/* 521 */       this.multiDimensionalComplexArray = multiDimensionalComplexArray;
/*     */ 
/*     */       
/* 524 */       int numOfDimensions = 0;
/* 525 */       Object lastDimension = multiDimensionalComplexArray;
/* 526 */       while (lastDimension instanceof Object[]) {
/* 527 */         Object[] array = (Object[])lastDimension;
/* 528 */         numOfDimensions++;
/* 529 */         lastDimension = array[0];
/*     */       } 
/*     */ 
/*     */       
/* 533 */       this.dimensionSize = new int[numOfDimensions];
/*     */ 
/*     */       
/* 536 */       numOfDimensions = 0;
/* 537 */       lastDimension = multiDimensionalComplexArray;
/* 538 */       while (lastDimension instanceof Object[]) {
/* 539 */         Object[] array = (Object[])lastDimension;
/* 540 */         this.dimensionSize[numOfDimensions++] = array.length;
/* 541 */         lastDimension = array[0];
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Complex get(int... vector) throws DimensionMismatchException {
/* 556 */       if (vector == null) {
/* 557 */         if (this.dimensionSize.length > 0) {
/* 558 */           throw new DimensionMismatchException(0, this.dimensionSize.length);
/*     */         }
/*     */ 
/*     */         
/* 562 */         return null;
/*     */       } 
/* 564 */       if (vector.length != this.dimensionSize.length) {
/* 565 */         throw new DimensionMismatchException(vector.length, this.dimensionSize.length);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 570 */       Object lastDimension = this.multiDimensionalComplexArray;
/*     */       
/* 572 */       for (int i = 0; i < this.dimensionSize.length; i++) {
/* 573 */         lastDimension = ((Object[])lastDimension)[vector[i]];
/*     */       }
/* 575 */       return (Complex)lastDimension;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Complex set(Complex magnitude, int... vector) throws DimensionMismatchException {
/* 589 */       if (vector == null) {
/* 590 */         if (this.dimensionSize.length > 0) {
/* 591 */           throw new DimensionMismatchException(0, this.dimensionSize.length);
/*     */         }
/*     */ 
/*     */         
/* 595 */         return null;
/*     */       } 
/* 597 */       if (vector.length != this.dimensionSize.length) {
/* 598 */         throw new DimensionMismatchException(vector.length, this.dimensionSize.length);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 603 */       Object[] lastDimension = (Object[])this.multiDimensionalComplexArray;
/* 604 */       for (int i = 0; i < this.dimensionSize.length - 1; i++) {
/* 605 */         lastDimension = (Object[])lastDimension[vector[i]];
/*     */       }
/*     */       
/* 608 */       Complex lastValue = (Complex)lastDimension[vector[this.dimensionSize.length - 1]];
/* 609 */       lastDimension[vector[this.dimensionSize.length - 1]] = magnitude;
/*     */       
/* 611 */       return lastValue;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int[] getDimensionSizes() {
/* 620 */       return (int[])this.dimensionSize.clone();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getArray() {
/* 629 */       return this.multiDimensionalComplexArray;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 635 */       MultiDimensionalComplexMatrix mdcm = new MultiDimensionalComplexMatrix(Array.newInstance(Complex.class, this.dimensionSize));
/*     */ 
/*     */       
/* 638 */       clone(mdcm);
/* 639 */       return mdcm;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void clone(MultiDimensionalComplexMatrix mdcm) {
/* 649 */       int[] vector = new int[this.dimensionSize.length];
/* 650 */       int size = 1;
/* 651 */       for (int i = 0; i < this.dimensionSize.length; i++) {
/* 652 */         size *= this.dimensionSize[i];
/*     */       }
/* 654 */       int[][] vectorList = new int[size][this.dimensionSize.length];
/* 655 */       for (int[] nextVector : vectorList) {
/* 656 */         System.arraycopy(vector, 0, nextVector, 0, this.dimensionSize.length);
/*     */         
/* 658 */         for (int j = 0; j < this.dimensionSize.length; j++) {
/* 659 */           vector[j] = vector[j] + 1;
/* 660 */           if (vector[j] < this.dimensionSize[j]) {
/*     */             break;
/*     */           }
/* 663 */           vector[j] = 0;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 668 */       for (int[] nextVector : vectorList)
/* 669 */         mdcm.set(get(nextVector), nextVector); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/transform/FastFourierTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */