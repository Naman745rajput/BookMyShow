package com.naman.bms.repository;

import com.naman.bms.model.Payment;
import com.naman.bms.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long>
{
    List<ShowSeat> findByShowId(Long movieId);

    List<ShowSeat> findByBookingId(Long bookingId);

    List<ShowSeat> findByShowIdAndStatus(Long showId, String status);

}
