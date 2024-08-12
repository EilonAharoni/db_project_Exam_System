package db_proj;


import java.io.IOException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
import java.util.Scanner;

public class ManuallyExam extends Exam{
//	private Question[] examQuestions;

//	private int amountOfWantQuestions;
	private final String INVALID = "Invalid value";
//	private QuestionesRepository repository;
//	private int numOfQustionInExam;
//	private Answer noAnswer; alredy in Exam
	private Answer multipleAnswers;

	public ManuallyExam(int amountOfWantQuestions) {
		super(amountOfWantQuestions);
		this.multipleAnswers = new Answer("More than one answer are correct!");
		
	}

	public static Scanner s = new Scanner(System.in);
	
	@Override ///////creatExam////////
	public void creatExam(QuestionesRepository repository) throws MoreQuestionsThanAllowedException, IOException, NotEnoughAnswersInQuestionException {	
		if(amountOfWantQuestions > MAX_OF_QUESTIONS_ALLOW) {
			throw new MoreQuestionsThanAllowedException(amountOfWantQuestions);
		}

		int qChoice = -1;
		while (qChoice != 0 && amountOfWantQuestions > 0) {// loop to add all the qustions
			System.out.println("Chooce your qustion or press --> 0 to exit ");
			qChoice = s.nextInt();
			
			if (qChoice < 1) { //|| qChoice > repository.getNumOfAllQustiones()) {// check thet thr qustion number exist
				System.out.println(INVALID);
				break;
			}
			
				Question req1 = repository.getQuestionByNumber(qChoice);// pointer to the question in the repository///////// chang to copy constructor
				if(!this.addQuestionInExam(req1)) {
					System.out.println("This question already exists, plese choose again\n");
					continue;
				};// Add new qustion to the exam   
				if(req1 instanceof MultipleChoiceQuestion) {
					if(((MultipleChoiceQuestion) req1).getNumOfAnswers()<MINIMUM_ANSSWERS_IN_QUESTION) {
						throw new NotEnoughAnswersInQuestionException(qChoice);
				}
				}
				
				
				if(req1 instanceof MultipleChoiceQuestion) {
				System.out.println("How many answers would you like in this question?");
				int amountOfAnswers = s.nextInt(); // number of wanted answers
				int amountOfExistAnswers = ((MultipleChoiceQuestion) repository.getQuestionByNumber(qChoice)).getNumOfAnswers();// Number of exist
																							// answer in the qustion
				if (amountOfAnswers < 1 || amountOfAnswers > amountOfExistAnswers) {
					System.out.println(INVALID);
					break;
				}
				if (amountOfAnswers < MINIMUM_ANSSWERS_IN_QUESTION) {
					throw new NotEnoughAnswersInQuestionException(amountOfAnswers);
				}
				
				System.out.println(req1.toString()); // print the answers of the qustion
				int aChoice = -1;
				while (aChoice != 0 && amountOfAnswers > 0) { // loop to add answers
						System.out.println("Chooce your answers or press --> 0 to exit ");
						aChoice = s.nextInt();
						int numOfAnswers = ((MultipleChoiceQuestion) req1).getNumOfAnswers();
						if(aChoice<1 || aChoice > numOfAnswers) {
							System.out.println(INVALID);
							continue;
						}
						Answer ans = ((MultipleChoiceQuestion) req1).getAnswerByNumber(aChoice);
						if (ans == null) {// can check just the int (aChoice)
							System.out.println(INVALID);
							break;
						}
						
						boolean correction =((MultipleChoiceQuestion) req1).getAnswersCorrection(aChoice);
						
						if(!((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]// creat new answer by Description and corection

	                     ).addAnswer(ans, correction)) {
							System.out.println("This answer has already been selected, please select a new answer\n");
							continue;
						};
						
						amountOfAnswers--;// Numbers of answers to add
					}
				
				int correctAnswersCount = ((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).getCorrectAnswersCount();
				if (correctAnswersCount == 1) {
					((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).addAnswer(this.multipleAnswers, false);
					((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).addAnswer(this.noAnswer, false);

				}
				else if (correctAnswersCount == 0) {
					((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).addAnswer(this.multipleAnswers, false);
					((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).addAnswer(this.noAnswer, true);
				}
				else {
					((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).cleanAnswersCorrection();
					((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).addAnswer(this.multipleAnswers, true);
					((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).addAnswer(this.noAnswer, false);

				  }
		       }
				amountOfWantQuestions--;
			} 
				this.deployToFile();
		}
			
		

		///////addQuestionToExam/////////
//		public boolean addQuestionInExam(Question question) {
//			for (int i = 0; i < numOfQustionInExam; i++) {
//				if (question.getQuestionDescription().equals(examQuestions[i].getQuestionDescription())) // this question alredy exist
//					return false;
//			}
//			if(question instanceof MultipleChoiceQuestion) {
//			this.examQuestions[numOfQustionInExam++] = new MultipleChoiceQuestion((MultipleChoiceQuestion) question);
//						}
//			else {
//				this.examQuestions[numOfQustionInExam++] = new OpenQuestion((OpenQuestion) question);
//			}
//			return true;
//
//		}
//		
	

	public Question[] getExamQuestions() {
		return examQuestions;
	}

		
}
