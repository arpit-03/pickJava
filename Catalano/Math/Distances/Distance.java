/*     */ package Catalano.Math.Distances;
/*     */ 
/*     */ import Catalano.Core.IntPoint;
/*     */ import Catalano.Math.Matrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Distance
/*     */ {
/*     */   public static double ArithmeticGeometricDivergence(double[] p, double[] q) {
/*  45 */     double r = 0.0D;
/*  46 */     for (int i = 0; i < p.length; i++) {
/*  47 */       double den = p[i] * q[i];
/*  48 */       if (den != 0.0D) {
/*  49 */         double num = p[i] + q[i];
/*  50 */         r += num / 2.0D * Math.log(num / 2.0D * Math.sqrt(den));
/*     */       } 
/*     */     } 
/*  53 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Bhattacharyya(double[] histogram1, double[] histogram2) {
/*  63 */     int bins = histogram1.length;
/*  64 */     double b = 0.0D;
/*     */     
/*  66 */     for (int i = 0; i < bins; i++) {
/*  67 */       b += Math.sqrt(histogram1[i]) * Math.sqrt(histogram2[i]);
/*     */     }
/*     */     
/*  70 */     return Math.sqrt(1.0D - b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double BrayCurtis(double[] p, double[] q) {
/*  81 */     double sumN = 0.0D, sumP = sumN;
/*     */     
/*  83 */     int i = 0;
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
/*     */   public static double BrayCurtis(double x1, double y1, double x2, double y2) {
/* 100 */     double sumN = Math.abs(x1 - x2) + Math.abs(y1 - y2);
/* 101 */     double sumP = Math.abs(x1 + x2) + Math.abs(y1 + y2);
/*     */     
/* 103 */     return sumN / sumP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double BrayCurtis(IntPoint p, IntPoint q) {
/* 113 */     return BrayCurtis(p.x, p.y, q.x, q.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Canberra(double[] p, double[] q) {
/* 123 */     double distance = 0.0D;
/*     */     
/* 125 */     for (int i = 0; i < p.length; i++) {
/* 126 */       distance += Math.abs(p[i] - q[i]) / (Math.abs(p[i]) + Math.abs(q[i]));
/*     */     }
/*     */     
/* 129 */     return distance;
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
/*     */   public static double Canberra(double x1, double y1, double x2, double y2) {
/* 143 */     double distance = Math.abs(x1 - x2) / (Math.abs(x1) + Math.abs(x2));
/* 144 */     distance += Math.abs(y1 - y2) / (Math.abs(y1) + Math.abs(y2));
/*     */     
/* 146 */     return distance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Canberra(IntPoint p, IntPoint q) {
/* 156 */     return Canberra(p.x, p.y, q.x, q.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Chebyshev(double[] p, double[] q) {
/* 166 */     double max = Math.abs(p[0] - q[0]);
/*     */     
/* 168 */     for (int i = 1; i < p.length; i++) {
/* 169 */       double abs = Math.abs(p[i] - q[i]);
/* 170 */       if (abs > max) max = abs;
/*     */     
/*     */     } 
/* 173 */     return max;
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
/*     */   public static double Chebyshev(double x1, double y1, double x2, double y2) {
/* 185 */     double max = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
/* 186 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Chebyshev(IntPoint p, IntPoint q) {
/* 196 */     return Chebyshev(p.x, p.y, q.x, q.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Chessboard(double[] x, double[] y) {
/* 207 */     double d = 0.0D;
/* 208 */     for (int i = 0; i < x.length; i++) {
/* 209 */       d = Math.max(d, x[i] - y[i]);
/*     */     }
/*     */     
/* 212 */     return d;
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
/*     */   public static double Chessboard(double x1, double y1, double x2, double y2) {
/* 224 */     double dx = Math.abs(x1 - x2);
/* 225 */     double dy = Math.abs(y1 - y2);
/*     */     
/* 227 */     return Math.max(dx, dy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Chessboard(IntPoint p, IntPoint q) {
/* 237 */     return Chessboard(p.x, p.y, q.x, q.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double ChiSquare(double[] histogram1, double[] histogram2) {
/* 247 */     double r = 0.0D;
/* 248 */     for (int i = 0; i < histogram1.length; i++) {
/* 249 */       double t = histogram1[i] + histogram2[i];
/* 250 */       if (t != 0.0D) {
/* 251 */         r += Math.pow(histogram1[i] - histogram2[i], 2.0D) / t;
/*     */       }
/*     */     } 
/* 254 */     return 0.5D * r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Correlation(double[] p, double[] q) {
/* 265 */     double x = 0.0D;
/* 266 */     double y = 0.0D;
/*     */     
/* 268 */     for (int i = 0; i < p.length; i++) {
/* 269 */       x += -p[i];
/* 270 */       y += -q[i];
/*     */     } 
/*     */     
/* 273 */     x /= p.length;
/* 274 */     y /= q.length;
/*     */     
/* 276 */     double num = 0.0D;
/* 277 */     double den1 = 0.0D;
/* 278 */     double den2 = 0.0D;
/* 279 */     for (int j = 0; j < p.length; j++) {
/*     */       
/* 281 */       num += (p[j] + x) * (q[j] + y);
/*     */       
/* 283 */       den1 += Math.abs(Math.pow(p[j] + x, 2.0D));
/* 284 */       den2 += Math.abs(Math.pow(q[j] + x, 2.0D));
/*     */     } 
/*     */     
/* 287 */     return 1.0D - num / Math.sqrt(den1) * Math.sqrt(den2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Cosine(double[] p, double[] q) {
/* 298 */     double sumProduct = 0.0D;
/* 299 */     double sumP = 0.0D, sumQ = 0.0D;
/*     */     
/* 301 */     for (int i = 0; i < p.length; i++) {
/* 302 */       sumProduct += p[i] * q[i];
/* 303 */       sumP += Math.pow(Math.abs(p[i]), 2.0D);
/* 304 */       sumQ += Math.pow(Math.abs(q[i]), 2.0D);
/*     */     } 
/*     */     
/* 307 */     sumP = Math.sqrt(sumP);
/* 308 */     sumQ = Math.sqrt(sumQ);
/*     */     
/* 310 */     double result = 1.0D - sumProduct / sumP * sumQ;
/*     */     
/* 312 */     return result;
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
/*     */   public static double Cosine(double x1, double y1, double x2, double y2) {
/* 325 */     double sumProduct = x1 * x2 + y1 * y2;
/* 326 */     double sumP = Math.pow(Math.abs(x1), 2.0D) + Math.pow(Math.abs(x2), 2.0D);
/* 327 */     double sumQ = Math.pow(Math.abs(y1), 2.0D) + Math.pow(Math.abs(y2), 2.0D);
/* 328 */     sumP = Math.sqrt(sumP);
/* 329 */     sumQ = Math.sqrt(sumQ);
/*     */     
/* 331 */     double result = 1.0D - sumProduct / sumP * sumQ;
/* 332 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Cosine(IntPoint p, IntPoint q) {
/* 342 */     return Cosine(p.x, p.y, q.x, q.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Euclidean(double[] p, double[] q) {
/* 352 */     return Math.sqrt(SquaredEuclidean(p, q));
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
/*     */   public static double Euclidean(double x1, double y1, double x2, double y2) {
/* 364 */     double dx = Math.abs(x1 - x2);
/* 365 */     double dy = Math.abs(y1 - y2);
/*     */     
/* 367 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Euclidean(IntPoint p, IntPoint q) {
/* 377 */     return Euclidean(p.x, p.y, q.x, q.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int Hamming(String first, String second) {
/* 388 */     if (first.length() != second.length()) {
/* 389 */       throw new IllegalArgumentException("The size of string must be the same.");
/*     */     }
/* 391 */     int diff = 0;
/* 392 */     for (int i = 0; i < first.length(); i++) {
/* 393 */       if (first.charAt(i) != second.charAt(i))
/* 394 */         diff++; 
/* 395 */     }  return diff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double JaccardDistance(double[] p, double[] q) {
/* 405 */     double distance = 0.0D;
/* 406 */     int intersection = 0, union = 0;
/*     */     
/* 408 */     for (int x = 0; x < p.length; x++) {
/*     */       
/* 410 */       if (p[x] != 0.0D || q[x] != 0.0D) {
/*     */         
/* 412 */         if (p[x] == q[x])
/*     */         {
/* 414 */           intersection++;
/*     */         }
/*     */         
/* 417 */         union++;
/*     */       } 
/*     */     } 
/*     */     
/* 421 */     if (union != 0) {
/* 422 */       distance = 1.0D - intersection / union;
/*     */     } else {
/* 424 */       distance = 0.0D;
/*     */     } 
/* 426 */     return distance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double JDivergence(double[] p, double[] q) {
/* 436 */     boolean intersection = false;
/* 437 */     double k = 0.0D;
/*     */     
/* 439 */     for (int i = 0; i < p.length; i++) {
/* 440 */       if (p[i] != 0.0D && q[i] != 0.0D) {
/* 441 */         intersection = true;
/* 442 */         k += (p[i] - q[i]) * Math.log(p[i] / q[i]);
/*     */       } 
/*     */     } 
/*     */     
/* 446 */     if (intersection) {
/* 447 */       return k;
/*     */     }
/* 449 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double JensenDifferenceDivergence(double[] p, double[] q) {
/* 459 */     boolean intersection = false;
/* 460 */     double k = 0.0D;
/*     */     
/* 462 */     for (int i = 0; i < p.length; i++) {
/* 463 */       if (p[i] != 0.0D && q[i] != 0.0D) {
/* 464 */         intersection = true;
/* 465 */         double pq = p[i] + q[i];
/* 466 */         k += (p[i] * Math.log(p[i]) + q[i] * Math.log(q[i])) / 2.0D - pq / 2.0D * Math.log(pq / 2.0D);
/*     */       } 
/*     */     } 
/*     */     
/* 470 */     if (intersection) {
/* 471 */       return k;
/*     */     }
/* 473 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double JensenShannonDivergence(double[] p, double[] q) {
/* 483 */     double[] m = new double[p.length];
/* 484 */     for (int i = 0; i < m.length; i++) {
/* 485 */       m[i] = (p[i] + q[i]) / 2.0D;
/*     */     }
/*     */     
/* 488 */     return (KullbackLeiblerDivergence(p, m) + KullbackLeiblerDivergence(q, m)) / 2.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double KDivergence(double[] p, double[] q) {
/* 498 */     double r = 0.0D;
/* 499 */     for (int i = 0; i < p.length; i++) {
/* 500 */       double den = p[i] + q[i];
/* 501 */       if (den != 0.0D && p[i] != 0.0D) {
/* 502 */         r += p[i] * Math.log(2.0D * p[i] / den);
/*     */       }
/*     */     } 
/* 505 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double KumarJohnsonDivergence(double[] p, double[] q) {
/* 515 */     double r = 0.0D;
/* 516 */     for (int i = 0; i < p.length; i++) {
/* 517 */       if (p[i] != 0.0D && q[i] != 0.0D) {
/* 518 */         r += Math.pow(p[i] * p[i] - q[i] * q[i], 2.0D) / 2.0D * Math.pow(p[i] * q[i], 1.5D);
/*     */       }
/*     */     } 
/* 521 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double KullbackLeiblerDivergence(double[] p, double[] q) {
/* 531 */     boolean intersection = false;
/* 532 */     double k = 0.0D;
/*     */     
/* 534 */     for (int i = 0; i < p.length; i++) {
/* 535 */       if (p[i] != 0.0D && q[i] != 0.0D) {
/* 536 */         intersection = true;
/* 537 */         k += p[i] * Math.log(p[i] / q[i]);
/*     */       } 
/*     */     } 
/*     */     
/* 541 */     if (intersection) {
/* 542 */       return k;
/*     */     }
/* 544 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Mahalanobis(double[][] A, double[][] B) {
/* 555 */     if ((A[0]).length != (B[0]).length) {
/* 556 */       throw new IllegalArgumentException("The number of columns of both matrix must be equals.");
/*     */     }
/* 558 */     double[][] subA = new double[A.length][(A[0]).length];
/* 559 */     double[][] subB = new double[B.length][(B[0]).length];
/*     */ 
/*     */     
/* 562 */     double[] meansA = new double[(A[0]).length];
/* 563 */     for (int j = 0; j < (A[0]).length; j++) {
/* 564 */       int n; for (n = 0; n < A.length; n++) {
/* 565 */         meansA[j] = meansA[j] + A[n][j];
/*     */       }
/* 567 */       meansA[j] = meansA[j] / A.length;
/* 568 */       for (n = 0; n < A.length; n++) {
/* 569 */         subA[n][j] = A[n][j] - meansA[j];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 574 */     double[] meansB = new double[(B[0]).length];
/* 575 */     for (int k = 0; k < (B[0]).length; k++) {
/* 576 */       int n; for (n = 0; n < B.length; n++) {
/* 577 */         meansB[k] = meansB[k] + B[n][k];
/*     */       }
/* 579 */       meansB[k] = meansB[k] / B.length;
/* 580 */       for (n = 0; n < B.length; n++) {
/* 581 */         subB[n][k] = B[n][k] - meansB[k];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 586 */     double[][] covA = Covariance(subA);
/* 587 */     double[][] covB = Covariance(subB);
/*     */ 
/*     */     
/* 590 */     double rows = (subA.length + subB.length);
/* 591 */     double[][] pCov = new double[covA.length][(covA[0]).length];
/* 592 */     for (int i = 0; i < pCov.length; i++) {
/* 593 */       for (int n = 0; n < (pCov[0]).length; n++) {
/* 594 */         pCov[i][n] = covA[i][n] * subA.length / rows + covB[i][n] * subB.length / rows;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 599 */     pCov = Matrix.Inverse(pCov);
/*     */ 
/*     */     
/* 602 */     double[] diff = new double[(A[0]).length];
/* 603 */     for (int m = 0; m < diff.length; m++) {
/* 604 */       diff[m] = meansA[m] - meansB[m];
/*     */     }
/*     */     
/* 607 */     return Math.sqrt(Matrix.InnerProduct(Matrix.MultiplyByTranspose(pCov, diff), diff));
/*     */   }
/*     */ 
/*     */   
/*     */   private static double Covariance(double[] x, double[] y, double meanX, double meanY) {
/* 612 */     double result = 0.0D;
/* 613 */     for (int i = 0; i < x.length; i++) {
/* 614 */       result += (x[i] - meanX) * (y[i] - meanY);
/*     */     }
/*     */     
/* 617 */     return result / x.length;
/*     */   }
/*     */   
/*     */   private static double[][] Covariance(double[][] matrix) {
/* 621 */     double[] means = new double[(matrix[0]).length]; int i;
/* 622 */     for (i = 0; i < matrix.length; i++) {
/* 623 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 624 */         means[j] = means[j] + matrix[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 628 */     for (i = 0; i < means.length; i++) {
/* 629 */       means[i] = means[i] / means.length;
/*     */     }
/*     */     
/* 632 */     return Covariance(matrix, means);
/*     */   }
/*     */   
/*     */   private static double[][] Covariance(double[][] matrix, double[] means) {
/* 636 */     double[][] cov = new double[means.length][means.length];
/*     */     
/* 638 */     for (int i = 0; i < cov.length; i++) {
/* 639 */       for (int j = 0; j < (cov[0]).length; j++) {
/* 640 */         cov[i][j] = Covariance(Matrix.getColumn(matrix, i), Matrix.getColumn(matrix, j), means[i], means[j]);
/*     */       }
/*     */     } 
/*     */     
/* 644 */     return cov;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Manhattan(double[] p, double[] q) {
/* 655 */     double sum = 0.0D;
/* 656 */     for (int i = 0; i < p.length; i++) {
/* 657 */       sum += Math.abs(p[i] - q[i]);
/*     */     }
/* 659 */     return sum;
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
/*     */   public static double Manhattan(double x1, double y1, double x2, double y2) {
/* 671 */     double dx = Math.abs(x1 - x2);
/* 672 */     double dy = Math.abs(y1 - y2);
/*     */     
/* 674 */     return dx + dy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Manhattan(IntPoint p, IntPoint q) {
/* 684 */     return Manhattan(p.x, p.y, q.x, q.y);
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
/*     */   public static double Minkowski(double x1, double y1, double x2, double y2, int r) {
/* 697 */     double sum = Math.pow(Math.abs(x1 - x2), r);
/* 698 */     sum += Math.pow(Math.abs(y1 - y2), r);
/* 699 */     return Math.pow(sum, (1 / r));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Minkowski(IntPoint p, IntPoint q, int r) {
/* 710 */     return Minkowski(p.x, p.y, q.x, q.y, r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Minkowski(double[] u, double[] v, double p) {
/* 721 */     double distance = 0.0D;
/* 722 */     for (int i = 0; i < u.length; i++) {
/* 723 */       distance += Math.pow(Math.abs(u[i] - v[i]), p);
/*     */     }
/* 725 */     return Math.pow(distance, 1.0D / p);
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
/*     */   public static double QuasiEuclidean(double x1, double y1, double x2, double y2) {
/* 738 */     if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
/* 739 */       return Math.abs(x1 - x2) + 0.41421356237309515D * Math.abs(y1 - y2);
/*     */     }
/* 741 */     return 0.41421356237309515D * Math.abs(x1 - x2) + Math.abs(y1 - y2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double QuasiEuclidean(IntPoint p, IntPoint q) {
/* 752 */     return QuasiEuclidean(p.x, p.y, q.x, q.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double SquaredEuclidean(double[] x, double[] y) {
/* 762 */     double d = 0.0D;
/*     */     
/* 764 */     for (int i = 0; i < x.length; i++) {
/*     */       
/* 766 */       double u = x[i] - y[i];
/* 767 */       d += u * u;
/*     */     } 
/*     */     
/* 770 */     return d;
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
/*     */   public static double SquaredEuclidean(double x1, double y1, double x2, double y2) {
/* 783 */     double dx = x2 - x1;
/* 784 */     double dy = y2 - y1;
/* 785 */     return dx * dx + dy * dy;
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
/*     */   public static double SquaredEuclidean(IntPoint p, IntPoint q) {
/* 797 */     double dx = (q.x - p.x);
/* 798 */     double dy = (q.y - p.y);
/* 799 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double SymmetricChiSquareDivergence(double[] p, double[] q) {
/* 810 */     double r = 0.0D;
/* 811 */     for (int i = 0; i < p.length; i++) {
/* 812 */       double den = p[i] * q[i];
/* 813 */       if (den != 0.0D) {
/* 814 */         double p1 = p[i] - q[i];
/* 815 */         double p2 = p[i] + q[i];
/* 816 */         r += p1 * p1 * p2 / den;
/*     */       } 
/*     */     } 
/*     */     
/* 820 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double SymmetricKullbackLeibler(double[] p, double[] q) {
/* 831 */     double dist = 0.0D;
/* 832 */     for (int i = 0; i < p.length; i++) {
/* 833 */       dist += (p[i] - q[i]) * (Math.log(p[i]) - Math.log(q[i]));
/*     */     }
/*     */     
/* 836 */     return dist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Taneja(double[] p, double[] q) {
/* 846 */     double r = 0.0D;
/* 847 */     for (int i = 0; i < p.length; i++) {
/* 848 */       if (p[i] != 0.0D && q[i] != 0.0D) {
/* 849 */         double pq = p[i] + q[i];
/* 850 */         r += pq / 2.0D * Math.log(pq / 2.0D * Math.sqrt(p[i] * q[i]));
/*     */       } 
/*     */     } 
/* 853 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double TopsoeDivergence(double[] p, double[] q) {
/* 863 */     double r = 0.0D;
/* 864 */     for (int i = 0; i < p.length; i++) {
/* 865 */       if (p[i] != 0.0D && q[i] != 0.0D) {
/* 866 */         double den = p[i] + q[i];
/* 867 */         r += p[i] * Math.log(2.0D * p[i] / den) + q[i] * Math.log(2.0D * q[i] / den);
/*     */       } 
/*     */     } 
/* 870 */     return r;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Distances/Distance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */