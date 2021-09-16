/*    */ package org.apache.http.client.fluent;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.http.NameValuePair;
/*    */ import org.apache.http.message.BasicNameValuePair;
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
/*    */ public class Form
/*    */ {
/*    */   private final List<NameValuePair> params;
/*    */   
/*    */   public static Form form() {
/* 40 */     return new Form();
/*    */   }
/*    */ 
/*    */   
/*    */   Form() {
/* 45 */     this.params = new ArrayList<NameValuePair>();
/*    */   }
/*    */   
/*    */   public Form add(String name, String value) {
/* 49 */     this.params.add(new BasicNameValuePair(name, value));
/* 50 */     return this;
/*    */   }
/*    */   
/*    */   public List<NameValuePair> build() {
/* 54 */     return new ArrayList<NameValuePair>(this.params);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/Form.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */