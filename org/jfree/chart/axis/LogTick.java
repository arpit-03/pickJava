/*    */ package org.jfree.chart.axis;
/*    */ 
/*    */ import java.text.AttributedString;
/*    */ import org.jfree.ui.TextAnchor;
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
/*    */ public class LogTick
/*    */   extends ValueTick
/*    */ {
/*    */   AttributedString attributedLabel;
/*    */   
/*    */   public LogTick(TickType type, double value, AttributedString label, TextAnchor textAnchor) {
/* 65 */     super(type, value, null, textAnchor, textAnchor, 0.0D);
/* 66 */     this.attributedLabel = label;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AttributedString getAttributedLabel() {
/* 76 */     return this.attributedLabel;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/LogTick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */