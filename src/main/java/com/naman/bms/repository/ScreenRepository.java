package com.naman.bms.repository;

import com.naman.bms.model.Payment;
import com.naman.bms.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<Screen,Long>
{
    List<Screen> findByTheaterId(Long theaterId);

}
