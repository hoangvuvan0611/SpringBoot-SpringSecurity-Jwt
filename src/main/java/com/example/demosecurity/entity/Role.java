package com.example.demosecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @SequenceGenerator(name = "roles_sequence", sequenceName = "roles_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "roles_sequence")
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @Fetch(value = FetchMode.SELECT)
    @JsonIgnore
    private Set<User> userSet = new HashSet<>();

    public Role(String name){
        this.name = name;
    }
}
