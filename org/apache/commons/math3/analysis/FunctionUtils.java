/*     */ package org.apache.commons.math3.analysis;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*     */ import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableFunction;
/*     */ import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableVectorFunction;
/*     */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
/*     */ import org.apache.commons.math3.analysis.function.Identity;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FunctionUtils
/*     */ {
/*     */   public static UnivariateFunction compose(UnivariateFunction... f) {
/*  51 */     return new UnivariateFunction()
/*     */       {
/*     */         public double value(double x) {
/*  54 */           double r = x;
/*  55 */           for (int i = f.length - 1; i >= 0; i--) {
/*  56 */             r = f[i].value(r);
/*     */           }
/*  58 */           return r;
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
/*     */   public static UnivariateDifferentiableFunction compose(UnivariateDifferentiableFunction... f) {
/*  74 */     return new UnivariateDifferentiableFunction()
/*     */       {
/*     */         public double value(double t)
/*     */         {
/*  78 */           double r = t;
/*  79 */           for (int i = f.length - 1; i >= 0; i--) {
/*  80 */             r = f[i].value(r);
/*     */           }
/*  82 */           return r;
/*     */         }
/*     */ 
/*     */         
/*     */         public DerivativeStructure value(DerivativeStructure t) {
/*  87 */           DerivativeStructure r = t;
/*  88 */           for (int i = f.length - 1; i >= 0; i--) {
/*  89 */             r = f[i].value(r);
/*     */           }
/*  91 */           return r;
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
/*     */   @Deprecated
/*     */   public static DifferentiableUnivariateFunction compose(DifferentiableUnivariateFunction... f) {
/* 109 */     return new DifferentiableUnivariateFunction()
/*     */       {
/*     */         public double value(double x) {
/* 112 */           double r = x;
/* 113 */           for (int i = f.length - 1; i >= 0; i--) {
/* 114 */             r = f[i].value(r);
/*     */           }
/* 116 */           return r;
/*     */         }
/*     */ 
/*     */         
/*     */         public UnivariateFunction derivative() {
/* 121 */           return new UnivariateFunction()
/*     */             {
/*     */               public double value(double x) {
/* 124 */                 double p = 1.0D;
/* 125 */                 double r = x;
/* 126 */                 for (int i = f.length - 1; i >= 0; i--) {
/* 127 */                   p *= f[i].derivative().value(r);
/* 128 */                   r = f[i].value(r);
/*     */                 } 
/* 130 */                 return p;
/*     */               }
/*     */             };
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
/*     */   public static UnivariateFunction add(UnivariateFunction... f) {
/* 144 */     return new UnivariateFunction()
/*     */       {
/*     */         public double value(double x) {
/* 147 */           double r = f[0].value(x);
/* 148 */           for (int i = 1; i < f.length; i++) {
/* 149 */             r += f[i].value(x);
/*     */           }
/* 151 */           return r;
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
/*     */   public static UnivariateDifferentiableFunction add(UnivariateDifferentiableFunction... f) {
/* 164 */     return new UnivariateDifferentiableFunction()
/*     */       {
/*     */         public double value(double t)
/*     */         {
/* 168 */           double r = f[0].value(t);
/* 169 */           for (int i = 1; i < f.length; i++) {
/* 170 */             r += f[i].value(t);
/*     */           }
/* 172 */           return r;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
/* 180 */           DerivativeStructure r = f[0].value(t);
/* 181 */           for (int i = 1; i < f.length; i++) {
/* 182 */             r = r.add(f[i].value(t));
/*     */           }
/* 184 */           return r;
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
/*     */   @Deprecated
/*     */   public static DifferentiableUnivariateFunction add(DifferentiableUnivariateFunction... f) {
/* 199 */     return new DifferentiableUnivariateFunction()
/*     */       {
/*     */         public double value(double x) {
/* 202 */           double r = f[0].value(x);
/* 203 */           for (int i = 1; i < f.length; i++) {
/* 204 */             r += f[i].value(x);
/*     */           }
/* 206 */           return r;
/*     */         }
/*     */ 
/*     */         
/*     */         public UnivariateFunction derivative() {
/* 211 */           return new UnivariateFunction()
/*     */             {
/*     */               public double value(double x) {
/* 214 */                 double r = f[0].derivative().value(x);
/* 215 */                 for (int i = 1; i < f.length; i++) {
/* 216 */                   r += f[i].derivative().value(x);
/*     */                 }
/* 218 */                 return r;
/*     */               }
/*     */             };
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
/*     */   public static UnivariateFunction multiply(UnivariateFunction... f) {
/* 232 */     return new UnivariateFunction()
/*     */       {
/*     */         public double value(double x) {
/* 235 */           double r = f[0].value(x);
/* 236 */           for (int i = 1; i < f.length; i++) {
/* 237 */             r *= f[i].value(x);
/*     */           }
/* 239 */           return r;
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
/*     */   public static UnivariateDifferentiableFunction multiply(UnivariateDifferentiableFunction... f) {
/* 252 */     return new UnivariateDifferentiableFunction()
/*     */       {
/*     */         public double value(double t)
/*     */         {
/* 256 */           double r = f[0].value(t);
/* 257 */           for (int i = 1; i < f.length; i++) {
/* 258 */             r *= f[i].value(t);
/*     */           }
/* 260 */           return r;
/*     */         }
/*     */ 
/*     */         
/*     */         public DerivativeStructure value(DerivativeStructure t) {
/* 265 */           DerivativeStructure r = f[0].value(t);
/* 266 */           for (int i = 1; i < f.length; i++) {
/* 267 */             r = r.multiply(f[i].value(t));
/*     */           }
/* 269 */           return r;
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
/*     */   @Deprecated
/*     */   public static DifferentiableUnivariateFunction multiply(DifferentiableUnivariateFunction... f) {
/* 284 */     return new DifferentiableUnivariateFunction()
/*     */       {
/*     */         public double value(double x) {
/* 287 */           double r = f[0].value(x);
/* 288 */           for (int i = 1; i < f.length; i++) {
/* 289 */             r *= f[i].value(x);
/*     */           }
/* 291 */           return r;
/*     */         }
/*     */ 
/*     */         
/*     */         public UnivariateFunction derivative() {
/* 296 */           return new UnivariateFunction()
/*     */             {
/*     */               public double value(double x) {
/* 299 */                 double sum = 0.0D;
/* 300 */                 for (int i = 0; i < f.length; i++) {
/* 301 */                   double prod = f[i].derivative().value(x);
/* 302 */                   for (int j = 0; j < f.length; j++) {
/* 303 */                     if (i != j) {
/* 304 */                       prod *= f[j].value(x);
/*     */                     }
/*     */                   } 
/* 307 */                   sum += prod;
/*     */                 } 
/* 309 */                 return sum;
/*     */               }
/*     */             };
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
/*     */   public static UnivariateFunction combine(final BivariateFunction combiner, final UnivariateFunction f, final UnivariateFunction g) {
/* 328 */     return new UnivariateFunction()
/*     */       {
/*     */         public double value(double x) {
/* 331 */           return combiner.value(f.value(x), g.value(x));
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
/*     */   
/*     */   public static MultivariateFunction collector(final BivariateFunction combiner, final UnivariateFunction f, final double initialValue) {
/* 349 */     return new MultivariateFunction()
/*     */       {
/*     */         public double value(double[] point) {
/* 352 */           double result = combiner.value(initialValue, f.value(point[0]));
/* 353 */           for (int i = 1; i < point.length; i++) {
/* 354 */             result = combiner.value(result, f.value(point[i]));
/*     */           }
/* 356 */           return result;
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
/*     */   public static MultivariateFunction collector(BivariateFunction combiner, double initialValue) {
/* 372 */     return collector(combiner, (UnivariateFunction)new Identity(), initialValue);
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
/*     */   public static UnivariateFunction fix1stArgument(final BivariateFunction f, final double fixed) {
/* 384 */     return new UnivariateFunction()
/*     */       {
/*     */         public double value(double x) {
/* 387 */           return f.value(fixed, x);
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
/*     */   public static UnivariateFunction fix2ndArgument(final BivariateFunction f, final double fixed) {
/* 400 */     return new UnivariateFunction()
/*     */       {
/*     */         public double value(double x) {
/* 403 */           return f.value(x, fixed);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] sample(UnivariateFunction f, double min, double max, int n) throws NumberIsTooLargeException, NotStrictlyPositiveException {
/* 428 */     if (n <= 0) {
/* 429 */       throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_NUMBER_OF_SAMPLES, Integer.valueOf(n));
/*     */     }
/*     */ 
/*     */     
/* 433 */     if (min >= max) {
/* 434 */       throw new NumberIsTooLargeException(Double.valueOf(min), Double.valueOf(max), false);
/*     */     }
/*     */     
/* 437 */     double[] s = new double[n];
/* 438 */     double h = (max - min) / n;
/* 439 */     for (int i = 0; i < n; i++) {
/* 440 */       s[i] = f.value(min + i * h);
/*     */     }
/* 442 */     return s;
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
/*     */   @Deprecated
/*     */   public static DifferentiableUnivariateFunction toDifferentiableUnivariateFunction(final UnivariateDifferentiableFunction f) {
/* 455 */     return new DifferentiableUnivariateFunction()
/*     */       {
/*     */         public double value(double x)
/*     */         {
/* 459 */           return f.value(x);
/*     */         }
/*     */ 
/*     */         
/*     */         public UnivariateFunction derivative() {
/* 464 */           return new UnivariateFunction()
/*     */             {
/*     */               public double value(double x) {
/* 467 */                 return f.value(new DerivativeStructure(1, 1, 0, x)).getPartialDerivative(new int[] { 1 });
/*     */               }
/*     */             };
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
/*     */   
/*     */   @Deprecated
/*     */   public static UnivariateDifferentiableFunction toUnivariateDifferential(final DifferentiableUnivariateFunction f) {
/* 488 */     return new UnivariateDifferentiableFunction()
/*     */       {
/*     */         public double value(double x)
/*     */         {
/* 492 */           return f.value(x);
/*     */         }
/*     */         public DerivativeStructure value(DerivativeStructure t) throws NumberIsTooLargeException {
/*     */           int parameters;
/*     */           double[] derivatives;
/*     */           double fPrime;
/*     */           int[] orders;
/*     */           int i;
/* 500 */           switch (t.getOrder()) {
/*     */             case 0:
/* 502 */               return new DerivativeStructure(t.getFreeParameters(), 0, f.value(t.getValue()));
/*     */             case 1:
/* 504 */               parameters = t.getFreeParameters();
/* 505 */               derivatives = new double[parameters + 1];
/* 506 */               derivatives[0] = f.value(t.getValue());
/* 507 */               fPrime = f.derivative().value(t.getValue());
/* 508 */               orders = new int[parameters];
/* 509 */               for (i = 0; i < parameters; i++) {
/* 510 */                 orders[i] = 1;
/* 511 */                 derivatives[i + 1] = fPrime * t.getPartialDerivative(orders);
/* 512 */                 orders[i] = 0;
/*     */               } 
/* 514 */               return new DerivativeStructure(parameters, 1, derivatives);
/*     */           } 
/*     */           
/* 517 */           throw new NumberIsTooLargeException(Integer.valueOf(t.getOrder()), Integer.valueOf(1), true);
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
/*     */   @Deprecated
/*     */   public static DifferentiableMultivariateFunction toDifferentiableMultivariateFunction(final MultivariateDifferentiableFunction f) {
/* 534 */     return new DifferentiableMultivariateFunction()
/*     */       {
/*     */         public double value(double[] x)
/*     */         {
/* 538 */           return f.value(x);
/*     */         }
/*     */ 
/*     */         
/*     */         public MultivariateFunction partialDerivative(final int k) {
/* 543 */           return new MultivariateFunction()
/*     */             {
/*     */               public double value(double[] x)
/*     */               {
/* 547 */                 int n = x.length;
/*     */ 
/*     */                 
/* 550 */                 DerivativeStructure[] dsX = new DerivativeStructure[n];
/* 551 */                 for (int i = 0; i < n; i++) {
/* 552 */                   if (i == k) {
/* 553 */                     dsX[i] = new DerivativeStructure(1, 1, 0, x[i]);
/*     */                   } else {
/* 555 */                     dsX[i] = new DerivativeStructure(1, 1, x[i]);
/*     */                   } 
/*     */                 } 
/* 558 */                 DerivativeStructure y = f.value(dsX);
/*     */ 
/*     */                 
/* 561 */                 return y.getPartialDerivative(new int[] { 1 });
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public MultivariateVectorFunction gradient() {
/* 569 */           return new MultivariateVectorFunction()
/*     */             {
/*     */               public double[] value(double[] x)
/*     */               {
/* 573 */                 int n = x.length;
/*     */ 
/*     */                 
/* 576 */                 DerivativeStructure[] dsX = new DerivativeStructure[n];
/* 577 */                 for (int i = 0; i < n; i++) {
/* 578 */                   dsX[i] = new DerivativeStructure(n, 1, i, x[i]);
/*     */                 }
/* 580 */                 DerivativeStructure y = f.value(dsX);
/*     */ 
/*     */                 
/* 583 */                 double[] gradient = new double[n];
/* 584 */                 int[] orders = new int[n];
/* 585 */                 for (int j = 0; j < n; j++) {
/* 586 */                   orders[j] = 1;
/* 587 */                   gradient[j] = y.getPartialDerivative(orders);
/* 588 */                   orders[j] = 0;
/*     */                 } 
/*     */                 
/* 591 */                 return gradient;
/*     */               }
/*     */             };
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static MultivariateDifferentiableFunction toMultivariateDifferentiableFunction(final DifferentiableMultivariateFunction f) {
/* 615 */     return new MultivariateDifferentiableFunction()
/*     */       {
/*     */         public double value(double[] x)
/*     */         {
/* 619 */           return f.value(x);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public DerivativeStructure value(DerivativeStructure[] t) throws DimensionMismatchException, NumberIsTooLargeException {
/* 630 */           int parameters = t[0].getFreeParameters();
/* 631 */           int order = t[0].getOrder();
/* 632 */           int n = t.length;
/* 633 */           if (order > 1) {
/* 634 */             throw new NumberIsTooLargeException(Integer.valueOf(order), Integer.valueOf(1), true);
/*     */           }
/*     */ 
/*     */           
/* 638 */           for (int i = 0; i < n; i++) {
/* 639 */             if (t[i].getFreeParameters() != parameters) {
/* 640 */               throw new DimensionMismatchException(t[i].getFreeParameters(), parameters);
/*     */             }
/*     */             
/* 643 */             if (t[i].getOrder() != order) {
/* 644 */               throw new DimensionMismatchException(t[i].getOrder(), order);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 649 */           double[] point = new double[n];
/* 650 */           for (int j = 0; j < n; j++) {
/* 651 */             point[j] = t[j].getValue();
/*     */           }
/* 653 */           double value = f.value(point);
/* 654 */           double[] gradient = f.gradient().value(point);
/*     */ 
/*     */           
/* 657 */           double[] derivatives = new double[parameters + 1];
/* 658 */           derivatives[0] = value;
/* 659 */           int[] orders = new int[parameters];
/* 660 */           for (int k = 0; k < parameters; k++) {
/* 661 */             orders[k] = 1;
/* 662 */             for (int m = 0; m < n; m++) {
/* 663 */               derivatives[k + 1] = derivatives[k + 1] + gradient[m] * t[m].getPartialDerivative(orders);
/*     */             }
/* 665 */             orders[k] = 0;
/*     */           } 
/*     */           
/* 668 */           return new DerivativeStructure(parameters, order, derivatives);
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
/*     */   @Deprecated
/*     */   public static DifferentiableMultivariateVectorFunction toDifferentiableMultivariateVectorFunction(final MultivariateDifferentiableVectorFunction f) {
/* 685 */     return new DifferentiableMultivariateVectorFunction()
/*     */       {
/*     */         public double[] value(double[] x)
/*     */         {
/* 689 */           return f.value(x);
/*     */         }
/*     */ 
/*     */         
/*     */         public MultivariateMatrixFunction jacobian() {
/* 694 */           return new MultivariateMatrixFunction()
/*     */             {
/*     */               public double[][] value(double[] x)
/*     */               {
/* 698 */                 int n = x.length;
/*     */ 
/*     */                 
/* 701 */                 DerivativeStructure[] dsX = new DerivativeStructure[n];
/* 702 */                 for (int i = 0; i < n; i++) {
/* 703 */                   dsX[i] = new DerivativeStructure(n, 1, i, x[i]);
/*     */                 }
/* 705 */                 DerivativeStructure[] y = f.value(dsX);
/*     */ 
/*     */                 
/* 708 */                 double[][] jacobian = new double[y.length][n];
/* 709 */                 int[] orders = new int[n];
/* 710 */                 for (int j = 0; j < y.length; j++) {
/* 711 */                   for (int k = 0; k < n; k++) {
/* 712 */                     orders[k] = 1;
/* 713 */                     jacobian[j][k] = y[j].getPartialDerivative(orders);
/* 714 */                     orders[k] = 0;
/*     */                   } 
/*     */                 } 
/*     */                 
/* 718 */                 return jacobian;
/*     */               }
/*     */             };
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static MultivariateDifferentiableVectorFunction toMultivariateDifferentiableVectorFunction(final DifferentiableMultivariateVectorFunction f) {
/* 742 */     return new MultivariateDifferentiableVectorFunction()
/*     */       {
/*     */         public double[] value(double[] x)
/*     */         {
/* 746 */           return f.value(x);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public DerivativeStructure[] value(DerivativeStructure[] t) throws DimensionMismatchException, NumberIsTooLargeException {
/* 757 */           int parameters = t[0].getFreeParameters();
/* 758 */           int order = t[0].getOrder();
/* 759 */           int n = t.length;
/* 760 */           if (order > 1) {
/* 761 */             throw new NumberIsTooLargeException(Integer.valueOf(order), Integer.valueOf(1), true);
/*     */           }
/*     */ 
/*     */           
/* 765 */           for (int i = 0; i < n; i++) {
/* 766 */             if (t[i].getFreeParameters() != parameters) {
/* 767 */               throw new DimensionMismatchException(t[i].getFreeParameters(), parameters);
/*     */             }
/*     */             
/* 770 */             if (t[i].getOrder() != order) {
/* 771 */               throw new DimensionMismatchException(t[i].getOrder(), order);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 776 */           double[] point = new double[n];
/* 777 */           for (int j = 0; j < n; j++) {
/* 778 */             point[j] = t[j].getValue();
/*     */           }
/* 780 */           double[] value = f.value(point);
/* 781 */           double[][] jacobian = f.jacobian().value(point);
/*     */ 
/*     */           
/* 784 */           DerivativeStructure[] merged = new DerivativeStructure[value.length];
/* 785 */           for (int k = 0; k < merged.length; k++) {
/* 786 */             double[] derivatives = new double[parameters + 1];
/* 787 */             derivatives[0] = value[k];
/* 788 */             int[] orders = new int[parameters];
/* 789 */             for (int m = 0; m < parameters; m++) {
/* 790 */               orders[m] = 1;
/* 791 */               for (int i1 = 0; i1 < n; i1++) {
/* 792 */                 derivatives[m + 1] = derivatives[m + 1] + jacobian[k][i1] * t[i1].getPartialDerivative(orders);
/*     */               }
/* 794 */               orders[m] = 0;
/*     */             } 
/* 796 */             merged[k] = new DerivativeStructure(parameters, order, derivatives);
/*     */           } 
/*     */           
/* 799 */           return merged;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/FunctionUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */