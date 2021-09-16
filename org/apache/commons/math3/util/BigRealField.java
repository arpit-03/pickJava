/*    */ package org.apache.commons.math3.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.Field;
/*    */ import org.apache.commons.math3.FieldElement;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BigRealField
/*    */   implements Field<BigReal>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4756431066541037559L;
/*    */   
/*    */   private BigRealField() {}
/*    */   
/*    */   public static BigRealField getInstance() {
/* 47 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public BigReal getOne() {
/* 52 */     return BigReal.ONE;
/*    */   }
/*    */ 
/*    */   
/*    */   public BigReal getZero() {
/* 57 */     return BigReal.ZERO;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends FieldElement<BigReal>> getRuntimeClass() {
/* 62 */     return (Class)BigReal.class;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class LazyHolder
/*    */   {
/* 71 */     private static final BigRealField INSTANCE = new BigRealField();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Object readResolve() {
/* 80 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/BigRealField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */