/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.random.UnitSphereRandomVectorGenerator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InterpolatingMicrosphere
/*     */ {
/*     */   private final List<Facet> microsphere;
/*     */   private final List<FacetData> microsphereData;
/*     */   private final int dimension;
/*     */   private final int size;
/*     */   private final double maxDarkFraction;
/*     */   private final double darkThreshold;
/*     */   private final double background;
/*     */   
/*     */   protected InterpolatingMicrosphere(int dimension, int size, double maxDarkFraction, double darkThreshold, double background) {
/*  77 */     if (dimension <= 0) {
/*  78 */       throw new NotStrictlyPositiveException(Integer.valueOf(dimension));
/*     */     }
/*  80 */     if (size <= 0) {
/*  81 */       throw new NotStrictlyPositiveException(Integer.valueOf(size));
/*     */     }
/*  83 */     if (maxDarkFraction < 0.0D || maxDarkFraction > 1.0D)
/*     */     {
/*  85 */       throw new OutOfRangeException(Double.valueOf(maxDarkFraction), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*  87 */     if (darkThreshold < 0.0D) {
/*  88 */       throw new NotPositiveException(Double.valueOf(darkThreshold));
/*     */     }
/*     */     
/*  91 */     this.dimension = dimension;
/*  92 */     this.size = size;
/*  93 */     this.maxDarkFraction = maxDarkFraction;
/*  94 */     this.darkThreshold = darkThreshold;
/*  95 */     this.background = background;
/*  96 */     this.microsphere = new ArrayList<Facet>(size);
/*  97 */     this.microsphereData = new ArrayList<FacetData>(size);
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
/*     */   public InterpolatingMicrosphere(int dimension, int size, double maxDarkFraction, double darkThreshold, double background, UnitSphereRandomVectorGenerator rand) {
/* 128 */     this(dimension, size, maxDarkFraction, darkThreshold, background);
/*     */ 
/*     */ 
/*     */     
/* 132 */     for (int i = 0; i < size; i++) {
/* 133 */       add(rand.nextVector(), false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InterpolatingMicrosphere(InterpolatingMicrosphere other) {
/* 143 */     this.dimension = other.dimension;
/* 144 */     this.size = other.size;
/* 145 */     this.maxDarkFraction = other.maxDarkFraction;
/* 146 */     this.darkThreshold = other.darkThreshold;
/* 147 */     this.background = other.background;
/*     */ 
/*     */     
/* 150 */     this.microsphere = other.microsphere;
/*     */ 
/*     */     
/* 153 */     this.microsphereData = new ArrayList<FacetData>(this.size);
/* 154 */     for (FacetData fd : other.microsphereData) {
/* 155 */       this.microsphereData.add(new FacetData(fd.illumination(), fd.sample()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InterpolatingMicrosphere copy() {
/* 165 */     return new InterpolatingMicrosphere(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 174 */     return this.dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 183 */     return this.size;
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
/*     */   public double value(double[] point, double[][] samplePoints, double[] sampleValues, double exponent, double noInterpolationTolerance) {
/* 211 */     if (exponent < 0.0D) {
/* 212 */       throw new NotPositiveException(Double.valueOf(exponent));
/*     */     }
/*     */     
/* 215 */     clear();
/*     */ 
/*     */ 
/*     */     
/* 219 */     int numSamples = samplePoints.length;
/* 220 */     for (int i = 0; i < numSamples; i++) {
/*     */       
/* 222 */       double[] diff = MathArrays.ebeSubtract(samplePoints[i], point);
/* 223 */       double diffNorm = MathArrays.safeNorm(diff);
/*     */       
/* 225 */       if (FastMath.abs(diffNorm) < noInterpolationTolerance)
/*     */       {
/*     */         
/* 228 */         return sampleValues[i];
/*     */       }
/*     */       
/* 231 */       double weight = FastMath.pow(diffNorm, -exponent);
/* 232 */       illuminate(diff, sampleValues[i], weight);
/*     */     } 
/*     */     
/* 235 */     return interpolate();
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
/*     */   protected void add(double[] normal, boolean copy) {
/* 251 */     if (this.microsphere.size() >= this.size) {
/* 252 */       throw new MaxCountExceededException(Integer.valueOf(this.size));
/*     */     }
/* 254 */     if (normal.length > this.dimension) {
/* 255 */       throw new DimensionMismatchException(normal.length, this.dimension);
/*     */     }
/*     */     
/* 258 */     this.microsphere.add(new Facet(copy ? (double[])normal.clone() : normal));
/* 259 */     this.microsphereData.add(new FacetData(0.0D, 0.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double interpolate() {
/* 270 */     int darkCount = 0;
/*     */     
/* 272 */     double value = 0.0D;
/* 273 */     double totalWeight = 0.0D;
/* 274 */     for (FacetData fd : this.microsphereData) {
/* 275 */       double iV = fd.illumination();
/* 276 */       if (iV != 0.0D) {
/* 277 */         value += iV * fd.sample();
/* 278 */         totalWeight += iV; continue;
/*     */       } 
/* 280 */       darkCount++;
/*     */     } 
/*     */ 
/*     */     
/* 284 */     double darkFraction = darkCount / this.size;
/*     */     
/* 286 */     return (darkFraction <= this.maxDarkFraction) ? (value / totalWeight) : this.background;
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
/*     */   private void illuminate(double[] sampleDirection, double sampleValue, double weight) {
/* 302 */     for (int i = 0; i < this.size; i++) {
/* 303 */       double[] n = ((Facet)this.microsphere.get(i)).getNormal();
/* 304 */       double cos = MathArrays.cosAngle(n, sampleDirection);
/*     */       
/* 306 */       if (cos > 0.0D) {
/* 307 */         double illumination = cos * weight;
/*     */         
/* 309 */         if (illumination > this.darkThreshold && illumination > ((FacetData)this.microsphereData.get(i)).illumination())
/*     */         {
/* 311 */           this.microsphereData.set(i, new FacetData(illumination, sampleValue));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void clear() {
/* 321 */     for (int i = 0; i < this.size; i++) {
/* 322 */       this.microsphereData.set(i, new FacetData(0.0D, 0.0D));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Facet
/*     */   {
/*     */     private final double[] normal;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Facet(double[] n) {
/* 338 */       this.normal = n;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] getNormal() {
/* 347 */       return this.normal;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FacetData
/*     */   {
/*     */     private final double illumination;
/*     */ 
/*     */ 
/*     */     
/*     */     private final double sample;
/*     */ 
/*     */ 
/*     */     
/*     */     FacetData(double illumination, double sample) {
/* 365 */       this.illumination = illumination;
/* 366 */       this.sample = sample;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double illumination() {
/* 374 */       return this.illumination;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double sample() {
/* 382 */       return this.sample;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/InterpolatingMicrosphere.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */