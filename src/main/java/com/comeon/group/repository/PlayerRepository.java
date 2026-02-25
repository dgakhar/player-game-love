package com.comeon.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comeon.group.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
