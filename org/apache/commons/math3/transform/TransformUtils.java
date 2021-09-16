/*     */ package org.apache.commons.math3.transform;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.complex.Complex;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
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
/*     */ public class TransformUtils
/*     */ {
/*  37 */   private static final int[] POWERS_OF_TWO = new int[] { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] scaleArray(double[] f, double d) {
/*  61 */     for (int i = 0; i < f.length; i++) {
/*  62 */       f[i] = f[i] * d;
/*     */     }
/*  64 */     return f;
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
/*     */   public static Complex[] scaleArray(Complex[] f, double d) {
/*  77 */     for (int i = 0; i < f.length; i++) {
/*  78 */       f[i] = new Complex(d * f[i].getReal(), d * f[i].getImaginary());
/*     */     }
/*  80 */     return f;
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
/*     */   public static double[][] createRealImaginaryArray(Complex[] dataC) {
/*  98 */     double[][] dataRI = new double[2][dataC.length];
/*  99 */     double[] dataR = dataRI[0];
/* 100 */     double[] dataI = dataRI[1];
/* 101 */     for (int i = 0; i < dataC.length; i++) {
/* 102 */       Complex c = dataC[i];
/* 103 */       dataR[i] = c.getReal();
/* 104 */       dataI[i] = c.getImaginary();
/*     */     } 
/* 106 */     return dataRI;
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
/*     */   public static Complex[] createComplexArray(double[][] dataRI) throws DimensionMismatchException {
/* 126 */     if (dataRI.length != 2) {
/* 127 */       throw new DimensionMismatchException(dataRI.length, 2);
/*     */     }
/* 129 */     double[] dataR = dataRI[0];
/* 130 */     double[] dataI = dataRI[1];
/* 131 */     if (dataR.length != dataI.length) {
/* 132 */       throw new DimensionMismatchException(dataI.length, dataR.length);
/*     */     }
/*     */     
/* 135 */     int n = dataR.length;
/* 136 */     Complex[] c = new Complex[n];
/* 137 */     for (int i = 0; i < n; i++) {
/* 138 */       c[i] = new Complex(dataR[i], dataI[i]);
/*     */     }
/* 140 */     return c;
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
/*     */   public static int exactLog2(int n) throws MathIllegalArgumentException {
/* 155 */     int index = Arrays.binarySearch(POWERS_OF_TWO, n);
/* 156 */     if (index < 0) {
/* 157 */       throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, new Object[] { Integer.valueOf(n) });
/*     */     }
/*     */ 
/*     */     
/* 161 */     return index;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/transform/TransformUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */