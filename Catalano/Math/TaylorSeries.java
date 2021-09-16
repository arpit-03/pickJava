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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TaylorSeries
/*     */ {
/*     */   public static double Sin(double x, int nTerms) {
/*  45 */     if (nTerms < 2) return x; 
/*  46 */     if (nTerms == 2) {
/*  47 */       return x - x * x * x / 6.0D;
/*     */     }
/*     */ 
/*     */     
/*  51 */     double mult = x * x * x;
/*  52 */     double fact = 6.0D;
/*  53 */     double sign = 1.0D;
/*  54 */     int factS = 5;
/*  55 */     double result = x - mult / fact;
/*  56 */     for (int i = 3; i <= nTerms; i++) {
/*  57 */       mult *= x * x;
/*  58 */       fact *= (factS * (factS - 1));
/*  59 */       factS += 2;
/*  60 */       result += sign * mult / fact;
/*  61 */       sign *= -1.0D;
/*     */     } 
/*     */     
/*  64 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Cos(double x, int nTerms) {
/*  75 */     if (nTerms < 2) return 1.0D; 
/*  76 */     if (nTerms == 2) {
/*  77 */       return 1.0D - x * x / 2.0D;
/*     */     }
/*     */ 
/*     */     
/*  81 */     double mult = x * x;
/*  82 */     double fact = 2.0D;
/*  83 */     double sign = 1.0D;
/*  84 */     int factS = 4;
/*  85 */     double result = 1.0D - mult / fact;
/*  86 */     for (int i = 3; i <= nTerms; i++) {
/*  87 */       mult *= x * x;
/*  88 */       fact *= (factS * (factS - 1));
/*  89 */       factS += 2;
/*  90 */       result += sign * mult / fact;
/*  91 */       sign *= -1.0D;
/*     */     } 
/*     */     
/*  94 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Sinh(double x, int nTerms) {
/* 105 */     if (nTerms < 2) return x; 
/* 106 */     if (nTerms == 2) {
/* 107 */       return x + x * x * x / 6.0D;
/*     */     }
/*     */ 
/*     */     
/* 111 */     double mult = x * x * x;
/* 112 */     double fact = 6.0D;
/* 113 */     int factS = 5;
/* 114 */     double result = x + mult / fact;
/* 115 */     for (int i = 3; i <= nTerms; i++) {
/* 116 */       mult *= x * x;
/* 117 */       fact *= (factS * (factS - 1));
/* 118 */       factS += 2;
/* 119 */       result += mult / fact;
/*     */     } 
/*     */     
/* 122 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Cosh(double x, int nTerms) {
/* 133 */     if (nTerms < 2) return x; 
/* 134 */     if (nTerms == 2) {
/* 135 */       return 1.0D + x * x / 2.0D;
/*     */     }
/*     */ 
/*     */     
/* 139 */     double mult = x * x;
/* 140 */     double fact = 2.0D;
/* 141 */     int factS = 4;
/* 142 */     double result = 1.0D + mult / fact;
/* 143 */     for (int i = 3; i <= nTerms; i++) {
/* 144 */       mult *= x * x;
/* 145 */       fact *= (factS * (factS - 1));
/* 146 */       factS += 2;
/* 147 */       result += mult / fact;
/*     */     } 
/*     */     
/* 150 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Exp(double x, int nTerms) {
/* 161 */     if (nTerms < 2) return 1.0D + x; 
/* 162 */     if (nTerms == 2) {
/* 163 */       return 1.0D + x + x * x / 2.0D;
/*     */     }
/*     */ 
/*     */     
/* 167 */     double mult = x * x;
/* 168 */     double fact = 2.0D;
/* 169 */     double result = 1.0D + x + mult / fact;
/* 170 */     for (int i = 3; i <= nTerms; i++) {
/* 171 */       mult *= x;
/* 172 */       fact *= i;
/* 173 */       result += mult / fact;
/*     */     } 
/*     */     
/* 176 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/TaylorSeries.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */