package com.wordcounttool;

import javax.swing.*;
import java.io.File;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Create an invisible frame to anchor dialogs
        JFrame frame = new JFrame("Word Count Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true); // ensures popups show on top

        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
        fileChooser.setDialogTitle("📂 Select a file to count words");

        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();
            String outputPath = inputFile.getParent() + File.separator + "output.txt";

            // Use SwingUtilities to ensure the pop-up is shown on the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                WordCounter.countWords(inputFile.getAbsolutePath(), outputPath, frame);
            });
        } else {
            JOptionPane.showMessageDialog(frame, "❌ No file selected. Exiting.");
        }

        frame.dispose(); // close the invisible frame Wowhen done
    }
}
