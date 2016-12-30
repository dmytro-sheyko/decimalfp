package org.decimalfp.transform;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class T {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (InputStream is = T.class.getResourceAsStream("/sample/Sample.class")) {
            int r;
            while ((r = is.read()) != -1) {
                bos.write(r);
            }
        }
        byte[] initial = bos.toByteArray();
        System.out.println(initial.length);
        byte[] transformed = TransformerUtil.transform(initial);
        System.out.println(transformed.length);
        System.out.println(initial != transformed);
        System.out.println(Arrays.equals(initial, transformed));
        File outfile = new File("gen/sample/Sample.class");
        outfile.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(outfile)) {
            fos.write(transformed);
        }
    }
}
