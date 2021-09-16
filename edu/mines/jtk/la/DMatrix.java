/*     */ package edu.mines.jtk.la;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DMatrix
/*     */ {
/*     */   private int _m;
/*     */   private int _n;
/*     */   private double[][] _a;
/*     */   
/*     */   public DMatrix(int m, int n) {
/*  41 */     this._m = m;
/*  42 */     this._n = n;
/*  43 */     this._a = new double[m][n];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix(int m, int n, double v) {
/*  53 */     this(m, n);
/*  54 */     ArrayMath.fill(v, this._a);
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
/*     */   public DMatrix(double[][] a) {
/*  68 */     Check.argument(ArrayMath.isRegular(a), "array a is regular");
/*  69 */     this._m = a.length;
/*  70 */     this._n = (a[0]).length;
/*  71 */     this._a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix(DMatrix a) {
/*  79 */     this(a._m, a._n, ArrayMath.copy(a._a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getM() {
/*  87 */     return this._m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/*  95 */     return this._m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getN() {
/* 103 */     return this._n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 111 */     return this._n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getArray() {
/* 119 */     return this._a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSquare() {
/* 127 */     return (this._m == this._n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSymmetric() {
/* 135 */     if (!isSquare())
/* 136 */       return false; 
/* 137 */     for (int i = 0; i < this._n; i++) {
/* 138 */       for (int j = i + 1; j < this._n; j++)
/* 139 */       { if (this._a[i][j] != this._a[j][i])
/* 140 */           return false;  } 
/* 141 */     }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] get() {
/* 149 */     return ArrayMath.copy(this._a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void get(double[][] a) {
/* 157 */     ArrayMath.copy(this._a, a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int i, int j) {
/* 167 */     return this._a[i][j];
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
/*     */   public DMatrix get(int i0, int i1, int j0, int j1) {
/* 179 */     checkI(i0, i1);
/* 180 */     checkJ(j0, j1);
/* 181 */     int m = i1 - i0 + 1;
/* 182 */     int n = j1 - j0 + 1;
/* 183 */     DMatrix x = new DMatrix(m, n);
/* 184 */     ArrayMath.copy(n, m, j0, i0, this._a, 0, 0, x._a);
/* 185 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix get(int[] r, int[] c) {
/* 195 */     if (r == null && c == null) {
/* 196 */       return new DMatrix(this._m, this._n, ArrayMath.copy(this._a));
/*     */     }
/* 198 */     int m = (r != null) ? r.length : this._m;
/* 199 */     int n = (c != null) ? c.length : this._n;
/* 200 */     double[][] b = new double[m][n];
/* 201 */     if (r == null)
/* 202 */     { for (int i = 0; i < m; i++)
/* 203 */       { for (int j = 0; j < n; j++)
/* 204 */           b[i][j] = this._a[i][c[j]];  }  }
/* 205 */     else if (c == null)
/* 206 */     { for (int i = 0; i < m; i++) {
/* 207 */         for (int j = 0; j < n; j++)
/* 208 */           b[i][j] = this._a[r[i]][j]; 
/*     */       }  }
/* 210 */     else { for (int i = 0; i < m; i++) {
/* 211 */         for (int j = 0; j < n; j++)
/* 212 */           b[i][j] = this._a[r[i]][c[j]]; 
/*     */       }  }
/* 214 */      return new DMatrix(m, n, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix get(int i, int[] c) {
/* 225 */     return get(i, i, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix get(int[] r, int j) {
/* 235 */     return get(r, j, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix get(int i0, int i1, int[] c) {
/* 246 */     checkI(i0, i1);
/* 247 */     if (c == null) {
/* 248 */       return get(i0, i1, 0, this._n - 1);
/*     */     }
/* 250 */     int m = i1 - i0 + 1;
/* 251 */     int n = c.length;
/* 252 */     double[][] b = new double[m][n];
/* 253 */     for (int i = i0; i <= i1; i++) {
/* 254 */       for (int j = 0; j < n; j++) {
/* 255 */         b[i - i0][j] = this._a[i][c[j]];
/*     */       }
/*     */     } 
/* 258 */     return new DMatrix(m, n, b);
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
/*     */   public DMatrix get(int[] r, int j0, int j1) {
/* 270 */     checkJ(j0, j1);
/* 271 */     if (r == null) {
/* 272 */       return get(0, this._m - 1, j0, j1);
/*     */     }
/* 274 */     int m = r.length;
/* 275 */     int n = j1 - j0 + 1;
/* 276 */     double[][] b = new double[m][n];
/* 277 */     for (int i = 0; i < m; i++) {
/* 278 */       for (int j = j0; j <= j1; j++) {
/* 279 */         b[i][j - j0] = this._a[r[i]][j];
/*     */       }
/*     */     } 
/* 282 */     return new DMatrix(m, n, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getPackedColumns() {
/* 291 */     double[] c = new double[this._m * this._n];
/* 292 */     for (int i = 0; i < this._m; i++) {
/* 293 */       for (int j = 0; j < this._n; j++)
/* 294 */         c[i + j * this._m] = this._a[i][j]; 
/* 295 */     }  return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getPackedRows() {
/* 303 */     double[] r = new double[this._m * this._n];
/* 304 */     for (int i = 0; i < this._m; i++) {
/* 305 */       for (int j = 0; j < this._n; j++)
/* 306 */         r[i * this._n + j] = this._a[i][j]; 
/* 307 */     }  return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(double[][] a) {
/* 316 */     for (int i = 0; i < this._m; i++) {
/* 317 */       for (int j = 0; j < this._n; j++) {
/* 318 */         this._a[i][j] = a[i][j];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int i, int j, double v) {
/* 328 */     this._a[i][j] = v;
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
/*     */   public void set(int i0, int i1, int j0, int j1, DMatrix x) {
/* 340 */     checkI(i0, i1);
/* 341 */     checkJ(j0, j1);
/* 342 */     int m = i1 - i0 + 1;
/* 343 */     int n = j1 - j0 + 1;
/* 344 */     Check.argument((m == x._m), "i1-i0+1 equals number of rows in x");
/* 345 */     Check.argument((n == x._n), "j1-j0+1 equals number of columns in x");
/* 346 */     ArrayMath.copy(n, m, 0, 0, x._a, j0, i0, this._a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int[] r, int[] c, DMatrix x) {
/* 356 */     if (r == null) {
/* 357 */       Check.argument((this._m == x._m), "number of rows equal in this and x");
/*     */     } else {
/* 359 */       Check.argument((r.length == x._m), "r.length equals number of rows in x");
/*     */     } 
/* 361 */     if (c == null) {
/* 362 */       Check.argument((this._n == x._n), "number of columns equal in this and x");
/*     */     } else {
/* 364 */       Check.argument((c.length == x._n), "c.length equals number of columns in x");
/*     */     } 
/* 366 */     if (r == null && c == null) {
/* 367 */       ArrayMath.copy(x._a, this._a);
/*     */     } else {
/* 369 */       int m = (r != null) ? r.length : this._m;
/* 370 */       int n = (c != null) ? c.length : this._n;
/* 371 */       double[][] b = x._a;
/* 372 */       if (r == null)
/* 373 */       { for (int i = 0; i < m; i++)
/* 374 */         { for (int j = 0; j < n; j++)
/* 375 */             this._a[i][c[j]] = b[i][j];  }  }
/* 376 */       else if (c == null)
/* 377 */       { for (int i = 0; i < m; i++) {
/* 378 */           for (int j = 0; j < n; j++)
/* 379 */             this._a[r[i]][j] = b[i][j]; 
/*     */         }  }
/* 381 */       else { for (int i = 0; i < m; i++) {
/* 382 */           for (int j = 0; j < n; j++) {
/* 383 */             this._a[r[i]][c[j]] = b[i][j];
/*     */           }
/*     */         }  }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int i, int[] c, DMatrix x) {
/* 395 */     set(i, i, c, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int[] r, int j, DMatrix x) {
/* 405 */     set(r, j, j, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int i0, int i1, int[] c, DMatrix x) {
/* 416 */     checkI(i0, i1);
/* 417 */     Check.argument((i1 - i0 + 1 == x._m), "i1-i0+1 equals number of rows in x");
/* 418 */     if (c == null) {
/* 419 */       set(i0, i1, 0, this._n - 1, x);
/*     */     } else {
/* 421 */       int n = c.length;
/* 422 */       double[][] b = x._a;
/* 423 */       for (int i = i0; i <= i1; i++) {
/* 424 */         for (int j = 0; j < n; j++) {
/* 425 */           this._a[i][c[j]] = b[i - i0][j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int[] r, int j0, int j1, DMatrix x) {
/* 439 */     checkJ(j0, j1);
/* 440 */     Check.argument((j1 - j0 + 1 == x._n), "j1-j0+1 equals number of columns in x");
/* 441 */     if (r == null) {
/* 442 */       set(0, this._m - 1, j0, j1, x);
/*     */     } else {
/* 444 */       int m = r.length;
/* 445 */       double[][] b = x._a;
/* 446 */       for (int i = 0; i < m; i++) {
/* 447 */         for (int j = j0; j <= j1; j++) {
/* 448 */           this._a[r[i]][j] = b[i][j - j0];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPackedColumns(double[] c) {
/* 459 */     for (int i = 0; i < this._m; i++) {
/* 460 */       for (int j = 0; j < this._n; j++) {
/* 461 */         this._a[i][j] = c[i + j * this._m];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPackedRows(double[] r) {
/* 469 */     for (int i = 0; i < this._m; i++) {
/* 470 */       for (int j = 0; j < this._n; j++) {
/* 471 */         this._a[i][j] = r[i * this._n + j];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix transpose() {
/* 479 */     DMatrix x = new DMatrix(this._n, this._m);
/* 480 */     double[][] b = x._a;
/* 481 */     for (int i = 0; i < this._m; i++) {
/* 482 */       for (int j = 0; j < this._n; j++) {
/* 483 */         b[j][i] = this._a[i][j];
/*     */       }
/*     */     } 
/* 486 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double norm1() {
/* 494 */     double f = 0.0D;
/* 495 */     for (int j = 0; j < this._n; j++) {
/* 496 */       double s = 0.0D;
/* 497 */       for (int i = 0; i < this._m; i++)
/* 498 */         s += ArrayMath.abs(this._a[i][j]); 
/* 499 */       f = ArrayMath.max(f, s);
/*     */     } 
/* 501 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double norm2() {
/* 509 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double normI() {
/* 517 */     double f = 0.0D;
/* 518 */     for (int i = 0; i < this._m; i++) {
/* 519 */       double s = 0.0D;
/* 520 */       for (int j = 0; j < this._n; j++)
/* 521 */         s += ArrayMath.abs(this._a[i][j]); 
/* 522 */       f = ArrayMath.max(f, s);
/*     */     } 
/* 524 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double normF() {
/* 532 */     double f = 0.0D;
/* 533 */     for (int i = 0; i < this._m; i++) {
/* 534 */       for (int j = 0; j < this._n; j++) {
/* 535 */         f = ArrayMath.hypot(f, this._a[i][j]);
/*     */       }
/*     */     } 
/* 538 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix negate() {
/* 546 */     DMatrix c = new DMatrix(this._m, this._n);
/* 547 */     ArrayMath.neg(this._a, c._a);
/* 548 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix plus(DMatrix b) {
/* 557 */     DMatrix c = new DMatrix(this._m, this._n);
/* 558 */     ArrayMath.add(this._a, b._a, c._a);
/* 559 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix plusEquals(DMatrix b) {
/* 568 */     ArrayMath.add(this._a, b._a, this._a);
/* 569 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix minus(DMatrix b) {
/* 578 */     DMatrix c = new DMatrix(this._m, this._n);
/* 579 */     ArrayMath.sub(this._a, b._a, c._a);
/* 580 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix minusEquals(DMatrix b) {
/* 589 */     ArrayMath.sub(this._a, b._a, this._a);
/* 590 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix arrayTimes(DMatrix b) {
/* 600 */     DMatrix c = new DMatrix(this._m, this._n);
/* 601 */     ArrayMath.mul(this._a, b._a, c._a);
/* 602 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix arrayTimesEquals(DMatrix b) {
/* 612 */     ArrayMath.mul(this._a, b._a, this._a);
/* 613 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix arrayRightDivide(DMatrix b) {
/* 623 */     DMatrix c = new DMatrix(this._m, this._n);
/* 624 */     ArrayMath.div(this._a, b._a, c._a);
/* 625 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix arrayRightDivideEquals(DMatrix b) {
/* 635 */     ArrayMath.div(this._a, b._a, this._a);
/* 636 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix arrayLeftDivide(DMatrix b) {
/* 646 */     DMatrix c = new DMatrix(this._m, this._n);
/* 647 */     ArrayMath.div(b._a, this._a, c._a);
/* 648 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix arrayLeftDivideEquals(DMatrix b) {
/* 658 */     ArrayMath.div(b._a, this._a, this._a);
/* 659 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix times(double s) {
/* 668 */     DMatrix c = new DMatrix(this._m, this._n);
/* 669 */     ArrayMath.mul(this._a, s, c._a);
/* 670 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix timesEquals(double s) {
/* 679 */     ArrayMath.mul(this._a, s, this._a);
/* 680 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix times(DMatrix b) {
/* 690 */     Check.argument((this._n == b._m), "number of columns in A equals number of rows in B");
/*     */     
/* 692 */     DMatrix c = new DMatrix(this._m, b._n);
/* 693 */     double[][] aa = this._a;
/* 694 */     double[][] ba = b._a;
/* 695 */     double[][] ca = c._a;
/* 696 */     double[] bj = new double[this._n];
/* 697 */     for (int j = 0; j < b._n; j++) {
/* 698 */       for (int k = 0; k < this._n; k++)
/* 699 */         bj[k] = ba[k][j]; 
/* 700 */       for (int i = 0; i < this._m; i++) {
/* 701 */         double[] ai = aa[i];
/* 702 */         double s = 0.0D;
/* 703 */         for (int m = 0; m < this._n; m++)
/* 704 */           s += ai[m] * bj[m]; 
/* 705 */         ca[i][j] = s;
/*     */       } 
/*     */     } 
/* 708 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double trace() {
/* 716 */     int mn = ArrayMath.min(this._m, this._n);
/* 717 */     double t = 0.0D;
/* 718 */     for (int i = 0; i < mn; i++)
/* 719 */       t += this._a[i][i]; 
/* 720 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DMatrix random(int m, int n) {
/* 731 */     DMatrix x = new DMatrix(m, n);
/* 732 */     ArrayMath.rand(x._a);
/* 733 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DMatrix identity(int m, int n) {
/* 743 */     DMatrix x = new DMatrix(m, n);
/* 744 */     double[][] xa = x._a;
/* 745 */     int mn = ArrayMath.min(m, n);
/* 746 */     for (int i = 0; i < mn; i++)
/* 747 */       xa[i][i] = 1.0D; 
/* 748 */     return x;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 752 */     if (this == obj)
/* 753 */       return true; 
/* 754 */     if (obj == null || getClass() != obj.getClass())
/* 755 */       return false; 
/* 756 */     DMatrix that = (DMatrix)obj;
/* 757 */     if (this._m != that._m || this._n != that._n)
/* 758 */       return false; 
/* 759 */     double[][] a = this._a;
/* 760 */     double[][] b = that._a;
/* 761 */     for (int i = 0; i < this._m; i++) {
/* 762 */       for (int j = 0; j < this._n; j++) {
/* 763 */         if (a[i][j] != b[i][j])
/* 764 */           return false; 
/*     */       } 
/*     */     } 
/* 767 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 771 */     int h = this._m ^ this._n;
/* 772 */     for (int i = 0; i < this._m; i++) {
/* 773 */       for (int j = 0; j < this._n; j++) {
/* 774 */         long bits = Double.doubleToLongBits(this._a[i][j]);
/* 775 */         h ^= (int)(bits ^ bits >>> 32L);
/*     */       } 
/*     */     } 
/* 778 */     return h;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 782 */     String ls = System.getProperty("line.separator");
/* 783 */     StringBuilder sb = new StringBuilder();
/* 784 */     String[][] s = format(this._a);
/* 785 */     int max = maxlen(s);
/* 786 */     String format = "%" + max + "s";
/* 787 */     sb.append("[[");
/* 788 */     int ncol = 77 / (max + 2);
/* 789 */     if (ncol >= 5)
/* 790 */       ncol = ncol / 5 * 5; 
/* 791 */     for (int i = 0; i < this._m; i++) {
/* 792 */       int nrow = 1 + (this._n - 1) / ncol;
/* 793 */       if (i > 0)
/* 794 */         sb.append(" ["); 
/* 795 */       for (int irow = 0, j = 0; irow < nrow; irow++) {
/* 796 */         for (int icol = 0; icol < ncol && j < this._n; icol++, j++) {
/* 797 */           sb.append(String.format(format, new Object[] { s[i][j] }));
/* 798 */           if (j < this._n - 1)
/* 799 */             sb.append(", "); 
/*     */         } 
/* 801 */         if (j < this._n) {
/* 802 */           sb.append(ls);
/* 803 */           sb.append("  ");
/*     */         }
/* 805 */         else if (i < this._m - 1) {
/* 806 */           sb.append("],");
/* 807 */           sb.append(ls);
/*     */         } else {
/* 809 */           sb.append("]]");
/* 810 */           sb.append(ls);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 815 */     return sb.toString();
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
/*     */   DMatrix(int m, int n, double[][] a) {
/* 830 */     this._m = m;
/* 831 */     this._n = n;
/* 832 */     this._a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkI(int i) {
/* 843 */     if (i < 0 || i >= this._m)
/* 844 */       Check.argument((0 <= i && i < this._m), "row index i=" + i + " is in bounds"); 
/*     */   }
/*     */   
/*     */   private void checkJ(int j) {
/* 848 */     if (j < 0 || j >= this._n)
/* 849 */       Check.argument((0 <= j && j < this._n), "column index j=" + j + " is in bounds"); 
/*     */   }
/*     */   
/*     */   private void checkI(int i0, int i1) {
/* 853 */     checkI(i0); checkI(i1); Check.argument((i0 <= i1), "i0<=i1");
/*     */   }
/*     */   
/*     */   private void checkJ(int j0, int j1) {
/* 857 */     checkJ(j0); checkJ(j1); Check.argument((j0 <= j1), "j0<=j1");
/*     */   }
/*     */   
/*     */   private static String[][] format(double[][] d) {
/*     */     String f;
/* 862 */     int m = d.length;
/* 863 */     int n = (d[0]).length;
/* 864 */     int pg = 6;
/* 865 */     String fg = "% ." + pg + "g";
/* 866 */     int pemax = -1;
/* 867 */     int pfmax = -1;
/* 868 */     for (int i = 0; i < m; i++) {
/* 869 */       for (int k = 0; k < n; k++) {
/* 870 */         String str = String.format(fg, new Object[] { Double.valueOf(d[i][k]) });
/* 871 */         str = clean(str);
/* 872 */         int ls = str.length();
/* 873 */         if (str.contains("e")) {
/* 874 */           int pe = (ls > 7) ? (ls - 7) : 0;
/* 875 */           if (pemax < pe)
/* 876 */             pemax = pe; 
/*     */         } else {
/* 878 */           int ip = str.indexOf('.');
/* 879 */           int pf = (ip >= 0) ? (ls - 1 - ip) : 0;
/* 880 */           if (pfmax < pf)
/* 881 */             pfmax = pf; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 885 */     String[][] s = new String[m][n];
/*     */     
/* 887 */     if (pemax >= 0) {
/* 888 */       if (pfmax > pg - 1)
/* 889 */         pfmax = pg - 1; 
/* 890 */       int pe = (pemax > pfmax) ? pemax : pfmax;
/* 891 */       f = "% ." + pe + "e";
/*     */     } else {
/* 893 */       int pf = pfmax;
/* 894 */       f = "% ." + pf + "f";
/*     */     } 
/* 896 */     for (int j = 0; j < m; j++) {
/* 897 */       for (int k = 0; k < n; k++) {
/* 898 */         s[j][k] = String.format(f, new Object[] { Double.valueOf(d[j][k]) });
/*     */       } 
/*     */     } 
/* 901 */     return s;
/*     */   }
/*     */   private static String clean(String s) {
/* 904 */     int len = s.length();
/* 905 */     int iend = s.indexOf('e');
/* 906 */     if (iend < 0)
/* 907 */       iend = s.indexOf('E'); 
/* 908 */     if (iend < 0)
/* 909 */       iend = len; 
/* 910 */     int ibeg = iend;
/* 911 */     if (s.indexOf('.') > 0) {
/* 912 */       while (ibeg > 0 && s.charAt(ibeg - 1) == '0')
/* 913 */         ibeg--; 
/* 914 */       if (ibeg > 0 && s.charAt(ibeg - 1) == '.')
/* 915 */         ibeg--; 
/*     */     } 
/* 917 */     if (ibeg < iend) {
/* 918 */       String sb = s.substring(0, ibeg);
/* 919 */       s = (iend < len) ? (sb + s.substring(iend, len)) : sb;
/*     */     } 
/* 921 */     return s;
/*     */   }
/*     */   private static int maxlen(String[][] s) {
/* 924 */     int max = 0;
/* 925 */     int m = s.length;
/* 926 */     int n = (s[0]).length;
/* 927 */     for (int i = 0; i < m; i++) {
/* 928 */       for (int j = 0; j < n; j++) {
/* 929 */         int len = s[i][j].length();
/* 930 */         if (max < len)
/* 931 */           max = len; 
/*     */       } 
/*     */     } 
/* 934 */     return max;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/la/DMatrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */