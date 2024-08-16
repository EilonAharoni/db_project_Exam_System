package db_proj;

import java.io.Serializable;
import java.util.Arrays;

import db_proj.Question.eDifficultyLevel;

public class QuestionesRepository implements Serializable{
	private static final long serialVersionUID = -7886997808143966345L;
	private String subject;
	// PUBLIC IN ORDER TO REACH IT FROM MAIN
	public int numOfAllQustiones;
	public int numOfAllAnswers;
	public Question[] allQuestions;
	public Answer[] allAnswers;

	public QuestionesRepository(int numOfAllQustiones,String subject) {
		this.allQuestions = new Question[numOfAllQustiones];
		this.setSubject(subject);

	}

	public QuestionesRepository(String subject) {// defult constractor
		this.allQuestions = new Question[10];
		this.allAnswers = new Answer[50];
		this.setSubject(subject);

	}

	public int getNumOfAllQustiones() {
		return numOfAllQustiones;
	}

	public int getNumOfAnswers() {
		return numOfAllAnswers;
	}

	public void initHardCode() {
			switch(subject) {
			case "Math": {
	//Hard code			

				addMultipleChoiceQuestion("How much is 2+2 ?",eDifficultyLevel.Easy);
				addAnswerToRepository("6");
				addAnswerToRepository("13");
				addAnswerToRepository("4");
				addAnswerToRepository("5");
				addAnswerFromRepoToQusteion(1, 1, false);
				addAnswerFromRepoToQusteion(1, 2, false);
				addAnswerFromRepoToQusteion(1, 3, true);
				addAnswerFromRepoToQusteion(1, 4, false);
				
				
				
				addMultipleChoiceQuestion("How much is √36 ?",eDifficultyLevel.Medium);
				addAnswerToRepository("12");
				addAnswerFromRepoToQusteion(2, 1, true);
				addAnswerFromRepoToQusteion(2, 2, false);
				addAnswerFromRepoToQusteion(2, 3, false);
				addAnswerFromRepoToQusteion(2, 4, false);

				
				
				addMultipleChoiceQuestion("How much is 8*5 ?",eDifficultyLevel.Medium);
				addAnswerToRepository("40");
				addAnswerToRepository("35");
				addAnswerToRepository("24");
				addAnswerToRepository("20");
				addAnswerFromRepoToQusteion(3, 5, false);
				addAnswerFromRepoToQusteion(3, 6, true);
				addAnswerFromRepoToQusteion(3, 7, false);
				addAnswerFromRepoToQusteion(3, 8, false);
				
				
				
				addMultipleChoiceQuestion("How much is √25 ?",eDifficultyLevel.Medium);
				addAnswerToRepository("9");
				addAnswerFromRepoToQusteion(4, 1, false);
				addAnswerFromRepoToQusteion(4, 4, true);
				addAnswerFromRepoToQusteion(4, 3, false);
				addAnswerFromRepoToQusteion(4, 8, false);
				
				addMultipleChoiceQuestion("What is the square root of 64?",eDifficultyLevel.Medium);
				addAnswerToRepository("8");
				addAnswerFromRepoToQusteion(5, 1, false);
				addAnswerFromRepoToQusteion(5, 11, true);
				addAnswerFromRepoToQusteion(5, 3, false);
				addAnswerFromRepoToQusteion(5, 4, false);
				addAnswerFromRepoToQusteion(5, 6, false);
				
				addMultipleChoiceQuestion("What is the value of π (pi) to two decimal places?",eDifficultyLevel.Easy);
				addAnswerToRepository("2.14");
				addAnswerToRepository("3.14");	
				addAnswerToRepository("4.14");	
				addAnswerToRepository("5.14");	
				addAnswerFromRepoToQusteion(6, 12, false);
				addAnswerFromRepoToQusteion(6, 13, true);
				addAnswerFromRepoToQusteion(6, 14, false);
				addAnswerFromRepoToQusteion(6, 15, false);
				
				addMultipleChoiceQuestion("If x = 5 and y = 3, what is the value of 2x + 3y?",eDifficultyLevel.Hard);
				addAnswerToRepository("19");
				addAnswerFromRepoToQusteion(7, 2, false);
				addAnswerFromRepoToQusteion(7, 4, false);
				addAnswerFromRepoToQusteion(7, 5, false);
				addAnswerFromRepoToQusteion(7, 16, true);
				
				addMultipleChoiceQuestion("Solve the equation: 2x + 5 = 17", eDifficultyLevel.Hard);
				addAnswerToRepository(" x = 4");
				addAnswerToRepository(" x = 8");
				addAnswerToRepository(" x = 6");
				addAnswerToRepository(" x = 10");
				addAnswerFromRepoToQusteion(8, 17, false);
				addAnswerFromRepoToQusteion(8, 18, false);
				addAnswerFromRepoToQusteion(8, 19, true);
				addAnswerFromRepoToQusteion(8, 20, false);
				
				addOpenQuestion("Calculate the perimeter of a rectangle with length 12 units and width 5 units.",eDifficultyLevel.Hard,"P = 2 * (12 + 5) = 2 * 17 = 34 units.");
				

			
				
				break;
				}
			
			case "Capitals":
			{
				
				addMultipleChoiceQuestion("What is the Capital City of Israel ?",eDifficultyLevel.Easy);
				addAnswerToRepository("Washington");
				addAnswerToRepository("London");
				addAnswerToRepository("Jerusalem");
				addAnswerToRepository("Amsterdam");
				addAnswerFromRepoToQusteion(1, 1, false);
				addAnswerFromRepoToQusteion(1, 2, false);
				addAnswerFromRepoToQusteion(1, 3, true);
				addAnswerFromRepoToQusteion(1, 4, false);
				
				
				
				addMultipleChoiceQuestion("What is the capital city of Japan?",eDifficultyLevel.Medium);
				addAnswerToRepository("Tokyo");
				addAnswerToRepository("Beijing");
				addAnswerToRepository("Seoul");
				addAnswerToRepository("Bangkok");
				addAnswerFromRepoToQusteion(2, 5, true);
				addAnswerFromRepoToQusteion(2, 6, false);
				addAnswerFromRepoToQusteion(2, 7, false);
				addAnswerFromRepoToQusteion(2, 8, false);
				
				addMultipleChoiceQuestion("What is the capital city of Brazil?", eDifficultyLevel.Hard);
				addAnswerToRepository("Rio de Janeiro");
				addAnswerToRepository("Sao Paulo");
				addAnswerToRepository("Buenos Aires");
				addAnswerToRepository(" Brasilia");
				addAnswerFromRepoToQusteion(3, 9, false);
				addAnswerFromRepoToQusteion(3, 10, false);
				addAnswerFromRepoToQusteion(3, 11, false);
				addAnswerFromRepoToQusteion(3, 12, true);
				
				addMultipleChoiceQuestion("What is the capital city of Australia?", eDifficultyLevel.Hard);
				addAnswerToRepository("Sydney");
				addAnswerToRepository("Melbourne");
				addAnswerToRepository("Canberra");
				addAnswerToRepository("Perth");
				addAnswerFromRepoToQusteion(4, 13, false);
				addAnswerFromRepoToQusteion(4, 14, false);
				addAnswerFromRepoToQusteion(4, 15, true);
				addAnswerFromRepoToQusteion(4, 2, false);
				addAnswerFromRepoToQusteion(4, 16, false);
				
				addOpenQuestion("What is the capital city of South Africa?", eDifficultyLevel.Hard, "Pretoria");
				addOpenQuestion("What is the capital city of Germany?", eDifficultyLevel.Easy, "Berlin");
				addOpenQuestion("What is the capital city of India?", eDifficultyLevel.Medium, "New Delhi");
				addOpenQuestion("What is the capital city of Egypt?", eDifficultyLevel.Easy, "Cairo");

				


				
				
				
				
				
				
				break;
			
			}
			
			default:
			{
				break;
			}
			
			
		}
			

	}

	public Question getQuestionByNumber(int numOfQustion) {// Get Qustion from repository by number
		if (numOfQustion > 0 && numOfQustion <= numOfAllQustiones)
			return allQuestions[numOfQustion - 1];
		return null;
	}

	// add new answer to repository // (2)
	public boolean addAnswerToRepository(String newAnswerDescription) {
		for (int i = 0; i < numOfAllAnswers; i++) {
			if (newAnswerDescription.equals(allAnswers[i].getAnswerDescription()))
				return false;// this answers is alredy exist
		}
		if (numOfAllAnswers == allAnswers.length)
			allAnswers = Arrays.copyOf(allAnswers, numOfAllAnswers * 2);

		Answer newAnswer = new Answer(newAnswerDescription);
		this.allAnswers[numOfAllAnswers++] = newAnswer;
		return true;
	}

	// Add answer from repository to qustion(3)
	public boolean addAnswerFromRepoToQusteion(int qustionNumber, int answerIndex, boolean correction) {
		if (qustionNumber < 0 || qustionNumber > numOfAllQustiones)// check if the qustion exist in repository
			return false;

		if (answerIndex < 0 || answerIndex > numOfAllAnswers)// check if the qustion exist in repository
			return false;

		return ((MultipleChoiceQuestion) this.allQuestions[qustionNumber - 1]).addAnswer(allAnswers[answerIndex - 1], correction);

	}

	// add new multiple choice qustion to repository(4)
	public boolean addMultipleChoiceQuestion(String question, eDifficultyLevel dificultyLevel) {
		for (int i = 0; i < numOfAllQustiones; i++) {
			if (question.equals(allQuestions[i].getQuestionDescription())) // this question alredy exist
				return false;
		}
		if (numOfAllQustiones == allQuestions.length)
			allQuestions = Arrays.copyOf(allQuestions, numOfAllQustiones * 2);

		allQuestions[numOfAllQustiones++] = new MultipleChoiceQuestion(question,dificultyLevel);
		return true;
	}
	//add new open qustion to repository(4)
	public boolean addOpenQuestion(String question, eDifficultyLevel dificultyLevel,String schoolAnswer) {
		for (int i = 0; i < numOfAllQustiones; i++) {
			if (question.equals(allQuestions[i].getQuestionDescription())) // this question alredy exist
				return false;
		}
		if (numOfAllQustiones == allQuestions.length)
			allQuestions = Arrays.copyOf(allQuestions, numOfAllQustiones * 2);
		
		this.addAnswerToRepository(schoolAnswer);
		Answer answer = this.allAnswers[numOfAllAnswers-1];
		allQuestions[numOfAllQustiones++] = new OpenQuestion(question,dificultyLevel,answer);
		
		return true;
	}

	// delet Answer of qustion(5)
	public boolean deleteAnswerFromQustion(int questionNumber, int answerNumberInQuestion) {
		return (((MultipleChoiceQuestion) this.allQuestions[questionNumber - 1]).deleteAnswerOfQuestion(answerNumberInQuestion));
		// deleteAnswerOfQuestion is a boolean function
	}

	public boolean deleteQuestion(int index) {// (6)
		if (numOfAllQustiones == 0)
			return false;

		if (index == numOfAllQustiones) {// if its the last answer in the array
			allQuestions[index - 1] = null;
			numOfAllQustiones--;
			return true;
		}
		if (index < numOfAllQustiones && index > 0) {
			allQuestions[index - 1] = allQuestions[numOfAllQustiones - 1];
			allQuestions[numOfAllQustiones - 1] = null;
			numOfAllQustiones--;
			return true;
		}
		return false;
	}
	
	

	public String showAnswersFromRepository(MultipleChoiceQuestion question) {
		StringBuffer sb = new StringBuffer();
		sb.append("Choose from the options below:\n");
		for (int i = 0; i < numOfAllAnswers; i++) {
			if(!question.answerExist(allAnswers[i])) {//showe only answer thet not exist in question

				int j = i + 1;
				sb.append(j + ") ").append(allAnswers[i].getAnswerDescription() + "\n");
			}
			else {
				
			}
			
		}
		return sb.toString();
	}
	

	@Override
	public String toString() {// Showe all Qustiens and answers(1)
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < numOfAllQustiones; i++) {
			int j = i + 1;
			sb.append("Question " + j + ": " + allQuestions[i].toString());
		}
		return sb.toString();

	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
