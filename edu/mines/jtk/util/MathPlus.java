/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MathPlus
/*     */ {
/*     */   public static final double E = 2.718281828459045D;
/*     */   public static final float FLT_E = 2.7182817F;
/*     */   public static final double DBL_E = 2.718281828459045D;
/*     */   public static final double PI = 3.141592653589793D;
/*     */   public static final float FLT_PI = 3.1415927F;
/*     */   public static final double DBL_PI = 3.141592653589793D;
/*     */   public static final float FLT_MAX = 3.4028235E38F;
/*     */   public static final float FLT_MIN = 1.4E-45F;
/*     */   public static final float FLT_EPSILON = 1.1920929E-7F;
/*     */   public static final double DBL_MAX = 1.7976931348623157E308D;
/*     */   public static final double DBL_MIN = 4.9E-324D;
/*     */   public static final double DBL_EPSILON = 2.220446049250313E-16D;
/*     */   
/*     */   public static float sin(float x) {
/* 100 */     return (float)Math.sin(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double sin(double x) {
/* 109 */     return Math.sin(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float cos(float x) {
/* 118 */     return (float)Math.cos(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double cos(double x) {
/* 127 */     return Math.cos(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float tan(float x) {
/* 136 */     return (float)Math.tan(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double tan(double x) {
/* 145 */     return Math.tan(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float asin(float x) {
/* 155 */     return (float)Math.asin(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double asin(double x) {
/* 165 */     return Math.asin(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float acos(float x) {
/* 175 */     return (float)Math.acos(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double acos(double x) {
/* 185 */     return Math.acos(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float atan(float x) {
/* 195 */     return (float)Math.atan(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double atan(double x) {
/* 205 */     return Math.atan(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float atan2(float y, float x) {
/* 216 */     return (float)Math.atan2(y, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double atan2(double y, double x) {
/* 227 */     return Math.atan2(y, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float toRadians(float angdeg) {
/* 236 */     return (float)Math.toRadians(angdeg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double toRadians(double angdeg) {
/* 245 */     return Math.toRadians(angdeg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float toDegrees(float angrad) {
/* 254 */     return (float)Math.toDegrees(angrad);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double toDegrees(double angrad) {
/* 263 */     return Math.toDegrees(angrad);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float exp(float x) {
/* 272 */     return (float)Math.exp(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double exp(double x) {
/* 281 */     return Math.exp(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float log(float x) {
/* 290 */     return (float)Math.log(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double log(double x) {
/* 299 */     return Math.log(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float log10(float x) {
/* 308 */     return (float)Math.log10(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double log10(double x) {
/* 317 */     return Math.log10(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float sqrt(float x) {
/* 326 */     return (float)Math.sqrt(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double sqrt(double x) {
/* 335 */     return Math.sqrt(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float pow(float x, float y) {
/* 345 */     return (float)Math.pow(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double pow(double x, double y) {
/* 355 */     return Math.pow(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float sinh(float x) {
/* 364 */     return (float)Math.sinh(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double sinh(double x) {
/* 373 */     return Math.sinh(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float cosh(float x) {
/* 382 */     return (float)Math.cosh(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double cosh(double x) {
/* 391 */     return Math.cosh(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float tanh(float x) {
/* 400 */     return (float)Math.tanh(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double tanh(double x) {
/* 409 */     return Math.tanh(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float ceil(float x) {
/* 419 */     return (float)Math.ceil(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double ceil(double x) {
/* 429 */     return Math.ceil(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float floor(float x) {
/* 439 */     return (float)Math.floor(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double floor(double x) {
/* 449 */     return Math.floor(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float rint(float x) {
/* 459 */     return (float)Math.rint(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double rint(double x) {
/* 469 */     return Math.rint(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int round(float x) {
/* 479 */     return Math.round(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long round(double x) {
/* 489 */     return Math.round(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float signum(float x) {
/* 500 */     return (x > 0.0F) ? 1.0F : ((x < 0.0F) ? -1.0F : 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double signum(double x) {
/* 511 */     return (x > 0.0D) ? 1.0D : ((x < 0.0D) ? -1.0D : 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int abs(int x) {
/* 520 */     return (x >= 0) ? x : -x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long abs(long x) {
/* 529 */     return (x >= 0L) ? x : -x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float abs(float x) {
/* 539 */     return (x >= 0.0F) ? x : -x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double abs(double x) {
/* 549 */     return (x >= 0.0D) ? x : -x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int max(int a, int b) {
/* 559 */     return (a >= b) ? a : b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int max(int a, int b, int c) {
/* 570 */     return max(a, max(b, c));
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
/*     */   public static int max(int a, int b, int c, int d) {
/* 582 */     return max(a, max(b, max(c, d)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long max(long a, long b) {
/* 592 */     return (a >= b) ? a : b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long max(long a, long b, long c) {
/* 603 */     return max(a, max(b, c));
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
/*     */   public static long max(long a, long b, long c, long d) {
/* 615 */     return max(a, max(b, max(c, d)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float max(float a, float b) {
/* 625 */     return (a >= b) ? a : b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float max(float a, float b, float c) {
/* 636 */     return max(a, max(b, c));
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
/*     */   public static float max(float a, float b, float c, float d) {
/* 648 */     return max(a, max(b, max(c, d)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double max(double a, double b) {
/* 658 */     return (a >= b) ? a : b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double max(double a, double b, double c) {
/* 669 */     return max(a, max(b, c));
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
/*     */   public static double max(double a, double b, double c, double d) {
/* 681 */     return max(a, max(b, max(c, d)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int min(int a, int b) {
/* 691 */     return (a <= b) ? a : b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int min(int a, int b, int c) {
/* 702 */     return min(a, min(b, c));
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
/*     */   public static int min(int a, int b, int c, int d) {
/* 714 */     return min(a, min(b, min(c, d)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long min(long a, long b) {
/* 724 */     return (a <= b) ? a : b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long min(long a, long b, long c) {
/* 735 */     return min(a, min(b, c));
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
/*     */   public static long min(long a, long b, long c, long d) {
/* 747 */     return min(a, min(b, min(c, d)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float min(float a, float b) {
/* 757 */     return (a <= b) ? a : b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float min(float a, float b, float c) {
/* 768 */     return min(a, min(b, c));
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
/*     */   public static float min(float a, float b, float c, float d) {
/* 780 */     return min(a, min(b, min(c, d)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double min(double a, double b) {
/* 790 */     return (a <= b) ? a : b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double min(double a, double b, double c) {
/* 801 */     return min(a, min(b, c));
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
/*     */   public static double min(double a, double b, double c, double d) {
/* 813 */     return min(a, min(b, min(c, d)));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/MathPlus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */