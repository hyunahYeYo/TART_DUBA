package sjsu.tart.duba;

/**
 * Created by RosieHyunahPark on 2018-07-31.
 */

public class Route {
    private String location;
    private String address;
    private Route pNext;

    Route(String location, String address) {
        this.location = location;
        this.address = address;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
    public void setNext(Route newRoute) {
        this.pNext = newRoute;
    }
    public Route getNext() {
        return this.pNext;
    }

}
