package com.example.demo.repositories;

import com.example.demo.entities.ContactInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactInformationRepository extends JpaRepository<ContactInformation, Long> {

    @Query("SELECT c FROM ContactInformation c WHERE lower(c.mobileNumber) LIKE %:searchTerm% or lower(c.address) LIKE %:searchTerm%  ")
    Page<ContactInformation> finaAllContactInformation(String searchTerm, Pageable paginate);
}
