/*    */ package org.apache.http.impl.client.cache;
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
/*    */ enum RequestProtocolError
/*    */ {
/* 34 */   UNKNOWN,
/* 35 */   BODY_BUT_NO_LENGTH_ERROR,
/* 36 */   WEAK_ETAG_ON_PUTDELETE_METHOD_ERROR,
/* 37 */   WEAK_ETAG_AND_RANGE_ERROR,
/* 38 */   NO_CACHE_DIRECTIVE_WITH_FIELD_NAME;
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/RequestProtocolError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */