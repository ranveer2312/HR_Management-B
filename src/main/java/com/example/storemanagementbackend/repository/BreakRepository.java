// package com.example.storemanagementbackend.repository;

// import com.example.storemanagementbackend.model.Break;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;

// @Repository
// public interface BreakRepository extends JpaRepository<Break, Long> {
// Optional<Break> findByEmployeeIdAndActiveTrue(String employeeId);

// @Query("SELECT b FROM Break b WHERE b.active = true AND b.startTime <
// :cutoffTime AND b.hrNotified = false")
// List<Break> findOverdueBreaks(LocalDateTime cutoffTime);

// List<Break> findByEmployeeIdOrderByStartTimeDesc(String employeeId);
// List<Break> findAllByOrderByStartTimeDesc();
// boolean existsByEmployeeIdAndBreakTypeAndStartTimeBetween(String employeeId,
// Break.BreakType breakType, LocalDateTime startOfDay, LocalDateTime endOfDay);
// }
