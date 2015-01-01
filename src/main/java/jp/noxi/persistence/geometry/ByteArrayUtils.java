package jp.noxi.persistence.geometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.ByteBuffer;

final class ByteArrayUtils {

    /**
     * Convert double value to byte array.
     */
    @Nonnull
    public static byte[] doubleToBytes(double value) {
        return ByteBuffer.allocate(Double.BYTES).putDouble(value).array();
    }

    /**
     * Convert double value to reversed byte array.
     */
    @Nonnull
    public static byte[] doubleToReverseBytes(double value) {
        return reverse(doubleToBytes(value));
    }

    /**
     * Convert byte array to double value.
     */
    public static double bytesToDouble(@Nullable byte[] value) {
        if (value == null)
            return Double.NaN;
        if (value.length != Double.BYTES)
            throw new IllegalArgumentException("value length is not double");

        return ByteBuffer.allocate(Double.BYTES).put(value).getDouble(0);
    }

    /**
     * Convert byte array to reversed double value.
     */
    public static double reverseBytesToDouble(@Nullable byte[] value) {
        return bytesToDouble(reverse(value));
    }

    /**
     * Reverse byte array
     */
    @Nullable
    public static byte[] reverse(@Nullable byte[] data) {
        if (data == null)
            return null;

        int length = data.length;
        byte[] result = new byte[length];
        if (length == 0)
            return result;

        for (int i = 0; i < length; i++) {
            result[i] = data[length - i - 1];
        }

        return result;
    }


    private ByteArrayUtils() {
    }
}
