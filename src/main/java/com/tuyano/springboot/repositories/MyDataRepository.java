package com.tuyano.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuyano.springboot.MyData;

@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long> {

	public Optional<MyData> findById(long name);
	public Optional<MyData> findByIdGreaterThan(long name);
	
}
