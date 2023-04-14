package com.toriumsystems.aidrink.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.toriumsystems.aidrink.identity.audit.Auditable;
import com.toriumsystems.aidrink.identity.model.UserIdentity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// @Entity
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// @Getter
public class WorkspaceMember {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotNull
    // @ManyToOne
    private UserIdentity identity;

    // @NotNull
    // @ManyToOne
    // private Workspace workspace;

    // @NotNull
    // private WorkspaceRole role;

    // @OneToMany(mappedBy = "owner")
    // @Builder.Default
    // private List<Task> createdTasks = new ArrayList<>();

    // @OneToMany(mappedBy = "assignee")
    // @Builder.Default
    // private List<Task> assignedTasks = new ArrayList<>();

}
