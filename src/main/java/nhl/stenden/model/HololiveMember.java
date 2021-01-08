package nhl.stenden.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "hololive_member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HololiveMember {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name= "name")
    @NotNull
    private String name;

    @Column(name = "channel_id")
    @NotNull
    private String channel;

    @Column(name = "uploads_id")
    @NotNull
    private String uploads;

    @OneToMany(mappedBy = "member")
    private Set<Video> videos;
}
