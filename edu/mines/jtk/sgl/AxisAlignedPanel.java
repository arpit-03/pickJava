/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AxisAlignedPanel
/*     */   extends Node
/*     */ {
/*     */   private AxisAlignedFrame _frame;
/*     */   
/*     */   public AxisAlignedPanel() {}
/*     */   
/*     */   public AxisAlignedPanel(AxisAlignedFrame frame) {
/*  38 */     setFrame(frame);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedFrame getFrame() {
/*  46 */     return this._frame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrame(AxisAlignedFrame frame) {
/*  56 */     this._frame = frame;
/*  57 */     if (this._frame != null) {
/*  58 */       BoxConstraint constraint = getBoxConstraint();
/*  59 */       if (constraint != null)
/*  60 */         this._frame.setBoxConstraint(constraint); 
/*     */     } 
/*  62 */     dirtyBoundingSphere();
/*  63 */     dirtyDraw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoxConstraint getBoxConstraint() {
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pick(PickContext pc) {
/*  84 */     if (this._frame != null) {
/*  85 */       this._frame.pickOnFrame(pc);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 100 */     if (this._frame == null) {
/* 101 */       return new BoundingSphere();
/*     */     }
/* 103 */     return this._frame.computeBoundingSphereOfFrame(finite);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/AxisAlignedPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */