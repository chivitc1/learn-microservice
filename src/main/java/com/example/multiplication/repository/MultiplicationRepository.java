package com.example.multiplication.repository;

import com.example.multiplication.domain.Multiplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long>
{
	// Query using native SQL return first result
//	@Query(value = "SELECT * FROM multiplication t WHERE t.factora = :factorA " +
//			"AND t.factorb = :factorB  limit 1", nativeQuery = true)
//	Optional<Multiplication> findFirstByFactorAAndFactorB(@Param("factorA") int _factorA,
//														  @Param("factorB") int _factorB);

	/**
	 * Query using dynamic jpa method return first result
	 * @param _factorA
	 * @param _factorB
	 * @return
	 */
	Optional<Multiplication> findFirstByFactorAAndFactorB(int _factorA, int _factorB);
}
