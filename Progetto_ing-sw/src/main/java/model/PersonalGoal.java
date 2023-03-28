package model;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

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
    public int getPoints(){
        int counter=0;
        List<String> positions=new ArrayList();
        //Object o= new JSONParser().parse(new FileReader(PersonalGoal.json));
        //JSONObject j= (JSONObject) o;
        //if(this.id.equals(j.get("id"))){
          //  positions.add(j.get("00"));
            //positions.add(j.get("13"));
            //positions.add(j.get("20"));
            //positions.add(j.get("25"));
            //positions.add(j.get("32"));
            //positions.add(j.get("41"));}
        //if(tilesShelf[0][0]=positions.get(0){counter++;}
        //if(tilesShelf[1][3]=positions.get(1){counter++;}
        //if(tilesShelf[2][0]=positions.get(2){counter++;}
        //if(tilesShelf[2][5]=positions.get(3){counter++;}
        //if(tilesShelf[3][2]=positions.get(4){counter++;}
        //if(tilesShelf[4][1]=positions.get(5){counter++;}


        return 0;
    } //points.get(i);
}
