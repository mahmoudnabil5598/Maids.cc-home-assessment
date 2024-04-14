package com.example.demo.repositories;

import com.example.demo.entities.Author;
import com.example.demo.entities.Patron;
import com.example.demo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String name);

    @Query("SELECT u FROM Patron u WHERE lower(u.fullName) LIKE %:searchTerm%")
    Page<Patron> findAllPatrons(String searchTerm, Pageable paginate);

    @Query("SELECT u FROM Author u WHERE lower(u.fullName) LIKE %:searchTerm%")
    Page<Author> findAllAuthors(String searchTerm, Pageable paginate);
}
