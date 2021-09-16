/*      */ package org.apache.commons.math3.analysis.differentiation;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.MathInternalError;
/*      */ import org.apache.commons.math3.exception.NotPositiveException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*      */ import org.apache.commons.math3.util.CombinatoricsUtils;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathArrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DSCompiler
/*      */ {
/*  128 */   private static AtomicReference<DSCompiler[][]> compilers = (AtomicReference)new AtomicReference<DSCompiler>(null);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int parameters;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int order;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[][] sizes;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[][] derivativesIndirection;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[] lowerIndirection;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[][][] multIndirection;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[][][] compIndirection;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DSCompiler(int parameters, int order, DSCompiler valueCompiler, DSCompiler derivativeCompiler) throws NumberIsTooLargeException {
/*  163 */     this.parameters = parameters;
/*  164 */     this.order = order;
/*  165 */     this.sizes = compileSizes(parameters, order, valueCompiler);
/*  166 */     this.derivativesIndirection = compileDerivativesIndirection(parameters, order, valueCompiler, derivativeCompiler);
/*      */ 
/*      */     
/*  169 */     this.lowerIndirection = compileLowerIndirection(parameters, order, valueCompiler, derivativeCompiler);
/*      */ 
/*      */     
/*  172 */     this.multIndirection = compileMultiplicationIndirection(parameters, order, valueCompiler, derivativeCompiler, this.lowerIndirection);
/*      */ 
/*      */     
/*  175 */     this.compIndirection = compileCompositionIndirection(parameters, order, valueCompiler, derivativeCompiler, this.sizes, this.derivativesIndirection);
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
/*      */   public static DSCompiler getCompiler(int parameters, int order) throws NumberIsTooLargeException {
/*  192 */     DSCompiler[][] cache = compilers.get();
/*  193 */     if (cache != null && cache.length > parameters && (cache[parameters]).length > order && cache[parameters][order] != null)
/*      */     {
/*      */       
/*  196 */       return cache[parameters][order];
/*      */     }
/*      */ 
/*      */     
/*  200 */     int maxParameters = FastMath.max(parameters, (cache == null) ? 0 : cache.length);
/*  201 */     int maxOrder = FastMath.max(order, (cache == null) ? 0 : (cache[0]).length);
/*  202 */     DSCompiler[][] newCache = new DSCompiler[maxParameters + 1][maxOrder + 1];
/*      */     
/*  204 */     if (cache != null)
/*      */     {
/*  206 */       for (int i = 0; i < cache.length; i++) {
/*  207 */         System.arraycopy(cache[i], 0, newCache[i], 0, (cache[i]).length);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*  212 */     for (int diag = 0; diag <= parameters + order; diag++) {
/*  213 */       for (int o = FastMath.max(0, diag - parameters); o <= FastMath.min(order, diag); o++) {
/*  214 */         int p = diag - o;
/*  215 */         if (newCache[p][o] == null) {
/*  216 */           DSCompiler valueCompiler = (p == 0) ? null : newCache[p - 1][o];
/*  217 */           DSCompiler derivativeCompiler = (o == 0) ? null : newCache[p][o - 1];
/*  218 */           newCache[p][o] = new DSCompiler(p, o, valueCompiler, derivativeCompiler);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  224 */     compilers.compareAndSet(cache, newCache);
/*      */     
/*  226 */     return newCache[parameters][order];
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
/*      */   private static int[][] compileSizes(int parameters, int order, DSCompiler valueCompiler) {
/*  239 */     int[][] sizes = new int[parameters + 1][order + 1];
/*  240 */     if (parameters == 0) {
/*  241 */       Arrays.fill(sizes[0], 1);
/*      */     } else {
/*  243 */       System.arraycopy(valueCompiler.sizes, 0, sizes, 0, parameters);
/*  244 */       sizes[parameters][0] = 1;
/*  245 */       for (int i = 0; i < order; i++) {
/*  246 */         sizes[parameters][i + 1] = sizes[parameters][i] + sizes[parameters - 1][i + 1];
/*      */       }
/*      */     } 
/*      */     
/*  250 */     return sizes;
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
/*      */   private static int[][] compileDerivativesIndirection(int parameters, int order, DSCompiler valueCompiler, DSCompiler derivativeCompiler) {
/*  265 */     if (parameters == 0 || order == 0) {
/*  266 */       return new int[1][parameters];
/*      */     }
/*      */     
/*  269 */     int vSize = valueCompiler.derivativesIndirection.length;
/*  270 */     int dSize = derivativeCompiler.derivativesIndirection.length;
/*  271 */     int[][] derivativesIndirection = new int[vSize + dSize][parameters];
/*      */     
/*      */     int i;
/*  274 */     for (i = 0; i < vSize; i++)
/*      */     {
/*  276 */       System.arraycopy(valueCompiler.derivativesIndirection[i], 0, derivativesIndirection[i], 0, parameters - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  282 */     for (i = 0; i < dSize; i++) {
/*      */ 
/*      */       
/*  285 */       System.arraycopy(derivativeCompiler.derivativesIndirection[i], 0, derivativesIndirection[vSize + i], 0, parameters);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  290 */       derivativesIndirection[vSize + i][parameters - 1] = derivativesIndirection[vSize + i][parameters - 1] + 1;
/*      */     } 
/*      */ 
/*      */     
/*  294 */     return derivativesIndirection;
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
/*      */   private static int[] compileLowerIndirection(int parameters, int order, DSCompiler valueCompiler, DSCompiler derivativeCompiler) {
/*  313 */     if (parameters == 0 || order <= 1) {
/*  314 */       return new int[] { 0 };
/*      */     }
/*      */ 
/*      */     
/*  318 */     int vSize = valueCompiler.lowerIndirection.length;
/*  319 */     int dSize = derivativeCompiler.lowerIndirection.length;
/*  320 */     int[] lowerIndirection = new int[vSize + dSize];
/*  321 */     System.arraycopy(valueCompiler.lowerIndirection, 0, lowerIndirection, 0, vSize);
/*  322 */     for (int i = 0; i < dSize; i++) {
/*  323 */       lowerIndirection[vSize + i] = valueCompiler.getSize() + derivativeCompiler.lowerIndirection[i];
/*      */     }
/*      */     
/*  326 */     return lowerIndirection;
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
/*      */   private static int[][][] compileMultiplicationIndirection(int parameters, int order, DSCompiler valueCompiler, DSCompiler derivativeCompiler, int[] lowerIndirection) {
/*  348 */     if (parameters == 0 || order == 0) {
/*  349 */       return new int[][][] { { { 1, 0, 0 } } };
/*      */     }
/*      */ 
/*      */     
/*  353 */     int vSize = valueCompiler.multIndirection.length;
/*  354 */     int dSize = derivativeCompiler.multIndirection.length;
/*  355 */     int[][][] multIndirection = new int[vSize + dSize][][];
/*      */     
/*  357 */     System.arraycopy(valueCompiler.multIndirection, 0, multIndirection, 0, vSize);
/*      */     
/*  359 */     for (int i = 0; i < dSize; i++) {
/*  360 */       int[][] dRow = derivativeCompiler.multIndirection[i];
/*  361 */       List<int[]> row = (List)new ArrayList<int>(dRow.length * 2);
/*  362 */       for (int j = 0; j < dRow.length; j++) {
/*  363 */         row.add(new int[] { dRow[j][0], lowerIndirection[dRow[j][1]], vSize + dRow[j][2] });
/*  364 */         row.add(new int[] { dRow[j][0], vSize + dRow[j][1], lowerIndirection[dRow[j][2]] });
/*      */       } 
/*      */ 
/*      */       
/*  368 */       List<int[]> combined = (List)new ArrayList<int>(row.size());
/*  369 */       for (int k = 0; k < row.size(); k++) {
/*  370 */         int[] termJ = row.get(k);
/*  371 */         if (termJ[0] > 0) {
/*  372 */           for (int m = k + 1; m < row.size(); m++) {
/*  373 */             int[] termK = row.get(m);
/*  374 */             if (termJ[1] == termK[1] && termJ[2] == termK[2]) {
/*      */               
/*  376 */               termJ[0] = termJ[0] + termK[0];
/*      */               
/*  378 */               termK[0] = 0;
/*      */             } 
/*      */           } 
/*  381 */           combined.add(termJ);
/*      */         } 
/*      */       } 
/*      */       
/*  385 */       multIndirection[vSize + i] = combined.<int[]>toArray(new int[combined.size()][]);
/*      */     } 
/*      */ 
/*      */     
/*  389 */     return multIndirection;
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
/*      */   private static int[][][] compileCompositionIndirection(int parameters, int order, DSCompiler valueCompiler, DSCompiler derivativeCompiler, int[][] sizes, int[][] derivativesIndirection) throws NumberIsTooLargeException {
/*  415 */     if (parameters == 0 || order == 0) {
/*  416 */       return new int[][][] { { { 1, 0 } } };
/*      */     }
/*      */     
/*  419 */     int vSize = valueCompiler.compIndirection.length;
/*  420 */     int dSize = derivativeCompiler.compIndirection.length;
/*  421 */     int[][][] compIndirection = new int[vSize + dSize][][];
/*      */ 
/*      */     
/*  424 */     System.arraycopy(valueCompiler.compIndirection, 0, compIndirection, 0, vSize);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  430 */     for (int i = 0; i < dSize; i++) {
/*  431 */       List<int[]> row = (List)new ArrayList<int>();
/*  432 */       for (int[] term : derivativeCompiler.compIndirection[i]) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  437 */         int[] derivedTermF = new int[term.length + 1];
/*  438 */         derivedTermF[0] = term[0];
/*  439 */         derivedTermF[1] = term[1] + 1;
/*  440 */         int[] orders = new int[parameters];
/*  441 */         orders[parameters - 1] = 1;
/*  442 */         derivedTermF[term.length] = getPartialDerivativeIndex(parameters, order, sizes, orders);
/*  443 */         for (int k = 2; k < term.length; k++)
/*      */         {
/*      */           
/*  446 */           derivedTermF[k] = convertIndex(term[k], parameters, derivativeCompiler.derivativesIndirection, parameters, order, sizes);
/*      */         }
/*      */ 
/*      */         
/*  450 */         Arrays.sort(derivedTermF, 2, derivedTermF.length);
/*  451 */         row.add(derivedTermF);
/*      */ 
/*      */         
/*  454 */         for (int l = 2; l < term.length; l++) {
/*  455 */           int[] derivedTermG = new int[term.length];
/*  456 */           derivedTermG[0] = term[0];
/*  457 */           derivedTermG[1] = term[1];
/*  458 */           for (int m = 2; m < term.length; m++) {
/*      */ 
/*      */             
/*  461 */             derivedTermG[m] = convertIndex(term[m], parameters, derivativeCompiler.derivativesIndirection, parameters, order, sizes);
/*      */ 
/*      */             
/*  464 */             if (m == l) {
/*      */               
/*  466 */               System.arraycopy(derivativesIndirection[derivedTermG[m]], 0, orders, 0, parameters);
/*  467 */               orders[parameters - 1] = orders[parameters - 1] + 1;
/*  468 */               derivedTermG[m] = getPartialDerivativeIndex(parameters, order, sizes, orders);
/*      */             } 
/*      */           } 
/*  471 */           Arrays.sort(derivedTermG, 2, derivedTermG.length);
/*  472 */           row.add(derivedTermG);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  478 */       List<int[]> combined = (List)new ArrayList<int>(row.size());
/*  479 */       for (int j = 0; j < row.size(); j++) {
/*  480 */         int[] termJ = row.get(j);
/*  481 */         if (termJ[0] > 0) {
/*  482 */           for (int k = j + 1; k < row.size(); k++) {
/*  483 */             int m, termK[] = row.get(k);
/*  484 */             boolean equals = (termJ.length == termK.length);
/*  485 */             for (int l = 1; equals && l < termJ.length; l++) {
/*  486 */               m = equals & ((termJ[l] == termK[l]) ? 1 : 0);
/*      */             }
/*  488 */             if (m != 0) {
/*      */               
/*  490 */               termJ[0] = termJ[0] + termK[0];
/*      */               
/*  492 */               termK[0] = 0;
/*      */             } 
/*      */           } 
/*  495 */           combined.add(termJ);
/*      */         } 
/*      */       } 
/*      */       
/*  499 */       compIndirection[vSize + i] = combined.<int[]>toArray(new int[combined.size()][]);
/*      */     } 
/*      */ 
/*      */     
/*  503 */     return compIndirection;
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
/*      */   public int getPartialDerivativeIndex(int... orders) throws DimensionMismatchException, NumberIsTooLargeException {
/*  543 */     if (orders.length != getFreeParameters()) {
/*  544 */       throw new DimensionMismatchException(orders.length, getFreeParameters());
/*      */     }
/*      */     
/*  547 */     return getPartialDerivativeIndex(this.parameters, this.order, this.sizes, orders);
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
/*      */   private static int getPartialDerivativeIndex(int parameters, int order, int[][] sizes, int... orders) throws NumberIsTooLargeException {
/*  567 */     int index = 0;
/*  568 */     int m = order;
/*  569 */     int ordersSum = 0;
/*  570 */     for (int i = parameters - 1; i >= 0; i--) {
/*      */ 
/*      */       
/*  573 */       int derivativeOrder = orders[i];
/*      */ 
/*      */       
/*  576 */       ordersSum += derivativeOrder;
/*  577 */       if (ordersSum > order) {
/*  578 */         throw new NumberIsTooLargeException(Integer.valueOf(ordersSum), Integer.valueOf(order), true);
/*      */       }
/*      */       
/*  581 */       while (derivativeOrder-- > 0)
/*      */       {
/*      */ 
/*      */         
/*  585 */         index += sizes[i][m--];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  590 */     return index;
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
/*      */   private static int convertIndex(int index, int srcP, int[][] srcDerivativesIndirection, int destP, int destO, int[][] destSizes) throws NumberIsTooLargeException {
/*  610 */     int[] orders = new int[destP];
/*  611 */     System.arraycopy(srcDerivativesIndirection[index], 0, orders, 0, FastMath.min(srcP, destP));
/*  612 */     return getPartialDerivativeIndex(destP, destO, destSizes, orders);
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
/*      */   public int[] getPartialDerivativeOrders(int index) {
/*  624 */     return this.derivativesIndirection[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFreeParameters() {
/*  631 */     return this.parameters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrder() {
/*  638 */     return this.order;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSize() {
/*  649 */     return this.sizes[this.parameters][this.order];
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
/*      */   public void linearCombination(double a1, double[] c1, int offset1, double a2, double[] c2, int offset2, double[] result, int resultOffset) {
/*  667 */     for (int i = 0; i < getSize(); i++) {
/*  668 */       result[resultOffset + i] = MathArrays.linearCombination(a1, c1[offset1 + i], a2, c2[offset2 + i]);
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
/*      */   public void linearCombination(double a1, double[] c1, int offset1, double a2, double[] c2, int offset2, double a3, double[] c3, int offset3, double[] result, int resultOffset) {
/*  692 */     for (int i = 0; i < getSize(); i++) {
/*  693 */       result[resultOffset + i] = MathArrays.linearCombination(a1, c1[offset1 + i], a2, c2[offset2 + i], a3, c3[offset3 + i]);
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
/*      */   public void linearCombination(double a1, double[] c1, int offset1, double a2, double[] c2, int offset2, double a3, double[] c3, int offset3, double a4, double[] c4, int offset4, double[] result, int resultOffset) {
/*  723 */     for (int i = 0; i < getSize(); i++) {
/*  724 */       result[resultOffset + i] = MathArrays.linearCombination(a1, c1[offset1 + i], a2, c2[offset2 + i], a3, c3[offset3 + i], a4, c4[offset4 + i]);
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
/*      */   public void add(double[] lhs, int lhsOffset, double[] rhs, int rhsOffset, double[] result, int resultOffset) {
/*  744 */     for (int i = 0; i < getSize(); i++) {
/*  745 */       result[resultOffset + i] = lhs[lhsOffset + i] + rhs[rhsOffset + i];
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
/*      */   public void subtract(double[] lhs, int lhsOffset, double[] rhs, int rhsOffset, double[] result, int resultOffset) {
/*  760 */     for (int i = 0; i < getSize(); i++) {
/*  761 */       result[resultOffset + i] = lhs[lhsOffset + i] - rhs[rhsOffset + i];
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
/*      */   public void multiply(double[] lhs, int lhsOffset, double[] rhs, int rhsOffset, double[] result, int resultOffset) {
/*  778 */     for (int i = 0; i < this.multIndirection.length; i++) {
/*  779 */       int[][] mappingI = this.multIndirection[i];
/*  780 */       double r = 0.0D;
/*  781 */       for (int j = 0; j < mappingI.length; j++) {
/*  782 */         r += mappingI[j][0] * lhs[lhsOffset + mappingI[j][1]] * rhs[rhsOffset + mappingI[j][2]];
/*      */       }
/*      */ 
/*      */       
/*  786 */       result[resultOffset + i] = r;
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
/*      */   public void divide(double[] lhs, int lhsOffset, double[] rhs, int rhsOffset, double[] result, int resultOffset) {
/*  803 */     double[] reciprocal = new double[getSize()];
/*  804 */     pow(rhs, lhsOffset, -1, reciprocal, 0);
/*  805 */     multiply(lhs, lhsOffset, reciprocal, 0, result, resultOffset);
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
/*      */   public void remainder(double[] lhs, int lhsOffset, double[] rhs, int rhsOffset, double[] result, int resultOffset) {
/*  822 */     double rem = FastMath.IEEEremainder(lhs[lhsOffset], rhs[rhsOffset]);
/*  823 */     double k = FastMath.rint((lhs[lhsOffset] - rem) / rhs[rhsOffset]);
/*      */ 
/*      */     
/*  826 */     result[resultOffset] = rem;
/*      */ 
/*      */     
/*  829 */     for (int i = 1; i < getSize(); i++) {
/*  830 */       result[resultOffset + i] = lhs[lhsOffset + i] - k * rhs[rhsOffset + i];
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
/*      */   public void pow(double a, double[] operand, int operandOffset, double[] result, int resultOffset) {
/*  851 */     double[] function = new double[1 + this.order];
/*  852 */     if (a == 0.0D) {
/*  853 */       if (operand[operandOffset] == 0.0D) {
/*  854 */         function[0] = 1.0D;
/*  855 */         double infinity = Double.POSITIVE_INFINITY;
/*  856 */         for (int i = 1; i < function.length; i++) {
/*  857 */           infinity = -infinity;
/*  858 */           function[i] = infinity;
/*      */         } 
/*  860 */       } else if (operand[operandOffset] < 0.0D) {
/*  861 */         Arrays.fill(function, Double.NaN);
/*      */       } 
/*      */     } else {
/*  864 */       function[0] = FastMath.pow(a, operand[operandOffset]);
/*  865 */       double lnA = FastMath.log(a);
/*  866 */       for (int i = 1; i < function.length; i++) {
/*  867 */         function[i] = lnA * function[i - 1];
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  873 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void pow(double[] operand, int operandOffset, double p, double[] result, int resultOffset) {
/*  891 */     double[] function = new double[1 + this.order];
/*  892 */     double xk = FastMath.pow(operand[operandOffset], p - this.order);
/*  893 */     for (int i = this.order; i > 0; i--) {
/*  894 */       function[i] = xk;
/*  895 */       xk *= operand[operandOffset];
/*      */     } 
/*  897 */     function[0] = xk;
/*  898 */     double coefficient = p;
/*  899 */     for (int j = 1; j <= this.order; j++) {
/*  900 */       function[j] = function[j] * coefficient;
/*  901 */       coefficient *= p - j;
/*      */     } 
/*      */ 
/*      */     
/*  905 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void pow(double[] operand, int operandOffset, int n, double[] result, int resultOffset) {
/*  921 */     if (n == 0) {
/*      */       
/*  923 */       result[resultOffset] = 1.0D;
/*  924 */       Arrays.fill(result, resultOffset + 1, resultOffset + getSize(), 0.0D);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  930 */     double[] function = new double[1 + this.order];
/*      */     
/*  932 */     if (n > 0) {
/*      */       
/*  934 */       int maxOrder = FastMath.min(this.order, n);
/*  935 */       double xk = FastMath.pow(operand[operandOffset], n - maxOrder);
/*  936 */       for (int j = maxOrder; j > 0; j--) {
/*  937 */         function[j] = xk;
/*  938 */         xk *= operand[operandOffset];
/*      */       } 
/*  940 */       function[0] = xk;
/*      */     } else {
/*      */       
/*  943 */       double inv = 1.0D / operand[operandOffset];
/*  944 */       double xk = FastMath.pow(inv, -n);
/*  945 */       for (int j = 0; j <= this.order; j++) {
/*  946 */         function[j] = xk;
/*  947 */         xk *= inv;
/*      */       } 
/*      */     } 
/*      */     
/*  951 */     double coefficient = n;
/*  952 */     for (int i = 1; i <= this.order; i++) {
/*  953 */       function[i] = function[i] * coefficient;
/*  954 */       coefficient *= (n - i);
/*      */     } 
/*      */ 
/*      */     
/*  958 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void pow(double[] x, int xOffset, double[] y, int yOffset, double[] result, int resultOffset) {
/*  975 */     double[] logX = new double[getSize()];
/*  976 */     log(x, xOffset, logX, 0);
/*  977 */     double[] yLogX = new double[getSize()];
/*  978 */     multiply(logX, 0, y, yOffset, yLogX, 0);
/*  979 */     exp(yLogX, 0, result, resultOffset);
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
/*      */   public void rootN(double[] operand, int operandOffset, int n, double[] result, int resultOffset) {
/*  996 */     double xk, function[] = new double[1 + this.order];
/*      */     
/*  998 */     if (n == 2) {
/*  999 */       function[0] = FastMath.sqrt(operand[operandOffset]);
/* 1000 */       xk = 0.5D / function[0];
/* 1001 */     } else if (n == 3) {
/* 1002 */       function[0] = FastMath.cbrt(operand[operandOffset]);
/* 1003 */       xk = 1.0D / 3.0D * function[0] * function[0];
/*      */     } else {
/* 1005 */       function[0] = FastMath.pow(operand[operandOffset], 1.0D / n);
/* 1006 */       xk = 1.0D / n * FastMath.pow(function[0], n - 1);
/*      */     } 
/* 1008 */     double nReciprocal = 1.0D / n;
/* 1009 */     double xReciprocal = 1.0D / operand[operandOffset];
/* 1010 */     for (int i = 1; i <= this.order; i++) {
/* 1011 */       function[i] = xk;
/* 1012 */       xk *= xReciprocal * (nReciprocal - i);
/*      */     } 
/*      */ 
/*      */     
/* 1016 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void exp(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1032 */     double[] function = new double[1 + this.order];
/* 1033 */     Arrays.fill(function, FastMath.exp(operand[operandOffset]));
/*      */ 
/*      */     
/* 1036 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void expm1(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1052 */     double[] function = new double[1 + this.order];
/* 1053 */     function[0] = FastMath.expm1(operand[operandOffset]);
/* 1054 */     Arrays.fill(function, 1, 1 + this.order, FastMath.exp(operand[operandOffset]));
/*      */ 
/*      */     
/* 1057 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void log(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1073 */     double[] function = new double[1 + this.order];
/* 1074 */     function[0] = FastMath.log(operand[operandOffset]);
/* 1075 */     if (this.order > 0) {
/* 1076 */       double inv = 1.0D / operand[operandOffset];
/* 1077 */       double xk = inv;
/* 1078 */       for (int i = 1; i <= this.order; i++) {
/* 1079 */         function[i] = xk;
/* 1080 */         xk *= -i * inv;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1085 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void log1p(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1100 */     double[] function = new double[1 + this.order];
/* 1101 */     function[0] = FastMath.log1p(operand[operandOffset]);
/* 1102 */     if (this.order > 0) {
/* 1103 */       double inv = 1.0D / (1.0D + operand[operandOffset]);
/* 1104 */       double xk = inv;
/* 1105 */       for (int i = 1; i <= this.order; i++) {
/* 1106 */         function[i] = xk;
/* 1107 */         xk *= -i * inv;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1112 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void log10(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1127 */     double[] function = new double[1 + this.order];
/* 1128 */     function[0] = FastMath.log10(operand[operandOffset]);
/* 1129 */     if (this.order > 0) {
/* 1130 */       double inv = 1.0D / operand[operandOffset];
/* 1131 */       double xk = inv / FastMath.log(10.0D);
/* 1132 */       for (int i = 1; i <= this.order; i++) {
/* 1133 */         function[i] = xk;
/* 1134 */         xk *= -i * inv;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1139 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void cos(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1155 */     double[] function = new double[1 + this.order];
/* 1156 */     function[0] = FastMath.cos(operand[operandOffset]);
/* 1157 */     if (this.order > 0) {
/* 1158 */       function[1] = -FastMath.sin(operand[operandOffset]);
/* 1159 */       for (int i = 2; i <= this.order; i++) {
/* 1160 */         function[i] = -function[i - 2];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1165 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void sin(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1181 */     double[] function = new double[1 + this.order];
/* 1182 */     function[0] = FastMath.sin(operand[operandOffset]);
/* 1183 */     if (this.order > 0) {
/* 1184 */       function[1] = FastMath.cos(operand[operandOffset]);
/* 1185 */       for (int i = 2; i <= this.order; i++) {
/* 1186 */         function[i] = -function[i - 2];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1191 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void tan(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1207 */     double[] function = new double[1 + this.order];
/* 1208 */     double t = FastMath.tan(operand[operandOffset]);
/* 1209 */     function[0] = t;
/*      */     
/* 1211 */     if (this.order > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1220 */       double[] p = new double[this.order + 2];
/* 1221 */       p[1] = 1.0D;
/* 1222 */       double t2 = t * t;
/* 1223 */       for (int n = 1; n <= this.order; n++) {
/*      */ 
/*      */         
/* 1226 */         double v = 0.0D;
/* 1227 */         p[n + 1] = n * p[n];
/* 1228 */         for (int k = n + 1; k >= 0; k -= 2) {
/* 1229 */           v = v * t2 + p[k];
/* 1230 */           if (k > 2) {
/* 1231 */             p[k - 2] = (k - 1) * p[k - 1] + (k - 3) * p[k - 3];
/* 1232 */           } else if (k == 2) {
/* 1233 */             p[0] = p[1];
/*      */           } 
/*      */         } 
/* 1236 */         if ((n & 0x1) == 0) {
/* 1237 */           v *= t;
/*      */         }
/*      */         
/* 1240 */         function[n] = v;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1246 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void acos(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1262 */     double[] function = new double[1 + this.order];
/* 1263 */     double x = operand[operandOffset];
/* 1264 */     function[0] = FastMath.acos(x);
/* 1265 */     if (this.order > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1273 */       double[] p = new double[this.order];
/* 1274 */       p[0] = -1.0D;
/* 1275 */       double x2 = x * x;
/* 1276 */       double f = 1.0D / (1.0D - x2);
/* 1277 */       double coeff = FastMath.sqrt(f);
/* 1278 */       function[1] = coeff * p[0];
/* 1279 */       for (int n = 2; n <= this.order; n++) {
/*      */ 
/*      */         
/* 1282 */         double v = 0.0D;
/* 1283 */         p[n - 1] = (n - 1) * p[n - 2];
/* 1284 */         for (int k = n - 1; k >= 0; k -= 2) {
/* 1285 */           v = v * x2 + p[k];
/* 1286 */           if (k > 2) {
/* 1287 */             p[k - 2] = (k - 1) * p[k - 1] + (2 * n - k) * p[k - 3];
/* 1288 */           } else if (k == 2) {
/* 1289 */             p[0] = p[1];
/*      */           } 
/*      */         } 
/* 1292 */         if ((n & 0x1) == 0) {
/* 1293 */           v *= x;
/*      */         }
/*      */         
/* 1296 */         coeff *= f;
/* 1297 */         function[n] = coeff * v;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1303 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void asin(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1319 */     double[] function = new double[1 + this.order];
/* 1320 */     double x = operand[operandOffset];
/* 1321 */     function[0] = FastMath.asin(x);
/* 1322 */     if (this.order > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1330 */       double[] p = new double[this.order];
/* 1331 */       p[0] = 1.0D;
/* 1332 */       double x2 = x * x;
/* 1333 */       double f = 1.0D / (1.0D - x2);
/* 1334 */       double coeff = FastMath.sqrt(f);
/* 1335 */       function[1] = coeff * p[0];
/* 1336 */       for (int n = 2; n <= this.order; n++) {
/*      */ 
/*      */         
/* 1339 */         double v = 0.0D;
/* 1340 */         p[n - 1] = (n - 1) * p[n - 2];
/* 1341 */         for (int k = n - 1; k >= 0; k -= 2) {
/* 1342 */           v = v * x2 + p[k];
/* 1343 */           if (k > 2) {
/* 1344 */             p[k - 2] = (k - 1) * p[k - 1] + (2 * n - k) * p[k - 3];
/* 1345 */           } else if (k == 2) {
/* 1346 */             p[0] = p[1];
/*      */           } 
/*      */         } 
/* 1349 */         if ((n & 0x1) == 0) {
/* 1350 */           v *= x;
/*      */         }
/*      */         
/* 1353 */         coeff *= f;
/* 1354 */         function[n] = coeff * v;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1360 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void atan(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1376 */     double[] function = new double[1 + this.order];
/* 1377 */     double x = operand[operandOffset];
/* 1378 */     function[0] = FastMath.atan(x);
/* 1379 */     if (this.order > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1387 */       double[] q = new double[this.order];
/* 1388 */       q[0] = 1.0D;
/* 1389 */       double x2 = x * x;
/* 1390 */       double f = 1.0D / (1.0D + x2);
/* 1391 */       double coeff = f;
/* 1392 */       function[1] = coeff * q[0];
/* 1393 */       for (int n = 2; n <= this.order; n++) {
/*      */ 
/*      */         
/* 1396 */         double v = 0.0D;
/* 1397 */         q[n - 1] = -n * q[n - 2];
/* 1398 */         for (int k = n - 1; k >= 0; k -= 2) {
/* 1399 */           v = v * x2 + q[k];
/* 1400 */           if (k > 2) {
/* 1401 */             q[k - 2] = (k - 1) * q[k - 1] + (k - 1 - 2 * n) * q[k - 3];
/* 1402 */           } else if (k == 2) {
/* 1403 */             q[0] = q[1];
/*      */           } 
/*      */         } 
/* 1406 */         if ((n & 0x1) == 0) {
/* 1407 */           v *= x;
/*      */         }
/*      */         
/* 1410 */         coeff *= f;
/* 1411 */         function[n] = coeff * v;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1417 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void atan2(double[] y, int yOffset, double[] x, int xOffset, double[] result, int resultOffset) {
/* 1436 */     double[] tmp1 = new double[getSize()];
/* 1437 */     multiply(x, xOffset, x, xOffset, tmp1, 0);
/* 1438 */     double[] tmp2 = new double[getSize()];
/* 1439 */     multiply(y, yOffset, y, yOffset, tmp2, 0);
/* 1440 */     add(tmp1, 0, tmp2, 0, tmp2, 0);
/* 1441 */     rootN(tmp2, 0, 2, tmp1, 0);
/*      */     
/* 1443 */     if (x[xOffset] >= 0.0D) {
/*      */ 
/*      */       
/* 1446 */       add(tmp1, 0, x, xOffset, tmp2, 0);
/* 1447 */       divide(y, yOffset, tmp2, 0, tmp1, 0);
/* 1448 */       atan(tmp1, 0, tmp2, 0);
/* 1449 */       for (int i = 0; i < tmp2.length; i++) {
/* 1450 */         result[resultOffset + i] = 2.0D * tmp2[i];
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1456 */       subtract(tmp1, 0, x, xOffset, tmp2, 0);
/* 1457 */       divide(y, yOffset, tmp2, 0, tmp1, 0);
/* 1458 */       atan(tmp1, 0, tmp2, 0);
/* 1459 */       result[resultOffset] = ((tmp2[0] <= 0.0D) ? -3.141592653589793D : Math.PI) - 2.0D * tmp2[0];
/*      */       
/* 1461 */       for (int i = 1; i < tmp2.length; i++) {
/* 1462 */         result[resultOffset + i] = -2.0D * tmp2[i];
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1468 */     result[resultOffset] = FastMath.atan2(y[yOffset], x[xOffset]);
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
/*      */   public void cosh(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1484 */     double[] function = new double[1 + this.order];
/* 1485 */     function[0] = FastMath.cosh(operand[operandOffset]);
/* 1486 */     if (this.order > 0) {
/* 1487 */       function[1] = FastMath.sinh(operand[operandOffset]);
/* 1488 */       for (int i = 2; i <= this.order; i++) {
/* 1489 */         function[i] = function[i - 2];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1494 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void sinh(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1510 */     double[] function = new double[1 + this.order];
/* 1511 */     function[0] = FastMath.sinh(operand[operandOffset]);
/* 1512 */     if (this.order > 0) {
/* 1513 */       function[1] = FastMath.cosh(operand[operandOffset]);
/* 1514 */       for (int i = 2; i <= this.order; i++) {
/* 1515 */         function[i] = function[i - 2];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1520 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void tanh(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1536 */     double[] function = new double[1 + this.order];
/* 1537 */     double t = FastMath.tanh(operand[operandOffset]);
/* 1538 */     function[0] = t;
/*      */     
/* 1540 */     if (this.order > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1549 */       double[] p = new double[this.order + 2];
/* 1550 */       p[1] = 1.0D;
/* 1551 */       double t2 = t * t;
/* 1552 */       for (int n = 1; n <= this.order; n++) {
/*      */ 
/*      */         
/* 1555 */         double v = 0.0D;
/* 1556 */         p[n + 1] = -n * p[n];
/* 1557 */         for (int k = n + 1; k >= 0; k -= 2) {
/* 1558 */           v = v * t2 + p[k];
/* 1559 */           if (k > 2) {
/* 1560 */             p[k - 2] = (k - 1) * p[k - 1] - (k - 3) * p[k - 3];
/* 1561 */           } else if (k == 2) {
/* 1562 */             p[0] = p[1];
/*      */           } 
/*      */         } 
/* 1565 */         if ((n & 0x1) == 0) {
/* 1566 */           v *= t;
/*      */         }
/*      */         
/* 1569 */         function[n] = v;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1575 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void acosh(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1591 */     double[] function = new double[1 + this.order];
/* 1592 */     double x = operand[operandOffset];
/* 1593 */     function[0] = FastMath.acosh(x);
/* 1594 */     if (this.order > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1602 */       double[] p = new double[this.order];
/* 1603 */       p[0] = 1.0D;
/* 1604 */       double x2 = x * x;
/* 1605 */       double f = 1.0D / (x2 - 1.0D);
/* 1606 */       double coeff = FastMath.sqrt(f);
/* 1607 */       function[1] = coeff * p[0];
/* 1608 */       for (int n = 2; n <= this.order; n++) {
/*      */ 
/*      */         
/* 1611 */         double v = 0.0D;
/* 1612 */         p[n - 1] = (1 - n) * p[n - 2];
/* 1613 */         for (int k = n - 1; k >= 0; k -= 2) {
/* 1614 */           v = v * x2 + p[k];
/* 1615 */           if (k > 2) {
/* 1616 */             p[k - 2] = (1 - k) * p[k - 1] + (k - 2 * n) * p[k - 3];
/* 1617 */           } else if (k == 2) {
/* 1618 */             p[0] = -p[1];
/*      */           } 
/*      */         } 
/* 1621 */         if ((n & 0x1) == 0) {
/* 1622 */           v *= x;
/*      */         }
/*      */         
/* 1625 */         coeff *= f;
/* 1626 */         function[n] = coeff * v;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1632 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void asinh(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1648 */     double[] function = new double[1 + this.order];
/* 1649 */     double x = operand[operandOffset];
/* 1650 */     function[0] = FastMath.asinh(x);
/* 1651 */     if (this.order > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1659 */       double[] p = new double[this.order];
/* 1660 */       p[0] = 1.0D;
/* 1661 */       double x2 = x * x;
/* 1662 */       double f = 1.0D / (1.0D + x2);
/* 1663 */       double coeff = FastMath.sqrt(f);
/* 1664 */       function[1] = coeff * p[0];
/* 1665 */       for (int n = 2; n <= this.order; n++) {
/*      */ 
/*      */         
/* 1668 */         double v = 0.0D;
/* 1669 */         p[n - 1] = (1 - n) * p[n - 2];
/* 1670 */         for (int k = n - 1; k >= 0; k -= 2) {
/* 1671 */           v = v * x2 + p[k];
/* 1672 */           if (k > 2) {
/* 1673 */             p[k - 2] = (k - 1) * p[k - 1] + (k - 2 * n) * p[k - 3];
/* 1674 */           } else if (k == 2) {
/* 1675 */             p[0] = p[1];
/*      */           } 
/*      */         } 
/* 1678 */         if ((n & 0x1) == 0) {
/* 1679 */           v *= x;
/*      */         }
/*      */         
/* 1682 */         coeff *= f;
/* 1683 */         function[n] = coeff * v;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1689 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void atanh(double[] operand, int operandOffset, double[] result, int resultOffset) {
/* 1705 */     double[] function = new double[1 + this.order];
/* 1706 */     double x = operand[operandOffset];
/* 1707 */     function[0] = FastMath.atanh(x);
/* 1708 */     if (this.order > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1716 */       double[] q = new double[this.order];
/* 1717 */       q[0] = 1.0D;
/* 1718 */       double x2 = x * x;
/* 1719 */       double f = 1.0D / (1.0D - x2);
/* 1720 */       double coeff = f;
/* 1721 */       function[1] = coeff * q[0];
/* 1722 */       for (int n = 2; n <= this.order; n++) {
/*      */ 
/*      */         
/* 1725 */         double v = 0.0D;
/* 1726 */         q[n - 1] = n * q[n - 2];
/* 1727 */         for (int k = n - 1; k >= 0; k -= 2) {
/* 1728 */           v = v * x2 + q[k];
/* 1729 */           if (k > 2) {
/* 1730 */             q[k - 2] = (k - 1) * q[k - 1] + (2 * n - k + 1) * q[k - 3];
/* 1731 */           } else if (k == 2) {
/* 1732 */             q[0] = q[1];
/*      */           } 
/*      */         } 
/* 1735 */         if ((n & 0x1) == 0) {
/* 1736 */           v *= x;
/*      */         }
/*      */         
/* 1739 */         coeff *= f;
/* 1740 */         function[n] = coeff * v;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1746 */     compose(operand, operandOffset, function, result, resultOffset);
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
/*      */   public void compose(double[] operand, int operandOffset, double[] f, double[] result, int resultOffset) {
/* 1762 */     for (int i = 0; i < this.compIndirection.length; i++) {
/* 1763 */       int[][] mappingI = this.compIndirection[i];
/* 1764 */       double r = 0.0D;
/* 1765 */       for (int j = 0; j < mappingI.length; j++) {
/* 1766 */         int[] mappingIJ = mappingI[j];
/* 1767 */         double product = mappingIJ[0] * f[mappingIJ[1]];
/* 1768 */         for (int k = 2; k < mappingIJ.length; k++) {
/* 1769 */           product *= operand[operandOffset + mappingIJ[k]];
/*      */         }
/* 1771 */         r += product;
/*      */       } 
/* 1773 */       result[resultOffset + i] = r;
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
/*      */   public double taylor(double[] ds, int dsOffset, double... delta) throws MathArithmeticException {
/* 1786 */     double value = 0.0D;
/* 1787 */     for (int i = getSize() - 1; i >= 0; i--) {
/* 1788 */       int[] orders = getPartialDerivativeOrders(i);
/* 1789 */       double term = ds[dsOffset + i];
/* 1790 */       for (int k = 0; k < orders.length; k++) {
/* 1791 */         if (orders[k] > 0) {
/*      */           try {
/* 1793 */             term *= FastMath.pow(delta[k], orders[k]) / CombinatoricsUtils.factorial(orders[k]);
/*      */           }
/* 1795 */           catch (NotPositiveException e) {
/*      */             
/* 1797 */             throw new MathInternalError(e);
/*      */           } 
/*      */         }
/*      */       } 
/* 1801 */       value += term;
/*      */     } 
/* 1803 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCompatibility(DSCompiler compiler) throws DimensionMismatchException {
/* 1812 */     if (this.parameters != compiler.parameters) {
/* 1813 */       throw new DimensionMismatchException(this.parameters, compiler.parameters);
/*      */     }
/* 1815 */     if (this.order != compiler.order)
/* 1816 */       throw new DimensionMismatchException(this.order, compiler.order); 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/differentiation/DSCompiler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */