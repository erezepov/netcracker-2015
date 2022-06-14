package com.netcracker.edu.rezepov.arch;

import java.io.IOException;

/**
 * Class to test Archiver
 */
public class Main {
    public static void main(String[] args) {
        Archiver archiver = new Archiver();
        byte[] inputByte = new byte[1];
        String[] params;
        System.out.println("'z' for create a new archive");
        System.out.println("'u' for unpack existing one");
        System.out.println("'a' for add file to an archive");
        System.out.println("'g' for get comment to an archive");
        System.out.println("'s' for set comment to an archive");
        System.out.println("'c' for create a new archive with comment");
        try {
            System.in.read(inputByte);
            switch ((char)inputByte[0]) {
                case 'z':
                    params = new String[args.length - 1];
                    System.arraycopy(args, 1, params, 0, args.length - 1);
                    archiver.zip(args[0], params);
                    break;
                case 'u':
                    if (args.length == 1) {
                        archiver.unzip(args[0]);
                    } else if (args.length == 2) {
                        archiver.unzip(args[0], args[1]);
                    }
                    break;
                case 'a':
                    archiver.addFile(args[0], args[1]);
                    break;
                case 'g':
                    System.out.println(archiver.getComment(args[0]));
                    break;
                case 's':
                    archiver.setComment(args[0], args[1]);
                    break;
                case 'w':
                    params = new String[args.length - 2];
                    System.arraycopy(args, 1, params, 0, args.length - 2);
                    archiver.zip(args[0], params, args[args.length - 1]);
                    break;
                default:
                    System.out.println("No.");
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Wrong type.");
        }
    }
}
