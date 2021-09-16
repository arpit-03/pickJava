/*     */ package org.apache.commons.math3.optim.linear;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.optim.PointValuePair;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
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
/*     */ class SimplexTableau
/*     */   implements Serializable
/*     */ {
/*     */   private static final String NEGATIVE_VAR_COLUMN_LABEL = "x-";
/*     */   private static final long serialVersionUID = -1369660067587938365L;
/*     */   private final LinearObjectiveFunction f;
/*     */   private final List<LinearConstraint> constraints;
/*     */   private final boolean restrictToNonNegative;
/*  80 */   private final List<String> columnLabels = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Array2DRowRealMatrix tableau;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int numDecisionVariables;
/*     */ 
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
/*     */   private int[] basicVariables;
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] basicRows;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SimplexTableau(LinearObjectiveFunction f, Collection<LinearConstraint> constraints, GoalType goalType, boolean restrictToNonNegative, double epsilon) {
/* 121 */     this(f, constraints, goalType, restrictToNonNegative, epsilon, 10);
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
/*     */   SimplexTableau(LinearObjectiveFunction f, Collection<LinearConstraint> constraints, GoalType goalType, boolean restrictToNonNegative, double epsilon, int maxUlps) {
/* 139 */     this.f = f;
/* 140 */     this.constraints = normalizeConstraints(constraints);
/* 141 */     this.restrictToNonNegative = restrictToNonNegative;
/* 142 */     this.epsilon = epsilon;
/* 143 */     this.maxUlps = maxUlps;
/* 144 */     this.numDecisionVariables = f.getCoefficients().getDimension() + (restrictToNonNegative ? 0 : 1);
/* 145 */     this.numSlackVariables = getConstraintTypeCounts(Relationship.LEQ) + getConstraintTypeCounts(Relationship.GEQ);
/*     */     
/* 147 */     this.numArtificialVariables = getConstraintTypeCounts(Relationship.EQ) + getConstraintTypeCounts(Relationship.GEQ);
/*     */     
/* 149 */     this.tableau = createTableau((goalType == GoalType.MAXIMIZE));
/*     */ 
/*     */     
/* 152 */     initializeBasicVariables(getSlackVariableOffset());
/* 153 */     initializeColumnLabels();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeColumnLabels() {
/* 160 */     if (getNumObjectiveFunctions() == 2) {
/* 161 */       this.columnLabels.add("W");
/*     */     }
/* 163 */     this.columnLabels.add("Z"); int i;
/* 164 */     for (i = 0; i < getOriginalNumDecisionVariables(); i++) {
/* 165 */       this.columnLabels.add("x" + i);
/*     */     }
/* 167 */     if (!this.restrictToNonNegative) {
/* 168 */       this.columnLabels.add("x-");
/*     */     }
/* 170 */     for (i = 0; i < getNumSlackVariables(); i++) {
/* 171 */       this.columnLabels.add("s" + i);
/*     */     }
/* 173 */     for (i = 0; i < getNumArtificialVariables(); i++) {
/* 174 */       this.columnLabels.add("a" + i);
/*     */     }
/* 176 */     this.columnLabels.add("RHS");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Array2DRowRealMatrix createTableau(boolean maximize) {
/* 187 */     int width = this.numDecisionVariables + this.numSlackVariables + this.numArtificialVariables + getNumObjectiveFunctions() + 1;
/*     */     
/* 189 */     int height = this.constraints.size() + getNumObjectiveFunctions();
/* 190 */     Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(height, width);
/*     */ 
/*     */     
/* 193 */     if (getNumObjectiveFunctions() == 2) {
/* 194 */       matrix.setEntry(0, 0, -1.0D);
/*     */     }
/*     */     
/* 197 */     int zIndex = (getNumObjectiveFunctions() == 1) ? 0 : 1;
/* 198 */     matrix.setEntry(zIndex, zIndex, maximize ? 1.0D : -1.0D);
/* 199 */     RealVector objectiveCoefficients = maximize ? this.f.getCoefficients().mapMultiply(-1.0D) : this.f.getCoefficients();
/* 200 */     copyArray(objectiveCoefficients.toArray(), matrix.getDataRef()[zIndex]);
/* 201 */     matrix.setEntry(zIndex, width - 1, maximize ? this.f.getConstantTerm() : (-1.0D * this.f.getConstantTerm()));
/*     */     
/* 203 */     if (!this.restrictToNonNegative) {
/* 204 */       matrix.setEntry(zIndex, getSlackVariableOffset() - 1, getInvertedCoefficientSum(objectiveCoefficients));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 209 */     int slackVar = 0;
/* 210 */     int artificialVar = 0;
/* 211 */     for (int i = 0; i < this.constraints.size(); i++) {
/* 212 */       LinearConstraint constraint = this.constraints.get(i);
/* 213 */       int row = getNumObjectiveFunctions() + i;
/*     */ 
/*     */       
/* 216 */       copyArray(constraint.getCoefficients().toArray(), matrix.getDataRef()[row]);
/*     */ 
/*     */       
/* 219 */       if (!this.restrictToNonNegative) {
/* 220 */         matrix.setEntry(row, getSlackVariableOffset() - 1, getInvertedCoefficientSum(constraint.getCoefficients()));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 225 */       matrix.setEntry(row, width - 1, constraint.getValue());
/*     */ 
/*     */       
/* 228 */       if (constraint.getRelationship() == Relationship.LEQ) {
/* 229 */         matrix.setEntry(row, getSlackVariableOffset() + slackVar++, 1.0D);
/* 230 */       } else if (constraint.getRelationship() == Relationship.GEQ) {
/* 231 */         matrix.setEntry(row, getSlackVariableOffset() + slackVar++, -1.0D);
/*     */       } 
/*     */ 
/*     */       
/* 235 */       if (constraint.getRelationship() == Relationship.EQ || constraint.getRelationship() == Relationship.GEQ) {
/*     */         
/* 237 */         matrix.setEntry(0, getArtificialVariableOffset() + artificialVar, 1.0D);
/* 238 */         matrix.setEntry(row, getArtificialVariableOffset() + artificialVar++, 1.0D);
/* 239 */         matrix.setRowVector(0, matrix.getRowVector(0).subtract(matrix.getRowVector(row)));
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     return matrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<LinearConstraint> normalizeConstraints(Collection<LinearConstraint> originalConstraints) {
/* 252 */     List<LinearConstraint> normalized = new ArrayList<LinearConstraint>(originalConstraints.size());
/* 253 */     for (LinearConstraint constraint : originalConstraints) {
/* 254 */       normalized.add(normalize(constraint));
/*     */     }
/* 256 */     return normalized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LinearConstraint normalize(LinearConstraint constraint) {
/* 265 */     if (constraint.getValue() < 0.0D) {
/* 266 */       return new LinearConstraint(constraint.getCoefficients().mapMultiply(-1.0D), constraint.getRelationship().oppositeRelationship(), -1.0D * constraint.getValue());
/*     */     }
/*     */ 
/*     */     
/* 270 */     return new LinearConstraint(constraint.getCoefficients(), constraint.getRelationship(), constraint.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getNumObjectiveFunctions() {
/* 279 */     return (this.numArtificialVariables > 0) ? 2 : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getConstraintTypeCounts(Relationship relationship) {
/* 288 */     int count = 0;
/* 289 */     for (LinearConstraint constraint : this.constraints) {
/* 290 */       if (constraint.getRelationship() == relationship) {
/* 291 */         count++;
/*     */       }
/*     */     } 
/* 294 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static double getInvertedCoefficientSum(RealVector coefficients) {
/* 303 */     double sum = 0.0D;
/* 304 */     for (double coefficient : coefficients.toArray()) {
/* 305 */       sum -= coefficient;
/*     */     }
/* 307 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Integer getBasicRow(int col) {
/* 316 */     int row = this.basicVariables[col];
/* 317 */     return (row == -1) ? null : Integer.valueOf(row);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getBasicVariable(int row) {
/* 326 */     return this.basicRows[row];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeBasicVariables(int startColumn) {
/* 334 */     this.basicVariables = new int[getWidth() - 1];
/* 335 */     this.basicRows = new int[getHeight()];
/*     */     
/* 337 */     Arrays.fill(this.basicVariables, -1);
/*     */     
/* 339 */     for (int i = startColumn; i < getWidth() - 1; i++) {
/* 340 */       Integer row = findBasicRow(i);
/* 341 */       if (row != null) {
/* 342 */         this.basicVariables[i] = row.intValue();
/* 343 */         this.basicRows[row.intValue()] = i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer findBasicRow(int col) {
/* 354 */     Integer row = null;
/* 355 */     for (int i = 0; i < getHeight(); i++) {
/* 356 */       double entry = getEntry(i, col);
/* 357 */       if (Precision.equals(entry, 1.0D, this.maxUlps) && row == null) {
/* 358 */         row = Integer.valueOf(i);
/* 359 */       } else if (!Precision.equals(entry, 0.0D, this.maxUlps)) {
/* 360 */         return null;
/*     */       } 
/*     */     } 
/* 363 */     return row;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropPhase1Objective() {
/* 371 */     if (getNumObjectiveFunctions() == 1) {
/*     */       return;
/*     */     }
/*     */     
/* 375 */     Set<Integer> columnsToDrop = new TreeSet<Integer>();
/* 376 */     columnsToDrop.add(Integer.valueOf(0));
/*     */     
/*     */     int i;
/* 379 */     for (i = getNumObjectiveFunctions(); i < getArtificialVariableOffset(); i++) {
/* 380 */       double entry = getEntry(0, i);
/* 381 */       if (Precision.compareTo(entry, 0.0D, this.epsilon) > 0) {
/* 382 */         columnsToDrop.add(Integer.valueOf(i));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 387 */     for (i = 0; i < getNumArtificialVariables(); i++) {
/* 388 */       int col = i + getArtificialVariableOffset();
/* 389 */       if (getBasicRow(col) == null) {
/* 390 */         columnsToDrop.add(Integer.valueOf(col));
/*     */       }
/*     */     } 
/*     */     
/* 394 */     double[][] matrix = new double[getHeight() - 1][getWidth() - columnsToDrop.size()];
/* 395 */     for (int j = 1; j < getHeight(); j++) {
/* 396 */       int col = 0;
/* 397 */       for (int m = 0; m < getWidth(); m++) {
/* 398 */         if (!columnsToDrop.contains(Integer.valueOf(m))) {
/* 399 */           matrix[j - 1][col++] = getEntry(j, m);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 405 */     Integer[] drop = columnsToDrop.<Integer>toArray(new Integer[columnsToDrop.size()]);
/* 406 */     for (int k = drop.length - 1; k >= 0; k--) {
/* 407 */       this.columnLabels.remove(drop[k].intValue());
/*     */     }
/*     */     
/* 410 */     this.tableau = new Array2DRowRealMatrix(matrix);
/* 411 */     this.numArtificialVariables = 0;
/*     */     
/* 413 */     initializeBasicVariables(getNumObjectiveFunctions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyArray(double[] src, double[] dest) {
/* 421 */     System.arraycopy(src, 0, dest, getNumObjectiveFunctions(), src.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isOptimal() {
/* 429 */     double[] objectiveFunctionRow = getRow(0);
/* 430 */     int end = getRhsOffset();
/* 431 */     for (int i = getNumObjectiveFunctions(); i < end; i++) {
/* 432 */       double entry = objectiveFunctionRow[i];
/* 433 */       if (Precision.compareTo(entry, 0.0D, this.epsilon) < 0) {
/* 434 */         return false;
/*     */       }
/*     */     } 
/* 437 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointValuePair getSolution() {
/* 445 */     int negativeVarColumn = this.columnLabels.indexOf("x-");
/* 446 */     Integer negativeVarBasicRow = (negativeVarColumn > 0) ? getBasicRow(negativeVarColumn) : null;
/* 447 */     double mostNegative = (negativeVarBasicRow == null) ? 0.0D : getEntry(negativeVarBasicRow.intValue(), getRhsOffset());
/*     */     
/* 449 */     Set<Integer> usedBasicRows = new HashSet<Integer>();
/* 450 */     double[] coefficients = new double[getOriginalNumDecisionVariables()];
/* 451 */     for (int i = 0; i < coefficients.length; i++) {
/* 452 */       int colIndex = this.columnLabels.indexOf("x" + i);
/* 453 */       if (colIndex < 0) {
/* 454 */         coefficients[i] = 0.0D;
/*     */       } else {
/*     */         
/* 457 */         Integer basicRow = getBasicRow(colIndex);
/* 458 */         if (basicRow != null && basicRow.intValue() == 0) {
/*     */ 
/*     */ 
/*     */           
/* 462 */           coefficients[i] = 0.0D;
/* 463 */         } else if (usedBasicRows.contains(basicRow)) {
/*     */ 
/*     */           
/* 466 */           coefficients[i] = 0.0D - (this.restrictToNonNegative ? 0.0D : mostNegative);
/*     */         } else {
/* 468 */           usedBasicRows.add(basicRow);
/* 469 */           coefficients[i] = ((basicRow == null) ? 0.0D : getEntry(basicRow.intValue(), getRhsOffset())) - (this.restrictToNonNegative ? 0.0D : mostNegative);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 474 */     return new PointValuePair(coefficients, this.f.value(coefficients));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void performRowOperations(int pivotCol, int pivotRow) {
/* 485 */     double pivotVal = getEntry(pivotRow, pivotCol);
/* 486 */     divideRow(pivotRow, pivotVal);
/*     */ 
/*     */     
/* 489 */     for (int i = 0; i < getHeight(); i++) {
/* 490 */       if (i != pivotRow) {
/* 491 */         double multiplier = getEntry(i, pivotCol);
/* 492 */         if (multiplier != 0.0D) {
/* 493 */           subtractRow(i, pivotRow, multiplier);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 499 */     int previousBasicVariable = getBasicVariable(pivotRow);
/* 500 */     this.basicVariables[previousBasicVariable] = -1;
/* 501 */     this.basicVariables[pivotCol] = pivotRow;
/* 502 */     this.basicRows[pivotRow] = pivotCol;
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
/*     */   protected void divideRow(int dividendRowIndex, double divisor) {
/* 515 */     double[] dividendRow = getRow(dividendRowIndex);
/* 516 */     for (int j = 0; j < getWidth(); j++) {
/* 517 */       dividendRow[j] = dividendRow[j] / divisor;
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
/*     */   protected void subtractRow(int minuendRowIndex, int subtrahendRowIndex, double multiplier) {
/* 532 */     double[] minuendRow = getRow(minuendRowIndex);
/* 533 */     double[] subtrahendRow = getRow(subtrahendRowIndex);
/* 534 */     for (int i = 0; i < getWidth(); i++) {
/* 535 */       minuendRow[i] = minuendRow[i] - subtrahendRow[i] * multiplier;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getWidth() {
/* 544 */     return this.tableau.getColumnDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getHeight() {
/* 552 */     return this.tableau.getRowDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final double getEntry(int row, int column) {
/* 562 */     return this.tableau.getEntry(row, column);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setEntry(int row, int column, double value) {
/* 572 */     this.tableau.setEntry(row, column, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getSlackVariableOffset() {
/* 580 */     return getNumObjectiveFunctions() + this.numDecisionVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getArtificialVariableOffset() {
/* 588 */     return getNumObjectiveFunctions() + this.numDecisionVariables + this.numSlackVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getRhsOffset() {
/* 596 */     return getWidth() - 1;
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
/* 609 */     return this.numDecisionVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getOriginalNumDecisionVariables() {
/* 618 */     return this.f.getCoefficients().getDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getNumSlackVariables() {
/* 626 */     return this.numSlackVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getNumArtificialVariables() {
/* 634 */     return this.numArtificialVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final double[] getRow(int row) {
/* 643 */     return this.tableau.getDataRef()[row];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final double[][] getData() {
/* 651 */     return this.tableau.getData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 658 */     if (this == other) {
/* 659 */       return true;
/*     */     }
/*     */     
/* 662 */     if (other instanceof SimplexTableau) {
/* 663 */       SimplexTableau rhs = (SimplexTableau)other;
/* 664 */       return (this.restrictToNonNegative == rhs.restrictToNonNegative && this.numDecisionVariables == rhs.numDecisionVariables && this.numSlackVariables == rhs.numSlackVariables && this.numArtificialVariables == rhs.numArtificialVariables && this.epsilon == rhs.epsilon && this.maxUlps == rhs.maxUlps && this.f.equals(rhs.f) && this.constraints.equals(rhs.constraints) && this.tableau.equals(rhs.tableau));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 674 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 680 */     return Boolean.valueOf(this.restrictToNonNegative).hashCode() ^ this.numDecisionVariables ^ this.numSlackVariables ^ this.numArtificialVariables ^ Double.valueOf(this.epsilon).hashCode() ^ this.maxUlps ^ this.f.hashCode() ^ this.constraints.hashCode() ^ this.tableau.hashCode();
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
/* 698 */     oos.defaultWriteObject();
/* 699 */     MatrixUtils.serializeRealMatrix((RealMatrix)this.tableau, oos);
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
/* 710 */     ois.defaultReadObject();
/* 711 */     MatrixUtils.deserializeRealMatrix(this, "tableau", ois);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/linear/SimplexTableau.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */