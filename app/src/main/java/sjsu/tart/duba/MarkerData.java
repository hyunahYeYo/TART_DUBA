package sjsu.tart.duba;

/**
 * Created by lion7 on 2018-07-31.
 */

public class MarkerData {
    String title;
    String tag;
    String lan;
    String lon;
    String color;

    public MarkerData(
            String title, String tag, String lan, String lon, String color
    ){
        this.title = title;
        this.tag = tag;
        this.lan = lan;
        this.lon = lon;
        this.color = color;
    }

    public void show(){
        System.out.println(
                title + ", " + tag + ", " + lan + ", " + lon + ", " + color
        );
    }

}
