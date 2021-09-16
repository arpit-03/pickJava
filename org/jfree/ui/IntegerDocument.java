/*    */ package org.jfree.ui;
/*    */ 
/*    */ import javax.swing.text.AttributeSet;
/*    */ import javax.swing.text.BadLocationException;
/*    */ import javax.swing.text.PlainDocument;
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
/*    */ public class IntegerDocument
/*    */   extends PlainDocument
/*    */ {
/*    */   public void insertString(int i, String s, AttributeSet attributes) throws BadLocationException {
/* 69 */     super.insertString(i, s, attributes);
/* 70 */     if (s != null && (!s.equals("-") || i != 0 || s.length() >= 2))
/*    */       try {
/* 72 */         Integer.parseInt(getText(0, getLength()));
/*    */       }
/* 74 */       catch (NumberFormatException e) {
/* 75 */         remove(i, s.length());
/*    */       }  
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/IntegerDocument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */