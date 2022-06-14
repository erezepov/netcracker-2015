package ru.ncedu.java.tasks;

import java.io.*;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by eschy_000 on 16.10.2015.
 */
public class WordProcessorImpl implements WordProcessor{
    private String source;

    public String getText() {
        return source;
    }

    public void setSource(String src) throws IllegalArgumentException {
        if (src == null) {
            throw new IllegalArgumentException("null");
        } else {
            source = src;
        }
    }

    public void setSourceFile(String srcFile) throws IOException, IllegalArgumentException {
        if (srcFile == null) {
            throw new IllegalArgumentException("null");
        } else {
            source = new String(Files.readAllBytes(new File(srcFile).toPath()));
        }
    }

    public void setSource(FileInputStream fis) throws IOException, IllegalArgumentException {
        if (fis == null) {
            throw new IllegalArgumentException("null");
        } else {
            BufferedReader buffread = new BufferedReader(new InputStreamReader(fis));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = buffread.readLine()) != null) {
                out.append(line);
            }
            source = out.toString();
        }
    }

    public Set<String> wordsStartWith(String begin) throws IllegalStateException {
        if (source == null) {
            throw new IllegalStateException("null");
        }
        if (begin == null) {
            begin = "";
        }
        Set<String> words = new HashSet<>();
        Scanner scanner = new Scanner(new StringReader(source));
        String word;
        while (scanner.hasNext()) {
            if ((word = scanner.next().toLowerCase()).startsWith(begin.toLowerCase())) {
                words.add(word);
            }
        }
        return words;
    }
}
