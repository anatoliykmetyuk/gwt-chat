package com.chat.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChatServiceAsync {
  void join(String name, AsyncCallback<Void> cb) throws IllegalArgumentException;
  void send(String name, String message, AsyncCallback<Void> cb) throws IllegalArgumentException;

  void users(AsyncCallback<String[]> cb) throws IllegalArgumentException;
  void messages(AsyncCallback<String[]> cb) throws IllegalArgumentException;
}
