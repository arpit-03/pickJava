/*    */ package us.hebi.matlab.mat.types;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ import us.hebi.matlab.mat.util.Preconditions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractCell
/*    */   extends AbstractCellBase
/*    */ {
/*    */   protected final Array[] contents;
/*    */   
/*    */   protected AbstractCell(int[] dims, Array[] values) {
/* 40 */     super(dims);
/* 41 */     Preconditions.checkArgument((values.length == getNumElements()), "invalid length");
/* 42 */     this.contents = values;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T extends Array> T get(int index) {
/* 48 */     return (T)this.contents[index];
/*    */   }
/*    */ 
/*    */   
/*    */   public Cell set(int index, Array value) {
/* 53 */     this.contents[index] = value;
/* 54 */     return this;
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/*    */     byte b;
/*    */     int i;
/*    */     Array[] arrayOfArray;
/* 61 */     for (i = (arrayOfArray = this.contents).length, b = 0; b < i; ) { Array array = arrayOfArray[b];
/* 62 */       array.close(); b++; }
/*    */     
/* 64 */     Arrays.fill((Object[])this.contents, getEmptyValue());
/*    */   }
/*    */   
/*    */   protected abstract Array getEmptyValue();
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractCell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */