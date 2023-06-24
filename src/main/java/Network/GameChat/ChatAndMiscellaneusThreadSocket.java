package Network.GameChat;

import Network.SOCKET.SocketClientV2;
import Network.messages.Message;
import Network.messages.SocketMessages;
import view.UI;
import view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatAndMiscellaneusThreadSocket extends Thread {
    private View currentView;
    private UI cli;
    private SocketClientV2 client;
    private String name;
    private int idLobby;
    private int typeChosen;
    private BufferedReader reader;

    private volatile boolean stop;

    public ChatAndMiscellaneusThreadSocket(View view, UI cli, SocketClientV2 client, String name, int idLobby, int typeChosen) {
        this.currentView = view;
        this.cli = cli;
        this.client = client;
        this.name = name;
        this.idLobby = idLobby;
        this.typeChosen = typeChosen;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.stop = false;
    }

    public void setBufferEnd() {
        stop = true;
        interrupt();
    }

    public void run() {
        while (!stop && !Thread.currentThread().isInterrupted()) {
            if (typeChosen == 2) {
                try {
                    if (reader.ready()) {
                        String context = reader.readLine();

                        if (context.equals("/dashboard")) {
                            client.sendMessage(new Message(name, SocketMessages.DASHBOARD_REQ, idLobby));
                        } else if (context.equals("/personalgoal")) {
                            client.sendMessage(new Message(name, SocketMessages.PERSONAL_GOAL_REQ, idLobby));
                        } else if (context.equals("/commongoal")) {
                            client.sendMessage(new Message(name, SocketMessages.COMMONGOAL_REQ, idLobby));
                        } else if (context.equals("/shelf")) {
                            client.sendMessage(new Message(name, SocketMessages.SHELF_REQ, idLobby));
                        } else if (context.equals("/refresh")) {
                            client.sendMessage(new Message(name, SocketMessages.REFRESH_REQ, idLobby));
                        }
                    }
                } catch (IOException e) {
                    // Gestisci l'eccezione
                }

            } else {
                while (true) {
                    try {
                        if (cli.getClicked()) {
                            client.sendMessage(new Message(name, SocketMessages.REFRESH_REQ, idLobby));
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // Gestisci l'eccezione
                    }
                }
            }
        }
    }
}
