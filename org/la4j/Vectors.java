/*     */ package org.la4j;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import org.la4j.vector.VectorFactory;
/*     */ import org.la4j.vector.dense.BasicVector;
/*     */ import org.la4j.vector.functor.VectorAccumulator;
/*     */ import org.la4j.vector.functor.VectorFunction;
/*     */ import org.la4j.vector.functor.VectorPredicate;
/*     */ import org.la4j.vector.functor.VectorProcedure;
/*     */ import org.la4j.vector.sparse.CompressedVector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Vectors
/*     */ {
/*  38 */   public static final double EPS = LinearAlgebra.EPS;
/*  39 */   public static final int ROUND_FACTOR = LinearAlgebra.ROUND_FACTOR;
/*     */   
/*  41 */   public static final VectorFactory<BasicVector> BASIC = new VectorFactory<BasicVector>()
/*     */     {
/*     */       public BasicVector apply(int length) {
/*  44 */         return BasicVector.zero(length);
/*     */       }
/*     */     };
/*     */   
/*  48 */   public static final VectorFactory<CompressedVector> COMPRESSED = new VectorFactory<CompressedVector>()
/*     */     {
/*     */       public CompressedVector apply(int length) {
/*  51 */         return CompressedVector.zero(length);
/*     */       }
/*     */     };
/*     */   
/*  55 */   public static final VectorFactory<?>[] FACTORIES = new VectorFactory[] { BASIC, COMPRESSED };
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final VectorFactory<BasicVector> DENSE = BASIC;
/*     */   
/*  61 */   public static final VectorFactory<CompressedVector> SPARSE = COMPRESSED;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final VectorPredicate ZERO_VECTOR = new VectorPredicate()
/*     */     {
/*     */       public boolean test(int i, double value) {
/*  71 */         return (Math.abs(value) < Vectors.EPS);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final VectorPredicate POSITIVE_VECTOR = new VectorPredicate()
/*     */     {
/*     */       public boolean test(int i, double value) {
/*  83 */         return (value > 0.0D);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final VectorPredicate NEGATIVE_VECTOR = new VectorPredicate()
/*     */     {
/*     */       public boolean test(int i, double value) {
/*  95 */         return (value < 0.0D);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static final VectorFunction INC_FUNCTION = new VectorFunction()
/*     */     {
/*     */       public double evaluate(int i, double value) {
/* 105 */         return value + 1.0D;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public static final VectorFunction DEC_FUNCTION = new VectorFunction()
/*     */     {
/*     */       public double evaluate(int i, double value) {
/* 115 */         return value - 1.0D;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public static final VectorFunction INV_FUNCTION = new VectorFunction()
/*     */     {
/*     */       public double evaluate(int i, double value) {
/* 125 */         return -value;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorFunction asConstFunction(final double arg) {
/* 137 */     return new VectorFunction()
/*     */       {
/*     */         public double evaluate(int i, double value) {
/* 140 */           return arg;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorFunction asPlusFunction(final double arg) {
/* 153 */     return new VectorFunction()
/*     */       {
/*     */         public double evaluate(int i, double value) {
/* 156 */           return value + arg;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorFunction asMinusFunction(final double arg) {
/* 169 */     return new VectorFunction()
/*     */       {
/*     */         public double evaluate(int i, double value) {
/* 172 */           return value - arg;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorFunction asMulFunction(final double arg) {
/* 185 */     return new VectorFunction()
/*     */       {
/*     */         public double evaluate(int i, double value) {
/* 188 */           return value * arg;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorFunction asDivFunction(final double arg) {
/* 201 */     return new VectorFunction()
/*     */       {
/*     */         public double evaluate(int i, double value) {
/* 204 */           return value / arg;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorFunction asModFunction(final double arg) {
/* 217 */     return new VectorFunction()
/*     */       {
/*     */         public double evaluate(int i, double value) {
/* 220 */           return value % arg;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorAccumulator asSumAccumulator(final double neutral) {
/* 233 */     return new VectorAccumulator() {
/* 234 */         private BigDecimal result = new BigDecimal(neutral);
/*     */ 
/*     */         
/*     */         public void update(int i, double value) {
/* 238 */           this.result = this.result.add(new BigDecimal(value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 243 */           double value = this.result.setScale(Vectors.ROUND_FACTOR, RoundingMode.CEILING).doubleValue();
/* 244 */           this.result = new BigDecimal(neutral);
/* 245 */           return value;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorAccumulator asProductAccumulator(final double neutral) {
/* 258 */     return new VectorAccumulator() {
/* 259 */         private BigDecimal result = new BigDecimal(neutral);
/*     */ 
/*     */         
/*     */         public void update(int i, double value) {
/* 263 */           this.result = this.result.multiply(new BigDecimal(value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 268 */           double value = this.result.setScale(Vectors.ROUND_FACTOR, RoundingMode.CEILING).doubleValue();
/* 269 */           this.result = new BigDecimal(neutral);
/* 270 */           return value;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorAccumulator mkMinAccumulator() {
/* 281 */     return new VectorAccumulator() {
/* 282 */         private double result = Double.POSITIVE_INFINITY;
/*     */ 
/*     */         
/*     */         public void update(int i, double value) {
/* 286 */           this.result = Math.min(this.result, value);
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 291 */           double value = this.result;
/* 292 */           this.result = Double.POSITIVE_INFINITY;
/* 293 */           return value;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorAccumulator mkMaxAccumulator() {
/* 304 */     return new VectorAccumulator() {
/* 305 */         private double result = Double.NEGATIVE_INFINITY;
/*     */ 
/*     */         
/*     */         public void update(int i, double value) {
/* 309 */           this.result = Math.max(this.result, value);
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 314 */           double value = this.result;
/* 315 */           this.result = Double.NEGATIVE_INFINITY;
/* 316 */           return value;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorAccumulator mkEuclideanNormAccumulator() {
/* 328 */     return new VectorAccumulator() {
/* 329 */         private BigDecimal result = new BigDecimal(0.0D);
/*     */ 
/*     */         
/*     */         public void update(int i, double value) {
/* 333 */           this.result = this.result.add(new BigDecimal(value * value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 338 */           double value = this.result.setScale(Vectors.ROUND_FACTOR, RoundingMode.CEILING).doubleValue();
/* 339 */           this.result = new BigDecimal(0.0D);
/* 340 */           return Math.sqrt(value);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorAccumulator mkManhattanNormAccumulator() {
/* 352 */     return new VectorAccumulator() {
/* 353 */         private double result = 0.0D;
/*     */ 
/*     */         
/*     */         public void update(int i, double value) {
/* 357 */           this.result += Math.abs(value);
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 362 */           double value = this.result;
/* 363 */           this.result = 0.0D;
/* 364 */           return value;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VectorAccumulator mkInfinityNormAccumulator() {
/* 376 */     return new VectorAccumulator() {
/* 377 */         private double result = Double.NEGATIVE_INFINITY;
/*     */ 
/*     */         
/*     */         public void update(int i, double value) {
/* 381 */           this.result = Math.max(this.result, Math.abs(value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 386 */           double value = this.result;
/* 387 */           this.result = Double.NEGATIVE_INFINITY;
/* 388 */           return value;
/*     */         }
/*     */       };
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
/*     */   public static VectorAccumulator asSumFunctionAccumulator(final double neutral, final VectorFunction function) {
/* 404 */     return new VectorAccumulator() {
/* 405 */         private final VectorAccumulator sumAccumulator = Vectors.asSumAccumulator(neutral);
/*     */ 
/*     */         
/*     */         public void update(int i, double value) {
/* 409 */           this.sumAccumulator.update(i, function.evaluate(i, value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 414 */           return this.sumAccumulator.accumulate();
/*     */         }
/*     */       };
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
/*     */   public static VectorAccumulator asProductFunctionAccumulator(final double neutral, final VectorFunction function) {
/* 431 */     return new VectorAccumulator() {
/* 432 */         private final VectorAccumulator productAccumulator = Vectors.asProductAccumulator(neutral);
/*     */ 
/*     */         
/*     */         public void update(int i, double value) {
/* 436 */           this.productAccumulator.update(i, function.evaluate(i, value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 441 */           return this.productAccumulator.accumulate();
/*     */         }
/*     */       };
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
/*     */   public static VectorProcedure asAccumulatorProcedure(final VectorAccumulator accumulator) {
/* 456 */     return new VectorProcedure()
/*     */       {
/*     */         public void apply(int i, double value) {
/* 459 */           accumulator.update(i, value);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/Vectors.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */