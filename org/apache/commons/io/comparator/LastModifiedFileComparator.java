/*    */ package org.apache.commons.io.comparator;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
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
/*    */ public class LastModifiedFileComparator
/*    */   extends AbstractFileComparator
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7372168004395734046L;
/* 53 */   public static final Comparator<File> LASTMODIFIED_COMPARATOR = new LastModifiedFileComparator();
/*    */ 
/*    */   
/* 56 */   public static final Comparator<File> LASTMODIFIED_REVERSE = new ReverseComparator(LASTMODIFIED_COMPARATOR);
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
/*    */   public int compare(File file1, File file2) {
/* 70 */     long result = file1.lastModified() - file2.lastModified();
/* 71 */     if (result < 0L)
/* 72 */       return -1; 
/* 73 */     if (result > 0L) {
/* 74 */       return 1;
/*    */     }
/* 76 */     return 0;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/io/comparator/LastModifiedFileComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */