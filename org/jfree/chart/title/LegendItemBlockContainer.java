/*     */ package org.jfree.chart.title;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.chart.block.Arrangement;
/*     */ import org.jfree.chart.block.BlockContainer;
/*     */ import org.jfree.chart.block.BlockResult;
/*     */ import org.jfree.chart.block.EntityBlockParams;
/*     */ import org.jfree.chart.entity.ChartEntity;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.entity.LegendItemEntity;
/*     */ import org.jfree.chart.entity.StandardEntityCollection;
/*     */ import org.jfree.data.general.Dataset;
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
/*     */ public class LegendItemBlockContainer
/*     */   extends BlockContainer
/*     */ {
/*     */   private Dataset dataset;
/*     */   private Comparable seriesKey;
/*     */   private int datasetIndex;
/*     */   private int series;
/*     */   private String toolTipText;
/*     */   private String urlText;
/*     */   
/*     */   public LegendItemBlockContainer(Arrangement arrangement, int datasetIndex, int series) {
/* 103 */     super(arrangement);
/* 104 */     this.datasetIndex = datasetIndex;
/* 105 */     this.series = series;
/*     */   }
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
/*     */   public LegendItemBlockContainer(Arrangement arrangement, Dataset dataset, Comparable seriesKey) {
/* 119 */     super(arrangement);
/* 120 */     this.dataset = dataset;
/* 121 */     this.seriesKey = seriesKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dataset getDataset() {
/* 132 */     return this.dataset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getSeriesKey() {
/* 143 */     return this.seriesKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDatasetIndex() {
/* 154 */     return this.datasetIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesIndex() {
/* 163 */     return this.series;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolTipText() {
/* 174 */     return this.toolTipText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setToolTipText(String text) {
/* 185 */     this.toolTipText = text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURLText() {
/* 196 */     return this.urlText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURLText(String text) {
/* 207 */     this.urlText = text;
/*     */   }
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
/*     */   public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
/* 223 */     super.draw(g2, area, null);
/*     */     
/* 225 */     BlockResult r = new BlockResult();
/* 226 */     if (params instanceof EntityBlockParams) {
/* 227 */       EntityBlockParams ebp = (EntityBlockParams)params;
/* 228 */       if (ebp.getGenerateEntities()) {
/* 229 */         StandardEntityCollection standardEntityCollection = new StandardEntityCollection();
/*     */         
/* 231 */         LegendItemEntity entity = new LegendItemEntity((Shape)area.clone());
/* 232 */         entity.setSeriesIndex(this.series);
/* 233 */         entity.setSeriesKey(this.seriesKey);
/* 234 */         entity.setDataset(this.dataset);
/* 235 */         entity.setToolTipText(getToolTipText());
/* 236 */         entity.setURLText(getURLText());
/* 237 */         standardEntityCollection.add((ChartEntity)entity);
/* 238 */         r.setEntityCollection((EntityCollection)standardEntityCollection);
/*     */       } 
/*     */     } 
/* 241 */     return r;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/title/LegendItemBlockContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */