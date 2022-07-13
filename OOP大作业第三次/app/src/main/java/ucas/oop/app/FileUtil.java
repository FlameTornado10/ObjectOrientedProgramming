package ucas.oop.app;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.io.*;
import java.util.Arrays;

public class FileUtil {

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
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
            bWriter.write(str);
            bWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String parse(String content) {
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        // enable table parse!
        options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(content);
        return renderer.render(document);
    }
    public static String mdToHtml(String md, String css){

        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String htmlResult = parse(md);
        String fileCheck = "src=\"";
        String fileCheck1 = "file:";
        int index = 0;
        String tempCheck = htmlResult;
        while((index = tempCheck.indexOf(fileCheck)) != -1){
            String head = htmlResult.substring(0, index + 5);
            String temp = htmlResult.substring(index + 5);
            if(temp.indexOf(fileCheck1) != 0) {
                int tempIndex1 = temp.indexOf("\"");
                int tempIndex2 = temp.indexOf(" ");
                int tempIndex3 = temp.indexOf("/>");
                if (tempIndex1 < tempIndex2 || tempIndex1 < tempIndex3) {
                    htmlResult = head + "file:\\\\" + temp;
                }
            }
            tempCheck = temp;
        }
        htmlResult = "<style type=\"text/css\">" + css + "</style>" +
                     "<div class=\"markdown-body \" >" + htmlResult +
                     "</div>";
        return htmlResult;
    }
}