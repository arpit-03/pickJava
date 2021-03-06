package org.apache.http.conn.routing;

public interface HttpRouteDirector {
  public static final int UNREACHABLE = -1;
  
  public static final int COMPLETE = 0;
  
  public static final int CONNECT_TARGET = 1;
  
  public static final int CONNECT_PROXY = 2;
  
  public static final int TUNNEL_TARGET = 3;
  
  public static final int TUNNEL_PROXY = 4;
  
  public static final int LAYER_PROTOCOL = 5;
  
  int nextStep(RouteInfo paramRouteInfo1, RouteInfo paramRouteInfo2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/conn/routing/HttpRouteDirector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */