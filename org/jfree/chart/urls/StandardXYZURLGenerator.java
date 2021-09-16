/*    */ package org.jfree.chart.urls;
/*    */ 
/*    */ import org.jfree.data.xy.XYDataset;
/*    */ import org.jfree.data.xy.XYZDataset;
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
/*    */ public class StandardXYZURLGenerator
/*    */   extends StandardXYURLGenerator
/*    */   implements XYZURLGenerator
/*    */ {
/*    */   public String generateURL(XYZDataset dataset, int series, int item) {
/* 62 */     return generateURL((XYDataset)dataset, series, item);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/urls/StandardXYZURLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */