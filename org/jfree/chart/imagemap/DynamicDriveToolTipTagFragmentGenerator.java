/*    */ package org.jfree.chart.imagemap;
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
/*    */ public class DynamicDriveToolTipTagFragmentGenerator
/*    */   implements ToolTipTagFragmentGenerator
/*    */ {
/* 54 */   protected String title = "";
/*    */ 
/*    */   
/* 57 */   protected int style = 1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DynamicDriveToolTipTagFragmentGenerator() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DynamicDriveToolTipTagFragmentGenerator(String title, int style) {
/* 75 */     this.title = title;
/* 76 */     this.style = style;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String generateToolTipFragment(String toolTipText) {
/* 88 */     return " onMouseOver=\"return stm(['" + 
/* 89 */       ImageMapUtilities.javascriptEscape(this.title) + "','" + 
/* 90 */       ImageMapUtilities.javascriptEscape(toolTipText) + "'],Style[" + this.style + "]);\"" + " onMouseOut=\"return htm();\"";
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/imagemap/DynamicDriveToolTipTagFragmentGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */