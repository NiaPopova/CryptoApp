package com.crypto.trading.repository;

import com.crypto.trading.model.entity.UserHold;
import com.crypto.trading.model.entity.UserHoldId;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHoldRepository extends CrudRepository<UserHold, UserHoldId> {
    @Transactional
    void deleteAllByIdUserId(Integer id);
}
