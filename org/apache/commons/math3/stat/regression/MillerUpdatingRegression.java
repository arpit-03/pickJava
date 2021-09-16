/*      */ package org.apache.commons.math3.stat.regression;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathArrays;
/*      */ import org.apache.commons.math3.util.Precision;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MillerUpdatingRegression
/*      */   implements UpdatingMultipleLinearRegression
/*      */ {
/*      */   private final int nvars;
/*      */   private final double[] d;
/*      */   private final double[] rhs;
/*      */   private final double[] r;
/*      */   private final double[] tol;
/*      */   private final double[] rss;
/*      */   private final int[] vorder;
/*      */   private final double[] work_tolset;
/*   61 */   private long nobs = 0L;
/*      */   
/*   63 */   private double sserr = 0.0D;
/*      */   
/*      */   private boolean rss_set = false;
/*      */   
/*      */   private boolean tol_set = false;
/*      */   
/*      */   private final boolean[] lindep;
/*      */   
/*      */   private final double[] x_sing;
/*      */   
/*      */   private final double[] work_sing;
/*      */   
/*   75 */   private double sumy = 0.0D;
/*      */   
/*   77 */   private double sumsqy = 0.0D;
/*      */ 
/*      */   
/*      */   private boolean hasIntercept;
/*      */ 
/*      */   
/*      */   private final double epsilon;
/*      */ 
/*      */ 
/*      */   
/*      */   private MillerUpdatingRegression() {
/*   88 */     this(-1, false, Double.NaN);
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
/*      */   public MillerUpdatingRegression(int numberOfVariables, boolean includeConstant, double errorTolerance) throws ModelSpecificationException {
/*  101 */     if (numberOfVariables < 1) {
/*  102 */       throw new ModelSpecificationException(LocalizedFormats.NO_REGRESSORS, new Object[0]);
/*      */     }
/*  104 */     if (includeConstant) {
/*  105 */       this.nvars = numberOfVariables + 1;
/*      */     } else {
/*  107 */       this.nvars = numberOfVariables;
/*      */     } 
/*  109 */     this.hasIntercept = includeConstant;
/*  110 */     this.nobs = 0L;
/*  111 */     this.d = new double[this.nvars];
/*  112 */     this.rhs = new double[this.nvars];
/*  113 */     this.r = new double[this.nvars * (this.nvars - 1) / 2];
/*  114 */     this.tol = new double[this.nvars];
/*  115 */     this.rss = new double[this.nvars];
/*  116 */     this.vorder = new int[this.nvars];
/*  117 */     this.x_sing = new double[this.nvars];
/*  118 */     this.work_sing = new double[this.nvars];
/*  119 */     this.work_tolset = new double[this.nvars];
/*  120 */     this.lindep = new boolean[this.nvars];
/*  121 */     for (int i = 0; i < this.nvars; i++) {
/*  122 */       this.vorder[i] = i;
/*      */     }
/*  124 */     if (errorTolerance > 0.0D) {
/*  125 */       this.epsilon = errorTolerance;
/*      */     } else {
/*  127 */       this.epsilon = -errorTolerance;
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
/*      */   public MillerUpdatingRegression(int numberOfVariables, boolean includeConstant) throws ModelSpecificationException {
/*  140 */     this(numberOfVariables, includeConstant, Precision.EPSILON);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasIntercept() {
/*  148 */     return this.hasIntercept;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getN() {
/*  156 */     return this.nobs;
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
/*      */   public void addObservation(double[] x, double y) throws ModelSpecificationException {
/*  169 */     if ((!this.hasIntercept && x.length != this.nvars) || (this.hasIntercept && x.length + 1 != this.nvars))
/*      */     {
/*  171 */       throw new ModelSpecificationException(LocalizedFormats.INVALID_REGRESSION_OBSERVATION, new Object[] { Integer.valueOf(x.length), Integer.valueOf(this.nvars) });
/*      */     }
/*      */     
/*  174 */     if (!this.hasIntercept) {
/*  175 */       include(MathArrays.copyOf(x, x.length), 1.0D, y);
/*      */     } else {
/*  177 */       double[] tmp = new double[x.length + 1];
/*  178 */       System.arraycopy(x, 0, tmp, 1, x.length);
/*  179 */       tmp[0] = 1.0D;
/*  180 */       include(tmp, 1.0D, y);
/*      */     } 
/*  182 */     this.nobs++;
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
/*      */   public void addObservations(double[][] x, double[] y) throws ModelSpecificationException {
/*  194 */     if (x == null || y == null || x.length != y.length) {
/*  195 */       throw new ModelSpecificationException(LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, new Object[] { Integer.valueOf((x == null) ? 0 : x.length), Integer.valueOf((y == null) ? 0 : y.length) });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  200 */     if (x.length == 0) {
/*  201 */       throw new ModelSpecificationException(LocalizedFormats.NO_DATA, new Object[0]);
/*      */     }
/*      */     
/*  204 */     if ((x[0]).length + 1 > x.length) {
/*  205 */       throw new ModelSpecificationException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, new Object[] { Integer.valueOf(x.length), Integer.valueOf((x[0]).length) });
/*      */     }
/*      */ 
/*      */     
/*  209 */     for (int i = 0; i < x.length; i++) {
/*  210 */       addObservation(x[i], y[i]);
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
/*      */   private void include(double[] x, double wi, double yi) {
/*  229 */     int nextr = 0;
/*  230 */     double w = wi;
/*  231 */     double y = yi;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  238 */     this.rss_set = false;
/*  239 */     this.sumy = smartAdd(yi, this.sumy);
/*  240 */     this.sumsqy = smartAdd(this.sumsqy, yi * yi);
/*  241 */     for (int i = 0; i < x.length; i++) {
/*  242 */       if (w == 0.0D) {
/*      */         return;
/*      */       }
/*  245 */       double xi = x[i];
/*      */       
/*  247 */       if (xi == 0.0D) {
/*  248 */         nextr += this.nvars - i - 1;
/*      */       } else {
/*      */         
/*  251 */         double dpi, di = this.d[i];
/*  252 */         double wxi = w * xi;
/*  253 */         double _w = w;
/*  254 */         if (di != 0.0D) {
/*  255 */           dpi = smartAdd(di, wxi * xi);
/*  256 */           double tmp = wxi * xi / di;
/*  257 */           if (FastMath.abs(tmp) > Precision.EPSILON) {
/*  258 */             w = di * w / dpi;
/*      */           }
/*      */         } else {
/*  261 */           dpi = wxi * xi;
/*  262 */           w = 0.0D;
/*      */         } 
/*  264 */         this.d[i] = dpi;
/*  265 */         for (int k = i + 1; k < this.nvars; k++) {
/*  266 */           double d = x[k];
/*  267 */           x[k] = smartAdd(d, -xi * this.r[nextr]);
/*  268 */           if (di != 0.0D) {
/*  269 */             this.r[nextr] = smartAdd(di * this.r[nextr], _w * xi * d) / dpi;
/*      */           } else {
/*  271 */             this.r[nextr] = d / xi;
/*      */           } 
/*  273 */           nextr++;
/*      */         } 
/*  275 */         double xk = y;
/*  276 */         y = smartAdd(xk, -xi * this.rhs[i]);
/*  277 */         if (di != 0.0D) {
/*  278 */           this.rhs[i] = smartAdd(di * this.rhs[i], wxi * xk) / dpi;
/*      */         } else {
/*  280 */           this.rhs[i] = xk / xi;
/*      */         } 
/*      */       } 
/*  283 */     }  this.sserr = smartAdd(this.sserr, w * y * y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double smartAdd(double a, double b) {
/*  294 */     double _a = FastMath.abs(a);
/*  295 */     double _b = FastMath.abs(b);
/*  296 */     if (_a > _b) {
/*  297 */       double d = _a * Precision.EPSILON;
/*  298 */       if (_b > d) {
/*  299 */         return a + b;
/*      */       }
/*  301 */       return a;
/*      */     } 
/*  303 */     double eps = _b * Precision.EPSILON;
/*  304 */     if (_a > eps) {
/*  305 */       return a + b;
/*      */     }
/*  307 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  316 */     Arrays.fill(this.d, 0.0D);
/*  317 */     Arrays.fill(this.rhs, 0.0D);
/*  318 */     Arrays.fill(this.r, 0.0D);
/*  319 */     Arrays.fill(this.tol, 0.0D);
/*  320 */     Arrays.fill(this.rss, 0.0D);
/*  321 */     Arrays.fill(this.work_tolset, 0.0D);
/*  322 */     Arrays.fill(this.work_sing, 0.0D);
/*  323 */     Arrays.fill(this.x_sing, 0.0D);
/*  324 */     Arrays.fill(this.lindep, false);
/*  325 */     for (int i = 0; i < this.nvars; i++) {
/*  326 */       this.vorder[i] = i;
/*      */     }
/*  328 */     this.nobs = 0L;
/*  329 */     this.sserr = 0.0D;
/*  330 */     this.sumy = 0.0D;
/*  331 */     this.sumsqy = 0.0D;
/*  332 */     this.rss_set = false;
/*  333 */     this.tol_set = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void tolset() {
/*  342 */     double eps = this.epsilon;
/*  343 */     for (int i = 0; i < this.nvars; i++) {
/*  344 */       this.work_tolset[i] = FastMath.sqrt(this.d[i]);
/*      */     }
/*  346 */     this.tol[0] = eps * this.work_tolset[0];
/*  347 */     for (int col = 1; col < this.nvars; col++) {
/*  348 */       int pos = col - 1;
/*  349 */       double total = this.work_tolset[col];
/*  350 */       for (int row = 0; row < col; row++) {
/*  351 */         total += FastMath.abs(this.r[pos]) * this.work_tolset[row];
/*  352 */         pos += this.nvars - row - 2;
/*      */       } 
/*  354 */       this.tol[col] = eps * total;
/*      */     } 
/*  356 */     this.tol_set = true;
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
/*      */   private double[] regcf(int nreq) throws ModelSpecificationException {
/*  372 */     if (nreq < 1) {
/*  373 */       throw new ModelSpecificationException(LocalizedFormats.NO_REGRESSORS, new Object[0]);
/*      */     }
/*  375 */     if (nreq > this.nvars) {
/*  376 */       throw new ModelSpecificationException(LocalizedFormats.TOO_MANY_REGRESSORS, new Object[] { Integer.valueOf(nreq), Integer.valueOf(this.nvars) });
/*      */     }
/*      */     
/*  379 */     if (!this.tol_set) {
/*  380 */       tolset();
/*      */     }
/*  382 */     double[] ret = new double[nreq];
/*  383 */     boolean rankProblem = false; int i;
/*  384 */     for (i = nreq - 1; i > -1; i--) {
/*  385 */       if (FastMath.sqrt(this.d[i]) < this.tol[i]) {
/*  386 */         ret[i] = 0.0D;
/*  387 */         this.d[i] = 0.0D;
/*  388 */         rankProblem = true;
/*      */       } else {
/*  390 */         ret[i] = this.rhs[i];
/*  391 */         int nextr = i * (this.nvars + this.nvars - i - 1) / 2;
/*  392 */         for (int j = i + 1; j < nreq; j++) {
/*  393 */           ret[i] = smartAdd(ret[i], -this.r[nextr] * ret[j]);
/*  394 */           nextr++;
/*      */         } 
/*      */       } 
/*      */     } 
/*  398 */     if (rankProblem) {
/*  399 */       for (i = 0; i < nreq; i++) {
/*  400 */         if (this.lindep[i]) {
/*  401 */           ret[i] = Double.NaN;
/*      */         }
/*      */       } 
/*      */     }
/*  405 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void singcheck() {
/*  414 */     for (int i = 0; i < this.nvars; i++) {
/*  415 */       this.work_sing[i] = FastMath.sqrt(this.d[i]);
/*      */     }
/*  417 */     for (int col = 0; col < this.nvars; col++) {
/*      */ 
/*      */ 
/*      */       
/*  421 */       double temp = this.tol[col];
/*  422 */       int pos = col - 1;
/*  423 */       for (int row = 0; row < col - 1; row++) {
/*  424 */         if (FastMath.abs(this.r[pos]) * this.work_sing[row] < temp) {
/*  425 */           this.r[pos] = 0.0D;
/*      */         }
/*  427 */         pos += this.nvars - row - 2;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  432 */       this.lindep[col] = false;
/*  433 */       if (this.work_sing[col] < temp) {
/*  434 */         this.lindep[col] = true;
/*  435 */         if (col < this.nvars - 1) {
/*  436 */           Arrays.fill(this.x_sing, 0.0D);
/*  437 */           int _pi = col * (this.nvars + this.nvars - col - 1) / 2;
/*  438 */           for (int _xi = col + 1; _xi < this.nvars; _xi++, _pi++) {
/*  439 */             this.x_sing[_xi] = this.r[_pi];
/*  440 */             this.r[_pi] = 0.0D;
/*      */           } 
/*  442 */           double y = this.rhs[col];
/*  443 */           double weight = this.d[col];
/*  444 */           this.d[col] = 0.0D;
/*  445 */           this.rhs[col] = 0.0D;
/*  446 */           include(this.x_sing, weight, y);
/*      */         } else {
/*  448 */           this.sserr += this.d[col] * this.rhs[col] * this.rhs[col];
/*      */         } 
/*      */       } 
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
/*      */   private void ss() {
/*  464 */     double total = this.sserr;
/*  465 */     this.rss[this.nvars - 1] = this.sserr;
/*  466 */     for (int i = this.nvars - 1; i > 0; i--) {
/*  467 */       total += this.d[i] * this.rhs[i] * this.rhs[i];
/*  468 */       this.rss[i - 1] = total;
/*      */     } 
/*  470 */     this.rss_set = true;
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
/*      */   private double[] cov(int nreq) {
/*  492 */     if (this.nobs <= nreq) {
/*  493 */       return null;
/*      */     }
/*  495 */     double rnk = 0.0D;
/*  496 */     for (int i = 0; i < nreq; i++) {
/*  497 */       if (!this.lindep[i]) {
/*  498 */         rnk++;
/*      */       }
/*      */     } 
/*  501 */     double var = this.rss[nreq - 1] / (this.nobs - rnk);
/*  502 */     double[] rinv = new double[nreq * (nreq - 1) / 2];
/*  503 */     inverse(rinv, nreq);
/*  504 */     double[] covmat = new double[nreq * (nreq + 1) / 2];
/*  505 */     Arrays.fill(covmat, Double.NaN);
/*      */ 
/*      */     
/*  508 */     int start = 0;
/*  509 */     double total = 0.0D;
/*  510 */     for (int row = 0; row < nreq; row++) {
/*  511 */       int pos2 = start;
/*  512 */       if (!this.lindep[row]) {
/*  513 */         for (int col = row; col < nreq; col++) {
/*  514 */           if (!this.lindep[col]) {
/*  515 */             int pos1 = start + col - row;
/*  516 */             if (row == col) {
/*  517 */               total = 1.0D / this.d[col];
/*      */             } else {
/*  519 */               total = rinv[pos1 - 1] / this.d[col];
/*      */             } 
/*  521 */             for (int k = col + 1; k < nreq; k++) {
/*  522 */               if (!this.lindep[k]) {
/*  523 */                 total += rinv[pos1] * rinv[pos2] / this.d[k];
/*      */               }
/*  525 */               pos1++;
/*  526 */               pos2++;
/*      */             } 
/*  528 */             covmat[(col + 1) * col / 2 + row] = total * var;
/*      */           } else {
/*  530 */             pos2 += nreq - col - 1;
/*      */           } 
/*      */         } 
/*      */       }
/*  534 */       start += nreq - row - 1;
/*      */     } 
/*  536 */     return covmat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void inverse(double[] rinv, int nreq) {
/*  547 */     int pos = nreq * (nreq - 1) / 2 - 1;
/*  548 */     int pos1 = -1;
/*  549 */     int pos2 = -1;
/*  550 */     double total = 0.0D;
/*  551 */     Arrays.fill(rinv, Double.NaN);
/*  552 */     for (int row = nreq - 1; row > 0; row--) {
/*  553 */       if (!this.lindep[row]) {
/*  554 */         int start = (row - 1) * (this.nvars + this.nvars - row) / 2;
/*  555 */         for (int col = nreq; col > row; col--) {
/*  556 */           pos1 = start;
/*  557 */           pos2 = pos;
/*  558 */           total = 0.0D;
/*  559 */           for (int k = row; k < col - 1; k++) {
/*  560 */             pos2 += nreq - k - 1;
/*  561 */             if (!this.lindep[k]) {
/*  562 */               total += -this.r[pos1] * rinv[pos2];
/*      */             }
/*  564 */             pos1++;
/*      */           } 
/*  566 */           rinv[pos] = total - this.r[pos1];
/*  567 */           pos--;
/*      */         } 
/*      */       } else {
/*  570 */         pos -= nreq - row;
/*      */       } 
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
/*      */   public double[] getPartialCorrelations(int in) {
/*  609 */     double[] output = new double[(this.nvars - in + 1) * (this.nvars - in) / 2];
/*      */ 
/*      */ 
/*      */     
/*  613 */     int rms_off = -in;
/*  614 */     int wrk_off = -(in + 1);
/*  615 */     double[] rms = new double[this.nvars - in];
/*  616 */     double[] work = new double[this.nvars - in - 1];
/*      */ 
/*      */ 
/*      */     
/*  620 */     int offXX = (this.nvars - in) * (this.nvars - in - 1) / 2;
/*  621 */     if (in < -1 || in >= this.nvars) {
/*  622 */       return null;
/*      */     }
/*  624 */     int nvm = this.nvars - 1;
/*  625 */     int base_pos = this.r.length - (nvm - in) * (nvm - in + 1) / 2;
/*  626 */     if (this.d[in] > 0.0D) {
/*  627 */       rms[in + rms_off] = 1.0D / FastMath.sqrt(this.d[in]);
/*      */     }
/*  629 */     for (int col = in + 1; col < this.nvars; col++) {
/*  630 */       int i = base_pos + col - 1 - in;
/*  631 */       double sumxx = this.d[col];
/*  632 */       for (int j = in; j < col; j++) {
/*  633 */         sumxx += this.d[j] * this.r[i] * this.r[i];
/*  634 */         i += this.nvars - j - 2;
/*      */       } 
/*  636 */       if (sumxx > 0.0D) {
/*  637 */         rms[col + rms_off] = 1.0D / FastMath.sqrt(sumxx);
/*      */       } else {
/*  639 */         rms[col + rms_off] = 0.0D;
/*      */       } 
/*      */     } 
/*  642 */     double sumyy = this.sserr;
/*  643 */     for (int row = in; row < this.nvars; row++) {
/*  644 */       sumyy += this.d[row] * this.rhs[row] * this.rhs[row];
/*      */     }
/*  646 */     if (sumyy > 0.0D) {
/*  647 */       sumyy = 1.0D / FastMath.sqrt(sumyy);
/*      */     }
/*  649 */     int pos = 0;
/*  650 */     for (int col1 = in; col1 < this.nvars; col1++) {
/*  651 */       double sumxy = 0.0D;
/*  652 */       Arrays.fill(work, 0.0D);
/*  653 */       int pos1 = base_pos + col1 - in - 1;
/*  654 */       for (int i = in; i < col1; i++) {
/*  655 */         int j = pos1 + 1;
/*  656 */         for (int k = col1 + 1; k < this.nvars; k++) {
/*  657 */           work[k + wrk_off] = work[k + wrk_off] + this.d[i] * this.r[pos1] * this.r[j];
/*  658 */           j++;
/*      */         } 
/*  660 */         sumxy += this.d[i] * this.r[pos1] * this.rhs[i];
/*  661 */         pos1 += this.nvars - i - 2;
/*      */       } 
/*  663 */       int pos2 = pos1 + 1;
/*  664 */       for (int col2 = col1 + 1; col2 < this.nvars; col2++) {
/*  665 */         work[col2 + wrk_off] = work[col2 + wrk_off] + this.d[col1] * this.r[pos2];
/*  666 */         pos2++;
/*  667 */         output[(col2 - 1 - in) * (col2 - in) / 2 + col1 - in] = work[col2 + wrk_off] * rms[col1 + rms_off] * rms[col2 + rms_off];
/*      */         
/*  669 */         pos++;
/*      */       } 
/*  671 */       sumxy += this.d[col1] * this.rhs[col1];
/*  672 */       output[col1 + rms_off + offXX] = sumxy * rms[col1 + rms_off] * sumyy;
/*      */     } 
/*      */     
/*  675 */     return output;
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
/*      */   private void vmove(int from, int to) {
/*      */     int first, inc;
/*  701 */     boolean bSkipTo40 = false;
/*  702 */     if (from == to) {
/*      */       return;
/*      */     }
/*  705 */     if (!this.rss_set) {
/*  706 */       ss();
/*      */     }
/*  708 */     int count = 0;
/*  709 */     if (from < to) {
/*  710 */       first = from;
/*  711 */       inc = 1;
/*  712 */       count = to - from;
/*      */     } else {
/*  714 */       first = from - 1;
/*  715 */       inc = -1;
/*  716 */       count = from - to;
/*      */     } 
/*      */     
/*  719 */     int m = first;
/*  720 */     int idx = 0;
/*  721 */     while (idx < count) {
/*  722 */       int m1 = m * (this.nvars + this.nvars - m - 1) / 2;
/*  723 */       int m2 = m1 + this.nvars - m - 1;
/*  724 */       int mp1 = m + 1;
/*      */       
/*  726 */       double d1 = this.d[m];
/*  727 */       double d2 = this.d[mp1];
/*      */       
/*  729 */       if (d1 > this.epsilon || d2 > this.epsilon) {
/*  730 */         double d = this.r[m1];
/*  731 */         if (FastMath.abs(d) * FastMath.sqrt(d1) < this.tol[mp1]) {
/*  732 */           d = 0.0D;
/*      */         }
/*  734 */         if (d1 < this.epsilon || FastMath.abs(d) < this.epsilon) {
/*  735 */           this.d[m] = d2;
/*  736 */           this.d[mp1] = d1;
/*  737 */           this.r[m1] = 0.0D;
/*  738 */           for (int col = m + 2; col < this.nvars; col++) {
/*  739 */             m1++;
/*  740 */             d = this.r[m1];
/*  741 */             this.r[m1] = this.r[m2];
/*  742 */             this.r[m2] = d;
/*  743 */             m2++;
/*      */           } 
/*  745 */           d = this.rhs[m];
/*  746 */           this.rhs[m] = this.rhs[mp1];
/*  747 */           this.rhs[mp1] = d;
/*  748 */           bSkipTo40 = true;
/*      */         }
/*  750 */         else if (d2 < this.epsilon) {
/*  751 */           this.d[m] = d1 * d * d;
/*  752 */           this.r[m1] = 1.0D / d;
/*  753 */           for (int _i = m1 + 1; _i < m1 + this.nvars - m - 1; _i++) {
/*  754 */             this.r[_i] = this.r[_i] / d;
/*      */           }
/*  756 */           this.rhs[m] = this.rhs[m] / d;
/*  757 */           bSkipTo40 = true;
/*      */         } 
/*      */         
/*  760 */         if (!bSkipTo40) {
/*  761 */           double d1new = d2 + d1 * d * d;
/*  762 */           double cbar = d2 / d1new;
/*  763 */           double sbar = d * d1 / d1new;
/*  764 */           double d2new = d1 * cbar;
/*  765 */           this.d[m] = d1new;
/*  766 */           this.d[mp1] = d2new;
/*  767 */           this.r[m1] = sbar;
/*  768 */           for (int col = m + 2; col < this.nvars; col++) {
/*  769 */             m1++;
/*  770 */             double d3 = this.r[m1];
/*  771 */             this.r[m1] = cbar * this.r[m2] + sbar * d3;
/*  772 */             this.r[m2] = d3 - d * this.r[m2];
/*  773 */             m2++;
/*      */           } 
/*  775 */           double Y = this.rhs[m];
/*  776 */           this.rhs[m] = cbar * this.rhs[mp1] + sbar * Y;
/*  777 */           this.rhs[mp1] = Y - d * this.rhs[mp1];
/*      */         } 
/*      */       } 
/*  780 */       if (m > 0) {
/*  781 */         int pos = m;
/*  782 */         for (int row = 0; row < m; row++) {
/*  783 */           double d = this.r[pos];
/*  784 */           this.r[pos] = this.r[pos - 1];
/*  785 */           this.r[pos - 1] = d;
/*  786 */           pos += this.nvars - row - 2;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  791 */       m1 = this.vorder[m];
/*  792 */       this.vorder[m] = this.vorder[mp1];
/*  793 */       this.vorder[mp1] = m1;
/*  794 */       double X = this.tol[m];
/*  795 */       this.tol[m] = this.tol[mp1];
/*  796 */       this.tol[mp1] = X;
/*  797 */       this.rss[m] = this.rss[mp1] + this.d[mp1] * this.rhs[mp1] * this.rhs[mp1];
/*      */       
/*  799 */       m += inc;
/*  800 */       idx++;
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
/*      */   private int reorderRegressors(int[] list, int pos1) {
/*  823 */     if (list.length < 1 || list.length > this.nvars + 1 - pos1) {
/*  824 */       return -1;
/*      */     }
/*  826 */     int next = pos1;
/*  827 */     int i = pos1;
/*  828 */     while (i < this.nvars) {
/*  829 */       int l = this.vorder[i];
/*  830 */       for (int j = 0; j < list.length; j++) {
/*  831 */         if (l == list[j] && i > next) {
/*  832 */           vmove(i, next);
/*  833 */           next++;
/*  834 */           if (next >= list.length + pos1) {
/*  835 */             return 0;
/*      */           }
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  841 */       i++;
/*      */     } 
/*  843 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDiagonalOfHatMatrix(double[] row_data) {
/*  853 */     double[] xrow, wk = new double[this.nvars];
/*      */ 
/*      */ 
/*      */     
/*  857 */     if (row_data.length > this.nvars) {
/*  858 */       return Double.NaN;
/*      */     }
/*      */     
/*  861 */     if (this.hasIntercept) {
/*  862 */       xrow = new double[row_data.length + 1];
/*  863 */       xrow[0] = 1.0D;
/*  864 */       System.arraycopy(row_data, 0, xrow, 1, row_data.length);
/*      */     } else {
/*  866 */       xrow = row_data;
/*      */     } 
/*  868 */     double hii = 0.0D;
/*  869 */     for (int col = 0; col < xrow.length; col++) {
/*  870 */       if (FastMath.sqrt(this.d[col]) < this.tol[col]) {
/*  871 */         wk[col] = 0.0D;
/*      */       } else {
/*  873 */         int pos = col - 1;
/*  874 */         double total = xrow[col];
/*  875 */         for (int row = 0; row < col; row++) {
/*  876 */           total = smartAdd(total, -wk[row] * this.r[pos]);
/*  877 */           pos += this.nvars - row - 2;
/*      */         } 
/*  879 */         wk[col] = total;
/*  880 */         hii = smartAdd(hii, total * total / this.d[col]);
/*      */       } 
/*      */     } 
/*  883 */     return hii;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getOrderOfRegressors() {
/*  894 */     return MathArrays.copyOf(this.vorder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RegressionResults regress() throws ModelSpecificationException {
/*  905 */     return regress(this.nvars);
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
/*      */   public RegressionResults regress(int numberOfRegressors) throws ModelSpecificationException {
/*  919 */     if (this.nobs <= numberOfRegressors) {
/*  920 */       throw new ModelSpecificationException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, new Object[] { Long.valueOf(this.nobs), Integer.valueOf(numberOfRegressors) });
/*      */     }
/*      */ 
/*      */     
/*  924 */     if (numberOfRegressors > this.nvars) {
/*  925 */       throw new ModelSpecificationException(LocalizedFormats.TOO_MANY_REGRESSORS, new Object[] { Integer.valueOf(numberOfRegressors), Integer.valueOf(this.nvars) });
/*      */     }
/*      */ 
/*      */     
/*  929 */     tolset();
/*  930 */     singcheck();
/*      */     
/*  932 */     double[] beta = regcf(numberOfRegressors);
/*      */     
/*  934 */     ss();
/*      */     
/*  936 */     double[] cov = cov(numberOfRegressors);
/*      */     
/*  938 */     int rnk = 0;
/*  939 */     for (int i = 0; i < this.lindep.length; i++) {
/*  940 */       if (!this.lindep[i]) {
/*  941 */         rnk++;
/*      */       }
/*      */     } 
/*      */     
/*  945 */     boolean needsReorder = false;
/*  946 */     for (int j = 0; j < numberOfRegressors; j++) {
/*  947 */       if (this.vorder[j] != j) {
/*  948 */         needsReorder = true;
/*      */         break;
/*      */       } 
/*      */     } 
/*  952 */     if (!needsReorder) {
/*  953 */       return new RegressionResults(beta, new double[][] { cov }, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
/*      */     }
/*      */ 
/*      */     
/*  957 */     double[] betaNew = new double[beta.length];
/*  958 */     double[] covNew = new double[cov.length];
/*      */     
/*  960 */     int[] newIndices = new int[beta.length];
/*  961 */     for (int k = 0; k < this.nvars; k++) {
/*  962 */       for (int n = 0; n < numberOfRegressors; n++) {
/*  963 */         if (this.vorder[n] == k) {
/*  964 */           betaNew[k] = beta[n];
/*  965 */           newIndices[k] = n;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  970 */     int idx1 = 0;
/*      */ 
/*      */ 
/*      */     
/*  974 */     for (int m = 0; m < beta.length; m++) {
/*  975 */       int _i = newIndices[m];
/*  976 */       for (int n = 0; n <= m; n++, idx1++) {
/*  977 */         int idx2, _j = newIndices[n];
/*  978 */         if (_i > _j) {
/*  979 */           idx2 = _i * (_i + 1) / 2 + _j;
/*      */         } else {
/*  981 */           idx2 = _j * (_j + 1) / 2 + _i;
/*      */         } 
/*  983 */         covNew[idx1] = cov[idx2];
/*      */       } 
/*      */     } 
/*  986 */     return new RegressionResults(betaNew, new double[][] { covNew }, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
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
/*      */   public RegressionResults regress(int[] variablesToInclude) throws ModelSpecificationException {
/*      */     int[] series;
/* 1005 */     if (variablesToInclude.length > this.nvars) {
/* 1006 */       throw new ModelSpecificationException(LocalizedFormats.TOO_MANY_REGRESSORS, new Object[] { Integer.valueOf(variablesToInclude.length), Integer.valueOf(this.nvars) });
/*      */     }
/*      */     
/* 1009 */     if (this.nobs <= this.nvars) {
/* 1010 */       throw new ModelSpecificationException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, new Object[] { Long.valueOf(this.nobs), Integer.valueOf(this.nvars) });
/*      */     }
/*      */ 
/*      */     
/* 1014 */     Arrays.sort(variablesToInclude);
/* 1015 */     int iExclude = 0;
/* 1016 */     for (int i = 0; i < variablesToInclude.length; i++) {
/* 1017 */       if (i >= this.nvars) {
/* 1018 */         throw new ModelSpecificationException(LocalizedFormats.INDEX_LARGER_THAN_MAX, new Object[] { Integer.valueOf(i), Integer.valueOf(this.nvars) });
/*      */       }
/*      */       
/* 1021 */       if (i > 0 && variablesToInclude[i] == variablesToInclude[i - 1]) {
/* 1022 */         variablesToInclude[i] = -1;
/* 1023 */         iExclude++;
/*      */       } 
/*      */     } 
/*      */     
/* 1027 */     if (iExclude > 0) {
/* 1028 */       int i1 = 0;
/* 1029 */       series = new int[variablesToInclude.length - iExclude];
/* 1030 */       for (int i2 = 0; i2 < variablesToInclude.length; i2++) {
/* 1031 */         if (variablesToInclude[i2] > -1) {
/* 1032 */           series[i1] = variablesToInclude[i2];
/* 1033 */           i1++;
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1037 */       series = variablesToInclude;
/*      */     } 
/*      */     
/* 1040 */     reorderRegressors(series, 0);
/* 1041 */     tolset();
/* 1042 */     singcheck();
/*      */     
/* 1044 */     double[] beta = regcf(series.length);
/*      */     
/* 1046 */     ss();
/*      */     
/* 1048 */     double[] cov = cov(series.length);
/*      */     
/* 1050 */     int rnk = 0;
/* 1051 */     for (int j = 0; j < this.lindep.length; j++) {
/* 1052 */       if (!this.lindep[j]) {
/* 1053 */         rnk++;
/*      */       }
/*      */     } 
/*      */     
/* 1057 */     boolean needsReorder = false;
/* 1058 */     for (int k = 0; k < this.nvars; k++) {
/* 1059 */       if (this.vorder[k] != series[k]) {
/* 1060 */         needsReorder = true;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1064 */     if (!needsReorder) {
/* 1065 */       return new RegressionResults(beta, new double[][] { cov }, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
/*      */     }
/*      */ 
/*      */     
/* 1069 */     double[] betaNew = new double[beta.length];
/* 1070 */     int[] newIndices = new int[beta.length];
/* 1071 */     for (int m = 0; m < series.length; m++) {
/* 1072 */       for (int i1 = 0; i1 < this.vorder.length; i1++) {
/* 1073 */         if (this.vorder[i1] == series[m]) {
/* 1074 */           betaNew[m] = beta[i1];
/* 1075 */           newIndices[m] = i1;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1079 */     double[] covNew = new double[cov.length];
/* 1080 */     int idx1 = 0;
/*      */ 
/*      */ 
/*      */     
/* 1084 */     for (int n = 0; n < beta.length; n++) {
/* 1085 */       int _i = newIndices[n];
/* 1086 */       for (int i1 = 0; i1 <= n; i1++, idx1++) {
/* 1087 */         int idx2, _j = newIndices[i1];
/* 1088 */         if (_i > _j) {
/* 1089 */           idx2 = _i * (_i + 1) / 2 + _j;
/*      */         } else {
/* 1091 */           idx2 = _j * (_j + 1) / 2 + _i;
/*      */         } 
/* 1093 */         covNew[idx1] = cov[idx2];
/*      */       } 
/*      */     } 
/* 1096 */     return new RegressionResults(betaNew, new double[][] { covNew }, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/regression/MillerUpdatingRegression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */