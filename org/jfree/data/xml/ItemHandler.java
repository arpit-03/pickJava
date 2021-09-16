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
/*     */ public class ItemHandler
/*     */   extends DefaultHandler
/*     */   implements DatasetTags
/*     */ {
/*     */   private RootHandler root;
/*     */   private DefaultHandler parent;
/*     */   private Comparable key;
/*     */   private Number value;
/*     */   
/*     */   public ItemHandler(RootHandler root, DefaultHandler parent) {
/*  71 */     this.root = root;
/*  72 */     this.parent = parent;
/*  73 */     this.key = null;
/*  74 */     this.value = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getKey() {
/*  83 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKey(Comparable key) {
/*  92 */     this.key = key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getValue() {
/* 101 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Number value) {
/* 110 */     this.value = value;
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
/* 129 */     if (qName.equals("Item")) {
/* 130 */       KeyHandler subhandler = new KeyHandler(this.root, this);
/* 131 */       this.root.pushSubHandler(subhandler);
/*     */     }
/* 133 */     else if (qName.equals("Value")) {
/* 134 */       ValueHandler subhandler = new ValueHandler(this.root, this);
/* 135 */       this.root.pushSubHandler(subhandler);
/*     */     } else {
/*     */       
/* 138 */       throw new SAXException("Expected <Item> or <Value>...found " + qName);
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
/*     */   public void endElement(String namespaceURI, String localName, String qName) {
/* 157 */     if (this.parent instanceof PieDatasetHandler) {
/* 158 */       PieDatasetHandler handler = (PieDatasetHandler)this.parent;
/* 159 */       handler.addItem(this.key, this.value);
/* 160 */       this.root.popSubHandler();
/*     */     }
/* 162 */     else if (this.parent instanceof CategorySeriesHandler) {
/* 163 */       CategorySeriesHandler handler = (CategorySeriesHandler)this.parent;
/* 164 */       handler.addItem(this.key, this.value);
/* 165 */       this.root.popSubHandler();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xml/ItemHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */