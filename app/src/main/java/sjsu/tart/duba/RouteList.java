package sjsu.tart.duba;

/**
 * Created by RosieHyunahPark on 2018-07-31.
 */

public class RouteList {
    private Route HeadRoute;
    private Route TailRoute;
    private int size = 0;

    public void addList(Route newRoute) {
        if(size==0) { //List is empty
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
