/*    */ package org.apache.commons.math3.util;
/*    */ 
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
/*    */ public class Decimal64Field
/*    */   implements Field<Decimal64>
/*    */ {
/* 31 */   private static final Decimal64Field INSTANCE = new Decimal64Field();
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
/*    */   public static final Decimal64Field getInstance() {
/* 44 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Decimal64 getZero() {
/* 49 */     return Decimal64.ZERO;
/*    */   }
/*    */ 
/*    */   
/*    */   public Decimal64 getOne() {
/* 54 */     return Decimal64.ONE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends FieldElement<Decimal64>> getRuntimeClass() {
/* 59 */     return (Class)Decimal64.class;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/Decimal64Field.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */