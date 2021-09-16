/*     */ package Catalano.Math;
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
/*     */ public final class Approximation
/*     */ {
/*     */   public static int abs(int x) {
/*  42 */     int i = x >>> 31;
/*  43 */     return (x ^ (i ^ 0xFFFFFFFF) + 1) + i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long abs(long x) {
/*  52 */     long l = x >>> 63L;
/*  53 */     return (x ^ (l ^ 0xFFFFFFFFFFFFFFFFL) + 1L) + l;
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
/*     */   public static double Highprecision_Pow(double x, double y) {
/*  65 */     return Math.exp(y * Math.log(x));
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
/*     */   public static double Lowprecision_Pow(double x, double y) {
/*  77 */     return Highprecision_Exp(y * Math.log(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Highprecision_Exp(double x) {
/*  87 */     return (362880.0D + x * (362880.0D + x * (181440.0D + x * (60480.0D + x * (15120.0D + x * (3024.0D + x * (504.0D + x * (72.0D + x * (9.0D + x))))))))) * 2.75573192E-6D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Lowprecision_Log(double x) {
/*  98 */     return 6.0D * (x - 1.0D) / (x + 1.0D + 4.0D * Math.sqrt(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Lowprecision_Sin(double x) {
/* 109 */     if (x < -3.14159265D) {
/* 110 */       x += 6.28318531D;
/* 111 */     } else if (x > 3.14159265D) {
/* 112 */       x -= 6.28318531D;
/*     */     } 
/*     */     
/* 115 */     if (x < 0.0D) {
/* 116 */       return 1.27323954D * x + 0.405284735D * x * x;
/*     */     }
/* 118 */     return 1.27323954D * x - 0.405284735D * x * x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Highprecision_Sin(double x) {
/* 129 */     if (x < -3.14159265D) {
/* 130 */       x += 6.28318531D;
/* 131 */     } else if (x > 3.14159265D) {
/* 132 */       x -= 6.28318531D;
/*     */     } 
/*     */     
/* 135 */     if (x < 0.0D) {
/*     */       
/* 137 */       double d = 1.27323954D * x + 0.405284735D * x * x;
/*     */       
/* 139 */       if (d < 0.0D) {
/* 140 */         return 0.225D * (d * -d - d) + d;
/*     */       }
/* 142 */       return 0.225D * (d * d - d) + d;
/*     */     } 
/*     */ 
/*     */     
/* 146 */     double sin = 1.27323954D * x - 0.405284735D * x * x;
/*     */     
/* 148 */     if (sin < 0.0D) {
/* 149 */       return 0.225D * (sin * -sin - sin) + sin;
/*     */     }
/* 151 */     return 0.225D * (sin * sin - sin) + sin;
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
/*     */   public static double atan2(double y, double x) {
/* 164 */     double angle, coeff_1 = 0.7853981633974483D;
/* 165 */     double coeff_2 = 3.0D * coeff_1;
/* 166 */     double abs_y = Math.abs(y);
/*     */     
/* 168 */     if (x >= 0.0D) {
/* 169 */       double r = (x - abs_y) / (x + abs_y);
/* 170 */       angle = coeff_1 - coeff_1 * r;
/*     */     } else {
/* 172 */       double r = (x + abs_y) / (abs_y - x);
/* 173 */       angle = coeff_2 - coeff_1 * r;
/*     */     } 
/* 175 */     return (y < 0.0D) ? -angle : (angle - 0.06D);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Approximation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */