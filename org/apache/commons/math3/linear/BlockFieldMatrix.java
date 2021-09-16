/*      */ package org.apache.commons.math3.linear;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.FieldElement;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.NoDataException;
/*      */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathArrays;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BlockFieldMatrix<T extends FieldElement<T>>
/*      */   extends AbstractFieldMatrix<T>
/*      */   implements Serializable
/*      */ {
/*      */   public static final int BLOCK_SIZE = 36;
/*      */   private static final long serialVersionUID = -4602336630143123183L;
/*      */   private final T[][] blocks;
/*      */   private final int rows;
/*      */   private final int columns;
/*      */   private final int blockRows;
/*      */   private final int blockColumns;
/*      */   
/*      */   public BlockFieldMatrix(Field<T> field, int rows, int columns) throws NotStrictlyPositiveException {
/*  102 */     super(field, rows, columns);
/*  103 */     this.rows = rows;
/*  104 */     this.columns = columns;
/*      */ 
/*      */     
/*  107 */     this.blockRows = (rows + 36 - 1) / 36;
/*  108 */     this.blockColumns = (columns + 36 - 1) / 36;
/*      */ 
/*      */     
/*  111 */     this.blocks = createBlocksLayout(field, rows, columns);
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
/*      */   public BlockFieldMatrix(T[][] rawData) throws DimensionMismatchException {
/*  129 */     this(rawData.length, (rawData[0]).length, toBlocksLayout(rawData), false);
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
/*      */   public BlockFieldMatrix(int rows, int columns, T[][] blockData, boolean copyArray) throws DimensionMismatchException, NotStrictlyPositiveException {
/*  152 */     super(extractField(blockData), rows, columns);
/*  153 */     this.rows = rows;
/*  154 */     this.columns = columns;
/*      */ 
/*      */     
/*  157 */     this.blockRows = (rows + 36 - 1) / 36;
/*  158 */     this.blockColumns = (columns + 36 - 1) / 36;
/*      */     
/*  160 */     if (copyArray) {
/*      */       
/*  162 */       this.blocks = (T[][])MathArrays.buildArray(getField(), this.blockRows * this.blockColumns, -1);
/*      */     } else {
/*      */       
/*  165 */       this.blocks = blockData;
/*      */     } 
/*      */     
/*  168 */     int index = 0;
/*  169 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/*  170 */       int iHeight = blockHeight(iBlock);
/*  171 */       for (int jBlock = 0; jBlock < this.blockColumns; jBlock++, index++) {
/*  172 */         if ((blockData[index]).length != iHeight * blockWidth(jBlock)) {
/*  173 */           throw new DimensionMismatchException((blockData[index]).length, iHeight * blockWidth(jBlock));
/*      */         }
/*      */         
/*  176 */         if (copyArray) {
/*  177 */           this.blocks[index] = (T[])blockData[index].clone();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends FieldElement<T>> T[][] toBlocksLayout(T[][] rawData) throws DimensionMismatchException {
/*  210 */     int rows = rawData.length;
/*  211 */     int columns = (rawData[0]).length;
/*  212 */     int blockRows = (rows + 36 - 1) / 36;
/*  213 */     int blockColumns = (columns + 36 - 1) / 36;
/*      */ 
/*      */     
/*  216 */     for (int i = 0; i < rawData.length; i++) {
/*  217 */       int length = (rawData[i]).length;
/*  218 */       if (length != columns) {
/*  219 */         throw new DimensionMismatchException(columns, length);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  224 */     Field<T> field = extractField(rawData);
/*  225 */     FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(field, blockRows * blockColumns, -1);
/*  226 */     int blockIndex = 0;
/*  227 */     for (int iBlock = 0; iBlock < blockRows; iBlock++) {
/*  228 */       int pStart = iBlock * 36;
/*  229 */       int pEnd = FastMath.min(pStart + 36, rows);
/*  230 */       int iHeight = pEnd - pStart;
/*  231 */       for (int jBlock = 0; jBlock < blockColumns; jBlock++) {
/*  232 */         int qStart = jBlock * 36;
/*  233 */         int qEnd = FastMath.min(qStart + 36, columns);
/*  234 */         int jWidth = qEnd - qStart;
/*      */ 
/*      */         
/*  237 */         FieldElement[] arrayOfFieldElement1 = (FieldElement[])MathArrays.buildArray(field, iHeight * jWidth);
/*  238 */         arrayOfFieldElement[blockIndex] = arrayOfFieldElement1;
/*      */ 
/*      */         
/*  241 */         int index = 0;
/*  242 */         for (int p = pStart; p < pEnd; p++) {
/*  243 */           System.arraycopy(rawData[p], qStart, arrayOfFieldElement1, index, jWidth);
/*  244 */           index += jWidth;
/*      */         } 
/*      */         
/*  247 */         blockIndex++;
/*      */       } 
/*      */     } 
/*      */     
/*  251 */     return (T[][])arrayOfFieldElement;
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
/*      */   public static <T extends FieldElement<T>> T[][] createBlocksLayout(Field<T> field, int rows, int columns) {
/*  271 */     int blockRows = (rows + 36 - 1) / 36;
/*  272 */     int blockColumns = (columns + 36 - 1) / 36;
/*      */     
/*  274 */     FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(field, blockRows * blockColumns, -1);
/*  275 */     int blockIndex = 0;
/*  276 */     for (int iBlock = 0; iBlock < blockRows; iBlock++) {
/*  277 */       int pStart = iBlock * 36;
/*  278 */       int pEnd = FastMath.min(pStart + 36, rows);
/*  279 */       int iHeight = pEnd - pStart;
/*  280 */       for (int jBlock = 0; jBlock < blockColumns; jBlock++) {
/*  281 */         int qStart = jBlock * 36;
/*  282 */         int qEnd = FastMath.min(qStart + 36, columns);
/*  283 */         int jWidth = qEnd - qStart;
/*  284 */         arrayOfFieldElement[blockIndex] = (FieldElement[])MathArrays.buildArray(field, iHeight * jWidth);
/*  285 */         blockIndex++;
/*      */       } 
/*      */     } 
/*      */     
/*  289 */     return (T[][])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> createMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
/*  297 */     return new BlockFieldMatrix(getField(), rowDimension, columnDimension);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> copy() {
/*  306 */     BlockFieldMatrix<T> copied = new BlockFieldMatrix(getField(), this.rows, this.columns);
/*      */ 
/*      */     
/*  309 */     for (int i = 0; i < this.blocks.length; i++) {
/*  310 */       System.arraycopy(this.blocks[i], 0, copied.blocks[i], 0, (this.blocks[i]).length);
/*      */     }
/*      */     
/*  313 */     return copied;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> add(FieldMatrix<T> m) throws MatrixDimensionMismatchException {
/*      */     try {
/*  321 */       return add((BlockFieldMatrix<T>)m);
/*  322 */     } catch (ClassCastException cce) {
/*      */ 
/*      */       
/*  325 */       checkAdditionCompatible(m);
/*      */       
/*  327 */       BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, this.columns);
/*      */ 
/*      */       
/*  330 */       int blockIndex = 0;
/*  331 */       for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
/*  332 */         for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
/*      */ 
/*      */           
/*  335 */           T[] outBlock = out.blocks[blockIndex];
/*  336 */           T[] tBlock = this.blocks[blockIndex];
/*  337 */           int pStart = iBlock * 36;
/*  338 */           int pEnd = FastMath.min(pStart + 36, this.rows);
/*  339 */           int qStart = jBlock * 36;
/*  340 */           int qEnd = FastMath.min(qStart + 36, this.columns);
/*  341 */           int k = 0;
/*  342 */           for (int p = pStart; p < pEnd; p++) {
/*  343 */             for (int q = qStart; q < qEnd; q++) {
/*  344 */               outBlock[k] = (T)tBlock[k].add(m.getEntry(p, q));
/*  345 */               k++;
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  350 */           blockIndex++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  355 */       return out;
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
/*      */   public BlockFieldMatrix<T> add(BlockFieldMatrix<T> m) throws MatrixDimensionMismatchException {
/*  371 */     checkAdditionCompatible(m);
/*      */     
/*  373 */     BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, this.columns);
/*      */ 
/*      */     
/*  376 */     for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
/*  377 */       T[] outBlock = out.blocks[blockIndex];
/*  378 */       T[] tBlock = this.blocks[blockIndex];
/*  379 */       T[] mBlock = m.blocks[blockIndex];
/*  380 */       for (int k = 0; k < outBlock.length; k++) {
/*  381 */         outBlock[k] = (T)tBlock[k].add(mBlock[k]);
/*      */       }
/*      */     } 
/*      */     
/*  385 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> subtract(FieldMatrix<T> m) throws MatrixDimensionMismatchException {
/*      */     try {
/*  393 */       return subtract((BlockFieldMatrix<T>)m);
/*  394 */     } catch (ClassCastException cce) {
/*      */ 
/*      */       
/*  397 */       checkSubtractionCompatible(m);
/*      */       
/*  399 */       BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, this.columns);
/*      */ 
/*      */       
/*  402 */       int blockIndex = 0;
/*  403 */       for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
/*  404 */         for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
/*      */ 
/*      */           
/*  407 */           T[] outBlock = out.blocks[blockIndex];
/*  408 */           T[] tBlock = this.blocks[blockIndex];
/*  409 */           int pStart = iBlock * 36;
/*  410 */           int pEnd = FastMath.min(pStart + 36, this.rows);
/*  411 */           int qStart = jBlock * 36;
/*  412 */           int qEnd = FastMath.min(qStart + 36, this.columns);
/*  413 */           int k = 0;
/*  414 */           for (int p = pStart; p < pEnd; p++) {
/*  415 */             for (int q = qStart; q < qEnd; q++) {
/*  416 */               outBlock[k] = (T)tBlock[k].subtract(m.getEntry(p, q));
/*  417 */               k++;
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  422 */           blockIndex++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  427 */       return out;
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
/*      */   public BlockFieldMatrix<T> subtract(BlockFieldMatrix<T> m) throws MatrixDimensionMismatchException {
/*  441 */     checkSubtractionCompatible(m);
/*      */     
/*  443 */     BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, this.columns);
/*      */ 
/*      */     
/*  446 */     for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
/*  447 */       T[] outBlock = out.blocks[blockIndex];
/*  448 */       T[] tBlock = this.blocks[blockIndex];
/*  449 */       T[] mBlock = m.blocks[blockIndex];
/*  450 */       for (int k = 0; k < outBlock.length; k++) {
/*  451 */         outBlock[k] = (T)tBlock[k].subtract(mBlock[k]);
/*      */       }
/*      */     } 
/*      */     
/*  455 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> scalarAdd(T d) {
/*  461 */     BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, this.columns);
/*      */ 
/*      */     
/*  464 */     for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
/*  465 */       T[] outBlock = out.blocks[blockIndex];
/*  466 */       T[] tBlock = this.blocks[blockIndex];
/*  467 */       for (int k = 0; k < outBlock.length; k++) {
/*  468 */         outBlock[k] = (T)tBlock[k].add(d);
/*      */       }
/*      */     } 
/*      */     
/*  472 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> scalarMultiply(T d) {
/*  479 */     BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, this.columns);
/*      */ 
/*      */     
/*  482 */     for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
/*  483 */       T[] outBlock = out.blocks[blockIndex];
/*  484 */       T[] tBlock = this.blocks[blockIndex];
/*  485 */       for (int k = 0; k < outBlock.length; k++) {
/*  486 */         outBlock[k] = (T)tBlock[k].multiply(d);
/*      */       }
/*      */     } 
/*      */     
/*  490 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> multiply(FieldMatrix<T> m) throws DimensionMismatchException {
/*      */     try {
/*  498 */       return multiply((BlockFieldMatrix<T>)m);
/*  499 */     } catch (ClassCastException cce) {
/*      */ 
/*      */       
/*  502 */       checkMultiplicationCompatible(m);
/*      */       
/*  504 */       BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, m.getColumnDimension());
/*  505 */       FieldElement fieldElement = (FieldElement)getField().getZero();
/*      */ 
/*      */       
/*  508 */       int blockIndex = 0;
/*  509 */       for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
/*      */         
/*  511 */         int pStart = iBlock * 36;
/*  512 */         int pEnd = FastMath.min(pStart + 36, this.rows);
/*      */         
/*  514 */         for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
/*      */           
/*  516 */           int qStart = jBlock * 36;
/*  517 */           int qEnd = FastMath.min(qStart + 36, m.getColumnDimension());
/*      */ 
/*      */           
/*  520 */           T[] outBlock = out.blocks[blockIndex];
/*      */ 
/*      */           
/*  523 */           for (int kBlock = 0; kBlock < this.blockColumns; kBlock++) {
/*  524 */             int kWidth = blockWidth(kBlock);
/*  525 */             T[] tBlock = this.blocks[iBlock * this.blockColumns + kBlock];
/*  526 */             int rStart = kBlock * 36;
/*  527 */             int k = 0;
/*  528 */             for (int p = pStart; p < pEnd; p++) {
/*  529 */               int lStart = (p - pStart) * kWidth;
/*  530 */               int lEnd = lStart + kWidth;
/*  531 */               for (int q = qStart; q < qEnd; q++) {
/*  532 */                 FieldElement fieldElement1 = fieldElement;
/*  533 */                 int r = rStart;
/*  534 */                 for (int l = lStart; l < lEnd; l++) {
/*  535 */                   fieldElement1 = (FieldElement)fieldElement1.add(tBlock[l].multiply(m.getEntry(r, q)));
/*  536 */                   r++;
/*      */                 } 
/*  538 */                 outBlock[k] = (T)outBlock[k].add(fieldElement1);
/*  539 */                 k++;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  545 */           blockIndex++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  550 */       return out;
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
/*      */   public BlockFieldMatrix<T> multiply(BlockFieldMatrix<T> m) throws DimensionMismatchException {
/*  565 */     checkMultiplicationCompatible(m);
/*      */     
/*  567 */     BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, m.columns);
/*  568 */     FieldElement fieldElement = (FieldElement)getField().getZero();
/*      */ 
/*      */     
/*  571 */     int blockIndex = 0;
/*  572 */     for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
/*      */       
/*  574 */       int pStart = iBlock * 36;
/*  575 */       int pEnd = FastMath.min(pStart + 36, this.rows);
/*      */       
/*  577 */       for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
/*  578 */         int jWidth = out.blockWidth(jBlock);
/*  579 */         int jWidth2 = jWidth + jWidth;
/*  580 */         int jWidth3 = jWidth2 + jWidth;
/*  581 */         int jWidth4 = jWidth3 + jWidth;
/*      */ 
/*      */         
/*  584 */         T[] outBlock = out.blocks[blockIndex];
/*      */ 
/*      */         
/*  587 */         for (int kBlock = 0; kBlock < this.blockColumns; kBlock++) {
/*  588 */           int kWidth = blockWidth(kBlock);
/*  589 */           T[] tBlock = this.blocks[iBlock * this.blockColumns + kBlock];
/*  590 */           T[] mBlock = m.blocks[kBlock * m.blockColumns + jBlock];
/*  591 */           int k = 0;
/*  592 */           for (int p = pStart; p < pEnd; p++) {
/*  593 */             int lStart = (p - pStart) * kWidth;
/*  594 */             int lEnd = lStart + kWidth;
/*  595 */             for (int nStart = 0; nStart < jWidth; nStart++) {
/*  596 */               FieldElement fieldElement1 = fieldElement;
/*  597 */               int l = lStart;
/*  598 */               int n = nStart;
/*  599 */               while (l < lEnd - 3) {
/*  600 */                 fieldElement1 = (FieldElement)((FieldElement)((FieldElement)((FieldElement)fieldElement1.add(tBlock[l].multiply(mBlock[n]))).add(tBlock[l + 1].multiply(mBlock[n + jWidth]))).add(tBlock[l + 2].multiply(mBlock[n + jWidth2]))).add(tBlock[l + 3].multiply(mBlock[n + jWidth3]));
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  605 */                 l += 4;
/*  606 */                 n += jWidth4;
/*      */               } 
/*  608 */               while (l < lEnd) {
/*  609 */                 fieldElement1 = (FieldElement)fieldElement1.add(tBlock[l++].multiply(mBlock[n]));
/*  610 */                 n += jWidth;
/*      */               } 
/*  612 */               outBlock[k] = (T)outBlock[k].add(fieldElement1);
/*  613 */               k++;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  619 */         blockIndex++;
/*      */       } 
/*      */     } 
/*      */     
/*  623 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T[][] getData() {
/*  630 */     FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(getField(), getRowDimension(), getColumnDimension());
/*  631 */     int lastColumns = this.columns - (this.blockColumns - 1) * 36;
/*      */     
/*  633 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/*  634 */       int pStart = iBlock * 36;
/*  635 */       int pEnd = FastMath.min(pStart + 36, this.rows);
/*  636 */       int regularPos = 0;
/*  637 */       int lastPos = 0;
/*  638 */       for (int p = pStart; p < pEnd; p++) {
/*  639 */         FieldElement[] arrayOfFieldElement1 = arrayOfFieldElement[p];
/*  640 */         int blockIndex = iBlock * this.blockColumns;
/*  641 */         int dataPos = 0;
/*  642 */         for (int jBlock = 0; jBlock < this.blockColumns - 1; jBlock++) {
/*  643 */           System.arraycopy(this.blocks[blockIndex++], regularPos, arrayOfFieldElement1, dataPos, 36);
/*  644 */           dataPos += 36;
/*      */         } 
/*  646 */         System.arraycopy(this.blocks[blockIndex], lastPos, arrayOfFieldElement1, dataPos, lastColumns);
/*  647 */         regularPos += 36;
/*  648 */         lastPos += lastColumns;
/*      */       } 
/*      */     } 
/*      */     
/*  652 */     return (T[][])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/*  662 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/*      */ 
/*      */     
/*  665 */     BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), endRow - startRow + 1, endColumn - startColumn + 1);
/*      */ 
/*      */ 
/*      */     
/*  669 */     int blockStartRow = startRow / 36;
/*  670 */     int rowsShift = startRow % 36;
/*  671 */     int blockStartColumn = startColumn / 36;
/*  672 */     int columnsShift = startColumn % 36;
/*      */ 
/*      */     
/*  675 */     int pBlock = blockStartRow;
/*  676 */     for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
/*  677 */       int iHeight = out.blockHeight(iBlock);
/*  678 */       int qBlock = blockStartColumn;
/*  679 */       for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
/*  680 */         int jWidth = out.blockWidth(jBlock);
/*      */ 
/*      */         
/*  683 */         int outIndex = iBlock * out.blockColumns + jBlock;
/*  684 */         T[] outBlock = out.blocks[outIndex];
/*  685 */         int index = pBlock * this.blockColumns + qBlock;
/*  686 */         int width = blockWidth(qBlock);
/*      */         
/*  688 */         int heightExcess = iHeight + rowsShift - 36;
/*  689 */         int widthExcess = jWidth + columnsShift - 36;
/*  690 */         if (heightExcess > 0) {
/*      */           
/*  692 */           if (widthExcess > 0)
/*      */           {
/*  694 */             int width2 = blockWidth(qBlock + 1);
/*  695 */             copyBlockPart(this.blocks[index], width, rowsShift, 36, columnsShift, 36, outBlock, jWidth, 0, 0);
/*      */ 
/*      */ 
/*      */             
/*  699 */             copyBlockPart(this.blocks[index + 1], width2, rowsShift, 36, 0, widthExcess, outBlock, jWidth, 0, jWidth - widthExcess);
/*      */ 
/*      */ 
/*      */             
/*  703 */             copyBlockPart(this.blocks[index + this.blockColumns], width, 0, heightExcess, columnsShift, 36, outBlock, jWidth, iHeight - heightExcess, 0);
/*      */ 
/*      */ 
/*      */             
/*  707 */             copyBlockPart(this.blocks[index + this.blockColumns + 1], width2, 0, heightExcess, 0, widthExcess, outBlock, jWidth, iHeight - heightExcess, jWidth - widthExcess);
/*      */           
/*      */           }
/*      */           else
/*      */           {
/*      */             
/*  713 */             copyBlockPart(this.blocks[index], width, rowsShift, 36, columnsShift, jWidth + columnsShift, outBlock, jWidth, 0, 0);
/*      */ 
/*      */ 
/*      */             
/*  717 */             copyBlockPart(this.blocks[index + this.blockColumns], width, 0, heightExcess, columnsShift, jWidth + columnsShift, outBlock, jWidth, iHeight - heightExcess, 0);
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*  724 */         else if (widthExcess > 0) {
/*      */           
/*  726 */           int width2 = blockWidth(qBlock + 1);
/*  727 */           copyBlockPart(this.blocks[index], width, rowsShift, iHeight + rowsShift, columnsShift, 36, outBlock, jWidth, 0, 0);
/*      */ 
/*      */ 
/*      */           
/*  731 */           copyBlockPart(this.blocks[index + 1], width2, rowsShift, iHeight + rowsShift, 0, widthExcess, outBlock, jWidth, 0, jWidth - widthExcess);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  737 */           copyBlockPart(this.blocks[index], width, rowsShift, iHeight + rowsShift, columnsShift, jWidth + columnsShift, outBlock, jWidth, 0, 0);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  743 */         qBlock++;
/*      */       } 
/*  745 */       pBlock++;
/*      */     } 
/*      */     
/*  748 */     return out;
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
/*      */   private void copyBlockPart(T[] srcBlock, int srcWidth, int srcStartRow, int srcEndRow, int srcStartColumn, int srcEndColumn, T[] dstBlock, int dstWidth, int dstStartRow, int dstStartColumn) {
/*  771 */     int length = srcEndColumn - srcStartColumn;
/*  772 */     int srcPos = srcStartRow * srcWidth + srcStartColumn;
/*  773 */     int dstPos = dstStartRow * dstWidth + dstStartColumn;
/*  774 */     for (int srcRow = srcStartRow; srcRow < srcEndRow; srcRow++) {
/*  775 */       System.arraycopy(srcBlock, srcPos, dstBlock, dstPos, length);
/*  776 */       srcPos += srcWidth;
/*  777 */       dstPos += dstWidth;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubMatrix(T[][] subMatrix, int row, int column) throws DimensionMismatchException, OutOfRangeException, NoDataException, NullArgumentException {
/*  788 */     MathUtils.checkNotNull(subMatrix);
/*  789 */     int refLength = (subMatrix[0]).length;
/*  790 */     if (refLength == 0) {
/*  791 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
/*      */     }
/*  793 */     int endRow = row + subMatrix.length - 1;
/*  794 */     int endColumn = column + refLength - 1;
/*  795 */     checkSubMatrixIndex(row, endRow, column, endColumn);
/*  796 */     for (T[] subRow : subMatrix) {
/*  797 */       if (subRow.length != refLength) {
/*  798 */         throw new DimensionMismatchException(refLength, subRow.length);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  803 */     int blockStartRow = row / 36;
/*  804 */     int blockEndRow = (endRow + 36) / 36;
/*  805 */     int blockStartColumn = column / 36;
/*  806 */     int blockEndColumn = (endColumn + 36) / 36;
/*      */ 
/*      */     
/*  809 */     for (int iBlock = blockStartRow; iBlock < blockEndRow; iBlock++) {
/*  810 */       int iHeight = blockHeight(iBlock);
/*  811 */       int firstRow = iBlock * 36;
/*  812 */       int iStart = FastMath.max(row, firstRow);
/*  813 */       int iEnd = FastMath.min(endRow + 1, firstRow + iHeight);
/*      */       
/*  815 */       for (int jBlock = blockStartColumn; jBlock < blockEndColumn; jBlock++) {
/*  816 */         int jWidth = blockWidth(jBlock);
/*  817 */         int firstColumn = jBlock * 36;
/*  818 */         int jStart = FastMath.max(column, firstColumn);
/*  819 */         int jEnd = FastMath.min(endColumn + 1, firstColumn + jWidth);
/*  820 */         int jLength = jEnd - jStart;
/*      */ 
/*      */         
/*  823 */         T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  824 */         for (int i = iStart; i < iEnd; i++) {
/*  825 */           System.arraycopy(subMatrix[i - row], jStart - column, block, (i - firstRow) * jWidth + jStart - firstColumn, jLength);
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
/*      */   public FieldMatrix<T> getRowMatrix(int row) throws OutOfRangeException {
/*  838 */     checkRowIndex(row);
/*  839 */     BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), 1, this.columns);
/*      */ 
/*      */     
/*  842 */     int iBlock = row / 36;
/*  843 */     int iRow = row - iBlock * 36;
/*  844 */     int outBlockIndex = 0;
/*  845 */     int outIndex = 0;
/*  846 */     T[] outBlock = out.blocks[outBlockIndex];
/*  847 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/*  848 */       int jWidth = blockWidth(jBlock);
/*  849 */       T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  850 */       int available = outBlock.length - outIndex;
/*  851 */       if (jWidth > available) {
/*  852 */         System.arraycopy(block, iRow * jWidth, outBlock, outIndex, available);
/*  853 */         outBlock = out.blocks[++outBlockIndex];
/*  854 */         System.arraycopy(block, iRow * jWidth, outBlock, 0, jWidth - available);
/*  855 */         outIndex = jWidth - available;
/*      */       } else {
/*  857 */         System.arraycopy(block, iRow * jWidth, outBlock, outIndex, jWidth);
/*  858 */         outIndex += jWidth;
/*      */       } 
/*      */     } 
/*      */     
/*  862 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRowMatrix(int row, FieldMatrix<T> matrix) throws MatrixDimensionMismatchException, OutOfRangeException {
/*      */     try {
/*  870 */       setRowMatrix(row, (BlockFieldMatrix<T>)matrix);
/*  871 */     } catch (ClassCastException cce) {
/*  872 */       super.setRowMatrix(row, matrix);
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
/*      */   public void setRowMatrix(int row, BlockFieldMatrix<T> matrix) throws MatrixDimensionMismatchException, OutOfRangeException {
/*  889 */     checkRowIndex(row);
/*  890 */     int nCols = getColumnDimension();
/*  891 */     if (matrix.getRowDimension() != 1 || matrix.getColumnDimension() != nCols)
/*      */     {
/*  893 */       throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), 1, nCols);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  899 */     int iBlock = row / 36;
/*  900 */     int iRow = row - iBlock * 36;
/*  901 */     int mBlockIndex = 0;
/*  902 */     int mIndex = 0;
/*  903 */     T[] mBlock = matrix.blocks[mBlockIndex];
/*  904 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/*  905 */       int jWidth = blockWidth(jBlock);
/*  906 */       T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  907 */       int available = mBlock.length - mIndex;
/*  908 */       if (jWidth > available) {
/*  909 */         System.arraycopy(mBlock, mIndex, block, iRow * jWidth, available);
/*  910 */         mBlock = matrix.blocks[++mBlockIndex];
/*  911 */         System.arraycopy(mBlock, 0, block, iRow * jWidth, jWidth - available);
/*  912 */         mIndex = jWidth - available;
/*      */       } else {
/*  914 */         System.arraycopy(mBlock, mIndex, block, iRow * jWidth, jWidth);
/*  915 */         mIndex += jWidth;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> getColumnMatrix(int column) throws OutOfRangeException {
/*  924 */     checkColumnIndex(column);
/*  925 */     BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, 1);
/*      */ 
/*      */     
/*  928 */     int jBlock = column / 36;
/*  929 */     int jColumn = column - jBlock * 36;
/*  930 */     int jWidth = blockWidth(jBlock);
/*  931 */     int outBlockIndex = 0;
/*  932 */     int outIndex = 0;
/*  933 */     T[] outBlock = out.blocks[outBlockIndex];
/*  934 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/*  935 */       int iHeight = blockHeight(iBlock);
/*  936 */       T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  937 */       for (int i = 0; i < iHeight; i++) {
/*  938 */         if (outIndex >= outBlock.length) {
/*  939 */           outBlock = out.blocks[++outBlockIndex];
/*  940 */           outIndex = 0;
/*      */         } 
/*  942 */         outBlock[outIndex++] = block[i * jWidth + jColumn];
/*      */       } 
/*      */     } 
/*      */     
/*  946 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnMatrix(int column, FieldMatrix<T> matrix) throws MatrixDimensionMismatchException, OutOfRangeException {
/*      */     try {
/*  954 */       setColumnMatrix(column, (BlockFieldMatrix<T>)matrix);
/*  955 */     } catch (ClassCastException cce) {
/*  956 */       super.setColumnMatrix(column, matrix);
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
/*      */   void setColumnMatrix(int column, BlockFieldMatrix<T> matrix) throws MatrixDimensionMismatchException, OutOfRangeException {
/*  973 */     checkColumnIndex(column);
/*  974 */     int nRows = getRowDimension();
/*  975 */     if (matrix.getRowDimension() != nRows || matrix.getColumnDimension() != 1)
/*      */     {
/*  977 */       throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), nRows, 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  983 */     int jBlock = column / 36;
/*  984 */     int jColumn = column - jBlock * 36;
/*  985 */     int jWidth = blockWidth(jBlock);
/*  986 */     int mBlockIndex = 0;
/*  987 */     int mIndex = 0;
/*  988 */     T[] mBlock = matrix.blocks[mBlockIndex];
/*  989 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/*  990 */       int iHeight = blockHeight(iBlock);
/*  991 */       T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/*  992 */       for (int i = 0; i < iHeight; i++) {
/*  993 */         if (mIndex >= mBlock.length) {
/*  994 */           mBlock = matrix.blocks[++mBlockIndex];
/*  995 */           mIndex = 0;
/*      */         } 
/*  997 */         block[i * jWidth + jColumn] = mBlock[mIndex++];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> getRowVector(int row) throws OutOfRangeException {
/* 1006 */     checkRowIndex(row);
/* 1007 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(getField(), this.columns);
/*      */ 
/*      */     
/* 1010 */     int iBlock = row / 36;
/* 1011 */     int iRow = row - iBlock * 36;
/* 1012 */     int outIndex = 0;
/* 1013 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1014 */       int jWidth = blockWidth(jBlock);
/* 1015 */       T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1016 */       System.arraycopy(block, iRow * jWidth, arrayOfFieldElement, outIndex, jWidth);
/* 1017 */       outIndex += jWidth;
/*      */     } 
/*      */     
/* 1020 */     return new ArrayFieldVector<T>(getField(), (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRowVector(int row, FieldVector<T> vector) throws MatrixDimensionMismatchException, OutOfRangeException {
/*      */     try {
/* 1028 */       setRow(row, ((ArrayFieldVector<T>)vector).getDataRef());
/* 1029 */     } catch (ClassCastException cce) {
/* 1030 */       super.setRowVector(row, vector);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> getColumnVector(int column) throws OutOfRangeException {
/* 1038 */     checkColumnIndex(column);
/* 1039 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(getField(), this.rows);
/*      */ 
/*      */     
/* 1042 */     int jBlock = column / 36;
/* 1043 */     int jColumn = column - jBlock * 36;
/* 1044 */     int jWidth = blockWidth(jBlock);
/* 1045 */     int outIndex = 0;
/* 1046 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1047 */       int iHeight = blockHeight(iBlock);
/* 1048 */       T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1049 */       for (int i = 0; i < iHeight; i++) {
/* 1050 */         arrayOfFieldElement[outIndex++] = (FieldElement)block[i * jWidth + jColumn];
/*      */       }
/*      */     } 
/*      */     
/* 1054 */     return new ArrayFieldVector<T>(getField(), (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnVector(int column, FieldVector<T> vector) throws OutOfRangeException, MatrixDimensionMismatchException {
/*      */     try {
/* 1062 */       setColumn(column, ((ArrayFieldVector<T>)vector).getDataRef());
/* 1063 */     } catch (ClassCastException cce) {
/* 1064 */       super.setColumnVector(column, vector);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T[] getRow(int row) throws OutOfRangeException {
/* 1071 */     checkRowIndex(row);
/* 1072 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(getField(), this.columns);
/*      */ 
/*      */     
/* 1075 */     int iBlock = row / 36;
/* 1076 */     int iRow = row - iBlock * 36;
/* 1077 */     int outIndex = 0;
/* 1078 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1079 */       int jWidth = blockWidth(jBlock);
/* 1080 */       T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1081 */       System.arraycopy(block, iRow * jWidth, arrayOfFieldElement, outIndex, jWidth);
/* 1082 */       outIndex += jWidth;
/*      */     } 
/*      */     
/* 1085 */     return (T[])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRow(int row, T[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
/* 1092 */     checkRowIndex(row);
/* 1093 */     int nCols = getColumnDimension();
/* 1094 */     if (array.length != nCols) {
/* 1095 */       throw new MatrixDimensionMismatchException(1, array.length, 1, nCols);
/*      */     }
/*      */ 
/*      */     
/* 1099 */     int iBlock = row / 36;
/* 1100 */     int iRow = row - iBlock * 36;
/* 1101 */     int outIndex = 0;
/* 1102 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1103 */       int jWidth = blockWidth(jBlock);
/* 1104 */       T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1105 */       System.arraycopy(array, outIndex, block, iRow * jWidth, jWidth);
/* 1106 */       outIndex += jWidth;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T[] getColumn(int column) throws OutOfRangeException {
/* 1113 */     checkColumnIndex(column);
/* 1114 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(getField(), this.rows);
/*      */ 
/*      */     
/* 1117 */     int jBlock = column / 36;
/* 1118 */     int jColumn = column - jBlock * 36;
/* 1119 */     int jWidth = blockWidth(jBlock);
/* 1120 */     int outIndex = 0;
/* 1121 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1122 */       int iHeight = blockHeight(iBlock);
/* 1123 */       T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1124 */       for (int i = 0; i < iHeight; i++) {
/* 1125 */         arrayOfFieldElement[outIndex++] = (FieldElement)block[i * jWidth + jColumn];
/*      */       }
/*      */     } 
/*      */     
/* 1129 */     return (T[])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumn(int column, T[] array) throws MatrixDimensionMismatchException, OutOfRangeException {
/* 1136 */     checkColumnIndex(column);
/* 1137 */     int nRows = getRowDimension();
/* 1138 */     if (array.length != nRows) {
/* 1139 */       throw new MatrixDimensionMismatchException(array.length, 1, nRows, 1);
/*      */     }
/*      */ 
/*      */     
/* 1143 */     int jBlock = column / 36;
/* 1144 */     int jColumn = column - jBlock * 36;
/* 1145 */     int jWidth = blockWidth(jBlock);
/* 1146 */     int outIndex = 0;
/* 1147 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1148 */       int iHeight = blockHeight(iBlock);
/* 1149 */       T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1150 */       for (int i = 0; i < iHeight; i++) {
/* 1151 */         block[i * jWidth + jColumn] = array[outIndex++];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getEntry(int row, int column) throws OutOfRangeException {
/* 1160 */     checkRowIndex(row);
/* 1161 */     checkColumnIndex(column);
/*      */     
/* 1163 */     int iBlock = row / 36;
/* 1164 */     int jBlock = column / 36;
/* 1165 */     int k = (row - iBlock * 36) * blockWidth(jBlock) + column - jBlock * 36;
/*      */ 
/*      */     
/* 1168 */     return this.blocks[iBlock * this.blockColumns + jBlock][k];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntry(int row, int column, T value) throws OutOfRangeException {
/* 1175 */     checkRowIndex(row);
/* 1176 */     checkColumnIndex(column);
/*      */     
/* 1178 */     int iBlock = row / 36;
/* 1179 */     int jBlock = column / 36;
/* 1180 */     int k = (row - iBlock * 36) * blockWidth(jBlock) + column - jBlock * 36;
/*      */ 
/*      */     
/* 1183 */     this.blocks[iBlock * this.blockColumns + jBlock][k] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToEntry(int row, int column, T increment) throws OutOfRangeException {
/* 1190 */     checkRowIndex(row);
/* 1191 */     checkColumnIndex(column);
/*      */     
/* 1193 */     int iBlock = row / 36;
/* 1194 */     int jBlock = column / 36;
/* 1195 */     int k = (row - iBlock * 36) * blockWidth(jBlock) + column - jBlock * 36;
/*      */     
/* 1197 */     T[] blockIJ = this.blocks[iBlock * this.blockColumns + jBlock];
/*      */     
/* 1199 */     blockIJ[k] = (T)blockIJ[k].add(increment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void multiplyEntry(int row, int column, T factor) throws OutOfRangeException {
/* 1206 */     checkRowIndex(row);
/* 1207 */     checkColumnIndex(column);
/*      */     
/* 1209 */     int iBlock = row / 36;
/* 1210 */     int jBlock = column / 36;
/* 1211 */     int k = (row - iBlock * 36) * blockWidth(jBlock) + column - jBlock * 36;
/*      */     
/* 1213 */     T[] blockIJ = this.blocks[iBlock * this.blockColumns + jBlock];
/*      */     
/* 1215 */     blockIJ[k] = (T)blockIJ[k].multiply(factor);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> transpose() {
/* 1221 */     int nRows = getRowDimension();
/* 1222 */     int nCols = getColumnDimension();
/* 1223 */     BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), nCols, nRows);
/*      */ 
/*      */     
/* 1226 */     int blockIndex = 0;
/* 1227 */     for (int iBlock = 0; iBlock < this.blockColumns; iBlock++) {
/* 1228 */       for (int jBlock = 0; jBlock < this.blockRows; jBlock++) {
/*      */ 
/*      */         
/* 1231 */         T[] outBlock = out.blocks[blockIndex];
/* 1232 */         T[] tBlock = this.blocks[jBlock * this.blockColumns + iBlock];
/* 1233 */         int pStart = iBlock * 36;
/* 1234 */         int pEnd = FastMath.min(pStart + 36, this.columns);
/* 1235 */         int qStart = jBlock * 36;
/* 1236 */         int qEnd = FastMath.min(qStart + 36, this.rows);
/* 1237 */         int k = 0;
/* 1238 */         for (int p = pStart; p < pEnd; p++) {
/* 1239 */           int lInc = pEnd - pStart;
/* 1240 */           int l = p - pStart;
/* 1241 */           for (int q = qStart; q < qEnd; q++) {
/* 1242 */             outBlock[k] = tBlock[l];
/* 1243 */             k++;
/* 1244 */             l += lInc;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1249 */         blockIndex++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1254 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowDimension() {
/* 1260 */     return this.rows;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnDimension() {
/* 1266 */     return this.columns;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T[] operate(T[] v) throws DimensionMismatchException {
/* 1272 */     if (v.length != this.columns) {
/* 1273 */       throw new DimensionMismatchException(v.length, this.columns);
/*      */     }
/* 1275 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(getField(), this.rows);
/* 1276 */     FieldElement fieldElement = (FieldElement)getField().getZero();
/*      */ 
/*      */     
/* 1279 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1280 */       int pStart = iBlock * 36;
/* 1281 */       int pEnd = FastMath.min(pStart + 36, this.rows);
/* 1282 */       for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1283 */         T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1284 */         int qStart = jBlock * 36;
/* 1285 */         int qEnd = FastMath.min(qStart + 36, this.columns);
/* 1286 */         int k = 0;
/* 1287 */         for (int p = pStart; p < pEnd; p++) {
/* 1288 */           FieldElement fieldElement1 = fieldElement;
/* 1289 */           int q = qStart;
/* 1290 */           while (q < qEnd - 3) {
/* 1291 */             fieldElement1 = (FieldElement)((FieldElement)((FieldElement)((FieldElement)fieldElement1.add(block[k].multiply(v[q]))).add(block[k + 1].multiply(v[q + 1]))).add(block[k + 2].multiply(v[q + 2]))).add(block[k + 3].multiply(v[q + 3]));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1296 */             k += 4;
/* 1297 */             q += 4;
/*      */           } 
/* 1299 */           while (q < qEnd) {
/* 1300 */             fieldElement1 = (FieldElement)fieldElement1.add(block[k++].multiply(v[q++]));
/*      */           }
/* 1302 */           arrayOfFieldElement[p] = (FieldElement)arrayOfFieldElement[p].add(fieldElement1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1307 */     return (T[])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T[] preMultiply(T[] v) throws DimensionMismatchException {
/* 1314 */     if (v.length != this.rows) {
/* 1315 */       throw new DimensionMismatchException(v.length, this.rows);
/*      */     }
/* 1317 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(getField(), this.columns);
/* 1318 */     FieldElement fieldElement = (FieldElement)getField().getZero();
/*      */ 
/*      */     
/* 1321 */     for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1322 */       int jWidth = blockWidth(jBlock);
/* 1323 */       int jWidth2 = jWidth + jWidth;
/* 1324 */       int jWidth3 = jWidth2 + jWidth;
/* 1325 */       int jWidth4 = jWidth3 + jWidth;
/* 1326 */       int qStart = jBlock * 36;
/* 1327 */       int qEnd = FastMath.min(qStart + 36, this.columns);
/* 1328 */       for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1329 */         T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1330 */         int pStart = iBlock * 36;
/* 1331 */         int pEnd = FastMath.min(pStart + 36, this.rows);
/* 1332 */         for (int q = qStart; q < qEnd; q++) {
/* 1333 */           int k = q - qStart;
/* 1334 */           FieldElement fieldElement1 = fieldElement;
/* 1335 */           int p = pStart;
/* 1336 */           while (p < pEnd - 3) {
/* 1337 */             fieldElement1 = (FieldElement)((FieldElement)((FieldElement)((FieldElement)fieldElement1.add(block[k].multiply(v[p]))).add(block[k + jWidth].multiply(v[p + 1]))).add(block[k + jWidth2].multiply(v[p + 2]))).add(block[k + jWidth3].multiply(v[p + 3]));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1342 */             k += jWidth4;
/* 1343 */             p += 4;
/*      */           } 
/* 1345 */           while (p < pEnd) {
/* 1346 */             fieldElement1 = (FieldElement)fieldElement1.add(block[k].multiply(v[p++]));
/* 1347 */             k += jWidth;
/*      */           } 
/* 1349 */           arrayOfFieldElement[q] = (FieldElement)arrayOfFieldElement[q].add(fieldElement1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1354 */     return (T[])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInRowOrder(FieldMatrixChangingVisitor<T> visitor) {
/* 1360 */     visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
/* 1361 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1362 */       int pStart = iBlock * 36;
/* 1363 */       int pEnd = FastMath.min(pStart + 36, this.rows);
/* 1364 */       for (int p = pStart; p < pEnd; p++) {
/* 1365 */         for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1366 */           int jWidth = blockWidth(jBlock);
/* 1367 */           int qStart = jBlock * 36;
/* 1368 */           int qEnd = FastMath.min(qStart + 36, this.columns);
/* 1369 */           T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1370 */           int k = (p - pStart) * jWidth;
/* 1371 */           for (int q = qStart; q < qEnd; q++) {
/* 1372 */             block[k] = visitor.visit(p, q, block[k]);
/* 1373 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1378 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInRowOrder(FieldMatrixPreservingVisitor<T> visitor) {
/* 1384 */     visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
/* 1385 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1386 */       int pStart = iBlock * 36;
/* 1387 */       int pEnd = FastMath.min(pStart + 36, this.rows);
/* 1388 */       for (int p = pStart; p < pEnd; p++) {
/* 1389 */         for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1390 */           int jWidth = blockWidth(jBlock);
/* 1391 */           int qStart = jBlock * 36;
/* 1392 */           int qEnd = FastMath.min(qStart + 36, this.columns);
/* 1393 */           T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1394 */           int k = (p - pStart) * jWidth;
/* 1395 */           for (int q = qStart; q < qEnd; q++) {
/* 1396 */             visitor.visit(p, q, block[k]);
/* 1397 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1402 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInRowOrder(FieldMatrixChangingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 1411 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/* 1412 */     visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
/* 1413 */     for (int iBlock = startRow / 36; iBlock < 1 + endRow / 36; iBlock++) {
/* 1414 */       int p0 = iBlock * 36;
/* 1415 */       int pStart = FastMath.max(startRow, p0);
/* 1416 */       int pEnd = FastMath.min((iBlock + 1) * 36, 1 + endRow);
/* 1417 */       for (int p = pStart; p < pEnd; p++) {
/* 1418 */         for (int jBlock = startColumn / 36; jBlock < 1 + endColumn / 36; jBlock++) {
/* 1419 */           int jWidth = blockWidth(jBlock);
/* 1420 */           int q0 = jBlock * 36;
/* 1421 */           int qStart = FastMath.max(startColumn, q0);
/* 1422 */           int qEnd = FastMath.min((jBlock + 1) * 36, 1 + endColumn);
/* 1423 */           T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1424 */           int k = (p - p0) * jWidth + qStart - q0;
/* 1425 */           for (int q = qStart; q < qEnd; q++) {
/* 1426 */             block[k] = visitor.visit(p, q, block[k]);
/* 1427 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1432 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInRowOrder(FieldMatrixPreservingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 1441 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/* 1442 */     visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
/* 1443 */     for (int iBlock = startRow / 36; iBlock < 1 + endRow / 36; iBlock++) {
/* 1444 */       int p0 = iBlock * 36;
/* 1445 */       int pStart = FastMath.max(startRow, p0);
/* 1446 */       int pEnd = FastMath.min((iBlock + 1) * 36, 1 + endRow);
/* 1447 */       for (int p = pStart; p < pEnd; p++) {
/* 1448 */         for (int jBlock = startColumn / 36; jBlock < 1 + endColumn / 36; jBlock++) {
/* 1449 */           int jWidth = blockWidth(jBlock);
/* 1450 */           int q0 = jBlock * 36;
/* 1451 */           int qStart = FastMath.max(startColumn, q0);
/* 1452 */           int qEnd = FastMath.min((jBlock + 1) * 36, 1 + endColumn);
/* 1453 */           T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1454 */           int k = (p - p0) * jWidth + qStart - q0;
/* 1455 */           for (int q = qStart; q < qEnd; q++) {
/* 1456 */             visitor.visit(p, q, block[k]);
/* 1457 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1462 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInOptimizedOrder(FieldMatrixChangingVisitor<T> visitor) {
/* 1468 */     visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
/* 1469 */     int blockIndex = 0;
/* 1470 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1471 */       int pStart = iBlock * 36;
/* 1472 */       int pEnd = FastMath.min(pStart + 36, this.rows);
/* 1473 */       for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1474 */         int qStart = jBlock * 36;
/* 1475 */         int qEnd = FastMath.min(qStart + 36, this.columns);
/* 1476 */         T[] block = this.blocks[blockIndex];
/* 1477 */         int k = 0;
/* 1478 */         for (int p = pStart; p < pEnd; p++) {
/* 1479 */           for (int q = qStart; q < qEnd; q++) {
/* 1480 */             block[k] = visitor.visit(p, q, block[k]);
/* 1481 */             k++;
/*      */           } 
/*      */         } 
/* 1484 */         blockIndex++;
/*      */       } 
/*      */     } 
/* 1487 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInOptimizedOrder(FieldMatrixPreservingVisitor<T> visitor) {
/* 1493 */     visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
/* 1494 */     int blockIndex = 0;
/* 1495 */     for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
/* 1496 */       int pStart = iBlock * 36;
/* 1497 */       int pEnd = FastMath.min(pStart + 36, this.rows);
/* 1498 */       for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
/* 1499 */         int qStart = jBlock * 36;
/* 1500 */         int qEnd = FastMath.min(qStart + 36, this.columns);
/* 1501 */         T[] block = this.blocks[blockIndex];
/* 1502 */         int k = 0;
/* 1503 */         for (int p = pStart; p < pEnd; p++) {
/* 1504 */           for (int q = qStart; q < qEnd; q++) {
/* 1505 */             visitor.visit(p, q, block[k]);
/* 1506 */             k++;
/*      */           } 
/*      */         } 
/* 1509 */         blockIndex++;
/*      */       } 
/*      */     } 
/* 1512 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInOptimizedOrder(FieldMatrixChangingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 1521 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/* 1522 */     visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
/* 1523 */     for (int iBlock = startRow / 36; iBlock < 1 + endRow / 36; iBlock++) {
/* 1524 */       int p0 = iBlock * 36;
/* 1525 */       int pStart = FastMath.max(startRow, p0);
/* 1526 */       int pEnd = FastMath.min((iBlock + 1) * 36, 1 + endRow);
/* 1527 */       for (int jBlock = startColumn / 36; jBlock < 1 + endColumn / 36; jBlock++) {
/* 1528 */         int jWidth = blockWidth(jBlock);
/* 1529 */         int q0 = jBlock * 36;
/* 1530 */         int qStart = FastMath.max(startColumn, q0);
/* 1531 */         int qEnd = FastMath.min((jBlock + 1) * 36, 1 + endColumn);
/* 1532 */         T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1533 */         for (int p = pStart; p < pEnd; p++) {
/* 1534 */           int k = (p - p0) * jWidth + qStart - q0;
/* 1535 */           for (int q = qStart; q < qEnd; q++) {
/* 1536 */             block[k] = visitor.visit(p, q, block[k]);
/* 1537 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1542 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInOptimizedOrder(FieldMatrixPreservingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 1551 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/* 1552 */     visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
/* 1553 */     for (int iBlock = startRow / 36; iBlock < 1 + endRow / 36; iBlock++) {
/* 1554 */       int p0 = iBlock * 36;
/* 1555 */       int pStart = FastMath.max(startRow, p0);
/* 1556 */       int pEnd = FastMath.min((iBlock + 1) * 36, 1 + endRow);
/* 1557 */       for (int jBlock = startColumn / 36; jBlock < 1 + endColumn / 36; jBlock++) {
/* 1558 */         int jWidth = blockWidth(jBlock);
/* 1559 */         int q0 = jBlock * 36;
/* 1560 */         int qStart = FastMath.max(startColumn, q0);
/* 1561 */         int qEnd = FastMath.min((jBlock + 1) * 36, 1 + endColumn);
/* 1562 */         T[] block = this.blocks[iBlock * this.blockColumns + jBlock];
/* 1563 */         for (int p = pStart; p < pEnd; p++) {
/* 1564 */           int k = (p - p0) * jWidth + qStart - q0;
/* 1565 */           for (int q = qStart; q < qEnd; q++) {
/* 1566 */             visitor.visit(p, q, block[k]);
/* 1567 */             k++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1572 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int blockHeight(int blockRow) {
/* 1581 */     return (blockRow == this.blockRows - 1) ? (this.rows - blockRow * 36) : 36;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int blockWidth(int blockColumn) {
/* 1590 */     return (blockColumn == this.blockColumns - 1) ? (this.columns - blockColumn * 36) : 36;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/BlockFieldMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */