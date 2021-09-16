/*    */ package edu.mines.jtk.sgl;
/*    */ 
/*    */ import java.awt.event.MouseEvent;
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
/*    */ public abstract class MouseConstrained
/*    */ {
/*    */   private Matrix44 _pixelToLocal;
/*    */   
/*    */   public MouseConstrained(Matrix44 localToPixel) {
/* 36 */     this._pixelToLocal = localToPixel.inverse();
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
/*    */   public abstract Point3 getPoint(MouseEvent paramMouseEvent);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Segment getMouseSegment(MouseEvent event) {
/* 57 */     int x = event.getX();
/* 58 */     int y = event.getY();
/* 59 */     Point3 near = new Point3(x, y, 0.0D);
/* 60 */     Point3 far = new Point3(x, y, 1.0D);
/* 61 */     near = this._pixelToLocal.times(near);
/* 62 */     far = this._pixelToLocal.times(far);
/* 63 */     return new Segment(near, far);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/MouseConstrained.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */