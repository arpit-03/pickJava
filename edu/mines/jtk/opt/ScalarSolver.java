/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
/*     */ import edu.mines.jtk.util.Monitor;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalarSolver
/*     */ {
/*  29 */   private static final double GOLD = 0.5D * (Math.sqrt(5.0D) - 1.0D);
/*  30 */   private static final Almost s_almost = new Almost();
/*     */   
/*  32 */   private Function _function = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] _doubleTemp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double solve(double scalarMin, double scalarMax, double okError, double okFraction, int numberIterations, Monitor monitor) {
/*     */     double xmin;
/*  78 */     if (monitor == null) {
/*  79 */       monitor = Monitor.NULL_MONITOR;
/*     */     }
/*  81 */     monitor.report(0.0D);
/*     */     
/*  83 */     int nter = numberIterations;
/*  84 */     if (nter < 6) {
/*  85 */       nter = 6;
/*     */     }
/*  87 */     double[] xs = { 0.0D, 0.25D, 0.75D, 1.0D };
/*  88 */     double[] fs = new double[4];
/*  89 */     for (int i = 0; i < fs.length; i++) {
/*  90 */       fs[i] = function(xs[i], scalarMin, scalarMax);
/*     */     }
/*  92 */     int iter = 4;
/*     */ 
/*     */     
/*  95 */     double error = 1.0D;
/*  96 */     double previousError = 1.0D;
/*     */     while (true) {
/*  98 */       monitor.report(iter / nter);
/*     */       
/* 100 */       int imin = sort(xs, fs);
/* 101 */       xmin = xs[imin];
/*     */ 
/*     */       
/* 104 */       double previousPreviousError = previousError;
/* 105 */       previousError = error;
/* 106 */       if (imin == 0) {
/* 107 */         error = xs[1] - xs[0];
/* 108 */       } else if (imin == 3) {
/* 109 */         error = xs[3] - xs[2];
/* 110 */       } else if (imin == 1 || imin == 2) {
/* 111 */         error = xs[imin + 1] - xs[imin - 1];
/*     */       } else {
/* 113 */         assert false : "impossible imin=" + imin;
/*     */       } 
/* 115 */       double fraction = Almost.FLOAT.divide(error, xmin, 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 126 */       if (iter >= nter || (error < okError && fraction < okFraction) || monitor.isCanceled()) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 132 */       double xnew = 3.4028234663852886E38D;
/* 133 */       if (imin == 0) {
/* 134 */         assert xs[imin] == 0.0D : "Left endpoint should be zero, not " + xs[imin];
/* 135 */         xnew = xs[1] * 0.1D;
/*     */       }
/* 137 */       else if (imin == 3) {
/* 138 */         assert xs[imin] == 1.0D : "Right endpoint should be one, not " + xs[imin];
/* 139 */         xnew = 1.0D - 0.1D * (1.0D - xs[2]);
/*     */       }
/* 141 */       else if (imin == 1 || imin == 2) {
/* 142 */         boolean goodConvergence = false;
/* 143 */         if (error < previousPreviousError * 0.501D) {
/*     */           
/*     */           try {
/*     */             
/* 147 */             xnew = minParabola(xs[imin - 1], fs[imin - 1], xs[imin], fs[imin], xs[imin + 1], fs[imin + 1]);
/*     */ 
/*     */ 
/*     */             
/* 151 */             goodConvergence = true;
/* 152 */           } catch (BadParabolaException e) {
/*     */             
/* 154 */             goodConvergence = false;
/*     */           } 
/*     */         }
/* 157 */         if (!goodConvergence)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 165 */           if (xs[imin] - xs[imin - 1] >= xs[imin + 1] - xs[imin]) {
/*     */ 
/*     */             
/* 168 */             xnew = xs[imin - 1] + GOLD * (xs[imin] - xs[imin - 1]);
/*     */           } else {
/*     */             
/* 171 */             xnew = xs[imin + 1] - GOLD * (xs[imin + 1] - xs[imin]);
/*     */           } 
/*     */         }
/*     */       } else {
/* 175 */         assert false : "Impossible imin=" + imin;
/*     */       } 
/*     */       
/* 178 */       assert xnew != 3.4028234663852886E38D : "bad xnew";
/*     */ 
/*     */       
/* 181 */       double fnew = 3.4028234663852886E38D;
/*     */       
/* 183 */       for (int j = 0; j < xs.length; j++) {
/* 184 */         if (Almost.FLOAT.equal(xnew, xs[j]))
/*     */         {
/* 186 */           fnew = fs[j];
/*     */         }
/*     */       } 
/* 189 */       if (fnew == 3.4028234663852886E38D) {
/* 190 */         fnew = function(xnew, scalarMin, scalarMax);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 195 */       if (imin < 2) {
/* 196 */         xs[3] = xnew;
/* 197 */         fs[3] = fnew;
/*     */       } else {
/*     */         
/* 200 */         xs[0] = xnew;
/* 201 */         fs[0] = fnew;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 206 */       iter++;
/*     */     } 
/*     */     
/* 209 */     assert xmin >= 0.0D && xmin <= 1.0D : "Impossible xmin=" + xmin;
/*     */     
/* 211 */     double result = scalarMin + xmin * (scalarMax - scalarMin);
/*     */     
/* 213 */     monitor.report(1.0D);
/* 214 */     return result;
/*     */   }
/*     */   public ScalarSolver(Function function) {
/* 217 */     this._doubleTemp = new double[4];
/*     */     this._function = function;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int sort(double[] xs, double[] fs) {
/* 227 */     assert xs.length == 4;
/* 228 */     int[] sortedX = (new IndexSorter(xs)).getSortedIndices();
/* 229 */     System.arraycopy(xs, 0, this._doubleTemp, 0, 4); int i;
/* 230 */     for (i = 0; i < xs.length; i++) {
/* 231 */       xs[i] = this._doubleTemp[sortedX[i]];
/*     */     }
/* 233 */     System.arraycopy(fs, 0, this._doubleTemp, 0, 4);
/* 234 */     for (i = 0; i < xs.length; i++) {
/* 235 */       fs[i] = this._doubleTemp[sortedX[i]];
/*     */     }
/* 237 */     int imin = 0;
/* 238 */     for (int j = 1; j < fs.length; j++) {
/* 239 */       if (fs[j] < fs[imin]) {
/* 240 */         imin = j;
/*     */       }
/*     */     } 
/* 243 */     return imin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double function(double x, double scalarMin, double scalarMax) {
/* 253 */     return function(scalarMin + x * (scalarMax - scalarMin));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double function(double scalar) {
/* 263 */     return this._function.function(scalar);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double minParabola(double x1, double f1, double x2, double f2, double x3, double f3) throws BadParabolaException, IllegalArgumentException {
/* 292 */     if (!Almost.FLOAT.le(x1, x2) || !Almost.FLOAT.le(x2, x3)) {
/* 293 */       throw new BadParabolaException("Violates x1 <= x2 <= x3: x1=" + x1 + " x2=" + x2 + " x3=" + x3);
/*     */     }
/*     */     
/* 296 */     if (Almost.FLOAT.equal(x1, x2)) {
/*     */       
/* 298 */       double result = x2 + 0.1D * (x3 - x2);
/*     */       
/* 300 */       return result;
/*     */     } 
/* 302 */     if (Almost.FLOAT.equal(x2, x3)) {
/*     */       
/* 304 */       double result = x1 + 0.9D * (x2 - x1);
/*     */       
/* 306 */       return result;
/*     */     } 
/* 308 */     if (!Almost.FLOAT.le(f2, f1) || !Almost.FLOAT.le(f2, f3)) {
/* 309 */       throw new BadParabolaException("Violates f(x2) <= f(x1), f(x2) <= f(x3): f1=" + f1 + " f2=" + f2 + " f3=" + f3);
/*     */     }
/*     */ 
/*     */     
/* 313 */     double xm = Almost.FLOAT.divide(x2 - x1, x3 - x1, 0.0D);
/* 314 */     if (xm < 0.001D || xm > 0.999D) {
/* 315 */       throw new BadParabolaException("Parabola is badly sampled x1=" + x1 + " x2=" + x2 + " x3=" + x3);
/*     */     }
/*     */     
/* 318 */     double a = Almost.FLOAT.divide((f3 - f1) * xm - f2 - f1, xm - xm * xm, 0.0D);
/* 319 */     double b = f3 - f1 - a;
/* 320 */     if (Almost.FLOAT.ge(a * b, 0.0D) || 0.5D * Math.abs(b) > Math.abs(a)) {
/* 321 */       throw new BadParabolaException("Poor numerical conditioning a=" + a + " b=" + b);
/*     */     }
/*     */     
/* 324 */     xm = Almost.FLOAT.divide(-0.5D * b, a, -1.0D);
/* 325 */     if (xm < 0.0D || xm > 1.0D) {
/* 326 */       throw new BadParabolaException("Poor numerical conditioning a=" + a + " b=" + b + " xm=" + xm);
/*     */     }
/*     */     
/* 329 */     return xm * (x3 - x1) + x1;
/*     */   }
/*     */   
/*     */   public static interface Function {
/*     */     double function(double param1Double);
/*     */   }
/*     */   
/*     */   private static class BadParabolaException
/*     */     extends Exception {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private BadParabolaException(String message) {
/* 341 */       super(message);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class IndexSorter
/*     */   {
/* 351 */     private double[] _values = null;
/* 352 */     private int _length = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private IndexSorter(double[] values) {
/* 361 */       this._values = values;
/* 362 */       this._length = values.length;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int[] getSortedIndices() {
/* 373 */       MyComparable[] c = new MyComparable[this._length];
/* 374 */       for (int i = 0; i < c.length; i++) {
/* 375 */         c[i] = new MyComparable(i);
/*     */       }
/*     */       
/* 378 */       Arrays.sort((Object[])c);
/* 379 */       int[] result = new int[c.length];
/* 380 */       for (int j = 0; j < result.length; j++) {
/* 381 */         result[j] = (c[j]).index;
/*     */       }
/* 383 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private class MyComparable
/*     */       implements Comparable<MyComparable>
/*     */     {
/* 393 */       private int index = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private MyComparable(int index) {
/* 401 */         this.index = index;
/*     */       }
/*     */ 
/*     */       
/*     */       public int compareTo(MyComparable o) {
/* 406 */         return ScalarSolver.s_almost.cmp(ScalarSolver.IndexSorter.this._values[this.index], ScalarSolver.IndexSorter.this._values[o.index]);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/ScalarSolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */