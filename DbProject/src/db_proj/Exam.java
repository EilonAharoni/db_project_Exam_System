package db_proj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class Exam implements Examable {
	protected Question[] examQuestions;
	protected int numOfQustionInExam;
	protected Answer noAnswer;
	protected int amountOfWantQuestions;
	public static final int MAX_OF_QUESTIONS_ALLOW=3;
	public static final int  MINIMUM_ANSSWERS_IN_QUESTION = 4;
	
	public Exam(int amountOfWantQuestions) {
		this.amountOfWantQuestions = amountOfWantQuestions;
		this.examQuestions = new Question[amountOfWantQuestions];
		this.noAnswer = new Answer("No answer is correct!");
	}
	
	
	
	


	public void deployToFile() throws IOException {
	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
		String currentTime = formatter.format(Calendar.getInstance().getTime());
		Date date = new Date();
		System.out.println(formatter.format(date));
		String fileName = "exam" + currentTime + ".txt";
		
		  File myObj = new File(fileName);
	      if (myObj.createNewFile()) {
	        System.out.println("File created: " + myObj.getName()+ "\n");
	      } else {
	        System.out.println("File already exists.");
	      }
	      FileWriter myWriter = new FileWriter(fileName);


		for (int i = 0; i < this.numOfQustionInExam; i++) {
			Question question = this.examQuestions[i];
			String questionStr = question.showeQuestion();
			myWriter.write(questionStr + "\n");			
			
		}
		
		myWriter.close();

		
		 fileName = "solution" + currentTime + ".txt";
		
		   myObj = new File(fileName);
	      if (myObj.createNewFile()) {
	        System.out.println("File created: " + myObj.getName()+ "\n");
	      } else {
	        System.out.println("File already exists.");
	      }
	      myWriter = new FileWriter(fileName);

		for (int i = 0; i < this.numOfQustionInExam; i++) {
			Question question = this.examQuestions[i];
			String questionStr = question.toString();
			myWriter.write(questionStr + "\n");			
			
		}
		myWriter.close();
	}
	
	
	
	
	public boolean addQuestionInExam(Question question) {
		for (int i = 0; i < numOfQustionInExam; i++) {
			if (question.getQuestionDescription().equals(examQuestions[i].getQuestionDescription())) // this question alredy exist
				return false;
		}
		if(question instanceof MultipleChoiceQuestion) {
		this.examQuestions[numOfQustionInExam++] = new MultipleChoiceQuestion((MultipleChoiceQuestion) question);
					}
		else {
			this.examQuestions[numOfQustionInExam++] = new OpenQuestion((OpenQuestion) question);
		}
		return true;

	}
	
	
	
	

}
