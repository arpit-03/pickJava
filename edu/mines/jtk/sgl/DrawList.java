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
/*    */ public class DrawList
/*    */ {
/*    */   public void append(Node[] nodes) {
/* 48 */     this._list.add(nodes);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(DrawContext dc) {
/* 58 */     Node[] empty = new Node[0];
/* 59 */     Node[] nodes = empty;
/*    */     
/* 61 */     int nnode = nodes.length;
/*    */ 
/*    */ 
/*    */     
/* 65 */     int nlist = this._list.size();
/* 66 */     for (int ilist = 0; ilist <= nlist; ilist++) {
/*    */ 
/*    */       
/* 69 */       Node[] prevs = nodes;
/* 70 */       int nprev = nnode;
/*    */ 
/*    */       
/* 73 */       nodes = (ilist < nlist) ? this._list.get(ilist) : empty;
/* 74 */       nnode = nodes.length;
/*    */ 
/*    */       
/* 77 */       int mnode = (nnode < nprev) ? nnode : nprev;
/* 78 */       int knode = 0;
/* 79 */       while (knode < mnode && nodes[knode] == prevs[knode]) {
/* 80 */         knode++;
/*    */       }
/*    */       int inode;
/* 83 */       for (inode = nprev - 1; inode >= knode; inode--) {
/* 84 */         prevs[inode].drawEnd(dc);
/*    */       }
/*    */       
/* 87 */       for (inode = knode; inode < nnode; inode++) {
/* 88 */         nodes[inode].drawBegin(dc);
/*    */       }
/*    */       
/* 91 */       if (nnode > 0)
/* 92 */         nodes[nnode - 1].draw(dc); 
/*    */     } 
/*    */   }
/*    */   
/* 96 */   private ArrayList<Node[]> _list = (ArrayList)new ArrayList<>(32);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/DrawList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */