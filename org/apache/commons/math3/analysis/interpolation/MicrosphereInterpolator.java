/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.random.UnitSphereRandomVectorGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class MicrosphereInterpolator
/*     */   implements MultivariateInterpolator
/*     */ {
/*     */   public static final int DEFAULT_MICROSPHERE_ELEMENTS = 2000;
/*     */   public static final int DEFAULT_BRIGHTNESS_EXPONENT = 2;
/*     */   private final int microsphereElements;
/*     */   private final int brightnessExponent;
/*     */   
/*     */   public MicrosphereInterpolator() {
/*  65 */     this(2000, 2);
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
/*     */   public MicrosphereInterpolator(int elements, int exponent) throws NotPositiveException, NotStrictlyPositiveException {
/*  79 */     if (exponent < 0) {
/*  80 */       throw new NotPositiveException(Integer.valueOf(exponent));
/*     */     }
/*  82 */     if (elements <= 0) {
/*  83 */       throw new NotStrictlyPositiveException(Integer.valueOf(elements));
/*     */     }
/*     */     
/*  86 */     this.microsphereElements = elements;
/*  87 */     this.brightnessExponent = exponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultivariateFunction interpolate(double[][] xval, double[] yval) throws DimensionMismatchException, NoDataException, NullArgumentException {
/*  98 */     UnitSphereRandomVectorGenerator rand = new UnitSphereRandomVectorGenerator((xval[0]).length);
/*     */     
/* 100 */     return new MicrosphereInterpolatingFunction(xval, yval, this.brightnessExponent, this.microsphereElements, rand);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/MicrosphereInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */