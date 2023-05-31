package view.Javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class GUIJavafx extends Application{

    private Stage mystage;

    private GUIJavafxController myGUIcontroller;

    public void init(){
        launch();
}

    @Override
    public void start(Stage stage) throws Exception {
       Parent root= FXMLLoader.load(main.resources.inizio.fxml);
    }


    public String askNickname(){
        myGUIcontroller.enableNickname();
        return null;
    }




}
