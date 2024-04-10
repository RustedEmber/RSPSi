package rspsi.gui;

import rspsi.Main;
import rspsi.Rspsi;
import rspsi.client.Interface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.util.Date;

import static rspsi.Main.workspace;

public class SaveChanges {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
    public static void exportRsi2(final int type, String selectedPath) {
        try {
            if (Main.topParent == null) {
                System.out.println("Nothing Loaded.");
                return;
            }

            // Get the current date and time
            String dateTimeString = getCurrentDateTime();

            // Create the file path with the formatted date and time
            String fileName = dateTimeString + "_exported_file.rsi";
            String filePath = new File(selectedPath, fileName).getAbsolutePath();

            // Export the file to the specified path
            Rspsi.export(filePath, type == 0 ? new Interface[]{Main.topParent} : Main.editing);

            System.out.println("File exported to: " + filePath);
        } catch (Exception e) {
            System.err.println("Error exporting RSI file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void toFile2() {
        if (Main.topParent == null) {
            System.err.println("Nothing Loaded.");
            return;
        }

        // Get the current date and time in the desired format
        String dateTimeString = getCurrentDateTime();

        // Create the file name with the formatted date and time
        String fileName = dateTimeString + "_exported_file.dat";

        // Create the file path in the C:/ESDZ directory
        String filePath = "C:/ESDZ/" + fileName;

        // Save the file to the specified path
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            Rspsi.packToData(filePath+ (!filePath.toLowerCase().endsWith(".dat") ? ".dat" : ""));
            // For example:
            // fos.write(yourDataBytes);

            System.out.println("File saved to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        Menu menu = new Menu(workspace);
       toFile2();
    }


    public static String getCurrentDateTime() {
        return dateFormat.format(new Date());
    }
}
