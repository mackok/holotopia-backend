package nhl.stenden.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {

    @JsonProperty("youtube_id")
    @Size(min = 1, max = 60)
    private String youtubeCode;

    @JsonProperty("thumbnail")
    @Size(min = 1, max = 200)
    private String thumbnail;
}
