import java.util.Arrays;

public class MD4 {

    private static byte[] z = new byte[]{
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
            0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15,
            0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};

    private static byte[] s = new byte[]{
            3, 7, 11, 19, 3, 7, 11, 19, 3, 7, 11, 19, 3, 7, 11, 19,
            3, 5, 9, 13, 3, 5, 9, 13, 3, 5, 9, 13, 3, 5, 9, 13,
            3, 9, 11, 15, 3, 9, 11, 15, 3, 9, 11, 15, 3, 9, 11, 15};

    private static int[] expandInput(byte[] input) {
        int byteLength = input.length + 64 - input.length % 64;
        byte[] expandedByte = Arrays.copyOf(input, byteLength);
        expandedByte[input.length] = (byte) 0x80;

        for (int i = 0; i < 8; i++)
            expandedByte[byteLength - 8 + i] = (byte) ((input.length * 8L) >>> (8 * i));

        int[] expandedInt = new int[expandedByte.length / 4];
        for (int i = 0; i < expandedInt.length; i++) {
            expandedInt[i] = (expandedByte[i * 4] & 0xFF) | ((expandedByte[1 + i * 4] & 0xFF) << 8) |
                    ((expandedByte[2 + i * 4] & 0xFF) << 16) | ((expandedByte[3 + i * 4] & 0xFF) << 24);
        }
        return expandedInt;
    }

    private static int f(int u, int v, int w) {
        return (u & v) | ((~u) & w);
    }

    private static int g(int u, int v, int w) {
        return (u & v) | (u & w) | (v & w);
    }

    private static int h(int u, int v, int w) {
        return ((u ^ v) ^ w);
    }


    public static String getHash(String str) {
        System.out.println("in " + str);
        int[] expanded = expandInput(str.getBytes());
        int[] H = new int[]{
                0x67452301,
                0xEFCDAB89,
                0x98BADCFE,
                0x10325476};

        int j, t;
        for (int i = 0; i < expanded.length / 16; i++) {

            int A = H[0],
                    B = H[1],
                    C = H[2],
                    D = H[3];


            //round 1
            for (j = 0; j < 16; j++) {

                t = A + f(B, C, D) + expanded[i * 16 + z[j]];
                A = D;
                D = C;
                C = B;
                B = ((t << s[j]) | (t >>> (32 - s[j])));
            }

            //round 2
            for (j = 16; j < 32; j++) {
                t = A + g(B, C, D) + expanded[i * 16 + z[j]] + 0x5A827999;
                A = D;
                D = C;
                C = B;
                B = (t << s[j]) | (t >>> (32 - s[j]));
            }

            //round 3
            for (j = 32; j < 48; j++) {
                t = A + h(B, C, D) + expanded[i * 16 + z[j]] + 0x6ED9EBA1;
                A = D;
                D = C;
                C = B;
                B = (t << s[j]) | (t >>> (32 - s[j]));
            }


            H[0] += A;
            H[1] += B;
            H[2] += C;
            H[3] += D;
        }
        byte[] result = new byte[16];

        for (int i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++)
                result[4 * i + j] = (byte) (H[i] >>> (8 * j));
        }

        StringBuilder out = new StringBuilder();
        for (byte temp : result) {
            int decimal = (int) temp & 0xff;  // bytes widen to int, need mask, prevent sign extension
            String hex = Integer.toHexString(decimal);
            out.append(hex);

        }
        return out.toString();
    }
}
