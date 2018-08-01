package sjsu.tart.duba;

import android.util.Log;

/**
 * Created by RosieHyunahPark on 2018-07-31.
 */

public  class RouteList {
    public static Route HeadRoute;
    public static Route TailRoute;
    public static int size = 0;

    public static void addList(String location) {
        Route newRoute = new Route(location);

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
    public static void deleteItem(int id) {
        Route cur = HeadRoute;
        Route prev = HeadRoute;
        for(int i=0;i<id+1;i++) {
            prev = cur;
            cur = cur.getNext();
        }
        prev.setNext(cur.getNext());
    }
    public static void printList() {
        Route temp = HeadRoute;

        while(temp!=TailRoute) {
            Log.d("RouteTest", "print : "+temp.getLocation());
            temp = temp.getNext();
        }
    }
}
