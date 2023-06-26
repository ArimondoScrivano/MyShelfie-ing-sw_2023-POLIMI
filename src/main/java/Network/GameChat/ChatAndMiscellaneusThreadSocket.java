package Network.GameChat;

import Network.SOCKET.SocketClientV2;
import Network.messages.Message;
import Network.messages.SocketMessages;
import view.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatAndMiscellaneusThreadSocket extends Thread {

    private final UI cli;
    private final SocketClientV2 client;
    private final String name;
    private final int idLobby;
    private final int typeChosen;
    private final BufferedReader reader;

    private volatile boolean stop;

    /**
     * A thread for handling chat and miscellaneous actions using sockets.
     *

     * @param cli        The user interface object.
     * @param client     The socket client object.
     * @param name       The name associated with the thread.
     * @param idLobby    The ID of the lobby.
     * @param typeChosen The chosen type for the thread.
     */
    public ChatAndMiscellaneusThreadSocket( UI cli, SocketClientV2 client, String name, int idLobby, int typeChosen) {
        this.cli = cli;
        this.client = client;
        this.name = name;
        this.idLobby = idLobby;
        this.typeChosen = typeChosen;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.stop = false;
    }

    /**
     * Sets the buffer end and interrupts the thread.
     */
    public void setBufferEnd() {
        stop = true;
        interrupt();
    }

    /**
     * Runs the thread logic.
     */
    public void run() {
        while (!stop && !Thread.currentThread().isInterrupted()) {
            if (typeChosen == 2) {
                try {
                    if (reader.ready()) {
                        String context = reader.readLine();

                        switch (context) {
                            case "/dashboard" ->
                                    client.sendMessage(new Message(name, SocketMessages.DASHBOARD_REQ, idLobby));
                            case "/personalgoal" ->
                                    client.sendMessage(new Message(name, SocketMessages.PERSONAL_GOAL_REQ, idLobby));
                            case "/commongoal" ->
                                    client.sendMessage(new Message(name, SocketMessages.COMMONGOAL_REQ, idLobby));
                            case "/shelf" -> client.sendMessage(new Message(name, SocketMessages.SHELF_REQ, idLobby));
                            case "/refresh" ->
                                    client.sendMessage(new Message(name, SocketMessages.REFRESH_REQ, idLobby));
                        }
                    }
                } catch (IOException e) {
                    // Handle the exception
                }

            } else {
                while (true) {
                    try {
                        if (cli.getClicked()) {
                            client.sendMessage(new Message(name, SocketMessages.REFRESH_REQ, idLobby));
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // Handle the exception
                    }
                }
            }
        }
    }
}