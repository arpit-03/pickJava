/*    */ package com.sun.jna.platform.mac;
/*    */ 
/*    */ import com.sun.jna.Library;
/*    */ import com.sun.jna.Native;
/*    */ import com.sun.jna.Structure;
/*    */ import com.sun.jna.platform.FileUtils;
/*    */ import com.sun.jna.ptr.ByteByReference;
/*    */ import com.sun.jna.ptr.PointerByReference;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
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
/*    */ public class MacFileUtils
/*    */   extends FileUtils
/*    */ {
/*    */   public boolean hasTrash() {
/* 41 */     return true;
/*    */   }
/*    */   public static interface FileManager extends Library { public static final int kFSFileOperationDefaultOptions = 0; public static final int kFSFileOperationsOverwrite = 1; public static final int kFSFileOperationsSkipSourcePermissionErrors = 2; public static final int kFSFileOperationsDoNotMoveAcrossVolumes = 4; public static final int kFSFileOperationsSkipPreflight = 8;
/*    */     public static final int kFSPathDefaultOptions = 0;
/* 45 */     public static final FileManager INSTANCE = (FileManager)Native.loadLibrary("CoreServices", FileManager.class); public static final int kFSPathMakeRefDoNotFollowLeafSymlink = 1;
/*    */     
/*    */     int FSRefMakePath(FSRef param1FSRef, byte[] param1ArrayOfbyte, int param1Int);
/*    */     
/*    */     int FSPathMakeRef(String param1String, int param1Int, ByteByReference param1ByteByReference);
/*    */     
/*    */     int FSPathMakeRefWithOptions(String param1String, int param1Int, FSRef param1FSRef, ByteByReference param1ByteByReference);
/*    */     
/*    */     int FSPathMoveObjectToTrashSync(String param1String, PointerByReference param1PointerByReference, int param1Int);
/*    */     
/*    */     int FSMoveObjectToTrashSync(FSRef param1FSRef1, FSRef param1FSRef2, int param1Int);
/*    */     
/* 57 */     public static class FSRef extends Structure { public static final List<String> FIELDS = createFieldsOrder("hidden");
/* 58 */       public byte[] hidden = new byte[80];
/*    */       
/*    */       protected List<String> getFieldOrder()
/*    */       {
/* 62 */         return FIELDS; } } } public static class FSRef extends Structure { protected List<String> getFieldOrder() { return FIELDS; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static final List<String> FIELDS = createFieldsOrder("hidden");
/*    */ 
/*    */     
/*    */     public byte[] hidden = new byte[80]; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void moveToTrash(File[] files) throws IOException {
/* 77 */     List<String> failed = new ArrayList<String>();
/* 78 */     for (File src : files) {
/* 79 */       FileManager.FSRef fsref = new FileManager.FSRef();
/* 80 */       int status = FileManager.INSTANCE.FSPathMakeRefWithOptions(src.getAbsolutePath(), 1, fsref, null);
/*    */ 
/*    */       
/* 83 */       if (status != 0) {
/* 84 */         failed.add(src + " (FSRef: " + status + ")");
/*    */       } else {
/*    */         
/* 87 */         status = FileManager.INSTANCE.FSMoveObjectToTrashSync(fsref, null, 0);
/* 88 */         if (status != 0)
/* 89 */           failed.add(src + " (" + status + ")"); 
/*    */       } 
/*    */     } 
/* 92 */     if (failed.size() > 0)
/* 93 */       throw new IOException("The following files could not be trashed: " + failed); 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/mac/MacFileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */