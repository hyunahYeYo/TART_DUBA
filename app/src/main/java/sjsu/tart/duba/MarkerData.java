package sjsu.tart.duba;

import android.util.Log;

/**
 * Created by lion7 on 2018-07-31.
 */

public class MarkerData implements Comparable<MarkerData>{
    String title, tag, lan, lon, color;

    private int tagNum = 11;
    private Boolean[] markerTag = new Boolean[tagNum];
    private Boolean[] userTag = new Boolean[tagNum];
    private String[] tagKey = {
            "Single", "Family", "Couple", "Friend",
            "Landmark", "Food", "Shoppping", "Art",
            "Child", "Adult", "Elderly"
    };

    private int matchedTagNum = 0;
    private String[] markerTagString;

    public MarkerData(
            String title, String tag, String lan, String lon, String color
    ){
        this.title = title;
        this.tag = tag;
        this.lan = lan;
        this.lon = lon;
        this.color = color;

        initTag(markerTag);
        initTag(userTag);

        markerTagString = tag.split("#");
    }

    public String show(){
        return title + ", " + tag + ", " + lan + ", " + lon + ", " + color;
    }

    public void reviseTags(String[] userTagString){

        for(int i = 0; i < userTagString.length; i++){
            reviseTag(userTag, userTagString[i]);
        }
        for(int i = 0; i < markerTagString.length; i++){
            reviseTag(markerTag, markerTagString[i]);
        }

        matchedTagNum = 0;
        for(int i = 0; i < tagNum; i++){
            if(userTag[i] && markerTag[i]){
                matchedTagNum++;
            }
        }
    }

    public void reviseTag(Boolean[] tag, String key){
        for(int i = 0; i < tagNum; i++){
            if(key.equals(tagKey[i])){
                tag[i] = true;
                break;
            }
        }
    }

    private void initTag(Boolean[] tag){
        for(int i = 0; i < tagNum; i++){
            tag[i] = Boolean.FALSE;
        }
    }

    public int compareTo(MarkerData other){
        return other.getMatchedTagNum() - matchedTagNum;
    }

    public void showInfoByLog(String tag){
        Log.d(tag, show() + " " + Integer.toString(matchedTagNum));
    }

    public int getMatchedTagNum(){  return matchedTagNum;  }
}
