package com.netcracker.edu.rezepov.arch;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * Class that often can pack and extract zip files. It also can set and get comments to archives.
 * @author Evgeny Rezepov
 */
public class Archiver {

    /**
     * Creates a new archive named <code>archiveName</code>
     * with the the files of given <code>filenames</code>.
     * @param archivePath the path to the archive to be created
     * @param filenames array of the names of files to be packed
     * @see #zip(String, String[], String)
     */
    public void zip (String archivePath, String[] filenames) {
        zip(archivePath, filenames, null);
    }

    /**
     * Tries to write file of <code>packFilePath</code> to the given output stream <code>zipOut</code>.
     * <p> Given a folder to pack, packs it recursively, creating a temp archive
     * using {@link #zip(String, String[])} and removing it as soon as
     * it is written to <code>zipOut</code>.
     * @param packFilePath path to file or folder to be packed
     * @param zipOut output stream to write <code>packFile</code>
     */
    private void pack (String packFilePath, ZipOutputStream zipOut) {
        try {
            File packFile = new File(packFilePath);
            if (packFile.isDirectory()) {
                if (packFile.list().length > 0) {
                    String[] filePaths = new String[packFile.list().length];
                    File tempZip = new File(packFilePath + File.separator + packFile.getName() + ".zip");
                    for (int i = 0; i < packFile.list().length; ++i) {
                        filePaths[i] = packFilePath + File.separator + packFile.list()[i];
                    }
                    zip(tempZip.getAbsolutePath(), filePaths);
                    zipOut.putNextEntry(new ZipEntry(packFile.getName() + File.separator + tempZip.getName()));
                    zipOut.write(Files.readAllBytes(tempZip.toPath()));
                    zipOut.closeEntry();
                    Files.deleteIfExists(tempZip.toPath());
                } else {
                    zipOut.putNextEntry(new ZipEntry(packFile.getName() + File.separator));
                    zipOut.closeEntry();
                }
            } else {
                zipOut.putNextEntry(new ZipEntry(packFile.getName()));
                zipOut.write(Files.readAllBytes(packFile.getAbsoluteFile().toPath()));
                zipOut.closeEntry();
            }
        } catch (Exception e) {
            System.err.println("Cannot pack file " + packFilePath);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Calls {@link #unzip(String, String)}
     * with <code>destDirPath</code> being equal to <code>archivePath</code> parent directory's path.
     * @param archivePath path to an archive to be extracted
     */
    public void unzip (String archivePath) {
        try {
            String destDirPath = new File(archivePath).getParent();
            unzip(archivePath, destDirPath);
        } catch (Exception e) {
            System.err.println("File " + archivePath + " not found.");
        }
    }

    /**
     * Extract files from an archive of <code>archivePath</code> to the <code>destDirPath</code>.
     * Will not replace existing files with the same names.
     * @param archivePath path of an archive to extract from
     * @param destDirPath path of a directory to extract to
     */
    public void unzip (String archivePath, String destDirPath) {
        ZipFile zipFile;
        ZipEntry zipEntry;
        File extractFile;
        InputStream zipIn = null;
        FileOutputStream fileOut = null;
        try {
            zipFile = new ZipFile(archivePath);
            for (Enumeration enumeration = zipFile.entries(); enumeration.hasMoreElements(); ) {
                zipEntry = (ZipEntry) enumeration.nextElement();
                extractFile = new File(destDirPath + File.separator + zipEntry.getName());
                if (zipEntry.getName().endsWith(File.separator)) {
                    extractFile.mkdirs();
                } else {
                    if (extractFile.toPath().getParent() != null)
                    extractFile.toPath().getParent().toFile().mkdirs();
                    if (!extractFile.createNewFile()) {
                        throw new IOException("Cannot create file " + extractFile);
                    }
                    zipIn = zipFile.getInputStream(zipEntry);
                    fileOut = new FileOutputStream(extractFile);
                    readAndWrite(zipIn, fileOut);
                    zipIn.close();
                    fileOut.close();
                }
            }
        } catch (Exception e) {
            System.err.println("Cannot extract " + archivePath);
            System.err.println(e.getMessage());
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
                if (zipIn != null) {
                    zipIn.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Adds file of its path <code>addFilePath</code> to archive of its path <code>archivePath</code>.
     * @param addFilePath path to the file to be added
     * @param archivePath path to the archive to add to
     * @return <code>true</code> if adding file was success. Otherwise <code>false</code>.
     */
    public boolean addFile (String addFilePath, String archivePath) {
        try {
            ZipOutputStream zipOut;
            if ((zipOut = openZipOutputStream(archivePath, getComment(archivePath))) != null) {
                pack(addFilePath, zipOut);
                zipOut.close();
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Opens <code>ZipOutputStream</code> and sets its <code>comment</code>
     * in order to add more files to an archive.
     * @param archivePath a path to an existing or not archive
     * @return <code>ZipOutputStream</code> if can. Otherwise <code>null</code>.
     */
    private ZipOutputStream openZipOutputStream (String archivePath, String comment) {
        ZipOutputStream zipOut;
        try {
            File archive = new File(archivePath);
            if (!archive.exists() || archive.isDirectory()) {
               if (archive.createNewFile()) {
                   zipOut = new ZipOutputStream(new FileOutputStream(archive));
                   zipOut.setComment(comment);
                   return zipOut;
               } else {
                   return null;
               }
            } else {
                ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(Files.readAllBytes(archive.toPath())));
                zipOut = new ZipOutputStream(new FileOutputStream(archive));
                ZipEntry zipEntry;
                zipOut.setComment(comment);
                while ((zipEntry = zipIn.getNextEntry()) != null) {
                    zipOut.putNextEntry(zipEntry);
                    readAndWrite(zipIn, zipOut);
                    zipOut.closeEntry();
                }
                zipIn.close();
                return zipOut;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Copies bytes from one stream (<code>fromStream</code>) to another (<code>toStream</code>)
     * @param fromStream
     * @param toStream
     * @throws IOException if something went wrong
     */
    private void readAndWrite (InputStream fromStream, OutputStream toStream) throws IOException {
        byte[] buffer = new byte[2048];
        for (int len; (len = fromStream.read(buffer)) > 0; ) {
            toStream.write(buffer, 0, len);
        }
    }

    /**
     * Returns comment to an archive from <code>archivePath</code>.
     * @param archivePath
     * @return <code>String</code> if and only if everything worked right. Otherwise <code>null</code>.
     */
    public String getComment (String archivePath) {
        try {
            return new ZipFile(new File(archivePath)).getComment();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Sets given <code>comment</code> to an archive of <code>archivePath</code>
     * @param archivePath
     * @param comment
     */
    public void setComment (String archivePath, String comment) {
        try {
            openZipOutputStream(archivePath, comment).close();
        } catch (NullPointerException e) {
            System.err.println("Cannot open " + archivePath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Creates a new archive named <code>archiveName</code>
     * with the the files of given <code>filenames</code>
     * and sets its comment to <code>comment</code>.
     * If archive with the same filename exists, will try to add files to it.
     * <p> Avoids empty filenames. Uses {@link #pack(String, ZipOutputStream)}
     * to write to an output stream.
     * <p> <code>archiveName</code> and <code>filenames</code> can also be paths to the files.
     * @param archivePath the path to the archive to be created
     * @param filenames array of the names of files to be packed
     * @param comment comment to the archive to be created
     */
    public void zip (String archivePath, String[] filenames, String comment) {
        ZipOutputStream zipOut = null;
        try {
            zipOut = openZipOutputStream(archivePath, comment);
            for (String filename: filenames) {
                pack(filename, zipOut);
            }
        } catch (Exception e) {
            System.err.println("Cannot write to " + archivePath);
            System.err.println(e.getMessage());
        } finally {
            try {
                if (zipOut != null) {
                    zipOut.close();
                }
            } catch (NullPointerException | IOException e) {
                System.err.println("Failed to close " + archivePath);
                System.err.println(e.getMessage());
            }
        }
    }
}