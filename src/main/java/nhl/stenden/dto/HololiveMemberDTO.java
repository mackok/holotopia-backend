package nhl.stenden.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HololiveMemberDTO {

    @JsonProperty("name")
    @Size(min = 3, max = 60)
    private String name;

    @JsonProperty("channel")
    @Size(min = 1, max = 60)
    private String channel;

    @JsonProperty("uploads")
    @Size(min = 1, max = 60)
    private String uploads;
}
