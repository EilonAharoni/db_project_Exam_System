package db_proj;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface Examable {
	void creatExam(QuestionesRepository qr, int testID, Connection conn) throws MoreQuestionsThanAllowedException, NotEnoughAnswersInQuestionException, IOException, SQLException;

}
