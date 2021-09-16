/*    */ package edu.mines.jtk.sgl;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ class ArrayStack<E>
/*    */ {
/*    */   public E push(E e) {
/* 27 */     this._list.add(e);
/* 28 */     return e;
/*    */   }
/*    */   
/*    */   public E pop() {
/* 32 */     return this._list.remove(this._list.size() - 1);
/*    */   }
/*    */   
/*    */   public E peek() {
/* 36 */     return this._list.get(this._list.size() - 1);
/*    */   }
/*    */   
/*    */   public E get(int index) {
/* 40 */     return this._list.get(index);
/*    */   }
/*    */   
/*    */   public int size() {
/* 44 */     return this._list.size();
/*    */   }
/*    */   
/* 47 */   private ArrayList<E> _list = new ArrayList<>();
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/ArrayStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */