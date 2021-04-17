package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.User;
import com.java.smart_garage.models.WorkService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface WorkServiceService {

    List<WorkService> getAllWorkServices(Optional<String> currency);

    WorkService getById(Optional<String> currency,int id);

    WorkService getByName(String name);

    void create(WorkService service, User credentialUser);

    void update(WorkService service, User credentialUser);

    void delete(int id, User credentialUser);
}
