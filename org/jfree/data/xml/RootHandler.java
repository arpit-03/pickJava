/*     */ package org.jfree.data.xml;
/*     */ 
/*     */ import java.util.Stack;
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
/*     */ public class RootHandler
/*     */   extends DefaultHandler
/*     */   implements DatasetTags
/*     */ {
/*  60 */   private Stack subHandlers = new Stack();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stack getSubHandlers() {
/*  69 */     return this.subHandlers;
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
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/*  84 */     DefaultHandler handler = getCurrentHandler();
/*  85 */     if (handler != this) {
/*  86 */       handler.characters(ch, start, length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHandler getCurrentHandler() {
/*  96 */     DefaultHandler result = this;
/*  97 */     if (this.subHandlers != null && 
/*  98 */       this.subHandlers.size() > 0) {
/*  99 */       Object top = this.subHandlers.peek();
/* 100 */       if (top != null) {
/* 101 */         result = (DefaultHandler)top;
/*     */       }
/*     */     } 
/*     */     
/* 105 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushSubHandler(DefaultHandler subhandler) {
/* 114 */     this.subHandlers.push(subhandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHandler popSubHandler() {
/* 123 */     return this.subHandlers.pop();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xml/RootHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */