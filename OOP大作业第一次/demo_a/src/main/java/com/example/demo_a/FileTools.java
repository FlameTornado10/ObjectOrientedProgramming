package com.example.demo_a;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.io.*;

public class FileTools {

    public static String readFile(File file) {
        StringBuilder resultStr = new StringBuilder();
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(file));
            String line = bReader.readLine();
            while (line != null) {
                resultStr.append(line + "\n");
                line = bReader.readLine();
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr.toString();
    }

    public static void writeFile(File file, String str) {
        try {
//            str = str.replace("\n","\n\n");
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
            bWriter.write(str);
            bWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String mdToHtml(String md){
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(md);
        String htmlResult = renderer.render(document);
        String fileCheck = new String("src=\"");
        String fileCheck1 = new String("file:");
        int index = 0;
        String tempCheck = new String(htmlResult);
        while((index = tempCheck.indexOf(fileCheck)) != -1){
            String head = htmlResult.substring(0, index + 5);
            String temp = htmlResult.substring(index + 5);
            if(temp.indexOf(fileCheck1) != 0) {
                int tempIndex1 = temp.indexOf("\"");
                int tempIndex2 = temp.indexOf(" ");
                int tempIndex3 = temp.indexOf("/>");
                if (tempIndex1 < tempIndex2 || tempIndex1 < tempIndex3) {
                    htmlResult = head + "file:\\\\\\" + temp;
                }
            }
            tempCheck = temp;
        }
        return htmlResult;
    }
}