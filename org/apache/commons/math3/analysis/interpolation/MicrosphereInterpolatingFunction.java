/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.random.UnitSphereRandomVectorGenerator;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class MicrosphereInterpolatingFunction
/*     */   implements MultivariateFunction
/*     */ {
/*     */   private final int dimension;
/*     */   private final List<MicrosphereSurfaceElement> microsphere;
/*     */   private final double brightnessExponent;
/*     */   private final Map<RealVector, Double> samples;
/*     */   
/*     */   private static class MicrosphereSurfaceElement
/*     */   {
/*     */     private final RealVector normal;
/*     */     private double brightestIllumination;
/*     */     private Map.Entry<RealVector, Double> brightestSample;
/*     */     
/*     */     MicrosphereSurfaceElement(double[] n) {
/*  80 */       this.normal = (RealVector)new ArrayRealVector(n);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     RealVector normal() {
/*  88 */       return this.normal;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void reset() {
/*  95 */       this.brightestIllumination = 0.0D;
/*  96 */       this.brightestSample = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void store(double illuminationFromSample, Map.Entry<RealVector, Double> sample) {
/* 106 */       if (illuminationFromSample > this.brightestIllumination) {
/* 107 */         this.brightestIllumination = illuminationFromSample;
/* 108 */         this.brightestSample = sample;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     double illumination() {
/* 117 */       return this.brightestIllumination;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Map.Entry<RealVector, Double> sample() {
/* 125 */       return this.brightestSample;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MicrosphereInterpolatingFunction(double[][] xval, double[] yval, int brightnessExponent, int microsphereElements, UnitSphereRandomVectorGenerator rand) throws DimensionMismatchException, NoDataException, NullArgumentException {
/* 156 */     if (xval == null || yval == null)
/*     */     {
/* 158 */       throw new NullArgumentException();
/*     */     }
/* 160 */     if (xval.length == 0) {
/* 161 */       throw new NoDataException();
/*     */     }
/* 163 */     if (xval.length != yval.length) {
/* 164 */       throw new DimensionMismatchException(xval.length, yval.length);
/*     */     }
/* 166 */     if (xval[0] == null) {
/* 167 */       throw new NullArgumentException();
/*     */     }
/*     */     
/* 170 */     this.dimension = (xval[0]).length;
/* 171 */     this.brightnessExponent = brightnessExponent;
/*     */ 
/*     */     
/* 174 */     this.samples = new HashMap<RealVector, Double>(yval.length); int i;
/* 175 */     for (i = 0; i < xval.length; i++) {
/* 176 */       double[] xvalI = xval[i];
/* 177 */       if (xvalI == null) {
/* 178 */         throw new NullArgumentException();
/*     */       }
/* 180 */       if (xvalI.length != this.dimension) {
/* 181 */         throw new DimensionMismatchException(xvalI.length, this.dimension);
/*     */       }
/*     */       
/* 184 */       this.samples.put(new ArrayRealVector(xvalI), Double.valueOf(yval[i]));
/*     */     } 
/*     */     
/* 187 */     this.microsphere = new ArrayList<MicrosphereSurfaceElement>(microsphereElements);
/*     */ 
/*     */     
/* 190 */     for (i = 0; i < microsphereElements; i++) {
/* 191 */       this.microsphere.add(new MicrosphereSurfaceElement(rand.nextVector()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double value(double[] point) throws DimensionMismatchException {
/* 201 */     ArrayRealVector arrayRealVector = new ArrayRealVector(point);
/*     */ 
/*     */     
/* 204 */     for (MicrosphereSurfaceElement md : this.microsphere) {
/* 205 */       md.reset();
/*     */     }
/*     */ 
/*     */     
/* 209 */     for (Map.Entry<RealVector, Double> sd : this.samples.entrySet()) {
/*     */ 
/*     */       
/* 212 */       RealVector diff = ((RealVector)sd.getKey()).subtract((RealVector)arrayRealVector);
/* 213 */       double diffNorm = diff.getNorm();
/*     */       
/* 215 */       if (FastMath.abs(diffNorm) < FastMath.ulp(1.0D))
/*     */       {
/*     */         
/* 218 */         return ((Double)sd.getValue()).doubleValue();
/*     */       }
/*     */       
/* 221 */       for (MicrosphereSurfaceElement md : this.microsphere) {
/* 222 */         double w = FastMath.pow(diffNorm, -this.brightnessExponent);
/* 223 */         md.store(cosAngle(diff, md.normal()) * w, sd);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 229 */     double value = 0.0D;
/* 230 */     double totalWeight = 0.0D;
/* 231 */     for (MicrosphereSurfaceElement md : this.microsphere) {
/* 232 */       double iV = md.illumination();
/* 233 */       Map.Entry<RealVector, Double> sd = md.sample();
/* 234 */       if (sd != null) {
/* 235 */         value += iV * ((Double)sd.getValue()).doubleValue();
/* 236 */         totalWeight += iV;
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     return value / totalWeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double cosAngle(RealVector v, RealVector w) {
/* 251 */     return v.dotProduct(w) / v.getNorm() * w.getNorm();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/MicrosphereInterpolatingFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */