package com.hw5.demo;

import java.io.*;
import java.util.*;

public class Convert {
    String inputPath;
    String outputPath;
    final static String ffmpegPath = "D:\\ffmpeg\\bin\\";

    public void process(String fileName) throws InterruptedException {
        inputPath = "D:\\GitHub\\SA-Homework\\SA-HW5\\HW5-input\\" + fileName;
        outputPath = "D:\\GitHub\\SA-Homework\\SA-HW5\\HW5-output\\";
        //String fileName = inputPath.substring(inputPath.lastIndexOf("\\") + 1, inputPath.lastIndexOf("."));
        String filetype = inputPath.substring(inputPath.lastIndexOf(".") + 1, inputPath.length());
        System.out.println(fileName);
        System.out.println(filetype);

        List<String> command = new ArrayList<String>();
        command.add(ffmpegPath + "ffmpeg");
        command.add("-i");
        command.add(inputPath);
        command.add("-y");
        command.add("-r");
        command.add("15");
        command.add("-s");
        command.add("480x360");
        command.add(outputPath + fileName.substring(0,fileName.lastIndexOf(".")) + "-360p" + "." + filetype);

        List<String> command1 = new ArrayList<String>();
        command1.add(ffmpegPath + "ffmpeg");
        command1.add("-i");
        command1.add(inputPath);
        command1.add("-y");
        command1.add("-r");
        command1.add("15");
        command1.add("-s");
        command1.add("864x480");
        command1.add(outputPath + fileName.substring(0,fileName.lastIndexOf(".")) + "-480p" + "." + filetype);

        try {
            Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
            new PrintStream(videoProcess.getErrorStream()).start();
            new PrintStream(videoProcess.getInputStream()).start();
            videoProcess.waitFor();

            Process videoProcess1 = new ProcessBuilder(command1).redirectErrorStream(true).start();
            new PrintStream(videoProcess1.getErrorStream()).start();
            new PrintStream(videoProcess1.getInputStream()).start();
            videoProcess1.waitFor();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class PrintStream extends Thread {
        java.io.InputStream __is = null;

        public PrintStream(java.io.InputStream is) {
            __is = is;
        }

        public void run() {
            try {
                while (this != null) {
                    int _ch = __is.read();
                    if (_ch != -1)
                        System.out.print((char) _ch);
                    else
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}