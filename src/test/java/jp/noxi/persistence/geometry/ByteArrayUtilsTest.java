package jp.noxi.persistence.geometry;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

@SuppressWarnings("all")
public class ByteArrayUtilsTest {

    @Test
    public void testDoubleToBytes() throws Exception {
        double value = 1d;
        byte[] result = ByteArrayUtils.doubleToBytes(value);
        assertArrayEquals(result, new byte[] {0x3f, (byte) 0xf0, 0, 0, 0, 0, 0, 0});
    }

    @Test
    public void testDoubleToReverseBytes() throws Exception {
        double value = 1d;
        byte[] result = ByteArrayUtils.doubleToReverseBytes(value);
        assertArrayEquals(result, new byte[] {0, 0, 0, 0, 0, 0, (byte) 0xf0, 0x3f});
    }

    @Test
    public void testBytesToDouble() throws Exception {
        byte[] array = {0x3f, (byte) 0xf0, 0, 0, 0, 0, 0, 0};
        double result = ByteArrayUtils.bytesToDouble(array);

        assertThat(result, is(1d));
    }

    @Test
    public void testBytesToDouble_null_to_NaN() throws Exception {
        double result = ByteArrayUtils.bytesToDouble(null);

        assertThat(result, is(Double.NaN));
    }

    @Test
    public void testReverseBytesToDouble() throws Exception {
        byte[] array = {0, 0, 0, 0, 0, 0, (byte) 0xf0, 0x3f};
        double result = ByteArrayUtils.reverseBytesToDouble(array);

        assertThat(result, is(1d));
    }

    @Test
    public void testReverseBytesToDouble_null_to_NaN() throws Exception {
        double result = ByteArrayUtils.reverseBytesToDouble(null);

        assertThat(result, is(Double.NaN));
    }

    @Test
    public void testReverse() throws Exception {
        byte[] array = {1, 2, 3, 4, 5};
        byte[] result = ByteArrayUtils.reverse(array);

        assertArrayEquals(result, new byte[] {5, 4, 3, 2, 1});
    }

    @Test
    public void testReverse_null_to_null() throws Exception {
        byte[] result = ByteArrayUtils.reverse(null);

        assertThat(result, is(nullValue()));
    }

}