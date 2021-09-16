/*    */ package edu.mines.jtk.opt;
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
/*    */ public class LinearTransformWrapper
/*    */   implements Transform
/*    */ {
/* 22 */   private LinearTransform _linearTransform = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LinearTransformWrapper(LinearTransform linearTransform) {
/* 28 */     this._linearTransform = linearTransform;
/*    */   }
/*    */ 
/*    */   
/*    */   public void forwardNonlinear(Vect data, VectConst model) {
/* 33 */     this._linearTransform.forward(data, model);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void forwardLinearized(Vect data, VectConst model, VectConst modelReference) {
/* 40 */     this._linearTransform.forward(data, model);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addTranspose(VectConst data, Vect model, VectConst modelReference) {
/* 47 */     this._linearTransform.addTranspose(data, model);
/*    */   }
/*    */ 
/*    */   
/*    */   public void inverseHessian(Vect model, VectConst modelReference) {
/* 52 */     this._linearTransform.inverseHessian(model);
/*    */   }
/*    */ 
/*    */   
/*    */   public void adjustRobustErrors(Vect dataError) {
/* 57 */     this._linearTransform.adjustRobustErrors(dataError);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/LinearTransformWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */