/*       */ package edu.mines.jtk.util;
/*       */ 
/*       */ import java.util.Random;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ public class ArrayMath
/*       */ {
/*       */   public static final double E = 2.718281828459045D;
/*       */   public static final float FLT_E = 2.7182817F;
/*       */   public static final double DBL_E = 2.718281828459045D;
/*       */   public static final double PI = 3.141592653589793D;
/*       */   public static final float FLT_PI = 3.1415927F;
/*       */   public static final double DBL_PI = 3.141592653589793D;
/*       */   public static final float FLT_MAX = 3.4028235E38F;
/*       */   public static final float FLT_MIN = 1.4E-45F;
/*       */   public static final float FLT_EPSILON = 1.1920929E-7F;
/*       */   public static final double DBL_MAX = 1.7976931348623157E308D;
/*       */   public static final double DBL_MIN = 4.9E-324D;
/*       */   public static final double DBL_EPSILON = 2.220446049250313E-16D;
/*       */   
/*       */   public static float sin(float x) {
/*   198 */     return (float)Math.sin(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double sin(double x) {
/*   207 */     return Math.sin(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float cos(float x) {
/*   216 */     return (float)Math.cos(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double cos(double x) {
/*   225 */     return Math.cos(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float tan(float x) {
/*   234 */     return (float)Math.tan(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double tan(double x) {
/*   243 */     return Math.tan(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float asin(float x) {
/*   254 */     return (float)Math.asin(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double asin(double x) {
/*   264 */     return Math.asin(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float acos(float x) {
/*   274 */     return (float)Math.acos(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double acos(double x) {
/*   284 */     return Math.acos(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float atan(float x) {
/*   294 */     return (float)Math.atan(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double atan(double x) {
/*   304 */     return Math.atan(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float atan2(float y, float x) {
/*   315 */     return (float)Math.atan2(y, x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double atan2(double y, double x) {
/*   326 */     return Math.atan2(y, x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float toRadians(float angdeg) {
/*   335 */     return (float)Math.toRadians(angdeg);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double toRadians(double angdeg) {
/*   344 */     return Math.toRadians(angdeg);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float toDegrees(float angrad) {
/*   353 */     return (float)Math.toDegrees(angrad);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double toDegrees(double angrad) {
/*   362 */     return Math.toDegrees(angrad);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float exp(float x) {
/*   371 */     return (float)Math.exp(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double exp(double x) {
/*   380 */     return Math.exp(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float log(float x) {
/*   389 */     return (float)Math.log(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double log(double x) {
/*   398 */     return Math.log(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float log10(float x) {
/*   407 */     return (float)Math.log10(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double log10(double x) {
/*   416 */     return Math.log10(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float sqrt(float x) {
/*   425 */     return (float)Math.sqrt(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double sqrt(double x) {
/*   434 */     return Math.sqrt(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float pow(float x, float y) {
/*   444 */     return (float)Math.pow(x, y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double pow(double x, double y) {
/*   454 */     return Math.pow(x, y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float sinh(float x) {
/*   463 */     return (float)Math.sinh(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double sinh(double x) {
/*   472 */     return Math.sinh(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float cosh(float x) {
/*   481 */     return (float)Math.cosh(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double cosh(double x) {
/*   490 */     return Math.cosh(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float tanh(float x) {
/*   499 */     return (float)Math.tanh(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double tanh(double x) {
/*   508 */     return Math.tanh(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float ceil(float x) {
/*   518 */     return (float)Math.ceil(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double ceil(double x) {
/*   528 */     return Math.ceil(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float floor(float x) {
/*   538 */     return (float)Math.floor(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double floor(double x) {
/*   548 */     return Math.floor(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float rint(float x) {
/*   558 */     return (float)Math.rint(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double rint(double x) {
/*   568 */     return Math.rint(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int round(float x) {
/*   578 */     return Math.round(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long round(double x) {
/*   588 */     return Math.round(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float signum(float x) {
/*   599 */     return (x > 0.0F) ? 1.0F : ((x < 0.0F) ? -1.0F : 0.0F);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double signum(double x) {
/*   610 */     return (x > 0.0D) ? 1.0D : ((x < 0.0D) ? -1.0D : 0.0D);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int abs(int x) {
/*   619 */     return (x >= 0) ? x : -x;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long abs(long x) {
/*   628 */     return (x >= 0L) ? x : -x;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float abs(float x) {
/*   640 */     return (x >= 0.0F) ? x : -x;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double abs(double x) {
/*   652 */     return (x >= 0.0D) ? x : -x;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int max(int a, int b) {
/*   662 */     return (a >= b) ? a : b;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int max(int a, int b, int c) {
/*   673 */     return max(a, max(b, c));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int max(int a, int b, int c, int d) {
/*   685 */     return max(a, max(b, max(c, d)));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long max(long a, long b) {
/*   695 */     return (a >= b) ? a : b;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long max(long a, long b, long c) {
/*   706 */     return max(a, max(b, c));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long max(long a, long b, long c, long d) {
/*   718 */     return max(a, max(b, max(c, d)));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float max(float a, float b) {
/*   728 */     return (a >= b) ? a : b;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float max(float a, float b, float c) {
/*   739 */     return max(a, max(b, c));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float max(float a, float b, float c, float d) {
/*   751 */     return max(a, max(b, max(c, d)));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double max(double a, double b) {
/*   761 */     return (a >= b) ? a : b;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double max(double a, double b, double c) {
/*   772 */     return max(a, max(b, c));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double max(double a, double b, double c, double d) {
/*   784 */     return max(a, max(b, max(c, d)));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int min(int a, int b) {
/*   794 */     return (a <= b) ? a : b;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int min(int a, int b, int c) {
/*   805 */     return min(a, min(b, c));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int min(int a, int b, int c, int d) {
/*   817 */     return min(a, min(b, min(c, d)));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long min(long a, long b) {
/*   827 */     return (a <= b) ? a : b;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long min(long a, long b, long c) {
/*   838 */     return min(a, min(b, c));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long min(long a, long b, long c, long d) {
/*   850 */     return min(a, min(b, min(c, d)));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float min(float a, float b) {
/*   860 */     return (a <= b) ? a : b;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float min(float a, float b, float c) {
/*   871 */     return min(a, min(b, c));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float min(float a, float b, float c, float d) {
/*   883 */     return min(a, min(b, min(c, d)));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double min(double a, double b) {
/*   893 */     return (a <= b) ? a : b;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double min(double a, double b, double c) {
/*   904 */     return min(a, min(b, c));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double min(double a, double b, double c, double d) {
/*   916 */     return min(a, min(b, min(c, d)));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double cbrt(double a) {
/*   927 */     return Math.cbrt(a);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float cbrt(float a) {
/*   936 */     return (float)Math.cbrt(a);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double IEEEremainder(double f1, double f2) {
/*   947 */     return Math.IEEEremainder(f1, f2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float IEEEremainder(float f1, float f2) {
/*   958 */     return (float)Math.IEEEremainder(f1, f2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double random() {
/*   967 */     return Math.random();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double randomDouble() {
/*   976 */     return Math.random();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float randomFloat() {
/*   985 */     return (float)Math.random();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double ulp(double d) {
/*   994 */     return Math.ulp(d);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float ulp(float d) {
/*  1003 */     return Math.ulp(d);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double hypot(double x, double y) {
/*  1014 */     return Math.hypot(x, y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float hypot(float x, float y) {
/*  1025 */     return (float)Math.hypot(x, y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double expm1(double x) {
/*  1034 */     return Math.expm1(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float expm1(float x) {
/*  1043 */     return (float)Math.expm1(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double log1p(double x) {
/*  1052 */     return Math.log1p(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float log1p(float x) {
/*  1061 */     return (float)Math.log1p(x);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[] zerobyte(int n1) {
/*  1077 */     return new byte[n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][] zerobyte(int n1, int n2) {
/*  1087 */     return new byte[n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][][] zerobyte(int n1, int n2, int n3) {
/*  1098 */     return new byte[n3][n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(byte[] rx) {
/*  1106 */     int n1 = rx.length;
/*  1107 */     for (int i1 = 0; i1 < n1; i1++) {
/*  1108 */       rx[i1] = 0;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(byte[][] rx) {
/*  1116 */     int n2 = rx.length;
/*  1117 */     for (int i2 = 0; i2 < n2; i2++) {
/*  1118 */       zero(rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(byte[][][] rx) {
/*  1126 */     int n3 = rx.length;
/*  1127 */     for (int i3 = 0; i3 < n3; i3++) {
/*  1128 */       zero(rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[] zeroshort(int n1) {
/*  1137 */     return new short[n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][] zeroshort(int n1, int n2) {
/*  1147 */     return new short[n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][][] zeroshort(int n1, int n2, int n3) {
/*  1158 */     return new short[n3][n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(short[] rx) {
/*  1166 */     int n1 = rx.length;
/*  1167 */     for (int i1 = 0; i1 < n1; i1++) {
/*  1168 */       rx[i1] = 0;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(short[][] rx) {
/*  1176 */     int n2 = rx.length;
/*  1177 */     for (int i2 = 0; i2 < n2; i2++) {
/*  1178 */       zero(rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(short[][][] rx) {
/*  1186 */     int n3 = rx.length;
/*  1187 */     for (int i3 = 0; i3 < n3; i3++) {
/*  1188 */       zero(rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] zeroint(int n1) {
/*  1197 */     return new int[n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] zeroint(int n1, int n2) {
/*  1207 */     return new int[n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][][] zeroint(int n1, int n2, int n3) {
/*  1218 */     return new int[n3][n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(int[] rx) {
/*  1226 */     int n1 = rx.length;
/*  1227 */     for (int i1 = 0; i1 < n1; i1++) {
/*  1228 */       rx[i1] = 0;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(int[][] rx) {
/*  1236 */     int n2 = rx.length;
/*  1237 */     for (int i2 = 0; i2 < n2; i2++) {
/*  1238 */       zero(rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(int[][][] rx) {
/*  1246 */     int n3 = rx.length;
/*  1247 */     for (int i3 = 0; i3 < n3; i3++) {
/*  1248 */       zero(rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] zerolong(int n1) {
/*  1257 */     return new long[n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] zerolong(int n1, int n2) {
/*  1267 */     return new long[n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][][] zerolong(int n1, int n2, int n3) {
/*  1278 */     return new long[n3][n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(long[] rx) {
/*  1286 */     int n1 = rx.length;
/*  1287 */     for (int i1 = 0; i1 < n1; i1++) {
/*  1288 */       rx[i1] = 0L;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(long[][] rx) {
/*  1296 */     int n2 = rx.length;
/*  1297 */     for (int i2 = 0; i2 < n2; i2++) {
/*  1298 */       zero(rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(long[][][] rx) {
/*  1306 */     int n3 = rx.length;
/*  1307 */     for (int i3 = 0; i3 < n3; i3++) {
/*  1308 */       zero(rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] zerofloat(int n1) {
/*  1317 */     return new float[n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] zerofloat(int n1, int n2) {
/*  1327 */     return new float[n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] zerofloat(int n1, int n2, int n3) {
/*  1338 */     return new float[n3][n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(float[] rx) {
/*  1346 */     int n1 = rx.length;
/*  1347 */     for (int i1 = 0; i1 < n1; i1++) {
/*  1348 */       rx[i1] = 0.0F;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(float[][] rx) {
/*  1356 */     int n2 = rx.length;
/*  1357 */     for (int i2 = 0; i2 < n2; i2++) {
/*  1358 */       zero(rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(float[][][] rx) {
/*  1366 */     int n3 = rx.length;
/*  1367 */     for (int i3 = 0; i3 < n3; i3++) {
/*  1368 */       zero(rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] czerofloat(int n1) {
/*  1377 */     return new float[2 * n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] czerofloat(int n1, int n2) {
/*  1387 */     return new float[n2][2 * n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] czerofloat(int n1, int n2, int n3) {
/*  1398 */     return new float[n3][n2][2 * n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void czero(float[] cx) {
/*  1406 */     zero(cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void czero(float[][] cx) {
/*  1414 */     zero(cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void czero(float[][][] cx) {
/*  1422 */     zero(cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] zerodouble(int n1) {
/*  1431 */     return new double[n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] zerodouble(int n1, int n2) {
/*  1441 */     return new double[n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] zerodouble(int n1, int n2, int n3) {
/*  1452 */     return new double[n3][n2][n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(double[] rx) {
/*  1460 */     int n1 = rx.length;
/*  1461 */     for (int i1 = 0; i1 < n1; i1++) {
/*  1462 */       rx[i1] = 0.0D;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(double[][] rx) {
/*  1470 */     int n2 = rx.length;
/*  1471 */     for (int i2 = 0; i2 < n2; i2++) {
/*  1472 */       zero(rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void zero(double[][][] rx) {
/*  1480 */     int n3 = rx.length;
/*  1481 */     for (int i3 = 0; i3 < n3; i3++) {
/*  1482 */       zero(rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] czerodouble(int n1) {
/*  1491 */     return new double[2 * n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] czerodouble(int n1, int n2) {
/*  1501 */     return new double[n2][2 * n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] czerodouble(int n1, int n2, int n3) {
/*  1512 */     return new double[n3][n2][2 * n1];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void czero(double[] cx) {
/*  1520 */     zero(cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void czero(double[][] cx) {
/*  1528 */     zero(cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void czero(double[][][] cx) {
/*  1536 */     zero(cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] randint(int n1) {
/*  1548 */     return randint(_random, n1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] randint(int n1, int n2) {
/*  1558 */     return randint(_random, n1, n2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][][] randint(int n1, int n2, int n3) {
/*  1569 */     return randint(_random, n1, n2, n3);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] randint(Random random, int n1) {
/*  1579 */     int[] rx = new int[n1];
/*  1580 */     rand(random, rx);
/*  1581 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] randint(Random random, int n1, int n2) {
/*  1592 */     int[][] rx = new int[n2][n1];
/*  1593 */     rand(random, rx);
/*  1594 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][][] randint(Random random, int n1, int n2, int n3) {
/*  1606 */     int[][][] rx = new int[n3][n2][n1];
/*  1607 */     rand(random, rx);
/*  1608 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(int[] rx) {
/*  1616 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(int[][] rx) {
/*  1624 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(int[][][] rx) {
/*  1632 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, int[] rx) {
/*  1641 */     int n1 = rx.length;
/*  1642 */     for (int i1 = 0; i1 < n1; i1++) {
/*  1643 */       rx[i1] = random.nextInt();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, int[][] rx) {
/*  1652 */     int n2 = rx.length;
/*  1653 */     for (int i2 = 0; i2 < n2; i2++) {
/*  1654 */       rand(random, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, int[][][] rx) {
/*  1663 */     int n3 = rx.length;
/*  1664 */     for (int i3 = 0; i3 < n3; i3++) {
/*  1665 */       rand(random, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] randlong(int n1) {
/*  1674 */     return randlong(_random, n1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] randlong(int n1, int n2) {
/*  1684 */     return randlong(_random, n1, n2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][][] randlong(int n1, int n2, int n3) {
/*  1695 */     return randlong(_random, n1, n2, n3);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] randlong(Random random, int n1) {
/*  1705 */     long[] rx = new long[n1];
/*  1706 */     rand(random, rx);
/*  1707 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] randlong(Random random, int n1, int n2) {
/*  1718 */     long[][] rx = new long[n2][n1];
/*  1719 */     rand(random, rx);
/*  1720 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][][] randlong(Random random, int n1, int n2, int n3) {
/*  1732 */     long[][][] rx = new long[n3][n2][n1];
/*  1733 */     rand(random, rx);
/*  1734 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(long[] rx) {
/*  1742 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(long[][] rx) {
/*  1750 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(long[][][] rx) {
/*  1758 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, long[] rx) {
/*  1767 */     int n1 = rx.length;
/*  1768 */     for (int i1 = 0; i1 < n1; i1++) {
/*  1769 */       rx[i1] = random.nextLong();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, long[][] rx) {
/*  1778 */     int n2 = rx.length;
/*  1779 */     for (int i2 = 0; i2 < n2; i2++) {
/*  1780 */       rand(random, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, long[][][] rx) {
/*  1789 */     int n3 = rx.length;
/*  1790 */     for (int i3 = 0; i3 < n3; i3++) {
/*  1791 */       rand(random, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] randfloat(int n1) {
/*  1800 */     return randfloat(_random, n1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] randfloat(int n1, int n2) {
/*  1810 */     return randfloat(_random, n1, n2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] randfloat(int n1, int n2, int n3) {
/*  1821 */     return randfloat(_random, n1, n2, n3);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] randfloat(Random random, int n1) {
/*  1831 */     float[] rx = new float[n1];
/*  1832 */     rand(random, rx);
/*  1833 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] randfloat(Random random, int n1, int n2) {
/*  1844 */     float[][] rx = new float[n2][n1];
/*  1845 */     rand(random, rx);
/*  1846 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] randfloat(Random random, int n1, int n2, int n3) {
/*  1858 */     float[][][] rx = new float[n3][n2][n1];
/*  1859 */     rand(random, rx);
/*  1860 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(float[] rx) {
/*  1868 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(float[][] rx) {
/*  1876 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(float[][][] rx) {
/*  1884 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, float[] rx) {
/*  1893 */     int n1 = rx.length;
/*  1894 */     for (int i1 = 0; i1 < n1; i1++) {
/*  1895 */       rx[i1] = random.nextFloat();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, float[][] rx) {
/*  1904 */     int n2 = rx.length;
/*  1905 */     for (int i2 = 0; i2 < n2; i2++) {
/*  1906 */       rand(random, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, float[][][] rx) {
/*  1915 */     int n3 = rx.length;
/*  1916 */     for (int i3 = 0; i3 < n3; i3++) {
/*  1917 */       rand(random, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] crandfloat(int n1) {
/*  1926 */     return crandfloat(_random, n1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] crandfloat(int n1, int n2) {
/*  1936 */     return crandfloat(_random, n1, n2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] crandfloat(int n1, int n2, int n3) {
/*  1947 */     return crandfloat(_random, n1, n2, n3);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] crandfloat(Random random, int n1) {
/*  1957 */     float[] cx = new float[2 * n1];
/*  1958 */     crand(random, cx);
/*  1959 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] crandfloat(Random random, int n1, int n2) {
/*  1970 */     float[][] cx = new float[n2][2 * n1];
/*  1971 */     crand(random, cx);
/*  1972 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] crandfloat(Random random, int n1, int n2, int n3) {
/*  1984 */     float[][][] cx = new float[n3][n2][2 * n1];
/*  1985 */     crand(random, cx);
/*  1986 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(float[] cx) {
/*  1994 */     crand(_random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(float[][] cx) {
/*  2002 */     crand(_random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(float[][][] cx) {
/*  2010 */     crand(_random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(Random random, float[] cx) {
/*  2019 */     rand(random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(Random random, float[][] cx) {
/*  2028 */     rand(random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(Random random, float[][][] cx) {
/*  2037 */     rand(random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] randdouble(int n1) {
/*  2046 */     return randdouble(_random, n1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] randdouble(int n1, int n2) {
/*  2056 */     return randdouble(_random, n1, n2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] randdouble(int n1, int n2, int n3) {
/*  2067 */     return randdouble(_random, n1, n2, n3);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] randdouble(Random random, int n1) {
/*  2077 */     double[] rx = new double[n1];
/*  2078 */     rand(random, rx);
/*  2079 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] randdouble(Random random, int n1, int n2) {
/*  2090 */     double[][] rx = new double[n2][n1];
/*  2091 */     rand(random, rx);
/*  2092 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] randdouble(Random random, int n1, int n2, int n3) {
/*  2104 */     double[][][] rx = new double[n3][n2][n1];
/*  2105 */     rand(random, rx);
/*  2106 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(double[] rx) {
/*  2114 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(double[][] rx) {
/*  2122 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(double[][][] rx) {
/*  2130 */     rand(_random, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, double[] rx) {
/*  2139 */     int n1 = rx.length;
/*  2140 */     for (int i1 = 0; i1 < n1; i1++) {
/*  2141 */       rx[i1] = random.nextDouble();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, double[][] rx) {
/*  2150 */     int n2 = rx.length;
/*  2151 */     for (int i2 = 0; i2 < n2; i2++) {
/*  2152 */       rand(random, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void rand(Random random, double[][][] rx) {
/*  2161 */     int n3 = rx.length;
/*  2162 */     for (int i3 = 0; i3 < n3; i3++) {
/*  2163 */       rand(random, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] cranddouble(int n1) {
/*  2172 */     return cranddouble(_random, n1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] cranddouble(int n1, int n2) {
/*  2182 */     return cranddouble(_random, n1, n2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] cranddouble(int n1, int n2, int n3) {
/*  2193 */     return cranddouble(_random, n1, n2, n3);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] cranddouble(Random random, int n1) {
/*  2203 */     double[] cx = new double[2 * n1];
/*  2204 */     crand(random, cx);
/*  2205 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] cranddouble(Random random, int n1, int n2) {
/*  2216 */     double[][] cx = new double[n2][2 * n1];
/*  2217 */     crand(random, cx);
/*  2218 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] cranddouble(Random random, int n1, int n2, int n3) {
/*  2230 */     double[][][] cx = new double[n3][n2][2 * n1];
/*  2231 */     crand(random, cx);
/*  2232 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(double[] cx) {
/*  2240 */     crand(_random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(double[][] cx) {
/*  2248 */     crand(_random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(double[][][] cx) {
/*  2256 */     crand(_random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(Random random, double[] cx) {
/*  2265 */     rand(random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(Random random, double[][] cx) {
/*  2274 */     rand(random, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void crand(Random random, double[][][] cx) {
/*  2283 */     rand(random, cx);
/*       */   }
/*  2285 */   private static Random _random = new Random();
/*       */ 
/*       */ 
/*       */   
/*       */   private static final int NSMALL_SORT = 7;
/*       */ 
/*       */   
/*       */   private static final int NLARGE_SORT = 40;
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[] fillbyte(byte ra, int n1) {
/*  2297 */     byte[] rx = new byte[n1];
/*  2298 */     fill(ra, rx);
/*  2299 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][] fillbyte(byte ra, int n1, int n2) {
/*  2310 */     byte[][] rx = new byte[n2][n1];
/*  2311 */     fill(ra, rx);
/*  2312 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][][] fillbyte(byte ra, int n1, int n2, int n3) {
/*  2324 */     byte[][][] rx = new byte[n3][n2][n1];
/*  2325 */     fill(ra, rx);
/*  2326 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(byte ra, byte[] rx) {
/*  2335 */     int n1 = rx.length;
/*  2336 */     for (int i1 = 0; i1 < n1; i1++) {
/*  2337 */       rx[i1] = ra;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(byte ra, byte[][] rx) {
/*  2346 */     int n2 = rx.length;
/*  2347 */     for (int i2 = 0; i2 < n2; i2++) {
/*  2348 */       fill(ra, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(byte ra, byte[][][] rx) {
/*  2357 */     int n3 = rx.length;
/*  2358 */     for (int i3 = 0; i3 < n3; i3++) {
/*  2359 */       fill(ra, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[] fillshort(short ra, int n1) {
/*  2369 */     short[] rx = new short[n1];
/*  2370 */     fill(ra, rx);
/*  2371 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][] fillshort(short ra, int n1, int n2) {
/*  2382 */     short[][] rx = new short[n2][n1];
/*  2383 */     fill(ra, rx);
/*  2384 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][][] fillshort(short ra, int n1, int n2, int n3) {
/*  2396 */     short[][][] rx = new short[n3][n2][n1];
/*  2397 */     fill(ra, rx);
/*  2398 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(short ra, short[] rx) {
/*  2407 */     int n1 = rx.length;
/*  2408 */     for (int i1 = 0; i1 < n1; i1++) {
/*  2409 */       rx[i1] = ra;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(short ra, short[][] rx) {
/*  2418 */     int n2 = rx.length;
/*  2419 */     for (int i2 = 0; i2 < n2; i2++) {
/*  2420 */       fill(ra, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(short ra, short[][][] rx) {
/*  2429 */     int n3 = rx.length;
/*  2430 */     for (int i3 = 0; i3 < n3; i3++) {
/*  2431 */       fill(ra, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] fillint(int ra, int n1) {
/*  2440 */     int[] rx = new int[n1];
/*  2441 */     fill(ra, rx);
/*  2442 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] fillint(int ra, int n1, int n2) {
/*  2452 */     int[][] rx = new int[n2][n1];
/*  2453 */     fill(ra, rx);
/*  2454 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][][] fillint(int ra, int n1, int n2, int n3) {
/*  2465 */     int[][][] rx = new int[n3][n2][n1];
/*  2466 */     fill(ra, rx);
/*  2467 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(int ra, int[] rx) {
/*  2476 */     int n1 = rx.length;
/*  2477 */     for (int i1 = 0; i1 < n1; i1++) {
/*  2478 */       rx[i1] = ra;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(int ra, int[][] rx) {
/*  2487 */     int n2 = rx.length;
/*  2488 */     for (int i2 = 0; i2 < n2; i2++) {
/*  2489 */       fill(ra, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(int ra, int[][][] rx) {
/*  2498 */     int n3 = rx.length;
/*  2499 */     for (int i3 = 0; i3 < n3; i3++) {
/*  2500 */       fill(ra, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] filllong(long ra, int n1) {
/*  2509 */     long[] rx = new long[n1];
/*  2510 */     fill(ra, rx);
/*  2511 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] filllong(long ra, int n1, int n2) {
/*  2521 */     long[][] rx = new long[n2][n1];
/*  2522 */     fill(ra, rx);
/*  2523 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][][] filllong(long ra, int n1, int n2, int n3) {
/*  2534 */     long[][][] rx = new long[n3][n2][n1];
/*  2535 */     fill(ra, rx);
/*  2536 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(long ra, long[] rx) {
/*  2545 */     int n1 = rx.length;
/*  2546 */     for (int i1 = 0; i1 < n1; i1++) {
/*  2547 */       rx[i1] = ra;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(long ra, long[][] rx) {
/*  2556 */     int n2 = rx.length;
/*  2557 */     for (int i2 = 0; i2 < n2; i2++) {
/*  2558 */       fill(ra, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(long ra, long[][][] rx) {
/*  2567 */     int n3 = rx.length;
/*  2568 */     for (int i3 = 0; i3 < n3; i3++) {
/*  2569 */       fill(ra, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] fillfloat(float ra, int n1) {
/*  2578 */     float[] rx = new float[n1];
/*  2579 */     fill(ra, rx);
/*  2580 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] fillfloat(float ra, int n1, int n2) {
/*  2590 */     float[][] rx = new float[n2][n1];
/*  2591 */     fill(ra, rx);
/*  2592 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] fillfloat(float ra, int n1, int n2, int n3) {
/*  2603 */     float[][][] rx = new float[n3][n2][n1];
/*  2604 */     fill(ra, rx);
/*  2605 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(float ra, float[] rx) {
/*  2614 */     int n1 = rx.length;
/*  2615 */     for (int i1 = 0; i1 < n1; i1++) {
/*  2616 */       rx[i1] = ra;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(float ra, float[][] rx) {
/*  2625 */     int n2 = rx.length;
/*  2626 */     for (int i2 = 0; i2 < n2; i2++) {
/*  2627 */       fill(ra, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(float ra, float[][][] rx) {
/*  2636 */     int n3 = rx.length;
/*  2637 */     for (int i3 = 0; i3 < n3; i3++) {
/*  2638 */       fill(ra, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] cfillfloat(Cfloat ca, int n1) {
/*  2647 */     float[] cx = new float[2 * n1];
/*  2648 */     cfill(ca, cx);
/*  2649 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] cfillfloat(Cfloat ca, int n1, int n2) {
/*  2659 */     float[][] cx = new float[n2][2 * n1];
/*  2660 */     cfill(ca, cx);
/*  2661 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] cfillfloat(Cfloat ca, int n1, int n2, int n3) {
/*  2672 */     float[][][] cx = new float[n3][n2][2 * n1];
/*  2673 */     cfill(ca, cx);
/*  2674 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cfill(Cfloat ca, float[] cx) {
/*  2683 */     int n1 = cx.length / 2;
/*  2684 */     float ar = ca.r;
/*  2685 */     float ai = ca.i;
/*  2686 */     for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/*  2687 */       cx[ir] = ar;
/*  2688 */       cx[ii] = ai;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cfill(Cfloat ca, float[][] cx) {
/*  2698 */     int n2 = cx.length;
/*  2699 */     for (int i2 = 0; i2 < n2; i2++) {
/*  2700 */       cfill(ca, cx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cfill(Cfloat ca, float[][][] cx) {
/*  2709 */     int n3 = cx.length;
/*  2710 */     for (int i3 = 0; i3 < n3; i3++) {
/*  2711 */       cfill(ca, cx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] filldouble(double ra, int n1) {
/*  2720 */     double[] rx = new double[n1];
/*  2721 */     fill(ra, rx);
/*  2722 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] filldouble(double ra, int n1, int n2) {
/*  2732 */     double[][] rx = new double[n2][n1];
/*  2733 */     fill(ra, rx);
/*  2734 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] filldouble(double ra, int n1, int n2, int n3) {
/*  2745 */     double[][][] rx = new double[n3][n2][n1];
/*  2746 */     fill(ra, rx);
/*  2747 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(double ra, double[] rx) {
/*  2756 */     int n1 = rx.length;
/*  2757 */     for (int i1 = 0; i1 < n1; i1++) {
/*  2758 */       rx[i1] = ra;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(double ra, double[][] rx) {
/*  2767 */     int n2 = rx.length;
/*  2768 */     for (int i2 = 0; i2 < n2; i2++) {
/*  2769 */       fill(ra, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void fill(double ra, double[][][] rx) {
/*  2778 */     int n3 = rx.length;
/*  2779 */     for (int i3 = 0; i3 < n3; i3++) {
/*  2780 */       fill(ra, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] cfilldouble(Cdouble ca, int n1) {
/*  2789 */     double[] cx = new double[2 * n1];
/*  2790 */     cfill(ca, cx);
/*  2791 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] cfilldouble(Cdouble ca, int n1, int n2) {
/*  2801 */     double[][] cx = new double[n2][2 * n1];
/*  2802 */     cfill(ca, cx);
/*  2803 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] cfilldouble(Cdouble ca, int n1, int n2, int n3) {
/*  2814 */     double[][][] cx = new double[n3][n2][2 * n1];
/*  2815 */     cfill(ca, cx);
/*  2816 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cfill(Cdouble ca, double[] cx) {
/*  2825 */     int n1 = cx.length / 2;
/*  2826 */     double ar = ca.r;
/*  2827 */     double ai = ca.i;
/*  2828 */     for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/*  2829 */       cx[ir] = ar;
/*  2830 */       cx[ii] = ai;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cfill(Cdouble ca, double[][] cx) {
/*  2840 */     int n2 = cx.length;
/*  2841 */     for (int i2 = 0; i2 < n2; i2++) {
/*  2842 */       cfill(ca, cx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cfill(Cdouble ca, double[][][] cx) {
/*  2851 */     int n3 = cx.length;
/*  2852 */     for (int i3 = 0; i3 < n3; i3++) {
/*  2853 */       cfill(ca, cx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[] rampbyte(byte ra, byte rb1, int n1) {
/*  2867 */     byte[] rx = new byte[n1];
/*  2868 */     ramp(ra, rb1, rx);
/*  2869 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][] rampbyte(byte ra, byte rb1, byte rb2, int n1, int n2) {
/*  2883 */     byte[][] rx = new byte[n2][n1];
/*  2884 */     ramp(ra, rb1, rb2, rx);
/*  2885 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][][] rampbyte(byte ra, byte rb1, byte rb2, byte rb3, int n1, int n2, int n3) {
/*  2901 */     byte[][][] rx = new byte[n3][n2][n1];
/*  2902 */     ramp(ra, rb1, rb2, rb3, rx);
/*  2903 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(byte ra, byte rb1, byte[] rx) {
/*  2914 */     int n1 = rx.length;
/*  2915 */     for (int i1 = 0; i1 < n1; i1++) {
/*  2916 */       rx[i1] = (byte)(ra + rb1 * i1);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(byte ra, byte rb1, byte rb2, byte[][] rx) {
/*  2928 */     int n2 = rx.length;
/*  2929 */     for (int i2 = 0; i2 < n2; i2++) {
/*  2930 */       ramp((byte)(ra + rb2 * i2), rb1, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(byte ra, byte rb1, byte rb2, byte rb3, byte[][][] rx) {
/*  2944 */     int n3 = rx.length;
/*  2945 */     for (int i3 = 0; i3 < n3; i3++) {
/*  2946 */       ramp((byte)(ra + rb3 * i3), rb1, rb2, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[] rampshort(short ra, short rb1, int n1) {
/*  2957 */     short[] rx = new short[n1];
/*  2958 */     ramp(ra, rb1, rx);
/*  2959 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][] rampshort(short ra, short rb1, short rb2, int n1, int n2) {
/*  2973 */     short[][] rx = new short[n2][n1];
/*  2974 */     ramp(ra, rb1, rb2, rx);
/*  2975 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][][] rampshort(short ra, short rb1, short rb2, short rb3, int n1, int n2, int n3) {
/*  2991 */     short[][][] rx = new short[n3][n2][n1];
/*  2992 */     ramp(ra, rb1, rb2, rb3, rx);
/*  2993 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(short ra, short rb1, short[] rx) {
/*  3004 */     int n1 = rx.length;
/*  3005 */     for (int i1 = 0; i1 < n1; i1++) {
/*  3006 */       rx[i1] = (short)(ra + rb1 * i1);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(short ra, short rb1, short rb2, short[][] rx) {
/*  3018 */     int n2 = rx.length;
/*  3019 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3020 */       ramp((short)(ra + rb2 * i2), rb1, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(short ra, short rb1, short rb2, short rb3, short[][][] rx) {
/*  3034 */     int n3 = rx.length;
/*  3035 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3036 */       ramp((short)(ra + rb3 * i3), rb1, rb2, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] rampint(int ra, int rb1, int n1) {
/*  3047 */     int[] rx = new int[n1];
/*  3048 */     ramp(ra, rb1, rx);
/*  3049 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] rampint(int ra, int rb1, int rb2, int n1, int n2) {
/*  3063 */     int[][] rx = new int[n2][n1];
/*  3064 */     ramp(ra, rb1, rb2, rx);
/*  3065 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][][] rampint(int ra, int rb1, int rb2, int rb3, int n1, int n2, int n3) {
/*  3081 */     int[][][] rx = new int[n3][n2][n1];
/*  3082 */     ramp(ra, rb1, rb2, rb3, rx);
/*  3083 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(int ra, int rb1, int[] rx) {
/*  3094 */     int n1 = rx.length;
/*  3095 */     for (int i1 = 0; i1 < n1; i1++) {
/*  3096 */       rx[i1] = ra + rb1 * i1;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(int ra, int rb1, int rb2, int[][] rx) {
/*  3108 */     int n2 = rx.length;
/*  3109 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3110 */       ramp(ra + rb2 * i2, rb1, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(int ra, int rb1, int rb2, int rb3, int[][][] rx) {
/*  3124 */     int n3 = rx.length;
/*  3125 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3126 */       ramp(ra + rb3 * i3, rb1, rb2, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] ramplong(long ra, long rb1, int n1) {
/*  3137 */     long[] rx = new long[n1];
/*  3138 */     ramp(ra, rb1, rx);
/*  3139 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] ramplong(long ra, long rb1, long rb2, int n1, int n2) {
/*  3153 */     long[][] rx = new long[n2][n1];
/*  3154 */     ramp(ra, rb1, rb2, rx);
/*  3155 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][][] ramplong(long ra, long rb1, long rb2, long rb3, int n1, int n2, int n3) {
/*  3171 */     long[][][] rx = new long[n3][n2][n1];
/*  3172 */     ramp(ra, rb1, rb2, rb3, rx);
/*  3173 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(long ra, long rb1, long[] rx) {
/*  3184 */     int n1 = rx.length;
/*  3185 */     for (int i1 = 0; i1 < n1; i1++) {
/*  3186 */       rx[i1] = ra + rb1 * i1;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(long ra, long rb1, long rb2, long[][] rx) {
/*  3198 */     int n2 = rx.length;
/*  3199 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3200 */       ramp(ra + rb2 * i2, rb1, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(long ra, long rb1, long rb2, long rb3, long[][][] rx) {
/*  3214 */     int n3 = rx.length;
/*  3215 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3216 */       ramp(ra + rb3 * i3, rb1, rb2, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] rampfloat(float ra, float rb1, int n1) {
/*  3227 */     float[] rx = new float[n1];
/*  3228 */     ramp(ra, rb1, rx);
/*  3229 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] rampfloat(float ra, float rb1, float rb2, int n1, int n2) {
/*  3243 */     float[][] rx = new float[n2][n1];
/*  3244 */     ramp(ra, rb1, rb2, rx);
/*  3245 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] rampfloat(float ra, float rb1, float rb2, float rb3, int n1, int n2, int n3) {
/*  3261 */     float[][][] rx = new float[n3][n2][n1];
/*  3262 */     ramp(ra, rb1, rb2, rb3, rx);
/*  3263 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(float ra, float rb1, float[] rx) {
/*  3274 */     int n1 = rx.length;
/*  3275 */     for (int i1 = 0; i1 < n1; i1++) {
/*  3276 */       rx[i1] = ra + rb1 * i1;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(float ra, float rb1, float rb2, float[][] rx) {
/*  3288 */     int n2 = rx.length;
/*  3289 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3290 */       ramp(ra + rb2 * i2, rb1, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(float ra, float rb1, float rb2, float rb3, float[][][] rx) {
/*  3304 */     int n3 = rx.length;
/*  3305 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3306 */       ramp(ra + rb3 * i3, rb1, rb2, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] crampfloat(Cfloat ca, Cfloat cb1, int n1) {
/*  3317 */     float[] cx = new float[2 * n1];
/*  3318 */     cramp(ca, cb1, cx);
/*  3319 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] crampfloat(Cfloat ca, Cfloat cb1, Cfloat cb2, int n1, int n2) {
/*  3333 */     float[][] cx = new float[n2][2 * n1];
/*  3334 */     cramp(ca, cb1, cb2, cx);
/*  3335 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] crampfloat(Cfloat ca, Cfloat cb1, Cfloat cb2, Cfloat cb3, int n1, int n2, int n3) {
/*  3351 */     float[][][] cx = new float[n3][n2][2 * n1];
/*  3352 */     cramp(ca, cb1, cb2, cb3, cx);
/*  3353 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cramp(Cfloat ca, Cfloat cb1, float[] cx) {
/*  3364 */     int n1 = cx.length / 2;
/*  3365 */     float ar = ca.r;
/*  3366 */     float ai = ca.i;
/*  3367 */     float br = cb1.r;
/*  3368 */     float bi = cb1.i;
/*  3369 */     for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/*  3370 */       cx[ir] = ar + br * i1;
/*  3371 */       cx[ii] = ai + bi * i1;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cramp(Cfloat ca, Cfloat cb1, Cfloat cb2, float[][] cx) {
/*  3384 */     int n2 = cx.length;
/*  3385 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3386 */       cramp(ca.plus(cb2.times(i2)), cb1, cx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cramp(Cfloat ca, Cfloat cb1, Cfloat cb2, Cfloat cb3, float[][][] cx) {
/*  3400 */     int n3 = cx.length;
/*  3401 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3402 */       cramp(ca.plus(cb3.times(i3)), cb1, cb2, cx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] rampdouble(double ra, double rb1, int n1) {
/*  3413 */     double[] rx = new double[n1];
/*  3414 */     ramp(ra, rb1, rx);
/*  3415 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] rampdouble(double ra, double rb1, double rb2, int n1, int n2) {
/*  3429 */     double[][] rx = new double[n2][n1];
/*  3430 */     ramp(ra, rb1, rb2, rx);
/*  3431 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] rampdouble(double ra, double rb1, double rb2, double rb3, int n1, int n2, int n3) {
/*  3447 */     double[][][] rx = new double[n3][n2][n1];
/*  3448 */     ramp(ra, rb1, rb2, rb3, rx);
/*  3449 */     return rx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(double ra, double rb1, double[] rx) {
/*  3460 */     int n1 = rx.length;
/*  3461 */     for (int i1 = 0; i1 < n1; i1++) {
/*  3462 */       rx[i1] = ra + rb1 * i1;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(double ra, double rb1, double rb2, double[][] rx) {
/*  3474 */     int n2 = rx.length;
/*  3475 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3476 */       ramp(ra + rb2 * i2, rb1, rx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ramp(double ra, double rb1, double rb2, double rb3, double[][][] rx) {
/*  3490 */     int n3 = rx.length;
/*  3491 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3492 */       ramp(ra + rb3 * i3, rb1, rb2, rx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] crampdouble(Cdouble ca, Cdouble cb1, int n1) {
/*  3503 */     double[] cx = new double[2 * n1];
/*  3504 */     cramp(ca, cb1, cx);
/*  3505 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] crampdouble(Cdouble ca, Cdouble cb1, Cdouble cb2, int n1, int n2) {
/*  3519 */     double[][] cx = new double[n2][2 * n1];
/*  3520 */     cramp(ca, cb1, cb2, cx);
/*  3521 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] crampdouble(Cdouble ca, Cdouble cb1, Cdouble cb2, Cdouble cb3, int n1, int n2, int n3) {
/*  3537 */     double[][][] cx = new double[n3][n2][2 * n1];
/*  3538 */     cramp(ca, cb1, cb2, cb3, cx);
/*  3539 */     return cx;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cramp(Cdouble ca, Cdouble cb1, double[] cx) {
/*  3550 */     int n1 = cx.length / 2;
/*  3551 */     double ar = ca.r;
/*  3552 */     double ai = ca.i;
/*  3553 */     double br = cb1.r;
/*  3554 */     double bi = cb1.i;
/*  3555 */     for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/*  3556 */       cx[ir] = ar + br * i1;
/*  3557 */       cx[ii] = ai + bi * i1;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cramp(Cdouble ca, Cdouble cb1, Cdouble cb2, double[][] cx) {
/*  3570 */     int n2 = cx.length;
/*  3571 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3572 */       cramp(ca.plus(cb2.times(i2)), cb1, cx[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void cramp(Cdouble ca, Cdouble cb1, Cdouble cb2, Cdouble cb3, double[][][] cx) {
/*  3586 */     int n3 = cx.length;
/*  3587 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3588 */       cramp(ca.plus(cb3.times(i3)), cb1, cb2, cx[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[] copy(byte[] rx) {
/*  3600 */     return copy(rx.length, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][] copy(byte[][] rx) {
/*  3609 */     int n2 = rx.length;
/*  3610 */     byte[][] ry = new byte[n2][];
/*  3611 */     for (int i2 = 0; i2 < n2; i2++)
/*  3612 */       ry[i2] = copy(rx[i2]); 
/*  3613 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][][] copy(byte[][][] rx) {
/*  3622 */     int n3 = rx.length;
/*  3623 */     byte[][][] ry = new byte[n3][][];
/*  3624 */     for (int i3 = 0; i3 < n3; i3++)
/*  3625 */       ry[i3] = copy(rx[i3]); 
/*  3626 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(byte[] rx, byte[] ry) {
/*  3635 */     copy(rx.length, rx, ry);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(byte[][] rx, byte[][] ry) {
/*  3644 */     int n2 = rx.length;
/*  3645 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3646 */       copy(rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(byte[][][] rx, byte[][][] ry) {
/*  3655 */     int n3 = rx.length;
/*  3656 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3657 */       copy(rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[] copy(int n1, byte[] rx) {
/*  3667 */     byte[] ry = new byte[n1];
/*  3668 */     copy(n1, rx, ry);
/*  3669 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][] copy(int n1, int n2, byte[][] rx) {
/*  3680 */     byte[][] ry = new byte[n2][n1];
/*  3681 */     copy(n1, n2, rx, ry);
/*  3682 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][][] copy(int n1, int n2, int n3, byte[][][] rx) {
/*  3694 */     byte[][][] ry = new byte[n3][n2][n1];
/*  3695 */     copy(n1, n2, n3, rx, ry);
/*  3696 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, byte[] rx, byte[] ry) {
/*  3706 */     for (int i1 = 0; i1 < n1; i1++) {
/*  3707 */       ry[i1] = rx[i1];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, byte[][] rx, byte[][] ry) {
/*  3718 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3719 */       copy(n1, rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, byte[][][] rx, byte[][][] ry) {
/*  3732 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3733 */       copy(n1, n2, rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[] copy(int n1, int j1, byte[] rx) {
/*  3746 */     byte[] ry = new byte[n1];
/*  3747 */     copy(n1, j1, rx, 0, ry);
/*  3748 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][] copy(int n1, int n2, int j1, int j2, byte[][] rx) {
/*  3763 */     byte[][] ry = new byte[n2][n1];
/*  3764 */     copy(n1, n2, j1, j2, rx, 0, 0, ry);
/*  3765 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, byte[][][] rx) {
/*  3782 */     byte[][][] ry = new byte[n3][n2][n1];
/*  3783 */     copy(n1, n2, n3, j1, j2, j3, rx, 0, 0, 0, ry);
/*  3784 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[] copy(int n1, int j1, int k1, byte[] rx) {
/*  3798 */     byte[] ry = new byte[n1];
/*  3799 */     copy(n1, j1, k1, rx, 0, 1, ry);
/*  3800 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][] copy(int n1, int n2, int j1, int j2, int k1, int k2, byte[][] rx) {
/*  3817 */     byte[][] ry = new byte[n2][n1];
/*  3818 */     copy(n1, n2, j1, j2, k1, k2, rx, 0, 0, 1, 1, ry);
/*  3819 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, int k1, int k2, int k3, byte[][][] rx) {
/*  3839 */     byte[][][] ry = new byte[n3][n2][n1];
/*  3840 */     copy(n1, n2, n3, j1, j2, j3, k1, k2, k3, rx, 0, 0, 0, 1, 1, 1, ry);
/*  3841 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, byte[] rx, int j1y, byte[] ry) {
/*  3856 */     for (int i1 = 0, ix = j1x, iy = j1y; i1 < n1; i1++) {
/*  3857 */       ry[iy++] = rx[ix++];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, byte[][] rx, int j1y, int j2y, byte[][] ry) {
/*  3875 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3876 */       copy(n1, j1x, rx[j2x + i2], j1y, ry[j2y + i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, byte[][][] rx, int j1y, int j2y, int j3y, byte[][][] ry) {
/*  3897 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3898 */       copy(n1, n2, j1x, j2x, rx[j3x + i3], j1y, j2y, ry[j3y + i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, int k1x, byte[] rx, int j1y, int k1y, byte[] ry) {
/*       */     int iy;
/*  3915 */     for (int i1 = 0, ix = j1x; i1 < n1; i1++, ix += k1x, iy += k1y) {
/*  3916 */       ry[iy] = rx[ix];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, int k1x, int k2x, byte[][] rx, int j1y, int j2y, int k1y, int k2y, byte[][] ry) {
/*  3938 */     for (int i2 = 0; i2 < n2; i2++) {
/*  3939 */       copy(n1, j1x, k1x, rx[j2x + i2 * k2x], j1y, k1y, ry[j2y + i2 * k2y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, int k1x, int k2x, int k3x, byte[][][] rx, int j1y, int j2y, int j3y, int k1y, int k2y, int k3y, byte[][][] ry) {
/*  3966 */     for (int i3 = 0; i3 < n3; i3++) {
/*  3967 */       copy(n1, n2, j1x, j2x, k1x, k2x, rx[j3x + i3 * k3x], j1y, j2y, k1y, k2y, ry[j3y + i3 * k3y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[] copy(short[] rx) {
/*  3976 */     return copy(rx.length, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][] copy(short[][] rx) {
/*  3985 */     int n2 = rx.length;
/*  3986 */     short[][] ry = new short[n2][];
/*  3987 */     for (int i2 = 0; i2 < n2; i2++)
/*  3988 */       ry[i2] = copy(rx[i2]); 
/*  3989 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][][] copy(short[][][] rx) {
/*  3998 */     int n3 = rx.length;
/*  3999 */     short[][][] ry = new short[n3][][];
/*  4000 */     for (int i3 = 0; i3 < n3; i3++)
/*  4001 */       ry[i3] = copy(rx[i3]); 
/*  4002 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(short[] rx, short[] ry) {
/*  4011 */     copy(rx.length, rx, ry);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(short[][] rx, short[][] ry) {
/*  4020 */     int n2 = rx.length;
/*  4021 */     for (int i2 = 0; i2 < n2; i2++) {
/*  4022 */       copy(rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(short[][][] rx, short[][][] ry) {
/*  4031 */     int n3 = rx.length;
/*  4032 */     for (int i3 = 0; i3 < n3; i3++) {
/*  4033 */       copy(rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[] copy(int n1, short[] rx) {
/*  4043 */     short[] ry = new short[n1];
/*  4044 */     copy(n1, rx, ry);
/*  4045 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][] copy(int n1, int n2, short[][] rx) {
/*  4056 */     short[][] ry = new short[n2][n1];
/*  4057 */     copy(n1, n2, rx, ry);
/*  4058 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][][] copy(int n1, int n2, int n3, short[][][] rx) {
/*  4070 */     short[][][] ry = new short[n3][n2][n1];
/*  4071 */     copy(n1, n2, n3, rx, ry);
/*  4072 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, short[] rx, short[] ry) {
/*  4082 */     for (int i1 = 0; i1 < n1; i1++) {
/*  4083 */       ry[i1] = rx[i1];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, short[][] rx, short[][] ry) {
/*  4094 */     for (int i2 = 0; i2 < n2; i2++) {
/*  4095 */       copy(n1, rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, short[][][] rx, short[][][] ry) {
/*  4108 */     for (int i3 = 0; i3 < n3; i3++) {
/*  4109 */       copy(n1, n2, rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[] copy(int n1, int j1, short[] rx) {
/*  4122 */     short[] ry = new short[n1];
/*  4123 */     copy(n1, j1, rx, 0, ry);
/*  4124 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][] copy(int n1, int n2, int j1, int j2, short[][] rx) {
/*  4139 */     short[][] ry = new short[n2][n1];
/*  4140 */     copy(n1, n2, j1, j2, rx, 0, 0, ry);
/*  4141 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, short[][][] rx) {
/*  4158 */     short[][][] ry = new short[n3][n2][n1];
/*  4159 */     copy(n1, n2, n3, j1, j2, j3, rx, 0, 0, 0, ry);
/*  4160 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[] copy(int n1, int j1, int k1, short[] rx) {
/*  4174 */     short[] ry = new short[n1];
/*  4175 */     copy(n1, j1, k1, rx, 0, 1, ry);
/*  4176 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][] copy(int n1, int n2, int j1, int j2, int k1, int k2, short[][] rx) {
/*  4193 */     short[][] ry = new short[n2][n1];
/*  4194 */     copy(n1, n2, j1, j2, k1, k2, rx, 0, 0, 1, 1, ry);
/*  4195 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, int k1, int k2, int k3, short[][][] rx) {
/*  4215 */     short[][][] ry = new short[n3][n2][n1];
/*  4216 */     copy(n1, n2, n3, j1, j2, j3, k1, k2, k3, rx, 0, 0, 0, 1, 1, 1, ry);
/*  4217 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, short[] rx, int j1y, short[] ry) {
/*  4232 */     for (int i1 = 0, ix = j1x, iy = j1y; i1 < n1; i1++) {
/*  4233 */       ry[iy++] = rx[ix++];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, short[][] rx, int j1y, int j2y, short[][] ry) {
/*  4251 */     for (int i2 = 0; i2 < n2; i2++) {
/*  4252 */       copy(n1, j1x, rx[j2x + i2], j1y, ry[j2y + i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, short[][][] rx, int j1y, int j2y, int j3y, short[][][] ry) {
/*  4273 */     for (int i3 = 0; i3 < n3; i3++) {
/*  4274 */       copy(n1, n2, j1x, j2x, rx[j3x + i3], j1y, j2y, ry[j3y + i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, int k1x, short[] rx, int j1y, int k1y, short[] ry) {
/*       */     int iy;
/*  4291 */     for (int i1 = 0, ix = j1x; i1 < n1; i1++, ix += k1x, iy += k1y) {
/*  4292 */       ry[iy] = rx[ix];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, int k1x, int k2x, short[][] rx, int j1y, int j2y, int k1y, int k2y, short[][] ry) {
/*  4314 */     for (int i2 = 0; i2 < n2; i2++) {
/*  4315 */       copy(n1, j1x, k1x, rx[j2x + i2 * k2x], j1y, k1y, ry[j2y + i2 * k2y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, int k1x, int k2x, int k3x, short[][][] rx, int j1y, int j2y, int j3y, int k1y, int k2y, int k3y, short[][][] ry) {
/*  4342 */     for (int i3 = 0; i3 < n3; i3++) {
/*  4343 */       copy(n1, n2, j1x, j2x, k1x, k2x, rx[j3x + i3 * k3x], j1y, j2y, k1y, k2y, ry[j3y + i3 * k3y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] copy(int[] rx) {
/*  4352 */     return copy(rx.length, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] copy(int[][] rx) {
/*  4361 */     int n2 = rx.length;
/*  4362 */     int[][] ry = new int[n2][];
/*  4363 */     for (int i2 = 0; i2 < n2; i2++)
/*  4364 */       ry[i2] = copy(rx[i2]); 
/*  4365 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][][] copy(int[][][] rx) {
/*  4374 */     int n3 = rx.length;
/*  4375 */     int[][][] ry = new int[n3][][];
/*  4376 */     for (int i3 = 0; i3 < n3; i3++)
/*  4377 */       ry[i3] = copy(rx[i3]); 
/*  4378 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int[] rx, int[] ry) {
/*  4387 */     copy(rx.length, rx, ry);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int[][] rx, int[][] ry) {
/*  4396 */     int n2 = rx.length;
/*  4397 */     for (int i2 = 0; i2 < n2; i2++) {
/*  4398 */       copy(rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int[][][] rx, int[][][] ry) {
/*  4407 */     int n3 = rx.length;
/*  4408 */     for (int i3 = 0; i3 < n3; i3++) {
/*  4409 */       copy(rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] copy(int n1, int[] rx) {
/*  4419 */     int[] ry = new int[n1];
/*  4420 */     copy(n1, rx, ry);
/*  4421 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] copy(int n1, int n2, int[][] rx) {
/*  4432 */     int[][] ry = new int[n2][n1];
/*  4433 */     copy(n1, n2, rx, ry);
/*  4434 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][][] copy(int n1, int n2, int n3, int[][][] rx) {
/*  4446 */     int[][][] ry = new int[n3][n2][n1];
/*  4447 */     copy(n1, n2, n3, rx, ry);
/*  4448 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int[] rx, int[] ry) {
/*  4458 */     for (int i1 = 0; i1 < n1; i1++) {
/*  4459 */       ry[i1] = rx[i1];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int[][] rx, int[][] ry) {
/*  4470 */     for (int i2 = 0; i2 < n2; i2++) {
/*  4471 */       copy(n1, rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int[][][] rx, int[][][] ry) {
/*  4484 */     for (int i3 = 0; i3 < n3; i3++) {
/*  4485 */       copy(n1, n2, rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] copy(int n1, int j1, int[] rx) {
/*  4498 */     int[] ry = new int[n1];
/*  4499 */     copy(n1, j1, rx, 0, ry);
/*  4500 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] copy(int n1, int n2, int j1, int j2, int[][] rx) {
/*  4515 */     int[][] ry = new int[n2][n1];
/*  4516 */     copy(n1, n2, j1, j2, rx, 0, 0, ry);
/*  4517 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, int[][][] rx) {
/*  4534 */     int[][][] ry = new int[n3][n2][n1];
/*  4535 */     copy(n1, n2, n3, j1, j2, j3, rx, 0, 0, 0, ry);
/*  4536 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] copy(int n1, int j1, int k1, int[] rx) {
/*  4550 */     int[] ry = new int[n1];
/*  4551 */     copy(n1, j1, k1, rx, 0, 1, ry);
/*  4552 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] copy(int n1, int n2, int j1, int j2, int k1, int k2, int[][] rx) {
/*  4569 */     int[][] ry = new int[n2][n1];
/*  4570 */     copy(n1, n2, j1, j2, k1, k2, rx, 0, 0, 1, 1, ry);
/*  4571 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, int k1, int k2, int k3, int[][][] rx) {
/*  4591 */     int[][][] ry = new int[n3][n2][n1];
/*  4592 */     copy(n1, n2, n3, j1, j2, j3, k1, k2, k3, rx, 0, 0, 0, 1, 1, 1, ry);
/*  4593 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, int[] rx, int j1y, int[] ry) {
/*  4608 */     for (int i1 = 0, ix = j1x, iy = j1y; i1 < n1; i1++) {
/*  4609 */       ry[iy++] = rx[ix++];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, int[][] rx, int j1y, int j2y, int[][] ry) {
/*  4627 */     for (int i2 = 0; i2 < n2; i2++) {
/*  4628 */       copy(n1, j1x, rx[j2x + i2], j1y, ry[j2y + i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, int[][][] rx, int j1y, int j2y, int j3y, int[][][] ry) {
/*  4649 */     for (int i3 = 0; i3 < n3; i3++) {
/*  4650 */       copy(n1, n2, j1x, j2x, rx[j3x + i3], j1y, j2y, ry[j3y + i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, int k1x, int[] rx, int j1y, int k1y, int[] ry) {
/*       */     int iy;
/*  4667 */     for (int i1 = 0, ix = j1x; i1 < n1; i1++, ix += k1x, iy += k1y) {
/*  4668 */       ry[iy] = rx[ix];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, int k1x, int k2x, int[][] rx, int j1y, int j2y, int k1y, int k2y, int[][] ry) {
/*  4690 */     for (int i2 = 0; i2 < n2; i2++) {
/*  4691 */       copy(n1, j1x, k1x, rx[j2x + i2 * k2x], j1y, k1y, ry[j2y + i2 * k2y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, int k1x, int k2x, int k3x, int[][][] rx, int j1y, int j2y, int j3y, int k1y, int k2y, int k3y, int[][][] ry) {
/*  4718 */     for (int i3 = 0; i3 < n3; i3++) {
/*  4719 */       copy(n1, n2, j1x, j2x, k1x, k2x, rx[j3x + i3 * k3x], j1y, j2y, k1y, k2y, ry[j3y + i3 * k3y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] copy(long[] rx) {
/*  4728 */     return copy(rx.length, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] copy(long[][] rx) {
/*  4737 */     int n2 = rx.length;
/*  4738 */     long[][] ry = new long[n2][];
/*  4739 */     for (int i2 = 0; i2 < n2; i2++)
/*  4740 */       ry[i2] = copy(rx[i2]); 
/*  4741 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][][] copy(long[][][] rx) {
/*  4750 */     int n3 = rx.length;
/*  4751 */     long[][][] ry = new long[n3][][];
/*  4752 */     for (int i3 = 0; i3 < n3; i3++)
/*  4753 */       ry[i3] = copy(rx[i3]); 
/*  4754 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(long[] rx, long[] ry) {
/*  4763 */     copy(rx.length, rx, ry);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(long[][] rx, long[][] ry) {
/*  4772 */     int n2 = rx.length;
/*  4773 */     for (int i2 = 0; i2 < n2; i2++) {
/*  4774 */       copy(rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(long[][][] rx, long[][][] ry) {
/*  4783 */     int n3 = rx.length;
/*  4784 */     for (int i3 = 0; i3 < n3; i3++) {
/*  4785 */       copy(rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] copy(int n1, long[] rx) {
/*  4795 */     long[] ry = new long[n1];
/*  4796 */     copy(n1, rx, ry);
/*  4797 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] copy(int n1, int n2, long[][] rx) {
/*  4808 */     long[][] ry = new long[n2][n1];
/*  4809 */     copy(n1, n2, rx, ry);
/*  4810 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][][] copy(int n1, int n2, int n3, long[][][] rx) {
/*  4822 */     long[][][] ry = new long[n3][n2][n1];
/*  4823 */     copy(n1, n2, n3, rx, ry);
/*  4824 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, long[] rx, long[] ry) {
/*  4834 */     for (int i1 = 0; i1 < n1; i1++) {
/*  4835 */       ry[i1] = rx[i1];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, long[][] rx, long[][] ry) {
/*  4846 */     for (int i2 = 0; i2 < n2; i2++) {
/*  4847 */       copy(n1, rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, long[][][] rx, long[][][] ry) {
/*  4860 */     for (int i3 = 0; i3 < n3; i3++) {
/*  4861 */       copy(n1, n2, rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] copy(int n1, int j1, long[] rx) {
/*  4874 */     long[] ry = new long[n1];
/*  4875 */     copy(n1, j1, rx, 0, ry);
/*  4876 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] copy(int n1, int n2, int j1, int j2, long[][] rx) {
/*  4891 */     long[][] ry = new long[n2][n1];
/*  4892 */     copy(n1, n2, j1, j2, rx, 0, 0, ry);
/*  4893 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, long[][][] rx) {
/*  4910 */     long[][][] ry = new long[n3][n2][n1];
/*  4911 */     copy(n1, n2, n3, j1, j2, j3, rx, 0, 0, 0, ry);
/*  4912 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] copy(int n1, int j1, int k1, long[] rx) {
/*  4926 */     long[] ry = new long[n1];
/*  4927 */     copy(n1, j1, k1, rx, 0, 1, ry);
/*  4928 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] copy(int n1, int n2, int j1, int j2, int k1, int k2, long[][] rx) {
/*  4945 */     long[][] ry = new long[n2][n1];
/*  4946 */     copy(n1, n2, j1, j2, k1, k2, rx, 0, 0, 1, 1, ry);
/*  4947 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, int k1, int k2, int k3, long[][][] rx) {
/*  4967 */     long[][][] ry = new long[n3][n2][n1];
/*  4968 */     copy(n1, n2, n3, j1, j2, j3, k1, k2, k3, rx, 0, 0, 0, 1, 1, 1, ry);
/*  4969 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, long[] rx, int j1y, long[] ry) {
/*  4984 */     for (int i1 = 0, ix = j1x, iy = j1y; i1 < n1; i1++) {
/*  4985 */       ry[iy++] = rx[ix++];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, long[][] rx, int j1y, int j2y, long[][] ry) {
/*  5003 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5004 */       copy(n1, j1x, rx[j2x + i2], j1y, ry[j2y + i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, long[][][] rx, int j1y, int j2y, int j3y, long[][][] ry) {
/*  5025 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5026 */       copy(n1, n2, j1x, j2x, rx[j3x + i3], j1y, j2y, ry[j3y + i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, int k1x, long[] rx, int j1y, int k1y, long[] ry) {
/*       */     int iy;
/*  5043 */     for (int i1 = 0, ix = j1x; i1 < n1; i1++, ix += k1x, iy += k1y) {
/*  5044 */       ry[iy] = rx[ix];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, int k1x, int k2x, long[][] rx, int j1y, int j2y, int k1y, int k2y, long[][] ry) {
/*  5066 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5067 */       copy(n1, j1x, k1x, rx[j2x + i2 * k2x], j1y, k1y, ry[j2y + i2 * k2y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, int k1x, int k2x, int k3x, long[][][] rx, int j1y, int j2y, int j3y, int k1y, int k2y, int k3y, long[][][] ry) {
/*  5094 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5095 */       copy(n1, n2, j1x, j2x, k1x, k2x, rx[j3x + i3 * k3x], j1y, j2y, k1y, k2y, ry[j3y + i3 * k3y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] copy(float[] rx) {
/*  5104 */     return copy(rx.length, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] copy(float[][] rx) {
/*  5113 */     int n2 = rx.length;
/*  5114 */     float[][] ry = new float[n2][];
/*  5115 */     for (int i2 = 0; i2 < n2; i2++)
/*  5116 */       ry[i2] = copy(rx[i2]); 
/*  5117 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] copy(float[][][] rx) {
/*  5126 */     int n3 = rx.length;
/*  5127 */     float[][][] ry = new float[n3][][];
/*  5128 */     for (int i3 = 0; i3 < n3; i3++)
/*  5129 */       ry[i3] = copy(rx[i3]); 
/*  5130 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(float[] rx, float[] ry) {
/*  5139 */     copy(rx.length, rx, ry);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(float[][] rx, float[][] ry) {
/*  5148 */     int n2 = rx.length;
/*  5149 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5150 */       copy(rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(float[][][] rx, float[][][] ry) {
/*  5159 */     int n3 = rx.length;
/*  5160 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5161 */       copy(rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] copy(int n1, float[] rx) {
/*  5171 */     float[] ry = new float[n1];
/*  5172 */     copy(n1, rx, ry);
/*  5173 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] copy(int n1, int n2, float[][] rx) {
/*  5184 */     float[][] ry = new float[n2][n1];
/*  5185 */     copy(n1, n2, rx, ry);
/*  5186 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] copy(int n1, int n2, int n3, float[][][] rx) {
/*  5198 */     float[][][] ry = new float[n3][n2][n1];
/*  5199 */     copy(n1, n2, n3, rx, ry);
/*  5200 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, float[] rx, float[] ry) {
/*  5210 */     for (int i1 = 0; i1 < n1; i1++) {
/*  5211 */       ry[i1] = rx[i1];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, float[][] rx, float[][] ry) {
/*  5222 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5223 */       copy(n1, rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, float[][][] rx, float[][][] ry) {
/*  5236 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5237 */       copy(n1, n2, rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] copy(int n1, int j1, float[] rx) {
/*  5250 */     float[] ry = new float[n1];
/*  5251 */     copy(n1, j1, rx, 0, ry);
/*  5252 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] copy(int n1, int n2, int j1, int j2, float[][] rx) {
/*  5267 */     float[][] ry = new float[n2][n1];
/*  5268 */     copy(n1, n2, j1, j2, rx, 0, 0, ry);
/*  5269 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, float[][][] rx) {
/*  5286 */     float[][][] ry = new float[n3][n2][n1];
/*  5287 */     copy(n1, n2, n3, j1, j2, j3, rx, 0, 0, 0, ry);
/*  5288 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] copy(int n1, int j1, int k1, float[] rx) {
/*  5302 */     float[] ry = new float[n1];
/*  5303 */     copy(n1, j1, k1, rx, 0, 1, ry);
/*  5304 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] copy(int n1, int n2, int j1, int j2, int k1, int k2, float[][] rx) {
/*  5321 */     float[][] ry = new float[n2][n1];
/*  5322 */     copy(n1, n2, j1, j2, k1, k2, rx, 0, 0, 1, 1, ry);
/*  5323 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, int k1, int k2, int k3, float[][][] rx) {
/*  5343 */     float[][][] ry = new float[n3][n2][n1];
/*  5344 */     copy(n1, n2, n3, j1, j2, j3, k1, k2, k3, rx, 0, 0, 0, 1, 1, 1, ry);
/*  5345 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, float[] rx, int j1y, float[] ry) {
/*  5360 */     for (int i1 = 0, ix = j1x, iy = j1y; i1 < n1; i1++) {
/*  5361 */       ry[iy++] = rx[ix++];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, float[][] rx, int j1y, int j2y, float[][] ry) {
/*  5379 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5380 */       copy(n1, j1x, rx[j2x + i2], j1y, ry[j2y + i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, float[][][] rx, int j1y, int j2y, int j3y, float[][][] ry) {
/*  5401 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5402 */       copy(n1, n2, j1x, j2x, rx[j3x + i3], j1y, j2y, ry[j3y + i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, int k1x, float[] rx, int j1y, int k1y, float[] ry) {
/*       */     int iy;
/*  5419 */     for (int i1 = 0, ix = j1x; i1 < n1; i1++, ix += k1x, iy += k1y) {
/*  5420 */       ry[iy] = rx[ix];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, int k1x, int k2x, float[][] rx, int j1y, int j2y, int k1y, int k2y, float[][] ry) {
/*  5442 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5443 */       copy(n1, j1x, k1x, rx[j2x + i2 * k2x], j1y, k1y, ry[j2y + i2 * k2y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, int k1x, int k2x, int k3x, float[][][] rx, int j1y, int j2y, int j3y, int k1y, int k2y, int k3y, float[][][] ry) {
/*  5470 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5471 */       copy(n1, n2, j1x, j2x, k1x, k2x, rx[j3x + i3 * k3x], j1y, j2y, k1y, k2y, ry[j3y + i3 * k3y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] ccopy(float[] cx) {
/*  5480 */     return ccopy(cx.length / 2, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] ccopy(float[][] cx) {
/*  5489 */     int n2 = cx.length;
/*  5490 */     float[][] cy = new float[n2][];
/*  5491 */     for (int i2 = 0; i2 < n2; i2++)
/*  5492 */       cy[i2] = ccopy(cx[i2]); 
/*  5493 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] ccopy(float[][][] cx) {
/*  5502 */     int n3 = cx.length;
/*  5503 */     float[][][] cy = new float[n3][][];
/*  5504 */     for (int i3 = 0; i3 < n3; i3++)
/*  5505 */       cy[i3] = ccopy(cx[i3]); 
/*  5506 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(float[] cx, float[] cy) {
/*  5515 */     ccopy(cx.length / 2, cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(float[][] cx, float[][] cy) {
/*  5524 */     int n2 = cx.length;
/*  5525 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5526 */       ccopy(cx[i2], cy[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(float[][][] cx, float[][][] cy) {
/*  5535 */     int n3 = cx.length;
/*  5536 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5537 */       ccopy(cx[i3], cy[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] ccopy(int n1, float[] cx) {
/*  5547 */     float[] cy = new float[2 * n1];
/*  5548 */     ccopy(n1, cx, cy);
/*  5549 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] ccopy(int n1, int n2, float[][] cx) {
/*  5560 */     float[][] cy = new float[n2][2 * n1];
/*  5561 */     ccopy(n1, n2, cx, cy);
/*  5562 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] ccopy(int n1, int n2, int n3, float[][][] cx) {
/*  5574 */     float[][][] cy = new float[n3][n2][2 * n1];
/*  5575 */     ccopy(n1, n2, n3, cx, cy);
/*  5576 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, float[] cx, float[] cy) {
/*  5586 */     copy(2 * n1, cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, float[][] cx, float[][] cy) {
/*  5597 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5598 */       ccopy(n1, cx[i2], cy[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, int n3, float[][][] cx, float[][][] cy) {
/*  5611 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5612 */       ccopy(n1, n2, cx[i3], cy[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] ccopy(int n1, int j1, float[] cx) {
/*  5625 */     float[] cy = new float[2 * n1];
/*  5626 */     ccopy(n1, j1, cx, 0, cy);
/*  5627 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] ccopy(int n1, int n2, int j1, int j2, float[][] cx) {
/*  5642 */     float[][] cy = new float[n2][2 * n1];
/*  5643 */     ccopy(n1, n2, j1, j2, cx, 0, 0, cy);
/*  5644 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] ccopy(int n1, int n2, int n3, int j1, int j2, int j3, float[][][] cx) {
/*  5661 */     float[][][] cy = new float[n3][n2][2 * n1];
/*  5662 */     ccopy(n1, n2, n3, j1, j2, j3, cx, 0, 0, 0, cy);
/*  5663 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] ccopy(int n1, int j1, int k1, float[] cx) {
/*  5677 */     float[] cy = new float[2 * n1];
/*  5678 */     ccopy(n1, j1, k1, cx, 0, 1, cy);
/*  5679 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] ccopy(int n1, int n2, int j1, int j2, int k1, int k2, float[][] cx) {
/*  5696 */     float[][] cy = new float[n2][2 * n1];
/*  5697 */     ccopy(n1, n2, j1, j2, k1, k2, cx, 0, 0, 1, 1, cy);
/*  5698 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] ccopy(int n1, int n2, int n3, int j1, int j2, int j3, int k1, int k2, int k3, float[][][] cx) {
/*  5718 */     float[][][] cy = new float[n3][n2][2 * n1];
/*  5719 */     ccopy(n1, n2, n3, j1, j2, j3, k1, k2, k3, cx, 0, 0, 0, 1, 1, 1, cy);
/*  5720 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int j1x, float[] cx, int j1y, float[] cy) {
/*  5735 */     for (int i1 = 0, ix = 2 * j1x, iy = 2 * j1y; i1 < n1; i1++) {
/*  5736 */       cy[iy++] = cx[ix++];
/*  5737 */       cy[iy++] = cx[ix++];
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, int j1x, int j2x, float[][] cx, int j1y, int j2y, float[][] cy) {
/*  5756 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5757 */       ccopy(n1, j1x, cx[j2x + i2], j1y, cy[j2y + i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, int n3, int j1x, int j2x, int j3x, float[][][] cx, int j1y, int j2y, int j3y, float[][][] cy) {
/*  5778 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5779 */       ccopy(n1, n2, j1x, j2x, cx[j3x + i3], j1y, j2y, cy[j3y + i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int j1x, int k1x, float[] cx, int j1y, int k1y, float[] cy) {
/*  5796 */     int k1x2 = k1x * 2;
/*  5797 */     int k1y2 = k1y * 2; int iy;
/*  5798 */     for (int i1 = 0, ix = 2 * j1x; i1 < n1; i1++, ix += k1x2, iy += k1y2) {
/*  5799 */       cy[iy] = cx[ix];
/*  5800 */       cy[iy + 1] = cx[ix + 1];
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, int j1x, int j2x, int k1x, int k2x, float[][] cx, int j1y, int j2y, int k1y, int k2y, float[][] cy) {
/*  5823 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5824 */       ccopy(n1, j1x, k1x, cx[j2x + i2 * k2x], j1y, k1y, cy[j2y + i2 * k2y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, int n3, int j1x, int j2x, int j3x, int k1x, int k2x, int k3x, float[][][] cx, int j1y, int j2y, int j3y, int k1y, int k2y, int k3y, float[][][] cy) {
/*  5851 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5852 */       ccopy(n1, n2, j1x, j2x, k1x, k2x, cx[j3x + i3 * k3x], j1y, j2y, k1y, k2y, cy[j3y + i3 * k3y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] copy(double[] rx) {
/*  5863 */     return copy(rx.length, rx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] copy(double[][] rx) {
/*  5872 */     int n2 = rx.length;
/*  5873 */     double[][] ry = new double[n2][];
/*  5874 */     for (int i2 = 0; i2 < n2; i2++)
/*  5875 */       ry[i2] = copy(rx[i2]); 
/*  5876 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] copy(double[][][] rx) {
/*  5885 */     int n3 = rx.length;
/*  5886 */     double[][][] ry = new double[n3][][];
/*  5887 */     for (int i3 = 0; i3 < n3; i3++)
/*  5888 */       ry[i3] = copy(rx[i3]); 
/*  5889 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(double[] rx, double[] ry) {
/*  5898 */     copy(rx.length, rx, ry);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(double[][] rx, double[][] ry) {
/*  5907 */     int n2 = rx.length;
/*  5908 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5909 */       copy(rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(double[][][] rx, double[][][] ry) {
/*  5918 */     int n3 = rx.length;
/*  5919 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5920 */       copy(rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] copy(int n1, double[] rx) {
/*  5930 */     double[] ry = new double[n1];
/*  5931 */     copy(n1, rx, ry);
/*  5932 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] copy(int n1, int n2, double[][] rx) {
/*  5943 */     double[][] ry = new double[n2][n1];
/*  5944 */     copy(n1, n2, rx, ry);
/*  5945 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] copy(int n1, int n2, int n3, double[][][] rx) {
/*  5957 */     double[][][] ry = new double[n3][n2][n1];
/*  5958 */     copy(n1, n2, n3, rx, ry);
/*  5959 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, double[] rx, double[] ry) {
/*  5969 */     for (int i1 = 0; i1 < n1; i1++) {
/*  5970 */       ry[i1] = rx[i1];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, double[][] rx, double[][] ry) {
/*  5981 */     for (int i2 = 0; i2 < n2; i2++) {
/*  5982 */       copy(n1, rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, double[][][] rx, double[][][] ry) {
/*  5995 */     for (int i3 = 0; i3 < n3; i3++) {
/*  5996 */       copy(n1, n2, rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] copy(int n1, int j1, double[] rx) {
/*  6009 */     double[] ry = new double[n1];
/*  6010 */     copy(n1, j1, rx, 0, ry);
/*  6011 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] copy(int n1, int n2, int j1, int j2, double[][] rx) {
/*  6026 */     double[][] ry = new double[n2][n1];
/*  6027 */     copy(n1, n2, j1, j2, rx, 0, 0, ry);
/*  6028 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, double[][][] rx) {
/*  6045 */     double[][][] ry = new double[n3][n2][n1];
/*  6046 */     copy(n1, n2, n3, j1, j2, j3, rx, 0, 0, 0, ry);
/*  6047 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] copy(int n1, int j1, int k1, double[] rx) {
/*  6061 */     double[] ry = new double[n1];
/*  6062 */     copy(n1, j1, k1, rx, 0, 1, ry);
/*  6063 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] copy(int n1, int n2, int j1, int j2, int k1, int k2, double[][] rx) {
/*  6080 */     double[][] ry = new double[n2][n1];
/*  6081 */     copy(n1, n2, j1, j2, k1, k2, rx, 0, 0, 1, 1, ry);
/*  6082 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] copy(int n1, int n2, int n3, int j1, int j2, int j3, int k1, int k2, int k3, double[][][] rx) {
/*  6102 */     double[][][] ry = new double[n3][n2][n1];
/*  6103 */     copy(n1, n2, n3, j1, j2, j3, k1, k2, k3, rx, 0, 0, 0, 1, 1, 1, ry);
/*  6104 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, double[] rx, int j1y, double[] ry) {
/*  6119 */     for (int i1 = 0, ix = j1x, iy = j1y; i1 < n1; i1++) {
/*  6120 */       ry[iy++] = rx[ix++];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, double[][] rx, int j1y, int j2y, double[][] ry) {
/*  6138 */     for (int i2 = 0; i2 < n2; i2++) {
/*  6139 */       copy(n1, j1x, rx[j2x + i2], j1y, ry[j2y + i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, double[][][] rx, int j1y, int j2y, int j3y, double[][][] ry) {
/*  6160 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6161 */       copy(n1, n2, j1x, j2x, rx[j3x + i3], j1y, j2y, ry[j3y + i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int j1x, int k1x, double[] rx, int j1y, int k1y, double[] ry) {
/*       */     int iy;
/*  6178 */     for (int i1 = 0, ix = j1x; i1 < n1; i1++, ix += k1x, iy += k1y) {
/*  6179 */       ry[iy] = rx[ix];
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int j1x, int j2x, int k1x, int k2x, double[][] rx, int j1y, int j2y, int k1y, int k2y, double[][] ry) {
/*  6201 */     for (int i2 = 0; i2 < n2; i2++) {
/*  6202 */       copy(n1, j1x, k1x, rx[j2x + i2 * k2x], j1y, k1y, ry[j2y + i2 * k2y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void copy(int n1, int n2, int n3, int j1x, int j2x, int j3x, int k1x, int k2x, int k3x, double[][][] rx, int j1y, int j2y, int j3y, int k1y, int k2y, int k3y, double[][][] ry) {
/*  6229 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6230 */       copy(n1, n2, j1x, j2x, k1x, k2x, rx[j3x + i3 * k3x], j1y, j2y, k1y, k2y, ry[j3y + i3 * k3y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] ccopy(double[] cx) {
/*  6239 */     return ccopy(cx.length / 2, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] ccopy(double[][] cx) {
/*  6248 */     int n2 = cx.length;
/*  6249 */     double[][] cy = new double[n2][];
/*  6250 */     for (int i2 = 0; i2 < n2; i2++)
/*  6251 */       cy[i2] = ccopy(cx[i2]); 
/*  6252 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] ccopy(double[][][] cx) {
/*  6261 */     int n3 = cx.length;
/*  6262 */     double[][][] cy = new double[n3][][];
/*  6263 */     for (int i3 = 0; i3 < n3; i3++)
/*  6264 */       cy[i3] = ccopy(cx[i3]); 
/*  6265 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(double[] cx, double[] cy) {
/*  6274 */     ccopy(cx.length / 2, cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(double[][] cx, double[][] cy) {
/*  6283 */     int n2 = cx.length;
/*  6284 */     for (int i2 = 0; i2 < n2; i2++) {
/*  6285 */       ccopy(cx[i2], cy[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(double[][][] cx, double[][][] cy) {
/*  6294 */     int n3 = cx.length;
/*  6295 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6296 */       ccopy(cx[i3], cy[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] ccopy(int n1, double[] cx) {
/*  6306 */     double[] cy = new double[2 * n1];
/*  6307 */     ccopy(n1, cx, cy);
/*  6308 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] ccopy(int n1, int n2, double[][] cx) {
/*  6319 */     double[][] cy = new double[n2][2 * n1];
/*  6320 */     ccopy(n1, n2, cx, cy);
/*  6321 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] ccopy(int n1, int n2, int n3, double[][][] cx) {
/*  6333 */     double[][][] cy = new double[n3][n2][2 * n1];
/*  6334 */     ccopy(n1, n2, n3, cx, cy);
/*  6335 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, double[] cx, double[] cy) {
/*  6345 */     copy(2 * n1, cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, double[][] cx, double[][] cy) {
/*  6356 */     for (int i2 = 0; i2 < n2; i2++) {
/*  6357 */       ccopy(n1, cx[i2], cy[i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, int n3, double[][][] cx, double[][][] cy) {
/*  6370 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6371 */       ccopy(n1, n2, cx[i3], cy[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] ccopy(int n1, int j1, double[] cx) {
/*  6384 */     double[] cy = new double[2 * n1];
/*  6385 */     ccopy(n1, j1, cx, 0, cy);
/*  6386 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] ccopy(int n1, int n2, int j1, int j2, double[][] cx) {
/*  6401 */     double[][] cy = new double[n2][2 * n1];
/*  6402 */     ccopy(n1, n2, j1, j2, cx, 0, 0, cy);
/*  6403 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] ccopy(int n1, int n2, int n3, int j1, int j2, int j3, double[][][] cx) {
/*  6420 */     double[][][] cy = new double[n3][n2][2 * n1];
/*  6421 */     ccopy(n1, n2, n3, j1, j2, j3, cx, 0, 0, 0, cy);
/*  6422 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] ccopy(int n1, int j1, int k1, double[] cx) {
/*  6436 */     double[] cy = new double[2 * n1];
/*  6437 */     ccopy(n1, j1, k1, cx, 0, 1, cy);
/*  6438 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] ccopy(int n1, int n2, int j1, int j2, int k1, int k2, double[][] cx) {
/*  6455 */     double[][] cy = new double[n2][2 * n1];
/*  6456 */     ccopy(n1, n2, j1, j2, k1, k2, cx, 0, 0, 1, 1, cy);
/*  6457 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] ccopy(int n1, int n2, int n3, int j1, int j2, int j3, int k1, int k2, int k3, double[][][] cx) {
/*  6477 */     double[][][] cy = new double[n3][n2][2 * n1];
/*  6478 */     ccopy(n1, n2, n3, j1, j2, j3, k1, k2, k3, cx, 0, 0, 0, 1, 1, 1, cy);
/*  6479 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int j1x, double[] cx, int j1y, double[] cy) {
/*  6494 */     for (int i1 = 0, ix = 2 * j1x, iy = 2 * j1y; i1 < n1; i1++) {
/*  6495 */       cy[iy++] = cx[ix++];
/*  6496 */       cy[iy++] = cx[ix++];
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, int j1x, int j2x, double[][] cx, int j1y, int j2y, double[][] cy) {
/*  6515 */     for (int i2 = 0; i2 < n2; i2++) {
/*  6516 */       ccopy(n1, j1x, cx[j2x + i2], j1y, cy[j2y + i2]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, int n3, int j1x, int j2x, int j3x, double[][][] cx, int j1y, int j2y, int j3y, double[][][] cy) {
/*  6537 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6538 */       ccopy(n1, n2, j1x, j2x, cx[j3x + i3], j1y, j2y, cy[j3y + i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int j1x, int k1x, double[] cx, int j1y, int k1y, double[] cy) {
/*  6555 */     int k1x2 = k1x * 2;
/*  6556 */     int k1y2 = k1y * 2; int iy;
/*  6557 */     for (int i1 = 0, ix = 2 * j1x; i1 < n1; i1++, ix += k1x2, iy += k1y2) {
/*  6558 */       cy[iy] = cx[ix];
/*  6559 */       cy[iy + 1] = cx[ix + 1];
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, int j1x, int j2x, int k1x, int k2x, double[][] cx, int j1y, int j2y, int k1y, int k2y, double[][] cy) {
/*  6582 */     for (int i2 = 0; i2 < n2; i2++) {
/*  6583 */       ccopy(n1, j1x, k1x, cx[j2x + i2 * k2x], j1y, k1y, cy[j2y + i2 * k2y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void ccopy(int n1, int n2, int n3, int j1x, int j2x, int j3x, int k1x, int k2x, int k3x, double[][][] cx, int j1y, int j2y, int j3y, int k1y, int k2y, int k3y, double[][][] cy) {
/*  6610 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6611 */       ccopy(n1, n2, j1x, j2x, k1x, k2x, cx[j3x + i3 * k3x], j1y, j2y, k1y, k2y, cy[j3y + i3 * k3y]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[] reverse(byte[] rx) {
/*  6620 */     byte[] ry = new byte[rx.length];
/*  6621 */     reverse(rx, ry);
/*  6622 */     return ry;
/*       */   }
/*       */   
/*       */   public static void reverse(byte[] rx, byte[] ry) {
/*  6626 */     int n1 = rx.length;
/*  6627 */     for (int i1 = 0, j1 = n1 - 1; i1 < n1; i1++, j1--)
/*  6628 */       ry[j1] = rx[i1]; 
/*       */   }
/*       */   
/*       */   public static short[] reverse(short[] rx) {
/*  6632 */     short[] ry = new short[rx.length];
/*  6633 */     reverse(rx, ry);
/*  6634 */     return ry;
/*       */   }
/*       */   
/*       */   public static void reverse(short[] rx, short[] ry) {
/*  6638 */     int n1 = rx.length;
/*  6639 */     for (int i1 = 0, j1 = n1 - 1; i1 < n1; i1++, j1--)
/*  6640 */       ry[j1] = rx[i1]; 
/*       */   }
/*       */   
/*       */   public static int[] reverse(int[] rx) {
/*  6644 */     int[] ry = new int[rx.length];
/*  6645 */     reverse(rx, ry);
/*  6646 */     return ry;
/*       */   }
/*       */   
/*       */   public static void reverse(int[] rx, int[] ry) {
/*  6650 */     int n1 = rx.length;
/*  6651 */     for (int i1 = 0, j1 = n1 - 1; i1 < n1; i1++, j1--)
/*  6652 */       ry[j1] = rx[i1]; 
/*       */   }
/*       */   
/*       */   public static long[] reverse(long[] rx) {
/*  6656 */     long[] ry = new long[rx.length];
/*  6657 */     reverse(rx, ry);
/*  6658 */     return ry;
/*       */   }
/*       */   
/*       */   public static void reverse(long[] rx, long[] ry) {
/*  6662 */     int n1 = rx.length;
/*  6663 */     for (int i1 = 0, j1 = n1 - 1; i1 < n1; i1++, j1--)
/*  6664 */       ry[j1] = rx[i1]; 
/*       */   }
/*       */   
/*       */   public static float[] reverse(float[] rx) {
/*  6668 */     float[] ry = new float[rx.length];
/*  6669 */     reverse(rx, ry);
/*  6670 */     return ry;
/*       */   }
/*       */   
/*       */   public static void reverse(float[] rx, float[] ry) {
/*  6674 */     int n1 = rx.length;
/*  6675 */     for (int i1 = 0, j1 = n1 - 1; i1 < n1; i1++, j1--)
/*  6676 */       ry[j1] = rx[i1]; 
/*       */   }
/*       */   
/*       */   public static float[] creverse(float[] rx) {
/*  6680 */     float[] ry = new float[rx.length];
/*  6681 */     reverse(rx, ry);
/*  6682 */     return ry;
/*       */   }
/*       */   
/*       */   public static void creverse(float[] rx, float[] ry) {
/*  6686 */     int n1 = rx.length / 2;
/*  6687 */     for (int i1 = 0, j1 = 2 * n1 - 2; i1 < n1; i1 += 2, j1 -= 2) {
/*  6688 */       ry[j1] = rx[i1];
/*  6689 */       ry[j1 + 1] = rx[i1 + 1];
/*       */     } 
/*       */   }
/*       */   
/*       */   public static double[] reverse(double[] rx) {
/*  6694 */     double[] ry = new double[rx.length];
/*  6695 */     reverse(rx, ry);
/*  6696 */     return ry;
/*       */   }
/*       */   
/*       */   public static void reverse(double[] rx, double[] ry) {
/*  6700 */     int n1 = rx.length;
/*  6701 */     for (int i1 = 0, j1 = n1 - 1; i1 < n1; i1++, j1--)
/*  6702 */       ry[j1] = rx[i1]; 
/*       */   }
/*       */   
/*       */   public static double[] creverse(double[] rx) {
/*  6706 */     double[] ry = new double[rx.length];
/*  6707 */     reverse(rx, ry);
/*  6708 */     return ry;
/*       */   }
/*       */   
/*       */   public static void creverse(double[] rx, double[] ry) {
/*  6712 */     int n1 = rx.length / 2;
/*  6713 */     for (int i1 = 0, j1 = 2 * n1 - 2; i1 < n1; i1 += 2, j1 -= 2) {
/*  6714 */       ry[j1] = rx[i1];
/*  6715 */       ry[j1 + 1] = rx[i1 + 1];
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[] flatten(byte[][] rx) {
/*  6728 */     int n = 0;
/*  6729 */     int n2 = rx.length;
/*  6730 */     for (int i2 = 0; i2 < n2; i2++)
/*  6731 */       n += (rx[i2]).length; 
/*  6732 */     byte[] ry = new byte[n];
/*  6733 */     for (int i = 0, iy = 0; i < n2; i++) {
/*  6734 */       int n1 = (rx[i]).length;
/*  6735 */       copy(n1, 0, rx[i], iy, ry);
/*  6736 */       iy += n1;
/*       */     } 
/*  6738 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[] flatten(byte[][][] rx) {
/*  6747 */     int n = 0;
/*  6748 */     int n3 = rx.length;
/*  6749 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6750 */       int n2 = (rx[i3]).length;
/*  6751 */       for (int i2 = 0; i2 < n2; i2++)
/*  6752 */         n += (rx[i3][i2]).length; 
/*       */     } 
/*  6754 */     byte[] ry = new byte[n];
/*  6755 */     for (int i = 0, iy = 0; i < n3; i++) {
/*  6756 */       int n2 = (rx[i]).length;
/*  6757 */       for (int i2 = 0; i2 < n2; i2++) {
/*  6758 */         int n1 = (rx[i][i2]).length;
/*  6759 */         copy(n1, 0, rx[i][i2], iy, ry);
/*  6760 */         iy += n1;
/*       */       } 
/*       */     } 
/*  6763 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[] flatten(short[][] rx) {
/*  6772 */     int n = 0;
/*  6773 */     int n2 = rx.length;
/*  6774 */     for (int i2 = 0; i2 < n2; i2++)
/*  6775 */       n += (rx[i2]).length; 
/*  6776 */     short[] ry = new short[n];
/*  6777 */     for (int i = 0, iy = 0; i < n2; i++) {
/*  6778 */       int n1 = (rx[i]).length;
/*  6779 */       copy(n1, 0, rx[i], iy, ry);
/*  6780 */       iy += n1;
/*       */     } 
/*  6782 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[] flatten(short[][][] rx) {
/*  6791 */     int n = 0;
/*  6792 */     int n3 = rx.length;
/*  6793 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6794 */       int n2 = (rx[i3]).length;
/*  6795 */       for (int i2 = 0; i2 < n2; i2++)
/*  6796 */         n += (rx[i3][i2]).length; 
/*       */     } 
/*  6798 */     short[] ry = new short[n];
/*  6799 */     for (int i = 0, iy = 0; i < n3; i++) {
/*  6800 */       int n2 = (rx[i]).length;
/*  6801 */       for (int i2 = 0; i2 < n2; i2++) {
/*  6802 */         int n1 = (rx[i][i2]).length;
/*  6803 */         copy(n1, 0, rx[i][i2], iy, ry);
/*  6804 */         iy += n1;
/*       */       } 
/*       */     } 
/*  6807 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] flatten(int[][] rx) {
/*  6816 */     int n = 0;
/*  6817 */     int n2 = rx.length;
/*  6818 */     for (int i2 = 0; i2 < n2; i2++)
/*  6819 */       n += (rx[i2]).length; 
/*  6820 */     int[] ry = new int[n];
/*  6821 */     for (int i = 0, iy = 0; i < n2; i++) {
/*  6822 */       int n1 = (rx[i]).length;
/*  6823 */       copy(n1, 0, rx[i], iy, ry);
/*  6824 */       iy += n1;
/*       */     } 
/*  6826 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[] flatten(int[][][] rx) {
/*  6835 */     int n = 0;
/*  6836 */     int n3 = rx.length;
/*  6837 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6838 */       int n2 = (rx[i3]).length;
/*  6839 */       for (int i2 = 0; i2 < n2; i2++)
/*  6840 */         n += (rx[i3][i2]).length; 
/*       */     } 
/*  6842 */     int[] ry = new int[n];
/*  6843 */     for (int i = 0, iy = 0; i < n3; i++) {
/*  6844 */       int n2 = (rx[i]).length;
/*  6845 */       for (int i2 = 0; i2 < n2; i2++) {
/*  6846 */         int n1 = (rx[i][i2]).length;
/*  6847 */         copy(n1, 0, rx[i][i2], iy, ry);
/*  6848 */         iy += n1;
/*       */       } 
/*       */     } 
/*  6851 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] flatten(long[][] rx) {
/*  6860 */     int n = 0;
/*  6861 */     int n2 = rx.length;
/*  6862 */     for (int i2 = 0; i2 < n2; i2++)
/*  6863 */       n += (rx[i2]).length; 
/*  6864 */     long[] ry = new long[n];
/*  6865 */     for (int i = 0, iy = 0; i < n2; i++) {
/*  6866 */       int n1 = (rx[i]).length;
/*  6867 */       copy(n1, 0, rx[i], iy, ry);
/*  6868 */       iy += n1;
/*       */     } 
/*  6870 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[] flatten(long[][][] rx) {
/*  6879 */     int n = 0;
/*  6880 */     int n3 = rx.length;
/*  6881 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6882 */       int n2 = (rx[i3]).length;
/*  6883 */       for (int i2 = 0; i2 < n2; i2++)
/*  6884 */         n += (rx[i3][i2]).length; 
/*       */     } 
/*  6886 */     long[] ry = new long[n];
/*  6887 */     for (int i = 0, iy = 0; i < n3; i++) {
/*  6888 */       int n2 = (rx[i]).length;
/*  6889 */       for (int i2 = 0; i2 < n2; i2++) {
/*  6890 */         int n1 = (rx[i][i2]).length;
/*  6891 */         copy(n1, 0, rx[i][i2], iy, ry);
/*  6892 */         iy += n1;
/*       */       } 
/*       */     } 
/*  6895 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] flatten(float[][] rx) {
/*  6904 */     int n = 0;
/*  6905 */     int n2 = rx.length;
/*  6906 */     for (int i2 = 0; i2 < n2; i2++)
/*  6907 */       n += (rx[i2]).length; 
/*  6908 */     float[] ry = new float[n];
/*  6909 */     for (int i = 0, iy = 0; i < n2; i++) {
/*  6910 */       int n1 = (rx[i]).length;
/*  6911 */       copy(n1, 0, rx[i], iy, ry);
/*  6912 */       iy += n1;
/*       */     } 
/*  6914 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] flatten(float[][][] rx) {
/*  6923 */     int n = 0;
/*  6924 */     int n3 = rx.length;
/*  6925 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6926 */       int n2 = (rx[i3]).length;
/*  6927 */       for (int i2 = 0; i2 < n2; i2++)
/*  6928 */         n += (rx[i3][i2]).length; 
/*       */     } 
/*  6930 */     float[] ry = new float[n];
/*  6931 */     for (int i = 0, iy = 0; i < n3; i++) {
/*  6932 */       int n2 = (rx[i]).length;
/*  6933 */       for (int i2 = 0; i2 < n2; i2++) {
/*  6934 */         int n1 = (rx[i][i2]).length;
/*  6935 */         copy(n1, 0, rx[i][i2], iy, ry);
/*  6936 */         iy += n1;
/*       */       } 
/*       */     } 
/*  6939 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] cflatten(float[][] cx) {
/*  6948 */     return flatten(cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] cflatten(float[][][] cx) {
/*  6957 */     return flatten(cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] flatten(double[][] rx) {
/*  6966 */     int n = 0;
/*  6967 */     int n2 = rx.length;
/*  6968 */     for (int i2 = 0; i2 < n2; i2++)
/*  6969 */       n += (rx[i2]).length; 
/*  6970 */     double[] ry = new double[n];
/*  6971 */     for (int i = 0, iy = 0; i < n2; i++) {
/*  6972 */       int n1 = (rx[i]).length;
/*  6973 */       copy(n1, 0, rx[i], iy, ry);
/*  6974 */       iy += n1;
/*       */     } 
/*  6976 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] flatten(double[][][] rx) {
/*  6985 */     int n = 0;
/*  6986 */     int n3 = rx.length;
/*  6987 */     for (int i3 = 0; i3 < n3; i3++) {
/*  6988 */       int n2 = (rx[i3]).length;
/*  6989 */       for (int i2 = 0; i2 < n2; i2++)
/*  6990 */         n += (rx[i3][i2]).length; 
/*       */     } 
/*  6992 */     double[] ry = new double[n];
/*  6993 */     for (int i = 0, iy = 0; i < n3; i++) {
/*  6994 */       int n2 = (rx[i]).length;
/*  6995 */       for (int i2 = 0; i2 < n2; i2++) {
/*  6996 */         int n1 = (rx[i][i2]).length;
/*  6997 */         copy(n1, 0, rx[i][i2], iy, ry);
/*  6998 */         iy += n1;
/*       */       } 
/*       */     } 
/*  7001 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] cflatten(double[][] cx) {
/*  7010 */     return flatten(cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[] cflatten(double[][][] cx) {
/*  7019 */     return flatten(cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][] reshape(int n1, int n2, byte[] rx) {
/*  7033 */     byte[][] ry = new byte[n2][n1];
/*  7034 */     for (int i2 = 0, ix = 0; i2 < n2; i2++) {
/*  7035 */       copy(n1, ix, rx, 0, ry[i2]);
/*  7036 */       ix += n1;
/*       */     } 
/*  7038 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][][] reshape(int n1, int n2, int n3, byte[] rx) {
/*  7050 */     byte[][][] ry = new byte[n3][n2][n1];
/*  7051 */     for (int i3 = 0, ix = 0; i3 < n3; i3++) {
/*  7052 */       for (int i2 = 0; i2 < n2; i2++) {
/*  7053 */         copy(n1, ix, rx, 0, ry[i3][i2]);
/*  7054 */         ix += n1;
/*       */       } 
/*       */     } 
/*  7057 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][] reshape(int n1, int n2, short[] rx) {
/*  7068 */     short[][] ry = new short[n2][n1];
/*  7069 */     for (int i2 = 0, ix = 0; i2 < n2; i2++) {
/*  7070 */       copy(n1, ix, rx, 0, ry[i2]);
/*  7071 */       ix += n1;
/*       */     } 
/*  7073 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][][] reshape(int n1, int n2, int n3, short[] rx) {
/*  7085 */     short[][][] ry = new short[n3][n2][n1];
/*  7086 */     for (int i3 = 0, ix = 0; i3 < n3; i3++) {
/*  7087 */       for (int i2 = 0; i2 < n2; i2++) {
/*  7088 */         copy(n1, ix, rx, 0, ry[i3][i2]);
/*  7089 */         ix += n1;
/*       */       } 
/*       */     } 
/*  7092 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] reshape(int n1, int n2, int[] rx) {
/*  7103 */     int[][] ry = new int[n2][n1];
/*  7104 */     for (int i2 = 0, ix = 0; i2 < n2; i2++) {
/*  7105 */       copy(n1, ix, rx, 0, ry[i2]);
/*  7106 */       ix += n1;
/*       */     } 
/*  7108 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][][] reshape(int n1, int n2, int n3, int[] rx) {
/*  7120 */     int[][][] ry = new int[n3][n2][n1];
/*  7121 */     for (int i3 = 0, ix = 0; i3 < n3; i3++) {
/*  7122 */       for (int i2 = 0; i2 < n2; i2++) {
/*  7123 */         copy(n1, ix, rx, 0, ry[i3][i2]);
/*  7124 */         ix += n1;
/*       */       } 
/*       */     } 
/*  7127 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] reshape(int n1, int n2, long[] rx) {
/*  7138 */     long[][] ry = new long[n2][n1];
/*  7139 */     for (int i2 = 0, ix = 0; i2 < n2; i2++) {
/*  7140 */       copy(n1, ix, rx, 0, ry[i2]);
/*  7141 */       ix += n1;
/*       */     } 
/*  7143 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][][] reshape(int n1, int n2, int n3, long[] rx) {
/*  7155 */     long[][][] ry = new long[n3][n2][n1];
/*  7156 */     for (int i3 = 0, ix = 0; i3 < n3; i3++) {
/*  7157 */       for (int i2 = 0; i2 < n2; i2++) {
/*  7158 */         copy(n1, ix, rx, 0, ry[i3][i2]);
/*  7159 */         ix += n1;
/*       */       } 
/*       */     } 
/*  7162 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] reshape(int n1, int n2, float[] rx) {
/*  7173 */     float[][] ry = new float[n2][n1];
/*  7174 */     for (int i2 = 0, ix = 0; i2 < n2; i2++) {
/*  7175 */       copy(n1, ix, rx, 0, ry[i2]);
/*  7176 */       ix += n1;
/*       */     } 
/*  7178 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] reshape(int n1, int n2, int n3, float[] rx) {
/*  7190 */     float[][][] ry = new float[n3][n2][n1];
/*  7191 */     for (int i3 = 0, ix = 0; i3 < n3; i3++) {
/*  7192 */       for (int i2 = 0; i2 < n2; i2++) {
/*  7193 */         copy(n1, ix, rx, 0, ry[i3][i2]);
/*  7194 */         ix += n1;
/*       */       } 
/*       */     } 
/*  7197 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] creshape(int n1, int n2, float[] cx) {
/*  7208 */     return reshape(2 * n1, n2, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][][] creshape(int n1, int n2, int n3, float[] cx) {
/*  7220 */     return reshape(2 * n1, n2, n3, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] reshape(int n1, int n2, double[] rx) {
/*  7231 */     double[][] ry = new double[n2][n1];
/*  7232 */     for (int i2 = 0, ix = 0; i2 < n2; i2++) {
/*  7233 */       copy(n1, ix, rx, 0, ry[i2]);
/*  7234 */       ix += n1;
/*       */     } 
/*  7236 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] reshape(int n1, int n2, int n3, double[] rx) {
/*  7248 */     double[][][] ry = new double[n3][n2][n1];
/*  7249 */     for (int i3 = 0, ix = 0; i3 < n3; i3++) {
/*  7250 */       for (int i2 = 0; i2 < n2; i2++) {
/*  7251 */         copy(n1, ix, rx, 0, ry[i3][i2]);
/*  7252 */         ix += n1;
/*       */       } 
/*       */     } 
/*  7255 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] creshape(int n1, int n2, double[] cx) {
/*  7266 */     return reshape(2 * n1, n2, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][][] creshape(int n1, int n2, int n3, double[] cx) {
/*  7278 */     return reshape(2 * n1, n2, n3, cx);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte[][] transpose(byte[][] rx) {
/*  7290 */     int n2 = rx.length;
/*  7291 */     int n1 = (rx[0]).length;
/*  7292 */     byte[][] ry = new byte[n1][n2];
/*  7293 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7294 */       for (int i1 = 0; i1 < n1; i1++) {
/*  7295 */         ry[i1][i2] = rx[i2][i1];
/*       */       }
/*       */     } 
/*  7298 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static short[][] transpose(short[][] rx) {
/*  7307 */     int n2 = rx.length;
/*  7308 */     int n1 = (rx[0]).length;
/*  7309 */     short[][] ry = new short[n1][n2];
/*  7310 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7311 */       for (int i1 = 0; i1 < n1; i1++) {
/*  7312 */         ry[i1][i2] = rx[i2][i1];
/*       */       }
/*       */     } 
/*  7315 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int[][] transpose(int[][] rx) {
/*  7324 */     int n2 = rx.length;
/*  7325 */     int n1 = (rx[0]).length;
/*  7326 */     int[][] ry = new int[n1][n2];
/*  7327 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7328 */       for (int i1 = 0; i1 < n1; i1++) {
/*  7329 */         ry[i1][i2] = rx[i2][i1];
/*       */       }
/*       */     } 
/*  7332 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static long[][] transpose(long[][] rx) {
/*  7341 */     int n2 = rx.length;
/*  7342 */     int n1 = (rx[0]).length;
/*  7343 */     long[][] ry = new long[n1][n2];
/*  7344 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7345 */       for (int i1 = 0; i1 < n1; i1++) {
/*  7346 */         ry[i1][i2] = rx[i2][i1];
/*       */       }
/*       */     } 
/*  7349 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] transpose(float[][] rx) {
/*  7358 */     int n2 = rx.length;
/*  7359 */     int n1 = (rx[0]).length;
/*  7360 */     float[][] ry = new float[n1][n2];
/*  7361 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7362 */       for (int i1 = 0; i1 < n1; i1++) {
/*  7363 */         ry[i1][i2] = rx[i2][i1];
/*       */       }
/*       */     } 
/*  7366 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[][] ctranspose(float[][] cx) {
/*  7375 */     int n2 = cx.length;
/*  7376 */     int n1 = (cx[0]).length / 2;
/*  7377 */     float[][] cy = new float[n1][2 * n2];
/*  7378 */     for (int i2 = 0, iy = 0; i2 < n2; i2++, iy += 2) {
/*  7379 */       for (int i1 = 0, ix = 0; i1 < n1; i1++, ix += 2) {
/*  7380 */         cy[i1][iy] = cx[i2][ix];
/*  7381 */         cy[i1][iy + 1] = cx[i2][ix + 1];
/*       */       } 
/*       */     } 
/*  7384 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] transpose(double[][] rx) {
/*  7393 */     int n2 = rx.length;
/*  7394 */     int n1 = (rx[0]).length;
/*  7395 */     double[][] ry = new double[n1][n2];
/*  7396 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7397 */       for (int i1 = 0; i1 < n1; i1++) {
/*  7398 */         ry[i1][i2] = rx[i2][i1];
/*       */       }
/*       */     } 
/*  7401 */     return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static double[][] ctranspose(double[][] cx) {
/*  7410 */     int n2 = cx.length;
/*  7411 */     int n1 = (cx[0]).length / 2;
/*  7412 */     double[][] cy = new double[n1][2 * n2];
/*  7413 */     for (int i2 = 0, iy = 0; i2 < n2; i2++, iy += 2) {
/*  7414 */       for (int i1 = 0, ix = 0; i1 < n1; i1++, ix += 2) {
/*  7415 */         cy[i1][iy] = cx[i2][ix];
/*  7416 */         cy[i1][iy + 1] = cx[i2][ix + 1];
/*       */       } 
/*       */     } 
/*  7419 */     return cy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(byte[] x, byte[] y) {
/*  7432 */     return (x != y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(byte[][] x, byte[][] y) {
/*  7442 */     if (x == y)
/*  7443 */       return false; 
/*  7444 */     int n2 = x.length;
/*  7445 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7446 */       if (!distinct(x[i2], y[i2]))
/*  7447 */         return false; 
/*  7448 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(byte[][][] x, byte[][][] y) {
/*  7458 */     if (x == y)
/*  7459 */       return false; 
/*  7460 */     int n3 = x.length;
/*  7461 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7462 */       if (!distinct(x[i3], y[i3]))
/*  7463 */         return false; 
/*  7464 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(short[] x, short[] y) {
/*  7474 */     return (x != y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(short[][] x, short[][] y) {
/*  7484 */     if (x == y)
/*  7485 */       return false; 
/*  7486 */     int n2 = x.length;
/*  7487 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7488 */       if (!distinct(x[i2], y[i2]))
/*  7489 */         return false; 
/*  7490 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(short[][][] x, short[][][] y) {
/*  7500 */     if (x == y)
/*  7501 */       return false; 
/*  7502 */     int n3 = x.length;
/*  7503 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7504 */       if (!distinct(x[i3], y[i3]))
/*  7505 */         return false; 
/*  7506 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(int[] x, int[] y) {
/*  7516 */     return (x != y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(int[][] x, int[][] y) {
/*  7526 */     if (x == y)
/*  7527 */       return false; 
/*  7528 */     int n2 = x.length;
/*  7529 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7530 */       if (!distinct(x[i2], y[i2]))
/*  7531 */         return false; 
/*  7532 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(int[][][] x, int[][][] y) {
/*  7542 */     if (x == y)
/*  7543 */       return false; 
/*  7544 */     int n3 = x.length;
/*  7545 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7546 */       if (!distinct(x[i3], y[i3]))
/*  7547 */         return false; 
/*  7548 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(long[] x, long[] y) {
/*  7558 */     return (x != y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(long[][] x, long[][] y) {
/*  7568 */     if (x == y)
/*  7569 */       return false; 
/*  7570 */     int n2 = x.length;
/*  7571 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7572 */       if (!distinct(x[i2], y[i2]))
/*  7573 */         return false; 
/*  7574 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(long[][][] x, long[][][] y) {
/*  7584 */     if (x == y)
/*  7585 */       return false; 
/*  7586 */     int n3 = x.length;
/*  7587 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7588 */       if (!distinct(x[i3], y[i3]))
/*  7589 */         return false; 
/*  7590 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(float[] x, float[] y) {
/*  7600 */     return (x != y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(float[][] x, float[][] y) {
/*  7610 */     if (x == y)
/*  7611 */       return false; 
/*  7612 */     int n2 = x.length;
/*  7613 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7614 */       if (!distinct(x[i2], y[i2]))
/*  7615 */         return false; 
/*  7616 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(float[][][] x, float[][][] y) {
/*  7626 */     if (x == y)
/*  7627 */       return false; 
/*  7628 */     int n3 = x.length;
/*  7629 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7630 */       if (!distinct(x[i3], y[i3]))
/*  7631 */         return false; 
/*  7632 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(double[] x, double[] y) {
/*  7642 */     return (x != y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(double[][] x, double[][] y) {
/*  7652 */     if (x == y)
/*  7653 */       return false; 
/*  7654 */     int n2 = x.length;
/*  7655 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7656 */       if (!distinct(x[i2], y[i2]))
/*  7657 */         return false; 
/*  7658 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean distinct(double[][][] x, double[][][] y) {
/*  7668 */     if (x == y)
/*  7669 */       return false; 
/*  7670 */     int n3 = x.length;
/*  7671 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7672 */       if (!distinct(x[i3], y[i3]))
/*  7673 */         return false; 
/*  7674 */     }  return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(byte[] rx, byte[] ry) {
/*  7687 */     int n1 = rx.length;
/*  7688 */     for (int i1 = 0; i1 < n1; i1++) {
/*  7689 */       if (rx[i1] != ry[i1])
/*  7690 */         return false; 
/*       */     } 
/*  7692 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(byte[][] rx, byte[][] ry) {
/*  7702 */     int n2 = rx.length;
/*  7703 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7704 */       if (!equal(rx[i2], ry[i2]))
/*  7705 */         return false; 
/*       */     } 
/*  7707 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(byte[][][] rx, byte[][][] ry) {
/*  7717 */     int n3 = rx.length;
/*  7718 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7719 */       if (!equal(rx[i3], ry[i3]))
/*  7720 */         return false; 
/*       */     } 
/*  7722 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(short[] rx, short[] ry) {
/*  7732 */     int n1 = rx.length;
/*  7733 */     for (int i1 = 0; i1 < n1; i1++) {
/*  7734 */       if (rx[i1] != ry[i1])
/*  7735 */         return false; 
/*       */     } 
/*  7737 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(short[][] rx, short[][] ry) {
/*  7747 */     int n2 = rx.length;
/*  7748 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7749 */       if (!equal(rx[i2], ry[i2]))
/*  7750 */         return false; 
/*       */     } 
/*  7752 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(short[][][] rx, short[][][] ry) {
/*  7762 */     int n3 = rx.length;
/*  7763 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7764 */       if (!equal(rx[i3], ry[i3]))
/*  7765 */         return false; 
/*       */     } 
/*  7767 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(int[] rx, int[] ry) {
/*  7777 */     int n1 = rx.length;
/*  7778 */     for (int i1 = 0; i1 < n1; i1++) {
/*  7779 */       if (rx[i1] != ry[i1])
/*  7780 */         return false; 
/*       */     } 
/*  7782 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(int[][] rx, int[][] ry) {
/*  7792 */     int n2 = rx.length;
/*  7793 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7794 */       if (!equal(rx[i2], ry[i2]))
/*  7795 */         return false; 
/*       */     } 
/*  7797 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(int[][][] rx, int[][][] ry) {
/*  7807 */     int n3 = rx.length;
/*  7808 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7809 */       if (!equal(rx[i3], ry[i3]))
/*  7810 */         return false; 
/*       */     } 
/*  7812 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(long[] rx, long[] ry) {
/*  7822 */     int n1 = rx.length;
/*  7823 */     for (int i1 = 0; i1 < n1; i1++) {
/*  7824 */       if (rx[i1] != ry[i1])
/*  7825 */         return false; 
/*       */     } 
/*  7827 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(long[][] rx, long[][] ry) {
/*  7837 */     int n2 = rx.length;
/*  7838 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7839 */       if (!equal(rx[i2], ry[i2]))
/*  7840 */         return false; 
/*       */     } 
/*  7842 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(long[][][] rx, long[][][] ry) {
/*  7852 */     int n3 = rx.length;
/*  7853 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7854 */       if (!equal(rx[i3], ry[i3]))
/*  7855 */         return false; 
/*       */     } 
/*  7857 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(float[] rx, float[] ry) {
/*  7867 */     int n1 = rx.length;
/*  7868 */     for (int i1 = 0; i1 < n1; i1++) {
/*  7869 */       if (rx[i1] != ry[i1])
/*  7870 */         return false; 
/*       */     } 
/*  7872 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(float[][] rx, float[][] ry) {
/*  7882 */     int n2 = rx.length;
/*  7883 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7884 */       if (!equal(rx[i2], ry[i2]))
/*  7885 */         return false; 
/*       */     } 
/*  7887 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(float[][][] rx, float[][][] ry) {
/*  7897 */     int n3 = rx.length;
/*  7898 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7899 */       if (!equal(rx[i3], ry[i3]))
/*  7900 */         return false; 
/*       */     } 
/*  7902 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(float tolerance, float[] rx, float[] ry) {
/*  7914 */     int n1 = rx.length;
/*  7915 */     for (int i1 = 0; i1 < n1; i1++) {
/*  7916 */       if (!equal(tolerance, rx[i1], ry[i1]))
/*  7917 */         return false; 
/*       */     } 
/*  7919 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(float tolerance, float[][] rx, float[][] ry) {
/*  7931 */     int n2 = rx.length;
/*  7932 */     for (int i2 = 0; i2 < n2; i2++) {
/*  7933 */       if (!equal(tolerance, rx[i2], ry[i2]))
/*  7934 */         return false; 
/*       */     } 
/*  7936 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(float tolerance, float[][][] rx, float[][][] ry) {
/*  7949 */     int n3 = rx.length;
/*  7950 */     for (int i3 = 0; i3 < n3; i3++) {
/*  7951 */       if (!equal(tolerance, rx[i3], ry[i3]))
/*  7952 */         return false; 
/*       */     } 
/*  7954 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(float[] cx, float[] cy) {
/*  7964 */     return equal(cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(float[][] cx, float[][] cy) {
/*  7974 */     return equal(cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(float[][][] cx, float[][][] cy) {
/*  7984 */     return equal(cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(float tolerance, float[] cx, float[] cy) {
/*  7996 */     return equal(tolerance, cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(float tolerance, float[][] cx, float[][] cy) {
/*  8008 */     return equal(tolerance, cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(float tolerance, float[][][] cx, float[][][] cy) {
/*  8021 */     return equal(tolerance, cx, cy);
/*       */   }
/*       */   private static boolean equal(float tolerance, float ra, float rb) {
/*  8024 */     float val = abs(ra - rb);
/*  8025 */     return (val <= tolerance || Float.isNaN(val));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(double[] rx, double[] ry) {
/*  8035 */     int n1 = rx.length;
/*  8036 */     for (int i1 = 0; i1 < n1; i1++) {
/*  8037 */       if (rx[i1] != ry[i1])
/*  8038 */         return false; 
/*       */     } 
/*  8040 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(double[][] rx, double[][] ry) {
/*  8050 */     int n2 = rx.length;
/*  8051 */     for (int i2 = 0; i2 < n2; i2++) {
/*  8052 */       if (!equal(rx[i2], ry[i2]))
/*  8053 */         return false; 
/*       */     } 
/*  8055 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(double[][][] rx, double[][][] ry) {
/*  8065 */     int n3 = rx.length;
/*  8066 */     for (int i3 = 0; i3 < n3; i3++) {
/*  8067 */       if (!equal(rx[i3], ry[i3]))
/*  8068 */         return false; 
/*       */     } 
/*  8070 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(double tolerance, double[] rx, double[] ry) {
/*  8082 */     int n1 = rx.length;
/*  8083 */     for (int i1 = 0; i1 < n1; i1++) {
/*  8084 */       if (!equal(tolerance, rx[i1], ry[i1]))
/*  8085 */         return false; 
/*       */     } 
/*  8087 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(double tolerance, double[][] rx, double[][] ry) {
/*  8099 */     int n2 = rx.length;
/*  8100 */     for (int i2 = 0; i2 < n2; i2++) {
/*  8101 */       if (!equal(tolerance, rx[i2], ry[i2]))
/*  8102 */         return false; 
/*       */     } 
/*  8104 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean equal(double tolerance, double[][][] rx, double[][][] ry) {
/*  8117 */     int n3 = rx.length;
/*  8118 */     for (int i3 = 0; i3 < n3; i3++) {
/*  8119 */       if (!equal(tolerance, rx[i3], ry[i3]))
/*  8120 */         return false; 
/*       */     } 
/*  8122 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(double[] cx, double[] cy) {
/*  8132 */     return equal(cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(double[][] cx, double[][] cy) {
/*  8142 */     return equal(cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(double[][][] cx, double[][][] cy) {
/*  8152 */     return equal(cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(double tolerance, double[] cx, double[] cy) {
/*  8164 */     return equal(tolerance, cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(double tolerance, double[][] cx, double[][] cy) {
/*  8176 */     return equal(tolerance, cx, cy);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean cequal(double tolerance, double[][][] cx, double[][][] cy) {
/*  8189 */     return equal(tolerance, cx, cy);
/*       */   }
/*       */   private static boolean equal(double tolerance, double ra, double rb) {
/*  8192 */     double val = abs(ra - rb);
/*  8193 */     return (val <= tolerance || Double.isNaN(val));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isRegular(byte[][] a) {
/*  8207 */     int n2 = a.length;
/*  8208 */     int n1 = (a[0]).length;
/*  8209 */     for (int i2 = 1; i2 < n2; i2++) {
/*  8210 */       if ((a[i2]).length != n1)
/*  8211 */         return false; 
/*       */     } 
/*  8213 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isRegular(byte[][][] a) {
/*  8224 */     int n3 = a.length;
/*  8225 */     int n2 = (a[0]).length;
/*  8226 */     for (int i3 = 0; i3 < n3; i3++) {
/*  8227 */       if ((a[i3]).length != n2 || !isRegular(a[i3]))
/*  8228 */         return false; 
/*       */     } 
/*  8230 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isRegular(short[][] a) {
/*  8241 */     int n2 = a.length;
/*  8242 */     int n1 = (a[0]).length;
/*  8243 */     for (int i2 = 1; i2 < n2; i2++) {
/*  8244 */       if ((a[i2]).length != n1)
/*  8245 */         return false; 
/*       */     } 
/*  8247 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isRegular(short[][][] a) {
/*  8258 */     int n3 = a.length;
/*  8259 */     int n2 = (a[0]).length;
/*  8260 */     for (int i3 = 0; i3 < n3; i3++) {
/*  8261 */       if ((a[i3]).length != n2 || !isRegular(a[i3]))
/*  8262 */         return false; 
/*       */     } 
/*  8264 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isRegular(int[][] a) {
/*  8275 */     int n2 = a.length;
/*  8276 */     int n1 = (a[0]).length;
/*  8277 */     for (int i2 = 1; i2 < n2; i2++) {
/*  8278 */       if ((a[i2]).length != n1)
/*  8279 */         return false; 
/*       */     } 
/*  8281 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isRegular(int[][][] a) {
/*  8292 */     int n3 = a.length;
/*  8293 */     int n2 = (a[0]).length;
/*  8294 */     for (int i3 = 0; i3 < n3; i3++) {
/*  8295 */       if ((a[i3]).length != n2 || !isRegular(a[i3]))
/*  8296 */         return false; 
/*       */     } 
/*  8298 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isRegular(float[][] a) {
/*  8309 */     int n2 = a.length;
/*  8310 */     int n1 = (a[0]).length;
/*  8311 */     for (int i2 = 1; i2 < n2; i2++) {
/*  8312 */       if ((a[i2]).length != n1)
/*  8313 */         return false; 
/*       */     } 
/*  8315 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isRegular(float[][][] a) {
/*  8326 */     int n3 = a.length;
/*  8327 */     int n2 = (a[0]).length;
/*  8328 */     for (int i3 = 0; i3 < n3; i3++) {
/*  8329 */       if ((a[i3]).length != n2 || !isRegular(a[i3]))
/*  8330 */         return false; 
/*       */     } 
/*  8332 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isRegular(double[][] a) {
/*  8343 */     int n2 = a.length;
/*  8344 */     int n1 = (a[0]).length;
/*  8345 */     for (int i2 = 1; i2 < n2; i2++) {
/*  8346 */       if ((a[i2]).length != n1)
/*  8347 */         return false; 
/*       */     } 
/*  8349 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isRegular(double[][][] a) {
/*  8360 */     int n3 = a.length;
/*  8361 */     int n2 = (a[0]).length;
/*  8362 */     for (int i3 = 0; i3 < n3; i3++) {
/*  8363 */       if ((a[i3]).length != n2 || !isRegular(a[i3]))
/*  8364 */         return false; 
/*       */     } 
/*  8366 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isIncreasing(byte[] a) {
/*  8380 */     int n = a.length;
/*  8381 */     if (n > 1)
/*  8382 */       for (int i = 1; i < n; i++) {
/*  8383 */         if (a[i - 1] >= a[i]) {
/*  8384 */           return false;
/*       */         }
/*       */       }  
/*  8387 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isDecreasing(byte[] a) {
/*  8398 */     int n = a.length;
/*  8399 */     if (n > 1)
/*  8400 */       for (int i = 1; i < n; i++) {
/*  8401 */         if (a[i - 1] <= a[i]) {
/*  8402 */           return false;
/*       */         }
/*       */       }  
/*  8405 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isMonotonic(byte[] a) {
/*  8416 */     return (isIncreasing(a) || isDecreasing(a));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isIncreasing(short[] a) {
/*  8427 */     int n = a.length;
/*  8428 */     if (n > 1)
/*  8429 */       for (int i = 1; i < n; i++) {
/*  8430 */         if (a[i - 1] >= a[i]) {
/*  8431 */           return false;
/*       */         }
/*       */       }  
/*  8434 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isDecreasing(short[] a) {
/*  8445 */     int n = a.length;
/*  8446 */     if (n > 1)
/*  8447 */       for (int i = 1; i < n; i++) {
/*  8448 */         if (a[i - 1] <= a[i]) {
/*  8449 */           return false;
/*       */         }
/*       */       }  
/*  8452 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isMonotonic(short[] a) {
/*  8463 */     return (isIncreasing(a) || isDecreasing(a));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isIncreasing(int[] a) {
/*  8474 */     int n = a.length;
/*  8475 */     if (n > 1)
/*  8476 */       for (int i = 1; i < n; i++) {
/*  8477 */         if (a[i - 1] >= a[i]) {
/*  8478 */           return false;
/*       */         }
/*       */       }  
/*  8481 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isDecreasing(int[] a) {
/*  8492 */     int n = a.length;
/*  8493 */     if (n > 1)
/*  8494 */       for (int i = 1; i < n; i++) {
/*  8495 */         if (a[i - 1] <= a[i]) {
/*  8496 */           return false;
/*       */         }
/*       */       }  
/*  8499 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isMonotonic(int[] a) {
/*  8510 */     return (isIncreasing(a) || isDecreasing(a));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isIncreasing(long[] a) {
/*  8521 */     int n = a.length;
/*  8522 */     if (n > 1)
/*  8523 */       for (int i = 1; i < n; i++) {
/*  8524 */         if (a[i - 1] >= a[i]) {
/*  8525 */           return false;
/*       */         }
/*       */       }  
/*  8528 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isDecreasing(long[] a) {
/*  8539 */     int n = a.length;
/*  8540 */     if (n > 1)
/*  8541 */       for (int i = 1; i < n; i++) {
/*  8542 */         if (a[i - 1] <= a[i]) {
/*  8543 */           return false;
/*       */         }
/*       */       }  
/*  8546 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isMonotonic(long[] a) {
/*  8557 */     return (isIncreasing(a) || isDecreasing(a));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isIncreasing(float[] a) {
/*  8568 */     int n = a.length;
/*  8569 */     if (n > 1)
/*  8570 */       for (int i = 1; i < n; i++) {
/*  8571 */         if (a[i - 1] >= a[i]) {
/*  8572 */           return false;
/*       */         }
/*       */       }  
/*  8575 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isDecreasing(float[] a) {
/*  8586 */     int n = a.length;
/*  8587 */     if (n > 1)
/*  8588 */       for (int i = 1; i < n; i++) {
/*  8589 */         if (a[i - 1] <= a[i]) {
/*  8590 */           return false;
/*       */         }
/*       */       }  
/*  8593 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isMonotonic(float[] a) {
/*  8604 */     return (isIncreasing(a) || isDecreasing(a));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isIncreasing(double[] a) {
/*  8615 */     int n = a.length;
/*  8616 */     if (n > 1)
/*  8617 */       for (int i = 1; i < n; i++) {
/*  8618 */         if (a[i - 1] >= a[i]) {
/*  8619 */           return false;
/*       */         }
/*       */       }  
/*  8622 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isDecreasing(double[] a) {
/*  8633 */     int n = a.length;
/*  8634 */     if (n > 1)
/*  8635 */       for (int i = 1; i < n; i++) {
/*  8636 */         if (a[i - 1] <= a[i]) {
/*  8637 */           return false;
/*       */         }
/*       */       }  
/*  8640 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isMonotonic(double[] a) {
/*  8651 */     return (isIncreasing(a) || isDecreasing(a));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickSort(byte[] a) {
/*  8663 */     int n = a.length;
/*  8664 */     if (n < 7) {
/*  8665 */       insertionSort(a, 0, n - 1);
/*       */     } else {
/*  8667 */       int[] m = new int[2];
/*  8668 */       quickSort(a, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickIndexSort(byte[] a, int[] i) {
/*  8679 */     int n = a.length;
/*  8680 */     if (n < 7) {
/*  8681 */       insertionSort(a, i, 0, n - 1);
/*       */     } else {
/*  8683 */       int[] m = new int[2];
/*  8684 */       quickSort(a, i, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialSort(int k, byte[] a) {
/*  8697 */     int n = a.length;
/*  8698 */     int p = 0;
/*  8699 */     int q = n - 1;
/*  8700 */     int[] m = (n > 7) ? new int[2] : null;
/*  8701 */     while (q - p >= 7) {
/*  8702 */       m[0] = p;
/*  8703 */       m[1] = q;
/*  8704 */       quickPartition(a, m);
/*  8705 */       if (k < m[0]) {
/*  8706 */         q = m[0] - 1; continue;
/*  8707 */       }  if (k > m[1]) {
/*  8708 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  8713 */     insertionSort(a, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialIndexSort(int k, byte[] a, int[] i) {
/*  8726 */     int n = i.length;
/*  8727 */     int p = 0;
/*  8728 */     int q = n - 1;
/*  8729 */     int[] m = (n > 7) ? new int[2] : null;
/*  8730 */     while (q - p >= 7) {
/*  8731 */       m[0] = p;
/*  8732 */       m[1] = q;
/*  8733 */       quickPartition(a, i, m);
/*  8734 */       if (k < m[0]) {
/*  8735 */         q = m[0] - 1; continue;
/*  8736 */       }  if (k > m[1]) {
/*  8737 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  8742 */     insertionSort(a, i, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickSort(short[] a) {
/*  8751 */     int n = a.length;
/*  8752 */     if (n < 7) {
/*  8753 */       insertionSort(a, 0, n - 1);
/*       */     } else {
/*  8755 */       int[] m = new int[2];
/*  8756 */       quickSort(a, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickIndexSort(short[] a, int[] i) {
/*  8767 */     int n = a.length;
/*  8768 */     if (n < 7) {
/*  8769 */       insertionSort(a, i, 0, n - 1);
/*       */     } else {
/*  8771 */       int[] m = new int[2];
/*  8772 */       quickSort(a, i, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialSort(int k, short[] a) {
/*  8785 */     int n = a.length;
/*  8786 */     int p = 0;
/*  8787 */     int q = n - 1;
/*  8788 */     int[] m = (n > 7) ? new int[2] : null;
/*  8789 */     while (q - p >= 7) {
/*  8790 */       m[0] = p;
/*  8791 */       m[1] = q;
/*  8792 */       quickPartition(a, m);
/*  8793 */       if (k < m[0]) {
/*  8794 */         q = m[0] - 1; continue;
/*  8795 */       }  if (k > m[1]) {
/*  8796 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  8801 */     insertionSort(a, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialIndexSort(int k, short[] a, int[] i) {
/*  8814 */     int n = i.length;
/*  8815 */     int p = 0;
/*  8816 */     int q = n - 1;
/*  8817 */     int[] m = (n > 7) ? new int[2] : null;
/*  8818 */     while (q - p >= 7) {
/*  8819 */       m[0] = p;
/*  8820 */       m[1] = q;
/*  8821 */       quickPartition(a, i, m);
/*  8822 */       if (k < m[0]) {
/*  8823 */         q = m[0] - 1; continue;
/*  8824 */       }  if (k > m[1]) {
/*  8825 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  8830 */     insertionSort(a, i, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickSort(int[] a) {
/*  8839 */     int n = a.length;
/*  8840 */     if (n < 7) {
/*  8841 */       insertionSort(a, 0, n - 1);
/*       */     } else {
/*  8843 */       int[] m = new int[2];
/*  8844 */       quickSort(a, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickIndexSort(int[] a, int[] i) {
/*  8855 */     int n = a.length;
/*  8856 */     if (n < 7) {
/*  8857 */       insertionSort(a, i, 0, n - 1);
/*       */     } else {
/*  8859 */       int[] m = new int[2];
/*  8860 */       quickSort(a, i, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialSort(int k, int[] a) {
/*  8873 */     int n = a.length;
/*  8874 */     int p = 0;
/*  8875 */     int q = n - 1;
/*  8876 */     int[] m = (n > 7) ? new int[2] : null;
/*  8877 */     while (q - p >= 7) {
/*  8878 */       m[0] = p;
/*  8879 */       m[1] = q;
/*  8880 */       quickPartition(a, m);
/*  8881 */       if (k < m[0]) {
/*  8882 */         q = m[0] - 1; continue;
/*  8883 */       }  if (k > m[1]) {
/*  8884 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  8889 */     insertionSort(a, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialIndexSort(int k, int[] a, int[] i) {
/*  8902 */     int n = i.length;
/*  8903 */     int p = 0;
/*  8904 */     int q = n - 1;
/*  8905 */     int[] m = (n > 7) ? new int[2] : null;
/*  8906 */     while (q - p >= 7) {
/*  8907 */       m[0] = p;
/*  8908 */       m[1] = q;
/*  8909 */       quickPartition(a, i, m);
/*  8910 */       if (k < m[0]) {
/*  8911 */         q = m[0] - 1; continue;
/*  8912 */       }  if (k > m[1]) {
/*  8913 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  8918 */     insertionSort(a, i, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickSort(long[] a) {
/*  8927 */     int n = a.length;
/*  8928 */     if (n < 7) {
/*  8929 */       insertionSort(a, 0, n - 1);
/*       */     } else {
/*  8931 */       int[] m = new int[2];
/*  8932 */       quickSort(a, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickIndexSort(long[] a, int[] i) {
/*  8943 */     int n = a.length;
/*  8944 */     if (n < 7) {
/*  8945 */       insertionSort(a, i, 0, n - 1);
/*       */     } else {
/*  8947 */       int[] m = new int[2];
/*  8948 */       quickSort(a, i, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialSort(int k, long[] a) {
/*  8961 */     int n = a.length;
/*  8962 */     int p = 0;
/*  8963 */     int q = n - 1;
/*  8964 */     int[] m = (n > 7) ? new int[2] : null;
/*  8965 */     while (q - p >= 7) {
/*  8966 */       m[0] = p;
/*  8967 */       m[1] = q;
/*  8968 */       quickPartition(a, m);
/*  8969 */       if (k < m[0]) {
/*  8970 */         q = m[0] - 1; continue;
/*  8971 */       }  if (k > m[1]) {
/*  8972 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  8977 */     insertionSort(a, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialIndexSort(int k, long[] a, int[] i) {
/*  8990 */     int n = i.length;
/*  8991 */     int p = 0;
/*  8992 */     int q = n - 1;
/*  8993 */     int[] m = (n > 7) ? new int[2] : null;
/*  8994 */     while (q - p >= 7) {
/*  8995 */       m[0] = p;
/*  8996 */       m[1] = q;
/*  8997 */       quickPartition(a, i, m);
/*  8998 */       if (k < m[0]) {
/*  8999 */         q = m[0] - 1; continue;
/*  9000 */       }  if (k > m[1]) {
/*  9001 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  9006 */     insertionSort(a, i, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickSort(float[] a) {
/*  9015 */     int n = a.length;
/*  9016 */     if (n < 7) {
/*  9017 */       insertionSort(a, 0, n - 1);
/*       */     } else {
/*  9019 */       int[] m = new int[2];
/*  9020 */       quickSort(a, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickIndexSort(float[] a, int[] i) {
/*  9031 */     int n = a.length;
/*  9032 */     if (n < 7) {
/*  9033 */       insertionSort(a, i, 0, n - 1);
/*       */     } else {
/*  9035 */       int[] m = new int[2];
/*  9036 */       quickSort(a, i, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialSort(int k, float[] a) {
/*  9049 */     int n = a.length;
/*  9050 */     int p = 0;
/*  9051 */     int q = n - 1;
/*  9052 */     int[] m = (n > 7) ? new int[2] : null;
/*  9053 */     while (q - p >= 7) {
/*  9054 */       m[0] = p;
/*  9055 */       m[1] = q;
/*  9056 */       quickPartition(a, m);
/*  9057 */       if (k < m[0]) {
/*  9058 */         q = m[0] - 1; continue;
/*  9059 */       }  if (k > m[1]) {
/*  9060 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  9065 */     insertionSort(a, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialIndexSort(int k, float[] a, int[] i) {
/*  9078 */     int n = i.length;
/*  9079 */     int p = 0;
/*  9080 */     int q = n - 1;
/*  9081 */     int[] m = (n > 7) ? new int[2] : null;
/*  9082 */     while (q - p >= 7) {
/*  9083 */       m[0] = p;
/*  9084 */       m[1] = q;
/*  9085 */       quickPartition(a, i, m);
/*  9086 */       if (k < m[0]) {
/*  9087 */         q = m[0] - 1; continue;
/*  9088 */       }  if (k > m[1]) {
/*  9089 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  9094 */     insertionSort(a, i, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickSort(double[] a) {
/*  9103 */     int n = a.length;
/*  9104 */     if (n < 7) {
/*  9105 */       insertionSort(a, 0, n - 1);
/*       */     } else {
/*  9107 */       int[] m = new int[2];
/*  9108 */       quickSort(a, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickIndexSort(double[] a, int[] i) {
/*  9119 */     int n = a.length;
/*  9120 */     if (n < 7) {
/*  9121 */       insertionSort(a, i, 0, n - 1);
/*       */     } else {
/*  9123 */       int[] m = new int[2];
/*  9124 */       quickSort(a, i, 0, n - 1, m);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialSort(int k, double[] a) {
/*  9137 */     int n = a.length;
/*  9138 */     int p = 0;
/*  9139 */     int q = n - 1;
/*  9140 */     int[] m = (n > 7) ? new int[2] : null;
/*  9141 */     while (q - p >= 7) {
/*  9142 */       m[0] = p;
/*  9143 */       m[1] = q;
/*  9144 */       quickPartition(a, m);
/*  9145 */       if (k < m[0]) {
/*  9146 */         q = m[0] - 1; continue;
/*  9147 */       }  if (k > m[1]) {
/*  9148 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  9153 */     insertionSort(a, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void quickPartialIndexSort(int k, double[] a, int[] i) {
/*  9166 */     int n = i.length;
/*  9167 */     int p = 0;
/*  9168 */     int q = n - 1;
/*  9169 */     int[] m = (n > 7) ? new int[2] : null;
/*  9170 */     while (q - p >= 7) {
/*  9171 */       m[0] = p;
/*  9172 */       m[1] = q;
/*  9173 */       quickPartition(a, i, m);
/*  9174 */       if (k < m[0]) {
/*  9175 */         q = m[0] - 1; continue;
/*  9176 */       }  if (k > m[1]) {
/*  9177 */         p = m[1] + 1;
/*       */         continue;
/*       */       } 
/*       */       return;
/*       */     } 
/*  9182 */     insertionSort(a, i, p, q);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private static int med3(byte[] a, int i, int j, int k) {
/*  9190 */     return (a[i] < a[j]) ? ((a[j] < a[k]) ? j : ((a[i] < a[k]) ? k : i)) : ((a[j] > a[k]) ? j : ((a[i] > a[k]) ? k : i));
/*       */   }
/*       */ 
/*       */   
/*       */   private static int med3(byte[] a, int[] i, int j, int k, int l) {
/*  9195 */     return (a[i[j]] < a[i[k]]) ? ((a[i[k]] < a[i[l]]) ? k : ((a[i[j]] < a[i[l]]) ? l : j)) : ((a[i[k]] > a[i[l]]) ? k : ((a[i[j]] > a[i[l]]) ? l : j));
/*       */   }
/*       */ 
/*       */   
/*       */   private static void swap(byte[] a, int i, int j) {
/*  9200 */     byte ai = a[i];
/*  9201 */     a[i] = a[j];
/*  9202 */     a[j] = ai;
/*       */   }
/*       */   private static void swap(byte[] a, int i, int j, int n) {
/*  9205 */     while (n > 0) {
/*  9206 */       byte ai = a[i];
/*  9207 */       a[i++] = a[j];
/*  9208 */       a[j++] = ai;
/*  9209 */       n--;
/*       */     } 
/*       */   }
/*       */   private static void insertionSort(byte[] a, int p, int q) {
/*  9213 */     for (int i = p; i <= q; i++) {
/*  9214 */       for (int j = i; j > p && a[j - 1] > a[j]; j--)
/*  9215 */         swap(a, j, j - 1); 
/*       */     } 
/*       */   } private static void insertionSort(byte[] a, int[] i, int p, int q) {
/*  9218 */     for (int j = p; j <= q; j++) {
/*  9219 */       for (int k = j; k > p && a[i[k - 1]] > a[i[k]]; k--)
/*  9220 */         swap(i, k, k - 1); 
/*       */     } 
/*       */   } private static void quickSort(byte[] a, int p, int q, int[] m) {
/*  9223 */     if (q - p <= 7) {
/*  9224 */       insertionSort(a, p, q);
/*       */     } else {
/*  9226 */       m[0] = p;
/*  9227 */       m[1] = q;
/*  9228 */       quickPartition(a, m);
/*  9229 */       int r = m[0];
/*  9230 */       int s = m[1];
/*  9231 */       if (p < r - 1)
/*  9232 */         quickSort(a, p, r - 1, m); 
/*  9233 */       if (q > s + 1)
/*  9234 */         quickSort(a, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickSort(byte[] a, int[] i, int p, int q, int[] m) {
/*  9238 */     if (q - p <= 7) {
/*  9239 */       insertionSort(a, i, p, q);
/*       */     } else {
/*  9241 */       m[0] = p;
/*  9242 */       m[1] = q;
/*  9243 */       quickPartition(a, i, m);
/*  9244 */       int r = m[0];
/*  9245 */       int s = m[1];
/*  9246 */       if (p < r - 1)
/*  9247 */         quickSort(a, i, p, r - 1, m); 
/*  9248 */       if (q > s + 1)
/*  9249 */         quickSort(a, i, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickPartition(byte[] x, int[] m) {
/*  9253 */     int p = m[0];
/*  9254 */     int q = m[1];
/*  9255 */     int n = q - p + 1;
/*  9256 */     int k = (p + q) / 2;
/*  9257 */     if (n > 7) {
/*  9258 */       int j = p;
/*  9259 */       int l = q;
/*  9260 */       if (n > 40) {
/*  9261 */         int i = n / 8;
/*  9262 */         j = med3(x, j, j + i, j + 2 * i);
/*  9263 */         k = med3(x, k - i, k, k + i);
/*  9264 */         l = med3(x, l - 2 * i, l - i, l);
/*       */       } 
/*  9266 */       k = med3(x, j, k, l);
/*       */     } 
/*  9268 */     byte y = x[k];
/*  9269 */     int a = p, b = p;
/*  9270 */     int c = q, d = q;
/*       */     while (true) {
/*  9272 */       if (b <= c && x[b] <= y) {
/*  9273 */         if (x[b] == y)
/*  9274 */           swap(x, a++, b); 
/*  9275 */         b++; continue;
/*       */       } 
/*  9277 */       while (c >= b && x[c] >= y) {
/*  9278 */         if (x[c] == y)
/*  9279 */           swap(x, c, d--); 
/*  9280 */         c--;
/*       */       } 
/*  9282 */       if (b > c)
/*       */         break; 
/*  9284 */       swap(x, b, c);
/*  9285 */       b++;
/*  9286 */       c--;
/*       */     } 
/*  9288 */     int r = Math.min(a - p, b - a);
/*  9289 */     int s = Math.min(d - c, q - d);
/*  9290 */     int t = q + 1;
/*  9291 */     swap(x, p, b - r, r);
/*  9292 */     swap(x, b, t - s, s);
/*  9293 */     m[0] = p + b - a;
/*  9294 */     m[1] = q - d - c;
/*       */   }
/*       */   private static void quickPartition(byte[] x, int[] i, int[] m) {
/*  9297 */     int p = m[0];
/*  9298 */     int q = m[1];
/*  9299 */     int n = q - p + 1;
/*  9300 */     int k = (p + q) / 2;
/*  9301 */     if (n > 7) {
/*  9302 */       int j = p;
/*  9303 */       int l = q;
/*  9304 */       if (n > 40) {
/*  9305 */         int i1 = n / 8;
/*  9306 */         j = med3(x, i, j, j + i1, j + 2 * i1);
/*  9307 */         k = med3(x, i, k - i1, k, k + i1);
/*  9308 */         l = med3(x, i, l - 2 * i1, l - i1, l);
/*       */       } 
/*  9310 */       k = med3(x, i, j, k, l);
/*       */     } 
/*  9312 */     byte y = x[i[k]];
/*  9313 */     int a = p, b = p;
/*  9314 */     int c = q, d = q;
/*       */     while (true) {
/*  9316 */       if (b <= c && x[i[b]] <= y) {
/*  9317 */         if (x[i[b]] == y)
/*  9318 */           swap(i, a++, b); 
/*  9319 */         b++; continue;
/*       */       } 
/*  9321 */       while (c >= b && x[i[c]] >= y) {
/*  9322 */         if (x[i[c]] == y)
/*  9323 */           swap(i, c, d--); 
/*  9324 */         c--;
/*       */       } 
/*  9326 */       if (b > c)
/*       */         break; 
/*  9328 */       swap(i, b, c);
/*  9329 */       b++;
/*  9330 */       c--;
/*       */     } 
/*  9332 */     int r = Math.min(a - p, b - a);
/*  9333 */     int s = Math.min(d - c, q - d);
/*  9334 */     int t = q + 1;
/*  9335 */     swap(i, p, b - r, r);
/*  9336 */     swap(i, b, t - s, s);
/*  9337 */     m[0] = p + b - a;
/*  9338 */     m[1] = q - d - c;
/*       */   }
/*       */   private static int med3(short[] a, int i, int j, int k) {
/*  9341 */     return (a[i] < a[j]) ? ((a[j] < a[k]) ? j : ((a[i] < a[k]) ? k : i)) : ((a[j] > a[k]) ? j : ((a[i] > a[k]) ? k : i));
/*       */   }
/*       */ 
/*       */   
/*       */   private static int med3(short[] a, int[] i, int j, int k, int l) {
/*  9346 */     return (a[i[j]] < a[i[k]]) ? ((a[i[k]] < a[i[l]]) ? k : ((a[i[j]] < a[i[l]]) ? l : j)) : ((a[i[k]] > a[i[l]]) ? k : ((a[i[j]] > a[i[l]]) ? l : j));
/*       */   }
/*       */ 
/*       */   
/*       */   private static void swap(short[] a, int i, int j) {
/*  9351 */     short ai = a[i];
/*  9352 */     a[i] = a[j];
/*  9353 */     a[j] = ai;
/*       */   }
/*       */   private static void swap(short[] a, int i, int j, int n) {
/*  9356 */     while (n > 0) {
/*  9357 */       short ai = a[i];
/*  9358 */       a[i++] = a[j];
/*  9359 */       a[j++] = ai;
/*  9360 */       n--;
/*       */     } 
/*       */   }
/*       */   private static void insertionSort(short[] a, int p, int q) {
/*  9364 */     for (int i = p; i <= q; i++) {
/*  9365 */       for (int j = i; j > p && a[j - 1] > a[j]; j--)
/*  9366 */         swap(a, j, j - 1); 
/*       */     } 
/*       */   } private static void insertionSort(short[] a, int[] i, int p, int q) {
/*  9369 */     for (int j = p; j <= q; j++) {
/*  9370 */       for (int k = j; k > p && a[i[k - 1]] > a[i[k]]; k--)
/*  9371 */         swap(i, k, k - 1); 
/*       */     } 
/*       */   } private static void quickSort(short[] a, int p, int q, int[] m) {
/*  9374 */     if (q - p <= 7) {
/*  9375 */       insertionSort(a, p, q);
/*       */     } else {
/*  9377 */       m[0] = p;
/*  9378 */       m[1] = q;
/*  9379 */       quickPartition(a, m);
/*  9380 */       int r = m[0];
/*  9381 */       int s = m[1];
/*  9382 */       if (p < r - 1)
/*  9383 */         quickSort(a, p, r - 1, m); 
/*  9384 */       if (q > s + 1)
/*  9385 */         quickSort(a, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickSort(short[] a, int[] i, int p, int q, int[] m) {
/*  9389 */     if (q - p <= 7) {
/*  9390 */       insertionSort(a, i, p, q);
/*       */     } else {
/*  9392 */       m[0] = p;
/*  9393 */       m[1] = q;
/*  9394 */       quickPartition(a, i, m);
/*  9395 */       int r = m[0];
/*  9396 */       int s = m[1];
/*  9397 */       if (p < r - 1)
/*  9398 */         quickSort(a, i, p, r - 1, m); 
/*  9399 */       if (q > s + 1)
/*  9400 */         quickSort(a, i, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickPartition(short[] x, int[] m) {
/*  9404 */     int p = m[0];
/*  9405 */     int q = m[1];
/*  9406 */     int n = q - p + 1;
/*  9407 */     int k = (p + q) / 2;
/*  9408 */     if (n > 7) {
/*  9409 */       int j = p;
/*  9410 */       int l = q;
/*  9411 */       if (n > 40) {
/*  9412 */         int i = n / 8;
/*  9413 */         j = med3(x, j, j + i, j + 2 * i);
/*  9414 */         k = med3(x, k - i, k, k + i);
/*  9415 */         l = med3(x, l - 2 * i, l - i, l);
/*       */       } 
/*  9417 */       k = med3(x, j, k, l);
/*       */     } 
/*  9419 */     short y = x[k];
/*  9420 */     int a = p, b = p;
/*  9421 */     int c = q, d = q;
/*       */     while (true) {
/*  9423 */       if (b <= c && x[b] <= y) {
/*  9424 */         if (x[b] == y)
/*  9425 */           swap(x, a++, b); 
/*  9426 */         b++; continue;
/*       */       } 
/*  9428 */       while (c >= b && x[c] >= y) {
/*  9429 */         if (x[c] == y)
/*  9430 */           swap(x, c, d--); 
/*  9431 */         c--;
/*       */       } 
/*  9433 */       if (b > c)
/*       */         break; 
/*  9435 */       swap(x, b, c);
/*  9436 */       b++;
/*  9437 */       c--;
/*       */     } 
/*  9439 */     int r = Math.min(a - p, b - a);
/*  9440 */     int s = Math.min(d - c, q - d);
/*  9441 */     int t = q + 1;
/*  9442 */     swap(x, p, b - r, r);
/*  9443 */     swap(x, b, t - s, s);
/*  9444 */     m[0] = p + b - a;
/*  9445 */     m[1] = q - d - c;
/*       */   }
/*       */   private static void quickPartition(short[] x, int[] i, int[] m) {
/*  9448 */     int p = m[0];
/*  9449 */     int q = m[1];
/*  9450 */     int n = q - p + 1;
/*  9451 */     int k = (p + q) / 2;
/*  9452 */     if (n > 7) {
/*  9453 */       int j = p;
/*  9454 */       int l = q;
/*  9455 */       if (n > 40) {
/*  9456 */         int i1 = n / 8;
/*  9457 */         j = med3(x, i, j, j + i1, j + 2 * i1);
/*  9458 */         k = med3(x, i, k - i1, k, k + i1);
/*  9459 */         l = med3(x, i, l - 2 * i1, l - i1, l);
/*       */       } 
/*  9461 */       k = med3(x, i, j, k, l);
/*       */     } 
/*  9463 */     short y = x[i[k]];
/*  9464 */     int a = p, b = p;
/*  9465 */     int c = q, d = q;
/*       */     while (true) {
/*  9467 */       if (b <= c && x[i[b]] <= y) {
/*  9468 */         if (x[i[b]] == y)
/*  9469 */           swap(i, a++, b); 
/*  9470 */         b++; continue;
/*       */       } 
/*  9472 */       while (c >= b && x[i[c]] >= y) {
/*  9473 */         if (x[i[c]] == y)
/*  9474 */           swap(i, c, d--); 
/*  9475 */         c--;
/*       */       } 
/*  9477 */       if (b > c)
/*       */         break; 
/*  9479 */       swap(i, b, c);
/*  9480 */       b++;
/*  9481 */       c--;
/*       */     } 
/*  9483 */     int r = Math.min(a - p, b - a);
/*  9484 */     int s = Math.min(d - c, q - d);
/*  9485 */     int t = q + 1;
/*  9486 */     swap(i, p, b - r, r);
/*  9487 */     swap(i, b, t - s, s);
/*  9488 */     m[0] = p + b - a;
/*  9489 */     m[1] = q - d - c;
/*       */   }
/*       */   private static int med3(int[] a, int i, int j, int k) {
/*  9492 */     return (a[i] < a[j]) ? ((a[j] < a[k]) ? j : ((a[i] < a[k]) ? k : i)) : ((a[j] > a[k]) ? j : ((a[i] > a[k]) ? k : i));
/*       */   }
/*       */ 
/*       */   
/*       */   private static int med3(int[] a, int[] i, int j, int k, int l) {
/*  9497 */     return (a[i[j]] < a[i[k]]) ? ((a[i[k]] < a[i[l]]) ? k : ((a[i[j]] < a[i[l]]) ? l : j)) : ((a[i[k]] > a[i[l]]) ? k : ((a[i[j]] > a[i[l]]) ? l : j));
/*       */   }
/*       */ 
/*       */   
/*       */   private static void swap(int[] a, int i, int j) {
/*  9502 */     int ai = a[i];
/*  9503 */     a[i] = a[j];
/*  9504 */     a[j] = ai;
/*       */   }
/*       */   private static void swap(int[] a, int i, int j, int n) {
/*  9507 */     while (n > 0) {
/*  9508 */       int ai = a[i];
/*  9509 */       a[i++] = a[j];
/*  9510 */       a[j++] = ai;
/*  9511 */       n--;
/*       */     } 
/*       */   }
/*       */   private static void insertionSort(int[] a, int p, int q) {
/*  9515 */     for (int i = p; i <= q; i++) {
/*  9516 */       for (int j = i; j > p && a[j - 1] > a[j]; j--)
/*  9517 */         swap(a, j, j - 1); 
/*       */     } 
/*       */   } private static void insertionSort(int[] a, int[] i, int p, int q) {
/*  9520 */     for (int j = p; j <= q; j++) {
/*  9521 */       for (int k = j; k > p && a[i[k - 1]] > a[i[k]]; k--)
/*  9522 */         swap(i, k, k - 1); 
/*       */     } 
/*       */   } private static void quickSort(int[] a, int p, int q, int[] m) {
/*  9525 */     if (q - p <= 7) {
/*  9526 */       insertionSort(a, p, q);
/*       */     } else {
/*  9528 */       m[0] = p;
/*  9529 */       m[1] = q;
/*  9530 */       quickPartition(a, m);
/*  9531 */       int r = m[0];
/*  9532 */       int s = m[1];
/*  9533 */       if (p < r - 1)
/*  9534 */         quickSort(a, p, r - 1, m); 
/*  9535 */       if (q > s + 1)
/*  9536 */         quickSort(a, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickSort(int[] a, int[] i, int p, int q, int[] m) {
/*  9540 */     if (q - p <= 7) {
/*  9541 */       insertionSort(a, i, p, q);
/*       */     } else {
/*  9543 */       m[0] = p;
/*  9544 */       m[1] = q;
/*  9545 */       quickPartition(a, i, m);
/*  9546 */       int r = m[0];
/*  9547 */       int s = m[1];
/*  9548 */       if (p < r - 1)
/*  9549 */         quickSort(a, i, p, r - 1, m); 
/*  9550 */       if (q > s + 1)
/*  9551 */         quickSort(a, i, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickPartition(int[] x, int[] m) {
/*  9555 */     int p = m[0];
/*  9556 */     int q = m[1];
/*  9557 */     int n = q - p + 1;
/*  9558 */     int k = (p + q) / 2;
/*  9559 */     if (n > 7) {
/*  9560 */       int j = p;
/*  9561 */       int l = q;
/*  9562 */       if (n > 40) {
/*  9563 */         int i = n / 8;
/*  9564 */         j = med3(x, j, j + i, j + 2 * i);
/*  9565 */         k = med3(x, k - i, k, k + i);
/*  9566 */         l = med3(x, l - 2 * i, l - i, l);
/*       */       } 
/*  9568 */       k = med3(x, j, k, l);
/*       */     } 
/*  9570 */     int y = x[k];
/*  9571 */     int a = p, b = p;
/*  9572 */     int c = q, d = q;
/*       */     while (true) {
/*  9574 */       if (b <= c && x[b] <= y) {
/*  9575 */         if (x[b] == y)
/*  9576 */           swap(x, a++, b); 
/*  9577 */         b++; continue;
/*       */       } 
/*  9579 */       while (c >= b && x[c] >= y) {
/*  9580 */         if (x[c] == y)
/*  9581 */           swap(x, c, d--); 
/*  9582 */         c--;
/*       */       } 
/*  9584 */       if (b > c)
/*       */         break; 
/*  9586 */       swap(x, b, c);
/*  9587 */       b++;
/*  9588 */       c--;
/*       */     } 
/*  9590 */     int r = Math.min(a - p, b - a);
/*  9591 */     int s = Math.min(d - c, q - d);
/*  9592 */     int t = q + 1;
/*  9593 */     swap(x, p, b - r, r);
/*  9594 */     swap(x, b, t - s, s);
/*  9595 */     m[0] = p + b - a;
/*  9596 */     m[1] = q - d - c;
/*       */   }
/*       */   private static void quickPartition(int[] x, int[] i, int[] m) {
/*  9599 */     int p = m[0];
/*  9600 */     int q = m[1];
/*  9601 */     int n = q - p + 1;
/*  9602 */     int k = (p + q) / 2;
/*  9603 */     if (n > 7) {
/*  9604 */       int j = p;
/*  9605 */       int l = q;
/*  9606 */       if (n > 40) {
/*  9607 */         int i1 = n / 8;
/*  9608 */         j = med3(x, i, j, j + i1, j + 2 * i1);
/*  9609 */         k = med3(x, i, k - i1, k, k + i1);
/*  9610 */         l = med3(x, i, l - 2 * i1, l - i1, l);
/*       */       } 
/*  9612 */       k = med3(x, i, j, k, l);
/*       */     } 
/*  9614 */     int y = x[i[k]];
/*  9615 */     int a = p, b = p;
/*  9616 */     int c = q, d = q;
/*       */     while (true) {
/*  9618 */       if (b <= c && x[i[b]] <= y) {
/*  9619 */         if (x[i[b]] == y)
/*  9620 */           swap(i, a++, b); 
/*  9621 */         b++; continue;
/*       */       } 
/*  9623 */       while (c >= b && x[i[c]] >= y) {
/*  9624 */         if (x[i[c]] == y)
/*  9625 */           swap(i, c, d--); 
/*  9626 */         c--;
/*       */       } 
/*  9628 */       if (b > c)
/*       */         break; 
/*  9630 */       swap(i, b, c);
/*  9631 */       b++;
/*  9632 */       c--;
/*       */     } 
/*  9634 */     int r = Math.min(a - p, b - a);
/*  9635 */     int s = Math.min(d - c, q - d);
/*  9636 */     int t = q + 1;
/*  9637 */     swap(i, p, b - r, r);
/*  9638 */     swap(i, b, t - s, s);
/*  9639 */     m[0] = p + b - a;
/*  9640 */     m[1] = q - d - c;
/*       */   }
/*       */   private static int med3(long[] a, int i, int j, int k) {
/*  9643 */     return (a[i] < a[j]) ? ((a[j] < a[k]) ? j : ((a[i] < a[k]) ? k : i)) : ((a[j] > a[k]) ? j : ((a[i] > a[k]) ? k : i));
/*       */   }
/*       */ 
/*       */   
/*       */   private static int med3(long[] a, int[] i, int j, int k, int l) {
/*  9648 */     return (a[i[j]] < a[i[k]]) ? ((a[i[k]] < a[i[l]]) ? k : ((a[i[j]] < a[i[l]]) ? l : j)) : ((a[i[k]] > a[i[l]]) ? k : ((a[i[j]] > a[i[l]]) ? l : j));
/*       */   }
/*       */ 
/*       */   
/*       */   private static void swap(long[] a, int i, int j) {
/*  9653 */     long ai = a[i];
/*  9654 */     a[i] = a[j];
/*  9655 */     a[j] = ai;
/*       */   }
/*       */   private static void swap(long[] a, int i, int j, int n) {
/*  9658 */     while (n > 0) {
/*  9659 */       long ai = a[i];
/*  9660 */       a[i++] = a[j];
/*  9661 */       a[j++] = ai;
/*  9662 */       n--;
/*       */     } 
/*       */   }
/*       */   private static void insertionSort(long[] a, int p, int q) {
/*  9666 */     for (int i = p; i <= q; i++) {
/*  9667 */       for (int j = i; j > p && a[j - 1] > a[j]; j--)
/*  9668 */         swap(a, j, j - 1); 
/*       */     } 
/*       */   } private static void insertionSort(long[] a, int[] i, int p, int q) {
/*  9671 */     for (int j = p; j <= q; j++) {
/*  9672 */       for (int k = j; k > p && a[i[k - 1]] > a[i[k]]; k--)
/*  9673 */         swap(i, k, k - 1); 
/*       */     } 
/*       */   } private static void quickSort(long[] a, int p, int q, int[] m) {
/*  9676 */     if (q - p <= 7) {
/*  9677 */       insertionSort(a, p, q);
/*       */     } else {
/*  9679 */       m[0] = p;
/*  9680 */       m[1] = q;
/*  9681 */       quickPartition(a, m);
/*  9682 */       int r = m[0];
/*  9683 */       int s = m[1];
/*  9684 */       if (p < r - 1)
/*  9685 */         quickSort(a, p, r - 1, m); 
/*  9686 */       if (q > s + 1)
/*  9687 */         quickSort(a, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickSort(long[] a, int[] i, int p, int q, int[] m) {
/*  9691 */     if (q - p <= 7) {
/*  9692 */       insertionSort(a, i, p, q);
/*       */     } else {
/*  9694 */       m[0] = p;
/*  9695 */       m[1] = q;
/*  9696 */       quickPartition(a, i, m);
/*  9697 */       int r = m[0];
/*  9698 */       int s = m[1];
/*  9699 */       if (p < r - 1)
/*  9700 */         quickSort(a, i, p, r - 1, m); 
/*  9701 */       if (q > s + 1)
/*  9702 */         quickSort(a, i, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickPartition(long[] x, int[] m) {
/*  9706 */     int p = m[0];
/*  9707 */     int q = m[1];
/*  9708 */     int n = q - p + 1;
/*  9709 */     int k = (p + q) / 2;
/*  9710 */     if (n > 7) {
/*  9711 */       int j = p;
/*  9712 */       int l = q;
/*  9713 */       if (n > 40) {
/*  9714 */         int i = n / 8;
/*  9715 */         j = med3(x, j, j + i, j + 2 * i);
/*  9716 */         k = med3(x, k - i, k, k + i);
/*  9717 */         l = med3(x, l - 2 * i, l - i, l);
/*       */       } 
/*  9719 */       k = med3(x, j, k, l);
/*       */     } 
/*  9721 */     long y = x[k];
/*  9722 */     int a = p, b = p;
/*  9723 */     int c = q, d = q;
/*       */     while (true) {
/*  9725 */       if (b <= c && x[b] <= y) {
/*  9726 */         if (x[b] == y)
/*  9727 */           swap(x, a++, b); 
/*  9728 */         b++; continue;
/*       */       } 
/*  9730 */       while (c >= b && x[c] >= y) {
/*  9731 */         if (x[c] == y)
/*  9732 */           swap(x, c, d--); 
/*  9733 */         c--;
/*       */       } 
/*  9735 */       if (b > c)
/*       */         break; 
/*  9737 */       swap(x, b, c);
/*  9738 */       b++;
/*  9739 */       c--;
/*       */     } 
/*  9741 */     int r = Math.min(a - p, b - a);
/*  9742 */     int s = Math.min(d - c, q - d);
/*  9743 */     int t = q + 1;
/*  9744 */     swap(x, p, b - r, r);
/*  9745 */     swap(x, b, t - s, s);
/*  9746 */     m[0] = p + b - a;
/*  9747 */     m[1] = q - d - c;
/*       */   }
/*       */   private static void quickPartition(long[] x, int[] i, int[] m) {
/*  9750 */     int p = m[0];
/*  9751 */     int q = m[1];
/*  9752 */     int n = q - p + 1;
/*  9753 */     int k = (p + q) / 2;
/*  9754 */     if (n > 7) {
/*  9755 */       int j = p;
/*  9756 */       int l = q;
/*  9757 */       if (n > 40) {
/*  9758 */         int i1 = n / 8;
/*  9759 */         j = med3(x, i, j, j + i1, j + 2 * i1);
/*  9760 */         k = med3(x, i, k - i1, k, k + i1);
/*  9761 */         l = med3(x, i, l - 2 * i1, l - i1, l);
/*       */       } 
/*  9763 */       k = med3(x, i, j, k, l);
/*       */     } 
/*  9765 */     long y = x[i[k]];
/*  9766 */     int a = p, b = p;
/*  9767 */     int c = q, d = q;
/*       */     while (true) {
/*  9769 */       if (b <= c && x[i[b]] <= y) {
/*  9770 */         if (x[i[b]] == y)
/*  9771 */           swap(i, a++, b); 
/*  9772 */         b++; continue;
/*       */       } 
/*  9774 */       while (c >= b && x[i[c]] >= y) {
/*  9775 */         if (x[i[c]] == y)
/*  9776 */           swap(i, c, d--); 
/*  9777 */         c--;
/*       */       } 
/*  9779 */       if (b > c)
/*       */         break; 
/*  9781 */       swap(i, b, c);
/*  9782 */       b++;
/*  9783 */       c--;
/*       */     } 
/*  9785 */     int r = Math.min(a - p, b - a);
/*  9786 */     int s = Math.min(d - c, q - d);
/*  9787 */     int t = q + 1;
/*  9788 */     swap(i, p, b - r, r);
/*  9789 */     swap(i, b, t - s, s);
/*  9790 */     m[0] = p + b - a;
/*  9791 */     m[1] = q - d - c;
/*       */   }
/*       */   private static int med3(float[] a, int i, int j, int k) {
/*  9794 */     return (a[i] < a[j]) ? ((a[j] < a[k]) ? j : ((a[i] < a[k]) ? k : i)) : ((a[j] > a[k]) ? j : ((a[i] > a[k]) ? k : i));
/*       */   }
/*       */ 
/*       */   
/*       */   private static int med3(float[] a, int[] i, int j, int k, int l) {
/*  9799 */     return (a[i[j]] < a[i[k]]) ? ((a[i[k]] < a[i[l]]) ? k : ((a[i[j]] < a[i[l]]) ? l : j)) : ((a[i[k]] > a[i[l]]) ? k : ((a[i[j]] > a[i[l]]) ? l : j));
/*       */   }
/*       */ 
/*       */   
/*       */   private static void swap(float[] a, int i, int j) {
/*  9804 */     float ai = a[i];
/*  9805 */     a[i] = a[j];
/*  9806 */     a[j] = ai;
/*       */   }
/*       */   private static void swap(float[] a, int i, int j, int n) {
/*  9809 */     while (n > 0) {
/*  9810 */       float ai = a[i];
/*  9811 */       a[i++] = a[j];
/*  9812 */       a[j++] = ai;
/*  9813 */       n--;
/*       */     } 
/*       */   }
/*       */   private static void insertionSort(float[] a, int p, int q) {
/*  9817 */     for (int i = p; i <= q; i++) {
/*  9818 */       for (int j = i; j > p && a[j - 1] > a[j]; j--)
/*  9819 */         swap(a, j, j - 1); 
/*       */     } 
/*       */   } private static void insertionSort(float[] a, int[] i, int p, int q) {
/*  9822 */     for (int j = p; j <= q; j++) {
/*  9823 */       for (int k = j; k > p && a[i[k - 1]] > a[i[k]]; k--)
/*  9824 */         swap(i, k, k - 1); 
/*       */     } 
/*       */   } private static void quickSort(float[] a, int p, int q, int[] m) {
/*  9827 */     if (q - p <= 7) {
/*  9828 */       insertionSort(a, p, q);
/*       */     } else {
/*  9830 */       m[0] = p;
/*  9831 */       m[1] = q;
/*  9832 */       quickPartition(a, m);
/*  9833 */       int r = m[0];
/*  9834 */       int s = m[1];
/*  9835 */       if (p < r - 1)
/*  9836 */         quickSort(a, p, r - 1, m); 
/*  9837 */       if (q > s + 1)
/*  9838 */         quickSort(a, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickSort(float[] a, int[] i, int p, int q, int[] m) {
/*  9842 */     if (q - p <= 7) {
/*  9843 */       insertionSort(a, i, p, q);
/*       */     } else {
/*  9845 */       m[0] = p;
/*  9846 */       m[1] = q;
/*  9847 */       quickPartition(a, i, m);
/*  9848 */       int r = m[0];
/*  9849 */       int s = m[1];
/*  9850 */       if (p < r - 1)
/*  9851 */         quickSort(a, i, p, r - 1, m); 
/*  9852 */       if (q > s + 1)
/*  9853 */         quickSort(a, i, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickPartition(float[] x, int[] m) {
/*  9857 */     int p = m[0];
/*  9858 */     int q = m[1];
/*  9859 */     int n = q - p + 1;
/*  9860 */     int k = (p + q) / 2;
/*  9861 */     if (n > 7) {
/*  9862 */       int j = p;
/*  9863 */       int l = q;
/*  9864 */       if (n > 40) {
/*  9865 */         int i = n / 8;
/*  9866 */         j = med3(x, j, j + i, j + 2 * i);
/*  9867 */         k = med3(x, k - i, k, k + i);
/*  9868 */         l = med3(x, l - 2 * i, l - i, l);
/*       */       } 
/*  9870 */       k = med3(x, j, k, l);
/*       */     } 
/*  9872 */     float y = x[k];
/*  9873 */     int a = p, b = p;
/*  9874 */     int c = q, d = q;
/*       */     while (true) {
/*  9876 */       if (b <= c && x[b] <= y) {
/*  9877 */         if (x[b] == y)
/*  9878 */           swap(x, a++, b); 
/*  9879 */         b++; continue;
/*       */       } 
/*  9881 */       while (c >= b && x[c] >= y) {
/*  9882 */         if (x[c] == y)
/*  9883 */           swap(x, c, d--); 
/*  9884 */         c--;
/*       */       } 
/*  9886 */       if (b > c)
/*       */         break; 
/*  9888 */       swap(x, b, c);
/*  9889 */       b++;
/*  9890 */       c--;
/*       */     } 
/*  9892 */     int r = Math.min(a - p, b - a);
/*  9893 */     int s = Math.min(d - c, q - d);
/*  9894 */     int t = q + 1;
/*  9895 */     swap(x, p, b - r, r);
/*  9896 */     swap(x, b, t - s, s);
/*  9897 */     m[0] = p + b - a;
/*  9898 */     m[1] = q - d - c;
/*       */   }
/*       */   private static void quickPartition(float[] x, int[] i, int[] m) {
/*  9901 */     int p = m[0];
/*  9902 */     int q = m[1];
/*  9903 */     int n = q - p + 1;
/*  9904 */     int k = (p + q) / 2;
/*  9905 */     if (n > 7) {
/*  9906 */       int j = p;
/*  9907 */       int l = q;
/*  9908 */       if (n > 40) {
/*  9909 */         int i1 = n / 8;
/*  9910 */         j = med3(x, i, j, j + i1, j + 2 * i1);
/*  9911 */         k = med3(x, i, k - i1, k, k + i1);
/*  9912 */         l = med3(x, i, l - 2 * i1, l - i1, l);
/*       */       } 
/*  9914 */       k = med3(x, i, j, k, l);
/*       */     } 
/*  9916 */     float y = x[i[k]];
/*  9917 */     int a = p, b = p;
/*  9918 */     int c = q, d = q;
/*       */     while (true) {
/*  9920 */       if (b <= c && x[i[b]] <= y) {
/*  9921 */         if (x[i[b]] == y)
/*  9922 */           swap(i, a++, b); 
/*  9923 */         b++; continue;
/*       */       } 
/*  9925 */       while (c >= b && x[i[c]] >= y) {
/*  9926 */         if (x[i[c]] == y)
/*  9927 */           swap(i, c, d--); 
/*  9928 */         c--;
/*       */       } 
/*  9930 */       if (b > c)
/*       */         break; 
/*  9932 */       swap(i, b, c);
/*  9933 */       b++;
/*  9934 */       c--;
/*       */     } 
/*  9936 */     int r = Math.min(a - p, b - a);
/*  9937 */     int s = Math.min(d - c, q - d);
/*  9938 */     int t = q + 1;
/*  9939 */     swap(i, p, b - r, r);
/*  9940 */     swap(i, b, t - s, s);
/*  9941 */     m[0] = p + b - a;
/*  9942 */     m[1] = q - d - c;
/*       */   }
/*       */   private static int med3(double[] a, int i, int j, int k) {
/*  9945 */     return (a[i] < a[j]) ? ((a[j] < a[k]) ? j : ((a[i] < a[k]) ? k : i)) : ((a[j] > a[k]) ? j : ((a[i] > a[k]) ? k : i));
/*       */   }
/*       */ 
/*       */   
/*       */   private static int med3(double[] a, int[] i, int j, int k, int l) {
/*  9950 */     return (a[i[j]] < a[i[k]]) ? ((a[i[k]] < a[i[l]]) ? k : ((a[i[j]] < a[i[l]]) ? l : j)) : ((a[i[k]] > a[i[l]]) ? k : ((a[i[j]] > a[i[l]]) ? l : j));
/*       */   }
/*       */ 
/*       */   
/*       */   private static void swap(double[] a, int i, int j) {
/*  9955 */     double ai = a[i];
/*  9956 */     a[i] = a[j];
/*  9957 */     a[j] = ai;
/*       */   }
/*       */   private static void swap(double[] a, int i, int j, int n) {
/*  9960 */     while (n > 0) {
/*  9961 */       double ai = a[i];
/*  9962 */       a[i++] = a[j];
/*  9963 */       a[j++] = ai;
/*  9964 */       n--;
/*       */     } 
/*       */   }
/*       */   private static void insertionSort(double[] a, int p, int q) {
/*  9968 */     for (int i = p; i <= q; i++) {
/*  9969 */       for (int j = i; j > p && a[j - 1] > a[j]; j--)
/*  9970 */         swap(a, j, j - 1); 
/*       */     } 
/*       */   } private static void insertionSort(double[] a, int[] i, int p, int q) {
/*  9973 */     for (int j = p; j <= q; j++) {
/*  9974 */       for (int k = j; k > p && a[i[k - 1]] > a[i[k]]; k--)
/*  9975 */         swap(i, k, k - 1); 
/*       */     } 
/*       */   } private static void quickSort(double[] a, int p, int q, int[] m) {
/*  9978 */     if (q - p <= 7) {
/*  9979 */       insertionSort(a, p, q);
/*       */     } else {
/*  9981 */       m[0] = p;
/*  9982 */       m[1] = q;
/*  9983 */       quickPartition(a, m);
/*  9984 */       int r = m[0];
/*  9985 */       int s = m[1];
/*  9986 */       if (p < r - 1)
/*  9987 */         quickSort(a, p, r - 1, m); 
/*  9988 */       if (q > s + 1)
/*  9989 */         quickSort(a, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickSort(double[] a, int[] i, int p, int q, int[] m) {
/*  9993 */     if (q - p <= 7) {
/*  9994 */       insertionSort(a, i, p, q);
/*       */     } else {
/*  9996 */       m[0] = p;
/*  9997 */       m[1] = q;
/*  9998 */       quickPartition(a, i, m);
/*  9999 */       int r = m[0];
/* 10000 */       int s = m[1];
/* 10001 */       if (p < r - 1)
/* 10002 */         quickSort(a, i, p, r - 1, m); 
/* 10003 */       if (q > s + 1)
/* 10004 */         quickSort(a, i, s + 1, q, m); 
/*       */     } 
/*       */   }
/*       */   private static void quickPartition(double[] x, int[] m) {
/* 10008 */     int p = m[0];
/* 10009 */     int q = m[1];
/* 10010 */     int n = q - p + 1;
/* 10011 */     int k = (p + q) / 2;
/* 10012 */     if (n > 7) {
/* 10013 */       int j = p;
/* 10014 */       int l = q;
/* 10015 */       if (n > 40) {
/* 10016 */         int i = n / 8;
/* 10017 */         j = med3(x, j, j + i, j + 2 * i);
/* 10018 */         k = med3(x, k - i, k, k + i);
/* 10019 */         l = med3(x, l - 2 * i, l - i, l);
/*       */       } 
/* 10021 */       k = med3(x, j, k, l);
/*       */     } 
/* 10023 */     double y = x[k];
/* 10024 */     int a = p, b = p;
/* 10025 */     int c = q, d = q;
/*       */     while (true) {
/* 10027 */       if (b <= c && x[b] <= y) {
/* 10028 */         if (x[b] == y)
/* 10029 */           swap(x, a++, b); 
/* 10030 */         b++; continue;
/*       */       } 
/* 10032 */       while (c >= b && x[c] >= y) {
/* 10033 */         if (x[c] == y)
/* 10034 */           swap(x, c, d--); 
/* 10035 */         c--;
/*       */       } 
/* 10037 */       if (b > c)
/*       */         break; 
/* 10039 */       swap(x, b, c);
/* 10040 */       b++;
/* 10041 */       c--;
/*       */     } 
/* 10043 */     int r = Math.min(a - p, b - a);
/* 10044 */     int s = Math.min(d - c, q - d);
/* 10045 */     int t = q + 1;
/* 10046 */     swap(x, p, b - r, r);
/* 10047 */     swap(x, b, t - s, s);
/* 10048 */     m[0] = p + b - a;
/* 10049 */     m[1] = q - d - c;
/*       */   }
/*       */   private static void quickPartition(double[] x, int[] i, int[] m) {
/* 10052 */     int p = m[0];
/* 10053 */     int q = m[1];
/* 10054 */     int n = q - p + 1;
/* 10055 */     int k = (p + q) / 2;
/* 10056 */     if (n > 7) {
/* 10057 */       int j = p;
/* 10058 */       int l = q;
/* 10059 */       if (n > 40) {
/* 10060 */         int i1 = n / 8;
/* 10061 */         j = med3(x, i, j, j + i1, j + 2 * i1);
/* 10062 */         k = med3(x, i, k - i1, k, k + i1);
/* 10063 */         l = med3(x, i, l - 2 * i1, l - i1, l);
/*       */       } 
/* 10065 */       k = med3(x, i, j, k, l);
/*       */     } 
/* 10067 */     double y = x[i[k]];
/* 10068 */     int a = p, b = p;
/* 10069 */     int c = q, d = q;
/*       */     while (true) {
/* 10071 */       if (b <= c && x[i[b]] <= y) {
/* 10072 */         if (x[i[b]] == y)
/* 10073 */           swap(i, a++, b); 
/* 10074 */         b++; continue;
/*       */       } 
/* 10076 */       while (c >= b && x[i[c]] >= y) {
/* 10077 */         if (x[i[c]] == y)
/* 10078 */           swap(i, c, d--); 
/* 10079 */         c--;
/*       */       } 
/* 10081 */       if (b > c)
/*       */         break; 
/* 10083 */       swap(i, b, c);
/* 10084 */       b++;
/* 10085 */       c--;
/*       */     } 
/* 10087 */     int r = Math.min(a - p, b - a);
/* 10088 */     int s = Math.min(d - c, q - d);
/* 10089 */     int t = q + 1;
/* 10090 */     swap(i, p, b - r, r);
/* 10091 */     swap(i, b, t - s, s);
/* 10092 */     m[0] = p + b - a;
/* 10093 */     m[1] = q - d - c;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(byte[] a, byte x) {
/* 10112 */     return binarySearch(a, x, a.length);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(byte[] a, byte x, int i) {
/* 10133 */     int n = a.length;
/* 10134 */     int nm1 = n - 1;
/* 10135 */     int low = 0;
/* 10136 */     int high = nm1;
/* 10137 */     boolean increasing = (n < 2 || a[0] < a[1]);
/* 10138 */     if (i < n) {
/* 10139 */       high = (0 <= i) ? i : -(i + 1);
/* 10140 */       low = high - 1;
/* 10141 */       int step = 1;
/* 10142 */       if (increasing) {
/* 10143 */         for (; 0 < low && x < a[low]; low -= step, step += step)
/* 10144 */           high = low; 
/* 10145 */         for (; high < nm1 && a[high] < x; high += step, step += step)
/* 10146 */           low = high; 
/*       */       } else {
/* 10148 */         for (; 0 < low && x > a[low]; low -= step, step += step)
/* 10149 */           high = low; 
/* 10150 */         for (; high < nm1 && a[high] > x; high += step, step += step)
/* 10151 */           low = high; 
/*       */       } 
/* 10153 */       if (low < 0) low = 0; 
/* 10154 */       if (high > nm1) high = nm1; 
/*       */     } 
/* 10156 */     if (increasing) {
/* 10157 */       while (low <= high) {
/* 10158 */         int mid = low + high >> 1;
/* 10159 */         byte amid = a[mid];
/* 10160 */         if (amid < x) {
/* 10161 */           low = mid + 1; continue;
/* 10162 */         }  if (amid > x) {
/* 10163 */           high = mid - 1; continue;
/*       */         } 
/* 10165 */         return mid;
/*       */       } 
/*       */     } else {
/* 10168 */       while (low <= high) {
/* 10169 */         int mid = low + high >> 1;
/* 10170 */         byte amid = a[mid];
/* 10171 */         if (amid > x) {
/* 10172 */           low = mid + 1; continue;
/* 10173 */         }  if (amid < x) {
/* 10174 */           high = mid - 1; continue;
/*       */         } 
/* 10176 */         return mid;
/*       */       } 
/*       */     } 
/* 10179 */     return -(low + 1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(short[] a, short x) {
/* 10195 */     return binarySearch(a, x, a.length);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(short[] a, short x, int i) {
/* 10216 */     int n = a.length;
/* 10217 */     int nm1 = n - 1;
/* 10218 */     int low = 0;
/* 10219 */     int high = nm1;
/* 10220 */     boolean increasing = (n < 2 || a[0] < a[1]);
/* 10221 */     if (i < n) {
/* 10222 */       high = (0 <= i) ? i : -(i + 1);
/* 10223 */       low = high - 1;
/* 10224 */       int step = 1;
/* 10225 */       if (increasing) {
/* 10226 */         for (; 0 < low && x < a[low]; low -= step, step += step)
/* 10227 */           high = low; 
/* 10228 */         for (; high < nm1 && a[high] < x; high += step, step += step)
/* 10229 */           low = high; 
/*       */       } else {
/* 10231 */         for (; 0 < low && x > a[low]; low -= step, step += step)
/* 10232 */           high = low; 
/* 10233 */         for (; high < nm1 && a[high] > x; high += step, step += step)
/* 10234 */           low = high; 
/*       */       } 
/* 10236 */       if (low < 0) low = 0; 
/* 10237 */       if (high > nm1) high = nm1; 
/*       */     } 
/* 10239 */     if (increasing) {
/* 10240 */       while (low <= high) {
/* 10241 */         int mid = low + high >> 1;
/* 10242 */         short amid = a[mid];
/* 10243 */         if (amid < x) {
/* 10244 */           low = mid + 1; continue;
/* 10245 */         }  if (amid > x) {
/* 10246 */           high = mid - 1; continue;
/*       */         } 
/* 10248 */         return mid;
/*       */       } 
/*       */     } else {
/* 10251 */       while (low <= high) {
/* 10252 */         int mid = low + high >> 1;
/* 10253 */         short amid = a[mid];
/* 10254 */         if (amid > x) {
/* 10255 */           low = mid + 1; continue;
/* 10256 */         }  if (amid < x) {
/* 10257 */           high = mid - 1; continue;
/*       */         } 
/* 10259 */         return mid;
/*       */       } 
/*       */     } 
/* 10262 */     return -(low + 1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(int[] a, int x) {
/* 10278 */     return binarySearch(a, x, a.length);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(int[] a, int x, int i) {
/* 10299 */     int n = a.length;
/* 10300 */     int nm1 = n - 1;
/* 10301 */     int low = 0;
/* 10302 */     int high = nm1;
/* 10303 */     boolean increasing = (n < 2 || a[0] < a[1]);
/* 10304 */     if (i < n) {
/* 10305 */       high = (0 <= i) ? i : -(i + 1);
/* 10306 */       low = high - 1;
/* 10307 */       int step = 1;
/* 10308 */       if (increasing) {
/* 10309 */         for (; 0 < low && x < a[low]; low -= step, step += step)
/* 10310 */           high = low; 
/* 10311 */         for (; high < nm1 && a[high] < x; high += step, step += step)
/* 10312 */           low = high; 
/*       */       } else {
/* 10314 */         for (; 0 < low && x > a[low]; low -= step, step += step)
/* 10315 */           high = low; 
/* 10316 */         for (; high < nm1 && a[high] > x; high += step, step += step)
/* 10317 */           low = high; 
/*       */       } 
/* 10319 */       if (low < 0) low = 0; 
/* 10320 */       if (high > nm1) high = nm1; 
/*       */     } 
/* 10322 */     if (increasing) {
/* 10323 */       while (low <= high) {
/* 10324 */         int mid = low + high >> 1;
/* 10325 */         int amid = a[mid];
/* 10326 */         if (amid < x) {
/* 10327 */           low = mid + 1; continue;
/* 10328 */         }  if (amid > x) {
/* 10329 */           high = mid - 1; continue;
/*       */         } 
/* 10331 */         return mid;
/*       */       } 
/*       */     } else {
/* 10334 */       while (low <= high) {
/* 10335 */         int mid = low + high >> 1;
/* 10336 */         int amid = a[mid];
/* 10337 */         if (amid > x) {
/* 10338 */           low = mid + 1; continue;
/* 10339 */         }  if (amid < x) {
/* 10340 */           high = mid - 1; continue;
/*       */         } 
/* 10342 */         return mid;
/*       */       } 
/*       */     } 
/* 10345 */     return -(low + 1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(long[] a, long x) {
/* 10361 */     return binarySearch(a, x, a.length);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(long[] a, long x, int i) {
/* 10382 */     int n = a.length;
/* 10383 */     int nm1 = n - 1;
/* 10384 */     int low = 0;
/* 10385 */     int high = nm1;
/* 10386 */     boolean increasing = (n < 2 || a[0] < a[1]);
/* 10387 */     if (i < n) {
/* 10388 */       high = (0 <= i) ? i : -(i + 1);
/* 10389 */       low = high - 1;
/* 10390 */       int step = 1;
/* 10391 */       if (increasing) {
/* 10392 */         for (; 0 < low && x < a[low]; low -= step, step += step)
/* 10393 */           high = low; 
/* 10394 */         for (; high < nm1 && a[high] < x; high += step, step += step)
/* 10395 */           low = high; 
/*       */       } else {
/* 10397 */         for (; 0 < low && x > a[low]; low -= step, step += step)
/* 10398 */           high = low; 
/* 10399 */         for (; high < nm1 && a[high] > x; high += step, step += step)
/* 10400 */           low = high; 
/*       */       } 
/* 10402 */       if (low < 0) low = 0; 
/* 10403 */       if (high > nm1) high = nm1; 
/*       */     } 
/* 10405 */     if (increasing) {
/* 10406 */       while (low <= high) {
/* 10407 */         int mid = low + high >> 1;
/* 10408 */         long amid = a[mid];
/* 10409 */         if (amid < x) {
/* 10410 */           low = mid + 1; continue;
/* 10411 */         }  if (amid > x) {
/* 10412 */           high = mid - 1; continue;
/*       */         } 
/* 10414 */         return mid;
/*       */       } 
/*       */     } else {
/* 10417 */       while (low <= high) {
/* 10418 */         int mid = low + high >> 1;
/* 10419 */         long amid = a[mid];
/* 10420 */         if (amid > x) {
/* 10421 */           low = mid + 1; continue;
/* 10422 */         }  if (amid < x) {
/* 10423 */           high = mid - 1; continue;
/*       */         } 
/* 10425 */         return mid;
/*       */       } 
/*       */     } 
/* 10428 */     return -(low + 1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(float[] a, float x) {
/* 10444 */     return binarySearch(a, x, a.length);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(float[] a, float x, int i) {
/* 10465 */     int n = a.length;
/* 10466 */     int nm1 = n - 1;
/* 10467 */     int low = 0;
/* 10468 */     int high = nm1;
/* 10469 */     boolean increasing = (n < 2 || a[0] < a[1]);
/* 10470 */     if (i < n) {
/* 10471 */       high = (0 <= i) ? i : -(i + 1);
/* 10472 */       low = high - 1;
/* 10473 */       int step = 1;
/* 10474 */       if (increasing) {
/* 10475 */         for (; 0 < low && x < a[low]; low -= step, step += step)
/* 10476 */           high = low; 
/* 10477 */         for (; high < nm1 && a[high] < x; high += step, step += step)
/* 10478 */           low = high; 
/*       */       } else {
/* 10480 */         for (; 0 < low && x > a[low]; low -= step, step += step)
/* 10481 */           high = low; 
/* 10482 */         for (; high < nm1 && a[high] > x; high += step, step += step)
/* 10483 */           low = high; 
/*       */       } 
/* 10485 */       if (low < 0) low = 0; 
/* 10486 */       if (high > nm1) high = nm1; 
/*       */     } 
/* 10488 */     if (increasing) {
/* 10489 */       while (low <= high) {
/* 10490 */         int mid = low + high >> 1;
/* 10491 */         float amid = a[mid];
/* 10492 */         if (amid < x) {
/* 10493 */           low = mid + 1; continue;
/* 10494 */         }  if (amid > x) {
/* 10495 */           high = mid - 1; continue;
/*       */         } 
/* 10497 */         return mid;
/*       */       } 
/*       */     } else {
/* 10500 */       while (low <= high) {
/* 10501 */         int mid = low + high >> 1;
/* 10502 */         float amid = a[mid];
/* 10503 */         if (amid > x) {
/* 10504 */           low = mid + 1; continue;
/* 10505 */         }  if (amid < x) {
/* 10506 */           high = mid - 1; continue;
/*       */         } 
/* 10508 */         return mid;
/*       */       } 
/*       */     } 
/* 10511 */     return -(low + 1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(double[] a, double x) {
/* 10527 */     return binarySearch(a, x, a.length);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int binarySearch(double[] a, double x, int i) {
/* 10548 */     int n = a.length;
/* 10549 */     int nm1 = n - 1;
/* 10550 */     int low = 0;
/* 10551 */     int high = nm1;
/* 10552 */     boolean increasing = (n < 2 || a[0] < a[1]);
/* 10553 */     if (i < n) {
/* 10554 */       high = (0 <= i) ? i : -(i + 1);
/* 10555 */       low = high - 1;
/* 10556 */       int step = 1;
/* 10557 */       if (increasing) {
/* 10558 */         for (; 0 < low && x < a[low]; low -= step, step += step)
/* 10559 */           high = low; 
/* 10560 */         for (; high < nm1 && a[high] < x; high += step, step += step)
/* 10561 */           low = high; 
/*       */       } else {
/* 10563 */         for (; 0 < low && x > a[low]; low -= step, step += step)
/* 10564 */           high = low; 
/* 10565 */         for (; high < nm1 && a[high] > x; high += step, step += step)
/* 10566 */           low = high; 
/*       */       } 
/* 10568 */       if (low < 0) low = 0; 
/* 10569 */       if (high > nm1) high = nm1; 
/*       */     } 
/* 10571 */     if (increasing) {
/* 10572 */       while (low <= high) {
/* 10573 */         int mid = low + high >> 1;
/* 10574 */         double amid = a[mid];
/* 10575 */         if (amid < x) {
/* 10576 */           low = mid + 1; continue;
/* 10577 */         }  if (amid > x) {
/* 10578 */           high = mid - 1; continue;
/*       */         } 
/* 10580 */         return mid;
/*       */       } 
/*       */     } else {
/* 10583 */       while (low <= high) {
/* 10584 */         int mid = low + high >> 1;
/* 10585 */         double amid = a[mid];
/* 10586 */         if (amid > x) {
/* 10587 */           low = mid + 1; continue;
/* 10588 */         }  if (amid < x) {
/* 10589 */           high = mid - 1; continue;
/*       */         } 
/* 10591 */         return mid;
/*       */       } 
/*       */     } 
/* 10594 */     return -(low + 1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] add(float[] rx, float[] ry) {
/* 10601 */     return _add.apply(rx, ry);
/*       */   }
/*       */   public static float[] add(float ra, float[] ry) {
/* 10604 */     return _add.apply(ra, ry);
/*       */   }
/*       */   public static float[] add(float[] rx, float rb) {
/* 10607 */     return _add.apply(rx, rb);
/*       */   }
/*       */   public static float[][] add(float[][] rx, float[][] ry) {
/* 10610 */     return _add.apply(rx, ry);
/*       */   }
/*       */   public static float[][] add(float ra, float[][] ry) {
/* 10613 */     return _add.apply(ra, ry);
/*       */   }
/*       */   public static float[][] add(float[][] rx, float rb) {
/* 10616 */     return _add.apply(rx, rb);
/*       */   }
/*       */   public static float[][][] add(float[][][] rx, float[][][] ry) {
/* 10619 */     return _add.apply(rx, ry);
/*       */   }
/*       */   public static float[][][] add(float ra, float[][][] ry) {
/* 10622 */     return _add.apply(ra, ry);
/*       */   }
/*       */   public static float[][][] add(float[][][] rx, float rb) {
/* 10625 */     return _add.apply(rx, rb);
/*       */   }
/*       */   public static void add(float[] rx, float[] ry, float[] rz) {
/* 10628 */     _add.apply(rx, ry, rz);
/*       */   }
/*       */   public static void add(float ra, float[] ry, float[] rz) {
/* 10631 */     _add.apply(ra, ry, rz);
/*       */   }
/*       */   public static void add(float[] rx, float rb, float[] rz) {
/* 10634 */     _add.apply(rx, rb, rz);
/*       */   }
/*       */   public static void add(float[][] rx, float[][] ry, float[][] rz) {
/* 10637 */     _add.apply(rx, ry, rz);
/*       */   }
/*       */   public static void add(float ra, float[][] ry, float[][] rz) {
/* 10640 */     _add.apply(ra, ry, rz);
/*       */   }
/*       */   public static void add(float[][] rx, float rb, float[][] rz) {
/* 10643 */     _add.apply(rx, rb, rz);
/*       */   }
/*       */   public static void add(float[][][] rx, float[][][] ry, float[][][] rz) {
/* 10646 */     _add.apply(rx, ry, rz);
/*       */   }
/*       */   public static void add(float ra, float[][][] ry, float[][][] rz) {
/* 10649 */     _add.apply(ra, ry, rz);
/*       */   }
/*       */   public static void add(float[][][] rx, float rb, float[][][] rz) {
/* 10652 */     _add.apply(rx, rb, rz);
/*       */   }
/*       */   public static float[] sub(float[] rx, float[] ry) {
/* 10655 */     return _sub.apply(rx, ry);
/*       */   }
/*       */   public static float[] sub(float ra, float[] ry) {
/* 10658 */     return _sub.apply(ra, ry);
/*       */   }
/*       */   public static float[] sub(float[] rx, float rb) {
/* 10661 */     return _sub.apply(rx, rb);
/*       */   }
/*       */   public static float[][] sub(float[][] rx, float[][] ry) {
/* 10664 */     return _sub.apply(rx, ry);
/*       */   }
/*       */   public static float[][] sub(float ra, float[][] ry) {
/* 10667 */     return _sub.apply(ra, ry);
/*       */   }
/*       */   public static float[][] sub(float[][] rx, float rb) {
/* 10670 */     return _sub.apply(rx, rb);
/*       */   }
/*       */   public static float[][][] sub(float[][][] rx, float[][][] ry) {
/* 10673 */     return _sub.apply(rx, ry);
/*       */   }
/*       */   public static float[][][] sub(float ra, float[][][] ry) {
/* 10676 */     return _sub.apply(ra, ry);
/*       */   }
/*       */   public static float[][][] sub(float[][][] rx, float rb) {
/* 10679 */     return _sub.apply(rx, rb);
/*       */   }
/*       */   public static void sub(float[] rx, float[] ry, float[] rz) {
/* 10682 */     _sub.apply(rx, ry, rz);
/*       */   }
/*       */   public static void sub(float ra, float[] ry, float[] rz) {
/* 10685 */     _sub.apply(ra, ry, rz);
/*       */   }
/*       */   public static void sub(float[] rx, float rb, float[] rz) {
/* 10688 */     _sub.apply(rx, rb, rz);
/*       */   }
/*       */   public static void sub(float[][] rx, float[][] ry, float[][] rz) {
/* 10691 */     _sub.apply(rx, ry, rz);
/*       */   }
/*       */   public static void sub(float ra, float[][] ry, float[][] rz) {
/* 10694 */     _sub.apply(ra, ry, rz);
/*       */   }
/*       */   public static void sub(float[][] rx, float rb, float[][] rz) {
/* 10697 */     _sub.apply(rx, rb, rz);
/*       */   }
/*       */   public static void sub(float[][][] rx, float[][][] ry, float[][][] rz) {
/* 10700 */     _sub.apply(rx, ry, rz);
/*       */   }
/*       */   public static void sub(float ra, float[][][] ry, float[][][] rz) {
/* 10703 */     _sub.apply(ra, ry, rz);
/*       */   }
/*       */   public static void sub(float[][][] rx, float rb, float[][][] rz) {
/* 10706 */     _sub.apply(rx, rb, rz);
/*       */   }
/*       */   public static float[] mul(float[] rx, float[] ry) {
/* 10709 */     return _mul.apply(rx, ry);
/*       */   }
/*       */   public static float[] mul(float ra, float[] ry) {
/* 10712 */     return _mul.apply(ra, ry);
/*       */   }
/*       */   public static float[] mul(float[] rx, float rb) {
/* 10715 */     return _mul.apply(rx, rb);
/*       */   }
/*       */   public static float[][] mul(float[][] rx, float[][] ry) {
/* 10718 */     return _mul.apply(rx, ry);
/*       */   }
/*       */   public static float[][] mul(float ra, float[][] ry) {
/* 10721 */     return _mul.apply(ra, ry);
/*       */   }
/*       */   public static float[][] mul(float[][] rx, float rb) {
/* 10724 */     return _mul.apply(rx, rb);
/*       */   }
/*       */   public static float[][][] mul(float[][][] rx, float[][][] ry) {
/* 10727 */     return _mul.apply(rx, ry);
/*       */   }
/*       */   public static float[][][] mul(float ra, float[][][] ry) {
/* 10730 */     return _mul.apply(ra, ry);
/*       */   }
/*       */   public static float[][][] mul(float[][][] rx, float rb) {
/* 10733 */     return _mul.apply(rx, rb);
/*       */   }
/*       */   public static void mul(float[] rx, float[] ry, float[] rz) {
/* 10736 */     _mul.apply(rx, ry, rz);
/*       */   }
/*       */   public static void mul(float ra, float[] ry, float[] rz) {
/* 10739 */     _mul.apply(ra, ry, rz);
/*       */   }
/*       */   public static void mul(float[] rx, float rb, float[] rz) {
/* 10742 */     _mul.apply(rx, rb, rz);
/*       */   }
/*       */   public static void mul(float[][] rx, float[][] ry, float[][] rz) {
/* 10745 */     _mul.apply(rx, ry, rz);
/*       */   }
/*       */   public static void mul(float ra, float[][] ry, float[][] rz) {
/* 10748 */     _mul.apply(ra, ry, rz);
/*       */   }
/*       */   public static void mul(float[][] rx, float rb, float[][] rz) {
/* 10751 */     _mul.apply(rx, rb, rz);
/*       */   }
/*       */   public static void mul(float[][][] rx, float[][][] ry, float[][][] rz) {
/* 10754 */     _mul.apply(rx, ry, rz);
/*       */   }
/*       */   public static void mul(float ra, float[][][] ry, float[][][] rz) {
/* 10757 */     _mul.apply(ra, ry, rz);
/*       */   }
/*       */   public static void mul(float[][][] rx, float rb, float[][][] rz) {
/* 10760 */     _mul.apply(rx, rb, rz);
/*       */   }
/*       */   public static float[] div(float[] rx, float[] ry) {
/* 10763 */     return _div.apply(rx, ry);
/*       */   }
/*       */   public static float[] div(float ra, float[] ry) {
/* 10766 */     return _div.apply(ra, ry);
/*       */   }
/*       */   public static float[] div(float[] rx, float rb) {
/* 10769 */     return _div.apply(rx, rb);
/*       */   }
/*       */   public static float[][] div(float[][] rx, float[][] ry) {
/* 10772 */     return _div.apply(rx, ry);
/*       */   }
/*       */   public static float[][] div(float ra, float[][] ry) {
/* 10775 */     return _div.apply(ra, ry);
/*       */   }
/*       */   public static float[][] div(float[][] rx, float rb) {
/* 10778 */     return _div.apply(rx, rb);
/*       */   }
/*       */   public static float[][][] div(float[][][] rx, float[][][] ry) {
/* 10781 */     return _div.apply(rx, ry);
/*       */   }
/*       */   public static float[][][] div(float ra, float[][][] ry) {
/* 10784 */     return _div.apply(ra, ry);
/*       */   }
/*       */   public static float[][][] div(float[][][] rx, float rb) {
/* 10787 */     return _div.apply(rx, rb);
/*       */   }
/*       */   public static void div(float[] rx, float[] ry, float[] rz) {
/* 10790 */     _div.apply(rx, ry, rz);
/*       */   }
/*       */   public static void div(float ra, float[] ry, float[] rz) {
/* 10793 */     _div.apply(ra, ry, rz);
/*       */   }
/*       */   public static void div(float[] rx, float rb, float[] rz) {
/* 10796 */     _div.apply(rx, rb, rz);
/*       */   }
/*       */   public static void div(float[][] rx, float[][] ry, float[][] rz) {
/* 10799 */     _div.apply(rx, ry, rz);
/*       */   }
/*       */   public static void div(float ra, float[][] ry, float[][] rz) {
/* 10802 */     _div.apply(ra, ry, rz);
/*       */   }
/*       */   public static void div(float[][] rx, float rb, float[][] rz) {
/* 10805 */     _div.apply(rx, rb, rz);
/*       */   }
/*       */   public static void div(float[][][] rx, float[][][] ry, float[][][] rz) {
/* 10808 */     _div.apply(rx, ry, rz);
/*       */   }
/*       */   public static void div(float ra, float[][][] ry, float[][][] rz) {
/* 10811 */     _div.apply(ra, ry, rz);
/*       */   }
/*       */   public static void div(float[][][] rx, float rb, float[][][] rz) {
/* 10814 */     _div.apply(rx, rb, rz);
/*       */   }
/*       */   public static double[] add(double[] rx, double[] ry) {
/* 10817 */     return _add.apply(rx, ry);
/*       */   }
/*       */   public static double[] add(double ra, double[] ry) {
/* 10820 */     return _add.apply(ra, ry);
/*       */   }
/*       */   public static double[] add(double[] rx, double rb) {
/* 10823 */     return _add.apply(rx, rb);
/*       */   }
/*       */   public static double[][] add(double[][] rx, double[][] ry) {
/* 10826 */     return _add.apply(rx, ry);
/*       */   }
/*       */   public static double[][] add(double ra, double[][] ry) {
/* 10829 */     return _add.apply(ra, ry);
/*       */   }
/*       */   public static double[][] add(double[][] rx, double rb) {
/* 10832 */     return _add.apply(rx, rb);
/*       */   }
/*       */   public static double[][][] add(double[][][] rx, double[][][] ry) {
/* 10835 */     return _add.apply(rx, ry);
/*       */   }
/*       */   public static double[][][] add(double ra, double[][][] ry) {
/* 10838 */     return _add.apply(ra, ry);
/*       */   }
/*       */   public static double[][][] add(double[][][] rx, double rb) {
/* 10841 */     return _add.apply(rx, rb);
/*       */   }
/*       */   public static void add(double[] rx, double[] ry, double[] rz) {
/* 10844 */     _add.apply(rx, ry, rz);
/*       */   }
/*       */   public static void add(double ra, double[] ry, double[] rz) {
/* 10847 */     _add.apply(ra, ry, rz);
/*       */   }
/*       */   public static void add(double[] rx, double rb, double[] rz) {
/* 10850 */     _add.apply(rx, rb, rz);
/*       */   }
/*       */   public static void add(double[][] rx, double[][] ry, double[][] rz) {
/* 10853 */     _add.apply(rx, ry, rz);
/*       */   }
/*       */   public static void add(double ra, double[][] ry, double[][] rz) {
/* 10856 */     _add.apply(ra, ry, rz);
/*       */   }
/*       */   public static void add(double[][] rx, double rb, double[][] rz) {
/* 10859 */     _add.apply(rx, rb, rz);
/*       */   }
/*       */   public static void add(double[][][] rx, double[][][] ry, double[][][] rz) {
/* 10862 */     _add.apply(rx, ry, rz);
/*       */   }
/*       */   public static void add(double ra, double[][][] ry, double[][][] rz) {
/* 10865 */     _add.apply(ra, ry, rz);
/*       */   }
/*       */   public static void add(double[][][] rx, double rb, double[][][] rz) {
/* 10868 */     _add.apply(rx, rb, rz);
/*       */   }
/*       */   public static double[] sub(double[] rx, double[] ry) {
/* 10871 */     return _sub.apply(rx, ry);
/*       */   }
/*       */   public static double[] sub(double ra, double[] ry) {
/* 10874 */     return _sub.apply(ra, ry);
/*       */   }
/*       */   public static double[] sub(double[] rx, double rb) {
/* 10877 */     return _sub.apply(rx, rb);
/*       */   }
/*       */   public static double[][] sub(double[][] rx, double[][] ry) {
/* 10880 */     return _sub.apply(rx, ry);
/*       */   }
/*       */   public static double[][] sub(double ra, double[][] ry) {
/* 10883 */     return _sub.apply(ra, ry);
/*       */   }
/*       */   public static double[][] sub(double[][] rx, double rb) {
/* 10886 */     return _sub.apply(rx, rb);
/*       */   }
/*       */   public static double[][][] sub(double[][][] rx, double[][][] ry) {
/* 10889 */     return _sub.apply(rx, ry);
/*       */   }
/*       */   public static double[][][] sub(double ra, double[][][] ry) {
/* 10892 */     return _sub.apply(ra, ry);
/*       */   }
/*       */   public static double[][][] sub(double[][][] rx, double rb) {
/* 10895 */     return _sub.apply(rx, rb);
/*       */   }
/*       */   public static void sub(double[] rx, double[] ry, double[] rz) {
/* 10898 */     _sub.apply(rx, ry, rz);
/*       */   }
/*       */   public static void sub(double ra, double[] ry, double[] rz) {
/* 10901 */     _sub.apply(ra, ry, rz);
/*       */   }
/*       */   public static void sub(double[] rx, double rb, double[] rz) {
/* 10904 */     _sub.apply(rx, rb, rz);
/*       */   }
/*       */   public static void sub(double[][] rx, double[][] ry, double[][] rz) {
/* 10907 */     _sub.apply(rx, ry, rz);
/*       */   }
/*       */   public static void sub(double ra, double[][] ry, double[][] rz) {
/* 10910 */     _sub.apply(ra, ry, rz);
/*       */   }
/*       */   public static void sub(double[][] rx, double rb, double[][] rz) {
/* 10913 */     _sub.apply(rx, rb, rz);
/*       */   }
/*       */   public static void sub(double[][][] rx, double[][][] ry, double[][][] rz) {
/* 10916 */     _sub.apply(rx, ry, rz);
/*       */   }
/*       */   public static void sub(double ra, double[][][] ry, double[][][] rz) {
/* 10919 */     _sub.apply(ra, ry, rz);
/*       */   }
/*       */   public static void sub(double[][][] rx, double rb, double[][][] rz) {
/* 10922 */     _sub.apply(rx, rb, rz);
/*       */   }
/*       */   public static double[] mul(double[] rx, double[] ry) {
/* 10925 */     return _mul.apply(rx, ry);
/*       */   }
/*       */   public static double[] mul(double ra, double[] ry) {
/* 10928 */     return _mul.apply(ra, ry);
/*       */   }
/*       */   public static double[] mul(double[] rx, double rb) {
/* 10931 */     return _mul.apply(rx, rb);
/*       */   }
/*       */   public static double[][] mul(double[][] rx, double[][] ry) {
/* 10934 */     return _mul.apply(rx, ry);
/*       */   }
/*       */   public static double[][] mul(double ra, double[][] ry) {
/* 10937 */     return _mul.apply(ra, ry);
/*       */   }
/*       */   public static double[][] mul(double[][] rx, double rb) {
/* 10940 */     return _mul.apply(rx, rb);
/*       */   }
/*       */   public static double[][][] mul(double[][][] rx, double[][][] ry) {
/* 10943 */     return _mul.apply(rx, ry);
/*       */   }
/*       */   public static double[][][] mul(double ra, double[][][] ry) {
/* 10946 */     return _mul.apply(ra, ry);
/*       */   }
/*       */   public static double[][][] mul(double[][][] rx, double rb) {
/* 10949 */     return _mul.apply(rx, rb);
/*       */   }
/*       */   public static void mul(double[] rx, double[] ry, double[] rz) {
/* 10952 */     _mul.apply(rx, ry, rz);
/*       */   }
/*       */   public static void mul(double ra, double[] ry, double[] rz) {
/* 10955 */     _mul.apply(ra, ry, rz);
/*       */   }
/*       */   public static void mul(double[] rx, double rb, double[] rz) {
/* 10958 */     _mul.apply(rx, rb, rz);
/*       */   }
/*       */   public static void mul(double[][] rx, double[][] ry, double[][] rz) {
/* 10961 */     _mul.apply(rx, ry, rz);
/*       */   }
/*       */   public static void mul(double ra, double[][] ry, double[][] rz) {
/* 10964 */     _mul.apply(ra, ry, rz);
/*       */   }
/*       */   public static void mul(double[][] rx, double rb, double[][] rz) {
/* 10967 */     _mul.apply(rx, rb, rz);
/*       */   }
/*       */   public static void mul(double[][][] rx, double[][][] ry, double[][][] rz) {
/* 10970 */     _mul.apply(rx, ry, rz);
/*       */   }
/*       */   public static void mul(double ra, double[][][] ry, double[][][] rz) {
/* 10973 */     _mul.apply(ra, ry, rz);
/*       */   }
/*       */   public static void mul(double[][][] rx, double rb, double[][][] rz) {
/* 10976 */     _mul.apply(rx, rb, rz);
/*       */   }
/*       */   public static double[] div(double[] rx, double[] ry) {
/* 10979 */     return _div.apply(rx, ry);
/*       */   }
/*       */   public static double[] div(double ra, double[] ry) {
/* 10982 */     return _div.apply(ra, ry);
/*       */   }
/*       */   public static double[] div(double[] rx, double rb) {
/* 10985 */     return _div.apply(rx, rb);
/*       */   }
/*       */   public static double[][] div(double[][] rx, double[][] ry) {
/* 10988 */     return _div.apply(rx, ry);
/*       */   }
/*       */   public static double[][] div(double ra, double[][] ry) {
/* 10991 */     return _div.apply(ra, ry);
/*       */   }
/*       */   public static double[][] div(double[][] rx, double rb) {
/* 10994 */     return _div.apply(rx, rb);
/*       */   }
/*       */   public static double[][][] div(double[][][] rx, double[][][] ry) {
/* 10997 */     return _div.apply(rx, ry);
/*       */   }
/*       */   public static double[][][] div(double ra, double[][][] ry) {
/* 11000 */     return _div.apply(ra, ry);
/*       */   }
/*       */   public static double[][][] div(double[][][] rx, double rb) {
/* 11003 */     return _div.apply(rx, rb);
/*       */   }
/*       */   public static void div(double[] rx, double[] ry, double[] rz) {
/* 11006 */     _div.apply(rx, ry, rz);
/*       */   }
/*       */   public static void div(double ra, double[] ry, double[] rz) {
/* 11009 */     _div.apply(ra, ry, rz);
/*       */   }
/*       */   public static void div(double[] rx, double rb, double[] rz) {
/* 11012 */     _div.apply(rx, rb, rz);
/*       */   }
/*       */   public static void div(double[][] rx, double[][] ry, double[][] rz) {
/* 11015 */     _div.apply(rx, ry, rz);
/*       */   }
/*       */   public static void div(double ra, double[][] ry, double[][] rz) {
/* 11018 */     _div.apply(ra, ry, rz);
/*       */   }
/*       */   public static void div(double[][] rx, double rb, double[][] rz) {
/* 11021 */     _div.apply(rx, rb, rz);
/*       */   }
/*       */   public static void div(double[][][] rx, double[][][] ry, double[][][] rz) {
/* 11024 */     _div.apply(rx, ry, rz);
/*       */   }
/*       */   public static void div(double ra, double[][][] ry, double[][][] rz) {
/* 11027 */     _div.apply(ra, ry, rz);
/*       */   }
/*       */   public static void div(double[][][] rx, double rb, double[][][] rz) {
/* 11030 */     _div.apply(rx, rb, rz);
/*       */   }
/*       */   private static abstract class Binary { private Binary() {}
/*       */     float[] apply(float[] rx, float[] ry) {
/* 11034 */       int n1 = rx.length;
/* 11035 */       float[] rz = new float[n1];
/* 11036 */       apply(rx, ry, rz);
/* 11037 */       return rz;
/*       */     }
/*       */     float[] apply(float ra, float[] ry) {
/* 11040 */       int n1 = ry.length;
/* 11041 */       float[] rz = new float[n1];
/* 11042 */       apply(ra, ry, rz);
/* 11043 */       return rz;
/*       */     }
/*       */     float[] apply(float[] rx, float rb) {
/* 11046 */       int n1 = rx.length;
/* 11047 */       float[] rz = new float[n1];
/* 11048 */       apply(rx, rb, rz);
/* 11049 */       return rz;
/*       */     }
/*       */     float[][] apply(float[][] rx, float[][] ry) {
/* 11052 */       int n2 = rx.length;
/* 11053 */       float[][] rz = new float[n2][];
/* 11054 */       for (int i2 = 0; i2 < n2; i2++)
/* 11055 */         rz[i2] = apply(rx[i2], ry[i2]); 
/* 11056 */       return rz;
/*       */     }
/*       */     float[][] apply(float ra, float[][] ry) {
/* 11059 */       int n2 = ry.length;
/* 11060 */       float[][] rz = new float[n2][];
/* 11061 */       for (int i2 = 0; i2 < n2; i2++)
/* 11062 */         rz[i2] = apply(ra, ry[i2]); 
/* 11063 */       return rz;
/*       */     }
/*       */     float[][] apply(float[][] rx, float rb) {
/* 11066 */       int n2 = rx.length;
/* 11067 */       float[][] rz = new float[n2][];
/* 11068 */       for (int i2 = 0; i2 < n2; i2++)
/* 11069 */         rz[i2] = apply(rx[i2], rb); 
/* 11070 */       return rz;
/*       */     }
/*       */     float[][][] apply(float[][][] rx, float[][][] ry) {
/* 11073 */       int n3 = rx.length;
/* 11074 */       float[][][] rz = new float[n3][][];
/* 11075 */       for (int i3 = 0; i3 < n3; i3++)
/* 11076 */         rz[i3] = apply(rx[i3], ry[i3]); 
/* 11077 */       return rz;
/*       */     }
/*       */     float[][][] apply(float ra, float[][][] ry) {
/* 11080 */       int n3 = ry.length;
/* 11081 */       float[][][] rz = new float[n3][][];
/* 11082 */       for (int i3 = 0; i3 < n3; i3++)
/* 11083 */         rz[i3] = apply(ra, ry[i3]); 
/* 11084 */       return rz;
/*       */     }
/*       */     float[][][] apply(float[][][] rx, float rb) {
/* 11087 */       int n3 = rx.length;
/* 11088 */       float[][][] rz = new float[n3][][];
/* 11089 */       for (int i3 = 0; i3 < n3; i3++)
/* 11090 */         rz[i3] = apply(rx[i3], rb); 
/* 11091 */       return rz;
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*       */     void apply(float[][] rx, float[][] ry, float[][] rz) {
/* 11097 */       int n2 = rx.length;
/* 11098 */       for (int i2 = 0; i2 < n2; i2++)
/* 11099 */         apply(rx[i2], ry[i2], rz[i2]); 
/*       */     }
/*       */     void apply(float ra, float[][] ry, float[][] rz) {
/* 11102 */       int n2 = ry.length;
/* 11103 */       for (int i2 = 0; i2 < n2; i2++)
/* 11104 */         apply(ra, ry[i2], rz[i2]); 
/*       */     }
/*       */     void apply(float[][] rx, float rb, float[][] rz) {
/* 11107 */       int n2 = rx.length;
/* 11108 */       for (int i2 = 0; i2 < n2; i2++)
/* 11109 */         apply(rx[i2], rb, rz[i2]); 
/*       */     }
/*       */     void apply(float[][][] rx, float[][][] ry, float[][][] rz) {
/* 11112 */       int n3 = rx.length;
/* 11113 */       for (int i3 = 0; i3 < n3; i3++)
/* 11114 */         apply(rx[i3], ry[i3], rz[i3]); 
/*       */     }
/*       */     void apply(float ra, float[][][] ry, float[][][] rz) {
/* 11117 */       int n3 = ry.length;
/* 11118 */       for (int i3 = 0; i3 < n3; i3++)
/* 11119 */         apply(ra, ry[i3], rz[i3]); 
/*       */     }
/*       */     void apply(float[][][] rx, float rb, float[][][] rz) {
/* 11122 */       int n3 = rx.length;
/* 11123 */       for (int i3 = 0; i3 < n3; i3++)
/* 11124 */         apply(rx[i3], rb, rz[i3]); 
/*       */     }
/*       */     double[] apply(double[] rx, double[] ry) {
/* 11127 */       int n1 = rx.length;
/* 11128 */       double[] rz = new double[n1];
/* 11129 */       apply(rx, ry, rz);
/* 11130 */       return rz;
/*       */     }
/*       */     double[] apply(double ra, double[] ry) {
/* 11133 */       int n1 = ry.length;
/* 11134 */       double[] rz = new double[n1];
/* 11135 */       apply(ra, ry, rz);
/* 11136 */       return rz;
/*       */     }
/*       */     double[] apply(double[] rx, double rb) {
/* 11139 */       int n1 = rx.length;
/* 11140 */       double[] rz = new double[n1];
/* 11141 */       apply(rx, rb, rz);
/* 11142 */       return rz;
/*       */     }
/*       */     double[][] apply(double[][] rx, double[][] ry) {
/* 11145 */       int n2 = rx.length;
/* 11146 */       double[][] rz = new double[n2][];
/* 11147 */       for (int i2 = 0; i2 < n2; i2++)
/* 11148 */         rz[i2] = apply(rx[i2], ry[i2]); 
/* 11149 */       return rz;
/*       */     }
/*       */     double[][] apply(double ra, double[][] ry) {
/* 11152 */       int n2 = ry.length;
/* 11153 */       double[][] rz = new double[n2][];
/* 11154 */       for (int i2 = 0; i2 < n2; i2++)
/* 11155 */         rz[i2] = apply(ra, ry[i2]); 
/* 11156 */       return rz;
/*       */     }
/*       */     double[][] apply(double[][] rx, double rb) {
/* 11159 */       int n2 = rx.length;
/* 11160 */       double[][] rz = new double[n2][];
/* 11161 */       for (int i2 = 0; i2 < n2; i2++)
/* 11162 */         rz[i2] = apply(rx[i2], rb); 
/* 11163 */       return rz;
/*       */     }
/*       */     double[][][] apply(double[][][] rx, double[][][] ry) {
/* 11166 */       int n3 = rx.length;
/* 11167 */       double[][][] rz = new double[n3][][];
/* 11168 */       for (int i3 = 0; i3 < n3; i3++)
/* 11169 */         rz[i3] = apply(rx[i3], ry[i3]); 
/* 11170 */       return rz;
/*       */     }
/*       */     double[][][] apply(double ra, double[][][] ry) {
/* 11173 */       int n3 = ry.length;
/* 11174 */       double[][][] rz = new double[n3][][];
/* 11175 */       for (int i3 = 0; i3 < n3; i3++)
/* 11176 */         rz[i3] = apply(ra, ry[i3]); 
/* 11177 */       return rz;
/*       */     }
/*       */     double[][][] apply(double[][][] rx, double rb) {
/* 11180 */       int n3 = rx.length;
/* 11181 */       double[][][] rz = new double[n3][][];
/* 11182 */       for (int i3 = 0; i3 < n3; i3++)
/* 11183 */         rz[i3] = apply(rx[i3], rb); 
/* 11184 */       return rz;
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*       */     void apply(double[][] rx, double[][] ry, double[][] rz) {
/* 11190 */       int n2 = rx.length;
/* 11191 */       for (int i2 = 0; i2 < n2; i2++)
/* 11192 */         apply(rx[i2], ry[i2], rz[i2]); 
/*       */     }
/*       */     void apply(double ra, double[][] ry, double[][] rz) {
/* 11195 */       int n2 = ry.length;
/* 11196 */       for (int i2 = 0; i2 < n2; i2++)
/* 11197 */         apply(ra, ry[i2], rz[i2]); 
/*       */     }
/*       */     void apply(double[][] rx, double rb, double[][] rz) {
/* 11200 */       int n2 = rx.length;
/* 11201 */       for (int i2 = 0; i2 < n2; i2++)
/* 11202 */         apply(rx[i2], rb, rz[i2]); 
/*       */     }
/*       */     void apply(double[][][] rx, double[][][] ry, double[][][] rz) {
/* 11205 */       int n3 = rx.length;
/* 11206 */       for (int i3 = 0; i3 < n3; i3++)
/* 11207 */         apply(rx[i3], ry[i3], rz[i3]); 
/*       */     }
/*       */     void apply(double ra, double[][][] ry, double[][][] rz) {
/* 11210 */       int n3 = ry.length;
/* 11211 */       for (int i3 = 0; i3 < n3; i3++)
/* 11212 */         apply(ra, ry[i3], rz[i3]); 
/*       */     } abstract void apply(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2, float[] param1ArrayOffloat3); abstract void apply(float param1Float, float[] param1ArrayOffloat1, float[] param1ArrayOffloat2); abstract void apply(float[] param1ArrayOffloat1, float param1Float, float[] param1ArrayOffloat2);
/*       */     void apply(double[][][] rx, double rb, double[][][] rz) {
/* 11215 */       int n3 = rx.length;
/* 11216 */       for (int i3 = 0; i3 < n3; i3++)
/* 11217 */         apply(rx[i3], rb, rz[i3]); 
/*       */     } abstract void apply(double[] param1ArrayOfdouble1, double[] param1ArrayOfdouble2, double[] param1ArrayOfdouble3); abstract void apply(double param1Double, double[] param1ArrayOfdouble1, double[] param1ArrayOfdouble2);
/*       */     abstract void apply(double[] param1ArrayOfdouble1, double param1Double, double[] param1ArrayOfdouble2); }
/* 11220 */   private static Binary _add = new Binary() {
/*       */       void apply(float[] rx, float[] ry, float[] rz) {
/* 11222 */         int n1 = rx.length;
/* 11223 */         for (int i1 = 0; i1 < n1; i1++)
/* 11224 */           rz[i1] = rx[i1] + ry[i1]; 
/*       */       }
/*       */       void apply(float ra, float[] ry, float[] rz) {
/* 11227 */         int n1 = ry.length;
/* 11228 */         for (int i1 = 0; i1 < n1; i1++)
/* 11229 */           rz[i1] = ra + ry[i1]; 
/*       */       }
/*       */       void apply(float[] rx, float rb, float[] rz) {
/* 11232 */         int n1 = rx.length;
/* 11233 */         for (int i1 = 0; i1 < n1; i1++)
/* 11234 */           rz[i1] = rx[i1] + rb; 
/*       */       }
/*       */       void apply(double[] rx, double[] ry, double[] rz) {
/* 11237 */         int n1 = rx.length;
/* 11238 */         for (int i1 = 0; i1 < n1; i1++)
/* 11239 */           rz[i1] = rx[i1] + ry[i1]; 
/*       */       }
/*       */       void apply(double ra, double[] ry, double[] rz) {
/* 11242 */         int n1 = ry.length;
/* 11243 */         for (int i1 = 0; i1 < n1; i1++)
/* 11244 */           rz[i1] = ra + ry[i1]; 
/*       */       }
/*       */       void apply(double[] rx, double rb, double[] rz) {
/* 11247 */         int n1 = rx.length;
/* 11248 */         for (int i1 = 0; i1 < n1; i1++)
/* 11249 */           rz[i1] = rx[i1] + rb; 
/*       */       }
/*       */     };
/* 11252 */   private static Binary _sub = new Binary() {
/*       */       void apply(float[] rx, float[] ry, float[] rz) {
/* 11254 */         int n1 = rx.length;
/* 11255 */         for (int i1 = 0; i1 < n1; i1++)
/* 11256 */           rz[i1] = rx[i1] - ry[i1]; 
/*       */       }
/*       */       void apply(float ra, float[] ry, float[] rz) {
/* 11259 */         int n1 = ry.length;
/* 11260 */         for (int i1 = 0; i1 < n1; i1++)
/* 11261 */           rz[i1] = ra - ry[i1]; 
/*       */       }
/*       */       void apply(float[] rx, float rb, float[] rz) {
/* 11264 */         int n1 = rx.length;
/* 11265 */         for (int i1 = 0; i1 < n1; i1++)
/* 11266 */           rz[i1] = rx[i1] - rb; 
/*       */       }
/*       */       void apply(double[] rx, double[] ry, double[] rz) {
/* 11269 */         int n1 = rx.length;
/* 11270 */         for (int i1 = 0; i1 < n1; i1++)
/* 11271 */           rz[i1] = rx[i1] - ry[i1]; 
/*       */       }
/*       */       void apply(double ra, double[] ry, double[] rz) {
/* 11274 */         int n1 = ry.length;
/* 11275 */         for (int i1 = 0; i1 < n1; i1++)
/* 11276 */           rz[i1] = ra - ry[i1]; 
/*       */       }
/*       */       void apply(double[] rx, double rb, double[] rz) {
/* 11279 */         int n1 = rx.length;
/* 11280 */         for (int i1 = 0; i1 < n1; i1++)
/* 11281 */           rz[i1] = rx[i1] - rb; 
/*       */       }
/*       */     };
/* 11284 */   private static Binary _mul = new Binary() {
/*       */       void apply(float[] rx, float[] ry, float[] rz) {
/* 11286 */         int n1 = rx.length;
/* 11287 */         for (int i1 = 0; i1 < n1; i1++)
/* 11288 */           rz[i1] = rx[i1] * ry[i1]; 
/*       */       }
/*       */       void apply(float ra, float[] ry, float[] rz) {
/* 11291 */         int n1 = ry.length;
/* 11292 */         for (int i1 = 0; i1 < n1; i1++)
/* 11293 */           rz[i1] = ra * ry[i1]; 
/*       */       }
/*       */       void apply(float[] rx, float rb, float[] rz) {
/* 11296 */         int n1 = rx.length;
/* 11297 */         for (int i1 = 0; i1 < n1; i1++)
/* 11298 */           rz[i1] = rx[i1] * rb; 
/*       */       }
/*       */       void apply(double[] rx, double[] ry, double[] rz) {
/* 11301 */         int n1 = rx.length;
/* 11302 */         for (int i1 = 0; i1 < n1; i1++)
/* 11303 */           rz[i1] = rx[i1] * ry[i1]; 
/*       */       }
/*       */       void apply(double ra, double[] ry, double[] rz) {
/* 11306 */         int n1 = ry.length;
/* 11307 */         for (int i1 = 0; i1 < n1; i1++)
/* 11308 */           rz[i1] = ra * ry[i1]; 
/*       */       }
/*       */       void apply(double[] rx, double rb, double[] rz) {
/* 11311 */         int n1 = rx.length;
/* 11312 */         for (int i1 = 0; i1 < n1; i1++)
/* 11313 */           rz[i1] = rx[i1] * rb; 
/*       */       }
/*       */     };
/* 11316 */   private static Binary _div = new Binary() {
/*       */       void apply(float[] rx, float[] ry, float[] rz) {
/* 11318 */         int n1 = rx.length;
/* 11319 */         for (int i1 = 0; i1 < n1; i1++)
/* 11320 */           rz[i1] = rx[i1] / ry[i1]; 
/*       */       }
/*       */       void apply(float ra, float[] ry, float[] rz) {
/* 11323 */         int n1 = ry.length;
/* 11324 */         for (int i1 = 0; i1 < n1; i1++)
/* 11325 */           rz[i1] = ra / ry[i1]; 
/*       */       }
/*       */       void apply(float[] rx, float rb, float[] rz) {
/* 11328 */         int n1 = rx.length;
/* 11329 */         for (int i1 = 0; i1 < n1; i1++)
/* 11330 */           rz[i1] = rx[i1] / rb; 
/*       */       }
/*       */       void apply(double[] rx, double[] ry, double[] rz) {
/* 11333 */         int n1 = rx.length;
/* 11334 */         for (int i1 = 0; i1 < n1; i1++)
/* 11335 */           rz[i1] = rx[i1] / ry[i1]; 
/*       */       }
/*       */       void apply(double ra, double[] ry, double[] rz) {
/* 11338 */         int n1 = ry.length;
/* 11339 */         for (int i1 = 0; i1 < n1; i1++)
/* 11340 */           rz[i1] = ra / ry[i1]; 
/*       */       }
/*       */       void apply(double[] rx, double rb, double[] rz) {
/* 11343 */         int n1 = rx.length;
/* 11344 */         for (int i1 = 0; i1 < n1; i1++) {
/* 11345 */           rz[i1] = rx[i1] / rb;
/*       */         }
/*       */       }
/*       */     };
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] abs(float[] rx) {
/* 11353 */     return _abs.apply(rx);
/*       */   }
/*       */   public static float[][] abs(float[][] rx) {
/* 11356 */     return _abs.apply(rx);
/*       */   }
/*       */   public static float[][][] abs(float[][][] rx) {
/* 11359 */     return _abs.apply(rx);
/*       */   }
/*       */   public static void abs(float[] rx, float[] ry) {
/* 11362 */     _abs.apply(rx, ry);
/*       */   }
/*       */   public static void abs(float[][] rx, float[][] ry) {
/* 11365 */     _abs.apply(rx, ry);
/*       */   }
/*       */   public static void abs(float[][][] rx, float[][][] ry) {
/* 11368 */     _abs.apply(rx, ry);
/*       */   }
/*       */   public static float[] neg(float[] rx) {
/* 11371 */     return _neg.apply(rx);
/*       */   }
/*       */   public static float[][] neg(float[][] rx) {
/* 11374 */     return _neg.apply(rx);
/*       */   }
/*       */   public static float[][][] neg(float[][][] rx) {
/* 11377 */     return _neg.apply(rx);
/*       */   }
/*       */   public static void neg(float[] rx, float[] ry) {
/* 11380 */     _neg.apply(rx, ry);
/*       */   }
/*       */   public static void neg(float[][] rx, float[][] ry) {
/* 11383 */     _neg.apply(rx, ry);
/*       */   }
/*       */   public static void neg(float[][][] rx, float[][][] ry) {
/* 11386 */     _neg.apply(rx, ry);
/*       */   }
/*       */   public static float[] cos(float[] rx) {
/* 11389 */     return _cos.apply(rx);
/*       */   }
/*       */   public static float[][] cos(float[][] rx) {
/* 11392 */     return _cos.apply(rx);
/*       */   }
/*       */   public static float[][][] cos(float[][][] rx) {
/* 11395 */     return _cos.apply(rx);
/*       */   }
/*       */   public static void cos(float[] rx, float[] ry) {
/* 11398 */     _cos.apply(rx, ry);
/*       */   }
/*       */   public static void cos(float[][] rx, float[][] ry) {
/* 11401 */     _cos.apply(rx, ry);
/*       */   }
/*       */   public static void cos(float[][][] rx, float[][][] ry) {
/* 11404 */     _cos.apply(rx, ry);
/*       */   }
/*       */   public static float[] sin(float[] rx) {
/* 11407 */     return _sin.apply(rx);
/*       */   }
/*       */   public static float[][] sin(float[][] rx) {
/* 11410 */     return _sin.apply(rx);
/*       */   }
/*       */   public static float[][][] sin(float[][][] rx) {
/* 11413 */     return _sin.apply(rx);
/*       */   }
/*       */   public static void sin(float[] rx, float[] ry) {
/* 11416 */     _sin.apply(rx, ry);
/*       */   }
/*       */   public static void sin(float[][] rx, float[][] ry) {
/* 11419 */     _sin.apply(rx, ry);
/*       */   }
/*       */   public static void sin(float[][][] rx, float[][][] ry) {
/* 11422 */     _sin.apply(rx, ry);
/*       */   }
/*       */   public static float[] exp(float[] rx) {
/* 11425 */     return _exp.apply(rx);
/*       */   }
/*       */   public static float[][] exp(float[][] rx) {
/* 11428 */     return _exp.apply(rx);
/*       */   }
/*       */   public static float[][][] exp(float[][][] rx) {
/* 11431 */     return _exp.apply(rx);
/*       */   }
/*       */   public static void exp(float[] rx, float[] ry) {
/* 11434 */     _exp.apply(rx, ry);
/*       */   }
/*       */   public static void exp(float[][] rx, float[][] ry) {
/* 11437 */     _exp.apply(rx, ry);
/*       */   }
/*       */   public static void exp(float[][][] rx, float[][][] ry) {
/* 11440 */     _exp.apply(rx, ry);
/*       */   }
/*       */   public static float[] log(float[] rx) {
/* 11443 */     return _log.apply(rx);
/*       */   }
/*       */   public static float[][] log(float[][] rx) {
/* 11446 */     return _log.apply(rx);
/*       */   }
/*       */   public static float[][][] log(float[][][] rx) {
/* 11449 */     return _log.apply(rx);
/*       */   }
/*       */   public static void log(float[] rx, float[] ry) {
/* 11452 */     _log.apply(rx, ry);
/*       */   }
/*       */   public static void log(float[][] rx, float[][] ry) {
/* 11455 */     _log.apply(rx, ry);
/*       */   }
/*       */   public static void log(float[][][] rx, float[][][] ry) {
/* 11458 */     _log.apply(rx, ry);
/*       */   }
/*       */   public static float[] log10(float[] rx) {
/* 11461 */     return _log10.apply(rx);
/*       */   }
/*       */   public static float[][] log10(float[][] rx) {
/* 11464 */     return _log10.apply(rx);
/*       */   }
/*       */   public static float[][][] log10(float[][][] rx) {
/* 11467 */     return _log10.apply(rx);
/*       */   }
/*       */   public static void log10(float[] rx, float[] ry) {
/* 11470 */     _log10.apply(rx, ry);
/*       */   }
/*       */   public static void log10(float[][] rx, float[][] ry) {
/* 11473 */     _log10.apply(rx, ry);
/*       */   }
/*       */   public static void log10(float[][][] rx, float[][][] ry) {
/* 11476 */     _log10.apply(rx, ry);
/*       */   }
/*       */   public static float[] sqrt(float[] rx) {
/* 11479 */     return _sqrt.apply(rx);
/*       */   }
/*       */   public static float[][] sqrt(float[][] rx) {
/* 11482 */     return _sqrt.apply(rx);
/*       */   }
/*       */   public static float[][][] sqrt(float[][][] rx) {
/* 11485 */     return _sqrt.apply(rx);
/*       */   }
/*       */   public static void sqrt(float[] rx, float[] ry) {
/* 11488 */     _sqrt.apply(rx, ry);
/*       */   }
/*       */   public static void sqrt(float[][] rx, float[][] ry) {
/* 11491 */     _sqrt.apply(rx, ry);
/*       */   }
/*       */   public static void sqrt(float[][][] rx, float[][][] ry) {
/* 11494 */     _sqrt.apply(rx, ry);
/*       */   }
/*       */   public static float[] sgn(float[] rx) {
/* 11497 */     return _sgn.apply(rx);
/*       */   }
/*       */   public static float[][] sgn(float[][] rx) {
/* 11500 */     return _sgn.apply(rx);
/*       */   }
/*       */   public static float[][][] sgn(float[][][] rx) {
/* 11503 */     return _sgn.apply(rx);
/*       */   }
/*       */   public static void sgn(float[] rx, float[] ry) {
/* 11506 */     _sgn.apply(rx, ry);
/*       */   }
/*       */   public static void sgn(float[][] rx, float[][] ry) {
/* 11509 */     _sgn.apply(rx, ry);
/*       */   }
/*       */   public static void sgn(float[][][] rx, float[][][] ry) {
/* 11512 */     _sgn.apply(rx, ry);
/*       */   }
/*       */   public static double[] abs(double[] rx) {
/* 11515 */     return _abs.apply(rx);
/*       */   }
/*       */   public static double[][] abs(double[][] rx) {
/* 11518 */     return _abs.apply(rx);
/*       */   }
/*       */   public static double[][][] abs(double[][][] rx) {
/* 11521 */     return _abs.apply(rx);
/*       */   }
/*       */   public static void abs(double[] rx, double[] ry) {
/* 11524 */     _abs.apply(rx, ry);
/*       */   }
/*       */   public static void abs(double[][] rx, double[][] ry) {
/* 11527 */     _abs.apply(rx, ry);
/*       */   }
/*       */   public static void abs(double[][][] rx, double[][][] ry) {
/* 11530 */     _abs.apply(rx, ry);
/*       */   }
/*       */   public static double[] neg(double[] rx) {
/* 11533 */     return _neg.apply(rx);
/*       */   }
/*       */   public static double[][] neg(double[][] rx) {
/* 11536 */     return _neg.apply(rx);
/*       */   }
/*       */   public static double[][][] neg(double[][][] rx) {
/* 11539 */     return _neg.apply(rx);
/*       */   }
/*       */   public static void neg(double[] rx, double[] ry) {
/* 11542 */     _neg.apply(rx, ry);
/*       */   }
/*       */   public static void neg(double[][] rx, double[][] ry) {
/* 11545 */     _neg.apply(rx, ry);
/*       */   }
/*       */   public static void neg(double[][][] rx, double[][][] ry) {
/* 11548 */     _neg.apply(rx, ry);
/*       */   }
/*       */   public static double[] cos(double[] rx) {
/* 11551 */     return _cos.apply(rx);
/*       */   }
/*       */   public static double[][] cos(double[][] rx) {
/* 11554 */     return _cos.apply(rx);
/*       */   }
/*       */   public static double[][][] cos(double[][][] rx) {
/* 11557 */     return _cos.apply(rx);
/*       */   }
/*       */   public static void cos(double[] rx, double[] ry) {
/* 11560 */     _cos.apply(rx, ry);
/*       */   }
/*       */   public static void cos(double[][] rx, double[][] ry) {
/* 11563 */     _cos.apply(rx, ry);
/*       */   }
/*       */   public static void cos(double[][][] rx, double[][][] ry) {
/* 11566 */     _cos.apply(rx, ry);
/*       */   }
/*       */   public static double[] sin(double[] rx) {
/* 11569 */     return _sin.apply(rx);
/*       */   }
/*       */   public static double[][] sin(double[][] rx) {
/* 11572 */     return _sin.apply(rx);
/*       */   }
/*       */   public static double[][][] sin(double[][][] rx) {
/* 11575 */     return _sin.apply(rx);
/*       */   }
/*       */   public static void sin(double[] rx, double[] ry) {
/* 11578 */     _sin.apply(rx, ry);
/*       */   }
/*       */   public static void sin(double[][] rx, double[][] ry) {
/* 11581 */     _sin.apply(rx, ry);
/*       */   }
/*       */   public static void sin(double[][][] rx, double[][][] ry) {
/* 11584 */     _sin.apply(rx, ry);
/*       */   }
/*       */   public static double[] exp(double[] rx) {
/* 11587 */     return _exp.apply(rx);
/*       */   }
/*       */   public static double[][] exp(double[][] rx) {
/* 11590 */     return _exp.apply(rx);
/*       */   }
/*       */   public static double[][][] exp(double[][][] rx) {
/* 11593 */     return _exp.apply(rx);
/*       */   }
/*       */   public static void exp(double[] rx, double[] ry) {
/* 11596 */     _exp.apply(rx, ry);
/*       */   }
/*       */   public static void exp(double[][] rx, double[][] ry) {
/* 11599 */     _exp.apply(rx, ry);
/*       */   }
/*       */   public static void exp(double[][][] rx, double[][][] ry) {
/* 11602 */     _exp.apply(rx, ry);
/*       */   }
/*       */   public static double[] log(double[] rx) {
/* 11605 */     return _log.apply(rx);
/*       */   }
/*       */   public static double[][] log(double[][] rx) {
/* 11608 */     return _log.apply(rx);
/*       */   }
/*       */   public static double[][][] log(double[][][] rx) {
/* 11611 */     return _log.apply(rx);
/*       */   }
/*       */   public static void log(double[] rx, double[] ry) {
/* 11614 */     _log.apply(rx, ry);
/*       */   }
/*       */   public static void log(double[][] rx, double[][] ry) {
/* 11617 */     _log.apply(rx, ry);
/*       */   }
/*       */   public static void log(double[][][] rx, double[][][] ry) {
/* 11620 */     _log.apply(rx, ry);
/*       */   }
/*       */   public static double[] log10(double[] rx) {
/* 11623 */     return _log10.apply(rx);
/*       */   }
/*       */   public static double[][] log10(double[][] rx) {
/* 11626 */     return _log10.apply(rx);
/*       */   }
/*       */   public static double[][][] log10(double[][][] rx) {
/* 11629 */     return _log10.apply(rx);
/*       */   }
/*       */   public static void log10(double[] rx, double[] ry) {
/* 11632 */     _log10.apply(rx, ry);
/*       */   }
/*       */   public static void log10(double[][] rx, double[][] ry) {
/* 11635 */     _log10.apply(rx, ry);
/*       */   }
/*       */   public static void log10(double[][][] rx, double[][][] ry) {
/* 11638 */     _log10.apply(rx, ry);
/*       */   }
/*       */   public static double[] sqrt(double[] rx) {
/* 11641 */     return _sqrt.apply(rx);
/*       */   }
/*       */   public static double[][] sqrt(double[][] rx) {
/* 11644 */     return _sqrt.apply(rx);
/*       */   }
/*       */   public static double[][][] sqrt(double[][][] rx) {
/* 11647 */     return _sqrt.apply(rx);
/*       */   }
/*       */   public static void sqrt(double[] rx, double[] ry) {
/* 11650 */     _sqrt.apply(rx, ry);
/*       */   }
/*       */   public static void sqrt(double[][] rx, double[][] ry) {
/* 11653 */     _sqrt.apply(rx, ry);
/*       */   }
/*       */   public static void sqrt(double[][][] rx, double[][][] ry) {
/* 11656 */     _sqrt.apply(rx, ry);
/*       */   }
/*       */   public static double[] sgn(double[] rx) {
/* 11659 */     return _sgn.apply(rx);
/*       */   }
/*       */   public static double[][] sgn(double[][] rx) {
/* 11662 */     return _sgn.apply(rx);
/*       */   }
/*       */   public static double[][][] sgn(double[][][] rx) {
/* 11665 */     return _sgn.apply(rx);
/*       */   }
/*       */   public static void sgn(double[] rx, double[] ry) {
/* 11668 */     _sgn.apply(rx, ry);
/*       */   }
/*       */   public static void sgn(double[][] rx, double[][] ry) {
/* 11671 */     _sgn.apply(rx, ry);
/*       */   }
/*       */   public static void sgn(double[][][] rx, double[][][] ry) {
/* 11674 */     _sgn.apply(rx, ry);
/*       */   }
/*       */   private static abstract class Unary { private Unary() {}
/*       */     float[] apply(float[] rx) {
/* 11678 */       int n1 = rx.length;
/* 11679 */       float[] ry = new float[n1];
/* 11680 */       apply(rx, ry);
/* 11681 */       return ry;
/*       */     }
/*       */     float[][] apply(float[][] rx) {
/* 11684 */       int n2 = rx.length;
/* 11685 */       float[][] ry = new float[n2][];
/* 11686 */       for (int i2 = 0; i2 < n2; i2++)
/* 11687 */         ry[i2] = apply(rx[i2]); 
/* 11688 */       return ry;
/*       */     }
/*       */     float[][][] apply(float[][][] rx) {
/* 11691 */       int n3 = rx.length;
/* 11692 */       float[][][] ry = new float[n3][][];
/* 11693 */       for (int i3 = 0; i3 < n3; i3++)
/* 11694 */         ry[i3] = apply(rx[i3]); 
/* 11695 */       return ry;
/*       */     }
/*       */     
/*       */     void apply(float[][] rx, float[][] ry) {
/* 11699 */       int n2 = rx.length;
/* 11700 */       for (int i2 = 0; i2 < n2; i2++)
/* 11701 */         apply(rx[i2], ry[i2]); 
/*       */     }
/*       */     void apply(float[][][] rx, float[][][] ry) {
/* 11704 */       int n3 = rx.length;
/* 11705 */       for (int i3 = 0; i3 < n3; i3++)
/* 11706 */         apply(rx[i3], ry[i3]); 
/*       */     }
/*       */     double[] apply(double[] rx) {
/* 11709 */       int n1 = rx.length;
/* 11710 */       double[] ry = new double[n1];
/* 11711 */       apply(rx, ry);
/* 11712 */       return ry;
/*       */     }
/*       */     double[][] apply(double[][] rx) {
/* 11715 */       int n2 = rx.length;
/* 11716 */       double[][] ry = new double[n2][];
/* 11717 */       for (int i2 = 0; i2 < n2; i2++)
/* 11718 */         ry[i2] = apply(rx[i2]); 
/* 11719 */       return ry;
/*       */     }
/*       */     double[][][] apply(double[][][] rx) {
/* 11722 */       int n3 = rx.length;
/* 11723 */       double[][][] ry = new double[n3][][];
/* 11724 */       for (int i3 = 0; i3 < n3; i3++)
/* 11725 */         ry[i3] = apply(rx[i3]); 
/* 11726 */       return ry;
/*       */     }
/*       */     
/*       */     void apply(double[][] rx, double[][] ry) {
/* 11730 */       int n2 = rx.length;
/* 11731 */       for (int i2 = 0; i2 < n2; i2++)
/* 11732 */         apply(rx[i2], ry[i2]); 
/*       */     }
/*       */     void apply(double[][][] rx, double[][][] ry) {
/* 11735 */       int n3 = rx.length;
/* 11736 */       for (int i3 = 0; i3 < n3; i3++)
/* 11737 */         apply(rx[i3], ry[i3]); 
/*       */     } abstract void apply(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2);
/*       */     abstract void apply(double[] param1ArrayOfdouble1, double[] param1ArrayOfdouble2); }
/* 11740 */   private static Unary _abs = new Unary() {
/*       */       void apply(float[] rx, float[] ry) {
/* 11742 */         int n1 = rx.length;
/* 11743 */         for (int i1 = 0; i1 < n1; i1++) {
/* 11744 */           float rxi = rx[i1];
/* 11745 */           ry[i1] = (rxi >= 0.0F) ? rxi : -rxi;
/*       */         } 
/*       */       }
/*       */       void apply(double[] rx, double[] ry) {
/* 11749 */         int n1 = rx.length;
/* 11750 */         for (int i1 = 0; i1 < n1; i1++) {
/* 11751 */           double rxi = rx[i1];
/* 11752 */           ry[i1] = (rxi >= 0.0D) ? rxi : -rxi;
/*       */         } 
/*       */       }
/*       */     };
/* 11756 */   private static Unary _neg = new Unary() {
/*       */       void apply(float[] rx, float[] ry) {
/* 11758 */         int n1 = rx.length;
/* 11759 */         for (int i1 = 0; i1 < n1; i1++)
/* 11760 */           ry[i1] = -rx[i1]; 
/*       */       }
/*       */       void apply(double[] rx, double[] ry) {
/* 11763 */         int n1 = rx.length;
/* 11764 */         for (int i1 = 0; i1 < n1; i1++)
/* 11765 */           ry[i1] = -rx[i1]; 
/*       */       }
/*       */     };
/* 11768 */   private static Unary _cos = new Unary() {
/*       */       void apply(float[] rx, float[] ry) {
/* 11770 */         int n1 = rx.length;
/* 11771 */         for (int i1 = 0; i1 < n1; i1++)
/* 11772 */           ry[i1] = (float)Math.cos(rx[i1]); 
/*       */       }
/*       */       void apply(double[] rx, double[] ry) {
/* 11775 */         int n1 = rx.length;
/* 11776 */         for (int i1 = 0; i1 < n1; i1++)
/* 11777 */           ry[i1] = Math.cos(rx[i1]); 
/*       */       }
/*       */     };
/* 11780 */   private static Unary _sin = new Unary() {
/*       */       void apply(float[] rx, float[] ry) {
/* 11782 */         int n1 = rx.length;
/* 11783 */         for (int i1 = 0; i1 < n1; i1++)
/* 11784 */           ry[i1] = (float)Math.sin(rx[i1]); 
/*       */       }
/*       */       void apply(double[] rx, double[] ry) {
/* 11787 */         int n1 = rx.length;
/* 11788 */         for (int i1 = 0; i1 < n1; i1++)
/* 11789 */           ry[i1] = Math.sin(rx[i1]); 
/*       */       }
/*       */     };
/* 11792 */   private static Unary _exp = new Unary() {
/*       */       void apply(float[] rx, float[] ry) {
/* 11794 */         int n1 = rx.length;
/* 11795 */         for (int i1 = 0; i1 < n1; i1++)
/* 11796 */           ry[i1] = (float)Math.exp(rx[i1]); 
/*       */       }
/*       */       void apply(double[] rx, double[] ry) {
/* 11799 */         int n1 = rx.length;
/* 11800 */         for (int i1 = 0; i1 < n1; i1++)
/* 11801 */           ry[i1] = Math.exp(rx[i1]); 
/*       */       }
/*       */     };
/* 11804 */   private static Unary _log = new Unary() {
/*       */       void apply(float[] rx, float[] ry) {
/* 11806 */         int n1 = rx.length;
/* 11807 */         for (int i1 = 0; i1 < n1; i1++)
/* 11808 */           ry[i1] = (float)Math.log(rx[i1]); 
/*       */       }
/*       */       void apply(double[] rx, double[] ry) {
/* 11811 */         int n1 = rx.length;
/* 11812 */         for (int i1 = 0; i1 < n1; i1++)
/* 11813 */           ry[i1] = Math.log(rx[i1]); 
/*       */       }
/*       */     };
/* 11816 */   private static Unary _log10 = new Unary() {
/*       */       void apply(float[] rx, float[] ry) {
/* 11818 */         int n1 = rx.length;
/* 11819 */         for (int i1 = 0; i1 < n1; i1++)
/* 11820 */           ry[i1] = (float)Math.log10(rx[i1]); 
/*       */       }
/*       */       void apply(double[] rx, double[] ry) {
/* 11823 */         int n1 = rx.length;
/* 11824 */         for (int i1 = 0; i1 < n1; i1++)
/* 11825 */           ry[i1] = Math.log10(rx[i1]); 
/*       */       }
/*       */     };
/* 11828 */   private static Unary _sqrt = new Unary() {
/*       */       void apply(float[] rx, float[] ry) {
/* 11830 */         int n1 = rx.length;
/* 11831 */         for (int i1 = 0; i1 < n1; i1++)
/* 11832 */           ry[i1] = (float)Math.sqrt(rx[i1]); 
/*       */       }
/*       */       void apply(double[] rx, double[] ry) {
/* 11835 */         int n1 = rx.length;
/* 11836 */         for (int i1 = 0; i1 < n1; i1++)
/* 11837 */           ry[i1] = Math.sqrt(rx[i1]); 
/*       */       }
/*       */     };
/* 11840 */   private static Unary _sgn = new Unary() {
/*       */       void apply(float[] rx, float[] ry) {
/* 11842 */         int n1 = rx.length;
/* 11843 */         for (int i1 = 0; i1 < n1; i1++)
/* 11844 */           ry[i1] = (rx[i1] > 0.0F) ? 1.0F : ((rx[i1] < 0.0F) ? -1.0F : 0.0F); 
/*       */       }
/*       */       void apply(double[] rx, double[] ry) {
/* 11847 */         int n1 = rx.length;
/* 11848 */         for (int i1 = 0; i1 < n1; i1++) {
/* 11849 */           ry[i1] = (rx[i1] > 0.0D) ? 1.0D : ((rx[i1] < 0.0D) ? -1.0D : 0.0D);
/*       */         }
/*       */       }
/*       */     };
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] clip(float rxmin, float rxmax, float[] rx) {
/* 11857 */     int n1 = rx.length;
/* 11858 */     float[] ry = new float[n1];
/* 11859 */     clip(rxmin, rxmax, rx, ry);
/* 11860 */     return ry;
/*       */   }
/*       */   public static float[][] clip(float rxmin, float rxmax, float[][] rx) {
/* 11863 */     int n2 = rx.length;
/* 11864 */     float[][] ry = new float[n2][];
/* 11865 */     for (int i2 = 0; i2 < n2; i2++)
/* 11866 */       ry[i2] = clip(rxmin, rxmax, rx[i2]); 
/* 11867 */     return ry;
/*       */   }
/*       */   public static float[][][] clip(float rxmin, float rxmax, float[][][] rx) {
/* 11870 */     int n3 = rx.length;
/* 11871 */     float[][][] ry = new float[n3][][];
/* 11872 */     for (int i3 = 0; i3 < n3; i3++)
/* 11873 */       ry[i3] = clip(rxmin, rxmax, rx[i3]); 
/* 11874 */     return ry;
/*       */   }
/*       */ 
/*       */   
/*       */   public static void clip(float rxmin, float rxmax, float[] rx, float[] ry) {
/* 11879 */     int n1 = rx.length;
/* 11880 */     for (int i1 = 0; i1 < n1; i1++) {
/* 11881 */       float rxi = rx[i1];
/* 11882 */       ry[i1] = (rxi < rxmin) ? rxmin : ((rxi > rxmax) ? rxmax : rxi);
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   public static void clip(float rxmin, float rxmax, float[][] rx, float[][] ry) {
/* 11888 */     int n2 = rx.length;
/* 11889 */     for (int i2 = 0; i2 < n2; i2++) {
/* 11890 */       clip(rxmin, rxmax, rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */   
/*       */   public static void clip(float rxmin, float rxmax, float[][][] rx, float[][][] ry) {
/* 11895 */     int n3 = rx.length;
/* 11896 */     for (int i3 = 0; i3 < n3; i3++)
/* 11897 */       clip(rxmin, rxmax, rx[i3], ry[i3]); 
/*       */   }
/*       */   public static double[] clip(double rxmin, double rxmax, double[] rx) {
/* 11900 */     int n1 = rx.length;
/* 11901 */     double[] ry = new double[n1];
/* 11902 */     clip(rxmin, rxmax, rx, ry);
/* 11903 */     return ry;
/*       */   }
/*       */   public static double[][] clip(double rxmin, double rxmax, double[][] rx) {
/* 11906 */     int n2 = rx.length;
/* 11907 */     double[][] ry = new double[n2][];
/* 11908 */     for (int i2 = 0; i2 < n2; i2++)
/* 11909 */       ry[i2] = clip(rxmin, rxmax, rx[i2]); 
/* 11910 */     return ry;
/*       */   }
/*       */   public static double[][][] clip(double rxmin, double rxmax, double[][][] rx) {
/* 11913 */     int n3 = rx.length;
/* 11914 */     double[][][] ry = new double[n3][][];
/* 11915 */     for (int i3 = 0; i3 < n3; i3++)
/* 11916 */       ry[i3] = clip(rxmin, rxmax, rx[i3]); 
/* 11917 */     return ry;
/*       */   }
/*       */ 
/*       */   
/*       */   public static void clip(double rxmin, double rxmax, double[] rx, double[] ry) {
/* 11922 */     int n1 = rx.length;
/* 11923 */     for (int i1 = 0; i1 < n1; i1++) {
/* 11924 */       double rxi = rx[i1];
/* 11925 */       ry[i1] = (rxi < rxmin) ? rxmin : ((rxi > rxmax) ? rxmax : rxi);
/*       */     } 
/*       */   }
/*       */ 
/*       */   
/*       */   public static void clip(double rxmin, double rxmax, double[][] rx, double[][] ry) {
/* 11931 */     int n2 = rx.length;
/* 11932 */     for (int i2 = 0; i2 < n2; i2++) {
/* 11933 */       clip(rxmin, rxmax, rx[i2], ry[i2]);
/*       */     }
/*       */   }
/*       */   
/*       */   public static void clip(double rxmin, double rxmax, double[][][] rx, double[][][] ry) {
/* 11938 */     int n3 = rx.length;
/* 11939 */     for (int i3 = 0; i3 < n3; i3++) {
/* 11940 */       clip(rxmin, rxmax, rx[i3], ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] pow(float[] rx, float ra) {
/* 11947 */     int n1 = rx.length;
/* 11948 */     float[] ry = new float[n1];
/* 11949 */     pow(rx, ra, ry);
/* 11950 */     return ry;
/*       */   }
/*       */   public static float[][] pow(float[][] rx, float ra) {
/* 11953 */     int n2 = rx.length;
/* 11954 */     float[][] ry = new float[n2][];
/* 11955 */     for (int i2 = 0; i2 < n2; i2++)
/* 11956 */       ry[i2] = pow(rx[i2], ra); 
/* 11957 */     return ry;
/*       */   }
/*       */   public static float[][][] pow(float[][][] rx, float ra) {
/* 11960 */     int n3 = rx.length;
/* 11961 */     float[][][] ry = new float[n3][][];
/* 11962 */     for (int i3 = 0; i3 < n3; i3++)
/* 11963 */       ry[i3] = pow(rx[i3], ra); 
/* 11964 */     return ry;
/*       */   }
/*       */   public static void pow(float[] rx, float ra, float[] ry) {
/* 11967 */     int n1 = rx.length;
/* 11968 */     for (int i1 = 0; i1 < n1; i1++)
/* 11969 */       ry[i1] = (float)Math.pow(rx[i1], ra); 
/*       */   }
/*       */   public static void pow(float[][] rx, float ra, float[][] ry) {
/* 11972 */     int n2 = rx.length;
/* 11973 */     for (int i2 = 0; i2 < n2; i2++)
/* 11974 */       pow(rx[i2], ra, ry[i2]); 
/*       */   }
/*       */   public static void pow(float[][][] rx, float ra, float[][][] ry) {
/* 11977 */     int n3 = rx.length;
/* 11978 */     for (int i3 = 0; i3 < n3; i3++)
/* 11979 */       pow(rx[i3], ra, ry[i3]); 
/*       */   }
/*       */   public static double[] pow(double[] rx, double ra) {
/* 11982 */     int n1 = rx.length;
/* 11983 */     double[] ry = new double[n1];
/* 11984 */     pow(rx, ra, ry);
/* 11985 */     return ry;
/*       */   }
/*       */   public static double[][] pow(double[][] rx, double ra) {
/* 11988 */     int n2 = rx.length;
/* 11989 */     double[][] ry = new double[n2][];
/* 11990 */     for (int i2 = 0; i2 < n2; i2++)
/* 11991 */       ry[i2] = pow(rx[i2], ra); 
/* 11992 */     return ry;
/*       */   }
/*       */   public static double[][][] pow(double[][][] rx, double ra) {
/* 11995 */     int n3 = rx.length;
/* 11996 */     double[][][] ry = new double[n3][][];
/* 11997 */     for (int i3 = 0; i3 < n3; i3++)
/* 11998 */       ry[i3] = pow(rx[i3], ra); 
/* 11999 */     return ry;
/*       */   }
/*       */   public static void pow(double[] rx, double ra, double[] ry) {
/* 12002 */     int n1 = rx.length;
/* 12003 */     for (int i1 = 0; i1 < n1; i1++)
/* 12004 */       ry[i1] = Math.pow(rx[i1], ra); 
/*       */   }
/*       */   public static void pow(double[][] rx, double ra, double[][] ry) {
/* 12007 */     int n2 = rx.length;
/* 12008 */     for (int i2 = 0; i2 < n2; i2++)
/* 12009 */       pow(rx[i2], ra, ry[i2]); 
/*       */   }
/*       */   public static void pow(double[][][] rx, double ra, double[][][] ry) {
/* 12012 */     int n3 = rx.length;
/* 12013 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12014 */       pow(rx[i3], ra, ry[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte sum(byte[] rx) {
/* 12021 */     int n1 = rx.length;
/* 12022 */     byte sum = 0;
/* 12023 */     for (int i1 = 0; i1 < n1; i1++)
/* 12024 */       sum = (byte)(sum + rx[i1]); 
/* 12025 */     return sum;
/*       */   }
/*       */   public static byte sum(byte[][] rx) {
/* 12028 */     int n2 = rx.length;
/* 12029 */     byte sum = 0;
/* 12030 */     for (int i2 = 0; i2 < n2; i2++)
/* 12031 */       sum = (byte)(sum + sum(rx[i2])); 
/* 12032 */     return sum;
/*       */   }
/*       */   public static byte sum(byte[][][] rx) {
/* 12035 */     int n3 = rx.length;
/* 12036 */     byte sum = 0;
/* 12037 */     for (int i3 = 0; i3 < n3; i3++)
/* 12038 */       sum = (byte)(sum + sum(rx[i3])); 
/* 12039 */     return sum;
/*       */   }
/*       */   public static short sum(short[] rx) {
/* 12042 */     int n1 = rx.length;
/* 12043 */     short sum = 0;
/* 12044 */     for (int i1 = 0; i1 < n1; i1++)
/* 12045 */       sum = (short)(sum + rx[i1]); 
/* 12046 */     return sum;
/*       */   }
/*       */   public static short sum(short[][] rx) {
/* 12049 */     int n2 = rx.length;
/* 12050 */     short sum = 0;
/* 12051 */     for (int i2 = 0; i2 < n2; i2++)
/* 12052 */       sum = (short)(sum + sum(rx[i2])); 
/* 12053 */     return sum;
/*       */   }
/*       */   public static short sum(short[][][] rx) {
/* 12056 */     int n3 = rx.length;
/* 12057 */     short sum = 0;
/* 12058 */     for (int i3 = 0; i3 < n3; i3++)
/* 12059 */       sum = (short)(sum + sum(rx[i3])); 
/* 12060 */     return sum;
/*       */   }
/*       */   public static int sum(int[] rx) {
/* 12063 */     int n1 = rx.length;
/* 12064 */     int sum = 0;
/* 12065 */     for (int i1 = 0; i1 < n1; i1++)
/* 12066 */       sum += rx[i1]; 
/* 12067 */     return sum;
/*       */   }
/*       */   public static int sum(int[][] rx) {
/* 12070 */     int n2 = rx.length;
/* 12071 */     int sum = 0;
/* 12072 */     for (int i2 = 0; i2 < n2; i2++)
/* 12073 */       sum += sum(rx[i2]); 
/* 12074 */     return sum;
/*       */   }
/*       */   public static int sum(int[][][] rx) {
/* 12077 */     int n3 = rx.length;
/* 12078 */     int sum = 0;
/* 12079 */     for (int i3 = 0; i3 < n3; i3++)
/* 12080 */       sum += sum(rx[i3]); 
/* 12081 */     return sum;
/*       */   }
/*       */   public static long sum(long[] rx) {
/* 12084 */     int n1 = rx.length;
/* 12085 */     long sum = 0L;
/* 12086 */     for (int i1 = 0; i1 < n1; i1++)
/* 12087 */       sum += rx[i1]; 
/* 12088 */     return sum;
/*       */   }
/*       */   public static long sum(long[][] rx) {
/* 12091 */     int n2 = rx.length;
/* 12092 */     long sum = 0L;
/* 12093 */     for (int i2 = 0; i2 < n2; i2++)
/* 12094 */       sum += sum(rx[i2]); 
/* 12095 */     return sum;
/*       */   }
/*       */   public static long sum(long[][][] rx) {
/* 12098 */     int n3 = rx.length;
/* 12099 */     long sum = 0L;
/* 12100 */     for (int i3 = 0; i3 < n3; i3++)
/* 12101 */       sum += sum(rx[i3]); 
/* 12102 */     return sum;
/*       */   }
/*       */   public static float sum(float[] rx) {
/* 12105 */     int n1 = rx.length;
/* 12106 */     float sum = 0.0F;
/* 12107 */     for (int i1 = 0; i1 < n1; i1++)
/* 12108 */       sum += rx[i1]; 
/* 12109 */     return sum;
/*       */   }
/*       */   public static float sum(float[][] rx) {
/* 12112 */     int n2 = rx.length;
/* 12113 */     float sum = 0.0F;
/* 12114 */     for (int i2 = 0; i2 < n2; i2++)
/* 12115 */       sum += sum(rx[i2]); 
/* 12116 */     return sum;
/*       */   }
/*       */   public static float sum(float[][][] rx) {
/* 12119 */     int n3 = rx.length;
/* 12120 */     float sum = 0.0F;
/* 12121 */     for (int i3 = 0; i3 < n3; i3++)
/* 12122 */       sum += sum(rx[i3]); 
/* 12123 */     return sum;
/*       */   }
/*       */   public static double sum(double[] rx) {
/* 12126 */     int n1 = rx.length;
/* 12127 */     double sum = 0.0D;
/* 12128 */     for (int i1 = 0; i1 < n1; i1++)
/* 12129 */       sum += rx[i1]; 
/* 12130 */     return sum;
/*       */   }
/*       */   public static double sum(double[][] rx) {
/* 12133 */     int n2 = rx.length;
/* 12134 */     double sum = 0.0D;
/* 12135 */     for (int i2 = 0; i2 < n2; i2++)
/* 12136 */       sum += sum(rx[i2]); 
/* 12137 */     return sum;
/*       */   }
/*       */   public static double sum(double[][][] rx) {
/* 12140 */     int n3 = rx.length;
/* 12141 */     double sum = 0.0D;
/* 12142 */     for (int i3 = 0; i3 < n3; i3++)
/* 12143 */       sum += sum(rx[i3]); 
/* 12144 */     return sum;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static byte max(byte[] rx) {
/* 12151 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static byte max(byte[][] rx) {
/* 12154 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static byte max(byte[][][] rx) {
/* 12157 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static byte max(byte[] rx, int[] index) {
/* 12160 */     int i1max = 0;
/* 12161 */     byte max = rx[0];
/* 12162 */     int n1 = rx.length;
/* 12163 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12164 */       if (rx[i1] > max) {
/* 12165 */         max = rx[i1];
/* 12166 */         i1max = i1;
/*       */       } 
/*       */     } 
/* 12169 */     if (index != null)
/* 12170 */       index[0] = i1max; 
/* 12171 */     return max;
/*       */   }
/*       */   public static byte max(byte[][] rx, int[] index) {
/* 12174 */     int i1max = 0;
/* 12175 */     int i2max = 0;
/* 12176 */     byte max = rx[0][0];
/* 12177 */     int n2 = rx.length;
/* 12178 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12179 */       byte[] rxi2 = rx[i2];
/* 12180 */       int n1 = rxi2.length;
/* 12181 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12182 */         if (rxi2[i1] > max) {
/* 12183 */           max = rxi2[i1];
/* 12184 */           i2max = i2;
/* 12185 */           i1max = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12189 */     if (index != null) {
/* 12190 */       index[0] = i1max;
/* 12191 */       index[1] = i2max;
/*       */     } 
/* 12193 */     return max;
/*       */   }
/*       */   public static byte max(byte[][][] rx, int[] index) {
/* 12196 */     int i1max = 0;
/* 12197 */     int i2max = 0;
/* 12198 */     int i3max = 0;
/* 12199 */     byte max = rx[0][0][0];
/* 12200 */     int n3 = rx.length;
/* 12201 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12202 */       byte[][] rxi3 = rx[i3];
/* 12203 */       int n2 = rxi3.length;
/* 12204 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12205 */         byte[] rxi3i2 = rxi3[i2];
/* 12206 */         int n1 = rxi3i2.length;
/* 12207 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12208 */           if (rxi3i2[i1] > max) {
/* 12209 */             max = rxi3i2[i1];
/* 12210 */             i1max = i1;
/* 12211 */             i2max = i2;
/* 12212 */             i3max = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12217 */     if (index != null) {
/* 12218 */       index[0] = i1max;
/* 12219 */       index[1] = i2max;
/* 12220 */       index[2] = i3max;
/*       */     } 
/* 12222 */     return max;
/*       */   }
/*       */   public static byte min(byte[] rx) {
/* 12225 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static byte min(byte[][] rx) {
/* 12228 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static byte min(byte[][][] rx) {
/* 12231 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static byte min(byte[] rx, int[] index) {
/* 12234 */     int i1min = 0;
/* 12235 */     byte min = rx[0];
/* 12236 */     int n1 = rx.length;
/* 12237 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12238 */       if (rx[i1] < min) {
/* 12239 */         min = rx[i1];
/* 12240 */         i1min = i1;
/*       */       } 
/*       */     } 
/* 12243 */     if (index != null)
/* 12244 */       index[0] = i1min; 
/* 12245 */     return min;
/*       */   }
/*       */   public static byte min(byte[][] rx, int[] index) {
/* 12248 */     int i1min = 0;
/* 12249 */     int i2min = 0;
/* 12250 */     byte min = rx[0][0];
/* 12251 */     int n2 = rx.length;
/* 12252 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12253 */       byte[] rxi2 = rx[i2];
/* 12254 */       int n1 = rxi2.length;
/* 12255 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12256 */         if (rxi2[i1] < min) {
/* 12257 */           min = rxi2[i1];
/* 12258 */           i2min = i2;
/* 12259 */           i1min = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12263 */     if (index != null) {
/* 12264 */       index[0] = i1min;
/* 12265 */       index[1] = i2min;
/*       */     } 
/* 12267 */     return min;
/*       */   }
/*       */   public static byte min(byte[][][] rx, int[] index) {
/* 12270 */     int i1min = 0;
/* 12271 */     int i2min = 0;
/* 12272 */     int i3min = 0;
/* 12273 */     byte min = rx[0][0][0];
/* 12274 */     int n3 = rx.length;
/* 12275 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12276 */       byte[][] rxi3 = rx[i3];
/* 12277 */       int n2 = rxi3.length;
/* 12278 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12279 */         byte[] rxi3i2 = rxi3[i2];
/* 12280 */         int n1 = rxi3i2.length;
/* 12281 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12282 */           if (rxi3i2[i1] < min) {
/* 12283 */             min = rxi3i2[i1];
/* 12284 */             i1min = i1;
/* 12285 */             i2min = i2;
/* 12286 */             i3min = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12291 */     if (index != null) {
/* 12292 */       index[0] = i1min;
/* 12293 */       index[1] = i2min;
/* 12294 */       index[2] = i3min;
/*       */     } 
/* 12296 */     return min;
/*       */   }
/*       */   public static short max(short[] rx) {
/* 12299 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static short max(short[][] rx) {
/* 12302 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static short max(short[][][] rx) {
/* 12305 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static short max(short[] rx, int[] index) {
/* 12308 */     int i1max = 0;
/* 12309 */     short max = rx[0];
/* 12310 */     int n1 = rx.length;
/* 12311 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12312 */       if (rx[i1] > max) {
/* 12313 */         max = rx[i1];
/* 12314 */         i1max = i1;
/*       */       } 
/*       */     } 
/* 12317 */     if (index != null)
/* 12318 */       index[0] = i1max; 
/* 12319 */     return max;
/*       */   }
/*       */   public static short max(short[][] rx, int[] index) {
/* 12322 */     int i1max = 0;
/* 12323 */     int i2max = 0;
/* 12324 */     short max = rx[0][0];
/* 12325 */     int n2 = rx.length;
/* 12326 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12327 */       short[] rxi2 = rx[i2];
/* 12328 */       int n1 = rxi2.length;
/* 12329 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12330 */         if (rxi2[i1] > max) {
/* 12331 */           max = rxi2[i1];
/* 12332 */           i2max = i2;
/* 12333 */           i1max = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12337 */     if (index != null) {
/* 12338 */       index[0] = i1max;
/* 12339 */       index[1] = i2max;
/*       */     } 
/* 12341 */     return max;
/*       */   }
/*       */   public static short max(short[][][] rx, int[] index) {
/* 12344 */     int i1max = 0;
/* 12345 */     int i2max = 0;
/* 12346 */     int i3max = 0;
/* 12347 */     short max = rx[0][0][0];
/* 12348 */     int n3 = rx.length;
/* 12349 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12350 */       short[][] rxi3 = rx[i3];
/* 12351 */       int n2 = rxi3.length;
/* 12352 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12353 */         short[] rxi3i2 = rxi3[i2];
/* 12354 */         int n1 = rxi3i2.length;
/* 12355 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12356 */           if (rxi3i2[i1] > max) {
/* 12357 */             max = rxi3i2[i1];
/* 12358 */             i1max = i1;
/* 12359 */             i2max = i2;
/* 12360 */             i3max = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12365 */     if (index != null) {
/* 12366 */       index[0] = i1max;
/* 12367 */       index[1] = i2max;
/* 12368 */       index[2] = i3max;
/*       */     } 
/* 12370 */     return max;
/*       */   }
/*       */   public static short min(short[] rx) {
/* 12373 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static short min(short[][] rx) {
/* 12376 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static short min(short[][][] rx) {
/* 12379 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static short min(short[] rx, int[] index) {
/* 12382 */     int i1min = 0;
/* 12383 */     short min = rx[0];
/* 12384 */     int n1 = rx.length;
/* 12385 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12386 */       if (rx[i1] < min) {
/* 12387 */         min = rx[i1];
/* 12388 */         i1min = i1;
/*       */       } 
/*       */     } 
/* 12391 */     if (index != null)
/* 12392 */       index[0] = i1min; 
/* 12393 */     return min;
/*       */   }
/*       */   public static short min(short[][] rx, int[] index) {
/* 12396 */     int i1min = 0;
/* 12397 */     int i2min = 0;
/* 12398 */     short min = rx[0][0];
/* 12399 */     int n2 = rx.length;
/* 12400 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12401 */       short[] rxi2 = rx[i2];
/* 12402 */       int n1 = rxi2.length;
/* 12403 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12404 */         if (rxi2[i1] < min) {
/* 12405 */           min = rxi2[i1];
/* 12406 */           i2min = i2;
/* 12407 */           i1min = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12411 */     if (index != null) {
/* 12412 */       index[0] = i1min;
/* 12413 */       index[1] = i2min;
/*       */     } 
/* 12415 */     return min;
/*       */   }
/*       */   public static short min(short[][][] rx, int[] index) {
/* 12418 */     int i1min = 0;
/* 12419 */     int i2min = 0;
/* 12420 */     int i3min = 0;
/* 12421 */     short min = rx[0][0][0];
/* 12422 */     int n3 = rx.length;
/* 12423 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12424 */       short[][] rxi3 = rx[i3];
/* 12425 */       int n2 = rxi3.length;
/* 12426 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12427 */         short[] rxi3i2 = rxi3[i2];
/* 12428 */         int n1 = rxi3i2.length;
/* 12429 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12430 */           if (rxi3i2[i1] < min) {
/* 12431 */             min = rxi3i2[i1];
/* 12432 */             i1min = i1;
/* 12433 */             i2min = i2;
/* 12434 */             i3min = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12439 */     if (index != null) {
/* 12440 */       index[0] = i1min;
/* 12441 */       index[1] = i2min;
/* 12442 */       index[2] = i3min;
/*       */     } 
/* 12444 */     return min;
/*       */   }
/*       */   public static int max(int[] rx) {
/* 12447 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static int max(int[][] rx) {
/* 12450 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static int max(int[][][] rx) {
/* 12453 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static int max(int[] rx, int[] index) {
/* 12456 */     int i1max = 0;
/* 12457 */     int max = rx[0];
/* 12458 */     int n1 = rx.length;
/* 12459 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12460 */       if (rx[i1] > max) {
/* 12461 */         max = rx[i1];
/* 12462 */         i1max = i1;
/*       */       } 
/*       */     } 
/* 12465 */     if (index != null)
/* 12466 */       index[0] = i1max; 
/* 12467 */     return max;
/*       */   }
/*       */   public static int max(int[][] rx, int[] index) {
/* 12470 */     int i1max = 0;
/* 12471 */     int i2max = 0;
/* 12472 */     int max = rx[0][0];
/* 12473 */     int n2 = rx.length;
/* 12474 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12475 */       int[] rxi2 = rx[i2];
/* 12476 */       int n1 = rxi2.length;
/* 12477 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12478 */         if (rxi2[i1] > max) {
/* 12479 */           max = rxi2[i1];
/* 12480 */           i2max = i2;
/* 12481 */           i1max = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12485 */     if (index != null) {
/* 12486 */       index[0] = i1max;
/* 12487 */       index[1] = i2max;
/*       */     } 
/* 12489 */     return max;
/*       */   }
/*       */   public static int max(int[][][] rx, int[] index) {
/* 12492 */     int i1max = 0;
/* 12493 */     int i2max = 0;
/* 12494 */     int i3max = 0;
/* 12495 */     int max = rx[0][0][0];
/* 12496 */     int n3 = rx.length;
/* 12497 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12498 */       int[][] rxi3 = rx[i3];
/* 12499 */       int n2 = rxi3.length;
/* 12500 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12501 */         int[] rxi3i2 = rxi3[i2];
/* 12502 */         int n1 = rxi3i2.length;
/* 12503 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12504 */           if (rxi3i2[i1] > max) {
/* 12505 */             max = rxi3i2[i1];
/* 12506 */             i1max = i1;
/* 12507 */             i2max = i2;
/* 12508 */             i3max = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12513 */     if (index != null) {
/* 12514 */       index[0] = i1max;
/* 12515 */       index[1] = i2max;
/* 12516 */       index[2] = i3max;
/*       */     } 
/* 12518 */     return max;
/*       */   }
/*       */   public static int min(int[] rx) {
/* 12521 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static int min(int[][] rx) {
/* 12524 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static int min(int[][][] rx) {
/* 12527 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static int min(int[] rx, int[] index) {
/* 12530 */     int i1min = 0;
/* 12531 */     int min = rx[0];
/* 12532 */     int n1 = rx.length;
/* 12533 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12534 */       if (rx[i1] < min) {
/* 12535 */         min = rx[i1];
/* 12536 */         i1min = i1;
/*       */       } 
/*       */     } 
/* 12539 */     if (index != null)
/* 12540 */       index[0] = i1min; 
/* 12541 */     return min;
/*       */   }
/*       */   public static int min(int[][] rx, int[] index) {
/* 12544 */     int i1min = 0;
/* 12545 */     int i2min = 0;
/* 12546 */     int min = rx[0][0];
/* 12547 */     int n2 = rx.length;
/* 12548 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12549 */       int[] rxi2 = rx[i2];
/* 12550 */       int n1 = rxi2.length;
/* 12551 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12552 */         if (rxi2[i1] < min) {
/* 12553 */           min = rxi2[i1];
/* 12554 */           i2min = i2;
/* 12555 */           i1min = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12559 */     if (index != null) {
/* 12560 */       index[0] = i1min;
/* 12561 */       index[1] = i2min;
/*       */     } 
/* 12563 */     return min;
/*       */   }
/*       */   public static int min(int[][][] rx, int[] index) {
/* 12566 */     int i1min = 0;
/* 12567 */     int i2min = 0;
/* 12568 */     int i3min = 0;
/* 12569 */     int min = rx[0][0][0];
/* 12570 */     int n3 = rx.length;
/* 12571 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12572 */       int[][] rxi3 = rx[i3];
/* 12573 */       int n2 = rxi3.length;
/* 12574 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12575 */         int[] rxi3i2 = rxi3[i2];
/* 12576 */         int n1 = rxi3i2.length;
/* 12577 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12578 */           if (rxi3i2[i1] < min) {
/* 12579 */             min = rxi3i2[i1];
/* 12580 */             i1min = i1;
/* 12581 */             i2min = i2;
/* 12582 */             i3min = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12587 */     if (index != null) {
/* 12588 */       index[0] = i1min;
/* 12589 */       index[1] = i2min;
/* 12590 */       index[2] = i3min;
/*       */     } 
/* 12592 */     return min;
/*       */   }
/*       */   public static long max(long[] rx) {
/* 12595 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static long max(long[][] rx) {
/* 12598 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static long max(long[][][] rx) {
/* 12601 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static long max(long[] rx, int[] index) {
/* 12604 */     int i1max = 0;
/* 12605 */     long max = rx[0];
/* 12606 */     int n1 = rx.length;
/* 12607 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12608 */       if (rx[i1] > max) {
/* 12609 */         max = rx[i1];
/* 12610 */         i1max = i1;
/*       */       } 
/*       */     } 
/* 12613 */     if (index != null)
/* 12614 */       index[0] = i1max; 
/* 12615 */     return max;
/*       */   }
/*       */   public static long max(long[][] rx, int[] index) {
/* 12618 */     int i1max = 0;
/* 12619 */     int i2max = 0;
/* 12620 */     long max = rx[0][0];
/* 12621 */     int n2 = rx.length;
/* 12622 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12623 */       long[] rxi2 = rx[i2];
/* 12624 */       int n1 = rxi2.length;
/* 12625 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12626 */         if (rxi2[i1] > max) {
/* 12627 */           max = rxi2[i1];
/* 12628 */           i2max = i2;
/* 12629 */           i1max = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12633 */     if (index != null) {
/* 12634 */       index[0] = i1max;
/* 12635 */       index[1] = i2max;
/*       */     } 
/* 12637 */     return max;
/*       */   }
/*       */   public static long max(long[][][] rx, int[] index) {
/* 12640 */     int i1max = 0;
/* 12641 */     int i2max = 0;
/* 12642 */     int i3max = 0;
/* 12643 */     long max = rx[0][0][0];
/* 12644 */     int n3 = rx.length;
/* 12645 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12646 */       long[][] rxi3 = rx[i3];
/* 12647 */       int n2 = rxi3.length;
/* 12648 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12649 */         long[] rxi3i2 = rxi3[i2];
/* 12650 */         int n1 = rxi3i2.length;
/* 12651 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12652 */           if (rxi3i2[i1] > max) {
/* 12653 */             max = rxi3i2[i1];
/* 12654 */             i1max = i1;
/* 12655 */             i2max = i2;
/* 12656 */             i3max = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12661 */     if (index != null) {
/* 12662 */       index[0] = i1max;
/* 12663 */       index[1] = i2max;
/* 12664 */       index[2] = i3max;
/*       */     } 
/* 12666 */     return max;
/*       */   }
/*       */   public static long min(long[] rx) {
/* 12669 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static long min(long[][] rx) {
/* 12672 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static long min(long[][][] rx) {
/* 12675 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static long min(long[] rx, int[] index) {
/* 12678 */     int i1min = 0;
/* 12679 */     long min = rx[0];
/* 12680 */     int n1 = rx.length;
/* 12681 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12682 */       if (rx[i1] < min) {
/* 12683 */         min = rx[i1];
/* 12684 */         i1min = i1;
/*       */       } 
/*       */     } 
/* 12687 */     if (index != null)
/* 12688 */       index[0] = i1min; 
/* 12689 */     return min;
/*       */   }
/*       */   public static long min(long[][] rx, int[] index) {
/* 12692 */     int i1min = 0;
/* 12693 */     int i2min = 0;
/* 12694 */     long min = rx[0][0];
/* 12695 */     int n2 = rx.length;
/* 12696 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12697 */       long[] rxi2 = rx[i2];
/* 12698 */       int n1 = rxi2.length;
/* 12699 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12700 */         if (rxi2[i1] < min) {
/* 12701 */           min = rxi2[i1];
/* 12702 */           i2min = i2;
/* 12703 */           i1min = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12707 */     if (index != null) {
/* 12708 */       index[0] = i1min;
/* 12709 */       index[1] = i2min;
/*       */     } 
/* 12711 */     return min;
/*       */   }
/*       */   public static long min(long[][][] rx, int[] index) {
/* 12714 */     int i1min = 0;
/* 12715 */     int i2min = 0;
/* 12716 */     int i3min = 0;
/* 12717 */     long min = rx[0][0][0];
/* 12718 */     int n3 = rx.length;
/* 12719 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12720 */       long[][] rxi3 = rx[i3];
/* 12721 */       int n2 = rxi3.length;
/* 12722 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12723 */         long[] rxi3i2 = rxi3[i2];
/* 12724 */         int n1 = rxi3i2.length;
/* 12725 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12726 */           if (rxi3i2[i1] < min) {
/* 12727 */             min = rxi3i2[i1];
/* 12728 */             i1min = i1;
/* 12729 */             i2min = i2;
/* 12730 */             i3min = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12735 */     if (index != null) {
/* 12736 */       index[0] = i1min;
/* 12737 */       index[1] = i2min;
/* 12738 */       index[2] = i3min;
/*       */     } 
/* 12740 */     return min;
/*       */   }
/*       */   public static float max(float[] rx) {
/* 12743 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static float max(float[][] rx) {
/* 12746 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static float max(float[][][] rx) {
/* 12749 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static float max(float[] rx, int[] index) {
/* 12752 */     int i1max = 0;
/* 12753 */     float max = rx[0];
/* 12754 */     int n1 = rx.length;
/* 12755 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12756 */       if (rx[i1] > max) {
/* 12757 */         max = rx[i1];
/* 12758 */         i1max = i1;
/*       */       } 
/*       */     } 
/* 12761 */     if (index != null)
/* 12762 */       index[0] = i1max; 
/* 12763 */     return max;
/*       */   }
/*       */   public static float max(float[][] rx, int[] index) {
/* 12766 */     int i1max = 0;
/* 12767 */     int i2max = 0;
/* 12768 */     float max = rx[0][0];
/* 12769 */     int n2 = rx.length;
/* 12770 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12771 */       float[] rxi2 = rx[i2];
/* 12772 */       int n1 = rxi2.length;
/* 12773 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12774 */         if (rxi2[i1] > max) {
/* 12775 */           max = rxi2[i1];
/* 12776 */           i2max = i2;
/* 12777 */           i1max = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12781 */     if (index != null) {
/* 12782 */       index[0] = i1max;
/* 12783 */       index[1] = i2max;
/*       */     } 
/* 12785 */     return max;
/*       */   }
/*       */   public static float max(float[][][] rx, int[] index) {
/* 12788 */     int i1max = 0;
/* 12789 */     int i2max = 0;
/* 12790 */     int i3max = 0;
/* 12791 */     float max = rx[0][0][0];
/* 12792 */     int n3 = rx.length;
/* 12793 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12794 */       float[][] rxi3 = rx[i3];
/* 12795 */       int n2 = rxi3.length;
/* 12796 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12797 */         float[] rxi3i2 = rxi3[i2];
/* 12798 */         int n1 = rxi3i2.length;
/* 12799 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12800 */           if (rxi3i2[i1] > max) {
/* 12801 */             max = rxi3i2[i1];
/* 12802 */             i1max = i1;
/* 12803 */             i2max = i2;
/* 12804 */             i3max = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12809 */     if (index != null) {
/* 12810 */       index[0] = i1max;
/* 12811 */       index[1] = i2max;
/* 12812 */       index[2] = i3max;
/*       */     } 
/* 12814 */     return max;
/*       */   }
/*       */   public static float min(float[] rx) {
/* 12817 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static float min(float[][] rx) {
/* 12820 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static float min(float[][][] rx) {
/* 12823 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static float min(float[] rx, int[] index) {
/* 12826 */     int i1min = 0;
/* 12827 */     float min = rx[0];
/* 12828 */     int n1 = rx.length;
/* 12829 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12830 */       if (rx[i1] < min) {
/* 12831 */         min = rx[i1];
/* 12832 */         i1min = i1;
/*       */       } 
/*       */     } 
/* 12835 */     if (index != null)
/* 12836 */       index[0] = i1min; 
/* 12837 */     return min;
/*       */   }
/*       */   public static float min(float[][] rx, int[] index) {
/* 12840 */     int i1min = 0;
/* 12841 */     int i2min = 0;
/* 12842 */     float min = rx[0][0];
/* 12843 */     int n2 = rx.length;
/* 12844 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12845 */       float[] rxi2 = rx[i2];
/* 12846 */       int n1 = rxi2.length;
/* 12847 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12848 */         if (rxi2[i1] < min) {
/* 12849 */           min = rxi2[i1];
/* 12850 */           i2min = i2;
/* 12851 */           i1min = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12855 */     if (index != null) {
/* 12856 */       index[0] = i1min;
/* 12857 */       index[1] = i2min;
/*       */     } 
/* 12859 */     return min;
/*       */   }
/*       */   public static float min(float[][][] rx, int[] index) {
/* 12862 */     int i1min = 0;
/* 12863 */     int i2min = 0;
/* 12864 */     int i3min = 0;
/* 12865 */     float min = rx[0][0][0];
/* 12866 */     int n3 = rx.length;
/* 12867 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12868 */       float[][] rxi3 = rx[i3];
/* 12869 */       int n2 = rxi3.length;
/* 12870 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12871 */         float[] rxi3i2 = rxi3[i2];
/* 12872 */         int n1 = rxi3i2.length;
/* 12873 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12874 */           if (rxi3i2[i1] < min) {
/* 12875 */             min = rxi3i2[i1];
/* 12876 */             i1min = i1;
/* 12877 */             i2min = i2;
/* 12878 */             i3min = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12883 */     if (index != null) {
/* 12884 */       index[0] = i1min;
/* 12885 */       index[1] = i2min;
/* 12886 */       index[2] = i3min;
/*       */     } 
/* 12888 */     return min;
/*       */   }
/*       */   public static double max(double[] rx) {
/* 12891 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static double max(double[][] rx) {
/* 12894 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static double max(double[][][] rx) {
/* 12897 */     return max(rx, (int[])null);
/*       */   }
/*       */   public static double max(double[] rx, int[] index) {
/* 12900 */     int i1max = 0;
/* 12901 */     double max = rx[0];
/* 12902 */     int n1 = rx.length;
/* 12903 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12904 */       if (rx[i1] > max) {
/* 12905 */         max = rx[i1];
/* 12906 */         i1max = i1;
/*       */       } 
/*       */     } 
/* 12909 */     if (index != null)
/* 12910 */       index[0] = i1max; 
/* 12911 */     return max;
/*       */   }
/*       */   public static double max(double[][] rx, int[] index) {
/* 12914 */     int i1max = 0;
/* 12915 */     int i2max = 0;
/* 12916 */     double max = rx[0][0];
/* 12917 */     int n2 = rx.length;
/* 12918 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12919 */       double[] rxi2 = rx[i2];
/* 12920 */       int n1 = rxi2.length;
/* 12921 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12922 */         if (rxi2[i1] > max) {
/* 12923 */           max = rxi2[i1];
/* 12924 */           i2max = i2;
/* 12925 */           i1max = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 12929 */     if (index != null) {
/* 12930 */       index[0] = i1max;
/* 12931 */       index[1] = i2max;
/*       */     } 
/* 12933 */     return max;
/*       */   }
/*       */   public static double max(double[][][] rx, int[] index) {
/* 12936 */     int i1max = 0;
/* 12937 */     int i2max = 0;
/* 12938 */     int i3max = 0;
/* 12939 */     double max = rx[0][0][0];
/* 12940 */     int n3 = rx.length;
/* 12941 */     for (int i3 = 0; i3 < n3; i3++) {
/* 12942 */       double[][] rxi3 = rx[i3];
/* 12943 */       int n2 = rxi3.length;
/* 12944 */       for (int i2 = 0; i2 < n2; i2++) {
/* 12945 */         double[] rxi3i2 = rxi3[i2];
/* 12946 */         int n1 = rxi3i2.length;
/* 12947 */         for (int i1 = 0; i1 < n1; i1++) {
/* 12948 */           if (rxi3i2[i1] > max) {
/* 12949 */             max = rxi3i2[i1];
/* 12950 */             i1max = i1;
/* 12951 */             i2max = i2;
/* 12952 */             i3max = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 12957 */     if (index != null) {
/* 12958 */       index[0] = i1max;
/* 12959 */       index[1] = i2max;
/* 12960 */       index[2] = i3max;
/*       */     } 
/* 12962 */     return max;
/*       */   }
/*       */   public static double min(double[] rx) {
/* 12965 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static double min(double[][] rx) {
/* 12968 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static double min(double[][][] rx) {
/* 12971 */     return min(rx, (int[])null);
/*       */   }
/*       */   public static double min(double[] rx, int[] index) {
/* 12974 */     int i1min = 0;
/* 12975 */     double min = rx[0];
/* 12976 */     int n1 = rx.length;
/* 12977 */     for (int i1 = 1; i1 < n1; i1++) {
/* 12978 */       if (rx[i1] < min) {
/* 12979 */         min = rx[i1];
/* 12980 */         i1min = i1;
/*       */       } 
/*       */     } 
/* 12983 */     if (index != null)
/* 12984 */       index[0] = i1min; 
/* 12985 */     return min;
/*       */   }
/*       */   public static double min(double[][] rx, int[] index) {
/* 12988 */     int i1min = 0;
/* 12989 */     int i2min = 0;
/* 12990 */     double min = rx[0][0];
/* 12991 */     int n2 = rx.length;
/* 12992 */     for (int i2 = 0; i2 < n2; i2++) {
/* 12993 */       double[] rxi2 = rx[i2];
/* 12994 */       int n1 = rxi2.length;
/* 12995 */       for (int i1 = 0; i1 < n1; i1++) {
/* 12996 */         if (rxi2[i1] < min) {
/* 12997 */           min = rxi2[i1];
/* 12998 */           i2min = i2;
/* 12999 */           i1min = i1;
/*       */         } 
/*       */       } 
/*       */     } 
/* 13003 */     if (index != null) {
/* 13004 */       index[0] = i1min;
/* 13005 */       index[1] = i2min;
/*       */     } 
/* 13007 */     return min;
/*       */   }
/*       */   public static double min(double[][][] rx, int[] index) {
/* 13010 */     int i1min = 0;
/* 13011 */     int i2min = 0;
/* 13012 */     int i3min = 0;
/* 13013 */     double min = rx[0][0][0];
/* 13014 */     int n3 = rx.length;
/* 13015 */     for (int i3 = 0; i3 < n3; i3++) {
/* 13016 */       double[][] rxi3 = rx[i3];
/* 13017 */       int n2 = rxi3.length;
/* 13018 */       for (int i2 = 0; i2 < n2; i2++) {
/* 13019 */         double[] rxi3i2 = rxi3[i2];
/* 13020 */         int n1 = rxi3i2.length;
/* 13021 */         for (int i1 = 0; i1 < n1; i1++) {
/* 13022 */           if (rxi3i2[i1] < min) {
/* 13023 */             min = rxi3i2[i1];
/* 13024 */             i1min = i1;
/* 13025 */             i2min = i2;
/* 13026 */             i3min = i3;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/* 13031 */     if (index != null) {
/* 13032 */       index[0] = i1min;
/* 13033 */       index[1] = i2min;
/* 13034 */       index[2] = i3min;
/*       */     } 
/* 13036 */     return min;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] cadd(float[] cx, float[] cy) {
/* 13043 */     return _cadd.apply(cx, cy);
/*       */   }
/*       */   public static float[] cadd(Cfloat ca, float[] cy) {
/* 13046 */     return _cadd.apply(ca, cy);
/*       */   }
/*       */   public static float[] cadd(float[] cx, Cfloat cb) {
/* 13049 */     return _cadd.apply(cx, cb);
/*       */   }
/*       */   public static float[][] cadd(float[][] cx, float[][] cy) {
/* 13052 */     return _cadd.apply(cx, cy);
/*       */   }
/*       */   public static float[][] cadd(Cfloat ca, float[][] cy) {
/* 13055 */     return _cadd.apply(ca, cy);
/*       */   }
/*       */   public static float[][] cadd(float[][] cx, Cfloat cb) {
/* 13058 */     return _cadd.apply(cx, cb);
/*       */   }
/*       */   public static float[][][] cadd(float[][][] cx, float[][][] cy) {
/* 13061 */     return _cadd.apply(cx, cy);
/*       */   }
/*       */   public static float[][][] cadd(Cfloat ca, float[][][] cy) {
/* 13064 */     return _cadd.apply(ca, cy);
/*       */   }
/*       */   public static float[][][] cadd(float[][][] cx, Cfloat cb) {
/* 13067 */     return _cadd.apply(cx, cb);
/*       */   }
/*       */   public static void cadd(float[] cx, float[] cy, float[] cz) {
/* 13070 */     _cadd.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cadd(Cfloat ca, float[] cy, float[] cz) {
/* 13073 */     _cadd.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cadd(float[] cx, Cfloat cb, float[] cz) {
/* 13076 */     _cadd.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cadd(float[][] cx, float[][] cy, float[][] cz) {
/* 13079 */     _cadd.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cadd(Cfloat ca, float[][] cy, float[][] cz) {
/* 13082 */     _cadd.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cadd(float[][] cx, Cfloat cb, float[][] cz) {
/* 13085 */     _cadd.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cadd(float[][][] cx, float[][][] cy, float[][][] cz) {
/* 13088 */     _cadd.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cadd(Cfloat ca, float[][][] cy, float[][][] cz) {
/* 13091 */     _cadd.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cadd(float[][][] cx, Cfloat cb, float[][][] cz) {
/* 13094 */     _cadd.apply(cx, cb, cz);
/*       */   }
/*       */   public static float[] csub(float[] cx, float[] cy) {
/* 13097 */     return _csub.apply(cx, cy);
/*       */   }
/*       */   public static float[] csub(Cfloat ca, float[] cy) {
/* 13100 */     return _csub.apply(ca, cy);
/*       */   }
/*       */   public static float[] csub(float[] cx, Cfloat cb) {
/* 13103 */     return _csub.apply(cx, cb);
/*       */   }
/*       */   public static float[][] csub(float[][] cx, float[][] cy) {
/* 13106 */     return _csub.apply(cx, cy);
/*       */   }
/*       */   public static float[][] csub(Cfloat ca, float[][] cy) {
/* 13109 */     return _csub.apply(ca, cy);
/*       */   }
/*       */   public static float[][] csub(float[][] cx, Cfloat cb) {
/* 13112 */     return _csub.apply(cx, cb);
/*       */   }
/*       */   public static float[][][] csub(float[][][] cx, float[][][] cy) {
/* 13115 */     return _csub.apply(cx, cy);
/*       */   }
/*       */   public static float[][][] csub(Cfloat ca, float[][][] cy) {
/* 13118 */     return _csub.apply(ca, cy);
/*       */   }
/*       */   public static float[][][] csub(float[][][] cx, Cfloat cb) {
/* 13121 */     return _csub.apply(cx, cb);
/*       */   }
/*       */   public static void csub(float[] cx, float[] cy, float[] cz) {
/* 13124 */     _csub.apply(cx, cy, cz);
/*       */   }
/*       */   public static void csub(Cfloat ca, float[] cy, float[] cz) {
/* 13127 */     _csub.apply(ca, cy, cz);
/*       */   }
/*       */   public static void csub(float[] cx, Cfloat cb, float[] cz) {
/* 13130 */     _csub.apply(cx, cb, cz);
/*       */   }
/*       */   public static void csub(float[][] cx, float[][] cy, float[][] cz) {
/* 13133 */     _csub.apply(cx, cy, cz);
/*       */   }
/*       */   public static void csub(Cfloat ca, float[][] cy, float[][] cz) {
/* 13136 */     _csub.apply(ca, cy, cz);
/*       */   }
/*       */   public static void csub(float[][] cx, Cfloat cb, float[][] cz) {
/* 13139 */     _csub.apply(cx, cb, cz);
/*       */   }
/*       */   public static void csub(float[][][] cx, float[][][] cy, float[][][] cz) {
/* 13142 */     _csub.apply(cx, cy, cz);
/*       */   }
/*       */   public static void csub(Cfloat ca, float[][][] cy, float[][][] cz) {
/* 13145 */     _csub.apply(ca, cy, cz);
/*       */   }
/*       */   public static void csub(float[][][] cx, Cfloat cb, float[][][] cz) {
/* 13148 */     _csub.apply(cx, cb, cz);
/*       */   }
/*       */   public static float[] cmul(float[] cx, float[] cy) {
/* 13151 */     return _cmul.apply(cx, cy);
/*       */   }
/*       */   public static float[] cmul(Cfloat ca, float[] cy) {
/* 13154 */     return _cmul.apply(ca, cy);
/*       */   }
/*       */   public static float[] cmul(float[] cx, Cfloat cb) {
/* 13157 */     return _cmul.apply(cx, cb);
/*       */   }
/*       */   public static float[][] cmul(float[][] cx, float[][] cy) {
/* 13160 */     return _cmul.apply(cx, cy);
/*       */   }
/*       */   public static float[][] cmul(Cfloat ca, float[][] cy) {
/* 13163 */     return _cmul.apply(ca, cy);
/*       */   }
/*       */   public static float[][] cmul(float[][] cx, Cfloat cb) {
/* 13166 */     return _cmul.apply(cx, cb);
/*       */   }
/*       */   public static float[][][] cmul(float[][][] cx, float[][][] cy) {
/* 13169 */     return _cmul.apply(cx, cy);
/*       */   }
/*       */   public static float[][][] cmul(Cfloat ca, float[][][] cy) {
/* 13172 */     return _cmul.apply(ca, cy);
/*       */   }
/*       */   public static float[][][] cmul(float[][][] cx, Cfloat cb) {
/* 13175 */     return _cmul.apply(cx, cb);
/*       */   }
/*       */   public static void cmul(float[] cx, float[] cy, float[] cz) {
/* 13178 */     _cmul.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cmul(Cfloat ca, float[] cy, float[] cz) {
/* 13181 */     _cmul.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cmul(float[] cx, Cfloat cb, float[] cz) {
/* 13184 */     _cmul.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cmul(float[][] cx, float[][] cy, float[][] cz) {
/* 13187 */     _cmul.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cmul(Cfloat ca, float[][] cy, float[][] cz) {
/* 13190 */     _cmul.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cmul(float[][] cx, Cfloat cb, float[][] cz) {
/* 13193 */     _cmul.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cmul(float[][][] cx, float[][][] cy, float[][][] cz) {
/* 13196 */     _cmul.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cmul(Cfloat ca, float[][][] cy, float[][][] cz) {
/* 13199 */     _cmul.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cmul(float[][][] cx, Cfloat cb, float[][][] cz) {
/* 13202 */     _cmul.apply(cx, cb, cz);
/*       */   }
/*       */   public static float[] cdiv(float[] cx, float[] cy) {
/* 13205 */     return _cdiv.apply(cx, cy);
/*       */   }
/*       */   public static float[] cdiv(Cfloat ca, float[] cy) {
/* 13208 */     return _cdiv.apply(ca, cy);
/*       */   }
/*       */   public static float[] cdiv(float[] cx, Cfloat cb) {
/* 13211 */     return _cdiv.apply(cx, cb);
/*       */   }
/*       */   public static float[][] cdiv(float[][] cx, float[][] cy) {
/* 13214 */     return _cdiv.apply(cx, cy);
/*       */   }
/*       */   public static float[][] cdiv(Cfloat ca, float[][] cy) {
/* 13217 */     return _cdiv.apply(ca, cy);
/*       */   }
/*       */   public static float[][] cdiv(float[][] cx, Cfloat cb) {
/* 13220 */     return _cdiv.apply(cx, cb);
/*       */   }
/*       */   public static float[][][] cdiv(float[][][] cx, float[][][] cy) {
/* 13223 */     return _cdiv.apply(cx, cy);
/*       */   }
/*       */   public static float[][][] cdiv(Cfloat ca, float[][][] cy) {
/* 13226 */     return _cdiv.apply(ca, cy);
/*       */   }
/*       */   public static float[][][] cdiv(float[][][] cx, Cfloat cb) {
/* 13229 */     return _cdiv.apply(cx, cb);
/*       */   }
/*       */   public static void cdiv(float[] cx, float[] cy, float[] cz) {
/* 13232 */     _cdiv.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cdiv(Cfloat ca, float[] cy, float[] cz) {
/* 13235 */     _cdiv.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cdiv(float[] cx, Cfloat cb, float[] cz) {
/* 13238 */     _cdiv.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cdiv(float[][] cx, float[][] cy, float[][] cz) {
/* 13241 */     _cdiv.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cdiv(Cfloat ca, float[][] cy, float[][] cz) {
/* 13244 */     _cdiv.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cdiv(float[][] cx, Cfloat cb, float[][] cz) {
/* 13247 */     _cdiv.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cdiv(float[][][] cx, float[][][] cy, float[][][] cz) {
/* 13250 */     _cdiv.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cdiv(Cfloat ca, float[][][] cy, float[][][] cz) {
/* 13253 */     _cdiv.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cdiv(float[][][] cx, Cfloat cb, float[][][] cz) {
/* 13256 */     _cdiv.apply(cx, cb, cz);
/*       */   }
/*       */   public static double[] cadd(double[] cx, double[] cy) {
/* 13259 */     return _cadd.apply(cx, cy);
/*       */   }
/*       */   public static double[] cadd(Cdouble ca, double[] cy) {
/* 13262 */     return _cadd.apply(ca, cy);
/*       */   }
/*       */   public static double[] cadd(double[] cx, Cdouble cb) {
/* 13265 */     return _cadd.apply(cx, cb);
/*       */   }
/*       */   public static double[][] cadd(double[][] cx, double[][] cy) {
/* 13268 */     return _cadd.apply(cx, cy);
/*       */   }
/*       */   public static double[][] cadd(Cdouble ca, double[][] cy) {
/* 13271 */     return _cadd.apply(ca, cy);
/*       */   }
/*       */   public static double[][] cadd(double[][] cx, Cdouble cb) {
/* 13274 */     return _cadd.apply(cx, cb);
/*       */   }
/*       */   public static double[][][] cadd(double[][][] cx, double[][][] cy) {
/* 13277 */     return _cadd.apply(cx, cy);
/*       */   }
/*       */   public static double[][][] cadd(Cdouble ca, double[][][] cy) {
/* 13280 */     return _cadd.apply(ca, cy);
/*       */   }
/*       */   public static double[][][] cadd(double[][][] cx, Cdouble cb) {
/* 13283 */     return _cadd.apply(cx, cb);
/*       */   }
/*       */   public static void cadd(double[] cx, double[] cy, double[] cz) {
/* 13286 */     _cadd.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cadd(Cdouble ca, double[] cy, double[] cz) {
/* 13289 */     _cadd.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cadd(double[] cx, Cdouble cb, double[] cz) {
/* 13292 */     _cadd.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cadd(double[][] cx, double[][] cy, double[][] cz) {
/* 13295 */     _cadd.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cadd(Cdouble ca, double[][] cy, double[][] cz) {
/* 13298 */     _cadd.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cadd(double[][] cx, Cdouble cb, double[][] cz) {
/* 13301 */     _cadd.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cadd(double[][][] cx, double[][][] cy, double[][][] cz) {
/* 13304 */     _cadd.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cadd(Cdouble ca, double[][][] cy, double[][][] cz) {
/* 13307 */     _cadd.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cadd(double[][][] cx, Cdouble cb, double[][][] cz) {
/* 13310 */     _cadd.apply(cx, cb, cz);
/*       */   }
/*       */   public static double[] csub(double[] cx, double[] cy) {
/* 13313 */     return _csub.apply(cx, cy);
/*       */   }
/*       */   public static double[] csub(Cdouble ca, double[] cy) {
/* 13316 */     return _csub.apply(ca, cy);
/*       */   }
/*       */   public static double[] csub(double[] cx, Cdouble cb) {
/* 13319 */     return _csub.apply(cx, cb);
/*       */   }
/*       */   public static double[][] csub(double[][] cx, double[][] cy) {
/* 13322 */     return _csub.apply(cx, cy);
/*       */   }
/*       */   public static double[][] csub(Cdouble ca, double[][] cy) {
/* 13325 */     return _csub.apply(ca, cy);
/*       */   }
/*       */   public static double[][] csub(double[][] cx, Cdouble cb) {
/* 13328 */     return _csub.apply(cx, cb);
/*       */   }
/*       */   public static double[][][] csub(double[][][] cx, double[][][] cy) {
/* 13331 */     return _csub.apply(cx, cy);
/*       */   }
/*       */   public static double[][][] csub(Cdouble ca, double[][][] cy) {
/* 13334 */     return _csub.apply(ca, cy);
/*       */   }
/*       */   public static double[][][] csub(double[][][] cx, Cdouble cb) {
/* 13337 */     return _csub.apply(cx, cb);
/*       */   }
/*       */   public static void csub(double[] cx, double[] cy, double[] cz) {
/* 13340 */     _csub.apply(cx, cy, cz);
/*       */   }
/*       */   public static void csub(Cdouble ca, double[] cy, double[] cz) {
/* 13343 */     _csub.apply(ca, cy, cz);
/*       */   }
/*       */   public static void csub(double[] cx, Cdouble cb, double[] cz) {
/* 13346 */     _csub.apply(cx, cb, cz);
/*       */   }
/*       */   public static void csub(double[][] cx, double[][] cy, double[][] cz) {
/* 13349 */     _csub.apply(cx, cy, cz);
/*       */   }
/*       */   public static void csub(Cdouble ca, double[][] cy, double[][] cz) {
/* 13352 */     _csub.apply(ca, cy, cz);
/*       */   }
/*       */   public static void csub(double[][] cx, Cdouble cb, double[][] cz) {
/* 13355 */     _csub.apply(cx, cb, cz);
/*       */   }
/*       */   public static void csub(double[][][] cx, double[][][] cy, double[][][] cz) {
/* 13358 */     _csub.apply(cx, cy, cz);
/*       */   }
/*       */   public static void csub(Cdouble ca, double[][][] cy, double[][][] cz) {
/* 13361 */     _csub.apply(ca, cy, cz);
/*       */   }
/*       */   public static void csub(double[][][] cx, Cdouble cb, double[][][] cz) {
/* 13364 */     _csub.apply(cx, cb, cz);
/*       */   }
/*       */   public static double[] cmul(double[] cx, double[] cy) {
/* 13367 */     return _cmul.apply(cx, cy);
/*       */   }
/*       */   public static double[] cmul(Cdouble ca, double[] cy) {
/* 13370 */     return _cmul.apply(ca, cy);
/*       */   }
/*       */   public static double[] cmul(double[] cx, Cdouble cb) {
/* 13373 */     return _cmul.apply(cx, cb);
/*       */   }
/*       */   public static double[][] cmul(double[][] cx, double[][] cy) {
/* 13376 */     return _cmul.apply(cx, cy);
/*       */   }
/*       */   public static double[][] cmul(Cdouble ca, double[][] cy) {
/* 13379 */     return _cmul.apply(ca, cy);
/*       */   }
/*       */   public static double[][] cmul(double[][] cx, Cdouble cb) {
/* 13382 */     return _cmul.apply(cx, cb);
/*       */   }
/*       */   public static double[][][] cmul(double[][][] cx, double[][][] cy) {
/* 13385 */     return _cmul.apply(cx, cy);
/*       */   }
/*       */   public static double[][][] cmul(Cdouble ca, double[][][] cy) {
/* 13388 */     return _cmul.apply(ca, cy);
/*       */   }
/*       */   public static double[][][] cmul(double[][][] cx, Cdouble cb) {
/* 13391 */     return _cmul.apply(cx, cb);
/*       */   }
/*       */   public static void cmul(double[] cx, double[] cy, double[] cz) {
/* 13394 */     _cmul.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cmul(Cdouble ca, double[] cy, double[] cz) {
/* 13397 */     _cmul.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cmul(double[] cx, Cdouble cb, double[] cz) {
/* 13400 */     _cmul.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cmul(double[][] cx, double[][] cy, double[][] cz) {
/* 13403 */     _cmul.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cmul(Cdouble ca, double[][] cy, double[][] cz) {
/* 13406 */     _cmul.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cmul(double[][] cx, Cdouble cb, double[][] cz) {
/* 13409 */     _cmul.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cmul(double[][][] cx, double[][][] cy, double[][][] cz) {
/* 13412 */     _cmul.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cmul(Cdouble ca, double[][][] cy, double[][][] cz) {
/* 13415 */     _cmul.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cmul(double[][][] cx, Cdouble cb, double[][][] cz) {
/* 13418 */     _cmul.apply(cx, cb, cz);
/*       */   }
/*       */   public static double[] cdiv(double[] cx, double[] cy) {
/* 13421 */     return _cdiv.apply(cx, cy);
/*       */   }
/*       */   public static double[] cdiv(Cdouble ca, double[] cy) {
/* 13424 */     return _cdiv.apply(ca, cy);
/*       */   }
/*       */   public static double[] cdiv(double[] cx, Cdouble cb) {
/* 13427 */     return _cdiv.apply(cx, cb);
/*       */   }
/*       */   public static double[][] cdiv(double[][] cx, double[][] cy) {
/* 13430 */     return _cdiv.apply(cx, cy);
/*       */   }
/*       */   public static double[][] cdiv(Cdouble ca, double[][] cy) {
/* 13433 */     return _cdiv.apply(ca, cy);
/*       */   }
/*       */   public static double[][] cdiv(double[][] cx, Cdouble cb) {
/* 13436 */     return _cdiv.apply(cx, cb);
/*       */   }
/*       */   public static double[][][] cdiv(double[][][] cx, double[][][] cy) {
/* 13439 */     return _cdiv.apply(cx, cy);
/*       */   }
/*       */   public static double[][][] cdiv(Cdouble ca, double[][][] cy) {
/* 13442 */     return _cdiv.apply(ca, cy);
/*       */   }
/*       */   public static double[][][] cdiv(double[][][] cx, Cdouble cb) {
/* 13445 */     return _cdiv.apply(cx, cb);
/*       */   }
/*       */   public static void cdiv(double[] cx, double[] cy, double[] cz) {
/* 13448 */     _cdiv.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cdiv(Cdouble ca, double[] cy, double[] cz) {
/* 13451 */     _cdiv.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cdiv(double[] cx, Cdouble cb, double[] cz) {
/* 13454 */     _cdiv.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cdiv(double[][] cx, double[][] cy, double[][] cz) {
/* 13457 */     _cdiv.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cdiv(Cdouble ca, double[][] cy, double[][] cz) {
/* 13460 */     _cdiv.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cdiv(double[][] cx, Cdouble cb, double[][] cz) {
/* 13463 */     _cdiv.apply(cx, cb, cz);
/*       */   }
/*       */   public static void cdiv(double[][][] cx, double[][][] cy, double[][][] cz) {
/* 13466 */     _cdiv.apply(cx, cy, cz);
/*       */   }
/*       */   public static void cdiv(Cdouble ca, double[][][] cy, double[][][] cz) {
/* 13469 */     _cdiv.apply(ca, cy, cz);
/*       */   }
/*       */   public static void cdiv(double[][][] cx, Cdouble cb, double[][][] cz) {
/* 13472 */     _cdiv.apply(cx, cb, cz);
/*       */   }
/*       */   private static abstract class CBinary { private CBinary() {}
/*       */     float[] apply(float[] cx, float[] cy) {
/* 13476 */       int n1 = cx.length / 2;
/* 13477 */       float[] cz = new float[2 * n1];
/* 13478 */       apply(cx, cy, cz);
/* 13479 */       return cz;
/*       */     }
/*       */     float[] apply(Cfloat ca, float[] cy) {
/* 13482 */       int n1 = cy.length / 2;
/* 13483 */       float[] cz = new float[2 * n1];
/* 13484 */       apply(ca, cy, cz);
/* 13485 */       return cz;
/*       */     }
/*       */     float[] apply(float[] cx, Cfloat cb) {
/* 13488 */       int n1 = cx.length / 2;
/* 13489 */       float[] cz = new float[2 * n1];
/* 13490 */       apply(cx, cb, cz);
/* 13491 */       return cz;
/*       */     }
/*       */     float[][] apply(float[][] cx, float[][] cy) {
/* 13494 */       int n2 = cx.length;
/* 13495 */       float[][] cz = new float[n2][];
/* 13496 */       for (int i2 = 0; i2 < n2; i2++)
/* 13497 */         cz[i2] = apply(cx[i2], cy[i2]); 
/* 13498 */       return cz;
/*       */     }
/*       */     float[][] apply(Cfloat ca, float[][] cy) {
/* 13501 */       int n2 = cy.length;
/* 13502 */       float[][] cz = new float[n2][];
/* 13503 */       for (int i2 = 0; i2 < n2; i2++)
/* 13504 */         cz[i2] = apply(ca, cy[i2]); 
/* 13505 */       return cz;
/*       */     }
/*       */     float[][] apply(float[][] cx, Cfloat cb) {
/* 13508 */       int n2 = cx.length;
/* 13509 */       float[][] cz = new float[n2][];
/* 13510 */       for (int i2 = 0; i2 < n2; i2++)
/* 13511 */         cz[i2] = apply(cx[i2], cb); 
/* 13512 */       return cz;
/*       */     }
/*       */     float[][][] apply(float[][][] cx, float[][][] cy) {
/* 13515 */       int n3 = cx.length;
/* 13516 */       float[][][] cz = new float[n3][][];
/* 13517 */       for (int i3 = 0; i3 < n3; i3++)
/* 13518 */         cz[i3] = apply(cx[i3], cy[i3]); 
/* 13519 */       return cz;
/*       */     }
/*       */     float[][][] apply(Cfloat ca, float[][][] cy) {
/* 13522 */       int n3 = cy.length;
/* 13523 */       float[][][] cz = new float[n3][][];
/* 13524 */       for (int i3 = 0; i3 < n3; i3++)
/* 13525 */         cz[i3] = apply(ca, cy[i3]); 
/* 13526 */       return cz;
/*       */     }
/*       */     float[][][] apply(float[][][] cx, Cfloat cb) {
/* 13529 */       int n3 = cx.length;
/* 13530 */       float[][][] cz = new float[n3][][];
/* 13531 */       for (int i3 = 0; i3 < n3; i3++)
/* 13532 */         cz[i3] = apply(cx[i3], cb); 
/* 13533 */       return cz;
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*       */     void apply(float[][] cx, float[][] cy, float[][] cz) {
/* 13539 */       int n2 = cx.length;
/* 13540 */       for (int i2 = 0; i2 < n2; i2++)
/* 13541 */         apply(cx[i2], cy[i2], cz[i2]); 
/*       */     }
/*       */     void apply(Cfloat ca, float[][] cy, float[][] cz) {
/* 13544 */       int n2 = cy.length;
/* 13545 */       for (int i2 = 0; i2 < n2; i2++)
/* 13546 */         apply(ca, cy[i2], cz[i2]); 
/*       */     }
/*       */     void apply(float[][] cx, Cfloat cb, float[][] cz) {
/* 13549 */       int n2 = cx.length;
/* 13550 */       for (int i2 = 0; i2 < n2; i2++)
/* 13551 */         apply(cx[i2], cb, cz[i2]); 
/*       */     }
/*       */     void apply(float[][][] cx, float[][][] cy, float[][][] cz) {
/* 13554 */       int n3 = cx.length;
/* 13555 */       for (int i3 = 0; i3 < n3; i3++)
/* 13556 */         apply(cx[i3], cy[i3], cz[i3]); 
/*       */     }
/*       */     void apply(Cfloat ca, float[][][] cy, float[][][] cz) {
/* 13559 */       int n3 = cy.length;
/* 13560 */       for (int i3 = 0; i3 < n3; i3++)
/* 13561 */         apply(ca, cy[i3], cz[i3]); 
/*       */     }
/*       */     void apply(float[][][] cx, Cfloat cb, float[][][] cz) {
/* 13564 */       int n3 = cx.length;
/* 13565 */       for (int i3 = 0; i3 < n3; i3++)
/* 13566 */         apply(cx[i3], cb, cz[i3]); 
/*       */     }
/*       */     double[] apply(double[] cx, double[] cy) {
/* 13569 */       int n1 = cx.length / 2;
/* 13570 */       double[] cz = new double[2 * n1];
/* 13571 */       apply(cx, cy, cz);
/* 13572 */       return cz;
/*       */     }
/*       */     double[] apply(Cdouble ca, double[] cy) {
/* 13575 */       int n1 = cy.length / 2;
/* 13576 */       double[] cz = new double[2 * n1];
/* 13577 */       apply(ca, cy, cz);
/* 13578 */       return cz;
/*       */     }
/*       */     double[] apply(double[] cx, Cdouble cb) {
/* 13581 */       int n1 = cx.length / 2;
/* 13582 */       double[] cz = new double[2 * n1];
/* 13583 */       apply(cx, cb, cz);
/* 13584 */       return cz;
/*       */     }
/*       */     double[][] apply(double[][] cx, double[][] cy) {
/* 13587 */       int n2 = cx.length;
/* 13588 */       double[][] cz = new double[n2][];
/* 13589 */       for (int i2 = 0; i2 < n2; i2++)
/* 13590 */         cz[i2] = apply(cx[i2], cy[i2]); 
/* 13591 */       return cz;
/*       */     }
/*       */     double[][] apply(Cdouble ca, double[][] cy) {
/* 13594 */       int n2 = cy.length;
/* 13595 */       double[][] cz = new double[n2][];
/* 13596 */       for (int i2 = 0; i2 < n2; i2++)
/* 13597 */         cz[i2] = apply(ca, cy[i2]); 
/* 13598 */       return cz;
/*       */     }
/*       */     double[][] apply(double[][] cx, Cdouble cb) {
/* 13601 */       int n2 = cx.length;
/* 13602 */       double[][] cz = new double[n2][];
/* 13603 */       for (int i2 = 0; i2 < n2; i2++)
/* 13604 */         cz[i2] = apply(cx[i2], cb); 
/* 13605 */       return cz;
/*       */     }
/*       */     double[][][] apply(double[][][] cx, double[][][] cy) {
/* 13608 */       int n3 = cx.length;
/* 13609 */       double[][][] cz = new double[n3][][];
/* 13610 */       for (int i3 = 0; i3 < n3; i3++)
/* 13611 */         cz[i3] = apply(cx[i3], cy[i3]); 
/* 13612 */       return cz;
/*       */     }
/*       */     double[][][] apply(Cdouble ca, double[][][] cy) {
/* 13615 */       int n3 = cy.length;
/* 13616 */       double[][][] cz = new double[n3][][];
/* 13617 */       for (int i3 = 0; i3 < n3; i3++)
/* 13618 */         cz[i3] = apply(ca, cy[i3]); 
/* 13619 */       return cz;
/*       */     }
/*       */     double[][][] apply(double[][][] cx, Cdouble cb) {
/* 13622 */       int n3 = cx.length;
/* 13623 */       double[][][] cz = new double[n3][][];
/* 13624 */       for (int i3 = 0; i3 < n3; i3++)
/* 13625 */         cz[i3] = apply(cx[i3], cb); 
/* 13626 */       return cz;
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*       */     void apply(double[][] cx, double[][] cy, double[][] cz) {
/* 13632 */       int n2 = cx.length;
/* 13633 */       for (int i2 = 0; i2 < n2; i2++)
/* 13634 */         apply(cx[i2], cy[i2], cz[i2]); 
/*       */     }
/*       */     void apply(Cdouble ca, double[][] cy, double[][] cz) {
/* 13637 */       int n2 = cy.length;
/* 13638 */       for (int i2 = 0; i2 < n2; i2++)
/* 13639 */         apply(ca, cy[i2], cz[i2]); 
/*       */     }
/*       */     void apply(double[][] cx, Cdouble cb, double[][] cz) {
/* 13642 */       int n2 = cx.length;
/* 13643 */       for (int i2 = 0; i2 < n2; i2++)
/* 13644 */         apply(cx[i2], cb, cz[i2]); 
/*       */     }
/*       */     void apply(double[][][] cx, double[][][] cy, double[][][] cz) {
/* 13647 */       int n3 = cx.length;
/* 13648 */       for (int i3 = 0; i3 < n3; i3++)
/* 13649 */         apply(cx[i3], cy[i3], cz[i3]); 
/*       */     }
/*       */     void apply(Cdouble ca, double[][][] cy, double[][][] cz) {
/* 13652 */       int n3 = cy.length;
/* 13653 */       for (int i3 = 0; i3 < n3; i3++)
/* 13654 */         apply(ca, cy[i3], cz[i3]); 
/*       */     }
/*       */     void apply(double[][][] cx, Cdouble cb, double[][][] cz) {
/* 13657 */       int n3 = cx.length;
/* 13658 */       for (int i3 = 0; i3 < n3; i3++)
/* 13659 */         apply(cx[i3], cb, cz[i3]); 
/*       */     } abstract void apply(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2, float[] param1ArrayOffloat3); abstract void apply(Cfloat param1Cfloat, float[] param1ArrayOffloat1, float[] param1ArrayOffloat2); abstract void apply(float[] param1ArrayOffloat1, Cfloat param1Cfloat, float[] param1ArrayOffloat2); abstract void apply(double[] param1ArrayOfdouble1, double[] param1ArrayOfdouble2, double[] param1ArrayOfdouble3); abstract void apply(Cdouble param1Cdouble, double[] param1ArrayOfdouble1, double[] param1ArrayOfdouble2);
/*       */     abstract void apply(double[] param1ArrayOfdouble1, Cdouble param1Cdouble, double[] param1ArrayOfdouble2); }
/* 13662 */   private static CBinary _cadd = new CBinary() {
/*       */       void apply(float[] cx, float[] cy, float[] cz) {
/* 13664 */         int n1 = cx.length / 2;
/* 13665 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13666 */           cz[ir] = cx[ir] + cy[ir];
/* 13667 */           cz[ii] = cx[ii] + cy[ii];
/*       */         } 
/*       */       }
/*       */       void apply(Cfloat ca, float[] cy, float[] cz) {
/* 13671 */         int n1 = cy.length / 2;
/* 13672 */         float ar = ca.r;
/* 13673 */         float ai = ca.i;
/* 13674 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13675 */           cz[ir] = ar + cy[ir];
/* 13676 */           cz[ii] = ai + cy[ii];
/*       */         } 
/*       */       }
/*       */       void apply(float[] cx, Cfloat cb, float[] cz) {
/* 13680 */         int n1 = cx.length / 2;
/* 13681 */         float br = cb.r;
/* 13682 */         float bi = cb.i;
/* 13683 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13684 */           cz[ir] = cx[ir] + br;
/* 13685 */           cz[ii] = cx[ii] + bi;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy, double[] cz) {
/* 13689 */         int n1 = cx.length / 2;
/* 13690 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13691 */           cz[ir] = cx[ir] + cy[ir];
/* 13692 */           cz[ii] = cx[ii] + cy[ii];
/*       */         } 
/*       */       }
/*       */       void apply(Cdouble ca, double[] cy, double[] cz) {
/* 13696 */         int n1 = cy.length / 2;
/* 13697 */         double ar = ca.r;
/* 13698 */         double ai = ca.i;
/* 13699 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13700 */           cz[ir] = ar + cy[ir];
/* 13701 */           cz[ii] = ai + cy[ii];
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, Cdouble cb, double[] cz) {
/* 13705 */         int n1 = cx.length / 2;
/* 13706 */         double br = cb.r;
/* 13707 */         double bi = cb.i;
/* 13708 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13709 */           cz[ir] = cx[ir] + br;
/* 13710 */           cz[ii] = cx[ii] + bi;
/*       */         } 
/*       */       }
/*       */     };
/* 13714 */   private static CBinary _csub = new CBinary() {
/*       */       void apply(float[] cx, float[] cy, float[] cz) {
/* 13716 */         int n1 = cx.length / 2;
/* 13717 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13718 */           cz[ir] = cx[ir] - cy[ir];
/* 13719 */           cz[ii] = cx[ii] - cy[ii];
/*       */         } 
/*       */       }
/*       */       void apply(Cfloat ca, float[] cy, float[] cz) {
/* 13723 */         float ar = ca.r;
/* 13724 */         float ai = ca.i;
/* 13725 */         int n1 = cy.length / 2;
/* 13726 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13727 */           cz[ir] = ar - cy[ir];
/* 13728 */           cz[ii] = ai - cy[ii];
/*       */         } 
/*       */       }
/*       */       void apply(float[] cx, Cfloat cb, float[] cz) {
/* 13732 */         float br = cb.r;
/* 13733 */         float bi = cb.i;
/* 13734 */         int n1 = cx.length / 2;
/* 13735 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13736 */           cz[ir] = cx[ir] - br;
/* 13737 */           cz[ii] = cx[ii] - bi;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy, double[] cz) {
/* 13741 */         int n1 = cx.length / 2;
/* 13742 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13743 */           cz[ir] = cx[ir] - cy[ir];
/* 13744 */           cz[ii] = cx[ii] - cy[ii];
/*       */         } 
/*       */       }
/*       */       void apply(Cdouble ca, double[] cy, double[] cz) {
/* 13748 */         double ar = ca.r;
/* 13749 */         double ai = ca.i;
/* 13750 */         int n1 = cy.length / 2;
/* 13751 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13752 */           cz[ir] = ar - cy[ir];
/* 13753 */           cz[ii] = ai - cy[ii];
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, Cdouble cb, double[] cz) {
/* 13757 */         double br = cb.r;
/* 13758 */         double bi = cb.i;
/* 13759 */         int n1 = cx.length / 2;
/* 13760 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13761 */           cz[ir] = cx[ir] - br;
/* 13762 */           cz[ii] = cx[ii] - bi;
/*       */         } 
/*       */       }
/*       */     };
/* 13766 */   private static CBinary _cmul = new CBinary() {
/*       */       void apply(float[] cx, float[] cy, float[] cz) {
/* 13768 */         int n1 = cx.length / 2;
/* 13769 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13770 */           float xr = cx[ir];
/* 13771 */           float xi = cx[ii];
/* 13772 */           float yr = cy[ir];
/* 13773 */           float yi = cy[ii];
/* 13774 */           cz[ir] = xr * yr - xi * yi;
/* 13775 */           cz[ii] = xr * yi + xi * yr;
/*       */         } 
/*       */       }
/*       */       void apply(Cfloat ca, float[] cy, float[] cz) {
/* 13779 */         float ar = ca.r;
/* 13780 */         float ai = ca.i;
/* 13781 */         int n1 = cy.length / 2;
/* 13782 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13783 */           float yr = cy[ir];
/* 13784 */           float yi = cy[ii];
/* 13785 */           cz[ir] = ar * yr - ai * yi;
/* 13786 */           cz[ii] = ar * yi + ai * yr;
/*       */         } 
/*       */       }
/*       */       void apply(float[] cx, Cfloat cb, float[] cz) {
/* 13790 */         float br = cb.r;
/* 13791 */         float bi = cb.i;
/* 13792 */         int n1 = cx.length / 2;
/* 13793 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13794 */           float xr = cx[ir];
/* 13795 */           float xi = cx[ii];
/* 13796 */           cz[ir] = xr * br - xi * bi;
/* 13797 */           cz[ii] = xr * bi + xi * br;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy, double[] cz) {
/* 13801 */         int n1 = cx.length / 2;
/* 13802 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13803 */           double xr = cx[ir];
/* 13804 */           double xi = cx[ii];
/* 13805 */           double yr = cy[ir];
/* 13806 */           double yi = cy[ii];
/* 13807 */           cz[ir] = xr * yr - xi * yi;
/* 13808 */           cz[ii] = xr * yi + xi * yr;
/*       */         } 
/*       */       }
/*       */       void apply(Cdouble ca, double[] cy, double[] cz) {
/* 13812 */         double ar = ca.r;
/* 13813 */         double ai = ca.i;
/* 13814 */         int n1 = cy.length / 2;
/* 13815 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13816 */           double yr = cy[ir];
/* 13817 */           double yi = cy[ii];
/* 13818 */           cz[ir] = ar * yr - ai * yi;
/* 13819 */           cz[ii] = ar * yi + ai * yr;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, Cdouble cb, double[] cz) {
/* 13823 */         double br = cb.r;
/* 13824 */         double bi = cb.i;
/* 13825 */         int n1 = cx.length / 2;
/* 13826 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13827 */           double xr = cx[ir];
/* 13828 */           double xi = cx[ii];
/* 13829 */           cz[ir] = xr * br - xi * bi;
/* 13830 */           cz[ii] = xr * bi + xi * br;
/*       */         } 
/*       */       }
/*       */     };
/* 13834 */   private static CBinary _cdiv = new CBinary() {
/*       */       void apply(float[] cx, float[] cy, float[] cz) {
/* 13836 */         int n1 = cx.length / 2;
/* 13837 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13838 */           float xr = cx[ir];
/* 13839 */           float xi = cx[ii];
/* 13840 */           float yr = cy[ir];
/* 13841 */           float yi = cy[ii];
/* 13842 */           float yd = yr * yr + yi * yi;
/* 13843 */           cz[ir] = (xr * yr + xi * yi) / yd;
/* 13844 */           cz[ii] = (xi * yr - xr * yi) / yd;
/*       */         } 
/*       */       }
/*       */       void apply(Cfloat ca, float[] cy, float[] cz) {
/* 13848 */         float ar = ca.r;
/* 13849 */         float ai = ca.i;
/* 13850 */         int n1 = cy.length / 2;
/* 13851 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13852 */           float yr = cy[ir];
/* 13853 */           float yi = cy[ii];
/* 13854 */           float yd = yr * yr + yi * yi;
/* 13855 */           cz[ir] = (ar * yr + ai * yi) / yd;
/* 13856 */           cz[ii] = (ai * yr - ar * yi) / yd;
/*       */         } 
/*       */       }
/*       */       void apply(float[] cx, Cfloat cb, float[] cz) {
/* 13860 */         float br = cb.r;
/* 13861 */         float bi = cb.i;
/* 13862 */         float bd = br * br + bi * bi;
/* 13863 */         int n1 = cx.length / 2;
/* 13864 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13865 */           float xr = cx[ir];
/* 13866 */           float xi = cx[ii];
/* 13867 */           cz[ir] = (xr * br + xi * bi) / bd;
/* 13868 */           cz[ii] = (xi * br - xr * bi) / bd;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy, double[] cz) {
/* 13872 */         int n1 = cx.length / 2;
/* 13873 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13874 */           double xr = cx[ir];
/* 13875 */           double xi = cx[ii];
/* 13876 */           double yr = cy[ir];
/* 13877 */           double yi = cy[ii];
/* 13878 */           double yd = yr * yr + yi * yi;
/* 13879 */           cz[ir] = (xr * yr + xi * yi) / yd;
/* 13880 */           cz[ii] = (xi * yr - xr * yi) / yd;
/*       */         } 
/*       */       }
/*       */       void apply(Cdouble ca, double[] cy, double[] cz) {
/* 13884 */         double ar = ca.r;
/* 13885 */         double ai = ca.i;
/* 13886 */         int n1 = cy.length / 2;
/* 13887 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13888 */           double yr = cy[ir];
/* 13889 */           double yi = cy[ii];
/* 13890 */           double yd = yr * yr + yi * yi;
/* 13891 */           cz[ir] = (ar * yr + ai * yi) / yd;
/* 13892 */           cz[ii] = (ai * yr - ar * yi) / yd;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, Cdouble cb, double[] cz) {
/* 13896 */         double br = cb.r;
/* 13897 */         double bi = cb.i;
/* 13898 */         double bd = br * br + bi * bi;
/* 13899 */         int n1 = cx.length / 2;
/* 13900 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 13901 */           double xr = cx[ir];
/* 13902 */           double xi = cx[ii];
/* 13903 */           cz[ir] = (xr * br + xi * bi) / bd;
/* 13904 */           cz[ii] = (xi * br - xr * bi) / bd;
/*       */         } 
/*       */       }
/*       */     };
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] cneg(float[] cx) {
/* 13913 */     return _cneg.apply(cx);
/*       */   }
/*       */   public static float[][] cneg(float[][] cx) {
/* 13916 */     return _cneg.apply(cx);
/*       */   }
/*       */   public static float[][][] cneg(float[][][] cx) {
/* 13919 */     return _cneg.apply(cx);
/*       */   }
/*       */   public static void cneg(float[] cx, float[] cy) {
/* 13922 */     _cneg.apply(cx, cy);
/*       */   }
/*       */   public static void cneg(float[][] cx, float[][] cy) {
/* 13925 */     _cneg.apply(cx, cy);
/*       */   }
/*       */   public static void cneg(float[][][] cx, float[][][] cy) {
/* 13928 */     _cneg.apply(cx, cy);
/*       */   }
/*       */   public static float[] cconj(float[] cx) {
/* 13931 */     return _cconj.apply(cx);
/*       */   }
/*       */   public static float[][] cconj(float[][] cx) {
/* 13934 */     return _cconj.apply(cx);
/*       */   }
/*       */   public static float[][][] cconj(float[][][] cx) {
/* 13937 */     return _cconj.apply(cx);
/*       */   }
/*       */   public static void cconj(float[] cx, float[] cy) {
/* 13940 */     _cconj.apply(cx, cy);
/*       */   }
/*       */   public static void cconj(float[][] cx, float[][] cy) {
/* 13943 */     _cconj.apply(cx, cy);
/*       */   }
/*       */   public static void cconj(float[][][] cx, float[][][] cy) {
/* 13946 */     _cconj.apply(cx, cy);
/*       */   }
/*       */   public static float[] ccos(float[] cx) {
/* 13949 */     return _ccos.apply(cx);
/*       */   }
/*       */   public static float[][] ccos(float[][] cx) {
/* 13952 */     return _ccos.apply(cx);
/*       */   }
/*       */   public static float[][][] ccos(float[][][] cx) {
/* 13955 */     return _ccos.apply(cx);
/*       */   }
/*       */   public static void ccos(float[] cx, float[] cy) {
/* 13958 */     _ccos.apply(cx, cy);
/*       */   }
/*       */   public static void ccos(float[][] cx, float[][] cy) {
/* 13961 */     _ccos.apply(cx, cy);
/*       */   }
/*       */   public static void ccos(float[][][] cx, float[][][] cy) {
/* 13964 */     _ccos.apply(cx, cy);
/*       */   }
/*       */   public static float[] csin(float[] cx) {
/* 13967 */     return _csin.apply(cx);
/*       */   }
/*       */   public static float[][] csin(float[][] cx) {
/* 13970 */     return _csin.apply(cx);
/*       */   }
/*       */   public static float[][][] csin(float[][][] cx) {
/* 13973 */     return _csin.apply(cx);
/*       */   }
/*       */   public static void csin(float[] cx, float[] cy) {
/* 13976 */     _csin.apply(cx, cy);
/*       */   }
/*       */   public static void csin(float[][] cx, float[][] cy) {
/* 13979 */     _csin.apply(cx, cy);
/*       */   }
/*       */   public static void csin(float[][][] cx, float[][][] cy) {
/* 13982 */     _csin.apply(cx, cy);
/*       */   }
/*       */   public static float[] csqrt(float[] cx) {
/* 13985 */     return _csqrt.apply(cx);
/*       */   }
/*       */   public static float[][] csqrt(float[][] cx) {
/* 13988 */     return _csqrt.apply(cx);
/*       */   }
/*       */   public static float[][][] csqrt(float[][][] cx) {
/* 13991 */     return _csqrt.apply(cx);
/*       */   }
/*       */   public static void csqrt(float[] cx, float[] cy) {
/* 13994 */     _csqrt.apply(cx, cy);
/*       */   }
/*       */   public static void csqrt(float[][] cx, float[][] cy) {
/* 13997 */     _csqrt.apply(cx, cy);
/*       */   }
/*       */   public static void csqrt(float[][][] cx, float[][][] cy) {
/* 14000 */     _csqrt.apply(cx, cy);
/*       */   }
/*       */   public static float[] cexp(float[] cx) {
/* 14003 */     return _cexp.apply(cx);
/*       */   }
/*       */   public static float[][] cexp(float[][] cx) {
/* 14006 */     return _cexp.apply(cx);
/*       */   }
/*       */   public static float[][][] cexp(float[][][] cx) {
/* 14009 */     return _cexp.apply(cx);
/*       */   }
/*       */   public static void cexp(float[] cx, float[] cy) {
/* 14012 */     _cexp.apply(cx, cy);
/*       */   }
/*       */   public static void cexp(float[][] cx, float[][] cy) {
/* 14015 */     _cexp.apply(cx, cy);
/*       */   }
/*       */   public static void cexp(float[][][] cx, float[][][] cy) {
/* 14018 */     _cexp.apply(cx, cy);
/*       */   }
/*       */   public static float[] clog(float[] cx) {
/* 14021 */     return _clog.apply(cx);
/*       */   }
/*       */   public static float[][] clog(float[][] cx) {
/* 14024 */     return _clog.apply(cx);
/*       */   }
/*       */   public static float[][][] clog(float[][][] cx) {
/* 14027 */     return _clog.apply(cx);
/*       */   }
/*       */   public static void clog(float[] cx, float[] cy) {
/* 14030 */     _clog.apply(cx, cy);
/*       */   }
/*       */   public static void clog(float[][] cx, float[][] cy) {
/* 14033 */     _clog.apply(cx, cy);
/*       */   }
/*       */   public static void clog(float[][][] cx, float[][][] cy) {
/* 14036 */     _clog.apply(cx, cy);
/*       */   }
/*       */   public static float[] clog10(float[] cx) {
/* 14039 */     return _clog10.apply(cx);
/*       */   }
/*       */   public static float[][] clog10(float[][] cx) {
/* 14042 */     return _clog10.apply(cx);
/*       */   }
/*       */   public static float[][][] clog10(float[][][] cx) {
/* 14045 */     return _clog10.apply(cx);
/*       */   }
/*       */   public static void clog10(float[] cx, float[] cy) {
/* 14048 */     _clog10.apply(cx, cy);
/*       */   }
/*       */   public static void clog10(float[][] cx, float[][] cy) {
/* 14051 */     _clog10.apply(cx, cy);
/*       */   }
/*       */   public static void clog10(float[][][] cx, float[][][] cy) {
/* 14054 */     _clog10.apply(cx, cy);
/*       */   }
/*       */   public static double[] cneg(double[] cx) {
/* 14057 */     return _cneg.apply(cx);
/*       */   }
/*       */   public static double[][] cneg(double[][] cx) {
/* 14060 */     return _cneg.apply(cx);
/*       */   }
/*       */   public static double[][][] cneg(double[][][] cx) {
/* 14063 */     return _cneg.apply(cx);
/*       */   }
/*       */   public static void cneg(double[] cx, double[] cy) {
/* 14066 */     _cneg.apply(cx, cy);
/*       */   }
/*       */   public static void cneg(double[][] cx, double[][] cy) {
/* 14069 */     _cneg.apply(cx, cy);
/*       */   }
/*       */   public static void cneg(double[][][] cx, double[][][] cy) {
/* 14072 */     _cneg.apply(cx, cy);
/*       */   }
/*       */   public static double[] cconj(double[] cx) {
/* 14075 */     return _cconj.apply(cx);
/*       */   }
/*       */   public static double[][] cconj(double[][] cx) {
/* 14078 */     return _cconj.apply(cx);
/*       */   }
/*       */   public static double[][][] cconj(double[][][] cx) {
/* 14081 */     return _cconj.apply(cx);
/*       */   }
/*       */   public static void cconj(double[] cx, double[] cy) {
/* 14084 */     _cconj.apply(cx, cy);
/*       */   }
/*       */   public static void cconj(double[][] cx, double[][] cy) {
/* 14087 */     _cconj.apply(cx, cy);
/*       */   }
/*       */   public static void cconj(double[][][] cx, double[][][] cy) {
/* 14090 */     _cconj.apply(cx, cy);
/*       */   }
/*       */   public static double[] ccos(double[] cx) {
/* 14093 */     return _ccos.apply(cx);
/*       */   }
/*       */   public static double[][] ccos(double[][] cx) {
/* 14096 */     return _ccos.apply(cx);
/*       */   }
/*       */   public static double[][][] ccos(double[][][] cx) {
/* 14099 */     return _ccos.apply(cx);
/*       */   }
/*       */   public static void ccos(double[] cx, double[] cy) {
/* 14102 */     _ccos.apply(cx, cy);
/*       */   }
/*       */   public static void ccos(double[][] cx, double[][] cy) {
/* 14105 */     _ccos.apply(cx, cy);
/*       */   }
/*       */   public static void ccos(double[][][] cx, double[][][] cy) {
/* 14108 */     _ccos.apply(cx, cy);
/*       */   }
/*       */   public static double[] csin(double[] cx) {
/* 14111 */     return _csin.apply(cx);
/*       */   }
/*       */   public static double[][] csin(double[][] cx) {
/* 14114 */     return _csin.apply(cx);
/*       */   }
/*       */   public static double[][][] csin(double[][][] cx) {
/* 14117 */     return _csin.apply(cx);
/*       */   }
/*       */   public static void csin(double[] cx, double[] cy) {
/* 14120 */     _csin.apply(cx, cy);
/*       */   }
/*       */   public static void csin(double[][] cx, double[][] cy) {
/* 14123 */     _csin.apply(cx, cy);
/*       */   }
/*       */   public static void csin(double[][][] cx, double[][][] cy) {
/* 14126 */     _csin.apply(cx, cy);
/*       */   }
/*       */   public static double[] csqrt(double[] cx) {
/* 14129 */     return _csqrt.apply(cx);
/*       */   }
/*       */   public static double[][] csqrt(double[][] cx) {
/* 14132 */     return _csqrt.apply(cx);
/*       */   }
/*       */   public static double[][][] csqrt(double[][][] cx) {
/* 14135 */     return _csqrt.apply(cx);
/*       */   }
/*       */   public static void csqrt(double[] cx, double[] cy) {
/* 14138 */     _csqrt.apply(cx, cy);
/*       */   }
/*       */   public static void csqrt(double[][] cx, double[][] cy) {
/* 14141 */     _csqrt.apply(cx, cy);
/*       */   }
/*       */   public static void csqrt(double[][][] cx, double[][][] cy) {
/* 14144 */     _csqrt.apply(cx, cy);
/*       */   }
/*       */   public static double[] cexp(double[] cx) {
/* 14147 */     return _cexp.apply(cx);
/*       */   }
/*       */   public static double[][] cexp(double[][] cx) {
/* 14150 */     return _cexp.apply(cx);
/*       */   }
/*       */   public static double[][][] cexp(double[][][] cx) {
/* 14153 */     return _cexp.apply(cx);
/*       */   }
/*       */   public static void cexp(double[] cx, double[] cy) {
/* 14156 */     _cexp.apply(cx, cy);
/*       */   }
/*       */   public static void cexp(double[][] cx, double[][] cy) {
/* 14159 */     _cexp.apply(cx, cy);
/*       */   }
/*       */   public static void cexp(double[][][] cx, double[][][] cy) {
/* 14162 */     _cexp.apply(cx, cy);
/*       */   }
/*       */   public static double[] clog(double[] cx) {
/* 14165 */     return _clog.apply(cx);
/*       */   }
/*       */   public static double[][] clog(double[][] cx) {
/* 14168 */     return _clog.apply(cx);
/*       */   }
/*       */   public static double[][][] clog(double[][][] cx) {
/* 14171 */     return _clog.apply(cx);
/*       */   }
/*       */   public static void clog(double[] cx, double[] cy) {
/* 14174 */     _clog.apply(cx, cy);
/*       */   }
/*       */   public static void clog(double[][] cx, double[][] cy) {
/* 14177 */     _clog.apply(cx, cy);
/*       */   }
/*       */   public static void clog(double[][][] cx, double[][][] cy) {
/* 14180 */     _clog.apply(cx, cy);
/*       */   }
/*       */   public static double[] clog10(double[] cx) {
/* 14183 */     return _clog10.apply(cx);
/*       */   }
/*       */   public static double[][] clog10(double[][] cx) {
/* 14186 */     return _clog10.apply(cx);
/*       */   }
/*       */   public static double[][][] clog10(double[][][] cx) {
/* 14189 */     return _clog10.apply(cx);
/*       */   }
/*       */   public static void clog10(double[] cx, double[] cy) {
/* 14192 */     _clog10.apply(cx, cy);
/*       */   }
/*       */   public static void clog10(double[][] cx, double[][] cy) {
/* 14195 */     _clog10.apply(cx, cy);
/*       */   }
/*       */   public static void clog10(double[][][] cx, double[][][] cy) {
/* 14198 */     _clog10.apply(cx, cy);
/*       */   }
/*       */   private static abstract class ComplexToComplex { private ComplexToComplex() {}
/*       */     float[] apply(float[] cx) {
/* 14202 */       int n1 = cx.length / 2;
/* 14203 */       float[] cy = new float[2 * n1];
/* 14204 */       apply(cx, cy);
/* 14205 */       return cy;
/*       */     }
/*       */     float[][] apply(float[][] cx) {
/* 14208 */       int n2 = cx.length;
/* 14209 */       float[][] cy = new float[n2][];
/* 14210 */       for (int i2 = 0; i2 < n2; i2++)
/* 14211 */         cy[i2] = apply(cx[i2]); 
/* 14212 */       return cy;
/*       */     }
/*       */     float[][][] apply(float[][][] cx) {
/* 14215 */       int n3 = cx.length;
/* 14216 */       float[][][] cy = new float[n3][][];
/* 14217 */       for (int i3 = 0; i3 < n3; i3++)
/* 14218 */         cy[i3] = apply(cx[i3]); 
/* 14219 */       return cy;
/*       */     }
/*       */     
/*       */     void apply(float[][] cx, float[][] cy) {
/* 14223 */       int n2 = cx.length;
/* 14224 */       for (int i2 = 0; i2 < n2; i2++)
/* 14225 */         apply(cx[i2], cy[i2]); 
/*       */     }
/*       */     void apply(float[][][] cx, float[][][] cy) {
/* 14228 */       int n3 = cx.length;
/* 14229 */       for (int i3 = 0; i3 < n3; i3++)
/* 14230 */         apply(cx[i3], cy[i3]); 
/*       */     }
/*       */     double[] apply(double[] cx) {
/* 14233 */       int n1 = cx.length / 2;
/* 14234 */       double[] cy = new double[2 * n1];
/* 14235 */       apply(cx, cy);
/* 14236 */       return cy;
/*       */     }
/*       */     double[][] apply(double[][] cx) {
/* 14239 */       int n2 = cx.length;
/* 14240 */       double[][] cy = new double[n2][];
/* 14241 */       for (int i2 = 0; i2 < n2; i2++)
/* 14242 */         cy[i2] = apply(cx[i2]); 
/* 14243 */       return cy;
/*       */     }
/*       */     double[][][] apply(double[][][] cx) {
/* 14246 */       int n3 = cx.length;
/* 14247 */       double[][][] cy = new double[n3][][];
/* 14248 */       for (int i3 = 0; i3 < n3; i3++)
/* 14249 */         cy[i3] = apply(cx[i3]); 
/* 14250 */       return cy;
/*       */     }
/*       */     
/*       */     void apply(double[][] cx, double[][] cy) {
/* 14254 */       int n2 = cx.length;
/* 14255 */       for (int i2 = 0; i2 < n2; i2++)
/* 14256 */         apply(cx[i2], cy[i2]); 
/*       */     }
/*       */     void apply(double[][][] cx, double[][][] cy) {
/* 14259 */       int n3 = cx.length;
/* 14260 */       for (int i3 = 0; i3 < n3; i3++)
/* 14261 */         apply(cx[i3], cy[i3]); 
/*       */     } abstract void apply(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2);
/*       */     abstract void apply(double[] param1ArrayOfdouble1, double[] param1ArrayOfdouble2); }
/* 14264 */   private static ComplexToComplex _cneg = new ComplexToComplex() {
/*       */       void apply(float[] cx, float[] cy) {
/* 14266 */         int n1 = cx.length;
/* 14267 */         for (int i1 = 0; i1 < n1; i1++)
/* 14268 */           cy[i1] = -cx[i1]; 
/*       */       }
/*       */       void apply(double[] cx, double[] cy) {
/* 14271 */         int n1 = cx.length;
/* 14272 */         for (int i1 = 0; i1 < n1; i1++)
/* 14273 */           cy[i1] = -cx[i1]; 
/*       */       }
/*       */     };
/* 14276 */   private static ComplexToComplex _cconj = new ComplexToComplex() {
/*       */       void apply(float[] cx, float[] cy) {
/* 14278 */         int n1 = cx.length / 2;
/* 14279 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14280 */           cy[ir] = cx[ir];
/* 14281 */           cy[ii] = -cx[ii];
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy) {
/* 14285 */         int n1 = cx.length / 2;
/* 14286 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14287 */           cy[ir] = cx[ir];
/* 14288 */           cy[ii] = -cx[ii];
/*       */         } 
/*       */       }
/*       */     };
/* 14292 */   private static ComplexToComplex _ccos = new ComplexToComplex() {
/*       */       void apply(float[] cx, float[] cy) {
/* 14294 */         Cfloat ct = new Cfloat();
/* 14295 */         int n1 = cx.length / 2;
/* 14296 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14297 */           ct.r = cx[ir];
/* 14298 */           ct.i = cx[ii];
/* 14299 */           Cfloat ce = Cfloat.cos(ct);
/* 14300 */           cy[ir] = ce.r;
/* 14301 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy) {
/* 14305 */         Cdouble ct = new Cdouble();
/* 14306 */         int n1 = cx.length / 2;
/* 14307 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14308 */           ct.r = cx[ir];
/* 14309 */           ct.i = cx[ii];
/* 14310 */           Cdouble ce = Cdouble.cos(ct);
/* 14311 */           cy[ir] = ce.r;
/* 14312 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */     };
/* 14316 */   private static ComplexToComplex _csin = new ComplexToComplex() {
/*       */       void apply(float[] cx, float[] cy) {
/* 14318 */         Cfloat ct = new Cfloat();
/* 14319 */         int n1 = cx.length / 2;
/* 14320 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14321 */           ct.r = cx[ir];
/* 14322 */           ct.i = cx[ii];
/* 14323 */           Cfloat ce = Cfloat.sin(ct);
/* 14324 */           cy[ir] = ce.r;
/* 14325 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy) {
/* 14329 */         Cdouble ct = new Cdouble();
/* 14330 */         int n1 = cx.length / 2;
/* 14331 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14332 */           ct.r = cx[ir];
/* 14333 */           ct.i = cx[ii];
/* 14334 */           Cdouble ce = Cdouble.sin(ct);
/* 14335 */           cy[ir] = ce.r;
/* 14336 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */     };
/* 14340 */   private static ComplexToComplex _csqrt = new ComplexToComplex() {
/*       */       void apply(float[] cx, float[] cy) {
/* 14342 */         Cfloat ct = new Cfloat();
/* 14343 */         int n1 = cx.length / 2;
/* 14344 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14345 */           ct.r = cx[ir];
/* 14346 */           ct.i = cx[ii];
/* 14347 */           Cfloat ce = Cfloat.sqrt(ct);
/* 14348 */           cy[ir] = ce.r;
/* 14349 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy) {
/* 14353 */         Cdouble ct = new Cdouble();
/* 14354 */         int n1 = cx.length / 2;
/* 14355 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14356 */           ct.r = cx[ir];
/* 14357 */           ct.i = cx[ii];
/* 14358 */           Cdouble ce = Cdouble.sqrt(ct);
/* 14359 */           cy[ir] = ce.r;
/* 14360 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */     };
/* 14364 */   private static ComplexToComplex _cexp = new ComplexToComplex() {
/*       */       void apply(float[] cx, float[] cy) {
/* 14366 */         Cfloat ct = new Cfloat();
/* 14367 */         int n1 = cx.length / 2;
/* 14368 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14369 */           ct.r = cx[ir];
/* 14370 */           ct.i = cx[ii];
/* 14371 */           Cfloat ce = Cfloat.exp(ct);
/* 14372 */           cy[ir] = ce.r;
/* 14373 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy) {
/* 14377 */         Cdouble ct = new Cdouble();
/* 14378 */         int n1 = cx.length / 2;
/* 14379 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14380 */           ct.r = cx[ir];
/* 14381 */           ct.i = cx[ii];
/* 14382 */           Cdouble ce = Cdouble.exp(ct);
/* 14383 */           cy[ir] = ce.r;
/* 14384 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */     };
/* 14388 */   private static ComplexToComplex _clog = new ComplexToComplex() {
/*       */       void apply(float[] cx, float[] cy) {
/* 14390 */         Cfloat ct = new Cfloat();
/* 14391 */         int n1 = cx.length / 2;
/* 14392 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14393 */           ct.r = cx[ir];
/* 14394 */           ct.i = cx[ii];
/* 14395 */           Cfloat ce = Cfloat.log(ct);
/* 14396 */           cy[ir] = ce.r;
/* 14397 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy) {
/* 14401 */         Cdouble ct = new Cdouble();
/* 14402 */         int n1 = cx.length / 2;
/* 14403 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14404 */           ct.r = cx[ir];
/* 14405 */           ct.i = cx[ii];
/* 14406 */           Cdouble ce = Cdouble.log(ct);
/* 14407 */           cy[ir] = ce.r;
/* 14408 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */     };
/* 14412 */   private static ComplexToComplex _clog10 = new ComplexToComplex() {
/*       */       void apply(float[] cx, float[] cy) {
/* 14414 */         Cfloat ct = new Cfloat();
/* 14415 */         int n1 = cx.length / 2;
/* 14416 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14417 */           ct.r = cx[ir];
/* 14418 */           ct.i = cx[ii];
/* 14419 */           Cfloat ce = Cfloat.log10(ct);
/* 14420 */           cy[ir] = ce.r;
/* 14421 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] cy) {
/* 14425 */         Cdouble ct = new Cdouble();
/* 14426 */         int n1 = cx.length / 2;
/* 14427 */         for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14428 */           ct.r = cx[ir];
/* 14429 */           ct.i = cx[ii];
/* 14430 */           Cdouble ce = Cdouble.log10(ct);
/* 14431 */           cy[ir] = ce.r;
/* 14432 */           cy[ii] = ce.i;
/*       */         } 
/*       */       }
/*       */     };
/*       */   
/*       */   public static float[] cpow(float[] cx, float ra) {
/* 14438 */     int n1 = cx.length / 2;
/* 14439 */     float[] cy = new float[2 * n1];
/* 14440 */     cpow(cx, ra, cy);
/* 14441 */     return cy;
/*       */   }
/*       */   public static float[][] cpow(float[][] cx, float ra) {
/* 14444 */     int n2 = cx.length;
/* 14445 */     float[][] cy = new float[n2][];
/* 14446 */     for (int i2 = 0; i2 < n2; i2++)
/* 14447 */       cy[i2] = cpow(cx[i2], ra); 
/* 14448 */     return cy;
/*       */   }
/*       */   public static float[][][] cpow(float[][][] cx, float ra) {
/* 14451 */     int n3 = cx.length;
/* 14452 */     float[][][] cy = new float[n3][][];
/* 14453 */     for (int i3 = 0; i3 < n3; i3++)
/* 14454 */       cy[i3] = cpow(cx[i3], ra); 
/* 14455 */     return cy;
/*       */   }
/*       */   public static void cpow(float[] cx, float ra, float[] cy) {
/* 14458 */     Cfloat ct = new Cfloat();
/* 14459 */     int n1 = cx.length / 2;
/* 14460 */     for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14461 */       ct.r = cx[ir];
/* 14462 */       ct.i = cx[ii];
/* 14463 */       Cfloat ce = Cfloat.pow(ct, ra);
/* 14464 */       cy[ir] = ce.r;
/* 14465 */       cy[ii] = ce.i;
/*       */     } 
/*       */   }
/*       */   public static void cpow(float[][] cx, float ra, float[][] cy) {
/* 14469 */     int n2 = cx.length;
/* 14470 */     for (int i2 = 0; i2 < n2; i2++)
/* 14471 */       cpow(cx[i2], ra, cy[i2]); 
/*       */   }
/*       */   public static void cpow(float[][][] cx, float ra, float[][][] cy) {
/* 14474 */     int n3 = cx.length;
/* 14475 */     for (int i3 = 0; i3 < n3; i3++)
/* 14476 */       cpow(cx[i3], ra, cy[i3]); 
/*       */   }
/*       */   public static float[] cpow(float[] cx, Cfloat ca) {
/* 14479 */     int n1 = cx.length / 2;
/* 14480 */     float[] cy = new float[2 * n1];
/* 14481 */     cpow(cx, ca, cy);
/* 14482 */     return cy;
/*       */   }
/*       */   public static float[][] cpow(float[][] cx, Cfloat ca) {
/* 14485 */     int n2 = cx.length;
/* 14486 */     float[][] cy = new float[n2][];
/* 14487 */     for (int i2 = 0; i2 < n2; i2++)
/* 14488 */       cy[i2] = cpow(cx[i2], ca); 
/* 14489 */     return cy;
/*       */   }
/*       */   public static float[][][] cpow(float[][][] cx, Cfloat ca) {
/* 14492 */     int n3 = cx.length;
/* 14493 */     float[][][] cy = new float[n3][][];
/* 14494 */     for (int i3 = 0; i3 < n3; i3++)
/* 14495 */       cy[i3] = cpow(cx[i3], ca); 
/* 14496 */     return cy;
/*       */   }
/*       */   public static void cpow(float[] cx, Cfloat ca, float[] cy) {
/* 14499 */     Cfloat ct = new Cfloat();
/* 14500 */     int n1 = cx.length / 2;
/* 14501 */     for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14502 */       ct.r = cx[ir];
/* 14503 */       ct.i = cx[ii];
/* 14504 */       Cfloat ce = Cfloat.pow(ct, ca);
/* 14505 */       cy[ir] = ce.r;
/* 14506 */       cy[ii] = ce.i;
/*       */     } 
/*       */   }
/*       */   public static void cpow(float[][] cx, Cfloat ca, float[][] cy) {
/* 14510 */     int n2 = cx.length;
/* 14511 */     for (int i2 = 0; i2 < n2; i2++)
/* 14512 */       cpow(cx[i2], ca, cy[i2]); 
/*       */   }
/*       */   public static void cpow(float[][][] cx, Cfloat ca, float[][][] cy) {
/* 14515 */     int n3 = cx.length;
/* 14516 */     for (int i3 = 0; i3 < n3; i3++)
/* 14517 */       cpow(cx[i3], ca, cy[i3]); 
/*       */   }
/*       */   public static double[] cpow(double[] cx, double ra) {
/* 14520 */     int n1 = cx.length / 2;
/* 14521 */     double[] cy = new double[2 * n1];
/* 14522 */     cpow(cx, ra, cy);
/* 14523 */     return cy;
/*       */   }
/*       */   public static double[][] cpow(double[][] cx, double ra) {
/* 14526 */     int n2 = cx.length;
/* 14527 */     double[][] cy = new double[n2][];
/* 14528 */     for (int i2 = 0; i2 < n2; i2++)
/* 14529 */       cy[i2] = cpow(cx[i2], ra); 
/* 14530 */     return cy;
/*       */   }
/*       */   public static double[][][] cpow(double[][][] cx, double ra) {
/* 14533 */     int n3 = cx.length;
/* 14534 */     double[][][] cy = new double[n3][][];
/* 14535 */     for (int i3 = 0; i3 < n3; i3++)
/* 14536 */       cy[i3] = cpow(cx[i3], ra); 
/* 14537 */     return cy;
/*       */   }
/*       */   public static void cpow(double[] cx, double ra, double[] cy) {
/* 14540 */     Cdouble ct = new Cdouble();
/* 14541 */     int n1 = cx.length / 2;
/* 14542 */     for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14543 */       ct.r = cx[ir];
/* 14544 */       ct.i = cx[ii];
/* 14545 */       Cdouble ce = Cdouble.pow(ct, ra);
/* 14546 */       cy[ir] = ce.r;
/* 14547 */       cy[ii] = ce.i;
/*       */     } 
/*       */   }
/*       */   public static void cpow(double[][] cx, double ra, double[][] cy) {
/* 14551 */     int n2 = cx.length;
/* 14552 */     for (int i2 = 0; i2 < n2; i2++)
/* 14553 */       cpow(cx[i2], ra, cy[i2]); 
/*       */   }
/*       */   public static void cpow(double[][][] cx, double ra, double[][][] cy) {
/* 14556 */     int n3 = cx.length;
/* 14557 */     for (int i3 = 0; i3 < n3; i3++)
/* 14558 */       cpow(cx[i3], ra, cy[i3]); 
/*       */   }
/*       */   public static double[] cpow(double[] cx, Cdouble ca) {
/* 14561 */     int n1 = cx.length / 2;
/* 14562 */     double[] cy = new double[2 * n1];
/* 14563 */     cpow(cx, ca, cy);
/* 14564 */     return cy;
/*       */   }
/*       */   public static double[][] cpow(double[][] cx, Cdouble ca) {
/* 14567 */     int n2 = cx.length;
/* 14568 */     double[][] cy = new double[n2][];
/* 14569 */     for (int i2 = 0; i2 < n2; i2++)
/* 14570 */       cy[i2] = cpow(cx[i2], ca); 
/* 14571 */     return cy;
/*       */   }
/*       */   public static double[][][] cpow(double[][][] cx, Cdouble ca) {
/* 14574 */     int n3 = cx.length;
/* 14575 */     double[][][] cy = new double[n3][][];
/* 14576 */     for (int i3 = 0; i3 < n3; i3++)
/* 14577 */       cy[i3] = cpow(cx[i3], ca); 
/* 14578 */     return cy;
/*       */   }
/*       */   public static void cpow(double[] cx, Cdouble ca, double[] cy) {
/* 14581 */     Cdouble ct = new Cdouble();
/* 14582 */     int n1 = cx.length / 2;
/* 14583 */     for (int ir = 0, ii = 1, nn = 2 * n1; ir < nn; ir += 2, ii += 2) {
/* 14584 */       ct.r = cx[ir];
/* 14585 */       ct.i = cx[ii];
/* 14586 */       Cdouble ce = Cdouble.pow(ct, ca);
/* 14587 */       cy[ir] = ce.r;
/* 14588 */       cy[ii] = ce.i;
/*       */     } 
/*       */   }
/*       */   public static void cpow(double[][] cx, Cdouble ca, double[][] cy) {
/* 14592 */     int n2 = cx.length;
/* 14593 */     for (int i2 = 0; i2 < n2; i2++)
/* 14594 */       cpow(cx[i2], ca, cy[i2]); 
/*       */   }
/*       */   public static void cpow(double[][][] cx, Cdouble ca, double[][][] cy) {
/* 14597 */     int n3 = cx.length;
/* 14598 */     for (int i3 = 0; i3 < n3; i3++) {
/* 14599 */       cpow(cx[i3], ca, cy[i3]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] creal(float[] cx) {
/* 14606 */     return _creal.apply(cx);
/*       */   }
/*       */   public static float[][] creal(float[][] cx) {
/* 14609 */     return _creal.apply(cx);
/*       */   }
/*       */   public static float[][][] creal(float[][][] cx) {
/* 14612 */     return _creal.apply(cx);
/*       */   }
/*       */   public static void creal(float[] cx, float[] cy) {
/* 14615 */     _creal.apply(cx, cy);
/*       */   }
/*       */   public static void creal(float[][] cx, float[][] cy) {
/* 14618 */     _creal.apply(cx, cy);
/*       */   }
/*       */   public static void creal(float[][][] cx, float[][][] cy) {
/* 14621 */     _creal.apply(cx, cy);
/*       */   }
/*       */   public static float[] cimag(float[] cx) {
/* 14624 */     return _cimag.apply(cx);
/*       */   }
/*       */   public static float[][] cimag(float[][] cx) {
/* 14627 */     return _cimag.apply(cx);
/*       */   }
/*       */   public static float[][][] cimag(float[][][] cx) {
/* 14630 */     return _cimag.apply(cx);
/*       */   }
/*       */   public static void cimag(float[] cx, float[] cy) {
/* 14633 */     _cimag.apply(cx, cy);
/*       */   }
/*       */   public static void cimag(float[][] cx, float[][] cy) {
/* 14636 */     _cimag.apply(cx, cy);
/*       */   }
/*       */   public static void cimag(float[][][] cx, float[][][] cy) {
/* 14639 */     _cimag.apply(cx, cy);
/*       */   }
/*       */   public static float[] cabs(float[] cx) {
/* 14642 */     return _cabs.apply(cx);
/*       */   }
/*       */   public static float[][] cabs(float[][] cx) {
/* 14645 */     return _cabs.apply(cx);
/*       */   }
/*       */   public static float[][][] cabs(float[][][] cx) {
/* 14648 */     return _cabs.apply(cx);
/*       */   }
/*       */   public static void cabs(float[] cx, float[] cy) {
/* 14651 */     _cabs.apply(cx, cy);
/*       */   }
/*       */   public static void cabs(float[][] cx, float[][] cy) {
/* 14654 */     _cabs.apply(cx, cy);
/*       */   }
/*       */   public static void cabs(float[][][] cx, float[][][] cy) {
/* 14657 */     _cabs.apply(cx, cy);
/*       */   }
/*       */   public static float[] carg(float[] cx) {
/* 14660 */     return _carg.apply(cx);
/*       */   }
/*       */   public static float[][] carg(float[][] cx) {
/* 14663 */     return _carg.apply(cx);
/*       */   }
/*       */   public static float[][][] carg(float[][][] cx) {
/* 14666 */     return _carg.apply(cx);
/*       */   }
/*       */   public static void carg(float[] cx, float[] cy) {
/* 14669 */     _carg.apply(cx, cy);
/*       */   }
/*       */   public static void carg(float[][] cx, float[][] cy) {
/* 14672 */     _carg.apply(cx, cy);
/*       */   }
/*       */   public static void carg(float[][][] cx, float[][][] cy) {
/* 14675 */     _carg.apply(cx, cy);
/*       */   }
/*       */   public static float[] cnorm(float[] cx) {
/* 14678 */     return _cnorm.apply(cx);
/*       */   }
/*       */   public static float[][] cnorm(float[][] cx) {
/* 14681 */     return _cnorm.apply(cx);
/*       */   }
/*       */   public static float[][][] cnorm(float[][][] cx) {
/* 14684 */     return _cnorm.apply(cx);
/*       */   }
/*       */   public static void cnorm(float[] cx, float[] cy) {
/* 14687 */     _cnorm.apply(cx, cy);
/*       */   }
/*       */   public static void cnorm(float[][] cx, float[][] cy) {
/* 14690 */     _cnorm.apply(cx, cy);
/*       */   }
/*       */   public static void cnorm(float[][][] cx, float[][][] cy) {
/* 14693 */     _cnorm.apply(cx, cy);
/*       */   }
/*       */   public static double[] creal(double[] cx) {
/* 14696 */     return _creal.apply(cx);
/*       */   }
/*       */   public static double[][] creal(double[][] cx) {
/* 14699 */     return _creal.apply(cx);
/*       */   }
/*       */   public static double[][][] creal(double[][][] cx) {
/* 14702 */     return _creal.apply(cx);
/*       */   }
/*       */   public static void creal(double[] cx, double[] cy) {
/* 14705 */     _creal.apply(cx, cy);
/*       */   }
/*       */   public static void creal(double[][] cx, double[][] cy) {
/* 14708 */     _creal.apply(cx, cy);
/*       */   }
/*       */   public static void creal(double[][][] cx, double[][][] cy) {
/* 14711 */     _creal.apply(cx, cy);
/*       */   }
/*       */   public static double[] cimag(double[] cx) {
/* 14714 */     return _cimag.apply(cx);
/*       */   }
/*       */   public static double[][] cimag(double[][] cx) {
/* 14717 */     return _cimag.apply(cx);
/*       */   }
/*       */   public static double[][][] cimag(double[][][] cx) {
/* 14720 */     return _cimag.apply(cx);
/*       */   }
/*       */   public static void cimag(double[] cx, double[] cy) {
/* 14723 */     _cimag.apply(cx, cy);
/*       */   }
/*       */   public static void cimag(double[][] cx, double[][] cy) {
/* 14726 */     _cimag.apply(cx, cy);
/*       */   }
/*       */   public static void cimag(double[][][] cx, double[][][] cy) {
/* 14729 */     _cimag.apply(cx, cy);
/*       */   }
/*       */   public static double[] cabs(double[] cx) {
/* 14732 */     return _cabs.apply(cx);
/*       */   }
/*       */   public static double[][] cabs(double[][] cx) {
/* 14735 */     return _cabs.apply(cx);
/*       */   }
/*       */   public static double[][][] cabs(double[][][] cx) {
/* 14738 */     return _cabs.apply(cx);
/*       */   }
/*       */   public static void cabs(double[] cx, double[] cy) {
/* 14741 */     _cabs.apply(cx, cy);
/*       */   }
/*       */   public static void cabs(double[][] cx, double[][] cy) {
/* 14744 */     _cabs.apply(cx, cy);
/*       */   }
/*       */   public static void cabs(double[][][] cx, double[][][] cy) {
/* 14747 */     _cabs.apply(cx, cy);
/*       */   }
/*       */   public static double[] carg(double[] cx) {
/* 14750 */     return _carg.apply(cx);
/*       */   }
/*       */   public static double[][] carg(double[][] cx) {
/* 14753 */     return _carg.apply(cx);
/*       */   }
/*       */   public static double[][][] carg(double[][][] cx) {
/* 14756 */     return _carg.apply(cx);
/*       */   }
/*       */   public static void carg(double[] cx, double[] cy) {
/* 14759 */     _carg.apply(cx, cy);
/*       */   }
/*       */   public static void carg(double[][] cx, double[][] cy) {
/* 14762 */     _carg.apply(cx, cy);
/*       */   }
/*       */   public static void carg(double[][][] cx, double[][][] cy) {
/* 14765 */     _carg.apply(cx, cy);
/*       */   }
/*       */   public static double[] cnorm(double[] cx) {
/* 14768 */     return _cnorm.apply(cx);
/*       */   }
/*       */   public static double[][] cnorm(double[][] cx) {
/* 14771 */     return _cnorm.apply(cx);
/*       */   }
/*       */   public static double[][][] cnorm(double[][][] cx) {
/* 14774 */     return _cnorm.apply(cx);
/*       */   }
/*       */   public static void cnorm(double[] cx, double[] cy) {
/* 14777 */     _cnorm.apply(cx, cy);
/*       */   }
/*       */   public static void cnorm(double[][] cx, double[][] cy) {
/* 14780 */     _cnorm.apply(cx, cy);
/*       */   }
/*       */   public static void cnorm(double[][][] cx, double[][][] cy) {
/* 14783 */     _cnorm.apply(cx, cy);
/*       */   }
/*       */   private static abstract class ComplexToReal { private ComplexToReal() {}
/*       */     float[] apply(float[] cx) {
/* 14787 */       int n1 = cx.length / 2;
/* 14788 */       float[] cy = new float[n1];
/* 14789 */       apply(cx, cy);
/* 14790 */       return cy;
/*       */     }
/*       */     float[][] apply(float[][] cx) {
/* 14793 */       int n2 = cx.length;
/* 14794 */       float[][] cy = new float[n2][];
/* 14795 */       for (int i2 = 0; i2 < n2; i2++)
/* 14796 */         cy[i2] = apply(cx[i2]); 
/* 14797 */       return cy;
/*       */     }
/*       */     float[][][] apply(float[][][] cx) {
/* 14800 */       int n3 = cx.length;
/* 14801 */       float[][][] cy = new float[n3][][];
/* 14802 */       for (int i3 = 0; i3 < n3; i3++)
/* 14803 */         cy[i3] = apply(cx[i3]); 
/* 14804 */       return cy;
/*       */     }
/*       */     
/*       */     void apply(float[][] cx, float[][] ry) {
/* 14808 */       int n2 = cx.length;
/* 14809 */       for (int i2 = 0; i2 < n2; i2++)
/* 14810 */         apply(cx[i2], ry[i2]); 
/*       */     }
/*       */     void apply(float[][][] cx, float[][][] ry) {
/* 14813 */       int n3 = cx.length;
/* 14814 */       for (int i3 = 0; i3 < n3; i3++)
/* 14815 */         apply(cx[i3], ry[i3]); 
/*       */     }
/*       */     double[] apply(double[] cx) {
/* 14818 */       int n1 = cx.length / 2;
/* 14819 */       double[] cy = new double[n1];
/* 14820 */       apply(cx, cy);
/* 14821 */       return cy;
/*       */     }
/*       */     double[][] apply(double[][] cx) {
/* 14824 */       int n2 = cx.length;
/* 14825 */       double[][] cy = new double[n2][];
/* 14826 */       for (int i2 = 0; i2 < n2; i2++)
/* 14827 */         cy[i2] = apply(cx[i2]); 
/* 14828 */       return cy;
/*       */     }
/*       */     double[][][] apply(double[][][] cx) {
/* 14831 */       int n3 = cx.length;
/* 14832 */       double[][][] cy = new double[n3][][];
/* 14833 */       for (int i3 = 0; i3 < n3; i3++)
/* 14834 */         cy[i3] = apply(cx[i3]); 
/* 14835 */       return cy;
/*       */     }
/*       */     
/*       */     void apply(double[][] cx, double[][] ry) {
/* 14839 */       int n2 = cx.length;
/* 14840 */       for (int i2 = 0; i2 < n2; i2++)
/* 14841 */         apply(cx[i2], ry[i2]); 
/*       */     }
/*       */     void apply(double[][][] cx, double[][][] ry) {
/* 14844 */       int n3 = cx.length;
/* 14845 */       for (int i3 = 0; i3 < n3; i3++)
/* 14846 */         apply(cx[i3], ry[i3]); 
/*       */     } abstract void apply(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2);
/*       */     abstract void apply(double[] param1ArrayOfdouble1, double[] param1ArrayOfdouble2); }
/* 14849 */   private static ComplexToReal _creal = new ComplexToReal() {
/*       */       void apply(float[] cx, float[] ry) {
/* 14851 */         int n1 = cx.length / 2;
/* 14852 */         for (int i1 = 0, ir = 0; i1 < n1; i1++, ir += 2)
/* 14853 */           ry[i1] = cx[ir]; 
/*       */       }
/*       */       void apply(double[] cx, double[] ry) {
/* 14856 */         int n1 = cx.length / 2;
/* 14857 */         for (int i1 = 0, ir = 0; i1 < n1; i1++, ir += 2)
/* 14858 */           ry[i1] = cx[ir]; 
/*       */       }
/*       */     };
/* 14861 */   private static ComplexToReal _cimag = new ComplexToReal() {
/*       */       void apply(float[] cx, float[] ry) {
/* 14863 */         int n1 = cx.length / 2;
/* 14864 */         for (int i1 = 0, ii = 1; i1 < n1; i1++, ii += 2)
/* 14865 */           ry[i1] = cx[ii]; 
/*       */       }
/*       */       void apply(double[] cx, double[] ry) {
/* 14868 */         int n1 = cx.length / 2;
/* 14869 */         for (int i1 = 0, ii = 1; i1 < n1; i1++, ii += 2)
/* 14870 */           ry[i1] = cx[ii]; 
/*       */       }
/*       */     };
/* 14873 */   private static ComplexToReal _cabs = new ComplexToReal() {
/*       */       void apply(float[] cx, float[] ry) {
/* 14875 */         Cfloat ct = new Cfloat();
/* 14876 */         int n1 = cx.length / 2;
/* 14877 */         for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 14878 */           ct.r = cx[ir];
/* 14879 */           ct.i = cx[ii];
/* 14880 */           ry[i1] = Cfloat.abs(ct);
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] ry) {
/* 14884 */         Cdouble ct = new Cdouble();
/* 14885 */         int n1 = cx.length / 2;
/* 14886 */         for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 14887 */           ct.r = cx[ir];
/* 14888 */           ct.i = cx[ii];
/* 14889 */           ry[i1] = Cdouble.abs(ct);
/*       */         } 
/*       */       }
/*       */     };
/* 14893 */   private static ComplexToReal _carg = new ComplexToReal() {
/*       */       void apply(float[] cx, float[] ry) {
/* 14895 */         Cfloat ct = new Cfloat();
/* 14896 */         int n1 = cx.length / 2;
/* 14897 */         for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 14898 */           ct.r = cx[ir];
/* 14899 */           ct.i = cx[ii];
/* 14900 */           ry[i1] = Cfloat.arg(ct);
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] ry) {
/* 14904 */         Cdouble ct = new Cdouble();
/* 14905 */         int n1 = cx.length / 2;
/* 14906 */         for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 14907 */           ct.r = cx[ir];
/* 14908 */           ct.i = cx[ii];
/* 14909 */           ry[i1] = Cdouble.arg(ct);
/*       */         } 
/*       */       }
/*       */     };
/* 14913 */   private static ComplexToReal _cnorm = new ComplexToReal() {
/*       */       void apply(float[] cx, float[] ry) {
/* 14915 */         int n1 = cx.length / 2;
/* 14916 */         for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 14917 */           float cr = cx[ir];
/* 14918 */           float ci = cx[ii];
/* 14919 */           ry[i1] = cr * cr + ci * ci;
/*       */         } 
/*       */       }
/*       */       void apply(double[] cx, double[] ry) {
/* 14923 */         int n1 = cx.length / 2;
/* 14924 */         for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 14925 */           double cr = cx[ir];
/* 14926 */           double ci = cx[ii];
/* 14927 */           ry[i1] = cr * cr + ci * ci;
/*       */         } 
/*       */       }
/*       */     };
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] cmplx(float[] rx, float[] ry) {
/* 14936 */     return _cmplx.apply(rx, ry);
/*       */   }
/*       */   public static float[][] cmplx(float[][] rx, float[][] ry) {
/* 14939 */     return _cmplx.apply(rx, ry);
/*       */   }
/*       */   public static float[][][] cmplx(float[][][] rx, float[][][] ry) {
/* 14942 */     return _cmplx.apply(rx, ry);
/*       */   }
/*       */   public static void cmplx(float[] rx, float[] ry, float[] cz) {
/* 14945 */     _cmplx.apply(rx, ry, cz);
/*       */   }
/*       */   public static void cmplx(float[][] rx, float[][] ry, float[][] cz) {
/* 14948 */     _cmplx.apply(rx, ry, cz);
/*       */   }
/*       */   public static void cmplx(float[][][] rx, float[][][] ry, float[][][] cz) {
/* 14951 */     _cmplx.apply(rx, ry, cz);
/*       */   }
/*       */   public static float[] polar(float[] rx, float[] ry) {
/* 14954 */     return _polar.apply(rx, ry);
/*       */   }
/*       */   public static float[][] polar(float[][] rx, float[][] ry) {
/* 14957 */     return _polar.apply(rx, ry);
/*       */   }
/*       */   public static float[][][] polar(float[][][] rx, float[][][] ry) {
/* 14960 */     return _polar.apply(rx, ry);
/*       */   }
/*       */   public static void polar(float[] rx, float[] ry, float[] cz) {
/* 14963 */     _polar.apply(rx, ry, cz);
/*       */   }
/*       */   public static void polar(float[][] rx, float[][] ry, float[][] cz) {
/* 14966 */     _polar.apply(rx, ry, cz);
/*       */   }
/*       */   public static void polar(float[][][] rx, float[][][] ry, float[][][] cz) {
/* 14969 */     _polar.apply(rx, ry, cz);
/*       */   }
/*       */   public static double[] cmplx(double[] rx, double[] ry) {
/* 14972 */     return _cmplx.apply(rx, ry);
/*       */   }
/*       */   public static double[][] cmplx(double[][] rx, double[][] ry) {
/* 14975 */     return _cmplx.apply(rx, ry);
/*       */   }
/*       */   public static double[][][] cmplx(double[][][] rx, double[][][] ry) {
/* 14978 */     return _cmplx.apply(rx, ry);
/*       */   }
/*       */   public static void cmplx(double[] rx, double[] ry, double[] cz) {
/* 14981 */     _cmplx.apply(rx, ry, cz);
/*       */   }
/*       */   public static void cmplx(double[][] rx, double[][] ry, double[][] cz) {
/* 14984 */     _cmplx.apply(rx, ry, cz);
/*       */   }
/*       */   public static void cmplx(double[][][] rx, double[][][] ry, double[][][] cz) {
/* 14987 */     _cmplx.apply(rx, ry, cz);
/*       */   }
/*       */   public static double[] polar(double[] rx, double[] ry) {
/* 14990 */     return _polar.apply(rx, ry);
/*       */   }
/*       */   public static double[][] polar(double[][] rx, double[][] ry) {
/* 14993 */     return _polar.apply(rx, ry);
/*       */   }
/*       */   public static double[][][] polar(double[][][] rx, double[][][] ry) {
/* 14996 */     return _polar.apply(rx, ry);
/*       */   }
/*       */   public static void polar(double[] rx, double[] ry, double[] cz) {
/* 14999 */     _polar.apply(rx, ry, cz);
/*       */   }
/*       */   public static void polar(double[][] rx, double[][] ry, double[][] cz) {
/* 15002 */     _polar.apply(rx, ry, cz);
/*       */   }
/*       */   public static void polar(double[][][] rx, double[][][] ry, double[][][] cz) {
/* 15005 */     _polar.apply(rx, ry, cz);
/*       */   }
/*       */   private static abstract class RealToComplex { private RealToComplex() {}
/*       */     float[] apply(float[] rx, float[] ry) {
/* 15009 */       int n1 = rx.length;
/* 15010 */       float[] cz = new float[2 * n1];
/* 15011 */       apply(rx, ry, cz);
/* 15012 */       return cz;
/*       */     }
/*       */     float[][] apply(float[][] rx, float[][] ry) {
/* 15015 */       int n2 = rx.length;
/* 15016 */       float[][] cz = new float[n2][];
/* 15017 */       for (int i2 = 0; i2 < n2; i2++)
/* 15018 */         cz[i2] = apply(rx[i2], ry[i2]); 
/* 15019 */       return cz;
/*       */     }
/*       */     float[][][] apply(float[][][] rx, float[][][] ry) {
/* 15022 */       int n3 = rx.length;
/* 15023 */       float[][][] cz = new float[n3][][];
/* 15024 */       for (int i3 = 0; i3 < n3; i3++)
/* 15025 */         cz[i3] = apply(rx[i3], ry[i3]); 
/* 15026 */       return cz;
/*       */     }
/*       */     
/*       */     void apply(float[][] rx, float[][] ry, float[][] cz) {
/* 15030 */       int n2 = rx.length;
/* 15031 */       for (int i2 = 0; i2 < n2; i2++)
/* 15032 */         apply(rx[i2], ry[i2], cz[i2]); 
/*       */     }
/*       */     void apply(float[][][] rx, float[][][] ry, float[][][] cz) {
/* 15035 */       int n3 = cz.length;
/* 15036 */       for (int i3 = 0; i3 < n3; i3++)
/* 15037 */         apply(rx[i3], ry[i3], cz[i3]); 
/*       */     }
/*       */     double[] apply(double[] rx, double[] ry) {
/* 15040 */       int n1 = rx.length;
/* 15041 */       double[] cz = new double[2 * n1];
/* 15042 */       apply(rx, ry, cz);
/* 15043 */       return cz;
/*       */     }
/*       */     double[][] apply(double[][] rx, double[][] ry) {
/* 15046 */       int n2 = rx.length;
/* 15047 */       double[][] cz = new double[n2][];
/* 15048 */       for (int i2 = 0; i2 < n2; i2++)
/* 15049 */         cz[i2] = apply(rx[i2], ry[i2]); 
/* 15050 */       return cz;
/*       */     }
/*       */     double[][][] apply(double[][][] rx, double[][][] ry) {
/* 15053 */       int n3 = rx.length;
/* 15054 */       double[][][] cz = new double[n3][][];
/* 15055 */       for (int i3 = 0; i3 < n3; i3++)
/* 15056 */         cz[i3] = apply(rx[i3], ry[i3]); 
/* 15057 */       return cz;
/*       */     }
/*       */     
/*       */     void apply(double[][] rx, double[][] ry, double[][] cz) {
/* 15061 */       int n2 = rx.length;
/* 15062 */       for (int i2 = 0; i2 < n2; i2++)
/* 15063 */         apply(rx[i2], ry[i2], cz[i2]); 
/*       */     }
/*       */     void apply(double[][][] rx, double[][][] ry, double[][][] cz) {
/* 15066 */       int n3 = cz.length;
/* 15067 */       for (int i3 = 0; i3 < n3; i3++)
/* 15068 */         apply(rx[i3], ry[i3], cz[i3]); 
/*       */     } abstract void apply(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2, float[] param1ArrayOffloat3);
/*       */     abstract void apply(double[] param1ArrayOfdouble1, double[] param1ArrayOfdouble2, double[] param1ArrayOfdouble3); }
/* 15071 */   private static RealToComplex _cmplx = new RealToComplex() {
/*       */       void apply(float[] rx, float[] ry, float[] cz) {
/* 15073 */         int n1 = rx.length;
/* 15074 */         for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 15075 */           cz[ir] = rx[i1];
/* 15076 */           cz[ii] = ry[i1];
/*       */         } 
/*       */       }
/*       */       void apply(double[] rx, double[] ry, double[] cz) {
/* 15080 */         int n1 = rx.length;
/* 15081 */         for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 15082 */           cz[ir] = rx[i1];
/* 15083 */           cz[ii] = ry[i1];
/*       */         } 
/*       */       }
/*       */     };
/* 15087 */   private static RealToComplex _polar = new RealToComplex() {
/*       */       void apply(float[] rx, float[] ry, float[] cz) {
/* 15089 */         int n1 = rx.length;
/* 15090 */         for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 15091 */           float r = rx[i1];
/* 15092 */           float a = ry[i1];
/* 15093 */           cz[ir] = r * (float)Math.cos(a);
/* 15094 */           cz[ii] = r * (float)Math.sin(a);
/*       */         } 
/*       */       }
/*       */       void apply(double[] rx, double[] ry, double[] cz) {
/* 15098 */         int n1 = rx.length;
/* 15099 */         for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 15100 */           double r = rx[i1];
/* 15101 */           double a = ry[i1];
/* 15102 */           cz[ir] = r * Math.cos(a);
/* 15103 */           cz[ii] = r * Math.sin(a);
/*       */         } 
/*       */       }
/*       */     };
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Cfloat csum(float[] cx) {
/* 15112 */     int n1 = cx.length / 2;
/* 15113 */     float sr = 0.0F;
/* 15114 */     float si = 0.0F;
/* 15115 */     for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 15116 */       sr += cx[ir];
/* 15117 */       si += cx[ii];
/*       */     } 
/* 15119 */     return new Cfloat(sr, si);
/*       */   }
/*       */   public static Cfloat csum(float[][] cx) {
/* 15122 */     int n2 = cx.length;
/* 15123 */     Cfloat s = new Cfloat();
/* 15124 */     for (int i2 = 0; i2 < n2; i2++)
/* 15125 */       s.plusEquals(csum(cx[i2])); 
/* 15126 */     return s;
/*       */   }
/*       */   public static Cfloat csum(float[][][] cx) {
/* 15129 */     int n3 = cx.length;
/* 15130 */     Cfloat s = new Cfloat();
/* 15131 */     for (int i3 = 0; i3 < n3; i3++)
/* 15132 */       s.plusEquals(csum(cx[i3])); 
/* 15133 */     return s;
/*       */   }
/*       */   public static Cdouble csum(double[] cx) {
/* 15136 */     int n1 = cx.length / 2;
/* 15137 */     double sr = 0.0D;
/* 15138 */     double si = 0.0D;
/* 15139 */     for (int i1 = 0, ir = 0, ii = 1; i1 < n1; i1++, ir += 2, ii += 2) {
/* 15140 */       sr += cx[ir];
/* 15141 */       si += cx[ii];
/*       */     } 
/* 15143 */     return new Cdouble(sr, si);
/*       */   }
/*       */   public static Cdouble csum(double[][] cx) {
/* 15146 */     int n2 = cx.length;
/* 15147 */     Cdouble s = new Cdouble();
/* 15148 */     for (int i2 = 0; i2 < n2; i2++)
/* 15149 */       s.plusEquals(csum(cx[i2])); 
/* 15150 */     return s;
/*       */   }
/*       */   public static Cdouble csum(double[][][] cx) {
/* 15153 */     int n3 = cx.length;
/* 15154 */     Cdouble s = new Cdouble();
/* 15155 */     for (int i3 = 0; i3 < n3; i3++)
/* 15156 */       s.plusEquals(csum(cx[i3])); 
/* 15157 */     return s;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static float[] tofloat(byte[] rx) {
/* 15164 */     int n1 = rx.length;
/* 15165 */     float[] ry = new float[n1];
/* 15166 */     for (int i1 = 0; i1 < n1; i1++)
/* 15167 */       ry[i1] = rx[i1]; 
/* 15168 */     return ry;
/*       */   }
/*       */   public static float[][] tofloat(byte[][] rx) {
/* 15171 */     int n1 = (rx[0]).length;
/* 15172 */     int n2 = rx.length;
/* 15173 */     float[][] ry = new float[n2][n1];
/* 15174 */     for (int i2 = 0; i2 < n2; i2++) {
/* 15175 */       for (int i1 = 0; i1 < n1; i1++)
/* 15176 */         ry[i2][i1] = rx[i2][i1]; 
/* 15177 */     }  return ry;
/*       */   }
/*       */   public static float[][][] tofloat(byte[][][] rx) {
/* 15180 */     int n1 = (rx[0][0]).length;
/* 15181 */     int n2 = (rx[0]).length;
/* 15182 */     int n3 = rx.length;
/* 15183 */     float[][][] ry = new float[n3][n2][n1];
/* 15184 */     for (int i3 = 0; i3 < n3; i3++) {
/* 15185 */       for (int i2 = 0; i2 < n2; i2++)
/* 15186 */       { for (int i1 = 0; i1 < n1; i1++)
/* 15187 */           ry[i3][i2][i1] = rx[i3][i2][i1];  } 
/* 15188 */     }  return ry;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void dump(byte[] rx) {
/* 15195 */     ByteIterator li = new ByteIterator(rx);
/* 15196 */     String[] s = format(li);
/* 15197 */     dump(s);
/*       */   }
/*       */   public static void dump(byte[][] rx) {
/* 15200 */     ByteIterator li = new ByteIterator(rx);
/* 15201 */     String[] s = format(li);
/* 15202 */     int n2 = rx.length;
/* 15203 */     int[] n = new int[n2];
/* 15204 */     for (int i2 = 0; i2 < n2; i2++)
/* 15205 */       n[i2] = (rx[i2]).length; 
/* 15206 */     dump(n, s);
/*       */   }
/*       */   public static void dump(byte[][][] rx) {
/* 15209 */     ByteIterator li = new ByteIterator(rx);
/* 15210 */     String[] s = format(li);
/* 15211 */     int n3 = rx.length;
/* 15212 */     int[][] n = new int[n3][];
/* 15213 */     for (int i3 = 0; i3 < n3; i3++) {
/* 15214 */       int n2 = (rx[i3]).length;
/* 15215 */       n[i3] = new int[n2];
/* 15216 */       for (int i2 = 0; i2 < n2; i2++)
/* 15217 */         n[i3][i2] = (rx[i3][i2]).length; 
/*       */     } 
/* 15219 */     dump(n, s);
/*       */   }
/*       */   public static void dump(short[] rx) {
/* 15222 */     ShortIterator li = new ShortIterator(rx);
/* 15223 */     String[] s = format(li);
/* 15224 */     dump(s);
/*       */   }
/*       */   public static void dump(short[][] rx) {
/* 15227 */     ShortIterator li = new ShortIterator(rx);
/* 15228 */     String[] s = format(li);
/* 15229 */     int n2 = rx.length;
/* 15230 */     int[] n = new int[n2];
/* 15231 */     for (int i2 = 0; i2 < n2; i2++)
/* 15232 */       n[i2] = (rx[i2]).length; 
/* 15233 */     dump(n, s);
/*       */   }
/*       */   public static void dump(short[][][] rx) {
/* 15236 */     ShortIterator li = new ShortIterator(rx);
/* 15237 */     String[] s = format(li);
/* 15238 */     int n3 = rx.length;
/* 15239 */     int[][] n = new int[n3][];
/* 15240 */     for (int i3 = 0; i3 < n3; i3++) {
/* 15241 */       int n2 = (rx[i3]).length;
/* 15242 */       n[i3] = new int[n2];
/* 15243 */       for (int i2 = 0; i2 < n2; i2++)
/* 15244 */         n[i3][i2] = (rx[i3][i2]).length; 
/*       */     } 
/* 15246 */     dump(n, s);
/*       */   }
/*       */   public static void dump(int[] rx) {
/* 15249 */     IntIterator li = new IntIterator(rx);
/* 15250 */     String[] s = format(li);
/* 15251 */     dump(s);
/*       */   }
/*       */   public static void dump(int[][] rx) {
/* 15254 */     IntIterator li = new IntIterator(rx);
/* 15255 */     String[] s = format(li);
/* 15256 */     int n2 = rx.length;
/* 15257 */     int[] n = new int[n2];
/* 15258 */     for (int i2 = 0; i2 < n2; i2++)
/* 15259 */       n[i2] = (rx[i2]).length; 
/* 15260 */     dump(n, s);
/*       */   }
/*       */   public static void dump(int[][][] rx) {
/* 15263 */     IntIterator li = new IntIterator(rx);
/* 15264 */     String[] s = format(li);
/* 15265 */     int n3 = rx.length;
/* 15266 */     int[][] n = new int[n3][];
/* 15267 */     for (int i3 = 0; i3 < n3; i3++) {
/* 15268 */       int n2 = (rx[i3]).length;
/* 15269 */       n[i3] = new int[n2];
/* 15270 */       for (int i2 = 0; i2 < n2; i2++)
/* 15271 */         n[i3][i2] = (rx[i3][i2]).length; 
/*       */     } 
/* 15273 */     dump(n, s);
/*       */   }
/*       */   public static void dump(long[] rx) {
/* 15276 */     LongIterator li = new LongIterator(rx);
/* 15277 */     String[] s = format(li);
/* 15278 */     dump(s);
/*       */   }
/*       */   public static void dump(long[][] rx) {
/* 15281 */     LongIterator li = new LongIterator(rx);
/* 15282 */     String[] s = format(li);
/* 15283 */     int n2 = rx.length;
/* 15284 */     int[] n = new int[n2];
/* 15285 */     for (int i2 = 0; i2 < n2; i2++)
/* 15286 */       n[i2] = (rx[i2]).length; 
/* 15287 */     dump(n, s);
/*       */   }
/*       */   public static void dump(long[][][] rx) {
/* 15290 */     LongIterator li = new LongIterator(rx);
/* 15291 */     String[] s = format(li);
/* 15292 */     int n3 = rx.length;
/* 15293 */     int[][] n = new int[n3][];
/* 15294 */     for (int i3 = 0; i3 < n3; i3++) {
/* 15295 */       int n2 = (rx[i3]).length;
/* 15296 */       n[i3] = new int[n2];
/* 15297 */       for (int i2 = 0; i2 < n2; i2++)
/* 15298 */         n[i3][i2] = (rx[i3][i2]).length; 
/*       */     } 
/* 15300 */     dump(n, s);
/*       */   }
/*       */   public static void dump(float[] rx) {
/* 15303 */     FloatIterator di = new FloatIterator(rx);
/* 15304 */     String[] s = format(di);
/* 15305 */     dump(s);
/*       */   }
/*       */   public static void dump(float[][] rx) {
/* 15308 */     FloatIterator di = new FloatIterator(rx);
/* 15309 */     String[] s = format(di);
/* 15310 */     int n2 = rx.length;
/* 15311 */     int[] n = new int[n2];
/* 15312 */     for (int i2 = 0; i2 < n2; i2++)
/* 15313 */       n[i2] = (rx[i2]).length; 
/* 15314 */     dump(n, s);
/*       */   }
/*       */   public static void dump(float[][][] rx) {
/* 15317 */     FloatIterator di = new FloatIterator(rx);
/* 15318 */     String[] s = format(di);
/* 15319 */     int n3 = rx.length;
/* 15320 */     int[][] n = new int[n3][];
/* 15321 */     for (int i3 = 0; i3 < n3; i3++) {
/* 15322 */       int n2 = (rx[i3]).length;
/* 15323 */       n[i3] = new int[n2];
/* 15324 */       for (int i2 = 0; i2 < n2; i2++)
/* 15325 */         n[i3][i2] = (rx[i3][i2]).length; 
/*       */     } 
/* 15327 */     dump(n, s);
/*       */   }
/*       */   public static void cdump(float[] cx) {
/* 15330 */     FloatIterator di = new FloatIterator(cx);
/* 15331 */     String[] s = format(di);
/* 15332 */     cdump(s);
/*       */   }
/*       */   public static void cdump(float[][] cx) {
/* 15335 */     FloatIterator di = new FloatIterator(cx);
/* 15336 */     String[] s = format(di);
/* 15337 */     int n2 = cx.length;
/* 15338 */     int[] n = new int[n2];
/* 15339 */     for (int i2 = 0; i2 < n2; i2++)
/* 15340 */       n[i2] = (cx[i2]).length / 2; 
/* 15341 */     cdump(n, s);
/*       */   }
/*       */   public static void cdump(float[][][] cx) {
/* 15344 */     FloatIterator di = new FloatIterator(cx);
/* 15345 */     String[] s = format(di);
/* 15346 */     int n3 = cx.length;
/* 15347 */     int[][] n = new int[n3][];
/* 15348 */     for (int i3 = 0; i3 < n3; i3++) {
/* 15349 */       int n2 = (cx[i3]).length;
/* 15350 */       n[i3] = new int[n2];
/* 15351 */       for (int i2 = 0; i2 < n2; i2++)
/* 15352 */         n[i3][i2] = (cx[i3][i2]).length / 2; 
/*       */     } 
/* 15354 */     cdump(n, s);
/*       */   }
/*       */   public static void dump(double[] rx) {
/* 15357 */     DoubleIterator di = new DoubleIterator(rx);
/* 15358 */     String[] s = format(di);
/* 15359 */     dump(s);
/*       */   }
/*       */   public static void dump(double[][] rx) {
/* 15362 */     DoubleIterator di = new DoubleIterator(rx);
/* 15363 */     String[] s = format(di);
/* 15364 */     int n2 = rx.length;
/* 15365 */     int[] n = new int[n2];
/* 15366 */     for (int i2 = 0; i2 < n2; i2++)
/* 15367 */       n[i2] = (rx[i2]).length; 
/* 15368 */     dump(n, s);
/*       */   }
/*       */   public static void dump(double[][][] rx) {
/* 15371 */     DoubleIterator di = new DoubleIterator(rx);
/* 15372 */     String[] s = format(di);
/* 15373 */     int n3 = rx.length;
/* 15374 */     int[][] n = new int[n3][];
/* 15375 */     for (int i3 = 0; i3 < n3; i3++) {
/* 15376 */       int n2 = (rx[i3]).length;
/* 15377 */       n[i3] = new int[n2];
/* 15378 */       for (int i2 = 0; i2 < n2; i2++)
/* 15379 */         n[i3][i2] = (rx[i3][i2]).length; 
/*       */     } 
/* 15381 */     dump(n, s);
/*       */   }
/*       */   public static void cdump(double[] cx) {
/* 15384 */     DoubleIterator di = new DoubleIterator(cx);
/* 15385 */     String[] s = format(di);
/* 15386 */     cdump(s);
/*       */   }
/*       */   public static void cdump(double[][] cx) {
/* 15389 */     DoubleIterator di = new DoubleIterator(cx);
/* 15390 */     String[] s = format(di);
/* 15391 */     int n2 = cx.length;
/* 15392 */     int[] n = new int[n2];
/* 15393 */     for (int i2 = 0; i2 < n2; i2++)
/* 15394 */       n[i2] = (cx[i2]).length / 2; 
/* 15395 */     cdump(n, s);
/*       */   }
/*       */   public static void cdump(double[][][] cx) {
/* 15398 */     DoubleIterator di = new DoubleIterator(cx);
/* 15399 */     String[] s = format(di);
/* 15400 */     int n3 = cx.length;
/* 15401 */     int[][] n = new int[n3][];
/* 15402 */     for (int i3 = 0; i3 < n3; i3++) {
/* 15403 */       int n2 = (cx[i3]).length;
/* 15404 */       n[i3] = new int[n2];
/* 15405 */       for (int i2 = 0; i2 < n2; i2++)
/* 15406 */         n[i3][i2] = (cx[i3][i2]).length / 2; 
/*       */     } 
/* 15408 */     cdump(n, s);
/*       */   }
/*       */   private static int maxlen(String[] s) {
/* 15411 */     int max = 0;
/* 15412 */     int n = s.length;
/* 15413 */     for (int i = 0; i < n; i++) {
/* 15414 */       int len = s[i].length();
/* 15415 */       if (max < len)
/* 15416 */         max = len; 
/*       */     } 
/* 15418 */     return max;
/*       */   }
/*       */   private static void dump(String[] s) {
/* 15421 */     int max = maxlen(s);
/* 15422 */     int n1 = s.length;
/* 15423 */     String format = "%" + max + "s";
/* 15424 */     System.out.print("{");
/* 15425 */     int ncol = 78 / (max + 2);
/* 15426 */     int nrow = 1 + (n1 - 1) / ncol;
/* 15427 */     if (nrow > 1 && ncol >= 5) {
/* 15428 */       ncol = ncol / 5 * 5;
/* 15429 */       nrow = 1 + (n1 - 1) / ncol;
/*       */     } 
/* 15431 */     for (int irow = 0, i1 = 0; irow < nrow; irow++) {
/* 15432 */       for (int icol = 0; icol < ncol && i1 < n1; icol++, i1++) {
/* 15433 */         System.out.printf(format, new Object[] { s[i1] });
/* 15434 */         if (i1 < n1 - 1)
/* 15435 */           System.out.print(", "); 
/*       */       } 
/* 15437 */       if (i1 < n1) {
/* 15438 */         System.out.println();
/* 15439 */         System.out.print(" ");
/*       */       } else {
/* 15441 */         System.out.println("}");
/*       */       } 
/*       */     } 
/*       */   }
/*       */   private static void dump(int[] n, String[] s) {
/* 15446 */     int max = maxlen(s);
/* 15447 */     int n2 = n.length;
/* 15448 */     String format = "%" + max + "s";
/* 15449 */     System.out.print("{{");
/* 15450 */     int ncol = 77 / (max + 2);
/* 15451 */     if (ncol >= 5)
/* 15452 */       ncol = ncol / 5 * 5; 
/* 15453 */     for (int i2 = 0, i = 0; i2 < n2; i2++) {
/* 15454 */       int n1 = n[i2];
/* 15455 */       int nrow = 1 + (n1 - 1) / ncol;
/* 15456 */       if (i2 > 0)
/* 15457 */         System.out.print(" {"); 
/* 15458 */       for (int irow = 0, i1 = 0; irow < nrow; irow++) {
/* 15459 */         for (int icol = 0; icol < ncol && i1 < n1; icol++, i1++, i++) {
/* 15460 */           System.out.printf(format, new Object[] { s[i] });
/* 15461 */           if (i1 < n1 - 1)
/* 15462 */             System.out.print(", "); 
/*       */         } 
/* 15464 */         if (i1 < n1) {
/* 15465 */           System.out.println();
/* 15466 */           System.out.print("  ");
/*       */         }
/* 15468 */         else if (i2 < n2 - 1) {
/* 15469 */           System.out.println("},");
/*       */         } else {
/* 15471 */           System.out.println("}}");
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */   
/*       */   private static void dump(int[][] n, String[] s) {
/* 15478 */     int max = maxlen(s);
/* 15479 */     int n3 = n.length;
/* 15480 */     String format = "%" + max + "s";
/* 15481 */     System.out.print("{{{");
/* 15482 */     int ncol = 76 / (max + 2);
/* 15483 */     if (ncol >= 5)
/* 15484 */       ncol = ncol / 5 * 5; 
/* 15485 */     for (int i3 = 0, i = 0; i3 < n3; i3++) {
/* 15486 */       if (i3 > 0)
/* 15487 */         System.out.print(" {{"); 
/* 15488 */       int n2 = (n[i3]).length;
/* 15489 */       for (int i2 = 0; i2 < n2; i2++) {
/* 15490 */         int n1 = n[i3][i2];
/* 15491 */         int nrow = 1 + (n1 - 1) / ncol;
/* 15492 */         if (i2 > 0)
/* 15493 */           System.out.print("  {"); 
/* 15494 */         for (int irow = 0, i1 = 0; irow < nrow; irow++) {
/* 15495 */           for (int icol = 0; icol < ncol && i1 < n1; icol++, i1++, i++) {
/* 15496 */             System.out.printf(format, new Object[] { s[i] });
/* 15497 */             if (i1 < n1 - 1)
/* 15498 */               System.out.print(", "); 
/*       */           } 
/* 15500 */           if (i1 < n1) {
/* 15501 */             System.out.println();
/* 15502 */             System.out.print("   ");
/*       */           }
/* 15504 */           else if (i2 < n2 - 1) {
/* 15505 */             System.out.println("},");
/* 15506 */           } else if (i3 < n3 - 1) {
/* 15507 */             System.out.println("}},");
/*       */           } else {
/* 15509 */             System.out.println("}}}");
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */   
/*       */   private static void format(String sr, String si, StringBuilder sb) {
/* 15517 */     sb.delete(0, sb.length());
/* 15518 */     sb.append('(');
/* 15519 */     if (sr.charAt(0) == ' ') {
/* 15520 */       sb.append(sr.substring(1, sr.length()));
/*       */     } else {
/* 15522 */       sb.append(sr);
/*       */     } 
/* 15524 */     if (si.charAt(0) == ' ') {
/* 15525 */       sb.append('+');
/* 15526 */       sb.append(si.substring(1, si.length()));
/*       */     } else {
/* 15528 */       sb.append(si);
/*       */     } 
/* 15530 */     sb.append('i');
/* 15531 */     sb.append(')');
/*       */   }
/*       */   private static void cdump(String[] s) {
/* 15534 */     int max = 2 * maxlen(s) + 3;
/* 15535 */     StringBuilder sb = new StringBuilder(max);
/* 15536 */     int n1 = s.length / 2;
/* 15537 */     String format = "%" + max + "s";
/* 15538 */     System.out.print("{");
/* 15539 */     int ncol = 78 / (max + 2);
/* 15540 */     int nrow = 1 + (n1 - 1) / ncol;
/* 15541 */     if (nrow > 1 && ncol >= 5) {
/* 15542 */       ncol = ncol / 5 * 5;
/* 15543 */       nrow = 1 + (n1 - 1) / ncol;
/*       */     } 
/* 15545 */     for (int irow = 0, i1 = 0, ir = 0, ii = 1; irow < nrow; irow++) {
/* 15546 */       for (int icol = 0; icol < ncol && i1 < n1; icol++, i1++, ir += 2, ii += 2) {
/* 15547 */         format(s[ir], s[ii], sb);
/* 15548 */         System.out.printf(format, new Object[] { sb });
/* 15549 */         if (i1 < n1 - 1)
/* 15550 */           System.out.print(", "); 
/*       */       } 
/* 15552 */       if (i1 < n1) {
/* 15553 */         System.out.println();
/* 15554 */         System.out.print(" ");
/*       */       } else {
/* 15556 */         System.out.println("}");
/*       */       } 
/*       */     } 
/*       */   }
/*       */   private static void cdump(int[] n, String[] s) {
/* 15561 */     int max = 2 * maxlen(s) + 3;
/* 15562 */     StringBuilder sb = new StringBuilder(max);
/* 15563 */     int n2 = n.length;
/* 15564 */     String format = "%" + max + "s";
/* 15565 */     System.out.print("{{");
/* 15566 */     int ncol = 77 / (max + 2);
/* 15567 */     if (ncol >= 5)
/* 15568 */       ncol = ncol / 5 * 5; 
/* 15569 */     for (int i2 = 0, ir = 0, ii = 1; i2 < n2; i2++) {
/* 15570 */       int n1 = n[i2];
/* 15571 */       int nrow = 1 + (n1 - 1) / ncol;
/* 15572 */       if (i2 > 0)
/* 15573 */         System.out.print(" {"); 
/* 15574 */       for (int irow = 0, i1 = 0; irow < nrow; irow++) {
/* 15575 */         for (int icol = 0; icol < ncol && i1 < n1; icol++, i1++, ir += 2, ii += 2) {
/* 15576 */           format(s[ir], s[ii], sb);
/* 15577 */           System.out.printf(format, new Object[] { sb });
/* 15578 */           if (i1 < n1 - 1)
/* 15579 */             System.out.print(", "); 
/*       */         } 
/* 15581 */         if (i1 < n1) {
/* 15582 */           System.out.println();
/* 15583 */           System.out.print("  ");
/*       */         }
/* 15585 */         else if (i2 < n2 - 1) {
/* 15586 */           System.out.println("},");
/*       */         } else {
/* 15588 */           System.out.println("}}");
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */   
/*       */   private static void cdump(int[][] n, String[] s) {
/* 15595 */     int max = 2 * maxlen(s) + 3;
/* 15596 */     StringBuilder sb = new StringBuilder(max);
/* 15597 */     int n3 = n.length;
/* 15598 */     String format = "%" + max + "s";
/* 15599 */     System.out.print("{{{");
/* 15600 */     int ncol = 76 / (max + 2);
/* 15601 */     if (ncol >= 5)
/* 15602 */       ncol = ncol / 5 * 5; 
/* 15603 */     for (int i3 = 0, ir = 0, ii = 1; i3 < n3; i3++) {
/* 15604 */       if (i3 > 0)
/* 15605 */         System.out.print(" {{"); 
/* 15606 */       int n2 = (n[i3]).length;
/* 15607 */       for (int i2 = 0; i2 < n2; i2++) {
/* 15608 */         int n1 = n[i3][i2];
/* 15609 */         int nrow = 1 + (n1 - 1) / ncol;
/* 15610 */         if (i2 > 0)
/* 15611 */           System.out.print("  {"); 
/* 15612 */         for (int irow = 0, i1 = 0; irow < nrow; irow++) {
/* 15613 */           for (int icol = 0; icol < ncol && i1 < n1; icol++, i1++, ir += 2, ii += 2) {
/* 15614 */             format(s[ir], s[ii], sb);
/* 15615 */             System.out.printf(format, new Object[] { sb });
/* 15616 */             if (i1 < n1 - 1)
/* 15617 */               System.out.print(", "); 
/*       */           } 
/* 15619 */           if (i1 < n1) {
/* 15620 */             System.out.println();
/* 15621 */             System.out.print("   ");
/*       */           }
/* 15623 */           else if (i2 < n2 - 1) {
/* 15624 */             System.out.println("},");
/* 15625 */           } else if (i3 < n3 - 1) {
/* 15626 */             System.out.println("}},");
/*       */           } else {
/* 15628 */             System.out.println("}}}");
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */   private static class ByteIterator { private byte[] _a1; private byte[][] _a2; private byte[][][] _a3; private int _n; private int _i; private int _i1; private int _i2; private int _i3;
/*       */     
/*       */     ByteIterator(byte[] a) {
/* 15637 */       this._n = a.length;
/* 15638 */       this._i = 0;
/* 15639 */       this._i1 = 0;
/* 15640 */       this._a1 = a;
/*       */     }
/*       */     ByteIterator(byte[][] a) {
/* 15643 */       this._n = 0;
/* 15644 */       int n2 = a.length;
/* 15645 */       for (int i2 = 0; i2 < n2; i2++)
/* 15646 */         this._n += (a[i2]).length; 
/* 15647 */       this._i = 0;
/* 15648 */       this._i1 = 0;
/* 15649 */       this._i2 = 0;
/* 15650 */       this._a2 = a;
/*       */     }
/*       */     ByteIterator(byte[][][] a) {
/* 15653 */       this._n = 0;
/* 15654 */       int n3 = a.length;
/* 15655 */       for (int i3 = 0; i3 < n3; i3++) {
/* 15656 */         int n2 = (a[i3]).length;
/* 15657 */         for (int i2 = 0; i2 < n2; i2++)
/* 15658 */           this._n += (a[i3][i2]).length; 
/*       */       } 
/* 15660 */       this._i = 0;
/* 15661 */       this._i1 = 0;
/* 15662 */       this._i2 = 0;
/* 15663 */       this._i3 = 0;
/* 15664 */       this._a3 = a;
/*       */     }
/*       */     int count() {
/* 15667 */       return this._n;
/*       */     }
/*       */     byte get() {
/* 15670 */       assert this._i < this._n;
/* 15671 */       byte a = 0;
/* 15672 */       if (this._a1 != null) {
/* 15673 */         a = this._a1[this._i1++];
/* 15674 */       } else if (this._a2 != null) {
/* 15675 */         while (this._i1 >= (this._a2[this._i2]).length) {
/* 15676 */           this._i1 = 0;
/* 15677 */           this._i2++;
/*       */         } 
/* 15679 */         a = this._a2[this._i2][this._i1++];
/* 15680 */       } else if (this._a3 != null) {
/* 15681 */         while (this._i1 >= (this._a3[this._i3][this._i2]).length) {
/* 15682 */           this._i1 = 0;
/* 15683 */           this._i2++;
/* 15684 */           while (this._i2 >= (this._a3[this._i3]).length) {
/* 15685 */             this._i1 = 0;
/* 15686 */             this._i2 = 0;
/* 15687 */             this._i3++;
/*       */           } 
/*       */         } 
/* 15690 */         a = this._a3[this._i3][this._i2][this._i1++];
/*       */       } 
/* 15692 */       this._i++;
/* 15693 */       return a;
/*       */     }
/*       */     void reset() {
/* 15696 */       this._i = this._i1 = this._i2 = this._i3 = 0;
/*       */     } }
/*       */   private static class ShortIterator { private short[] _a1; private short[][] _a2; private short[][][] _a3; private int _n;
/*       */     private int _i;
/*       */     private int _i1;
/*       */     private int _i2;
/*       */     private int _i3;
/*       */     
/*       */     ShortIterator(short[] a) {
/* 15705 */       this._n = a.length;
/* 15706 */       this._i = 0;
/* 15707 */       this._i1 = 0;
/* 15708 */       this._a1 = a;
/*       */     }
/*       */     ShortIterator(short[][] a) {
/* 15711 */       this._n = 0;
/* 15712 */       int n2 = a.length;
/* 15713 */       for (int i2 = 0; i2 < n2; i2++)
/* 15714 */         this._n += (a[i2]).length; 
/* 15715 */       this._i = 0;
/* 15716 */       this._i1 = 0;
/* 15717 */       this._i2 = 0;
/* 15718 */       this._a2 = a;
/*       */     }
/*       */     ShortIterator(short[][][] a) {
/* 15721 */       this._n = 0;
/* 15722 */       int n3 = a.length;
/* 15723 */       for (int i3 = 0; i3 < n3; i3++) {
/* 15724 */         int n2 = (a[i3]).length;
/* 15725 */         for (int i2 = 0; i2 < n2; i2++)
/* 15726 */           this._n += (a[i3][i2]).length; 
/*       */       } 
/* 15728 */       this._i = 0;
/* 15729 */       this._i1 = 0;
/* 15730 */       this._i2 = 0;
/* 15731 */       this._i3 = 0;
/* 15732 */       this._a3 = a;
/*       */     }
/*       */     int count() {
/* 15735 */       return this._n;
/*       */     }
/*       */     short get() {
/* 15738 */       assert this._i < this._n;
/* 15739 */       short a = 0;
/* 15740 */       if (this._a1 != null) {
/* 15741 */         a = this._a1[this._i1++];
/* 15742 */       } else if (this._a2 != null) {
/* 15743 */         while (this._i1 >= (this._a2[this._i2]).length) {
/* 15744 */           this._i1 = 0;
/* 15745 */           this._i2++;
/*       */         } 
/* 15747 */         a = this._a2[this._i2][this._i1++];
/* 15748 */       } else if (this._a3 != null) {
/* 15749 */         while (this._i1 >= (this._a3[this._i3][this._i2]).length) {
/* 15750 */           this._i1 = 0;
/* 15751 */           this._i2++;
/* 15752 */           while (this._i2 >= (this._a3[this._i3]).length) {
/* 15753 */             this._i1 = 0;
/* 15754 */             this._i2 = 0;
/* 15755 */             this._i3++;
/*       */           } 
/*       */         } 
/* 15758 */         a = this._a3[this._i3][this._i2][this._i1++];
/*       */       } 
/* 15760 */       this._i++;
/* 15761 */       return a;
/*       */     }
/*       */     void reset() {
/* 15764 */       this._i = this._i1 = this._i2 = this._i3 = 0;
/*       */     } }
/*       */   private static class IntIterator { private int[] _a1; private int[][] _a2; private int[][][] _a3; private int _n;
/*       */     private int _i;
/*       */     private int _i1;
/*       */     private int _i2;
/*       */     private int _i3;
/*       */     
/*       */     IntIterator(int[] a) {
/* 15773 */       this._n = a.length;
/* 15774 */       this._i = 0;
/* 15775 */       this._i1 = 0;
/* 15776 */       this._a1 = a;
/*       */     }
/*       */     IntIterator(int[][] a) {
/* 15779 */       this._n = 0;
/* 15780 */       int n2 = a.length;
/* 15781 */       for (int i2 = 0; i2 < n2; i2++)
/* 15782 */         this._n += (a[i2]).length; 
/* 15783 */       this._i = 0;
/* 15784 */       this._i1 = 0;
/* 15785 */       this._i2 = 0;
/* 15786 */       this._a2 = a;
/*       */     }
/*       */     IntIterator(int[][][] a) {
/* 15789 */       this._n = 0;
/* 15790 */       int n3 = a.length;
/* 15791 */       for (int i3 = 0; i3 < n3; i3++) {
/* 15792 */         int n2 = (a[i3]).length;
/* 15793 */         for (int i2 = 0; i2 < n2; i2++)
/* 15794 */           this._n += (a[i3][i2]).length; 
/*       */       } 
/* 15796 */       this._i = 0;
/* 15797 */       this._i1 = 0;
/* 15798 */       this._i2 = 0;
/* 15799 */       this._i3 = 0;
/* 15800 */       this._a3 = a;
/*       */     }
/*       */     int count() {
/* 15803 */       return this._n;
/*       */     }
/*       */     int get() {
/* 15806 */       assert this._i < this._n;
/* 15807 */       int a = 0;
/* 15808 */       if (this._a1 != null) {
/* 15809 */         a = this._a1[this._i1++];
/* 15810 */       } else if (this._a2 != null) {
/* 15811 */         while (this._i1 >= (this._a2[this._i2]).length) {
/* 15812 */           this._i1 = 0;
/* 15813 */           this._i2++;
/*       */         } 
/* 15815 */         a = this._a2[this._i2][this._i1++];
/* 15816 */       } else if (this._a3 != null) {
/* 15817 */         while (this._i1 >= (this._a3[this._i3][this._i2]).length) {
/* 15818 */           this._i1 = 0;
/* 15819 */           this._i2++;
/* 15820 */           while (this._i2 >= (this._a3[this._i3]).length) {
/* 15821 */             this._i1 = 0;
/* 15822 */             this._i2 = 0;
/* 15823 */             this._i3++;
/*       */           } 
/*       */         } 
/* 15826 */         a = this._a3[this._i3][this._i2][this._i1++];
/*       */       } 
/* 15828 */       this._i++;
/* 15829 */       return a;
/*       */     }
/*       */     void reset() {
/* 15832 */       this._i = this._i1 = this._i2 = this._i3 = 0;
/*       */     } }
/*       */   private static class LongIterator { private long[] _a1; private long[][] _a2; private long[][][] _a3; private int _n;
/*       */     private int _i;
/*       */     private int _i1;
/*       */     private int _i2;
/*       */     private int _i3;
/*       */     
/*       */     LongIterator(long[] a) {
/* 15841 */       this._n = a.length;
/* 15842 */       this._i = 0;
/* 15843 */       this._i1 = 0;
/* 15844 */       this._a1 = a;
/*       */     }
/*       */     LongIterator(long[][] a) {
/* 15847 */       this._n = 0;
/* 15848 */       int n2 = a.length;
/* 15849 */       for (int i2 = 0; i2 < n2; i2++)
/* 15850 */         this._n += (a[i2]).length; 
/* 15851 */       this._i = 0;
/* 15852 */       this._i1 = 0;
/* 15853 */       this._i2 = 0;
/* 15854 */       this._a2 = a;
/*       */     }
/*       */     LongIterator(long[][][] a) {
/* 15857 */       this._n = 0;
/* 15858 */       int n3 = a.length;
/* 15859 */       for (int i3 = 0; i3 < n3; i3++) {
/* 15860 */         int n2 = (a[i3]).length;
/* 15861 */         for (int i2 = 0; i2 < n2; i2++)
/* 15862 */           this._n += (a[i3][i2]).length; 
/*       */       } 
/* 15864 */       this._i = 0;
/* 15865 */       this._i1 = 0;
/* 15866 */       this._i2 = 0;
/* 15867 */       this._i3 = 0;
/* 15868 */       this._a3 = a;
/*       */     }
/*       */     int count() {
/* 15871 */       return this._n;
/*       */     }
/*       */     long get() {
/* 15874 */       assert this._i < this._n;
/* 15875 */       long a = 0L;
/* 15876 */       if (this._a1 != null) {
/* 15877 */         a = this._a1[this._i1++];
/* 15878 */       } else if (this._a2 != null) {
/* 15879 */         while (this._i1 >= (this._a2[this._i2]).length) {
/* 15880 */           this._i1 = 0;
/* 15881 */           this._i2++;
/*       */         } 
/* 15883 */         a = this._a2[this._i2][this._i1++];
/* 15884 */       } else if (this._a3 != null) {
/* 15885 */         while (this._i1 >= (this._a3[this._i3][this._i2]).length) {
/* 15886 */           this._i1 = 0;
/* 15887 */           this._i2++;
/* 15888 */           while (this._i2 >= (this._a3[this._i3]).length) {
/* 15889 */             this._i1 = 0;
/* 15890 */             this._i2 = 0;
/* 15891 */             this._i3++;
/*       */           } 
/*       */         } 
/* 15894 */         a = this._a3[this._i3][this._i2][this._i1++];
/*       */       } 
/* 15896 */       this._i++;
/* 15897 */       return a;
/*       */     }
/*       */     void reset() {
/* 15900 */       this._i = this._i1 = this._i2 = this._i3 = 0;
/*       */     } }
/*       */   private static class FloatIterator { private float[] _a1; private float[][] _a2; private float[][][] _a3; private int _n;
/*       */     private int _i;
/*       */     private int _i1;
/*       */     private int _i2;
/*       */     private int _i3;
/*       */     
/*       */     FloatIterator(float[] a) {
/* 15909 */       this._n = a.length;
/* 15910 */       this._i = 0;
/* 15911 */       this._i1 = 0;
/* 15912 */       this._a1 = a;
/*       */     }
/*       */     FloatIterator(float[][] a) {
/* 15915 */       this._n = 0;
/* 15916 */       int n2 = a.length;
/* 15917 */       for (int i2 = 0; i2 < n2; i2++)
/* 15918 */         this._n += (a[i2]).length; 
/* 15919 */       this._i = 0;
/* 15920 */       this._i1 = 0;
/* 15921 */       this._i2 = 0;
/* 15922 */       this._a2 = a;
/*       */     }
/*       */     FloatIterator(float[][][] a) {
/* 15925 */       this._n = 0;
/* 15926 */       int n3 = a.length;
/* 15927 */       for (int i3 = 0; i3 < n3; i3++) {
/* 15928 */         int n2 = (a[i3]).length;
/* 15929 */         for (int i2 = 0; i2 < n2; i2++)
/* 15930 */           this._n += (a[i3][i2]).length; 
/*       */       } 
/* 15932 */       this._i = 0;
/* 15933 */       this._i1 = 0;
/* 15934 */       this._i2 = 0;
/* 15935 */       this._i3 = 0;
/* 15936 */       this._a3 = a;
/*       */     }
/*       */     int count() {
/* 15939 */       return this._n;
/*       */     }
/*       */     float get() {
/* 15942 */       assert this._i < this._n;
/* 15943 */       float a = 0.0F;
/* 15944 */       if (this._a1 != null) {
/* 15945 */         a = this._a1[this._i1++];
/* 15946 */       } else if (this._a2 != null) {
/* 15947 */         while (this._i1 >= (this._a2[this._i2]).length) {
/* 15948 */           this._i1 = 0;
/* 15949 */           this._i2++;
/*       */         } 
/* 15951 */         a = this._a2[this._i2][this._i1++];
/* 15952 */       } else if (this._a3 != null) {
/* 15953 */         while (this._i1 >= (this._a3[this._i3][this._i2]).length) {
/* 15954 */           this._i1 = 0;
/* 15955 */           this._i2++;
/* 15956 */           while (this._i2 >= (this._a3[this._i3]).length) {
/* 15957 */             this._i1 = 0;
/* 15958 */             this._i2 = 0;
/* 15959 */             this._i3++;
/*       */           } 
/*       */         } 
/* 15962 */         a = this._a3[this._i3][this._i2][this._i1++];
/*       */       } 
/* 15964 */       this._i++;
/* 15965 */       return a;
/*       */     }
/*       */     void reset() {
/* 15968 */       this._i = this._i1 = this._i2 = this._i3 = 0;
/*       */     } }
/*       */   private static class DoubleIterator { private double[] _a1; private double[][] _a2; private double[][][] _a3; private int _n;
/*       */     private int _i;
/*       */     private int _i1;
/*       */     private int _i2;
/*       */     private int _i3;
/*       */     
/*       */     DoubleIterator(double[] a) {
/* 15977 */       this._n = a.length;
/* 15978 */       this._i = 0;
/* 15979 */       this._i1 = 0;
/* 15980 */       this._a1 = a;
/*       */     }
/*       */     DoubleIterator(double[][] a) {
/* 15983 */       this._n = 0;
/* 15984 */       int n2 = a.length;
/* 15985 */       for (int i2 = 0; i2 < n2; i2++)
/* 15986 */         this._n += (a[i2]).length; 
/* 15987 */       this._i = 0;
/* 15988 */       this._i1 = 0;
/* 15989 */       this._i2 = 0;
/* 15990 */       this._a2 = a;
/*       */     }
/*       */     DoubleIterator(double[][][] a) {
/* 15993 */       this._n = 0;
/* 15994 */       int n3 = a.length;
/* 15995 */       for (int i3 = 0; i3 < n3; i3++) {
/* 15996 */         int n2 = (a[i3]).length;
/* 15997 */         for (int i2 = 0; i2 < n2; i2++)
/* 15998 */           this._n += (a[i3][i2]).length; 
/*       */       } 
/* 16000 */       this._i = 0;
/* 16001 */       this._i1 = 0;
/* 16002 */       this._i2 = 0;
/* 16003 */       this._i3 = 0;
/* 16004 */       this._a3 = a;
/*       */     }
/*       */     int count() {
/* 16007 */       return this._n;
/*       */     }
/*       */     double get() {
/* 16010 */       assert this._i < this._n;
/* 16011 */       double a = 0.0D;
/* 16012 */       if (this._a1 != null) {
/* 16013 */         a = this._a1[this._i1++];
/* 16014 */       } else if (this._a2 != null) {
/* 16015 */         while (this._i1 >= (this._a2[this._i2]).length) {
/* 16016 */           this._i1 = 0;
/* 16017 */           this._i2++;
/*       */         } 
/* 16019 */         a = this._a2[this._i2][this._i1++];
/* 16020 */       } else if (this._a3 != null) {
/* 16021 */         while (this._i1 >= (this._a3[this._i3][this._i2]).length) {
/* 16022 */           this._i1 = 0;
/* 16023 */           this._i2++;
/* 16024 */           while (this._i2 >= (this._a3[this._i3]).length) {
/* 16025 */             this._i1 = 0;
/* 16026 */             this._i2 = 0;
/* 16027 */             this._i3++;
/*       */           } 
/*       */         } 
/* 16030 */         a = this._a3[this._i3][this._i2][this._i1++];
/*       */       } 
/* 16032 */       this._i++;
/* 16033 */       return a;
/*       */     }
/*       */     void reset() {
/* 16036 */       this._i = this._i1 = this._i2 = this._i3 = 0;
/*       */     } }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private static String[] format(ByteIterator li) {
/* 16044 */     int n = li.count();
/* 16045 */     String[] s = new String[n];
/* 16046 */     for (int i = 0; i < n; i++) {
/* 16047 */       s[i] = String.format("% d", new Object[] { Byte.valueOf(li.get()) });
/* 16048 */     }  return s;
/*       */   }
/*       */   private static String[] format(ShortIterator li) {
/* 16051 */     int n = li.count();
/* 16052 */     String[] s = new String[n];
/* 16053 */     for (int i = 0; i < n; i++) {
/* 16054 */       s[i] = String.format("% d", new Object[] { Short.valueOf(li.get()) });
/* 16055 */     }  return s;
/*       */   }
/*       */   private static String[] format(IntIterator li) {
/* 16058 */     int n = li.count();
/* 16059 */     String[] s = new String[n];
/* 16060 */     for (int i = 0; i < n; i++) {
/* 16061 */       s[i] = String.format("% d", new Object[] { Integer.valueOf(li.get()) });
/* 16062 */     }  return s;
/*       */   }
/*       */   private static String[] format(LongIterator li) {
/* 16065 */     int n = li.count();
/* 16066 */     String[] s = new String[n];
/* 16067 */     for (int i = 0; i < n; i++) {
/* 16068 */       s[i] = String.format("% d", new Object[] { Long.valueOf(li.get()) });
/* 16069 */     }  return s;
/*       */   } private static String[] format(DoubleIterator di) {
/*       */     String f;
/* 16072 */     int pg = 6;
/* 16073 */     String fg = "% ." + pg + "g";
/* 16074 */     int pemax = -1;
/* 16075 */     int pfmax = -1;
/* 16076 */     int n = di.count();
/* 16077 */     for (int i = 0; i < n; i++) {
/* 16078 */       String str = String.format(fg, new Object[] { Double.valueOf(di.get()) });
/* 16079 */       str = clean(str);
/* 16080 */       int ls = str.length();
/* 16081 */       if (str.contains("e")) {
/* 16082 */         int pe = (ls > 7) ? (ls - 7) : 0;
/* 16083 */         if (pemax < pe)
/* 16084 */           pemax = pe; 
/*       */       } else {
/* 16086 */         int ip = str.indexOf('.');
/* 16087 */         int pf = (ip >= 0) ? (ls - 1 - ip) : 0;
/* 16088 */         if (pfmax < pf)
/* 16089 */           pfmax = pf; 
/*       */       } 
/*       */     } 
/* 16092 */     String[] s = new String[n];
/*       */     
/* 16094 */     if (pemax >= 0) {
/* 16095 */       if (pfmax > pg - 1)
/* 16096 */         pfmax = pg - 1; 
/* 16097 */       int pe = (pemax > pfmax) ? pemax : pfmax;
/* 16098 */       f = "% ." + pe + "e";
/*       */     } else {
/* 16100 */       int pf = pfmax;
/* 16101 */       f = "% ." + pf + "f";
/*       */     } 
/* 16103 */     di.reset();
/* 16104 */     for (int j = 0; j < n; j++) {
/* 16105 */       s[j] = String.format(f, new Object[] { Double.valueOf(di.get()) });
/* 16106 */     }  return s;
/*       */   } private static String[] format(FloatIterator di) {
/*       */     String f;
/* 16109 */     int pg = 6;
/* 16110 */     String fg = "% ." + pg + "g";
/* 16111 */     int pemax = -1;
/* 16112 */     int pfmax = -1;
/* 16113 */     int n = di.count();
/* 16114 */     for (int i = 0; i < n; i++) {
/* 16115 */       String str = String.format(fg, new Object[] { Float.valueOf(di.get()) });
/* 16116 */       str = clean(str);
/* 16117 */       int ls = str.length();
/* 16118 */       if (str.contains("e")) {
/* 16119 */         int pe = (ls > 7) ? (ls - 7) : 0;
/* 16120 */         if (pemax < pe)
/* 16121 */           pemax = pe; 
/*       */       } else {
/* 16123 */         int ip = str.indexOf('.');
/* 16124 */         int pf = (ip >= 0) ? (ls - 1 - ip) : 0;
/* 16125 */         if (pfmax < pf)
/* 16126 */           pfmax = pf; 
/*       */       } 
/*       */     } 
/* 16129 */     String[] s = new String[n];
/*       */     
/* 16131 */     if (pemax >= 0) {
/* 16132 */       if (pfmax > pg - 1)
/* 16133 */         pfmax = pg - 1; 
/* 16134 */       int pe = (pemax > pfmax) ? pemax : pfmax;
/* 16135 */       f = "% ." + pe + "e";
/*       */     } else {
/* 16137 */       int pf = pfmax;
/* 16138 */       f = "% ." + pf + "f";
/*       */     } 
/* 16140 */     di.reset();
/* 16141 */     for (int j = 0; j < n; j++) {
/* 16142 */       s[j] = String.format(f, new Object[] { Float.valueOf(di.get()) });
/* 16143 */     }  return s;
/*       */   }
/*       */   private static String clean(String s) {
/* 16146 */     int len = s.length();
/* 16147 */     int iend = s.indexOf('e');
/* 16148 */     if (iend < 0)
/* 16149 */       iend = s.indexOf('E'); 
/* 16150 */     if (iend < 0)
/* 16151 */       iend = len; 
/* 16152 */     int ibeg = iend;
/* 16153 */     if (s.indexOf('.') > 0) {
/* 16154 */       while (ibeg > 0 && s.charAt(ibeg - 1) == '0')
/* 16155 */         ibeg--; 
/* 16156 */       if (ibeg > 0 && s.charAt(ibeg - 1) == '.')
/* 16157 */         ibeg--; 
/*       */     } 
/* 16159 */     if (ibeg < iend) {
/* 16160 */       String sb = s.substring(0, ibeg);
/* 16161 */       s = (iend < len) ? (sb + s.substring(iend, len)) : sb;
/*       */     } 
/* 16163 */     return s;
/*       */   }
/*       */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/ArrayMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */