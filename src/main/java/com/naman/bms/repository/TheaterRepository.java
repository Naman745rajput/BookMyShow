package com.naman.bms.repository;

import com.naman.bms.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater,Long>
{
    List<Theater> findByCity(String city);

}
