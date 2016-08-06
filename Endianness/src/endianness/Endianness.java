package endianness;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Difference between little endian and big endian.<br/>
 *
 * @author skorkmaz, 2016
 */
public class Endianness {

    public static void main(String[] args) {
        byte[] byteArray = {0b00000101 /* = 5*/, 0b01010001, (byte) 0b10000000, 0b00000001, 0x0A, 0x0B};
        ByteBuffer bf = ByteBuffer.wrap(byteArray);
        bf.order(ByteOrder.BIG_ENDIAN); //first byte in data array is most significant, second byte is least significant, i.e. contruct two byte value by attaching the second byte to the right of first byte.
        short val1_BigEndian = bf.getShort(0); //get two bytes (16 bit) of data, starting at index 0
        short val2_BigEndian = bf.getShort(2); //get two bytes (16 bit) of data, starting at index 2
        short val3_BigEndian = bf.getShort(4); //get two bytes (16 bit) of data, starting at index 4
        System.out.println("val1 (big endian) = " + val1_BigEndian); //expected 00000101 01010001 = 1361
        System.out.println("val2 (big endian) = " + val2_BigEndian); //expected 10000000 00000001 = 32769, 2's complement for 16 bit: 32769 - 2^16 = -32767
        System.out.println("val3 (big endian) = " + val3_BigEndian); //expected 0x0A0B = 2571
        bf.order(ByteOrder.LITTLE_ENDIAN);//first byte in data array is least significant, second byte is most significant, i.e. contruct two byte value by attaching the second byte to the left of first byte.
        short val1_LittleEndian = bf.getShort(0);
        short val2_LittleEndian = bf.getShort(2);
        short val3_LittleEndian = bf.getShort(4);
        System.out.println("val1 (little endian) = " + val1_LittleEndian); //expected 01010001 00000101 = 20741
        System.out.println("val2 (little endian) = " + val2_LittleEndian); //expected 00000001 10000000 = 384
        System.out.println("val3 (little endian) = " + val3_LittleEndian); //expected 0x0B0A = 2826
        System.out.println("Before buffer change, byteArray[0] = " + byteArray[0]);
        bf.put(0, (byte) 0b1); //change in buffet will change original byte array:
        System.out.println("After buffer change, byteArray[0] = " + byteArray[0]);
    }

}
