public class Car {

}
class CarInFactory {
    private String model;
    private int productionDate;
    private int engineID;
    private int warrantyPeriod;

    public String getModel() {
        return this.model;
    }
    public int getProductionDate() {
        return this.productionDate;
    }
    public int getEngineID() {
        return this.engineID;
    }
    public int getWarrantyPeriod() {
        return this.warrantyPeriod;
    }
    public void setProductionDate(int date) {
        this.productionDate = date;
    }
    public void setEngineID(int date) {
        this.engineID = date;
    }
    public void setWarrantyPeriod(int date) {
        this.warrantyPeriod = date;
    }
}

class Car_Driver{
    private String model;
    private int productionDate;
    private int engineID;
    private int warrantyPeriod;

    public String getModel() {
        return this.model;
    }
    public int getProductionDate() {
        return this.productionDate;
    }
    public int getEngineID() {
        return this.engineID;
    }
    public int getWarrantyPeriod() {
        return this.warrantyPeriod;
    }
    public void setProductionDate(int date) {
        this.productionDate = date;
    }
    private int DegreeOfLove;
    public int getDegreeOfLove(){
        return this.DegreeOfLove;
    }
}
class Car_TAB{      //Traffic Administration Bureau
    private int ID;
    private int scoresRemaining;
    private String model;
    private int productionDate;
    private int engineID;

    public String getModel() {
        return this.model;
    }
    public int getProductionDate() {
        return this.productionDate;
    }
    public int getScoresRemaining() {
        return this.scoresRemaining;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public void setScoresRemaining(int score){
        this.scoresRemaining -= score;
    }
}