package com.crudempleados.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoClaveMigrationService implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpleadoClaveMigrationService.class);

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("Clave migration startup check completed for empleados");
    }
}
