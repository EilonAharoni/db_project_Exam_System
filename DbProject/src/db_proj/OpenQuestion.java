package db_proj;

public class OpenQuestion extends Question {
	private Answer schoolAnswer;

	public OpenQuestion(String questionDescriptio,eDifficultyLevel dificultyLevel,String schoolAnswer) {
		super(questionDescriptio, dificultyLevel);
		this.setSchoolAnswer(schoolAnswer);
	}
	
	public String showeQuestion() {// without the answer
		StringBuffer sb1 = new StringBuffer();
		sb1.append(this.getQuestionDescription() + "\n");
		
		return sb1.toString();
	}

	public OpenQuestion(OpenQuestion other) {
		super(other);
		this.schoolAnswer = other.schoolAnswer;
		
	}

	@Override
	public String toString() {
		return super.toString()+"\n"+ "The answer: " + schoolAnswer + "\n\n";
	}

	public String getSchoolAnswer() {
		return schoolAnswer.getAnswerDescription();
	}

	public void setSchoolAnswer(String schoolAnswer) {
		this.schoolAnswer = new Answer(schoolAnswer);
	}

}
