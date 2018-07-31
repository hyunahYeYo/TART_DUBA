package sjsu.tart.duba;

/**
 * Created by RosieHyunahPark on 2018-07-31.
 */

public class Route {
    private String location;
    private String address;
    private String longitude;
    private String latitude;

    private Route pNext;

    Route(String location, String address, String longitude, String latitude) {
        this.location = location;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
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
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setNext(Route newRoute) {
        this.pNext = newRoute;
    }
    public Route getNext() {
        return this.pNext;
    }
}
