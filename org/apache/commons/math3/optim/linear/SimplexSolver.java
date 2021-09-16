/*     */ package org.apache.commons.math3.optim.linear;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.TooManyIterationsException;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
/*     */ import org.apache.commons.math3.optim.PointValuePair;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimplexSolver
/*     */   extends LinearOptimizer
/*     */ {
/*     */   static final int DEFAULT_ULPS = 10;
/*     */   static final double DEFAULT_CUT_OFF = 1.0E-10D;
/*     */   private static final double DEFAULT_EPSILON = 1.0E-6D;
/*     */   private final double epsilon;
/*     */   private final int maxUlps;
/*     */   private final double cutOff;
/*     */   private PivotSelectionRule pivotSelection;
/*     */   private SolutionCallback solutionCallback;
/*     */   
/*     */   public SimplexSolver() {
/* 100 */     this(1.0E-6D, 10, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexSolver(double epsilon) {
/* 109 */     this(epsilon, 10, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexSolver(double epsilon, int maxUlps) {
/* 119 */     this(epsilon, maxUlps, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexSolver(double epsilon, int maxUlps, double cutOff) {
/* 130 */     this.epsilon = epsilon;
/* 131 */     this.maxUlps = maxUlps;
/* 132 */     this.cutOff = cutOff;
/* 133 */     this.pivotSelection = PivotSelectionRule.DANTZIG;
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
/*     */   public PointValuePair optimize(OptimizationData... optData) throws TooManyIterationsException {
/* 154 */     return super.optimize(optData);
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
/*     */   protected void parseOptimizationData(OptimizationData... optData) {
/* 172 */     super.parseOptimizationData(optData);
/*     */ 
/*     */     
/* 175 */     this.solutionCallback = null;
/*     */     
/* 177 */     for (OptimizationData data : optData) {
/* 178 */       if (data instanceof SolutionCallback) {
/* 179 */         this.solutionCallback = (SolutionCallback)data;
/*     */       
/*     */       }
/* 182 */       else if (data instanceof PivotSelectionRule) {
/* 183 */         this.pivotSelection = (PivotSelectionRule)data;
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
/*     */   private Integer getPivotColumn(SimplexTableau tableau) {
/* 196 */     double minValue = 0.0D;
/* 197 */     Integer minPos = null;
/* 198 */     for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getWidth() - 1; i++) {
/* 199 */       double entry = tableau.getEntry(0, i);
/*     */ 
/*     */       
/* 202 */       if (entry < minValue) {
/* 203 */         minValue = entry;
/* 204 */         minPos = Integer.valueOf(i);
/*     */ 
/*     */         
/* 207 */         if (this.pivotSelection == PivotSelectionRule.BLAND && isValidPivotColumn(tableau, i)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 212 */     return minPos;
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
/*     */   private boolean isValidPivotColumn(SimplexTableau tableau, int col) {
/* 228 */     for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getHeight(); i++) {
/* 229 */       double entry = tableau.getEntry(i, col);
/*     */ 
/*     */       
/* 232 */       if (Precision.compareTo(entry, 0.0D, this.cutOff) > 0) {
/* 233 */         return true;
/*     */       }
/*     */     } 
/* 236 */     return false;
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
/*     */   private Integer getPivotRow(SimplexTableau tableau, int col) {
/* 248 */     List<Integer> minRatioPositions = new ArrayList<Integer>();
/* 249 */     double minRatio = Double.MAX_VALUE;
/* 250 */     for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getHeight(); i++) {
/* 251 */       double rhs = tableau.getEntry(i, tableau.getWidth() - 1);
/* 252 */       double entry = tableau.getEntry(i, col);
/*     */ 
/*     */ 
/*     */       
/* 256 */       if (Precision.compareTo(entry, 0.0D, this.cutOff) > 0) {
/* 257 */         double ratio = FastMath.abs(rhs / entry);
/*     */ 
/*     */         
/* 260 */         int cmp = Double.compare(ratio, minRatio);
/* 261 */         if (cmp == 0) {
/* 262 */           minRatioPositions.add(Integer.valueOf(i));
/* 263 */         } else if (cmp < 0) {
/* 264 */           minRatio = ratio;
/* 265 */           minRatioPositions.clear();
/* 266 */           minRatioPositions.add(Integer.valueOf(i));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 271 */     if (minRatioPositions.size() == 0)
/* 272 */       return null; 
/* 273 */     if (minRatioPositions.size() > 1) {
/*     */ 
/*     */ 
/*     */       
/* 277 */       if (tableau.getNumArtificialVariables() > 0) {
/* 278 */         for (Integer row : minRatioPositions) {
/* 279 */           for (int j = 0; j < tableau.getNumArtificialVariables(); j++) {
/* 280 */             int column = j + tableau.getArtificialVariableOffset();
/* 281 */             double entry = tableau.getEntry(row.intValue(), column);
/* 282 */             if (Precision.equals(entry, 1.0D, this.maxUlps) && row.equals(tableau.getBasicRow(column))) {
/* 283 */               return row;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 295 */       Integer minRow = null;
/* 296 */       int minIndex = tableau.getWidth();
/* 297 */       for (Integer row : minRatioPositions) {
/* 298 */         int basicVar = tableau.getBasicVariable(row.intValue());
/* 299 */         if (basicVar < minIndex) {
/* 300 */           minIndex = basicVar;
/* 301 */           minRow = row;
/*     */         } 
/*     */       } 
/* 304 */       return minRow;
/*     */     } 
/* 306 */     return minRatioPositions.get(0);
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
/*     */   protected void doIteration(SimplexTableau tableau) throws TooManyIterationsException, UnboundedSolutionException {
/* 320 */     incrementIterationCount();
/*     */     
/* 322 */     Integer pivotCol = getPivotColumn(tableau);
/* 323 */     Integer pivotRow = getPivotRow(tableau, pivotCol.intValue());
/* 324 */     if (pivotRow == null) {
/* 325 */       throw new UnboundedSolutionException();
/*     */     }
/*     */     
/* 328 */     tableau.performRowOperations(pivotCol.intValue(), pivotRow.intValue());
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
/*     */   protected void solvePhase1(SimplexTableau tableau) throws TooManyIterationsException, UnboundedSolutionException, NoFeasibleSolutionException {
/* 345 */     if (tableau.getNumArtificialVariables() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 349 */     while (!tableau.isOptimal()) {
/* 350 */       doIteration(tableau);
/*     */     }
/*     */ 
/*     */     
/* 354 */     if (!Precision.equals(tableau.getEntry(0, tableau.getRhsOffset()), 0.0D, this.epsilon)) {
/* 355 */       throw new NoFeasibleSolutionException();
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
/*     */   public PointValuePair doOptimize() throws TooManyIterationsException, UnboundedSolutionException, NoFeasibleSolutionException {
/* 368 */     if (this.solutionCallback != null) {
/* 369 */       this.solutionCallback.setTableau(null);
/*     */     }
/*     */     
/* 372 */     SimplexTableau tableau = new SimplexTableau(getFunction(), getConstraints(), getGoalType(), isRestrictedToNonNegative(), this.epsilon, this.maxUlps);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 380 */     solvePhase1(tableau);
/* 381 */     tableau.dropPhase1Objective();
/*     */ 
/*     */     
/* 384 */     if (this.solutionCallback != null) {
/* 385 */       this.solutionCallback.setTableau(tableau);
/*     */     }
/*     */     
/* 388 */     while (!tableau.isOptimal()) {
/* 389 */       doIteration(tableau);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 396 */     PointValuePair solution = tableau.getSolution();
/* 397 */     if (isRestrictedToNonNegative()) {
/* 398 */       double[] coeff = solution.getPoint();
/* 399 */       for (int i = 0; i < coeff.length; i++) {
/* 400 */         if (Precision.compareTo(coeff[i], 0.0D, this.epsilon) < 0) {
/* 401 */           throw new NoFeasibleSolutionException();
/*     */         }
/*     */       } 
/*     */     } 
/* 405 */     return solution;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/linear/SimplexSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */