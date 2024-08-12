package db_proj;

import java.io.Serializable;

public abstract class Question implements Serializable {
	private static final long serialVersionUID = -352234631959845953L;

	public enum eDifficultyLevel {
		Easy,Medium,Hard
	};
	protected String questionDescription;
	protected eDifficultyLevel dificultyLevel;
	protected static int idCoounter;
	private int id;
	
	
	public Question(String questionDescriptio,eDifficultyLevel dificultyLevel) {
		setQuestionDescription(questionDescriptio);
		this.dificultyLevel = dificultyLevel;
		 this.id = ++idCoounter;
	}
	public Question(Question other) {
		this(other.questionDescription,other.dificultyLevel);
		 this.id = ++idCoounter;
	
		
	}
	
	
	






	public String getQuestionDescription() {
		return questionDescription;
	}
	
	public void setQuestionDescription(String questionDescription) {
		if (questionDescription == null || questionDescription.isEmpty())
			questionDescription = "Undifine question";
		this.questionDescription = questionDescription;
	}
	
	public eDifficultyLevel getDificultyLevel() {
		return dificultyLevel;
	}

	
	@Override
	public String toString() {
		return  questionDescription;
	}










	protected abstract String showeQuestion();
	
	public int getId() {
		return id;
	}




}
