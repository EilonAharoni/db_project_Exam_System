package db_proj;

//
//import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		final String INVALID = "Invalid input\n";
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
		        try {
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
		        else {
		        	System.out.println("Invalid option. Please choose 1 or 2.");
		        	
		        }
		      }	catch (InputMismatchException e) {
		    	   System.out.println("Invalid input. Please enter a number.");
	                s.nextLine(); // Clear the invalid input from the scanner
			  }   
			} while(!userConect);
		
	        

	    conn.close();    
		} catch (SQLException e1) {
			System.out.println(INVALID);
			e1.printStackTrace();
		}
               
            
		 
		RepositoriesManneger repositoriesManager = new RepositoriesManneger();
		
		try {
			Connection conn = DatabaseManager.getConnection();
			repositoriesManager.initRepositoriesFromDB(conn);
			conn.close();
		} catch (SQLException e) {
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
				try {
					Connection conn = DatabaseManager.getConnection();
					repositoriesManager.addRepository(conn,newRepoSubject);
					conn.close();
				} catch (Exception e) {
					System.out.println("Could not Enter new subject..");
				}

			}
		
			QuestionesRepository questionRepository = repositoriesManager.getRepository(index-1);
	
			final int BACK = 0;
			final int OP1 = 1;
			final int OP2 = 2;
			final int OP3 = 3;
			final int OP4 = 4;
			final int OP5 = 5;
			final int OP6 = 6;
			final int OP7 = 7;
			final int OP8 = 8;
			final int OP9 = 9;
			final int OP10 = 10;
			final int OP11 = 11;
			
			
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
						String answerDescription = s.nextLine();
						try {
							Connection conn = DatabaseManager.getConnection();
							AnswerDAO answerDAO = new AnswerDAO(conn);
							Answer newAnswer = new Answer(answerDescription);
							answerDAO.save(newAnswer,questionRepository.getSubject());
							if (questionRepository.addAnswerToRepository(answerDescription,newAnswer)) {
								System.out.println("Answer added successfully to both database and repository\n");
							} else {
								System.out.println("Answer added to database but already exists in repository\n");
							}
							conn.close();
						} catch (SQLException e) {
							System.out.println("Error occurred while saving answer to database: " + e.getMessage());
						}
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
					        
					        System.out.println("Answers that already exist in the question: \n"
					                + ((MultipleChoiceQuestion) q).showeQuestion());
					        System.out.println(questionRepository.showAnswersFromRepository((MultipleChoiceQuestion) q));
					        
					        System.out.println("Please write the number of the new answer");
					        int ansIndx2 = s.nextInt();
					        
					        if (ansIndx2 > 0 && ansIndx2 <= questionRepository.getNumOfAnswers()) {
					            System.out.println("Please write the correctness of the answer (true/false)");
					            boolean correctness = s.nextBoolean();
					            
					            if (questionRepository.addAnswerFromRepoToQusteion(questienIndex2, ansIndx2, correctness)) {
					                
					                String sql = "INSERT INTO Multiple_Choice_Answers (question_id, answer_id, is_correct) VALUES (?, ?, ?)";
					                Connection conn = DatabaseManager.getConnection();
					                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					                    Answer ans = questionRepository.allAnswers[ansIndx2 - 1];
					                    int answerId = ans.getId();
					                    int questionId = q.getId();
					                    pstmt.setInt(1, questionId);  // The generated question_id from the MultipleChoice table
					                    pstmt.setInt(2, answerId);    // The answer_id from the Answer object
					                    pstmt.setBoolean(3, correctness);  // Correctness of the answer
					                    pstmt.executeUpdate();  // Insert the new answer into the database
					                    
					                    // The trigger will automatically update the number_of_answers
					                    System.out.println("Answer added successfully. The number_of_answers is automatically updated by the trigger.\n");
					                } catch (SQLException e) {
					                    System.out.println("Error occurred while inserting the answer into the database: " + e.getMessage());
					                }
					                break;
					            }
					        }

					        System.out.println(INVALID);
					        break;

					    } catch (InputMismatchException | SQLException e) {
					        System.out.println("Error: " + e.getMessage());
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
						try {
							Connection conn = DatabaseManager.getConnection();
							QuestionDAO questionDAO = new QuestionDAO(conn);
							AnswerDAO answerDAO = new AnswerDAO(conn);

							switch(ans) {
								case 1:
									if (questionRepository.addMultipleChoiceQuestion(description,theLevel)) {
//							saveRepositories(repositoriesManager,"repository.dat");

										// Get the last added question
										Question question = questionRepository.allQuestions[questionRepository.numOfAllQustiones-1];




										// Save question to the database
										questionDAO.save(question, questionRepository.getSubject());
										System.out.println("Question successfully added to repository and database\n");
									}

									else
										System.out.println(INVALID);
									break;

								case 2:
									System.out.println("Please write the answer to the question");
									s.nextLine();
									String answer = s.nextLine();
									Answer newAns = new Answer(answer);
									answerDAO.save(newAns,questionRepository.getSubject());

									if (questionRepository.addOpenQuestion(description,theLevel,answer,newAns)) {
										// Get the last added question
										Question question = questionRepository.allQuestions[questionRepository.numOfAllQustiones-1];

										// Save question to the database
										questionDAO.save(question, questionRepository.getSubject());
										System.out.println("Question successfully added to repository and database\n");
									}
									else
										System.out.println(INVALID);
									break;

							}
							conn.close();
						} catch (SQLException e) {
							System.out.println("Error occurred while saving question to database: " + e.getMessage());
						}
						break;



					case OP5:
					    System.out.println("What is the number of the question in which you would like to delete an answer?");
					    int questienIndex6 = s.nextInt();
					    Question q5 = questionRepository.getQuestionByNumber(questienIndex6);
					    boolean outBounds = (questienIndex6 < 1 || questienIndex6 > questionRepository.getNumOfAllQustiones());

					    // Check if question is valid and not an Open Question
					    if (outBounds || q5 instanceof OpenQuestion) {
					        System.out.println(INVALID);
					        break;
					    }

					    System.out.println("What is the number of the answer you would like to delete?");
					    int ansIndx6 = s.nextInt();

					    // Validate and delete the answer from the repository
					    if (questionRepository.getQuestionByNumber(questienIndex6) instanceof MultipleChoiceQuestion) {
					        MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) q5;
					        int answerId = mcq.getAnswers()[ansIndx6 - 1].getId(); // Retrieve the answer ID
					        if (mcq.deleteAnswerOfQuestion(ansIndx6)) {
					            try {
					                String sql = "DELETE FROM multiple_choice_answers WHERE question_id = ? AND answer_id = ?";
					                Connection conn = DatabaseManager.getConnection();
					                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					                    pstmt.setInt(1, q5.getId());
					                    pstmt.setInt(2, answerId);
					                    pstmt.executeUpdate(); // Execute the delete operation
					                }
					                System.out.println("Answer successfully deleted from repository and database. The number_of_answers is automatically updated by the trigger.\n");
					            } catch (SQLException e) {
					                System.out.println("Error occurred while deleting answer from database: " + e.getMessage());
					            }
					        } else {
					            System.out.println(INVALID);
					        }
					    } else {
					        System.out.println(INVALID);
					    }
					    break;

				case OP6:
					System.out.println("What is the number of the question you would like to delete?");
					int numOfQuestion = s.nextInt();
					int questionID = questionRepository.allQuestions[numOfQuestion-1].getId();
					if (questionRepository.deleteQuestion(numOfQuestion))// Check q exist + define int
					{
						try {
								String sql = "DELETE FROM questions WHERE question_id = ?";
								Connection conn = DatabaseManager.getConnection();
								try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
									pstmt.setInt(1, questionID);
									pstmt.executeUpdate(); // Execute the delete operation
								}
								System.out.println("Question successfully deleted\n");

						}
						catch (SQLException e) {
							System.out.println(e.getMessage());
						}
					}
					else
						System.out.println(INVALID);
	
					break;
	
				case OP7:// creating a exem manually
					System.out.println("How many questions would you like on the exam?");
					int amountOfQuestions = 0;
					boolean validInput = false;

					while (!validInput) {
					    try {
					        amountOfQuestions = s.nextInt(); // get the amount of questions the user wants
					        int amountOfExistingQuestions = questionRepository.getNumOfAllQustiones(); // get the amount of all questions in the repository

					        if (amountOfQuestions < 1 || amountOfQuestions > amountOfExistingQuestions) {
					            System.out.println(INVALID);
					        } else {
					            validInput = true; // Valid input, exit the loop
					        }
					    } catch (InputMismatchException e) {
					        System.out.println("Invalid input. Please enter a valid number.");
					        s.nextLine(); // Clear the invalid input from the scanner
					    }
					}
					System.out.println(questionRepository);// print repository to show the user
					ManuallyExam manuallyExam = new ManuallyExam(amountOfQuestions);
					int generatedTestId = 0;
					try {
						Connection conn = DatabaseManager.getConnection();
						String sql = "INSERT INTO tests (user_id) VALUES (?) RETURNING test_id";
						PreparedStatement pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, user.getUsername());
							ResultSet rs = pstmt.executeQuery();
							if (rs.next()) {
								 generatedTestId = rs.getInt(1);
							}
							if(generatedTestId<=0) {
								System.out.println("Bad TEST ID");
								return;
							}
						manuallyExam.creatExam(questionRepository,generatedTestId,conn);
							conn.close();
					} catch (MoreQuestionsThanAllowedException | NotEnoughAnswersInQuestionException e) {
						System.out.println(e.getMessage());
						break;
						
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					//	manuallyExam.deployToFile();
	
					break;
					
					/////// creating aouto exem 
				case OP8:	
					 amountOfQuestions = 0;
					int amountOfExistingQuestions = questionRepository.getNumOfAllQustiones(); // get the amount of all questions in the repository
					 validInput = false;

					while (!validInput) {
					    try {
					        System.out.println("How many questions would you like on the exam?");
					        amountOfQuestions = s.nextInt(); // get the amount of questions the user wants
					        
					        if (amountOfQuestions < 1 || amountOfQuestions > amountOfExistingQuestions) {
					            System.out.println(INVALID);
					        } else {
					            validInput = true; // Valid input, exit the loop
					        }
					    } catch (InputMismatchException e) {
					        System.out.println("Invalid input. Please enter a valid number.");
					        s.nextLine(); // Clear the invalid input from the scanner
					    }
					}

					if (!validInput) {
					    break;
					}

						 AutomaticExam AutomaticExam = new AutomaticExam(amountOfQuestions);
						int generatedTestId2 = 0;
						 try {

							 Connection conn = DatabaseManager.getConnection();
							 String sql = "INSERT INTO tests (user_id) VALUES (?) RETURNING test_id";
							 PreparedStatement pstmt = conn.prepareStatement(sql);
							 pstmt.setString(1, user.getUsername());
							 ResultSet rs = pstmt.executeQuery();
							 if (rs.next()) {
								 generatedTestId2 = rs.getInt(1);
							 }
							 if(generatedTestId2<=0) {
								 System.out.println("Bad TEST ID");
								 return;
							 }
							 AutomaticExam.creatExam(questionRepository,generatedTestId2,conn);
							 conn.close();
							 
						 } catch (Exception e) {
						 System.out.println(e.getMessage());

							
						}

						 break;
						 case OP9:
							 System.out.println("Save Answers");
								try {
									Connection conn2 = DatabaseManager.getConnection();
									boolean flag = false;
									do {
										AnswerDAO answerdao_2 = new AnswerDAO(conn2);
										for(int i =0;i<questionRepository.numOfAllAnswers;i++) {
											if(questionRepository.allAnswers[i] == null)
												break;
											answerdao_2.save(questionRepository.allAnswers[i],questionRepository.getSubject());
										}
										flag = true;
										conn2.close();
									}while(!flag);

								} catch (SQLException e) {
									e.printStackTrace();  // Handle the SQL exception
								}

							 break;
							
					case OP10:
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
					
					case OP11:
						System.out.println("Trying to fetch a Question");
						try {
							Connection conn2 = DatabaseManager.getConnection();
							boolean flag = false;
							do {
								QuestionDAO answerdao_2 = new QuestionDAO(conn2);
								Question qTry = answerdao_2.findById(2);
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
		System.out.println("To show all questiones and answers press--> 1");
		System.out.println("To add new answer to repository press--> 2");
		System.out.println("To add an existing answer to question press--> 3");
		System.out.println("To add a new question press--> 4");
		System.out.println("To delete an answer to a question press--> 5");
		System.out.println("To delete a question press--> 6");
		System.out.println("To creat an exam manually press--> 7");
		System.out.println("To creat an exam automatically press--> 8");
		System.out.println("To save all answers to data base Press --> 9");
		System.out.println("To save all questions to data base Press --> 10");
		System.out.println("To get a question to data base Press --> 11");
		System.out.println("To go back press 0");

	}

	

}
