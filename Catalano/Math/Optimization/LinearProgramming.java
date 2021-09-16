/*     */ package Catalano.Math.Optimization;
/*     */ 
/*     */ import Catalano.Math.Matrix;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinearProgramming
/*     */ {
/*     */   public static final int INFEASIBLE = 0;
/*     */   public static final int OPTIMAL = 1;
/*     */   public static final int UNBOUNDED = 2;
/*     */   private static final int SECOND_PHASE = -2;
/*     */   private double[] function;
/*     */   private Objective objective;
/*     */   private double[] r;
/*     */   private double solution;
/*     */   private boolean isInfinite;
/*     */   private int iterations;
/*     */   private List<Constraint> constraints;
/*     */   
/*     */   public enum Objective
/*     */   {
/*  57 */     Minimize,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     Maximize;
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
/*     */   public int getIterations() {
/*  86 */     return this.iterations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSolution() {
/*  94 */     return this.solution;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getCoefficients() {
/* 102 */     return this.r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinearProgramming(Objective objective) {
/* 110 */     this.objective = objective;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInfinite() {
/* 118 */     return this.isInfinite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Solve(double[] function, List<Constraint> constraints) {
/* 129 */     this.function = function;
/* 130 */     this.constraints = constraints;
/* 131 */     this.iterations = 0;
/* 132 */     this.isInfinite = false;
/*     */ 
/*     */     
/* 135 */     Tableau tableau = CreateTableau(function, constraints);
/*     */     
/* 137 */     double[][] tab = tableau.getTableau();
/* 138 */     double[] vars = tableau.getCoefVars();
/* 139 */     int[] basis = tableau.getBasis();
/*     */     
/* 141 */     int status = -1;
/*     */ 
/*     */     
/*     */     while (true) {
/* 145 */       this.iterations++;
/*     */ 
/*     */       
/* 148 */       int q = findColumn(tab, vars, basis);
/*     */ 
/*     */       
/* 151 */       if (q == -2) {
/* 152 */         tableau = CreateSecondTableau(tab, vars, basis);
/* 153 */         tab = tableau.getTableau();
/* 154 */         vars = tableau.getCoefVars();
/* 155 */         basis = tableau.getBasis();
/*     */       } 
/*     */ 
/*     */       
/* 159 */       if (q >= 0 && 
/* 160 */         isUnbounded(tab, q)) {
/* 161 */         return 2;
/*     */       }
/*     */       
/* 164 */       if (q == -1) {
/*     */ 
/*     */         
/* 167 */         if (isInfeasible(tab, vars, basis)) {
/* 168 */           return 0;
/*     */         }
/*     */         
/* 171 */         status = 1;
/*     */         
/*     */         break;
/*     */       } 
/* 175 */       if (q >= 0) {
/* 176 */         int p = findRow(tab, q, vars, basis);
/*     */ 
/*     */         
/* 179 */         basis[p] = q;
/*     */ 
/*     */         
/* 182 */         double pivot = tab[p][q];
/*     */         
/*     */         int j;
/* 185 */         for (j = 0; j < (tab[0]).length; j++) {
/* 186 */           tab[p][j] = tab[p][j] / pivot;
/*     */         }
/*     */ 
/*     */         
/* 190 */         for (j = 0; j < tab.length; j++) {
/* 191 */           if (j != p) {
/* 192 */             pivot = tab[j][q];
/* 193 */             for (int k = 0; k < (tab[0]).length; k++) {
/* 194 */               tab[j][k] = tab[j][k] - pivot * tab[p][k];
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 202 */     this.r = new double[vars.length]; int i;
/* 203 */     for (i = 0; i < basis.length; i++) {
/* 204 */       this.r[basis[i]] = tab[i][(tab[0]).length - 1];
/*     */     }
/*     */ 
/*     */     
/* 208 */     this.solution = 0.0D;
/* 209 */     for (i = 0; i < function.length; i++) {
/* 210 */       this.solution += function[i] * this.r[i];
/*     */     }
/*     */     
/* 213 */     if (this.objective == Objective.Minimize) {
/* 214 */       for (i = 0; i < function.length; i++) {
/* 215 */         function[i] = function[i] * -1.0D;
/*     */       }
/*     */     }
/*     */     
/* 219 */     if (this.objective == Objective.Minimize) {
/* 220 */       this.solution = -this.solution;
/*     */     }
/* 222 */     this.r = Arrays.copyOf(this.r, function.length);
/* 223 */     return status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Tableau CreateSecondTableau(double[][] tableau, double[] vars, int[] basis) {
/* 230 */     int c = 0;
/* 231 */     for (int i = 0; i < vars.length; i++) {
/* 232 */       if (vars[i] == -1.0D) c++;
/*     */     
/*     */     } 
/*     */     
/* 236 */     int[] index = new int[c];
/* 237 */     int idx = 0; int j;
/* 238 */     for (j = 0; j < vars.length; j++) {
/* 239 */       if (vars[j] == -1.0D) {
/* 240 */         index[idx] = j;
/* 241 */         idx++;
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     tableau = Matrix.RemoveColumns(tableau, index);
/*     */     
/* 247 */     vars = new double[(tableau[0]).length];
/*     */     
/* 249 */     for (j = 0; j < this.function.length; j++) {
/* 250 */       vars[j] = this.function[j];
/*     */     }
/*     */     
/* 253 */     return new Tableau(tableau, vars, basis);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isUnbounded(double[][] tableau, int q) {
/* 258 */     int c = 0;
/* 259 */     for (int i = 0; i < tableau.length; i++) {
/* 260 */       if (tableau[i][q] <= 0.0D) c++;
/*     */     
/*     */     } 
/* 263 */     if (c == tableau.length) return true;
/*     */     
/* 265 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isInfeasible(double[][] tableau, double[] artVariables, int[] basis) {
/* 269 */     for (int i = 0; i < tableau.length; i++) {
/* 270 */       if (tableau[i][(tableau[0]).length - 1] > 0.0D && artVariables[basis[i]] == -1.0D) {
/* 271 */         return true;
/*     */       }
/*     */     } 
/* 274 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findColumn(double[][] tableau, double[] coef, int[] basis) {
/* 280 */     double[] z = new double[(tableau[0]).length - 1]; int i;
/* 281 */     for (i = 0; i < tableau.length; i++) {
/* 282 */       for (int k = 0; k < (tableau[0]).length - 1; k++) {
/* 283 */         z[k] = z[k] + tableau[i][k] * coef[basis[i]];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 288 */     for (i = 0; i < coef.length - 1; i++) {
/* 289 */       z[i] = z[i] - coef[i];
/*     */     }
/*     */ 
/*     */     
/* 293 */     boolean isOptimal = true;
/* 294 */     for (int j = 0; j < z.length; j++) {
/* 295 */       if (z[j] < 0.0D) {
/* 296 */         isOptimal = false;
/*     */       }
/*     */     } 
/*     */     
/* 300 */     if (isOptimal) {
/* 301 */       double sum = 0.0D; int k;
/* 302 */       for (k = 0; k < tableau.length; k++) {
/* 303 */         sum += tableau[k][(tableau[0]).length - 1] * coef[basis[k]];
/*     */       }
/*     */ 
/*     */       
/* 307 */       if (sum == 0.0D) {
/* 308 */         return -2;
/*     */       }
/*     */ 
/*     */       
/* 312 */       for (k = 0; k < basis.length; k++) {
/* 313 */         coef[basis[k]] = Double.NaN;
/*     */       }
/*     */       
/* 316 */       int c = 0;
/* 317 */       for (int m = 0; m < z.length; m++) {
/* 318 */         if (!Double.isNaN(coef[m]) && z[m] == 0.0D) c++;
/*     */       
/*     */       } 
/* 321 */       if (c > 0) this.isInfinite = true;
/*     */       
/* 323 */       return -1;
/*     */     } 
/*     */ 
/*     */     
/* 327 */     if (isOptimal) return -1;
/*     */     
/* 329 */     return Matrix.MinIndex(z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findRow(double[][] tableau, int q, double[] artVar, int[] basis) {
/* 335 */     double min = Double.MAX_VALUE;
/* 336 */     double[] div = new double[tableau.length];
/* 337 */     for (int i = 0; i < div.length; i++) {
/* 338 */       if (tableau[i][q] > 0.0D) {
/* 339 */         div[i] = tableau[i][(tableau[0]).length - 1] / tableau[i][q];
/* 340 */         min = Math.min(min, div[i]);
/*     */       } else {
/*     */         
/* 343 */         div[i] = Double.MAX_VALUE;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 349 */     int c = 0; int j;
/* 350 */     for (j = 0; j < div.length; j++) {
/* 351 */       if (div[j] == min) c++;
/*     */     
/*     */     } 
/* 354 */     if (c == 1) return Matrix.MinIndex(div);
/*     */     
/* 356 */     for (j = 0; j < div.length; j++) {
/* 357 */       if (div[j] == min && artVar[basis[j]] == -1.0D) {
/* 358 */         return j;
/*     */       }
/*     */     } 
/* 361 */     return Matrix.MinIndex(div);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Tableau CreateTableau(double[] function, List<Constraint> constraint) {
/* 368 */     if (this.objective == Objective.Minimize) {
/* 369 */       for (int k = 0; k < function.length; k++) {
/* 370 */         function[k] = function[k] * -1.0D;
/*     */       }
/*     */     }
/*     */     
/* 374 */     boolean hasArtificialVariable = false;
/* 375 */     int nBasic = 0;
/* 376 */     int nArtificial = 0;
/*     */ 
/*     */     
/* 379 */     int vars = 0;
/* 380 */     for (Constraint c : constraint) {
/* 381 */       switch (c.getSymbol()) {
/*     */         case LESS_THAN:
/* 383 */           nBasic++;
/*     */         
/*     */         case null:
/* 386 */           nArtificial++;
/*     */         
/*     */         case GREATER_THAN:
/* 389 */           nBasic++;
/* 390 */           nArtificial++;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 395 */     vars = nBasic + nArtificial;
/* 396 */     if (nArtificial > 0) hasArtificialVariable = true;
/*     */ 
/*     */     
/* 399 */     double[][] tableau = new double[constraint.size()][function.length + vars + 1];
/*     */     
/* 401 */     int[] basis = new int[constraint.size()]; int i;
/* 402 */     for (i = 0; i < basis.length; i++) {
/* 403 */       basis[i] = i;
/*     */     }
/*     */ 
/*     */     
/* 407 */     for (i = 0; i < constraint.size(); i++) {
/* 408 */       Constraint c = constraint.get(i);
/* 409 */       for (int k = 0; k < function.length; k++) {
/* 410 */         if (c.getRightSide() >= 0.0D) {
/* 411 */           tableau[i][k] = c.getLeftSide()[k];
/*     */         } else {
/* 413 */           tableau[i][k] = -c.getLeftSide()[k];
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 418 */     for (i = 0; i < constraint.size(); i++) {
/* 419 */       Constraint c = constraint.get(i);
/* 420 */       if (c.getRightSide() >= 0.0D) {
/* 421 */         tableau[i][(tableau[0]).length - 1] = c.getRightSide();
/*     */       } else {
/* 423 */         tableau[i][(tableau[0]).length - 1] = -c.getRightSide();
/*     */       } 
/*     */     } 
/*     */     
/* 427 */     double[] t = new double[function.length + vars + 1];
/*     */     
/* 429 */     if (!hasArtificialVariable) {
/* 430 */       for (int k = 0; k < function.length; k++) {
/* 431 */         t[k] = function[k];
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 436 */     int movesSx = function.length;
/* 437 */     int movesAx = function.length + nBasic;
/* 438 */     for (int j = 0; j < constraint.size(); j++) {
/*     */       
/* 440 */       Constraint c = constraint.get(j);
/*     */       
/* 442 */       if (c.getRightSide() >= 0.0D) {
/* 443 */         switch (c.getSymbol()) {
/*     */           case LESS_THAN:
/* 445 */             tableau[j][movesSx] = 1.0D;
/* 446 */             basis[j] = movesSx;
/*     */             
/* 448 */             movesSx++;
/*     */             break;
/*     */           case null:
/* 451 */             tableau[j][movesAx] = 1.0D;
/* 452 */             basis[j] = movesAx;
/* 453 */             t[movesAx] = -1.0D;
/*     */             
/* 455 */             movesAx++;
/*     */             break;
/*     */           case GREATER_THAN:
/* 458 */             tableau[j][movesSx] = -1.0D;
/* 459 */             tableau[j][movesAx] = 1.0D;
/* 460 */             t[movesSx] = 0.0D;
/* 461 */             t[movesAx] = -1.0D;
/* 462 */             basis[j] = movesAx;
/*     */             
/* 464 */             movesSx++;
/* 465 */             movesAx++;
/*     */             break;
/*     */         } 
/*     */       
/*     */       } else {
/* 470 */         switch (c.getSymbol()) {
/*     */           case LESS_THAN:
/* 472 */             tableau[j][movesSx] = -1.0D;
/* 473 */             tableau[j][movesAx] = 1.0D;
/* 474 */             t[movesSx] = 0.0D;
/* 475 */             t[movesAx] = -1.0D;
/* 476 */             basis[j] = movesAx;
/*     */             
/* 478 */             movesSx++;
/* 479 */             movesAx++;
/*     */             break;
/*     */           case null:
/* 482 */             tableau[j][movesAx] = 1.0D;
/* 483 */             basis[j] = movesAx;
/* 484 */             t[movesAx] = -1.0D;
/*     */             
/* 486 */             movesAx++;
/*     */             break;
/*     */           case GREATER_THAN:
/* 489 */             tableau[j][movesSx] = 1.0D;
/* 490 */             basis[j] = movesSx;
/*     */             
/* 492 */             movesSx++;
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/*     */     } 
/* 499 */     return new Tableau(tableau, t, basis);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Optimization/LinearProgramming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */