package com.comeon.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.comeon.group.entity.Love;

public interface LoveRepository extends JpaRepository<Love, Long> {

	Optional<Love> findByPlayerIdAndGameId(Long playerId, Long gameId);

	List<Love> findByPlayerId(Long playerId);

	@Query("""
			    SELECT l.game.id, COUNT(l.id)
			    FROM Love l
			    GROUP BY l.game.id
			    ORDER BY COUNT(l.id) DESC
			""")
	List<Object[]> findMostLovedGames(PageRequest pageRequest);
}
