/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.util.Collections;
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
/*     */ public class PieLabelDistributor
/*     */   extends AbstractPieLabelDistributor
/*     */ {
/*  55 */   private double minGap = 4.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PieLabelDistributor(int labelCount) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void distributeLabels(double minY, double height) {
/*  74 */     sort();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     if (isOverlap()) {
/*  80 */       adjustDownwards(minY, height);
/*     */     }
/*     */     
/*  83 */     if (isOverlap()) {
/*  84 */       adjustUpwards(minY, height);
/*     */     }
/*     */     
/*  87 */     if (isOverlap()) {
/*  88 */       spreadEvenly(minY, height);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isOverlap() {
/*  99 */     double y = 0.0D;
/* 100 */     for (int i = 0; i < this.labels.size(); i++) {
/* 101 */       PieLabelRecord plr = getPieLabelRecord(i);
/* 102 */       if (y > plr.getLowerY()) {
/* 103 */         return true;
/*     */       }
/* 105 */       y = plr.getUpperY();
/*     */     } 
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void adjustInwards() {
/* 115 */     int lower = 0;
/* 116 */     int upper = this.labels.size() - 1;
/* 117 */     while (upper > lower) {
/* 118 */       if (lower < upper - 1) {
/* 119 */         PieLabelRecord r0 = getPieLabelRecord(lower);
/* 120 */         PieLabelRecord r1 = getPieLabelRecord(lower + 1);
/* 121 */         if (r1.getLowerY() < r0.getUpperY()) {
/* 122 */           double adjust = r0.getUpperY() - r1.getLowerY() + this.minGap;
/*     */           
/* 124 */           r1.setAllocatedY(r1.getAllocatedY() + adjust);
/*     */         } 
/*     */       } 
/* 127 */       PieLabelRecord r2 = getPieLabelRecord(upper - 1);
/* 128 */       PieLabelRecord r3 = getPieLabelRecord(upper);
/* 129 */       if (r2.getUpperY() > r3.getLowerY()) {
/* 130 */         double adjust = r2.getUpperY() - r3.getLowerY() + this.minGap;
/* 131 */         r3.setAllocatedY(r3.getAllocatedY() + adjust);
/*     */       } 
/* 133 */       lower++;
/* 134 */       upper--;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void adjustDownwards(double minY, double height) {
/* 146 */     for (int i = 0; i < this.labels.size() - 1; i++) {
/* 147 */       PieLabelRecord record0 = getPieLabelRecord(i);
/* 148 */       PieLabelRecord record1 = getPieLabelRecord(i + 1);
/* 149 */       if (record1.getLowerY() < record0.getUpperY()) {
/* 150 */         record1.setAllocatedY(Math.min(minY + height - record1
/* 151 */               .getLabelHeight() / 2.0D, record0
/* 152 */               .getUpperY() + this.minGap + record1
/* 153 */               .getLabelHeight() / 2.0D));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void adjustUpwards(double minY, double height) {
/* 166 */     for (int i = this.labels.size() - 1; i > 0; i--) {
/* 167 */       PieLabelRecord record0 = getPieLabelRecord(i);
/* 168 */       PieLabelRecord record1 = getPieLabelRecord(i - 1);
/* 169 */       if (record1.getUpperY() > record0.getLowerY()) {
/* 170 */         record1.setAllocatedY(Math.max(minY + record1
/* 171 */               .getLabelHeight() / 2.0D, record0.getLowerY() - this.minGap - record1
/* 172 */               .getLabelHeight() / 2.0D));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void spreadEvenly(double minY, double height) {
/* 185 */     double y = minY;
/* 186 */     double sumOfLabelHeights = 0.0D;
/* 187 */     for (int i = 0; i < this.labels.size(); i++) {
/* 188 */       sumOfLabelHeights += getPieLabelRecord(i).getLabelHeight();
/*     */     }
/* 190 */     double gap = height - sumOfLabelHeights;
/* 191 */     if (this.labels.size() > 1) {
/* 192 */       gap /= (this.labels.size() - 1);
/*     */     }
/* 194 */     for (int j = 0; j < this.labels.size(); j++) {
/* 195 */       PieLabelRecord record = getPieLabelRecord(j);
/* 196 */       y += record.getLabelHeight() / 2.0D;
/* 197 */       record.setAllocatedY(y);
/* 198 */       y = y + record.getLabelHeight() / 2.0D + gap;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/* 206 */     Collections.sort(this.labels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 217 */     StringBuilder result = new StringBuilder();
/* 218 */     for (int i = 0; i < this.labels.size(); i++) {
/* 219 */       result.append(getPieLabelRecord(i).toString()).append("\n");
/*     */     }
/* 221 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/PieLabelDistributor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */