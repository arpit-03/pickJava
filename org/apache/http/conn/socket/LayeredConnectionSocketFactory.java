package org.apache.http.conn.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.http.protocol.HttpContext;

public interface LayeredConnectionSocketFactory extends ConnectionSocketFactory {
  Socket createLayeredSocket(Socket paramSocket, String paramString, int paramInt, HttpContext paramHttpContext) throws IOException, UnknownHostException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/conn/socket/LayeredConnectionSocketFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */