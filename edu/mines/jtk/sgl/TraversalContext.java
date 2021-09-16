/*    */ package edu.mines.jtk.sgl;
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
/*    */ public class TraversalContext
/*    */ {
/*    */   public int countNodes() {
/* 34 */     return this._nodeStack.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Node getNode() {
/* 42 */     return this._nodeStack.peek();
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
/*    */ 
/*    */ 
/*    */   
/*    */   public Node getNode(int index) {
/* 57 */     if (index < 0) {
/* 58 */       return this._nodeStack.get(index + countNodes());
/*    */     }
/* 60 */     return this._nodeStack.get(index);
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
/*    */   public Node[] getNodes() {
/* 72 */     int nnode = this._nodeStack.size();
/* 73 */     Node[] nodes = new Node[nnode];
/* 74 */     for (int inode = 0; inode < nnode; inode++)
/* 75 */       nodes[inode] = this._nodeStack.get(inode); 
/* 76 */     return nodes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void pushNode(Node node) {
/* 84 */     this._nodeStack.push(node);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void popNode() {
/* 92 */     this._nodeStack.pop();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 98 */   private ArrayStack<Node> _nodeStack = new ArrayStack<>();
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/TraversalContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */