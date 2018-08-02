package sjsu.tart.duba;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by RosieHyunahPark on 2018-07-31.
 */

public class Route {
    private String location;
    private String address;
    private Marker marker;
    private Route pNext;

    Route(String location, String address, Marker marker) {
        this.location = location;
        this.address = address;
        this.marker = marker;
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

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

}
