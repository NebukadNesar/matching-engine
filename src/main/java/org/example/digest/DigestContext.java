package org.example.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestContext {
    private String digestAlgo;
    private MessageDigest md;

    public DigestContext(final String digestAlgo) {
        this.digestAlgo = digestAlgo;
        getMessageDigest();
    }

    public void getMessageDigest() {
        try {
            md = MessageDigest.getInstance(digestAlgo);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(" Error no such digest algorithm=" + digestAlgo);
        }
    }

    public static byte[] toByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }

    public void resetDigest() {
        if (md != null) {
            md.reset();
        }
    }

    public void digest() {
        if (md != null) {
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                sb.append(Integer.toHexString(b + 0x800).substring(1));
            }
            System.out.println("MD5 (output) = " + sb);
        }
    }

    public void update(Object message) {
        if (md != null) {
            md.update((byte[]) message);
        }
    }
}
