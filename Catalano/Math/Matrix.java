/*      */ package Catalano.Math;
/*      */ 
/*      */ import Catalano.Core.ArraysUtil;
/*      */ import Catalano.Core.DoubleRange;
/*      */ import Catalano.Core.IntPoint;
/*      */ import Catalano.Math.Decompositions.LUDecomposition;
/*      */ import Catalano.Math.Decompositions.SingularValueDecomposition;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Matrix
/*      */ {
/*      */   public static double[][] Abs(double[][] A) {
/*   52 */     int rows = A.length;
/*   53 */     int cols = (A[0]).length;
/*   54 */     double[][] r = new double[rows][cols];
/*      */     
/*   56 */     for (int i = 0; i < rows; i++) {
/*   57 */       for (int j = 0; j < cols; j++) {
/*   58 */         r[i][j] = Math.abs(A[i][j]);
/*      */       }
/*      */     } 
/*      */     
/*   62 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] Abs(int[][] A) {
/*   72 */     int rows = A.length;
/*   73 */     int cols = (A[0]).length;
/*   74 */     int[][] r = new int[rows][cols];
/*      */     
/*   76 */     for (int i = 0; i < rows; i++) {
/*   77 */       for (int j = 0; j < cols; j++) {
/*   78 */         r[i][j] = Math.abs(A[i][j]);
/*      */       }
/*      */     } 
/*      */     
/*   82 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] Abs(float[][] A) {
/*   92 */     int rows = A.length;
/*   93 */     int cols = (A[0]).length;
/*   94 */     float[][] r = new float[rows][cols];
/*      */     
/*   96 */     for (int i = 0; i < rows; i++) {
/*   97 */       for (int j = 0; j < cols; j++) {
/*   98 */         r[i][j] = Math.abs(A[i][j]);
/*      */       }
/*      */     } 
/*      */     
/*  102 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] CreateMatrix1D(int size, double val) {
/*  113 */     double[] v = new double[size];
/*      */     
/*  115 */     for (int i = 0; i < size; i++) {
/*  116 */       v[i] = val;
/*      */     }
/*      */     
/*  119 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] CreateMatrix1D(int size, int val) {
/*  130 */     int[] v = new int[size];
/*      */     
/*  132 */     for (int i = 0; i < size; i++) {
/*  133 */       v[i] = val;
/*      */     }
/*      */     
/*  136 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] CreateMatrix1D(int size, float val) {
/*  147 */     float[] v = new float[size];
/*      */     
/*  149 */     for (int i = 0; i < size; i++) {
/*  150 */       v[i] = val;
/*      */     }
/*      */     
/*  153 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void Reverse(int[] input) {
/*  158 */     int last = input.length - 1;
/*  159 */     int middle = input.length / 2;
/*  160 */     for (int i = 0; i <= middle; i++) {
/*  161 */       int temp = input[i];
/*  162 */       input[i] = input[last - i];
/*  163 */       input[last - i] = temp;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void Reverse(double[] input) {
/*  169 */     int last = input.length - 1;
/*  170 */     int middle = input.length / 2;
/*  171 */     for (int i = 0; i <= middle; i++) {
/*  172 */       double temp = input[i];
/*  173 */       input[i] = input[last - i];
/*  174 */       input[last - i] = temp;
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
/*      */   public static double[][] CreateMatrix2D(int height, int width, double val) {
/*  189 */     double[][] v = new double[height][width];
/*      */     
/*  191 */     for (int i = 0; i < height; i++) {
/*  192 */       for (int j = 0; j < width; j++) {
/*  193 */         v[i][j] = val;
/*      */       }
/*      */     } 
/*      */     
/*  197 */     return v;
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
/*      */   public static int[][] CreateMatrix2D(int height, int width, int val) {
/*  210 */     int[][] v = new int[height][width];
/*      */     
/*  212 */     for (int i = 0; i < height; i++) {
/*  213 */       for (int j = 0; j < width; j++) {
/*  214 */         v[i][j] = val;
/*      */       }
/*      */     } 
/*      */     
/*  218 */     return v;
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
/*      */   public static float[][] CreateMatrix2D(int height, int width, float val) {
/*  231 */     float[][] v = new float[height][width];
/*      */     
/*  233 */     for (int i = 0; i < height; i++) {
/*  234 */       for (int j = 0; j < width; j++) {
/*  235 */         v[i][j] = val;
/*      */       }
/*      */     } 
/*      */     
/*  239 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] Indices(int from, int to) {
/*  250 */     int[] vector = new int[to - from];
/*  251 */     for (int i = 0; i < vector.length; i++)
/*  252 */       vector[i] = from++; 
/*  253 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double InnerProduct(double[] A, double[] B) {
/*  263 */     double sum = 0.0D;
/*  264 */     for (int i = 0; i < A.length; i++) {
/*  265 */       sum += A[i] * B[i];
/*      */     }
/*  267 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int InnerProduct(int[] A, int[] B) {
/*  277 */     int sum = 0;
/*  278 */     for (int i = 0; i < A.length; i++) {
/*  279 */       sum += A[i] * B[i];
/*      */     }
/*  281 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float InnerProduct(float[] A, float[] B) {
/*  291 */     float sum = 0.0F;
/*  292 */     for (int i = 0; i < A.length; i++) {
/*  293 */       sum += A[i] * B[i];
/*      */     }
/*  295 */     return sum;
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
/*      */   public static double[] InsertColumns(double[] A, double[] B, int index) {
/*  307 */     if (index > A.length) {
/*  308 */       throw new IllegalArgumentException("The index must be at least a valid index inside the array A.");
/*      */     }
/*  310 */     double[] v = new double[A.length + B.length];
/*      */     
/*  312 */     int idx = 0;
/*  313 */     for (int i = 0; i < A.length; i++) {
/*  314 */       if (i != index) {
/*  315 */         v[idx] = A[i];
/*  316 */         idx++;
/*      */       } else {
/*      */         
/*  319 */         for (int j = 0; j < B.length; j++) {
/*  320 */           v[i + j] = B[j];
/*  321 */           idx++;
/*      */         } 
/*  323 */         v[idx++] = A[i];
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  329 */     if (index == A.length) {
/*  330 */       for (int j = 0; j < B.length; j++) {
/*  331 */         v[A.length + j] = B[j];
/*      */       }
/*      */     }
/*      */     
/*  335 */     return v;
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
/*      */   public static int[] InsertColumns(int[] A, int[] B, int index) {
/*  347 */     if (index > A.length) {
/*  348 */       throw new IllegalArgumentException("The index must be at least a valid index inside the array A.");
/*      */     }
/*  350 */     int[] v = new int[A.length + B.length];
/*      */     
/*  352 */     int idx = 0;
/*  353 */     for (int i = 0; i < A.length; i++) {
/*  354 */       if (i != index) {
/*  355 */         v[idx] = A[i];
/*  356 */         idx++;
/*      */       } else {
/*      */         
/*  359 */         for (int j = 0; j < B.length; j++) {
/*  360 */           v[i + j] = B[j];
/*  361 */           idx++;
/*      */         } 
/*  363 */         v[idx++] = A[i];
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  369 */     if (index == A.length) {
/*  370 */       for (int j = 0; j < B.length; j++) {
/*  371 */         v[A.length + j] = B[j];
/*      */       }
/*      */     }
/*      */     
/*  375 */     return v;
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
/*      */   public static float[] InsertColumns(float[] A, float[] B, int index) {
/*  387 */     if (index > A.length) {
/*  388 */       throw new IllegalArgumentException("The index must be at least a valid index inside the array A.");
/*      */     }
/*  390 */     float[] v = new float[A.length + B.length];
/*      */     
/*  392 */     int idx = 0;
/*  393 */     for (int i = 0; i < A.length; i++) {
/*  394 */       if (i != index) {
/*  395 */         v[idx] = A[i];
/*  396 */         idx++;
/*      */       } else {
/*      */         
/*  399 */         for (int j = 0; j < B.length; j++) {
/*  400 */           v[i + j] = B[j];
/*  401 */           idx++;
/*      */         } 
/*  403 */         v[idx++] = A[i];
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  409 */     if (index == A.length) {
/*  410 */       for (int j = 0; j < B.length; j++) {
/*  411 */         v[A.length + j] = B[j];
/*      */       }
/*      */     }
/*      */     
/*  415 */     return v;
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
/*      */   public static byte[] InsertColumns(byte[] A, byte[] B, int index) {
/*  427 */     if (index > A.length) {
/*  428 */       throw new IllegalArgumentException("The index must be at least a valid index inside the array A.");
/*      */     }
/*  430 */     byte[] v = new byte[A.length + B.length];
/*      */     
/*  432 */     int idx = 0;
/*  433 */     for (int i = 0; i < A.length; i++) {
/*  434 */       if (i != index) {
/*  435 */         v[idx] = A[i];
/*  436 */         idx++;
/*      */       } else {
/*      */         
/*  439 */         for (int j = 0; j < B.length; j++) {
/*  440 */           v[i + j] = B[j];
/*  441 */           idx++;
/*      */         } 
/*  443 */         v[idx++] = A[i];
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  449 */     if (index == A.length) {
/*  450 */       for (int j = 0; j < B.length; j++) {
/*  451 */         v[A.length + j] = B[j];
/*      */       }
/*      */     }
/*      */     
/*  455 */     return v;
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
/*      */   public static Object[] InsertColumns(Object[] A, Object[] B, int index) {
/*  467 */     if (index > A.length) {
/*  468 */       throw new IllegalArgumentException("The index must be at least a valid index inside the array A.");
/*      */     }
/*  470 */     Object[] v = new Object[A.length + B.length];
/*      */     
/*  472 */     int idx = 0;
/*  473 */     for (int i = 0; i < A.length; i++) {
/*  474 */       if (i != index) {
/*  475 */         v[idx] = A[i];
/*  476 */         idx++;
/*      */       } else {
/*      */         
/*  479 */         for (int j = 0; j < B.length; j++) {
/*  480 */           v[i + j] = B[j];
/*  481 */           idx++;
/*      */         } 
/*  483 */         v[idx++] = A[i];
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  489 */     if (index == A.length) {
/*  490 */       for (int j = 0; j < B.length; j++) {
/*  491 */         v[A.length + j] = B[j];
/*      */       }
/*      */     }
/*      */     
/*  495 */     return v;
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
/*      */   public static double[][] InsertColumn(double[][] A, double[] B, int index) {
/*  507 */     if (A.length != B.length) {
/*  508 */       throw new IllegalArgumentException("The column of the matrix must be the same size of the vector B.");
/*      */     }
/*  510 */     double[][] mat = new double[A.length][(A[0]).length + 1]; int i;
/*  511 */     for (i = 0; i < A.length; i++) {
/*  512 */       int idx = 0;
/*  513 */       for (int j = 0; j <= (A[0]).length; j++) {
/*  514 */         if (j != index) {
/*  515 */           mat[i][j] = A[i][idx];
/*  516 */           idx++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  521 */     for (i = 0; i < B.length; i++) {
/*  522 */       mat[i][index] = B[i];
/*      */     }
/*      */     
/*  525 */     return mat;
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
/*      */   public static int[][] InsertColumn(int[][] A, int[] B, int index) {
/*  537 */     if (A.length != B.length) {
/*  538 */       throw new IllegalArgumentException("The column of the matrix must be the same size of the vector B.");
/*      */     }
/*  540 */     int[][] mat = new int[A.length][(A[0]).length + 1]; int i;
/*  541 */     for (i = 0; i < A.length; i++) {
/*  542 */       int idx = 0;
/*  543 */       for (int j = 0; j <= (A[0]).length; j++) {
/*  544 */         if (j != index) {
/*  545 */           mat[i][j] = A[i][idx];
/*  546 */           idx++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  551 */     for (i = 0; i < B.length; i++) {
/*  552 */       mat[i][index] = B[i];
/*      */     }
/*      */     
/*  555 */     return mat;
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
/*      */   public static float[][] InsertColumn(float[][] A, float[] B, int index) {
/*  567 */     if (A.length != B.length) {
/*  568 */       throw new IllegalArgumentException("The column of the matrix must be the same size of the vector B.");
/*      */     }
/*  570 */     float[][] mat = new float[A.length][(A[0]).length + 1]; int i;
/*  571 */     for (i = 0; i < A.length; i++) {
/*  572 */       int idx = 0;
/*  573 */       for (int j = 0; j <= (A[0]).length; j++) {
/*  574 */         if (j != index) {
/*  575 */           mat[i][j] = A[i][idx];
/*  576 */           idx++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  581 */     for (i = 0; i < B.length; i++) {
/*  582 */       mat[i][index] = B[i];
/*      */     }
/*      */     
/*  585 */     return mat;
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
/*      */   public static double[][] InsertColumns(double[][] A, double[][] B, int index) {
/*  597 */     if (A.length != B.length) {
/*  598 */       throw new IllegalArgumentException("The rows of the matrix must be the same size of the rows of B.");
/*      */     }
/*  600 */     if (index > (A[0]).length) {
/*  601 */       throw new IllegalArgumentException("The index must be in the range [0..number of columns + 1]");
/*      */     }
/*  603 */     double[][] mat = new double[A.length][(A[0]).length + (B[0]).length];
/*  604 */     for (int i = 0; i < A.length; i++) {
/*  605 */       int idx = 0;
/*      */       int j;
/*  607 */       for (j = 0; j < (A[0]).length; j++) {
/*  608 */         if (j != index) {
/*  609 */           mat[i][idx] = A[i][j];
/*  610 */           idx++;
/*      */         } else {
/*      */           
/*  613 */           for (int k = 0; k < (B[0]).length; k++) {
/*  614 */             mat[i][j + k] = B[i][k];
/*      */           }
/*      */           
/*  617 */           idx += (B[0]).length;
/*  618 */           mat[i][idx] = A[i][j];
/*  619 */           idx++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  624 */       if (j == index) {
/*  625 */         for (int k = 0; k < (B[0]).length; k++) {
/*  626 */           mat[i][j + k] = B[i][k];
/*      */         }
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  632 */     return mat;
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
/*      */   public static int[][] InsertColumns(int[][] A, int[][] B, int index) {
/*  644 */     if (A.length != B.length) {
/*  645 */       throw new IllegalArgumentException("The rows of the matrix must be the same size of the rows of B.");
/*      */     }
/*  647 */     if (index > (A[0]).length) {
/*  648 */       throw new IllegalArgumentException("The index must be in the range [0..number of columns + 1]");
/*      */     }
/*  650 */     int[][] mat = new int[A.length][(A[0]).length + (B[0]).length];
/*  651 */     for (int i = 0; i < A.length; i++) {
/*  652 */       int idx = 0;
/*      */       int j;
/*  654 */       for (j = 0; j < (A[0]).length; j++) {
/*  655 */         if (j != index) {
/*  656 */           mat[i][idx] = A[i][j];
/*  657 */           idx++;
/*      */         } else {
/*      */           
/*  660 */           for (int k = 0; k < (B[0]).length; k++) {
/*  661 */             mat[i][j + k] = B[i][k];
/*      */           }
/*      */           
/*  664 */           idx += (B[0]).length;
/*  665 */           mat[i][idx] = A[i][j];
/*  666 */           idx++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  671 */       if (j == index) {
/*  672 */         for (int k = 0; k < (B[0]).length; k++) {
/*  673 */           mat[i][j + k] = B[i][k];
/*      */         }
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  679 */     return mat;
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
/*      */   public static float[][] InsertColumns(float[][] A, float[][] B, int index) {
/*  691 */     if (A.length != B.length) {
/*  692 */       throw new IllegalArgumentException("The rows of the matrix must be the same size of the rows of B.");
/*      */     }
/*  694 */     if (index > (A[0]).length) {
/*  695 */       throw new IllegalArgumentException("The index must be in the range [0..number of columns + 1]");
/*      */     }
/*  697 */     float[][] mat = new float[A.length][(A[0]).length + (B[0]).length];
/*  698 */     for (int i = 0; i < A.length; i++) {
/*  699 */       int idx = 0;
/*      */       int j;
/*  701 */       for (j = 0; j < (A[0]).length; j++) {
/*  702 */         if (j != index) {
/*  703 */           mat[i][idx] = A[i][j];
/*  704 */           idx++;
/*      */         } else {
/*      */           
/*  707 */           for (int k = 0; k < (B[0]).length; k++) {
/*  708 */             mat[i][j + k] = B[i][k];
/*      */           }
/*      */           
/*  711 */           idx += (B[0]).length;
/*  712 */           mat[i][idx] = A[i][j];
/*  713 */           idx++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  718 */       if (j == index) {
/*  719 */         for (int k = 0; k < (B[0]).length; k++) {
/*  720 */           mat[i][j + k] = B[i][k];
/*      */         }
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  726 */     return mat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] Log(double[] A) {
/*  735 */     int size = A.length;
/*  736 */     double[] r = new double[size];
/*  737 */     for (int i = 0; i < size; i++) {
/*  738 */       r[i] = Math.log(A[i]);
/*      */     }
/*  740 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Log(double[][] A) {
/*  750 */     int rows = A.length;
/*  751 */     int cols = (A[0]).length;
/*  752 */     double[][] r = new double[rows][cols];
/*      */     
/*  754 */     for (int i = 0; i < rows; i++) {
/*  755 */       for (int j = 0; j < cols; j++) {
/*  756 */         r[i][j] = Math.log(A[i][j]);
/*      */       }
/*      */     } 
/*      */     
/*  760 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] Add(double[] A, double[] B) {
/*  770 */     for (int i = 0; i < A.length; i++) {
/*  771 */       A[i] = A[i] + B[i];
/*      */     }
/*  773 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] Add(int[] A, int[] B) {
/*  783 */     for (int i = 0; i < A.length; i++) {
/*  784 */       A[i] = A[i] + B[i];
/*      */     }
/*  786 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] Add(float[] A, float[] B) {
/*  796 */     for (int i = 0; i < A.length; i++) {
/*  797 */       A[i] = A[i] + B[i];
/*      */     }
/*  799 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Add(double[][] A, double[][] B) {
/*  809 */     for (int i = 0; i < A.length; i++) {
/*  810 */       for (int j = 0; j < (A[0]).length; j++) {
/*  811 */         A[i][j] = A[i][j] + B[i][j];
/*      */       }
/*      */     } 
/*  814 */     return A;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] Add(int[][] A, int[][] B) {
/*  824 */     for (int i = 0; i < A.length; i++) {
/*  825 */       for (int j = 0; j < (A[0]).length; j++) {
/*  826 */         A[i][j] = A[i][j] + B[i][j];
/*      */       }
/*      */     } 
/*  829 */     return A;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] Add(float[][] A, float[][] B) {
/*  839 */     for (int i = 0; i < A.length; i++) {
/*  840 */       for (int j = 0; j < (A[0]).length; j++) {
/*  841 */         A[i][j] = A[i][j] + B[i][j];
/*      */       }
/*      */     } 
/*  844 */     return A;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Add(double[] A, double scalar) {
/*  853 */     for (int i = 0; i < A.length; i++) {
/*  854 */       A[i] = A[i] + scalar;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Add(int[] A, int scalar) {
/*  864 */     for (int i = 0; i < A.length; i++) {
/*  865 */       A[i] = A[i] + scalar;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Add(float[] A, float scalar) {
/*  875 */     for (int i = 0; i < A.length; i++) {
/*  876 */       A[i] = A[i] + scalar;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Add(double[][] A, double scalar) {
/*  886 */     for (int i = 0; i < A.length; i++) {
/*  887 */       for (int j = 0; j < (A[0]).length; j++) {
/*  888 */         A[i][j] = A[i][j] + scalar;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Add(int[][] A, int scalar) {
/*  899 */     for (int i = 0; i < A.length; i++) {
/*  900 */       for (int j = 0; j < (A[0]).length; j++) {
/*  901 */         A[i][j] = A[i][j] + scalar;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Add(float[][] A, float scalar) {
/*  912 */     for (int i = 0; i < A.length; i++) {
/*  913 */       for (int j = 0; j < (A[0]).length; j++) {
/*  914 */         A[i][j] = A[i][j] + scalar;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Clear(int[] A) {
/*  924 */     for (int i = 0; i < A.length; i++) {
/*  925 */       A[i] = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Clear(float[] A) {
/*  934 */     for (int i = 0; i < A.length; i++) {
/*  935 */       A[i] = 0.0F;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Clear(double[] A) {
/*  944 */     for (int i = 0; i < A.length; i++) {
/*  945 */       A[i] = 0.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Clear(int[][] A) {
/*  954 */     for (int i = 0; i < A.length; i++) {
/*  955 */       for (int j = 0; j < (A[0]).length; j++) {
/*  956 */         A[i][j] = 0;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Clear(float[][] A) {
/*  966 */     for (int i = 0; i < A.length; i++) {
/*  967 */       for (int j = 0; j < (A[0]).length; j++) {
/*  968 */         A[i][j] = 0.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Clear(double[][] A) {
/*  978 */     for (int i = 0; i < A.length; i++) {
/*  979 */       for (int j = 0; j < (A[0]).length; j++) {
/*  980 */         A[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Copy(double[][] A) {
/*  991 */     double[][] m = new double[A.length][(A[0]).length];
/*  992 */     for (int i = 0; i < A.length; i++) {
/*  993 */       for (int j = 0; j < (A[0]).length; j++) {
/*  994 */         m[i][j] = A[i][j];
/*      */       }
/*      */     } 
/*  997 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] Copy(int[][] A) {
/* 1006 */     int[][] m = new int[A.length][(A[0]).length];
/* 1007 */     for (int i = 0; i < A.length; i++) {
/* 1008 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1009 */         m[i][j] = A[i][j];
/*      */       }
/*      */     } 
/* 1012 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] Copy(float[][] A) {
/* 1021 */     float[][] m = new float[A.length][(A[0]).length];
/* 1022 */     for (int i = 0; i < A.length; i++) {
/* 1023 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1024 */         m[i][j] = A[i][j];
/*      */       }
/*      */     } 
/* 1027 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Determinant(double[][] A) {
/* 1036 */     return (new LUDecomposition(A)).determinant();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] Diff(double[] A, int differences) {
/* 1047 */     double[] result = new double[A.length - differences];
/* 1048 */     for (int i = 0; i < result.length; i++) {
/* 1049 */       result[i] = A[i + differences] - A[i];
/*      */     }
/*      */     
/* 1052 */     return result;
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
/*      */   public static int[] Diff(int[] A, int differences) {
/* 1064 */     int[] result = new int[A.length - differences];
/* 1065 */     for (int i = 0; i < result.length; i++) {
/* 1066 */       result[i] = A[i + differences] - A[i];
/*      */     }
/*      */     
/* 1069 */     return result;
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
/*      */   public static float[] Diff(float[] A, int differences) {
/* 1081 */     float[] result = new float[A.length - differences];
/* 1082 */     for (int i = 0; i < result.length; i++) {
/* 1083 */       result[i] = A[i + differences] - A[i];
/*      */     }
/*      */     
/* 1086 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Divide(double[][] A, double scalar) {
/* 1096 */     for (int i = 0; i < A.length; i++) {
/* 1097 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1098 */         A[i][j] = A[i][j] / scalar;
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
/*      */   public static double[][] Exp(double[][] A) {
/* 1110 */     int rows = A.length;
/* 1111 */     int cols = (A[0]).length;
/* 1112 */     double[][] r = new double[rows][cols];
/*      */     
/* 1114 */     for (int i = 0; i < rows; i++) {
/* 1115 */       for (int j = 0; j < cols; j++) {
/* 1116 */         r[i][j] = Math.exp(A[i][j]);
/*      */       }
/*      */     } 
/*      */     
/* 1120 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Divide(int[][] A, int scalar) {
/* 1129 */     for (int i = 0; i < A.length; i++) {
/* 1130 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1131 */         A[i][j] = A[i][j] / scalar;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Divide(float[][] A, float scalar) {
/* 1142 */     for (int i = 0; i < A.length; i++) {
/* 1143 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1144 */         A[i][j] = A[i][j] / scalar;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] DotProduct(double[][] A, double[][] B) {
/* 1155 */     double[][] result = new double[A.length][(A[0]).length];
/* 1156 */     for (int i = 0; i < A.length; i++) {
/* 1157 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1158 */         result[i][j] = A[i][j] * B[i][j];
/*      */       }
/*      */     } 
/* 1161 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] DotProduct(int[][] A, int[][] B) {
/* 1170 */     int[][] result = new int[A.length][(A[0]).length];
/* 1171 */     for (int i = 0; i < A.length; i++) {
/* 1172 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1173 */         result[i][j] = A[i][j] * B[i][j];
/*      */       }
/*      */     } 
/* 1176 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] DotProduct(float[][] A, float[][] B) {
/* 1185 */     float[][] result = new float[A.length][(A[0]).length];
/* 1186 */     for (int i = 0; i < A.length; i++) {
/* 1187 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1188 */         result[i][j] = A[i][j] * B[i][j];
/*      */       }
/*      */     } 
/* 1191 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Fill(double[] A, double number) {
/* 1200 */     for (int i = 0; i < A.length; i++) {
/* 1201 */       A[i] = number;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] getColumn(double[][] A, int n) {
/* 1212 */     int rows = A.length;
/* 1213 */     double[] v = new double[rows];
/*      */     
/* 1215 */     for (int i = 0; i < rows; i++) {
/* 1216 */       v[i] = A[i][n];
/*      */     }
/* 1218 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] getColumn(int[][] A, int n) {
/* 1228 */     int rows = A.length;
/* 1229 */     int[] v = new int[rows];
/*      */     
/* 1231 */     for (int i = 0; i < rows; i++) {
/* 1232 */       v[i] = A[i][n];
/*      */     }
/* 1234 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] getColumn(float[][] A, int n) {
/* 1244 */     int rows = A.length;
/* 1245 */     float[] v = new float[rows];
/*      */     
/* 1247 */     for (int i = 0; i < rows; i++) {
/* 1248 */       v[i] = A[i][n];
/*      */     }
/* 1250 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] getColumn(Object[][] A, int n) {
/* 1261 */     int rows = A.length;
/* 1262 */     Object[] v = (Object[])Array.newInstance(A[0][n].getClass(), rows);
/*      */     
/* 1264 */     for (int i = 0; i < rows; i++) {
/* 1265 */       v[i] = A[i][n];
/*      */     }
/*      */     
/* 1268 */     return (T[])v;
/*      */   }
/*      */   
/*      */   public static double[] getColumns(double[] A, int[] indexes) {
/* 1272 */     double[] v = new double[indexes.length];
/* 1273 */     for (int i = 0; i < v.length; i++) {
/* 1274 */       v[i] = A[indexes[i]];
/*      */     }
/* 1276 */     return v;
/*      */   }
/*      */   
/*      */   public static int[] getColumns(int[] A, int[] indexes) {
/* 1280 */     int[] v = new int[indexes.length];
/* 1281 */     for (int i = 0; i < v.length; i++) {
/* 1282 */       v[i] = A[indexes[i]];
/*      */     }
/* 1284 */     return v;
/*      */   }
/*      */   
/*      */   public static float[] getColumns(float[] A, int[] indexes) {
/* 1288 */     float[] v = new float[indexes.length];
/* 1289 */     for (int i = 0; i < v.length; i++) {
/* 1290 */       v[i] = A[indexes[i]];
/*      */     }
/* 1292 */     return v;
/*      */   }
/*      */   
/*      */   public static <T> T[] getColumns(Object[] A, int[] indexes) {
/* 1296 */     Object[] v = (Object[])Array.newInstance(A[0].getClass(), indexes.length);
/* 1297 */     for (int i = 0; i < v.length; i++) {
/* 1298 */       v[i] = A[indexes[i]];
/*      */     }
/* 1300 */     return (T[])v;
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
/*      */   public static double[] getColumns(double[] A, int startIndex, int endIndex) {
/* 1312 */     double[] v = new double[endIndex - startIndex + 1];
/* 1313 */     for (int i = 0; i < v.length; i++) {
/* 1314 */       v[i] = A[startIndex + i];
/*      */     }
/* 1316 */     return v;
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
/*      */   public static int[] getColumns(int[] A, int startIndex, int endIndex) {
/* 1329 */     int[] v = new int[endIndex - startIndex + 1];
/* 1330 */     for (int i = 0; i < v.length; i++) {
/* 1331 */       v[i] = A[startIndex + i];
/*      */     }
/* 1333 */     return v;
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
/*      */   public static float[] getColumns(float[] A, int startIndex, int endIndex) {
/* 1346 */     float[] v = new float[endIndex - startIndex + 1];
/* 1347 */     for (int i = 0; i < v.length; i++) {
/* 1348 */       v[i] = A[startIndex + i];
/*      */     }
/* 1350 */     return v;
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
/*      */   public static <T> T[] getColumns(Object[] A, int startIndex, int endIndex) {
/* 1363 */     Object[] v = (Object[])Array.newInstance(A[0].getClass(), A.length);
/* 1364 */     for (int i = 0; i < v.length; i++) {
/* 1365 */       v[i] = A[startIndex + i];
/*      */     }
/* 1367 */     return (T[])v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] getColumns(double[][] A, int[] indexes) {
/* 1378 */     double[][] m = new double[A.length][indexes.length];
/* 1379 */     for (int i = 0; i < m.length; i++) {
/* 1380 */       for (int j = 0; j < (m[0]).length; j++) {
/* 1381 */         m[i][j] = A[i][indexes[j]];
/*      */       }
/*      */     } 
/* 1384 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] getColumns(int[][] A, int[] indexes) {
/* 1394 */     int[][] m = new int[A.length][indexes.length];
/* 1395 */     for (int i = 0; i < m.length; i++) {
/* 1396 */       for (int j = 0; j < (m[0]).length; j++) {
/* 1397 */         m[i][j] = A[i][indexes[j]];
/*      */       }
/*      */     } 
/* 1400 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] getColumns(float[][] A, int[] indexes) {
/* 1410 */     float[][] m = new float[A.length][indexes.length];
/* 1411 */     for (int i = 0; i < m.length; i++) {
/* 1412 */       for (int j = 0; j < (m[0]).length; j++) {
/* 1413 */         m[i][j] = A[i][indexes[j]];
/*      */       }
/*      */     } 
/* 1416 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[][] getColumns(Object[][] A, int[] indexes) {
/* 1427 */     Object[][] m = (Object[][])Array.newInstance(A[0][0].getClass(), new int[] { A.length, indexes.length });
/* 1428 */     for (int i = 0; i < m.length; i++) {
/* 1429 */       for (int j = 0; j < (m[0]).length; j++) {
/* 1430 */         m[i][j] = A[i][indexes[j]];
/*      */       }
/*      */     } 
/* 1433 */     return (T[][])m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] getRow(double[][] A, int n) {
/* 1443 */     int cols = (A[0]).length;
/* 1444 */     double[] v = new double[cols];
/*      */     
/* 1446 */     for (int i = 0; i < cols; i++) {
/* 1447 */       v[i] = A[n][i];
/*      */     }
/* 1449 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] getRow(int[][] A, int n) {
/* 1459 */     int cols = (A[0]).length;
/* 1460 */     int[] v = new int[cols];
/*      */     
/* 1462 */     for (int i = 0; i < cols; i++) {
/* 1463 */       v[i] = A[n][i];
/*      */     }
/* 1465 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] getRow(float[][] A, int n) {
/* 1475 */     int cols = (A[0]).length;
/* 1476 */     float[] v = new float[cols];
/*      */     
/* 1478 */     for (int i = 0; i < cols; i++) {
/* 1479 */       v[i] = A[n][i];
/*      */     }
/* 1481 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] getRows(double[][] A, int[] indexes) {
/* 1491 */     double[][] m = new double[indexes.length][(A[0]).length];
/* 1492 */     for (int i = 0; i < m.length; i++) {
/* 1493 */       for (int j = 0; j < (m[0]).length; j++) {
/* 1494 */         m[i][j] = A[indexes[i]][j];
/*      */       }
/*      */     } 
/* 1497 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] getRows(int[][] A, int[] indexes) {
/* 1507 */     int[][] m = new int[indexes.length][(A[0]).length];
/* 1508 */     for (int i = 0; i < m.length; i++) {
/* 1509 */       for (int j = 0; j < (m[0]).length; j++) {
/* 1510 */         m[i][j] = A[indexes[i]][j];
/*      */       }
/*      */     } 
/* 1513 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] getRows(float[][] A, int[] indexes) {
/* 1523 */     float[][] m = new float[indexes.length][(A[0]).length];
/* 1524 */     for (int i = 0; i < m.length; i++) {
/* 1525 */       for (int j = 0; j < (m[0]).length; j++) {
/* 1526 */         m[i][j] = A[indexes[i]][j];
/*      */       }
/*      */     } 
/* 1529 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] getRows(double[] A, int[] indexes) {
/* 1539 */     double[] v = new double[indexes.length];
/* 1540 */     for (int i = 0; i < v.length; i++) {
/* 1541 */       v[i] = A[indexes[i]];
/*      */     }
/* 1543 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] getRows(int[] A, int[] indexes) {
/* 1553 */     int[] v = new int[indexes.length];
/* 1554 */     for (int i = 0; i < v.length; i++) {
/* 1555 */       v[i] = A[indexes[i]];
/*      */     }
/* 1557 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] getRows(float[] A, int[] indexes) {
/* 1567 */     float[] v = new float[indexes.length];
/* 1568 */     for (int i = 0; i < v.length; i++) {
/* 1569 */       v[i] = A[indexes[i]];
/*      */     }
/* 1571 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] getRows(Object[] A, int[] indexes) {
/* 1582 */     Object[] v = (Object[])Array.newInstance(A[0].getClass(), indexes.length);
/* 1583 */     for (int i = 0; i < v.length; i++) {
/* 1584 */       v[i] = A[indexes[i]];
/*      */     }
/* 1586 */     return (T[])v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Fill(int[] A, int number) {
/* 1595 */     for (int i = 0; i < A.length; i++) {
/* 1596 */       A[i] = number;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Fill(float[] A, float number) {
/* 1606 */     for (int i = 0; i < A.length; i++) {
/* 1607 */       A[i] = number;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Fill(double[][] A, double number) {
/* 1617 */     for (int i = 0; i < A.length; i++) {
/* 1618 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1619 */         A[i][j] = number;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Fill(int[][] A, int number) {
/* 1630 */     for (int i = 0; i < A.length; i++) {
/* 1631 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1632 */         A[i][j] = number;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Fill(float[][] A, float number) {
/* 1643 */     for (int i = 0; i < A.length; i++) {
/* 1644 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1645 */         A[i][j] = number;
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
/*      */   public static double[] Subtract(double[] A, double[] B) {
/* 1657 */     double[] r = new double[A.length];
/* 1658 */     for (int i = 0; i < A.length; i++) {
/* 1659 */       r[i] = A[i] - B[i];
/*      */     }
/* 1661 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] Subtract(int[] A, int[] B) {
/* 1671 */     int[] r = new int[A.length];
/* 1672 */     for (int i = 0; i < A.length; i++) {
/* 1673 */       r[i] = A[i] - B[i];
/*      */     }
/* 1675 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] Subtract(float[] A, float[] B) {
/* 1685 */     float[] r = new float[A.length];
/* 1686 */     for (int i = 0; i < A.length; i++) {
/* 1687 */       r[i] = A[i] - B[i];
/*      */     }
/* 1689 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Subtract(double[][] A, double[][] B) {
/* 1699 */     double[][] r = new double[A.length][(A[0]).length];
/* 1700 */     for (int i = 0; i < A.length; i++) {
/* 1701 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1702 */         r[i][j] = A[i][j] - B[i][j];
/*      */       }
/*      */     } 
/* 1705 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] Subtract(int[][] A, int[][] B) {
/* 1715 */     int[][] r = new int[A.length][(A[0]).length];
/* 1716 */     for (int i = 0; i < A.length; i++) {
/* 1717 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1718 */         r[i][j] = A[i][j] - B[i][j];
/*      */       }
/*      */     } 
/* 1721 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] Subtract(float[][] A, float[][] B) {
/* 1731 */     float[][] r = new float[A.length][(A[0]).length];
/* 1732 */     for (int i = 0; i < A.length; i++) {
/* 1733 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1734 */         r[i][j] = A[i][j] - B[i][j];
/*      */       }
/*      */     } 
/* 1737 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Subtract(double[] A, double scalar) {
/* 1746 */     for (int i = 0; i < A.length; i++) {
/* 1747 */       A[i] = A[i] - scalar;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Subtract(int[] A, int scalar) {
/* 1757 */     for (int i = 0; i < A.length; i++) {
/* 1758 */       A[i] = A[i] - scalar;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Subtract(float[] A, float scalar) {
/* 1768 */     for (int i = 0; i < A.length; i++) {
/* 1769 */       A[i] = A[i] - scalar;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Sum(double[][] A) {
/* 1779 */     double sum = 0.0D;
/* 1780 */     for (int i = 0; i < A.length; i++) {
/* 1781 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1782 */         sum += A[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 1786 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int Sum(int[][] A) {
/* 1795 */     int sum = 0;
/* 1796 */     for (int i = 0; i < A.length; i++) {
/* 1797 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1798 */         sum += A[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 1802 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float Sum(float[][] A) {
/* 1811 */     float sum = 0.0F;
/* 1812 */     for (int i = 0; i < A.length; i++) {
/* 1813 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1814 */         sum += A[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 1818 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double SumAbs(double[][] A) {
/* 1827 */     double sum = 0.0D;
/* 1828 */     for (int i = 0; i < A.length; i++) {
/* 1829 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1830 */         sum += Math.abs(A[i][j]);
/*      */       }
/*      */     } 
/*      */     
/* 1834 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int SumAbs(int[][] A) {
/* 1843 */     int sum = 0;
/* 1844 */     for (int i = 0; i < A.length; i++) {
/* 1845 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1846 */         sum += Math.abs(A[i][j]);
/*      */       }
/*      */     } 
/*      */     
/* 1850 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float SumAbs(float[][] A) {
/* 1859 */     float sum = 0.0F;
/* 1860 */     for (int i = 0; i < A.length; i++) {
/* 1861 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1862 */         sum += Math.abs(A[i][j]);
/*      */       }
/*      */     } 
/*      */     
/* 1866 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Subtract(double[][] A, double scalar) {
/* 1875 */     for (int i = 0; i < A.length; i++) {
/* 1876 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1877 */         A[i][j] = A[i][j] - scalar;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Subtract(int[][] A, int scalar) {
/* 1888 */     for (int i = 0; i < A.length; i++) {
/* 1889 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1890 */         A[i][j] = A[i][j] - scalar;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void Subtract(float[][] A, float scalar) {
/* 1901 */     for (int i = 0; i < A.length; i++) {
/* 1902 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1903 */         A[i][j] = A[i][j] - scalar;
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
/*      */   public static void SwapColumn(double[][] A, int a, int b) {
/* 1915 */     for (int i = 0; i < A.length; i++) {
/* 1916 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1917 */         double t1 = A[i][a];
/* 1918 */         double t2 = A[i][b];
/* 1919 */         A[i][a] = t2;
/* 1920 */         A[i][b] = t1;
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
/*      */   public static void SwapColumn(int[][] A, int a, int b) {
/* 1932 */     for (int i = 0; i < A.length; i++) {
/* 1933 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1934 */         int t1 = A[i][a];
/* 1935 */         int t2 = A[i][b];
/* 1936 */         A[i][a] = t2;
/* 1937 */         A[i][b] = t1;
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
/*      */   public static void SwapColumn(float[][] A, int a, int b) {
/* 1949 */     for (int i = 0; i < A.length; i++) {
/* 1950 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1951 */         float t1 = A[i][a];
/* 1952 */         float t2 = A[i][b];
/* 1953 */         A[i][a] = t2;
/* 1954 */         A[i][b] = t1;
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
/*      */   public static void SwapRow(double[][] A, int a, int b) {
/* 1966 */     for (int i = 0; i < A.length; i++) {
/* 1967 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1968 */         double t1 = A[a][j];
/* 1969 */         double t2 = A[b][j];
/* 1970 */         A[a][j] = t2;
/* 1971 */         A[b][j] = t1;
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
/*      */   public static void SwapRow(int[][] A, int a, int b) {
/* 1983 */     for (int i = 0; i < A.length; i++) {
/* 1984 */       for (int j = 0; j < (A[0]).length; j++) {
/* 1985 */         int t1 = A[a][j];
/* 1986 */         int t2 = A[b][j];
/* 1987 */         A[a][j] = t2;
/* 1988 */         A[b][j] = t1;
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
/*      */   public static void SwapRow(float[][] A, int a, int b) {
/* 2000 */     for (int i = 0; i < A.length; i++) {
/* 2001 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2002 */         float t1 = A[a][j];
/* 2003 */         float t2 = A[b][j];
/* 2004 */         A[a][j] = t2;
/* 2005 */         A[b][j] = t1;
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
/*      */   public static double[] Multiply(double[] A, double scalar) {
/* 2017 */     double[] r = new double[A.length];
/* 2018 */     for (int i = 0; i < r.length; i++) {
/* 2019 */       r[i] = A[i] * scalar;
/*      */     }
/* 2021 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] Multiply(int[] A, int scalar) {
/* 2031 */     int[] r = new int[A.length];
/* 2032 */     for (int i = 0; i < r.length; i++) {
/* 2033 */       r[i] = A[i] * scalar;
/*      */     }
/* 2035 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] Multiply(float[] A, float scalar) {
/* 2045 */     float[] r = new float[A.length];
/* 2046 */     for (int i = 0; i < r.length; i++) {
/* 2047 */       r[i] = A[i] * scalar;
/*      */     }
/* 2049 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Multiply(double[][] A, double[][] B) {
/* 2060 */     if ((A[0]).length != B.length) {
/* 2061 */       throw new IllegalArgumentException("Illegal matrix dimensions.");
/*      */     }
/* 2063 */     double[][] result = new double[A.length][(B[0]).length];
/*      */     
/* 2065 */     int n = (A[0]).length;
/* 2066 */     int m = A.length;
/* 2067 */     int p = (B[0]).length;
/*      */     
/* 2069 */     double[] Bcolj = new double[n];
/* 2070 */     for (int j = 0; j < p; j++) {
/*      */       
/* 2072 */       for (int k = 0; k < n; k++) {
/* 2073 */         Bcolj[k] = B[k][j];
/*      */       }
/* 2075 */       for (int i = 0; i < m; i++) {
/*      */         
/* 2077 */         double[] Arowi = A[i];
/*      */         
/* 2079 */         double s = 0.0D;
/* 2080 */         for (int i1 = 0; i1 < n; i1++) {
/* 2081 */           s += Arowi[i1] * Bcolj[i1];
/*      */         }
/* 2083 */         result[i][j] = s;
/*      */       } 
/*      */     } 
/*      */     
/* 2087 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] Multiply(int[][] A, int[][] B) {
/* 2098 */     if ((A[0]).length != B.length) {
/* 2099 */       throw new IllegalArgumentException("Illegal matrix dimensions.");
/*      */     }
/* 2101 */     int[][] result = new int[A.length][(B[0]).length];
/*      */     
/* 2103 */     int n = (A[0]).length;
/* 2104 */     int m = A.length;
/* 2105 */     int p = (B[0]).length;
/*      */     
/* 2107 */     int[] Bcolj = new int[n];
/* 2108 */     for (int j = 0; j < p; j++) {
/*      */       
/* 2110 */       for (int k = 0; k < n; k++) {
/* 2111 */         Bcolj[k] = B[k][j];
/*      */       }
/* 2113 */       for (int i = 0; i < m; i++) {
/*      */         
/* 2115 */         int[] Arowi = A[i];
/*      */         
/* 2117 */         int s = 0;
/* 2118 */         for (int i1 = 0; i1 < n; i1++) {
/* 2119 */           s += Arowi[i1] * Bcolj[i1];
/*      */         }
/* 2121 */         result[i][j] = s;
/*      */       } 
/*      */     } 
/*      */     
/* 2125 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] Multiply(double[] A, double[][] B) {
/* 2136 */     double[] r = new double[(B[0]).length];
/* 2137 */     for (int j = 0; j < (B[0]).length; j++) {
/* 2138 */       for (int i = 0; i < B.length; i++) {
/* 2139 */         r[j] = r[j] + A[i] * B[i][j];
/*      */       }
/*      */     } 
/* 2142 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] Multiply(int[] A, int[][] B) {
/* 2153 */     int[] r = new int[(B[0]).length];
/* 2154 */     for (int j = 0; j < (B[0]).length; j++) {
/* 2155 */       for (int i = 0; i < B.length; i++) {
/* 2156 */         r[j] = r[j] + A[i] * B[i][j];
/*      */       }
/*      */     } 
/* 2159 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] Multiply(float[] A, float[][] B) {
/* 2170 */     float[] r = new float[(B[0]).length];
/* 2171 */     for (int j = 0; j < (B[0]).length; j++) {
/* 2172 */       for (int i = 0; i < B.length; i++) {
/* 2173 */         r[j] = r[j] + A[i] * B[i][j];
/*      */       }
/*      */     } 
/* 2176 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] Multiply(float[][] A, float[][] B) {
/* 2187 */     if ((A[0]).length != B.length) {
/* 2188 */       throw new IllegalArgumentException("Illegal matrix dimensions.");
/*      */     }
/* 2190 */     float[][] result = new float[A.length][(B[0]).length];
/*      */     
/* 2192 */     int n = (A[0]).length;
/* 2193 */     int m = A.length;
/* 2194 */     int p = (B[0]).length;
/*      */     
/* 2196 */     float[] Bcolj = new float[n];
/* 2197 */     for (int j = 0; j < p; j++) {
/*      */       
/* 2199 */       for (int k = 0; k < n; k++) {
/* 2200 */         Bcolj[k] = B[k][j];
/*      */       }
/* 2202 */       for (int i = 0; i < m; i++) {
/*      */         
/* 2204 */         float[] Arowi = A[i];
/*      */         
/* 2206 */         float s = 0.0F;
/* 2207 */         for (int i1 = 0; i1 < n; i1++) {
/* 2208 */           s += Arowi[i1] * Bcolj[i1];
/*      */         }
/* 2210 */         result[i][j] = s;
/*      */       } 
/*      */     } 
/*      */     
/* 2214 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Multiply(double[][] A, double value) {
/* 2225 */     double[][] result = new double[A.length][(A[0]).length];
/* 2226 */     for (int i = 0; i < A.length; i++) {
/* 2227 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2228 */         result[i][j] = A[i][j] * value;
/*      */       }
/*      */     } 
/* 2231 */     return result;
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
/*      */   public static float[][] Multiply(float[][] A, float value) {
/* 2243 */     float[][] result = new float[A.length][(A[0]).length];
/* 2244 */     for (int i = 0; i < A.length; i++) {
/* 2245 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2246 */         result[i][j] = A[i][j] * value;
/*      */       }
/*      */     } 
/* 2249 */     return result;
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
/*      */   public static int[][] Multiply(int[][] A, int value) {
/* 2261 */     int[][] result = new int[A.length][(A[0]).length];
/* 2262 */     for (int i = 0; i < A.length; i++) {
/* 2263 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2264 */         result[i][j] = A[i][j] * value;
/*      */       }
/*      */     } 
/* 2267 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public static double[][] MultiplyByDiagonal(double[][] A, double[] B) {
/* 2272 */     double[][] r = new double[A.length][B.length];
/*      */     
/* 2274 */     for (int i = 0; i < r.length; i++) {
/* 2275 */       for (int j = 0; j < (r[0]).length; j++) {
/* 2276 */         r[i][j] = A[i][j] * B[j];
/*      */       }
/*      */     } 
/*      */     
/* 2280 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] MultiplyByTranspose(double[][] A) {
/* 2289 */     return Multiply(A, Transpose(A));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] MultiplyByTranspose(int[][] A) {
/* 2298 */     return Multiply(A, Transpose(A));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] MultiplyByTranspose(float[][] A) {
/* 2307 */     return Multiply(A, Transpose(A));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] MultiplyByTranspose(double[][] A, double[][] B) {
/* 2317 */     return Multiply(A, Transpose(B));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] MultiplyByTranspose(int[][] A, int[][] B) {
/* 2327 */     return Multiply(A, Transpose(B));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] MultiplyByTranspose(float[][] A, float[][] B) {
/* 2337 */     return Multiply(A, Transpose(B));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] MultiplyByTranspose(double[][] A, double[] B) {
/* 2348 */     if ((A[0]).length != B.length) {
/* 2349 */       throw new IllegalArgumentException("The columns of the matrix A must be the same of the vector B");
/*      */     }
/* 2351 */     double[] result = new double[A.length];
/* 2352 */     for (int i = 0; i < A.length; i++) {
/* 2353 */       double r = 0.0D;
/* 2354 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2355 */         r += A[i][j] * B[j];
/*      */       }
/* 2357 */       result[i] = r;
/*      */     } 
/* 2359 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] MultiplyByTranspose(int[][] A, int[] B) {
/* 2370 */     if ((A[0]).length != B.length) {
/* 2371 */       throw new IllegalArgumentException("The columns of the matrix A must be the same of the vector B");
/*      */     }
/* 2373 */     int[] result = new int[A.length];
/* 2374 */     for (int i = 0; i < A.length; i++) {
/* 2375 */       int r = 0;
/* 2376 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2377 */         r += A[i][j] * B[j];
/*      */       }
/* 2379 */       result[i] = r;
/*      */     } 
/* 2381 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] MultiplyByTranspose(float[][] A, float[] B) {
/* 2392 */     if ((A[0]).length != B.length) {
/* 2393 */       throw new IllegalArgumentException("The columns of the matrix A must be the same of the vector B");
/*      */     }
/* 2395 */     float[] result = new float[A.length];
/* 2396 */     for (int i = 0; i < A.length; i++) {
/* 2397 */       float r = 0.0F;
/* 2398 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2399 */         r += A[i][j] * B[j];
/*      */       }
/* 2401 */       result[i] = r;
/*      */     } 
/* 2403 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Norm1(double[][] A) {
/* 2413 */     double max = 0.0D;
/* 2414 */     for (int j = 0; j < (A[0]).length; j++) {
/* 2415 */       double sum = 0.0D;
/* 2416 */       for (int i = 0; i < A.length; i++) {
/* 2417 */         sum += Math.abs(A[i][j]);
/*      */       }
/* 2419 */       max = Math.max(max, sum);
/*      */     } 
/*      */     
/* 2422 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int Norm1(int[][] A) {
/* 2433 */     int max = 0;
/* 2434 */     for (int j = 0; j < (A[0]).length; j++) {
/* 2435 */       int sum = 0;
/* 2436 */       for (int i = 0; i < A.length; i++) {
/* 2437 */         sum += Math.abs(A[i][j]);
/*      */       }
/* 2439 */       max = Math.max(max, sum);
/*      */     } 
/*      */     
/* 2442 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float Norm1(float[][] A) {
/* 2453 */     float max = 0.0F;
/* 2454 */     for (int j = 0; j < (A[0]).length; j++) {
/* 2455 */       float sum = 0.0F;
/* 2456 */       for (int i = 0; i < A.length; i++) {
/* 2457 */         sum += Math.abs(A[i][j]);
/*      */       }
/* 2459 */       max = Math.max(max, sum);
/*      */     } 
/*      */     
/* 2462 */     return max;
/*      */   }
/*      */ 
/*      */   
/*      */   public static double Norm2(double[] A) {
/* 2467 */     double sum = 0.0D;
/* 2468 */     for (int i = 0; i < A.length; i++) {
/* 2469 */       sum += Math.pow(Math.abs(A[i]), 2.0D);
/*      */     }
/* 2471 */     return Math.sqrt(sum);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Norm2(double[][] A) {
/* 2480 */     return (new SingularValueDecomposition(A)).getS()[0][0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double NormF(double[][] A) {
/* 2490 */     double sum = 0.0D;
/* 2491 */     for (int i = 0; i < A.length; i++) {
/* 2492 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2493 */         sum += Math.pow(Math.abs(A[i][j]), 2.0D);
/*      */       }
/*      */     } 
/*      */     
/* 2497 */     return Math.sqrt(sum);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double NormF(int[][] A) {
/* 2508 */     double sum = 0.0D;
/* 2509 */     for (int i = 0; i < A.length; i++) {
/* 2510 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2511 */         sum += Math.pow(Math.abs(A[i][j]), 2.0D);
/*      */       }
/*      */     } 
/*      */     
/* 2515 */     return Math.sqrt(sum);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float NormF(float[][] A) {
/* 2526 */     float sum = 0.0F;
/* 2527 */     for (int i = 0; i < A.length; i++) {
/* 2528 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2529 */         sum = (float)(sum + Math.pow(Math.abs(A[i][j]), 2.0D));
/*      */       }
/*      */     } 
/*      */     
/* 2533 */     return (float)Math.sqrt(sum);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double NormP(double[] v, int p) {
/* 2544 */     double sum = 0.0D;
/* 2545 */     for (int i = 0; i < v.length; i++) {
/* 2546 */       sum += Math.pow(Math.abs(v[i]), p);
/*      */     }
/* 2548 */     return Math.pow(sum, 1.0D / p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] OuterProduct(double[] u) {
/* 2558 */     return OuterProduct(u, u);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] OuterProduct(double[] u, double[] v) {
/* 2569 */     double[][] r = new double[u.length][v.length];
/*      */     
/* 2571 */     for (int i = 0; i < u.length; i++) {
/* 2572 */       for (int j = 0; j < v.length; j++) {
/* 2573 */         r[i][j] = u[i] * v[j];
/*      */       }
/*      */     } 
/*      */     
/* 2577 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] OuterProduct(int[] u) {
/* 2587 */     return OuterProduct(u, u);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] OuterProduct(int[] u, int[] v) {
/* 2598 */     int[][] r = new int[u.length][v.length];
/*      */     
/* 2600 */     for (int i = 0; i < u.length; i++) {
/* 2601 */       for (int j = 0; j < v.length; j++) {
/* 2602 */         r[i][j] = u[i] * v[j];
/*      */       }
/*      */     } 
/*      */     
/* 2606 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] OuterProduct(float[] u) {
/* 2616 */     return OuterProduct(u, u);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] OuterProduct(float[] u, float[] v) {
/* 2627 */     float[][] r = new float[u.length][v.length];
/*      */     
/* 2629 */     for (int i = 0; i < u.length; i++) {
/* 2630 */       for (int j = 0; j < v.length; j++) {
/* 2631 */         r[i][j] = u[i] * v[j];
/*      */       }
/*      */     } 
/*      */     
/* 2635 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] PseudoInverse(double[][] A) {
/* 2644 */     return (new SingularValueDecomposition(A)).inverse();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Trace(double[][] A) {
/* 2653 */     if (isSquare(A)) {
/* 2654 */       double sum = 0.0D;
/* 2655 */       for (int i = 0; i < A.length; i++) {
/* 2656 */         sum += A[i][i];
/*      */       }
/* 2658 */       return sum;
/*      */     } 
/*      */     
/* 2661 */     throw new IllegalArgumentException("The matrix must be square.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int Trace(int[][] A) {
/* 2671 */     if (isSquare(A)) {
/* 2672 */       int sum = 0;
/* 2673 */       for (int i = 0; i < A.length; i++) {
/* 2674 */         sum += A[i][i];
/*      */       }
/* 2676 */       return sum;
/*      */     } 
/*      */     
/* 2679 */     throw new IllegalArgumentException("The matrix must be square.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float Trace(float[][] A) {
/* 2689 */     if (isSquare(A)) {
/* 2690 */       float sum = 0.0F;
/* 2691 */       for (int i = 0; i < A.length; i++) {
/* 2692 */         sum += A[i][i];
/*      */       }
/* 2694 */       return sum;
/*      */     } 
/*      */     
/* 2697 */     throw new IllegalArgumentException("The matrix must be square.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Transpose(double[][] A) {
/* 2707 */     double[][] t = new double[(A[0]).length][A.length];
/*      */     
/* 2709 */     for (int i = 0; i < A.length; i++) {
/* 2710 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2711 */         t[j][i] = A[i][j];
/*      */       }
/*      */     } 
/* 2714 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] Transpose(int[][] A) {
/* 2723 */     int[][] t = new int[(A[0]).length][A.length];
/*      */     
/* 2725 */     for (int i = 0; i < A.length; i++) {
/* 2726 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2727 */         t[j][i] = A[i][j];
/*      */       }
/*      */     } 
/* 2730 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] Transpose(float[][] A) {
/* 2739 */     float[][] t = new float[(A[0]).length][A.length];
/*      */     
/* 2741 */     for (int i = 0; i < A.length; i++) {
/* 2742 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2743 */         t[j][i] = A[i][j];
/*      */       }
/*      */     } 
/* 2746 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> E[][] Transpose(Object[][] A) {
/* 2756 */     Object[][] t = (Object[][])Array.newInstance(A[0][0].getClass(), new int[] { (A[0]).length, A.length });
/*      */     
/* 2758 */     for (int i = 0; i < A.length; i++) {
/* 2759 */       for (int j = 0; j < (A[0]).length; j++) {
/* 2760 */         t[j][i] = A[i][j];
/*      */       }
/*      */     } 
/* 2763 */     return (E[][])t;
/*      */   }
/*      */   
/*      */   public static double[] UniformRandom(List<DoubleRange> ranges) {
/* 2767 */     Random rand = new Random();
/*      */     
/* 2769 */     double[] r = new double[ranges.size()];
/* 2770 */     for (int i = 0; i < r.length; i++) {
/* 2771 */       DoubleRange range = ranges.get(i);
/* 2772 */       r[i] = range.getMin() + rand.nextDouble() * (range.getMax() - range.getMin());
/*      */     } 
/* 2774 */     return r;
/*      */   }
/*      */   
/*      */   public static double[] UniformRandom(List<DoubleRange> ranges, long seed) {
/* 2778 */     Random rand = new Random(seed);
/*      */     
/* 2780 */     double[] r = new double[ranges.size()];
/* 2781 */     for (int i = 0; i < r.length; i++) {
/* 2782 */       DoubleRange range = ranges.get(i);
/* 2783 */       r[i] = range.getMin() + rand.nextDouble() * (range.getMax() - range.getMin());
/*      */     } 
/* 2785 */     return r;
/*      */   }
/*      */   
/*      */   public static double[] UniformRandom(DoubleRange range, int size) {
/* 2789 */     return UniformRandom(range.getMin(), range.getMax(), size);
/*      */   }
/*      */   
/*      */   public static double[] UniformRandom(double min, double max, int size) {
/* 2793 */     Random rand = new Random();
/*      */     
/* 2795 */     double[] r = new double[size];
/* 2796 */     for (int i = 0; i < r.length; i++) {
/* 2797 */       r[i] = min + rand.nextDouble() * (max - min);
/*      */     }
/*      */     
/* 2800 */     return r;
/*      */   }
/*      */   
/*      */   public static double[] UniformRandom(double min, double max, int size, long seed) {
/* 2804 */     Random rand = new Random(seed);
/*      */     
/* 2806 */     double[] r = new double[size];
/* 2807 */     for (int i = 0; i < r.length; i++) {
/* 2808 */       r[i] = min + rand.nextDouble() * (max - min);
/*      */     }
/*      */     
/* 2811 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Identity(int order) {
/* 2820 */     order = Math.max(order, 2);
/*      */     
/* 2822 */     double[][] matrix = new double[order][order];
/*      */     int i;
/* 2824 */     for (i = 0; i < order; i++) {
/* 2825 */       for (int j = 0; j < order; j++) {
/* 2826 */         matrix[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */     
/* 2830 */     for (i = 0; i < order; ) { matrix[i][i] = 1.0D; i++; }
/* 2831 */      return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Identity(int m, int n) {
/* 2841 */     double[][] A = new double[m][n];
/* 2842 */     for (int i = 0; i < m; i++) {
/* 2843 */       for (int j = 0; j < n; j++) {
/* 2844 */         A[i][j] = (i == j) ? 1.0D : 0.0D;
/*      */       }
/*      */     } 
/* 2847 */     return A;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Inverse(double[][] A) {
/* 2856 */     return (new LUDecomposition(A)).inverse();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] Linspace(double min, double max, int points) {
/* 2867 */     double[] v = new double[points];
/* 2868 */     for (int i = 0; i < points; i++) {
/* 2869 */       v[i] = min + i * (max - min) / (points - 1);
/*      */     }
/* 2871 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Max(double[][] matrix) {
/* 2880 */     double max = -2.147483648E9D;
/* 2881 */     for (int i = 0; i < matrix.length; i++) {
/* 2882 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 2883 */         max = Math.max(max, matrix[i][j]);
/*      */       }
/*      */     } 
/* 2886 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Max(double[] matrix) {
/* 2895 */     double max = -2.147483648E9D;
/* 2896 */     for (int i = 0; i < matrix.length; i++) {
/* 2897 */       max = Math.max(max, matrix[i]);
/*      */     }
/* 2899 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int Max(int[] matrix) {
/* 2908 */     int max = Integer.MIN_VALUE;
/* 2909 */     for (int i = 0; i < matrix.length; i++) {
/* 2910 */       max = Math.max(max, matrix[i]);
/*      */     }
/* 2912 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float Max(float[] matrix) {
/* 2921 */     float max = -2.14748365E9F;
/* 2922 */     for (int i = 0; i < matrix.length; i++) {
/* 2923 */       max = Math.max(max, matrix[i]);
/*      */     }
/* 2925 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int Max(int[][] matrix) {
/* 2934 */     int max = Integer.MIN_VALUE;
/* 2935 */     for (int i = 0; i < matrix.length; i++) {
/* 2936 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 2937 */         max = Math.max(max, matrix[i][j]);
/*      */       }
/*      */     } 
/* 2940 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float Max(float[][] matrix) {
/* 2949 */     float max = -2.14748365E9F;
/* 2950 */     for (int i = 0; i < matrix.length; i++) {
/* 2951 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 2952 */         max = Math.max(max, matrix[i][j]);
/*      */       }
/*      */     } 
/* 2955 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int MaxIndex(double[] matrix) {
/* 2964 */     int index = 0;
/* 2965 */     double max = Double.MIN_VALUE;
/* 2966 */     for (int i = 0; i < matrix.length; i++) {
/* 2967 */       double currentValue = Math.max(max, matrix[i]);
/* 2968 */       if (currentValue > max) {
/* 2969 */         max = currentValue;
/* 2970 */         index = i;
/*      */       } 
/*      */     } 
/* 2973 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntPoint MaxIndex(double[][] matrix) {
/* 2982 */     IntPoint index = new IntPoint();
/* 2983 */     double max = Double.MIN_VALUE;
/* 2984 */     for (int i = 0; i < matrix.length; i++) {
/* 2985 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 2986 */         double currentValue = Math.max(max, matrix[i][j]);
/* 2987 */         if (currentValue > max) {
/* 2988 */           max = currentValue;
/* 2989 */           index.setXY(i, j);
/*      */         } 
/*      */       } 
/*      */     } 
/* 2993 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int MaxIndex(int[] matrix) {
/* 3002 */     int index = 0;
/* 3003 */     int max = Integer.MIN_VALUE;
/* 3004 */     for (int i = 0; i < matrix.length; i++) {
/* 3005 */       int currentValue = Math.max(max, matrix[i]);
/* 3006 */       if (currentValue > max) {
/* 3007 */         max = currentValue;
/* 3008 */         index = i;
/*      */       } 
/*      */     } 
/* 3011 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntPoint MaxIndex(int[][] matrix) {
/* 3020 */     IntPoint index = new IntPoint();
/* 3021 */     int max = Integer.MIN_VALUE;
/* 3022 */     for (int i = 0; i < matrix.length; i++) {
/* 3023 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3024 */         int currentValue = Math.max(max, matrix[i][j]);
/* 3025 */         if (currentValue > max) {
/* 3026 */           max = currentValue;
/* 3027 */           index.setXY(i, j);
/*      */         } 
/*      */       } 
/*      */     } 
/* 3031 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int MaxIndex(float[] matrix) {
/* 3040 */     int index = 0;
/* 3041 */     float max = Float.MIN_VALUE;
/* 3042 */     for (int i = 0; i < matrix.length; i++) {
/* 3043 */       float currentValue = Math.max(max, matrix[i]);
/* 3044 */       if (currentValue > max) {
/* 3045 */         max = currentValue;
/* 3046 */         index = i;
/*      */       } 
/*      */     } 
/* 3049 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntPoint MaxIndex(float[][] matrix) {
/* 3058 */     IntPoint index = new IntPoint();
/* 3059 */     float max = Float.MIN_VALUE;
/* 3060 */     for (int i = 0; i < matrix.length; i++) {
/* 3061 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3062 */         float currentValue = Math.max(max, matrix[i][j]);
/* 3063 */         if (currentValue > max) {
/* 3064 */           max = currentValue;
/* 3065 */           index.setXY(i, j);
/*      */         } 
/*      */       } 
/*      */     } 
/* 3069 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Mean(double[][] matrix) {
/* 3078 */     double mean = 0.0D;
/* 3079 */     for (int i = 0; i < matrix.length; i++) {
/* 3080 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3081 */         mean += matrix[i][j];
/*      */       }
/*      */     } 
/* 3084 */     return mean /= (matrix.length * (matrix[0]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Mean(int[][] matrix) {
/* 3093 */     double mean = 0.0D;
/* 3094 */     for (int i = 0; i < matrix.length; i++) {
/* 3095 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3096 */         mean += matrix[i][j];
/*      */       }
/*      */     } 
/* 3099 */     return mean /= (matrix.length * (matrix[0]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float Mean(float[][] matrix) {
/* 3108 */     float mean = 0.0F;
/* 3109 */     for (int i = 0; i < matrix.length; i++) {
/* 3110 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3111 */         mean += matrix[i][j];
/*      */       }
/*      */     } 
/* 3114 */     return mean /= (matrix.length * (matrix[0]).length);
/*      */   }
/*      */   
/*      */   public static int[][] MemberwiseClone(int[][] data) {
/* 3118 */     int[][] clone = new int[data.length][];
/* 3119 */     for (int i = 0; i < data.length; i++)
/* 3120 */       clone[i] = (int[])data[i].clone(); 
/* 3121 */     return clone;
/*      */   }
/*      */   
/*      */   public static float[][] MemberwiseClone(float[][] data) {
/* 3125 */     float[][] clone = new float[data.length][];
/* 3126 */     for (int i = 0; i < data.length; i++)
/* 3127 */       clone[i] = (float[])data[i].clone(); 
/* 3128 */     return clone;
/*      */   }
/*      */   
/*      */   public static double[][] MemberwiseClone(double[][] data) {
/* 3132 */     double[][] clone = new double[data.length][];
/* 3133 */     for (int i = 0; i < data.length; i++)
/* 3134 */       clone[i] = (double[])data[i].clone(); 
/* 3135 */     return clone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Min(double[] matrix) {
/* 3144 */     double min = 2.147483647E9D;
/* 3145 */     for (int i = 0; i < matrix.length; i++) {
/* 3146 */       min = Math.min(min, matrix[i]);
/*      */     }
/* 3148 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double Min(double[][] matrix) {
/* 3157 */     double min = Double.MAX_VALUE;
/* 3158 */     for (int i = 0; i < matrix.length; i++) {
/* 3159 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3160 */         if (matrix[i][j] != Double.NaN) {
/* 3161 */           min = Math.min(min, matrix[i][j]);
/*      */         }
/*      */       } 
/*      */     } 
/* 3165 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int Min(int[] matrix) {
/* 3174 */     int min = Integer.MAX_VALUE;
/* 3175 */     for (int i = 0; i < matrix.length; i++) {
/* 3176 */       min = Math.min(min, matrix[i]);
/*      */     }
/* 3178 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float Min(float[] matrix) {
/* 3187 */     float min = 2.14748365E9F;
/* 3188 */     for (int i = 0; i < matrix.length; i++) {
/* 3189 */       min = Math.min(min, matrix[i]);
/*      */     }
/* 3191 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int Min(int[][] matrix) {
/* 3200 */     int min = Integer.MAX_VALUE;
/* 3201 */     for (int i = 0; i < matrix.length; i++) {
/* 3202 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3203 */         min = Math.min(min, matrix[i][j]);
/*      */       }
/*      */     } 
/* 3206 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float Min(float[][] matrix) {
/* 3215 */     float min = 2.14748365E9F;
/* 3216 */     for (int i = 0; i < matrix.length; i++) {
/* 3217 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3218 */         min = Math.min(min, matrix[i][j]);
/*      */       }
/*      */     } 
/* 3221 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int MinIndex(double[] matrix) {
/* 3230 */     int index = 0;
/* 3231 */     double min = Double.MAX_VALUE;
/* 3232 */     for (int i = 0; i < matrix.length; i++) {
/* 3233 */       double currentValue = Math.min(min, matrix[i]);
/* 3234 */       if (currentValue < min) {
/* 3235 */         min = currentValue;
/* 3236 */         index = i;
/*      */       } 
/*      */     } 
/* 3239 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntPoint MinIndex(double[][] matrix) {
/* 3248 */     IntPoint index = new IntPoint();
/* 3249 */     double min = Double.MAX_VALUE;
/* 3250 */     for (int i = 0; i < matrix.length; i++) {
/* 3251 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3252 */         double currentValue = Math.min(min, matrix[i][j]);
/* 3253 */         if (currentValue < min) {
/* 3254 */           min = currentValue;
/* 3255 */           index.setXY(i, j);
/*      */         } 
/*      */       } 
/*      */     } 
/* 3259 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] MinMax(double[][] matrix) {
/* 3268 */     double min = Double.MAX_VALUE;
/* 3269 */     double max = -1.7976931348623157E308D;
/* 3270 */     for (int i = 0; i < matrix.length; i++) {
/* 3271 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3272 */         min = Math.min(min, matrix[i][j]);
/* 3273 */         max = Math.max(max, matrix[i][j]);
/*      */       } 
/*      */     } 
/* 3276 */     return new double[] { min, max };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] MinMax(int[][] matrix) {
/* 3285 */     int min = Integer.MAX_VALUE;
/* 3286 */     int max = -2147483647;
/* 3287 */     for (int i = 0; i < matrix.length; i++) {
/* 3288 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3289 */         min = Math.min(min, matrix[i][j]);
/* 3290 */         max = Math.max(max, matrix[i][j]);
/*      */       } 
/*      */     } 
/* 3293 */     return new int[] { min, max };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] MinMax(float[][] matrix) {
/* 3302 */     float min = Float.MAX_VALUE;
/* 3303 */     float max = -3.4028235E38F;
/* 3304 */     for (int i = 0; i < matrix.length; i++) {
/* 3305 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3306 */         min = Math.min(min, matrix[i][j]);
/* 3307 */         max = Math.max(max, matrix[i][j]);
/*      */       } 
/*      */     } 
/* 3310 */     return new float[] { min, max };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int MinIndex(int[] matrix) {
/* 3319 */     int index = 0;
/* 3320 */     int min = Integer.MAX_VALUE;
/* 3321 */     for (int i = 0; i < matrix.length; i++) {
/* 3322 */       int currentValue = Math.min(min, matrix[i]);
/* 3323 */       if (currentValue < min) {
/* 3324 */         min = currentValue;
/* 3325 */         index = i;
/*      */       } 
/*      */     } 
/* 3328 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntPoint MinIndex(int[][] matrix) {
/* 3337 */     IntPoint index = new IntPoint();
/* 3338 */     int min = Integer.MAX_VALUE;
/* 3339 */     for (int i = 0; i < matrix.length; i++) {
/* 3340 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3341 */         int currentValue = Math.min(min, matrix[i][j]);
/* 3342 */         if (currentValue < min) {
/* 3343 */           min = currentValue;
/* 3344 */           index.setXY(i, j);
/*      */         } 
/*      */       } 
/*      */     } 
/* 3348 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int MinIndex(float[] matrix) {
/* 3357 */     int index = 0;
/* 3358 */     float min = Float.MAX_VALUE;
/* 3359 */     for (int i = 0; i < matrix.length; i++) {
/* 3360 */       float currentValue = Math.min(min, matrix[i]);
/* 3361 */       if (currentValue < min) {
/* 3362 */         min = currentValue;
/* 3363 */         index = i;
/*      */       } 
/*      */     } 
/* 3366 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntPoint MinIndex(float[][] matrix) {
/* 3375 */     IntPoint index = new IntPoint();
/* 3376 */     float min = Float.MAX_VALUE;
/* 3377 */     for (int i = 0; i < matrix.length; i++) {
/* 3378 */       for (int j = 0; j < (matrix[0]).length; j++) {
/* 3379 */         float currentValue = Math.min(min, matrix[i][j]);
/* 3380 */         if (currentValue < min) {
/* 3381 */           min = currentValue;
/* 3382 */           index.setXY(i, j);
/*      */         } 
/*      */       } 
/*      */     } 
/* 3386 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEqual(double[][] A, double[][] B) {
/* 3397 */     if (A.length != B.length || (A[0]).length != (B[0]).length) {
/* 3398 */       throw new IllegalArgumentException("The matrix A must be the same size of the B.");
/*      */     }
/* 3400 */     for (int i = 0; i < A.length; i++) {
/* 3401 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3402 */         if (A[i][j] != B[i][j]) {
/* 3403 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 3407 */     return true;
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
/*      */   public static boolean isEqual(int[][] A, int[][] B) {
/* 3419 */     if (A.length != B.length || (A[0]).length != (B[0]).length) {
/* 3420 */       throw new IllegalArgumentException("The matrix A must be the same size of the B.");
/*      */     }
/* 3422 */     for (int i = 0; i < A.length; i++) {
/* 3423 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3424 */         if (A[i][j] != B[i][j]) {
/* 3425 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 3429 */     return true;
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
/*      */   public static boolean isEqual(float[][] A, float[][] B) {
/* 3441 */     if (A.length != B.length || (A[0]).length != (B[0]).length) {
/* 3442 */       throw new IllegalArgumentException("The matrix A must be the same size of the B.");
/*      */     }
/* 3444 */     for (int i = 0; i < A.length; i++) {
/* 3445 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3446 */         if (A[i][j] != B[i][j]) {
/* 3447 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 3451 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNonNegative(double[][] A) {
/* 3462 */     for (int i = 0; i < A.length; i++) {
/* 3463 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3464 */         if (A[i][j] < 0.0D) return false; 
/*      */       } 
/* 3466 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNonNegative(int[][] A) {
/* 3476 */     for (int i = 0; i < A.length; i++) {
/* 3477 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3478 */         if (A[i][j] < 0) return false; 
/*      */       } 
/* 3480 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNonNegative(float[][] A) {
/* 3490 */     for (int i = 0; i < A.length; i++) {
/* 3491 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3492 */         if (A[i][j] < 0.0F) return false; 
/*      */       } 
/* 3494 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSquare(double[][] A) {
/* 3503 */     if (A.length * A.length == A.length * (A[0]).length) {
/* 3504 */       return true;
/*      */     }
/* 3506 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSquare(int[][] A) {
/* 3515 */     if (A.length * A.length == A.length * (A[0]).length) {
/* 3516 */       return true;
/*      */     }
/* 3518 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSquare(float[][] A) {
/* 3527 */     if (A.length * A.length == A.length * (A[0]).length) {
/* 3528 */       return true;
/*      */     }
/* 3530 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSymmetric(double[][] A) {
/* 3539 */     double[][] B = Transpose(A);
/* 3540 */     return isEqual(A, B);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSymmetric(int[][] A) {
/* 3549 */     int[][] B = Transpose(A);
/* 3550 */     return isEqual(A, B);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSymmetric(float[][] A) {
/* 3559 */     float[][] B = Transpose(A);
/* 3560 */     return isEqual(A, B);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(int[] A) {
/* 3569 */     for (int i = 0; i < A.length; i++) {
/* 3570 */       if (A[i] != 0) return false; 
/* 3571 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(float[] A) {
/* 3580 */     for (int i = 0; i < A.length; i++) {
/* 3581 */       if (A[i] != 0.0F) return false; 
/* 3582 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(double[] A) {
/* 3591 */     for (int i = 0; i < A.length; i++) {
/* 3592 */       if (A[i] != 0.0D) return false; 
/* 3593 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(int[][] A) {
/* 3602 */     for (int i = 0; i < A.length; i++) {
/* 3603 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3604 */         if (A[i][j] != 0) return false; 
/*      */       } 
/*      */     } 
/* 3607 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(float[][] A) {
/* 3616 */     for (int i = 0; i < A.length; i++) {
/* 3617 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3618 */         if (A[i][j] != 0.0F) return false; 
/*      */       } 
/*      */     } 
/* 3621 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(double[][] A) {
/* 3630 */     for (int i = 0; i < A.length; i++) {
/* 3631 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3632 */         if (A[i][j] != 0.0D) return false; 
/*      */       } 
/*      */     } 
/* 3635 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] RandomPermutation(int n) {
/* 3645 */     int[] indexes = Indices(0, n);
/* 3646 */     ArraysUtil.Shuffle(indexes);
/* 3647 */     return indexes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int Rank(double[][] A) {
/* 3657 */     SingularValueDecomposition svd = new SingularValueDecomposition(A, false, false);
/* 3658 */     return svd.rank();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int Rank(int[][] A) {
/* 3667 */     SingularValueDecomposition svd = new SingularValueDecomposition(ArraysUtil.toDouble(A), false, false);
/* 3668 */     return svd.rank();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int Rank(float[][] A) {
/* 3677 */     SingularValueDecomposition svd = new SingularValueDecomposition(ArraysUtil.toDouble(A), false, false);
/* 3678 */     return svd.rank();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] RemoveColumns(int[][] A, int[] index) {
/* 3689 */     if ((A[0]).length - index.length <= 0) {
/* 3690 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 3692 */     int[][] B = new int[A.length][(A[0]).length - index.length];
/*      */ 
/*      */ 
/*      */     
/* 3696 */     for (int i = 0; i < A.length; i++) {
/* 3697 */       int idx = index[0];
/* 3698 */       int p = 0;
/* 3699 */       int c = 0;
/* 3700 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3701 */         if (j == idx) {
/* 3702 */           if (p < index.length - 1) {
/* 3703 */             idx = index[++p];
/*      */           }
/*      */         } else {
/* 3706 */           B[i][c++] = A[i][j];
/*      */         } 
/*      */       } 
/*      */     } 
/* 3710 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] RemoveColumn(double[][] A, int index) {
/* 3720 */     double[][] B = new double[A.length][(A[0]).length - 1];
/*      */     
/* 3722 */     for (int i = 0; i < A.length; i++) {
/* 3723 */       int idx = 0;
/* 3724 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3725 */         if (j != index) {
/* 3726 */           B[i][idx] = A[i][j];
/* 3727 */           idx++;
/*      */         } 
/*      */       } 
/*      */     } 
/* 3731 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] RemoveColumn(int[][] A, int index) {
/* 3741 */     int[][] B = new int[A.length][(A[0]).length - 1];
/*      */     
/* 3743 */     for (int i = 0; i < A.length; i++) {
/* 3744 */       int idx = 0;
/* 3745 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3746 */         if (j != index) {
/* 3747 */           B[i][idx] = A[i][j];
/* 3748 */           idx++;
/*      */         } 
/*      */       } 
/*      */     } 
/* 3752 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] RemoveColumn(float[][] A, int index) {
/* 3762 */     float[][] B = new float[A.length][(A[0]).length - 1];
/*      */     
/* 3764 */     for (int i = 0; i < A.length; i++) {
/* 3765 */       int idx = 0;
/* 3766 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3767 */         if (j != index) {
/* 3768 */           B[i][idx] = A[i][j];
/* 3769 */           idx++;
/*      */         } 
/*      */       } 
/*      */     } 
/* 3773 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] RemoveColumns(double[][] A, int[] index) {
/* 3784 */     if ((A[0]).length - index.length <= 0) {
/* 3785 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 3787 */     double[][] B = new double[A.length][(A[0]).length - index.length];
/*      */ 
/*      */ 
/*      */     
/* 3791 */     for (int i = 0; i < A.length; i++) {
/* 3792 */       int idx = index[0];
/* 3793 */       int p = 0;
/* 3794 */       int c = 0;
/* 3795 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3796 */         if (j == idx) {
/* 3797 */           if (p < index.length - 1) {
/* 3798 */             idx = index[++p];
/*      */           }
/*      */         } else {
/* 3801 */           B[i][c++] = A[i][j];
/*      */         } 
/*      */       } 
/*      */     } 
/* 3805 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] RemoveColumns(float[][] A, int[] index) {
/* 3816 */     if ((A[0]).length - index.length <= 0) {
/* 3817 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 3819 */     float[][] B = new float[A.length][(A[0]).length - index.length];
/*      */ 
/*      */ 
/*      */     
/* 3823 */     for (int i = 0; i < A.length; i++) {
/* 3824 */       int idx = index[0];
/* 3825 */       int p = 0;
/* 3826 */       int c = 0;
/* 3827 */       for (int j = 0; j < (A[0]).length; j++) {
/* 3828 */         if (j == idx) {
/* 3829 */           if (p < index.length - 1) {
/* 3830 */             idx = index[++p];
/*      */           }
/*      */         } else {
/* 3833 */           B[i][c++] = A[i][j];
/*      */         } 
/*      */       } 
/*      */     } 
/* 3837 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] RemoveColumn(double[] A, int index) {
/* 3847 */     if (A.length - index <= 0) {
/* 3848 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 3850 */     double[] B = new double[A.length - 1];
/* 3851 */     int idx = 0;
/* 3852 */     for (int i = 0; i < A.length; i++) {
/* 3853 */       if (i != index) {
/* 3854 */         B[idx++] = A[i];
/*      */       }
/*      */     } 
/*      */     
/* 3858 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] RemoveColumn(int[] A, int index) {
/* 3868 */     if (A.length - index <= 0) {
/* 3869 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 3871 */     int[] B = new int[A.length - 1];
/* 3872 */     int idx = 0;
/* 3873 */     for (int i = 0; i < A.length; i++) {
/* 3874 */       if (i != index) {
/* 3875 */         B[idx++] = A[i];
/*      */       }
/*      */     } 
/*      */     
/* 3879 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] RemoveColumn(float[] A, int index) {
/* 3889 */     if (A.length - index <= 0) {
/* 3890 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 3892 */     float[] B = new float[A.length - 1];
/* 3893 */     int idx = 0;
/* 3894 */     for (int i = 0; i < A.length; i++) {
/* 3895 */       if (i != index) {
/* 3896 */         B[idx++] = A[i];
/*      */       }
/*      */     } 
/*      */     
/* 3900 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] RemoveColumn(Object[] A, int index) {
/* 3911 */     if (A.length - index <= 0) {
/* 3912 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 3914 */     Object[] B = (Object[])Array.newInstance(A[0].getClass(), A.length - 1);
/* 3915 */     int idx = 0;
/* 3916 */     for (int i = 0; i < A.length; i++) {
/* 3917 */       if (i != index) {
/* 3918 */         B[idx++] = A[i];
/*      */       }
/*      */     } 
/*      */     
/* 3922 */     return (T[])B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] RemoveColumns(double[] A, int[] index) {
/* 3932 */     if (A.length - index.length <= 0) {
/* 3933 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 3935 */     double[] B = new double[A.length - index.length];
/* 3936 */     int idx = 0;
/* 3937 */     for (int i = 0; i < A.length; i++) {
/* 3938 */       boolean has = false;
/* 3939 */       for (int j = 0; j < index.length; j++) {
/* 3940 */         if (index[j] == i) has = true; 
/* 3941 */       }  if (!has) {
/* 3942 */         B[idx] = A[i];
/* 3943 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 3947 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] RemoveColumns(int[] A, int[] index) {
/* 3957 */     if (A.length - index.length <= 0) {
/* 3958 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 3960 */     int[] B = new int[A.length - index.length];
/* 3961 */     int idx = 0;
/* 3962 */     for (int i = 0; i < A.length; i++) {
/* 3963 */       boolean has = false;
/* 3964 */       for (int j = 0; j < index.length; j++) {
/* 3965 */         if (index[j] == i) has = true; 
/* 3966 */       }  if (!has) {
/* 3967 */         B[idx] = A[i];
/* 3968 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 3972 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] RemoveColumns(float[] A, int[] index) {
/* 3982 */     if (A.length - index.length <= 0) {
/* 3983 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 3985 */     float[] B = new float[A.length - index.length];
/* 3986 */     int idx = 0;
/* 3987 */     for (int i = 0; i < A.length; i++) {
/* 3988 */       boolean has = false;
/* 3989 */       for (int j = 0; j < index.length; j++) {
/* 3990 */         if (index[j] == i) has = true; 
/* 3991 */       }  if (!has) {
/* 3992 */         B[idx] = A[i];
/* 3993 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 3997 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] RemoveColumns(double[] A, List<Integer> index) {
/* 4007 */     if (A.length - index.size() <= 0) {
/* 4008 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 4010 */     double[] B = new double[A.length - index.size()];
/* 4011 */     int idx = 0;
/* 4012 */     for (int i = 0; i < A.length; i++) {
/* 4013 */       boolean has = false;
/* 4014 */       for (int j = 0; j < index.size(); j++) {
/* 4015 */         if (((Integer)index.get(j)).intValue() == i) has = true; 
/*      */       } 
/* 4017 */       if (!has) {
/* 4018 */         B[idx] = A[i];
/* 4019 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 4023 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] RemoveColumns(int[] A, List<Integer> index) {
/* 4033 */     if (A.length - index.size() <= 0) {
/* 4034 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 4036 */     int[] B = new int[A.length - index.size()];
/* 4037 */     int idx = 0;
/* 4038 */     for (int i = 0; i < A.length; i++) {
/* 4039 */       boolean has = false;
/* 4040 */       for (int j = 0; j < index.size(); j++) {
/* 4041 */         if (((Integer)index.get(j)).intValue() == i) has = true; 
/*      */       } 
/* 4043 */       if (!has) {
/* 4044 */         B[idx] = A[i];
/* 4045 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 4049 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] RemoveColumns(float[] A, List<Integer> index) {
/* 4059 */     if (A.length - index.size() <= 0) {
/* 4060 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 4062 */     float[] B = new float[A.length - index.size()];
/* 4063 */     int idx = 0;
/* 4064 */     for (int i = 0; i < A.length; i++) {
/* 4065 */       boolean has = false;
/* 4066 */       for (int j = 0; j < index.size(); j++) {
/* 4067 */         if (((Integer)index.get(j)).intValue() == i) has = true; 
/*      */       } 
/* 4069 */       if (!has) {
/* 4070 */         B[idx] = A[i];
/* 4071 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 4075 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] RemoveColumns(boolean[] A, int[] index) {
/* 4085 */     if (A.length - index.length <= 0) {
/* 4086 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 4088 */     boolean[] B = new boolean[A.length - index.length];
/* 4089 */     int idx = 0;
/* 4090 */     for (int i = 0; i < A.length; i++) {
/* 4091 */       boolean has = false;
/* 4092 */       for (int j = 0; j < index.length; j++) {
/* 4093 */         if (index[j] == i) has = true; 
/* 4094 */       }  if (!has) {
/* 4095 */         B[idx] = A[i];
/* 4096 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 4100 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[] RemoveColumns(Object[] A, int[] index) {
/* 4111 */     if (A.length - index.length <= 0) {
/* 4112 */       throw new IllegalArgumentException("The number of columns is less or equal zero.");
/*      */     }
/* 4114 */     Object[] B = (Object[])Array.newInstance(A[0].getClass(), A.length - index.length);
/* 4115 */     int idx = 0;
/* 4116 */     for (int i = 0; i < A.length; i++) {
/* 4117 */       boolean has = false;
/* 4118 */       for (int j = 0; j < index.length; j++) {
/* 4119 */         if (index[j] == i) has = true; 
/* 4120 */       }  if (!has) {
/* 4121 */         B[idx] = A[i];
/* 4122 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 4126 */     return (T[])B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] RemoveRow(double[][] A, int index) {
/* 4136 */     if (A.length - 1 <= 0) {
/* 4137 */       throw new IllegalArgumentException("The number of rows is less or equal zero.");
/*      */     }
/* 4139 */     double[][] B = new double[A.length - 1][(A[0]).length];
/* 4140 */     int idx = 0;
/* 4141 */     for (int i = 0; i < A.length; i++) {
/* 4142 */       if (i != index) {
/* 4143 */         System.arraycopy(A[i], 0, B[idx], 0, (A[0]).length);
/* 4144 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 4148 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] RemoveRow(int[][] A, int index) {
/* 4158 */     if (A.length - 1 <= 0) {
/* 4159 */       throw new IllegalArgumentException("The number of rows is less or equal zero.");
/*      */     }
/* 4161 */     int[][] B = new int[A.length - 1][(A[0]).length];
/* 4162 */     int idx = 0;
/* 4163 */     for (int i = 0; i < A.length; i++) {
/* 4164 */       if (i != index) {
/* 4165 */         System.arraycopy(A[i], 0, B[idx], 0, (A[0]).length);
/* 4166 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 4170 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] RemoveRow(float[][] A, int index) {
/* 4180 */     if (A.length - 1 <= 0) {
/* 4181 */       throw new IllegalArgumentException("The number of rows is less or equal zero.");
/*      */     }
/* 4183 */     float[][] B = new float[A.length - 1][(A[0]).length];
/* 4184 */     int idx = 0;
/* 4185 */     for (int i = 0; i < A.length; i++) {
/* 4186 */       if (i != index) {
/* 4187 */         System.arraycopy(A[i], 0, B[idx], 0, (A[0]).length);
/* 4188 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 4192 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T[][] RemoveRow(Object[][] A, int index) {
/* 4202 */     if (A.length - 1 <= 0) {
/* 4203 */       throw new IllegalArgumentException("The number of rows is less or equal zero.");
/*      */     }
/* 4205 */     Object[][] B = (Object[][])Array.newInstance(A[0][0].getClass(), new int[] { A.length - 1, (A[0]).length });
/* 4206 */     int idx = 0;
/* 4207 */     for (int i = 0; i < A.length; i++) {
/* 4208 */       if (i != index) {
/* 4209 */         System.arraycopy(A[i], 0, B[idx], 0, (A[0]).length);
/* 4210 */         idx++;
/*      */       } 
/*      */     } 
/*      */     
/* 4214 */     return (T[][])B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] RemoveRows(int[][] A, int[] index) {
/* 4224 */     if (A.length - index.length <= 0) {
/* 4225 */       throw new IllegalArgumentException("The number of rows is less or equal zero.");
/*      */     }
/* 4227 */     int[][] B = new int[A.length - index.length][(A[0]).length];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4232 */     int[] copy = Arrays.copyOf(index, index.length);
/* 4233 */     Arrays.sort(copy);
/*      */     
/* 4235 */     int idx = copy[0];
/* 4236 */     int c = 0, p = c;
/* 4237 */     for (int i = 0; i < A.length; i++) {
/* 4238 */       if (i == idx) {
/* 4239 */         if (p < index.length - 1) {
/* 4240 */           idx = copy[++p];
/*      */         }
/*      */       } else {
/* 4243 */         for (int j = 0; j < (A[0]).length; j++) {
/* 4244 */           B[c][j] = A[i][j];
/*      */         }
/* 4246 */         c++;
/*      */       } 
/*      */     } 
/* 4249 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] RemoveRows(double[][] A, int[] index) {
/* 4260 */     if (A.length - index.length <= 0) {
/* 4261 */       throw new IllegalArgumentException("The number of rows is less or equal zero.");
/*      */     }
/* 4263 */     int[] copy = Arrays.copyOf(index, index.length);
/* 4264 */     Arrays.sort(copy);
/*      */     
/* 4266 */     double[][] B = new double[A.length - index.length][(A[0]).length];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4271 */     int idx = copy[0];
/* 4272 */     int c = 0, p = c;
/* 4273 */     for (int i = 0; i < A.length; i++) {
/* 4274 */       if (i == idx) {
/* 4275 */         if (p < index.length - 1) {
/* 4276 */           idx = copy[++p];
/*      */         }
/*      */       } else {
/* 4279 */         for (int j = 0; j < (A[0]).length; j++) {
/* 4280 */           B[c][j] = A[i][j];
/*      */         }
/* 4282 */         c++;
/*      */       } 
/*      */     } 
/* 4285 */     return B;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] RemoveRows(float[][] A, int[] index) {
/* 4295 */     if (A.length - index.length <= 0) {
/* 4296 */       throw new IllegalArgumentException("The number of rows is less or equal zero.");
/*      */     }
/* 4298 */     int[] copy = Arrays.copyOf(index, index.length);
/* 4299 */     Arrays.sort(copy);
/*      */     
/* 4301 */     float[][] B = new float[A.length - index.length][(A[0]).length];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4306 */     int idx = copy[0];
/* 4307 */     int c = 0, p = c;
/* 4308 */     for (int i = 0; i < A.length; i++) {
/* 4309 */       if (i == idx) {
/* 4310 */         if (p < index.length - 1) {
/* 4311 */           idx = copy[++p];
/*      */         }
/*      */       } else {
/* 4314 */         for (int j = 0; j < (A[0]).length; j++) {
/* 4315 */           B[c][j] = A[i][j];
/*      */         }
/* 4317 */         c++;
/*      */       } 
/*      */     } 
/* 4320 */     return B;
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
/*      */   public static double[][] Reshape(double[] vector, int m, int n) {
/* 4332 */     if (vector.length != m * n) {
/* 4333 */       throw new IllegalArgumentException("The size of vector must be the same of product of m and n.");
/*      */     }
/* 4335 */     int x = 0;
/* 4336 */     double[][] result = new double[m][n];
/* 4337 */     for (int i = 0; i < m; i++) {
/* 4338 */       for (int j = 0; j < n; j++) {
/* 4339 */         result[i][j] = vector[x++];
/*      */       }
/*      */     } 
/*      */     
/* 4343 */     return result;
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
/*      */   public static int[][] Reshape(int[] vector, int m, int n) {
/* 4356 */     if (vector.length != m * n) {
/* 4357 */       throw new IllegalArgumentException("The size of vector must be the same of product of m and n.");
/*      */     }
/* 4359 */     int x = 0;
/* 4360 */     int[][] result = new int[m][n];
/* 4361 */     for (int i = 0; i < m; i++) {
/* 4362 */       for (int j = 0; j < n; j++) {
/* 4363 */         result[i][j] = vector[x++];
/*      */       }
/*      */     } 
/*      */     
/* 4367 */     return result;
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
/*      */   public static float[][] Reshape(float[] vector, int m, int n) {
/* 4380 */     if (vector.length != m * n) {
/* 4381 */       throw new IllegalArgumentException("The size of vector must be the same of product of m and n.");
/*      */     }
/* 4383 */     int x = 0;
/* 4384 */     float[][] result = new float[m][n];
/* 4385 */     for (int i = 0; i < m; i++) {
/* 4386 */       for (int j = 0; j < n; j++) {
/* 4387 */         result[i][j] = vector[x++];
/*      */       }
/*      */     } 
/*      */     
/* 4391 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] Reshape(double[][] A) {
/* 4402 */     double[] vector = new double[A.length * (A[0]).length];
/* 4403 */     int x = 0;
/* 4404 */     for (int i = 0; i < A.length; i++) {
/* 4405 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4406 */         vector[x++] = A[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 4410 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] Reshape(int[][] A) {
/* 4420 */     int[] vector = new int[A.length * (A[0]).length];
/* 4421 */     int x = 0;
/* 4422 */     for (int i = 0; i < A.length; i++) {
/* 4423 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4424 */         vector[x++] = A[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 4428 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] Reshape(float[][] A) {
/* 4438 */     float[] vector = new float[A.length * (A[0]).length];
/* 4439 */     int x = 0;
/* 4440 */     for (int i = 0; i < A.length; i++) {
/* 4441 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4442 */         vector[x++] = A[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 4446 */     return vector;
/*      */   }
/*      */   
/*      */   public static double[][] SubMatrix(double[][] data, int rows, int cols) {
/* 4450 */     double[][] m = new double[rows][cols];
/* 4451 */     for (int i = 0; i < rows; i++) {
/* 4452 */       for (int j = 0; j < cols; j++) {
/* 4453 */         m[i][j] = data[i][j];
/*      */       }
/*      */     } 
/* 4456 */     return m;
/*      */   }
/*      */   
/*      */   public static int[] SubMatrix(int[] data, int first) {
/* 4460 */     if (first < 0 || first > data.length) {
/* 4461 */       throw new IllegalArgumentException("first");
/*      */     }
/* 4463 */     if (first == 0) {
/* 4464 */       return data;
/*      */     }
/* 4466 */     return Submatrix(data, 0, first - 1);
/*      */   }
/*      */   
/*      */   public static double[] SubMatrix(double[] data, int first) {
/* 4470 */     if (first < 0 || first > data.length) {
/* 4471 */       throw new IllegalArgumentException("first");
/*      */     }
/* 4473 */     if (first == 0) {
/* 4474 */       return data;
/*      */     }
/* 4476 */     return Submatrix(data, 0, first - 1);
/*      */   }
/*      */   
/*      */   public static int[] Submatrix(int[] data, int startRow, int endRow) {
/* 4480 */     if (startRow < 0)
/* 4481 */       throw new IllegalArgumentException("startRow"); 
/* 4482 */     if (endRow >= data.length) {
/* 4483 */       throw new IllegalArgumentException("endRow");
/*      */     }
/* 4485 */     int[] X = new int[endRow - startRow + 1];
/*      */     
/* 4487 */     for (int i = startRow; i <= endRow; i++) {
/* 4488 */       X[i - startRow] = data[i];
/*      */     }
/* 4490 */     return X;
/*      */   }
/*      */   
/*      */   public static double[] Submatrix(double[] data, int startRow, int endRow) {
/* 4494 */     if (startRow < 0)
/* 4495 */       throw new IllegalArgumentException("startRow"); 
/* 4496 */     if (endRow >= data.length) {
/* 4497 */       throw new IllegalArgumentException("endRow");
/*      */     }
/* 4499 */     double[] X = new double[endRow - startRow + 1];
/*      */     
/* 4501 */     for (int i = startRow; i <= endRow; i++) {
/* 4502 */       X[i - startRow] = data[i];
/*      */     }
/* 4504 */     return X;
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
/*      */   public static double[][] Submatrix(double[][] data, int startRow, int endRow, int startColumn, int endColumn) {
/* 4517 */     if (startRow > endRow || startRow < 0 || startRow >= data.length || 
/* 4518 */       endRow < 0 || endRow >= data.length) {
/* 4519 */       throw new IllegalArgumentException("Argument out of range.");
/*      */     }
/*      */     
/* 4522 */     double[][] X = new double[endRow - startRow + 1][endColumn - startColumn + 1];
/*      */     
/* 4524 */     for (int i = startRow; i <= endRow; i++) {
/* 4525 */       for (int j = startColumn; j <= endColumn; j++)
/*      */       {
/* 4527 */         X[i - startRow][j - startColumn] = data[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 4531 */     return X;
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
/*      */   public static int[][] Submatrix(int[][] data, int startRow, int endRow, int startColumn, int endColumn) {
/* 4544 */     if (startRow > endRow || startRow < 0 || startRow >= data.length || 
/* 4545 */       endRow < 0 || endRow >= data.length) {
/* 4546 */       throw new IllegalArgumentException("Argument out of range.");
/*      */     }
/*      */     
/* 4549 */     int[][] X = new int[endRow - startRow + 1][endColumn - startColumn + 1];
/*      */     
/* 4551 */     for (int i = startRow; i <= endRow; i++) {
/* 4552 */       for (int j = startColumn; j <= endColumn; j++)
/*      */       {
/* 4554 */         X[i - startRow][j - startColumn] = data[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 4558 */     return X;
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
/*      */   public static float[][] Submatrix(float[][] data, int startRow, int endRow, int startColumn, int endColumn) {
/* 4571 */     if (startRow > endRow || startRow < 0 || startRow >= data.length || 
/* 4572 */       endRow < 0 || endRow >= data.length) {
/* 4573 */       throw new IllegalArgumentException("Argument out of range.");
/*      */     }
/*      */     
/* 4576 */     float[][] X = new float[endRow - startRow + 1][endColumn - startColumn + 1];
/*      */     
/* 4578 */     for (int i = startRow; i <= endRow; i++) {
/* 4579 */       for (int j = startColumn; j <= endColumn; j++)
/*      */       {
/* 4581 */         X[i - startRow][j - startColumn] = data[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 4585 */     return X;
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
/*      */   public static double[][] Submatrix(double[][] data, int startRow, int endRow, int[] columnIndexes) {
/* 4597 */     if (startRow > endRow || startRow < 0 || startRow >= data.length || 
/* 4598 */       endRow < 0 || endRow >= data.length) {
/* 4599 */       throw new IllegalArgumentException("Argument out of range.");
/*      */     }
/*      */     
/* 4602 */     if (columnIndexes == null) {
/* 4603 */       columnIndexes = Indices(0, (data[0]).length);
/*      */     }
/* 4605 */     double[][] X = new double[endRow - startRow + 1][columnIndexes.length];
/*      */     
/* 4607 */     for (int i = startRow; i <= endRow; i++) {
/* 4608 */       for (int j = 0; j < columnIndexes.length; j++) {
/* 4609 */         if (columnIndexes[j] < 0 || columnIndexes[j] >= (data[0]).length) {
/* 4610 */           throw new IllegalArgumentException("Argument out of range.");
/*      */         }
/*      */         
/* 4613 */         X[i - startRow][j] = data[i][columnIndexes[j]];
/*      */       } 
/*      */     } 
/*      */     
/* 4617 */     return X;
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
/*      */   public static int[][] Submatrix(int[][] data, int startRow, int endRow, int[] columnIndexes) {
/* 4629 */     if (startRow > endRow || startRow < 0 || startRow >= data.length || 
/* 4630 */       endRow < 0 || endRow >= data.length) {
/* 4631 */       throw new IllegalArgumentException("Argument out of range.");
/*      */     }
/*      */     
/* 4634 */     if (columnIndexes == null) {
/* 4635 */       columnIndexes = Indices(0, (data[0]).length);
/*      */     }
/* 4637 */     int[][] X = new int[endRow - startRow + 1][columnIndexes.length];
/*      */     
/* 4639 */     for (int i = startRow; i <= endRow; i++) {
/* 4640 */       for (int j = 0; j < columnIndexes.length; j++) {
/* 4641 */         if (columnIndexes[j] < 0 || columnIndexes[j] >= (data[0]).length) {
/* 4642 */           throw new IllegalArgumentException("Argument out of range.");
/*      */         }
/*      */         
/* 4645 */         X[i - startRow][j] = data[i][columnIndexes[j]];
/*      */       } 
/*      */     } 
/*      */     
/* 4649 */     return X;
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
/*      */   public static float[][] Submatrix(float[][] data, int startRow, int endRow, int[] columnIndexes) {
/* 4661 */     if (startRow > endRow || startRow < 0 || startRow >= data.length || 
/* 4662 */       endRow < 0 || endRow >= data.length) {
/* 4663 */       throw new IllegalArgumentException("Argument out of range.");
/*      */     }
/*      */     
/* 4666 */     if (columnIndexes == null) {
/* 4667 */       columnIndexes = Indices(0, (data[0]).length);
/*      */     }
/* 4669 */     float[][] X = new float[endRow - startRow + 1][columnIndexes.length];
/*      */     
/* 4671 */     for (int i = startRow; i <= endRow; i++) {
/* 4672 */       for (int j = 0; j < columnIndexes.length; j++) {
/* 4673 */         if (columnIndexes[j] < 0 || columnIndexes[j] >= (data[0]).length) {
/* 4674 */           throw new IllegalArgumentException("Argument out of range.");
/*      */         }
/*      */         
/* 4677 */         X[i - startRow][j] = data[i][columnIndexes[j]];
/*      */       } 
/*      */     } 
/*      */     
/* 4681 */     return X;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] Submatrix(int[][] data, int[] rowIndexes) {
/* 4691 */     int[][] X = new int[rowIndexes.length][(data[0]).length];
/*      */     
/* 4693 */     for (int i = 0; i < rowIndexes.length; i++) {
/*      */       
/* 4695 */       for (int j = 0; j < (data[0]).length; j++) {
/*      */         
/* 4697 */         if (rowIndexes[i] < 0 || rowIndexes[i] >= data.length) {
/* 4698 */           throw new IllegalArgumentException("Argument out of range.");
/*      */         }
/* 4700 */         X[i][j] = data[rowIndexes[i]][j];
/*      */       } 
/*      */     } 
/*      */     
/* 4704 */     return X;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] Submatrix(double[][] data, int[] rowIndexes) {
/* 4714 */     double[][] X = new double[rowIndexes.length][(data[0]).length];
/*      */     
/* 4716 */     for (int i = 0; i < rowIndexes.length; i++) {
/*      */       
/* 4718 */       for (int j = 0; j < (data[0]).length; j++) {
/*      */         
/* 4720 */         if (rowIndexes[i] < 0 || rowIndexes[i] >= data.length) {
/* 4721 */           throw new IllegalArgumentException("Argument out of range.");
/*      */         }
/* 4723 */         X[i][j] = data[rowIndexes[i]][j];
/*      */       } 
/*      */     } 
/*      */     
/* 4727 */     return X;
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
/*      */   public static int[][] Submatrix(int[][] data, int[] rowIndexes, int startColumn, int endColumn) {
/* 4739 */     int[][] X = new int[rowIndexes.length][endColumn - startColumn + 1];
/*      */     
/* 4741 */     for (int i = 0; i < X.length; i++) {
/*      */       
/* 4743 */       for (int j = 0; j < (X[0]).length; j++) {
/*      */         
/* 4745 */         if (rowIndexes[i] < 0 || rowIndexes[i] >= data.length) {
/* 4746 */           throw new IllegalArgumentException("Argument out of range.");
/*      */         }
/* 4748 */         X[i][j] = data[rowIndexes[i]][startColumn + j];
/*      */       } 
/*      */     } 
/*      */     
/* 4752 */     return X;
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
/*      */   public static double[][] Submatrix(double[][] data, int[] rowIndexes, int startColumn, int endColumn) {
/* 4764 */     double[][] X = new double[rowIndexes.length][endColumn - startColumn + 1];
/*      */     
/* 4766 */     for (int i = 0; i < X.length; i++) {
/*      */       
/* 4768 */       for (int j = 0; j < (X[0]).length; j++) {
/*      */         
/* 4770 */         if (rowIndexes[i] < 0 || rowIndexes[i] >= data.length) {
/* 4771 */           throw new IllegalArgumentException("Argument out of range.");
/*      */         }
/* 4773 */         X[i][j] = data[rowIndexes[i]][startColumn + j];
/*      */       } 
/*      */     } 
/*      */     
/* 4777 */     return X;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] toDoubleArray(int[][] A) {
/* 4786 */     double[] m = new double[A.length * (A[0]).length];
/*      */     
/* 4788 */     int index = 0;
/* 4789 */     for (int i = 0; i < A.length; i++) {
/* 4790 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4791 */         m[index] = A[i][j];
/* 4792 */         index++;
/*      */       } 
/*      */     } 
/* 4795 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] toDoubleArray(double[][] A) {
/* 4804 */     double[] m = new double[A.length * (A[0]).length];
/*      */     
/* 4806 */     int index = 0;
/* 4807 */     for (int i = 0; i < A.length; i++) {
/* 4808 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4809 */         m[index] = A[i][j];
/* 4810 */         index++;
/*      */       } 
/*      */     } 
/* 4813 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] toDoubleArray(float[][] A) {
/* 4822 */     double[] m = new double[A.length * (A[0]).length];
/*      */     
/* 4824 */     int index = 0;
/* 4825 */     for (int i = 0; i < A.length; i++) {
/* 4826 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4827 */         m[index] = A[i][j];
/* 4828 */         index++;
/*      */       } 
/*      */     } 
/* 4831 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] toDoubleMatrix(List<double[]> list) {
/* 4840 */     double[][] m = new double[list.size()][((double[])list.get(0)).length];
/* 4841 */     for (int i = 0; i < m.length; i++) {
/* 4842 */       m[i] = list.get(i);
/*      */     }
/*      */     
/* 4845 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] toIntArray(int[][] A) {
/* 4854 */     int[] m = new int[A.length * (A[0]).length];
/*      */     
/* 4856 */     int index = 0;
/* 4857 */     for (int i = 0; i < A.length; i++) {
/* 4858 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4859 */         m[index] = A[i][j];
/* 4860 */         index++;
/*      */       } 
/*      */     } 
/* 4863 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] toIntArray(double[][] A) {
/* 4872 */     int[] m = new int[A.length * (A[0]).length];
/*      */     
/* 4874 */     int index = 0;
/* 4875 */     for (int i = 0; i < A.length; i++) {
/* 4876 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4877 */         m[index] = (int)A[i][j];
/* 4878 */         index++;
/*      */       } 
/*      */     } 
/* 4881 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] toIntArray(float[][] A) {
/* 4890 */     int[] m = new int[A.length * (A[0]).length];
/*      */     
/* 4892 */     int index = 0;
/* 4893 */     for (int i = 0; i < A.length; i++) {
/* 4894 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4895 */         m[index] = (int)A[i][j];
/* 4896 */         index++;
/*      */       } 
/*      */     } 
/* 4899 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] toIntMatrix(List<int[]> list) {
/* 4908 */     int[][] m = new int[list.size()][((int[])list.get(0)).length];
/* 4909 */     for (int i = 0; i < m.length; i++) {
/* 4910 */       m[i] = list.get(i);
/*      */     }
/*      */     
/* 4913 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] toFloatArray(int[][] A) {
/* 4922 */     float[] m = new float[A.length * (A[0]).length];
/*      */     
/* 4924 */     int index = 0;
/* 4925 */     for (int i = 0; i < A.length; i++) {
/* 4926 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4927 */         m[index] = A[i][j];
/* 4928 */         index++;
/*      */       } 
/*      */     } 
/* 4931 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] toFloatArray(double[][] A) {
/* 4940 */     float[] m = new float[A.length * (A[0]).length];
/*      */     
/* 4942 */     int index = 0;
/* 4943 */     for (int i = 0; i < A.length; i++) {
/* 4944 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4945 */         m[index] = (float)A[i][j];
/* 4946 */         index++;
/*      */       } 
/*      */     } 
/* 4949 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] toFloatArray(float[][] A) {
/* 4958 */     float[] m = new float[A.length * (A[0]).length];
/*      */     
/* 4960 */     int index = 0;
/* 4961 */     for (int i = 0; i < A.length; i++) {
/* 4962 */       for (int j = 0; j < (A[0]).length; j++) {
/* 4963 */         m[index] = A[i][j];
/* 4964 */         index++;
/*      */       } 
/*      */     } 
/* 4967 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] toFloatMatrix(List<float[]> list) {
/* 4976 */     float[][] m = new float[list.size()][((float[])list.get(0)).length];
/* 4977 */     for (int i = 0; i < m.length; i++) {
/* 4978 */       m[i] = list.get(i);
/*      */     }
/*      */     
/* 4981 */     return m;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean[][] CreateMatrix2D(int height, int width, boolean val) {
/* 4986 */     boolean[][] v = new boolean[height][width];
/*      */     
/* 4988 */     for (int i = 0; i < height; i++) {
/* 4989 */       for (int j = 0; j < width; j++) {
/* 4990 */         v[i][j] = val;
/*      */       }
/*      */     } 
/*      */     
/* 4994 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] getRows(double[][] traceIn, int start, int end) {
/* 5000 */     double[][] traceOut = new double[end - start + 1][(traceIn[0]).length];
/*      */     
/* 5002 */     for (int i = start; i < end + 1; i++) {
/* 5003 */       for (int j = 0; j < (traceIn[0]).length; j++) {
/* 5004 */         traceOut[i - start][j] = traceIn[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 5008 */     return traceOut;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Matrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */