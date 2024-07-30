package bdabackend.bda.Utils;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import bdabackend.bda.Entity.RegionEntity;

import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.ParseException;

import java.util.List;

public class GeoUtils {

    // Radio de la Tierra en kilómetros
    private static final double RADIO_TIERRA_KM = 6371.0;

    private static final GeometryFactory geometryFactory = new GeometryFactory();

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

    /**
     * Comprueba si un Point se encuentra dentro de un MultiPolygon.
     * 
     * @param point        El punto a comprobar.
     * @param multiPolygon El MultiPolygon en el que comprobar.
     * @return true si el punto se encuentra dentro del MultiPolygon, false en caso
     *         contrario.
     */
    public static boolean puntoEnMultiPolygon(Point point, MultiPolygon multiPolygon) {
        org.locationtech.jts.geom.Point jtsPoint = new GeometryFactory()
                .createPoint(new Coordinate(point.getX(), point.getY()));
        return multiPolygon.contains(jtsPoint);
    }

    /**
     * Transforma un Lis<?> desde la tabla regiones_chile a un MultiPolygon.
     * En la tabla regiones_chile, la geometría se almacena como un WKB.
     * 
     * @param List<?> regiones, posición 0 de la lista tiene el id, posición 1 tiene
     *                la geometría y posición 2 tiene el nombre.
     * @return RegionEntity con la geometría convertida a MultiPolygon.
     */
    public static RegionEntity convertirWKBaMultiPolygon(List<?> regiones) {
        System.out.println(regiones.get(0));
        System.out.println((RegionEntity) regiones.get(0));
        // Acceder al primer valor del Object
        System.out.println(regiones.get(0).getClass());
        byte[] geomBytes = (byte[]) regiones.get(1);
        WKBReader reader = new WKBReader();
        try {
            Geometry geometry = reader.read(geomBytes);
            if (geometry instanceof MultiPolygon) {

                RegionEntity region = new RegionEntity((String) regiones.get(2), (MultiPolygon) geometry);
                region.setId((Long) regiones.get(0));
                return region;
            } else {
                throw new IllegalArgumentException("La geometría no es multipoligonal");
            }
        } catch (ParseException e) {
            throw new RuntimeException("Error al convertir byte array a MultiPolygon", e);
        }
    }

    public static MultiPolygon wktToMultiPolygon(String wkt) throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        Geometry geometry = reader.read(wkt);
        if (geometry instanceof MultiPolygon) {
            return (MultiPolygon) geometry;
        } else {
            throw new IllegalArgumentException("The provided WKT does not represent a MultiPolygon.");
        }
    }
}