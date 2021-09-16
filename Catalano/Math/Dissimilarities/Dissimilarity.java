/*     */ package Catalano.Math.Dissimilarities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Dissimilarity
/*     */ {
/*     */   public static double Dice(int[] x, int[] y) {
/*  43 */     int tf = 0;
/*  44 */     int ft = 0;
/*  45 */     int tt = 0;
/*     */     
/*  47 */     for (int i = 0; i < x.length; i++) {
/*  48 */       if (x[i] == 1 && y[i] == 0) tf++; 
/*  49 */       if (x[i] == 0 && y[i] == 1) ft++; 
/*  50 */       if (x[i] == 1 && y[i] == 1) tt++;
/*     */     
/*     */     } 
/*  53 */     return (tf + ft) / (2 * tt + ft + tf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Jaccard(int[] x, int[] y) {
/*  64 */     int tf = 0;
/*  65 */     int ft = 0;
/*  66 */     int tt = 0;
/*     */     
/*  68 */     for (int i = 0; i < x.length; i++) {
/*  69 */       if (x[i] == 1 && y[i] == 0) tf++; 
/*  70 */       if (x[i] == 0 && y[i] == 1) ft++; 
/*  71 */       if (x[i] == 1 && y[i] == 1) tt++;
/*     */     
/*     */     } 
/*  74 */     return (tf + ft) / (tt + ft + tf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Kulsinsk(int[] x, int[] y) {
/*  85 */     int tf = 0;
/*  86 */     int ft = 0;
/*  87 */     int tt = 0;
/*     */     
/*  89 */     for (int i = 0; i < x.length; i++) {
/*  90 */       if (x[i] == 1 && y[i] == 0) tf++; 
/*  91 */       if (x[i] == 0 && y[i] == 1) ft++; 
/*  92 */       if (x[i] == 1 && y[i] == 1) tt++;
/*     */     
/*     */     } 
/*  95 */     return (tf + ft - tt + x.length) / (ft + tf + x.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Matching(int[] x, int[] y) {
/* 106 */     int tf = 0;
/* 107 */     int ft = 0;
/*     */     
/* 109 */     for (int i = 0; i < x.length; i++) {
/* 110 */       if (x[i] == 1 && y[i] == 0) tf++; 
/* 111 */       if (x[i] == 0 && y[i] == 1) ft++;
/*     */     
/*     */     } 
/* 114 */     return (tf + ft) / x.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double RogersTanimoto(int[] x, int[] y) {
/* 125 */     int tf = 0;
/* 126 */     int ft = 0;
/* 127 */     int tt = 0;
/* 128 */     int ff = 0;
/*     */     
/* 130 */     for (int i = 0; i < x.length; i++) {
/* 131 */       if (x[i] == 1 && y[i] == 0) tf++; 
/* 132 */       if (x[i] == 0 && y[i] == 1) ft++; 
/* 133 */       if (x[i] == 1 && y[i] == 1) tt++; 
/* 134 */       if (x[i] == 0 && y[i] == 0) ff++;
/*     */     
/*     */     } 
/* 137 */     int r = 2 * (tf + ft);
/* 138 */     return r / (tt + ff + r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double RusselRao(int[] x, int[] y) {
/* 149 */     int tt = 0;
/*     */     
/* 151 */     for (int i = 0; i < x.length; i++) {
/* 152 */       if (x[i] == 1 && y[i] == 1) tt++;
/*     */     
/*     */     } 
/* 155 */     return (x.length - tt) / x.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double SokalMichener(int[] x, int[] y) {
/* 166 */     int tf = 0;
/* 167 */     int ft = 0;
/* 168 */     int tt = 0;
/* 169 */     int ff = 0;
/*     */     
/* 171 */     for (int i = 0; i < x.length; i++) {
/* 172 */       if (x[i] == 1 && y[i] == 0) tf++; 
/* 173 */       if (x[i] == 0 && y[i] == 1) ft++; 
/* 174 */       if (x[i] == 1 && y[i] == 1) tt++; 
/* 175 */       if (x[i] == 0 && y[i] == 0) ff++;
/*     */     
/*     */     } 
/* 178 */     int r = 2 * (tf + ft);
/* 179 */     return r / (ff + tt + r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double SokalSneath(int[] x, int[] y) {
/* 190 */     int tf = 0;
/* 191 */     int ft = 0;
/* 192 */     int tt = 0;
/*     */     
/* 194 */     for (int i = 0; i < x.length; i++) {
/* 195 */       if (x[i] == 1 && y[i] == 0) tf++; 
/* 196 */       if (x[i] == 0 && y[i] == 1) ft++; 
/* 197 */       if (x[i] == 1 && y[i] == 1) tt++;
/*     */     
/*     */     } 
/* 200 */     int r = 2 * (tf + ft);
/* 201 */     return r / (tt + r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Yule(int[] x, int[] y) {
/* 212 */     int tf = 0;
/* 213 */     int ft = 0;
/* 214 */     int tt = 0;
/* 215 */     int ff = 0;
/*     */     
/* 217 */     for (int i = 0; i < x.length; i++) {
/* 218 */       if (x[i] == 1 && y[i] == 0) tf++; 
/* 219 */       if (x[i] == 0 && y[i] == 1) ft++; 
/* 220 */       if (x[i] == 1 && y[i] == 1) tt++; 
/* 221 */       if (x[i] == 0 && y[i] == 0) ff++;
/*     */     
/*     */     } 
/* 224 */     double r = (2 * (tf + ft));
/* 225 */     return r / ((tt + ff) + r / 2.0D);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Dissimilarities/Dissimilarity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */