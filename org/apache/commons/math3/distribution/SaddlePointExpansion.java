/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.special.Gamma;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ final class SaddlePointExpansion
/*     */ {
/*  48 */   private static final double HALF_LOG_2_PI = 0.5D * FastMath.log(6.283185307179586D);
/*     */ 
/*     */   
/*  51 */   private static final double[] EXACT_STIRLING_ERRORS = new double[] { 0.0D, 0.15342640972002736D, 0.08106146679532726D, 0.05481412105191765D, 0.0413406959554093D, 0.03316287351993629D, 0.02767792568499834D, 0.023746163656297496D, 0.020790672103765093D, 0.018488450532673187D, 0.016644691189821193D, 0.015134973221917378D, 0.013876128823070748D, 0.012810465242920227D, 0.01189670994589177D, 0.011104559758206917D, 0.010411265261972096D, 0.009799416126158804D, 0.009255462182712733D, 0.008768700134139386D, 0.00833056343336287D, 0.00793411456431402D, 0.007573675487951841D, 0.007244554301320383D, 0.00694284010720953D, 0.006665247032707682D, 0.006408994188004207D, 0.006171712263039458D, 0.0059513701127588475D, 0.0057462165130101155D, 0.005554733551962801D };
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
/*     */   static double getStirlingError(double z) {
/*     */     double ret;
/* 108 */     if (z < 15.0D) {
/* 109 */       double z2 = 2.0D * z;
/* 110 */       if (FastMath.floor(z2) == z2) {
/* 111 */         ret = EXACT_STIRLING_ERRORS[(int)z2];
/*     */       } else {
/* 113 */         ret = Gamma.logGamma(z + 1.0D) - (z + 0.5D) * FastMath.log(z) + z - HALF_LOG_2_PI;
/*     */       } 
/*     */     } else {
/*     */       
/* 117 */       double z2 = z * z;
/* 118 */       ret = (0.08333333333333333D - (0.002777777777777778D - (7.936507936507937E-4D - (5.952380952380953E-4D - 8.417508417508417E-4D / z2) / z2) / z2) / z2) / z;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     return ret;
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
/*     */ 
/*     */ 
/*     */   
/*     */   static double getDeviancePart(double x, double mu) {
/*     */     double ret;
/* 146 */     if (FastMath.abs(x - mu) < 0.1D * (x + mu)) {
/* 147 */       double d = x - mu;
/* 148 */       double v = d / (x + mu);
/* 149 */       double s1 = v * d;
/* 150 */       double s = Double.NaN;
/* 151 */       double ej = 2.0D * x * v;
/* 152 */       v *= v;
/* 153 */       int j = 1;
/* 154 */       while (s1 != s) {
/* 155 */         s = s1;
/* 156 */         ej *= v;
/* 157 */         s1 = s + ej / (j * 2 + 1);
/* 158 */         j++;
/*     */       } 
/* 160 */       ret = s1;
/*     */     } else {
/* 162 */       ret = x * FastMath.log(x / mu) + mu - x;
/*     */     } 
/* 164 */     return ret;
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
/*     */   static double logBinomialProbability(int x, int n, double p, double q) {
/*     */     double ret;
/* 179 */     if (x == 0) {
/* 180 */       if (p < 0.1D) {
/* 181 */         ret = -getDeviancePart(n, n * q) - n * p;
/*     */       } else {
/* 183 */         ret = n * FastMath.log(q);
/*     */       } 
/* 185 */     } else if (x == n) {
/* 186 */       if (q < 0.1D) {
/* 187 */         ret = -getDeviancePart(n, n * p) - n * q;
/*     */       } else {
/* 189 */         ret = n * FastMath.log(p);
/*     */       } 
/*     */     } else {
/* 192 */       ret = getStirlingError(n) - getStirlingError(x) - getStirlingError((n - x)) - getDeviancePart(x, n * p) - getDeviancePart((n - x), n * q);
/*     */ 
/*     */       
/* 195 */       double f = 6.283185307179586D * x * (n - x) / n;
/* 196 */       ret = -0.5D * FastMath.log(f) + ret;
/*     */     } 
/* 198 */     return ret;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/SaddlePointExpansion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */