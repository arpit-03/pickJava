/*    */ package us.hebi.matlab.mat.format;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import us.hebi.matlab.mat.types.Array;
/*    */ import us.hebi.matlab.mat.types.Cell;
/*    */ import us.hebi.matlab.mat.types.JavaObject;
/*    */ import us.hebi.matlab.mat.types.Matrix;
/*    */ import us.hebi.matlab.mat.types.Struct;
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
/*    */ class MatJavaObject
/*    */   extends MatOpaque
/*    */   implements JavaObject
/*    */ {
/*    */   MatJavaObject(String className, Array content) {
/* 36 */     super("java", className, content);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Matrix getSerializedData() {
/* 46 */     Array content = getContent();
/*    */ 
/*    */     
/* 49 */     if (content instanceof Matrix) {
/* 50 */       return (Matrix)content;
/*    */     }
/*    */     
/* 53 */     if (content instanceof Struct) {
/* 54 */       return (Matrix)((Struct)content).get("Values");
/*    */     }
/*    */ 
/*    */     
/* 58 */     if (content instanceof Cell) {
/* 59 */       Cell cells = (Cell)content;
/* 60 */       for (int i = 0; i < cells.getNumElements(); i++) {
/* 61 */         Array array = cells.get(i);
/* 62 */         if (array instanceof Matrix) {
/* 63 */           return (Matrix)array;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 68 */     String msg = String.format("Unexpected byte storage. Found: %s", new Object[] { content });
/* 69 */     throw new IllegalStateException(msg);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object instantiateObject() throws Exception {
/* 75 */     ByteBuffer buffer = Mat5.exportBytes(getSerializedData());
/* 76 */     byte[] bytes = new byte[buffer.remaining()];
/* 77 */     buffer.get(bytes);
/*    */ 
/*    */     
/* 80 */     ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
/*    */     try {
/* 82 */       return ois.readObject();
/*    */     } finally {
/* 84 */       ois.close();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/MatJavaObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */