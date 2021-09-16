/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumberTickUnitSource
/*     */   implements TickUnitSource, Serializable
/*     */ {
/*     */   private boolean integers;
/*  58 */   private int power = 0;
/*     */   
/*  60 */   private int factor = 1; private NumberFormat formatter;
/*     */   private DecimalFormat dfNeg4;
/*     */   private DecimalFormat dfNeg3;
/*     */   private DecimalFormat dfNeg2;
/*     */   private DecimalFormat dfNeg1;
/*     */   private DecimalFormat df0;
/*     */   private DecimalFormat df;
/*     */   
/*     */   public NumberTickUnitSource() {
/*  69 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberTickUnitSource(boolean integers) {
/*  78 */     this(integers, null);
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
/*     */   public TickUnit getLargerTickUnit(TickUnit unit) {
/*  97 */     TickUnit t = getCeilingTickUnit(unit);
/*  98 */     if (t.equals(unit)) {
/*  99 */       next();
/*     */       
/* 101 */       t = new NumberTickUnit(getTickSize(), getTickLabelFormat(), getMinorTickCount());
/*     */     } 
/* 103 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   public TickUnit getCeilingTickUnit(TickUnit unit) {
/* 108 */     return getCeilingTickUnit(unit.getSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public TickUnit getCeilingTickUnit(double size) {
/* 113 */     if (Double.isInfinite(size)) {
/* 114 */       throw new IllegalArgumentException("Must be finite.");
/*     */     }
/* 116 */     this.power = (int)Math.ceil(Math.log10(size));
/* 117 */     if (this.integers) {
/* 118 */       this.power = Math.max(this.power, 0);
/*     */     }
/* 120 */     this.factor = 1;
/* 121 */     boolean done = false;
/*     */ 
/*     */     
/* 124 */     while (!done) {
/* 125 */       done = !previous();
/* 126 */       if (getTickSize() < size) {
/* 127 */         next();
/* 128 */         done = true;
/*     */       } 
/*     */     } 
/* 131 */     return new NumberTickUnit(getTickSize(), getTickLabelFormat(), 
/* 132 */         getMinorTickCount());
/*     */   }
/*     */   
/*     */   private boolean next() {
/* 136 */     if (this.factor == 1) {
/* 137 */       this.factor = 2;
/* 138 */       return true;
/*     */     } 
/* 140 */     if (this.factor == 2) {
/* 141 */       this.factor = 5;
/* 142 */       return true;
/*     */     } 
/* 144 */     if (this.factor == 5) {
/* 145 */       if (this.power == 300) {
/* 146 */         return false;
/*     */       }
/* 148 */       this.power++;
/* 149 */       this.factor = 1;
/* 150 */       return true;
/*     */     } 
/* 152 */     throw new IllegalStateException("We should never get here.");
/*     */   }
/*     */   
/*     */   private boolean previous() {
/* 156 */     if (this.factor == 1) {
/* 157 */       if ((this.integers && this.power == 0) || this.power == -300) {
/* 158 */         return false;
/*     */       }
/* 160 */       this.factor = 5;
/* 161 */       this.power--;
/* 162 */       return true;
/*     */     } 
/* 164 */     if (this.factor == 2) {
/* 165 */       this.factor = 1;
/* 166 */       return true;
/*     */     } 
/* 168 */     if (this.factor == 5) {
/* 169 */       this.factor = 2;
/* 170 */       return true;
/*     */     } 
/* 172 */     throw new IllegalStateException("We should never get here.");
/*     */   }
/*     */   
/*     */   private double getTickSize() {
/* 176 */     return this.factor * Math.pow(10.0D, this.power);
/*     */   }
/*     */   
/* 179 */   public NumberTickUnitSource(boolean integers, NumberFormat formatter) { this.dfNeg4 = new DecimalFormat("0.0000");
/* 180 */     this.dfNeg3 = new DecimalFormat("0.000");
/* 181 */     this.dfNeg2 = new DecimalFormat("0.00");
/* 182 */     this.dfNeg1 = new DecimalFormat("0.0");
/* 183 */     this.df0 = new DecimalFormat("#,##0");
/* 184 */     this.df = new DecimalFormat("#.######E0"); this.integers = integers;
/*     */     this.formatter = formatter;
/*     */     this.power = 0;
/* 187 */     this.factor = 1; } private NumberFormat getTickLabelFormat() { if (this.formatter != null) {
/* 188 */       return this.formatter;
/*     */     }
/* 190 */     if (this.power == -4) {
/* 191 */       return this.dfNeg4;
/*     */     }
/* 193 */     if (this.power == -3) {
/* 194 */       return this.dfNeg3;
/*     */     }
/* 196 */     if (this.power == -2) {
/* 197 */       return this.dfNeg2;
/*     */     }
/* 199 */     if (this.power == -1) {
/* 200 */       return this.dfNeg1;
/*     */     }
/* 202 */     if (this.power >= 0 && this.power <= 6) {
/* 203 */       return this.df0;
/*     */     }
/* 205 */     return this.df; }
/*     */ 
/*     */   
/*     */   private int getMinorTickCount() {
/* 209 */     if (this.factor == 1)
/* 210 */       return 10; 
/* 211 */     if (this.factor == 5) {
/* 212 */       return 5;
/*     */     }
/* 214 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 219 */     if (obj == this) {
/* 220 */       return true;
/*     */     }
/* 222 */     if (!(obj instanceof NumberTickUnitSource)) {
/* 223 */       return false;
/*     */     }
/* 225 */     NumberTickUnitSource that = (NumberTickUnitSource)obj;
/* 226 */     if (this.integers != that.integers) {
/* 227 */       return false;
/*     */     }
/* 229 */     if (!ObjectUtilities.equal(this.formatter, that.formatter)) {
/* 230 */       return false;
/*     */     }
/* 232 */     if (this.power != that.power) {
/* 233 */       return false;
/*     */     }
/* 235 */     if (this.factor != that.factor) {
/* 236 */       return false;
/*     */     }
/* 238 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/NumberTickUnitSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */