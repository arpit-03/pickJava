/*     */ package org.jfree.data.xml;
/*     */ 
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
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
/*     */ public class KeyHandler
/*     */   extends DefaultHandler
/*     */   implements DatasetTags
/*     */ {
/*     */   private RootHandler rootHandler;
/*     */   private ItemHandler itemHandler;
/*     */   private StringBuffer currentText;
/*     */   
/*     */   public KeyHandler(RootHandler rootHandler, ItemHandler itemHandler) {
/*  71 */     this.rootHandler = rootHandler;
/*  72 */     this.itemHandler = itemHandler;
/*  73 */     this.currentText = new StringBuffer();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/*  93 */     if (qName.equals("Key")) {
/*  94 */       clearCurrentText();
/*     */     } else {
/*     */       
/*  97 */       throw new SAXException("Expecting <Key> but found " + qName);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/* 116 */     if (qName.equals("Key")) {
/* 117 */       this.itemHandler.setKey(getCurrentText());
/* 118 */       this.rootHandler.popSubHandler();
/* 119 */       this.rootHandler.pushSubHandler(new ValueHandler(this.rootHandler, this.itemHandler));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 124 */       throw new SAXException("Expecting </Key> but found " + qName);
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
/*     */   public void characters(char[] ch, int start, int length) {
/* 138 */     if (this.currentText != null) {
/* 139 */       this.currentText.append(String.copyValueOf(ch, start, length));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getCurrentText() {
/* 149 */     return this.currentText.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearCurrentText() {
/* 156 */     this.currentText.delete(0, this.currentText.length());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xml/KeyHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */