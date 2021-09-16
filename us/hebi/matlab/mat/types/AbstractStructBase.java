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
/*     */ public abstract class AbstractStructBase
/*     */   extends AbstractArray
/*     */   implements Struct
/*     */ {
/*     */   protected AbstractStructBase(int[] dims) {
/*  32 */     super(dims);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T extends Array> T get(String paramString, int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Struct set(String paramString, int paramInt, Array paramArray);
/*     */ 
/*     */ 
/*     */   
/*     */   public MatlabType getType() {
/*  47 */     return MatlabType.Structure;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix getMatrix(String field) {
/*  52 */     return get(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public Sparse getSparse(String field) {
/*  57 */     return get(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public Char getChar(String field) {
/*  62 */     return get(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct getStruct(String field) {
/*  67 */     return get(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectStruct getObject(String field) {
/*  72 */     return get(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public Cell getCell(String field) {
/*  77 */     return get(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Array> T get(String field) {
/*  82 */     if (getNumElements() != 1)
/*  83 */       throw new IllegalStateException("Scalar structure required for this request."); 
/*  84 */     return get(field, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct set(String field, Array value) {
/*  89 */     if (getNumElements() != 1)
/*  90 */       throw new IllegalStateException("Scalar structure required for this assignment."); 
/*  91 */     return set(field, 0, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix getMatrix(String field, int index) {
/*  96 */     return get(field, index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix getMatrix(String field, int row, int col) {
/* 101 */     return get(field, getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix getMatrix(String field, int[] indices) {
/* 106 */     return get(field, getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public Sparse getSparse(String field, int index) {
/* 111 */     return get(field, index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Sparse getSparse(String field, int row, int col) {
/* 116 */     return get(field, getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Sparse getSparse(String field, int[] indices) {
/* 121 */     return get(field, getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public Char getChar(String field, int index) {
/* 126 */     return get(field, index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Char getChar(String field, int row, int col) {
/* 131 */     return get(field, getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Char getChar(String field, int[] indices) {
/* 136 */     return get(field, getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct getStruct(String field, int index) {
/* 141 */     return get(field, index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct getStruct(String field, int row, int col) {
/* 146 */     return get(field, getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct getStruct(String field, int[] indices) {
/* 151 */     return get(field, getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectStruct getObject(String field, int index) {
/* 156 */     return get(field, index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectStruct getObject(String field, int row, int col) {
/* 161 */     return get(field, getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectStruct getObject(String field, int[] indices) {
/* 166 */     return get(field, getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public Cell getCell(String field, int index) {
/* 171 */     return get(field, index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Cell getCell(String field, int row, int col) {
/* 176 */     return get(field, getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Cell getCell(String field, int[] indices) {
/* 181 */     return get(field, getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Array> T get(String field, int row, int col) {
/* 186 */     return get(field, getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Array> T get(String field, int[] indices) {
/* 191 */     return get(field, getColumnMajorIndex(indices));
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct set(String field, int row, int col, Array value) {
/* 196 */     return get(field, getColumnMajorIndex(row, col));
/*     */   }
/*     */ 
/*     */   
/*     */   public Struct set(String field, int[] indices, Array value) {
/* 201 */     return get(field, getColumnMajorIndex(indices));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractStructBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */