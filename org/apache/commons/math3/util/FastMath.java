/*      */ package org.apache.commons.math3.util;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*      */ public class FastMath
/*      */ {
/*      */   public static final double PI = 3.141592653589793D;
/*      */   public static final double E = 2.718281828459045D;
/*      */   static final int EXP_INT_TABLE_MAX_INDEX = 750;
/*      */   static final int EXP_INT_TABLE_LEN = 1500;
/*      */   static final int LN_MANT_LEN = 1024;
/*      */   static final int EXP_FRAC_TABLE_LEN = 1025;
/*   99 */   private static final double LOG_MAX_VALUE = StrictMath.log(Double.MAX_VALUE);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean RECOMPUTE_TABLES_AT_RUNTIME = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final double LN_2_A = 0.6931470632553101D;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final double LN_2_B = 1.1730463525082348E-7D;
/*      */ 
/*      */ 
/*      */   
/*  117 */   private static final double[][] LN_QUICK_COEF = new double[][] { { 1.0D, 5.669184079525E-24D }, { -0.25D, -0.25D }, { 0.3333333134651184D, 1.986821492305628E-8D }, { -0.25D, -6.663542893624021E-14D }, { 0.19999998807907104D, 1.1921056801463227E-8D }, { -0.1666666567325592D, -7.800414592973399E-9D }, { 0.1428571343421936D, 5.650007086920087E-9D }, { -0.12502530217170715D, -7.44321345601866E-11D }, { 0.11113807559013367D, 9.219544613762692E-9D } };
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
/*  130 */   private static final double[][] LN_HI_PREC_COEF = new double[][] { { 1.0D, -6.032174644509064E-23D }, { -0.25D, -0.25D }, { 0.3333333134651184D, 1.9868161777724352E-8D }, { -0.2499999701976776D, -2.957007209750105E-8D }, { 0.19999954104423523D, 1.5830993332061267E-10D }, { -0.16624879837036133D, -2.6033824355191673E-8D } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SINE_TABLE_LEN = 14;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  143 */   private static final double[] SINE_TABLE_A = new double[] { 0.0D, 0.1246747374534607D, 0.24740394949913025D, 0.366272509098053D, 0.4794255495071411D, 0.5850973129272461D, 0.6816387176513672D, 0.7675435543060303D, 0.8414709568023682D, 0.902267575263977D, 0.9489846229553223D, 0.9808930158615112D, 0.9974949359893799D, 0.9985313415527344D };
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
/*  162 */   private static final double[] SINE_TABLE_B = new double[] { 0.0D, -4.068233003401932E-9D, 9.755392680573412E-9D, 1.9987994582857286E-8D, -1.0902938113007961E-8D, -3.9986783938944604E-8D, 4.23719669792332E-8D, -5.207000323380292E-8D, 2.800552834259E-8D, 1.883511811213715E-8D, -3.5997360512765566E-9D, 4.116164446561962E-8D, 5.0614674548127384E-8D, -1.0129027912496858E-9D };
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
/*  181 */   private static final double[] COSINE_TABLE_A = new double[] { 1.0D, 0.9921976327896118D, 0.9689123630523682D, 0.9305076599121094D, 0.8775825500488281D, 0.8109631538391113D, 0.7316888570785522D, 0.6409968137741089D, 0.5403022766113281D, 0.4311765432357788D, 0.3153223395347595D, 0.19454771280288696D, 0.07073719799518585D, -0.05417713522911072D };
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
/*  200 */   private static final double[] COSINE_TABLE_B = new double[] { 0.0D, 3.4439717236742845E-8D, 5.865827662008209E-8D, -3.7999795083850525E-8D, 1.184154459111628E-8D, -3.43338934259355E-8D, 1.1795268640216787E-8D, 4.438921624363781E-8D, 2.925681159240093E-8D, -2.6437112632041807E-8D, 2.2860509143963117E-8D, -4.813899778443457E-9D, 3.6725170580355583E-9D, 2.0217439756338078E-10D };
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
/*  220 */   private static final double[] TANGENT_TABLE_A = new double[] { 0.0D, 0.1256551444530487D, 0.25534194707870483D, 0.3936265707015991D, 0.5463024377822876D, 0.7214844226837158D, 0.9315965175628662D, 1.1974215507507324D, 1.5574076175689697D, 2.092571258544922D, 3.0095696449279785D, 5.041914939880371D, 14.101419448852539D, -18.430862426757812D };
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
/*  239 */   private static final double[] TANGENT_TABLE_B = new double[] { 0.0D, -7.877917738262007E-9D, -2.5857668567479893E-8D, 5.2240336371356666E-9D, 5.206150291559893E-8D, 1.8307188599677033E-8D, -5.7618793749770706E-8D, 7.848361555046424E-8D, 1.0708593250394448E-7D, 1.7827257129423813E-8D, 2.893485277253286E-8D, 3.1660099222737955E-7D, 4.983191803254889E-7D, -3.356118100840571E-7D };
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
/*  258 */   private static final long[] RECIP_2PI = new long[] { 2935890503282001226L, 9154082963658192752L, 3952090531849364496L, 9193070505571053912L, 7910884519577875640L, 113236205062349959L, 4577762542105553359L, -5034868814120038111L, 4208363204685324176L, 5648769086999809661L, 2819561105158720014L, -4035746434778044925L, -302932621132653753L, -2644281811660520851L, -3183605296591799669L, 6722166367014452318L, -3512299194304650054L, -7278142539171889152L };
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
/*  279 */   private static final long[] PI_O_4_BITS = new long[] { -3958705157555305932L, -4267615245585081135L };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  287 */   private static final double[] EIGHTHS = new double[] { 0.0D, 0.125D, 0.25D, 0.375D, 0.5D, 0.625D, 0.75D, 0.875D, 1.0D, 1.125D, 1.25D, 1.375D, 1.5D, 1.625D };
/*      */ 
/*      */   
/*  290 */   private static final double[] CBRTTWO = new double[] { 0.6299605249474366D, 0.7937005259840998D, 1.0D, 1.2599210498948732D, 1.5874010519681994D };
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long HEX_40000000 = 1073741824L;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long MASK_30BITS = -1073741824L;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MASK_NON_SIGN_INT = 2147483647;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long MASK_NON_SIGN_LONG = 9223372036854775807L;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long MASK_DOUBLE_EXPONENT = 9218868437227405312L;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long MASK_DOUBLE_MANTISSA = 4503599627370495L;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long IMPLICIT_HIGH_BIT = 4503599627370496L;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final double TWO_POWER_52 = 4.503599627370496E15D;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final double F_1_3 = 0.3333333333333333D;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final double F_1_5 = 0.2D;
/*      */ 
/*      */   
/*      */   private static final double F_1_7 = 0.14285714285714285D;
/*      */ 
/*      */   
/*      */   private static final double F_1_9 = 0.1111111111111111D;
/*      */ 
/*      */   
/*      */   private static final double F_1_11 = 0.09090909090909091D;
/*      */ 
/*      */   
/*      */   private static final double F_1_13 = 0.07692307692307693D;
/*      */ 
/*      */   
/*      */   private static final double F_1_15 = 0.06666666666666667D;
/*      */ 
/*      */   
/*      */   private static final double F_1_17 = 0.058823529411764705D;
/*      */ 
/*      */   
/*      */   private static final double F_3_4 = 0.75D;
/*      */ 
/*      */   
/*      */   private static final double F_15_16 = 0.9375D;
/*      */ 
/*      */   
/*      */   private static final double F_13_14 = 0.9285714285714286D;
/*      */ 
/*      */   
/*      */   private static final double F_11_12 = 0.9166666666666666D;
/*      */ 
/*      */   
/*      */   private static final double F_9_10 = 0.9D;
/*      */ 
/*      */   
/*      */   private static final double F_7_8 = 0.875D;
/*      */ 
/*      */   
/*      */   private static final double F_5_6 = 0.8333333333333334D;
/*      */ 
/*      */   
/*      */   private static final double F_1_2 = 0.5D;
/*      */ 
/*      */   
/*      */   private static final double F_1_4 = 0.25D;
/*      */ 
/*      */ 
/*      */   
/*      */   private static double doubleHighPart(double d) {
/*  380 */     if (d > -Precision.SAFE_MIN && d < Precision.SAFE_MIN) {
/*  381 */       return d;
/*      */     }
/*  383 */     long xl = Double.doubleToRawLongBits(d);
/*  384 */     xl &= 0xFFFFFFFFC0000000L;
/*  385 */     return Double.longBitsToDouble(xl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double sqrt(double a) {
/*  394 */     return Math.sqrt(a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double cosh(double x) {
/*  402 */     if (x != x) {
/*  403 */       return x;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  411 */     if (x > 20.0D) {
/*  412 */       if (x >= LOG_MAX_VALUE) {
/*      */         
/*  414 */         double t = exp(0.5D * x);
/*  415 */         return 0.5D * t * t;
/*      */       } 
/*  417 */       return 0.5D * exp(x);
/*      */     } 
/*  419 */     if (x < -20.0D) {
/*  420 */       if (x <= -LOG_MAX_VALUE) {
/*      */         
/*  422 */         double t = exp(-0.5D * x);
/*  423 */         return 0.5D * t * t;
/*      */       } 
/*  425 */       return 0.5D * exp(-x);
/*      */     } 
/*      */ 
/*      */     
/*  429 */     double[] hiPrec = new double[2];
/*  430 */     if (x < 0.0D) {
/*  431 */       x = -x;
/*      */     }
/*  433 */     exp(x, 0.0D, hiPrec);
/*      */     
/*  435 */     double ya = hiPrec[0] + hiPrec[1];
/*  436 */     double yb = -(ya - hiPrec[0] - hiPrec[1]);
/*      */     
/*  438 */     double temp = ya * 1.073741824E9D;
/*  439 */     double yaa = ya + temp - temp;
/*  440 */     double yab = ya - yaa;
/*      */ 
/*      */     
/*  443 */     double recip = 1.0D / ya;
/*  444 */     temp = recip * 1.073741824E9D;
/*  445 */     double recipa = recip + temp - temp;
/*  446 */     double recipb = recip - recipa;
/*      */ 
/*      */     
/*  449 */     recipb += (1.0D - yaa * recipa - yaa * recipb - yab * recipa - yab * recipb) * recip;
/*      */     
/*  451 */     recipb += -yb * recip * recip;
/*      */ 
/*      */     
/*  454 */     temp = ya + recipa;
/*  455 */     yb += -(temp - ya - recipa);
/*  456 */     ya = temp;
/*  457 */     temp = ya + recipb;
/*  458 */     yb += -(temp - ya - recipb);
/*  459 */     ya = temp;
/*      */     
/*  461 */     double result = ya + yb;
/*  462 */     result *= 0.5D;
/*  463 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double sinh(double x) {
/*      */     double result;
/*  471 */     boolean negate = false;
/*  472 */     if (x != x) {
/*  473 */       return x;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  481 */     if (x > 20.0D) {
/*  482 */       if (x >= LOG_MAX_VALUE) {
/*      */         
/*  484 */         double t = exp(0.5D * x);
/*  485 */         return 0.5D * t * t;
/*      */       } 
/*  487 */       return 0.5D * exp(x);
/*      */     } 
/*  489 */     if (x < -20.0D) {
/*  490 */       if (x <= -LOG_MAX_VALUE) {
/*      */         
/*  492 */         double t = exp(-0.5D * x);
/*  493 */         return -0.5D * t * t;
/*      */       } 
/*  495 */       return -0.5D * exp(-x);
/*      */     } 
/*      */ 
/*      */     
/*  499 */     if (x == 0.0D) {
/*  500 */       return x;
/*      */     }
/*      */     
/*  503 */     if (x < 0.0D) {
/*  504 */       x = -x;
/*  505 */       negate = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  510 */     if (x > 0.25D) {
/*  511 */       double[] hiPrec = new double[2];
/*  512 */       exp(x, 0.0D, hiPrec);
/*      */       
/*  514 */       double ya = hiPrec[0] + hiPrec[1];
/*  515 */       double yb = -(ya - hiPrec[0] - hiPrec[1]);
/*      */       
/*  517 */       double temp = ya * 1.073741824E9D;
/*  518 */       double yaa = ya + temp - temp;
/*  519 */       double yab = ya - yaa;
/*      */ 
/*      */       
/*  522 */       double recip = 1.0D / ya;
/*  523 */       temp = recip * 1.073741824E9D;
/*  524 */       double recipa = recip + temp - temp;
/*  525 */       double recipb = recip - recipa;
/*      */ 
/*      */       
/*  528 */       recipb += (1.0D - yaa * recipa - yaa * recipb - yab * recipa - yab * recipb) * recip;
/*      */       
/*  530 */       recipb += -yb * recip * recip;
/*      */       
/*  532 */       recipa = -recipa;
/*  533 */       recipb = -recipb;
/*      */ 
/*      */       
/*  536 */       temp = ya + recipa;
/*  537 */       yb += -(temp - ya - recipa);
/*  538 */       ya = temp;
/*  539 */       temp = ya + recipb;
/*  540 */       yb += -(temp - ya - recipb);
/*  541 */       ya = temp;
/*      */       
/*  543 */       result = ya + yb;
/*  544 */       result *= 0.5D;
/*      */     } else {
/*      */       
/*  547 */       double[] hiPrec = new double[2];
/*  548 */       expm1(x, hiPrec);
/*      */       
/*  550 */       double ya = hiPrec[0] + hiPrec[1];
/*  551 */       double yb = -(ya - hiPrec[0] - hiPrec[1]);
/*      */ 
/*      */       
/*  554 */       double denom = 1.0D + ya;
/*  555 */       double denomr = 1.0D / denom;
/*  556 */       double denomb = -(denom - 1.0D - ya) + yb;
/*  557 */       double ratio = ya * denomr;
/*  558 */       double temp = ratio * 1.073741824E9D;
/*  559 */       double ra = ratio + temp - temp;
/*  560 */       double rb = ratio - ra;
/*      */       
/*  562 */       temp = denom * 1.073741824E9D;
/*  563 */       double za = denom + temp - temp;
/*  564 */       double zb = denom - za;
/*      */       
/*  566 */       rb += (ya - za * ra - za * rb - zb * ra - zb * rb) * denomr;
/*      */ 
/*      */       
/*  569 */       rb += yb * denomr;
/*  570 */       rb += -ya * denomb * denomr * denomr;
/*      */ 
/*      */       
/*  573 */       temp = ya + ra;
/*  574 */       yb += -(temp - ya - ra);
/*  575 */       ya = temp;
/*  576 */       temp = ya + rb;
/*  577 */       yb += -(temp - ya - rb);
/*  578 */       ya = temp;
/*      */       
/*  580 */       result = ya + yb;
/*  581 */       result *= 0.5D;
/*      */     } 
/*      */     
/*  584 */     if (negate) {
/*  585 */       result = -result;
/*      */     }
/*      */     
/*  588 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double tanh(double x) {
/*      */     double result;
/*  596 */     boolean negate = false;
/*      */     
/*  598 */     if (x != x) {
/*  599 */       return x;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  608 */     if (x > 20.0D) {
/*  609 */       return 1.0D;
/*      */     }
/*      */     
/*  612 */     if (x < -20.0D) {
/*  613 */       return -1.0D;
/*      */     }
/*      */     
/*  616 */     if (x == 0.0D) {
/*  617 */       return x;
/*      */     }
/*      */     
/*  620 */     if (x < 0.0D) {
/*  621 */       x = -x;
/*  622 */       negate = true;
/*      */     } 
/*      */ 
/*      */     
/*  626 */     if (x >= 0.5D) {
/*  627 */       double[] hiPrec = new double[2];
/*      */       
/*  629 */       exp(x * 2.0D, 0.0D, hiPrec);
/*      */       
/*  631 */       double ya = hiPrec[0] + hiPrec[1];
/*  632 */       double yb = -(ya - hiPrec[0] - hiPrec[1]);
/*      */ 
/*      */       
/*  635 */       double na = -1.0D + ya;
/*  636 */       double nb = -(na + 1.0D - ya);
/*  637 */       double temp = na + yb;
/*  638 */       nb += -(temp - na - yb);
/*  639 */       na = temp;
/*      */ 
/*      */       
/*  642 */       double da = 1.0D + ya;
/*  643 */       double db = -(da - 1.0D - ya);
/*  644 */       temp = da + yb;
/*  645 */       db += -(temp - da - yb);
/*  646 */       da = temp;
/*      */       
/*  648 */       temp = da * 1.073741824E9D;
/*  649 */       double daa = da + temp - temp;
/*  650 */       double dab = da - daa;
/*      */ 
/*      */       
/*  653 */       double ratio = na / da;
/*  654 */       temp = ratio * 1.073741824E9D;
/*  655 */       double ratioa = ratio + temp - temp;
/*  656 */       double ratiob = ratio - ratioa;
/*      */ 
/*      */       
/*  659 */       ratiob += (na - daa * ratioa - daa * ratiob - dab * ratioa - dab * ratiob) / da;
/*      */ 
/*      */       
/*  662 */       ratiob += nb / da;
/*      */       
/*  664 */       ratiob += -db * na / da / da;
/*      */       
/*  666 */       result = ratioa + ratiob;
/*      */     } else {
/*      */       
/*  669 */       double[] hiPrec = new double[2];
/*      */       
/*  671 */       expm1(x * 2.0D, hiPrec);
/*      */       
/*  673 */       double ya = hiPrec[0] + hiPrec[1];
/*  674 */       double yb = -(ya - hiPrec[0] - hiPrec[1]);
/*      */ 
/*      */       
/*  677 */       double na = ya;
/*  678 */       double nb = yb;
/*      */ 
/*      */       
/*  681 */       double da = 2.0D + ya;
/*  682 */       double db = -(da - 2.0D - ya);
/*  683 */       double temp = da + yb;
/*  684 */       db += -(temp - da - yb);
/*  685 */       da = temp;
/*      */       
/*  687 */       temp = da * 1.073741824E9D;
/*  688 */       double daa = da + temp - temp;
/*  689 */       double dab = da - daa;
/*      */ 
/*      */       
/*  692 */       double ratio = na / da;
/*  693 */       temp = ratio * 1.073741824E9D;
/*  694 */       double ratioa = ratio + temp - temp;
/*  695 */       double ratiob = ratio - ratioa;
/*      */ 
/*      */       
/*  698 */       ratiob += (na - daa * ratioa - daa * ratiob - dab * ratioa - dab * ratiob) / da;
/*      */ 
/*      */       
/*  701 */       ratiob += nb / da;
/*      */       
/*  703 */       ratiob += -db * na / da / da;
/*      */       
/*  705 */       result = ratioa + ratiob;
/*      */     } 
/*      */     
/*  708 */     if (negate) {
/*  709 */       result = -result;
/*      */     }
/*      */     
/*  712 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double acosh(double a) {
/*  720 */     return log(a + sqrt(a * a - 1.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double asinh(double a) {
/*      */     double absAsinh;
/*  728 */     boolean negative = false;
/*  729 */     if (a < 0.0D) {
/*  730 */       negative = true;
/*  731 */       a = -a;
/*      */     } 
/*      */ 
/*      */     
/*  735 */     if (a > 0.167D) {
/*  736 */       absAsinh = log(sqrt(a * a + 1.0D) + a);
/*      */     } else {
/*  738 */       double a2 = a * a;
/*  739 */       if (a > 0.097D) {
/*  740 */         absAsinh = a * (1.0D - a2 * (0.3333333333333333D - a2 * (0.2D - a2 * (0.14285714285714285D - a2 * (0.1111111111111111D - a2 * (0.09090909090909091D - a2 * (0.07692307692307693D - a2 * (0.06666666666666667D - a2 * 0.058823529411764705D * 0.9375D) * 0.9285714285714286D) * 0.9166666666666666D) * 0.9D) * 0.875D) * 0.8333333333333334D) * 0.75D) * 0.5D);
/*  741 */       } else if (a > 0.036D) {
/*  742 */         absAsinh = a * (1.0D - a2 * (0.3333333333333333D - a2 * (0.2D - a2 * (0.14285714285714285D - a2 * (0.1111111111111111D - a2 * (0.09090909090909091D - a2 * 0.07692307692307693D * 0.9166666666666666D) * 0.9D) * 0.875D) * 0.8333333333333334D) * 0.75D) * 0.5D);
/*  743 */       } else if (a > 0.0036D) {
/*  744 */         absAsinh = a * (1.0D - a2 * (0.3333333333333333D - a2 * (0.2D - a2 * (0.14285714285714285D - a2 * 0.1111111111111111D * 0.875D) * 0.8333333333333334D) * 0.75D) * 0.5D);
/*      */       } else {
/*  746 */         absAsinh = a * (1.0D - a2 * (0.3333333333333333D - a2 * 0.2D * 0.75D) * 0.5D);
/*      */       } 
/*      */     } 
/*      */     
/*  750 */     return negative ? -absAsinh : absAsinh;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double atanh(double a) {
/*      */     double absAtanh;
/*  758 */     boolean negative = false;
/*  759 */     if (a < 0.0D) {
/*  760 */       negative = true;
/*  761 */       a = -a;
/*      */     } 
/*      */ 
/*      */     
/*  765 */     if (a > 0.15D) {
/*  766 */       absAtanh = 0.5D * log((1.0D + a) / (1.0D - a));
/*      */     } else {
/*  768 */       double a2 = a * a;
/*  769 */       if (a > 0.087D) {
/*  770 */         absAtanh = a * (1.0D + a2 * (0.3333333333333333D + a2 * (0.2D + a2 * (0.14285714285714285D + a2 * (0.1111111111111111D + a2 * (0.09090909090909091D + a2 * (0.07692307692307693D + a2 * (0.06666666666666667D + a2 * 0.058823529411764705D))))))));
/*  771 */       } else if (a > 0.031D) {
/*  772 */         absAtanh = a * (1.0D + a2 * (0.3333333333333333D + a2 * (0.2D + a2 * (0.14285714285714285D + a2 * (0.1111111111111111D + a2 * (0.09090909090909091D + a2 * 0.07692307692307693D))))));
/*  773 */       } else if (a > 0.003D) {
/*  774 */         absAtanh = a * (1.0D + a2 * (0.3333333333333333D + a2 * (0.2D + a2 * (0.14285714285714285D + a2 * 0.1111111111111111D))));
/*      */       } else {
/*  776 */         absAtanh = a * (1.0D + a2 * (0.3333333333333333D + a2 * 0.2D));
/*      */       } 
/*      */     } 
/*      */     
/*  780 */     return negative ? -absAtanh : absAtanh;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double signum(double a) {
/*  789 */     return (a < 0.0D) ? -1.0D : ((a > 0.0D) ? 1.0D : a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float signum(float a) {
/*  798 */     return (a < 0.0F) ? -1.0F : ((a > 0.0F) ? 1.0F : a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double nextUp(double a) {
/*  806 */     return nextAfter(a, Double.POSITIVE_INFINITY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float nextUp(float a) {
/*  814 */     return nextAfter(a, Double.POSITIVE_INFINITY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double nextDown(double a) {
/*  823 */     return nextAfter(a, Double.NEGATIVE_INFINITY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float nextDown(float a) {
/*  832 */     return nextAfter(a, Double.NEGATIVE_INFINITY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double random() {
/*  840 */     return Math.random();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double exp(double x) {
/*  864 */     return exp(x, 0.0D, null);
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
/*      */   private static double exp(double x, double extra, double[] hiPrec) {
/*      */     double result;
/*  877 */     int intVal = (int)x;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  883 */     if (x < 0.0D) {
/*      */ 
/*      */ 
/*      */       
/*  887 */       if (x < -746.0D) {
/*  888 */         if (hiPrec != null) {
/*  889 */           hiPrec[0] = 0.0D;
/*  890 */           hiPrec[1] = 0.0D;
/*      */         } 
/*  892 */         return 0.0D;
/*      */       } 
/*      */       
/*  895 */       if (intVal < -709) {
/*      */         
/*  897 */         double d = exp(x + 40.19140625D, extra, hiPrec) / 2.85040095144011776E17D;
/*  898 */         if (hiPrec != null) {
/*  899 */           hiPrec[0] = hiPrec[0] / 2.85040095144011776E17D;
/*  900 */           hiPrec[1] = hiPrec[1] / 2.85040095144011776E17D;
/*      */         } 
/*  902 */         return d;
/*      */       } 
/*      */       
/*  905 */       if (intVal == -709) {
/*      */         
/*  907 */         double d = exp(x + 1.494140625D, extra, hiPrec) / 4.455505956692757D;
/*  908 */         if (hiPrec != null) {
/*  909 */           hiPrec[0] = hiPrec[0] / 4.455505956692757D;
/*  910 */           hiPrec[1] = hiPrec[1] / 4.455505956692757D;
/*      */         } 
/*  912 */         return d;
/*      */       } 
/*      */       
/*  915 */       intVal--;
/*      */     
/*      */     }
/*  918 */     else if (intVal > 709) {
/*  919 */       if (hiPrec != null) {
/*  920 */         hiPrec[0] = Double.POSITIVE_INFINITY;
/*  921 */         hiPrec[1] = 0.0D;
/*      */       } 
/*  923 */       return Double.POSITIVE_INFINITY;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  928 */     double intPartA = ExpIntTable.EXP_INT_TABLE_A[750 + intVal];
/*  929 */     double intPartB = ExpIntTable.EXP_INT_TABLE_B[750 + intVal];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  935 */     int intFrac = (int)((x - intVal) * 1024.0D);
/*  936 */     double fracPartA = ExpFracTable.EXP_FRAC_TABLE_A[intFrac];
/*  937 */     double fracPartB = ExpFracTable.EXP_FRAC_TABLE_B[intFrac];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  943 */     double epsilon = x - intVal + intFrac / 1024.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  952 */     double z = 0.04168701738764507D;
/*  953 */     z = z * epsilon + 0.1666666505023083D;
/*  954 */     z = z * epsilon + 0.5000000000042687D;
/*  955 */     z = z * epsilon + 1.0D;
/*  956 */     z = z * epsilon + -3.940510424527919E-20D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  963 */     double tempA = intPartA * fracPartA;
/*  964 */     double tempB = intPartA * fracPartB + intPartB * fracPartA + intPartB * fracPartB;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  970 */     double tempC = tempB + tempA;
/*      */ 
/*      */ 
/*      */     
/*  974 */     if (tempC == Double.POSITIVE_INFINITY) {
/*  975 */       return Double.POSITIVE_INFINITY;
/*      */     }
/*      */ 
/*      */     
/*  979 */     if (extra != 0.0D) {
/*  980 */       result = tempC * extra * z + tempC * extra + tempC * z + tempB + tempA;
/*      */     } else {
/*  982 */       result = tempC * z + tempB + tempA;
/*      */     } 
/*      */     
/*  985 */     if (hiPrec != null) {
/*      */       
/*  987 */       hiPrec[0] = tempA;
/*  988 */       hiPrec[1] = tempC * extra * z + tempC * extra + tempC * z + tempB;
/*      */     } 
/*      */     
/*  991 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double expm1(double x) {
/*  999 */     return expm1(x, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double expm1(double x, double[] hiPrecOut) {
/* 1008 */     if (x != x || x == 0.0D) {
/* 1009 */       return x;
/*      */     }
/*      */     
/* 1012 */     if (x <= -1.0D || x >= 1.0D) {
/*      */ 
/*      */       
/* 1015 */       double[] hiPrec = new double[2];
/* 1016 */       exp(x, 0.0D, hiPrec);
/* 1017 */       if (x > 0.0D) {
/* 1018 */         return -1.0D + hiPrec[0] + hiPrec[1];
/*      */       }
/* 1020 */       double ra = -1.0D + hiPrec[0];
/* 1021 */       double rb = -(ra + 1.0D - hiPrec[0]);
/* 1022 */       rb += hiPrec[1];
/* 1023 */       return ra + rb;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1030 */     boolean negative = false;
/*      */     
/* 1032 */     if (x < 0.0D) {
/* 1033 */       x = -x;
/* 1034 */       negative = true;
/*      */     } 
/*      */ 
/*      */     
/* 1038 */     int intFrac = (int)(x * 1024.0D);
/* 1039 */     double tempA = ExpFracTable.EXP_FRAC_TABLE_A[intFrac] - 1.0D;
/* 1040 */     double tempB = ExpFracTable.EXP_FRAC_TABLE_B[intFrac];
/*      */     
/* 1042 */     double d1 = tempA + tempB;
/* 1043 */     tempB = -(d1 - tempA - tempB);
/* 1044 */     tempA = d1;
/*      */     
/* 1046 */     d1 = tempA * 1.073741824E9D;
/* 1047 */     double baseA = tempA + d1 - d1;
/* 1048 */     double baseB = tempB + tempA - baseA;
/*      */     
/* 1050 */     double epsilon = x - intFrac / 1024.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1055 */     double zb = 0.008336750013465571D;
/* 1056 */     zb = zb * epsilon + 0.041666663879186654D;
/* 1057 */     zb = zb * epsilon + 0.16666666666745392D;
/* 1058 */     zb = zb * epsilon + 0.49999999999999994D;
/* 1059 */     zb *= epsilon;
/* 1060 */     zb *= epsilon;
/*      */     
/* 1062 */     double za = epsilon;
/* 1063 */     double temp = za + zb;
/* 1064 */     zb = -(temp - za - zb);
/* 1065 */     za = temp;
/*      */     
/* 1067 */     temp = za * 1.073741824E9D;
/* 1068 */     temp = za + temp - temp;
/* 1069 */     zb += za - temp;
/* 1070 */     za = temp;
/*      */ 
/*      */     
/* 1073 */     double ya = za * baseA;
/*      */     
/* 1075 */     temp = ya + za * baseB;
/* 1076 */     double yb = -(temp - ya - za * baseB);
/* 1077 */     ya = temp;
/*      */     
/* 1079 */     temp = ya + zb * baseA;
/* 1080 */     yb += -(temp - ya - zb * baseA);
/* 1081 */     ya = temp;
/*      */     
/* 1083 */     temp = ya + zb * baseB;
/* 1084 */     yb += -(temp - ya - zb * baseB);
/* 1085 */     ya = temp;
/*      */ 
/*      */ 
/*      */     
/* 1089 */     temp = ya + baseA;
/* 1090 */     yb += -(temp - baseA - ya);
/* 1091 */     ya = temp;
/*      */     
/* 1093 */     temp = ya + za;
/*      */     
/* 1095 */     yb += -(temp - ya - za);
/* 1096 */     ya = temp;
/*      */     
/* 1098 */     temp = ya + baseB;
/*      */     
/* 1100 */     yb += -(temp - ya - baseB);
/* 1101 */     ya = temp;
/*      */     
/* 1103 */     temp = ya + zb;
/*      */     
/* 1105 */     yb += -(temp - ya - zb);
/* 1106 */     ya = temp;
/*      */     
/* 1108 */     if (negative) {
/*      */       
/* 1110 */       double denom = 1.0D + ya;
/* 1111 */       double denomr = 1.0D / denom;
/* 1112 */       double denomb = -(denom - 1.0D - ya) + yb;
/* 1113 */       double ratio = ya * denomr;
/* 1114 */       temp = ratio * 1.073741824E9D;
/* 1115 */       double ra = ratio + temp - temp;
/* 1116 */       double rb = ratio - ra;
/*      */       
/* 1118 */       temp = denom * 1.073741824E9D;
/* 1119 */       za = denom + temp - temp;
/* 1120 */       zb = denom - za;
/*      */       
/* 1122 */       rb += (ya - za * ra - za * rb - zb * ra - zb * rb) * denomr;
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
/* 1133 */       rb += yb * denomr;
/* 1134 */       rb += -ya * denomb * denomr * denomr;
/*      */ 
/*      */       
/* 1137 */       ya = -ra;
/* 1138 */       yb = -rb;
/*      */     } 
/*      */     
/* 1141 */     if (hiPrecOut != null) {
/* 1142 */       hiPrecOut[0] = ya;
/* 1143 */       hiPrecOut[1] = yb;
/*      */     } 
/*      */     
/* 1146 */     return ya + yb;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double log(double x) {
/* 1156 */     return log(x, (double[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double log(double x, double[] hiPrec) {
/* 1166 */     if (x == 0.0D) {
/* 1167 */       return Double.NEGATIVE_INFINITY;
/*      */     }
/* 1169 */     long bits = Double.doubleToRawLongBits(x);
/*      */ 
/*      */     
/* 1172 */     if (((bits & Long.MIN_VALUE) != 0L || x != x) && x != 0.0D) {
/* 1173 */       if (hiPrec != null) {
/* 1174 */         hiPrec[0] = Double.NaN;
/*      */       }
/*      */       
/* 1177 */       return Double.NaN;
/*      */     } 
/*      */ 
/*      */     
/* 1181 */     if (x == Double.POSITIVE_INFINITY) {
/* 1182 */       if (hiPrec != null) {
/* 1183 */         hiPrec[0] = Double.POSITIVE_INFINITY;
/*      */       }
/*      */       
/* 1186 */       return Double.POSITIVE_INFINITY;
/*      */     } 
/*      */ 
/*      */     
/* 1190 */     int exp = (int)(bits >> 52L) - 1023;
/*      */     
/* 1192 */     if ((bits & 0x7FF0000000000000L) == 0L) {
/*      */       
/* 1194 */       if (x == 0.0D) {
/*      */         
/* 1196 */         if (hiPrec != null) {
/* 1197 */           hiPrec[0] = Double.NEGATIVE_INFINITY;
/*      */         }
/*      */         
/* 1200 */         return Double.NEGATIVE_INFINITY;
/*      */       } 
/*      */ 
/*      */       
/* 1204 */       bits <<= 1L;
/* 1205 */       while ((bits & 0x10000000000000L) == 0L) {
/* 1206 */         exp--;
/* 1207 */         bits <<= 1L;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1212 */     if ((exp == -1 || exp == 0) && x < 1.01D && x > 0.99D && hiPrec == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1217 */       double xa = x - 1.0D;
/* 1218 */       double xb = xa - x + 1.0D;
/* 1219 */       double tmp = xa * 1.073741824E9D;
/* 1220 */       double aa = xa + tmp - tmp;
/* 1221 */       double ab = xa - aa;
/* 1222 */       xa = aa;
/* 1223 */       xb = ab;
/*      */       
/* 1225 */       double[] lnCoef_last = LN_QUICK_COEF[LN_QUICK_COEF.length - 1];
/* 1226 */       double ya = lnCoef_last[0];
/* 1227 */       double yb = lnCoef_last[1];
/*      */       
/* 1229 */       for (int i = LN_QUICK_COEF.length - 2; i >= 0; i--) {
/*      */         
/* 1231 */         aa = ya * xa;
/* 1232 */         ab = ya * xb + yb * xa + yb * xb;
/*      */         
/* 1234 */         tmp = aa * 1.073741824E9D;
/* 1235 */         ya = aa + tmp - tmp;
/* 1236 */         yb = aa - ya + ab;
/*      */ 
/*      */         
/* 1239 */         double[] lnCoef_i = LN_QUICK_COEF[i];
/* 1240 */         aa = ya + lnCoef_i[0];
/* 1241 */         ab = yb + lnCoef_i[1];
/*      */         
/* 1243 */         tmp = aa * 1.073741824E9D;
/* 1244 */         ya = aa + tmp - tmp;
/* 1245 */         yb = aa - ya + ab;
/*      */       } 
/*      */ 
/*      */       
/* 1249 */       aa = ya * xa;
/* 1250 */       ab = ya * xb + yb * xa + yb * xb;
/*      */       
/* 1252 */       tmp = aa * 1.073741824E9D;
/* 1253 */       ya = aa + tmp - tmp;
/* 1254 */       yb = aa - ya + ab;
/*      */       
/* 1256 */       return ya + yb;
/*      */     } 
/*      */ 
/*      */     
/* 1260 */     double[] lnm = lnMant.LN_MANT[(int)((bits & 0xFFC0000000000L) >> 42L)];
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
/* 1271 */     double epsilon = (bits & 0x3FFFFFFFFFFL) / (4.503599627370496E15D + (bits & 0xFFC0000000000L));
/*      */     
/* 1273 */     double lnza = 0.0D;
/* 1274 */     double lnzb = 0.0D;
/*      */     
/* 1276 */     if (hiPrec != null) {
/*      */       
/* 1278 */       double tmp = epsilon * 1.073741824E9D;
/* 1279 */       double aa = epsilon + tmp - tmp;
/* 1280 */       double ab = epsilon - aa;
/* 1281 */       double xa = aa;
/* 1282 */       double xb = ab;
/*      */ 
/*      */       
/* 1285 */       double numer = (bits & 0x3FFFFFFFFFFL);
/* 1286 */       double denom = 4.503599627370496E15D + (bits & 0xFFC0000000000L);
/* 1287 */       aa = numer - xa * denom - xb * denom;
/* 1288 */       xb += aa / denom;
/*      */ 
/*      */       
/* 1291 */       double[] lnCoef_last = LN_HI_PREC_COEF[LN_HI_PREC_COEF.length - 1];
/* 1292 */       double ya = lnCoef_last[0];
/* 1293 */       double yb = lnCoef_last[1];
/*      */       
/* 1295 */       for (int i = LN_HI_PREC_COEF.length - 2; i >= 0; i--) {
/*      */         
/* 1297 */         aa = ya * xa;
/* 1298 */         ab = ya * xb + yb * xa + yb * xb;
/*      */         
/* 1300 */         tmp = aa * 1.073741824E9D;
/* 1301 */         ya = aa + tmp - tmp;
/* 1302 */         yb = aa - ya + ab;
/*      */ 
/*      */         
/* 1305 */         double[] lnCoef_i = LN_HI_PREC_COEF[i];
/* 1306 */         aa = ya + lnCoef_i[0];
/* 1307 */         ab = yb + lnCoef_i[1];
/*      */         
/* 1309 */         tmp = aa * 1.073741824E9D;
/* 1310 */         ya = aa + tmp - tmp;
/* 1311 */         yb = aa - ya + ab;
/*      */       } 
/*      */ 
/*      */       
/* 1315 */       aa = ya * xa;
/* 1316 */       ab = ya * xb + yb * xa + yb * xb;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1324 */       lnza = aa + ab;
/* 1325 */       lnzb = -(lnza - aa - ab);
/*      */     }
/*      */     else {
/*      */       
/* 1329 */       lnza = -0.16624882440418567D;
/* 1330 */       lnza = lnza * epsilon + 0.19999954120254515D;
/* 1331 */       lnza = lnza * epsilon + -0.2499999997677497D;
/* 1332 */       lnza = lnza * epsilon + 0.3333333333332802D;
/* 1333 */       lnza = lnza * epsilon + -0.5D;
/* 1334 */       lnza = lnza * epsilon + 1.0D;
/* 1335 */       lnza *= epsilon;
/*      */     } 
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
/* 1352 */     double a = 0.6931470632553101D * exp;
/* 1353 */     double b = 0.0D;
/* 1354 */     double c = a + lnm[0];
/* 1355 */     double d = -(c - a - lnm[0]);
/* 1356 */     a = c;
/* 1357 */     b += d;
/*      */     
/* 1359 */     c = a + lnza;
/* 1360 */     d = -(c - a - lnza);
/* 1361 */     a = c;
/* 1362 */     b += d;
/*      */     
/* 1364 */     c = a + 1.1730463525082348E-7D * exp;
/* 1365 */     d = -(c - a - 1.1730463525082348E-7D * exp);
/* 1366 */     a = c;
/* 1367 */     b += d;
/*      */     
/* 1369 */     c = a + lnm[1];
/* 1370 */     d = -(c - a - lnm[1]);
/* 1371 */     a = c;
/* 1372 */     b += d;
/*      */     
/* 1374 */     c = a + lnzb;
/* 1375 */     d = -(c - a - lnzb);
/* 1376 */     a = c;
/* 1377 */     b += d;
/*      */     
/* 1379 */     if (hiPrec != null) {
/* 1380 */       hiPrec[0] = a;
/* 1381 */       hiPrec[1] = b;
/*      */     } 
/*      */     
/* 1384 */     return a + b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double log1p(double x) {
/* 1394 */     if (x == -1.0D) {
/* 1395 */       return Double.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/* 1398 */     if (x == Double.POSITIVE_INFINITY) {
/* 1399 */       return Double.POSITIVE_INFINITY;
/*      */     }
/*      */     
/* 1402 */     if (x > 1.0E-6D || x < -1.0E-6D) {
/*      */       
/* 1404 */       double xpa = 1.0D + x;
/* 1405 */       double xpb = -(xpa - 1.0D - x);
/*      */       
/* 1407 */       double[] hiPrec = new double[2];
/* 1408 */       double lores = log(xpa, hiPrec);
/* 1409 */       if (Double.isInfinite(lores)) {
/* 1410 */         return lores;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1415 */       double fx1 = xpb / xpa;
/* 1416 */       double epsilon = 0.5D * fx1 + 1.0D;
/* 1417 */       return epsilon * fx1 + hiPrec[1] + hiPrec[0];
/*      */     } 
/*      */     
/* 1420 */     double y = (x * 0.3333333333333333D - 0.5D) * x + 1.0D;
/* 1421 */     return y * x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double log10(double x) {
/* 1430 */     double[] hiPrec = new double[2];
/*      */     
/* 1432 */     double lores = log(x, hiPrec);
/* 1433 */     if (Double.isInfinite(lores)) {
/* 1434 */       return lores;
/*      */     }
/*      */     
/* 1437 */     double tmp = hiPrec[0] * 1.073741824E9D;
/* 1438 */     double lna = hiPrec[0] + tmp - tmp;
/* 1439 */     double lnb = hiPrec[0] - lna + hiPrec[1];
/*      */     
/* 1441 */     double rln10a = 0.4342944622039795D;
/* 1442 */     double rln10b = 1.9699272335463627E-8D;
/*      */     
/* 1444 */     return 1.9699272335463627E-8D * lnb + 1.9699272335463627E-8D * lna + 0.4342944622039795D * lnb + 0.4342944622039795D * lna;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double log(double base, double x) {
/* 1464 */     return log(x) / log(base);
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
/*      */   public static double pow(double x, double y) {
/* 1476 */     if (y == 0.0D)
/*      */     {
/* 1478 */       return 1.0D;
/*      */     }
/*      */     
/* 1481 */     long yBits = Double.doubleToRawLongBits(y);
/* 1482 */     int yRawExp = (int)((yBits & 0x7FF0000000000000L) >> 52L);
/* 1483 */     long yRawMantissa = yBits & 0xFFFFFFFFFFFFFL;
/* 1484 */     long xBits = Double.doubleToRawLongBits(x);
/* 1485 */     int xRawExp = (int)((xBits & 0x7FF0000000000000L) >> 52L);
/* 1486 */     long xRawMantissa = xBits & 0xFFFFFFFFFFFFFL;
/*      */     
/* 1488 */     if (yRawExp > 1085) {
/*      */ 
/*      */       
/* 1491 */       if ((yRawExp == 2047 && yRawMantissa != 0L) || (xRawExp == 2047 && xRawMantissa != 0L))
/*      */       {
/*      */         
/* 1494 */         return Double.NaN; } 
/* 1495 */       if (xRawExp == 1023 && xRawMantissa == 0L) {
/*      */         
/* 1497 */         if (yRawExp == 2047)
/*      */         {
/* 1499 */           return Double.NaN;
/*      */         }
/*      */         
/* 1502 */         return 1.0D;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1510 */       if ((((y > 0.0D) ? 1 : 0) ^ ((xRawExp < 1023) ? 1 : 0)) != 0)
/*      */       {
/*      */         
/* 1513 */         return Double.POSITIVE_INFINITY;
/*      */       }
/*      */ 
/*      */       
/* 1517 */       return 0.0D;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1524 */     if (yRawExp >= 1023) {
/*      */       
/* 1526 */       long yFullMantissa = 0x10000000000000L | yRawMantissa;
/* 1527 */       if (yRawExp < 1075) {
/*      */         
/* 1529 */         long integralMask = -1L << 1075 - yRawExp;
/* 1530 */         if ((yFullMantissa & integralMask) == yFullMantissa)
/*      */         {
/* 1532 */           long l = yFullMantissa >> 1075 - yRawExp;
/* 1533 */           return pow(x, (y < 0.0D) ? -l : l);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1538 */         long l = yFullMantissa << yRawExp - 1075;
/* 1539 */         return pow(x, (y < 0.0D) ? -l : l);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1545 */     if (x == 0.0D)
/*      */     {
/*      */       
/* 1548 */       return (y < 0.0D) ? Double.POSITIVE_INFINITY : 0.0D; } 
/* 1549 */     if (xRawExp == 2047) {
/* 1550 */       if (xRawMantissa == 0L)
/*      */       {
/* 1552 */         return (y < 0.0D) ? 0.0D : Double.POSITIVE_INFINITY;
/*      */       }
/*      */       
/* 1555 */       return Double.NaN;
/*      */     } 
/* 1557 */     if (x < 0.0D)
/*      */     {
/* 1559 */       return Double.NaN;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1565 */     double tmp = y * 1.073741824E9D;
/* 1566 */     double ya = y + tmp - tmp;
/* 1567 */     double yb = y - ya;
/*      */ 
/*      */     
/* 1570 */     double[] lns = new double[2];
/* 1571 */     double lores = log(x, lns);
/* 1572 */     if (Double.isInfinite(lores)) {
/* 1573 */       return lores;
/*      */     }
/*      */     
/* 1576 */     double lna = lns[0];
/* 1577 */     double lnb = lns[1];
/*      */ 
/*      */     
/* 1580 */     double tmp1 = lna * 1.073741824E9D;
/* 1581 */     double tmp2 = lna + tmp1 - tmp1;
/* 1582 */     lnb += lna - tmp2;
/* 1583 */     lna = tmp2;
/*      */ 
/*      */     
/* 1586 */     double aa = lna * ya;
/* 1587 */     double ab = lna * yb + lnb * ya + lnb * yb;
/*      */     
/* 1589 */     lna = aa + ab;
/* 1590 */     lnb = -(lna - aa - ab);
/*      */     
/* 1592 */     double z = 0.008333333333333333D;
/* 1593 */     z = z * lnb + 0.041666666666666664D;
/* 1594 */     z = z * lnb + 0.16666666666666666D;
/* 1595 */     z = z * lnb + 0.5D;
/* 1596 */     z = z * lnb + 1.0D;
/* 1597 */     z *= lnb;
/*      */     
/* 1599 */     double result = exp(lna, z, null);
/*      */     
/* 1601 */     return result;
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
/*      */ 
/*      */   
/*      */   public static double pow(double d, int e) {
/* 1619 */     return pow(d, e);
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
/*      */   public static double pow(double d, long e) {
/* 1631 */     if (e == 0L)
/* 1632 */       return 1.0D; 
/* 1633 */     if (e > 0L) {
/* 1634 */       return ((new Split(d)).pow(e)).full;
/*      */     }
/* 1636 */     return ((new Split(d)).reciprocal().pow(-e)).full;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Split
/*      */   {
/* 1644 */     public static final Split NAN = new Split(Double.NaN, 0.0D);
/*      */ 
/*      */     
/* 1647 */     public static final Split POSITIVE_INFINITY = new Split(Double.POSITIVE_INFINITY, 0.0D);
/*      */ 
/*      */     
/* 1650 */     public static final Split NEGATIVE_INFINITY = new Split(Double.NEGATIVE_INFINITY, 0.0D);
/*      */ 
/*      */ 
/*      */     
/*      */     private final double full;
/*      */ 
/*      */     
/*      */     private final double high;
/*      */ 
/*      */     
/*      */     private final double low;
/*      */ 
/*      */ 
/*      */     
/*      */     Split(double x) {
/* 1665 */       this.full = x;
/* 1666 */       this.high = Double.longBitsToDouble(Double.doubleToRawLongBits(x) & 0xFFFFFFFFF8000000L);
/* 1667 */       this.low = x - this.high;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Split(double high, double low) {
/* 1675 */       this((high == 0.0D) ? ((low == 0.0D && Double.doubleToRawLongBits(high) == Long.MIN_VALUE) ? -0.0D : low) : (high + low), high, low);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Split(double full, double high, double low) {
/* 1684 */       this.full = full;
/* 1685 */       this.high = high;
/* 1686 */       this.low = low;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Split multiply(Split b) {
/* 1695 */       Split mulBasic = new Split(this.full * b.full);
/* 1696 */       double mulError = this.low * b.low - mulBasic.full - this.high * b.high - this.low * b.high - this.high * b.low;
/* 1697 */       return new Split(mulBasic.high, mulBasic.low + mulError);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Split reciprocal() {
/* 1705 */       double approximateInv = 1.0D / this.full;
/* 1706 */       Split splitInv = new Split(approximateInv);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1711 */       Split product = multiply(splitInv);
/* 1712 */       double error = product.high - 1.0D + product.low;
/*      */ 
/*      */       
/* 1715 */       return Double.isNaN(error) ? splitInv : new Split(splitInv.high, splitInv.low - error / this.full);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Split pow(long e) {
/* 1727 */       Split result = new Split(1.0D);
/*      */ 
/*      */       
/* 1730 */       Split d2p = new Split(this.full, this.high, this.low);
/*      */       long p;
/* 1732 */       for (p = e; p != 0L; p >>>= 1L) {
/*      */         
/* 1734 */         if ((p & 0x1L) != 0L)
/*      */         {
/* 1736 */           result = result.multiply(d2p);
/*      */         }
/*      */ 
/*      */         
/* 1740 */         d2p = d2p.multiply(d2p);
/*      */       } 
/*      */ 
/*      */       
/* 1744 */       if (Double.isNaN(result.full)) {
/* 1745 */         if (Double.isNaN(this.full)) {
/* 1746 */           return NAN;
/*      */         }
/*      */ 
/*      */         
/* 1750 */         if (FastMath.abs(this.full) < 1.0D)
/* 1751 */           return new Split(FastMath.copySign(0.0D, this.full), 0.0D); 
/* 1752 */         if (this.full < 0.0D && (e & 0x1L) == 1L) {
/* 1753 */           return NEGATIVE_INFINITY;
/*      */         }
/* 1755 */         return POSITIVE_INFINITY;
/*      */       } 
/*      */ 
/*      */       
/* 1759 */       return result;
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
/*      */ 
/*      */ 
/*      */   
/*      */   private static double polySine(double x) {
/* 1774 */     double x2 = x * x;
/*      */     
/* 1776 */     double p = 2.7553817452272217E-6D;
/* 1777 */     p = p * x2 + -1.9841269659586505E-4D;
/* 1778 */     p = p * x2 + 0.008333333333329196D;
/* 1779 */     p = p * x2 + -0.16666666666666666D;
/*      */ 
/*      */     
/* 1782 */     p = p * x2 * x;
/*      */     
/* 1784 */     return p;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double polyCosine(double x) {
/* 1794 */     double x2 = x * x;
/*      */     
/* 1796 */     double p = 2.479773539153719E-5D;
/* 1797 */     p = p * x2 + -0.0013888888689039883D;
/* 1798 */     p = p * x2 + 0.041666666666621166D;
/* 1799 */     p = p * x2 + -0.49999999999999994D;
/* 1800 */     p *= x2;
/*      */     
/* 1802 */     return p;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double sinQ(double xa, double xb) {
/* 1813 */     int idx = (int)(xa * 8.0D + 0.5D);
/* 1814 */     double epsilon = xa - EIGHTHS[idx];
/*      */ 
/*      */     
/* 1817 */     double sintA = SINE_TABLE_A[idx];
/* 1818 */     double sintB = SINE_TABLE_B[idx];
/* 1819 */     double costA = COSINE_TABLE_A[idx];
/* 1820 */     double costB = COSINE_TABLE_B[idx];
/*      */ 
/*      */     
/* 1823 */     double sinEpsA = epsilon;
/* 1824 */     double sinEpsB = polySine(epsilon);
/* 1825 */     double cosEpsA = 1.0D;
/* 1826 */     double cosEpsB = polyCosine(epsilon);
/*      */ 
/*      */     
/* 1829 */     double temp = sinEpsA * 1.073741824E9D;
/* 1830 */     double temp2 = sinEpsA + temp - temp;
/* 1831 */     sinEpsB += sinEpsA - temp2;
/* 1832 */     sinEpsA = temp2;
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
/* 1858 */     double a = 0.0D;
/* 1859 */     double b = 0.0D;
/*      */     
/* 1861 */     double t = sintA;
/* 1862 */     double c = a + t;
/* 1863 */     double d = -(c - a - t);
/* 1864 */     a = c;
/* 1865 */     b += d;
/*      */     
/* 1867 */     t = costA * sinEpsA;
/* 1868 */     c = a + t;
/* 1869 */     d = -(c - a - t);
/* 1870 */     a = c;
/* 1871 */     b += d;
/*      */     
/* 1873 */     b = b + sintA * cosEpsB + costA * sinEpsB;
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
/* 1888 */     b = b + sintB + costB * sinEpsA + sintB * cosEpsB + costB * sinEpsB;
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
/* 1915 */     if (xb != 0.0D) {
/* 1916 */       t = ((costA + costB) * (1.0D + cosEpsB) - (sintA + sintB) * (sinEpsA + sinEpsB)) * xb;
/*      */       
/* 1918 */       c = a + t;
/* 1919 */       d = -(c - a - t);
/* 1920 */       a = c;
/* 1921 */       b += d;
/*      */     } 
/*      */     
/* 1924 */     double result = a + b;
/*      */     
/* 1926 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double cosQ(double xa, double xb) {
/* 1937 */     double pi2a = 1.5707963267948966D;
/* 1938 */     double pi2b = 6.123233995736766E-17D;
/*      */     
/* 1940 */     double a = 1.5707963267948966D - xa;
/* 1941 */     double b = -(a - 1.5707963267948966D + xa);
/* 1942 */     b += 6.123233995736766E-17D - xb;
/*      */     
/* 1944 */     return sinQ(a, b);
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
/*      */   private static double tanQ(double xa, double xb, boolean cotanFlag) {
/* 1957 */     int idx = (int)(xa * 8.0D + 0.5D);
/* 1958 */     double epsilon = xa - EIGHTHS[idx];
/*      */ 
/*      */     
/* 1961 */     double sintA = SINE_TABLE_A[idx];
/* 1962 */     double sintB = SINE_TABLE_B[idx];
/* 1963 */     double costA = COSINE_TABLE_A[idx];
/* 1964 */     double costB = COSINE_TABLE_B[idx];
/*      */ 
/*      */     
/* 1967 */     double sinEpsA = epsilon;
/* 1968 */     double sinEpsB = polySine(epsilon);
/* 1969 */     double cosEpsA = 1.0D;
/* 1970 */     double cosEpsB = polyCosine(epsilon);
/*      */ 
/*      */     
/* 1973 */     double temp = sinEpsA * 1.073741824E9D;
/* 1974 */     double temp2 = sinEpsA + temp - temp;
/* 1975 */     sinEpsB += sinEpsA - temp2;
/* 1976 */     sinEpsA = temp2;
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
/* 2001 */     double a = 0.0D;
/* 2002 */     double b = 0.0D;
/*      */ 
/*      */     
/* 2005 */     double t = sintA;
/* 2006 */     double c = a + t;
/* 2007 */     double d = -(c - a - t);
/* 2008 */     a = c;
/* 2009 */     b += d;
/*      */     
/* 2011 */     t = costA * sinEpsA;
/* 2012 */     c = a + t;
/* 2013 */     d = -(c - a - t);
/* 2014 */     a = c;
/* 2015 */     b += d;
/*      */     
/* 2017 */     b += sintA * cosEpsB + costA * sinEpsB;
/* 2018 */     b += sintB + costB * sinEpsA + sintB * cosEpsB + costB * sinEpsB;
/*      */     
/* 2020 */     double sina = a + b;
/* 2021 */     double sinb = -(sina - a - b);
/*      */ 
/*      */ 
/*      */     
/* 2025 */     a = b = c = d = 0.0D;
/*      */     
/* 2027 */     t = costA * 1.0D;
/* 2028 */     c = a + t;
/* 2029 */     d = -(c - a - t);
/* 2030 */     a = c;
/* 2031 */     b += d;
/*      */     
/* 2033 */     t = -sintA * sinEpsA;
/* 2034 */     c = a + t;
/* 2035 */     d = -(c - a - t);
/* 2036 */     a = c;
/* 2037 */     b += d;
/*      */     
/* 2039 */     b += costB * 1.0D + costA * cosEpsB + costB * cosEpsB;
/* 2040 */     b -= sintB * sinEpsA + sintA * sinEpsB + sintB * sinEpsB;
/*      */     
/* 2042 */     double cosa = a + b;
/* 2043 */     double cosb = -(cosa - a - b);
/*      */     
/* 2045 */     if (cotanFlag) {
/*      */       
/* 2047 */       double tmp = cosa; cosa = sina; sina = tmp;
/* 2048 */       tmp = cosb; cosb = sinb; sinb = tmp;
/*      */     } 
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
/* 2062 */     double est = sina / cosa;
/*      */ 
/*      */     
/* 2065 */     temp = est * 1.073741824E9D;
/* 2066 */     double esta = est + temp - temp;
/* 2067 */     double estb = est - esta;
/*      */     
/* 2069 */     temp = cosa * 1.073741824E9D;
/* 2070 */     double cosaa = cosa + temp - temp;
/* 2071 */     double cosab = cosa - cosaa;
/*      */ 
/*      */     
/* 2074 */     double err = (sina - esta * cosaa - esta * cosab - estb * cosaa - estb * cosab) / cosa;
/* 2075 */     err += sinb / cosa;
/* 2076 */     err += -sina * cosb / cosa / cosa;
/*      */     
/* 2078 */     if (xb != 0.0D) {
/*      */ 
/*      */       
/* 2081 */       double xbadj = xb + est * est * xb;
/* 2082 */       if (cotanFlag) {
/* 2083 */         xbadj = -xbadj;
/*      */       }
/*      */       
/* 2086 */       err += xbadj;
/*      */     } 
/*      */     
/* 2089 */     return est + err;
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
/*      */   
/*      */   private static void reducePayneHanek(double x, double[] result) {
/* 2106 */     long shpi0, shpiA, shpiB, inbits = Double.doubleToRawLongBits(x);
/* 2107 */     int exponent = (int)(inbits >> 52L & 0x7FFL) - 1023;
/*      */ 
/*      */     
/* 2110 */     inbits &= 0xFFFFFFFFFFFFFL;
/* 2111 */     inbits |= 0x10000000000000L;
/*      */ 
/*      */     
/* 2114 */     exponent++;
/* 2115 */     inbits <<= 11L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2121 */     int idx = exponent >> 6;
/* 2122 */     int shift = exponent - (idx << 6);
/*      */     
/* 2124 */     if (shift != 0) {
/* 2125 */       shpi0 = (idx == 0) ? 0L : (RECIP_2PI[idx - 1] << shift);
/* 2126 */       shpi0 |= RECIP_2PI[idx] >>> 64 - shift;
/* 2127 */       shpiA = RECIP_2PI[idx] << shift | RECIP_2PI[idx + 1] >>> 64 - shift;
/* 2128 */       shpiB = RECIP_2PI[idx + 1] << shift | RECIP_2PI[idx + 2] >>> 64 - shift;
/*      */     } else {
/* 2130 */       shpi0 = (idx == 0) ? 0L : RECIP_2PI[idx - 1];
/* 2131 */       shpiA = RECIP_2PI[idx];
/* 2132 */       shpiB = RECIP_2PI[idx + 1];
/*      */     } 
/*      */ 
/*      */     
/* 2136 */     long a = inbits >>> 32L;
/* 2137 */     long b = inbits & 0xFFFFFFFFL;
/*      */     
/* 2139 */     long c = shpiA >>> 32L;
/* 2140 */     long d = shpiA & 0xFFFFFFFFL;
/*      */     
/* 2142 */     long ac = a * c;
/* 2143 */     long bd = b * d;
/* 2144 */     long bc = b * c;
/* 2145 */     long ad = a * d;
/*      */     
/* 2147 */     long prodB = bd + (ad << 32L);
/* 2148 */     long prodA = ac + (ad >>> 32L);
/*      */     
/* 2150 */     boolean bita = ((bd & Long.MIN_VALUE) != 0L);
/* 2151 */     boolean bitb = ((ad & 0x80000000L) != 0L);
/* 2152 */     boolean bitsum = ((prodB & Long.MIN_VALUE) != 0L);
/*      */ 
/*      */     
/* 2155 */     if ((bita && bitb) || ((bita || bitb) && !bitsum))
/*      */     {
/* 2157 */       prodA++;
/*      */     }
/*      */     
/* 2160 */     bita = ((prodB & Long.MIN_VALUE) != 0L);
/* 2161 */     bitb = ((bc & 0x80000000L) != 0L);
/*      */     
/* 2163 */     prodB += bc << 32L;
/* 2164 */     prodA += bc >>> 32L;
/*      */     
/* 2166 */     bitsum = ((prodB & Long.MIN_VALUE) != 0L);
/*      */ 
/*      */     
/* 2169 */     if ((bita && bitb) || ((bita || bitb) && !bitsum))
/*      */     {
/* 2171 */       prodA++;
/*      */     }
/*      */ 
/*      */     
/* 2175 */     c = shpiB >>> 32L;
/* 2176 */     d = shpiB & 0xFFFFFFFFL;
/* 2177 */     ac = a * c;
/* 2178 */     bc = b * c;
/* 2179 */     ad = a * d;
/*      */ 
/*      */     
/* 2182 */     ac += bc + ad >>> 32L;
/*      */     
/* 2184 */     bita = ((prodB & Long.MIN_VALUE) != 0L);
/* 2185 */     bitb = ((ac & Long.MIN_VALUE) != 0L);
/* 2186 */     prodB += ac;
/* 2187 */     bitsum = ((prodB & Long.MIN_VALUE) != 0L);
/*      */     
/* 2189 */     if ((bita && bitb) || ((bita || bitb) && !bitsum))
/*      */     {
/* 2191 */       prodA++;
/*      */     }
/*      */ 
/*      */     
/* 2195 */     c = shpi0 >>> 32L;
/* 2196 */     d = shpi0 & 0xFFFFFFFFL;
/*      */     
/* 2198 */     bd = b * d;
/* 2199 */     bc = b * c;
/* 2200 */     ad = a * d;
/*      */     
/* 2202 */     prodA += bd + (bc + ad << 32L);
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
/* 2214 */     int intPart = (int)(prodA >>> 62L);
/*      */ 
/*      */     
/* 2217 */     prodA <<= 2L;
/* 2218 */     prodA |= prodB >>> 62L;
/* 2219 */     prodB <<= 2L;
/*      */ 
/*      */     
/* 2222 */     a = prodA >>> 32L;
/* 2223 */     b = prodA & 0xFFFFFFFFL;
/*      */     
/* 2225 */     c = PI_O_4_BITS[0] >>> 32L;
/* 2226 */     d = PI_O_4_BITS[0] & 0xFFFFFFFFL;
/*      */     
/* 2228 */     ac = a * c;
/* 2229 */     bd = b * d;
/* 2230 */     bc = b * c;
/* 2231 */     ad = a * d;
/*      */     
/* 2233 */     long prod2B = bd + (ad << 32L);
/* 2234 */     long prod2A = ac + (ad >>> 32L);
/*      */     
/* 2236 */     bita = ((bd & Long.MIN_VALUE) != 0L);
/* 2237 */     bitb = ((ad & 0x80000000L) != 0L);
/* 2238 */     bitsum = ((prod2B & Long.MIN_VALUE) != 0L);
/*      */ 
/*      */     
/* 2241 */     if ((bita && bitb) || ((bita || bitb) && !bitsum))
/*      */     {
/* 2243 */       prod2A++;
/*      */     }
/*      */     
/* 2246 */     bita = ((prod2B & Long.MIN_VALUE) != 0L);
/* 2247 */     bitb = ((bc & 0x80000000L) != 0L);
/*      */     
/* 2249 */     prod2B += bc << 32L;
/* 2250 */     prod2A += bc >>> 32L;
/*      */     
/* 2252 */     bitsum = ((prod2B & Long.MIN_VALUE) != 0L);
/*      */ 
/*      */     
/* 2255 */     if ((bita && bitb) || ((bita || bitb) && !bitsum))
/*      */     {
/* 2257 */       prod2A++;
/*      */     }
/*      */ 
/*      */     
/* 2261 */     c = PI_O_4_BITS[1] >>> 32L;
/* 2262 */     d = PI_O_4_BITS[1] & 0xFFFFFFFFL;
/* 2263 */     ac = a * c;
/* 2264 */     bc = b * c;
/* 2265 */     ad = a * d;
/*      */ 
/*      */     
/* 2268 */     ac += bc + ad >>> 32L;
/*      */     
/* 2270 */     bita = ((prod2B & Long.MIN_VALUE) != 0L);
/* 2271 */     bitb = ((ac & Long.MIN_VALUE) != 0L);
/* 2272 */     prod2B += ac;
/* 2273 */     bitsum = ((prod2B & Long.MIN_VALUE) != 0L);
/*      */     
/* 2275 */     if ((bita && bitb) || ((bita || bitb) && !bitsum))
/*      */     {
/* 2277 */       prod2A++;
/*      */     }
/*      */ 
/*      */     
/* 2281 */     a = prodB >>> 32L;
/* 2282 */     b = prodB & 0xFFFFFFFFL;
/* 2283 */     c = PI_O_4_BITS[0] >>> 32L;
/* 2284 */     d = PI_O_4_BITS[0] & 0xFFFFFFFFL;
/* 2285 */     ac = a * c;
/* 2286 */     bc = b * c;
/* 2287 */     ad = a * d;
/*      */ 
/*      */     
/* 2290 */     ac += bc + ad >>> 32L;
/*      */     
/* 2292 */     bita = ((prod2B & Long.MIN_VALUE) != 0L);
/* 2293 */     bitb = ((ac & Long.MIN_VALUE) != 0L);
/* 2294 */     prod2B += ac;
/* 2295 */     bitsum = ((prod2B & Long.MIN_VALUE) != 0L);
/*      */     
/* 2297 */     if ((bita && bitb) || ((bita || bitb) && !bitsum))
/*      */     {
/* 2299 */       prod2A++;
/*      */     }
/*      */ 
/*      */     
/* 2303 */     double tmpA = (prod2A >>> 12L) / 4.503599627370496E15D;
/* 2304 */     double tmpB = (((prod2A & 0xFFFL) << 40L) + (prod2B >>> 24L)) / 4.503599627370496E15D / 4.503599627370496E15D;
/*      */     
/* 2306 */     double sumA = tmpA + tmpB;
/* 2307 */     double sumB = -(sumA - tmpA - tmpB);
/*      */ 
/*      */     
/* 2310 */     result[0] = intPart;
/* 2311 */     result[1] = sumA * 2.0D;
/* 2312 */     result[2] = sumB * 2.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double sin(double x) {
/* 2322 */     boolean negative = false;
/* 2323 */     int quadrant = 0;
/*      */     
/* 2325 */     double xb = 0.0D;
/*      */ 
/*      */     
/* 2328 */     double xa = x;
/* 2329 */     if (x < 0.0D) {
/* 2330 */       negative = true;
/* 2331 */       xa = -xa;
/*      */     } 
/*      */ 
/*      */     
/* 2335 */     if (xa == 0.0D) {
/* 2336 */       long bits = Double.doubleToRawLongBits(x);
/* 2337 */       if (bits < 0L) {
/* 2338 */         return -0.0D;
/*      */       }
/* 2340 */       return 0.0D;
/*      */     } 
/*      */     
/* 2343 */     if (xa != xa || xa == Double.POSITIVE_INFINITY) {
/* 2344 */       return Double.NaN;
/*      */     }
/*      */ 
/*      */     
/* 2348 */     if (xa > 3294198.0D) {
/*      */ 
/*      */ 
/*      */       
/* 2352 */       double[] reduceResults = new double[3];
/* 2353 */       reducePayneHanek(xa, reduceResults);
/* 2354 */       quadrant = (int)reduceResults[0] & 0x3;
/* 2355 */       xa = reduceResults[1];
/* 2356 */       xb = reduceResults[2];
/* 2357 */     } else if (xa > 1.5707963267948966D) {
/* 2358 */       CodyWaite cw = new CodyWaite(xa);
/* 2359 */       quadrant = cw.getK() & 0x3;
/* 2360 */       xa = cw.getRemA();
/* 2361 */       xb = cw.getRemB();
/*      */     } 
/*      */     
/* 2364 */     if (negative) {
/* 2365 */       quadrant ^= 0x2;
/*      */     }
/*      */     
/* 2368 */     switch (quadrant) {
/*      */       case 0:
/* 2370 */         return sinQ(xa, xb);
/*      */       case 1:
/* 2372 */         return cosQ(xa, xb);
/*      */       case 2:
/* 2374 */         return -sinQ(xa, xb);
/*      */       case 3:
/* 2376 */         return -cosQ(xa, xb);
/*      */     } 
/* 2378 */     return Double.NaN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double cos(double x) {
/* 2389 */     int quadrant = 0;
/*      */ 
/*      */     
/* 2392 */     double xa = x;
/* 2393 */     if (x < 0.0D) {
/* 2394 */       xa = -xa;
/*      */     }
/*      */     
/* 2397 */     if (xa != xa || xa == Double.POSITIVE_INFINITY) {
/* 2398 */       return Double.NaN;
/*      */     }
/*      */ 
/*      */     
/* 2402 */     double xb = 0.0D;
/* 2403 */     if (xa > 3294198.0D) {
/*      */ 
/*      */ 
/*      */       
/* 2407 */       double[] reduceResults = new double[3];
/* 2408 */       reducePayneHanek(xa, reduceResults);
/* 2409 */       quadrant = (int)reduceResults[0] & 0x3;
/* 2410 */       xa = reduceResults[1];
/* 2411 */       xb = reduceResults[2];
/* 2412 */     } else if (xa > 1.5707963267948966D) {
/* 2413 */       CodyWaite cw = new CodyWaite(xa);
/* 2414 */       quadrant = cw.getK() & 0x3;
/* 2415 */       xa = cw.getRemA();
/* 2416 */       xb = cw.getRemB();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2422 */     switch (quadrant) {
/*      */       case 0:
/* 2424 */         return cosQ(xa, xb);
/*      */       case 1:
/* 2426 */         return -sinQ(xa, xb);
/*      */       case 2:
/* 2428 */         return -cosQ(xa, xb);
/*      */       case 3:
/* 2430 */         return sinQ(xa, xb);
/*      */     } 
/* 2432 */     return Double.NaN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double tan(double x) {
/*      */     int i;
/*      */     double result;
/* 2443 */     boolean negative = false;
/* 2444 */     int quadrant = 0;
/*      */ 
/*      */     
/* 2447 */     double xa = x;
/* 2448 */     if (x < 0.0D) {
/* 2449 */       negative = true;
/* 2450 */       xa = -xa;
/*      */     } 
/*      */ 
/*      */     
/* 2454 */     if (xa == 0.0D) {
/* 2455 */       long bits = Double.doubleToRawLongBits(x);
/* 2456 */       if (bits < 0L) {
/* 2457 */         return -0.0D;
/*      */       }
/* 2459 */       return 0.0D;
/*      */     } 
/*      */     
/* 2462 */     if (xa != xa || xa == Double.POSITIVE_INFINITY) {
/* 2463 */       return Double.NaN;
/*      */     }
/*      */ 
/*      */     
/* 2467 */     double xb = 0.0D;
/* 2468 */     if (xa > 3294198.0D) {
/*      */ 
/*      */ 
/*      */       
/* 2472 */       double[] reduceResults = new double[3];
/* 2473 */       reducePayneHanek(xa, reduceResults);
/* 2474 */       quadrant = (int)reduceResults[0] & 0x3;
/* 2475 */       xa = reduceResults[1];
/* 2476 */       xb = reduceResults[2];
/* 2477 */     } else if (xa > 1.5707963267948966D) {
/* 2478 */       CodyWaite cw = new CodyWaite(xa);
/* 2479 */       quadrant = cw.getK() & 0x3;
/* 2480 */       xa = cw.getRemA();
/* 2481 */       xb = cw.getRemB();
/*      */     } 
/*      */     
/* 2484 */     if (xa > 1.5D) {
/*      */       
/* 2486 */       double pi2a = 1.5707963267948966D;
/* 2487 */       double pi2b = 6.123233995736766E-17D;
/*      */       
/* 2489 */       double a = 1.5707963267948966D - xa;
/* 2490 */       double b = -(a - 1.5707963267948966D + xa);
/* 2491 */       b += 6.123233995736766E-17D - xb;
/*      */       
/* 2493 */       xa = a + b;
/* 2494 */       xb = -(xa - a - b);
/* 2495 */       quadrant ^= 0x1;
/* 2496 */       i = negative ^ true;
/*      */     } 
/*      */ 
/*      */     
/* 2500 */     if ((quadrant & 0x1) == 0) {
/* 2501 */       result = tanQ(xa, xb, false);
/*      */     } else {
/* 2503 */       result = -tanQ(xa, xb, true);
/*      */     } 
/*      */     
/* 2506 */     if (i != 0) {
/* 2507 */       result = -result;
/*      */     }
/*      */     
/* 2510 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double atan(double x) {
/* 2519 */     return atan(x, 0.0D, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double atan(double xa, double xb, boolean leftPlane) {
/*      */     boolean negate;
/*      */     int idx;
/* 2529 */     if (xa == 0.0D) {
/* 2530 */       return leftPlane ? copySign(Math.PI, xa) : xa;
/*      */     }
/*      */ 
/*      */     
/* 2534 */     if (xa < 0.0D) {
/*      */       
/* 2536 */       xa = -xa;
/* 2537 */       xb = -xb;
/* 2538 */       negate = true;
/*      */     } else {
/* 2540 */       negate = false;
/*      */     } 
/*      */     
/* 2543 */     if (xa > 1.633123935319537E16D) {
/* 2544 */       return (negate ^ leftPlane) ? -1.5707963267948966D : 1.5707963267948966D;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2549 */     if (xa < 1.0D) {
/* 2550 */       idx = (int)((-1.7168146928204135D * xa * xa + 8.0D) * xa + 0.5D);
/*      */     } else {
/* 2552 */       double oneOverXa = 1.0D / xa;
/* 2553 */       idx = (int)(-((-1.7168146928204135D * oneOverXa * oneOverXa + 8.0D) * oneOverXa) + 13.07D);
/*      */     } 
/*      */     
/* 2556 */     double ttA = TANGENT_TABLE_A[idx];
/* 2557 */     double ttB = TANGENT_TABLE_B[idx];
/*      */     
/* 2559 */     double epsA = xa - ttA;
/* 2560 */     double epsB = -(epsA - xa + ttA);
/* 2561 */     epsB += xb - ttB;
/*      */     
/* 2563 */     double temp = epsA + epsB;
/* 2564 */     epsB = -(temp - epsA - epsB);
/* 2565 */     epsA = temp;
/*      */ 
/*      */     
/* 2568 */     temp = xa * 1.073741824E9D;
/* 2569 */     double ya = xa + temp - temp;
/* 2570 */     double yb = xb + xa - ya;
/* 2571 */     xa = ya;
/* 2572 */     xb += yb;
/*      */ 
/*      */     
/* 2575 */     if (idx == 0) {
/*      */ 
/*      */       
/* 2578 */       double denom = 1.0D / (1.0D + (xa + xb) * (ttA + ttB));
/*      */       
/* 2580 */       ya = epsA * denom;
/* 2581 */       yb = epsB * denom;
/*      */     } else {
/* 2583 */       double temp2 = xa * ttA;
/* 2584 */       double d1 = 1.0D + temp2;
/* 2585 */       double d2 = -(d1 - 1.0D - temp2);
/* 2586 */       temp2 = xb * ttA + xa * ttB;
/* 2587 */       temp = d1 + temp2;
/* 2588 */       d2 += -(temp - d1 - temp2);
/* 2589 */       d1 = temp;
/*      */       
/* 2591 */       d2 += xb * ttB;
/* 2592 */       ya = epsA / d1;
/*      */       
/* 2594 */       temp = ya * 1.073741824E9D;
/* 2595 */       double yaa = ya + temp - temp;
/* 2596 */       double yab = ya - yaa;
/*      */       
/* 2598 */       temp = d1 * 1.073741824E9D;
/* 2599 */       double zaa = d1 + temp - temp;
/* 2600 */       double zab = d1 - zaa;
/*      */ 
/*      */       
/* 2603 */       yb = (epsA - yaa * zaa - yaa * zab - yab * zaa - yab * zab) / d1;
/*      */       
/* 2605 */       yb += -epsA * d2 / d1 / d1;
/* 2606 */       yb += epsB / d1;
/*      */     } 
/*      */ 
/*      */     
/* 2610 */     epsA = ya;
/* 2611 */     epsB = yb;
/*      */ 
/*      */     
/* 2614 */     double epsA2 = epsA * epsA;
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
/* 2625 */     yb = 0.07490822288864472D;
/* 2626 */     yb = yb * epsA2 - 0.09088450866185192D;
/* 2627 */     yb = yb * epsA2 + 0.11111095942313305D;
/* 2628 */     yb = yb * epsA2 - 0.1428571423679182D;
/* 2629 */     yb = yb * epsA2 + 0.19999999999923582D;
/* 2630 */     yb = yb * epsA2 - 0.33333333333333287D;
/* 2631 */     yb = yb * epsA2 * epsA;
/*      */ 
/*      */     
/* 2634 */     ya = epsA;
/*      */     
/* 2636 */     temp = ya + yb;
/* 2637 */     yb = -(temp - ya - yb);
/* 2638 */     ya = temp;
/*      */ 
/*      */     
/* 2641 */     yb += epsB / (1.0D + epsA * epsA);
/*      */     
/* 2643 */     double eighths = EIGHTHS[idx];
/*      */ 
/*      */     
/* 2646 */     double za = eighths + ya;
/* 2647 */     double zb = -(za - eighths - ya);
/* 2648 */     temp = za + yb;
/* 2649 */     zb += -(temp - za - yb);
/* 2650 */     za = temp;
/*      */     
/* 2652 */     double result = za + zb;
/*      */     
/* 2654 */     if (leftPlane) {
/*      */       
/* 2656 */       double resultb = -(result - za - zb);
/* 2657 */       double pia = Math.PI;
/* 2658 */       double pib = 1.2246467991473532E-16D;
/*      */       
/* 2660 */       za = Math.PI - result;
/* 2661 */       zb = -(za - Math.PI + result);
/* 2662 */       zb += 1.2246467991473532E-16D - resultb;
/*      */       
/* 2664 */       result = za + zb;
/*      */     } 
/*      */ 
/*      */     
/* 2668 */     if (negate ^ leftPlane) {
/* 2669 */       result = -result;
/*      */     }
/*      */     
/* 2672 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double atan2(double y, double x) {
/* 2682 */     if (x != x || y != y) {
/* 2683 */       return Double.NaN;
/*      */     }
/*      */     
/* 2686 */     if (y == 0.0D) {
/* 2687 */       double d1 = x * y;
/* 2688 */       double invx = 1.0D / x;
/* 2689 */       double invy = 1.0D / y;
/*      */       
/* 2691 */       if (invx == 0.0D) {
/* 2692 */         if (x > 0.0D) {
/* 2693 */           return y;
/*      */         }
/* 2695 */         return copySign(Math.PI, y);
/*      */       } 
/*      */ 
/*      */       
/* 2699 */       if (x < 0.0D || invx < 0.0D) {
/* 2700 */         if (y < 0.0D || invy < 0.0D) {
/* 2701 */           return -3.141592653589793D;
/*      */         }
/* 2703 */         return Math.PI;
/*      */       } 
/*      */       
/* 2706 */       return d1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2712 */     if (y == Double.POSITIVE_INFINITY) {
/* 2713 */       if (x == Double.POSITIVE_INFINITY) {
/* 2714 */         return 0.7853981633974483D;
/*      */       }
/*      */       
/* 2717 */       if (x == Double.NEGATIVE_INFINITY) {
/* 2718 */         return 2.356194490192345D;
/*      */       }
/*      */       
/* 2721 */       return 1.5707963267948966D;
/*      */     } 
/*      */     
/* 2724 */     if (y == Double.NEGATIVE_INFINITY) {
/* 2725 */       if (x == Double.POSITIVE_INFINITY) {
/* 2726 */         return -0.7853981633974483D;
/*      */       }
/*      */       
/* 2729 */       if (x == Double.NEGATIVE_INFINITY) {
/* 2730 */         return -2.356194490192345D;
/*      */       }
/*      */       
/* 2733 */       return -1.5707963267948966D;
/*      */     } 
/*      */     
/* 2736 */     if (x == Double.POSITIVE_INFINITY) {
/* 2737 */       if (y > 0.0D || 1.0D / y > 0.0D) {
/* 2738 */         return 0.0D;
/*      */       }
/*      */       
/* 2741 */       if (y < 0.0D || 1.0D / y < 0.0D) {
/* 2742 */         return -0.0D;
/*      */       }
/*      */     } 
/*      */     
/* 2746 */     if (x == Double.NEGATIVE_INFINITY) {
/*      */       
/* 2748 */       if (y > 0.0D || 1.0D / y > 0.0D) {
/* 2749 */         return Math.PI;
/*      */       }
/*      */       
/* 2752 */       if (y < 0.0D || 1.0D / y < 0.0D) {
/* 2753 */         return -3.141592653589793D;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2759 */     if (x == 0.0D) {
/* 2760 */       if (y > 0.0D || 1.0D / y > 0.0D) {
/* 2761 */         return 1.5707963267948966D;
/*      */       }
/*      */       
/* 2764 */       if (y < 0.0D || 1.0D / y < 0.0D) {
/* 2765 */         return -1.5707963267948966D;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2770 */     double r = y / x;
/* 2771 */     if (Double.isInfinite(r)) {
/* 2772 */       return atan(r, 0.0D, (x < 0.0D));
/*      */     }
/*      */     
/* 2775 */     double ra = doubleHighPart(r);
/* 2776 */     double rb = r - ra;
/*      */ 
/*      */     
/* 2779 */     double xa = doubleHighPart(x);
/* 2780 */     double xb = x - xa;
/*      */     
/* 2782 */     rb += (y - ra * xa - ra * xb - rb * xa - rb * xb) / x;
/*      */     
/* 2784 */     double temp = ra + rb;
/* 2785 */     rb = -(temp - ra - rb);
/* 2786 */     ra = temp;
/*      */     
/* 2788 */     if (ra == 0.0D) {
/* 2789 */       ra = copySign(0.0D, y);
/*      */     }
/*      */ 
/*      */     
/* 2793 */     double result = atan(ra, rb, (x < 0.0D));
/*      */     
/* 2795 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double asin(double x) {
/* 2803 */     if (x != x) {
/* 2804 */       return Double.NaN;
/*      */     }
/*      */     
/* 2807 */     if (x > 1.0D || x < -1.0D) {
/* 2808 */       return Double.NaN;
/*      */     }
/*      */     
/* 2811 */     if (x == 1.0D) {
/* 2812 */       return 1.5707963267948966D;
/*      */     }
/*      */     
/* 2815 */     if (x == -1.0D) {
/* 2816 */       return -1.5707963267948966D;
/*      */     }
/*      */     
/* 2819 */     if (x == 0.0D) {
/* 2820 */       return x;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2826 */     double temp = x * 1.073741824E9D;
/* 2827 */     double xa = x + temp - temp;
/* 2828 */     double xb = x - xa;
/*      */ 
/*      */     
/* 2831 */     double ya = xa * xa;
/* 2832 */     double yb = xa * xb * 2.0D + xb * xb;
/*      */ 
/*      */     
/* 2835 */     ya = -ya;
/* 2836 */     yb = -yb;
/*      */     
/* 2838 */     double za = 1.0D + ya;
/* 2839 */     double zb = -(za - 1.0D - ya);
/*      */     
/* 2841 */     temp = za + yb;
/* 2842 */     zb += -(temp - za - yb);
/* 2843 */     za = temp;
/*      */ 
/*      */ 
/*      */     
/* 2847 */     double y = sqrt(za);
/* 2848 */     temp = y * 1.073741824E9D;
/* 2849 */     ya = y + temp - temp;
/* 2850 */     yb = y - ya;
/*      */ 
/*      */     
/* 2853 */     yb += (za - ya * ya - 2.0D * ya * yb - yb * yb) / 2.0D * y;
/*      */ 
/*      */     
/* 2856 */     double dx = zb / 2.0D * y;
/*      */ 
/*      */     
/* 2859 */     double r = x / y;
/* 2860 */     temp = r * 1.073741824E9D;
/* 2861 */     double ra = r + temp - temp;
/* 2862 */     double rb = r - ra;
/*      */     
/* 2864 */     rb += (x - ra * ya - ra * yb - rb * ya - rb * yb) / y;
/* 2865 */     rb += -x * dx / y / y;
/*      */     
/* 2867 */     temp = ra + rb;
/* 2868 */     rb = -(temp - ra - rb);
/* 2869 */     ra = temp;
/*      */     
/* 2871 */     return atan(ra, rb, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double acos(double x) {
/* 2879 */     if (x != x) {
/* 2880 */       return Double.NaN;
/*      */     }
/*      */     
/* 2883 */     if (x > 1.0D || x < -1.0D) {
/* 2884 */       return Double.NaN;
/*      */     }
/*      */     
/* 2887 */     if (x == -1.0D) {
/* 2888 */       return Math.PI;
/*      */     }
/*      */     
/* 2891 */     if (x == 1.0D) {
/* 2892 */       return 0.0D;
/*      */     }
/*      */     
/* 2895 */     if (x == 0.0D) {
/* 2896 */       return 1.5707963267948966D;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2902 */     double temp = x * 1.073741824E9D;
/* 2903 */     double xa = x + temp - temp;
/* 2904 */     double xb = x - xa;
/*      */ 
/*      */     
/* 2907 */     double ya = xa * xa;
/* 2908 */     double yb = xa * xb * 2.0D + xb * xb;
/*      */ 
/*      */     
/* 2911 */     ya = -ya;
/* 2912 */     yb = -yb;
/*      */     
/* 2914 */     double za = 1.0D + ya;
/* 2915 */     double zb = -(za - 1.0D - ya);
/*      */     
/* 2917 */     temp = za + yb;
/* 2918 */     zb += -(temp - za - yb);
/* 2919 */     za = temp;
/*      */ 
/*      */     
/* 2922 */     double y = sqrt(za);
/* 2923 */     temp = y * 1.073741824E9D;
/* 2924 */     ya = y + temp - temp;
/* 2925 */     yb = y - ya;
/*      */ 
/*      */     
/* 2928 */     yb += (za - ya * ya - 2.0D * ya * yb - yb * yb) / 2.0D * y;
/*      */ 
/*      */     
/* 2931 */     yb += zb / 2.0D * y;
/* 2932 */     y = ya + yb;
/* 2933 */     yb = -(y - ya - yb);
/*      */ 
/*      */     
/* 2936 */     double r = y / x;
/*      */ 
/*      */     
/* 2939 */     if (Double.isInfinite(r)) {
/* 2940 */       return 1.5707963267948966D;
/*      */     }
/*      */     
/* 2943 */     double ra = doubleHighPart(r);
/* 2944 */     double rb = r - ra;
/*      */     
/* 2946 */     rb += (y - ra * xa - ra * xb - rb * xa - rb * xb) / x;
/* 2947 */     rb += yb / x;
/*      */     
/* 2949 */     temp = ra + rb;
/* 2950 */     rb = -(temp - ra - rb);
/* 2951 */     ra = temp;
/*      */     
/* 2953 */     return atan(ra, rb, (x < 0.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double cbrt(double x) {
/* 2962 */     long inbits = Double.doubleToRawLongBits(x);
/* 2963 */     int exponent = (int)(inbits >> 52L & 0x7FFL) - 1023;
/* 2964 */     boolean subnormal = false;
/*      */     
/* 2966 */     if (exponent == -1023) {
/* 2967 */       if (x == 0.0D) {
/* 2968 */         return x;
/*      */       }
/*      */ 
/*      */       
/* 2972 */       subnormal = true;
/* 2973 */       x *= 1.8014398509481984E16D;
/* 2974 */       inbits = Double.doubleToRawLongBits(x);
/* 2975 */       exponent = (int)(inbits >> 52L & 0x7FFL) - 1023;
/*      */     } 
/*      */     
/* 2978 */     if (exponent == 1024)
/*      */     {
/* 2980 */       return x;
/*      */     }
/*      */ 
/*      */     
/* 2984 */     int exp3 = exponent / 3;
/*      */ 
/*      */     
/* 2987 */     double p2 = Double.longBitsToDouble(inbits & Long.MIN_VALUE | (exp3 + 1023 & 0x7FF) << 52L);
/*      */ 
/*      */ 
/*      */     
/* 2991 */     double mant = Double.longBitsToDouble(inbits & 0xFFFFFFFFFFFFFL | 0x3FF0000000000000L);
/*      */ 
/*      */     
/* 2994 */     double est = -0.010714690733195933D;
/* 2995 */     est = est * mant + 0.0875862700108075D;
/* 2996 */     est = est * mant + -0.3058015757857271D;
/* 2997 */     est = est * mant + 0.7249995199969751D;
/* 2998 */     est = est * mant + 0.5039018405998233D;
/*      */     
/* 3000 */     est *= CBRTTWO[exponent % 3 + 2];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3005 */     double xs = x / p2 * p2 * p2;
/* 3006 */     est += (xs - est * est * est) / 3.0D * est * est;
/* 3007 */     est += (xs - est * est * est) / 3.0D * est * est;
/*      */ 
/*      */     
/* 3010 */     double temp = est * 1.073741824E9D;
/* 3011 */     double ya = est + temp - temp;
/* 3012 */     double yb = est - ya;
/*      */     
/* 3014 */     double za = ya * ya;
/* 3015 */     double zb = ya * yb * 2.0D + yb * yb;
/* 3016 */     temp = za * 1.073741824E9D;
/* 3017 */     double temp2 = za + temp - temp;
/* 3018 */     zb += za - temp2;
/* 3019 */     za = temp2;
/*      */     
/* 3021 */     zb = za * yb + ya * zb + zb * yb;
/* 3022 */     za *= ya;
/*      */     
/* 3024 */     double na = xs - za;
/* 3025 */     double nb = -(na - xs + za);
/* 3026 */     nb -= zb;
/*      */     
/* 3028 */     est += (na + nb) / 3.0D * est * est;
/*      */ 
/*      */     
/* 3031 */     est *= p2;
/*      */     
/* 3033 */     if (subnormal) {
/* 3034 */       est *= 3.814697265625E-6D;
/*      */     }
/*      */     
/* 3037 */     return est;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double toRadians(double x) {
/* 3047 */     if (Double.isInfinite(x) || x == 0.0D) {
/* 3048 */       return x;
/*      */     }
/*      */ 
/*      */     
/* 3052 */     double facta = 0.01745329052209854D;
/* 3053 */     double factb = 1.997844754509471E-9D;
/*      */     
/* 3055 */     double xa = doubleHighPart(x);
/* 3056 */     double xb = x - xa;
/*      */     
/* 3058 */     double result = xb * 1.997844754509471E-9D + xb * 0.01745329052209854D + xa * 1.997844754509471E-9D + xa * 0.01745329052209854D;
/* 3059 */     if (result == 0.0D) {
/* 3060 */       result *= x;
/*      */     }
/* 3062 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double toDegrees(double x) {
/* 3072 */     if (Double.isInfinite(x) || x == 0.0D) {
/* 3073 */       return x;
/*      */     }
/*      */ 
/*      */     
/* 3077 */     double facta = 57.2957763671875D;
/* 3078 */     double factb = 3.145894820876798E-6D;
/*      */     
/* 3080 */     double xa = doubleHighPart(x);
/* 3081 */     double xb = x - xa;
/*      */     
/* 3083 */     return xb * 3.145894820876798E-6D + xb * 57.2957763671875D + xa * 3.145894820876798E-6D + xa * 57.2957763671875D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int abs(int x) {
/* 3092 */     int i = x >>> 31;
/* 3093 */     return (x ^ (i ^ 0xFFFFFFFF) + 1) + i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long abs(long x) {
/* 3102 */     long l = x >>> 63L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3107 */     return (x ^ (l ^ 0xFFFFFFFFFFFFFFFFL) + 1L) + l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float abs(float x) {
/* 3116 */     return Float.intBitsToFloat(Integer.MAX_VALUE & Float.floatToRawIntBits(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double abs(double x) {
/* 3125 */     return Double.longBitsToDouble(Long.MAX_VALUE & Double.doubleToRawLongBits(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double ulp(double x) {
/* 3134 */     if (Double.isInfinite(x)) {
/* 3135 */       return Double.POSITIVE_INFINITY;
/*      */     }
/* 3137 */     return abs(x - Double.longBitsToDouble(Double.doubleToRawLongBits(x) ^ 0x1L));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float ulp(float x) {
/* 3146 */     if (Float.isInfinite(x)) {
/* 3147 */       return Float.POSITIVE_INFINITY;
/*      */     }
/* 3149 */     return abs(x - Float.intBitsToFloat(Float.floatToIntBits(x) ^ 0x1));
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
/*      */   public static double scalb(double d, int n) {
/* 3161 */     if (n > -1023 && n < 1024) {
/* 3162 */       return d * Double.longBitsToDouble((n + 1023) << 52L);
/*      */     }
/*      */ 
/*      */     
/* 3166 */     if (Double.isNaN(d) || Double.isInfinite(d) || d == 0.0D) {
/* 3167 */       return d;
/*      */     }
/* 3169 */     if (n < -2098) {
/* 3170 */       return (d > 0.0D) ? 0.0D : -0.0D;
/*      */     }
/* 3172 */     if (n > 2097) {
/* 3173 */       return (d > 0.0D) ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
/*      */     }
/*      */ 
/*      */     
/* 3177 */     long bits = Double.doubleToRawLongBits(d);
/* 3178 */     long sign = bits & Long.MIN_VALUE;
/* 3179 */     int exponent = (int)(bits >>> 52L) & 0x7FF;
/* 3180 */     long mantissa = bits & 0xFFFFFFFFFFFFFL;
/*      */ 
/*      */     
/* 3183 */     int scaledExponent = exponent + n;
/*      */     
/* 3185 */     if (n < 0) {
/*      */       
/* 3187 */       if (scaledExponent > 0)
/*      */       {
/* 3189 */         return Double.longBitsToDouble(sign | scaledExponent << 52L | mantissa); } 
/* 3190 */       if (scaledExponent > -53) {
/*      */ 
/*      */ 
/*      */         
/* 3194 */         mantissa |= 0x10000000000000L;
/*      */ 
/*      */         
/* 3197 */         long mostSignificantLostBit = mantissa & 1L << -scaledExponent;
/* 3198 */         mantissa >>>= 1 - scaledExponent;
/* 3199 */         if (mostSignificantLostBit != 0L)
/*      */         {
/* 3201 */           mantissa++;
/*      */         }
/* 3203 */         return Double.longBitsToDouble(sign | mantissa);
/*      */       } 
/*      */ 
/*      */       
/* 3207 */       return (sign == 0L) ? 0.0D : -0.0D;
/*      */     } 
/*      */ 
/*      */     
/* 3211 */     if (exponent == 0) {
/*      */ 
/*      */       
/* 3214 */       while (mantissa >>> 52L != 1L) {
/* 3215 */         mantissa <<= 1L;
/* 3216 */         scaledExponent--;
/*      */       } 
/* 3218 */       scaledExponent++;
/* 3219 */       mantissa &= 0xFFFFFFFFFFFFFL;
/*      */       
/* 3221 */       if (scaledExponent < 2047) {
/* 3222 */         return Double.longBitsToDouble(sign | scaledExponent << 52L | mantissa);
/*      */       }
/* 3224 */       return (sign == 0L) ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
/*      */     } 
/*      */     
/* 3227 */     if (scaledExponent < 2047) {
/* 3228 */       return Double.longBitsToDouble(sign | scaledExponent << 52L | mantissa);
/*      */     }
/* 3230 */     return (sign == 0L) ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
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
/*      */   public static float scalb(float f, int n) {
/* 3245 */     if (n > -127 && n < 128) {
/* 3246 */       return f * Float.intBitsToFloat(n + 127 << 23);
/*      */     }
/*      */ 
/*      */     
/* 3250 */     if (Float.isNaN(f) || Float.isInfinite(f) || f == 0.0F) {
/* 3251 */       return f;
/*      */     }
/* 3253 */     if (n < -277) {
/* 3254 */       return (f > 0.0F) ? 0.0F : -0.0F;
/*      */     }
/* 3256 */     if (n > 276) {
/* 3257 */       return (f > 0.0F) ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
/*      */     }
/*      */ 
/*      */     
/* 3261 */     int bits = Float.floatToIntBits(f);
/* 3262 */     int sign = bits & Integer.MIN_VALUE;
/* 3263 */     int exponent = bits >>> 23 & 0xFF;
/* 3264 */     int mantissa = bits & 0x7FFFFF;
/*      */ 
/*      */     
/* 3267 */     int scaledExponent = exponent + n;
/*      */     
/* 3269 */     if (n < 0) {
/*      */       
/* 3271 */       if (scaledExponent > 0)
/*      */       {
/* 3273 */         return Float.intBitsToFloat(sign | scaledExponent << 23 | mantissa); } 
/* 3274 */       if (scaledExponent > -24) {
/*      */ 
/*      */ 
/*      */         
/* 3278 */         mantissa |= 0x800000;
/*      */ 
/*      */         
/* 3281 */         int mostSignificantLostBit = mantissa & 1 << -scaledExponent;
/* 3282 */         mantissa >>>= 1 - scaledExponent;
/* 3283 */         if (mostSignificantLostBit != 0)
/*      */         {
/* 3285 */           mantissa++;
/*      */         }
/* 3287 */         return Float.intBitsToFloat(sign | mantissa);
/*      */       } 
/*      */ 
/*      */       
/* 3291 */       return (sign == 0) ? 0.0F : -0.0F;
/*      */     } 
/*      */ 
/*      */     
/* 3295 */     if (exponent == 0) {
/*      */ 
/*      */       
/* 3298 */       while (mantissa >>> 23 != 1) {
/* 3299 */         mantissa <<= 1;
/* 3300 */         scaledExponent--;
/*      */       } 
/* 3302 */       scaledExponent++;
/* 3303 */       mantissa &= 0x7FFFFF;
/*      */       
/* 3305 */       if (scaledExponent < 255) {
/* 3306 */         return Float.intBitsToFloat(sign | scaledExponent << 23 | mantissa);
/*      */       }
/* 3308 */       return (sign == 0) ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
/*      */     } 
/*      */     
/* 3311 */     if (scaledExponent < 255) {
/* 3312 */       return Float.intBitsToFloat(sign | scaledExponent << 23 | mantissa);
/*      */     }
/* 3314 */     return (sign == 0) ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
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
/*      */   public static double nextAfter(double d, double direction) {
/* 3354 */     if (Double.isNaN(d) || Double.isNaN(direction))
/* 3355 */       return Double.NaN; 
/* 3356 */     if (d == direction)
/* 3357 */       return direction; 
/* 3358 */     if (Double.isInfinite(d))
/* 3359 */       return (d < 0.0D) ? -1.7976931348623157E308D : Double.MAX_VALUE; 
/* 3360 */     if (d == 0.0D) {
/* 3361 */       return (direction < 0.0D) ? -4.9E-324D : Double.MIN_VALUE;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3366 */     long bits = Double.doubleToRawLongBits(d);
/* 3367 */     long sign = bits & Long.MIN_VALUE;
/* 3368 */     if ((((direction < d) ? 1 : 0) ^ ((sign == 0L) ? 1 : 0)) != 0) {
/* 3369 */       return Double.longBitsToDouble(sign | (bits & Long.MAX_VALUE) + 1L);
/*      */     }
/* 3371 */     return Double.longBitsToDouble(sign | (bits & Long.MAX_VALUE) - 1L);
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
/*      */   public static float nextAfter(float f, double direction) {
/* 3410 */     if (Double.isNaN(f) || Double.isNaN(direction))
/* 3411 */       return Float.NaN; 
/* 3412 */     if (f == direction)
/* 3413 */       return (float)direction; 
/* 3414 */     if (Float.isInfinite(f))
/* 3415 */       return (f < 0.0F) ? -3.4028235E38F : Float.MAX_VALUE; 
/* 3416 */     if (f == 0.0F) {
/* 3417 */       return (direction < 0.0D) ? -1.4E-45F : Float.MIN_VALUE;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3422 */     int bits = Float.floatToIntBits(f);
/* 3423 */     int sign = bits & Integer.MIN_VALUE;
/* 3424 */     if ((((direction < f) ? 1 : 0) ^ ((sign == 0) ? 1 : 0)) != 0) {
/* 3425 */       return Float.intBitsToFloat(sign | (bits & Integer.MAX_VALUE) + 1);
/*      */     }
/* 3427 */     return Float.intBitsToFloat(sign | (bits & Integer.MAX_VALUE) - 1);
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
/*      */   public static double floor(double x) {
/* 3439 */     if (x != x) {
/* 3440 */       return x;
/*      */     }
/*      */     
/* 3443 */     if (x >= 4.503599627370496E15D || x <= -4.503599627370496E15D) {
/* 3444 */       return x;
/*      */     }
/*      */     
/* 3447 */     long y = (long)x;
/* 3448 */     if (x < 0.0D && y != x) {
/* 3449 */       y--;
/*      */     }
/*      */     
/* 3452 */     if (y == 0L) {
/* 3453 */       return x * y;
/*      */     }
/*      */     
/* 3456 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double ceil(double x) {
/* 3466 */     if (x != x) {
/* 3467 */       return x;
/*      */     }
/*      */     
/* 3470 */     double y = floor(x);
/* 3471 */     if (y == x) {
/* 3472 */       return y;
/*      */     }
/*      */     
/* 3475 */     y++;
/*      */     
/* 3477 */     if (y == 0.0D) {
/* 3478 */       return x * y;
/*      */     }
/*      */     
/* 3481 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double rint(double x) {
/* 3489 */     double y = floor(x);
/* 3490 */     double d = x - y;
/*      */     
/* 3492 */     if (d > 0.5D) {
/* 3493 */       if (y == -1.0D) {
/* 3494 */         return -0.0D;
/*      */       }
/* 3496 */       return y + 1.0D;
/*      */     } 
/* 3498 */     if (d < 0.5D) {
/* 3499 */       return y;
/*      */     }
/*      */ 
/*      */     
/* 3503 */     long z = (long)y;
/* 3504 */     return ((z & 0x1L) == 0L) ? y : (y + 1.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long round(double x) {
/* 3512 */     return (long)floor(x + 0.5D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int round(float x) {
/* 3520 */     return (int)floor((x + 0.5F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int a, int b) {
/* 3529 */     return (a <= b) ? a : b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long min(long a, long b) {
/* 3538 */     return (a <= b) ? a : b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float min(float a, float b) {
/* 3547 */     if (a > b) {
/* 3548 */       return b;
/*      */     }
/* 3550 */     if (a < b) {
/* 3551 */       return a;
/*      */     }
/*      */     
/* 3554 */     if (a != b) {
/* 3555 */       return Float.NaN;
/*      */     }
/*      */ 
/*      */     
/* 3559 */     int bits = Float.floatToRawIntBits(a);
/* 3560 */     if (bits == Integer.MIN_VALUE) {
/* 3561 */       return a;
/*      */     }
/* 3563 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double a, double b) {
/* 3572 */     if (a > b) {
/* 3573 */       return b;
/*      */     }
/* 3575 */     if (a < b) {
/* 3576 */       return a;
/*      */     }
/*      */     
/* 3579 */     if (a != b) {
/* 3580 */       return Double.NaN;
/*      */     }
/*      */ 
/*      */     
/* 3584 */     long bits = Double.doubleToRawLongBits(a);
/* 3585 */     if (bits == Long.MIN_VALUE) {
/* 3586 */       return a;
/*      */     }
/* 3588 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int a, int b) {
/* 3597 */     return (a <= b) ? b : a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long max(long a, long b) {
/* 3606 */     return (a <= b) ? b : a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float a, float b) {
/* 3615 */     if (a > b) {
/* 3616 */       return a;
/*      */     }
/* 3618 */     if (a < b) {
/* 3619 */       return b;
/*      */     }
/*      */     
/* 3622 */     if (a != b) {
/* 3623 */       return Float.NaN;
/*      */     }
/*      */ 
/*      */     
/* 3627 */     int bits = Float.floatToRawIntBits(a);
/* 3628 */     if (bits == Integer.MIN_VALUE) {
/* 3629 */       return b;
/*      */     }
/* 3631 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double a, double b) {
/* 3640 */     if (a > b) {
/* 3641 */       return a;
/*      */     }
/* 3643 */     if (a < b) {
/* 3644 */       return b;
/*      */     }
/*      */     
/* 3647 */     if (a != b) {
/* 3648 */       return Double.NaN;
/*      */     }
/*      */ 
/*      */     
/* 3652 */     long bits = Double.doubleToRawLongBits(a);
/* 3653 */     if (bits == Long.MIN_VALUE) {
/* 3654 */       return b;
/*      */     }
/* 3656 */     return a;
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
/*      */ 
/*      */   
/*      */   public static double hypot(double x, double y) {
/* 3674 */     if (Double.isInfinite(x) || Double.isInfinite(y))
/* 3675 */       return Double.POSITIVE_INFINITY; 
/* 3676 */     if (Double.isNaN(x) || Double.isNaN(y)) {
/* 3677 */       return Double.NaN;
/*      */     }
/*      */     
/* 3680 */     int expX = getExponent(x);
/* 3681 */     int expY = getExponent(y);
/* 3682 */     if (expX > expY + 27)
/*      */     {
/* 3684 */       return abs(x); } 
/* 3685 */     if (expY > expX + 27)
/*      */     {
/* 3687 */       return abs(y);
/*      */     }
/*      */ 
/*      */     
/* 3691 */     int middleExp = (expX + expY) / 2;
/*      */ 
/*      */     
/* 3694 */     double scaledX = scalb(x, -middleExp);
/* 3695 */     double scaledY = scalb(y, -middleExp);
/*      */ 
/*      */     
/* 3698 */     double scaledH = sqrt(scaledX * scaledX + scaledY * scaledY);
/*      */ 
/*      */     
/* 3701 */     return scalb(scaledH, middleExp);
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
/*      */   public static double IEEEremainder(double dividend, double divisor) {
/* 3729 */     return StrictMath.IEEEremainder(dividend, divisor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toIntExact(long n) throws MathArithmeticException {
/* 3739 */     if (n < -2147483648L || n > 2147483647L) {
/* 3740 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW, new Object[0]);
/*      */     }
/* 3742 */     return (int)n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int incrementExact(int n) throws MathArithmeticException {
/* 3753 */     if (n == Integer.MAX_VALUE) {
/* 3754 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, new Object[] { Integer.valueOf(n), Integer.valueOf(1) });
/*      */     }
/*      */     
/* 3757 */     return n + 1;
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
/*      */   public static long incrementExact(long n) throws MathArithmeticException {
/* 3769 */     if (n == Long.MAX_VALUE) {
/* 3770 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, new Object[] { Long.valueOf(n), Integer.valueOf(1) });
/*      */     }
/*      */     
/* 3773 */     return n + 1L;
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
/*      */   public static int decrementExact(int n) throws MathArithmeticException {
/* 3785 */     if (n == Integer.MIN_VALUE) {
/* 3786 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, new Object[] { Integer.valueOf(n), Integer.valueOf(1) });
/*      */     }
/*      */     
/* 3789 */     return n - 1;
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
/*      */   public static long decrementExact(long n) throws MathArithmeticException {
/* 3801 */     if (n == Long.MIN_VALUE) {
/* 3802 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, new Object[] { Long.valueOf(n), Integer.valueOf(1) });
/*      */     }
/*      */     
/* 3805 */     return n - 1L;
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
/*      */   public static int addExact(int a, int b) throws MathArithmeticException {
/* 3819 */     int sum = a + b;
/*      */ 
/*      */     
/* 3822 */     if ((a ^ b) >= 0 && (sum ^ b) < 0) {
/* 3823 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, new Object[] { Integer.valueOf(a), Integer.valueOf(b) });
/*      */     }
/*      */     
/* 3826 */     return sum;
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
/*      */   public static long addExact(long a, long b) throws MathArithmeticException {
/* 3840 */     long sum = a + b;
/*      */ 
/*      */     
/* 3843 */     if ((a ^ b) >= 0L && (sum ^ b) < 0L) {
/* 3844 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, new Object[] { Long.valueOf(a), Long.valueOf(b) });
/*      */     }
/*      */     
/* 3847 */     return sum;
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
/*      */   public static int subtractExact(int a, int b) {
/* 3861 */     int sub = a - b;
/*      */ 
/*      */     
/* 3864 */     if ((a ^ b) < 0 && (sub ^ b) >= 0) {
/* 3865 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, new Object[] { Integer.valueOf(a), Integer.valueOf(b) });
/*      */     }
/*      */     
/* 3868 */     return sub;
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
/*      */   public static long subtractExact(long a, long b) {
/* 3882 */     long sub = a - b;
/*      */ 
/*      */     
/* 3885 */     if ((a ^ b) < 0L && (sub ^ b) >= 0L) {
/* 3886 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, new Object[] { Long.valueOf(a), Long.valueOf(b) });
/*      */     }
/*      */     
/* 3889 */     return sub;
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
/*      */   public static int multiplyExact(int a, int b) {
/* 3901 */     if ((b > 0 && (a > Integer.MAX_VALUE / b || a < Integer.MIN_VALUE / b)) || (b < -1 && (a > Integer.MIN_VALUE / b || a < Integer.MAX_VALUE / b)) || (b == -1 && a == Integer.MIN_VALUE))
/*      */     {
/*      */       
/* 3904 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_MULTIPLICATION, new Object[] { Integer.valueOf(a), Integer.valueOf(b) });
/*      */     }
/* 3906 */     return a * b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long multiplyExact(long a, long b) {
/* 3917 */     if ((b > 0L && (a > Long.MAX_VALUE / b || a < Long.MIN_VALUE / b)) || (b < -1L && (a > Long.MIN_VALUE / b || a < Long.MAX_VALUE / b)) || (b == -1L && a == Long.MIN_VALUE))
/*      */     {
/*      */       
/* 3920 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_MULTIPLICATION, new Object[] { Long.valueOf(a), Long.valueOf(b) });
/*      */     }
/* 3922 */     return a * b;
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
/*      */ 
/*      */   
/*      */   public static int floorDiv(int a, int b) throws MathArithmeticException {
/* 3940 */     if (b == 0) {
/* 3941 */       throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*      */     }
/*      */     
/* 3944 */     int m = a % b;
/* 3945 */     if ((a ^ b) >= 0 || m == 0)
/*      */     {
/* 3947 */       return a / b;
/*      */     }
/*      */     
/* 3950 */     return a / b - 1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long floorDiv(long a, long b) throws MathArithmeticException {
/* 3970 */     if (b == 0L) {
/* 3971 */       throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*      */     }
/*      */     
/* 3974 */     long m = a % b;
/* 3975 */     if ((a ^ b) >= 0L || m == 0L)
/*      */     {
/* 3977 */       return a / b;
/*      */     }
/*      */     
/* 3980 */     return a / b - 1L;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int floorMod(int a, int b) throws MathArithmeticException {
/* 4000 */     if (b == 0) {
/* 4001 */       throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*      */     }
/*      */     
/* 4004 */     int m = a % b;
/* 4005 */     if ((a ^ b) >= 0 || m == 0)
/*      */     {
/* 4007 */       return m;
/*      */     }
/*      */     
/* 4010 */     return b + m;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long floorMod(long a, long b) {
/* 4030 */     if (b == 0L) {
/* 4031 */       throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*      */     }
/*      */     
/* 4034 */     long m = a % b;
/* 4035 */     if ((a ^ b) >= 0L || m == 0L)
/*      */     {
/* 4037 */       return m;
/*      */     }
/*      */     
/* 4040 */     return b + m;
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
/*      */ 
/*      */   
/*      */   public static double copySign(double magnitude, double sign) {
/* 4058 */     long m = Double.doubleToRawLongBits(magnitude);
/* 4059 */     long s = Double.doubleToRawLongBits(sign);
/* 4060 */     if ((m ^ s) >= 0L) {
/* 4061 */       return magnitude;
/*      */     }
/* 4063 */     return -magnitude;
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
/*      */   public static float copySign(float magnitude, float sign) {
/* 4079 */     int m = Float.floatToRawIntBits(magnitude);
/* 4080 */     int s = Float.floatToRawIntBits(sign);
/* 4081 */     if ((m ^ s) >= 0) {
/* 4082 */       return magnitude;
/*      */     }
/* 4084 */     return -magnitude;
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
/*      */   public static int getExponent(double d) {
/* 4098 */     return (int)(Double.doubleToRawLongBits(d) >>> 52L & 0x7FFL) - 1023;
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
/*      */   public static int getExponent(float f) {
/* 4112 */     return (Float.floatToRawIntBits(f) >>> 23 & 0xFF) - 127;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] a) {
/* 4121 */     PrintStream out = System.out;
/* 4122 */     FastMathCalc.printarray(out, "EXP_INT_TABLE_A", 1500, ExpIntTable.EXP_INT_TABLE_A);
/* 4123 */     FastMathCalc.printarray(out, "EXP_INT_TABLE_B", 1500, ExpIntTable.EXP_INT_TABLE_B);
/* 4124 */     FastMathCalc.printarray(out, "EXP_FRAC_TABLE_A", 1025, ExpFracTable.EXP_FRAC_TABLE_A);
/* 4125 */     FastMathCalc.printarray(out, "EXP_FRAC_TABLE_B", 1025, ExpFracTable.EXP_FRAC_TABLE_B);
/* 4126 */     FastMathCalc.printarray(out, "LN_MANT", 1024, lnMant.LN_MANT);
/* 4127 */     FastMathCalc.printarray(out, "SINE_TABLE_A", 14, SINE_TABLE_A);
/* 4128 */     FastMathCalc.printarray(out, "SINE_TABLE_B", 14, SINE_TABLE_B);
/* 4129 */     FastMathCalc.printarray(out, "COSINE_TABLE_A", 14, COSINE_TABLE_A);
/* 4130 */     FastMathCalc.printarray(out, "COSINE_TABLE_B", 14, COSINE_TABLE_B);
/* 4131 */     FastMathCalc.printarray(out, "TANGENT_TABLE_A", 14, TANGENT_TABLE_A);
/* 4132 */     FastMathCalc.printarray(out, "TANGENT_TABLE_B", 14, TANGENT_TABLE_B);
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
/*      */   private static class ExpIntTable
/*      */   {
/* 4168 */     private static final double[] EXP_INT_TABLE_A = FastMathLiteralArrays.loadExpIntA();
/* 4169 */     private static final double[] EXP_INT_TABLE_B = FastMathLiteralArrays.loadExpIntB();
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
/*      */   private static class ExpFracTable
/*      */   {
/* 4201 */     private static final double[] EXP_FRAC_TABLE_A = FastMathLiteralArrays.loadExpFracA();
/* 4202 */     private static final double[] EXP_FRAC_TABLE_B = FastMathLiteralArrays.loadExpFracB();
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
/*      */ 
/*      */ 
/*      */   
/*      */   private static class lnMant
/*      */   {
/* 4222 */     private static final double[][] LN_MANT = FastMathLiteralArrays.loadLnMant();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CodyWaite
/*      */   {
/*      */     private final int finalK;
/*      */ 
/*      */     
/*      */     private final double finalRemA;
/*      */ 
/*      */     
/*      */     private final double finalRemB;
/*      */ 
/*      */ 
/*      */     
/*      */     CodyWaite(double xa) {
/*      */       double remA, remB;
/* 4242 */       int k = (int)(xa * 0.6366197723675814D);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 4248 */         double a = -k * 1.570796251296997D;
/* 4249 */         remA = xa + a;
/* 4250 */         remB = -(remA - xa - a);
/*      */         
/* 4252 */         a = -k * 7.549789948768648E-8D;
/* 4253 */         double b = remA;
/* 4254 */         remA = a + b;
/* 4255 */         remB += -(remA - b - a);
/*      */         
/* 4257 */         a = -k * 6.123233995736766E-17D;
/* 4258 */         b = remA;
/* 4259 */         remA = a + b;
/* 4260 */         remB += -(remA - b - a);
/*      */         
/* 4262 */         if (remA > 0.0D) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4269 */         k--;
/*      */       } 
/*      */       
/* 4272 */       this.finalK = k;
/* 4273 */       this.finalRemA = remA;
/* 4274 */       this.finalRemB = remB;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getK() {
/* 4281 */       return this.finalK;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     double getRemA() {
/* 4287 */       return this.finalRemA;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     double getRemB() {
/* 4293 */       return this.finalRemB;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/FastMath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */