package db_proj;

import java.io.Serializable;

public class Answer implements Serializable {
	private static final long serialVersionUID = -2222817151022932593L;
	private int id;
	private String answerDescription;
	
	public Answer(String answerDescription) {
		this.answerDescription = answerDescription;
	}
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getAnswerDescription() {
		return answerDescription;
	}

	public void setAnswerDescription(String answerDescription) {
		if(answerDescription != null && !answerDescription.isEmpty()) 
			this.answerDescription = answerDescription;
	}
	




	@Override
	public String toString() {
		return   answerDescription ;
	}

}
