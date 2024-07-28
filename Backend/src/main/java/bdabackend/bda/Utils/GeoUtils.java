package bdabackend.bda.Utils;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class GeoUtils {

    // Radio de la Tierra en kilómetros
    private static final double RADIO_TIERRA_KM = 6371.0;

    /**
     * Calcula la distancia entre dos puntos geográficos usando la fórmula de
     * Haversine.
     *
     * @param punto1 El primer punto geográfico.
     * @param punto2 El segundo punto geográfico.
     * @return La distancia en kilómetros entre los dos puntos.
     */
    public static double calcularDistancia(GeoJsonPoint punto1, Point punto2) {
        double lat1 = Math.toRadians(punto1.getY());
        double lon1 = Math.toRadians(punto1.getX());
        double lat2 = Math.toRadians(punto2.getY());
        double lon2 = Math.toRadians(punto2.getX());

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }
}