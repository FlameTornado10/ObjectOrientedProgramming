package ucas.oop.app;


import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.util.List;

public class ExportController {

    public static void htmlCodeToPdf(String filePath, String pdfPath) {
        Document document = new Document();
        try {
            StyleSheet st = new StyleSheet();
            st.loadTagStyle("body", "leading", "16,0");
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            List p = HTMLWorker.parseToList(new FileReader(filePath), st);
            for(int k = 0; k < p.size(); ++k) {
                document.add((Element)p.get(k));
            }
            document.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) {
		String htmlPath = "c:\\image\\2.html";
		String pdfPath ="c:\\image\\4.pdf";
		File htmlFile = new File(htmlPath);
		File pdfFile = new File(pdfPath);
		if(htmlFile.exists()){
			if(!pdfFile.exists()){
				try {
					pdfFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
            ExportController.htmlCodeToPdf(htmlPath, pdfPath);
		}
	}
}
