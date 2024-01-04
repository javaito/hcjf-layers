package org.hcjf.layers.query;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.ogc.OGCGeometry;
import org.hcjf.utils.GeoUtils;
import org.hcjf.utils.JsonUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class QueryGeoFunctionsTest {

    @Test
    public void geoAggregateDistanceTest() {
        Collection<JoinableMap> resultSet = new ArrayList<>();

        JoinableMap map = new JoinableMap();
        map.put("id", 0);
        map.put("tripName", "trip");
        map.put("point", JsonUtils.createObject(GeoUtils.createGeometry("POINT (-68.781712 -32.878049)").asGeoJson()));
        resultSet.add(map);

        JoinableMap map1 = new JoinableMap();
        map1.put("id", 1);
        map1.put("tripName", "trip");
        map1.put("point", JsonUtils.createObject(GeoUtils.createGeometry("POINT (-68.780274 -32.878360)").asGeoJson()));
        resultSet.add(map1);

        JoinableMap map2 = new JoinableMap();
        map2.put("id", 2);
        map2.put("tripName", "trip");
        map2.put("point", JsonUtils.createObject(GeoUtils.createGeometry("POINT (-68.781120 -32.878649)").asGeoJson()));
        resultSet.add(map2);

        Query query = Query.compile("SELECT *, geoAggregateDistance(point) as distance FROM resource ORDER BY id");
        Collection<JoinableMap> resultSet1 = query.evaluate(resultSet);
        System.out.println();

        query = Query.compile("SELECT *, geoAggregateDistance(point, true) as distance FROM resource ORDER BY id");
        Collection<JoinableMap> resultSet2 = query.evaluate(resultSet);
        System.out.println();

        query = Query.compile("SELECT *, geoAggregateDistance(point, true, true) as distance FROM resource ORDER BY id");
        Collection<JoinableMap> resultSet3 = query.evaluate(resultSet);
        System.out.println();
    }

    @Test
    public void distanceTest() {
        //Saavedra 910
        //San José, Mendoza
        //-32.892190, -68.820792

        //Necochea 321
        //Mendoza
        //-32.894325, -68.820910

        OGCGeometry point1 = GeoUtils.createGeometry("POINT(-68.820792 -32.892190)");



        point1.setSpatialReference(SpatialReference.create(4326));
        OGCGeometry point2 = GeoUtils.createGeometry("POINT(-68.820910 -32.894325)");
        point2.setSpatialReference(SpatialReference.create(4326));

        System.out.println(GeometryEngine.geodesicDistanceOnWGS84(new Point(-68.820792, -32.892190), new Point(-68.820910, -32.894325)));

        
        System.out.println(point1.distance(point2));

    }

}
