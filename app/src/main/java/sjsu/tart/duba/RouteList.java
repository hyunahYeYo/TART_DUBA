package sjsu.tart.duba;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by RosieHyunahPark on 2018-07-31.
 */

public  class RouteList {
    public static Route HeadRoute;
    public static Route TailRoute;
    public static int size = 0;

    public static void addList(String location, String address, Marker marker, Context context) {
        Route newRoute = new Route(location, address, marker);

        if(size==0) { //List is empty
            Log.e("d","d");
            //This code can be used to input new Route into head.
            newRoute.setNext(HeadRoute);
            HeadRoute = newRoute;
            size++;
            if (HeadRoute.getNext() == null) {
                TailRoute = HeadRoute;
            }
        }
        else {
            TailRoute.setNext(newRoute);
            TailRoute = newRoute;
            size++;
        }
    }

    public static void changeOrder(int id, int dir) {
        Route cur = HeadRoute;
        Route prev = HeadRoute;
        Route pprev = HeadRoute;

        if(dir==0) { //Up
            if(id==0) return;
            for(int i=0;i<id;i++) {
                pprev = prev;
                prev = cur;
                cur = cur.getNext();
            }
            if(prev==HeadRoute) { //첫번째와 두번째 아이템 변경
                HeadRoute = cur;
                prev.setNext(cur.getNext());
                cur.setNext(prev);
            }
            else if(cur==TailRoute) {
                TailRoute = prev;
                pprev.setNext(cur);
                cur.setNext(prev);
                prev.setNext(null);
            }
            else {
                pprev.setNext(cur);
                prev.setNext(cur.getNext());
                cur.setNext(prev);
            }
        }
        else if(dir==1) { //Down
            for(int i=0;i<id+1;i++) {
                pprev = cur;
                cur = prev;
                prev = prev.getNext();
            }
            if(cur==HeadRoute) {
                HeadRoute = prev;
                cur.setNext(prev.getNext());
                prev.setNext(cur);
            }
            else if(prev==TailRoute) {
                TailRoute = cur;
                pprev.setNext(prev);
                prev.setNext(cur);
                cur.setNext(null);
            }
            else {
                pprev.setNext(prev);
                cur.setNext(prev.getNext());
                prev.setNext(cur);
            }

        }
    }

    public static void deleteItem(int id) {
        Route cur = HeadRoute;
        Route prev = HeadRoute;
        for(int i=0;i<id;i++) {
            prev = cur;
            cur = cur.getNext();
        }

        cur.getMarker().remove();

        if(cur==HeadRoute) {
            HeadRoute = cur.getNext();
        }
        else if(cur==TailRoute) {
            prev.setNext(null);
            TailRoute = prev;
        }
        else {
            prev.setNext(cur.getNext());
        }
        size--;
    }
    public static void mixList() {
        Route cur = HeadRoute;
        Route temp;

        for(int i=0;i<size;i++) {
            for(int j=0;j<size-i;j++) {
                temp = cur.getNext();
                if(cur.getLocation().compareTo(temp.getLocation())>0) {

                }
                else if(cur.getLocation().compareTo(temp.getLocation())<0) {

                }
            }
        }

    }
    /* List 항목 보기 */
    public static void printList() {
        Route temp = HeadRoute;

        while(true) {
            if(temp == null) break;
            Log.d("DELETE", "print : "+temp.getLocation());
            temp = temp.getNext();
        }

    }

    public static boolean checkList(String markerTitle){
        boolean added;
        Route temp = HeadRoute;
        while(true) {
            if(temp == null) break;
            if(temp.getLocation().equals(markerTitle))
                return true;
            temp = temp.getNext();
        }
        return false;
    }

}