/*    */ package us.hebi.matlab.mat.util;
/*    */ 
/*    */ import java.util.Locale;
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
/*    */ public final class PlatformInfo
/*    */ {
/*    */   public static boolean isWindows() {
/* 34 */     return IS_WINDOWS;
/*    */   }
/*    */   
/*    */   public static boolean isUnix() {
/* 38 */     return IS_UNIX;
/*    */   }
/*    */   
/*    */   public static boolean isMac() {
/* 42 */     return IS_MAC;
/*    */   }
/*    */   
/*    */   public static boolean isAndroid() {
/* 46 */     return IS_ANDROID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isMatlab() {
/* 53 */     return IS_MATLAB;
/*    */   }
/*    */   
/*    */   public static boolean hasSecurityManager() {
/* 57 */     return (System.getSecurityManager() != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getJavaVersion() {
/* 64 */     return JAVA_VERSION;
/*    */   }
/*    */ 
/*    */   
/* 68 */   private static final String OS = System.getProperty("os.name").toLowerCase(Locale.US);
/* 69 */   private static final boolean IS_WINDOWS = OS.contains("win");
/* 70 */   private static final boolean IS_MAC = OS.contains("mac");
/* 71 */   private static final boolean IS_UNIX = !(!OS.contains("nix") && !OS.contains("nux") && OS.indexOf("aix") <= 0);
/* 72 */   private static final boolean IS_SOLARIS = OS.contains("sunos");
/*    */   
/* 74 */   private static final boolean IS_ANDROID = "Dalvik".equals(System.getProperty("java.vm.name"));
/* 75 */   private static final boolean IS_MATLAB = isMatlab0();
/* 76 */   private static final int JAVA_VERSION = getMajorJavaVersion0();
/*    */ 
/*    */   
/*    */   private static boolean isMatlab0() {
/*    */     try {
/* 81 */       Class.forName("com.mathworks.jmi.Matlab", true, PlatformInfo.class.getClassLoader());
/* 82 */       return true;
/* 83 */     } catch (Exception ex) {
/* 84 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static int getMajorJavaVersion0() {
/* 90 */     if (isAndroid()) return 6;
/*    */ 
/*    */     
/* 93 */     String version = System.getProperty("java.specification.version", "6");
/* 94 */     String majorVersion = version.startsWith("1.") ? version.substring(2) : version;
/* 95 */     return Integer.parseInt(majorVersion);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/PlatformInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */