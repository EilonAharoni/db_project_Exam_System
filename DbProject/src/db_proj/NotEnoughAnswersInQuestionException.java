package db_proj;
public class NotEnoughAnswersInQuestionException extends ExamExeption {

	public NotEnoughAnswersInQuestionException(int numOfAnswersInQuestion) {
		super("There must be at least " + Exam.MINIMUM_ANSSWERS_IN_QUESTION +" answers in the question \n");
		
	}

}
