package com.example.jumlacycle.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive(message = "enter valid rate")
    @Pattern(regexp = "^[1-5]$",message = "Enter number 1 to 5")
    @Column(columnDefinition = "int not null check(rate<=5)")
    private int rate;

    @NotEmpty(message = "description can not be null")
    @Column(columnDefinition = "varchar(100) not null")
    @Size(min = 5, max = 100 ,message = "max length 100 for description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

}