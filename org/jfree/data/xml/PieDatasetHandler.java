/*     */ package org.jfree.data.xml;
/*     */ 
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ import org.jfree.data.general.PieDataset;
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
/*     */ public class PieDatasetHandler
/*     */   extends RootHandler
/*     */   implements DatasetTags
/*     */ {
/*  61 */   private DefaultPieDataset dataset = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PieDataset getDataset() {
/*  70 */     return (PieDataset)this.dataset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(Comparable key, Number value) {
/*  80 */     this.dataset.setValue(key, value);
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
/*  99 */     DefaultHandler current = getCurrentHandler();
/* 100 */     if (current != this) {
/* 101 */       current.startElement(namespaceURI, localName, qName, atts);
/*     */     }
/* 103 */     else if (qName.equals("PieDataset")) {
/* 104 */       this.dataset = new DefaultPieDataset();
/*     */     }
/* 106 */     else if (qName.equals("Item")) {
/* 107 */       ItemHandler subhandler = new ItemHandler(this, this);
/* 108 */       getSubHandlers().push(subhandler);
/* 109 */       subhandler.startElement(namespaceURI, localName, qName, atts);
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
/* 128 */     DefaultHandler current = getCurrentHandler();
/* 129 */     if (current != this)
/* 130 */       current.endElement(namespaceURI, localName, qName); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xml/PieDatasetHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */