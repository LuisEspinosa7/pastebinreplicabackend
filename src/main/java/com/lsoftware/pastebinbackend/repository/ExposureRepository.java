package com.lsoftware.pastebinbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lsoftware.pastebinbackend.entities.ExposureEntity;

@Repository
public interface ExposureRepository extends CrudRepository<ExposureEntity, Long> {
	ExposureEntity findById(long id);
}