/*    */ package org.la4j.matrix;
/*    */ 
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import org.la4j.Matrix;
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
/*    */ public abstract class MatrixFactory<T extends Matrix>
/*    */ {
/* 34 */   public final Class<T> outputClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
/*    */   
/*    */   public abstract T apply(int paramInt1, int paramInt2);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/matrix/MatrixFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */