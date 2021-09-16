/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCellBase
/*     */   extends AbstractArray
/*     */   implements Cell
/*     */ {
/*     */   protected AbstractCellBase(int[] dims) {
/*  32 */     super(dims);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T extends Array> T get(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Cell set(int paramInt, Array paramArray);
/*     */ 
/*     */ 
/*     */   
/*     */   public MatlabType getType() {
/*  47 */     return MatlabType.Cell;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix getMatrix(int index) {
/*  52 */     return get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix getMatrix(int row, int col) {
/*  57 */     return get(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix getMatrix(int[] indices) {
/*  62 */     return get(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public Sparse getSparse(int index) {
/*  67 */     return get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Sparse getSparse(int row, int col) {
/*  72 */     return get(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Sparse getSparse(int[] indices) {
/*  77 */     return get(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public Char getChar(int index) {
/*  82 */     return get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Char getChar(int row, int col) {
/*  87 */     return get(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Char getChar(int[] indices) {
/*  92 */     return get(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct getStruct(int index) {
/*  97 */     return get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct getStruct(int row, int col) {
/* 102 */     return get(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct getStruct(int[] indices) {
/* 107 */     return get(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public Cell getCell(int index) {
/* 112 */     return get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Cell getCell(int row, int col) {
/* 117 */     return get(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Cell getCell(int[] indices) {
/* 122 */     return get(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Array> T get(int row, int col) {
/* 127 */     return get(getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Array> T get(int[] indices) {
/* 132 */     return get(getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public Cell set(int row, int col, Array value) {
/* 137 */     set(getColumnMajorIndex(row, col), value);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Cell set(int[] indices, Array value) {
/* 143 */     set(getColumnMajorIndex(indices), value);
/* 144 */     return this;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractCellBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */