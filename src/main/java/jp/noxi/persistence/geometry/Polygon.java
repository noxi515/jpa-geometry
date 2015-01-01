package jp.noxi.persistence.geometry;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Geometry Polygon class
 *
 * @author noxi
 */
public class Polygon implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Collection<Point> points;

    public Polygon() {
        this(new ArrayList<>());
    }

    public Polygon(@Nullable Collection<Point> points) {
        this.points = points;
    }

    @Nullable
    public Collection<Point> getPoints() {
        return points;
    }

    public void setPoints(@Nullable Collection<Point> points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polygon polygon = (Polygon) o;

        if (points != null ? !points.equals(polygon.points) : polygon.points != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return points != null ? points.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "points=" + points +
                '}';
    }
}
