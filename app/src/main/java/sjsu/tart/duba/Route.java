package sjsu.tart.duba;

/**
 * Created by RosieHyunahPark on 2018-07-31.
 */

public class Route {
    private String location;

    private Route pNext;

    Route(String location) {
        this.location = location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }
    public void setNext(Route newRoute) {
        this.pNext = newRoute;
    }
    public Route getNext() {
        return this.pNext;
    }

}
