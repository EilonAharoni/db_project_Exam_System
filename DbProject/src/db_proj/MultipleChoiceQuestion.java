package db_proj;

public class MultipleChoiceQuestion extends Question{
//	private static final long serialVersionUID = 7799355368392491640L;
	private static final int MAX_NUM_OF_ANSWERS = 10;
	private int numOfAnswers;
	private Answer[] answers;
	public boolean[] answersCorrection; // MADE THIS PUBLIC TO REACH FROM QUESTION DAO


	public MultipleChoiceQuestion(String questionDescription,eDifficultyLevel dificultyLevel) {
		super(questionDescription,dificultyLevel);
		this.answers = new Answer[MAX_NUM_OF_ANSWERS];
		this.answersCorrection = new boolean[MAX_NUM_OF_ANSWERS];
	}


	//copy constructor
	public MultipleChoiceQuestion(MultipleChoiceQuestion other) {
		super(other);
		this.answers = new Answer[MAX_NUM_OF_ANSWERS];
		this.answersCorrection = new boolean[MAX_NUM_OF_ANSWERS];
	
	}





	//	public Question getQuestionById(int id) {// riposetory
//	
//	}
	// Add answer
	public boolean addAnswer(Answer answer, boolean correction) {// (2)
		if (this.numOfAnswers == MAX_NUM_OF_ANSWERS) {
			return false;
		}
		for (int i = 0; i < numOfAnswers; i++) {
			if (this.answers[i] == answer)
				return false;
		}
		this.answers[numOfAnswers] = answer;
		this.answersCorrection[numOfAnswers++] = correction;
		return true;

	}


	public void cleanAnswersCorrection() {
		for (int i = 0; i < numOfAnswers; i++) {
			this.answersCorrection[i] = false;

		}
	}

	// delete Answer
	public boolean deleteAnswerOfQuestion(int index) {// (6) index in array
		if (numOfAnswers == 0)
			return false;

		if (index == numOfAnswers) {
			answers[index - 1] = null;
			answersCorrection[index - 1] = false;
			numOfAnswers--;
			return true;
		}

		if (index <= numOfAnswers && index > 0) {// loop move all answer to left
			answers[index - 1] = answers[numOfAnswers - 1];
			answers[numOfAnswers - 1] = null;
			answersCorrection[index - 1] = answersCorrection[numOfAnswers - 1];
			answersCorrection[numOfAnswers - 1] = false;
			numOfAnswers--;
			return true;
		}
		return false;
	}

	// getNumOfAnswers
	public int getNumOfAnswers() {
		return numOfAnswers;
	}

	public Answer[] getAnswers() {
		return answers;
	}

	public boolean getAnswersCorrection(int index) {
		return answersCorrection[index-1];
	}

	public Answer getAnswerByNumber(int anwerIndex) {
		if (anwerIndex > 0 && anwerIndex <= numOfAnswers)
			return answers[anwerIndex - 1];
		return null;
	}

	public String showeQuestion() {// without boolian parmetr
		StringBuffer sb1 = new StringBuffer();
		sb1.append(questionDescription + "\n");
		for (int i = 0; i < numOfAnswers; i++) {
			int j = i + 1;
			sb1.append(j + ") " + answers[i].toString() + "\n");
		}
		return sb1.toString();
	}

	public int getCorrectAnswersCount() {
		int count = 0;
		for (int i = 0; i < numOfAnswers; i++) {
			if (this.answersCorrection[i]) {
				count++;
			}
		}
		return count;
	}
	
	public boolean answerExist(Answer ans) {
		for (int i = 0; i < numOfAnswers; i++) {
			if(this.answers[i]== ans)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {// (1)
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()+"\n");
		for (int i = 0; i < numOfAnswers; i++) {
			int j = i + 1;
			sb.append(j + ") " + answers[i].toString() + " - " + answersCorrection[i] + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}

}
