package com.cmanager.app.application.repository;

import com.cmanager.app.application.domain.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, String> {

    Optional<Show> findByIdIntegration(Integer idIntegration);

    Page<Show> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Show> findByNameContainingIgnoreCase(String name);
}
