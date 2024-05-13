package Classes;

public class Exercise {
    private String name;
    private String language;
    private String statement;
    private String code;

    public Exercise(String name, String language, String statement){
        this.name = name;
        this.language = language;
        this.statement = statement;
    }

    public String getName(){
        return name;
    }

    public String getLanguage(){
        return language;
    }

    public String getStatement(){
        return statement;
    }

    public String getCode() {
        return this.code;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setLanguage(String newLanguage){
        this.language = newLanguage;
    }

    public void setStatement(String newStatement){
        this.statement = newStatement;
    }

}

