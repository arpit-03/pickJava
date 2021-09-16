/*    */ package org.la4j.vector;
/*    */ 
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import org.la4j.Vector;
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
/*    */ public abstract class VectorFactory<T extends Vector>
/*    */ {
/* 34 */   public final Class<T> outputClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
/*    */   
/*    */   public abstract T apply(int paramInt);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/vector/VectorFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */