/*      */ package edu.mines.jtk.dsp;
/*      */ 
/*      */ import edu.mines.jtk.util.ArrayMath;
/*      */ import edu.mines.jtk.util.Cdouble;
/*      */ import edu.mines.jtk.util.Check;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Recursive2ndOrderFilter
/*      */ {
/*      */   private float _b0;
/*      */   private float _b1;
/*      */   private float _b2;
/*      */   private float _a1;
/*      */   private float _a2;
/*      */   
/*      */   public Recursive2ndOrderFilter(float b0, float b1, float b2, float a1, float a2) {
/*   54 */     this._b0 = b0;
/*   55 */     this._b1 = b1;
/*   56 */     this._b2 = b2;
/*   57 */     this._a1 = a1;
/*   58 */     this._a2 = a2;
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
/*      */   public Recursive2ndOrderFilter(double pole, double zero, double gain) {
/*   70 */     this._b0 = (float)gain;
/*   71 */     this._b1 = (float)(-gain * zero);
/*   72 */     this._a1 = (float)-pole;
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
/*      */   public Recursive2ndOrderFilter(Cdouble pole1, Cdouble pole2, Cdouble zero1, Cdouble zero2, double gain) {
/*   89 */     Check.argument(((pole1.i == 0.0D && pole2.i == 0.0D) || (pole2.r == pole1.r && -pole2.i == pole1.i)), "poles are real or conjugate pair");
/*      */ 
/*      */     
/*   92 */     Check.argument(((zero1.i == 0.0D && zero2.i == 0.0D) || (zero2.r == zero1.r && -zero2.i == zero1.i)), "zeros are real or conjugate pair");
/*      */ 
/*      */     
/*   95 */     this._b0 = (float)gain;
/*   96 */     this._b1 = (float)(-(zero1.r + zero2.r) * gain);
/*   97 */     this._b2 = (float)((zero1.times(zero2)).r * gain);
/*   98 */     this._a1 = (float)-(pole1.r + pole2.r);
/*   99 */     this._a2 = (float)(pole1.times(pole2)).r;
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
/*      */   public void applyForward(float[] x, float[] y) {
/*  111 */     checkArrays(x, y);
/*  112 */     int n = y.length;
/*      */ 
/*      */     
/*  115 */     if (this._b1 == 0.0F && this._b2 == 0.0F && this._a2 == 0.0F) {
/*  116 */       float yim1 = 0.0F;
/*  117 */       for (int i = 0; i < n; i++) {
/*  118 */         float xi = x[i];
/*  119 */         float yi = this._b0 * xi - this._a1 * yim1;
/*  120 */         y[i] = yi;
/*  121 */         yim1 = yi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  126 */     else if (this._b2 == 0.0F && this._a2 == 0.0F) {
/*  127 */       float yim1 = 0.0F;
/*  128 */       float xim1 = 0.0F;
/*  129 */       for (int i = 0; i < n; i++) {
/*  130 */         float xi = x[i];
/*  131 */         float yi = this._b0 * xi + this._b1 * xim1 - this._a1 * yim1;
/*  132 */         y[i] = yi;
/*  133 */         yim1 = yi;
/*  134 */         xim1 = xi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  139 */     else if (this._b2 == 0.0F) {
/*  140 */       float yim2 = 0.0F;
/*  141 */       float yim1 = 0.0F;
/*  142 */       float xim1 = 0.0F;
/*  143 */       for (int i = 0; i < n; i++) {
/*  144 */         float xi = x[i];
/*  145 */         float yi = this._b0 * xi + this._b1 * xim1 - this._a1 * yim1 - this._a2 * yim2;
/*  146 */         y[i] = yi;
/*  147 */         yim2 = yim1;
/*  148 */         yim1 = yi;
/*  149 */         xim1 = xi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  154 */     else if (this._b0 == 0.0F) {
/*  155 */       float yim2 = 0.0F;
/*  156 */       float yim1 = 0.0F;
/*  157 */       float xim2 = 0.0F;
/*  158 */       float xim1 = 0.0F;
/*  159 */       for (int i = 0; i < n; i++) {
/*  160 */         float xi = x[i];
/*  161 */         float yi = this._b1 * xim1 + this._b2 * xim2 - this._a1 * yim1 - this._a2 * yim2;
/*  162 */         y[i] = yi;
/*  163 */         yim2 = yim1;
/*  164 */         yim1 = yi;
/*  165 */         xim2 = xim1;
/*  166 */         xim1 = xi;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  172 */       float yim2 = 0.0F;
/*  173 */       float yim1 = 0.0F;
/*  174 */       float xim2 = 0.0F;
/*  175 */       float xim1 = 0.0F;
/*  176 */       for (int i = 0; i < n; i++) {
/*  177 */         float xi = x[i];
/*  178 */         float yi = this._b0 * xi + this._b1 * xim1 + this._b2 * xim2 - this._a1 * yim1 - this._a2 * yim2;
/*  179 */         y[i] = yi;
/*  180 */         yim2 = yim1;
/*  181 */         yim1 = yi;
/*  182 */         xim2 = xim1;
/*  183 */         xim1 = xi;
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
/*      */ 
/*      */   
/*      */   public void applyReverse(float[] x, float[] y) {
/*  197 */     checkArrays(x, y);
/*  198 */     int n = y.length;
/*      */ 
/*      */     
/*  201 */     if (this._b1 == 0.0F && this._b2 == 0.0F && this._a2 == 0.0F) {
/*  202 */       float yip1 = 0.0F;
/*  203 */       for (int i = n - 1; i >= 0; i--) {
/*  204 */         float xi = x[i];
/*  205 */         float yi = this._b0 * xi - this._a1 * yip1;
/*  206 */         y[i] = yi;
/*  207 */         yip1 = yi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  212 */     else if (this._b2 == 0.0F && this._a2 == 0.0F) {
/*  213 */       float xip1 = 0.0F;
/*  214 */       float yip1 = 0.0F;
/*  215 */       for (int i = n - 1; i >= 0; i--) {
/*  216 */         float xi = x[i];
/*  217 */         float yi = this._b0 * xi + this._b1 * xip1 - this._a1 * yip1;
/*  218 */         y[i] = yi;
/*  219 */         yip1 = yi;
/*  220 */         xip1 = xi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  225 */     else if (this._b2 == 0.0F) {
/*  226 */       float xip1 = 0.0F;
/*  227 */       float yip1 = 0.0F;
/*  228 */       float yip2 = 0.0F;
/*  229 */       for (int i = n - 1; i >= 0; i--) {
/*  230 */         float xi = x[i];
/*  231 */         float yi = this._b0 * xi + this._b1 * xip1 - this._a1 * yip1 - this._a2 * yip2;
/*  232 */         y[i] = yi;
/*  233 */         yip2 = yip1;
/*  234 */         yip1 = yi;
/*  235 */         xip1 = xi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  240 */     else if (this._b0 == 0.0F) {
/*  241 */       float xip1 = 0.0F;
/*  242 */       float xip2 = 0.0F;
/*  243 */       float yip1 = 0.0F;
/*  244 */       float yip2 = 0.0F;
/*  245 */       for (int i = n - 1; i >= 0; i--) {
/*  246 */         float xi = x[i];
/*  247 */         float yi = this._b1 * xip1 + this._b2 * xip2 - this._a1 * yip1 - this._a2 * yip2;
/*  248 */         y[i] = yi;
/*  249 */         yip2 = yip1;
/*  250 */         yip1 = yi;
/*  251 */         xip2 = xip1;
/*  252 */         xip1 = xi;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  258 */       float xip1 = 0.0F;
/*  259 */       float xip2 = 0.0F;
/*  260 */       float yip1 = 0.0F;
/*  261 */       float yip2 = 0.0F;
/*  262 */       for (int i = n - 1; i >= 0; i--) {
/*  263 */         float xi = x[i];
/*  264 */         float yi = this._b0 * xi + this._b1 * xip1 + this._b2 * xip2 - this._a1 * yip1 - this._a2 * yip2;
/*  265 */         y[i] = yi;
/*  266 */         yip2 = yip1;
/*  267 */         yip1 = yi;
/*  268 */         xip2 = xip1;
/*  269 */         xip1 = xi;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void accumulateForward(float[] x, float[] y) {
/*  285 */     checkArrays(x, y);
/*  286 */     int n = y.length;
/*      */ 
/*      */     
/*  289 */     if (this._b1 == 0.0F && this._b2 == 0.0F && this._a2 == 0.0F) {
/*  290 */       float yim1 = 0.0F;
/*  291 */       for (int i = 0; i < n; i++) {
/*  292 */         float xi = x[i];
/*  293 */         float yi = this._b0 * xi - this._a1 * yim1;
/*  294 */         y[i] = y[i] + yi;
/*  295 */         yim1 = yi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  300 */     else if (this._b2 == 0.0F && this._a2 == 0.0F) {
/*  301 */       float yim1 = 0.0F;
/*  302 */       float xim1 = 0.0F;
/*  303 */       for (int i = 0; i < n; i++) {
/*  304 */         float xi = x[i];
/*  305 */         float yi = this._b0 * xi + this._b1 * xim1 - this._a1 * yim1;
/*  306 */         y[i] = y[i] + yi;
/*  307 */         yim1 = yi;
/*  308 */         xim1 = xi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  313 */     else if (this._b2 == 0.0F) {
/*  314 */       float yim2 = 0.0F;
/*  315 */       float yim1 = 0.0F;
/*  316 */       float xim1 = 0.0F;
/*  317 */       for (int i = 0; i < n; i++) {
/*  318 */         float xi = x[i];
/*  319 */         float yi = this._b0 * xi + this._b1 * xim1 - this._a1 * yim1 - this._a2 * yim2;
/*  320 */         y[i] = y[i] + yi;
/*  321 */         yim2 = yim1;
/*  322 */         yim1 = yi;
/*  323 */         xim1 = xi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  328 */     else if (this._b0 == 0.0F) {
/*  329 */       float yim2 = 0.0F;
/*  330 */       float yim1 = 0.0F;
/*  331 */       float xim2 = 0.0F;
/*  332 */       float xim1 = 0.0F;
/*  333 */       for (int i = 0; i < n; i++) {
/*  334 */         float xi = x[i];
/*  335 */         float yi = this._b1 * xim1 + this._b2 * xim2 - this._a1 * yim1 - this._a2 * yim2;
/*  336 */         y[i] = y[i] + yi;
/*  337 */         yim2 = yim1;
/*  338 */         yim1 = yi;
/*  339 */         xim2 = xim1;
/*  340 */         xim1 = xi;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  346 */       float yim2 = 0.0F;
/*  347 */       float yim1 = 0.0F;
/*  348 */       float xim2 = 0.0F;
/*  349 */       float xim1 = 0.0F;
/*  350 */       for (int i = 0; i < n; i++) {
/*  351 */         float xi = x[i];
/*  352 */         float yi = this._b0 * xi + this._b1 * xim1 + this._b2 * xim2 - this._a1 * yim1 - this._a2 * yim2;
/*  353 */         y[i] = y[i] + yi;
/*  354 */         yim2 = yim1;
/*  355 */         yim1 = yi;
/*  356 */         xim2 = xim1;
/*  357 */         xim1 = xi;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void accumulateReverse(float[] x, float[] y) {
/*  373 */     checkArrays(x, y);
/*  374 */     int n = y.length;
/*      */ 
/*      */     
/*  377 */     if (this._b1 == 0.0F && this._b2 == 0.0F && this._a2 == 0.0F) {
/*  378 */       float yip1 = 0.0F;
/*  379 */       for (int i = n - 1; i >= 0; i--) {
/*  380 */         float xi = x[i];
/*  381 */         float yi = this._b0 * xi - this._a1 * yip1;
/*  382 */         y[i] = y[i] + yi;
/*  383 */         yip1 = yi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  388 */     else if (this._b2 == 0.0F && this._a2 == 0.0F) {
/*  389 */       float xip1 = 0.0F;
/*  390 */       float yip1 = 0.0F;
/*  391 */       for (int i = n - 1; i >= 0; i--) {
/*  392 */         float xi = x[i];
/*  393 */         float yi = this._b0 * xi + this._b1 * xip1 - this._a1 * yip1;
/*  394 */         y[i] = y[i] + yi;
/*  395 */         yip1 = yi;
/*  396 */         xip1 = xi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  401 */     else if (this._b2 == 0.0F) {
/*  402 */       float xip1 = 0.0F;
/*  403 */       float yip1 = 0.0F;
/*  404 */       float yip2 = 0.0F;
/*  405 */       for (int i = n - 1; i >= 0; i--) {
/*  406 */         float xi = x[i];
/*  407 */         float yi = this._b0 * xi + this._b1 * xip1 - this._a1 * yip1 - this._a2 * yip2;
/*  408 */         y[i] = y[i] + yi;
/*  409 */         yip2 = yip1;
/*  410 */         yip1 = yi;
/*  411 */         xip1 = xi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  416 */     else if (this._b0 == 0.0F) {
/*  417 */       float xip1 = 0.0F;
/*  418 */       float xip2 = 0.0F;
/*  419 */       float yip1 = 0.0F;
/*  420 */       float yip2 = 0.0F;
/*  421 */       for (int i = n - 1; i >= 0; i--) {
/*  422 */         float xi = x[i];
/*  423 */         float yi = this._b1 * xip1 + this._b2 * xip2 - this._a1 * yip1 - this._a2 * yip2;
/*  424 */         y[i] = y[i] + yi;
/*  425 */         yip2 = yip1;
/*  426 */         yip1 = yi;
/*  427 */         xip2 = xip1;
/*  428 */         xip1 = xi;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  434 */       float xip1 = 0.0F;
/*  435 */       float xip2 = 0.0F;
/*  436 */       float yip1 = 0.0F;
/*  437 */       float yip2 = 0.0F;
/*  438 */       for (int i = n - 1; i >= 0; i--) {
/*  439 */         float xi = x[i];
/*  440 */         float yi = this._b0 * xi + this._b1 * xip1 + this._b2 * xip2 - this._a1 * yip1 - this._a2 * yip2;
/*  441 */         y[i] = y[i] + yi;
/*  442 */         yip2 = yip1;
/*  443 */         yip1 = yi;
/*  444 */         xip2 = xip1;
/*  445 */         xip1 = xi;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply1Forward(float[][] x, float[][] y) {
/*  462 */     checkArrays(x, y);
/*  463 */     int n2 = y.length;
/*  464 */     for (int i2 = 0; i2 < n2; i2++) {
/*  465 */       applyForward(x[i2], y[i2]);
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
/*      */   public void apply1Reverse(float[][] x, float[][] y) {
/*  478 */     checkArrays(x, y);
/*  479 */     int n2 = y.length;
/*  480 */     for (int i2 = 0; i2 < n2; i2++) {
/*  481 */       applyReverse(x[i2], y[i2]);
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
/*      */   public void apply2Forward(float[][] x, float[][] y) {
/*  494 */     checkArrays(x, y);
/*  495 */     int n2 = y.length;
/*  496 */     int n1 = (y[0]).length;
/*      */ 
/*      */     
/*  499 */     if (this._b1 == 0.0F && this._b2 == 0.0F && this._a2 == 0.0F) {
/*  500 */       float[] yim1 = new float[n1];
/*  501 */       for (int i2 = 0; i2 < n2; i2++) {
/*  502 */         float[] xi = x[i2];
/*  503 */         float[] yi = y[i2];
/*  504 */         for (int i1 = 0; i1 < n1; i1++) {
/*  505 */           yi[i1] = this._b0 * xi[i1] - this._a1 * yim1[i1];
/*      */         }
/*      */         
/*  508 */         yim1 = yi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  513 */     else if (this._b2 == 0.0F && this._a2 == 0.0F) {
/*  514 */       float[] yim1 = new float[n1];
/*  515 */       float[] xim1 = new float[n1];
/*  516 */       float[] xi = new float[n1];
/*  517 */       for (int i2 = 0; i2 < n2; i2++) {
/*  518 */         float[] x2 = x[i2];
/*  519 */         float[] yi = y[i2];
/*  520 */         for (int i1 = 0; i1 < n1; i1++) {
/*  521 */           xi[i1] = x2[i1];
/*  522 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xim1[i1] - this._a1 * yim1[i1];
/*      */         } 
/*      */         
/*  525 */         yim1 = yi;
/*  526 */         float[] xt = xim1;
/*  527 */         xim1 = xi;
/*  528 */         xi = xt;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  533 */     else if (this._b2 == 0.0F) {
/*  534 */       float[] yim2 = new float[n1];
/*  535 */       float[] yim1 = new float[n1];
/*  536 */       float[] xim1 = new float[n1];
/*  537 */       float[] xi = new float[n1];
/*  538 */       for (int i2 = 0; i2 < n2; i2++) {
/*  539 */         float[] x2 = x[i2];
/*  540 */         float[] yi = y[i2];
/*  541 */         for (int i1 = 0; i1 < n1; i1++) {
/*  542 */           xi[i1] = x2[i1];
/*  543 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xim1[i1] - this._a1 * yim1[i1] - this._a2 * yim2[i1];
/*      */         } 
/*      */         
/*  546 */         yim2 = yim1;
/*  547 */         yim1 = yi;
/*  548 */         float[] xt = xim1;
/*  549 */         xim1 = xi;
/*  550 */         xi = xt;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  555 */     else if (this._b0 == 0.0F) {
/*  556 */       float[] yim2 = new float[n1];
/*  557 */       float[] yim1 = new float[n1];
/*  558 */       float[] xim2 = new float[n1];
/*  559 */       float[] xim1 = new float[n1];
/*  560 */       float[] xi = new float[n1];
/*  561 */       for (int i2 = 0; i2 < n2; i2++) {
/*  562 */         float[] x2 = x[i2];
/*  563 */         float[] yi = y[i2];
/*  564 */         for (int i1 = 0; i1 < n1; i1++) {
/*  565 */           xi[i1] = x2[i1];
/*  566 */           yi[i1] = this._b1 * xim1[i1] + this._b2 * xim2[i1] - this._a1 * yim1[i1] - this._a2 * yim2[i1];
/*      */         } 
/*      */         
/*  569 */         yim2 = yim1;
/*  570 */         yim1 = yi;
/*  571 */         float[] xt = xim2;
/*  572 */         xim2 = xim1;
/*  573 */         xim1 = xi;
/*  574 */         xi = xt;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  580 */       float[] yim2 = new float[n1];
/*  581 */       float[] yim1 = new float[n1];
/*  582 */       float[] xim2 = new float[n1];
/*  583 */       float[] xim1 = new float[n1];
/*  584 */       float[] xi = new float[n1];
/*  585 */       for (int i2 = 0; i2 < n2; i2++) {
/*  586 */         float[] x2 = x[i2];
/*  587 */         float[] yi = y[i2];
/*  588 */         for (int i1 = 0; i1 < n1; i1++) {
/*  589 */           xi[i1] = x2[i1];
/*  590 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xim1[i1] + this._b2 * xim2[i1] - this._a1 * yim1[i1] - this._a2 * yim2[i1];
/*      */         } 
/*      */         
/*  593 */         yim2 = yim1;
/*  594 */         yim1 = yi;
/*  595 */         float[] xt = xim2;
/*  596 */         xim2 = xim1;
/*  597 */         xim1 = xi;
/*  598 */         xi = xt;
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
/*      */ 
/*      */   
/*      */   public void apply2Reverse(float[][] x, float[][] y) {
/*  612 */     checkArrays(x, y);
/*  613 */     int n2 = y.length;
/*  614 */     int n1 = (y[0]).length;
/*      */ 
/*      */     
/*  617 */     if (this._b1 == 0.0F && this._b2 == 0.0F && this._a2 == 0.0F) {
/*  618 */       float[] yip1 = new float[n1];
/*  619 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  620 */         float[] xi = x[i2];
/*  621 */         float[] yi = y[i2];
/*  622 */         for (int i1 = 0; i1 < n1; i1++) {
/*  623 */           yi[i1] = this._b0 * xi[i1] - this._a1 * yip1[i1];
/*      */         }
/*      */         
/*  626 */         yip1 = yi;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  631 */     else if (this._b2 == 0.0F && this._a2 == 0.0F) {
/*  632 */       float[] yip1 = new float[n1];
/*  633 */       float[] xip1 = new float[n1];
/*  634 */       float[] xi = new float[n1];
/*  635 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  636 */         float[] x2 = x[i2];
/*  637 */         float[] yi = y[i2];
/*  638 */         for (int i1 = 0; i1 < n1; i1++) {
/*  639 */           xi[i1] = x2[i1];
/*  640 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xip1[i1] - this._a1 * yip1[i1];
/*      */         } 
/*      */         
/*  643 */         yip1 = yi;
/*  644 */         float[] xt = xip1;
/*  645 */         xip1 = xi;
/*  646 */         xi = xt;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  651 */     else if (this._b2 == 0.0F) {
/*  652 */       float[] yip2 = new float[n1];
/*  653 */       float[] yip1 = new float[n1];
/*  654 */       float[] xip1 = new float[n1];
/*  655 */       float[] xi = new float[n1];
/*  656 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  657 */         float[] x2 = x[i2];
/*  658 */         float[] yi = y[i2];
/*  659 */         for (int i1 = 0; i1 < n1; i1++) {
/*  660 */           xi[i1] = x2[i1];
/*  661 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xip1[i1] - this._a1 * yip1[i1] - this._a2 * yip2[i1];
/*      */         } 
/*      */         
/*  664 */         yip2 = yip1;
/*  665 */         yip1 = yi;
/*  666 */         float[] xt = xip1;
/*  667 */         xip1 = xi;
/*  668 */         xi = xt;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  673 */     else if (this._b0 == 0.0F) {
/*  674 */       float[] yip2 = new float[n1];
/*  675 */       float[] yip1 = new float[n1];
/*  676 */       float[] xip2 = new float[n1];
/*  677 */       float[] xip1 = new float[n1];
/*  678 */       float[] xi = new float[n1];
/*  679 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  680 */         float[] x2 = x[i2];
/*  681 */         float[] yi = y[i2];
/*  682 */         for (int i1 = 0; i1 < n1; i1++) {
/*  683 */           xi[i1] = x2[i1];
/*  684 */           yi[i1] = this._b1 * xip1[i1] + this._b2 * xip2[i1] - this._a1 * yip1[i1] - this._a2 * yip2[i1];
/*      */         } 
/*      */         
/*  687 */         yip2 = yip1;
/*  688 */         yip1 = yi;
/*  689 */         float[] xt = xip2;
/*  690 */         xip2 = xip1;
/*  691 */         xip1 = xi;
/*  692 */         xi = xt;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  698 */       float[] yip2 = new float[n1];
/*  699 */       float[] yip1 = new float[n1];
/*  700 */       float[] xip2 = new float[n1];
/*  701 */       float[] xip1 = new float[n1];
/*  702 */       float[] xi = new float[n1];
/*  703 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  704 */         float[] x2 = x[i2];
/*  705 */         float[] yi = y[i2];
/*  706 */         for (int i1 = 0; i1 < n1; i1++) {
/*  707 */           xi[i1] = x2[i1];
/*  708 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xip1[i1] + this._b2 * xip2[i1] - this._a1 * yip1[i1] - this._a2 * yip2[i1];
/*      */         } 
/*      */         
/*  711 */         yip2 = yip1;
/*  712 */         yip1 = yi;
/*  713 */         float[] xt = xip2;
/*  714 */         xip2 = xip1;
/*  715 */         xip1 = xi;
/*  716 */         xi = xt;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void accumulate1Forward(float[][] x, float[][] y) {
/*  732 */     checkArrays(x, y);
/*  733 */     int n2 = y.length;
/*  734 */     for (int i2 = 0; i2 < n2; i2++) {
/*  735 */       accumulateForward(x[i2], y[i2]);
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
/*      */   public void accumulate1Reverse(float[][] x, float[][] y) {
/*  750 */     checkArrays(x, y);
/*  751 */     int n2 = y.length;
/*  752 */     for (int i2 = 0; i2 < n2; i2++) {
/*  753 */       accumulateReverse(x[i2], y[i2]);
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
/*      */   public void accumulate2Forward(float[][] x, float[][] y) {
/*  768 */     checkArrays(x, y);
/*  769 */     int n2 = y.length;
/*  770 */     int n1 = (y[0]).length;
/*      */ 
/*      */     
/*  773 */     if (this._b1 == 0.0F && this._b2 == 0.0F && this._a2 == 0.0F) {
/*  774 */       float[] yim1 = new float[n1];
/*  775 */       float[] yi = new float[n1];
/*  776 */       for (int i2 = 0; i2 < n2; i2++) {
/*  777 */         float[] xi = x[i2];
/*  778 */         float[] y2 = y[i2];
/*  779 */         for (int i1 = 0; i1 < n1; i1++) {
/*  780 */           yi[i1] = this._b0 * xi[i1] - this._a1 * yim1[i1];
/*      */           
/*  782 */           y2[i1] = y2[i1] + yi[i1];
/*      */         } 
/*  784 */         float[] yt = yim1;
/*  785 */         yim1 = yi;
/*  786 */         yi = yt;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  791 */     else if (this._b2 == 0.0F && this._a2 == 0.0F) {
/*  792 */       float[] yim1 = new float[n1];
/*  793 */       float[] yi = new float[n1];
/*  794 */       float[] xim1 = new float[n1];
/*  795 */       float[] xi = new float[n1];
/*  796 */       for (int i2 = 0; i2 < n2; i2++) {
/*  797 */         float[] x2 = x[i2];
/*  798 */         float[] y2 = y[i2];
/*  799 */         for (int i1 = 0; i1 < n1; i1++) {
/*  800 */           xi[i1] = x2[i1];
/*  801 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xim1[i1] - this._a1 * yim1[i1];
/*      */           
/*  803 */           y2[i1] = y2[i1] + yi[i1];
/*      */         } 
/*  805 */         float[] yt = yim1;
/*  806 */         yim1 = yi;
/*  807 */         yi = yt;
/*  808 */         float[] xt = xim1;
/*  809 */         xim1 = xi;
/*  810 */         xi = xt;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  815 */     else if (this._b2 == 0.0F) {
/*  816 */       float[] yim2 = new float[n1];
/*  817 */       float[] yim1 = new float[n1];
/*  818 */       float[] yi = new float[n1];
/*  819 */       float[] xim1 = new float[n1];
/*  820 */       float[] xi = new float[n1];
/*  821 */       for (int i2 = 0; i2 < n2; i2++) {
/*  822 */         float[] x2 = x[i2];
/*  823 */         float[] y2 = y[i2];
/*  824 */         for (int i1 = 0; i1 < n1; i1++) {
/*  825 */           xi[i1] = x2[i1];
/*  826 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xim1[i1] - this._a1 * yim1[i1] - this._a2 * yim2[i1];
/*      */           
/*  828 */           y2[i1] = y2[i1] + yi[i1];
/*      */         } 
/*  830 */         float[] yt = yim2;
/*  831 */         yim2 = yim1;
/*  832 */         yim1 = yi;
/*  833 */         yi = yt;
/*  834 */         float[] xt = xim1;
/*  835 */         xim1 = xi;
/*  836 */         xi = xt;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  841 */     else if (this._b0 == 0.0F) {
/*  842 */       float[] yim2 = new float[n1];
/*  843 */       float[] yim1 = new float[n1];
/*  844 */       float[] yi = new float[n1];
/*  845 */       float[] xim2 = new float[n1];
/*  846 */       float[] xim1 = new float[n1];
/*  847 */       float[] xi = new float[n1];
/*  848 */       for (int i2 = 0; i2 < n2; i2++) {
/*  849 */         float[] x2 = x[i2];
/*  850 */         float[] y2 = y[i2];
/*  851 */         for (int i1 = 0; i1 < n1; i1++) {
/*  852 */           xi[i1] = x2[i1];
/*  853 */           yi[i1] = this._b1 * xim1[i1] + this._b2 * xim2[i1] - this._a1 * yim1[i1] - this._a2 * yim2[i1];
/*      */           
/*  855 */           y2[i1] = y2[i1] + yi[i1];
/*      */         } 
/*  857 */         float[] yt = yim2;
/*  858 */         yim2 = yim1;
/*  859 */         yim1 = yi;
/*  860 */         yi = yt;
/*  861 */         float[] xt = xim2;
/*  862 */         xim2 = xim1;
/*  863 */         xim1 = xi;
/*  864 */         xi = xt;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  870 */       float[] yim2 = new float[n1];
/*  871 */       float[] yim1 = new float[n1];
/*  872 */       float[] yi = new float[n1];
/*  873 */       float[] xim2 = new float[n1];
/*  874 */       float[] xim1 = new float[n1];
/*  875 */       float[] xi = new float[n1];
/*  876 */       for (int i2 = 0; i2 < n2; i2++) {
/*  877 */         float[] x2 = x[i2];
/*  878 */         float[] y2 = y[i2];
/*  879 */         for (int i1 = 0; i1 < n1; i1++) {
/*  880 */           xi[i1] = x2[i1];
/*  881 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xim1[i1] + this._b2 * xim2[i1] - this._a1 * yim1[i1] - this._a2 * yim2[i1];
/*      */           
/*  883 */           y2[i1] = y2[i1] + yi[i1];
/*      */         } 
/*  885 */         float[] yt = yim2;
/*  886 */         yim2 = yim1;
/*  887 */         yim1 = yi;
/*  888 */         yi = yt;
/*  889 */         float[] xt = xim2;
/*  890 */         xim2 = xim1;
/*  891 */         xim1 = xi;
/*  892 */         xi = xt;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void accumulate2Reverse(float[][] x, float[][] y) {
/*  909 */     checkArrays(x, y);
/*  910 */     int n2 = y.length;
/*  911 */     int n1 = (y[0]).length;
/*      */ 
/*      */     
/*  914 */     if (this._b1 == 0.0F && this._b2 == 0.0F && this._a2 == 0.0F) {
/*  915 */       float[] yip1 = new float[n1];
/*  916 */       float[] yi = new float[n1];
/*  917 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  918 */         float[] xi = x[i2];
/*  919 */         float[] y2 = y[i2];
/*  920 */         for (int i1 = 0; i1 < n1; i1++) {
/*  921 */           yi[i1] = this._b0 * xi[i1] - this._a1 * yip1[i1];
/*      */           
/*  923 */           y2[i1] = y2[i1] + yi[i1];
/*      */         } 
/*  925 */         float[] yt = yip1;
/*  926 */         yip1 = yi;
/*  927 */         yi = yt;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  932 */     else if (this._b2 == 0.0F && this._a2 == 0.0F) {
/*  933 */       float[] yip1 = new float[n1];
/*  934 */       float[] yi = new float[n1];
/*  935 */       float[] xip1 = new float[n1];
/*  936 */       float[] xi = new float[n1];
/*  937 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  938 */         float[] x2 = x[i2];
/*  939 */         float[] y2 = y[i2];
/*  940 */         for (int i1 = 0; i1 < n1; i1++) {
/*  941 */           xi[i1] = x2[i1];
/*  942 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xip1[i1] - this._a1 * yip1[i1];
/*      */           
/*  944 */           y2[i1] = y2[i1] + yi[i1];
/*      */         } 
/*  946 */         float[] yt = yip1;
/*  947 */         yip1 = yi;
/*  948 */         yi = yt;
/*  949 */         float[] xt = xip1;
/*  950 */         xip1 = xi;
/*  951 */         xi = xt;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  956 */     else if (this._b2 == 0.0F) {
/*  957 */       float[] yip2 = new float[n1];
/*  958 */       float[] yip1 = new float[n1];
/*  959 */       float[] yi = new float[n1];
/*  960 */       float[] xip1 = new float[n1];
/*  961 */       float[] xi = new float[n1];
/*  962 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  963 */         float[] x2 = x[i2];
/*  964 */         float[] y2 = y[i2];
/*  965 */         for (int i1 = 0; i1 < n1; i1++) {
/*  966 */           xi[i1] = x2[i1];
/*  967 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xip1[i1] - this._a1 * yip1[i1] - this._a2 * yip2[i1];
/*      */           
/*  969 */           y2[i1] = y2[i1] + yi[i1];
/*      */         } 
/*  971 */         float[] yt = yip2;
/*  972 */         yip2 = yip1;
/*  973 */         yip1 = yi;
/*  974 */         yi = yt;
/*  975 */         float[] xt = xip1;
/*  976 */         xip1 = xi;
/*  977 */         xi = xt;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  982 */     else if (this._b0 == 0.0F) {
/*  983 */       float[] yip2 = new float[n1];
/*  984 */       float[] yip1 = new float[n1];
/*  985 */       float[] yi = new float[n1];
/*  986 */       float[] xip2 = new float[n1];
/*  987 */       float[] xip1 = new float[n1];
/*  988 */       float[] xi = new float[n1];
/*  989 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  990 */         float[] x2 = x[i2];
/*  991 */         float[] y2 = y[i2];
/*  992 */         for (int i1 = 0; i1 < n1; i1++) {
/*  993 */           xi[i1] = x2[i1];
/*  994 */           yi[i1] = this._b1 * xip1[i1] + this._b2 * xip2[i1] - this._a1 * yip1[i1] - this._a2 * yip2[i1];
/*      */           
/*  996 */           y2[i1] = y2[i1] + yi[i1];
/*      */         } 
/*  998 */         float[] yt = yip2;
/*  999 */         yip2 = yip1;
/* 1000 */         yip1 = yi;
/* 1001 */         yi = yt;
/* 1002 */         float[] xt = xip2;
/* 1003 */         xip2 = xip1;
/* 1004 */         xip1 = xi;
/* 1005 */         xi = xt;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1011 */       float[] yip2 = new float[n1];
/* 1012 */       float[] yip1 = new float[n1];
/* 1013 */       float[] yi = new float[n1];
/* 1014 */       float[] xip2 = new float[n1];
/* 1015 */       float[] xip1 = new float[n1];
/* 1016 */       float[] xi = new float[n1];
/* 1017 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/* 1018 */         float[] x2 = x[i2];
/* 1019 */         float[] y2 = y[i2];
/* 1020 */         for (int i1 = 0; i1 < n1; i1++) {
/* 1021 */           xi[i1] = x2[i1];
/* 1022 */           yi[i1] = this._b0 * xi[i1] + this._b1 * xip1[i1] + this._b2 * xip2[i1] - this._a1 * yip1[i1] - this._a2 * yip2[i1];
/*      */           
/* 1024 */           y2[i1] = y2[i1] + yi[i1];
/*      */         } 
/* 1026 */         float[] yt = yip2;
/* 1027 */         yip2 = yip1;
/* 1028 */         yip1 = yi;
/* 1029 */         yi = yt;
/* 1030 */         float[] xt = xip2;
/* 1031 */         xip2 = xip1;
/* 1032 */         xip1 = xi;
/* 1033 */         xi = xt;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply1Forward(float[][][] x, float[][][] y) {
/* 1050 */     checkArrays(x, y);
/* 1051 */     int n3 = y.length;
/* 1052 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1053 */       apply1Forward(x[i3], y[i3]);
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
/*      */   public void apply1Reverse(float[][][] x, float[][][] y) {
/* 1066 */     checkArrays(x, y);
/* 1067 */     int n3 = y.length;
/* 1068 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1069 */       apply1Reverse(x[i3], y[i3]);
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
/*      */   public void apply2Forward(float[][][] x, float[][][] y) {
/* 1082 */     checkArrays(x, y);
/* 1083 */     int n3 = y.length;
/* 1084 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1085 */       apply2Forward(x[i3], y[i3]);
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
/*      */   public void apply2Reverse(float[][][] x, float[][][] y) {
/* 1098 */     checkArrays(x, y);
/* 1099 */     int n3 = y.length;
/* 1100 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1101 */       apply2Reverse(x[i3], y[i3]);
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
/*      */   public void apply3Forward(float[][][] x, float[][][] y) {
/* 1114 */     checkArrays(x, y);
/* 1115 */     int n3 = y.length;
/* 1116 */     int n2 = (y[0]).length;
/* 1117 */     int n1 = (y[0][0]).length;
/* 1118 */     float[][] xy = new float[n3][n1];
/* 1119 */     for (int i2 = 0; i2 < n2; i2++) {
/* 1120 */       get2(i2, x, xy);
/* 1121 */       apply2Forward(xy, xy);
/* 1122 */       set2(i2, xy, y);
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
/*      */   public void apply3Reverse(float[][][] x, float[][][] y) {
/* 1135 */     checkArrays(x, y);
/* 1136 */     int n3 = y.length;
/* 1137 */     int n2 = (y[0]).length;
/* 1138 */     int n1 = (y[0][0]).length;
/* 1139 */     float[][] xy = new float[n3][n1];
/* 1140 */     for (int i2 = 0; i2 < n2; i2++) {
/* 1141 */       get2(i2, x, xy);
/* 1142 */       apply2Reverse(xy, xy);
/* 1143 */       set2(i2, xy, y);
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
/*      */   public void accumulate1Forward(float[][][] x, float[][][] y) {
/* 1158 */     checkArrays(x, y);
/* 1159 */     int n3 = y.length;
/* 1160 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1161 */       accumulate1Forward(x[i3], y[i3]);
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
/*      */   public void accumulate1Reverse(float[][][] x, float[][][] y) {
/* 1176 */     checkArrays(x, y);
/* 1177 */     int n3 = y.length;
/* 1178 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1179 */       accumulate1Reverse(x[i3], y[i3]);
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
/*      */   public void accumulate2Forward(float[][][] x, float[][][] y) {
/* 1194 */     checkArrays(x, y);
/* 1195 */     int n3 = y.length;
/* 1196 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1197 */       accumulate2Forward(x[i3], y[i3]);
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
/*      */   public void accumulate2Reverse(float[][][] x, float[][][] y) {
/* 1212 */     checkArrays(x, y);
/* 1213 */     int n3 = y.length;
/* 1214 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1215 */       accumulate2Reverse(x[i3], y[i3]);
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
/*      */   public void accumulate3Forward(float[][][] x, float[][][] y) {
/* 1230 */     checkArrays(x, y);
/* 1231 */     int n3 = y.length;
/* 1232 */     int n2 = (y[0]).length;
/* 1233 */     int n1 = (y[0][0]).length;
/* 1234 */     float[][] xy = new float[n3][n1];
/* 1235 */     for (int i2 = 0; i2 < n2; i2++) {
/* 1236 */       get2(i2, x, xy);
/* 1237 */       apply2Forward(xy, xy);
/* 1238 */       acc2(i2, xy, y);
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
/*      */   
/*      */   public void accumulate3Reverse(float[][][] x, float[][][] y) {
/* 1254 */     checkArrays(x, y);
/* 1255 */     int n3 = y.length;
/* 1256 */     int n2 = (y[0]).length;
/* 1257 */     int n1 = (y[0][0]).length;
/* 1258 */     float[][] xy = new float[n3][n1];
/* 1259 */     for (int i2 = 0; i2 < n2; i2++) {
/* 1260 */       get2(i2, x, xy);
/* 1261 */       apply2Reverse(xy, xy);
/* 1262 */       acc2(i2, xy, y);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkArrays(float[] x, float[] y) {
/* 1272 */     Check.argument((x.length == y.length), "x.length==y.length");
/*      */   }
/*      */   
/*      */   private static void checkArrays(float[][] x, float[][] y) {
/* 1276 */     Check.argument((x.length == y.length), "x.length==y.length");
/* 1277 */     Check.argument(((x[0]).length == (y[0]).length), "x[0].length==y[0].length");
/* 1278 */     Check.argument(ArrayMath.isRegular(x), "x is regular");
/* 1279 */     Check.argument(ArrayMath.isRegular(y), "y is regular");
/*      */   }
/*      */   
/*      */   private static void checkArrays(float[][][] x, float[][][] y) {
/* 1283 */     Check.argument((x.length == y.length), "x.length==y.length");
/* 1284 */     Check.argument(((x[0]).length == (y[0]).length), "x[0].length==y[0].length");
/* 1285 */     Check.argument(((x[0][0]).length == (y[0][0]).length), "x[0][0].length==y[0][0].length");
/*      */     
/* 1287 */     Check.argument(ArrayMath.isRegular(x), "x is regular");
/* 1288 */     Check.argument(ArrayMath.isRegular(y), "y is regular");
/*      */   }
/*      */   
/*      */   private void get2(int i2, float[][][] x, float[][] x2) {
/* 1292 */     int n3 = x2.length;
/* 1293 */     int n1 = (x2[0]).length;
/* 1294 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1295 */       float[] x32 = x[i3][i2];
/* 1296 */       float[] x23 = x2[i3];
/* 1297 */       for (int i1 = 0; i1 < n1; i1++) {
/* 1298 */         x23[i1] = x32[i1];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void set2(int i2, float[][] x2, float[][][] x) {
/* 1304 */     int n3 = x2.length;
/* 1305 */     int n1 = (x2[0]).length;
/* 1306 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1307 */       float[] x32 = x[i3][i2];
/* 1308 */       float[] x23 = x2[i3];
/* 1309 */       for (int i1 = 0; i1 < n1; i1++) {
/* 1310 */         x32[i1] = x23[i1];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void acc2(int i2, float[][] x2, float[][][] x) {
/* 1316 */     int n3 = x2.length;
/* 1317 */     int n1 = (x2[0]).length;
/* 1318 */     for (int i3 = 0; i3 < n3; i3++) {
/* 1319 */       float[] x32 = x[i3][i2];
/* 1320 */       float[] x23 = x2[i3];
/* 1321 */       for (int i1 = 0; i1 < n1; i1++)
/* 1322 */         x32[i1] = x32[i1] + x23[i1]; 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/Recursive2ndOrderFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */