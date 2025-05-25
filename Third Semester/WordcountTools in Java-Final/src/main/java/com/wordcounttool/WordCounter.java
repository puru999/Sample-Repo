package com.wordcounttool;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.swing.JFrame;  // Added import for JFrame

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WordCounter {

    public static int lines = 0;
    public static int words = 0;
    public static int characters = 0;

    public static void countWords(String inputFilePath, String outputFilePath, JFrame frame) {
        try {
            String extension = getFileExtension(inputFilePath).toLowerCase();
            StringBuilder content = new StringBuilder();

            switch (extension) {
                case "txt":
                    content.append(Files.readString(Paths.get(inputFilePath)));
                    break;
                case "docx":
                    try (FileInputStream fis = new FileInputStream(inputFilePath);
                         XWPFDocument doc = new XWPFDocument(fis)) {
                        for (XWPFParagraph para : doc.getParagraphs()) {
                            content.append(para.getText()).append("\n");
                        }
                    }
                    break;
                case "pdf":
                    try (PDDocument document = PDDocument.load(new File(inputFilePath))) {
                        PDFTextStripper stripper = new PDFTextStripper();
                        content.append(stripper.getText(document));
                    }
                    break;
                default:
                    System.out.println("Unsupported file type.");
                    return;
            }

            String[] linesArray = content.toString().split("\r?\n");
            for (String line : linesArray) {
                lines++;
                characters += line.length();
                if (!line.trim().isEmpty()) {
                    words += line.trim().split("\\s+").length;
                }
            }

            // ✅ Save to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                writer.write("File: " + inputFilePath + "\n");
                writer.write("Total Lines: " + lines + "\n");
                writer.write("Total Words: " + words + "\n");
                writer.write("Total Characters: " + characters + "\n");
            }

            System.out.println("✅ Output saved to " + outputFilePath);

        } catch (IOException e) {
            System.err.println("❌ Error reading file: " + e.getMessage());
        }
    }

    private static String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        return (dotIndex > 0) ? filePath.substring(dotIndex + 1) : "";
    }
}
