/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.Memory;
/*      */ import com.sun.jna.Native;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.ptr.IntByReference;
/*      */ import com.sun.jna.ptr.LongByReference;
/*      */ import com.sun.jna.ptr.PointerByReference;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Advapi32Util
/*      */ {
/*      */   public static class Account
/*      */   {
/*      */     public String name;
/*      */     public String domain;
/*      */     public byte[] sid;
/*      */     public String sidString;
/*      */     public int accountType;
/*      */     public String fqn;
/*      */   }
/*      */   
/*      */   public static String getUserName() {
/*  143 */     char[] buffer = new char[128];
/*  144 */     IntByReference len = new IntByReference(buffer.length);
/*  145 */     boolean result = Advapi32.INSTANCE.GetUserNameW(buffer, len);
/*      */     
/*  147 */     if (!result) {
/*  148 */       switch (Kernel32.INSTANCE.GetLastError()) {
/*      */         case 122:
/*  150 */           buffer = new char[len.getValue()];
/*      */           break;
/*      */         
/*      */         default:
/*  154 */           throw new Win32Exception(Native.getLastError());
/*      */       } 
/*      */       
/*  157 */       result = Advapi32.INSTANCE.GetUserNameW(buffer, len);
/*      */     } 
/*      */     
/*  160 */     if (!result) {
/*  161 */       throw new Win32Exception(Native.getLastError());
/*      */     }
/*      */     
/*  164 */     return Native.toString(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Account getAccountByName(String accountName) {
/*  176 */     return getAccountByName(null, accountName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Account getAccountByName(String systemName, String accountName) {
/*  189 */     IntByReference pSid = new IntByReference(0);
/*  190 */     IntByReference cchDomainName = new IntByReference(0);
/*  191 */     PointerByReference peUse = new PointerByReference();
/*      */     
/*  193 */     if (Advapi32.INSTANCE.LookupAccountName(systemName, accountName, null, pSid, null, cchDomainName, peUse))
/*      */     {
/*  195 */       throw new RuntimeException("LookupAccountNameW was expected to fail with ERROR_INSUFFICIENT_BUFFER");
/*      */     }
/*      */ 
/*      */     
/*  199 */     int rc = Kernel32.INSTANCE.GetLastError();
/*  200 */     if (pSid.getValue() == 0 || rc != 122) {
/*  201 */       throw new Win32Exception(rc);
/*      */     }
/*      */     
/*  204 */     Memory sidMemory = new Memory(pSid.getValue());
/*  205 */     WinNT.PSID result = new WinNT.PSID((Pointer)sidMemory);
/*  206 */     char[] referencedDomainName = new char[cchDomainName.getValue() + 1];
/*      */     
/*  208 */     if (!Advapi32.INSTANCE.LookupAccountName(systemName, accountName, result, pSid, referencedDomainName, cchDomainName, peUse))
/*      */     {
/*  210 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  213 */     Account account = new Account();
/*  214 */     account.accountType = peUse.getPointer().getInt(0L);
/*  215 */     account.name = accountName;
/*      */     
/*  217 */     String[] accountNamePartsBs = accountName.split("\\\\", 2);
/*  218 */     String[] accountNamePartsAt = accountName.split("@", 2);
/*      */     
/*  220 */     if (accountNamePartsBs.length == 2) {
/*  221 */       account.name = accountNamePartsBs[1];
/*  222 */     } else if (accountNamePartsAt.length == 2) {
/*  223 */       account.name = accountNamePartsAt[0];
/*      */     } else {
/*  225 */       account.name = accountName;
/*      */     } 
/*      */     
/*  228 */     if (cchDomainName.getValue() > 0) {
/*  229 */       account.domain = Native.toString(referencedDomainName);
/*  230 */       account.fqn = account.domain + "\\" + account.name;
/*      */     } else {
/*  232 */       account.fqn = account.name;
/*      */     } 
/*      */     
/*  235 */     account.sid = result.getBytes();
/*  236 */     account.sidString = convertSidToStringSid(new WinNT.PSID(account.sid));
/*  237 */     return account;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Account getAccountBySid(WinNT.PSID sid) {
/*  248 */     return getAccountBySid((String)null, sid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Account getAccountBySid(String systemName, WinNT.PSID sid) {
/*  261 */     IntByReference cchName = new IntByReference();
/*  262 */     IntByReference cchDomainName = new IntByReference();
/*  263 */     PointerByReference peUse = new PointerByReference();
/*      */     
/*  265 */     if (Advapi32.INSTANCE.LookupAccountSid(null, sid, null, cchName, null, cchDomainName, peUse))
/*      */     {
/*  267 */       throw new RuntimeException("LookupAccountSidW was expected to fail with ERROR_INSUFFICIENT_BUFFER");
/*      */     }
/*      */ 
/*      */     
/*  271 */     int rc = Kernel32.INSTANCE.GetLastError();
/*  272 */     if (cchName.getValue() == 0 || rc != 122)
/*      */     {
/*  274 */       throw new Win32Exception(rc);
/*      */     }
/*      */     
/*  277 */     char[] domainName = new char[cchDomainName.getValue()];
/*  278 */     char[] name = new char[cchName.getValue()];
/*      */     
/*  280 */     if (!Advapi32.INSTANCE.LookupAccountSid(null, sid, name, cchName, domainName, cchDomainName, peUse))
/*      */     {
/*  282 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  285 */     Account account = new Account();
/*  286 */     account.accountType = peUse.getPointer().getInt(0L);
/*  287 */     account.name = Native.toString(name);
/*      */     
/*  289 */     if (cchDomainName.getValue() > 0) {
/*  290 */       account.domain = Native.toString(domainName);
/*  291 */       account.fqn = account.domain + "\\" + account.name;
/*      */     } else {
/*  293 */       account.fqn = account.name;
/*      */     } 
/*      */     
/*  296 */     account.sid = sid.getBytes();
/*  297 */     account.sidString = convertSidToStringSid(sid);
/*  298 */     return account;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String convertSidToStringSid(WinNT.PSID sid) {
/*  310 */     PointerByReference stringSid = new PointerByReference();
/*  311 */     if (!Advapi32.INSTANCE.ConvertSidToStringSid(sid, stringSid)) {
/*  312 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  315 */     Pointer ptr = stringSid.getValue();
/*      */     try {
/*  317 */       return ptr.getWideString(0L);
/*      */     } finally {
/*  319 */       Kernel32Util.freeLocalMemory(ptr);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] convertStringSidToSid(String sidString) {
/*  332 */     WinNT.PSIDByReference pSID = new WinNT.PSIDByReference();
/*  333 */     if (!Advapi32.INSTANCE.ConvertStringSidToSid(sidString, pSID)) {
/*  334 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  337 */     WinNT.PSID value = pSID.getValue();
/*      */     try {
/*  339 */       return value.getBytes();
/*      */     } finally {
/*  341 */       Kernel32Util.freeLocalMemory(value.getPointer());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWellKnownSid(String sidString, int wellKnownSidType) {
/*  356 */     WinNT.PSIDByReference pSID = new WinNT.PSIDByReference();
/*  357 */     if (!Advapi32.INSTANCE.ConvertStringSidToSid(sidString, pSID)) {
/*  358 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  361 */     WinNT.PSID value = pSID.getValue();
/*      */     try {
/*  363 */       return Advapi32.INSTANCE.IsWellKnownSid(value, wellKnownSidType);
/*      */     } finally {
/*  365 */       Kernel32Util.freeLocalMemory(value.getPointer());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWellKnownSid(byte[] sidBytes, int wellKnownSidType) {
/*  380 */     WinNT.PSID pSID = new WinNT.PSID(sidBytes);
/*  381 */     return Advapi32.INSTANCE.IsWellKnownSid(pSID, wellKnownSidType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int alignOnDWORD(int cbAcl) {
/*  390 */     return cbAcl + 3 & 0xFFFFFFFC;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAceSize(int sidLength) {
/*  399 */     return Native.getNativeSize(WinNT.ACCESS_ALLOWED_ACE.class, null) + sidLength - 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Account getAccountBySid(String sidString) {
/*  412 */     return getAccountBySid((String)null, sidString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Account getAccountBySid(String systemName, String sidString) {
/*  425 */     return getAccountBySid(systemName, new WinNT.PSID(convertStringSidToSid(sidString)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Account[] getTokenGroups(WinNT.HANDLE hToken) {
/*  438 */     IntByReference tokenInformationLength = new IntByReference();
/*  439 */     if (Advapi32.INSTANCE.GetTokenInformation(hToken, 2, null, 0, tokenInformationLength))
/*      */     {
/*      */       
/*  442 */       throw new RuntimeException("Expected GetTokenInformation to fail with ERROR_INSUFFICIENT_BUFFER");
/*      */     }
/*      */     
/*  445 */     int rc = Kernel32.INSTANCE.GetLastError();
/*  446 */     if (rc != 122) {
/*  447 */       throw new Win32Exception(rc);
/*      */     }
/*      */ 
/*      */     
/*  451 */     WinNT.TOKEN_GROUPS groups = new WinNT.TOKEN_GROUPS(tokenInformationLength.getValue());
/*  452 */     if (!Advapi32.INSTANCE.GetTokenInformation(hToken, 2, groups, tokenInformationLength
/*      */         
/*  454 */         .getValue(), tokenInformationLength)) {
/*  455 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  457 */     ArrayList<Account> userGroups = new ArrayList<Account>();
/*      */     
/*  459 */     for (WinNT.SID_AND_ATTRIBUTES sidAndAttribute : groups.getGroups()) {
/*  460 */       Account group = null;
/*      */       try {
/*  462 */         group = getAccountBySid(sidAndAttribute.Sid);
/*  463 */       } catch (Exception e) {
/*  464 */         group = new Account();
/*  465 */         group.sid = sidAndAttribute.Sid.getBytes();
/*  466 */         group
/*  467 */           .sidString = convertSidToStringSid(sidAndAttribute.Sid);
/*  468 */         group.name = group.sidString;
/*  469 */         group.fqn = group.sidString;
/*  470 */         group.accountType = 2;
/*      */       } 
/*  472 */       userGroups.add(group);
/*      */     } 
/*  474 */     return userGroups.<Account>toArray(new Account[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Account getTokenAccount(WinNT.HANDLE hToken) {
/*  487 */     IntByReference tokenInformationLength = new IntByReference();
/*  488 */     if (Advapi32.INSTANCE.GetTokenInformation(hToken, 1, null, 0, tokenInformationLength))
/*      */     {
/*      */       
/*  491 */       throw new RuntimeException("Expected GetTokenInformation to fail with ERROR_INSUFFICIENT_BUFFER");
/*      */     }
/*      */     
/*  494 */     int rc = Kernel32.INSTANCE.GetLastError();
/*  495 */     if (rc != 122) {
/*  496 */       throw new Win32Exception(rc);
/*      */     }
/*      */ 
/*      */     
/*  500 */     WinNT.TOKEN_USER user = new WinNT.TOKEN_USER(tokenInformationLength.getValue());
/*  501 */     if (!Advapi32.INSTANCE.GetTokenInformation(hToken, 1, user, tokenInformationLength
/*      */         
/*  503 */         .getValue(), tokenInformationLength)) {
/*  504 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  506 */     return getAccountBySid(user.User.Sid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Account[] getCurrentUserGroups() {
/*  515 */     WinNT.HANDLEByReference phToken = new WinNT.HANDLEByReference();
/*  516 */     Win32Exception err = null;
/*      */     
/*      */     try {
/*  519 */       WinNT.HANDLE threadHandle = Kernel32.INSTANCE.GetCurrentThread();
/*  520 */       if (!Advapi32.INSTANCE.OpenThreadToken(threadHandle, 10, true, phToken)) {
/*      */         
/*  522 */         int rc = Kernel32.INSTANCE.GetLastError();
/*  523 */         if (rc != 1008) {
/*  524 */           throw new Win32Exception(rc);
/*      */         }
/*      */         
/*  527 */         WinNT.HANDLE processHandle = Kernel32.INSTANCE.GetCurrentProcess();
/*  528 */         if (!Advapi32.INSTANCE.OpenProcessToken(processHandle, 10, phToken))
/*      */         {
/*  530 */           throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */         }
/*      */       } 
/*      */       
/*  534 */       return getTokenGroups(phToken.getValue());
/*  535 */     } catch (Win32Exception e) {
/*  536 */       err = e;
/*  537 */       throw err;
/*      */     } finally {
/*  539 */       WinNT.HANDLE hToken = phToken.getValue();
/*  540 */       if (!WinBase.INVALID_HANDLE_VALUE.equals(hToken)) {
/*      */         try {
/*  542 */           Kernel32Util.closeHandle(hToken);
/*  543 */         } catch (Win32Exception e) {
/*  544 */           if (err == null) {
/*  545 */             err = e;
/*      */           } else {
/*  547 */             err.addSuppressedReflected((Throwable)e);
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  552 */       if (err != null) {
/*  553 */         throw err;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean registryKeyExists(WinReg.HKEY root, String key) {
/*  568 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/*  569 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, key, 0, 131097, phkKey);
/*      */     
/*  571 */     switch (rc) {
/*      */       case 0:
/*  573 */         Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/*  574 */         return true;
/*      */       case 2:
/*  576 */         return false;
/*      */     } 
/*  578 */     throw new Win32Exception(rc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean registryValueExists(WinReg.HKEY root, String key, String value) {
/*  595 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/*  596 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, key, 0, 131097, phkKey);
/*      */     try {
/*      */       boolean bool1, bool2;
/*  599 */       switch (rc) {
/*      */         case 0:
/*      */           break;
/*      */         case 2:
/*  603 */           return false;
/*      */         default:
/*  605 */           throw new Win32Exception(rc);
/*      */       } 
/*  607 */       IntByReference lpcbData = new IntByReference();
/*  608 */       IntByReference lpType = new IntByReference();
/*  609 */       rc = Advapi32.INSTANCE.RegQueryValueEx(phkKey.getValue(), value, 0, lpType, (char[])null, lpcbData);
/*      */       
/*  611 */       switch (rc) {
/*      */         case 0:
/*      */         case 122:
/*      */         case 234:
/*  615 */           bool2 = true; return bool2;
/*      */         case 2:
/*  617 */           bool2 = false; return bool2;
/*      */       } 
/*  619 */       throw new Win32Exception(rc);
/*      */     } finally {
/*      */       
/*  622 */       if (phkKey.getValue() != WinBase.INVALID_HANDLE_VALUE) {
/*  623 */         rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/*  624 */         if (rc != 0) {
/*  625 */           throw new Win32Exception(rc);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String registryGetStringValue(WinReg.HKEY root, String key, String value) {
/*  644 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/*  645 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, key, 0, 131097, phkKey);
/*      */     
/*  647 */     if (rc != 0) {
/*  648 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/*  651 */       return registryGetStringValue(phkKey.getValue(), value);
/*      */     } finally {
/*  653 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/*  654 */       if (rc != 0) {
/*  655 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String registryGetStringValue(WinReg.HKEY hKey, String value) {
/*  670 */     IntByReference lpcbData = new IntByReference();
/*  671 */     IntByReference lpType = new IntByReference();
/*  672 */     int rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, (char[])null, lpcbData);
/*      */     
/*  674 */     if (rc != 0 && rc != 122)
/*      */     {
/*  676 */       throw new Win32Exception(rc);
/*      */     }
/*  678 */     if (lpType.getValue() != 1 && lpType
/*  679 */       .getValue() != 2) {
/*  680 */       throw new RuntimeException("Unexpected registry type " + lpType
/*  681 */           .getValue() + ", expected REG_SZ or REG_EXPAND_SZ");
/*      */     }
/*      */     
/*  684 */     char[] data = new char[lpcbData.getValue()];
/*  685 */     rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, data, lpcbData);
/*      */     
/*  687 */     if (rc != 0 && rc != 122)
/*      */     {
/*  689 */       throw new Win32Exception(rc);
/*      */     }
/*  691 */     return Native.toString(data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String registryGetExpandableStringValue(WinReg.HKEY root, String key, String value) {
/*  707 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/*  708 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, key, 0, 131097, phkKey);
/*      */     
/*  710 */     if (rc != 0) {
/*  711 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/*  714 */       return registryGetExpandableStringValue(phkKey.getValue(), value);
/*      */     } finally {
/*  716 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/*  717 */       if (rc != 0) {
/*  718 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String registryGetExpandableStringValue(WinReg.HKEY hKey, String value) {
/*  733 */     IntByReference lpcbData = new IntByReference();
/*  734 */     IntByReference lpType = new IntByReference();
/*  735 */     int rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, (char[])null, lpcbData);
/*      */     
/*  737 */     if (rc != 0 && rc != 122)
/*      */     {
/*  739 */       throw new Win32Exception(rc);
/*      */     }
/*  741 */     if (lpType.getValue() != 2) {
/*  742 */       throw new RuntimeException("Unexpected registry type " + lpType
/*  743 */           .getValue() + ", expected REG_SZ");
/*      */     }
/*  745 */     char[] data = new char[lpcbData.getValue()];
/*  746 */     rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, data, lpcbData);
/*      */     
/*  748 */     if (rc != 0 && rc != 122)
/*      */     {
/*  750 */       throw new Win32Exception(rc);
/*      */     }
/*  752 */     return Native.toString(data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] registryGetStringArray(WinReg.HKEY root, String key, String value) {
/*  768 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/*  769 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, key, 0, 131097, phkKey);
/*      */     
/*  771 */     if (rc != 0) {
/*  772 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/*  775 */       return registryGetStringArray(phkKey.getValue(), value);
/*      */     } finally {
/*  777 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/*  778 */       if (rc != 0) {
/*  779 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] registryGetStringArray(WinReg.HKEY hKey, String value) {
/*  794 */     IntByReference lpcbData = new IntByReference();
/*  795 */     IntByReference lpType = new IntByReference();
/*  796 */     int rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, (char[])null, lpcbData);
/*      */     
/*  798 */     if (rc != 0 && rc != 122)
/*      */     {
/*  800 */       throw new Win32Exception(rc);
/*      */     }
/*  802 */     if (lpType.getValue() != 7) {
/*  803 */       throw new RuntimeException("Unexpected registry type " + lpType
/*  804 */           .getValue() + ", expected REG_SZ");
/*      */     }
/*  806 */     Memory data = new Memory(lpcbData.getValue());
/*  807 */     rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, (Pointer)data, lpcbData);
/*      */     
/*  809 */     if (rc != 0 && rc != 122)
/*      */     {
/*  811 */       throw new Win32Exception(rc);
/*      */     }
/*  813 */     ArrayList<String> result = new ArrayList<String>();
/*  814 */     int offset = 0;
/*  815 */     while (offset < data.size()) {
/*  816 */       String s = data.getWideString(offset);
/*  817 */       offset += s.length() * Native.WCHAR_SIZE;
/*  818 */       offset += Native.WCHAR_SIZE;
/*  819 */       if (s.length() == 0 && offset == data.size()) {
/*      */         continue;
/*      */       }
/*  822 */       result.add(s);
/*      */     } 
/*      */     
/*  825 */     return result.<String>toArray(new String[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] registryGetBinaryValue(WinReg.HKEY root, String key, String value) {
/*  841 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/*  842 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, key, 0, 131097, phkKey);
/*      */     
/*  844 */     if (rc != 0) {
/*  845 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/*  848 */       return registryGetBinaryValue(phkKey.getValue(), value);
/*      */     } finally {
/*  850 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/*  851 */       if (rc != 0) {
/*  852 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] registryGetBinaryValue(WinReg.HKEY hKey, String value) {
/*  867 */     IntByReference lpcbData = new IntByReference();
/*  868 */     IntByReference lpType = new IntByReference();
/*  869 */     int rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, (char[])null, lpcbData);
/*      */     
/*  871 */     if (rc != 0 && rc != 122)
/*      */     {
/*  873 */       throw new Win32Exception(rc);
/*      */     }
/*  875 */     if (lpType.getValue() != 3) {
/*  876 */       throw new RuntimeException("Unexpected registry type " + lpType
/*  877 */           .getValue() + ", expected REG_BINARY");
/*      */     }
/*  879 */     byte[] data = new byte[lpcbData.getValue()];
/*  880 */     rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, data, lpcbData);
/*      */     
/*  882 */     if (rc != 0 && rc != 122)
/*      */     {
/*  884 */       throw new Win32Exception(rc);
/*      */     }
/*  886 */     return data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int registryGetIntValue(WinReg.HKEY root, String key, String value) {
/*  901 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/*  902 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, key, 0, 131097, phkKey);
/*      */     
/*  904 */     if (rc != 0) {
/*  905 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/*  908 */       return registryGetIntValue(phkKey.getValue(), value);
/*      */     } finally {
/*  910 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/*  911 */       if (rc != 0) {
/*  912 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int registryGetIntValue(WinReg.HKEY hKey, String value) {
/*  927 */     IntByReference lpcbData = new IntByReference();
/*  928 */     IntByReference lpType = new IntByReference();
/*  929 */     int rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, (char[])null, lpcbData);
/*      */     
/*  931 */     if (rc != 0 && rc != 122)
/*      */     {
/*  933 */       throw new Win32Exception(rc);
/*      */     }
/*  935 */     if (lpType.getValue() != 4) {
/*  936 */       throw new RuntimeException("Unexpected registry type " + lpType
/*  937 */           .getValue() + ", expected REG_DWORD");
/*      */     }
/*  939 */     IntByReference data = new IntByReference();
/*  940 */     rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, data, lpcbData);
/*      */     
/*  942 */     if (rc != 0 && rc != 122)
/*      */     {
/*  944 */       throw new Win32Exception(rc);
/*      */     }
/*  946 */     return data.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long registryGetLongValue(WinReg.HKEY root, String key, String value) {
/*  961 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/*  962 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, key, 0, 131097, phkKey);
/*      */     
/*  964 */     if (rc != 0) {
/*  965 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/*  968 */       return registryGetLongValue(phkKey.getValue(), value);
/*      */     } finally {
/*  970 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/*  971 */       if (rc != 0) {
/*  972 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long registryGetLongValue(WinReg.HKEY hKey, String value) {
/*  987 */     IntByReference lpcbData = new IntByReference();
/*  988 */     IntByReference lpType = new IntByReference();
/*  989 */     int rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, (char[])null, lpcbData);
/*      */     
/*  991 */     if (rc != 0 && rc != 122)
/*      */     {
/*  993 */       throw new Win32Exception(rc);
/*      */     }
/*  995 */     if (lpType.getValue() != 11) {
/*  996 */       throw new RuntimeException("Unexpected registry type " + lpType
/*  997 */           .getValue() + ", expected REG_QWORD");
/*      */     }
/*  999 */     LongByReference data = new LongByReference();
/* 1000 */     rc = Advapi32.INSTANCE.RegQueryValueEx(hKey, value, 0, lpType, data, lpcbData);
/*      */     
/* 1002 */     if (rc != 0 && rc != 122)
/*      */     {
/* 1004 */       throw new Win32Exception(rc);
/*      */     }
/* 1006 */     return data.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object registryGetValue(WinReg.HKEY hkKey, String subKey, String lpValueName) {
/* 1023 */     Object result = null;
/* 1024 */     IntByReference lpType = new IntByReference();
/* 1025 */     byte[] lpData = new byte[16383];
/* 1026 */     IntByReference lpcbData = new IntByReference(16383);
/*      */     
/* 1028 */     int rc = Advapi32.INSTANCE.RegGetValue(hkKey, subKey, lpValueName, 65535, lpType, lpData, lpcbData);
/*      */ 
/*      */ 
/*      */     
/* 1032 */     if (lpType.getValue() == 0) {
/* 1033 */       return null;
/*      */     }
/* 1035 */     if (rc != 0 && rc != 122)
/*      */     {
/* 1037 */       throw new Win32Exception(rc);
/*      */     }
/*      */     
/* 1040 */     Memory byteData = new Memory(lpcbData.getValue());
/* 1041 */     byteData.write(0L, lpData, 0, lpcbData.getValue());
/*      */     
/* 1043 */     if (lpType.getValue() == 4) {
/* 1044 */       result = Integer.valueOf(byteData.getInt(0L));
/* 1045 */     } else if (lpType.getValue() == 11) {
/* 1046 */       result = Long.valueOf(byteData.getLong(0L));
/* 1047 */     } else if (lpType.getValue() == 3) {
/* 1048 */       result = byteData.getByteArray(0L, lpcbData.getValue());
/* 1049 */     } else if (lpType.getValue() == 1 || lpType
/* 1050 */       .getValue() == 2) {
/* 1051 */       result = byteData.getWideString(0L);
/*      */     } 
/*      */     
/* 1054 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean registryCreateKey(WinReg.HKEY hKey, String keyName) {
/* 1067 */     WinReg.HKEYByReference phkResult = new WinReg.HKEYByReference();
/* 1068 */     IntByReference lpdwDisposition = new IntByReference();
/* 1069 */     int rc = Advapi32.INSTANCE.RegCreateKeyEx(hKey, keyName, 0, null, 0, 131097, null, phkResult, lpdwDisposition);
/*      */ 
/*      */     
/* 1072 */     if (rc != 0) {
/* 1073 */       throw new Win32Exception(rc);
/*      */     }
/* 1075 */     rc = Advapi32.INSTANCE.RegCloseKey(phkResult.getValue());
/* 1076 */     if (rc != 0) {
/* 1077 */       throw new Win32Exception(rc);
/*      */     }
/* 1079 */     return (1 == lpdwDisposition.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean registryCreateKey(WinReg.HKEY root, String parentPath, String keyName) {
/* 1095 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1096 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, parentPath, 0, 4, phkKey);
/*      */     
/* 1098 */     if (rc != 0) {
/* 1099 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1102 */       return registryCreateKey(phkKey.getValue(), keyName);
/*      */     } finally {
/* 1104 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1105 */       if (rc != 0) {
/* 1106 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetIntValue(WinReg.HKEY hKey, String name, int value) {
/* 1122 */     byte[] data = new byte[4];
/* 1123 */     data[0] = (byte)(value & 0xFF);
/* 1124 */     data[1] = (byte)(value >> 8 & 0xFF);
/* 1125 */     data[2] = (byte)(value >> 16 & 0xFF);
/* 1126 */     data[3] = (byte)(value >> 24 & 0xFF);
/* 1127 */     int rc = Advapi32.INSTANCE.RegSetValueEx(hKey, name, 0, 4, data, 4);
/*      */     
/* 1129 */     if (rc != 0) {
/* 1130 */       throw new Win32Exception(rc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetIntValue(WinReg.HKEY root, String keyPath, String name, int value) {
/* 1148 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1149 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, 131103, phkKey);
/*      */     
/* 1151 */     if (rc != 0) {
/* 1152 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1155 */       registrySetIntValue(phkKey.getValue(), name, value);
/*      */     } finally {
/* 1157 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1158 */       if (rc != 0) {
/* 1159 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetLongValue(WinReg.HKEY hKey, String name, long value) {
/* 1175 */     byte[] data = new byte[8];
/* 1176 */     data[0] = (byte)(int)(value & 0xFFL);
/* 1177 */     data[1] = (byte)(int)(value >> 8L & 0xFFL);
/* 1178 */     data[2] = (byte)(int)(value >> 16L & 0xFFL);
/* 1179 */     data[3] = (byte)(int)(value >> 24L & 0xFFL);
/* 1180 */     data[4] = (byte)(int)(value >> 32L & 0xFFL);
/* 1181 */     data[5] = (byte)(int)(value >> 40L & 0xFFL);
/* 1182 */     data[6] = (byte)(int)(value >> 48L & 0xFFL);
/* 1183 */     data[7] = (byte)(int)(value >> 56L & 0xFFL);
/* 1184 */     int rc = Advapi32.INSTANCE.RegSetValueEx(hKey, name, 0, 11, data, 8);
/*      */     
/* 1186 */     if (rc != 0) {
/* 1187 */       throw new Win32Exception(rc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetLongValue(WinReg.HKEY root, String keyPath, String name, long value) {
/* 1205 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1206 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, 131103, phkKey);
/*      */     
/* 1208 */     if (rc != 0) {
/* 1209 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1212 */       registrySetLongValue(phkKey.getValue(), name, value);
/*      */     } finally {
/* 1214 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1215 */       if (rc != 0) {
/* 1216 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetStringValue(WinReg.HKEY hKey, String name, String value) {
/* 1233 */     char[] data = Native.toCharArray(value);
/* 1234 */     int rc = Advapi32.INSTANCE.RegSetValueEx(hKey, name, 0, 1, data, data.length * Native.WCHAR_SIZE);
/*      */     
/* 1236 */     if (rc != 0) {
/* 1237 */       throw new Win32Exception(rc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetStringValue(WinReg.HKEY root, String keyPath, String name, String value) {
/* 1255 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1256 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, 131103, phkKey);
/*      */     
/* 1258 */     if (rc != 0) {
/* 1259 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1262 */       registrySetStringValue(phkKey.getValue(), name, value);
/*      */     } finally {
/* 1264 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1265 */       if (rc != 0) {
/* 1266 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetExpandableStringValue(WinReg.HKEY hKey, String name, String value) {
/* 1283 */     char[] data = Native.toCharArray(value);
/* 1284 */     int rc = Advapi32.INSTANCE.RegSetValueEx(hKey, name, 0, 2, data, data.length * Native.WCHAR_SIZE);
/*      */     
/* 1286 */     if (rc != 0) {
/* 1287 */       throw new Win32Exception(rc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetExpandableStringValue(WinReg.HKEY root, String keyPath, String name, String value) {
/* 1305 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1306 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, 131103, phkKey);
/*      */     
/* 1308 */     if (rc != 0) {
/* 1309 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1312 */       registrySetExpandableStringValue(phkKey.getValue(), name, value);
/*      */     } finally {
/* 1314 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1315 */       if (rc != 0) {
/* 1316 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetStringArray(WinReg.HKEY hKey, String name, String[] arr) {
/* 1333 */     int size = 0;
/* 1334 */     for (String s : arr) {
/* 1335 */       size += s.length() * Native.WCHAR_SIZE;
/* 1336 */       size += Native.WCHAR_SIZE;
/*      */     } 
/* 1338 */     size += Native.WCHAR_SIZE;
/*      */     
/* 1340 */     int offset = 0;
/* 1341 */     Memory data = new Memory(size);
/* 1342 */     for (String s : arr) {
/* 1343 */       data.setWideString(offset, s);
/* 1344 */       offset += s.length() * Native.WCHAR_SIZE;
/* 1345 */       offset += Native.WCHAR_SIZE;
/*      */     } 
/* 1347 */     for (int i = 0; i < Native.WCHAR_SIZE; i++) {
/* 1348 */       data.setByte(offset++, (byte)0);
/*      */     }
/*      */     
/* 1351 */     int rc = Advapi32.INSTANCE.RegSetValueEx(hKey, name, 0, 7, data
/* 1352 */         .getByteArray(0L, size), size);
/*      */     
/* 1354 */     if (rc != 0) {
/* 1355 */       throw new Win32Exception(rc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetStringArray(WinReg.HKEY root, String keyPath, String name, String[] arr) {
/* 1373 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1374 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, 131103, phkKey);
/*      */     
/* 1376 */     if (rc != 0) {
/* 1377 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1380 */       registrySetStringArray(phkKey.getValue(), name, arr);
/*      */     } finally {
/* 1382 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1383 */       if (rc != 0) {
/* 1384 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetBinaryValue(WinReg.HKEY hKey, String name, byte[] data) {
/* 1401 */     int rc = Advapi32.INSTANCE.RegSetValueEx(hKey, name, 0, 3, data, data.length);
/*      */     
/* 1403 */     if (rc != 0) {
/* 1404 */       throw new Win32Exception(rc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registrySetBinaryValue(WinReg.HKEY root, String keyPath, String name, byte[] data) {
/* 1422 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1423 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, 131103, phkKey);
/*      */     
/* 1425 */     if (rc != 0) {
/* 1426 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1429 */       registrySetBinaryValue(phkKey.getValue(), name, data);
/*      */     } finally {
/* 1431 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1432 */       if (rc != 0) {
/* 1433 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registryDeleteKey(WinReg.HKEY hKey, String keyName) {
/* 1447 */     int rc = Advapi32.INSTANCE.RegDeleteKey(hKey, keyName);
/* 1448 */     if (rc != 0) {
/* 1449 */       throw new Win32Exception(rc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registryDeleteKey(WinReg.HKEY root, String keyPath, String keyName) {
/* 1465 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1466 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, 131103, phkKey);
/*      */     
/* 1468 */     if (rc != 0) {
/* 1469 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1472 */       registryDeleteKey(phkKey.getValue(), keyName);
/*      */     } finally {
/* 1474 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1475 */       if (rc != 0) {
/* 1476 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registryDeleteValue(WinReg.HKEY hKey, String valueName) {
/* 1490 */     int rc = Advapi32.INSTANCE.RegDeleteValue(hKey, valueName);
/* 1491 */     if (rc != 0) {
/* 1492 */       throw new Win32Exception(rc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registryDeleteValue(WinReg.HKEY root, String keyPath, String valueName) {
/* 1508 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1509 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, 131103, phkKey);
/*      */     
/* 1511 */     if (rc != 0) {
/* 1512 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1515 */       registryDeleteValue(phkKey.getValue(), valueName);
/*      */     } finally {
/* 1517 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1518 */       if (rc != 0) {
/* 1519 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] registryGetKeys(WinReg.HKEY hKey) {
/* 1532 */     IntByReference lpcSubKeys = new IntByReference();
/* 1533 */     IntByReference lpcMaxSubKeyLen = new IntByReference();
/*      */     
/* 1535 */     int rc = Advapi32.INSTANCE.RegQueryInfoKey(hKey, null, null, null, lpcSubKeys, lpcMaxSubKeyLen, null, null, null, null, null, null);
/*      */     
/* 1537 */     if (rc != 0) {
/* 1538 */       throw new Win32Exception(rc);
/*      */     }
/* 1540 */     ArrayList<String> keys = new ArrayList<String>(lpcSubKeys.getValue());
/* 1541 */     char[] name = new char[lpcMaxSubKeyLen.getValue() + 1];
/* 1542 */     for (int i = 0; i < lpcSubKeys.getValue(); i++) {
/*      */       
/* 1544 */       IntByReference lpcchValueName = new IntByReference(lpcMaxSubKeyLen.getValue() + 1);
/* 1545 */       rc = Advapi32.INSTANCE.RegEnumKeyEx(hKey, i, name, lpcchValueName, null, null, null, null);
/*      */       
/* 1547 */       if (rc != 0) {
/* 1548 */         throw new Win32Exception(rc);
/*      */       }
/* 1550 */       keys.add(Native.toString(name));
/*      */     } 
/* 1552 */     return keys.<String>toArray(new String[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] registryGetKeys(WinReg.HKEY root, String keyPath) {
/* 1565 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1566 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, 131097, phkKey);
/*      */     
/* 1568 */     if (rc != 0) {
/* 1569 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1572 */       return registryGetKeys(phkKey.getValue());
/*      */     } finally {
/* 1574 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1575 */       if (rc != 0) {
/* 1576 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WinReg.HKEYByReference registryGetKey(WinReg.HKEY root, String keyPath, int samDesired) {
/* 1596 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1597 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, samDesired, phkKey);
/*      */     
/* 1599 */     if (rc != 0) {
/* 1600 */       throw new Win32Exception(rc);
/*      */     }
/*      */     
/* 1603 */     return phkKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registryCloseKey(WinReg.HKEY hKey) {
/* 1613 */     int rc = Advapi32.INSTANCE.RegCloseKey(hKey);
/* 1614 */     if (rc != 0) {
/* 1615 */       throw new Win32Exception(rc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TreeMap<String, Object> registryGetValues(WinReg.HKEY hKey) {
/* 1627 */     IntByReference lpcValues = new IntByReference();
/* 1628 */     IntByReference lpcMaxValueNameLen = new IntByReference();
/* 1629 */     IntByReference lpcMaxValueLen = new IntByReference();
/* 1630 */     int rc = Advapi32.INSTANCE.RegQueryInfoKey(hKey, null, null, null, null, null, null, lpcValues, lpcMaxValueNameLen, lpcMaxValueLen, null, null);
/*      */ 
/*      */     
/* 1633 */     if (rc != 0) {
/* 1634 */       throw new Win32Exception(rc);
/*      */     }
/* 1636 */     TreeMap<String, Object> keyValues = new TreeMap<String, Object>();
/* 1637 */     char[] name = new char[lpcMaxValueNameLen.getValue() + 1];
/* 1638 */     byte[] data = new byte[lpcMaxValueLen.getValue()];
/* 1639 */     for (int i = 0; i < lpcValues.getValue(); i++) {
/*      */       
/* 1641 */       IntByReference lpcchValueName = new IntByReference(lpcMaxValueNameLen.getValue() + 1);
/*      */       
/* 1643 */       IntByReference lpcbData = new IntByReference(lpcMaxValueLen.getValue());
/* 1644 */       IntByReference lpType = new IntByReference();
/* 1645 */       rc = Advapi32.INSTANCE.RegEnumValue(hKey, i, name, lpcchValueName, null, lpType, data, lpcbData);
/*      */       
/* 1647 */       if (rc != 0) {
/* 1648 */         throw new Win32Exception(rc);
/*      */       }
/*      */       
/* 1651 */       String nameString = Native.toString(name);
/*      */       
/* 1653 */       if (lpcbData.getValue() == 0) {
/* 1654 */         switch (lpType.getValue()) {
/*      */           case 3:
/* 1656 */             keyValues.put(nameString, new byte[0]);
/*      */             break;
/*      */           
/*      */           case 1:
/*      */           case 2:
/* 1661 */             keyValues.put(nameString, new char[0]);
/*      */             break;
/*      */           
/*      */           case 7:
/* 1665 */             keyValues.put(nameString, new String[0]);
/*      */             break;
/*      */           
/*      */           case 0:
/* 1669 */             keyValues.put(nameString, null);
/*      */             break;
/*      */           
/*      */           default:
/* 1673 */             throw new RuntimeException("Unsupported empty type: " + lpType
/* 1674 */                 .getValue());
/*      */         } 
/*      */       } else {
/*      */         Memory stringData; ArrayList<String> result;
/*      */         int offset;
/* 1679 */         Memory byteData = new Memory(lpcbData.getValue());
/* 1680 */         byteData.write(0L, data, 0, lpcbData.getValue());
/*      */         
/* 1682 */         switch (lpType.getValue()) {
/*      */           case 11:
/* 1684 */             keyValues.put(nameString, Long.valueOf(byteData.getLong(0L)));
/*      */             break;
/*      */           
/*      */           case 4:
/* 1688 */             keyValues.put(nameString, Integer.valueOf(byteData.getInt(0L)));
/*      */             break;
/*      */           
/*      */           case 1:
/*      */           case 2:
/* 1693 */             keyValues.put(nameString, byteData.getWideString(0L));
/*      */             break;
/*      */           
/*      */           case 3:
/* 1697 */             keyValues.put(nameString, byteData
/* 1698 */                 .getByteArray(0L, lpcbData.getValue()));
/*      */             break;
/*      */           
/*      */           case 7:
/* 1702 */             stringData = new Memory(lpcbData.getValue());
/* 1703 */             stringData.write(0L, data, 0, lpcbData.getValue());
/* 1704 */             result = new ArrayList<String>();
/* 1705 */             offset = 0;
/* 1706 */             while (offset < stringData.size()) {
/* 1707 */               String s = stringData.getWideString(offset);
/* 1708 */               offset += s.length() * Native.WCHAR_SIZE;
/* 1709 */               offset += Native.WCHAR_SIZE;
/* 1710 */               if (s.length() == 0 && offset == stringData.size()) {
/*      */                 continue;
/*      */               }
/* 1713 */               result.add(s);
/*      */             } 
/*      */             
/* 1716 */             keyValues.put(nameString, result.toArray(new String[0]));
/*      */             break;
/*      */           
/*      */           default:
/* 1720 */             throw new RuntimeException("Unsupported type: " + lpType
/* 1721 */                 .getValue());
/*      */         } 
/*      */       } 
/* 1724 */     }  return keyValues;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TreeMap<String, Object> registryGetValues(WinReg.HKEY root, String keyPath) {
/* 1738 */     WinReg.HKEYByReference phkKey = new WinReg.HKEYByReference();
/* 1739 */     int rc = Advapi32.INSTANCE.RegOpenKeyEx(root, keyPath, 0, 131097, phkKey);
/*      */     
/* 1741 */     if (rc != 0) {
/* 1742 */       throw new Win32Exception(rc);
/*      */     }
/*      */     try {
/* 1745 */       return registryGetValues(phkKey.getValue());
/*      */     } finally {
/* 1747 */       rc = Advapi32.INSTANCE.RegCloseKey(phkKey.getValue());
/* 1748 */       if (rc != 0) {
/* 1749 */         throw new Win32Exception(rc);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InfoKey registryQueryInfoKey(WinReg.HKEY hKey, int lpcbSecurityDescriptor) {
/* 1766 */     InfoKey infoKey = new InfoKey(hKey, lpcbSecurityDescriptor);
/* 1767 */     int rc = Advapi32.INSTANCE.RegQueryInfoKey(hKey, infoKey.lpClass, infoKey.lpcClass, null, infoKey.lpcSubKeys, infoKey.lpcMaxSubKeyLen, infoKey.lpcMaxClassLen, infoKey.lpcValues, infoKey.lpcMaxValueNameLen, infoKey.lpcMaxValueLen, infoKey.lpcbSecurityDescriptor, infoKey.lpftLastWriteTime);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1774 */     if (rc != 0) {
/* 1775 */       throw new Win32Exception(rc);
/*      */     }
/*      */     
/* 1778 */     return infoKey;
/*      */   }
/*      */   
/*      */   public static class InfoKey {
/*      */     public WinReg.HKEY hKey;
/* 1783 */     public char[] lpClass = new char[260];
/* 1784 */     public IntByReference lpcClass = new IntByReference(260);
/* 1785 */     public IntByReference lpcSubKeys = new IntByReference();
/* 1786 */     public IntByReference lpcMaxSubKeyLen = new IntByReference();
/* 1787 */     public IntByReference lpcMaxClassLen = new IntByReference();
/* 1788 */     public IntByReference lpcValues = new IntByReference();
/* 1789 */     public IntByReference lpcMaxValueNameLen = new IntByReference();
/* 1790 */     public IntByReference lpcMaxValueLen = new IntByReference();
/* 1791 */     public IntByReference lpcbSecurityDescriptor = new IntByReference();
/* 1792 */     public WinBase.FILETIME lpftLastWriteTime = new WinBase.FILETIME();
/*      */ 
/*      */     
/*      */     public InfoKey() {}
/*      */     
/*      */     public InfoKey(WinReg.HKEY hKey, int securityDescriptor) {
/* 1798 */       this.hKey = hKey;
/* 1799 */       this.lpcbSecurityDescriptor = new IntByReference(securityDescriptor);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static EnumKey registryRegEnumKey(WinReg.HKEY hKey, int dwIndex) {
/* 1813 */     EnumKey enumKey = new EnumKey(hKey, dwIndex);
/* 1814 */     int rc = Advapi32.INSTANCE.RegEnumKeyEx(hKey, enumKey.dwIndex, enumKey.lpName, enumKey.lpcName, null, enumKey.lpClass, enumKey.lpcbClass, enumKey.lpftLastWriteTime);
/*      */ 
/*      */ 
/*      */     
/* 1818 */     if (rc != 0) {
/* 1819 */       throw new Win32Exception(rc);
/*      */     }
/*      */     
/* 1822 */     return enumKey;
/*      */   }
/*      */   
/*      */   public static class EnumKey {
/*      */     public WinReg.HKEY hKey;
/* 1827 */     public int dwIndex = 0;
/* 1828 */     public char[] lpName = new char[255];
/* 1829 */     public IntByReference lpcName = new IntByReference(255);
/*      */     
/* 1831 */     public char[] lpClass = new char[255];
/* 1832 */     public IntByReference lpcbClass = new IntByReference(255);
/*      */     
/* 1834 */     public WinBase.FILETIME lpftLastWriteTime = new WinBase.FILETIME();
/*      */ 
/*      */     
/*      */     public EnumKey() {}
/*      */     
/*      */     public EnumKey(WinReg.HKEY hKey, int dwIndex) {
/* 1840 */       this.hKey = hKey;
/* 1841 */       this.dwIndex = dwIndex;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getEnvironmentBlock(Map<String, String> environment) {
/* 1856 */     StringBuilder out = new StringBuilder(environment.size() * 32);
/* 1857 */     for (Map.Entry<String, String> entry : environment.entrySet()) {
/* 1858 */       String key = entry.getKey(), value = entry.getValue();
/* 1859 */       if (value != null) {
/* 1860 */         out.append(key).append("=").append(value).append(false);
/*      */       }
/*      */     } 
/* 1863 */     return out.append(false).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public enum EventLogType
/*      */   {
/* 1870 */     Error, Warning, Informational, AuditSuccess, AuditFailure;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EventLogRecord
/*      */   {
/* 1877 */     private WinNT.EVENTLOGRECORD _record = null;
/*      */ 
/*      */     
/*      */     private String _source;
/*      */     
/*      */     private byte[] _data;
/*      */     
/*      */     private String[] _strings;
/*      */ 
/*      */     
/*      */     public WinNT.EVENTLOGRECORD getRecord() {
/* 1888 */       return this._record;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getEventId() {
/* 1897 */       return this._record.EventID.intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getSource() {
/* 1906 */       return this._source;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getStatusCode() {
/* 1915 */       return this._record.EventID.intValue() & 0xFFFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRecordNumber() {
/* 1926 */       return this._record.RecordNumber.intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getLength() {
/* 1935 */       return this._record.Length.intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String[] getStrings() {
/* 1944 */       return this._strings;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Advapi32Util.EventLogType getType() {
/* 1953 */       switch (this._record.EventType.intValue()) {
/*      */         case 0:
/*      */         case 4:
/* 1956 */           return Advapi32Util.EventLogType.Informational;
/*      */         case 16:
/* 1958 */           return Advapi32Util.EventLogType.AuditFailure;
/*      */         case 8:
/* 1960 */           return Advapi32Util.EventLogType.AuditSuccess;
/*      */         case 1:
/* 1962 */           return Advapi32Util.EventLogType.Error;
/*      */         case 2:
/* 1964 */           return Advapi32Util.EventLogType.Warning;
/*      */       } 
/* 1966 */       throw new RuntimeException("Invalid type: " + this._record.EventType
/* 1967 */           .intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] getData() {
/* 1977 */       return this._data;
/*      */     }
/*      */     
/*      */     public EventLogRecord(Pointer pevlr) {
/* 1981 */       this._record = new WinNT.EVENTLOGRECORD(pevlr);
/* 1982 */       this._source = pevlr.getWideString(this._record.size());
/*      */       
/* 1984 */       if (this._record.DataLength.intValue() > 0) {
/* 1985 */         this._data = pevlr.getByteArray(this._record.DataOffset.intValue(), this._record.DataLength
/* 1986 */             .intValue());
/*      */       }
/*      */       
/* 1989 */       if (this._record.NumStrings.intValue() > 0) {
/* 1990 */         ArrayList<String> strings = new ArrayList<String>();
/* 1991 */         int count = this._record.NumStrings.intValue();
/* 1992 */         long offset = this._record.StringOffset.intValue();
/* 1993 */         while (count > 0) {
/* 1994 */           String s = pevlr.getWideString(offset);
/* 1995 */           strings.add(s);
/* 1996 */           offset += (s.length() * Native.WCHAR_SIZE);
/* 1997 */           offset += Native.WCHAR_SIZE;
/* 1998 */           count--;
/*      */         } 
/* 2000 */         this._strings = strings.<String>toArray(new String[0]);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EventLogIterator
/*      */     implements Iterable<EventLogRecord>, Iterator<EventLogRecord>
/*      */   {
/* 2011 */     private WinNT.HANDLE _h = null;
/* 2012 */     private Memory _buffer = new Memory(65536L);
/*      */     
/*      */     private boolean _done = false;
/* 2015 */     private int _dwRead = 0;
/*      */     
/* 2017 */     private Pointer _pevlr = null;
/* 2018 */     private int _flags = 4;
/*      */     
/*      */     public EventLogIterator(String sourceName) {
/* 2021 */       this(null, sourceName, 4);
/*      */     }
/*      */     
/*      */     public EventLogIterator(String serverName, String sourceName, int flags) {
/* 2025 */       this._flags = flags;
/* 2026 */       this._h = Advapi32.INSTANCE.OpenEventLog(serverName, sourceName);
/* 2027 */       if (this._h == null) {
/* 2028 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean read() {
/* 2034 */       if (this._done || this._dwRead > 0) {
/* 2035 */         return false;
/*      */       }
/*      */       
/* 2038 */       IntByReference pnBytesRead = new IntByReference();
/* 2039 */       IntByReference pnMinNumberOfBytesNeeded = new IntByReference();
/*      */ 
/*      */       
/* 2042 */       if (!Advapi32.INSTANCE.ReadEventLog(this._h, 0x1 | this._flags, 0, (Pointer)this._buffer, 
/* 2043 */           (int)this._buffer.size(), pnBytesRead, pnMinNumberOfBytesNeeded)) {
/*      */ 
/*      */         
/* 2046 */         int rc = Kernel32.INSTANCE.GetLastError();
/*      */ 
/*      */         
/* 2049 */         if (rc == 122) {
/* 2050 */           this._buffer = new Memory(pnMinNumberOfBytesNeeded.getValue());
/*      */           
/* 2052 */           if (!Advapi32.INSTANCE.ReadEventLog(this._h, 0x1 | this._flags, 0, (Pointer)this._buffer, 
/*      */               
/* 2054 */               (int)this._buffer.size(), pnBytesRead, pnMinNumberOfBytesNeeded))
/*      */           {
/* 2056 */             throw new Win32Exception(Kernel32.INSTANCE
/* 2057 */                 .GetLastError());
/*      */           }
/*      */         } else {
/*      */           
/* 2061 */           close();
/* 2062 */           if (rc != 38) {
/* 2063 */             throw new Win32Exception(rc);
/*      */           }
/* 2065 */           return false;
/*      */         } 
/*      */       } 
/*      */       
/* 2069 */       this._dwRead = pnBytesRead.getValue();
/* 2070 */       this._pevlr = (Pointer)this._buffer;
/* 2071 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() {
/* 2079 */       this._done = true;
/* 2080 */       if (this._h != null) {
/* 2081 */         if (!Advapi32.INSTANCE.CloseEventLog(this._h)) {
/* 2082 */           throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */         }
/* 2084 */         this._h = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Iterator<Advapi32Util.EventLogRecord> iterator() {
/* 2092 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 2099 */       read();
/* 2100 */       return !this._done;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Advapi32Util.EventLogRecord next() {
/* 2107 */       read();
/* 2108 */       Advapi32Util.EventLogRecord record = new Advapi32Util.EventLogRecord(this._pevlr);
/* 2109 */       this._dwRead -= record.getLength();
/* 2110 */       this._pevlr = this._pevlr.share(record.getLength());
/* 2111 */       return record;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove() {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static WinNT.ACCESS_ACEStructure[] getFileSecurity(String fileName, boolean compact) {
/* 2123 */     int infoType = 4;
/* 2124 */     int nLength = 1024;
/* 2125 */     boolean repeat = false;
/* 2126 */     Memory memory = null;
/*      */     
/*      */     do {
/* 2129 */       repeat = false;
/* 2130 */       memory = new Memory(nLength);
/* 2131 */       IntByReference lpnSize = new IntByReference();
/* 2132 */       boolean succeded = Advapi32.INSTANCE.GetFileSecurity(fileName, infoType, (Pointer)memory, nLength, lpnSize);
/*      */ 
/*      */       
/* 2135 */       if (!succeded) {
/* 2136 */         int lastError = Kernel32.INSTANCE.GetLastError();
/* 2137 */         memory.clear();
/* 2138 */         if (122 != lastError) {
/* 2139 */           throw new Win32Exception(lastError);
/*      */         }
/*      */       } 
/* 2142 */       int lengthNeeded = lpnSize.getValue();
/* 2143 */       if (nLength >= lengthNeeded)
/* 2144 */         continue;  repeat = true;
/* 2145 */       nLength = lengthNeeded;
/* 2146 */       memory.clear();
/*      */     }
/* 2148 */     while (repeat);
/*      */     
/* 2150 */     WinNT.SECURITY_DESCRIPTOR_RELATIVE sdr = new WinNT.SECURITY_DESCRIPTOR_RELATIVE((Pointer)memory);
/*      */     
/* 2152 */     memory.clear();
/* 2153 */     WinNT.ACL dacl = sdr.getDiscretionaryACL();
/* 2154 */     WinNT.ACCESS_ACEStructure[] aceStructures = dacl.getACEStructures();
/*      */     
/* 2156 */     if (compact) {
/* 2157 */       Map<String, WinNT.ACCESS_ACEStructure> aceMap = new HashMap<String, WinNT.ACCESS_ACEStructure>();
/* 2158 */       for (WinNT.ACCESS_ACEStructure aceStructure : aceStructures) {
/* 2159 */         boolean inherted = ((aceStructure.AceFlags & 0x1F) != 0);
/*      */         
/* 2161 */         String key = aceStructure.getSidString() + "/" + inherted + "/" + aceStructure.getClass().getName();
/* 2162 */         WinNT.ACCESS_ACEStructure aceStructure2 = aceMap.get(key);
/* 2163 */         if (aceStructure2 != null) {
/* 2164 */           int accessMask = aceStructure2.Mask;
/* 2165 */           accessMask |= aceStructure.Mask;
/* 2166 */           aceStructure2.Mask = accessMask;
/*      */         } else {
/* 2168 */           aceMap.put(key, aceStructure);
/*      */         } 
/*      */       } 
/* 2171 */       return (WinNT.ACCESS_ACEStructure[])aceMap.values().toArray(
/* 2172 */           (Object[])new WinNT.ACCESS_ACEStructure[aceMap.size()]);
/*      */     } 
/* 2174 */     return aceStructures;
/*      */   }
/*      */   
/*      */   public enum AccessCheckPermission {
/* 2178 */     READ(-2147483648),
/* 2179 */     WRITE(1073741824),
/* 2180 */     EXECUTE(536870912);
/*      */     
/*      */     final int code;
/*      */     
/*      */     AccessCheckPermission(int code) {
/* 2185 */       this.code = code;
/*      */     }
/*      */     
/*      */     public int getCode() {
/* 2189 */       return this.code;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static Memory getSecurityDescriptorForFile(String absoluteFilePath) {
/* 2195 */     int infoType = 7;
/*      */ 
/*      */     
/* 2198 */     IntByReference lpnSize = new IntByReference();
/* 2199 */     boolean succeeded = Advapi32.INSTANCE.GetFileSecurity(absoluteFilePath, 7, null, 0, lpnSize);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2205 */     if (!succeeded) {
/* 2206 */       int lastError = Kernel32.INSTANCE.GetLastError();
/* 2207 */       if (122 != lastError) {
/* 2208 */         throw new Win32Exception(lastError);
/*      */       }
/*      */     } 
/*      */     
/* 2212 */     int nLength = lpnSize.getValue();
/* 2213 */     Memory securityDescriptorMemoryPointer = new Memory(nLength);
/* 2214 */     succeeded = Advapi32.INSTANCE.GetFileSecurity(absoluteFilePath, 7, (Pointer)securityDescriptorMemoryPointer, nLength, lpnSize);
/*      */ 
/*      */     
/* 2217 */     if (!succeeded) {
/* 2218 */       securityDescriptorMemoryPointer.clear();
/* 2219 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     } 
/*      */     
/* 2222 */     return securityDescriptorMemoryPointer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Memory getSecurityDescriptorForObject(String absoluteObjectPath, int objectType, boolean getSACL) {
/* 2240 */     int infoType = 0x7 | (getSACL ? 8 : 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2245 */     PointerByReference ppSecurityDescriptor = new PointerByReference();
/*      */     
/* 2247 */     int lastError = Advapi32.INSTANCE.GetNamedSecurityInfo(absoluteObjectPath, objectType, infoType, null, null, null, null, ppSecurityDescriptor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2257 */     if (lastError != 0) {
/* 2258 */       throw new Win32Exception(lastError);
/*      */     }
/*      */     
/* 2261 */     int nLength = Advapi32.INSTANCE.GetSecurityDescriptorLength(ppSecurityDescriptor.getValue());
/* 2262 */     Memory memory = new Memory(nLength);
/* 2263 */     Pointer secValue = ppSecurityDescriptor.getValue();
/*      */     try {
/* 2265 */       byte[] data = secValue.getByteArray(0L, nLength);
/* 2266 */       memory.write(0L, data, 0, nLength);
/* 2267 */       return memory;
/*      */     } finally {
/* 2269 */       Kernel32Util.freeLocalMemory(secValue);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setSecurityDescriptorForObject(String absoluteObjectPath, int objectType, WinNT.SECURITY_DESCRIPTOR_RELATIVE securityDescriptor, boolean setOwner, boolean setGroup, boolean setDACL, boolean setSACL, boolean setDACLProtectedStatus, boolean setSACLProtectedStatus) {
/* 2313 */     WinNT.PSID psidOwner = securityDescriptor.getOwner();
/* 2314 */     WinNT.PSID psidGroup = securityDescriptor.getGroup();
/* 2315 */     WinNT.ACL dacl = securityDescriptor.getDiscretionaryACL();
/* 2316 */     WinNT.ACL sacl = securityDescriptor.getSystemACL();
/*      */     
/* 2318 */     int infoType = 0;
/*      */     
/* 2320 */     if (setOwner) {
/* 2321 */       if (psidOwner == null)
/* 2322 */         throw new IllegalArgumentException("SECURITY_DESCRIPTOR_RELATIVE does not contain owner"); 
/* 2323 */       if (!Advapi32.INSTANCE.IsValidSid(psidOwner))
/* 2324 */         throw new IllegalArgumentException("Owner PSID is invalid"); 
/* 2325 */       infoType |= 0x1;
/*      */     } 
/*      */     
/* 2328 */     if (setGroup) {
/* 2329 */       if (psidGroup == null)
/* 2330 */         throw new IllegalArgumentException("SECURITY_DESCRIPTOR_RELATIVE does not contain group"); 
/* 2331 */       if (!Advapi32.INSTANCE.IsValidSid(psidGroup))
/* 2332 */         throw new IllegalArgumentException("Group PSID is invalid"); 
/* 2333 */       infoType |= 0x2;
/*      */     } 
/*      */     
/* 2336 */     if (setDACL) {
/* 2337 */       if (dacl == null)
/* 2338 */         throw new IllegalArgumentException("SECURITY_DESCRIPTOR_RELATIVE does not contain DACL"); 
/* 2339 */       if (!Advapi32.INSTANCE.IsValidAcl(dacl.getPointer()))
/* 2340 */         throw new IllegalArgumentException("DACL is invalid"); 
/* 2341 */       infoType |= 0x4;
/*      */     } 
/*      */     
/* 2344 */     if (setSACL) {
/* 2345 */       if (sacl == null)
/* 2346 */         throw new IllegalArgumentException("SECURITY_DESCRIPTOR_RELATIVE does not contain SACL"); 
/* 2347 */       if (!Advapi32.INSTANCE.IsValidAcl(sacl.getPointer()))
/* 2348 */         throw new IllegalArgumentException("SACL is invalid"); 
/* 2349 */       infoType |= 0x8;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2357 */     if (setDACLProtectedStatus) {
/* 2358 */       if ((securityDescriptor.Control & 0x1000) != 0) {
/* 2359 */         infoType |= Integer.MIN_VALUE;
/*      */       }
/* 2361 */       else if ((securityDescriptor.Control & 0x1000) == 0) {
/* 2362 */         infoType |= 0x20000000;
/*      */       } 
/*      */     }
/*      */     
/* 2366 */     if (setSACLProtectedStatus) {
/* 2367 */       if ((securityDescriptor.Control & 0x2000) != 0) {
/* 2368 */         infoType |= 0x40000000;
/*      */       }
/* 2370 */       else if ((securityDescriptor.Control & 0x2000) == 0) {
/* 2371 */         infoType |= 0x10000000;
/*      */       } 
/*      */     }
/*      */     
/* 2375 */     int lastError = Advapi32.INSTANCE.SetNamedSecurityInfo(absoluteObjectPath, objectType, infoType, setOwner ? psidOwner
/*      */ 
/*      */ 
/*      */         
/* 2379 */         .getPointer() : null, setGroup ? psidGroup
/* 2380 */         .getPointer() : null, setDACL ? dacl
/* 2381 */         .getPointer() : null, setSACL ? sacl
/* 2382 */         .getPointer() : null);
/*      */     
/* 2384 */     if (lastError != 0) {
/* 2385 */       throw new Win32Exception(lastError);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean accessCheck(File file, AccessCheckPermission permissionToCheck) {
/* 2396 */     Memory securityDescriptorMemoryPointer = getSecurityDescriptorForFile(file.getAbsolutePath().replace('/', '\\'));
/*      */     
/* 2398 */     WinNT.HANDLEByReference openedAccessToken = new WinNT.HANDLEByReference();
/* 2399 */     WinNT.HANDLEByReference duplicatedToken = new WinNT.HANDLEByReference();
/* 2400 */     Win32Exception err = null;
/*      */     try {
/* 2402 */       int desireAccess = 131086;
/* 2403 */       WinNT.HANDLE hProcess = Kernel32.INSTANCE.GetCurrentProcess();
/* 2404 */       if (!Advapi32.INSTANCE.OpenProcessToken(hProcess, desireAccess, openedAccessToken)) {
/* 2405 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/* 2408 */       if (!Advapi32.INSTANCE.DuplicateToken(openedAccessToken.getValue(), 2, duplicatedToken)) {
/* 2409 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/* 2412 */       WinNT.GENERIC_MAPPING mapping = new WinNT.GENERIC_MAPPING();
/* 2413 */       mapping.genericRead = new WinDef.DWORD(1179785L);
/* 2414 */       mapping.genericWrite = new WinDef.DWORD(1179926L);
/* 2415 */       mapping.genericExecute = new WinDef.DWORD(1179808L);
/* 2416 */       mapping.genericAll = new WinDef.DWORD(2032127L);
/*      */       
/* 2418 */       WinDef.DWORDByReference rights = new WinDef.DWORDByReference(new WinDef.DWORD(permissionToCheck.getCode()));
/* 2419 */       Advapi32.INSTANCE.MapGenericMask(rights, mapping);
/*      */       
/* 2421 */       WinNT.PRIVILEGE_SET privileges = new WinNT.PRIVILEGE_SET(1);
/* 2422 */       privileges.PrivilegeCount = new WinDef.DWORD(0L);
/* 2423 */       WinDef.DWORDByReference privilegeLength = new WinDef.DWORDByReference(new WinDef.DWORD(privileges.size()));
/*      */       
/* 2425 */       WinDef.DWORDByReference grantedAccess = new WinDef.DWORDByReference();
/* 2426 */       WinDef.BOOLByReference result = new WinDef.BOOLByReference();
/* 2427 */       if (!Advapi32.INSTANCE.AccessCheck((Pointer)securityDescriptorMemoryPointer, duplicatedToken
/* 2428 */           .getValue(), rights
/* 2429 */           .getValue(), mapping, privileges, privilegeLength, grantedAccess, result))
/*      */       {
/*      */         
/* 2432 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/* 2435 */       return result.getValue().booleanValue();
/* 2436 */     } catch (Win32Exception e) {
/* 2437 */       err = e;
/* 2438 */       throw err;
/*      */     } finally {
/*      */       try {
/* 2441 */         Kernel32Util.closeHandleRefs(new WinNT.HANDLEByReference[] { openedAccessToken, duplicatedToken });
/* 2442 */       } catch (Win32Exception e) {
/* 2443 */         if (err == null) {
/* 2444 */           err = e;
/*      */         } else {
/* 2446 */           err.addSuppressedReflected((Throwable)e);
/*      */         } 
/*      */       } 
/*      */       
/* 2450 */       if (securityDescriptorMemoryPointer != null) {
/* 2451 */         securityDescriptorMemoryPointer.clear();
/*      */       }
/*      */       
/* 2454 */       if (err != null) {
/* 2455 */         throw err;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WinNT.SECURITY_DESCRIPTOR_RELATIVE getFileSecurityDescriptor(File file, boolean getSACL) {
/* 2471 */     WinNT.SECURITY_DESCRIPTOR_RELATIVE sdr = null;
/* 2472 */     Memory securityDesc = getSecurityDescriptorForObject(file.getAbsolutePath().replaceAll("/", "\\"), 1, getSACL);
/* 2473 */     sdr = new WinNT.SECURITY_DESCRIPTOR_RELATIVE((Pointer)securityDesc);
/* 2474 */     return sdr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFileSecurityDescriptor(File file, WinNT.SECURITY_DESCRIPTOR_RELATIVE securityDescriptor, boolean setOwner, boolean setGroup, boolean setDACL, boolean setSACL, boolean setDACLProtectedStatus, boolean setSACLProtectedStatus) {
/* 2506 */     setSecurityDescriptorForObject(file.getAbsolutePath().replaceAll("/", "\\"), 1, securityDescriptor, setOwner, setGroup, setDACL, setSACL, setDACLProtectedStatus, setSACLProtectedStatus);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void encryptFile(File file) {
/* 2516 */     String lpFileName = file.getAbsolutePath();
/* 2517 */     if (!Advapi32.INSTANCE.EncryptFile(lpFileName)) {
/* 2518 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decryptFile(File file) {
/* 2529 */     String lpFileName = file.getAbsolutePath();
/* 2530 */     if (!Advapi32.INSTANCE.DecryptFile(lpFileName, new WinDef.DWORD(0L))) {
/* 2531 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int fileEncryptionStatus(File file) {
/* 2543 */     WinDef.DWORDByReference status = new WinDef.DWORDByReference();
/* 2544 */     String lpFileName = file.getAbsolutePath();
/* 2545 */     if (!Advapi32.INSTANCE.FileEncryptionStatus(lpFileName, status)) {
/* 2546 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/* 2548 */     return status.getValue().intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void disableEncryption(File directory, boolean disable) {
/* 2561 */     String dirPath = directory.getAbsolutePath();
/* 2562 */     if (!Advapi32.INSTANCE.EncryptionDisable(dirPath, disable)) {
/* 2563 */       throw new Win32Exception(Native.getLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void backupEncryptedFile(File src, File destDir) {
/* 2581 */     if (!destDir.isDirectory()) {
/* 2582 */       throw new IllegalArgumentException("destDir must be a directory.");
/*      */     }
/*      */     
/* 2585 */     WinDef.ULONG readFlag = new WinDef.ULONG(0L);
/* 2586 */     WinDef.ULONG writeFlag = new WinDef.ULONG(1L);
/*      */     
/* 2588 */     if (src.isDirectory()) {
/* 2589 */       writeFlag.setValue(3L);
/*      */     }
/*      */ 
/*      */     
/* 2593 */     String srcFileName = src.getAbsolutePath();
/* 2594 */     PointerByReference pvContext = new PointerByReference();
/* 2595 */     if (Advapi32.INSTANCE.OpenEncryptedFileRaw(srcFileName, readFlag, pvContext) != 0)
/*      */     {
/* 2597 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */ 
/*      */     
/* 2601 */     final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 2602 */     WinBase.FE_EXPORT_FUNC pfExportCallback = new WinBase.FE_EXPORT_FUNC()
/*      */       {
/*      */         public WinDef.DWORD callback(Pointer pbData, Pointer pvCallbackContext, WinDef.ULONG ulLength)
/*      */         {
/* 2606 */           byte[] arr = pbData.getByteArray(0L, ulLength.intValue());
/*      */           try {
/* 2608 */             outputStream.write(arr);
/* 2609 */           } catch (IOException e) {
/* 2610 */             throw new RuntimeException(e);
/*      */           } 
/* 2612 */           return new WinDef.DWORD(0L);
/*      */         }
/*      */       };
/*      */     
/* 2616 */     if (Advapi32.INSTANCE.ReadEncryptedFileRaw(pfExportCallback, null, pvContext
/* 2617 */         .getValue()) != 0) {
/* 2618 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 2623 */       outputStream.close();
/* 2624 */     } catch (IOException e) {
/* 2625 */       throw new RuntimeException(e);
/*      */     } 
/* 2627 */     Advapi32.INSTANCE.CloseEncryptedFileRaw(pvContext.getValue());
/*      */ 
/*      */ 
/*      */     
/* 2631 */     String destFileName = destDir.getAbsolutePath() + File.separator + src.getName();
/* 2632 */     pvContext = new PointerByReference();
/* 2633 */     if (Advapi32.INSTANCE.OpenEncryptedFileRaw(destFileName, writeFlag, pvContext) != 0)
/*      */     {
/* 2635 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */ 
/*      */     
/* 2639 */     final IntByReference elementsReadWrapper = new IntByReference(0);
/* 2640 */     WinBase.FE_IMPORT_FUNC pfImportCallback = new WinBase.FE_IMPORT_FUNC()
/*      */       {
/*      */         public WinDef.DWORD callback(Pointer pbData, Pointer pvCallbackContext, WinDef.ULONGByReference ulLength)
/*      */         {
/* 2644 */           int elementsRead = elementsReadWrapper.getValue();
/* 2645 */           int remainingElements = outputStream.size() - elementsRead;
/* 2646 */           int length = Math.min(remainingElements, ulLength.getValue().intValue());
/* 2647 */           pbData.write(0L, outputStream.toByteArray(), elementsRead, length);
/*      */           
/* 2649 */           elementsReadWrapper.setValue(elementsRead + length);
/* 2650 */           ulLength.setValue(new WinDef.ULONG(length));
/* 2651 */           return new WinDef.DWORD(0L);
/*      */         }
/*      */       };
/*      */     
/* 2655 */     if (Advapi32.INSTANCE.WriteEncryptedFileRaw(pfImportCallback, null, pvContext
/* 2656 */         .getValue()) != 0) {
/* 2657 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */ 
/*      */     
/* 2661 */     Advapi32.INSTANCE.CloseEncryptedFileRaw(pvContext.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Privilege
/*      */     implements Closeable
/*      */   {
/*      */     private boolean currentlyImpersonating = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean privilegesEnabled = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final WinNT.LUID[] pLuids;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Privilege(String... privileges) throws IllegalArgumentException, Win32Exception {
/* 2689 */       this.pLuids = new WinNT.LUID[privileges.length];
/* 2690 */       int i = 0;
/* 2691 */       for (String p : privileges) {
/* 2692 */         this.pLuids[i] = new WinNT.LUID();
/* 2693 */         if (!Advapi32.INSTANCE.LookupPrivilegeValue(null, p, this.pLuids[i])) {
/* 2694 */           throw new IllegalArgumentException("Failed to find privilege \"" + privileges[i] + "\" - " + Kernel32.INSTANCE.GetLastError());
/*      */         }
/* 2696 */         i++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() {
/* 2706 */       disable();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Privilege enable() throws Win32Exception {
/* 2718 */       if (this.privilegesEnabled) {
/* 2719 */         return this;
/*      */       }
/*      */       
/* 2722 */       WinNT.HANDLEByReference phThreadToken = new WinNT.HANDLEByReference();
/*      */       
/*      */       try {
/* 2725 */         phThreadToken.setValue(getThreadToken());
/* 2726 */         WinNT.TOKEN_PRIVILEGES tp = new WinNT.TOKEN_PRIVILEGES(this.pLuids.length);
/* 2727 */         for (int i = 0; i < this.pLuids.length; i++) {
/* 2728 */           tp.Privileges[i] = new WinNT.LUID_AND_ATTRIBUTES(this.pLuids[i], new WinDef.DWORD(2L));
/*      */         }
/* 2730 */         if (!Advapi32.INSTANCE.AdjustTokenPrivileges(phThreadToken.getValue(), false, tp, 0, null, null)) {
/* 2731 */           throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */         }
/* 2733 */         this.privilegesEnabled = true;
/*      */       }
/* 2735 */       catch (Win32Exception ex) {
/*      */         
/* 2737 */         if (this.currentlyImpersonating) {
/* 2738 */           Advapi32.INSTANCE.SetThreadToken(null, null);
/* 2739 */           this.currentlyImpersonating = false;
/*      */         
/*      */         }
/* 2742 */         else if (this.privilegesEnabled) {
/* 2743 */           WinNT.TOKEN_PRIVILEGES tp = new WinNT.TOKEN_PRIVILEGES(this.pLuids.length);
/* 2744 */           for (int i = 0; i < this.pLuids.length; i++) {
/* 2745 */             tp.Privileges[i] = new WinNT.LUID_AND_ATTRIBUTES(this.pLuids[i], new WinDef.DWORD(0L));
/*      */           }
/* 2747 */           Advapi32.INSTANCE.AdjustTokenPrivileges(phThreadToken.getValue(), false, tp, 0, null, null);
/* 2748 */           this.privilegesEnabled = false;
/*      */         } 
/*      */         
/* 2751 */         throw ex;
/*      */       }
/*      */       finally {
/*      */         
/* 2755 */         if (phThreadToken.getValue() != WinBase.INVALID_HANDLE_VALUE && phThreadToken
/* 2756 */           .getValue() != null) {
/* 2757 */           Kernel32.INSTANCE.CloseHandle(phThreadToken.getValue());
/* 2758 */           phThreadToken.setValue(null);
/*      */         } 
/*      */       } 
/* 2761 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void disable() throws Win32Exception {
/* 2770 */       WinNT.HANDLEByReference phThreadToken = new WinNT.HANDLEByReference();
/*      */       
/*      */       try {
/* 2773 */         phThreadToken.setValue(getThreadToken());
/* 2774 */         if (this.currentlyImpersonating) {
/* 2775 */           Advapi32.INSTANCE.SetThreadToken(null, null);
/*      */ 
/*      */         
/*      */         }
/* 2779 */         else if (this.privilegesEnabled) {
/* 2780 */           WinNT.TOKEN_PRIVILEGES tp = new WinNT.TOKEN_PRIVILEGES(this.pLuids.length);
/* 2781 */           for (int i = 0; i < this.pLuids.length; i++) {
/* 2782 */             tp.Privileges[i] = new WinNT.LUID_AND_ATTRIBUTES(this.pLuids[i], new WinDef.DWORD(0L));
/*      */           }
/* 2784 */           Advapi32.INSTANCE.AdjustTokenPrivileges(phThreadToken.getValue(), false, tp, 0, null, null);
/* 2785 */           this.privilegesEnabled = false;
/*      */         }
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/* 2791 */         if (phThreadToken.getValue() != WinBase.INVALID_HANDLE_VALUE && phThreadToken
/* 2792 */           .getValue() != null) {
/* 2793 */           Kernel32.INSTANCE.CloseHandle(phThreadToken.getValue());
/* 2794 */           phThreadToken.setValue(null);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private WinNT.HANDLE getThreadToken() throws Win32Exception {
/* 2807 */       WinNT.HANDLEByReference phThreadToken = new WinNT.HANDLEByReference();
/* 2808 */       WinNT.HANDLEByReference phProcessToken = new WinNT.HANDLEByReference();
/*      */ 
/*      */       
/*      */       try {
/* 2812 */         if (!Advapi32.INSTANCE.OpenThreadToken(Kernel32.INSTANCE.GetCurrentThread(), 32, false, phThreadToken))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2817 */           int lastError = Kernel32.INSTANCE.GetLastError();
/* 2818 */           if (1008 != lastError) {
/* 2819 */             throw new Win32Exception(lastError);
/*      */           }
/*      */ 
/*      */           
/* 2823 */           if (!Advapi32.INSTANCE.OpenProcessToken(Kernel32.INSTANCE.GetCurrentProcess(), 2, phProcessToken)) {
/* 2824 */             throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */           }
/*      */ 
/*      */           
/* 2828 */           if (!Advapi32.INSTANCE.DuplicateTokenEx(phProcessToken.getValue(), 36, null, 2, 2, phThreadToken))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2834 */             throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */           }
/*      */ 
/*      */           
/* 2838 */           if (!Advapi32.INSTANCE.SetThreadToken(null, phThreadToken.getValue())) {
/* 2839 */             throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */           }
/* 2841 */           this.currentlyImpersonating = true;
/*      */         }
/*      */       
/* 2844 */       } catch (Win32Exception ex) {
/*      */         
/* 2846 */         if (phThreadToken.getValue() != WinBase.INVALID_HANDLE_VALUE && phThreadToken
/* 2847 */           .getValue() != null) {
/* 2848 */           Kernel32.INSTANCE.CloseHandle(phThreadToken.getValue());
/* 2849 */           phThreadToken.setValue(null);
/*      */         } 
/* 2851 */         throw ex;
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/* 2856 */         if (phProcessToken.getValue() != WinBase.INVALID_HANDLE_VALUE && phProcessToken
/* 2857 */           .getValue() != null) {
/* 2858 */           Kernel32.INSTANCE.CloseHandle(phProcessToken.getValue());
/* 2859 */           phProcessToken.setValue(null);
/*      */         } 
/*      */       } 
/*      */       
/* 2863 */       return phThreadToken.getValue();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Advapi32Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */