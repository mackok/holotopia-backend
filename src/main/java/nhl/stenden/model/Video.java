package nhl.stenden.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "video")
@Getter
@Setter
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

    @Column(name = "m3u8")
    @Size(max = 200)
    private String m3u8;
}
