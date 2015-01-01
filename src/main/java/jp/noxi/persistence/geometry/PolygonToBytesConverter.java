package jp.noxi.persistence.geometry;

import static jp.noxi.persistence.geometry.ByteArrayUtils.doubleToReverseBytes;
import static jp.noxi.persistence.geometry.ByteArrayUtils.reverseBytesToDouble;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * {@link jp.noxi.persistence.geometry.Polygon} &lt;-&gt; byte[]
 *
 * @author noxi
 */
@Converter(autoApply = true)
public class PolygonToBytesConverter implements AttributeConverter<Polygon, byte[]> {

    private static final byte[] COMMON_BYTES = {
            0, 0, 0, 0,
            0x01,
            0x03, 0, 0, 0,
            0x01, 0, 0, 0
    };

    @Override
    public byte[] convertToDatabaseColumn(Polygon attribute) {
        if (attribute == null)
            return null;

        if (attribute.points == null || attribute.points.isEmpty())
            throw new IllegalStateException("points are empty.");

        int size = attribute.points.size();
        ByteBuffer buffer = ByteBuffer.allocate(COMMON_BYTES.length + 4 + Double.BYTES * size * 2)
                                      .put(COMMON_BYTES)
                                      .put((byte) size)
                                      .put((byte) 0).put((byte) 0).put((byte) 0);

        attribute.points.stream().forEach(p -> buffer.put(doubleToReverseBytes(p.latitude))
                                                     .put(doubleToReverseBytes(p.longitude)));

        return buffer.array();
    }

    @Override
    public Polygon convertToEntityAttribute(byte[] dbData) {
        if (dbData == null)
            return null;

        int size = (int) dbData[13];
        Polygon polygon = new Polygon(new ArrayList<>(size));
        byte[] buffer = new byte[Double.BYTES];

        IntStream.range(0, size)
                 .map(i -> 17 + i * Double.BYTES)
                 .mapToObj(i -> {
                     Point point = new Point();
                     Arrays.copyOfRange(buffer, i, i + Double.BYTES - 1);
                     point.setLatitude(reverseBytesToDouble(buffer));

                     Arrays.copyOfRange(buffer, i + Double.BYTES, i + Double.BYTES * 2 - 1);
                     point.setLatitude(reverseBytesToDouble(buffer));

                     return point;
                 })
                 .forEach(polygon.points::add);

        return polygon;
    }

}
