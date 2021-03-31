package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.ModelService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Model;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.dto.ModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("smartgarage/model")
public class ModelController {

    private final ModelService service;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public ModelController(ModelService service,
                           ModelConversionHelper modelConversionHelper,
                           AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Model> getAllModels(){
        return  service.getAllModels();
    }

    @GetMapping("/{id}")
    public Model getById(@PathVariable int id) {
        try {
            return service.getModelById(id);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PostMapping
    public Model create(@RequestHeader HttpHeaders headers, @Valid @RequestBody ModelDto modelDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Model model = modelConversionHelper.modelFromDto(modelDto);
            service.create(model, user);
            return model;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
