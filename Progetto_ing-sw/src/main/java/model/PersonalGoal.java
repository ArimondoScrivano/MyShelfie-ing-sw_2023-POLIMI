package model;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class PersonalGoal {
    int id;
    private int[][] layout;
    private List<Integer> points;
    //METHOD TO RETURN GOAL POINTS
    public PersonalGoal(){
        //this.id=id;
        this.points=new ArrayList();
        points.add(0, 1);
        points.add(1, 2);
        points.add(2, 4);
        points.add(3, 6);
        points.add(4, 9);
        points.add(5, 12);
    }
    public int checkPersonalGoal(Player myPlayer, int[][] tileTypes) throws IOException {
        int counter=0;
        int[][] alreadyCheckedPositions=new int[6][7];
        boolean checked=false;
        List<String> positions=new ArrayList();
        //create a gson instance
        Gson gson=new Gson();
        String jsonString=new String();

        if(id==1){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal1.json")));
        }
        else if(id==2){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal2.json")));
        }
        else if(id==3){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal3.json")));
        }
        else if(id==4){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal4.json")));
        }
        else if(id==5){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal5.json")));
        }
        else if(id==6){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal6.json")));
        }
        else if(id==7){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal7.json")));
        }
       else  if(id==8){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal8.json")));
        }
       else if(id==9){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal9.json")));
        }
       else if(id==10){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal10.json")));
        }
       else if(id==11){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal11.json")));
        }
       else if(id==12){
            jsonString=new String(Files.readAllBytes(Paths.get("src/main/java/model/jsonFiles/PersonalGoal12.json")));
        }else{ System.out.println("Personal Goal id problem"); return -1;}
        Type tipoArray= new TypeToken<int[]>(){}.getType();
        int[] layout=gson.fromJson(jsonString, tipoArray);
        int i=0;
        int column, row, color;
        for(i=0; i<layout.length; i++){
            column=layout[i]-(layout[i]%100);
            row=layout[i]-(layout[i]%10)-column;
            color=layout[i]-column-row;
            if(alreadyCheckedPositions[column][row]==-1){ checked=true;
            }else{
                if(tileTypes[column][row]==color){
                    myPlayer.points+=points.get(counter);
                    counter++; alreadyCheckedPositions[column][row]=-1;
                }//marchio le tiles per le quali ho già riscattato un punteggio
            }
        }
        return 0; //probabilmente modificherò il tipo da int a void, ma non oggi
    }
}