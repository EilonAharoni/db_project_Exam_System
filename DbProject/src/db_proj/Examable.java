package db_proj;

import java.io.IOException;

public interface Examable {
	void creatExam(QuestionesRepository qr) throws MoreQuestionsThanAllowedException,NotEnoughAnswersInQuestionException, IOException;

}
