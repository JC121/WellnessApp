package DataModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseModel<T> {

    @JsonProperty("successful")
    boolean successful;

    @JsonProperty("responseCode")
    int responseCode;

    @JsonProperty("response")
    String response;

    @JsonProperty("data")
    T data;


    public ResponseModel(boolean successful, int responseCode, String response) {
        this.successful = successful;
        this.responseCode = responseCode;
        this.response = response;
        this.data = null;
    }

    public String toString() {
        return "Successful: " + successful + "\n" +
                "Response Code: " + responseCode + "\n" +
                "Response: " + response + "\n" +
                "Data: " + data;
    }
}
