package ucas.oop.app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

public class NoteBookOperation {
    private static String MD_CSS = null;
    @FXML
    private MenuItem menu_open;
    @FXML
    private TextArea textArea;
    @FXML
    private WebView webView;
    @FXML
    private AnchorPane layoutPane;
    @FXML
    private TableView<NoteBook> noteBookTable;
    @FXML
    private TableColumn<NoteBook, String> noteBookColumn;

    private File result;
    private NoteBook currentNoteBook;
    private KeyCombination CTRL_E = new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN);
    private App app;
    private ObservableList<NoteBook> noteBookData = FXCollections.observableArrayList();
    public void setApp(App app){
        this.app = app;
    }
    @FXML
    private void initialize() {
        File classpath = new File(this.getClass().getResource("").getPath());
        File cssfile = new File(classpath + "\\" + "css.css");
        try {
            MD_CSS = FileUtil.readFile(cssfile);
            MD_CSS = "<style type=\"text/css\">\n" + MD_CSS + "\n</style>\n";
        } catch (Exception e) {
            MD_CSS = "";
        }
        menu_open.setAccelerator(CTRL_E);
        noteBookColumn.setCellValueFactory(
                cellData -> cellData.getValue().getName());
        noteBookTable.setItems(noteBookData);
        noteBookTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    currentNoteBook = newValue;
                    textArea.setText(newValue.getTextArea().getText());
                });
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                final WebEngine webengine = webView.getEngine();
                File f = new File(this.getClass().getResource("").getPath());
                String tempHtmlName = "temp.html";
                String tempHtmlPath = f + "\\" + tempHtmlName;
                File tempHtml = new File(tempHtmlPath);
                String htmlResult = FileUtil.mdToHtml(t1, MD_CSS);
                if (currentNoteBook == null) {
                    File result = new File(f + "\\" + "temp.md");
                    NoteBook noteBook = new NoteBook();
                    noteBook.setName("temp.md");
                    noteBook.setFile(result);
                    noteBook.setTextArea(t1);
                    noteBookData.add(noteBook);
                    currentNoteBook = noteBook;
                    FileUtil.writeFile(currentNoteBook.getFile(), t1);
                } else {
                    FileUtil.writeFile(currentNoteBook.getFile(), t1);
                }
                currentNoteBook.setTextArea(t1);
                FileUtil.writeFile(tempHtml, htmlResult);
                webengine.loadContent(htmlResult);
            }
        });
    }
    @FXML
    private void onMenuOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        result = fileChooser.showOpenDialog(layoutPane.getScene().getWindow());
        if (result != null) {
            NoteBook noteBook = new NoteBook();
            int index = 0;
            int last = 0;
            String temp = result + "";
            while((index = temp.indexOf("\\"))!= -1){
                temp = temp.substring(index + 1);
                if((last = temp.indexOf("\\")) == -1){
                    break;
                }
            }
            String content = FileUtil.readFile(result);
            noteBook.setName(temp);
            noteBook.setFile(result);
            noteBook.setTextArea(content);
            noteBookData.add(noteBook);
            currentNoteBook = noteBook;
            textArea.setText(content);
        }
    }
    @FXML
    private void onMenuSave(ActionEvent event) {
        try {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Markdown files (*.md)", "*.md"));
            File file = fc.showSaveDialog(new Stage());
            FileUtil.writeFile(file, textArea.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onMenuClose(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    private void onMenuDelete(ActionEvent event) {
        textArea.replaceSelection("");
    }
    @FXML
    private void onMenuInsertImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        String imagepath = fileChooser.showOpenDialog(layoutPane.getScene().getWindow()).toString();
        String image = "\n<img src=\"" + imagepath + "\"/>";
        textArea.appendText(image);
    }
    @FXML
    private void onMenuInsertChart(ActionEvent event) {
        String chart = "\n\n" +
                        "|   |   |   |\n" +
                        "| ---- | ---- | ---- |\n" +
                        "|   |   |   |\n";
        textArea.appendText(chart);
    }
    @FXML
    private void onMenuAbout(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Hello, I'm Master Yi", "About", JOptionPane.PLAIN_MESSAGE);
    }
    @FXML
    private void onMenuShortcuts(ActionEvent event) {
        String message = "Open file: Ctrl + E";
        JOptionPane.showMessageDialog(null, message, "Shortcuts", JOptionPane.PLAIN_MESSAGE);
    }
    @FXML
    private void onMenuUpload(){
        if(currentNoteBook == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Notebook");
            alert.setHeaderText("Please open a notebook");
            alert.setContentText("ERROR: notebook is null");
            alert.showAndWait();
        }
        else {
            UploadController uploadController = new UploadController();
            String url = uploadController.uploadDocument(currentNoteBook);
            app.showPersonEditDialog(url);
        }
    }
    @FXML
    private void onMenuExport(){
        if(currentNoteBook == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Notebook");
            alert.setHeaderText("Please open a notebook");
            alert.setContentText("ERROR: notebook is null");
            alert.showAndWait();
        } else {
            ExportController exportController = new ExportController();
            File file = null;
            try {
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pdf files (*.pdf)", "*.pdf"));
                file = fc.showSaveDialog(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            File f = new File(this.getClass().getResource("").getPath());
            String exportHtmlName = "export.html";
            String exportHtmlPath = f + "\\" + exportHtmlName;
            File exportHtml = new File(exportHtmlPath);
            String mdContent = textArea.getText();
            FileUtil.writeFile(exportHtml, FileUtil.parse(mdContent));
            exportController.htmlCodeToPdf(exportHtmlPath, file.getPath());

        }
    }
}
