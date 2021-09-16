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
/*     */ public class ValueHandler
/*     */   extends DefaultHandler
/*     */   implements DatasetTags
/*     */ {
/*     */   private RootHandler rootHandler;
/*     */   private ItemHandler itemHandler;
/*     */   private StringBuffer currentText;
/*     */   
/*     */   public ValueHandler(RootHandler rootHandler, ItemHandler itemHandler) {
/*  69 */     this.rootHandler = rootHandler;
/*  70 */     this.itemHandler = itemHandler;
/*  71 */     this.currentText = new StringBuffer();
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
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/*  90 */     if (qName.equals("Value")) {
/*     */       
/*  92 */       clearCurrentText();
/*     */     } else {
/*     */       
/*  95 */       throw new SAXException("Expecting <Value> but found " + qName);
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
/* 114 */     if (qName.equals("Value")) {
/*     */       Number value;
/*     */       try {
/* 117 */         value = Double.valueOf(this.currentText.toString());
/* 118 */         if (((Double)value).isNaN()) {
/* 119 */           value = null;
/*     */         }
/*     */       }
/* 122 */       catch (NumberFormatException e1) {
/* 123 */         value = null;
/*     */       } 
/* 125 */       this.itemHandler.setValue(value);
/* 126 */       this.rootHandler.popSubHandler();
/*     */     } else {
/*     */       
/* 129 */       throw new SAXException("Expecting </Value> but found " + qName);
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
/* 143 */     if (this.currentText != null) {
/* 144 */       this.currentText.append(String.copyValueOf(ch, start, length));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getCurrentText() {
/* 154 */     return this.currentText.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearCurrentText() {
/* 161 */     this.currentText.delete(0, this.currentText.length());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xml/ValueHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */