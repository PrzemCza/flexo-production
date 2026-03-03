package com.flexo.productionmonitor.controller;

import com.flexo.productionmonitor.dto.ProjectOrderResponse;
import com.flexo.productionmonitor.service.ProjectOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production-orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "true") // Dopasuj do swojego setupu
public class ProjectOrderController {

    private final ProjectOrderService orderService;

    @GetMapping
    public ResponseEntity<List<ProjectOrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrdersWithMonitor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectOrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderWithMonitor(id));
    }

    // Tutaj w przyszłości dodasz POST do tworzenia zlecenia
}