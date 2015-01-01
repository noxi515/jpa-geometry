package jp.noxi.persistence.geometry;

import static java.util.Arrays.copyOfRange;
import static jp.noxi.persistence.geometry.ByteArrayUtils.doubleToReverseBytes;
import static jp.noxi.persistence.geometry.ByteArrayUtils.reverseBytesToDouble;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.ByteBuffer;

/**
 * {@link Point} &lt;-&gt; {@code byte[]}
 */
@Converter(autoApply = true)
public class PointToBytesConverter implements AttributeConverter<Point, byte[]> {

    private static final byte[] COMMON_BYTES = {
            0, 0, 0, 0,
            0x01,
            0x01, 0, 0, 0
    };

    @Override
    public byte[] convertToDatabaseColumn(Point attribute) {
        if (attribute == null)
            return null;

        return ByteBuffer.allocate(25)
                .put(COMMON_BYTES)
                .put(doubleToReverseBytes(attribute.latitude))
                .put(doubleToReverseBytes(attribute.longitude))
                .array();
    }

    @Override
    public Point convertToEntityAttribute(byte[] dbData) {
        if (dbData == null)
            return null;

        return new Point(
                reverseBytesToDouble(copyOfRange(dbData, 9, 16)),
                reverseBytesToDouble(copyOfRange(dbData, 17, 24))
        );
    }

}
