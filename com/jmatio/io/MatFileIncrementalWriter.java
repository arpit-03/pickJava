/*     */ package com.jmatio.io;
/*     */ 
/*     */ import com.jmatio.types.MLArray;
/*     */ import com.jmatio.types.MLCell;
/*     */ import com.jmatio.types.MLChar;
/*     */ import com.jmatio.types.MLNumericArray;
/*     */ import com.jmatio.types.MLSparse;
/*     */ import com.jmatio.types.MLStructure;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.DeflaterOutputStream;
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
/*     */ public class MatFileIncrementalWriter
/*     */ {
/*  68 */   private WritableByteChannel channel = null;
/*     */   
/*     */   private boolean headerWritten = false;
/*     */   private boolean isStillValid = false;
/*  72 */   private Set<String> varNames = new TreeSet<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatFileIncrementalWriter(String fileName) throws IOException {
/*  82 */     this(new File(fileName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatFileIncrementalWriter(File file) throws IOException {
/*  93 */     this((new FileOutputStream(file)).getChannel());
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
/*     */   public MatFileIncrementalWriter(WritableByteChannel chan) throws IOException {
/* 106 */     this.channel = chan;
/* 107 */     this.isStillValid = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void write(MLArray data) throws IOException {
/* 113 */     String vName = data.getName();
/* 114 */     if (this.varNames.contains(vName))
/*     */     {
/* 116 */       throw new IllegalArgumentException("Error: variable " + vName + " specified more than once for file input.");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 121 */       if (!this.headerWritten)
/*     */       {
/* 123 */         writeHeader(this.channel);
/*     */       }
/*     */ 
/*     */       
/* 127 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 128 */       DataOutputStream dos = new DataOutputStream(baos);
/*     */       
/* 130 */       writeMatrix(dos, data);
/*     */ 
/*     */       
/* 133 */       Deflater compresser = new Deflater();
/*     */       
/* 135 */       byte[] input = baos.toByteArray();
/*     */       
/* 137 */       ByteArrayOutputStream compressed = new ByteArrayOutputStream();
/* 138 */       DataOutputStream dout = new DataOutputStream(new DeflaterOutputStream(compressed, compresser));
/*     */       
/* 140 */       dout.write(input);
/*     */       
/* 142 */       dout.close();
/* 143 */       compressed.close();
/*     */ 
/*     */       
/* 146 */       byte[] compressedBytes = compressed.toByteArray();
/* 147 */       ByteBuffer buf = ByteBuffer.allocateDirect(8 + compressedBytes.length);
/* 148 */       buf.putInt(15);
/* 149 */       buf.putInt(compressedBytes.length);
/* 150 */       buf.put(compressedBytes);
/*     */       
/* 152 */       buf.flip();
/* 153 */       this.channel.write(buf);
/*     */     }
/* 155 */     catch (IOException e) {
/*     */       
/* 157 */       throw e;
/*     */     } finally {}
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
/*     */   public synchronized void write(Collection<MLArray> data) throws IOException {
/*     */     try {
/* 180 */       for (MLArray matrix : data)
/*     */       {
/* 182 */         write(matrix);
/*     */       }
/*     */     }
/* 185 */     catch (IllegalArgumentException iae) {
/*     */       
/* 187 */       this.isStillValid = false;
/* 188 */       throw iae;
/*     */     }
/* 190 */     catch (IOException e) {
/*     */       
/* 192 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 198 */     this.channel.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeHeader(WritableByteChannel channel) throws IOException {
/* 209 */     MatFileHeader header = MatFileHeader.createHeader();
/* 210 */     char[] dest = new char[116];
/* 211 */     char[] src = header.getDescription().toCharArray();
/* 212 */     System.arraycopy(src, 0, dest, 0, src.length);
/*     */     
/* 214 */     byte[] endianIndicator = header.getEndianIndicator();
/*     */     
/* 216 */     ByteBuffer buf = ByteBuffer.allocateDirect(dest.length * 2 + 2 + endianIndicator.length);
/*     */     
/* 218 */     for (int i = 0; i < dest.length; i++)
/*     */     {
/* 220 */       buf.put((byte)dest[i]);
/*     */     }
/*     */     
/* 223 */     buf.position(buf.position() + 8);
/*     */ 
/*     */     
/* 226 */     int version = header.getVersion();
/* 227 */     buf.put((byte)(version >> 8));
/* 228 */     buf.put((byte)version);
/*     */     
/* 230 */     buf.put(endianIndicator);
/*     */     
/* 232 */     buf.flip();
/* 233 */     channel.write(buf);
/*     */     
/* 235 */     this.headerWritten = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMatrix(DataOutputStream output, MLArray array) throws IOException {
/*     */     OSArrayTag tag;
/*     */     ByteArrayOutputStream buffer;
/*     */     DataOutputStream bufferDOS;
/*     */     Character[] ac;
/*     */     int i, itag, ai[];
/*     */     Double[] ad;
/*     */     int j;
/* 250 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 251 */     DataOutputStream dos = new DataOutputStream(baos);
/*     */ 
/*     */     
/* 254 */     writeFlags(dos, array);
/*     */ 
/*     */     
/* 257 */     writeDimensions(dos, array);
/*     */ 
/*     */     
/* 260 */     writeName(dos, array);
/*     */     
/* 262 */     switch (array.getType()) {
/*     */ 
/*     */       
/*     */       case 4:
/* 266 */         buffer = new ByteArrayOutputStream();
/* 267 */         bufferDOS = new DataOutputStream(buffer);
/* 268 */         ac = ((MLChar)array).exportChar();
/* 269 */         for (i = 0; i < ac.length; i++)
/*     */         {
/* 271 */           bufferDOS.writeByte((byte)ac[i].charValue());
/*     */         }
/* 273 */         tag = new OSArrayTag(16, buffer.toByteArray());
/* 274 */         tag.writeTo(dos);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 279 */         tag = new OSArrayTag(9, ((MLNumericArray)array).getRealByteBuffer());
/*     */         
/* 281 */         tag.writeTo(dos);
/*     */ 
/*     */         
/* 284 */         if (array.isComplex()) {
/*     */           
/* 286 */           tag = new OSArrayTag(9, ((MLNumericArray)array).getImaginaryByteBuffer());
/*     */           
/* 288 */           tag.writeTo(dos);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 9:
/* 293 */         tag = new OSArrayTag(2, ((MLNumericArray)array).getRealByteBuffer());
/*     */         
/* 295 */         tag.writeTo(dos);
/*     */ 
/*     */         
/* 298 */         if (array.isComplex()) {
/*     */           
/* 300 */           tag = new OSArrayTag(2, ((MLNumericArray)array).getImaginaryByteBuffer());
/*     */           
/* 302 */           tag.writeTo(dos);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 8:
/* 307 */         tag = new OSArrayTag(1, ((MLNumericArray)array).getRealByteBuffer());
/*     */         
/* 309 */         tag.writeTo(dos);
/*     */ 
/*     */         
/* 312 */         if (array.isComplex()) {
/*     */           
/* 314 */           tag = new OSArrayTag(1, ((MLNumericArray)array).getImaginaryByteBuffer());
/*     */           
/* 316 */           tag.writeTo(dos);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 10:
/* 321 */         tag = new OSArrayTag(3, ((MLNumericArray)array).getRealByteBuffer());
/*     */         
/* 323 */         tag.writeTo(dos);
/*     */ 
/*     */         
/* 326 */         if (array.isComplex()) {
/*     */           
/* 328 */           tag = new OSArrayTag(3, ((MLNumericArray)array).getImaginaryByteBuffer());
/*     */           
/* 330 */           tag.writeTo(dos);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 14:
/* 335 */         tag = new OSArrayTag(12, ((MLNumericArray)array).getRealByteBuffer());
/*     */         
/* 337 */         tag.writeTo(dos);
/*     */ 
/*     */         
/* 340 */         if (array.isComplex()) {
/*     */           
/* 342 */           tag = new OSArrayTag(12, ((MLNumericArray)array).getImaginaryByteBuffer());
/*     */           
/* 344 */           tag.writeTo(dos);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 15:
/* 349 */         tag = new OSArrayTag(13, ((MLNumericArray)array).getRealByteBuffer());
/*     */         
/* 351 */         tag.writeTo(dos);
/*     */ 
/*     */         
/* 354 */         if (array.isComplex()) {
/*     */           
/* 356 */           tag = new OSArrayTag(13, ((MLNumericArray)array).getImaginaryByteBuffer());
/*     */           
/* 358 */           tag.writeTo(dos);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 2:
/* 363 */         itag = 262149;
/* 364 */         dos.writeInt(itag);
/* 365 */         dos.writeInt(((MLStructure)array).getMaxFieldLenth());
/*     */ 
/*     */         
/* 368 */         tag = new OSArrayTag(1, ((MLStructure)array).getKeySetToByteArray());
/* 369 */         tag.writeTo(dos);
/*     */         
/* 371 */         for (MLArray a : ((MLStructure)array).getAllFields())
/*     */         {
/* 373 */           writeMatrix(dos, a);
/*     */         }
/*     */         break;
/*     */       case 1:
/* 377 */         for (MLArray a : ((MLCell)array).cells())
/*     */         {
/* 379 */           writeMatrix(dos, a);
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 385 */         buffer = new ByteArrayOutputStream();
/* 386 */         bufferDOS = new DataOutputStream(buffer);
/* 387 */         ai = ((MLSparse)array).getIR();
/* 388 */         for (int k : ai)
/*     */         {
/* 390 */           bufferDOS.writeInt(k);
/*     */         }
/* 392 */         tag = new OSArrayTag(5, buffer.toByteArray());
/* 393 */         tag.writeTo(dos);
/*     */         
/* 395 */         buffer = new ByteArrayOutputStream();
/* 396 */         bufferDOS = new DataOutputStream(buffer);
/* 397 */         ai = ((MLSparse)array).getJC();
/* 398 */         for (int k : ai)
/*     */         {
/* 400 */           bufferDOS.writeInt(k);
/*     */         }
/* 402 */         tag = new OSArrayTag(5, buffer.toByteArray());
/* 403 */         tag.writeTo(dos);
/*     */         
/* 405 */         buffer = new ByteArrayOutputStream();
/* 406 */         bufferDOS = new DataOutputStream(buffer);
/*     */         
/* 408 */         ad = ((MLSparse)array).exportReal();
/*     */         
/* 410 */         for (j = 0; j < ad.length; j++)
/*     */         {
/* 412 */           bufferDOS.writeDouble(ad[j].doubleValue());
/*     */         }
/*     */         
/* 415 */         tag = new OSArrayTag(9, buffer.toByteArray());
/* 416 */         tag.writeTo(dos);
/*     */         
/* 418 */         if (array.isComplex()) {
/*     */           
/* 420 */           buffer = new ByteArrayOutputStream();
/* 421 */           bufferDOS = new DataOutputStream(buffer);
/* 422 */           ad = ((MLSparse)array).exportImaginary();
/* 423 */           for (j = 0; j < ad.length; j++)
/*     */           {
/* 425 */             bufferDOS.writeDouble(ad[j].doubleValue());
/*     */           }
/* 427 */           tag = new OSArrayTag(9, buffer.toByteArray());
/* 428 */           tag.writeTo(dos);
/*     */         } 
/*     */         break;
/*     */       default:
/* 432 */         throw new MatlabIOException("Cannot write matrix of type: " + MLArray.typeToString(array.getType()));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 438 */     output.writeInt(14);
/* 439 */     output.writeInt(baos.size());
/* 440 */     output.write(baos.toByteArray());
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
/*     */   private void writeFlags(DataOutputStream os, MLArray array) throws IOException {
/* 452 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 453 */     DataOutputStream bufferDOS = new DataOutputStream(buffer);
/*     */     
/* 455 */     bufferDOS.writeInt(array.getFlags());
/*     */     
/* 457 */     if (array.isSparse()) {
/*     */       
/* 459 */       bufferDOS.writeInt(((MLSparse)array).getMaxNZ());
/*     */     }
/*     */     else {
/*     */       
/* 463 */       bufferDOS.writeInt(0);
/*     */     } 
/* 465 */     OSArrayTag tag = new OSArrayTag(6, buffer.toByteArray());
/* 466 */     tag.writeTo(os);
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
/*     */   private void writeDimensions(DataOutputStream os, MLArray array) throws IOException {
/* 479 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 480 */     DataOutputStream bufferDOS = new DataOutputStream(buffer);
/*     */     
/* 482 */     int[] dims = array.getDimensions();
/* 483 */     for (int i = 0; i < dims.length; i++)
/*     */     {
/* 485 */       bufferDOS.writeInt(dims[i]);
/*     */     }
/* 487 */     OSArrayTag tag = new OSArrayTag(5, buffer.toByteArray());
/* 488 */     tag.writeTo(os);
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
/*     */   private void writeName(DataOutputStream os, MLArray array) throws IOException {
/* 501 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 502 */     DataOutputStream bufferDOS = new DataOutputStream(buffer);
/*     */     
/* 504 */     byte[] nameByteArray = array.getNameToByteArray();
/* 505 */     buffer = new ByteArrayOutputStream();
/* 506 */     bufferDOS = new DataOutputStream(buffer);
/* 507 */     bufferDOS.write(nameByteArray);
/* 508 */     OSArrayTag tag = new OSArrayTag(1, buffer.toByteArray());
/* 509 */     tag.writeTo(os);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/MatFileIncrementalWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */