package com.comeon.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comeon.group.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
