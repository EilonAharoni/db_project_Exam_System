package db_proj;

import java.io.IOException;
import java.util.Random;

public class AutomaticExam extends Exam {

	public AutomaticExam(int amountOfWantQuestions) {
		super(amountOfWantQuestions);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void creatExam(QuestionesRepository qr)
			throws MoreQuestionsThanAllowedException, NotEnoughAnswersInQuestionException, IOException {
		
		
		if(amountOfWantQuestions > MAX_OF_QUESTIONS_ALLOW) {
			throw new MoreQuestionsThanAllowedException(amountOfWantQuestions);
		}
		
		int questionCounter =0;
		Random r = new Random();
		while (questionCounter < amountOfWantQuestions) {
			int questionIndex = r.nextInt(qr.getNumOfAllQustiones())+1;
			Question question = qr.getQuestionByNumber(questionIndex);
			if(question instanceof MultipleChoiceQuestion) {//case of Multy Question
				int numOfExistsAnswers = ((MultipleChoiceQuestion) question).getNumOfAnswers();//Check if the number of the true answers is valid
				int numOfTrueAnswers = ((MultipleChoiceQuestion) question).getCorrectAnswersCount();
				if(numOfExistsAnswers < AutomaticExam.MINIMUM_ANSSWERS_IN_QUESTION || numOfTrueAnswers > 1){// if there is less then 4 answers in this question Or there is more then one correct answer continue
					continue;
				}
				
				if(!this.addQuestionInExam(question)) {//if this question is alredy exist
					continue;
				}
				int answerCounter = 0;
				
				while(answerCounter < AutomaticExam.MINIMUM_ANSSWERS_IN_QUESTION) {
					int answerIndex = r.nextInt(numOfExistsAnswers)+1;
					
					Answer ans = ((MultipleChoiceQuestion) question).getAnswerByNumber(answerIndex);

					boolean correction =((MultipleChoiceQuestion) question).getAnswersCorrection(answerIndex);
					
					if(!((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]// creat new answer by Description and corection

                     ).addAnswer(ans, correction)) {
						continue;
					
					}
					answerCounter++;
				}
				
				int correctAnswersCount = ((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).getCorrectAnswersCount();
				if (correctAnswersCount == 0) 
					((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).addAnswer(this.noAnswer, true);
				else
					((MultipleChoiceQuestion) this.examQuestions[numOfQustionInExam-1]).addAnswer(this.noAnswer, false);
				
					questionCounter++;
			}
			
			else if(question instanceof OpenQuestion){
				this.addQuestionInExam(question);
				questionCounter++;
			}
			

			
			
			
		}
		this.deployToFile();

	}

}
