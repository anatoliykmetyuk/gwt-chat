package com.chat.server;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.chat.client.Message;
import com.chat.client.ChatModel;

import com.chat.client.ChatService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ChatServiceImpl extends RemoteServiceServlet implements ChatService {
  private List<String> messages = new ArrayList<>();
  private List<String>  users    = new ArrayList<>();

  private int userCounter = 0;

  public void join(String name) throws IllegalArgumentException {
    users.add(name);
  }

  public void send(String user, String message) throws IllegalArgumentException {
    messages.add(user + ": " + message);
  }

  public String[] users() throws IllegalArgumentException {
    return users.toArray(new String[users.size()]);
  }

  public String[] messages() throws IllegalArgumentException {
    return messages.toArray(new String[messages.size()]);
  }
}
