package Network.GameChat;

import Network.RMI.Client_RMI;
import Network.messages.Message;
import Network.messages.MessageType;
import model.Tile;
import view.Cli;
import view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ChatAndMiscellaneusThread implements Runnable {
    private Client_RMI rmiclient;
    private View currentView;
    private Cli currentCli;
    private int currentStatusqueue;
    private Thread checknewMex;
    private String playerName;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void stopCheckmex() {
        this.checknewMex.interrupt();
    }

    public void ChatAndMiscellaneusThread(Client_RMI rmiclient, View currentView, Cli currentCli, String playerName){
        this.rmiclient= rmiclient;
        this.currentView= currentView;
        this.currentStatusqueue=-1;
        this.currentCli=currentCli;
        this.playerName=playerName;
    }


    public void run(){
        //check if there are new mex
        this.checknewMex = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                boolean newMexFlag= false;
                while (!newMexFlag) {
                    if ((rmiclient.showGameChat().size() -1) > currentStatusqueue) {
                        newMexFlag = true;
                        currentView.shownewMex();
                    }

                }
                Thread.currentThread().interrupt();
            }
        });
        checknewMex.start();

        String context= "no text";
    while(true) {
        try {
            context = reader.readLine();
        } catch (IOException e) {
            context = "An error has occurred";
        }


        //______________/newmex_______________//
        if(context.equals("/newmex")){
            List<GameMessage> Mex= new ArrayList<>();
            Mex= rmiclient.showGameChat();
            List<GameMessage> NewMex= new ArrayList<>();
            for(int i= currentStatusqueue +1; i< Mex.size(); i++){
                NewMex.add(Mex.get(i));

            }
            currentStatusqueue= Mex.size()-1;
            currentView.showGameChat(NewMex);
            if(this.checknewMex.isInterrupted()) {
                this.checknewMex.start();
            }
        }

        //___________________/allmex_______________//
        if(context.equals("/allmew")){
            currentView.showGameChat(rmiclient.showGameChat());
            currentStatusqueue= rmiclient.showGameChat().size()-1;

            if(this.checknewMex.isInterrupted()) {
                this.checknewMex.start();
            }
        }

        //__________________/writemex_______________//
        if(context.equals("/writemex")){
            List<String> possibleChatmex= currentCli.askNewChatMessage(rmiclient.playersName(), this.playerName );
                rmiclient.appendchatmex(possibleChatmex,playerName);

        }

        //__________________/dashboard______________//
        if(context.equals("/dashboard")){
            currentView.printDashboard(rmiclient.getDashboard());
        }

        //_________________/personalgoal_____________//
        if(context.equals("/personalgoal")){
            currentView.printPersonalGoal(rmiclient.getMyPersonalGoal());
        }

        //_________________/commongoal_______________//
        if(context.equals("/commongoal")){
            currentView.printCommonGoal(rmiclient.getCommonGoals());
        }

        //_________________/shelf____________________//
        if(context.equals("/shelf")){
            currentView.printShelf(rmiclient.getMyShelfie());
        }

    }
    }

    }


