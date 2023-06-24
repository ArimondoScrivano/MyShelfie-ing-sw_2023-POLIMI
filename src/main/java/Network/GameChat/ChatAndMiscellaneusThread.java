package Network.GameChat;

import Network.RMI.Client_RMI;
import view.UI;
import view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatAndMiscellaneusThread extends Thread {
    private Client_RMI rmiclient;
    private View currentView;
    private BufferedReader reader;
    private int typeChosen;
    private UI cli;
    private volatile boolean stop;

    public ChatAndMiscellaneusThread(Client_RMI rmiclient, View currentView, int typeChosen, UI cli) {
        this.rmiclient = rmiclient;
        this.currentView = currentView;
        this.typeChosen = typeChosen;
        this.cli = cli;
        this.stop = false;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
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
                        if (isInterrupted()) {
                            break;
                        }

                        // Processa l'input
                        if (context.equals("/dashboard")) {
                            currentView.printDashboard(rmiclient.getDashboard());
                        } else if (context.equals("/personalgoal")) {
                            currentView.printPersonalGoal(rmiclient.getMyPersonalGoal());
                        } else if (context.equals("/commongoal")) {
                            currentView.printCommonGoal(rmiclient.getCommonGoals());
                        } else if (context.equals("/shelf")) {
                            currentView.printShelf(rmiclient.getMyShelfie());
                        } else if (context.equals("/refresh")) {
                            currentView.showMatchInfo(
                                    rmiclient.getDashboard(),
                                    rmiclient.getCommonGoals(),
                                    rmiclient.getMyShelfie(),
                                    rmiclient.getMyPersonalGoal()
                            );
                        }
                    }
                } catch (IOException e) {
                    // Gestisci l'eccezione
                }
            } else {
                while (true) {
                    try {
                        if (cli.getClicked()) {
                            currentView.showMatchInfo(
                                    rmiclient.getDashboard(),
                                    rmiclient.getCommonGoals(),
                                    rmiclient.getMyShelfie(),
                                    rmiclient.getMyPersonalGoal()
                            );
                        }
                        Thread.sleep(100);
                    } catch (Exception e) {
                        // Gestisci l'eccezione
                    }
                }
            }
        }
    }
}
