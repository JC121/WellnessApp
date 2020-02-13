package DataModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Questions {

    @JsonProperty("question")
    private String Question = "";
    @JsonProperty("answers")
    private ArrayList<String> Answers = new ArrayList<>();
    private String correctAnswer;
    // 1 st ans is correct ans
    public Questions(String Quest, ArrayList<String> Answers){
        Question = Quest;
        if(Answers.size()<2){
            throw new IllegalArgumentException("Please enter in more options");
        }
        this.Answers = Answers;
        correctAnswer = Answers.get(0);
    }


    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public ArrayList<String> getAnswers() {
        return Answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        Answers = answers;
    }

}
