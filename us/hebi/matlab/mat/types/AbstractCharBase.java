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
/*     */ public abstract class AbstractCharBase
/*     */   extends AbstractArray
/*     */   implements Char
/*     */ {
/*     */   protected final StringBuilder builder;
/*     */   
/*     */   protected AbstractCharBase(int[] dims) {
/*  30 */     super(dims);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     this.builder = new StringBuilder();
/*     */   }
/*     */   
/*     */   public abstract CharSequence asCharSequence();
/*     */   
/*     */   public abstract char getChar(int paramInt);
/*     */   
/*     */   public abstract void setChar(int paramInt, char paramChar);
/*     */   
/*     */   public MatlabType getType() {
/*     */     return MatlabType.Character;
/*     */   }
/*     */   
/*     */   public String getString() {
/*     */     if (getNumRows() != 1)
/*     */       throw new IllegalStateException("Char is not a single row char string"); 
/*     */     return getRow(0);
/*     */   }
/*     */   
/*     */   public String getRow(int row) {
/*     */     checkNumDimensions(2);
/*     */     int numCols = getNumCols();
/*     */     synchronized (this.builder) {
/*     */       this.builder.ensureCapacity(numCols);
/*     */       this.builder.setLength(0);
/*     */       for (int col = 0; col < numCols; col++) {
/*     */         char c = getChar(row, col);
/*     */         if (c == '\000')
/*     */           break; 
/*     */         this.builder.append(c);
/*     */       } 
/*     */       return this.builder.toString();
/*     */     } 
/*     */   }
/*     */   
/*     */   public char getChar(int row, int col) {
/*     */     return getChar(getColumnMajorIndex(row, col));
/*     */   }
/*     */   
/*     */   public char getChar(int[] indices) {
/*     */     return getChar(getColumnMajorIndex(indices));
/*     */   }
/*     */   
/*     */   public void setChar(int row, int col, char value) {
/*     */     setChar(getColumnMajorIndex(row, col), value);
/*     */   }
/*     */   
/*     */   public void setChar(int[] indices, char value) {
/*     */     setChar(getColumnMajorIndex(indices), value);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractCharBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */