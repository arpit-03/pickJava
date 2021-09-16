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
/*     */ public interface DsGetDC
/*     */ {
/*     */   public static final int DS_DOMAIN_IN_FOREST = 1;
/*     */   public static final int DS_DOMAIN_DIRECT_OUTBOUND = 2;
/*     */   public static final int DS_DOMAIN_TREE_ROOT = 4;
/*     */   public static final int DS_DOMAIN_PRIMARY = 8;
/*     */   public static final int DS_DOMAIN_NATIVE_MODE = 16;
/*     */   public static final int DS_DOMAIN_DIRECT_INBOUND = 32;
/*     */   public static final int DS_DOMAIN_VALID_FLAGS = 63;
/*     */   
/*     */   public static class DOMAIN_CONTROLLER_INFO
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends DOMAIN_CONTROLLER_INFO
/*     */       implements Structure.ByReference {}
/*     */     
/*  51 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "DomainControllerName", "DomainControllerAddress", "DomainControllerAddressType", "DomainGuid", "DomainName", "DnsForestName", "Flags", "DcSiteName", "ClientSiteName" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String DomainControllerName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String DomainControllerAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int DomainControllerAddressType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Guid.GUID DomainGuid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String DomainName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String DnsForestName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int Flags;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String DcSiteName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String ClientSiteName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DOMAIN_CONTROLLER_INFO() {
/* 122 */       super(W32APITypeMapper.DEFAULT);
/*     */     }
/*     */     
/*     */     public DOMAIN_CONTROLLER_INFO(Pointer memory) {
/* 126 */       super(memory, 0, W32APITypeMapper.DEFAULT);
/* 127 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 132 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PDOMAIN_CONTROLLER_INFO
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends PDOMAIN_CONTROLLER_INFO
/*     */       implements Structure.ByReference {}
/*     */ 
/*     */     
/* 146 */     public static final List<String> FIELDS = createFieldsOrder("dci");
/*     */     
/*     */     public DsGetDC.DOMAIN_CONTROLLER_INFO.ByReference dci;
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 152 */       return FIELDS;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DS_DOMAIN_TRUSTS
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends DS_DOMAIN_TRUSTS
/*     */       implements Structure.ByReference {}
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
/* 197 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "NetbiosDomainName", "DnsDomainName", "Flags", "ParentIndex", "TrustType", "TrustAttributes", "DomainSid", "DomainGuid" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String NetbiosDomainName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String DnsDomainName;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int Flags;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int ParentIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int TrustType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int TrustAttributes;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.PSID.ByReference DomainSid;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Guid.GUID DomainGuid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 246 */       return FIELDS;
/*     */     }
/*     */     
/*     */     public DS_DOMAIN_TRUSTS() {
/* 250 */       super(W32APITypeMapper.DEFAULT);
/*     */     }
/*     */     
/*     */     public DS_DOMAIN_TRUSTS(Pointer p) {
/* 254 */       super(p, 0, W32APITypeMapper.DEFAULT);
/* 255 */       read();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/DsGetDC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */