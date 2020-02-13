package DataModels;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<String> possible = new ArrayList<>();
        possible.add("19");
        possible.add("20");
        possible.add("21");
        Questions ageQuestion = new Questions("How old am I", possible);

        ArrayList<String> possible2 = new ArrayList<>();
        possible2.add("sally");
        possible2.add("bob");
        possible2.add("miles");
        Questions nameQuestion = new Questions("Who am I", possible2);

        ArrayList<Questions> q1 = new ArrayList<>();
        q1.add(ageQuestion);
        q1.add(nameQuestion);
        Quiz quiz1 = new Quiz("Quiz on me",q1);

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            objectMapper.writeValue(new File("/Users/jignajain/Downloads/TestApplication 4/app/build/generated/res/resValues/debug/test.json"),quiz1);

            //compact format
            String jsonStr= objectMapper.writeValueAsString(quiz1);
            System.out.println(jsonStr);
            System.out.println("=======================");
            //pretty format
            String jsonStr2 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(quiz1);
            System.out.println(jsonStr2);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
