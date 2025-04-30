package com.fasteat.fasteat_api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class FasteatApiApplicationIT {

    @Test
    void contextLoads() {
        // Solo verifica que el contexto se carga
    }
}