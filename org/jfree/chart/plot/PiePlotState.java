/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.chart.renderer.RendererState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PiePlotState
/*     */   extends RendererState
/*     */ {
/*     */   private int passesRequired;
/*     */   private double total;
/*     */   private double latestAngle;
/*     */   private Rectangle2D explodedPieArea;
/*     */   private Rectangle2D pieArea;
/*     */   private double pieCenterX;
/*     */   private double pieCenterY;
/*     */   private double pieHRadius;
/*     */   private double pieWRadius;
/*     */   private Rectangle2D linkArea;
/*     */   
/*     */   public PiePlotState(PlotRenderingInfo info) {
/*  89 */     super(info);
/*  90 */     this.passesRequired = 1;
/*  91 */     this.total = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPassesRequired() {
/* 100 */     return this.passesRequired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPassesRequired(int passes) {
/* 109 */     this.passesRequired = passes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTotal() {
/* 118 */     return this.total;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTotal(double total) {
/* 127 */     this.total = total;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLatestAngle() {
/* 136 */     return this.latestAngle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLatestAngle(double angle) {
/* 145 */     this.latestAngle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D getPieArea() {
/* 154 */     return this.pieArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPieArea(Rectangle2D area) {
/* 163 */     this.pieArea = area;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D getExplodedPieArea() {
/* 172 */     return this.explodedPieArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExplodedPieArea(Rectangle2D area) {
/* 181 */     this.explodedPieArea = area;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPieCenterX() {
/* 190 */     return this.pieCenterX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPieCenterX(double x) {
/* 199 */     this.pieCenterX = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPieCenterY() {
/* 210 */     return this.pieCenterY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPieCenterY(double y) {
/* 220 */     this.pieCenterY = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D getLinkArea() {
/* 230 */     return this.linkArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkArea(Rectangle2D area) {
/* 240 */     this.linkArea = area;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPieHRadius() {
/* 249 */     return this.pieHRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPieHRadius(double radius) {
/* 258 */     this.pieHRadius = radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPieWRadius() {
/* 267 */     return this.pieWRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPieWRadius(double radius) {
/* 276 */     this.pieWRadius = radius;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/PiePlotState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */