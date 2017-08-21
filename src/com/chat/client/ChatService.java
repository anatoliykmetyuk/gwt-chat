package com.chat.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("chat")
public interface ChatService extends RemoteService {
  void join(String name) throws IllegalArgumentException;
  void send(String name, String message) throws IllegalArgumentException;
  String[] users() throws IllegalArgumentException;
  String[] messages() throws IllegalArgumentException;
}
