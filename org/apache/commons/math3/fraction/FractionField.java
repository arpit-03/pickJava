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
/*    */ public class FractionField
/*    */   implements Field<Fraction>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -1257768487499119313L;
/*    */   
/*    */   private FractionField() {}
/*    */   
/*    */   public static FractionField getInstance() {
/* 47 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Fraction getOne() {
/* 52 */     return Fraction.ONE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Fraction getZero() {
/* 57 */     return Fraction.ZERO;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends FieldElement<Fraction>> getRuntimeClass() {
/* 62 */     return (Class)Fraction.class;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class LazyHolder
/*    */   {
/* 70 */     private static final FractionField INSTANCE = new FractionField();
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


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fraction/FractionField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */