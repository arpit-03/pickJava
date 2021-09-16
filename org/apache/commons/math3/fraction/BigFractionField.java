/*    */ package org.apache.commons.math3.fraction;
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
/*    */ public class BigFractionField
/*    */   implements Field<BigFraction>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -1699294557189741703L;
/*    */   
/*    */   private BigFractionField() {}
/*    */   
/*    */   public static BigFractionField getInstance() {
/* 47 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public BigFraction getOne() {
/* 52 */     return BigFraction.ONE;
/*    */   }
/*    */ 
/*    */   
/*    */   public BigFraction getZero() {
/* 57 */     return BigFraction.ZERO;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends FieldElement<BigFraction>> getRuntimeClass() {
/* 62 */     return (Class)BigFraction.class;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class LazyHolder
/*    */   {
/* 70 */     private static final BigFractionField INSTANCE = new BigFractionField();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Object readResolve() {
/* 79 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fraction/BigFractionField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */