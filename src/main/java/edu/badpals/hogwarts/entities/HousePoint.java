package edu.badpals.hogwarts.entities;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "HousePoint.maxReceiver", query = "SELECT hs.receiver FROM HousePoint hs group by hs.receiver order by SUM(hs.points) desc limit 1")
@NamedQuery(name = "HousePoint.maxgiver", query = "SELECT hs.giver FROM HousePoint hs group by hs.giver order by SUM(hs.points) desc limit 1")
@Table(name = "house_points")
public class HousePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver")
    private Person giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver")
    private Person receiver;

    @Column(name = "points")
    private Integer points;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getGiver() {
        return giver;
    }

    public void setGiver(Person giver) {
        this.giver = giver;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

}