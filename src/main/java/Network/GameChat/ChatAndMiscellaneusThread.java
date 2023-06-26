package Network.GameChat;

import Network.RMI.Client_RMI;
import view.UI;
import view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatAndMiscellaneusThread extends Thread {
    private final Client_RMI rmiclient;
    private final View currentView;
    private final BufferedReader reader;
    private final int typeChosen;
    private final UI cli;
    private volatile boolean stop;

    /**
     * A thread for handling chat and miscellaneous actions.
     *
     * @param rmiclient   The RMI client object.
     * @param currentView The current view object.
     * @param typeChosen  The chosen type for the thread.
     * @param cli         The user interface object.
     */
    public ChatAndMiscellaneusThread(Client_RMI rmiclient, View currentView, int typeChosen, UI cli) {
        this.rmiclient = rmiclient;
        this.currentView = currentView;
        this.typeChosen = typeChosen;
        this.cli = cli;
        this.stop = false;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
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
                        if (isInterrupted()) {
                            break;
                        }

                        // Process the input
                        switch (context) {
                            case "/dashboard" -> currentView.printDashboard(rmiclient.getDashboard());
                            case "/personalgoal" -> currentView.printPersonalGoal(rmiclient.getMyPersonalGoal());
                            case "/commongoal" -> currentView.printCommonGoal(rmiclient.getCommonGoals());
                            case "/shelf" -> currentView.printShelf(rmiclient.getMyShelfie());
                            case "/refresh" -> currentView.showMatchInfo(
                                    rmiclient.getDashboard(),
                                    rmiclient.getCommonGoals(),
                                    rmiclient.getMyShelfie(),
                                    rmiclient.getMyPersonalGoal()
                            );
                        }
                    }
                } catch (IOException e) {
                    // Handle the exception
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
                        // Handle the exception
                    }
                }
            }
        }
    }
}
