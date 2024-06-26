package rspsi.io.util.bzip;

/**
 * Refactored by super_
 * @author super_
 */
public class BZip2BlockEntry {

    BZip2BlockEntry() {
        unzftab = new int[256];
        anIntArray585 = new int[257];
        inUse = new boolean[256];
        inUse16 = new boolean[16];
        seqToUnseq = new byte[256];
        yy = new byte[4096];
        anIntArray593 = new int[16];
        selector = new byte[18002];
        selectorMtf = new byte[18002];
        len = new byte[6][258];
        limit = new int[6][258];
        base = new int[6][258];
        perm = new int[6][258];
        minLens = new int[6];
    }

    byte inputBuffer[];
    int offset;
    int compressedSize;
    int anInt566;
    int anInt567;
    byte outputBuffer[];
    int anInt569;
    int decompressedSize;
    int anInt571;
    int anInt572;
    byte aByte573;
    int anInt574;
    boolean wasRandomised;
    int anInt576;
    int anInt577;
    int blockSize100k;
    int anInt579;
    int origPtr;
    int anInt581;
    int anInt582;
    final int[] unzftab;
    int anInt584;
    final int[] anIntArray585;
    public static int ll8[];
    int inUseOffset;
    final boolean[] inUse;
    final boolean[] inUse16;
    final byte[] seqToUnseq;
    final byte[] yy;
    final int[] anIntArray593;
    final byte[] selector;
    final byte[] selectorMtf;
    final byte[][] len;
    final int[][] limit;
    final int[][] base;
    final int[][] perm;
    final int[] minLens;
    int anInt601;
}