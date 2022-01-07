package com.maxmarkovdev.springboot.model.vote;

import com.maxmarkovdev.springboot.model.Answer;
import com.maxmarkovdev.springboot.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "votes_on_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Answer answer;

    @CreationTimestamp
    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime persistDateTime;

    @Column
    @Enumerated(EnumType.STRING)
    private VoteType vote;

    public VoteAnswer(User user, Answer answer, VoteType vote) {
        this.user = user;
        this.answer = answer;
        this.vote = vote;
    }
}
