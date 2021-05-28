package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.WorkServiceStatus;

import java.util.List;

public interface WorkServiceStatusRepository {
    List<WorkServiceStatus> getAllStatuses();

    WorkServiceStatus getById(int id);
}
