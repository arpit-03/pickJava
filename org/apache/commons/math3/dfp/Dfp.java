/*      */ package org.apache.commons.math3.dfp;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.RealFieldElement;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Dfp
/*      */   implements RealFieldElement<Dfp>
/*      */ {
/*      */   public static final int RADIX = 10000;
/*      */   public static final int MIN_EXP = -32767;
/*      */   public static final int MAX_EXP = 32768;
/*      */   public static final int ERR_SCALE = 32760;
/*      */   public static final byte FINITE = 0;
/*      */   public static final byte INFINITE = 1;
/*      */   public static final byte SNAN = 2;
/*      */   public static final byte QNAN = 3;
/*      */   private static final String NAN_STRING = "NaN";
/*      */   private static final String POS_INFINITY_STRING = "Infinity";
/*      */   private static final String NEG_INFINITY_STRING = "-Infinity";
/*      */   private static final String ADD_TRAP = "add";
/*      */   private static final String MULTIPLY_TRAP = "multiply";
/*      */   private static final String DIVIDE_TRAP = "divide";
/*      */   private static final String SQRT_TRAP = "sqrt";
/*      */   private static final String ALIGN_TRAP = "align";
/*      */   private static final String TRUNC_TRAP = "trunc";
/*      */   private static final String NEXT_AFTER_TRAP = "nextAfter";
/*      */   private static final String LESS_THAN_TRAP = "lessThan";
/*      */   private static final String GREATER_THAN_TRAP = "greaterThan";
/*      */   private static final String NEW_INSTANCE_TRAP = "newInstance";
/*      */   protected int[] mant;
/*      */   protected byte sign;
/*      */   protected int exp;
/*      */   protected byte nans;
/*      */   private final DfpField field;
/*      */   
/*      */   protected Dfp(DfpField field) {
/*  183 */     this.mant = new int[field.getRadixDigits()];
/*  184 */     this.sign = 1;
/*  185 */     this.exp = 0;
/*  186 */     this.nans = 0;
/*  187 */     this.field = field;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dfp(DfpField field, byte x) {
/*  195 */     this(field, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dfp(DfpField field, int x) {
/*  203 */     this(field, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dfp(DfpField field, long x) {
/*  213 */     this.mant = new int[field.getRadixDigits()];
/*  214 */     this.nans = 0;
/*  215 */     this.field = field;
/*      */     
/*  217 */     boolean isLongMin = false;
/*  218 */     if (x == Long.MIN_VALUE) {
/*      */ 
/*      */       
/*  221 */       isLongMin = true;
/*  222 */       x++;
/*      */     } 
/*      */ 
/*      */     
/*  226 */     if (x < 0L) {
/*  227 */       this.sign = -1;
/*  228 */       x = -x;
/*      */     } else {
/*  230 */       this.sign = 1;
/*      */     } 
/*      */     
/*  233 */     this.exp = 0;
/*  234 */     while (x != 0L) {
/*  235 */       System.arraycopy(this.mant, this.mant.length - this.exp, this.mant, this.mant.length - 1 - this.exp, this.exp);
/*  236 */       this.mant[this.mant.length - 1] = (int)(x % 10000L);
/*  237 */       x /= 10000L;
/*  238 */       this.exp++;
/*      */     } 
/*      */     
/*  241 */     if (isLongMin)
/*      */     {
/*      */       
/*  244 */       for (int i = 0; i < this.mant.length - 1; i++) {
/*  245 */         if (this.mant[i] != 0) {
/*  246 */           this.mant[i] = this.mant[i] + 1;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dfp(DfpField field, double x) {
/*  260 */     this.mant = new int[field.getRadixDigits()];
/*  261 */     this.sign = 1;
/*  262 */     this.exp = 0;
/*  263 */     this.nans = 0;
/*  264 */     this.field = field;
/*      */     
/*  266 */     long bits = Double.doubleToLongBits(x);
/*  267 */     long mantissa = bits & 0xFFFFFFFFFFFFFL;
/*  268 */     int exponent = (int)((bits & 0x7FF0000000000000L) >> 52L) - 1023;
/*      */     
/*  270 */     if (exponent == -1023) {
/*      */       
/*  272 */       if (x == 0.0D) {
/*      */         
/*  274 */         if ((bits & Long.MIN_VALUE) != 0L) {
/*  275 */           this.sign = -1;
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*  280 */       exponent++;
/*      */ 
/*      */       
/*  283 */       while ((mantissa & 0x10000000000000L) == 0L) {
/*  284 */         exponent--;
/*  285 */         mantissa <<= 1L;
/*      */       } 
/*  287 */       mantissa &= 0xFFFFFFFFFFFFFL;
/*      */     } 
/*      */     
/*  290 */     if (exponent == 1024) {
/*      */       
/*  292 */       if (x != x) {
/*  293 */         this.sign = 1;
/*  294 */         this.nans = 3;
/*  295 */       } else if (x < 0.0D) {
/*  296 */         this.sign = -1;
/*  297 */         this.nans = 1;
/*      */       } else {
/*  299 */         this.sign = 1;
/*  300 */         this.nans = 1;
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  305 */     Dfp xdfp = new Dfp(field, mantissa);
/*  306 */     xdfp = xdfp.divide(new Dfp(field, 4503599627370496L)).add(field.getOne());
/*  307 */     xdfp = xdfp.multiply(DfpMath.pow(field.getTwo(), exponent));
/*      */     
/*  309 */     if ((bits & Long.MIN_VALUE) != 0L) {
/*  310 */       xdfp = xdfp.negate();
/*      */     }
/*      */     
/*  313 */     System.arraycopy(xdfp.mant, 0, this.mant, 0, this.mant.length);
/*  314 */     this.sign = xdfp.sign;
/*  315 */     this.exp = xdfp.exp;
/*  316 */     this.nans = xdfp.nans;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp(Dfp d) {
/*  324 */     this.mant = (int[])d.mant.clone();
/*  325 */     this.sign = d.sign;
/*  326 */     this.exp = d.exp;
/*  327 */     this.nans = d.nans;
/*  328 */     this.field = d.field;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dfp(DfpField field, String s) {
/*      */     String fpdecimal;
/*  338 */     this.mant = new int[field.getRadixDigits()];
/*  339 */     this.sign = 1;
/*  340 */     this.exp = 0;
/*  341 */     this.nans = 0;
/*  342 */     this.field = field;
/*      */     
/*  344 */     boolean decimalFound = false;
/*  345 */     int rsize = 4;
/*  346 */     int offset = 4;
/*  347 */     char[] striped = new char[getRadixDigits() * 4 + 8];
/*      */ 
/*      */     
/*  350 */     if (s.equals("Infinity")) {
/*  351 */       this.sign = 1;
/*  352 */       this.nans = 1;
/*      */       
/*      */       return;
/*      */     } 
/*  356 */     if (s.equals("-Infinity")) {
/*  357 */       this.sign = -1;
/*  358 */       this.nans = 1;
/*      */       
/*      */       return;
/*      */     } 
/*  362 */     if (s.equals("NaN")) {
/*  363 */       this.sign = 1;
/*  364 */       this.nans = 3;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  369 */     int p = s.indexOf("e");
/*  370 */     if (p == -1) {
/*  371 */       p = s.indexOf("E");
/*      */     }
/*      */ 
/*      */     
/*  375 */     int sciexp = 0;
/*  376 */     if (p != -1) {
/*      */       
/*  378 */       fpdecimal = s.substring(0, p);
/*  379 */       String fpexp = s.substring(p + 1);
/*  380 */       boolean negative = false;
/*      */       
/*  382 */       for (int j = 0; j < fpexp.length(); j++) {
/*      */         
/*  384 */         if (fpexp.charAt(j) == '-') {
/*      */           
/*  386 */           negative = true;
/*      */         
/*      */         }
/*  389 */         else if (fpexp.charAt(j) >= '0' && fpexp.charAt(j) <= '9') {
/*  390 */           sciexp = sciexp * 10 + fpexp.charAt(j) - 48;
/*      */         } 
/*      */       } 
/*      */       
/*  394 */       if (negative) {
/*  395 */         sciexp = -sciexp;
/*      */       }
/*      */     } else {
/*      */       
/*  399 */       fpdecimal = s;
/*      */     } 
/*      */ 
/*      */     
/*  403 */     if (fpdecimal.indexOf("-") != -1) {
/*  404 */       this.sign = -1;
/*      */     }
/*      */ 
/*      */     
/*  408 */     p = 0;
/*      */ 
/*      */     
/*  411 */     int decimalPos = 0;
/*      */     
/*  413 */     while (fpdecimal.charAt(p) < '1' || fpdecimal.charAt(p) > '9') {
/*      */ 
/*      */ 
/*      */       
/*  417 */       if (decimalFound && fpdecimal.charAt(p) == '0') {
/*  418 */         decimalPos--;
/*      */       }
/*      */       
/*  421 */       if (fpdecimal.charAt(p) == '.') {
/*  422 */         decimalFound = true;
/*      */       }
/*      */       
/*  425 */       p++;
/*      */       
/*  427 */       if (p == fpdecimal.length()) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  433 */     int q = 4;
/*  434 */     striped[0] = '0';
/*  435 */     striped[1] = '0';
/*  436 */     striped[2] = '0';
/*  437 */     striped[3] = '0';
/*  438 */     int significantDigits = 0;
/*      */     
/*  440 */     while (p != fpdecimal.length()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  445 */       if (q == this.mant.length * 4 + 4 + 1) {
/*      */         break;
/*      */       }
/*      */       
/*  449 */       if (fpdecimal.charAt(p) == '.') {
/*  450 */         decimalFound = true;
/*  451 */         decimalPos = significantDigits;
/*  452 */         p++;
/*      */         
/*      */         continue;
/*      */       } 
/*  456 */       if (fpdecimal.charAt(p) < '0' || fpdecimal.charAt(p) > '9') {
/*  457 */         p++;
/*      */         
/*      */         continue;
/*      */       } 
/*  461 */       striped[q] = fpdecimal.charAt(p);
/*  462 */       q++;
/*  463 */       p++;
/*  464 */       significantDigits++;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  469 */     if (decimalFound && q != 4) {
/*      */       
/*  471 */       q--;
/*  472 */       while (q != 4) {
/*      */ 
/*      */         
/*  475 */         if (striped[q] == '0') {
/*  476 */           significantDigits--;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  484 */     if (decimalFound && significantDigits == 0) {
/*  485 */       decimalPos = 0;
/*      */     }
/*      */ 
/*      */     
/*  489 */     if (!decimalFound) {
/*  490 */       decimalPos = q - 4;
/*      */     }
/*      */ 
/*      */     
/*  494 */     q = 4;
/*  495 */     p = significantDigits - 1 + 4;
/*      */     
/*  497 */     while (p > q && 
/*  498 */       striped[p] == '0')
/*      */     {
/*      */       
/*  501 */       p--;
/*      */     }
/*      */ 
/*      */     
/*  505 */     int i = (400 - decimalPos - sciexp % 4) % 4;
/*  506 */     q -= i;
/*  507 */     decimalPos += i;
/*      */ 
/*      */     
/*  510 */     while (p - q < this.mant.length * 4) {
/*  511 */       for (i = 0; i < 4; i++) {
/*  512 */         striped[++p] = '0';
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  518 */     for (i = this.mant.length - 1; i >= 0; i--) {
/*  519 */       this.mant[i] = (striped[q] - 48) * 1000 + (striped[q + 1] - 48) * 100 + (striped[q + 2] - 48) * 10 + striped[q + 3] - 48;
/*      */ 
/*      */ 
/*      */       
/*  523 */       q += 4;
/*      */     } 
/*      */ 
/*      */     
/*  527 */     this.exp = (decimalPos + sciexp) / 4;
/*      */     
/*  529 */     if (q < striped.length)
/*      */     {
/*  531 */       round((striped[q] - 48) * 1000);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dfp(DfpField field, byte sign, byte nans) {
/*  543 */     this.field = field;
/*  544 */     this.mant = new int[field.getRadixDigits()];
/*  545 */     this.sign = sign;
/*  546 */     this.exp = 0;
/*  547 */     this.nans = nans;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp newInstance() {
/*  555 */     return new Dfp(getField());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp newInstance(byte x) {
/*  563 */     return new Dfp(getField(), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp newInstance(int x) {
/*  571 */     return new Dfp(getField(), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp newInstance(long x) {
/*  579 */     return new Dfp(getField(), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp newInstance(double x) {
/*  587 */     return new Dfp(getField(), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp newInstance(Dfp d) {
/*  598 */     if (this.field.getRadixDigits() != d.field.getRadixDigits()) {
/*  599 */       this.field.setIEEEFlagsBits(1);
/*  600 */       Dfp result = newInstance(getZero());
/*  601 */       result.nans = 3;
/*  602 */       return dotrap(1, "newInstance", d, result);
/*      */     } 
/*      */     
/*  605 */     return new Dfp(d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp newInstance(String s) {
/*  615 */     return new Dfp(this.field, s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp newInstance(byte sig, byte code) {
/*  625 */     return this.field.newDfp(sig, code);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DfpField getField() {
/*  636 */     return this.field;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRadixDigits() {
/*  643 */     return this.field.getRadixDigits();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp getZero() {
/*  650 */     return this.field.getZero();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp getOne() {
/*  657 */     return this.field.getOne();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp getTwo() {
/*  664 */     return this.field.getTwo();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void shiftLeft() {
/*  670 */     for (int i = this.mant.length - 1; i > 0; i--) {
/*  671 */       this.mant[i] = this.mant[i - 1];
/*      */     }
/*  673 */     this.mant[0] = 0;
/*  674 */     this.exp--;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void shiftRight() {
/*  682 */     for (int i = 0; i < this.mant.length - 1; i++) {
/*  683 */       this.mant[i] = this.mant[i + 1];
/*      */     }
/*  685 */     this.mant[this.mant.length - 1] = 0;
/*  686 */     this.exp++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int align(int e) {
/*  698 */     int lostdigit = 0;
/*  699 */     boolean inexact = false;
/*      */     
/*  701 */     int diff = this.exp - e;
/*      */     
/*  703 */     int adiff = diff;
/*  704 */     if (adiff < 0) {
/*  705 */       adiff = -adiff;
/*      */     }
/*      */     
/*  708 */     if (diff == 0) {
/*  709 */       return 0;
/*      */     }
/*      */     
/*  712 */     if (adiff > this.mant.length + 1) {
/*      */       
/*  714 */       Arrays.fill(this.mant, 0);
/*  715 */       this.exp = e;
/*      */       
/*  717 */       this.field.setIEEEFlagsBits(16);
/*  718 */       dotrap(16, "align", this, this);
/*      */       
/*  720 */       return 0;
/*      */     } 
/*      */     
/*  723 */     for (int i = 0; i < adiff; i++) {
/*  724 */       if (diff < 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  729 */         if (lostdigit != 0) {
/*  730 */           inexact = true;
/*      */         }
/*      */         
/*  733 */         lostdigit = this.mant[0];
/*      */         
/*  735 */         shiftRight();
/*      */       } else {
/*  737 */         shiftLeft();
/*      */       } 
/*      */     } 
/*      */     
/*  741 */     if (inexact) {
/*  742 */       this.field.setIEEEFlagsBits(16);
/*  743 */       dotrap(16, "align", this, this);
/*      */     } 
/*      */     
/*  746 */     return lostdigit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean lessThan(Dfp x) {
/*  757 */     if (this.field.getRadixDigits() != x.field.getRadixDigits()) {
/*  758 */       this.field.setIEEEFlagsBits(1);
/*  759 */       Dfp result = newInstance(getZero());
/*  760 */       result.nans = 3;
/*  761 */       dotrap(1, "lessThan", x, result);
/*  762 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  766 */     if (isNaN() || x.isNaN()) {
/*  767 */       this.field.setIEEEFlagsBits(1);
/*  768 */       dotrap(1, "lessThan", x, newInstance(getZero()));
/*  769 */       return false;
/*      */     } 
/*      */     
/*  772 */     return (compare(this, x) < 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean greaterThan(Dfp x) {
/*  782 */     if (this.field.getRadixDigits() != x.field.getRadixDigits()) {
/*  783 */       this.field.setIEEEFlagsBits(1);
/*  784 */       Dfp result = newInstance(getZero());
/*  785 */       result.nans = 3;
/*  786 */       dotrap(1, "greaterThan", x, result);
/*  787 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  791 */     if (isNaN() || x.isNaN()) {
/*  792 */       this.field.setIEEEFlagsBits(1);
/*  793 */       dotrap(1, "greaterThan", x, newInstance(getZero()));
/*  794 */       return false;
/*      */     } 
/*      */     
/*  797 */     return (compare(this, x) > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean negativeOrNull() {
/*  805 */     if (isNaN()) {
/*  806 */       this.field.setIEEEFlagsBits(1);
/*  807 */       dotrap(1, "lessThan", this, newInstance(getZero()));
/*  808 */       return false;
/*      */     } 
/*      */     
/*  811 */     return (this.sign < 0 || (this.mant[this.mant.length - 1] == 0 && !isInfinite()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean strictlyNegative() {
/*  820 */     if (isNaN()) {
/*  821 */       this.field.setIEEEFlagsBits(1);
/*  822 */       dotrap(1, "lessThan", this, newInstance(getZero()));
/*  823 */       return false;
/*      */     } 
/*      */     
/*  826 */     return (this.sign < 0 && (this.mant[this.mant.length - 1] != 0 || isInfinite()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean positiveOrNull() {
/*  835 */     if (isNaN()) {
/*  836 */       this.field.setIEEEFlagsBits(1);
/*  837 */       dotrap(1, "lessThan", this, newInstance(getZero()));
/*  838 */       return false;
/*      */     } 
/*      */     
/*  841 */     return (this.sign > 0 || (this.mant[this.mant.length - 1] == 0 && !isInfinite()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean strictlyPositive() {
/*  850 */     if (isNaN()) {
/*  851 */       this.field.setIEEEFlagsBits(1);
/*  852 */       dotrap(1, "lessThan", this, newInstance(getZero()));
/*  853 */       return false;
/*      */     } 
/*      */     
/*  856 */     return (this.sign > 0 && (this.mant[this.mant.length - 1] != 0 || isInfinite()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp abs() {
/*  865 */     Dfp result = newInstance(this);
/*  866 */     result.sign = 1;
/*  867 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInfinite() {
/*  874 */     return (this.nans == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNaN() {
/*  881 */     return (this.nans == 3 || this.nans == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isZero() {
/*  889 */     if (isNaN()) {
/*  890 */       this.field.setIEEEFlagsBits(1);
/*  891 */       dotrap(1, "lessThan", this, newInstance(getZero()));
/*  892 */       return false;
/*      */     } 
/*      */     
/*  895 */     return (this.mant[this.mant.length - 1] == 0 && !isInfinite());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) {
/*  906 */     if (other instanceof Dfp) {
/*  907 */       Dfp x = (Dfp)other;
/*  908 */       if (isNaN() || x.isNaN() || this.field.getRadixDigits() != x.field.getRadixDigits()) {
/*  909 */         return false;
/*      */       }
/*      */       
/*  912 */       return (compare(this, x) == 0);
/*      */     } 
/*      */     
/*  915 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  925 */     return 17 + (isZero() ? 0 : (this.sign << 8)) + (this.nans << 16) + this.exp + Arrays.hashCode(this.mant);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean unequal(Dfp x) {
/*  933 */     if (isNaN() || x.isNaN() || this.field.getRadixDigits() != x.field.getRadixDigits()) {
/*  934 */       return false;
/*      */     }
/*      */     
/*  937 */     return (greaterThan(x) || lessThan(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int compare(Dfp a, Dfp b) {
/*  948 */     if (a.mant[a.mant.length - 1] == 0 && b.mant[b.mant.length - 1] == 0 && a.nans == 0 && b.nans == 0)
/*      */     {
/*  950 */       return 0;
/*      */     }
/*      */     
/*  953 */     if (a.sign != b.sign) {
/*  954 */       if (a.sign == -1) {
/*  955 */         return -1;
/*      */       }
/*  957 */       return 1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  962 */     if (a.nans == 1 && b.nans == 0) {
/*  963 */       return a.sign;
/*      */     }
/*      */     
/*  966 */     if (a.nans == 0 && b.nans == 1) {
/*  967 */       return -b.sign;
/*      */     }
/*      */     
/*  970 */     if (a.nans == 1 && b.nans == 1) {
/*  971 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  975 */     if (b.mant[b.mant.length - 1] != 0 && a.mant[b.mant.length - 1] != 0) {
/*  976 */       if (a.exp < b.exp) {
/*  977 */         return -a.sign;
/*      */       }
/*      */       
/*  980 */       if (a.exp > b.exp) {
/*  981 */         return a.sign;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  986 */     for (int i = a.mant.length - 1; i >= 0; i--) {
/*  987 */       if (a.mant[i] > b.mant[i]) {
/*  988 */         return a.sign;
/*      */       }
/*      */       
/*  991 */       if (a.mant[i] < b.mant[i]) {
/*  992 */         return -a.sign;
/*      */       }
/*      */     } 
/*      */     
/*  996 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp rint() {
/* 1007 */     return trunc(DfpField.RoundingMode.ROUND_HALF_EVEN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp floor() {
/* 1016 */     return trunc(DfpField.RoundingMode.ROUND_FLOOR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp ceil() {
/* 1025 */     return trunc(DfpField.RoundingMode.ROUND_CEIL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp remainder(Dfp d) {
/* 1035 */     Dfp result = subtract(divide(d).rint().multiply(d));
/*      */ 
/*      */     
/* 1038 */     if (result.mant[this.mant.length - 1] == 0) {
/* 1039 */       result.sign = this.sign;
/*      */     }
/*      */     
/* 1042 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dfp trunc(DfpField.RoundingMode rmode) {
/*      */     int j;
/* 1051 */     boolean changed = false;
/*      */     
/* 1053 */     if (isNaN()) {
/* 1054 */       return newInstance(this);
/*      */     }
/*      */     
/* 1057 */     if (this.nans == 1) {
/* 1058 */       return newInstance(this);
/*      */     }
/*      */     
/* 1061 */     if (this.mant[this.mant.length - 1] == 0)
/*      */     {
/* 1063 */       return newInstance(this);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1068 */     if (this.exp < 0) {
/* 1069 */       this.field.setIEEEFlagsBits(16);
/* 1070 */       Dfp dfp = newInstance(getZero());
/* 1071 */       dfp = dotrap(16, "trunc", this, dfp);
/* 1072 */       return dfp;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1079 */     if (this.exp >= this.mant.length) {
/* 1080 */       return newInstance(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1086 */     Dfp result = newInstance(this);
/* 1087 */     for (int i = 0; i < this.mant.length - result.exp; i++) {
/* 1088 */       j = changed | ((result.mant[i] != 0) ? 1 : 0);
/* 1089 */       result.mant[i] = 0;
/*      */     } 
/*      */     
/* 1092 */     if (j != 0) {
/* 1093 */       switch (rmode)
/*      */       { case ROUND_FLOOR:
/* 1095 */           if (result.sign == -1)
/*      */           {
/* 1097 */             result = result.add(newInstance(-1));
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1128 */           this.field.setIEEEFlagsBits(16);
/* 1129 */           result = dotrap(16, "trunc", this, result);
/* 1130 */           return result;case ROUND_CEIL: if (result.sign == 1) result = result.add(getOne());  this.field.setIEEEFlagsBits(16); result = dotrap(16, "trunc", this, result); return result; }  Dfp half = newInstance("0.5"); Dfp a = subtract(result); a.sign = 1; if (a.greaterThan(half)) { a = newInstance(getOne()); a.sign = this.sign; result = result.add(a); }  if (a.equals(half) && result.exp > 0 && (result.mant[this.mant.length - result.exp] & 0x1) != 0) { a = newInstance(getOne()); a.sign = this.sign; result = result.add(a); }  this.field.setIEEEFlagsBits(16); result = dotrap(16, "trunc", this, result); return result;
/*      */     } 
/*      */     
/* 1133 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int intValue() {
/* 1142 */     int result = 0;
/*      */     
/* 1144 */     Dfp rounded = rint();
/*      */     
/* 1146 */     if (rounded.greaterThan(newInstance(2147483647))) {
/* 1147 */       return Integer.MAX_VALUE;
/*      */     }
/*      */     
/* 1150 */     if (rounded.lessThan(newInstance(-2147483648))) {
/* 1151 */       return Integer.MIN_VALUE;
/*      */     }
/*      */     
/* 1154 */     for (int i = this.mant.length - 1; i >= this.mant.length - rounded.exp; i--) {
/* 1155 */       result = result * 10000 + rounded.mant[i];
/*      */     }
/*      */     
/* 1158 */     if (rounded.sign == -1) {
/* 1159 */       result = -result;
/*      */     }
/*      */     
/* 1162 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int log10K() {
/* 1171 */     return this.exp - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp power10K(int e) {
/* 1179 */     Dfp d = newInstance(getOne());
/* 1180 */     d.exp = e + 1;
/* 1181 */     return d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int intLog10() {
/* 1189 */     if (this.mant[this.mant.length - 1] > 1000) {
/* 1190 */       return this.exp * 4 - 1;
/*      */     }
/* 1192 */     if (this.mant[this.mant.length - 1] > 100) {
/* 1193 */       return this.exp * 4 - 2;
/*      */     }
/* 1195 */     if (this.mant[this.mant.length - 1] > 10) {
/* 1196 */       return this.exp * 4 - 3;
/*      */     }
/* 1198 */     return this.exp * 4 - 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp power10(int e) {
/* 1206 */     Dfp d = newInstance(getOne());
/*      */     
/* 1208 */     if (e >= 0) {
/* 1209 */       d.exp = e / 4 + 1;
/*      */     } else {
/* 1211 */       d.exp = (e + 1) / 4;
/*      */     } 
/*      */     
/* 1214 */     switch ((e % 4 + 4) % 4) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/* 1227 */         return d;
/*      */       case 1:
/*      */         d = d.multiply(10);
/*      */       case 2:
/*      */         d = d.multiply(100);
/*      */     } 
/*      */     d = d.multiply(1000);
/*      */   }
/*      */ 
/*      */   
/*      */   protected int complement(int extra) {
/* 1238 */     extra = 10000 - extra;
/* 1239 */     for (int i = 0; i < this.mant.length; i++) {
/* 1240 */       this.mant[i] = 10000 - this.mant[i] - 1;
/*      */     }
/*      */     
/* 1243 */     int rh = extra / 10000;
/* 1244 */     extra -= rh * 10000;
/* 1245 */     for (int j = 0; j < this.mant.length; j++) {
/* 1246 */       int r = this.mant[j] + rh;
/* 1247 */       rh = r / 10000;
/* 1248 */       this.mant[j] = r - rh * 10000;
/*      */     } 
/*      */     
/* 1251 */     return extra;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp add(Dfp x) {
/* 1261 */     if (this.field.getRadixDigits() != x.field.getRadixDigits()) {
/* 1262 */       this.field.setIEEEFlagsBits(1);
/* 1263 */       Dfp dfp = newInstance(getZero());
/* 1264 */       dfp.nans = 3;
/* 1265 */       return dotrap(1, "add", x, dfp);
/*      */     } 
/*      */ 
/*      */     
/* 1269 */     if (this.nans != 0 || x.nans != 0) {
/* 1270 */       if (isNaN()) {
/* 1271 */         return this;
/*      */       }
/*      */       
/* 1274 */       if (x.isNaN()) {
/* 1275 */         return x;
/*      */       }
/*      */       
/* 1278 */       if (this.nans == 1 && x.nans == 0) {
/* 1279 */         return this;
/*      */       }
/*      */       
/* 1282 */       if (x.nans == 1 && this.nans == 0) {
/* 1283 */         return x;
/*      */       }
/*      */       
/* 1286 */       if (x.nans == 1 && this.nans == 1 && this.sign == x.sign) {
/* 1287 */         return x;
/*      */       }
/*      */       
/* 1290 */       if (x.nans == 1 && this.nans == 1 && this.sign != x.sign) {
/* 1291 */         this.field.setIEEEFlagsBits(1);
/* 1292 */         Dfp dfp = newInstance(getZero());
/* 1293 */         dfp.nans = 3;
/* 1294 */         dfp = dotrap(1, "add", x, dfp);
/* 1295 */         return dfp;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1300 */     Dfp a = newInstance(this);
/* 1301 */     Dfp b = newInstance(x);
/*      */ 
/*      */     
/* 1304 */     Dfp result = newInstance(getZero());
/*      */ 
/*      */     
/* 1307 */     byte asign = a.sign;
/* 1308 */     byte bsign = b.sign;
/*      */     
/* 1310 */     a.sign = 1;
/* 1311 */     b.sign = 1;
/*      */ 
/*      */     
/* 1314 */     byte rsign = bsign;
/* 1315 */     if (compare(a, b) > 0) {
/* 1316 */       rsign = asign;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1322 */     if (b.mant[this.mant.length - 1] == 0) {
/* 1323 */       b.exp = a.exp;
/*      */     }
/*      */     
/* 1326 */     if (a.mant[this.mant.length - 1] == 0) {
/* 1327 */       a.exp = b.exp;
/*      */     }
/*      */ 
/*      */     
/* 1331 */     int aextradigit = 0;
/* 1332 */     int bextradigit = 0;
/* 1333 */     if (a.exp < b.exp) {
/* 1334 */       aextradigit = a.align(b.exp);
/*      */     } else {
/* 1336 */       bextradigit = b.align(a.exp);
/*      */     } 
/*      */ 
/*      */     
/* 1340 */     if (asign != bsign) {
/* 1341 */       if (asign == rsign) {
/* 1342 */         bextradigit = b.complement(bextradigit);
/*      */       } else {
/* 1344 */         aextradigit = a.complement(aextradigit);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1349 */     int rh = 0; int i;
/* 1350 */     for (i = 0; i < this.mant.length; i++) {
/* 1351 */       int r = a.mant[i] + b.mant[i] + rh;
/* 1352 */       rh = r / 10000;
/* 1353 */       result.mant[i] = r - rh * 10000;
/*      */     } 
/* 1355 */     result.exp = a.exp;
/* 1356 */     result.sign = rsign;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1361 */     if (rh != 0 && asign == bsign) {
/* 1362 */       int lostdigit = result.mant[0];
/* 1363 */       result.shiftRight();
/* 1364 */       result.mant[this.mant.length - 1] = rh;
/* 1365 */       int j = result.round(lostdigit);
/* 1366 */       if (j != 0) {
/* 1367 */         result = dotrap(j, "add", x, result);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1372 */     for (i = 0; i < this.mant.length && 
/* 1373 */       result.mant[this.mant.length - 1] == 0; i++) {
/*      */ 
/*      */       
/* 1376 */       result.shiftLeft();
/* 1377 */       if (i == 0) {
/* 1378 */         result.mant[0] = aextradigit + bextradigit;
/* 1379 */         aextradigit = 0;
/* 1380 */         bextradigit = 0;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1385 */     if (result.mant[this.mant.length - 1] == 0) {
/* 1386 */       result.exp = 0;
/*      */       
/* 1388 */       if (asign != bsign)
/*      */       {
/* 1390 */         result.sign = 1;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1395 */     int excp = result.round(aextradigit + bextradigit);
/* 1396 */     if (excp != 0) {
/* 1397 */       result = dotrap(excp, "add", x, result);
/*      */     }
/*      */     
/* 1400 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp negate() {
/* 1407 */     Dfp result = newInstance(this);
/* 1408 */     result.sign = (byte)-result.sign;
/* 1409 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp subtract(Dfp x) {
/* 1417 */     return add(x.negate());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int round(int n) {
/* 1425 */     boolean inc = false;
/* 1426 */     switch (this.field.getRoundingMode()) {
/*      */       case ROUND_DOWN:
/* 1428 */         inc = false;
/*      */         break;
/*      */       
/*      */       case ROUND_UP:
/* 1432 */         inc = (n != 0);
/*      */         break;
/*      */       
/*      */       case ROUND_HALF_UP:
/* 1436 */         inc = (n >= 5000);
/*      */         break;
/*      */       
/*      */       case ROUND_HALF_DOWN:
/* 1440 */         inc = (n > 5000);
/*      */         break;
/*      */       
/*      */       case ROUND_HALF_EVEN:
/* 1444 */         inc = (n > 5000 || (n == 5000 && (this.mant[0] & 0x1) == 1));
/*      */         break;
/*      */       
/*      */       case ROUND_HALF_ODD:
/* 1448 */         inc = (n > 5000 || (n == 5000 && (this.mant[0] & 0x1) == 0));
/*      */         break;
/*      */       
/*      */       case ROUND_CEIL:
/* 1452 */         inc = (this.sign == 1 && n != 0);
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/* 1457 */         inc = (this.sign == -1 && n != 0);
/*      */         break;
/*      */     } 
/*      */     
/* 1461 */     if (inc) {
/*      */       
/* 1463 */       int rh = 1;
/* 1464 */       for (int i = 0; i < this.mant.length; i++) {
/* 1465 */         int r = this.mant[i] + rh;
/* 1466 */         rh = r / 10000;
/* 1467 */         this.mant[i] = r - rh * 10000;
/*      */       } 
/*      */       
/* 1470 */       if (rh != 0) {
/* 1471 */         shiftRight();
/* 1472 */         this.mant[this.mant.length - 1] = rh;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1477 */     if (this.exp < -32767) {
/*      */       
/* 1479 */       this.field.setIEEEFlagsBits(8);
/* 1480 */       return 8;
/*      */     } 
/*      */     
/* 1483 */     if (this.exp > 32768) {
/*      */       
/* 1485 */       this.field.setIEEEFlagsBits(4);
/* 1486 */       return 4;
/*      */     } 
/*      */     
/* 1489 */     if (n != 0) {
/*      */       
/* 1491 */       this.field.setIEEEFlagsBits(16);
/* 1492 */       return 16;
/*      */     } 
/*      */     
/* 1495 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp multiply(Dfp x) {
/*      */     int excp;
/* 1506 */     if (this.field.getRadixDigits() != x.field.getRadixDigits()) {
/* 1507 */       this.field.setIEEEFlagsBits(1);
/* 1508 */       Dfp dfp = newInstance(getZero());
/* 1509 */       dfp.nans = 3;
/* 1510 */       return dotrap(1, "multiply", x, dfp);
/*      */     } 
/*      */     
/* 1513 */     Dfp result = newInstance(getZero());
/*      */ 
/*      */     
/* 1516 */     if (this.nans != 0 || x.nans != 0) {
/* 1517 */       if (isNaN()) {
/* 1518 */         return this;
/*      */       }
/*      */       
/* 1521 */       if (x.isNaN()) {
/* 1522 */         return x;
/*      */       }
/*      */       
/* 1525 */       if (this.nans == 1 && x.nans == 0 && x.mant[this.mant.length - 1] != 0) {
/* 1526 */         result = newInstance(this);
/* 1527 */         result.sign = (byte)(this.sign * x.sign);
/* 1528 */         return result;
/*      */       } 
/*      */       
/* 1531 */       if (x.nans == 1 && this.nans == 0 && this.mant[this.mant.length - 1] != 0) {
/* 1532 */         result = newInstance(x);
/* 1533 */         result.sign = (byte)(this.sign * x.sign);
/* 1534 */         return result;
/*      */       } 
/*      */       
/* 1537 */       if (x.nans == 1 && this.nans == 1) {
/* 1538 */         result = newInstance(this);
/* 1539 */         result.sign = (byte)(this.sign * x.sign);
/* 1540 */         return result;
/*      */       } 
/*      */       
/* 1543 */       if ((x.nans == 1 && this.nans == 0 && this.mant[this.mant.length - 1] == 0) || (this.nans == 1 && x.nans == 0 && x.mant[this.mant.length - 1] == 0)) {
/*      */         
/* 1545 */         this.field.setIEEEFlagsBits(1);
/* 1546 */         result = newInstance(getZero());
/* 1547 */         result.nans = 3;
/* 1548 */         result = dotrap(1, "multiply", x, result);
/* 1549 */         return result;
/*      */       } 
/*      */     } 
/*      */     
/* 1553 */     int[] product = new int[this.mant.length * 2];
/*      */     
/* 1555 */     for (int i = 0; i < this.mant.length; i++) {
/* 1556 */       int rh = 0;
/* 1557 */       for (int k = 0; k < this.mant.length; k++) {
/* 1558 */         int r = this.mant[i] * x.mant[k];
/* 1559 */         r += product[i + k] + rh;
/*      */         
/* 1561 */         rh = r / 10000;
/* 1562 */         product[i + k] = r - rh * 10000;
/*      */       } 
/* 1564 */       product[i + this.mant.length] = rh;
/*      */     } 
/*      */ 
/*      */     
/* 1568 */     int md = this.mant.length * 2 - 1; int j;
/* 1569 */     for (j = this.mant.length * 2 - 1; j >= 0; j--) {
/* 1570 */       if (product[j] != 0) {
/* 1571 */         md = j;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1577 */     for (j = 0; j < this.mant.length; j++) {
/* 1578 */       result.mant[this.mant.length - j - 1] = product[md - j];
/*      */     }
/*      */ 
/*      */     
/* 1582 */     result.exp = this.exp + x.exp + md - 2 * this.mant.length + 1;
/* 1583 */     result.sign = (byte)((this.sign == x.sign) ? 1 : -1);
/*      */     
/* 1585 */     if (result.mant[this.mant.length - 1] == 0)
/*      */     {
/* 1587 */       result.exp = 0;
/*      */     }
/*      */ 
/*      */     
/* 1591 */     if (md > this.mant.length - 1) {
/* 1592 */       excp = result.round(product[md - this.mant.length]);
/*      */     } else {
/* 1594 */       excp = result.round(0);
/*      */     } 
/*      */     
/* 1597 */     if (excp != 0) {
/* 1598 */       result = dotrap(excp, "multiply", x, result);
/*      */     }
/*      */     
/* 1601 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp multiply(int x) {
/* 1610 */     if (x >= 0 && x < 10000) {
/* 1611 */       return multiplyFast(x);
/*      */     }
/* 1613 */     return multiply(newInstance(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Dfp multiplyFast(int x) {
/* 1623 */     Dfp result = newInstance(this);
/*      */ 
/*      */     
/* 1626 */     if (this.nans != 0) {
/* 1627 */       if (isNaN()) {
/* 1628 */         return this;
/*      */       }
/*      */       
/* 1631 */       if (this.nans == 1 && x != 0) {
/* 1632 */         result = newInstance(this);
/* 1633 */         return result;
/*      */       } 
/*      */       
/* 1636 */       if (this.nans == 1 && x == 0) {
/* 1637 */         this.field.setIEEEFlagsBits(1);
/* 1638 */         result = newInstance(getZero());
/* 1639 */         result.nans = 3;
/* 1640 */         result = dotrap(1, "multiply", newInstance(getZero()), result);
/* 1641 */         return result;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1646 */     if (x < 0 || x >= 10000) {
/* 1647 */       this.field.setIEEEFlagsBits(1);
/* 1648 */       result = newInstance(getZero());
/* 1649 */       result.nans = 3;
/* 1650 */       result = dotrap(1, "multiply", result, result);
/* 1651 */       return result;
/*      */     } 
/*      */     
/* 1654 */     int rh = 0;
/* 1655 */     for (int i = 0; i < this.mant.length; i++) {
/* 1656 */       int r = this.mant[i] * x + rh;
/* 1657 */       rh = r / 10000;
/* 1658 */       result.mant[i] = r - rh * 10000;
/*      */     } 
/*      */     
/* 1661 */     int lostdigit = 0;
/* 1662 */     if (rh != 0) {
/* 1663 */       lostdigit = result.mant[0];
/* 1664 */       result.shiftRight();
/* 1665 */       result.mant[this.mant.length - 1] = rh;
/*      */     } 
/*      */     
/* 1668 */     if (result.mant[this.mant.length - 1] == 0) {
/* 1669 */       result.exp = 0;
/*      */     }
/*      */     
/* 1672 */     int excp = result.round(lostdigit);
/* 1673 */     if (excp != 0) {
/* 1674 */       result = dotrap(excp, "multiply", result, result);
/*      */     }
/*      */     
/* 1677 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp divide(Dfp divisor) {
/* 1690 */     int excp, trial = 0;
/*      */ 
/*      */     
/* 1693 */     int md = 0;
/*      */ 
/*      */ 
/*      */     
/* 1697 */     if (this.field.getRadixDigits() != divisor.field.getRadixDigits()) {
/* 1698 */       this.field.setIEEEFlagsBits(1);
/* 1699 */       Dfp dfp = newInstance(getZero());
/* 1700 */       dfp.nans = 3;
/* 1701 */       return dotrap(1, "divide", divisor, dfp);
/*      */     } 
/*      */     
/* 1704 */     Dfp result = newInstance(getZero());
/*      */ 
/*      */     
/* 1707 */     if (this.nans != 0 || divisor.nans != 0) {
/* 1708 */       if (isNaN()) {
/* 1709 */         return this;
/*      */       }
/*      */       
/* 1712 */       if (divisor.isNaN()) {
/* 1713 */         return divisor;
/*      */       }
/*      */       
/* 1716 */       if (this.nans == 1 && divisor.nans == 0) {
/* 1717 */         result = newInstance(this);
/* 1718 */         result.sign = (byte)(this.sign * divisor.sign);
/* 1719 */         return result;
/*      */       } 
/*      */       
/* 1722 */       if (divisor.nans == 1 && this.nans == 0) {
/* 1723 */         result = newInstance(getZero());
/* 1724 */         result.sign = (byte)(this.sign * divisor.sign);
/* 1725 */         return result;
/*      */       } 
/*      */       
/* 1728 */       if (divisor.nans == 1 && this.nans == 1) {
/* 1729 */         this.field.setIEEEFlagsBits(1);
/* 1730 */         result = newInstance(getZero());
/* 1731 */         result.nans = 3;
/* 1732 */         result = dotrap(1, "divide", divisor, result);
/* 1733 */         return result;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1738 */     if (divisor.mant[this.mant.length - 1] == 0) {
/* 1739 */       this.field.setIEEEFlagsBits(2);
/* 1740 */       result = newInstance(getZero());
/* 1741 */       result.sign = (byte)(this.sign * divisor.sign);
/* 1742 */       result.nans = 1;
/* 1743 */       result = dotrap(2, "divide", divisor, result);
/* 1744 */       return result;
/*      */     } 
/*      */     
/* 1747 */     int[] dividend = new int[this.mant.length + 1];
/* 1748 */     int[] quotient = new int[this.mant.length + 2];
/* 1749 */     int[] remainder = new int[this.mant.length + 1];
/*      */ 
/*      */ 
/*      */     
/* 1753 */     dividend[this.mant.length] = 0;
/* 1754 */     quotient[this.mant.length] = 0;
/* 1755 */     quotient[this.mant.length + 1] = 0;
/* 1756 */     remainder[this.mant.length] = 0;
/*      */ 
/*      */     
/*      */     int i;
/*      */     
/* 1761 */     for (i = 0; i < this.mant.length; i++) {
/* 1762 */       dividend[i] = this.mant[i];
/* 1763 */       quotient[i] = 0;
/* 1764 */       remainder[i] = 0;
/*      */     } 
/*      */ 
/*      */     
/* 1768 */     int nsqd = 0;
/* 1769 */     for (int qd = this.mant.length + 1; qd >= 0; qd--) {
/*      */ 
/*      */ 
/*      */       
/* 1773 */       int divMsb = dividend[this.mant.length] * 10000 + dividend[this.mant.length - 1];
/* 1774 */       int min = divMsb / (divisor.mant[this.mant.length - 1] + 1);
/* 1775 */       int max = (divMsb + 1) / divisor.mant[this.mant.length - 1];
/*      */       
/* 1777 */       boolean trialgood = false;
/* 1778 */       while (!trialgood) {
/*      */         
/* 1780 */         trial = (min + max) / 2;
/*      */ 
/*      */         
/* 1783 */         int rh = 0; int k;
/* 1784 */         for (k = 0; k < this.mant.length + 1; k++) {
/* 1785 */           int dm = (k < this.mant.length) ? divisor.mant[k] : 0;
/* 1786 */           int r = dm * trial + rh;
/* 1787 */           rh = r / 10000;
/* 1788 */           remainder[k] = r - rh * 10000;
/*      */         } 
/*      */ 
/*      */         
/* 1792 */         rh = 1;
/* 1793 */         for (k = 0; k < this.mant.length + 1; k++) {
/* 1794 */           int r = 9999 - remainder[k] + dividend[k] + rh;
/* 1795 */           rh = r / 10000;
/* 1796 */           remainder[k] = r - rh * 10000;
/*      */         } 
/*      */ 
/*      */         
/* 1800 */         if (rh == 0) {
/*      */           
/* 1802 */           max = trial - 1;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1807 */         int minadj = remainder[this.mant.length] * 10000 + remainder[this.mant.length - 1];
/* 1808 */         minadj /= divisor.mant[this.mant.length - 1] + 1;
/*      */         
/* 1810 */         if (minadj >= 2) {
/* 1811 */           min = trial + minadj;
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1817 */         trialgood = false;
/* 1818 */         for (k = this.mant.length - 1; k >= 0; k--) {
/* 1819 */           if (divisor.mant[k] > remainder[k]) {
/* 1820 */             trialgood = true;
/*      */           }
/* 1822 */           if (divisor.mant[k] < remainder[k]) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/* 1827 */         if (remainder[this.mant.length] != 0) {
/* 1828 */           trialgood = false;
/*      */         }
/*      */         
/* 1831 */         if (!trialgood) {
/* 1832 */           min = trial + 1;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1837 */       quotient[qd] = trial;
/* 1838 */       if (trial != 0 || nsqd != 0) {
/* 1839 */         nsqd++;
/*      */       }
/*      */       
/* 1842 */       if (this.field.getRoundingMode() == DfpField.RoundingMode.ROUND_DOWN && nsqd == this.mant.length) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/* 1847 */       if (nsqd > this.mant.length) {
/*      */         break;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1853 */       dividend[0] = 0;
/* 1854 */       for (int j = 0; j < this.mant.length; j++) {
/* 1855 */         dividend[j + 1] = remainder[j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1860 */     md = this.mant.length;
/* 1861 */     for (i = this.mant.length + 1; i >= 0; i--) {
/* 1862 */       if (quotient[i] != 0) {
/* 1863 */         md = i;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1869 */     for (i = 0; i < this.mant.length; i++) {
/* 1870 */       result.mant[this.mant.length - i - 1] = quotient[md - i];
/*      */     }
/*      */ 
/*      */     
/* 1874 */     result.exp = this.exp - divisor.exp + md - this.mant.length;
/* 1875 */     result.sign = (byte)((this.sign == divisor.sign) ? 1 : -1);
/*      */     
/* 1877 */     if (result.mant[this.mant.length - 1] == 0) {
/* 1878 */       result.exp = 0;
/*      */     }
/*      */     
/* 1881 */     if (md > this.mant.length - 1) {
/* 1882 */       excp = result.round(quotient[md - this.mant.length]);
/*      */     } else {
/* 1884 */       excp = result.round(0);
/*      */     } 
/*      */     
/* 1887 */     if (excp != 0) {
/* 1888 */       result = dotrap(excp, "divide", divisor, result);
/*      */     }
/*      */     
/* 1891 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp divide(int divisor) {
/* 1902 */     if (this.nans != 0) {
/* 1903 */       if (isNaN()) {
/* 1904 */         return this;
/*      */       }
/*      */       
/* 1907 */       if (this.nans == 1) {
/* 1908 */         return newInstance(this);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1913 */     if (divisor == 0) {
/* 1914 */       this.field.setIEEEFlagsBits(2);
/* 1915 */       Dfp dfp = newInstance(getZero());
/* 1916 */       dfp.sign = this.sign;
/* 1917 */       dfp.nans = 1;
/* 1918 */       dfp = dotrap(2, "divide", getZero(), dfp);
/* 1919 */       return dfp;
/*      */     } 
/*      */ 
/*      */     
/* 1923 */     if (divisor < 0 || divisor >= 10000) {
/* 1924 */       this.field.setIEEEFlagsBits(1);
/* 1925 */       Dfp dfp = newInstance(getZero());
/* 1926 */       dfp.nans = 3;
/* 1927 */       dfp = dotrap(1, "divide", dfp, dfp);
/* 1928 */       return dfp;
/*      */     } 
/*      */     
/* 1931 */     Dfp result = newInstance(this);
/*      */     
/* 1933 */     int rl = 0;
/* 1934 */     for (int i = this.mant.length - 1; i >= 0; i--) {
/* 1935 */       int r = rl * 10000 + result.mant[i];
/* 1936 */       int rh = r / divisor;
/* 1937 */       rl = r - rh * divisor;
/* 1938 */       result.mant[i] = rh;
/*      */     } 
/*      */     
/* 1941 */     if (result.mant[this.mant.length - 1] == 0) {
/*      */       
/* 1943 */       result.shiftLeft();
/* 1944 */       int r = rl * 10000;
/* 1945 */       int rh = r / divisor;
/* 1946 */       rl = r - rh * divisor;
/* 1947 */       result.mant[0] = rh;
/*      */     } 
/*      */     
/* 1950 */     int excp = result.round(rl * 10000 / divisor);
/* 1951 */     if (excp != 0) {
/* 1952 */       result = dotrap(excp, "divide", result, result);
/*      */     }
/*      */     
/* 1955 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp reciprocal() {
/* 1961 */     return this.field.getOne().divide(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp sqrt() {
/* 1971 */     if (this.nans == 0 && this.mant[this.mant.length - 1] == 0)
/*      */     {
/* 1973 */       return newInstance(this);
/*      */     }
/*      */     
/* 1976 */     if (this.nans != 0) {
/* 1977 */       if (this.nans == 1 && this.sign == 1)
/*      */       {
/* 1979 */         return newInstance(this);
/*      */       }
/*      */       
/* 1982 */       if (this.nans == 3) {
/* 1983 */         return newInstance(this);
/*      */       }
/*      */       
/* 1986 */       if (this.nans == 2) {
/*      */ 
/*      */         
/* 1989 */         this.field.setIEEEFlagsBits(1);
/* 1990 */         Dfp result = newInstance(this);
/* 1991 */         result = dotrap(1, "sqrt", null, result);
/* 1992 */         return result;
/*      */       } 
/*      */     } 
/*      */     
/* 1996 */     if (this.sign == -1) {
/*      */ 
/*      */ 
/*      */       
/* 2000 */       this.field.setIEEEFlagsBits(1);
/* 2001 */       Dfp result = newInstance(this);
/* 2002 */       result.nans = 3;
/* 2003 */       result = dotrap(1, "sqrt", null, result);
/* 2004 */       return result;
/*      */     } 
/*      */     
/* 2007 */     Dfp x = newInstance(this);
/*      */ 
/*      */     
/* 2010 */     if (x.exp < -1 || x.exp > 1) {
/* 2011 */       this.exp /= 2;
/*      */     }
/*      */ 
/*      */     
/* 2015 */     switch (x.mant[this.mant.length - 1] / 2000) {
/*      */       case 0:
/* 2017 */         x.mant[this.mant.length - 1] = x.mant[this.mant.length - 1] / 2 + 1;
/*      */         break;
/*      */       case 2:
/* 2020 */         x.mant[this.mant.length - 1] = 1500;
/*      */         break;
/*      */       case 3:
/* 2023 */         x.mant[this.mant.length - 1] = 2200;
/*      */         break;
/*      */       default:
/* 2026 */         x.mant[this.mant.length - 1] = 3000;
/*      */         break;
/*      */     } 
/* 2029 */     Dfp dx = newInstance(x);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2034 */     Dfp px = getZero();
/* 2035 */     Dfp ppx = getZero();
/* 2036 */     while (x.unequal(px)) {
/* 2037 */       dx = newInstance(x);
/* 2038 */       dx.sign = -1;
/* 2039 */       dx = dx.add(divide(x));
/* 2040 */       dx = dx.divide(2);
/* 2041 */       ppx = px;
/* 2042 */       px = x;
/* 2043 */       x = x.add(dx);
/*      */       
/* 2045 */       if (x.equals(ppx)) {
/*      */         break;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2052 */       if (dx.mant[this.mant.length - 1] == 0) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 2057 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2066 */     if (this.nans != 0) {
/*      */       
/* 2068 */       if (this.nans == 1) {
/* 2069 */         return (this.sign < 0) ? "-Infinity" : "Infinity";
/*      */       }
/* 2071 */       return "NaN";
/*      */     } 
/*      */ 
/*      */     
/* 2075 */     if (this.exp > this.mant.length || this.exp < -1) {
/* 2076 */       return dfp2sci();
/*      */     }
/*      */     
/* 2079 */     return dfp2string();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String dfp2sci() {
/* 2087 */     char[] rawdigits = new char[this.mant.length * 4];
/* 2088 */     char[] outputbuffer = new char[this.mant.length * 4 + 20];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2096 */     int p = 0;
/* 2097 */     for (int i = this.mant.length - 1; i >= 0; i--) {
/* 2098 */       rawdigits[p++] = (char)(this.mant[i] / 1000 + 48);
/* 2099 */       rawdigits[p++] = (char)(this.mant[i] / 100 % 10 + 48);
/* 2100 */       rawdigits[p++] = (char)(this.mant[i] / 10 % 10 + 48);
/* 2101 */       rawdigits[p++] = (char)(this.mant[i] % 10 + 48);
/*      */     } 
/*      */ 
/*      */     
/* 2105 */     for (p = 0; p < rawdigits.length && 
/* 2106 */       rawdigits[p] == '0'; p++);
/*      */ 
/*      */ 
/*      */     
/* 2110 */     int shf = p;
/*      */ 
/*      */     
/* 2113 */     int q = 0;
/* 2114 */     if (this.sign == -1) {
/* 2115 */       outputbuffer[q++] = '-';
/*      */     }
/*      */     
/* 2118 */     if (p != rawdigits.length) {
/*      */       
/* 2120 */       outputbuffer[q++] = rawdigits[p++];
/* 2121 */       outputbuffer[q++] = '.';
/*      */       
/* 2123 */       while (p < rawdigits.length) {
/* 2124 */         outputbuffer[q++] = rawdigits[p++];
/*      */       }
/*      */     } else {
/* 2127 */       outputbuffer[q++] = '0';
/* 2128 */       outputbuffer[q++] = '.';
/* 2129 */       outputbuffer[q++] = '0';
/* 2130 */       outputbuffer[q++] = 'e';
/* 2131 */       outputbuffer[q++] = '0';
/* 2132 */       return new String(outputbuffer, 0, 5);
/*      */     } 
/*      */     
/* 2135 */     outputbuffer[q++] = 'e';
/*      */ 
/*      */ 
/*      */     
/* 2139 */     int e = this.exp * 4 - shf - 1;
/* 2140 */     int ae = e;
/* 2141 */     if (e < 0) {
/* 2142 */       ae = -e;
/*      */     }
/*      */ 
/*      */     
/* 2146 */     for (p = 1000000000; p > ae; p /= 10);
/*      */ 
/*      */ 
/*      */     
/* 2150 */     if (e < 0) {
/* 2151 */       outputbuffer[q++] = '-';
/*      */     }
/*      */     
/* 2154 */     while (p > 0) {
/* 2155 */       outputbuffer[q++] = (char)(ae / p + 48);
/* 2156 */       ae %= p;
/* 2157 */       p /= 10;
/*      */     } 
/*      */     
/* 2160 */     return new String(outputbuffer, 0, q);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String dfp2string() {
/* 2168 */     char[] buffer = new char[this.mant.length * 4 + 20];
/* 2169 */     int p = 1;
/*      */     
/* 2171 */     int e = this.exp;
/* 2172 */     boolean pointInserted = false;
/*      */     
/* 2174 */     buffer[0] = ' ';
/*      */     
/* 2176 */     if (e <= 0) {
/* 2177 */       buffer[p++] = '0';
/* 2178 */       buffer[p++] = '.';
/* 2179 */       pointInserted = true;
/*      */     } 
/*      */     
/* 2182 */     while (e < 0) {
/* 2183 */       buffer[p++] = '0';
/* 2184 */       buffer[p++] = '0';
/* 2185 */       buffer[p++] = '0';
/* 2186 */       buffer[p++] = '0';
/* 2187 */       e++;
/*      */     } 
/*      */     
/* 2190 */     for (int i = this.mant.length - 1; i >= 0; i--) {
/* 2191 */       buffer[p++] = (char)(this.mant[i] / 1000 + 48);
/* 2192 */       buffer[p++] = (char)(this.mant[i] / 100 % 10 + 48);
/* 2193 */       buffer[p++] = (char)(this.mant[i] / 10 % 10 + 48);
/* 2194 */       buffer[p++] = (char)(this.mant[i] % 10 + 48);
/* 2195 */       if (--e == 0) {
/* 2196 */         buffer[p++] = '.';
/* 2197 */         pointInserted = true;
/*      */       } 
/*      */     } 
/*      */     
/* 2201 */     while (e > 0) {
/* 2202 */       buffer[p++] = '0';
/* 2203 */       buffer[p++] = '0';
/* 2204 */       buffer[p++] = '0';
/* 2205 */       buffer[p++] = '0';
/* 2206 */       e--;
/*      */     } 
/*      */     
/* 2209 */     if (!pointInserted)
/*      */     {
/* 2211 */       buffer[p++] = '.';
/*      */     }
/*      */ 
/*      */     
/* 2215 */     int q = 1;
/* 2216 */     while (buffer[q] == '0') {
/* 2217 */       q++;
/*      */     }
/* 2219 */     if (buffer[q] == '.') {
/* 2220 */       q--;
/*      */     }
/*      */ 
/*      */     
/* 2224 */     while (buffer[p - 1] == '0') {
/* 2225 */       p--;
/*      */     }
/*      */ 
/*      */     
/* 2229 */     if (this.sign < 0) {
/* 2230 */       buffer[--q] = '-';
/*      */     }
/*      */     
/* 2233 */     return new String(buffer, q, p - q);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp dotrap(int type, String what, Dfp oper, Dfp result) {
/* 2245 */     Dfp def = result;
/*      */     
/* 2247 */     switch (type)
/*      */     { case 1:
/* 2249 */         def = newInstance(getZero());
/* 2250 */         def.sign = result.sign;
/* 2251 */         def.nans = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2299 */         return trap(type, what, oper, def, result);case 2: if (this.nans == 0 && this.mant[this.mant.length - 1] != 0) { def = newInstance(getZero()); def.sign = (byte)(this.sign * oper.sign); def.nans = 1; }  if (this.nans == 0 && this.mant[this.mant.length - 1] == 0) { def = newInstance(getZero()); def.nans = 3; }  if (this.nans == 1 || this.nans == 3) { def = newInstance(getZero()); def.nans = 3; }  if (this.nans == 1 || this.nans == 2) { def = newInstance(getZero()); def.nans = 3; }  return trap(type, what, oper, def, result);case 8: if (result.exp + this.mant.length < -32767) { def = newInstance(getZero()); def.sign = result.sign; } else { def = newInstance(result); }  result.exp += 32760; return trap(type, what, oper, def, result);case 4: result.exp -= 32760; def = newInstance(getZero()); def.sign = result.sign; def.nans = 1; return trap(type, what, oper, def, result); }  def = result; return trap(type, what, oper, def, result);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dfp trap(int type, String what, Dfp oper, Dfp def, Dfp result) {
/* 2315 */     return def;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int classify() {
/* 2322 */     return this.nans;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Dfp copysign(Dfp x, Dfp y) {
/* 2332 */     Dfp result = x.newInstance(x);
/* 2333 */     result.sign = y.sign;
/* 2334 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp nextAfter(Dfp x) {
/*      */     Dfp result;
/* 2345 */     if (this.field.getRadixDigits() != x.field.getRadixDigits()) {
/* 2346 */       this.field.setIEEEFlagsBits(1);
/* 2347 */       Dfp dfp = newInstance(getZero());
/* 2348 */       dfp.nans = 3;
/* 2349 */       return dotrap(1, "nextAfter", x, dfp);
/*      */     } 
/*      */ 
/*      */     
/* 2353 */     boolean up = false;
/* 2354 */     if (lessThan(x)) {
/* 2355 */       up = true;
/*      */     }
/*      */     
/* 2358 */     if (compare(this, x) == 0) {
/* 2359 */       return newInstance(x);
/*      */     }
/*      */     
/* 2362 */     if (lessThan(getZero())) {
/* 2363 */       up = !up;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2368 */     if (up) {
/* 2369 */       Dfp inc = newInstance(getOne());
/* 2370 */       inc.exp = this.exp - this.mant.length + 1;
/* 2371 */       inc.sign = this.sign;
/*      */       
/* 2373 */       if (equals(getZero())) {
/* 2374 */         inc.exp = -32767 - this.mant.length;
/*      */       }
/*      */       
/* 2377 */       result = add(inc);
/*      */     } else {
/* 2379 */       Dfp inc = newInstance(getOne());
/* 2380 */       inc.exp = this.exp;
/* 2381 */       inc.sign = this.sign;
/*      */       
/* 2383 */       if (equals(inc)) {
/* 2384 */         this.exp -= this.mant.length;
/*      */       } else {
/* 2386 */         inc.exp = this.exp - this.mant.length + 1;
/*      */       } 
/*      */       
/* 2389 */       if (equals(getZero())) {
/* 2390 */         inc.exp = -32767 - this.mant.length;
/*      */       }
/*      */       
/* 2393 */       result = subtract(inc);
/*      */     } 
/*      */     
/* 2396 */     if (result.classify() == 1 && classify() != 1) {
/* 2397 */       this.field.setIEEEFlagsBits(16);
/* 2398 */       result = dotrap(16, "nextAfter", x, result);
/*      */     } 
/*      */     
/* 2401 */     if (result.equals(getZero()) && !equals(getZero())) {
/* 2402 */       this.field.setIEEEFlagsBits(16);
/* 2403 */       result = dotrap(16, "nextAfter", x, result);
/*      */     } 
/*      */     
/* 2406 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double toDouble() {
/* 2416 */     if (isInfinite()) {
/* 2417 */       if (lessThan(getZero())) {
/* 2418 */         return Double.NEGATIVE_INFINITY;
/*      */       }
/* 2420 */       return Double.POSITIVE_INFINITY;
/*      */     } 
/*      */ 
/*      */     
/* 2424 */     if (isNaN()) {
/* 2425 */       return Double.NaN;
/*      */     }
/*      */     
/* 2428 */     Dfp y = this;
/* 2429 */     boolean negate = false;
/* 2430 */     int cmp0 = compare(this, getZero());
/* 2431 */     if (cmp0 == 0)
/* 2432 */       return (this.sign < 0) ? -0.0D : 0.0D; 
/* 2433 */     if (cmp0 < 0) {
/* 2434 */       y = negate();
/* 2435 */       negate = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2440 */     int exponent = (int)(y.intLog10() * 3.32D);
/* 2441 */     if (exponent < 0) {
/* 2442 */       exponent--;
/*      */     }
/*      */     
/* 2445 */     Dfp tempDfp = DfpMath.pow(getTwo(), exponent);
/* 2446 */     while (tempDfp.lessThan(y) || tempDfp.equals(y)) {
/* 2447 */       tempDfp = tempDfp.multiply(2);
/* 2448 */       exponent++;
/*      */     } 
/* 2450 */     exponent--;
/*      */ 
/*      */ 
/*      */     
/* 2454 */     y = y.divide(DfpMath.pow(getTwo(), exponent));
/* 2455 */     if (exponent > -1023) {
/* 2456 */       y = y.subtract(getOne());
/*      */     }
/*      */     
/* 2459 */     if (exponent < -1074) {
/* 2460 */       return 0.0D;
/*      */     }
/*      */     
/* 2463 */     if (exponent > 1023) {
/* 2464 */       return negate ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
/*      */     }
/*      */ 
/*      */     
/* 2468 */     y = y.multiply(newInstance(4503599627370496L)).rint();
/* 2469 */     String str = y.toString();
/* 2470 */     str = str.substring(0, str.length() - 1);
/* 2471 */     long mantissa = Long.parseLong(str);
/*      */     
/* 2473 */     if (mantissa == 4503599627370496L) {
/*      */       
/* 2475 */       mantissa = 0L;
/* 2476 */       exponent++;
/*      */     } 
/*      */ 
/*      */     
/* 2480 */     if (exponent <= -1023) {
/* 2481 */       exponent--;
/*      */     }
/*      */     
/* 2484 */     while (exponent < -1023) {
/* 2485 */       exponent++;
/* 2486 */       mantissa >>>= 1L;
/*      */     } 
/*      */     
/* 2489 */     long bits = mantissa | exponent + 1023L << 52L;
/* 2490 */     double x = Double.longBitsToDouble(bits);
/*      */     
/* 2492 */     if (negate) {
/* 2493 */       x = -x;
/*      */     }
/*      */     
/* 2496 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] toSplitDouble() {
/* 2505 */     double[] split = new double[2];
/* 2506 */     long mask = -1073741824L;
/*      */     
/* 2508 */     split[0] = Double.longBitsToDouble(Double.doubleToLongBits(toDouble()) & mask);
/* 2509 */     split[1] = subtract(newInstance(split[0])).toDouble();
/*      */     
/* 2511 */     return split;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getReal() {
/* 2518 */     return toDouble();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp add(double a) {
/* 2525 */     return add(newInstance(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp subtract(double a) {
/* 2532 */     return subtract(newInstance(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp multiply(double a) {
/* 2539 */     return multiply(newInstance(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp divide(double a) {
/* 2546 */     return divide(newInstance(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp remainder(double a) {
/* 2553 */     return remainder(newInstance(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long round() {
/* 2560 */     return FastMath.round(toDouble());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp signum() {
/* 2567 */     if (isNaN() || isZero()) {
/* 2568 */       return this;
/*      */     }
/* 2570 */     return newInstance((this.sign > 0) ? 1 : -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp copySign(Dfp s) {
/* 2578 */     if ((this.sign >= 0 && s.sign >= 0) || (this.sign < 0 && s.sign < 0)) {
/* 2579 */       return this;
/*      */     }
/* 2581 */     return negate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp copySign(double s) {
/* 2588 */     long sb = Double.doubleToLongBits(s);
/* 2589 */     if ((this.sign >= 0 && sb >= 0L) || (this.sign < 0 && sb < 0L)) {
/* 2590 */       return this;
/*      */     }
/* 2592 */     return negate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp scalb(int n) {
/* 2599 */     return multiply(DfpMath.pow(getTwo(), n));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp hypot(Dfp y) {
/* 2606 */     return multiply(this).add(y.multiply(y)).sqrt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp cbrt() {
/* 2613 */     return rootN(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp rootN(int n) {
/* 2620 */     return (this.sign >= 0) ? DfpMath.pow(this, getOne().divide(n)) : DfpMath.pow(negate(), getOne().divide(n)).negate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp pow(double p) {
/* 2629 */     return DfpMath.pow(this, newInstance(p));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp pow(int n) {
/* 2636 */     return DfpMath.pow(this, n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp pow(Dfp e) {
/* 2643 */     return DfpMath.pow(this, e);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp exp() {
/* 2650 */     return DfpMath.exp(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp expm1() {
/* 2657 */     return DfpMath.exp(this).subtract(getOne());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp log() {
/* 2664 */     return DfpMath.log(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp log1p() {
/* 2671 */     return DfpMath.log(add(getOne()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int log10() {
/* 2682 */     return intLog10();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp cos() {
/* 2697 */     return DfpMath.cos(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp sin() {
/* 2704 */     return DfpMath.sin(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp tan() {
/* 2711 */     return DfpMath.tan(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp acos() {
/* 2718 */     return DfpMath.acos(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp asin() {
/* 2725 */     return DfpMath.asin(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp atan() {
/* 2732 */     return DfpMath.atan(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp atan2(Dfp x) throws DimensionMismatchException {
/* 2742 */     Dfp r = x.multiply(x).add(multiply(this)).sqrt();
/*      */     
/* 2744 */     if (x.sign >= 0)
/*      */     {
/*      */       
/* 2747 */       return getTwo().multiply(divide(r.add(x)).atan());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2752 */     Dfp tmp = getTwo().multiply(divide(r.subtract(x)).atan());
/* 2753 */     Dfp pmPi = newInstance((tmp.sign <= 0) ? -3.141592653589793D : Math.PI);
/* 2754 */     return pmPi.subtract(tmp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp cosh() {
/* 2764 */     return DfpMath.exp(this).add(DfpMath.exp(negate())).divide(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp sinh() {
/* 2771 */     return DfpMath.exp(this).subtract(DfpMath.exp(negate())).divide(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp tanh() {
/* 2778 */     Dfp ePlus = DfpMath.exp(this);
/* 2779 */     Dfp eMinus = DfpMath.exp(negate());
/* 2780 */     return ePlus.subtract(eMinus).divide(ePlus.add(eMinus));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp acosh() {
/* 2787 */     return multiply(this).subtract(getOne()).sqrt().add(this).log();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp asinh() {
/* 2794 */     return multiply(this).add(getOne()).sqrt().add(this).log();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp atanh() {
/* 2801 */     return getOne().add(this).divide(getOne().subtract(this)).log().divide(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp linearCombination(Dfp[] a, Dfp[] b) throws DimensionMismatchException {
/* 2809 */     if (a.length != b.length) {
/* 2810 */       throw new DimensionMismatchException(a.length, b.length);
/*      */     }
/* 2812 */     Dfp r = getZero();
/* 2813 */     for (int i = 0; i < a.length; i++) {
/* 2814 */       r = r.add(a[i].multiply(b[i]));
/*      */     }
/* 2816 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp linearCombination(double[] a, Dfp[] b) throws DimensionMismatchException {
/* 2824 */     if (a.length != b.length) {
/* 2825 */       throw new DimensionMismatchException(a.length, b.length);
/*      */     }
/* 2827 */     Dfp r = getZero();
/* 2828 */     for (int i = 0; i < a.length; i++) {
/* 2829 */       r = r.add(b[i].multiply(a[i]));
/*      */     }
/* 2831 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp linearCombination(Dfp a1, Dfp b1, Dfp a2, Dfp b2) {
/* 2838 */     return a1.multiply(b1).add(a2.multiply(b2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp linearCombination(double a1, Dfp b1, double a2, Dfp b2) {
/* 2845 */     return b1.multiply(a1).add(b2.multiply(a2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp linearCombination(Dfp a1, Dfp b1, Dfp a2, Dfp b2, Dfp a3, Dfp b3) {
/* 2854 */     return a1.multiply(b1).add(a2.multiply(b2)).add(a3.multiply(b3));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp linearCombination(double a1, Dfp b1, double a2, Dfp b2, double a3, Dfp b3) {
/* 2863 */     return b1.multiply(a1).add(b2.multiply(a2)).add(b3.multiply(a3));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp linearCombination(Dfp a1, Dfp b1, Dfp a2, Dfp b2, Dfp a3, Dfp b3, Dfp a4, Dfp b4) {
/* 2871 */     return a1.multiply(b1).add(a2.multiply(b2)).add(a3.multiply(b3)).add(a4.multiply(b4));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dfp linearCombination(double a1, Dfp b1, double a2, Dfp b2, double a3, Dfp b3, double a4, Dfp b4) {
/* 2879 */     return b1.multiply(a1).add(b2.multiply(a2)).add(b3.multiply(a3)).add(b4.multiply(a4));
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/dfp/Dfp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */