package com.toriumsystems.aidrink.domain.model;

import java.util.List;

import com.toriumsystems.aidrink.identity.audit.Auditable;
import com.toriumsystems.aidrink.identity.model.UserIdentity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profile extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserIdentity identity;

    @OneToMany(mappedBy = "profile")
    private List<DrinkVote> votes;

    @Builder.Default
    private Integer initialPageIndex = 0;

    @Builder.Default
    private Integer currentPageIndex = 0;

    @Builder.Default
    private Integer currentPageOffset = 0;
}
