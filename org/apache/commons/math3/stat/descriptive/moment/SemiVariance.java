/*     */ package org.apache.commons.math3.stat.descriptive.moment;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.stat.descriptive.AbstractUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.UnivariateStatistic;
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
/*     */ public class SemiVariance
/*     */   extends AbstractUnivariateStatistic
/*     */   implements Serializable
/*     */ {
/*  60 */   public static final Direction UPSIDE_VARIANCE = Direction.UPSIDE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static final Direction DOWNSIDE_VARIANCE = Direction.DOWNSIDE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -2653430366886024994L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean biasCorrected = true;
/*     */ 
/*     */ 
/*     */   
/*  80 */   private Direction varianceDirection = Direction.DOWNSIDE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SemiVariance(boolean biasCorrected) {
/*  98 */     this.biasCorrected = biasCorrected;
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
/*     */   public SemiVariance(Direction direction) {
/* 110 */     this.varianceDirection = direction;
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
/*     */   public SemiVariance(boolean corrected, Direction direction) {
/* 126 */     this.biasCorrected = corrected;
/* 127 */     this.varianceDirection = direction;
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
/*     */   public SemiVariance(SemiVariance original) throws NullArgumentException {
/* 139 */     copy(original, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SemiVariance copy() {
/* 148 */     SemiVariance result = new SemiVariance();
/*     */     
/* 150 */     copy(this, result);
/* 151 */     return result;
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
/*     */   public static void copy(SemiVariance source, SemiVariance dest) throws NullArgumentException {
/* 165 */     MathUtils.checkNotNull(source);
/* 166 */     MathUtils.checkNotNull(dest);
/* 167 */     dest.setData(source.getDataRef());
/* 168 */     dest.biasCorrected = source.biasCorrected;
/* 169 */     dest.varianceDirection = source.varianceDirection;
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
/*     */   public double evaluate(double[] values, int start, int length) throws MathIllegalArgumentException {
/* 189 */     double m = (new Mean()).evaluate(values, start, length);
/* 190 */     return evaluate(values, m, this.varianceDirection, this.biasCorrected, 0, values.length);
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
/*     */   public double evaluate(double[] values, Direction direction) throws MathIllegalArgumentException {
/* 206 */     double m = (new Mean()).evaluate(values);
/* 207 */     return evaluate(values, m, direction, this.biasCorrected, 0, values.length);
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
/*     */   public double evaluate(double[] values, double cutoff) throws MathIllegalArgumentException {
/* 224 */     return evaluate(values, cutoff, this.varianceDirection, this.biasCorrected, 0, values.length);
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
/*     */   public double evaluate(double[] values, double cutoff, Direction direction) throws MathIllegalArgumentException {
/* 242 */     return evaluate(values, cutoff, direction, this.biasCorrected, 0, values.length);
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
/*     */   public double evaluate(double[] values, double cutoff, Direction direction, boolean corrected, int start, int length) throws MathIllegalArgumentException {
/* 266 */     test(values, start, length);
/* 267 */     if (values.length == 0) {
/* 268 */       return Double.NaN;
/*     */     }
/* 270 */     if (values.length == 1) {
/* 271 */       return 0.0D;
/*     */     }
/* 273 */     boolean booleanDirection = direction.getDirection();
/*     */     
/* 275 */     double dev = 0.0D;
/* 276 */     double sumsq = 0.0D;
/* 277 */     for (int i = start; i < length; i++) {
/* 278 */       if (((values[i] > cutoff)) == booleanDirection) {
/* 279 */         dev = values[i] - cutoff;
/* 280 */         sumsq += dev * dev;
/*     */       } 
/*     */     } 
/*     */     
/* 284 */     if (corrected) {
/* 285 */       return sumsq / (length - 1.0D);
/*     */     }
/* 287 */     return sumsq / length;
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
/*     */   public boolean isBiasCorrected() {
/* 299 */     return this.biasCorrected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiasCorrected(boolean biasCorrected) {
/* 308 */     this.biasCorrected = biasCorrected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Direction getVarianceDirection() {
/* 317 */     return this.varianceDirection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVarianceDirection(Direction varianceDirection) {
/* 326 */     this.varianceDirection = varianceDirection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SemiVariance() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Direction
/*     */   {
/* 338 */     UPSIDE(true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 344 */     DOWNSIDE(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean direction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Direction(boolean b) {
/* 357 */       this.direction = b;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean getDirection() {
/* 366 */       return this.direction;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/SemiVariance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */