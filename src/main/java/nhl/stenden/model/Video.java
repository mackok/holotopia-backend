package nhl.stenden.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "video")
@Getter //Don't change to @Data, causes StackOverflowException
@Setter //Don't change to @Data, causes StackOverflowException
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private HololiveMember member;

    @Column(name = "youtube_id")
    @Size(max = 60)
    @NotNull
    private String youtubeCode;

    @Column(name = "player_source")
    @Size(max = 100)
    private String playerSource;

    @Column(name = "sts")
    @Size(min = 5, max = 5)
    private String sts;

    @Column(name = "m3u8")
    @Size(max = 200)
    private String m3u8;
}
