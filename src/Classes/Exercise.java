package Classes;

/**
 * The Exercise class represents an exercise with its name, language, and statement.
 */
public class Exercise {
    private String name;
    private String language;
    private String statement;
    private String code;

    /**
     * Constructs an Exercise object with the specified name, language, and statement.
     *
     * @param name the name of the exercise.
     * @param language the programming language of the exercise.
     * @param statement the statement or description of the exercise.
     */
    public Exercise(String name, String language, String statement){
        this.name = name;
        this.language = language;
        this.statement = statement;
    }

    /**
     * Gets the name of the exercise.
     *
     * @return the name of the exercise.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the programming language of the exercise.
     *
     * @return the programming language of the exercise.
     */
    public String getLanguage(){
        return language;
    }

    /**
     * Gets the statement or description of the exercise.
     *
     * @return the statement or description of the exercise.
     */
    public String getStatement(){
        return statement;
    }

    /**
     * Gets the code of the exercise.
     *
     * @return the code of the exercise.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the name of the exercise.
     *
     * @param newName the new name of the exercise.
     */
    public void setName(String newName){
        this.name = newName;
    }

    /**
     * Sets the programming language of the exercise.
     *
     * @param newLanguage the new programming language of the exercise.
     */
    public void setLanguage(String newLanguage){
        this.language = newLanguage;
    }

    /**
     * Sets the statement or description of the exercise.
     *
     * @param newStatement the new statement or description of the exercise.
     */
    public void setStatement(String newStatement){
        this.statement = newStatement;
    }

}
