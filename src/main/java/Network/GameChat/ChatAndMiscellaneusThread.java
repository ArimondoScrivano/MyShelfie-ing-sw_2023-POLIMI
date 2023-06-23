package Network.GameChat;

import Network.RMI.Client_RMI;
import view.UI;
import view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatAndMiscellaneusThread extends Thread {
    private volatile boolean running = true;
    private volatile boolean paused = false;

    private Client_RMI rmiclient;
    private View currentView;
    private BufferedReader reader;

    private int typechosed;

    private UI cli;
    public ChatAndMiscellaneusThread(Client_RMI rmiclient, View currentView, int typechosed, UI cli) {
        this.rmiclient = rmiclient;
        this.currentView = currentView;
        this.typechosed= typechosed;
        this.cli= cli;
    }

    public void run() {
        while(!currentThread().isInterrupted()) {
            if(typechosed==2) {
                try {
                    this.reader = new BufferedReader(new InputStreamReader(System.in));
                    String context = reader.readLine();


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
                } catch (Exception e) {

                }
            }else{

            while(true) {
                
                try{
                if (cli.getClicked()) {
                    currentView.showMatchInfo(
                            rmiclient.getDashboard(),
                            rmiclient.getCommonGoals(),
                            rmiclient.getMyShelfie(),
                            rmiclient.getMyPersonalGoal()
                    );
                }
                Thread.sleep(100);
                }catch (Exception e){

                }
            }

            }
        }

        }
    }