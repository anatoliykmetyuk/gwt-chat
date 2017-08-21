package com.chat.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Timer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Chat implements EntryPoint {
  private String name = "";

  private TextBox  msgField = new TextBox();
  private Button   sendBtn  = new Button("Send");

  private TextArea msgsArea = new TextArea();
  private ListBox  usersBox = new ListBox();

  private TextBox nameField = new TextBox();
  private Button  joinBtn   = new Button("Join");

  private Panel chatView  = chatView();
  private Panel loginView = loginView();

  private final ChatServiceAsync chatService = GWT.create(ChatService.class);


  public void onModuleLoad() {
    layout();
    callbacks();
  }

  protected void layout() {
    RootPanel.get("chat" ).add(chatView );
    RootPanel.get("login").add(loginView);

    chatView .setVisible(false);
    loginView.setVisible(true );
  }

  protected Panel chatView() {
    HorizontalPanel sendMsgPan = new HorizontalPanel();
    sendMsgPan.add(msgField);
    sendMsgPan.add(sendBtn);

    msgField.setWidth("630px");


    HorizontalPanel msgsAndUsersPan = new HorizontalPanel();
    msgsAndUsersPan.add(msgsArea);
    msgsAndUsersPan.add(usersBox);

    msgsArea.setCharacterWidth(80);
    msgsArea.setVisibleLines(25);
    msgsArea.setReadOnly(true);

    usersBox.setVisibleItemCount(24);
    usersBox.setWidth("100px");


    VerticalPanel mainPan = new VerticalPanel();
    mainPan.add(msgsAndUsersPan);
    mainPan.add(sendMsgPan);

    return mainPan;
  }

  protected Panel loginView() {
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(nameField);
    hp.add(joinBtn);

    return hp;
  }

  protected void callbacks() {
    sendBtn.addClickHandler((ClickEvent evt) -> sendMessage());
    msgField.addKeyUpHandler((KeyUpEvent evt) -> {if (evt.getNativeKeyCode() == KeyCodes.KEY_ENTER) sendMessage();});

    joinBtn.addClickHandler((ClickEvent evt) -> joinChat());
    nameField.addKeyUpHandler((KeyUpEvent evt) -> {if (evt.getNativeKeyCode() == KeyCodes.KEY_ENTER) joinChat();});
  }

  protected void polling() {
    Timer t = new Timer() {
      @Override
      public void run() {
        chatService.users(usersHandler);
        chatService.messages(messagesHandler);
      }
    };

    t.scheduleRepeating(1000);
  }

  protected void sendMessage() {
    chatService.send(name, msgField.getText(), voidHandler);
    msgField.setText("");
  }

  protected void joinChat() {
    name = nameField.getText();

    chatService.join(name, new AsyncCallback<Void>() {
      public void onSuccess(Void x) {
        chatView .setVisible(true);
        loginView.setVisible(false);
        polling();
      }

      public void onFailure(Throwable e) {
        Window.alert(e.toString());
      }
    });
  }

  private final AsyncCallback<String[]> usersHandler = new AsyncCallback<String[]>() {
    public void onSuccess(String[] users) {
      usersBox.clear();
      for (String user: users) usersBox.addItem(user);
    }

    public void onFailure(Throwable e) {
      Window.alert(e.toString());
    }
  }; 

  private final AsyncCallback<String[]> messagesHandler = new AsyncCallback<String[]>() {
    public void onSuccess(String[] messages) {
      msgsArea.setText("");
      for (String msg: messages) msgsArea.setText(msgsArea.getText() + "\n" + msg);
    }

    public void onFailure(Throwable e) {
      Window.alert(e.toString());
    }
  };

  private final AsyncCallback<Void> voidHandler = new AsyncCallback<Void>() {
    public void onSuccess(Void x) {}
    public void onFailure(Throwable e) {}
  };
}
