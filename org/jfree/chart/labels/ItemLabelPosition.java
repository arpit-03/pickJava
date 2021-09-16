/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.ui.TextAnchor;
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
/*     */ public class ItemLabelPosition
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5845390630157034499L;
/*     */   private ItemLabelAnchor itemLabelAnchor;
/*     */   private TextAnchor textAnchor;
/*     */   private TextAnchor rotationAnchor;
/*     */   private double angle;
/*     */   
/*     */   public ItemLabelPosition() {
/*  77 */     this(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER, TextAnchor.CENTER, 0.0D);
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
/*     */   public ItemLabelPosition(ItemLabelAnchor itemLabelAnchor, TextAnchor textAnchor) {
/*  90 */     this(itemLabelAnchor, textAnchor, TextAnchor.CENTER, 0.0D);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemLabelPosition(ItemLabelAnchor itemLabelAnchor, TextAnchor textAnchor, TextAnchor rotationAnchor, double angle) {
/* 109 */     ParamChecks.nullNotPermitted(itemLabelAnchor, "itemLabelAnchor");
/* 110 */     ParamChecks.nullNotPermitted(textAnchor, "textAnchor");
/* 111 */     ParamChecks.nullNotPermitted(rotationAnchor, "rotationAnchor");
/* 112 */     this.itemLabelAnchor = itemLabelAnchor;
/* 113 */     this.textAnchor = textAnchor;
/* 114 */     this.rotationAnchor = rotationAnchor;
/* 115 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemLabelAnchor getItemLabelAnchor() {
/* 124 */     return this.itemLabelAnchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextAnchor getTextAnchor() {
/* 133 */     return this.textAnchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextAnchor getRotationAnchor() {
/* 142 */     return this.rotationAnchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAngle() {
/* 151 */     return this.angle;
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
/*     */   public boolean equals(Object obj) {
/* 163 */     if (obj == this) {
/* 164 */       return true;
/*     */     }
/* 166 */     if (!(obj instanceof ItemLabelPosition)) {
/* 167 */       return false;
/*     */     }
/* 169 */     ItemLabelPosition that = (ItemLabelPosition)obj;
/* 170 */     if (!this.itemLabelAnchor.equals(that.itemLabelAnchor)) {
/* 171 */       return false;
/*     */     }
/* 173 */     if (!this.textAnchor.equals(that.textAnchor)) {
/* 174 */       return false;
/*     */     }
/* 176 */     if (!this.rotationAnchor.equals(that.rotationAnchor)) {
/* 177 */       return false;
/*     */     }
/* 179 */     if (this.angle != that.angle) {
/* 180 */       return false;
/*     */     }
/* 182 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/ItemLabelPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */