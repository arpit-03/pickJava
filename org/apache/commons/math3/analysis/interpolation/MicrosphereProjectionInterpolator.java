/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MicrosphereProjectionInterpolator
/*     */   implements MultivariateInterpolator
/*     */ {
/*     */   private final double exponent;
/*     */   private final InterpolatingMicrosphere microsphere;
/*     */   private final boolean sharedSphere;
/*     */   private final double noInterpolationTolerance;
/*     */   
/*     */   public MicrosphereProjectionInterpolator(int dimension, int elements, double maxDarkFraction, double darkThreshold, double background, double exponent, boolean sharedSphere, double noInterpolationTolerance) {
/*  80 */     this(new InterpolatingMicrosphere(dimension, elements, maxDarkFraction, darkThreshold, background, new UnitSphereRandomVectorGenerator(dimension)), exponent, sharedSphere, noInterpolationTolerance);
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
/*     */   public MicrosphereProjectionInterpolator(InterpolatingMicrosphere microsphere, double exponent, boolean sharedSphere, double noInterpolationTolerance) throws NotPositiveException {
/* 111 */     if (exponent < 0.0D) {
/* 112 */       throw new NotPositiveException(Double.valueOf(exponent));
/*     */     }
/*     */     
/* 115 */     this.microsphere = microsphere;
/* 116 */     this.exponent = exponent;
/* 117 */     this.sharedSphere = sharedSphere;
/* 118 */     this.noInterpolationTolerance = noInterpolationTolerance;
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
/*     */   public MultivariateFunction interpolate(final double[][] xval, final double[] yval) throws DimensionMismatchException, NoDataException, NullArgumentException {
/* 132 */     if (xval == null || yval == null)
/*     */     {
/* 134 */       throw new NullArgumentException();
/*     */     }
/* 136 */     if (xval.length == 0) {
/* 137 */       throw new NoDataException();
/*     */     }
/* 139 */     if (xval.length != yval.length) {
/* 140 */       throw new DimensionMismatchException(xval.length, yval.length);
/*     */     }
/* 142 */     if (xval[0] == null) {
/* 143 */       throw new NullArgumentException();
/*     */     }
/* 145 */     int dimension = this.microsphere.getDimension();
/* 146 */     if (dimension != (xval[0]).length) {
/* 147 */       throw new DimensionMismatchException((xval[0]).length, dimension);
/*     */     }
/*     */ 
/*     */     
/* 151 */     final InterpolatingMicrosphere m = this.sharedSphere ? this.microsphere : this.microsphere.copy();
/*     */     
/* 153 */     return new MultivariateFunction()
/*     */       {
/*     */         public double value(double[] point) {
/* 156 */           return m.value(point, xval, yval, MicrosphereProjectionInterpolator.this.exponent, MicrosphereProjectionInterpolator.this.noInterpolationTolerance);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/MicrosphereProjectionInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */