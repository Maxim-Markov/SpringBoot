package com.maxmarkovdev.springboot.model.vote;

import com.maxmarkovdev.springboot.model.Question;
import com.maxmarkovdev.springboot.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes_on_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteQuestion implements Serializable {

    private static final long serialVersionUID = 6479035497338780810L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime localDateTime = LocalDateTime.now();

    @Column(name = "vote")
    @Enumerated(EnumType.STRING)
    private VoteType vote;

    public VoteQuestion(User user, Question question, VoteType vote) {
        this.user = user;
        this.question = question;
        this.vote = vote;
    }
}
