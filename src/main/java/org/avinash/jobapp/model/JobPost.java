package org.avinash.jobapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String postProfile;

    @Column(length = 2000)
    private String postDesc;

    private Integer reqExperience;

    @ElementCollection
    @CollectionTable(name = "job_tech_stack", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "technology")
    private List<String> postTechStack = new ArrayList<>();

    private String location;

    private String company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private User employer;

    private LocalDateTime postedAt;

    @PrePersist
    void onCreate() {
        if (postedAt == null) {
            postedAt = LocalDateTime.now();
        }
    }
}
