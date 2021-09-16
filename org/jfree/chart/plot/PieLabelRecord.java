/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.text.TextBox;
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
/*     */ public class PieLabelRecord
/*     */   implements Comparable, Serializable
/*     */ {
/*     */   private Comparable key;
/*     */   private double angle;
/*     */   private double baseY;
/*     */   private double allocatedY;
/*     */   private TextBox label;
/*     */   private double labelHeight;
/*     */   private double gap;
/*     */   private double linkPercent;
/*     */   
/*     */   public PieLabelRecord(Comparable key, double angle, double baseY, TextBox label, double labelHeight, double gap, double linkPercent) {
/*  93 */     this.key = key;
/*  94 */     this.angle = angle;
/*  95 */     this.baseY = baseY;
/*  96 */     this.allocatedY = baseY;
/*  97 */     this.label = label;
/*  98 */     this.labelHeight = labelHeight;
/*  99 */     this.gap = gap;
/* 100 */     this.linkPercent = linkPercent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBaseY() {
/* 110 */     return this.baseY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseY(double base) {
/* 119 */     this.baseY = base;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLowerY() {
/* 128 */     return this.allocatedY - this.labelHeight / 2.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getUpperY() {
/* 137 */     return this.allocatedY + this.labelHeight / 2.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAngle() {
/* 146 */     return this.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getKey() {
/* 155 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextBox getLabel() {
/* 164 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLabelHeight() {
/* 174 */     return this.labelHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAllocatedY() {
/* 183 */     return this.allocatedY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllocatedY(double y) {
/* 192 */     this.allocatedY = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGap() {
/* 201 */     return this.gap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLinkPercent() {
/* 210 */     return this.linkPercent;
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
/*     */   public int compareTo(Object obj) {
/* 222 */     int result = 0;
/* 223 */     if (obj instanceof PieLabelRecord) {
/* 224 */       PieLabelRecord plr = (PieLabelRecord)obj;
/* 225 */       if (this.baseY < plr.baseY) {
/* 226 */         result = -1;
/*     */       }
/* 228 */       else if (this.baseY > plr.baseY) {
/* 229 */         result = 1;
/*     */       } 
/*     */     } 
/* 232 */     return result;
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
/* 244 */     if (obj == this) {
/* 245 */       return true;
/*     */     }
/* 247 */     if (!(obj instanceof PieLabelRecord)) {
/* 248 */       return false;
/*     */     }
/* 250 */     PieLabelRecord that = (PieLabelRecord)obj;
/* 251 */     if (!this.key.equals(that.key)) {
/* 252 */       return false;
/*     */     }
/* 254 */     if (this.angle != that.angle) {
/* 255 */       return false;
/*     */     }
/* 257 */     if (this.gap != that.gap) {
/* 258 */       return false;
/*     */     }
/* 260 */     if (this.allocatedY != that.allocatedY) {
/* 261 */       return false;
/*     */     }
/* 263 */     if (this.baseY != that.baseY) {
/* 264 */       return false;
/*     */     }
/* 266 */     if (this.labelHeight != that.labelHeight) {
/* 267 */       return false;
/*     */     }
/* 269 */     if (this.linkPercent != that.linkPercent) {
/* 270 */       return false;
/*     */     }
/* 272 */     if (!this.label.equals(that.label)) {
/* 273 */       return false;
/*     */     }
/* 275 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 285 */     return this.baseY + ", " + this.key.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/PieLabelRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */