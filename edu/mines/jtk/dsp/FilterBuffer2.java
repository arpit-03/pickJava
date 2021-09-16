/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilterBuffer2
/*     */ {
/*     */   private int NO_INDEX;
/*     */   private int _l1;
/*     */   private int _l2;
/*     */   private int _m1;
/*     */   private int _m2;
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private int _nb1;
/*     */   private int _nb2;
/*     */   private float[][] _a;
/*     */   private float[][] _b;
/*     */   private int[] _i;
/*     */   private boolean _input;
/*     */   private boolean _output;
/*     */   private Extrapolation _extrapolation;
/*     */   
/*     */   public enum Mode
/*     */   {
/*  92 */     INPUT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     OUTPUT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     INPUT_OUTPUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Extrapolation
/*     */   {
/* 118 */     ZERO_VALUE,
/*     */ 
/*     */ 
/*     */     
/* 122 */     ZERO_SLOPE;
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
/*     */   public FilterBuffer2(int l1, int m1, int l2, int m2, float[][] a) {
/* 136 */     this(l1, m1, (a[0]).length, l2, m2, a.length);
/* 137 */     setArray(a);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FilterBuffer2(int l1, int m1, int n1, int l2, int m2, int n2) {
/* 258 */     this.NO_INDEX = Integer.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     this._input = true;
/* 267 */     this._output = false;
/* 268 */     this._extrapolation = Extrapolation.ZERO_VALUE; this._l1 = l1; this._m1 = m1; this._n1 = n1; this._l2 = l2; this._m2 = m2; this._n2 = n2; this._nb1 = this._l1 + this._n1 + this._m1; this._nb2 = this._l2 + 1 + this._m2; this._i = new int[this._nb2]; this._b = new float[this._nb2][this._nb1];
/*     */   }
/*     */   public void setArray(float[][] a) { Check.argument((this._n1 == (a[0]).length), "a[0].length is valid"); Check.argument((this._n2 == a.length), "a.length is valid"); this._a = a; for (int j2 = 0; j2 < this._nb2; j2++)
/* 271 */       this._i[j2] = this.NO_INDEX;  } public void setExtrapolation(Extrapolation extrapolation) { this._extrapolation = extrapolation; } private int j2(int i2) { return (i2 + this._l2) % this._nb2; }
/*     */   public void setMode(Mode mode) { this._input = (mode == Mode.INPUT || mode == Mode.INPUT_OUTPUT); this._output = (mode == Mode.OUTPUT || mode == Mode.INPUT_OUTPUT); }
/*     */   public float[] get(int i2) { checkIndex(i2); int j2 = j2(i2); if (this._i[j2] != i2) { int k2 = this._i[j2]; if (this._output && 0 <= k2 && k2 < this._n2) ArrayMath.copy(this._n1, this._l1, this._b[j2], 0, this._a[k2]);  if (this._output && !this._input) { ArrayMath.zero(this._b[j2]); } else if (this._extrapolation == Extrapolation.ZERO_SLOPE) { k2 = ArrayMath.max(0, ArrayMath.min(this._n2 - 1, i2)); ArrayMath.copy(this._n1, 0, this._a[k2], this._l1, this._b[j2]); fill(this._a[k2][0], this._l1, 0, this._b[j2]); fill(this._a[k2][this._n1 - 1], this._m1, this._l1 + this._n1, this._b[j2]); } else if (this._extrapolation == Extrapolation.ZERO_VALUE) { if (0 <= i2 && i2 < this._n2) { ArrayMath.copy(this._n1, 0, this._a[i2], this._l1, this._b[j2]); fill(0.0F, this._l1, 0, this._b[j2]); fill(0.0F, this._m1, this._l1 + this._n1, this._b[j2]); } else { ArrayMath.zero(this._b[j2]); }  }  this._i[j2] = i2; }  return this._b[j2]; }
/*     */   public void flush() { if (this._output) for (int j2 = 0; j2 < this._nb2; j2++) { int i2 = this._i[j2]; if (0 <= i2 && i2 < this._n2)
/* 275 */           ArrayMath.copy(this._n1, this._l1, this._b[j2], 0, this._a[i2]);  this._i[j2] = this.NO_INDEX; }   } private static void fill(float a, int n, int j, float[] b) { for (; n > 0; n--, j++)
/* 276 */       b[j] = a;  }
/*     */ 
/*     */   
/*     */   private void checkIndex(int i2) {
/* 280 */     Check.state((this._a != null), "array of values has been specified");
/* 281 */     Check.argument((-this._l2 <= i2 && i2 <= this._n2 + this._m2 - 1), "index i2=" + i2 + " is in bounds");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/FilterBuffer2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */