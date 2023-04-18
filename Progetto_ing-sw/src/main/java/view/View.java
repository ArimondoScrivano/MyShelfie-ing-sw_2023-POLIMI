package view;

import model.Game;

public interface View {
    //Request RMI or Socket connection
    void askConnection();
    //Request nickname
    void askNickname();
    //Showing info
    void showMatchInfo(Game current_game);
}
