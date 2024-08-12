package db_proj;
public class MoreQuestionsThanAllowedException extends ExamExeption {

	private static final long serialVersionUID = 2688618439811857521L;

	public MoreQuestionsThanAllowedException(int maxNumOfQuetions) {
		super("More questions than allowed!\n");
		
	}

}
