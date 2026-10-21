package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "turns")
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "turn_id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "turn_number", nullable = false)
    private int turnNumber;

    @Column(name = "throw1")
    private String throw1;

    @Column(name = "throw2")
    private String throw2;

    @Column(name = "throw3")
    private String throw3;

    @Column(name = "finisher", nullable = false)
    private boolean finisher;

    @Column(name = "bust", nullable = false)
    private boolean bust;

    public Turn() {
    }

    public Turn(Game game, int turnNumber) {
        this.game = game;
        this.turnNumber = turnNumber;
    }

    public void setThrows(String[] scores) {
        this.throw1 = scores[0];
        this.throw2 = scores[1];
        this.throw3 = scores[2];
    }

    public void setFinisher(boolean finisher) {
        this.finisher = finisher;
    }

    public void setBust(boolean bust) {
        this.bust = bust;
    }
}
