/*      */ package org.apache.commons.math3.util;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.TreeSet;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.distribution.UniformIntegerDistribution;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*      */ import org.apache.commons.math3.exception.MathInternalError;
/*      */ import org.apache.commons.math3.exception.NoDataException;
/*      */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*      */ import org.apache.commons.math3.exception.NotANumberException;
/*      */ import org.apache.commons.math3.exception.NotPositiveException;
/*      */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.random.RandomGenerator;
/*      */ import org.apache.commons.math3.random.Well19937c;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MathArrays
/*      */ {
/*      */   public static double[] scale(double val, double[] arr) {
/*   90 */     double[] newArr = new double[arr.length];
/*   91 */     for (int i = 0; i < arr.length; i++) {
/*   92 */       newArr[i] = arr[i] * val;
/*      */     }
/*   94 */     return newArr;
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
/*      */   public static void scaleInPlace(double val, double[] arr) {
/*  107 */     for (int i = 0; i < arr.length; i++) {
/*  108 */       arr[i] = arr[i] * val;
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
/*      */   public static double[] ebeAdd(double[] a, double[] b) throws DimensionMismatchException {
/*  124 */     checkEqualLength(a, b);
/*      */     
/*  126 */     double[] result = (double[])a.clone();
/*  127 */     for (int i = 0; i < a.length; i++) {
/*  128 */       result[i] = result[i] + b[i];
/*      */     }
/*  130 */     return result;
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
/*      */   public static double[] ebeSubtract(double[] a, double[] b) throws DimensionMismatchException {
/*  144 */     checkEqualLength(a, b);
/*      */     
/*  146 */     double[] result = (double[])a.clone();
/*  147 */     for (int i = 0; i < a.length; i++) {
/*  148 */       result[i] = result[i] - b[i];
/*      */     }
/*  150 */     return result;
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
/*      */   public static double[] ebeMultiply(double[] a, double[] b) throws DimensionMismatchException {
/*  164 */     checkEqualLength(a, b);
/*      */     
/*  166 */     double[] result = (double[])a.clone();
/*  167 */     for (int i = 0; i < a.length; i++) {
/*  168 */       result[i] = result[i] * b[i];
/*      */     }
/*  170 */     return result;
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
/*      */   public static double[] ebeDivide(double[] a, double[] b) throws DimensionMismatchException {
/*  184 */     checkEqualLength(a, b);
/*      */     
/*  186 */     double[] result = (double[])a.clone();
/*  187 */     for (int i = 0; i < a.length; i++) {
/*  188 */       result[i] = result[i] / b[i];
/*      */     }
/*  190 */     return result;
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
/*      */   public static double distance1(double[] p1, double[] p2) throws DimensionMismatchException {
/*  203 */     checkEqualLength(p1, p2);
/*  204 */     double sum = 0.0D;
/*  205 */     for (int i = 0; i < p1.length; i++) {
/*  206 */       sum += FastMath.abs(p1[i] - p2[i]);
/*      */     }
/*  208 */     return sum;
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
/*      */   public static int distance1(int[] p1, int[] p2) throws DimensionMismatchException {
/*  221 */     checkEqualLength(p1, p2);
/*  222 */     int sum = 0;
/*  223 */     for (int i = 0; i < p1.length; i++) {
/*  224 */       sum += FastMath.abs(p1[i] - p2[i]);
/*      */     }
/*  226 */     return sum;
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
/*      */   public static double distance(double[] p1, double[] p2) throws DimensionMismatchException {
/*  239 */     checkEqualLength(p1, p2);
/*  240 */     double sum = 0.0D;
/*  241 */     for (int i = 0; i < p1.length; i++) {
/*  242 */       double dp = p1[i] - p2[i];
/*  243 */       sum += dp * dp;
/*      */     } 
/*  245 */     return FastMath.sqrt(sum);
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
/*      */   public static double cosAngle(double[] v1, double[] v2) {
/*  257 */     return linearCombination(v1, v2) / safeNorm(v1) * safeNorm(v2);
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
/*      */   public static double distance(int[] p1, int[] p2) throws DimensionMismatchException {
/*  270 */     checkEqualLength(p1, p2);
/*  271 */     double sum = 0.0D;
/*  272 */     for (int i = 0; i < p1.length; i++) {
/*  273 */       double dp = (p1[i] - p2[i]);
/*  274 */       sum += dp * dp;
/*      */     } 
/*  276 */     return FastMath.sqrt(sum);
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
/*      */   public static double distanceInf(double[] p1, double[] p2) throws DimensionMismatchException {
/*  289 */     checkEqualLength(p1, p2);
/*  290 */     double max = 0.0D;
/*  291 */     for (int i = 0; i < p1.length; i++) {
/*  292 */       max = FastMath.max(max, FastMath.abs(p1[i] - p2[i]));
/*      */     }
/*  294 */     return max;
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
/*      */   public static int distanceInf(int[] p1, int[] p2) throws DimensionMismatchException {
/*  307 */     checkEqualLength(p1, p2);
/*  308 */     int max = 0;
/*  309 */     for (int i = 0; i < p1.length; i++) {
/*  310 */       max = FastMath.max(max, FastMath.abs(p1[i] - p2[i]));
/*      */     }
/*  312 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum OrderDirection
/*      */   {
/*  320 */     INCREASING,
/*      */     
/*  322 */     DECREASING;
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
/*      */   public static <T extends Comparable<? super T>> boolean isMonotonic(T[] val, OrderDirection dir, boolean strict) {
/*  337 */     T previous = val[0];
/*  338 */     int max = val.length;
/*  339 */     for (int i = 1; i < max; i++) {
/*      */       int comp;
/*  341 */       switch (dir) {
/*      */         case TAIL:
/*  343 */           comp = previous.compareTo(val[i]);
/*  344 */           if (strict) {
/*  345 */             if (comp >= 0)
/*  346 */               return false; 
/*      */             break;
/*      */           } 
/*  349 */           if (comp > 0) {
/*  350 */             return false;
/*      */           }
/*      */           break;
/*      */         
/*      */         case HEAD:
/*  355 */           comp = val[i].compareTo(previous);
/*  356 */           if (strict) {
/*  357 */             if (comp >= 0)
/*  358 */               return false; 
/*      */             break;
/*      */           } 
/*  361 */           if (comp > 0) {
/*  362 */             return false;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  368 */           throw new MathInternalError();
/*      */       } 
/*      */       
/*  371 */       previous = val[i];
/*      */     } 
/*  373 */     return true;
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
/*      */   public static boolean isMonotonic(double[] val, OrderDirection dir, boolean strict) {
/*  385 */     return checkOrder(val, dir, strict, false);
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
/*      */   public static boolean checkEqualLength(double[] a, double[] b, boolean abort) {
/*  402 */     if (a.length == b.length) {
/*  403 */       return true;
/*      */     }
/*  405 */     if (abort) {
/*  406 */       throw new DimensionMismatchException(a.length, b.length);
/*      */     }
/*  408 */     return false;
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
/*      */   public static void checkEqualLength(double[] a, double[] b) {
/*  422 */     checkEqualLength(a, b, true);
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
/*      */   public static boolean checkEqualLength(int[] a, int[] b, boolean abort) {
/*  440 */     if (a.length == b.length) {
/*  441 */       return true;
/*      */     }
/*  443 */     if (abort) {
/*  444 */       throw new DimensionMismatchException(a.length, b.length);
/*      */     }
/*  446 */     return false;
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
/*      */   public static void checkEqualLength(int[] a, int[] b) {
/*  460 */     checkEqualLength(a, b, true);
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
/*      */   public static boolean checkOrder(double[] val, OrderDirection dir, boolean strict, boolean abort) throws NonMonotonicSequenceException {
/*  477 */     double previous = val[0];
/*  478 */     int max = val.length;
/*      */     
/*      */     int index;
/*      */     
/*  482 */     for (index = 1; index < max; index++) {
/*  483 */       switch (dir) {
/*      */         case TAIL:
/*  485 */           if (strict ? (
/*  486 */             val[index] <= previous) : (
/*      */ 
/*      */ 
/*      */             
/*  490 */             val[index] < previous)) {
/*      */             break;
/*      */           }
/*      */           break;
/*      */         
/*      */         case HEAD:
/*  496 */           if (strict ? (
/*  497 */             val[index] >= previous) : (
/*      */ 
/*      */ 
/*      */             
/*  501 */             val[index] > previous)) {
/*      */             break;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  508 */           throw new MathInternalError();
/*      */       } 
/*      */       
/*  511 */       previous = val[index];
/*      */     } 
/*      */     
/*  514 */     if (index == max)
/*      */     {
/*  516 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  520 */     if (abort) {
/*  521 */       throw new NonMonotonicSequenceException(Double.valueOf(val[index]), Double.valueOf(previous), index, dir, strict);
/*      */     }
/*  523 */     return false;
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
/*      */   public static void checkOrder(double[] val, OrderDirection dir, boolean strict) throws NonMonotonicSequenceException {
/*  538 */     checkOrder(val, dir, strict, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkOrder(double[] val) throws NonMonotonicSequenceException {
/*  549 */     checkOrder(val, OrderDirection.INCREASING, true);
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
/*      */   public static void checkRectangular(long[][] in) throws NullArgumentException, DimensionMismatchException {
/*  562 */     MathUtils.checkNotNull(in);
/*  563 */     for (int i = 1; i < in.length; i++) {
/*  564 */       if ((in[i]).length != (in[0]).length) {
/*  565 */         throw new DimensionMismatchException(LocalizedFormats.DIFFERENT_ROWS_LENGTHS, (in[i]).length, (in[0]).length);
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
/*      */   public static void checkPositive(double[] in) throws NotStrictlyPositiveException {
/*  582 */     for (int i = 0; i < in.length; i++) {
/*  583 */       if (in[i] <= 0.0D) {
/*  584 */         throw new NotStrictlyPositiveException(Double.valueOf(in[i]));
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
/*      */   public static void checkNotNaN(double[] in) throws NotANumberException {
/*  598 */     for (int i = 0; i < in.length; i++) {
/*  599 */       if (Double.isNaN(in[i])) {
/*  600 */         throw new NotANumberException();
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
/*      */   public static void checkNonNegative(long[] in) throws NotPositiveException {
/*  614 */     for (int i = 0; i < in.length; i++) {
/*  615 */       if (in[i] < 0L) {
/*  616 */         throw new NotPositiveException(Long.valueOf(in[i]));
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
/*      */   public static void checkNonNegative(long[][] in) throws NotPositiveException {
/*  630 */     for (int i = 0; i < in.length; i++) {
/*  631 */       for (int j = 0; j < (in[i]).length; j++) {
/*  632 */         if (in[i][j] < 0L) {
/*  633 */           throw new NotPositiveException(Long.valueOf(in[i][j]));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double safeNorm(double[] v) {
/*  700 */     double norm, rdwarf = 3.834E-20D;
/*  701 */     double rgiant = 1.304E19D;
/*  702 */     double s1 = 0.0D;
/*  703 */     double s2 = 0.0D;
/*  704 */     double s3 = 0.0D;
/*  705 */     double x1max = 0.0D;
/*  706 */     double x3max = 0.0D;
/*  707 */     double floatn = v.length;
/*  708 */     double agiant = rgiant / floatn;
/*  709 */     for (int i = 0; i < v.length; i++) {
/*  710 */       double xabs = FastMath.abs(v[i]);
/*  711 */       if (xabs < rdwarf || xabs > agiant) {
/*  712 */         if (xabs > rdwarf) {
/*  713 */           if (xabs > x1max) {
/*  714 */             double r = x1max / xabs;
/*  715 */             s1 = 1.0D + s1 * r * r;
/*  716 */             x1max = xabs;
/*      */           } else {
/*  718 */             double r = xabs / x1max;
/*  719 */             s1 += r * r;
/*      */           }
/*      */         
/*  722 */         } else if (xabs > x3max) {
/*  723 */           double r = x3max / xabs;
/*  724 */           s3 = 1.0D + s3 * r * r;
/*  725 */           x3max = xabs;
/*      */         }
/*  727 */         else if (xabs != 0.0D) {
/*  728 */           double r = xabs / x3max;
/*  729 */           s3 += r * r;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  734 */         s2 += xabs * xabs;
/*      */       } 
/*      */     } 
/*      */     
/*  738 */     if (s1 != 0.0D) {
/*  739 */       norm = x1max * Math.sqrt(s1 + s2 / x1max / x1max);
/*      */     }
/*  741 */     else if (s2 == 0.0D) {
/*  742 */       norm = x3max * Math.sqrt(s3);
/*      */     }
/*  744 */     else if (s2 >= x3max) {
/*  745 */       norm = Math.sqrt(s2 * (1.0D + x3max / s2 * x3max * s3));
/*      */     } else {
/*  747 */       norm = Math.sqrt(x3max * (s2 / x3max + x3max * s3));
/*      */     } 
/*      */ 
/*      */     
/*  751 */     return norm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PairDoubleInteger
/*      */   {
/*      */     private final double key;
/*      */ 
/*      */ 
/*      */     
/*      */     private final int value;
/*      */ 
/*      */ 
/*      */     
/*      */     PairDoubleInteger(double key, int value) {
/*  768 */       this.key = key;
/*  769 */       this.value = value;
/*      */     }
/*      */ 
/*      */     
/*      */     public double getKey() {
/*  774 */       return this.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getValue() {
/*  779 */       return this.value;
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
/*      */   public static void sortInPlace(double[] x, double[]... yList) throws DimensionMismatchException, NullArgumentException {
/*  801 */     sortInPlace(x, OrderDirection.INCREASING, yList);
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
/*      */   public static void sortInPlace(double[] x, OrderDirection dir, double[]... yList) throws NullArgumentException, DimensionMismatchException {
/*  828 */     if (x == null) {
/*  829 */       throw new NullArgumentException();
/*      */     }
/*      */     
/*  832 */     int yListLen = yList.length;
/*  833 */     int len = x.length;
/*      */     
/*  835 */     for (int j = 0; j < yListLen; j++) {
/*  836 */       double[] y = yList[j];
/*  837 */       if (y == null) {
/*  838 */         throw new NullArgumentException();
/*      */       }
/*  840 */       if (y.length != len) {
/*  841 */         throw new DimensionMismatchException(y.length, len);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  846 */     List<PairDoubleInteger> list = new ArrayList<PairDoubleInteger>(len);
/*      */     
/*  848 */     for (int i = 0; i < len; i++) {
/*  849 */       list.add(new PairDoubleInteger(x[i], i));
/*      */     }
/*      */ 
/*      */     
/*  853 */     Comparator<PairDoubleInteger> comp = (dir == OrderDirection.INCREASING) ? new Comparator<PairDoubleInteger>()
/*      */       {
/*      */ 
/*      */         
/*      */         public int compare(MathArrays.PairDoubleInteger o1, MathArrays.PairDoubleInteger o2)
/*      */         {
/*  859 */           return Double.compare(o1.getKey(), o2.getKey());
/*      */         }
/*      */       } : new Comparator<PairDoubleInteger>()
/*      */       {
/*      */         public int compare(MathArrays.PairDoubleInteger o1, MathArrays.PairDoubleInteger o2)
/*      */         {
/*  865 */           return Double.compare(o2.getKey(), o1.getKey());
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*  870 */     Collections.sort(list, comp);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  875 */     int[] indices = new int[len];
/*  876 */     for (int m = 0; m < len; m++) {
/*  877 */       PairDoubleInteger e = list.get(m);
/*  878 */       x[m] = e.getKey();
/*  879 */       indices[m] = e.getValue();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  884 */     for (int k = 0; k < yListLen; k++) {
/*      */       
/*  886 */       double[] yInPlace = yList[k];
/*  887 */       double[] yOrig = (double[])yInPlace.clone();
/*      */       
/*  889 */       for (int n = 0; n < len; n++) {
/*  890 */         yInPlace[n] = yOrig[indices[n]];
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
/*      */   public static int[] copyOf(int[] source) {
/*  902 */     return copyOf(source, source.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] copyOf(double[] source) {
/*  912 */     return copyOf(source, source.length);
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
/*      */   public static int[] copyOf(int[] source, int len) {
/*  925 */     int[] output = new int[len];
/*  926 */     System.arraycopy(source, 0, output, 0, FastMath.min(len, source.length));
/*  927 */     return output;
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
/*      */   public static double[] copyOf(double[] source, int len) {
/*  940 */     double[] output = new double[len];
/*  941 */     System.arraycopy(source, 0, output, 0, FastMath.min(len, source.length));
/*  942 */     return output;
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
/*      */   public static double[] copyOfRange(double[] source, int from, int to) {
/*  954 */     int len = to - from;
/*  955 */     double[] output = new double[len];
/*  956 */     System.arraycopy(source, from, output, 0, FastMath.min(len, source.length - from));
/*  957 */     return output;
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
/*      */   public static double linearCombination(double[] a, double[] b) throws DimensionMismatchException {
/*  979 */     checkEqualLength(a, b);
/*  980 */     int len = a.length;
/*      */     
/*  982 */     if (len == 1)
/*      */     {
/*  984 */       return a[0] * b[0];
/*      */     }
/*      */     
/*  987 */     double[] prodHigh = new double[len];
/*  988 */     double prodLowSum = 0.0D;
/*      */     
/*  990 */     for (int i = 0; i < len; i++) {
/*  991 */       double ai = a[i];
/*  992 */       double aHigh = Double.longBitsToDouble(Double.doubleToRawLongBits(ai) & 0xFFFFFFFFF8000000L);
/*  993 */       double aLow = ai - aHigh;
/*      */       
/*  995 */       double bi = b[i];
/*  996 */       double bHigh = Double.longBitsToDouble(Double.doubleToRawLongBits(bi) & 0xFFFFFFFFF8000000L);
/*  997 */       double bLow = bi - bHigh;
/*  998 */       prodHigh[i] = ai * bi;
/*  999 */       double prodLow = aLow * bLow - prodHigh[i] - aHigh * bHigh - aLow * bHigh - aHigh * bLow;
/*      */ 
/*      */ 
/*      */       
/* 1003 */       prodLowSum += prodLow;
/*      */     } 
/*      */ 
/*      */     
/* 1007 */     double prodHighCur = prodHigh[0];
/* 1008 */     double prodHighNext = prodHigh[1];
/* 1009 */     double sHighPrev = prodHighCur + prodHighNext;
/* 1010 */     double sPrime = sHighPrev - prodHighNext;
/* 1011 */     double sLowSum = prodHighNext - sHighPrev - sPrime + prodHighCur - sPrime;
/*      */     
/* 1013 */     int lenMinusOne = len - 1;
/* 1014 */     for (int j = 1; j < lenMinusOne; j++) {
/* 1015 */       prodHighNext = prodHigh[j + 1];
/* 1016 */       double sHighCur = sHighPrev + prodHighNext;
/* 1017 */       sPrime = sHighCur - prodHighNext;
/* 1018 */       sLowSum += prodHighNext - sHighCur - sPrime + sHighPrev - sPrime;
/* 1019 */       sHighPrev = sHighCur;
/*      */     } 
/*      */     
/* 1022 */     double result = sHighPrev + prodLowSum + sLowSum;
/*      */     
/* 1024 */     if (Double.isNaN(result)) {
/*      */ 
/*      */       
/* 1027 */       result = 0.0D;
/* 1028 */       for (int k = 0; k < len; k++) {
/* 1029 */         result += a[k] * b[k];
/*      */       }
/*      */     } 
/*      */     
/* 1033 */     return result;
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
/*      */   public static double linearCombination(double a1, double b1, double a2, double b2) {
/* 1071 */     double a1High = Double.longBitsToDouble(Double.doubleToRawLongBits(a1) & 0xFFFFFFFFF8000000L);
/* 1072 */     double a1Low = a1 - a1High;
/* 1073 */     double b1High = Double.longBitsToDouble(Double.doubleToRawLongBits(b1) & 0xFFFFFFFFF8000000L);
/* 1074 */     double b1Low = b1 - b1High;
/*      */ 
/*      */     
/* 1077 */     double prod1High = a1 * b1;
/* 1078 */     double prod1Low = a1Low * b1Low - prod1High - a1High * b1High - a1Low * b1High - a1High * b1Low;
/*      */ 
/*      */     
/* 1081 */     double a2High = Double.longBitsToDouble(Double.doubleToRawLongBits(a2) & 0xFFFFFFFFF8000000L);
/* 1082 */     double a2Low = a2 - a2High;
/* 1083 */     double b2High = Double.longBitsToDouble(Double.doubleToRawLongBits(b2) & 0xFFFFFFFFF8000000L);
/* 1084 */     double b2Low = b2 - b2High;
/*      */ 
/*      */     
/* 1087 */     double prod2High = a2 * b2;
/* 1088 */     double prod2Low = a2Low * b2Low - prod2High - a2High * b2High - a2Low * b2High - a2High * b2Low;
/*      */ 
/*      */     
/* 1091 */     double s12High = prod1High + prod2High;
/* 1092 */     double s12Prime = s12High - prod2High;
/* 1093 */     double s12Low = prod2High - s12High - s12Prime + prod1High - s12Prime;
/*      */ 
/*      */ 
/*      */     
/* 1097 */     double result = s12High + prod1Low + prod2Low + s12Low;
/*      */     
/* 1099 */     if (Double.isNaN(result))
/*      */     {
/*      */       
/* 1102 */       result = a1 * b1 + a2 * b2;
/*      */     }
/*      */     
/* 1105 */     return result;
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
/*      */   public static double linearCombination(double a1, double b1, double a2, double b2, double a3, double b3) {
/* 1146 */     double a1High = Double.longBitsToDouble(Double.doubleToRawLongBits(a1) & 0xFFFFFFFFF8000000L);
/* 1147 */     double a1Low = a1 - a1High;
/* 1148 */     double b1High = Double.longBitsToDouble(Double.doubleToRawLongBits(b1) & 0xFFFFFFFFF8000000L);
/* 1149 */     double b1Low = b1 - b1High;
/*      */ 
/*      */     
/* 1152 */     double prod1High = a1 * b1;
/* 1153 */     double prod1Low = a1Low * b1Low - prod1High - a1High * b1High - a1Low * b1High - a1High * b1Low;
/*      */ 
/*      */     
/* 1156 */     double a2High = Double.longBitsToDouble(Double.doubleToRawLongBits(a2) & 0xFFFFFFFFF8000000L);
/* 1157 */     double a2Low = a2 - a2High;
/* 1158 */     double b2High = Double.longBitsToDouble(Double.doubleToRawLongBits(b2) & 0xFFFFFFFFF8000000L);
/* 1159 */     double b2Low = b2 - b2High;
/*      */ 
/*      */     
/* 1162 */     double prod2High = a2 * b2;
/* 1163 */     double prod2Low = a2Low * b2Low - prod2High - a2High * b2High - a2Low * b2High - a2High * b2Low;
/*      */ 
/*      */     
/* 1166 */     double a3High = Double.longBitsToDouble(Double.doubleToRawLongBits(a3) & 0xFFFFFFFFF8000000L);
/* 1167 */     double a3Low = a3 - a3High;
/* 1168 */     double b3High = Double.longBitsToDouble(Double.doubleToRawLongBits(b3) & 0xFFFFFFFFF8000000L);
/* 1169 */     double b3Low = b3 - b3High;
/*      */ 
/*      */     
/* 1172 */     double prod3High = a3 * b3;
/* 1173 */     double prod3Low = a3Low * b3Low - prod3High - a3High * b3High - a3Low * b3High - a3High * b3Low;
/*      */ 
/*      */     
/* 1176 */     double s12High = prod1High + prod2High;
/* 1177 */     double s12Prime = s12High - prod2High;
/* 1178 */     double s12Low = prod2High - s12High - s12Prime + prod1High - s12Prime;
/*      */ 
/*      */     
/* 1181 */     double s123High = s12High + prod3High;
/* 1182 */     double s123Prime = s123High - prod3High;
/* 1183 */     double s123Low = prod3High - s123High - s123Prime + s12High - s123Prime;
/*      */ 
/*      */ 
/*      */     
/* 1187 */     double result = s123High + prod1Low + prod2Low + prod3Low + s12Low + s123Low;
/*      */     
/* 1189 */     if (Double.isNaN(result))
/*      */     {
/*      */       
/* 1192 */       result = a1 * b1 + a2 * b2 + a3 * b3;
/*      */     }
/*      */     
/* 1195 */     return result;
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
/*      */   public static double linearCombination(double a1, double b1, double a2, double b2, double a3, double b3, double a4, double b4) {
/* 1241 */     double a1High = Double.longBitsToDouble(Double.doubleToRawLongBits(a1) & 0xFFFFFFFFF8000000L);
/* 1242 */     double a1Low = a1 - a1High;
/* 1243 */     double b1High = Double.longBitsToDouble(Double.doubleToRawLongBits(b1) & 0xFFFFFFFFF8000000L);
/* 1244 */     double b1Low = b1 - b1High;
/*      */ 
/*      */     
/* 1247 */     double prod1High = a1 * b1;
/* 1248 */     double prod1Low = a1Low * b1Low - prod1High - a1High * b1High - a1Low * b1High - a1High * b1Low;
/*      */ 
/*      */     
/* 1251 */     double a2High = Double.longBitsToDouble(Double.doubleToRawLongBits(a2) & 0xFFFFFFFFF8000000L);
/* 1252 */     double a2Low = a2 - a2High;
/* 1253 */     double b2High = Double.longBitsToDouble(Double.doubleToRawLongBits(b2) & 0xFFFFFFFFF8000000L);
/* 1254 */     double b2Low = b2 - b2High;
/*      */ 
/*      */     
/* 1257 */     double prod2High = a2 * b2;
/* 1258 */     double prod2Low = a2Low * b2Low - prod2High - a2High * b2High - a2Low * b2High - a2High * b2Low;
/*      */ 
/*      */     
/* 1261 */     double a3High = Double.longBitsToDouble(Double.doubleToRawLongBits(a3) & 0xFFFFFFFFF8000000L);
/* 1262 */     double a3Low = a3 - a3High;
/* 1263 */     double b3High = Double.longBitsToDouble(Double.doubleToRawLongBits(b3) & 0xFFFFFFFFF8000000L);
/* 1264 */     double b3Low = b3 - b3High;
/*      */ 
/*      */     
/* 1267 */     double prod3High = a3 * b3;
/* 1268 */     double prod3Low = a3Low * b3Low - prod3High - a3High * b3High - a3Low * b3High - a3High * b3Low;
/*      */ 
/*      */     
/* 1271 */     double a4High = Double.longBitsToDouble(Double.doubleToRawLongBits(a4) & 0xFFFFFFFFF8000000L);
/* 1272 */     double a4Low = a4 - a4High;
/* 1273 */     double b4High = Double.longBitsToDouble(Double.doubleToRawLongBits(b4) & 0xFFFFFFFFF8000000L);
/* 1274 */     double b4Low = b4 - b4High;
/*      */ 
/*      */     
/* 1277 */     double prod4High = a4 * b4;
/* 1278 */     double prod4Low = a4Low * b4Low - prod4High - a4High * b4High - a4Low * b4High - a4High * b4Low;
/*      */ 
/*      */     
/* 1281 */     double s12High = prod1High + prod2High;
/* 1282 */     double s12Prime = s12High - prod2High;
/* 1283 */     double s12Low = prod2High - s12High - s12Prime + prod1High - s12Prime;
/*      */ 
/*      */     
/* 1286 */     double s123High = s12High + prod3High;
/* 1287 */     double s123Prime = s123High - prod3High;
/* 1288 */     double s123Low = prod3High - s123High - s123Prime + s12High - s123Prime;
/*      */ 
/*      */     
/* 1291 */     double s1234High = s123High + prod4High;
/* 1292 */     double s1234Prime = s1234High - prod4High;
/* 1293 */     double s1234Low = prod4High - s1234High - s1234Prime + s123High - s1234Prime;
/*      */ 
/*      */ 
/*      */     
/* 1297 */     double result = s1234High + prod1Low + prod2Low + prod3Low + prod4Low + s12Low + s123Low + s1234Low;
/*      */     
/* 1299 */     if (Double.isNaN(result))
/*      */     {
/*      */       
/* 1302 */       result = a1 * b1 + a2 * b2 + a3 * b3 + a4 * b4;
/*      */     }
/*      */     
/* 1305 */     return result;
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
/*      */   public static boolean equals(float[] x, float[] y) {
/* 1319 */     if (x == null || y == null) {
/* 1320 */       return ((((x == null) ? 1 : 0) ^ ((y == null) ? 1 : 0)) == 0);
/*      */     }
/* 1322 */     if (x.length != y.length) {
/* 1323 */       return false;
/*      */     }
/* 1325 */     for (int i = 0; i < x.length; i++) {
/* 1326 */       if (!Precision.equals(x[i], y[i])) {
/* 1327 */         return false;
/*      */       }
/*      */     } 
/* 1330 */     return true;
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
/*      */   public static boolean equalsIncludingNaN(float[] x, float[] y) {
/* 1345 */     if (x == null || y == null) {
/* 1346 */       return ((((x == null) ? 1 : 0) ^ ((y == null) ? 1 : 0)) == 0);
/*      */     }
/* 1348 */     if (x.length != y.length) {
/* 1349 */       return false;
/*      */     }
/* 1351 */     for (int i = 0; i < x.length; i++) {
/* 1352 */       if (!Precision.equalsIncludingNaN(x[i], y[i])) {
/* 1353 */         return false;
/*      */       }
/*      */     } 
/* 1356 */     return true;
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
/*      */   public static boolean equals(double[] x, double[] y) {
/* 1370 */     if (x == null || y == null) {
/* 1371 */       return ((((x == null) ? 1 : 0) ^ ((y == null) ? 1 : 0)) == 0);
/*      */     }
/* 1373 */     if (x.length != y.length) {
/* 1374 */       return false;
/*      */     }
/* 1376 */     for (int i = 0; i < x.length; i++) {
/* 1377 */       if (!Precision.equals(x[i], y[i])) {
/* 1378 */         return false;
/*      */       }
/*      */     } 
/* 1381 */     return true;
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
/*      */   public static boolean equalsIncludingNaN(double[] x, double[] y) {
/* 1396 */     if (x == null || y == null) {
/* 1397 */       return ((((x == null) ? 1 : 0) ^ ((y == null) ? 1 : 0)) == 0);
/*      */     }
/* 1399 */     if (x.length != y.length) {
/* 1400 */       return false;
/*      */     }
/* 1402 */     for (int i = 0; i < x.length; i++) {
/* 1403 */       if (!Precision.equalsIncludingNaN(x[i], y[i])) {
/* 1404 */         return false;
/*      */       }
/*      */     } 
/* 1407 */     return true;
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
/*      */   public static double[] normalizeArray(double[] values, double normalizedSum) throws MathIllegalArgumentException, MathArithmeticException {
/* 1435 */     if (Double.isInfinite(normalizedSum)) {
/* 1436 */       throw new MathIllegalArgumentException(LocalizedFormats.NORMALIZE_INFINITE, new Object[0]);
/*      */     }
/* 1438 */     if (Double.isNaN(normalizedSum)) {
/* 1439 */       throw new MathIllegalArgumentException(LocalizedFormats.NORMALIZE_NAN, new Object[0]);
/*      */     }
/* 1441 */     double sum = 0.0D;
/* 1442 */     int len = values.length;
/* 1443 */     double[] out = new double[len]; int i;
/* 1444 */     for (i = 0; i < len; i++) {
/* 1445 */       if (Double.isInfinite(values[i])) {
/* 1446 */         throw new MathIllegalArgumentException(LocalizedFormats.INFINITE_ARRAY_ELEMENT, new Object[] { Double.valueOf(values[i]), Integer.valueOf(i) });
/*      */       }
/* 1448 */       if (!Double.isNaN(values[i])) {
/* 1449 */         sum += values[i];
/*      */       }
/*      */     } 
/* 1452 */     if (sum == 0.0D) {
/* 1453 */       throw new MathArithmeticException(LocalizedFormats.ARRAY_SUMS_TO_ZERO, new Object[0]);
/*      */     }
/* 1455 */     for (i = 0; i < len; i++) {
/* 1456 */       if (Double.isNaN(values[i])) {
/* 1457 */         out[i] = Double.NaN;
/*      */       } else {
/* 1459 */         out[i] = values[i] * normalizedSum / sum;
/*      */       } 
/*      */     } 
/* 1462 */     return out;
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
/*      */   public static <T> T[] buildArray(Field<T> field, int length) {
/* 1477 */     T[] array = (T[])Array.newInstance(field.getRuntimeClass(), length);
/* 1478 */     Arrays.fill((Object[])array, field.getZero());
/* 1479 */     return array;
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
/*      */   public static <T> T[][] buildArray(Field<T> field, int rows, int columns) {
/*      */     T[][] array;
/* 1497 */     if (columns < 0) {
/* 1498 */       T[] dummyRow = buildArray(field, 0);
/* 1499 */       array = (T[][])Array.newInstance(dummyRow.getClass(), rows);
/*      */     } else {
/* 1501 */       array = (T[][])Array.newInstance(field.getRuntimeClass(), new int[] { rows, columns });
/*      */ 
/*      */ 
/*      */       
/* 1505 */       for (int i = 0; i < rows; i++) {
/* 1506 */         Arrays.fill((Object[])array[i], field.getZero());
/*      */       }
/*      */     } 
/* 1509 */     return array;
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
/*      */   public static double[] convolve(double[] x, double[] h) throws NullArgumentException, NoDataException {
/* 1535 */     MathUtils.checkNotNull(x);
/* 1536 */     MathUtils.checkNotNull(h);
/*      */     
/* 1538 */     int xLen = x.length;
/* 1539 */     int hLen = h.length;
/*      */     
/* 1541 */     if (xLen == 0 || hLen == 0) {
/* 1542 */       throw new NoDataException();
/*      */     }
/*      */ 
/*      */     
/* 1546 */     int totalLength = xLen + hLen - 1;
/* 1547 */     double[] y = new double[totalLength];
/*      */ 
/*      */     
/* 1550 */     for (int n = 0; n < totalLength; n++) {
/* 1551 */       double yn = 0.0D;
/* 1552 */       int k = FastMath.max(0, n + 1 - xLen);
/* 1553 */       int j = n - k;
/* 1554 */       while (k < hLen && j >= 0) {
/* 1555 */         yn += x[j--] * h[k++];
/*      */       }
/* 1557 */       y[n] = yn;
/*      */     } 
/*      */     
/* 1560 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Position
/*      */   {
/* 1569 */     HEAD,
/*      */     
/* 1571 */     TAIL;
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
/*      */   public static void shuffle(int[] list, int start, Position pos) {
/* 1590 */     shuffle(list, start, pos, (RandomGenerator)new Well19937c());
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
/*      */   public static void shuffle(int[] list, int start, Position pos, RandomGenerator rng) {
/*      */     int i;
/* 1611 */     switch (pos) {
/*      */       case TAIL:
/* 1613 */         for (i = list.length - 1; i >= start; i--) {
/*      */           int target;
/* 1615 */           if (i == start) {
/* 1616 */             target = start;
/*      */           } else {
/*      */             
/* 1619 */             target = (new UniformIntegerDistribution(rng, start, i)).sample();
/*      */           } 
/* 1621 */           int temp = list[target];
/* 1622 */           list[target] = list[i];
/* 1623 */           list[i] = temp;
/*      */         } 
/*      */         return;
/*      */       
/*      */       case HEAD:
/* 1628 */         for (i = 0; i <= start; i++) {
/*      */           int target;
/* 1630 */           if (i == start) {
/* 1631 */             target = start;
/*      */           } else {
/*      */             
/* 1634 */             target = (new UniformIntegerDistribution(rng, i, start)).sample();
/*      */           } 
/* 1636 */           int temp = list[target];
/* 1637 */           list[target] = list[i];
/* 1638 */           list[i] = temp;
/*      */         } 
/*      */         return;
/*      */     } 
/*      */     
/* 1643 */     throw new MathInternalError();
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
/*      */   public static void shuffle(int[] list, RandomGenerator rng) {
/* 1657 */     shuffle(list, 0, Position.TAIL, rng);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void shuffle(int[] list) {
/* 1668 */     shuffle(list, (RandomGenerator)new Well19937c());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] natural(int n) {
/* 1679 */     return sequence(n, 0, 1);
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
/*      */   public static int[] sequence(int size, int start, int stride) {
/* 1697 */     int[] a = new int[size];
/* 1698 */     for (int i = 0; i < size; i++) {
/* 1699 */       a[i] = start + i * stride;
/*      */     }
/* 1701 */     return a;
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
/*      */   public static boolean verifyValues(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 1725 */     return verifyValues(values, begin, length, false);
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
/*      */   public static boolean verifyValues(double[] values, int begin, int length, boolean allowEmpty) throws MathIllegalArgumentException {
/* 1752 */     if (values == null) {
/* 1753 */       throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*      */     }
/*      */     
/* 1756 */     if (begin < 0) {
/* 1757 */       throw new NotPositiveException(LocalizedFormats.START_POSITION, Integer.valueOf(begin));
/*      */     }
/*      */     
/* 1760 */     if (length < 0) {
/* 1761 */       throw new NotPositiveException(LocalizedFormats.LENGTH, Integer.valueOf(length));
/*      */     }
/*      */     
/* 1764 */     if (begin + length > values.length) {
/* 1765 */       throw new NumberIsTooLargeException(LocalizedFormats.SUBARRAY_ENDS_AFTER_ARRAY_END, Integer.valueOf(begin + length), Integer.valueOf(values.length), true);
/*      */     }
/*      */ 
/*      */     
/* 1769 */     if (length == 0 && !allowEmpty) {
/* 1770 */       return false;
/*      */     }
/*      */     
/* 1773 */     return true;
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
/*      */   public static boolean verifyValues(double[] values, double[] weights, int begin, int length) throws MathIllegalArgumentException {
/* 1811 */     return verifyValues(values, weights, begin, length, false);
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
/*      */   public static boolean verifyValues(double[] values, double[] weights, int begin, int length, boolean allowEmpty) throws MathIllegalArgumentException {
/* 1850 */     if (weights == null || values == null) {
/* 1851 */       throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*      */     }
/*      */     
/* 1854 */     checkEqualLength(weights, values);
/*      */     
/* 1856 */     boolean containsPositiveWeight = false;
/* 1857 */     for (int i = begin; i < begin + length; i++) {
/* 1858 */       double weight = weights[i];
/* 1859 */       if (Double.isNaN(weight)) {
/* 1860 */         throw new MathIllegalArgumentException(LocalizedFormats.NAN_ELEMENT_AT_INDEX, new Object[] { Integer.valueOf(i) });
/*      */       }
/* 1862 */       if (Double.isInfinite(weight)) {
/* 1863 */         throw new MathIllegalArgumentException(LocalizedFormats.INFINITE_ARRAY_ELEMENT, new Object[] { Double.valueOf(weight), Integer.valueOf(i) });
/*      */       }
/* 1865 */       if (weight < 0.0D) {
/* 1866 */         throw new MathIllegalArgumentException(LocalizedFormats.NEGATIVE_ELEMENT_AT_INDEX, new Object[] { Integer.valueOf(i), Double.valueOf(weight) });
/*      */       }
/* 1868 */       if (!containsPositiveWeight && weight > 0.0D) {
/* 1869 */         containsPositiveWeight = true;
/*      */       }
/*      */     } 
/*      */     
/* 1873 */     if (!containsPositiveWeight) {
/* 1874 */       throw new MathIllegalArgumentException(LocalizedFormats.WEIGHT_AT_LEAST_ONE_NON_ZERO, new Object[0]);
/*      */     }
/*      */     
/* 1877 */     return verifyValues(values, begin, length, allowEmpty);
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
/*      */   public static double[] concatenate(double[]... x) {
/* 1892 */     int combinedLength = 0;
/* 1893 */     for (double[] a : x) {
/* 1894 */       combinedLength += a.length;
/*      */     }
/* 1896 */     int offset = 0;
/* 1897 */     int curLength = 0;
/* 1898 */     double[] combined = new double[combinedLength];
/* 1899 */     for (int i = 0; i < x.length; i++) {
/* 1900 */       curLength = (x[i]).length;
/* 1901 */       System.arraycopy(x[i], 0, combined, offset, curLength);
/* 1902 */       offset += curLength;
/*      */     } 
/* 1904 */     return combined;
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
/*      */   public static double[] unique(double[] data) {
/* 1922 */     TreeSet<Double> values = new TreeSet<Double>();
/* 1923 */     for (int i = 0; i < data.length; i++) {
/* 1924 */       values.add(Double.valueOf(data[i]));
/*      */     }
/* 1926 */     int count = values.size();
/* 1927 */     double[] out = new double[count];
/* 1928 */     Iterator<Double> iterator = values.iterator();
/* 1929 */     int j = 0;
/* 1930 */     while (iterator.hasNext()) {
/* 1931 */       out[count - ++j] = ((Double)iterator.next()).doubleValue();
/*      */     }
/* 1933 */     return out;
/*      */   }
/*      */   
/*      */   public static interface Function {
/*      */     double evaluate(double[] param1ArrayOfdouble);
/*      */     
/*      */     double evaluate(double[] param1ArrayOfdouble, int param1Int1, int param1Int2);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/MathArrays.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */