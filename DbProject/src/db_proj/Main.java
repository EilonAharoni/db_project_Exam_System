package db_proj;

//
//import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
import java.util.Scanner;

public class Main {

	public static Scanner s = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		final String INVALID = "Invalid value\n";
		final int EXIT = 0;
		 UserDAO userDAO = new UserDAO();
		 User user = null;
		  int choice;
		  boolean userConect = false;
		 try {
			Connection conn = DatabaseManager.getConnection();
			do {
				 
		        System.out.println("Welcome! Please choose an option:");
		        System.out.println("1. Log in");
		        System.out.println("2. Register");
		        
		        choice = s.nextInt();
		        s.nextLine(); // Consume newline
		        
		        
			      

		        if (choice == 1) {
		            // Log in process
		            System.out.println("Enter your username:");
		            String username = s.nextLine();

		            System.out.println("Enter your password:");
		            String password = s.nextLine();
		            
		            
		            if (userDAO.validateUser(conn, username, password)) {
	                    System.out.println("Login successful!");
	                    user = new User(username, password);
	                    userConect = true;
	                } else {
	                    System.out.println("Invalid username or password. Please try again.");
	                }
		        }else if(choice == 2) {
		        	// Registration process
		            System.out.println("Enter a username:");
		            String username = s.nextLine();
		        	
		        	
		        	
		            if (userDAO.usernameExists(conn, username)) {
	                    System.out.println("Username already exists. Please choose a different one.");
	                } else {
	                    System.out.println("Enter a password:");
	                    String password = s.nextLine();
	                    User newUser = new User(username, password);
	                    userDAO.registerUser(conn, newUser);
	                    System.out.println("Registration successful!");
	                    userConect = true;
	                }
		        }
				
			} while(!userConect);
		
	        

	    conn.close();    
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
               
            
		 
		RepositoriesManneger repositoriesManager = new RepositoriesManneger();
		try {
			repositoriesManager.initRepositories();
		} catch (Exception e) {
			System.out.println("Could not find initialization file..");
		}
		int index = -1;
		while(true){
			
			System.out.println("Please choose the subject of the repsitory");
			System.out.println(repositoriesManager.toString());
			System.out.println("To Exit press--> 0");
			index = s.nextInt();
			
			if (index == EXIT) {
				System.out.println("Good bye");
				break;
			}
			
			if (index == repositoriesManager.getNumRepositories()) {
				System.out.println("Please enter subject for the new repsitory");
				String newRepoSubject =  s.next();
				repositoriesManager.addRepository(newRepoSubject);
			}
		
			QuestionesRepository questionRepository = repositoriesManager.getRepository(index-1);
	//		QuestionesRepository questionRepository = new QuestionesRepository();
			final int BACK = 0;
			final int OP1 = 1;
			final int OP2 = 2;
			final int OP3 = 3;
			final int OP4 = 4;
			final int OP5 = 5;
			final int OP6 = 6;
			final int OP7 = 7;
			final int OP8 = 8;
			
			
			do {
				showManu();
				choice = s.nextInt();
				switch (choice) {
				case OP1:
					System.out.println(questionRepository);
					break;
	
				case OP2:
					s.nextLine();
					System.out.println("Write the answer you want to enter");
					if (questionRepository.addAnswerToRepository(s.nextLine())) {
						System.out.println("Add successfully\n");
						break;
					}
					System.out.println("Answer is already exist in repository\n");
					break;
	
				case OP3:
					try {
						System.out.println("Which question would you like to add an answer to?");
						int questienIndex2 = s.nextInt();
						Question q = questionRepository.getQuestionByNumber(questienIndex2);
						if (q == null || q instanceof OpenQuestion) {
							System.out.println(INVALID);
							break;
						}
						System.out.println("Answers thet already exist in the qustion: \n"
								+ ((MultipleChoiceQuestion) questionRepository.getQuestionByNumber(questienIndex2)).showeQuestion());
						System.out.println(questionRepository.showAnswersFromRepository((MultipleChoiceQuestion) questionRepository.getQuestionByNumber(questienIndex2)));
						System.out.println("Please write the number of the new answer");
						int ansIndx2 = s.nextInt();
						if (ansIndx2 > 0 && ansIndx2 <= questionRepository.getNumOfAnswers()) {
							System.out.println("Please write the correctness of the answer (true/false)");
							boolean correctness = s.nextBoolean();
							if (questionRepository.addAnswerFromRepoToQusteion(questienIndex2, ansIndx2, correctness)) {
								System.out.println("Add successfully\n");
								break;
							}
						}
		
						System.out.println(INVALID);
						break;
		
						
					}
					catch (InputMismatchException e) {
					System.out.println(e.getMessage());	
				
					}
					break;
					
				case OP4:
					System.out.println("Please write the question you would like to add");
					s.nextLine();
					String description = s.nextLine();
					System.out.println("Please choose the difficulty level of the question");
					Question.eDifficultyLevel[] allLevels = Question.eDifficultyLevel.values();
					for (int i = 0; i < allLevels.length; i++) {
						System.out.println(allLevels[i].ordinal()+1 + ") "+ allLevels[i].name());
					}
					 int levelInput = s.nextInt(); // difficulty level input
					    
					    // Validate the input
					    if (levelInput < 1 || levelInput > allLevels.length) {
					        System.out.println("Invalid difficulty level\n");
					        break;
					    }
					
					    Question.eDifficultyLevel theLevel = allLevels[levelInput - 1];
					
					System.out.println("What type of question?\n"
							+ "Multiple choice question press -->1\n"
							+ "Open qustion press -->2\n");
					int ans = s.nextInt(); //question type
					switch(ans) {
					case 1:
						if (questionRepository.addMultipleChoiceQuestion(description,theLevel)) {
//							 saveRepositories(repositoriesManager,"repository.dat");
							System.out.println("Question successfully added\n");
						}
							
						else
							System.out.println(INVALID);
						break;
						
					case 2:
						System.out.println("Please write the answer to the question");
						s.nextLine();
						String answer = s.nextLine();
						if (questionRepository.addOpenQuestion(description,theLevel,answer))
							System.out.println("Question successfully added\n");
						else
							System.out.println(INVALID);
						break;
					
					}
					break;
					
				
	
				case OP5:
					System.out.println("What is the number of the question in which you would like to delete an answer?");
					
					int questienIndex6 = s.nextInt();
					Question q5 = questionRepository.getQuestionByNumber(questienIndex6);
					boolean outBounds = (questienIndex6<1 || questienIndex6>questionRepository.getNumOfAllQustiones());// if the question in the array out of bounds
					if(outBounds ||q5 instanceof OpenQuestion){//// Check q5 exist and not open question
						System.out.println(INVALID);
						break;
					}
					System.out.println("What is the number of the answer you would like to delete?");
					int ansIndx6 = s.nextInt();// Check Ans exist
					if (((MultipleChoiceQuestion) questionRepository.getQuestionByNumber(questienIndex6)).deleteAnswerOfQuestion(ansIndx6))
						System.out.println("Anwer successfully deleted\n");
					else
						System.out.println(INVALID);
	
					break;
	
				case OP6:
					System.out.println("What is the number of the question you would like to delete?");
					if (questionRepository.deleteQuestion(s.nextInt()))// Check q exist + define int
						System.out.println("Question successfully deleted\n");
					else
						System.out.println(INVALID);
	
					break;
	
				case OP7:// creating a exem manually
					System.out.println("How many questions would you like on the exam? ");
					int amountOfQuestions = s.nextInt();// get the amount of question the user want
					int amountOfExistingQustions = questionRepository.getNumOfAllQustiones();// get the amount of all
																								// qustions in the
					// repository
					if (amountOfQuestions < 1 || amountOfQuestions > amountOfExistingQustions) {
						System.out.println(INVALID);
						break;
					}
	
					System.out.println(questionRepository);// print repository to show the user
					ManuallyExam manuallyExam = new ManuallyExam(amountOfQuestions);
					try {
						manuallyExam.creatExam(questionRepository);
					} catch (MoreQuestionsThanAllowedException | NotEnoughAnswersInQuestionException e) {
						System.out.println(e.getMessage());
						break;
						
					}
				//	manuallyExam.deployToFile();
	
					break;
					
					/////// creating aouto exem 
				case OP8:	
					System.out.println("How many questions would you like on the exam? ");
					 amountOfQuestions = s.nextInt();// get the amount of question the user want
					 amountOfExistingQustions = questionRepository.getNumOfAllQustiones();// get the amount of all
					 
					 if (amountOfQuestions < 1 || amountOfQuestions > amountOfExistingQustions) {
							System.out.println(INVALID);
							break;
						}
					 
						 AutomaticExam AutomaticExam = new AutomaticExam(amountOfQuestions);
						 try {
							 AutomaticExam.creatExam(questionRepository);
							 
							 
						 } catch (Exception e) {
						 System.out.println(e.getMessage());
						
							
						}
		
					 
						 break;
						 case 10:
							 System.out.println("Get Answers");
							 try {
								 Connection conn2 = DatabaseManager.getConnection();
								 List<Answer> answersList = new ArrayList<>();
									boolean flag = false;
								 do {
									AnswerDAO answerdao_2 = new AnswerDAO(conn2);
									for(int i =0;i<21;i++) {
										Answer answer = answerdao_2.findById(i + 1);  // Retrieve answer by ID (assuming IDs start at 1)
										if (answer != null) {
											answersList.add(answer);
										}
									}
									 for (Answer answer : answersList) {
										 System.out.println(answer);
									 }
									flag = true;
									conn2.close();
								 }while(!flag);

							 } catch (SQLException e) {
								 e.printStackTrace();  // Handle the SQL exception
							 }
							 break;
					case 9:
						System.out.println("Save Answers");
						try {
							Connection conn2 = DatabaseManager.getConnection();
							boolean flag = false;
							do {
								AnswerDAO answerdao_2 = new AnswerDAO(conn2);
								for(int i =0;i<questionRepository.numOfAllAnswers;i++) {
									if(questionRepository.allAnswers[i] == null)
										break;
									answerdao_2.save(questionRepository.allAnswers[i]);
								}
								flag = true;
								conn2.close();
							}while(!flag);

						} catch (SQLException e) {
							e.printStackTrace();  // Handle the SQL exception
						}

					 break;
					case 12:
						System.out.println("Saving Questions i hope");
						try {
							Connection conn2 = DatabaseManager.getConnection();
							boolean flag = false;
							do {
								QuestionDAO answerdao_2 = new QuestionDAO(conn2);
								for(int i =0;i<questionRepository.numOfAllQustiones;i++) {
									if(questionRepository.allQuestions[i] == null)
										break;
									answerdao_2.save(questionRepository.allQuestions[i],questionRepository.getSubject());
								}
								flag = true;
								conn2.close();
							}while(!flag);

						} catch (SQLException e) {
							e.printStackTrace();  // Handle the SQL exception
						}
							break;
					case 13:
						System.out.println("Trying to fetch a Question");
						try {
							Connection conn2 = DatabaseManager.getConnection();
							boolean flag = false;
							do {
								QuestionDAO answerdao_2 = new QuestionDAO(conn2);
								Question qTry = answerdao_2.findById(9);
								System.out.println(qTry);
								conn2.close();
								flag = true;
							}while(!flag);

						} catch (SQLException e) {
							e.printStackTrace();  // Handle the SQL exception
						}
						break;
				case BACK:
					break;
	
				default:
					System.out.println("Invalid input");
				}
	
			} while (choice != EXIT);
	
		
		}
	}

	private static void showManu() {
		System.out.println("To show all questiones and answers) press--> 1");
		System.out.println("To add new answer to repository) press--> 2");
		System.out.println("To add an existing answer to question) press--> 3");
		System.out.println("To add a new question) press--> 4");
		System.out.println("To delete an answer to a question) press--> 5");
		System.out.println("To delete a question) press--> 6");
		System.out.println("To creat an exam manually) press--> 7");
		System.out.println("To creat an exam automatically) press--> 8");
		System.out.println("To Save all answers to data base Press --> 9");
		System.out.println("To go back press 0");

	}

	

}
