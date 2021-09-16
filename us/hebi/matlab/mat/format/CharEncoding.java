/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ import us.hebi.matlab.mat.types.Source;
/*     */ import us.hebi.matlab.mat.util.Bytes;
/*     */ import us.hebi.matlab.mat.util.Casts;
/*     */ import us.hebi.matlab.mat.util.Preconditions;
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
/*     */ public enum CharEncoding
/*     */ {
/*  48 */   UInt16(Charsets.US_ASCII, Charsets.US_ASCII),
/*  49 */   Utf8(Charsets.UTF_8, Charsets.UTF_8),
/*  50 */   Utf16(Charsets.UTF_16LE, Charsets.UTF_32BE),
/*  51 */   Utf32(Charsets.UTF_32LE, Charsets.UTF_32BE); private final Charset charsetLE; private final Charset charsetBE;
/*     */   
/*     */   CharEncoding(Charset charsetLE, Charset charsetBE) {
/*  54 */     this.charsetLE = charsetLE;
/*  55 */     this.charsetBE = charsetBE;
/*  56 */     this.encoderLE = newEncoder(charsetLE);
/*  57 */     this.encoderBE = newEncoder(charsetBE);
/*     */   }
/*     */   private final CharsetEncoder encoderLE; private final CharsetEncoder encoderBE; private static final ThreadLocal<ByteBuffer> buffer;
/*     */   static String parseAsciiString(byte[] buffer) {
/*  61 */     return parseAsciiString(buffer, 0, buffer.length);
/*     */   }
/*     */ 
/*     */   
/*     */   static String parseAsciiString(byte[] buffer, int offset, int maxLength) {
/*  66 */     int length = Bytes.findFirst(buffer, offset, maxLength, (byte)0, maxLength);
/*     */ 
/*     */     
/*  69 */     while (length > 0 && buffer[length - 1] == 32) {
/*  70 */       length--;
/*     */     }
/*     */ 
/*     */     
/*  74 */     return (length == 0) ? "" : new String(buffer, offset, length, Charsets.US_ASCII);
/*     */   }
/*     */ 
/*     */   
/*     */   CharBuffer readCharBuffer(Source source, int numBytes) throws IOException {
/*  79 */     if (this == UInt16) {
/*     */ 
/*     */       
/*  82 */       int numElements = Casts.checkedDivide(numBytes, 2);
/*  83 */       CharBuffer charBuffer = CharBuffer.allocate(numElements);
/*  84 */       for (int i = 0; i < numElements; i++) {
/*  85 */         charBuffer.append((char)source.readShort());
/*     */       }
/*  87 */       charBuffer.rewind();
/*  88 */       return charBuffer;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  93 */     BufferAllocator bufferAllocator = Mat5.getDefaultBufferAllocator();
/*  94 */     ByteBuffer tmpBuffer = bufferAllocator.allocate(numBytes);
/*     */     
/*     */     try {
/*  97 */       source.readByteBuffer(tmpBuffer);
/*  98 */       tmpBuffer.rewind();
/*     */ 
/*     */       
/* 101 */       return getCharset(source.order()).decode(tmpBuffer);
/*     */     }
/*     */     finally {
/*     */       
/* 105 */       bufferAllocator.release(tmpBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEncodedLength(CharBuffer chars) {
/* 112 */     int position = chars.position();
/*     */     try {
/* 114 */       if (this == UInt16)
/* 115 */         return chars.remaining() * 2; 
/* 116 */       return writeEncodedUtf(chars, null);
/* 117 */     } catch (IOException ioe) {
/* 118 */       throw new AssertionError(ioe);
/*     */     } finally {
/* 120 */       chars.position(position);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeEncoded(CharBuffer chars, Sink sink) throws IOException {
/* 125 */     int position = chars.position();
/*     */     try {
/* 127 */       if (this == UInt16) {
/* 128 */         writeEncodedUInt16(chars, sink);
/*     */       } else {
/* 130 */         writeEncodedUtf((CharBuffer)Preconditions.checkNotNull(chars), (Sink)Preconditions.checkNotNull(sink));
/*     */       } 
/*     */     } finally {
/* 133 */       chars.position(position);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeEncodedUInt16(CharBuffer chars, Sink sink) throws IOException {
/* 138 */     while (chars.hasRemaining()) {
/* 139 */       sink.writeShort((short)chars.get());
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized int writeEncodedUtf(CharBuffer chars, Sink sink) throws IOException {
/* 144 */     if (!chars.hasRemaining()) {
/* 145 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     ByteOrder order = (sink == null) ? ByteOrder.nativeOrder() : sink.order();
/* 152 */     CharsetEncoder encoder = getEncoder(order);
/* 153 */     ByteBuffer tmp = buffer.get();
/*     */ 
/*     */     
/*     */     try {
/* 157 */       int length = 0;
/*     */       
/*     */       do {
/* 160 */         tmp.clear();
/* 161 */         status = encoder.encode(chars, tmp, true);
/* 162 */         if (status.isError())
/* 163 */           status.throwException(); 
/* 164 */         if (sink != null) {
/* 165 */           tmp.flip();
/* 166 */           sink.writeByteBuffer(tmp);
/*     */         } 
/* 168 */         length += tmp.position();
/*     */       }
/* 170 */       while (status.isOverflow());
/*     */ 
/*     */       
/* 173 */       tmp.clear();
/* 174 */       CoderResult status = encoder.flush(tmp);
/* 175 */       if (status.isError() || status.isOverflow())
/* 176 */         status.throwException(); 
/* 177 */       if (sink != null) {
/* 178 */         tmp.flip();
/* 179 */         sink.writeByteBuffer(tmp);
/*     */       } 
/* 181 */       length += tmp.position();
/* 182 */       return length;
/*     */     }
/*     */     finally {
/*     */       
/* 186 */       encoder.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Charset getCharset(ByteOrder order) {
/* 192 */     Charset charset = (order == ByteOrder.BIG_ENDIAN) ? this.charsetBE : this.charsetLE;
/* 193 */     if (charset == null)
/* 194 */       throw new AssertionError("Charset '" + this + "' is not supported on this platform"); 
/* 195 */     return charset;
/*     */   }
/*     */   
/*     */   private CharsetEncoder getEncoder(ByteOrder order) {
/* 199 */     CharsetEncoder encoder = (order == ByteOrder.BIG_ENDIAN) ? this.encoderBE : this.encoderLE;
/* 200 */     if (encoder == null)
/* 201 */       throw new AssertionError("Charset '" + this + "' is not supported on this platform"); 
/* 202 */     return encoder;
/*     */   }
/*     */   
/*     */   private static CharsetEncoder newEncoder(Charset charset) {
/* 206 */     if (charset == null) return null; 
/* 207 */     return charset.newEncoder()
/* 208 */       .onMalformedInput(CodingErrorAction.REPLACE)
/* 209 */       .onUnmappableCharacter(CodingErrorAction.REPLACE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 216 */     buffer = new ThreadLocal<ByteBuffer>()
/*     */       {
/*     */         protected ByteBuffer initialValue() {
/* 219 */           return ByteBuffer.allocate(8192);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/CharEncoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */