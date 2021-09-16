/*      */ package edu.mines.jtk.lapack;
/*      */ 
/*      */ import edu.mines.jtk.util.ArrayMath;
/*      */ import edu.mines.jtk.util.Check;
/*      */ import org.netlib.blas.BLAS;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DMatrix
/*      */ {
/*      */   public DMatrix(int m, int n) {
/*   57 */     this._m = m;
/*   58 */     this._n = n;
/*   59 */     this._a = new double[m * n];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix(int m, int n, double v) {
/*   69 */     this(m, n);
/*   70 */     ArrayMath.fill(v, this._a);
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
/*      */   public DMatrix(int m, int n, double[] a) {
/*   86 */     Check.argument((m * n <= a.length), "m*n <= a.length");
/*   87 */     this._m = m;
/*   88 */     this._n = n;
/*   89 */     this._a = a;
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
/*      */   public DMatrix(double[][] a) {
/*  103 */     Check.argument(ArrayMath.isRegular(a), "array a is regular");
/*  104 */     this._m = a.length;
/*  105 */     this._n = (a[0]).length;
/*  106 */     this._a = new double[this._m * this._n];
/*  107 */     set(a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix(DMatrix a) {
/*  115 */     this(a._m, a._n, ArrayMath.copy(a._a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getM() {
/*  123 */     return this._m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowCount() {
/*  131 */     return this._m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getN() {
/*  139 */     return this._n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnCount() {
/*  147 */     return this._n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] getArray() {
/*  155 */     return this._a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSquare() {
/*  163 */     return (this._m == this._n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSymmetric() {
/*  171 */     if (!isSquare())
/*  172 */       return false; 
/*  173 */     for (int i = 0; i < this._n; i++) {
/*  174 */       for (int j = i + 1; j < this._n; j++)
/*  175 */       { if (this._a[i + j * this._m] != this._a[j + i * this._m])
/*  176 */           return false;  } 
/*  177 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[][] get() {
/*  185 */     double[][] a = new double[this._m][this._n];
/*  186 */     get(a);
/*  187 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void get(double[][] a) {
/*  195 */     for (int i = 0; i < this._m; i++) {
/*  196 */       double[] ai = a[i];
/*  197 */       for (int j = 0; j < this._n; j++) {
/*  198 */         ai[j] = this._a[i + j * this._m];
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
/*      */   public double get(int i, int j) {
/*  210 */     return this._a[i + j * this._m];
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
/*      */   public DMatrix get(int i0, int i1, int j0, int j1) {
/*  222 */     checkI(i0, i1);
/*  223 */     checkJ(j0, j1);
/*  224 */     int m = i1 - i0 + 1;
/*  225 */     int n = j1 - j0 + 1;
/*  226 */     double[] b = new double[m * n];
/*  227 */     for (int j = 0; j < n; j++) {
/*  228 */       for (int i = 0; i < m; i++)
/*  229 */         b[i + j * m] = this._a[i + i0 + (j + j0) * this._m]; 
/*  230 */     }  return new DMatrix(m, n, b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix get(int[] r, int[] c) {
/*  240 */     if (r == null && c == null) {
/*  241 */       return new DMatrix(this._m, this._n, ArrayMath.copy(this._a));
/*      */     }
/*  243 */     int m = (r != null) ? r.length : this._m;
/*  244 */     int n = (c != null) ? c.length : this._n;
/*  245 */     double[] b = new double[m * n];
/*  246 */     if (r == null)
/*  247 */     { for (int j = 0; j < n; j++)
/*  248 */       { for (int i = 0; i < m; i++)
/*  249 */           b[i + j * m] = this._a[i + c[j] * this._m];  }  }
/*  250 */     else if (c == null)
/*  251 */     { for (int j = 0; j < n; j++) {
/*  252 */         for (int i = 0; i < m; i++)
/*  253 */           b[i + j * m] = this._a[r[i] + j * this._m]; 
/*      */       }  }
/*  255 */     else { for (int j = 0; j < n; j++) {
/*  256 */         for (int i = 0; i < m; i++)
/*  257 */           b[i + j * m] = this._a[r[i] + c[j] * this._m]; 
/*      */       }  }
/*  259 */      return new DMatrix(m, n, b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix get(int i, int[] c) {
/*  270 */     return get(i, i, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix get(int[] r, int j) {
/*  280 */     return get(r, j, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix get(int i0, int i1, int[] c) {
/*  291 */     checkI(i0, i1);
/*  292 */     if (c == null) {
/*  293 */       return get(i0, i1, 0, this._n - 1);
/*      */     }
/*  295 */     int m = i1 - i0 + 1;
/*  296 */     int n = c.length;
/*  297 */     double[] b = new double[m * n];
/*  298 */     for (int j = 0; j < n; j++) {
/*  299 */       for (int i = i0; i <= i1; i++) {
/*  300 */         b[i - i0 + j * m] = this._a[i + c[j] * this._m];
/*      */       }
/*      */     } 
/*  303 */     return new DMatrix(m, n, b);
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
/*      */   public DMatrix get(int[] r, int j0, int j1) {
/*  315 */     checkJ(j0, j1);
/*  316 */     if (r == null) {
/*  317 */       return get(0, this._m - 1, j0, j1);
/*      */     }
/*  319 */     int m = r.length;
/*  320 */     int n = j1 - j0 + 1;
/*  321 */     double[] b = new double[m * n];
/*  322 */     for (int j = j0; j <= j1; j++) {
/*  323 */       for (int i = 0; i < m; i++) {
/*  324 */         b[i + (j - j0) * m] = this._a[r[i] + j * this._m];
/*      */       }
/*      */     } 
/*  327 */     return new DMatrix(m, n, b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] getPackedColumns() {
/*  336 */     return ArrayMath.copy(this._a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] getPackedRows() {
/*  344 */     double[] r = new double[this._m * this._n];
/*  345 */     for (int j = 0; j < this._n; j++) {
/*  346 */       for (int i = 0; i < this._m; i++)
/*  347 */         r[i * this._n + j] = this._a[i + j * this._m]; 
/*  348 */     }  return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(double[][] a) {
/*  357 */     for (int i = 0; i < this._m; i++) {
/*  358 */       double[] ai = a[i];
/*  359 */       for (int j = 0; j < this._n; j++) {
/*  360 */         this._a[i + j * this._m] = ai[j];
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
/*      */   public void set(int i, int j, double v) {
/*  372 */     this._a[i + j * this._m] = v;
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
/*      */   public void set(int i0, int i1, int j0, int j1, DMatrix x) {
/*  384 */     checkI(i0, i1);
/*  385 */     checkJ(j0, j1);
/*  386 */     int m = i1 - i0 + 1;
/*  387 */     int n = j1 - j0 + 1;
/*  388 */     Check.argument((m == x._m), "i1-i0+1 equals number of rows in x");
/*  389 */     Check.argument((n == x._n), "j1-j0+1 equals number of columns in x");
/*  390 */     double[] b = x._a;
/*  391 */     for (int i = 0; i < m; i++) {
/*  392 */       for (int j = 0; j < n; j++) {
/*  393 */         this._a[i + i0 + (j + j0) * this._m] = b[i + j * m];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(int[] r, int[] c, DMatrix x) {
/*  403 */     if (r == null) {
/*  404 */       Check.argument((this._m == x._m), "number of rows equal in this and x");
/*      */     } else {
/*  406 */       Check.argument((r.length == x._m), "r.length equals number of rows in x");
/*      */     } 
/*  408 */     if (c == null) {
/*  409 */       Check.argument((this._n == x._n), "number of columns equal in this and x");
/*      */     } else {
/*  411 */       Check.argument((c.length == x._n), "c.length equals number of columns in x");
/*      */     } 
/*  413 */     if (r == null && c == null) {
/*  414 */       ArrayMath.copy(x._a, this._a);
/*      */     } else {
/*  416 */       int m = (r != null) ? r.length : this._m;
/*  417 */       int n = (c != null) ? c.length : this._n;
/*  418 */       double[] b = x._a;
/*  419 */       if (r == null)
/*  420 */       { for (int j = 0; j < n; j++)
/*  421 */         { for (int i = 0; i < m; i++)
/*  422 */             this._a[i + c[j] * this._m] = b[i + j * m];  }  }
/*  423 */       else if (c == null)
/*  424 */       { for (int j = 0; j < n; j++) {
/*  425 */           for (int i = 0; i < m; i++)
/*  426 */             this._a[r[i] + j * this._m] = b[i + j * m]; 
/*      */         }  }
/*  428 */       else { for (int j = 0; j < n; j++) {
/*  429 */           for (int i = 0; i < m; i++) {
/*  430 */             this._a[r[i] + c[j] * this._m] = b[i + j * m];
/*      */           }
/*      */         }  }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(int i, int[] c, DMatrix x) {
/*  442 */     set(i, i, c, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(int[] r, int j, DMatrix x) {
/*  452 */     set(r, j, j, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(int i0, int i1, int[] c, DMatrix x) {
/*  463 */     checkI(i0, i1);
/*  464 */     Check.argument((i1 - i0 + 1 == x._m), "i1-i0+1 equals number of rows in x");
/*  465 */     if (c == null) {
/*  466 */       set(i0, i1, 0, this._n - 1, x);
/*      */     } else {
/*  468 */       int m = i1 - i0 + 1;
/*  469 */       int n = c.length;
/*  470 */       double[] b = x._a;
/*  471 */       for (int j = 0; j < n; j++) {
/*  472 */         for (int i = i0; i <= i1; i++) {
/*  473 */           this._a[i + c[j] * this._m] = b[i - i0 + j * m];
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
/*      */   public void set(int[] r, int j0, int j1, DMatrix x) {
/*  487 */     checkJ(j0, j1);
/*  488 */     Check.argument((j1 - j0 + 1 == x._n), "j1-j0+1 equals number of columns in x");
/*  489 */     if (r == null) {
/*  490 */       set(0, this._m - 1, j0, j1, x);
/*      */     } else {
/*  492 */       int m = r.length;
/*  493 */       double[] b = x._a;
/*  494 */       for (int j = j0; j <= j1; j++) {
/*  495 */         for (int i = 0; i < m; i++) {
/*  496 */           this._a[r[i] + j * this._m] = b[i + (j - j0) * m];
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPackedColumns(double[] c) {
/*  507 */     ArrayMath.copy(this._m * this._n, c, this._a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPackedRows(double[] r) {
/*  515 */     for (int j = 0; j < this._n; j++) {
/*  516 */       for (int i = 0; i < this._m; i++) {
/*  517 */         this._a[i + j * this._m] = r[i * this._n + j];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix transpose() {
/*  525 */     DMatrix x = new DMatrix(this._n, this._m);
/*  526 */     double[] b = x._a;
/*  527 */     for (int j = 0; j < this._n; j++) {
/*  528 */       for (int i = 0; i < this._m; i++) {
/*  529 */         b[j + i * this._n] = this._a[i + j * this._m];
/*      */       }
/*      */     } 
/*  532 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double norm1() {
/*  540 */     double f = 0.0D;
/*  541 */     for (int j = 0; j < this._n; j++) {
/*  542 */       double s = 0.0D;
/*  543 */       for (int i = 0; i < this._m; i++)
/*  544 */         s += ArrayMath.abs(this._a[i + j * this._m]); 
/*  545 */       f = ArrayMath.max(f, s);
/*      */     } 
/*  547 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double norm2() {
/*  556 */     return (new DMatrixSvd(this)).norm2();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double normI() {
/*  564 */     double f = 0.0D;
/*  565 */     for (int i = 0; i < this._m; i++) {
/*  566 */       double s = 0.0D;
/*  567 */       for (int j = 0; j < this._n; j++)
/*  568 */         s += ArrayMath.abs(this._a[i + j * this._m]); 
/*  569 */       f = ArrayMath.max(f, s);
/*      */     } 
/*  571 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double normF() {
/*  579 */     double f = 0.0D;
/*  580 */     for (int j = 0; j < this._n; j++) {
/*  581 */       for (int i = 0; i < this._m; i++) {
/*  582 */         f = ArrayMath.hypot(f, this._a[i + j * this._m]);
/*      */       }
/*      */     } 
/*  585 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double det() {
/*  593 */     return (new DMatrixLud(this)).det();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double cond() {
/*  602 */     return (new DMatrixSvd(this)).cond();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double rank() {
/*  611 */     return (new DMatrixSvd(this)).rank();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double trace() {
/*  619 */     int mn = ArrayMath.min(this._m, this._n);
/*  620 */     double t = 0.0D;
/*  621 */     for (int i = 0; i < mn; i++)
/*  622 */       t += this._a[i + i * this._m]; 
/*  623 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrixLud lud() {
/*  631 */     return new DMatrixLud(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrixQrd qrd() {
/*  639 */     return new DMatrixQrd(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrixChd chd() {
/*  647 */     return new DMatrixChd(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrixEvd evd() {
/*  655 */     return new DMatrixEvd(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrixSvd svd() {
/*  663 */     return new DMatrixSvd(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix solve(DMatrix b) {
/*  673 */     Check.state((this._m >= this._n), "number of rows is not less than number of columns");
/*  674 */     if (this._m == this._n) {
/*  675 */       return (new DMatrixLud(this)).solve(b);
/*      */     }
/*  677 */     return (new DMatrixQrd(this)).solve(b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix inverse() {
/*  687 */     return solve(identity(this._m, this._m));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix negate() {
/*  695 */     DMatrix c = new DMatrix(this._m, this._n);
/*  696 */     ArrayMath.neg(this._a, c._a);
/*  697 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix plus(DMatrix b) {
/*  706 */     DMatrix c = new DMatrix(this._m, this._n);
/*  707 */     ArrayMath.add(this._a, b._a, c._a);
/*  708 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix plusEquals(DMatrix b) {
/*  717 */     ArrayMath.add(this._a, b._a, this._a);
/*  718 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix minus(DMatrix b) {
/*  727 */     DMatrix c = new DMatrix(this._m, this._n);
/*  728 */     ArrayMath.sub(this._a, b._a, c._a);
/*  729 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix minusEquals(DMatrix b) {
/*  738 */     ArrayMath.sub(this._a, b._a, this._a);
/*  739 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix arrayTimes(DMatrix b) {
/*  749 */     DMatrix c = new DMatrix(this._m, this._n);
/*  750 */     ArrayMath.mul(this._a, b._a, c._a);
/*  751 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix arrayTimesEquals(DMatrix b) {
/*  761 */     ArrayMath.mul(this._a, b._a, this._a);
/*  762 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix arrayRightDivide(DMatrix b) {
/*  772 */     DMatrix c = new DMatrix(this._m, this._n);
/*  773 */     ArrayMath.div(this._a, b._a, c._a);
/*  774 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix arrayRightDivideEquals(DMatrix b) {
/*  784 */     ArrayMath.div(this._a, b._a, this._a);
/*  785 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix arrayLeftDivide(DMatrix b) {
/*  795 */     DMatrix c = new DMatrix(this._m, this._n);
/*  796 */     ArrayMath.div(b._a, this._a, c._a);
/*  797 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix arrayLeftDivideEquals(DMatrix b) {
/*  807 */     ArrayMath.div(b._a, this._a, this._a);
/*  808 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix times(double s) {
/*  817 */     DMatrix c = new DMatrix(this._m, this._n);
/*  818 */     ArrayMath.mul(this._a, s, c._a);
/*  819 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix timesEquals(double s) {
/*  828 */     ArrayMath.mul(this._a, s, this._a);
/*  829 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix times(DMatrix b) {
/*  839 */     Check.argument((this._n == b._m), "number of columns in A equals number of rows in B");
/*      */     
/*  841 */     DMatrix c = new DMatrix(this._m, b._n);
/*  842 */     _blas.dgemm("N", "N", this._m, b._n, this._n, 1.0D, this._a, this._m, b._a, b._m, 1.0D, c._a, c._m);
/*  843 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix timesTranspose(DMatrix b) {
/*  854 */     Check.argument((this._n == b._n), "number of columns in A equals number of columns in B");
/*      */     
/*  856 */     DMatrix c = new DMatrix(this._m, b._m);
/*  857 */     _blas.dgemm("N", "T", this._m, b._n, this._n, 1.0D, this._a, this._m, b._a, b._m, 1.0D, c._a, c._m);
/*  858 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DMatrix transposeTimes(DMatrix b) {
/*  869 */     Check.argument((this._m == b._m), "number of rows in A equals number of rows in B");
/*      */     
/*  871 */     DMatrix c = new DMatrix(this._n, b._n);
/*  872 */     _blas.dgemm("T", "N", this._n, b._n, this._m, 1.0D, this._a, this._m, b._a, b._m, 1.0D, c._a, c._m);
/*  873 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DMatrix random(int m, int n) {
/*  884 */     DMatrix x = new DMatrix(m, n);
/*  885 */     ArrayMath.rand(x._a);
/*  886 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DMatrix random(int n) {
/*  896 */     return random(n, n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DMatrix identity(int m, int n) {
/*  906 */     DMatrix x = new DMatrix(m, n);
/*  907 */     double[] xa = x._a;
/*  908 */     int mn = ArrayMath.min(m, n);
/*  909 */     for (int i = 0; i < mn; i++)
/*  910 */       xa[i + i * m] = 1.0D; 
/*  911 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DMatrix identity(int n) {
/*  920 */     return identity(n, n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DMatrix diagonal(double[] d) {
/*  929 */     int n = d.length;
/*  930 */     double[] a = new double[n * n];
/*  931 */     for (int i = 0; i < n; i++)
/*  932 */       a[i + i * n] = d[i]; 
/*  933 */     return new DMatrix(n, n, a);
/*      */   }
/*      */   
/*      */   public boolean equals(Object obj) {
/*  937 */     if (this == obj)
/*  938 */       return true; 
/*  939 */     if (obj == null || getClass() != obj.getClass())
/*  940 */       return false; 
/*  941 */     DMatrix that = (DMatrix)obj;
/*  942 */     if (this._m != that._m || this._n != that._n)
/*  943 */       return false; 
/*  944 */     double[] a = this._a;
/*  945 */     double[] b = that._a;
/*  946 */     int mn = this._m * this._n;
/*  947 */     for (int i = 0; i < mn; i++) {
/*  948 */       if (a[i] != b[i])
/*  949 */         return false; 
/*      */     } 
/*  951 */     return true;
/*      */   }
/*      */   
/*      */   public int hashCode() {
/*  955 */     int h = this._m ^ this._n;
/*  956 */     int mn = this._m * this._n;
/*  957 */     for (int i = 0; i < mn; i++) {
/*  958 */       long bits = Double.doubleToLongBits(this._a[i]);
/*  959 */       h ^= (int)(bits ^ bits >>> 32L);
/*      */     } 
/*  961 */     return h;
/*      */   }
/*      */   
/*      */   public String toString() {
/*  965 */     String ls = System.getProperty("line.separator");
/*  966 */     StringBuilder sb = new StringBuilder();
/*  967 */     String[][] s = format(this._m, this._n, this._a);
/*  968 */     int max = maxlen(s);
/*  969 */     String format = "%" + max + "s";
/*  970 */     sb.append("[[");
/*  971 */     int ncol = 77 / (max + 2);
/*  972 */     if (ncol >= 5)
/*  973 */       ncol = ncol / 5 * 5; 
/*  974 */     for (int i = 0; i < this._m; i++) {
/*  975 */       int nrow = 1 + (this._n - 1) / ncol;
/*  976 */       if (i > 0)
/*  977 */         sb.append(" ["); 
/*  978 */       for (int irow = 0, j = 0; irow < nrow; irow++) {
/*  979 */         for (int icol = 0; icol < ncol && j < this._n; icol++, j++) {
/*  980 */           sb.append(String.format(format, new Object[] { s[i][j] }));
/*  981 */           if (j < this._n - 1)
/*  982 */             sb.append(", "); 
/*      */         } 
/*  984 */         if (j < this._n) {
/*  985 */           sb.append(ls);
/*  986 */           sb.append("  ");
/*      */         }
/*  988 */         else if (i < this._m - 1) {
/*  989 */           sb.append("],");
/*  990 */           sb.append(ls);
/*      */         } else {
/*  992 */           sb.append("]]");
/*  993 */           sb.append(ls);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  998 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1007 */   private static final BLAS _blas = BLAS.getInstance();
/*      */   
/*      */   private int _m;
/*      */   private int _n;
/*      */   private double[] _a;
/*      */   
/*      */   private void checkI(int i) {
/* 1014 */     if (i < 0 || i >= this._m)
/* 1015 */       Check.argument((0 <= i && i < this._m), "row index i=" + i + " is in bounds"); 
/*      */   }
/*      */   
/*      */   private void checkJ(int j) {
/* 1019 */     if (j < 0 || j >= this._n)
/* 1020 */       Check.argument((0 <= j && j < this._n), "column index j=" + j + " is in bounds"); 
/*      */   }
/*      */   
/*      */   private void checkI(int i0, int i1) {
/* 1024 */     checkI(i0); checkI(i1); Check.argument((i0 <= i1), "i0<=i1");
/*      */   }
/*      */   
/*      */   private void checkJ(int j0, int j1) {
/* 1028 */     checkJ(j0); checkJ(j1); Check.argument((j0 <= j1), "j0<=j1");
/*      */   }
/*      */   
/*      */   private static String[][] format(int m, int n, double[] d) {
/*      */     String f;
/* 1033 */     int pg = 6;
/* 1034 */     String fg = "% ." + pg + "g";
/* 1035 */     int pemax = -1;
/* 1036 */     int pfmax = -1;
/* 1037 */     for (int i = 0; i < m; i++) {
/* 1038 */       for (int k = 0; k < n; k++) {
/* 1039 */         String str = String.format(fg, new Object[] { Double.valueOf(d[i + k * m]) });
/* 1040 */         str = clean(str);
/* 1041 */         int ls = str.length();
/* 1042 */         if (str.contains("e")) {
/* 1043 */           int pe = (ls > 7) ? (ls - 7) : 0;
/* 1044 */           if (pemax < pe)
/* 1045 */             pemax = pe; 
/*      */         } else {
/* 1047 */           int ip = str.indexOf('.');
/* 1048 */           int pf = (ip >= 0) ? (ls - 1 - ip) : 0;
/* 1049 */           if (pfmax < pf)
/* 1050 */             pfmax = pf; 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1054 */     String[][] s = new String[m][n];
/*      */     
/* 1056 */     if (pemax >= 0) {
/* 1057 */       if (pfmax > pg - 1)
/* 1058 */         pfmax = pg - 1; 
/* 1059 */       int pe = (pemax > pfmax) ? pemax : pfmax;
/* 1060 */       f = "% ." + pe + "e";
/*      */     } else {
/* 1062 */       int pf = pfmax;
/* 1063 */       f = "% ." + pf + "f";
/*      */     } 
/* 1065 */     for (int j = 0; j < m; j++) {
/* 1066 */       for (int k = 0; k < n; k++) {
/* 1067 */         s[j][k] = String.format(f, new Object[] { Double.valueOf(d[j + k * m]) });
/*      */       } 
/*      */     } 
/* 1070 */     return s;
/*      */   }
/*      */   private static String clean(String s) {
/* 1073 */     int len = s.length();
/* 1074 */     int iend = s.indexOf('e');
/* 1075 */     if (iend < 0)
/* 1076 */       iend = s.indexOf('E'); 
/* 1077 */     if (iend < 0)
/* 1078 */       iend = len; 
/* 1079 */     int ibeg = iend;
/* 1080 */     if (s.indexOf('.') > 0) {
/* 1081 */       while (ibeg > 0 && s.charAt(ibeg - 1) == '0')
/* 1082 */         ibeg--; 
/* 1083 */       if (ibeg > 0 && s.charAt(ibeg - 1) == '.')
/* 1084 */         ibeg--; 
/*      */     } 
/* 1086 */     if (ibeg < iend) {
/* 1087 */       String sb = s.substring(0, ibeg);
/* 1088 */       s = (iend < len) ? (sb + s.substring(iend, len)) : sb;
/*      */     } 
/* 1090 */     return s;
/*      */   }
/*      */   private static int maxlen(String[][] s) {
/* 1093 */     int max = 0;
/* 1094 */     int m = s.length;
/* 1095 */     int n = (s[0]).length;
/* 1096 */     for (int i = 0; i < m; i++) {
/* 1097 */       for (int j = 0; j < n; j++) {
/* 1098 */         int len = s[i][j].length();
/* 1099 */         if (max < len)
/* 1100 */           max = len; 
/*      */       } 
/*      */     } 
/* 1103 */     return max;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/lapack/DMatrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */