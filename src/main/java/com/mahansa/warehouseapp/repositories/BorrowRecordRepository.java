package com.mahansa.warehouseapp.repositories;

import com.mahansa.warehouseapp.models.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
}
