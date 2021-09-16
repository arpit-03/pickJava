/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LasserreVolume
/*     */ {
/*     */   private int _m;
/*     */   private int _n;
/*     */   private AbList _ab;
/*     */   private ArrayList<AbList> _abStack;
/*     */   
/*     */   public LasserreVolume(int n) {
/*  56 */     this._m = 0;
/*  57 */     this._n = n;
/*  58 */     this._abStack = new ArrayList<>(this._n);
/*  59 */     for (int i = 1; i <= n; i++) {
/*  60 */       this._ab = new AbList(i);
/*  61 */       this._abStack.add(this._ab);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHalfSpace(double a1, double b) {
/*  73 */     Check.state((this._n >= 1), "dimension >= 1");
/*  74 */     double[] ab = this._ab.add();
/*  75 */     for (int i = 1; i < this._n; i++)
/*  76 */       ab[i] = 0.0D; 
/*  77 */     ab[0] = a1;
/*  78 */     ab[this._n] = b;
/*  79 */     this._m++;
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
/*     */   public void addHalfSpace(double a1, double a2, double b) {
/*  91 */     Check.state((this._n >= 2), "dimension >= 2");
/*  92 */     double[] ab = this._ab.add();
/*  93 */     for (int i = 2; i < this._n; i++)
/*  94 */       ab[i] = 0.0D; 
/*  95 */     ab[0] = a1;
/*  96 */     ab[1] = a2;
/*  97 */     ab[this._n] = b;
/*  98 */     this._m++;
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
/*     */   public void addHalfSpace(double a1, double a2, double a3, double b) {
/* 111 */     Check.state((this._n >= 3), "dimension >= 3");
/* 112 */     double[] ab = this._ab.add();
/* 113 */     for (int i = 3; i < this._n; i++)
/* 114 */       ab[i] = 0.0D; 
/* 115 */     ab[0] = a1;
/* 116 */     ab[1] = a2;
/* 117 */     ab[2] = a3;
/* 118 */     ab[this._n] = b;
/* 119 */     this._m++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHalfSpace(double[] a, double b) {
/* 129 */     int n = a.length;
/* 130 */     if (n > this._n) n = this._n; 
/* 131 */     double[] ab = this._ab.add(); int i;
/* 132 */     for (i = n; i < this._n; i++)
/* 133 */       ab[i] = 0.0D; 
/* 134 */     for (i = 0; i < n; i++)
/* 135 */       ab[i] = a[i]; 
/* 136 */     ab[this._n] = b;
/* 137 */     this._m++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 144 */     this._ab.clear();
/* 145 */     this._m = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getVolume() {
/* 153 */     return volume(this._m, this._n);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AbList
/*     */     extends ArrayList<double[]>
/*     */   {
/*     */     private int _m;
/*     */     
/*     */     private int _n;
/*     */ 
/*     */     
/*     */     AbList(int n) {
/* 166 */       super(n + 1);
/* 167 */       this._m = 0;
/* 168 */       this._n = n;
/*     */     }
/*     */     public double[] add() {
/* 171 */       if (this._m == size())
/* 172 */         add(new double[this._n + 1]); 
/* 173 */       this._m++;
/* 174 */       return get(this._m - 1);
/*     */     }
/*     */     public void clear() {
/* 177 */       this._m = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     void dump() {
/* 182 */       System.out.println("m=" + this._m + " n=" + this._n);
/* 183 */       for (double[] ab : this) {
/* 184 */         for (double abi : ab)
/* 185 */           System.out.print(abi + " "); 
/* 186 */         System.out.println();
/*     */       } 
/*     */     }
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
/*     */   private double volume(int m, int n) {
/* 201 */     if (m <= n) {
/* 202 */       return Double.POSITIVE_INFINITY;
/*     */     }
/*     */     
/* 205 */     AbList abList = this._abStack.get(n - 1);
/*     */ 
/*     */     
/* 208 */     if (n == 1) {
/* 209 */       double xlower = Double.NEGATIVE_INFINITY;
/* 210 */       double xupper = Double.POSITIVE_INFINITY;
/* 211 */       for (int i = 0; i < m; i++) {
/* 212 */         double[] arow = abList.get(i);
/* 213 */         double aval = arow[0];
/* 214 */         double bval = arow[1];
/* 215 */         if (aval < 0.0D) {
/* 216 */           double x = bval / aval;
/* 217 */           if (x > xlower) xlower = x; 
/* 218 */         } else if (aval > 0.0D) {
/* 219 */           double x = bval / aval;
/* 220 */           if (x < xupper) xupper = x; 
/*     */         } 
/*     */       } 
/* 223 */       double len = xupper - xlower;
/* 224 */       if (len < 0.0D) len = 0.0D; 
/* 225 */       return len;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 230 */     double sum = 0.0D;
/*     */ 
/*     */     
/* 233 */     for (int irow = 0; irow < m; irow++) {
/* 234 */       double[] arow = abList.get(irow);
/* 235 */       double brow = arow[n];
/*     */ 
/*     */       
/* 238 */       if (brow != 0.0D) {
/*     */ 
/*     */ 
/*     */         
/* 242 */         int jpiv = 0;
/* 243 */         double amax = 0.0D;
/* 244 */         for (int j = 0; j < n; j++) {
/* 245 */           double a = arow[j];
/* 246 */           if (a < 0.0D) a = -a; 
/* 247 */           if (a > amax) {
/* 248 */             jpiv = j;
/* 249 */             amax = a;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 254 */         if (amax != 0.0D)
/*     */         
/*     */         { 
/*     */           
/* 258 */           AbList abNext = this._abStack.get(n - 2);
/* 259 */           abNext.clear();
/* 260 */           double spiv = 1.0D / arow[jpiv];
/* 261 */           for (int i = 0; i < m; i++) {
/* 262 */             if (i != irow) {
/* 263 */               double[] ai = abList.get(i);
/* 264 */               double[] ak = abNext.add();
/* 265 */               for (int k = 0, l = 0; k <= n; k++) {
/* 266 */                 if (k != jpiv) {
/* 267 */                   ak[l++] = ai[k] - spiv * ai[jpiv] * arow[k];
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/* 272 */           double vol = volume(m - 1, n - 1);
/*     */ 
/*     */           
/* 275 */           if (vol == Double.POSITIVE_INFINITY) {
/* 276 */             return vol;
/*     */           }
/*     */           
/* 279 */           sum += brow / amax * vol; } 
/*     */       } 
/* 281 */     }  return sum / n;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/LasserreVolume.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */