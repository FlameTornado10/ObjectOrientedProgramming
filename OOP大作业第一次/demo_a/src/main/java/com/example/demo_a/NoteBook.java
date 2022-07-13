package com.example.demo_a;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;

import java.io.File;
import java.time.LocalDate;

public class NoteBook {
    private TextArea ttextArea = new TextArea();
    private StringProperty name;
//    File f = new File(this.getClass().getResource("").getPath());
//    String path = f + "\\" + this.name;
    private File file;
    public NoteBook(){
        this(null);
    }
    public NoteBook(String name) {
        this.name = new SimpleStringProperty(name);
    }
    public TextArea getTextArea(){
        return this.ttextArea;
    }
    public File getFile(){
        return this.file;
    }
    public StringProperty getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = new SimpleStringProperty(name);
    }
    public void setFile(File file){
        this.file = file;
    }
    public void setTextArea(String text){
        this.ttextArea.setText(text);
    }
}
