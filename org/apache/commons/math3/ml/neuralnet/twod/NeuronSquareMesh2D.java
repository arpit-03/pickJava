/*     */ package org.apache.commons.math3.ml.neuralnet.twod;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.ml.neuralnet.FeatureInitializer;
/*     */ import org.apache.commons.math3.ml.neuralnet.Network;
/*     */ import org.apache.commons.math3.ml.neuralnet.Neuron;
/*     */ import org.apache.commons.math3.ml.neuralnet.SquareNeighbourhood;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NeuronSquareMesh2D
/*     */   implements Iterable<Neuron>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Network network;
/*     */   private final int numberOfRows;
/*     */   private final int numberOfColumns;
/*     */   private final boolean wrapRows;
/*     */   private final boolean wrapColumns;
/*     */   private final SquareNeighbourhood neighbourhood;
/*     */   private final long[][] identifiers;
/*     */   
/*     */   public enum HorizontalDirection
/*     */   {
/*  74 */     RIGHT,
/*     */     
/*  76 */     CENTER,
/*     */     
/*  78 */     LEFT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum VerticalDirection
/*     */   {
/*  86 */     UP,
/*     */     
/*  88 */     CENTER,
/*     */     
/*  90 */     DOWN;
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
/*     */   NeuronSquareMesh2D(boolean wrapRowDim, boolean wrapColDim, SquareNeighbourhood neighbourhoodType, double[][][] featuresList) {
/* 110 */     this.numberOfRows = featuresList.length;
/* 111 */     this.numberOfColumns = (featuresList[0]).length;
/*     */     
/* 113 */     if (this.numberOfRows < 2) {
/* 114 */       throw new NumberIsTooSmallException(Integer.valueOf(this.numberOfRows), Integer.valueOf(2), true);
/*     */     }
/* 116 */     if (this.numberOfColumns < 2) {
/* 117 */       throw new NumberIsTooSmallException(Integer.valueOf(this.numberOfColumns), Integer.valueOf(2), true);
/*     */     }
/*     */     
/* 120 */     this.wrapRows = wrapRowDim;
/* 121 */     this.wrapColumns = wrapColDim;
/* 122 */     this.neighbourhood = neighbourhoodType;
/*     */     
/* 124 */     int fLen = (featuresList[0][0]).length;
/* 125 */     this.network = new Network(0L, fLen);
/* 126 */     this.identifiers = new long[this.numberOfRows][this.numberOfColumns];
/*     */ 
/*     */     
/* 129 */     for (int i = 0; i < this.numberOfRows; i++) {
/* 130 */       for (int j = 0; j < this.numberOfColumns; j++) {
/* 131 */         this.identifiers[i][j] = this.network.createNeuron(featuresList[i][j]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 136 */     createLinks();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NeuronSquareMesh2D(int numRows, boolean wrapRowDim, int numCols, boolean wrapColDim, SquareNeighbourhood neighbourhoodType, FeatureInitializer[] featureInit) {
/* 170 */     if (numRows < 2) {
/* 171 */       throw new NumberIsTooSmallException(Integer.valueOf(numRows), Integer.valueOf(2), true);
/*     */     }
/* 173 */     if (numCols < 2) {
/* 174 */       throw new NumberIsTooSmallException(Integer.valueOf(numCols), Integer.valueOf(2), true);
/*     */     }
/*     */     
/* 177 */     this.numberOfRows = numRows;
/* 178 */     this.wrapRows = wrapRowDim;
/* 179 */     this.numberOfColumns = numCols;
/* 180 */     this.wrapColumns = wrapColDim;
/* 181 */     this.neighbourhood = neighbourhoodType;
/* 182 */     this.identifiers = new long[this.numberOfRows][this.numberOfColumns];
/*     */     
/* 184 */     int fLen = featureInit.length;
/* 185 */     this.network = new Network(0L, fLen);
/*     */ 
/*     */     
/* 188 */     for (int i = 0; i < numRows; i++) {
/* 189 */       for (int j = 0; j < numCols; j++) {
/* 190 */         double[] features = new double[fLen];
/* 191 */         for (int fIndex = 0; fIndex < fLen; fIndex++) {
/* 192 */           features[fIndex] = featureInit[fIndex].value();
/*     */         }
/* 194 */         this.identifiers[i][j] = this.network.createNeuron(features);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 199 */     createLinks();
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
/*     */   private NeuronSquareMesh2D(boolean wrapRowDim, boolean wrapColDim, SquareNeighbourhood neighbourhoodType, Network net, long[][] idGrid) {
/* 219 */     this.numberOfRows = idGrid.length;
/* 220 */     this.numberOfColumns = (idGrid[0]).length;
/* 221 */     this.wrapRows = wrapRowDim;
/* 222 */     this.wrapColumns = wrapColDim;
/* 223 */     this.neighbourhood = neighbourhoodType;
/* 224 */     this.network = net;
/* 225 */     this.identifiers = idGrid;
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
/*     */   public synchronized NeuronSquareMesh2D copy() {
/* 237 */     long[][] idGrid = new long[this.numberOfRows][this.numberOfColumns];
/* 238 */     for (int r = 0; r < this.numberOfRows; r++) {
/* 239 */       for (int c = 0; c < this.numberOfColumns; c++) {
/* 240 */         idGrid[r][c] = this.identifiers[r][c];
/*     */       }
/*     */     } 
/*     */     
/* 244 */     return new NeuronSquareMesh2D(this.wrapRows, this.wrapColumns, this.neighbourhood, this.network.copy(), idGrid);
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
/*     */   public Iterator<Neuron> iterator() {
/* 256 */     return this.network.iterator();
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
/*     */   public Network getNetwork() {
/* 269 */     return this.network;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfRows() {
/* 278 */     return this.numberOfRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfColumns() {
/* 287 */     return this.numberOfColumns;
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
/*     */   public Neuron getNeuron(int i, int j) {
/* 305 */     if (i < 0 || i >= this.numberOfRows)
/*     */     {
/* 307 */       throw new OutOfRangeException(Integer.valueOf(i), Integer.valueOf(0), Integer.valueOf(this.numberOfRows - 1));
/*     */     }
/* 309 */     if (j < 0 || j >= this.numberOfColumns)
/*     */     {
/* 311 */       throw new OutOfRangeException(Integer.valueOf(j), Integer.valueOf(0), Integer.valueOf(this.numberOfColumns - 1));
/*     */     }
/*     */     
/* 314 */     return this.network.getNeuron(this.identifiers[i][j]);
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
/*     */   public Neuron getNeuron(int row, int col, HorizontalDirection alongRowDir, VerticalDirection alongColDir) {
/* 337 */     int[] location = getLocation(row, col, alongRowDir, alongColDir);
/*     */     
/* 339 */     return (location == null) ? null : getNeuron(location[0], location[1]);
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
/*     */   private int[] getLocation(int row, int col, HorizontalDirection alongRowDir, VerticalDirection alongColDir) {
/*     */     int colOffset, rowOffset;
/* 364 */     switch (alongRowDir) {
/*     */       case MOORE:
/* 366 */         colOffset = -1;
/*     */         break;
/*     */       case VON_NEUMANN:
/* 369 */         colOffset = 1;
/*     */         break;
/*     */       case null:
/* 372 */         colOffset = 0;
/*     */         break;
/*     */       
/*     */       default:
/* 376 */         throw new MathInternalError();
/*     */     } 
/* 378 */     int colIndex = col + colOffset;
/* 379 */     if (this.wrapColumns) {
/* 380 */       if (colIndex < 0) {
/* 381 */         colIndex += this.numberOfColumns;
/*     */       } else {
/* 383 */         colIndex %= this.numberOfColumns;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 388 */     switch (alongColDir) {
/*     */       case MOORE:
/* 390 */         rowOffset = -1;
/*     */         break;
/*     */       case VON_NEUMANN:
/* 393 */         rowOffset = 1;
/*     */         break;
/*     */       case null:
/* 396 */         rowOffset = 0;
/*     */         break;
/*     */       
/*     */       default:
/* 400 */         throw new MathInternalError();
/*     */     } 
/* 402 */     int rowIndex = row + rowOffset;
/* 403 */     if (this.wrapRows) {
/* 404 */       if (rowIndex < 0) {
/* 405 */         rowIndex += this.numberOfRows;
/*     */       } else {
/* 407 */         rowIndex %= this.numberOfRows;
/*     */       } 
/*     */     }
/*     */     
/* 411 */     if (rowIndex < 0 || rowIndex >= this.numberOfRows || colIndex < 0 || colIndex >= this.numberOfColumns)
/*     */     {
/*     */ 
/*     */       
/* 415 */       return null;
/*     */     }
/* 417 */     return new int[] { rowIndex, colIndex };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createLinks() {
/* 426 */     List<Long> linkEnd = new ArrayList<Long>();
/* 427 */     int iLast = this.numberOfRows - 1;
/* 428 */     int jLast = this.numberOfColumns - 1;
/* 429 */     for (int i = 0; i < this.numberOfRows; i++) {
/* 430 */       for (int j = 0; j < this.numberOfColumns; j++) {
/* 431 */         linkEnd.clear();
/*     */         
/* 433 */         switch (this.neighbourhood) {
/*     */ 
/*     */           
/*     */           case MOORE:
/* 437 */             if (i > 0) {
/* 438 */               if (j > 0) {
/* 439 */                 linkEnd.add(Long.valueOf(this.identifiers[i - 1][j - 1]));
/*     */               }
/* 441 */               if (j < jLast) {
/* 442 */                 linkEnd.add(Long.valueOf(this.identifiers[i - 1][j + 1]));
/*     */               }
/*     */             } 
/* 445 */             if (i < iLast) {
/* 446 */               if (j > 0) {
/* 447 */                 linkEnd.add(Long.valueOf(this.identifiers[i + 1][j - 1]));
/*     */               }
/* 449 */               if (j < jLast) {
/* 450 */                 linkEnd.add(Long.valueOf(this.identifiers[i + 1][j + 1]));
/*     */               }
/*     */             } 
/* 453 */             if (this.wrapRows) {
/* 454 */               if (i == 0) {
/* 455 */                 if (j > 0) {
/* 456 */                   linkEnd.add(Long.valueOf(this.identifiers[iLast][j - 1]));
/*     */                 }
/* 458 */                 if (j < jLast) {
/* 459 */                   linkEnd.add(Long.valueOf(this.identifiers[iLast][j + 1]));
/*     */                 }
/* 461 */               } else if (i == iLast) {
/* 462 */                 if (j > 0) {
/* 463 */                   linkEnd.add(Long.valueOf(this.identifiers[0][j - 1]));
/*     */                 }
/* 465 */                 if (j < jLast) {
/* 466 */                   linkEnd.add(Long.valueOf(this.identifiers[0][j + 1]));
/*     */                 }
/*     */               } 
/*     */             }
/* 470 */             if (this.wrapColumns) {
/* 471 */               if (j == 0) {
/* 472 */                 if (i > 0) {
/* 473 */                   linkEnd.add(Long.valueOf(this.identifiers[i - 1][jLast]));
/*     */                 }
/* 475 */                 if (i < iLast) {
/* 476 */                   linkEnd.add(Long.valueOf(this.identifiers[i + 1][jLast]));
/*     */                 }
/* 478 */               } else if (j == jLast) {
/* 479 */                 if (i > 0) {
/* 480 */                   linkEnd.add(Long.valueOf(this.identifiers[i - 1][0]));
/*     */                 }
/* 482 */                 if (i < iLast) {
/* 483 */                   linkEnd.add(Long.valueOf(this.identifiers[i + 1][0]));
/*     */                 }
/*     */               } 
/*     */             }
/* 487 */             if (this.wrapRows && this.wrapColumns)
/*     */             {
/* 489 */               if (i == 0 && j == 0) {
/*     */                 
/* 491 */                 linkEnd.add(Long.valueOf(this.identifiers[iLast][jLast]));
/* 492 */               } else if (i == 0 && j == jLast) {
/*     */                 
/* 494 */                 linkEnd.add(Long.valueOf(this.identifiers[iLast][0]));
/* 495 */               } else if (i == iLast && j == 0) {
/*     */                 
/* 497 */                 linkEnd.add(Long.valueOf(this.identifiers[0][jLast]));
/* 498 */               } else if (i == iLast && j == jLast) {
/*     */                 
/* 500 */                 linkEnd.add(Long.valueOf(this.identifiers[0][0]));
/*     */               } 
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case VON_NEUMANN:
/* 511 */             if (i > 0) {
/* 512 */               linkEnd.add(Long.valueOf(this.identifiers[i - 1][j]));
/*     */             }
/* 514 */             if (i < iLast) {
/* 515 */               linkEnd.add(Long.valueOf(this.identifiers[i + 1][j]));
/*     */             }
/* 517 */             if (this.wrapRows) {
/* 518 */               if (i == 0) {
/* 519 */                 linkEnd.add(Long.valueOf(this.identifiers[iLast][j]));
/* 520 */               } else if (i == iLast) {
/* 521 */                 linkEnd.add(Long.valueOf(this.identifiers[0][j]));
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/* 526 */             if (j > 0) {
/* 527 */               linkEnd.add(Long.valueOf(this.identifiers[i][j - 1]));
/*     */             }
/* 529 */             if (j < jLast) {
/* 530 */               linkEnd.add(Long.valueOf(this.identifiers[i][j + 1]));
/*     */             }
/* 532 */             if (this.wrapColumns) {
/* 533 */               if (j == 0) {
/* 534 */                 linkEnd.add(Long.valueOf(this.identifiers[i][jLast])); break;
/* 535 */               }  if (j == jLast) {
/* 536 */                 linkEnd.add(Long.valueOf(this.identifiers[i][0]));
/*     */               }
/*     */             } 
/*     */             break;
/*     */           
/*     */           default:
/* 542 */             throw new MathInternalError();
/*     */         } 
/*     */         
/* 545 */         Neuron aNeuron = this.network.getNeuron(this.identifiers[i][j]);
/* 546 */         for (Iterator<Long> i$ = linkEnd.iterator(); i$.hasNext(); ) { long b = ((Long)i$.next()).longValue();
/* 547 */           Neuron bNeuron = this.network.getNeuron(b);
/*     */ 
/*     */           
/* 550 */           this.network.addLink(aNeuron, bNeuron); }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) {
/* 562 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 571 */     double[][][] featuresList = new double[this.numberOfRows][this.numberOfColumns][];
/* 572 */     for (int i = 0; i < this.numberOfRows; i++) {
/* 573 */       for (int j = 0; j < this.numberOfColumns; j++) {
/* 574 */         featuresList[i][j] = getNeuron(i, j).getFeatures();
/*     */       }
/*     */     } 
/*     */     
/* 578 */     return new SerializationProxy(this.wrapRows, this.wrapColumns, this.neighbourhood, featuresList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SerializationProxy
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 20130226L;
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean wrapRows;
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean wrapColumns;
/*     */ 
/*     */ 
/*     */     
/*     */     private final SquareNeighbourhood neighbourhood;
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[][][] featuresList;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SerializationProxy(boolean wrapRows, boolean wrapColumns, SquareNeighbourhood neighbourhood, double[][][] featuresList) {
/* 610 */       this.wrapRows = wrapRows;
/* 611 */       this.wrapColumns = wrapColumns;
/* 612 */       this.neighbourhood = neighbourhood;
/* 613 */       this.featuresList = featuresList;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 622 */       return new NeuronSquareMesh2D(this.wrapRows, this.wrapColumns, this.neighbourhood, this.featuresList);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/twod/NeuronSquareMesh2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */