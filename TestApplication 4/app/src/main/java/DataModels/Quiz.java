package DataModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Quiz {
    @JsonProperty("quizName")
    private String QuizName = "";
    @JsonProperty("listOfQuestions")
    ArrayList<Questions> listOfQuestions ;

    public Quiz(String QuizName, ArrayList<Questions> listOfQuestions){
        this.QuizName = QuizName;
        this.listOfQuestions = listOfQuestions;
    }


}
