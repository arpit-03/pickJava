/*     */ package org.la4j;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import org.la4j.matrix.MatrixFactory;
/*     */ import org.la4j.matrix.dense.Basic1DMatrix;
/*     */ import org.la4j.matrix.dense.Basic2DMatrix;
/*     */ import org.la4j.matrix.functor.AdvancedMatrixPredicate;
/*     */ import org.la4j.matrix.functor.MatrixAccumulator;
/*     */ import org.la4j.matrix.functor.MatrixFunction;
/*     */ import org.la4j.matrix.functor.MatrixPredicate;
/*     */ import org.la4j.matrix.functor.MatrixProcedure;
/*     */ import org.la4j.matrix.sparse.CCSMatrix;
/*     */ import org.la4j.matrix.sparse.CRSMatrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Matrices
/*     */ {
/*  47 */   public static final double EPS = LinearAlgebra.EPS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public static final int ROUND_FACTOR = LinearAlgebra.ROUND_FACTOR;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final MatrixPredicate DIAGONAL_MATRIX = new MatrixPredicate()
/*     */     {
/*     */       public boolean test(int rows, int columns) {
/*  62 */         return (rows == columns);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean test(int i, int j, double value) {
/*  67 */         return (i == j || Math.abs(value) < Matrices.EPS);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final MatrixPredicate IDENTITY_MATRIX = new MatrixPredicate()
/*     */     {
/*     */       public boolean test(int rows, int columns) {
/*  79 */         return (rows == columns);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean test(int i, int j, double value) {
/*  84 */         return (i == j) ? ((Math.abs(1.0D - value) < Matrices.EPS)) : ((Math.abs(value) < Matrices.EPS));
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static final MatrixPredicate ZERO_MATRIX = new MatrixPredicate()
/*     */     {
/*     */       public boolean test(int rows, int columns) {
/*  97 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean test(int i, int j, double value) {
/* 102 */         return (Math.abs(value) < Matrices.EPS);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static final MatrixPredicate TRIDIAGONAL_MATRIX = new MatrixPredicate()
/*     */     {
/*     */       public boolean test(int rows, int columns) {
/* 114 */         return (rows == columns);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean test(int i, int j, double value) {
/* 119 */         return (Math.abs(i - j) <= 1 || Math.abs(value) < Matrices.EPS);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public static final MatrixPredicate POSITIVE_MATRIX = new MatrixPredicate()
/*     */     {
/*     */       public boolean test(int rows, int columns) {
/* 131 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean test(int i, int j, double value) {
/* 136 */         return (value > 0.0D);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public static final MatrixPredicate NEGATIVE_MATRIX = new MatrixPredicate()
/*     */     {
/*     */       public boolean test(int rows, int columns) {
/* 148 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean test(int i, int j, double value) {
/* 153 */         return (value < 0.0D);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public static final MatrixPredicate LOWER_BIDIAGONAL_MATRIX = new MatrixPredicate()
/*     */     {
/*     */       public boolean test(int rows, int columns) {
/* 163 */         return (rows == columns);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean test(int i, int j, double value) {
/* 168 */         return ((i != j && i != j + 1) || Math.abs(value) < Matrices.EPS);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static final MatrixPredicate UPPER_BIDIAGONAL_MATRIX = new MatrixPredicate()
/*     */     {
/*     */       public boolean test(int rows, int columns) {
/* 178 */         return (rows == columns);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean test(int i, int j, double value) {
/* 183 */         return ((i != j && i != j - 1) || Math.abs(value) < Matrices.EPS);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public static final MatrixPredicate LOWER_TRIANGULAR_MATRIX = new MatrixPredicate()
/*     */     {
/*     */       public boolean test(int rows, int columns) {
/* 195 */         return (rows == columns);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean test(int i, int j, double value) {
/* 200 */         return (i <= j || Math.abs(value) < Matrices.EPS);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   public static final MatrixPredicate UPPER_TRIANGULAR_MATRIX = new MatrixPredicate()
/*     */     {
/*     */       public boolean test(int rows, int columns) {
/* 212 */         return (rows == columns);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean test(int i, int j, double value) {
/* 217 */         return (i >= j || Math.abs(value) < Matrices.EPS);
/*     */       }
/*     */     };
/*     */   
/*     */   private static class SymmetricMatrixPredicate
/*     */     implements AdvancedMatrixPredicate {
/*     */     private SymmetricMatrixPredicate() {}
/*     */     
/*     */     public boolean test(Matrix matrix) {
/* 226 */       if (matrix.rows() != matrix.columns()) {
/* 227 */         return false;
/*     */       }
/*     */       
/* 230 */       for (int i = 0; i < matrix.rows(); i++) {
/* 231 */         for (int j = i + 1; j < matrix.columns(); j++) {
/* 232 */           double a = matrix.get(i, j);
/* 233 */           double b = matrix.get(j, i);
/* 234 */           double diff = Math.abs(a - b);
/* 235 */           if (diff / Math.max(Math.abs(a), Math.abs(b)) > Matrices.EPS) {
/* 236 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 241 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DiagonallyDominantPredicate
/*     */     implements AdvancedMatrixPredicate {
/*     */     private DiagonallyDominantPredicate() {}
/*     */     
/*     */     public boolean test(Matrix matrix) {
/* 250 */       if (matrix.rows() != matrix.columns()) {
/* 251 */         return false;
/*     */       }
/*     */       
/* 254 */       for (int i = 0; i < matrix.rows(); i++) {
/* 255 */         double sum = 0.0D;
/* 256 */         for (int j = 0; j < matrix.columns(); j++) {
/* 257 */           if (i != j) {
/* 258 */             sum += Math.abs(matrix.get(i, j));
/*     */           }
/*     */         } 
/* 261 */         if (sum > Math.abs(matrix.get(i, i)) - Matrices.EPS) {
/* 262 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 266 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class PositiveDefiniteMatrixPredicate implements AdvancedMatrixPredicate {
/*     */     private PositiveDefiniteMatrixPredicate() {}
/*     */     
/*     */     public boolean test(Matrix matrix) {
/* 274 */       if (matrix.rows() != matrix.columns()) {
/* 275 */         return false;
/*     */       }
/*     */       
/* 278 */       int size = matrix.columns();
/* 279 */       int currentSize = 1;
/*     */       
/* 281 */       while (currentSize <= size) {
/* 282 */         Matrix topLeftMatrix = matrix.sliceTopLeft(currentSize, currentSize);
/*     */         
/* 284 */         if (topLeftMatrix.determinant() < 0.0D) {
/* 285 */           return false;
/*     */         }
/*     */         
/* 288 */         currentSize++;
/*     */       } 
/*     */       
/* 291 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   public static final AdvancedMatrixPredicate SYMMETRIC_MATRIX = new SymmetricMatrixPredicate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 307 */   public static final AdvancedMatrixPredicate DIAGONALLY_DOMINANT_MATRIX = new DiagonallyDominantPredicate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 313 */   public static final AdvancedMatrixPredicate POSITIVE_DEFINITE_MATRIX = new PositiveDefiniteMatrixPredicate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 319 */   public static final MatrixFactory<Basic2DMatrix> BASIC_2D = new MatrixFactory<Basic2DMatrix>()
/*     */     {
/*     */       public Basic2DMatrix apply(int rows, int columns)
/*     */       {
/* 323 */         return Basic2DMatrix.zero(rows, columns);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 330 */   public static final MatrixFactory<Basic1DMatrix> BASIC_1D = new MatrixFactory<Basic1DMatrix>()
/*     */     {
/*     */       public Basic1DMatrix apply(int rows, int columns)
/*     */       {
/* 334 */         return Basic1DMatrix.zero(rows, columns);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 341 */   public static final MatrixFactory<Basic2DMatrix> DENSE = BASIC_2D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 346 */   public static final MatrixFactory<CCSMatrix> CCS = new MatrixFactory<CCSMatrix>()
/*     */     {
/*     */       public CCSMatrix apply(int rows, int columns)
/*     */       {
/* 350 */         return CCSMatrix.zero(rows, columns);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 357 */   public static final MatrixFactory<CRSMatrix> CRS = new MatrixFactory<CRSMatrix>()
/*     */     {
/*     */       public CRSMatrix apply(int rows, int columns)
/*     */       {
/* 361 */         return CRSMatrix.zero(rows, columns);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 368 */   public static final MatrixFactory<CRSMatrix> SPARSE = CRS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 373 */   public static final MatrixFactory<CRSMatrix> SPARSE_ROW_MAJOR = CRS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 378 */   public static final MatrixFactory<CCSMatrix> SPARSE_COLUMN_MAJOR = CCS;
/*     */   
/* 380 */   public static final MatrixFactory<?>[] CONVERTERS = new MatrixFactory[] { BASIC_2D, BASIC_1D, CRS, CCS };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 387 */   public static final MatrixFunction INC_FUNCTION = new MatrixFunction()
/*     */     {
/*     */       public double evaluate(int i, int j, double value) {
/* 390 */         return value + 1.0D;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 397 */   public static final MatrixFunction DEC_FUNCTION = new MatrixFunction()
/*     */     {
/*     */       public double evaluate(int i, int j, double value) {
/* 400 */         return value - 1.0D;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 407 */   public static final MatrixFunction INV_FUNCTION = new MatrixFunction()
/*     */     {
/*     */       public double evaluate(int i, int j, double value) {
/* 410 */         return -value;
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
/*     */   public static MatrixFunction asConstFunction(final double arg) {
/* 422 */     return new MatrixFunction()
/*     */       {
/*     */         public double evaluate(int i, int j, double value) {
/* 425 */           return arg;
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
/*     */   public static MatrixFunction asPlusFunction(final double arg) {
/* 438 */     return new MatrixFunction()
/*     */       {
/*     */         public double evaluate(int i, int j, double value) {
/* 441 */           return value + arg;
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
/*     */   public static MatrixFunction asMinusFunction(final double arg) {
/* 454 */     return new MatrixFunction()
/*     */       {
/*     */         public double evaluate(int i, int j, double value) {
/* 457 */           return value - arg;
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
/*     */   public static MatrixFunction asMulFunction(final double arg) {
/* 470 */     return new MatrixFunction()
/*     */       {
/*     */         public double evaluate(int i, int j, double value) {
/* 473 */           return value * arg;
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
/*     */   public static MatrixFunction asDivFunction(final double arg) {
/* 486 */     return new MatrixFunction()
/*     */       {
/*     */         public double evaluate(int i, int j, double value) {
/* 489 */           return value / arg;
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
/*     */   public static MatrixFunction asModFunction(final double arg) {
/* 502 */     return new MatrixFunction()
/*     */       {
/*     */         public double evaluate(int i, int j, double value) {
/* 505 */           return value % arg;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MatrixAccumulator mkMinAccumulator() {
/* 516 */     return new MatrixAccumulator() {
/* 517 */         private double result = Double.POSITIVE_INFINITY;
/*     */ 
/*     */         
/*     */         public void update(int i, int j, double value) {
/* 521 */           this.result = Math.min(this.result, value);
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 526 */           double value = this.result;
/* 527 */           this.result = Double.POSITIVE_INFINITY;
/* 528 */           return value;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MatrixAccumulator mkMaxAccumulator() {
/* 539 */     return new MatrixAccumulator() {
/* 540 */         private double result = Double.NEGATIVE_INFINITY;
/*     */ 
/*     */         
/*     */         public void update(int i, int j, double value) {
/* 544 */           this.result = Math.max(this.result, value);
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 549 */           double value = this.result;
/* 550 */           this.result = Double.NEGATIVE_INFINITY;
/* 551 */           return value;
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
/*     */   public static MatrixAccumulator mkEuclideanNormAccumulator() {
/* 564 */     return new MatrixAccumulator() {
/* 565 */         private BigDecimal result = new BigDecimal(0.0D);
/*     */ 
/*     */         
/*     */         public void update(int i, int j, double value) {
/* 569 */           this.result = this.result.add(new BigDecimal(value * value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 574 */           double value = this.result.setScale(Matrices.ROUND_FACTOR, RoundingMode.CEILING).doubleValue();
/* 575 */           this.result = new BigDecimal(0.0D);
/* 576 */           return Math.sqrt(value);
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
/*     */   public static MatrixAccumulator mkManhattanNormAccumulator() {
/* 589 */     return new MatrixAccumulator() {
/* 590 */         private double result = 0.0D;
/*     */ 
/*     */         
/*     */         public void update(int i, int j, double value) {
/* 594 */           this.result += Math.abs(value);
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 599 */           double value = this.result;
/* 600 */           this.result = 0.0D;
/* 601 */           return value;
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
/*     */   public static MatrixAccumulator mkInfinityNormAccumulator() {
/* 614 */     return new MatrixAccumulator() {
/* 615 */         private double result = Double.NEGATIVE_INFINITY;
/*     */ 
/*     */         
/*     */         public void update(int i, int j, double value) {
/* 619 */           this.result = Math.max(this.result, Math.abs(value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 624 */           double value = this.result;
/* 625 */           this.result = Double.NEGATIVE_INFINITY;
/* 626 */           return value;
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
/*     */   public static MatrixAccumulator asSumAccumulator(final double neutral) {
/* 639 */     return new MatrixAccumulator() {
/* 640 */         private BigDecimal result = new BigDecimal(neutral);
/*     */ 
/*     */         
/*     */         public void update(int i, int j, double value) {
/* 644 */           this.result = this.result.add(new BigDecimal(value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 649 */           double value = this.result.setScale(Matrices.ROUND_FACTOR, RoundingMode.CEILING).doubleValue();
/* 650 */           this.result = new BigDecimal(neutral);
/* 651 */           return value;
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
/*     */   public static MatrixAccumulator asProductAccumulator(final double neutral) {
/* 664 */     return new MatrixAccumulator() {
/* 665 */         private BigDecimal result = new BigDecimal(neutral);
/*     */ 
/*     */         
/*     */         public void update(int i, int j, double value) {
/* 669 */           this.result = this.result.multiply(new BigDecimal(value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 674 */           double value = this.result.setScale(Matrices.ROUND_FACTOR, RoundingMode.CEILING).doubleValue();
/* 675 */           this.result = new BigDecimal(neutral);
/* 676 */           return value;
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
/*     */   public static MatrixAccumulator asSumFunctionAccumulator(final double neutral, final MatrixFunction function) {
/* 691 */     return new MatrixAccumulator() {
/* 692 */         private final MatrixAccumulator sumAccumulator = Matrices.asSumAccumulator(neutral);
/*     */ 
/*     */         
/*     */         public void update(int i, int j, double value) {
/* 696 */           this.sumAccumulator.update(i, j, function.evaluate(i, j, value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 701 */           return this.sumAccumulator.accumulate();
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
/*     */   public static MatrixAccumulator asProductFunctionAccumulator(final double neutral, final MatrixFunction function) {
/* 718 */     return new MatrixAccumulator() {
/* 719 */         private final MatrixAccumulator productAccumulator = Matrices.asProductAccumulator(neutral);
/*     */ 
/*     */         
/*     */         public void update(int i, int j, double value) {
/* 723 */           this.productAccumulator.update(i, j, function.evaluate(i, j, value));
/*     */         }
/*     */ 
/*     */         
/*     */         public double accumulate() {
/* 728 */           return this.productAccumulator.accumulate();
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
/*     */   public static MatrixProcedure asAccumulatorProcedure(final MatrixAccumulator accumulator) {
/* 743 */     return new MatrixProcedure()
/*     */       {
/*     */         public void apply(int i, int j, double value) {
/* 746 */           accumulator.update(i, j, value);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/Matrices.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */