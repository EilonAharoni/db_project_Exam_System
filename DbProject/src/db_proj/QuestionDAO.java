package db_proj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class QuestionDAO {
    private Connection connection;

    public QuestionDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to save an Answer to the database
    public void save(Question question, String subject) throws SQLException {
        String sql;

        if (question instanceof OpenQuestion) {
            // Insert into OpenQuestions
            sql = "INSERT INTO OpenQuestions (question_description, difficulty_level, subject, right_answer) VALUES (?, ?, ?, ?) RETURNING question_id";
        } else if (question instanceof MultipleChoiceQuestion) {
            // Insert into MultipleChoice
            sql = "INSERT INTO MultipleChoice (question_description, difficulty_level, subject, number_of_answers) VALUES (?, ?, ?, ?) RETURNING question_id";
        } else {
            throw new IllegalArgumentException("Invalid question type: " + question.getClass().getName());
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, question.getQuestionDescription());
            pstmt.setString(2, question.getDificultyLevel().name());
            pstmt.setString(3, subject);

            // Set the specific attribute based on question type
            if (question instanceof OpenQuestion) {
                OpenQuestion openQuestion = (OpenQuestion) question;
                int rightAnswerId = openQuestion.getSchoolAnswerID() + 1; // Assuming getRightAnswer() returns an Answer object
                pstmt.setInt(4, rightAnswerId);
            } else if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion multipleChoice = (MultipleChoiceQuestion) question;
                pstmt.setInt(4, multipleChoice.getNumOfAnswers());
            }

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                question.setId(generatedId);
            }
        }
                if(question instanceof MultipleChoiceQuestion){
                    sql = "INSERT INTO Multiple_Choice_Answers (question_id, answer_id, is_correct) VALUES (?, ?, ?)";

                    try (PreparedStatement pstmt2 = connection.prepareStatement(sql)) {
                        MultipleChoiceQuestion multipleChoice = (MultipleChoiceQuestion) question;
                        Answer[] answers = multipleChoice.getAnswers();
                        for (int i = 0;i<4;i++) {
                            pstmt2.setInt(1, multipleChoice.getId());  // The generated question_id from the MultipleChoice table
                            pstmt2.setInt(2, answers[i].getId() + 1);          // The answer_id from the Answer object
                            pstmt2.setBoolean(3, multipleChoice.answersCorrection[i]);  // Assuming isCorrect() returns a boolean indicating if it's the correct answer
                            pstmt2.executeUpdate();
                        }
                    }
                }

        }


    // Method to fetch an Answer by ID
    public Question findById(int id) throws SQLException {
        Question question = null;

        // Try to find the question in OpenQuestions
        question = findOpenQuestionById(id);
        if (question != null) {
            return question;
        }
        return question;
        // If not found, try to find the question in MultipleChoice
//        question = findMultipleChoiceQuestionById(id);
//        return question;
    }

    private OpenQuestion findOpenQuestionById(int id) throws SQLException {
        String sql = "SELECT * FROM OpenQuestions WHERE question_id = ?";
        OpenQuestion openQuestion = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String Desc = rs.getString("question_description");
                Question.eDifficultyLevel Diff = Question.eDifficultyLevel.valueOf(rs.getString("difficulty_level"));
                int rightAnswerId = rs.getInt("right_answer");
                AnswerDAO answerdao_2 = new AnswerDAO(connection);
                Answer rightAnswer = answerdao_2.findById(rightAnswerId);
                openQuestion = new OpenQuestion(Desc,Diff,rightAnswer);
                openQuestion.setId(rs.getInt("question_id"));
            }
        }

        return openQuestion;
    }

//    private MultipleChoiceQuestion findMultipleChoiceQuestionById(int id) throws SQLException {
//        String sql = "SELECT * FROM MultipleChoice WHERE question_id = ?";
//        MultipleChoiceQuestion multipleChoice = null;
//
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setInt(1, id);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                multipleChoice = new MultipleChoiceQuestion();
//                multipleChoice.setId(rs.getInt("question_id"));
//                multipleChoice.setQuestionDescription(rs.getString("question_description"));
//                multipleChoice.setDifficultyLevel(DifficultyLevel.valueOf(rs.getString("difficulty_level")));
//                multipleChoice.setSubject(rs.getString("subject"));
//                multipleChoice.setQuestionType("multiple");
//                multipleChoice.setNumberOfAnswers(rs.getInt("number_of_answers"));
//
//                // Retrieve associated answers
//                String answersSql = "SELECT * FROM Multiple_Choice_Answers WHERE question_id = ?";
//                try (PreparedStatement answersPstmt = connection.prepareStatement(answersSql)) {
//                    answersPstmt.setInt(1, id);
//                    ResultSet answersRs = answersPstmt.executeQuery();
//
//                    List<Answer> answers = new ArrayList<>();
//                    boolean[] isCorrect = new boolean[4]; // Assuming you know there are 4 answers
//                    int index = 0;
//                    while (answersRs.next()) {
//                        int answerId = answersRs.getInt("answer_id");
//                        Answer answer = findAnswerById(answerId);
//                        answers.add(answer);
//                        isCorrect[index++] = answersRs.getBoolean("is_correct");
//                    }
//                    multipleChoice.setAnswers(answers);
//                    multipleChoice.setAnswersCorrection(isCorrect);
//                }
//            }
//        }
//
//        return multipleChoice;
//    }



}
