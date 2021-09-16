/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ public class StableRandomGenerator
/*     */   implements NormalizedRandomGenerator
/*     */ {
/*     */   private final RandomGenerator generator;
/*     */   private final double alpha;
/*     */   private final double beta;
/*     */   private final double zeta;
/*     */   
/*     */   public StableRandomGenerator(RandomGenerator generator, double alpha, double beta) throws NullArgumentException, OutOfRangeException {
/*  60 */     if (generator == null) {
/*  61 */       throw new NullArgumentException();
/*     */     }
/*     */     
/*  64 */     if (alpha <= 0.0D || alpha > 2.0D) {
/*  65 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_LEFT, Double.valueOf(alpha), Integer.valueOf(0), Integer.valueOf(2));
/*     */     }
/*     */ 
/*     */     
/*  69 */     if (beta < -1.0D || beta > 1.0D) {
/*  70 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_SIMPLE, Double.valueOf(beta), Integer.valueOf(-1), Integer.valueOf(1));
/*     */     }
/*     */ 
/*     */     
/*  74 */     this.generator = generator;
/*  75 */     this.alpha = alpha;
/*  76 */     this.beta = beta;
/*  77 */     if (alpha < 2.0D && beta != 0.0D) {
/*  78 */       this.zeta = beta * FastMath.tan(Math.PI * alpha / 2.0D);
/*     */     } else {
/*  80 */       this.zeta = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextNormalizedDouble() {
/*  91 */     double x, omega = -FastMath.log(this.generator.nextDouble());
/*  92 */     double phi = Math.PI * (this.generator.nextDouble() - 0.5D);
/*     */ 
/*     */     
/*  95 */     if (this.alpha == 2.0D) {
/*  96 */       return FastMath.sqrt(2.0D * omega) * FastMath.sin(phi);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     if (this.beta == 0.0D) {
/*     */       
/* 104 */       if (this.alpha == 1.0D) {
/* 105 */         x = FastMath.tan(phi);
/*     */       } else {
/* 107 */         x = FastMath.pow(omega * FastMath.cos((1.0D - this.alpha) * phi), 1.0D / this.alpha - 1.0D) * FastMath.sin(this.alpha * phi) / FastMath.pow(FastMath.cos(phi), 1.0D / this.alpha);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 114 */       double cosPhi = FastMath.cos(phi);
/*     */       
/* 116 */       if (FastMath.abs(this.alpha - 1.0D) > 1.0E-8D) {
/* 117 */         double alphaPhi = this.alpha * phi;
/* 118 */         double invAlphaPhi = phi - alphaPhi;
/* 119 */         x = (FastMath.sin(alphaPhi) + this.zeta * FastMath.cos(alphaPhi)) / cosPhi * (FastMath.cos(invAlphaPhi) + this.zeta * FastMath.sin(invAlphaPhi)) / FastMath.pow(omega * cosPhi, (1.0D - this.alpha) / this.alpha);
/*     */       }
/*     */       else {
/*     */         
/* 123 */         double betaPhi = 1.5707963267948966D + this.beta * phi;
/* 124 */         x = 0.6366197723675814D * (betaPhi * FastMath.tan(phi) - this.beta * FastMath.log(1.5707963267948966D * omega * cosPhi / betaPhi));
/*     */ 
/*     */         
/* 127 */         if (this.alpha != 1.0D) {
/* 128 */           x += this.beta * FastMath.tan(Math.PI * this.alpha / 2.0D);
/*     */         }
/*     */       } 
/*     */     } 
/* 132 */     return x;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/StableRandomGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */