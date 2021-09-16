/*      */ package org.apache.commons.math3.linear;
/*      */ 
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.util.ExceptionContext;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.IterationManager;
/*      */ import org.apache.commons.math3.util.MathUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SymmLQ
/*      */   extends PreconditionedIterativeLinearSolver
/*      */ {
/*      */   private static final String OPERATOR = "operator";
/*      */   private static final String THRESHOLD = "threshold";
/*      */   private static final String VECTOR = "vector";
/*      */   private static final String VECTOR1 = "vector1";
/*      */   private static final String VECTOR2 = "vector2";
/*      */   private final boolean check;
/*      */   private final double delta;
/*      */   
/*      */   private static class State
/*      */   {
/*  366 */     static final double CBRT_MACH_PREC = FastMath.cbrt(MACH_PREC);
/*      */     
/*      */     static final double MACH_PREC = FastMath.ulp(1.0D);
/*      */     
/*      */     private final RealLinearOperator a;
/*      */     
/*      */     private final RealVector b;
/*      */     
/*      */     private final boolean check;
/*      */     
/*      */     private final double delta;
/*      */     private double beta;
/*      */     private double beta1;
/*      */     private double bstep;
/*      */     private double cgnorm;
/*      */     private double dbar;
/*      */     private double gammaZeta;
/*      */     private double gbar;
/*      */     private double gmax;
/*      */     private double gmin;
/*      */     private final boolean goodb;
/*      */     private boolean hasConverged;
/*      */     
/*      */     State(RealLinearOperator a, RealLinearOperator m, RealVector b, boolean goodb, double shift, double delta, boolean check) {
/*  390 */       this.a = a;
/*  391 */       this.m = m;
/*  392 */       this.b = b;
/*  393 */       this.xL = new ArrayRealVector(b.getDimension());
/*  394 */       this.goodb = goodb;
/*  395 */       this.shift = shift;
/*  396 */       this.mb = (m == null) ? b : m.operate(b);
/*  397 */       this.hasConverged = false;
/*  398 */       this.check = check;
/*  399 */       this.delta = delta;
/*      */     }
/*      */ 
/*      */     
/*      */     private double lqnorm;
/*      */     
/*      */     private final RealLinearOperator m;
/*      */     
/*      */     private double minusEpsZeta;
/*      */     
/*      */     private final RealVector mb;
/*      */     
/*      */     private double oldb;
/*      */     
/*      */     private RealVector r1;
/*      */     private RealVector r2;
/*      */     private double rnorm;
/*      */     
/*      */     private static void checkSymmetry(RealLinearOperator l, RealVector x, RealVector y, RealVector z) throws NonSelfAdjointOperatorException {
/*  418 */       double s = y.dotProduct(y);
/*  419 */       double t = x.dotProduct(z);
/*  420 */       double epsa = (s + MACH_PREC) * CBRT_MACH_PREC;
/*  421 */       if (FastMath.abs(s - t) > epsa) {
/*      */         
/*  423 */         NonSelfAdjointOperatorException e = new NonSelfAdjointOperatorException();
/*  424 */         ExceptionContext context = e.getContext();
/*  425 */         context.setValue("operator", l);
/*  426 */         context.setValue("vector1", x);
/*  427 */         context.setValue("vector2", y);
/*  428 */         context.setValue("threshold", Double.valueOf(epsa));
/*  429 */         throw e;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private final double shift;
/*      */     
/*      */     private double snprod;
/*      */     
/*      */     private double tnorm;
/*      */     
/*      */     private RealVector wbar;
/*      */ 
/*      */     
/*      */     private static void throwNPDLOException(RealLinearOperator l, RealVector v) throws NonPositiveDefiniteOperatorException {
/*  444 */       NonPositiveDefiniteOperatorException e = new NonPositiveDefiniteOperatorException();
/*  445 */       ExceptionContext context = e.getContext();
/*  446 */       context.setValue("operator", l);
/*  447 */       context.setValue("vector", v);
/*  448 */       throw e;
/*      */     }
/*      */ 
/*      */     
/*      */     private final RealVector xL;
/*      */     private RealVector y;
/*      */     private double ynorm2;
/*      */     private boolean bIsNull;
/*      */     
/*      */     static {
/*      */     
/*      */     }
/*      */     
/*      */     private static void daxpy(double a, RealVector x, RealVector y) {
/*  462 */       int n = x.getDimension();
/*  463 */       for (int i = 0; i < n; i++) {
/*  464 */         y.setEntry(i, a * x.getEntry(i) + y.getEntry(i));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static void daxpbypz(double a, RealVector x, double b, RealVector y, RealVector z) {
/*  481 */       int n = z.getDimension();
/*  482 */       for (int i = 0; i < n; i++) {
/*      */         
/*  484 */         double zi = a * x.getEntry(i) + b * y.getEntry(i) + z.getEntry(i);
/*  485 */         z.setEntry(i, zi);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void refineSolution(RealVector x) {
/*  503 */       int n = this.xL.getDimension();
/*  504 */       if (this.lqnorm < this.cgnorm) {
/*  505 */         if (!this.goodb) {
/*  506 */           x.setSubVector(0, this.xL);
/*      */         } else {
/*  508 */           double step = this.bstep / this.beta1;
/*  509 */           for (int i = 0; i < n; i++) {
/*  510 */             double bi = this.mb.getEntry(i);
/*  511 */             double xi = this.xL.getEntry(i);
/*  512 */             x.setEntry(i, xi + step * bi);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  516 */         double anorm = FastMath.sqrt(this.tnorm);
/*  517 */         double diag = (this.gbar == 0.0D) ? (anorm * MACH_PREC) : this.gbar;
/*  518 */         double zbar = this.gammaZeta / diag;
/*  519 */         double step = (this.bstep + this.snprod * zbar) / this.beta1;
/*      */         
/*  521 */         if (!this.goodb) {
/*  522 */           for (int i = 0; i < n; i++) {
/*  523 */             double xi = this.xL.getEntry(i);
/*  524 */             double wi = this.wbar.getEntry(i);
/*  525 */             x.setEntry(i, xi + zbar * wi);
/*      */           } 
/*      */         } else {
/*  528 */           for (int i = 0; i < n; i++) {
/*  529 */             double xi = this.xL.getEntry(i);
/*  530 */             double wi = this.wbar.getEntry(i);
/*  531 */             double bi = this.mb.getEntry(i);
/*  532 */             x.setEntry(i, xi + zbar * wi + step * bi);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void init() {
/*  544 */       this.xL.set(0.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  549 */       this.r1 = this.b.copy();
/*  550 */       this.y = (this.m == null) ? this.b.copy() : this.m.operate(this.r1);
/*  551 */       if (this.m != null && this.check) {
/*  552 */         checkSymmetry(this.m, this.r1, this.y, this.m.operate(this.y));
/*      */       }
/*      */       
/*  555 */       this.beta1 = this.r1.dotProduct(this.y);
/*  556 */       if (this.beta1 < 0.0D) {
/*  557 */         throwNPDLOException(this.m, this.y);
/*      */       }
/*  559 */       if (this.beta1 == 0.0D) {
/*      */         
/*  561 */         this.bIsNull = true;
/*      */         return;
/*      */       } 
/*  564 */       this.bIsNull = false;
/*  565 */       this.beta1 = FastMath.sqrt(this.beta1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  571 */       RealVector v = this.y.mapMultiply(1.0D / this.beta1);
/*  572 */       this.y = this.a.operate(v);
/*  573 */       if (this.check) {
/*  574 */         checkSymmetry(this.a, v, this.y, this.a.operate(this.y));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  580 */       daxpy(-this.shift, v, this.y);
/*  581 */       double alpha = v.dotProduct(this.y);
/*  582 */       daxpy(-alpha / this.beta1, this.r1, this.y);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  589 */       double vty = v.dotProduct(this.y);
/*  590 */       double vtv = v.dotProduct(v);
/*  591 */       daxpy(-vty / vtv, v, this.y);
/*  592 */       this.r2 = this.y.copy();
/*  593 */       if (this.m != null) {
/*  594 */         this.y = this.m.operate(this.r2);
/*      */       }
/*  596 */       this.oldb = this.beta1;
/*  597 */       this.beta = this.r2.dotProduct(this.y);
/*  598 */       if (this.beta < 0.0D) {
/*  599 */         throwNPDLOException(this.m, this.y);
/*      */       }
/*  601 */       this.beta = FastMath.sqrt(this.beta);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  609 */       this.cgnorm = this.beta1;
/*  610 */       this.gbar = alpha;
/*  611 */       this.dbar = this.beta;
/*  612 */       this.gammaZeta = this.beta1;
/*  613 */       this.minusEpsZeta = 0.0D;
/*  614 */       this.bstep = 0.0D;
/*  615 */       this.snprod = 1.0D;
/*  616 */       this.tnorm = alpha * alpha + this.beta * this.beta;
/*  617 */       this.ynorm2 = 0.0D;
/*  618 */       this.gmax = FastMath.abs(alpha) + MACH_PREC;
/*  619 */       this.gmin = this.gmax;
/*      */       
/*  621 */       if (this.goodb) {
/*  622 */         this.wbar = new ArrayRealVector(this.a.getRowDimension());
/*  623 */         this.wbar.set(0.0D);
/*      */       } else {
/*  625 */         this.wbar = v;
/*      */       } 
/*  627 */       updateNorms();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void update() {
/*  637 */       RealVector v = this.y.mapMultiply(1.0D / this.beta);
/*  638 */       this.y = this.a.operate(v);
/*  639 */       daxpbypz(-this.shift, v, -this.beta / this.oldb, this.r1, this.y);
/*  640 */       double alpha = v.dotProduct(this.y);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  651 */       daxpy(-alpha / this.beta, this.r2, this.y);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  665 */       this.r1 = this.r2;
/*  666 */       this.r2 = this.y;
/*  667 */       if (this.m != null) {
/*  668 */         this.y = this.m.operate(this.r2);
/*      */       }
/*  670 */       this.oldb = this.beta;
/*  671 */       this.beta = this.r2.dotProduct(this.y);
/*  672 */       if (this.beta < 0.0D) {
/*  673 */         throwNPDLOException(this.m, this.y);
/*      */       }
/*  675 */       this.beta = FastMath.sqrt(this.beta);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  684 */       this.tnorm += alpha * alpha + this.oldb * this.oldb + this.beta * this.beta;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  692 */       double gamma = FastMath.sqrt(this.gbar * this.gbar + this.oldb * this.oldb);
/*  693 */       double c = this.gbar / gamma;
/*  694 */       double s = this.oldb / gamma;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  704 */       double deltak = c * this.dbar + s * alpha;
/*  705 */       this.gbar = s * this.dbar - c * alpha;
/*  706 */       double eps = s * this.beta;
/*  707 */       this.dbar = -c * this.beta;
/*  708 */       double zeta = this.gammaZeta / gamma;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  717 */       double zetaC = zeta * c;
/*  718 */       double zetaS = zeta * s;
/*  719 */       int n = this.xL.getDimension();
/*  720 */       for (int i = 0; i < n; i++) {
/*  721 */         double xi = this.xL.getEntry(i);
/*  722 */         double vi = v.getEntry(i);
/*  723 */         double wi = this.wbar.getEntry(i);
/*  724 */         this.xL.setEntry(i, xi + wi * zetaC + vi * zetaS);
/*  725 */         this.wbar.setEntry(i, wi * s - vi * c);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  733 */       this.bstep += this.snprod * c * zeta;
/*  734 */       this.snprod *= s;
/*  735 */       this.gmax = FastMath.max(this.gmax, gamma);
/*  736 */       this.gmin = FastMath.min(this.gmin, gamma);
/*  737 */       this.ynorm2 += zeta * zeta;
/*  738 */       this.gammaZeta = this.minusEpsZeta - deltak * zeta;
/*  739 */       this.minusEpsZeta = -eps * zeta;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  752 */       updateNorms();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void updateNorms() {
/*  760 */       double acond, anorm = FastMath.sqrt(this.tnorm);
/*  761 */       double ynorm = FastMath.sqrt(this.ynorm2);
/*  762 */       double epsa = anorm * MACH_PREC;
/*  763 */       double epsx = anorm * ynorm * MACH_PREC;
/*  764 */       double epsr = anorm * ynorm * this.delta;
/*  765 */       double diag = (this.gbar == 0.0D) ? epsa : this.gbar;
/*  766 */       this.lqnorm = FastMath.sqrt(this.gammaZeta * this.gammaZeta + this.minusEpsZeta * this.minusEpsZeta);
/*      */       
/*  768 */       double qrnorm = this.snprod * this.beta1;
/*  769 */       this.cgnorm = qrnorm * this.beta / FastMath.abs(diag);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  778 */       if (this.lqnorm <= this.cgnorm) {
/*  779 */         acond = this.gmax / this.gmin;
/*      */       } else {
/*  781 */         acond = this.gmax / FastMath.min(this.gmin, FastMath.abs(diag));
/*      */       } 
/*  783 */       if (acond * MACH_PREC >= 0.1D) {
/*  784 */         throw new IllConditionedOperatorException(acond);
/*      */       }
/*  786 */       if (this.beta1 <= epsx)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  791 */         throw new SingularOperatorException();
/*      */       }
/*  793 */       this.rnorm = FastMath.min(this.cgnorm, this.lqnorm);
/*  794 */       this.hasConverged = (this.cgnorm <= epsx || this.cgnorm <= epsr);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean hasConverged() {
/*  803 */       return this.hasConverged;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bEqualsNullVector() {
/*  812 */       return this.bIsNull;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean betaEqualsZero() {
/*  822 */       return (this.beta < MACH_PREC);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     double getNormOfResidual() {
/*  831 */       return this.rnorm;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SymmLQ(int maxIterations, double delta, boolean check) {
/*  871 */     super(maxIterations);
/*  872 */     this.delta = delta;
/*  873 */     this.check = check;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SymmLQ(IterationManager manager, double delta, boolean check) {
/*  889 */     super(manager);
/*  890 */     this.delta = delta;
/*  891 */     this.check = check;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getCheck() {
/*  901 */     return this.check;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector solve(RealLinearOperator a, RealLinearOperator m, RealVector b) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException, NonSelfAdjointOperatorException, NonPositiveDefiniteOperatorException, IllConditionedOperatorException {
/*  920 */     MathUtils.checkNotNull(a);
/*  921 */     RealVector x = new ArrayRealVector(a.getColumnDimension());
/*  922 */     return solveInPlace(a, m, b, x, false, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector solve(RealLinearOperator a, RealLinearOperator m, RealVector b, boolean goodb, double shift) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException, NonSelfAdjointOperatorException, NonPositiveDefiniteOperatorException, IllConditionedOperatorException {
/*  971 */     MathUtils.checkNotNull(a);
/*  972 */     RealVector x = new ArrayRealVector(a.getColumnDimension());
/*  973 */     return solveInPlace(a, m, b, x, goodb, shift);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector solve(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, NonPositiveDefiniteOperatorException, IllConditionedOperatorException, MaxCountExceededException {
/*  994 */     MathUtils.checkNotNull(x);
/*  995 */     return solveInPlace(a, m, b, x.copy(), false, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector solve(RealLinearOperator a, RealVector b) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, IllConditionedOperatorException, MaxCountExceededException {
/* 1010 */     MathUtils.checkNotNull(a);
/* 1011 */     RealVector x = new ArrayRealVector(a.getColumnDimension());
/* 1012 */     x.set(0.0D);
/* 1013 */     return solveInPlace(a, (RealLinearOperator)null, b, x, false, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector solve(RealLinearOperator a, RealVector b, boolean goodb, double shift) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, IllConditionedOperatorException, MaxCountExceededException {
/* 1056 */     MathUtils.checkNotNull(a);
/* 1057 */     RealVector x = new ArrayRealVector(a.getColumnDimension());
/* 1058 */     return solveInPlace(a, (RealLinearOperator)null, b, x, goodb, shift);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector solve(RealLinearOperator a, RealVector b, RealVector x) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, IllConditionedOperatorException, MaxCountExceededException {
/* 1076 */     MathUtils.checkNotNull(x);
/* 1077 */     return solveInPlace(a, (RealLinearOperator)null, b, x.copy(), false, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector solveInPlace(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, NonPositiveDefiniteOperatorException, IllConditionedOperatorException, MaxCountExceededException {
/* 1098 */     return solveInPlace(a, m, b, x, false, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector solveInPlace(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x, boolean goodb, double shift) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, NonPositiveDefiniteOperatorException, IllConditionedOperatorException, MaxCountExceededException {
/* 1150 */     checkParameters(a, m, b, x);
/*      */     
/* 1152 */     IterationManager manager = getIterationManager();
/*      */     
/* 1154 */     manager.resetIterationCount();
/* 1155 */     manager.incrementIterationCount();
/*      */ 
/*      */     
/* 1158 */     State state = new State(a, m, b, goodb, shift, this.delta, this.check);
/* 1159 */     state.init();
/* 1160 */     state.refineSolution(x);
/*      */     
/* 1162 */     IterativeLinearSolverEvent event = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), x, b, state.getNormOfResidual());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1167 */     if (state.bEqualsNullVector()) {
/*      */       
/* 1169 */       manager.fireTerminationEvent(event);
/* 1170 */       return x;
/*      */     } 
/*      */ 
/*      */     
/* 1174 */     boolean earlyStop = (state.betaEqualsZero() || state.hasConverged());
/* 1175 */     manager.fireInitializationEvent(event);
/* 1176 */     if (!earlyStop) {
/*      */       do {
/* 1178 */         manager.incrementIterationCount();
/* 1179 */         event = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), x, b, state.getNormOfResidual());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1184 */         manager.fireIterationStartedEvent(event);
/* 1185 */         state.update();
/* 1186 */         state.refineSolution(x);
/* 1187 */         event = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), x, b, state.getNormOfResidual());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1192 */         manager.fireIterationPerformedEvent(event);
/* 1193 */       } while (!state.hasConverged());
/*      */     }
/* 1195 */     event = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), x, b, state.getNormOfResidual());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1200 */     manager.fireTerminationEvent(event);
/* 1201 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector solveInPlace(RealLinearOperator a, RealVector b, RealVector x) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, IllConditionedOperatorException, MaxCountExceededException {
/* 1219 */     return solveInPlace(a, (RealLinearOperator)null, b, x, false, 0.0D);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/SymmLQ.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */