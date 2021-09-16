/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.MathPlus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineSearch
/*     */ {
/*     */   public static final int CONVERGED = 1;
/*     */   public static final int SMIN = 2;
/*     */   public static final int SMAX = 3;
/*     */   public static final int STOL = 4;
/*     */   public static final int FAILED = 5;
/*     */   private static final double SLO_FACTOR = 1.1D;
/*     */   private static final double SHI_FACTOR = 4.0D;
/*     */   private final Function _func;
/*     */   private final double _stol;
/*     */   private final double _ftol;
/*     */   private final double _gtol;
/*     */   
/*     */   public static interface Function
/*     */   {
/*     */     double[] evaluate(double param1Double);
/*     */   }
/*     */   
/*     */   public static class Result
/*     */   {
/*     */     public final double s;
/*     */     public final double f;
/*     */     public final double g;
/*     */     public final int ended;
/*     */     public final int neval;
/*     */     
/*     */     public boolean converged() {
/* 164 */       return (this.ended == 1);
/*     */     }
/*     */     
/*     */     private Result(double s, double f, double g, int ended, int neval) {
/* 168 */       this.s = s;
/* 169 */       this.f = f;
/* 170 */       this.g = g;
/* 171 */       this.ended = ended;
/* 172 */       this.neval = neval;
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
/*     */   
/*     */   public LineSearch(Function func, double stol, double ftol, double gtol) {
/* 187 */     Check.argument((stol >= 0.0D), "stol>=0.0");
/* 188 */     Check.argument((ftol >= 0.0D), "ftol>=0.0");
/* 189 */     Check.argument((gtol >= 0.0D), "gtol>=0.0");
/* 190 */     this._func = func;
/* 191 */     this._stol = stol;
/* 192 */     this._ftol = ftol;
/* 193 */     this._gtol = gtol;
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
/*     */   public Result search(double s, double f, double g, double smin, double smax) {
/* 211 */     Check.argument((smin >= 0.0D), "smin>=0.0");
/* 212 */     Check.argument((smin <= smax), "smin<=smax");
/* 213 */     Check.argument((smin <= s), "smin<=s");
/* 214 */     Check.argument((s <= smax), "s<=smax");
/* 215 */     Check.argument((g < 0.0D), "g<0.0");
/*     */     
/* 217 */     StepInterval si = new StepInterval();
/*     */     
/* 219 */     double finit = f;
/* 220 */     double ginit = g;
/* 221 */     double gtest = this._ftol * ginit;
/* 222 */     double width = smax - smin;
/* 223 */     double widthOld = 2.0D * width;
/*     */     
/* 225 */     double fa = finit;
/* 226 */     double ga = ginit;
/* 227 */     double fb = finit;
/* 228 */     double gb = ginit;
/* 229 */     double shi = s * 5.0D;
/*     */     
/* 231 */     double[] fg = this._func.evaluate(s);
/* 232 */     f = fg[0];
/* 233 */     g = fg[1];
/* 234 */     int neval = 1;
/* 235 */     int ended = 0;
/* 236 */     double slo = 0.0D;
/* 237 */     double sb = 0.0D;
/* 238 */     double sa = 0.0D;
/* 239 */     boolean bracketed = false;
/* 240 */     boolean stage1 = true;
/* 241 */     while (ended == 0) {
/*     */ 
/*     */       
/* 244 */       double ftest = finit + s * gtest;
/* 245 */       if (stage1 && f <= ftest && g >= 0.0D) {
/* 246 */         stage1 = false;
/*     */       }
/*     */ 
/*     */       
/* 250 */       if (bracketed && (s <= slo || s >= shi)) {
/* 251 */         ended = 5;
/* 252 */       } else if (bracketed && shi - slo <= this._stol * shi) {
/* 253 */         ended = 4;
/* 254 */       } else if (s == smax && f <= ftest && g <= gtest) {
/* 255 */         ended = 3;
/* 256 */       } else if (s == smin && (f > ftest || g >= gtest)) {
/* 257 */         ended = 2;
/* 258 */       } else if (f <= ftest && MathPlus.abs(g) <= this._gtol * -ginit) {
/* 259 */         ended = 1;
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 268 */         if (stage1 && f <= fa && f > ftest) {
/*     */ 
/*     */           
/* 271 */           double fm = f - s * gtest;
/* 272 */           double fam = fa - sa * gtest;
/* 273 */           double fbm = fb - sb * gtest;
/* 274 */           double gm = g - gtest;
/* 275 */           double gam = ga - gtest;
/* 276 */           double gbm = gb - gtest;
/*     */ 
/*     */           
/* 279 */           si.sa = sa;
/* 280 */           si.fa = fam;
/* 281 */           si.ga = gam;
/* 282 */           si.sb = sb;
/* 283 */           si.fb = fbm;
/* 284 */           si.gb = gbm;
/* 285 */           si.bracketed = bracketed;
/* 286 */           s = updateStep(s, fm, gm, slo, shi, si);
/* 287 */           sa = si.sa;
/* 288 */           fam = si.fa;
/* 289 */           gam = si.ga;
/* 290 */           sb = si.sb;
/* 291 */           fbm = si.fb;
/* 292 */           gbm = si.gb;
/* 293 */           bracketed = si.bracketed;
/*     */ 
/*     */           
/* 296 */           fa = fam + sa * gtest;
/* 297 */           fb = fbm + sb * gtest;
/* 298 */           ga = gam + gtest;
/* 299 */           gb = gbm + gtest;
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 306 */           si.sa = sa;
/* 307 */           si.fa = fa;
/* 308 */           si.ga = ga;
/* 309 */           si.sb = sb;
/* 310 */           si.fb = fb;
/* 311 */           si.gb = gb;
/* 312 */           si.bracketed = bracketed;
/* 313 */           s = updateStep(s, f, g, slo, shi, si);
/* 314 */           sa = si.sa;
/* 315 */           fa = si.fa;
/* 316 */           ga = si.ga;
/* 317 */           sb = si.sb;
/* 318 */           fb = si.fb;
/* 319 */           gb = si.gb;
/* 320 */           bracketed = si.bracketed;
/*     */         } 
/*     */ 
/*     */         
/* 324 */         if (bracketed) {
/* 325 */           if (MathPlus.abs(sb - sa) >= 0.66D * widthOld) {
/* 326 */             s = sa + 0.5D * (sb - sa);
/*     */           }
/* 328 */           widthOld = width;
/* 329 */           width = MathPlus.abs(sb - sa);
/*     */         } 
/*     */ 
/*     */         
/* 333 */         if (bracketed) {
/* 334 */           slo = MathPlus.min(sa, sb);
/* 335 */           shi = MathPlus.max(sa, sb);
/*     */         } else {
/* 337 */           slo = s + 1.1D * (s - sa);
/* 338 */           shi = s + 4.0D * (s - sa);
/*     */         } 
/*     */ 
/*     */         
/* 342 */         s = MathPlus.max(s, smin);
/* 343 */         s = MathPlus.min(s, smax);
/*     */ 
/*     */         
/* 346 */         if ((bracketed && (s <= slo || s >= shi)) || (bracketed && shi - slo <= this._stol * shi))
/*     */         {
/* 348 */           s = sa;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 353 */       fg = this._func.evaluate(s);
/* 354 */       f = fg[0];
/* 355 */       g = fg[1];
/* 356 */       neval++;
/*     */     } 
/*     */     
/* 359 */     return new Result(s, f, g, ended, neval);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class StepInterval
/*     */   {
/*     */     private StepInterval() {}
/*     */ 
/*     */ 
/*     */     
/* 371 */     double sa = 0.0D;
/* 372 */     double fa = 0.0D;
/* 373 */     double ga = 0.0D;
/* 374 */     double sb = 0.0D;
/* 375 */     double fb = 0.0D;
/* 376 */     double gb = 0.0D;
/*     */ 
/*     */     
/*     */     boolean bracketed = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private double updateStep(double sp, double fp, double gp, double smin, double smax, StepInterval si) {
/* 384 */     double sa = si.sa;
/* 385 */     double fa = si.fa;
/* 386 */     double ga = si.ga;
/* 387 */     double sb = si.sb;
/* 388 */     double fb = si.fb;
/* 389 */     double gb = si.gb;
/* 390 */     boolean bracketed = si.bracketed;
/*     */     
/* 392 */     double sgng = gp * ga / MathPlus.abs(ga);
/* 393 */     double spf = sp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 399 */     if (fp > fa) {
/* 400 */       double theta = 3.0D * (fa - fp) / (sp - sa) + ga + gp;
/* 401 */       double s = MathPlus.max(MathPlus.abs(theta), MathPlus.abs(ga), MathPlus.abs(gp));
/* 402 */       double gamma = s * MathPlus.sqrt(theta / s * theta / s - ga / s * gp / s);
/* 403 */       if (sp < sa) {
/* 404 */         gamma = -gamma;
/*     */       }
/* 406 */       double p = gamma - ga + theta;
/* 407 */       double q = gamma - ga + gamma + gp;
/* 408 */       double r = p / q;
/* 409 */       double spc = sa + r * (sp - sa);
/* 410 */       double spq = sa + ga / ((fa - fp) / (sp - sa) + ga) / 2.0D * (sp - sa);
/* 411 */       if (MathPlus.abs(spc - sa) < MathPlus.abs(spq - sa)) {
/* 412 */         spf = spc;
/*     */       } else {
/* 414 */         spf = spc + (spq - spc) / 2.0D;
/*     */       } 
/* 416 */       bracketed = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 423 */     else if (sgng < 0.0D) {
/* 424 */       double theta = 3.0D * (fa - fp) / (sp - sa) + ga + gp;
/* 425 */       double s = MathPlus.max(MathPlus.abs(theta), MathPlus.abs(ga), MathPlus.abs(gp));
/* 426 */       double gamma = s * MathPlus.sqrt(theta / s * theta / s - ga / s * gp / s);
/* 427 */       if (sp > sa) {
/* 428 */         gamma = -gamma;
/*     */       }
/* 430 */       double p = gamma - gp + theta;
/* 431 */       double q = gamma - gp + gamma + ga;
/* 432 */       double r = p / q;
/* 433 */       double spc = sp + r * (sa - sp);
/* 434 */       double spq = sp + gp / (gp - ga) * (sa - sp);
/* 435 */       if (MathPlus.abs(spc - sp) > MathPlus.abs(spq - sp)) {
/* 436 */         spf = spc;
/*     */       } else {
/* 438 */         spf = spq;
/*     */       } 
/* 440 */       bracketed = true;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 445 */     else if (MathPlus.abs(gp) < MathPlus.abs(ga)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 451 */       double spc, theta = 3.0D * (fa - fp) / (sp - sa) + ga + gp;
/* 452 */       double s = MathPlus.max(MathPlus.abs(theta), MathPlus.abs(ga), MathPlus.abs(gp));
/*     */ 
/*     */ 
/*     */       
/* 456 */       double gamma = s * MathPlus.sqrt(MathPlus.max(0.0D, theta / s * theta / s - ga / s * gp / s));
/* 457 */       if (sp > sa) {
/* 458 */         gamma = -gamma;
/*     */       }
/* 460 */       double p = gamma - gp + theta;
/* 461 */       double q = gamma + ga - gp + gamma;
/* 462 */       double r = p / q;
/*     */       
/* 464 */       if (r < 0.0D && gamma != 0.0D) {
/* 465 */         spc = sp + r * (sa - sp);
/* 466 */       } else if (sp > sa) {
/* 467 */         spc = smax;
/*     */       } else {
/* 469 */         spc = smin;
/*     */       } 
/* 471 */       double spq = sp + gp / (gp - ga) * (sa - sp);
/*     */ 
/*     */       
/* 474 */       if (bracketed)
/*     */       {
/*     */ 
/*     */         
/* 478 */         if (MathPlus.abs(spc - sp) < MathPlus.abs(spq - sp)) {
/* 479 */           spf = spc;
/*     */         } else {
/* 481 */           spf = spq;
/*     */         } 
/* 483 */         if (sp > sa) {
/* 484 */           spf = MathPlus.min(sp + 0.66D * (sb - sp), spf);
/*     */         } else {
/* 486 */           spf = MathPlus.max(sp + 0.66D * (sb - sp), spf);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else
/*     */       {
/*     */         
/* 495 */         if (MathPlus.abs(spc - sp) > MathPlus.abs(spq - sp)) {
/* 496 */           spf = spc;
/*     */         } else {
/* 498 */           spf = spq;
/*     */         } 
/* 500 */         spf = MathPlus.min(smax, spf);
/* 501 */         spf = MathPlus.max(smin, spf);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 510 */     else if (bracketed) {
/* 511 */       double theta = 3.0D * (fp - fb) / (sb - sp) + gb + gp;
/* 512 */       double s = MathPlus.max(MathPlus.abs(theta), MathPlus.abs(gb), MathPlus.abs(gp));
/* 513 */       double gamma = s * MathPlus.sqrt(theta / s * theta / s - gb / s * gp / s);
/* 514 */       if (sp > sb) {
/* 515 */         gamma = -gamma;
/*     */       }
/* 517 */       double p = gamma - gp + theta;
/* 518 */       double q = gamma - gp + gamma + gb;
/* 519 */       double r = p / q;
/* 520 */       double spc = sp + r * (sb - sp);
/* 521 */       spf = spc;
/* 522 */     } else if (sp > sa) {
/* 523 */       spf = smax;
/*     */     } else {
/* 525 */       spf = smin;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 530 */     if (fp > fa) {
/* 531 */       si.sb = sp;
/* 532 */       si.fb = fp;
/* 533 */       si.gb = gp;
/*     */     } else {
/* 535 */       if (sgng < 0.0D) {
/* 536 */         si.sb = sa;
/* 537 */         si.fb = fa;
/* 538 */         si.gb = ga;
/*     */       } 
/* 540 */       si.sa = sp;
/* 541 */       si.fa = fp;
/* 542 */       si.ga = gp;
/*     */     } 
/* 544 */     si.bracketed = bracketed;
/*     */ 
/*     */     
/* 547 */     return spf;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/LineSearch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */