/*    */ package org.jfree.chart.editor;
/*    */ 
/*    */ import org.jfree.chart.JFreeChart;
/*    */ import org.jfree.chart.util.ParamChecks;
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
/*    */ public class ChartEditorManager
/*    */ {
/* 56 */   static ChartEditorFactory factory = new DefaultChartEditorFactory();
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
/*    */   public static ChartEditorFactory getChartEditorFactory() {
/* 71 */     return factory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setChartEditorFactory(ChartEditorFactory f) {
/* 80 */     ParamChecks.nullNotPermitted(f, "f");
/* 81 */     factory = f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ChartEditor getChartEditor(JFreeChart chart) {
/* 92 */     return factory.createEditor(chart);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/editor/ChartEditorManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */