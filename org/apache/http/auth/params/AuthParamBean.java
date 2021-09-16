/*    */ package org.apache.http.auth.params;
/*    */ 
/*    */ import org.apache.http.params.HttpAbstractParamBean;
/*    */ import org.apache.http.params.HttpParams;
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
/*    */ @Deprecated
/*    */ public class AuthParamBean
/*    */   extends HttpAbstractParamBean
/*    */ {
/*    */   public AuthParamBean(HttpParams params) {
/* 48 */     super(params);
/*    */   }
/*    */   
/*    */   public void setCredentialCharset(String charset) {
/* 52 */     AuthParams.setCredentialCharset(this.params, charset);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/auth/params/AuthParamBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */