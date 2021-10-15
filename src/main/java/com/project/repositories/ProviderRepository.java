package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.entities.Provider;
@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long> {
	
	
	@Query("FROM Provider p WHERE p.name like %?1%")
	List<Provider> ListProviderName(String nom);
	
	@Query("select p from Provider p where p.name like %:name%")
	List<Provider> findProviderByNameLike(@Param("name") String name);

}