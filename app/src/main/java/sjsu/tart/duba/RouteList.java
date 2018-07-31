package sjsu.tart.duba;

import android.util.Log;

/**
 * Created by RosieHyunahPark on 2018-07-31.
 */

public  class RouteList {
    private static Route HeadRoute;
    private static Route TailRoute;
    private static int size = 0;

    public static void addList(String location) {
        Route newRoute = new Route(location);

     //   Log.e("size",""+size);
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
}
