/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.win32.W32APITypeMapper;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface LMAccess
/*     */ {
/*     */   public static final int FILTER_TEMP_DUPLICATE_ACCOUNT = 1;
/*     */   public static final int FILTER_NORMAL_ACCOUNT = 2;
/*     */   public static final int FILTER_INTERDOMAIN_TRUST_ACCOUNT = 8;
/*     */   public static final int FILTER_WORKSTATION_TRUST_ACCOUNT = 16;
/*     */   public static final int FILTER_SERVER_TRUST_ACCOUNT = 32;
/*     */   public static final int USER_PRIV_MASK = 3;
/*     */   public static final int USER_PRIV_GUEST = 0;
/*     */   public static final int USER_PRIV_USER = 1;
/*     */   public static final int USER_PRIV_ADMIN = 2;
/*     */   public static final int ACCESS_NONE = 0;
/*     */   public static final int ACCESS_READ = 1;
/*     */   public static final int ACCESS_WRITE = 2;
/*     */   public static final int ACCESS_CREATE = 4;
/*     */   public static final int ACCESS_EXEC = 8;
/*     */   public static final int ACCESS_DELETE = 16;
/*     */   public static final int ACCESS_ATRIB = 32;
/*     */   public static final int ACCESS_PERM = 64;
/*     */   public static final int ACCESS_ALL = 127;
/*     */   public static final int ACCESS_GROUP = 32768;
/*     */   
/*     */   public static class LOCALGROUP_INFO_0
/*     */     extends Structure
/*     */   {
/*  41 */     public static final List<String> FIELDS = createFieldsOrder("lgrui0_name");
/*     */     
/*     */     public String lgrui0_name;
/*     */     
/*     */     public LOCALGROUP_INFO_0() {
/*  46 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public LOCALGROUP_INFO_0(Pointer memory) {
/*  50 */       super(memory, 0, W32APITypeMapper.UNICODE);
/*  51 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  56 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LOCALGROUP_INFO_1 extends Structure {
/*  61 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "lgrui1_name", "lgrui1_comment" });
/*     */     
/*     */     public String lgrui1_name;
/*     */     public String lgrui1_comment;
/*     */     
/*     */     public LOCALGROUP_INFO_1() {
/*  67 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public LOCALGROUP_INFO_1(Pointer memory) {
/*  71 */       super(memory, 0, W32APITypeMapper.UNICODE);
/*  72 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  77 */       return FIELDS;
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
/*     */   public static class USER_INFO_0
/*     */     extends Structure
/*     */   {
/*  96 */     public static final List<String> FIELDS = createFieldsOrder("usri0_name");
/*     */ 
/*     */     
/*     */     public String usri0_name;
/*     */ 
/*     */     
/*     */     public USER_INFO_0() {
/* 103 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public USER_INFO_0(Pointer memory) {
/* 107 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 108 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 113 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class USER_INFO_1
/*     */     extends Structure
/*     */   {
/* 123 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "usri1_name", "usri1_password", "usri1_password_age", "usri1_priv", "usri1_home_dir", "usri1_comment", "usri1_flags", "usri1_script_path" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String usri1_name;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String usri1_password;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int usri1_password_age;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int usri1_priv;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String usri1_home_dir;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String usri1_comment;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int usri1_flags;
/*     */ 
/*     */ 
/*     */     
/*     */     public String usri1_script_path;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public USER_INFO_1() {
/* 168 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public USER_INFO_1(Pointer memory) {
/* 172 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 173 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 178 */       return FIELDS;
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
/*     */   public static class USER_INFO_23
/*     */     extends Structure
/*     */   {
/* 192 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "usri23_name", "usri23_full_name", "usri23_comment", "usri23_flags", "usri23_user_sid" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String usri23_name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String usri23_full_name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String usri23_comment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int usri23_flags;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.PSID.ByReference usri23_user_sid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public USER_INFO_23() {
/* 247 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public USER_INFO_23(Pointer memory) {
/* 251 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 252 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 257 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GROUP_USERS_INFO_0
/*     */     extends Structure
/*     */   {
/* 265 */     public static final List<String> FIELDS = createFieldsOrder("grui0_name");
/*     */ 
/*     */     
/*     */     public String grui0_name;
/*     */ 
/*     */     
/*     */     public GROUP_USERS_INFO_0() {
/* 272 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public GROUP_USERS_INFO_0(Pointer memory) {
/* 276 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 277 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 282 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class LOCALGROUP_USERS_INFO_0
/*     */     extends Structure
/*     */   {
/* 290 */     public static final List<String> FIELDS = createFieldsOrder("lgrui0_name");
/*     */ 
/*     */     
/*     */     public String lgrui0_name;
/*     */ 
/*     */     
/*     */     public LOCALGROUP_USERS_INFO_0() {
/* 297 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public LOCALGROUP_USERS_INFO_0(Pointer memory) {
/* 301 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 302 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 307 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class GROUP_INFO_0
/*     */     extends Structure
/*     */   {
/* 317 */     public static final List<String> FIELDS = createFieldsOrder("grpi0_name");
/*     */ 
/*     */ 
/*     */     
/*     */     public String grpi0_name;
/*     */ 
/*     */ 
/*     */     
/*     */     public GROUP_INFO_0() {
/* 326 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public GROUP_INFO_0(Pointer memory) {
/* 330 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 331 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 336 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class GROUP_INFO_1
/*     */     extends Structure
/*     */   {
/* 345 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "grpi1_name", "grpi1_comment" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String grpi1_name;
/*     */ 
/*     */ 
/*     */     
/*     */     public String grpi1_comment;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GROUP_INFO_1() {
/* 360 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public GROUP_INFO_1(Pointer memory) {
/* 364 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 365 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 370 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class GROUP_INFO_2
/*     */     extends Structure
/*     */   {
/* 379 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "grpi2_name", "grpi2_comment", "grpi2_group_id", "grpi2_attributes" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String grpi2_name;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String grpi2_comment;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int grpi2_group_id;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int grpi2_attributes;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GROUP_INFO_2() {
/* 405 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public GROUP_INFO_2(Pointer memory) {
/* 409 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 410 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 415 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class GROUP_INFO_3
/*     */     extends Structure
/*     */   {
/* 424 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "grpi3_name", "grpi3_comment", "grpi3_group_sid", "grpi3_attributes" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String grpi3_name;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String grpi3_comment;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.PSID.ByReference grpi3_group_sid;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int grpi3_attributes;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GROUP_INFO_3() {
/* 450 */       super(W32APITypeMapper.UNICODE);
/*     */     }
/*     */     
/*     */     public GROUP_INFO_3(Pointer memory) {
/* 454 */       super(memory, 0, W32APITypeMapper.UNICODE);
/* 455 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 460 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/LMAccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */