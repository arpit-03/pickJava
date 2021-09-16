/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.BivariateFunction;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BicubicSplineFunction
/*     */   implements BivariateFunction
/*     */ {
/*     */   private static final short N = 4;
/*     */   
/*     */   BicubicSplineFunction(double[] coeff) {
/* 461 */     this(coeff, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final BivariateFunction partialDerivativeX;
/*     */ 
/*     */   
/*     */   private final BivariateFunction partialDerivativeY;
/*     */ 
/*     */   
/* 473 */   private final double[][] a = new double[4][4]; BicubicSplineFunction(double[] coeff, boolean initializeDerivatives) {
/* 474 */     for (int i = 0; i < 4; i++) {
/* 475 */       for (int j = 0; j < 4; j++) {
/* 476 */         this.a[i][j] = coeff[i * 4 + j];
/*     */       }
/*     */     } 
/*     */     
/* 480 */     if (initializeDerivatives) {
/*     */       
/* 482 */       final double[][] aX = new double[4][4];
/* 483 */       final double[][] aY = new double[4][4];
/* 484 */       final double[][] aXX = new double[4][4];
/* 485 */       final double[][] aYY = new double[4][4];
/* 486 */       final double[][] aXY = new double[4][4];
/*     */       
/* 488 */       for (int j = 0; j < 4; j++) {
/* 489 */         for (int k = 0; k < 4; k++) {
/* 490 */           double c = this.a[j][k];
/* 491 */           aX[j][k] = j * c;
/* 492 */           aY[j][k] = k * c;
/* 493 */           aXX[j][k] = (j - 1) * aX[j][k];
/* 494 */           aYY[j][k] = (k - 1) * aY[j][k];
/* 495 */           aXY[j][k] = k * aX[j][k];
/*     */         } 
/*     */       } 
/*     */       
/* 499 */       this.partialDerivativeX = new BivariateFunction()
/*     */         {
/*     */           public double value(double x, double y) {
/* 502 */             double x2 = x * x;
/* 503 */             double[] pX = { 0.0D, 1.0D, x, x2 };
/*     */             
/* 505 */             double y2 = y * y;
/* 506 */             double y3 = y2 * y;
/* 507 */             double[] pY = { 1.0D, y, y2, y3 };
/*     */             
/* 509 */             return BicubicSplineFunction.this.apply(pX, pY, aX);
/*     */           }
/*     */         };
/* 512 */       this.partialDerivativeY = new BivariateFunction()
/*     */         {
/*     */           public double value(double x, double y) {
/* 515 */             double x2 = x * x;
/* 516 */             double x3 = x2 * x;
/* 517 */             double[] pX = { 1.0D, x, x2, x3 };
/*     */             
/* 519 */             double y2 = y * y;
/* 520 */             double[] pY = { 0.0D, 1.0D, y, y2 };
/*     */             
/* 522 */             return BicubicSplineFunction.this.apply(pX, pY, aY);
/*     */           }
/*     */         };
/* 525 */       this.partialDerivativeXX = new BivariateFunction()
/*     */         {
/*     */           public double value(double x, double y) {
/* 528 */             double[] pX = { 0.0D, 0.0D, 1.0D, x };
/*     */             
/* 530 */             double y2 = y * y;
/* 531 */             double y3 = y2 * y;
/* 532 */             double[] pY = { 1.0D, y, y2, y3 };
/*     */             
/* 534 */             return BicubicSplineFunction.this.apply(pX, pY, aXX);
/*     */           }
/*     */         };
/* 537 */       this.partialDerivativeYY = new BivariateFunction()
/*     */         {
/*     */           public double value(double x, double y) {
/* 540 */             double x2 = x * x;
/* 541 */             double x3 = x2 * x;
/* 542 */             double[] pX = { 1.0D, x, x2, x3 };
/*     */             
/* 544 */             double[] pY = { 0.0D, 0.0D, 1.0D, y };
/*     */             
/* 546 */             return BicubicSplineFunction.this.apply(pX, pY, aYY);
/*     */           }
/*     */         };
/* 549 */       this.partialDerivativeXY = new BivariateFunction()
/*     */         {
/*     */           public double value(double x, double y) {
/* 552 */             double x2 = x * x;
/* 553 */             double[] pX = { 0.0D, 1.0D, x, x2 };
/*     */             
/* 555 */             double y2 = y * y;
/* 556 */             double[] pY = { 0.0D, 1.0D, y, y2 };
/*     */             
/* 558 */             return BicubicSplineFunction.this.apply(pX, pY, aXY);
/*     */           }
/*     */         };
/*     */     } else {
/* 562 */       this.partialDerivativeX = null;
/* 563 */       this.partialDerivativeY = null;
/* 564 */       this.partialDerivativeXX = null;
/* 565 */       this.partialDerivativeYY = null;
/* 566 */       this.partialDerivativeXY = null;
/*     */     } 
/*     */   }
/*     */   private final BivariateFunction partialDerivativeXX;
/*     */   private final BivariateFunction partialDerivativeYY;
/*     */   private final BivariateFunction partialDerivativeXY;
/*     */   
/*     */   public double value(double x, double y) {
/* 574 */     if (x < 0.0D || x > 1.0D) {
/* 575 */       throw new OutOfRangeException(Double.valueOf(x), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/* 577 */     if (y < 0.0D || y > 1.0D) {
/* 578 */       throw new OutOfRangeException(Double.valueOf(y), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/* 581 */     double x2 = x * x;
/* 582 */     double x3 = x2 * x;
/* 583 */     double[] pX = { 1.0D, x, x2, x3 };
/*     */     
/* 585 */     double y2 = y * y;
/* 586 */     double y3 = y2 * y;
/* 587 */     double[] pY = { 1.0D, y, y2, y3 };
/*     */     
/* 589 */     return apply(pX, pY, this.a);
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
/*     */   private double apply(double[] pX, double[] pY, double[][] coeff) {
/* 601 */     double result = 0.0D;
/* 602 */     for (int i = 0; i < 4; i++) {
/* 603 */       for (int j = 0; j < 4; j++) {
/* 604 */         result += coeff[i][j] * pX[i] * pY[j];
/*     */       }
/*     */     } 
/*     */     
/* 608 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BivariateFunction partialDerivativeX() {
/* 615 */     return this.partialDerivativeX;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BivariateFunction partialDerivativeY() {
/* 621 */     return this.partialDerivativeY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BivariateFunction partialDerivativeXX() {
/* 627 */     return this.partialDerivativeXX;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BivariateFunction partialDerivativeYY() {
/* 633 */     return this.partialDerivativeYY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BivariateFunction partialDerivativeXY() {
/* 639 */     return this.partialDerivativeXY;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/BicubicSplineFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */