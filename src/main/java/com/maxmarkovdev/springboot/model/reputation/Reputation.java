package com.maxmarkovdev.springboot.model.reputation;

import com.maxmarkovdev.springboot.model.Answer;
import com.maxmarkovdev.springboot.model.Question;
import com.maxmarkovdev.springboot.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CombinedNotNullQuestionOrAnswer
@Table(name = "reputation")
public class Reputation implements Serializable {
    private static final long serialVersionUID = 7177182244933788025L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime persistDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @Column(name = "count")
    private Integer count;

    @Enumerated
    @NotNull
    @Column(name = "type")
    private ReputationType type;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Question.class, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Answer.class, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reputation reputation = (Reputation) o;
        return Objects.equals(id, reputation.id) &&
                Objects.equals(persistDate, reputation.persistDate) &&
                Objects.equals(count, reputation.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, persistDate, count);
    }
}
