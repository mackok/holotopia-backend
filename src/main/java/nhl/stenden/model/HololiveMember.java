package nhl.stenden.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String name;

    @Column(name = "channel")
    private String channel;

    @Column(name = "uploads")
    private String uploads;
}
