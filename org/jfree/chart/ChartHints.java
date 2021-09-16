/*     */ package org.jfree.chart;
/*     */ 
/*     */ import java.awt.RenderingHints;
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
/*     */ public final class ChartHints
/*     */ {
/*  65 */   public static final Key KEY_BEGIN_ELEMENT = new Key(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final Key KEY_END_ELEMENT = new Key(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Key
/*     */     extends RenderingHints.Key
/*     */   {
/*     */     public Key(int privateKey) {
/*  84 */       super(privateKey);
/*     */     }
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
/*     */     public boolean isCompatibleValue(Object val) {
/*  97 */       switch (intKey()) {
/*     */         case 0:
/*  99 */           return (val == null || val instanceof String || val instanceof java.util.Map);
/*     */         
/*     */         case 1:
/* 102 */           return (val == null || val instanceof Object);
/*     */       } 
/* 104 */       throw new RuntimeException("Not possible!");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/ChartHints.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */