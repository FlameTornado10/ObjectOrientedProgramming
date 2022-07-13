public class Main {



}

class Student {
    private int age;
    private String name;
    private int ID;
    private String[] books = new String[10]; //at last 10 books;
    private int nbook = 0;
    public void borrowBook(String book){
        this.books[nbook] = book;
        nbook++;
    }
    public int getNbook(){
        return this.nbook;
    }
    public int getAge() {
        return this.age;
    }
    public String getName() {
        return this.name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
}

class Book {
    private int PublicationDate ;
    private String name;
    private int ID;
    public int getPublicationDate() {
        return this.PublicationDate;
    }
    public String getName() {
        return this.name;
    }
    public int getID(){
        return this.ID;
    }
    public void setPublicationDate(int PublicationDate) {
        this.PublicationDate = PublicationDate;
    }
    public void setName(String name) {
        this.name = name;
    }
}

class administrator  {
    private String name;
    private Book[] books = new Book[1000];
    private int nbook = 1000;
    public void lendBook(int ID){
        this.books[ID] = null;
        nbook--;
    }
    public int getNbook(){
        return this.nbook;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}