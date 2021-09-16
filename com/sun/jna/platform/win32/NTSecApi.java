/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.Union;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface NTSecApi
/*     */ {
/*     */   public static final int ForestTrustTopLevelName = 0;
/*     */   public static final int ForestTrustTopLevelNameEx = 1;
/*     */   public static final int ForestTrustDomainInfo = 2;
/*     */   
/*     */   public static class LSA_UNICODE_STRING
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends LSA_UNICODE_STRING
/*     */       implements Structure.ByReference {}
/*     */     
/*  51 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "Length", "MaximumLength", "Buffer" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public short Length;
/*     */ 
/*     */ 
/*     */     
/*     */     public short MaximumLength;
/*     */ 
/*     */ 
/*     */     
/*     */     public Pointer Buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  70 */       return FIELDS;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getString() {
/*  79 */       byte[] data = this.Buffer.getByteArray(0L, this.Length);
/*  80 */       if (data.length < 2 || data[data.length - 1] != 0) {
/*  81 */         Memory newdata = new Memory((data.length + 2));
/*  82 */         newdata.write(0L, data, 0, data.length);
/*  83 */         return newdata.getWideString(0L);
/*     */       } 
/*  85 */       return this.Buffer.getWideString(0L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PLSA_UNICODE_STRING
/*     */   {
/*     */     public NTSecApi.LSA_UNICODE_STRING.ByReference s;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static class ByReference
/*     */       extends PLSA_UNICODE_STRING
/*     */       implements Structure.ByReference {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LSA_FOREST_TRUST_DOMAIN_INFO
/*     */     extends Structure
/*     */   {
/* 115 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "Sid", "DnsName", "NetbiosName" });
/*     */     
/*     */     public WinNT.PSID.ByReference Sid;
/*     */     
/*     */     public NTSecApi.LSA_UNICODE_STRING DnsName;
/*     */     public NTSecApi.LSA_UNICODE_STRING NetbiosName;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 123 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LSA_FOREST_TRUST_BINARY_DATA extends Structure {
/* 128 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "Length", "Buffer" });
/*     */     
/*     */     public int Length;
/*     */     
/*     */     public Pointer Buffer;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 135 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LSA_FOREST_TRUST_RECORD
/*     */     extends Structure {
/*     */     public static class ByReference
/*     */       extends LSA_FOREST_TRUST_RECORD
/*     */       implements Structure.ByReference {}
/*     */     
/*     */     public static class UNION
/*     */       extends Union {
/*     */       public NTSecApi.LSA_UNICODE_STRING TopLevelName;
/*     */       public NTSecApi.LSA_FOREST_TRUST_DOMAIN_INFO DomainInfo;
/*     */       public NTSecApi.LSA_FOREST_TRUST_BINARY_DATA Data;
/*     */       
/*     */       public static class ByReference
/*     */         extends UNION
/*     */         implements Structure.ByReference {}
/*     */     }
/* 155 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "Flags", "ForestTrustType", "Time", "u" });
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
/*     */     public int ForestTrustType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.LARGE_INTEGER Time;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public UNION u;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 183 */       return FIELDS;
/*     */     }
/*     */ 
/*     */     
/*     */     public void read() {
/* 188 */       super.read();
/*     */       
/* 190 */       switch (this.ForestTrustType) {
/*     */         case 0:
/*     */         case 1:
/* 193 */           this.u.setType(NTSecApi.LSA_UNICODE_STRING.class);
/*     */           break;
/*     */         case 2:
/* 196 */           this.u.setType(NTSecApi.LSA_FOREST_TRUST_DOMAIN_INFO.class);
/*     */           break;
/*     */         default:
/* 199 */           this.u.setType(NTSecApi.LSA_FOREST_TRUST_BINARY_DATA.class);
/*     */           break;
/*     */       } 
/*     */       
/* 203 */       this.u.read();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PLSA_FOREST_TRUST_RECORD
/*     */     extends Structure {
/*     */     public static class ByReference
/*     */       extends PLSA_FOREST_TRUST_RECORD implements Structure.ByReference {}
/*     */     
/* 212 */     public static final List<String> FIELDS = createFieldsOrder("tr");
/*     */     
/*     */     public NTSecApi.LSA_FOREST_TRUST_RECORD.ByReference tr;
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 218 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LSA_FOREST_TRUST_INFORMATION
/*     */     extends Structure {
/*     */     public static class ByReference
/*     */       extends LSA_FOREST_TRUST_INFORMATION
/*     */       implements Structure.ByReference {}
/*     */     
/* 228 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "RecordCount", "Entries" });
/*     */ 
/*     */ 
/*     */     
/*     */     public int RecordCount;
/*     */ 
/*     */ 
/*     */     
/*     */     public NTSecApi.PLSA_FOREST_TRUST_RECORD.ByReference Entries;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 242 */       return FIELDS;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public NTSecApi.PLSA_FOREST_TRUST_RECORD[] getEntries() {
/* 251 */       return (NTSecApi.PLSA_FOREST_TRUST_RECORD[])this.Entries.toArray(this.RecordCount);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PLSA_FOREST_TRUST_INFORMATION
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends PLSA_FOREST_TRUST_INFORMATION
/*     */       implements Structure.ByReference {}
/*     */ 
/*     */     
/* 264 */     public static final List<String> FIELDS = createFieldsOrder("fti");
/*     */     
/*     */     public NTSecApi.LSA_FOREST_TRUST_INFORMATION.ByReference fti;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 269 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/NTSecApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */