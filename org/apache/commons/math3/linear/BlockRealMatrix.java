/*      */ package org.apache.commons.math3.linear;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.NoDataException;
/*      */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BlockRealMatrix
/*      */   extends AbstractRealMatrix
/*      */   implements Serializable
/*      */ {
/*      */   public static final int BLOCK_SIZE = 52;
/*      */   private static final long serialVersionUID = 4991895511313664478L;
/*      */   private final double[][] blocks;
/*      */   private final int rows;
/*      */   private final int columns;
/*      */   private final int blockRows;
/*      */   private final int blockColumns;
/*      */   
/*      */   public BlockRealMatrix(int rows, int columns) throws NotStrictlyPositiveException {
/*   97 */     super(rows, columns);
/*   98 */     this.rows = rows;
/*   99 */     this.columns = columns;
/*      */ 
/*      */     
/*  102 */     this.blockRows = (rows + 52 - 1) / 52;
/*  103 */     this.blockColumns = (columns + 52 - 1) / 52;
/*      */ 
/*      */     
/*  106 */     this.blocks = createBlocksLayout(rows, columns);
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
/*      */   public BlockRealMatrix(double[][] rawData) throws DimensionMismatchException, NotStrictlyPositiveException {
/*  126 */     this(rawData.length, (rawData[0]).length, toBlocksLayout(rawData), false);
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
/*      */   public BlockRealMatrix(int rows, int columns, double[][] blockData, boolean copyArray) throws DimensionMismatchException, NotStrictlyPositiveException {
/*  148 */     super(rows, columns);
/*  149 */     this.rows = rows;
/*  150 */     this.columns = columns;
/*      */ 
/*      */     
/*  153 */     this.blockRows = (rows + 52 - 1) / 52;
/*  154 */     this.blockColumns = (columns + 52 - 1) / 52;
/*      */     
/*  156 */     if (copyArray) {
/*      */       
/*  158 */       this.blocks = new double[this.blockRows * this.blockColumns][];
/*      */     } else {
/*      */       
/*  161 */       this.blocks = blockData;
/*      */     } 
/*      */     
/*  164 */     int index = 0;
/*  165 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/*  166 */       int iHeight = blockHeight(iBlock);
/*  167 */       for (int jBlock = 0; jBlock < this.blockColumns; jBlock++, index++) {
/*  168 */         if ((blockData[index]).length != iHeight * blockWidth(jBlock)) {
/*  169 */           throw new DimensionMismatchException((blockData[index]).length, iHeight * blockWidth(jBlock));
/*      */         }
/*      */         
/*  172 */         if (copyArray) {
/*  173 */           this.blocks[index] = (double[])blockData[index].clone();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] toBlocksLayout(double[][] rawData) throws DimensionMismatchException {
/*  202 */     int rows = rawData.length;
/*  203 */     int columns = (rawData[0]).length;
/*  204 */     int blockRows = (rows + 52 - 1) / 52;
/*  205 */     int blockColumns = (columns + 52 - 1) / 52;
/*      */ 
/*      */     
/*  208 */     for (int i = 0; i < rawData.length; i++) {
/*  209 */       int length = (rawData[i]).length;
/*  210 */       if (length != columns) {
/*  211 */         throw new DimensionMismatchException(columns, length);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  216 */     double[][] blocks = new double[blockRows * blockColumns][];
/*  217 */     int blockIndex = 0;
/*  218 */     for (int iBlock = 0; iBlock < blockRows; iBlock++) {
/*  219 */       int pStart = iBlock * 52;
/*  220 */       int pEnd = FastMath.min(pStart + 52, rows);
/*  221 */       int iHeight = pEnd - pStart;
/*  222 */       for (int jBlock = 0; jBlock < blockColumns; jBlock++) {
/*  223 */         int qStart = jBlock * 52;
/*  224 */         int qEnd = FastMath.min(qStart + 52, columns);
/*  225 */         int jWidth = qEnd - qStart;
/*      */ 
/*      */         
/*  228 */         double[] block = new double[iHeight * jWidth];
/*  229 */         blocks[blockIndex] = block;
/*      */ 
/*      */         
/*  232 */         int index = 0;
/*  233 */         for (int p = pStart; p < pEnd; p++) {
/*  234 */           System.arraycopy(rawData[p], qStart, block, index, jWidth);
/*  235 */           index += jWidth;
/*      */         } 
/*  237 */         blockIndex++;
/*      */       } 
/*      */     } 
/*      */     
/*  241 */     return blocks;
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
/*      */   public static double[][] createBlocksLayout(int rows, int columns) {
/*  257 */     int blockRows = (rows + 52 - 1) / 52;
/*  258 */     int blockColumns = (columns + 52 - 1) / 52;
/*      */     
/*  260 */     double[][] blocks = new double[blockRows * blockColumns][];
/*  261 */     int blockIndex = 0;
/*  262 */     for (int iBlock = 0; iBlock < blockRows; iBlock++) {
/*  263 */       int pStart = iBlock * 52;
/*  264 */       int pEnd = FastMath.min(pStart + 52, rows);
/*  265 */       int iHeight = pEnd - pStart;
/*  266 */       for (int jBlock = 0; jBlock < blockColumns; jBlock++) {
/*  267 */         int qStart = jBlock * 52;
/*  268 */         int qEnd = FastMath.min(qStart + 52, columns);
/*  269 */         int jWidth = qEnd - qStart;
/*  270 */         blocks[blockIndex] = new double[iHeight * jWidth];
/*  271 */         blockIndex++;
/*      */       } 
/*      */     } 
/*      */     
/*  275 */     return blocks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRealMatrix createMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
/*  283 */     return new BlockRealMatrix(rowDimension, columnDimension);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRealMatrix copy() {
/*  290 */     BlockRealMatrix copied = new BlockRealMatrix(this.rows, this.columns);
/*      */ 
/*      */     
/*  293 */     for (int i = 0; i < this.blocks.length; i++) {
/*  294 */       System.arraycopy(this.blocks[i], 0, copied.blocks[i], 0, (this.blocks[i]).length);
/*      */     }
/*      */     
/*  297 */     return copied;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRealMatrix add(RealMatrix m) throws MatrixDimensionMismatchException {
/*      */     try {
/*  305 */       return add((BlockRealMatrix)m);
/*  306 */     } catch (ClassCastException cce) {
/*      */       
/*  308 */       MatrixUtils.checkAdditionCompatible(this, m);
/*      */       
/*  310 */       BlockRealMatrix out = new BlockRealMatrix(this.rows, this.columns);
/*      */ 
/*      */       
/*  313 */       int blockIndex = 0;
/*  314 */       for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
/*  315 */         for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
/*      */ 
/*      */           
/*  318 */           double[] outBlock = out.blocks[blockIndex];
/*  319 */           double[] tBlock = this.blocks[blockIndex];
/*  320 */           int pStart = iBlock * 52;
/*  321 */           int pEnd = FastMath.min(pStart + 52, this.rows);
/*  322 */           int qStart = jBlock * 52;
/*  323 */           int qEnd = FastMath.min(qStart + 52, this.columns);
/*  324 */           int k = 0;
/*  325 */           for (int p = pStart; p < pEnd; p++) {
/*  326 */             for (int q = qStart; q < qEnd; q++) {
/*  327 */               outBlock[k] = tBlock[k] + m.getEntry(p, q);
/*  328 */               k++;
/*      */             } 
/*      */           } 
/*      */           
/*  332 */           blockIndex++;
/*      */         } 
/*      */       } 
/*      */       
/*  336 */       return out;
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
/*      */   public BlockRealMatrix add(BlockRealMatrix m) throws MatrixDimensionMismatchException {
/*  351 */     MatrixUtils.checkAdditionCompatible(this, m);
/*      */     
/*  353 */     BlockRealMatrix out = new BlockRealMatrix(this.rows, this.columns);
/*      */ 
/*      */     
/*  356 */     for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
/*  357 */       double[] outBlock = out.blocks[blockIndex];
/*  358 */       double[] tBlock = this.blocks[blockIndex];
/*  359 */       double[] mBlock = m.blocks[blockIndex];
/*  360 */       for (int k = 0; k < outBlock.length; k++) {
/*  361 */         outBlock[k] = tBlock[k] + mBlock[k];
/*      */       }
/*      */     } 
/*      */     
/*  365 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRealMatrix subtract(RealMatrix m) throws MatrixDimensionMismatchException {
/*      */     try {
/*  373 */       return subtract((BlockRealMatrix)m);
/*  374 */     } catch (ClassCastException cce) {
/*      */       
/*  376 */       MatrixUtils.checkSubtractionCompatible(this, m);
/*      */       
/*  378 */       BlockRealMatrix out = new BlockRealMatrix(this.rows, this.columns);
/*      */ 
/*      */       
/*  381 */       int blockIndex = 0;
/*  382 */       for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
/*  383 */         for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
/*      */ 
/*      */           
/*  386 */           double[] outBlock = out.blocks[blockIndex];
/*  387 */           double[] tBlock = this.blocks[blockIndex];
/*  388 */           int pStart = iBlock * 52;
/*  389 */           int pEnd = FastMath.min(pStart + 52, this.rows);
/*  390 */           int qStart = jBlock * 52;
/*  391 */           int qEnd = FastMath.min(qStart + 52, this.columns);
/*  392 */           int k = 0;
/*  393 */           for (int p = pStart; p < pEnd; p++) {
/*  394 */             for (int q = qStart; q < qEnd; q++) {
/*  395 */               outBlock[k] = tBlock[k] - m.getEntry(p, q);
/*  396 */               k++;
/*      */             } 
/*      */           } 
/*      */           
/*  400 */           blockIndex++;
/*      */         } 
/*      */       } 
/*      */       
/*  404 */       return out;
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
/*      */   public BlockRealMatrix subtract(BlockRealMatrix m) throws MatrixDimensionMismatchException {
/*  419 */     MatrixUtils.checkSubtractionCompatible(this, m);
/*      */     
/*  421 */     BlockRealMatrix out = new BlockRealMatrix(this.rows, this.columns);
/*      */ 
/*      */     
/*  424 */     for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
/*  425 */       double[] outBlock = out.blocks[blockIndex];
/*  426 */       double[] tBlock = this.blocks[blockIndex];
/*  427 */       double[] mBlock = m.blocks[blockIndex];
/*  428 */       for (int k = 0; k < outBlock.length; k++) {
/*  429 */         outBlock[k] = tBlock[k] - mBlock[k];
/*      */       }
/*      */     } 
/*      */     
/*  433 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRealMatrix scalarAdd(double d) {
/*  440 */     BlockRealMatrix out = new BlockRealMatrix(this.rows, this.columns);
/*      */ 
/*      */     
/*  443 */     for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
/*  444 */       double[] outBlock = out.blocks[blockIndex];
/*  445 */       double[] tBlock = this.blocks[blockIndex];
/*  446 */       for (int k = 0; k < outBlock.length; k++) {
/*  447 */         outBlock[k] = tBlock[k] + d;
/*      */       }
/*      */     } 
/*      */     
/*  451 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public RealMatrix scalarMultiply(double d) {
/*  457 */     BlockRealMatrix out = new BlockRealMatrix(this.rows, this.columns);
/*      */ 
/*      */     
/*  460 */     for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
/*  461 */       double[] outBlock = out.blocks[blockIndex];
/*  462 */       double[] tBlock = this.blocks[blockIndex];
/*  463 */       for (int k = 0; k < outBlock.length; k++) {
/*  464 */         outBlock[k] = tBlock[k] * d;
/*      */       }
/*      */     } 
/*      */     
/*  468 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRealMatrix multiply(RealMatrix m) throws DimensionMismatchException {
/*      */     try {
/*  476 */       return multiply((BlockRealMatrix)m);
/*  477 */     } catch (ClassCastException cce) {
/*      */       
/*  479 */       MatrixUtils.checkMultiplicationCompatible(this, m);
/*      */       
/*  481 */       BlockRealMatrix out = new BlockRealMatrix(this.rows, m.getColumnDimension());
/*      */ 
/*      */       
/*  484 */       int blockIndex = 0;
/*  485 */       for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
/*  486 */         int pStart = iBlock * 52;
/*  487 */         int pEnd = FastMath.min(pStart + 52, this.rows);
/*      */         
/*  489 */         for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
/*  490 */           int qStart = jBlock * 52;
/*  491 */           int qEnd = FastMath.min(qStart + 52, m.getColumnDimension());
/*      */ 
/*      */           
/*  494 */           double[] outBlock = out.blocks[blockIndex];
/*      */ 
/*      */           
/*  497 */           for (int kBlock = 0; kBlock < this.blockColumns; kBlock++) {
/*  498 */             int kWidth = blockWidth(kBlock);
/*  499 */             double[] tBlock = this.blocks[iBlock * this.blockColumns + kBlock];
/*  500 */             int rStart = kBlock * 52;
/*  501 */             int k = 0;
/*  502 */             for (int p = pStart; p < pEnd; p++) {
/*  503 */               int lStart = (p - pStart) * kWidth;
/*  504 */               int lEnd = lStart + kWidth;
/*  505 */               for (int q = qStart; q < qEnd; q++) {
/*  506 */                 double sum = 0.0D;
/*  507 */                 int r = rStart;
/*  508 */                 for (int l = lStart; l < lEnd; l++) {
/*  509 */                   sum += tBlock[l] * m.getEntry(r, q);
/*  510 */                   r++;
/*      */                 } 
/*  512 */                 outBlock[k] = outBlock[k] + sum;
/*  513 */                 k++;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  518 */           blockIndex++;
/*      */         } 
/*      */       } 
/*      */       
/*  522 */       return out;
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
/*      */   public BlockRealMatrix multiply(BlockRealMatrix m) throws DimensionMismatchException {
/*  536 */     MatrixUtils.checkMultiplicationCompatible(this, m);
/*      */     
/*  538 */     BlockRealMatrix out = new BlockRealMatrix(this.rows, m.columns);
/*      */ 
/*      */     
/*  541 */     int blockIndex = 0;
/*  542 */     for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
/*      */       
/*  544 */       int pStart = iBlock * 52;
/*  545 */       int pEnd = FastMath.min(pStart + 52, this.rows);
/*      */       
/*  547 */       for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
/*  548 */         int jWidth = out.blockWidth(jBlock);
/*  549 */         int jWidth2 = jWidth + jWidth;
/*  550 */         int jWidth3 = jWidth2 + jWidth;
/*  551 */         int jWidth4 = jWidth3 + jWidth;
/*      */ 
/*      */         
/*  554 */         double[] outBlock = out.blocks[blockIndex];
/*      */ 
/*      */         
/*  557 */         for (int kBlock = 0; kBlock < this.blockColumns; kBlock++) {
/*  558 */           int kWidth = blockWidth(kBlock);
/*  559 */           double[] tBlock = this.blocks[iBlock * this.blockColumns + kBlock];
/*  560 */           double[] mBlock = m.blocks[kBlock * m.blockColumns + jBlock];
/*  561 */           int k = 0;
/*  562 */           for (int p = pStart; p < pEnd; p++) {
/*  563 */             int lStart = (p - pStart) * kWidth;
/*  564 */             int lEnd = lStart + kWidth;
/*  565 */             for (int nStart = 0; nStart < jWidth; nStart++) {
/*  566 */               double sum = 0.0D;
/*  567 */               int l = lStart;
/*  568 */               int n = nStart;
/*  569 */               while (l < lEnd - 3) {
/*  570 */                 sum += tBlock[l] * mBlock[n] + tBlock[l + 1] * mBlock[n + jWidth] + tBlock[l + 2] * mBlock[n + jWidth2] + tBlock[l + 3] * mBlock[n + jWidth3];
/*      */ 
/*      */ 
/*      */                 
/*  574 */                 l += 4;
/*  575 */                 n += jWidth4;
/*      */               } 
/*  577 */               while (l < lEnd) {
/*  578 */                 sum += tBlock[l++] * mBlock[n];
/*  579 */                 n += jWidth;
/*      */               } 
/*  581 */               outBlock[k] = outBlock[k] + sum;
/*  582 */               k++;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  587 */         blockIndex++;
/*      */       } 
/*      */     } 
/*      */     
/*  591 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double[][] getData() {
/*  597 */     double[][] data = new double[getRowDimension()][getColumnDimension()];
/*  598 */     int lastColumns = this.columns - (this.blockColumns - 1) * 52;
/*      */     
/*  600 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/*  601 */       int pStart = iBlock * 52;
/*  602 */       int pEnd = FastMath.min(pStart + 52, this.rows);
/*  603 */       int regularPos = 0;
/*  604 */       int lastPos = 0;
/*  605 */       for (int p = pStart; p < pEnd; p++) {
/*  606 */         double[] dataP = data[p];
/*  607 */         int blockIndex = iBlock * this.blockColumns;
/*  608 */         int dataPos = 0;
/*  609 */         for (int jBlock = 0; jBlock < this.blockColumns - 1; jBlock++) {
/*  610 */           System.arraycopy(this.blocks[blockIndex++], regularPos, dataP, dataPos, 52);
/*  611 */           dataPos += 52;
/*      */         } 
/*  613 */         System.arraycopy(this.blocks[blockIndex], lastPos, dataP, dataPos, lastColumns);
/*  614 */         regularPos += 52;
/*  615 */         lastPos += lastColumns;
/*      */       } 
/*      */     } 
/*      */     
/*  619 */     return data;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getNorm() {
/*  625 */     double[] colSums = new double[52];
/*  626 */     double maxColSum = 0.0D;
/*  627 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/*  628 */       int jWidth = blockWidth(jBlock);
/*  629 */       Arrays.fill(colSums, 0, jWidth, 0.0D);
/*  630 */       for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/*  631 */         int iHeight = blockHeight(iBlock);
/*  632 */         double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  633 */         for (int i = 0; i < jWidth; i++) {
/*  634 */           double sum = 0.0D;
/*  635 */           for (int k = 0; k < iHeight; k++) {
/*  636 */             sum += FastMath.abs(block[k * jWidth + i]);
/*      */           }
/*  638 */           colSums[i] = colSums[i] + sum;
/*      */         } 
/*      */       } 
/*  641 */       for (int j = 0; j < jWidth; j++) {
/*  642 */         maxColSum = FastMath.max(maxColSum, colSums[j]);
/*      */       }
/*      */     } 
/*  645 */     return maxColSum;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getFrobeniusNorm() {
/*  651 */     double sum2 = 0.0D;
/*  652 */     for (int blockIndex = 0; blockIndex < this.blocks.length; blockIndex++) {
/*  653 */       for (double entry : this.blocks[blockIndex]) {
/*  654 */         sum2 += entry * entry;
/*      */       }
/*      */     } 
/*  657 */     return FastMath.sqrt(sum2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRealMatrix getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/*  667 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/*      */ 
/*      */     
/*  670 */     BlockRealMatrix out = new BlockRealMatrix(endRow - startRow + 1, endColumn - startColumn + 1);
/*      */ 
/*      */ 
/*      */     
/*  674 */     int blockStartRow = startRow / 52;
/*  675 */     int rowsShift = startRow % 52;
/*  676 */     int blockStartColumn = startColumn / 52;
/*  677 */     int columnsShift = startColumn % 52;
/*      */ 
/*      */     
/*  680 */     int pBlock = blockStartRow;
/*  681 */     for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
/*  682 */       int iHeight = out.blockHeight(iBlock);
/*  683 */       int qBlock = blockStartColumn;
/*  684 */       for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
/*  685 */         int jWidth = out.blockWidth(jBlock);
/*      */ 
/*      */         
/*  688 */         int outIndex = iBlock * out.blockColumns + jBlock;
/*  689 */         double[] outBlock = out.blocks[outIndex];
/*  690 */         int index = pBlock * this.blockColumns + qBlock;
/*  691 */         int width = blockWidth(qBlock);
/*      */         
/*  693 */         int heightExcess = iHeight + rowsShift - 52;
/*  694 */         int widthExcess = jWidth + columnsShift - 52;
/*  695 */         if (heightExcess > 0) {
/*      */           
/*  697 */           if (widthExcess > 0)
/*      */           {
/*  699 */             int width2 = blockWidth(qBlock + 1);
/*  700 */             copyBlockPart(this.blocks[index], width, rowsShift, 52, columnsShift, 52, outBlock, jWidth, 0, 0);
/*      */ 
/*      */ 
/*      */             
/*  704 */             copyBlockPart(this.blocks[index + 1], width2, rowsShift, 52, 0, widthExcess, outBlock, jWidth, 0, jWidth - widthExcess);
/*      */ 
/*      */ 
/*      */             
/*  708 */             copyBlockPart(this.blocks[index + this.blockColumns], width, 0, heightExcess, columnsShift, 52, outBlock, jWidth, iHeight - heightExcess, 0);
/*      */ 
/*      */ 
/*      */             
/*  712 */             copyBlockPart(this.blocks[index + this.blockColumns + 1], width2, 0, heightExcess, 0, widthExcess, outBlock, jWidth, iHeight - heightExcess, jWidth - widthExcess);
/*      */           
/*      */           }
/*      */           else
/*      */           {
/*      */             
/*  718 */             copyBlockPart(this.blocks[index], width, rowsShift, 52, columnsShift, jWidth + columnsShift, outBlock, jWidth, 0, 0);
/*      */ 
/*      */ 
/*      */             
/*  722 */             copyBlockPart(this.blocks[index + this.blockColumns], width, 0, heightExcess, columnsShift, jWidth + columnsShift, outBlock, jWidth, iHeight - heightExcess, 0);
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*  729 */         else if (widthExcess > 0) {
/*      */           
/*  731 */           int width2 = blockWidth(qBlock + 1);
/*  732 */           copyBlockPart(this.blocks[index], width, rowsShift, iHeight + rowsShift, columnsShift, 52, outBlock, jWidth, 0, 0);
/*      */ 
/*      */ 
/*      */           
/*  736 */           copyBlockPart(this.blocks[index + 1], width2, rowsShift, iHeight + rowsShift, 0, widthExcess, outBlock, jWidth, 0, jWidth - widthExcess);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  742 */           copyBlockPart(this.blocks[index], width, rowsShift, iHeight + rowsShift, columnsShift, jWidth + columnsShift, outBlock, jWidth, 0, 0);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  748 */         qBlock++;
/*      */       } 
/*  750 */       pBlock++;
/*      */     } 
/*      */     
/*  753 */     return out;
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
/*      */   private void copyBlockPart(double[] srcBlock, int srcWidth, int srcStartRow, int srcEndRow, int srcStartColumn, int srcEndColumn, double[] dstBlock, int dstWidth, int dstStartRow, int dstStartColumn) {
/*  776 */     int length = srcEndColumn - srcStartColumn;
/*  777 */     int srcPos = srcStartRow * srcWidth + srcStartColumn;
/*  778 */     int dstPos = dstStartRow * dstWidth + dstStartColumn;
/*  779 */     for (int srcRow = srcStartRow; srcRow < srcEndRow; srcRow++) {
/*  780 */       System.arraycopy(srcBlock, srcPos, dstBlock, dstPos, length);
/*  781 */       srcPos += srcWidth;
/*  782 */       dstPos += dstWidth;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubMatrix(double[][] subMatrix, int row, int column) throws OutOfRangeException, NoDataException, NullArgumentException, DimensionMismatchException {
/*  793 */     MathUtils.checkNotNull(subMatrix);
/*  794 */     int refLength = (subMatrix[0]).length;
/*  795 */     if (refLength == 0) {
/*  796 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
/*      */     }
/*  798 */     int endRow = row + subMatrix.length - 1;
/*  799 */     int endColumn = column + refLength - 1;
/*  800 */     MatrixUtils.checkSubMatrixIndex(this, row, endRow, column, endColumn);
/*  801 */     for (double[] subRow : subMatrix) {
/*  802 */       if (subRow.length != refLength) {
/*  803 */         throw new DimensionMismatchException(refLength, subRow.length);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  808 */     int blockStartRow = row / 52;
/*  809 */     int blockEndRow = (endRow + 52) / 52;
/*  810 */     int blockStartColumn = column / 52;
/*  811 */     int blockEndColumn = (endColumn + 52) / 52;
/*      */ 
/*      */     
/*  814 */     for (int iBlock = blockStartRow; iBlock < blockEndRow; iBlock++) {
/*  815 */       int iHeight = blockHeight(iBlock);
/*  816 */       int firstRow = iBlock * 52;
/*  817 */       int iStart = FastMath.max(row, firstRow);
/*  818 */       int iEnd = FastMath.min(endRow + 1, firstRow + iHeight);
/*      */       
/*  820 */       for (int jBlock = blockStartColumn; jBlock < blockEndColumn; jBlock++) {
/*  821 */         int jWidth = blockWidth(jBlock);
/*  822 */         int firstColumn = jBlock * 52;
/*  823 */         int jStart = FastMath.max(column, firstColumn);
/*  824 */         int jEnd = FastMath.min(endColumn + 1, firstColumn + jWidth);
/*  825 */         int jLength = jEnd - jStart;
/*      */ 
/*      */         
/*  828 */         double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  829 */         for (int i = iStart; i < iEnd; i++) {
/*  830 */           System.arraycopy(subMatrix[i - row], jStart - column, block, (i - firstRow) * jWidth + jStart - firstColumn, jLength);
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
/*      */   public BlockRealMatrix getRowMatrix(int row) throws OutOfRangeException {
/*  843 */     MatrixUtils.checkRowIndex(this, row);
/*  844 */     BlockRealMatrix out = new BlockRealMatrix(1, this.columns);
/*      */ 
/*      */     
/*  847 */     int iBlock = row / 52;
/*  848 */     int iRow = row - iBlock * 52;
/*  849 */     int outBlockIndex = 0;
/*  850 */     int outIndex = 0;
/*  851 */     double[] outBlock = out.blocks[outBlockIndex];
/*  852 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/*  853 */       int jWidth = blockWidth(jBlock);
/*  854 */       double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  855 */       int available = outBlock.length - outIndex;
/*  856 */       if (jWidth > available) {
/*  857 */         System.arraycopy(block, iRow * jWidth, outBlock, outIndex, available);
/*  858 */         outBlock = out.blocks[++outBlockIndex];
/*  859 */         System.arraycopy(block, iRow * jWidth, outBlock, 0, jWidth - available);
/*  860 */         outIndex = jWidth - available;
/*      */       } else {
/*  862 */         System.arraycopy(block, iRow * jWidth, outBlock, outIndex, jWidth);
/*  863 */         outIndex += jWidth;
/*      */       } 
/*      */     } 
/*      */     
/*  867 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRowMatrix(int row, RealMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
/*      */     try {
/*  875 */       setRowMatrix(row, (BlockRealMatrix)matrix);
/*  876 */     } catch (ClassCastException cce) {
/*  877 */       super.setRowMatrix(row, matrix);
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
/*      */   
/*      */   public void setRowMatrix(int row, BlockRealMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
/*  894 */     MatrixUtils.checkRowIndex(this, row);
/*  895 */     int nCols = getColumnDimension();
/*  896 */     if (matrix.getRowDimension() != 1 || matrix.getColumnDimension() != nCols)
/*      */     {
/*  898 */       throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), 1, nCols);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  904 */     int iBlock = row / 52;
/*  905 */     int iRow = row - iBlock * 52;
/*  906 */     int mBlockIndex = 0;
/*  907 */     int mIndex = 0;
/*  908 */     double[] mBlock = matrix.blocks[mBlockIndex];
/*  909 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/*  910 */       int jWidth = blockWidth(jBlock);
/*  911 */       double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  912 */       int available = mBlock.length - mIndex;
/*  913 */       if (jWidth > available) {
/*  914 */         System.arraycopy(mBlock, mIndex, block, iRow * jWidth, available);
/*  915 */         mBlock = matrix.blocks[++mBlockIndex];
/*  916 */         System.arraycopy(mBlock, 0, block, iRow * jWidth, jWidth - available);
/*  917 */         mIndex = jWidth - available;
/*      */       } else {
/*  919 */         System.arraycopy(mBlock, mIndex, block, iRow * jWidth, jWidth);
/*  920 */         mIndex += jWidth;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRealMatrix getColumnMatrix(int column) throws OutOfRangeException {
/*  929 */     MatrixUtils.checkColumnIndex(this, column);
/*  930 */     BlockRealMatrix out = new BlockRealMatrix(this.rows, 1);
/*      */ 
/*      */     
/*  933 */     int jBlock = column / 52;
/*  934 */     int jColumn = column - jBlock * 52;
/*  935 */     int jWidth = blockWidth(jBlock);
/*  936 */     int outBlockIndex = 0;
/*  937 */     int outIndex = 0;
/*  938 */     double[] outBlock = out.blocks[outBlockIndex];
/*  939 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/*  940 */       int iHeight = blockHeight(iBlock);
/*  941 */       double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  942 */       for (int i = 0; i < iHeight; i++) {
/*  943 */         if (outIndex >= outBlock.length) {
/*  944 */           outBlock = out.blocks[++outBlockIndex];
/*  945 */           outIndex = 0;
/*      */         } 
/*  947 */         outBlock[outIndex++] = block[i * jWidth + jColumn];
/*      */       } 
/*      */     } 
/*      */     
/*  951 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnMatrix(int column, RealMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
/*      */     try {
/*  959 */       setColumnMatrix(column, (BlockRealMatrix)matrix);
/*  960 */     } catch (ClassCastException cce) {
/*  961 */       super.setColumnMatrix(column, matrix);
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
/*      */   
/*      */   void setColumnMatrix(int column, BlockRealMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
/*  978 */     MatrixUtils.checkColumnIndex(this, column);
/*  979 */     int nRows = getRowDimension();
/*  980 */     if (matrix.getRowDimension() != nRows || matrix.getColumnDimension() != 1)
/*      */     {
/*  982 */       throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), nRows, 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  988 */     int jBlock = column / 52;
/*  989 */     int jColumn = column - jBlock * 52;
/*  990 */     int jWidth = blockWidth(jBlock);
/*  991 */     int mBlockIndex = 0;
/*  992 */     int mIndex = 0;
/*  993 */     double[] mBlock = matrix.blocks[mBlockIndex];
/*  994 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/*  995 */       int iHeight = blockHeight(iBlock);
/*  996 */       double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  997 */       for (int i = 0; i < iHeight; i++) {
/*  998 */         if (mIndex >= mBlock.length) {
/*  999 */           mBlock = matrix.blocks[++mBlockIndex];
/* 1000 */           mIndex = 0;
/*      */         } 
/* 1002 */         block[i * jWidth + jColumn] = mBlock[mIndex++];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector getRowVector(int row) throws OutOfRangeException {
/* 1011 */     MatrixUtils.checkRowIndex(this, row);
/* 1012 */     double[] outData = new double[this.columns];
/*      */ 
/*      */     
/* 1015 */     int iBlock = row / 52;
/* 1016 */     int iRow = row - iBlock * 52;
/* 1017 */     int outIndex = 0;
/* 1018 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1019 */       int jWidth = blockWidth(jBlock);
/* 1020 */       double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1021 */       System.arraycopy(block, iRow * jWidth, outData, outIndex, jWidth);
/* 1022 */       outIndex += jWidth;
/*      */     } 
/*      */     
/* 1025 */     return new ArrayRealVector(outData, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRowVector(int row, RealVector vector) throws OutOfRangeException, MatrixDimensionMismatchException {
/*      */     try {
/* 1033 */       setRow(row, ((ArrayRealVector)vector).getDataRef());
/* 1034 */     } catch (ClassCastException cce) {
/* 1035 */       super.setRowVector(row, vector);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector getColumnVector(int column) throws OutOfRangeException {
/* 1043 */     MatrixUtils.checkColumnIndex(this, column);
/* 1044 */     double[] outData = new double[this.rows];
/*      */ 
/*      */     
/* 1047 */     int jBlock = column / 52;
/* 1048 */     int jColumn = column - jBlock * 52;
/* 1049 */     int jWidth = blockWidth(jBlock);
/* 1050 */     int outIndex = 0;
/* 1051 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1052 */       int iHeight = blockHeight(iBlock);
/* 1053 */       double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1054 */       for (int i = 0; i < iHeight; i++) {
/* 1055 */         outData[outIndex++] = block[i * jWidth + jColumn];
/*      */       }
/*      */     } 
/*      */     
/* 1059 */     return new ArrayRealVector(outData, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnVector(int column, RealVector vector) throws OutOfRangeException, MatrixDimensionMismatchException {
/*      */     try {
/* 1067 */       setColumn(column, ((ArrayRealVector)vector).getDataRef());
/* 1068 */     } catch (ClassCastException cce) {
/* 1069 */       super.setColumnVector(column, vector);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] getRow(int row) throws OutOfRangeException {
/* 1076 */     MatrixUtils.checkRowIndex(this, row);
/* 1077 */     double[] out = new double[this.columns];
/*      */ 
/*      */     
/* 1080 */     int iBlock = row / 52;
/* 1081 */     int iRow = row - iBlock * 52;
/* 1082 */     int outIndex = 0;
/* 1083 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1084 */       int jWidth = blockWidth(jBlock);
/* 1085 */       double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1086 */       System.arraycopy(block, iRow * jWidth, out, outIndex, jWidth);
/* 1087 */       outIndex += jWidth;
/*      */     } 
/*      */     
/* 1090 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRow(int row, double[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
/* 1097 */     MatrixUtils.checkRowIndex(this, row);
/* 1098 */     int nCols = getColumnDimension();
/* 1099 */     if (array.length != nCols) {
/* 1100 */       throw new MatrixDimensionMismatchException(1, array.length, 1, nCols);
/*      */     }
/*      */ 
/*      */     
/* 1104 */     int iBlock = row / 52;
/* 1105 */     int iRow = row - iBlock * 52;
/* 1106 */     int outIndex = 0;
/* 1107 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1108 */       int jWidth = blockWidth(jBlock);
/* 1109 */       double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1110 */       System.arraycopy(array, outIndex, block, iRow * jWidth, jWidth);
/* 1111 */       outIndex += jWidth;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] getColumn(int column) throws OutOfRangeException {
/* 1118 */     MatrixUtils.checkColumnIndex(this, column);
/* 1119 */     double[] out = new double[this.rows];
/*      */ 
/*      */     
/* 1122 */     int jBlock = column / 52;
/* 1123 */     int jColumn = column - jBlock * 52;
/* 1124 */     int jWidth = blockWidth(jBlock);
/* 1125 */     int outIndex = 0;
/* 1126 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1127 */       int iHeight = blockHeight(iBlock);
/* 1128 */       double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1129 */       for (int i = 0; i < iHeight; i++) {
/* 1130 */         out[outIndex++] = block[i * jWidth + jColumn];
/*      */       }
/*      */     } 
/*      */     
/* 1134 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumn(int column, double[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
/* 1141 */     MatrixUtils.checkColumnIndex(this, column);
/* 1142 */     int nRows = getRowDimension();
/* 1143 */     if (array.length != nRows) {
/* 1144 */       throw new MatrixDimensionMismatchException(array.length, 1, nRows, 1);
/*      */     }
/*      */ 
/*      */     
/* 1148 */     int jBlock = column / 52;
/* 1149 */     int jColumn = column - jBlock * 52;
/* 1150 */     int jWidth = blockWidth(jBlock);
/* 1151 */     int outIndex = 0;
/* 1152 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1153 */       int iHeight = blockHeight(iBlock);
/* 1154 */       double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1155 */       for (int i = 0; i < iHeight; i++) {
/* 1156 */         block[i * jWidth + jColumn] = array[outIndex++];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getEntry(int row, int column) throws OutOfRangeException {
/* 1165 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 1166 */     int iBlock = row / 52;
/* 1167 */     int jBlock = column / 52;
/* 1168 */     int k = (row - iBlock * 52) * blockWidth(jBlock) + column - jBlock * 52;
/*      */     
/* 1170 */     return this.blocks[iBlock * this.blockColumns + jBlock][k];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntry(int row, int column, double value) throws OutOfRangeException {
/* 1177 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 1178 */     int iBlock = row / 52;
/* 1179 */     int jBlock = column / 52;
/* 1180 */     int k = (row - iBlock * 52) * blockWidth(jBlock) + column - jBlock * 52;
/*      */     
/* 1182 */     this.blocks[iBlock * this.blockColumns + jBlock][k] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToEntry(int row, int column, double increment) throws OutOfRangeException {
/* 1190 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 1191 */     int iBlock = row / 52;
/* 1192 */     int jBlock = column / 52;
/* 1193 */     int k = (row - iBlock * 52) * blockWidth(jBlock) + column - jBlock * 52;
/*      */     
/* 1195 */     this.blocks[iBlock * this.blockColumns + jBlock][k] = this.blocks[iBlock * this.blockColumns + jBlock][k] + increment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void multiplyEntry(int row, int column, double factor) throws OutOfRangeException {
/* 1203 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 1204 */     int iBlock = row / 52;
/* 1205 */     int jBlock = column / 52;
/* 1206 */     int k = (row - iBlock * 52) * blockWidth(jBlock) + column - jBlock * 52;
/*      */     
/* 1208 */     this.blocks[iBlock * this.blockColumns + jBlock][k] = this.blocks[iBlock * this.blockColumns + jBlock][k] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRealMatrix transpose() {
/* 1214 */     int nRows = getRowDimension();
/* 1215 */     int nCols = getColumnDimension();
/* 1216 */     BlockRealMatrix out = new BlockRealMatrix(nCols, nRows);
/*      */ 
/*      */     
/* 1219 */     int blockIndex = 0;
/* 1220 */     for (int iBlock = 0; iBlock < this.blockColumns; iBlock++) {
/* 1221 */       for (int jBlock = 0; jBlock < this.blockRows; jBlock++) {
/*      */         
/* 1223 */         double[] outBlock = out.blocks[blockIndex];
/* 1224 */         double[] tBlock = this.blocks[jBlock * this.blockColumns + iBlock];
/* 1225 */         int pStart = iBlock * 52;
/* 1226 */         int pEnd = FastMath.min(pStart + 52, this.columns);
/* 1227 */         int qStart = jBlock * 52;
/* 1228 */         int qEnd = FastMath.min(qStart + 52, this.rows);
/* 1229 */         int k = 0;
/* 1230 */         for (int p = pStart; p < pEnd; p++) {
/* 1231 */           int lInc = pEnd - pStart;
/* 1232 */           int l = p - pStart;
/* 1233 */           for (int q = qStart; q < qEnd; q++) {
/* 1234 */             outBlock[k] = tBlock[l];
/* 1235 */             k++;
/* 1236 */             l += lInc;
/*      */           } 
/*      */         } 
/*      */         
/* 1240 */         blockIndex++;
/*      */       } 
/*      */     } 
/*      */     
/* 1244 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowDimension() {
/* 1250 */     return this.rows;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnDimension() {
/* 1256 */     return this.columns;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] operate(double[] v) throws DimensionMismatchException {
/* 1263 */     if (v.length != this.columns) {
/* 1264 */       throw new DimensionMismatchException(v.length, this.columns);
/*      */     }
/* 1266 */     double[] out = new double[this.rows];
/*      */ 
/*      */     
/* 1269 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1270 */       int pStart = iBlock * 52;
/* 1271 */       int pEnd = FastMath.min(pStart + 52, this.rows);
/* 1272 */       for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1273 */         double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1274 */         int qStart = jBlock * 52;
/* 1275 */         int qEnd = FastMath.min(qStart + 52, this.columns);
/* 1276 */         int k = 0;
/* 1277 */         for (int p = pStart; p < pEnd; p++) {
/* 1278 */           double sum = 0.0D;
/* 1279 */           int q = qStart;
/* 1280 */           while (q < qEnd - 3) {
/* 1281 */             sum += block[k] * v[q] + block[k + 1] * v[q + 1] + block[k + 2] * v[q + 2] + block[k + 3] * v[q + 3];
/*      */ 
/*      */ 
/*      */             
/* 1285 */             k += 4;
/* 1286 */             q += 4;
/*      */           } 
/* 1288 */           while (q < qEnd) {
/* 1289 */             sum += block[k++] * v[q++];
/*      */           }
/* 1291 */           out[p] = out[p] + sum;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1296 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] preMultiply(double[] v) throws DimensionMismatchException {
/* 1303 */     if (v.length != this.rows) {
/* 1304 */       throw new DimensionMismatchException(v.length, this.rows);
/*      */     }
/* 1306 */     double[] out = new double[this.columns];
/*      */ 
/*      */     
/* 1309 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1310 */       int jWidth = blockWidth(jBlock);
/* 1311 */       int jWidth2 = jWidth + jWidth;
/* 1312 */       int jWidth3 = jWidth2 + jWidth;
/* 1313 */       int jWidth4 = jWidth3 + jWidth;
/* 1314 */       int qStart = jBlock * 52;
/* 1315 */       int qEnd = FastMath.min(qStart + 52, this.columns);
/* 1316 */       for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1317 */         double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1318 */         int pStart = iBlock * 52;
/* 1319 */         int pEnd = FastMath.min(pStart + 52, this.rows);
/* 1320 */         for (int q = qStart; q < qEnd; q++) {
/* 1321 */           int k = q - qStart;
/* 1322 */           double sum = 0.0D;
/* 1323 */           int p = pStart;
/* 1324 */           while (p < pEnd - 3) {
/* 1325 */             sum += block[k] * v[p] + block[k + jWidth] * v[p + 1] + block[k + jWidth2] * v[p + 2] + block[k + jWidth3] * v[p + 3];
/*      */ 
/*      */ 
/*      */             
/* 1329 */             k += jWidth4;
/* 1330 */             p += 4;
/*      */           } 
/* 1332 */           while (p < pEnd) {
/* 1333 */             sum += block[k] * v[p++];
/* 1334 */             k += jWidth;
/*      */           } 
/* 1336 */           out[q] = out[q] + sum;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1341 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double walkInRowOrder(RealMatrixChangingVisitor visitor) {
/* 1347 */     visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
/* 1348 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1349 */       int pStart = iBlock * 52;
/* 1350 */       int pEnd = FastMath.min(pStart + 52, this.rows);
/* 1351 */       for (int p = pStart; p < pEnd; p++) {
/* 1352 */         for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1353 */           int jWidth = blockWidth(jBlock);
/* 1354 */           int qStart = jBlock * 52;
/* 1355 */           int qEnd = FastMath.min(qStart + 52, this.columns);
/* 1356 */           double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1357 */           int k = (p - pStart) * jWidth;
/* 1358 */           for (int q = qStart; q < qEnd; q++) {
/* 1359 */             block[k] = visitor.visit(p, q, block[k]);
/* 1360 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1365 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double walkInRowOrder(RealMatrixPreservingVisitor visitor) {
/* 1371 */     visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
/* 1372 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1373 */       int pStart = iBlock * 52;
/* 1374 */       int pEnd = FastMath.min(pStart + 52, this.rows);
/* 1375 */       for (int p = pStart; p < pEnd; p++) {
/* 1376 */         for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1377 */           int jWidth = blockWidth(jBlock);
/* 1378 */           int qStart = jBlock * 52;
/* 1379 */           int qEnd = FastMath.min(qStart + 52, this.columns);
/* 1380 */           double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1381 */           int k = (p - pStart) * jWidth;
/* 1382 */           for (int q = qStart; q < qEnd; q++) {
/* 1383 */             visitor.visit(p, q, block[k]);
/* 1384 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1389 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double walkInRowOrder(RealMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 1398 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 1399 */     visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
/* 1400 */     for (int iBlock = startRow / 52; iBlock < 1 + endRow / 52; iBlock++) {
/* 1401 */       int p0 = iBlock * 52;
/* 1402 */       int pStart = FastMath.max(startRow, p0);
/* 1403 */       int pEnd = FastMath.min((iBlock + 1) * 52, 1 + endRow);
/* 1404 */       for (int p = pStart; p < pEnd; p++) {
/* 1405 */         for (int jBlock = startColumn / 52; jBlock < 1 + endColumn / 52; jBlock++) {
/* 1406 */           int jWidth = blockWidth(jBlock);
/* 1407 */           int q0 = jBlock * 52;
/* 1408 */           int qStart = FastMath.max(startColumn, q0);
/* 1409 */           int qEnd = FastMath.min((jBlock + 1) * 52, 1 + endColumn);
/* 1410 */           double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1411 */           int k = (p - p0) * jWidth + qStart - q0;
/* 1412 */           for (int q = qStart; q < qEnd; q++) {
/* 1413 */             block[k] = visitor.visit(p, q, block[k]);
/* 1414 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1419 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double walkInRowOrder(RealMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 1428 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 1429 */     visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
/* 1430 */     for (int iBlock = startRow / 52; iBlock < 1 + endRow / 52; iBlock++) {
/* 1431 */       int p0 = iBlock * 52;
/* 1432 */       int pStart = FastMath.max(startRow, p0);
/* 1433 */       int pEnd = FastMath.min((iBlock + 1) * 52, 1 + endRow);
/* 1434 */       for (int p = pStart; p < pEnd; p++) {
/* 1435 */         for (int jBlock = startColumn / 52; jBlock < 1 + endColumn / 52; jBlock++) {
/* 1436 */           int jWidth = blockWidth(jBlock);
/* 1437 */           int q0 = jBlock * 52;
/* 1438 */           int qStart = FastMath.max(startColumn, q0);
/* 1439 */           int qEnd = FastMath.min((jBlock + 1) * 52, 1 + endColumn);
/* 1440 */           double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1441 */           int k = (p - p0) * jWidth + qStart - q0;
/* 1442 */           for (int q = qStart; q < qEnd; q++) {
/* 1443 */             visitor.visit(p, q, block[k]);
/* 1444 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1449 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double walkInOptimizedOrder(RealMatrixChangingVisitor visitor) {
/* 1455 */     visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
/* 1456 */     int blockIndex = 0;
/* 1457 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1458 */       int pStart = iBlock * 52;
/* 1459 */       int pEnd = FastMath.min(pStart + 52, this.rows);
/* 1460 */       for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1461 */         int qStart = jBlock * 52;
/* 1462 */         int qEnd = FastMath.min(qStart + 52, this.columns);
/* 1463 */         double[] block = this.blocks[blockIndex];
/* 1464 */         int k = 0;
/* 1465 */         for (int p = pStart; p < pEnd; p++) {
/* 1466 */           for (int q = qStart; q < qEnd; q++) {
/* 1467 */             block[k] = visitor.visit(p, q, block[k]);
/* 1468 */             k++;
/*      */           } 
/*      */         } 
/* 1471 */         blockIndex++;
/*      */       } 
/*      */     } 
/* 1474 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double walkInOptimizedOrder(RealMatrixPreservingVisitor visitor) {
/* 1480 */     visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
/* 1481 */     int blockIndex = 0;
/* 1482 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1483 */       int pStart = iBlock * 52;
/* 1484 */       int pEnd = FastMath.min(pStart + 52, this.rows);
/* 1485 */       for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1486 */         int qStart = jBlock * 52;
/* 1487 */         int qEnd = FastMath.min(qStart + 52, this.columns);
/* 1488 */         double[] block = this.blocks[blockIndex];
/* 1489 */         int k = 0;
/* 1490 */         for (int p = pStart; p < pEnd; p++) {
/* 1491 */           for (int q = qStart; q < qEnd; q++) {
/* 1492 */             visitor.visit(p, q, block[k]);
/* 1493 */             k++;
/*      */           } 
/*      */         } 
/* 1496 */         blockIndex++;
/*      */       } 
/*      */     } 
/* 1499 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double walkInOptimizedOrder(RealMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 1509 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 1510 */     visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
/* 1511 */     for (int iBlock = startRow / 52; iBlock < 1 + endRow / 52; iBlock++) {
/* 1512 */       int p0 = iBlock * 52;
/* 1513 */       int pStart = FastMath.max(startRow, p0);
/* 1514 */       int pEnd = FastMath.min((iBlock + 1) * 52, 1 + endRow);
/* 1515 */       for (int jBlock = startColumn / 52; jBlock < 1 + endColumn / 52; jBlock++) {
/* 1516 */         int jWidth = blockWidth(jBlock);
/* 1517 */         int q0 = jBlock * 52;
/* 1518 */         int qStart = FastMath.max(startColumn, q0);
/* 1519 */         int qEnd = FastMath.min((jBlock + 1) * 52, 1 + endColumn);
/* 1520 */         double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1521 */         for (int p = pStart; p < pEnd; p++) {
/* 1522 */           int k = (p - p0) * jWidth + qStart - q0;
/* 1523 */           for (int q = qStart; q < qEnd; q++) {
/* 1524 */             block[k] = visitor.visit(p, q, block[k]);
/* 1525 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1530 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double walkInOptimizedOrder(RealMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 1540 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 1541 */     visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
/* 1542 */     for (int iBlock = startRow / 52; iBlock < 1 + endRow / 52; iBlock++) {
/* 1543 */       int p0 = iBlock * 52;
/* 1544 */       int pStart = FastMath.max(startRow, p0);
/* 1545 */       int pEnd = FastMath.min((iBlock + 1) * 52, 1 + endRow);
/* 1546 */       for (int jBlock = startColumn / 52; jBlock < 1 + endColumn / 52; jBlock++) {
/* 1547 */         int jWidth = blockWidth(jBlock);
/* 1548 */         int q0 = jBlock * 52;
/* 1549 */         int qStart = FastMath.max(startColumn, q0);
/* 1550 */         int qEnd = FastMath.min((jBlock + 1) * 52, 1 + endColumn);
/* 1551 */         double[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1552 */         for (int p = pStart; p < pEnd; p++) {
/* 1553 */           int k = (p - p0) * jWidth + qStart - q0;
/* 1554 */           for (int q = qStart; q < qEnd; q++) {
/* 1555 */             visitor.visit(p, q, block[k]);
/* 1556 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1561 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int blockHeight(int blockRow) {
/* 1570 */     return (blockRow == this.blockRows - 1) ? (this.rows - blockRow * 52) : 52;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int blockWidth(int blockColumn) {
/* 1579 */     return (blockColumn == this.blockColumns - 1) ? (this.columns - blockColumn * 52) : 52;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/BlockRealMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */