package bytebufferdemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * ByteBuffer demo.<br/>
 *
 * @author skorkmaz, 2016
 */
public class ByteBufferDemo {

    public static void main(String[] args) {
        byte[] buffer1 = {1, 2};
        byte[] buffer2 = {3, 4};
        byte[] buffer3 = {5, 6};

        ByteBuffer bf1 = ByteBuffer.wrap(buffer1);
        ByteBuffer bf2 = ByteBuffer.wrap(buffer2);
        ByteBuffer bf3 = ByteBuffer.wrap(buffer3);

        //Combine buffers:
        ByteBuffer wholeBuffer = ByteBuffer.allocate(3 * bf1.capacity());
        wholeBuffer.put(bf1);
        wholeBuffer.put(bf2);
        wholeBuffer.put(bf3);
        byte[] wholeBufferArray = wholeBuffer.array();
        for (byte b : wholeBufferArray) {
            System.out.printf("%d ", b);
        }
        System.out.println("");
        //Get short (16 bit integer) value by combining 3rd and fourth bytes:
        //third byte  = 3 = 0000 0011
        //fourth byte = 4 = 0000 0100
        //combined (big endian): 0000 0011 0000 0100 = 772
        //combined (little endian): 0000 0100 0000 0011 = 1027
        System.out.println("3&4 as int16 (big endian)    = " + wholeBuffer.getShort(2));
        wholeBuffer.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println("3&4 as int16 (little endian) = " + wholeBuffer.getShort(2));
    }

}
