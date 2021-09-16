/*     */ package org.apache.http.impl.auth.win;
/*     */ 
/*     */ import com.sun.jna.platform.win32.Secur32;
/*     */ import com.sun.jna.platform.win32.Sspi;
/*     */ import com.sun.jna.platform.win32.Win32Exception;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.auth.AuthenticationException;
/*     */ import org.apache.http.auth.Credentials;
/*     */ import org.apache.http.auth.InvalidCredentialsException;
/*     */ import org.apache.http.auth.MalformedChallengeException;
/*     */ import org.apache.http.client.protocol.HttpClientContext;
/*     */ import org.apache.http.conn.routing.RouteInfo;
/*     */ import org.apache.http.impl.auth.AuthSchemeBase;
/*     */ import org.apache.http.message.BufferedHeader;
/*     */ import org.apache.http.protocol.HttpContext;
/*     */ import org.apache.http.util.CharArrayBuffer;
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
/*     */ public class WindowsNegotiateScheme
/*     */   extends AuthSchemeBase
/*     */ {
/*  71 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */   private final String scheme;
/*     */   
/*     */   private final String servicePrincipalName;
/*     */   
/*     */   private Sspi.CredHandle clientCred;
/*     */   
/*     */   private Sspi.CtxtHandle sspiContext;
/*     */   
/*     */   private boolean continueNeeded;
/*     */   private String challenge;
/*     */   
/*     */   public WindowsNegotiateScheme(String scheme, String servicePrincipalName) {
/*  85 */     this.scheme = (scheme == null) ? "Negotiate" : scheme;
/*  86 */     this.challenge = null;
/*  87 */     this.continueNeeded = true;
/*  88 */     this.servicePrincipalName = servicePrincipalName;
/*     */     
/*  90 */     if (this.log.isDebugEnabled()) {
/*  91 */       this.log.debug("Created WindowsNegotiateScheme using " + this.scheme);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dispose() {
/*  96 */     if (this.clientCred != null && !this.clientCred.isNull()) {
/*  97 */       int rc = Secur32.INSTANCE.FreeCredentialsHandle(this.clientCred);
/*  98 */       if (0 != rc) {
/*  99 */         throw new Win32Exception(rc);
/*     */       }
/*     */     } 
/* 102 */     if (this.sspiContext != null && !this.sspiContext.isNull()) {
/* 103 */       int rc = Secur32.INSTANCE.DeleteSecurityContext(this.sspiContext);
/* 104 */       if (0 != rc) {
/* 105 */         throw new Win32Exception(rc);
/*     */       }
/*     */     } 
/* 108 */     this.continueNeeded = true;
/* 109 */     this.clientCred = null;
/* 110 */     this.sspiContext = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void finalize() throws Throwable {
/* 115 */     dispose();
/* 116 */     super.finalize();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSchemeName() {
/* 121 */     return this.scheme;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameter(String name) {
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRealm() {
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConnectionBased() {
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void parseChallenge(CharArrayBuffer buffer, int beginIndex, int endIndex) throws MalformedChallengeException {
/* 146 */     this.challenge = buffer.substringTrimmed(beginIndex, endIndex);
/*     */     
/* 148 */     if (this.challenge.isEmpty() && 
/* 149 */       this.clientCred != null) {
/* 150 */       dispose();
/* 151 */       if (this.continueNeeded) {
/* 152 */         throw new RuntimeException("Unexpected token");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Header authenticate(Credentials credentials, HttpRequest request, HttpContext context) throws AuthenticationException {
/*     */     String str;
/* 165 */     if (this.clientCred == null) {
/*     */       
/* 167 */       if (!(credentials instanceof CurrentWindowsCredentials)) {
/* 168 */         throw new InvalidCredentialsException("Credentials cannot be used for " + getSchemeName() + " authentication: " + credentials.getClass().getName());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 175 */         String username = CurrentWindowsCredentials.getCurrentUsername();
/* 176 */         Sspi.TimeStamp lifetime = new Sspi.TimeStamp();
/*     */         
/* 178 */         this.clientCred = new Sspi.CredHandle();
/* 179 */         int rc = Secur32.INSTANCE.AcquireCredentialsHandle(username, this.scheme, 2, null, null, null, null, this.clientCred, lifetime);
/*     */ 
/*     */ 
/*     */         
/* 183 */         if (0 != rc) {
/* 184 */           throw new Win32Exception(rc);
/*     */         }
/*     */         
/* 187 */         String targetName = getServicePrincipalName(context);
/* 188 */         str = getToken(null, null, targetName);
/* 189 */       } catch (RuntimeException ex) {
/* 190 */         failAuthCleanup();
/* 191 */         if (ex instanceof Win32Exception) {
/* 192 */           throw new AuthenticationException("Authentication Failed", ex);
/*     */         }
/* 194 */         throw ex;
/*     */       } 
/*     */     } else {
/* 197 */       if (this.challenge == null || this.challenge.isEmpty()) {
/* 198 */         failAuthCleanup();
/* 199 */         throw new AuthenticationException("Authentication Failed");
/*     */       } 
/*     */       try {
/* 202 */         byte[] continueTokenBytes = Base64.decodeBase64(this.challenge);
/* 203 */         Sspi.SecBufferDesc continueTokenBuffer = new Sspi.SecBufferDesc(2, continueTokenBytes);
/*     */         
/* 205 */         String targetName = getServicePrincipalName(context);
/* 206 */         str = getToken(this.sspiContext, continueTokenBuffer, targetName);
/* 207 */       } catch (RuntimeException ex) {
/* 208 */         failAuthCleanup();
/* 209 */         if (ex instanceof Win32Exception) {
/* 210 */           throw new AuthenticationException("Authentication Failed", ex);
/*     */         }
/* 212 */         throw ex;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 217 */     CharArrayBuffer buffer = new CharArrayBuffer(this.scheme.length() + 30);
/* 218 */     if (isProxy()) {
/* 219 */       buffer.append("Proxy-Authorization");
/*     */     } else {
/* 221 */       buffer.append("Authorization");
/*     */     } 
/* 223 */     buffer.append(": ");
/* 224 */     buffer.append(this.scheme);
/* 225 */     buffer.append(" ");
/* 226 */     buffer.append(str);
/* 227 */     return (Header)new BufferedHeader(buffer);
/*     */   }
/*     */   
/*     */   private void failAuthCleanup() {
/* 231 */     dispose();
/* 232 */     this.continueNeeded = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getServicePrincipalName(HttpContext context) {
/*     */     String spn;
/* 242 */     if (this.servicePrincipalName != null) {
/* 243 */       spn = this.servicePrincipalName;
/* 244 */     } else if (isProxy()) {
/* 245 */       HttpClientContext clientContext = HttpClientContext.adapt(context);
/* 246 */       RouteInfo route = clientContext.getHttpRoute();
/* 247 */       if (route != null) {
/* 248 */         spn = "HTTP/" + route.getProxyHost().getHostName();
/*     */       } else {
/*     */         
/* 251 */         spn = null;
/*     */       } 
/*     */     } else {
/* 254 */       HttpClientContext clientContext = HttpClientContext.adapt(context);
/* 255 */       HttpHost target = clientContext.getTargetHost();
/* 256 */       if (target != null) {
/* 257 */         spn = "HTTP/" + target.getHostName();
/*     */       } else {
/* 259 */         RouteInfo route = clientContext.getHttpRoute();
/* 260 */         if (route != null) {
/* 261 */           spn = "HTTP/" + route.getTargetHost().getHostName();
/*     */         } else {
/*     */           
/* 264 */           spn = null;
/*     */         } 
/*     */       } 
/*     */     } 
/* 268 */     if (this.log.isDebugEnabled()) {
/* 269 */       this.log.debug("Using SPN: " + spn);
/*     */     }
/* 271 */     return spn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getToken(Sspi.CtxtHandle continueCtx, Sspi.SecBufferDesc continueToken, String targetName) {
/* 279 */     IntByReference attr = new IntByReference();
/* 280 */     Sspi.SecBufferDesc token = new Sspi.SecBufferDesc(2, 12288);
/*     */ 
/*     */     
/* 283 */     this.sspiContext = new Sspi.CtxtHandle();
/* 284 */     int rc = Secur32.INSTANCE.InitializeSecurityContext(this.clientCred, continueCtx, targetName, 3, 0, 16, continueToken, 0, this.sspiContext, token, attr, null);
/*     */ 
/*     */ 
/*     */     
/* 288 */     switch (rc) {
/*     */       case 590610:
/* 290 */         this.continueNeeded = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 300 */         return Base64.encodeBase64String(token.getBytes());case 0: dispose(); this.continueNeeded = false; return Base64.encodeBase64String(token.getBytes());
/*     */     } 
/*     */     dispose();
/*     */     throw new Win32Exception(rc);
/*     */   } public boolean isComplete() {
/* 305 */     return !this.continueNeeded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Header authenticate(Credentials credentials, HttpRequest request) throws AuthenticationException {
/* 316 */     return authenticate(credentials, request, null);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/auth/win/WindowsNegotiateScheme.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */