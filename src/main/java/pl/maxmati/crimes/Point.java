package pl.maxmati.crimes;

public class Point {
    public double lat;
    public double lon;

    public static Point make(double lat, double lon, Point p) {
        if(p == null)
            p = new Point();

        p.lat = Math.toRadians(lat);
        p.lon = Math.toRadians(lon);

        return p;
    }

    public double distance(Point p){ //TODO:
        double lat2 = p.lat;
        double lat1 = this.lat;
        double lon2 = p.lon;
        double lon1 = this.lon;
        double latDistance = lat2-lat1;
        double lonDistance = lon2-lon1;
        double latSin = Math.sin(latDistance / 2);
        double lonSin = Math.sin(lonDistance / 2);
        return latSin * latSin + Math.cos(lat1) * Math.cos(lat2) * lonSin * lonSin;
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); //atan2 jest funkcją monotoniczną (nie ma wpływu na porównania)
    }
}
