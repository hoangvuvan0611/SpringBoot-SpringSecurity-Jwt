package com.example.demosecurity.repositiory;

import com.example.demosecurity.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE from `users` u where u.email=:email", nativeQuery = true)
    void deleteByEmail(@Param("email") String email);
}
