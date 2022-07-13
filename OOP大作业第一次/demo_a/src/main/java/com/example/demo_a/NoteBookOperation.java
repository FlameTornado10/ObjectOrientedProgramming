package com.example.demo_a;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;

public class NoteBookOperation {
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

    private ObservableList<NoteBook> noteBookData = FXCollections.observableArrayList();
    public void temp(String mynote_name){
        final WebEngine webengine = webView.getEngine();
        File f = new File(this.getClass().getResource("").getPath());
        String tempHtmlName = "temp.html";
        String mynote_Path = f + "\\" + mynote_name;
        String tempHtmlPath = f + "\\" + tempHtmlName;
        File myNote = new File(mynote_Path);
        File tempHtml = new File(tempHtmlPath);
        //markdown-->html
        String note = FileTools.readFile(myNote);
        //text Area initialize
        textArea.setText(note);
        String htmlResult = FileTools.mdToHtml(note);
        //htmlResult --> temp.html
        FileTools.writeFile(tempHtml, htmlResult);
        System.out.println(htmlResult);
        webengine.loadContent(htmlResult);
    }
    private void setContent(NoteBook newValue){
        textArea = newValue.getTextArea();
        currentNoteBook = newValue;
    }
    @FXML
    private void initialize() {
        noteBookColumn.setCellValueFactory(
                cellData -> cellData.getValue().getName());
        noteBookTable.setItems(noteBookData);
        noteBookTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
//                    if(newValue != null) {
//                        setContent(newValue);
//                    }
                    System.out.println("hey");
                    System.out.println(newValue.getName());
                });
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                final WebEngine webengine = webView.getEngine();
                File f = new File(this.getClass().getResource("").getPath());
                String tempHtmlName = "temp.html";
                String tempHtmlPath = f + "\\" + tempHtmlName;
                File tempHtml = new File(tempHtmlPath);
                String htmlResult = FileTools.mdToHtml(t1);
                FileTools.writeFile(currentNoteBook.getFile(), t1);
                FileTools.writeFile(tempHtml, htmlResult);
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
            noteBook.setName(temp);
            noteBook.setFile(result);
            String tt = new String(FileTools.readFile(result));
            noteBook.setTextArea(tt);
            noteBookData.add(noteBook);
            currentNoteBook = noteBook;
            textArea.setText(tt);
        }
    }

    @FXML
    private void onMenuSave(ActionEvent event) {
        if (result != null) {
            FileTools.writeFile(result, textArea.getText());
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
    private void onMenuAbout(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Hello, I'm Master Yi", "About", JOptionPane.PLAIN_MESSAGE);
    }

    @FXML
    private void onContextSelectAll(ActionEvent event) {
        textArea.selectAll();
    }

}
