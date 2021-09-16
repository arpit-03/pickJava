/*     */ package org.apache.commons.math3.optimization.linear;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.optimization.GoalType;
/*     */ import org.apache.commons.math3.optimization.PointValuePair;
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
/*     */ @Deprecated
/*     */ class SimplexTableau
/*     */   implements Serializable
/*     */ {
/*     */   private static final String NEGATIVE_VAR_COLUMN_LABEL = "x-";
/*     */   private static final int DEFAULT_ULPS = 10;
/*     */   private static final double CUTOFF_THRESHOLD = 1.0E-12D;
/*     */   private static final long serialVersionUID = -1369660067587938365L;
/*     */   private final LinearObjectiveFunction f;
/*     */   private final List<LinearConstraint> constraints;
/*     */   private final boolean restrictToNonNegative;
/*  90 */   private final List<String> columnLabels = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient RealMatrix tableau;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int numDecisionVariables;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int numSlackVariables;
/*     */ 
/*     */ 
/*     */   
/*     */   private int numArtificialVariables;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double epsilon;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int maxUlps;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SimplexTableau(LinearObjectiveFunction f, Collection<LinearConstraint> constraints, GoalType goalType, boolean restrictToNonNegative, double epsilon) {
/* 122 */     this(f, constraints, goalType, restrictToNonNegative, epsilon, 10);
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
/*     */   SimplexTableau(LinearObjectiveFunction f, Collection<LinearConstraint> constraints, GoalType goalType, boolean restrictToNonNegative, double epsilon, int maxUlps) {
/* 139 */     this.f = f;
/* 140 */     this.constraints = normalizeConstraints(constraints);
/* 141 */     this.restrictToNonNegative = restrictToNonNegative;
/* 142 */     this.epsilon = epsilon;
/* 143 */     this.maxUlps = maxUlps;
/* 144 */     this.numDecisionVariables = f.getCoefficients().getDimension() + (restrictToNonNegative ? 0 : 1);
/*     */     
/* 146 */     this.numSlackVariables = getConstraintTypeCounts(Relationship.LEQ) + getConstraintTypeCounts(Relationship.GEQ);
/*     */     
/* 148 */     this.numArtificialVariables = getConstraintTypeCounts(Relationship.EQ) + getConstraintTypeCounts(Relationship.GEQ);
/*     */     
/* 150 */     this.tableau = createTableau((goalType == GoalType.MAXIMIZE));
/* 151 */     initializeColumnLabels();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeColumnLabels() {
/* 158 */     if (getNumObjectiveFunctions() == 2) {
/* 159 */       this.columnLabels.add("W");
/*     */     }
/* 161 */     this.columnLabels.add("Z"); int i;
/* 162 */     for (i = 0; i < getOriginalNumDecisionVariables(); i++) {
/* 163 */       this.columnLabels.add("x" + i);
/*     */     }
/* 165 */     if (!this.restrictToNonNegative) {
/* 166 */       this.columnLabels.add("x-");
/*     */     }
/* 168 */     for (i = 0; i < getNumSlackVariables(); i++) {
/* 169 */       this.columnLabels.add("s" + i);
/*     */     }
/* 171 */     for (i = 0; i < getNumArtificialVariables(); i++) {
/* 172 */       this.columnLabels.add("a" + i);
/*     */     }
/* 174 */     this.columnLabels.add("RHS");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RealMatrix createTableau(boolean maximize) {
/* 185 */     int width = this.numDecisionVariables + this.numSlackVariables + this.numArtificialVariables + getNumObjectiveFunctions() + 1;
/*     */     
/* 187 */     int height = this.constraints.size() + getNumObjectiveFunctions();
/* 188 */     Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(height, width);
/*     */ 
/*     */     
/* 191 */     if (getNumObjectiveFunctions() == 2) {
/* 192 */       matrix.setEntry(0, 0, -1.0D);
/*     */     }
/* 194 */     int zIndex = (getNumObjectiveFunctions() == 1) ? 0 : 1;
/* 195 */     matrix.setEntry(zIndex, zIndex, maximize ? 1.0D : -1.0D);
/* 196 */     RealVector objectiveCoefficients = maximize ? this.f.getCoefficients().mapMultiply(-1.0D) : this.f.getCoefficients();
/*     */     
/* 198 */     copyArray(objectiveCoefficients.toArray(), matrix.getDataRef()[zIndex]);
/* 199 */     matrix.setEntry(zIndex, width - 1, maximize ? this.f.getConstantTerm() : (-1.0D * this.f.getConstantTerm()));
/*     */ 
/*     */     
/* 202 */     if (!this.restrictToNonNegative) {
/* 203 */       matrix.setEntry(zIndex, getSlackVariableOffset() - 1, getInvertedCoefficientSum(objectiveCoefficients));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 208 */     int slackVar = 0;
/* 209 */     int artificialVar = 0;
/* 210 */     for (int i = 0; i < this.constraints.size(); i++) {
/* 211 */       LinearConstraint constraint = this.constraints.get(i);
/* 212 */       int row = getNumObjectiveFunctions() + i;
/*     */ 
/*     */       
/* 215 */       copyArray(constraint.getCoefficients().toArray(), matrix.getDataRef()[row]);
/*     */ 
/*     */       
/* 218 */       if (!this.restrictToNonNegative) {
/* 219 */         matrix.setEntry(row, getSlackVariableOffset() - 1, getInvertedCoefficientSum(constraint.getCoefficients()));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 224 */       matrix.setEntry(row, width - 1, constraint.getValue());
/*     */ 
/*     */       
/* 227 */       if (constraint.getRelationship() == Relationship.LEQ) {
/* 228 */         matrix.setEntry(row, getSlackVariableOffset() + slackVar++, 1.0D);
/* 229 */       } else if (constraint.getRelationship() == Relationship.GEQ) {
/* 230 */         matrix.setEntry(row, getSlackVariableOffset() + slackVar++, -1.0D);
/*     */       } 
/*     */ 
/*     */       
/* 234 */       if (constraint.getRelationship() == Relationship.EQ || constraint.getRelationship() == Relationship.GEQ) {
/*     */         
/* 236 */         matrix.setEntry(0, getArtificialVariableOffset() + artificialVar, 1.0D);
/* 237 */         matrix.setEntry(row, getArtificialVariableOffset() + artificialVar++, 1.0D);
/* 238 */         matrix.setRowVector(0, matrix.getRowVector(0).subtract(matrix.getRowVector(row)));
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     return (RealMatrix)matrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<LinearConstraint> normalizeConstraints(Collection<LinearConstraint> originalConstraints) {
/* 251 */     List<LinearConstraint> normalized = new ArrayList<LinearConstraint>(originalConstraints.size());
/* 252 */     for (LinearConstraint constraint : originalConstraints) {
/* 253 */       normalized.add(normalize(constraint));
/*     */     }
/* 255 */     return normalized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LinearConstraint normalize(LinearConstraint constraint) {
/* 264 */     if (constraint.getValue() < 0.0D) {
/* 265 */       return new LinearConstraint(constraint.getCoefficients().mapMultiply(-1.0D), constraint.getRelationship().oppositeRelationship(), -1.0D * constraint.getValue());
/*     */     }
/*     */ 
/*     */     
/* 269 */     return new LinearConstraint(constraint.getCoefficients(), constraint.getRelationship(), constraint.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getNumObjectiveFunctions() {
/* 278 */     return (this.numArtificialVariables > 0) ? 2 : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getConstraintTypeCounts(Relationship relationship) {
/* 287 */     int count = 0;
/* 288 */     for (LinearConstraint constraint : this.constraints) {
/* 289 */       if (constraint.getRelationship() == relationship) {
/* 290 */         count++;
/*     */       }
/*     */     } 
/* 293 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static double getInvertedCoefficientSum(RealVector coefficients) {
/* 302 */     double sum = 0.0D;
/* 303 */     for (double coefficient : coefficients.toArray()) {
/* 304 */       sum -= coefficient;
/*     */     }
/* 306 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Integer getBasicRow(int col) {
/* 315 */     Integer row = null;
/* 316 */     for (int i = 0; i < getHeight(); i++) {
/* 317 */       double entry = getEntry(i, col);
/* 318 */       if (Precision.equals(entry, 1.0D, this.maxUlps) && row == null) {
/* 319 */         row = Integer.valueOf(i);
/* 320 */       } else if (!Precision.equals(entry, 0.0D, this.maxUlps)) {
/* 321 */         return null;
/*     */       } 
/*     */     } 
/* 324 */     return row;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropPhase1Objective() {
/* 332 */     if (getNumObjectiveFunctions() == 1) {
/*     */       return;
/*     */     }
/*     */     
/* 336 */     Set<Integer> columnsToDrop = new TreeSet<Integer>();
/* 337 */     columnsToDrop.add(Integer.valueOf(0));
/*     */     
/*     */     int i;
/* 340 */     for (i = getNumObjectiveFunctions(); i < getArtificialVariableOffset(); i++) {
/* 341 */       double entry = this.tableau.getEntry(0, i);
/* 342 */       if (Precision.compareTo(entry, 0.0D, this.epsilon) > 0) {
/* 343 */         columnsToDrop.add(Integer.valueOf(i));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 348 */     for (i = 0; i < getNumArtificialVariables(); i++) {
/* 349 */       int col = i + getArtificialVariableOffset();
/* 350 */       if (getBasicRow(col) == null) {
/* 351 */         columnsToDrop.add(Integer.valueOf(col));
/*     */       }
/*     */     } 
/*     */     
/* 355 */     double[][] matrix = new double[getHeight() - 1][getWidth() - columnsToDrop.size()];
/* 356 */     for (int j = 1; j < getHeight(); j++) {
/* 357 */       int col = 0;
/* 358 */       for (int m = 0; m < getWidth(); m++) {
/* 359 */         if (!columnsToDrop.contains(Integer.valueOf(m))) {
/* 360 */           matrix[j - 1][col++] = this.tableau.getEntry(j, m);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 366 */     Integer[] drop = columnsToDrop.<Integer>toArray(new Integer[columnsToDrop.size()]);
/* 367 */     for (int k = drop.length - 1; k >= 0; k--) {
/* 368 */       this.columnLabels.remove(drop[k].intValue());
/*     */     }
/*     */     
/* 371 */     this.tableau = (RealMatrix)new Array2DRowRealMatrix(matrix);
/* 372 */     this.numArtificialVariables = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyArray(double[] src, double[] dest) {
/* 380 */     System.arraycopy(src, 0, dest, getNumObjectiveFunctions(), src.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isOptimal() {
/* 388 */     for (int i = getNumObjectiveFunctions(); i < getWidth() - 1; i++) {
/* 389 */       double entry = this.tableau.getEntry(0, i);
/* 390 */       if (Precision.compareTo(entry, 0.0D, this.epsilon) < 0) {
/* 391 */         return false;
/*     */       }
/*     */     } 
/* 394 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointValuePair getSolution() {
/* 402 */     int negativeVarColumn = this.columnLabels.indexOf("x-");
/* 403 */     Integer negativeVarBasicRow = (negativeVarColumn > 0) ? getBasicRow(negativeVarColumn) : null;
/* 404 */     double mostNegative = (negativeVarBasicRow == null) ? 0.0D : getEntry(negativeVarBasicRow.intValue(), getRhsOffset());
/*     */     
/* 406 */     Set<Integer> basicRows = new HashSet<Integer>();
/* 407 */     double[] coefficients = new double[getOriginalNumDecisionVariables()];
/* 408 */     for (int i = 0; i < coefficients.length; i++) {
/* 409 */       int colIndex = this.columnLabels.indexOf("x" + i);
/* 410 */       if (colIndex < 0) {
/* 411 */         coefficients[i] = 0.0D;
/*     */       } else {
/*     */         
/* 414 */         Integer basicRow = getBasicRow(colIndex);
/* 415 */         if (basicRow != null && basicRow.intValue() == 0) {
/*     */ 
/*     */ 
/*     */           
/* 419 */           coefficients[i] = 0.0D;
/* 420 */         } else if (basicRows.contains(basicRow)) {
/*     */ 
/*     */           
/* 423 */           coefficients[i] = 0.0D - (this.restrictToNonNegative ? 0.0D : mostNegative);
/*     */         } else {
/* 425 */           basicRows.add(basicRow);
/* 426 */           coefficients[i] = ((basicRow == null) ? 0.0D : getEntry(basicRow.intValue(), getRhsOffset())) - (this.restrictToNonNegative ? 0.0D : mostNegative);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 431 */     return new PointValuePair(coefficients, this.f.getValue(coefficients));
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
/*     */   protected void divideRow(int dividendRow, double divisor) {
/* 444 */     for (int j = 0; j < getWidth(); j++) {
/* 445 */       this.tableau.setEntry(dividendRow, j, this.tableau.getEntry(dividendRow, j) / divisor);
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
/*     */   
/*     */   protected void subtractRow(int minuendRow, int subtrahendRow, double multiple) {
/* 461 */     for (int i = 0; i < getWidth(); i++) {
/* 462 */       double result = this.tableau.getEntry(minuendRow, i) - this.tableau.getEntry(subtrahendRow, i) * multiple;
/*     */       
/* 464 */       if (FastMath.abs(result) < 1.0E-12D) {
/* 465 */         result = 0.0D;
/*     */       }
/* 467 */       this.tableau.setEntry(minuendRow, i, result);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getWidth() {
/* 476 */     return this.tableau.getColumnDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getHeight() {
/* 484 */     return this.tableau.getRowDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final double getEntry(int row, int column) {
/* 494 */     return this.tableau.getEntry(row, column);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setEntry(int row, int column, double value) {
/* 505 */     this.tableau.setEntry(row, column, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getSlackVariableOffset() {
/* 513 */     return getNumObjectiveFunctions() + this.numDecisionVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getArtificialVariableOffset() {
/* 521 */     return getNumObjectiveFunctions() + this.numDecisionVariables + this.numSlackVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getRhsOffset() {
/* 529 */     return getWidth() - 1;
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
/*     */   protected final int getNumDecisionVariables() {
/* 542 */     return this.numDecisionVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getOriginalNumDecisionVariables() {
/* 551 */     return this.f.getCoefficients().getDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getNumSlackVariables() {
/* 559 */     return this.numSlackVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getNumArtificialVariables() {
/* 567 */     return this.numArtificialVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final double[][] getData() {
/* 575 */     return this.tableau.getData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 582 */     if (this == other) {
/* 583 */       return true;
/*     */     }
/*     */     
/* 586 */     if (other instanceof SimplexTableau) {
/* 587 */       SimplexTableau rhs = (SimplexTableau)other;
/* 588 */       return (this.restrictToNonNegative == rhs.restrictToNonNegative && this.numDecisionVariables == rhs.numDecisionVariables && this.numSlackVariables == rhs.numSlackVariables && this.numArtificialVariables == rhs.numArtificialVariables && this.epsilon == rhs.epsilon && this.maxUlps == rhs.maxUlps && this.f.equals(rhs.f) && this.constraints.equals(rhs.constraints) && this.tableau.equals(rhs.tableau));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 598 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 604 */     return Boolean.valueOf(this.restrictToNonNegative).hashCode() ^ this.numDecisionVariables ^ this.numSlackVariables ^ this.numArtificialVariables ^ Double.valueOf(this.epsilon).hashCode() ^ this.maxUlps ^ this.f.hashCode() ^ this.constraints.hashCode() ^ this.tableau.hashCode();
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
/*     */   private void writeObject(ObjectOutputStream oos) throws IOException {
/* 622 */     oos.defaultWriteObject();
/* 623 */     MatrixUtils.serializeRealMatrix(this.tableau, oos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
/* 634 */     ois.defaultReadObject();
/* 635 */     MatrixUtils.deserializeRealMatrix(this, "tableau", ois);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/linear/SimplexTableau.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */