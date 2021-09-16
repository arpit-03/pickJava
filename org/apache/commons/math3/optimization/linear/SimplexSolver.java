/*     */ package org.apache.commons.math3.optimization.linear;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.optimization.PointValuePair;
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
/*     */ @Deprecated
/*     */ public class SimplexSolver
/*     */   extends AbstractLinearOptimizer
/*     */ {
/*     */   private static final double DEFAULT_EPSILON = 1.0E-6D;
/*     */   private static final int DEFAULT_ULPS = 10;
/*     */   private final double epsilon;
/*     */   private final int maxUlps;
/*     */   
/*     */   public SimplexSolver() {
/*  53 */     this(1.0E-6D, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexSolver(double epsilon, int maxUlps) {
/*  62 */     this.epsilon = epsilon;
/*  63 */     this.maxUlps = maxUlps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer getPivotColumn(SimplexTableau tableau) {
/*  72 */     double minValue = 0.0D;
/*  73 */     Integer minPos = null;
/*  74 */     for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getWidth() - 1; i++) {
/*  75 */       double entry = tableau.getEntry(0, i);
/*     */ 
/*     */       
/*  78 */       if (entry < minValue) {
/*  79 */         minValue = entry;
/*  80 */         minPos = Integer.valueOf(i);
/*     */       } 
/*     */     } 
/*  83 */     return minPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer getPivotRow(SimplexTableau tableau, int col) {
/*  94 */     List<Integer> minRatioPositions = new ArrayList<Integer>();
/*  95 */     double minRatio = Double.MAX_VALUE;
/*  96 */     for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getHeight(); i++) {
/*  97 */       double rhs = tableau.getEntry(i, tableau.getWidth() - 1);
/*  98 */       double entry = tableau.getEntry(i, col);
/*     */       
/* 100 */       if (Precision.compareTo(entry, 0.0D, this.maxUlps) > 0) {
/* 101 */         double ratio = rhs / entry;
/*     */ 
/*     */         
/* 104 */         int cmp = Double.compare(ratio, minRatio);
/* 105 */         if (cmp == 0) {
/* 106 */           minRatioPositions.add(Integer.valueOf(i));
/* 107 */         } else if (cmp < 0) {
/* 108 */           minRatio = ratio;
/* 109 */           minRatioPositions = new ArrayList<Integer>();
/* 110 */           minRatioPositions.add(Integer.valueOf(i));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     if (minRatioPositions.size() == 0)
/* 116 */       return null; 
/* 117 */     if (minRatioPositions.size() > 1) {
/*     */ 
/*     */ 
/*     */       
/* 121 */       if (tableau.getNumArtificialVariables() > 0) {
/* 122 */         for (Integer row : minRatioPositions) {
/* 123 */           for (int j = 0; j < tableau.getNumArtificialVariables(); j++) {
/* 124 */             int column = j + tableau.getArtificialVariableOffset();
/* 125 */             double entry = tableau.getEntry(row.intValue(), column);
/* 126 */             if (Precision.equals(entry, 1.0D, this.maxUlps) && row.equals(tableau.getBasicRow(column))) {
/* 127 */               return row;
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
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (getIterations() < getMaxIterations() / 2) {
/* 143 */         Integer minRow = null;
/* 144 */         int minIndex = tableau.getWidth();
/* 145 */         int varStart = tableau.getNumObjectiveFunctions();
/* 146 */         int varEnd = tableau.getWidth() - 1;
/* 147 */         for (Integer row : minRatioPositions) {
/* 148 */           for (int j = varStart; j < varEnd && !row.equals(minRow); j++) {
/* 149 */             Integer basicRow = tableau.getBasicRow(j);
/* 150 */             if (basicRow != null && basicRow.equals(row) && j < minIndex) {
/* 151 */               minIndex = j;
/* 152 */               minRow = row;
/*     */             } 
/*     */           } 
/*     */         } 
/* 156 */         return minRow;
/*     */       } 
/*     */     } 
/* 159 */     return minRatioPositions.get(0);
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
/*     */   protected void doIteration(SimplexTableau tableau) throws MaxCountExceededException, UnboundedSolutionException {
/* 171 */     incrementIterationsCounter();
/*     */     
/* 173 */     Integer pivotCol = getPivotColumn(tableau);
/* 174 */     Integer pivotRow = getPivotRow(tableau, pivotCol.intValue());
/* 175 */     if (pivotRow == null) {
/* 176 */       throw new UnboundedSolutionException();
/*     */     }
/*     */ 
/*     */     
/* 180 */     double pivotVal = tableau.getEntry(pivotRow.intValue(), pivotCol.intValue());
/* 181 */     tableau.divideRow(pivotRow.intValue(), pivotVal);
/*     */ 
/*     */     
/* 184 */     for (int i = 0; i < tableau.getHeight(); i++) {
/* 185 */       if (i != pivotRow.intValue()) {
/* 186 */         double multiplier = tableau.getEntry(i, pivotCol.intValue());
/* 187 */         tableau.subtractRow(i, pivotRow.intValue(), multiplier);
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
/*     */   
/*     */   protected void solvePhase1(SimplexTableau tableau) throws MaxCountExceededException, UnboundedSolutionException, NoFeasibleSolutionException {
/* 203 */     if (tableau.getNumArtificialVariables() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 207 */     while (!tableau.isOptimal()) {
/* 208 */       doIteration(tableau);
/*     */     }
/*     */ 
/*     */     
/* 212 */     if (!Precision.equals(tableau.getEntry(0, tableau.getRhsOffset()), 0.0D, this.epsilon)) {
/* 213 */       throw new NoFeasibleSolutionException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointValuePair doOptimize() throws MaxCountExceededException, UnboundedSolutionException, NoFeasibleSolutionException {
/* 221 */     SimplexTableau tableau = new SimplexTableau(getFunction(), getConstraints(), getGoalType(), restrictToNonNegative(), this.epsilon, this.maxUlps);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     solvePhase1(tableau);
/* 230 */     tableau.dropPhase1Objective();
/*     */     
/* 232 */     while (!tableau.isOptimal()) {
/* 233 */       doIteration(tableau);
/*     */     }
/* 235 */     return tableau.getSolution();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/linear/SimplexSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */