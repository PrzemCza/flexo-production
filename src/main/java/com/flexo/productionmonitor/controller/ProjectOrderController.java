package com.flexo.productionmonitor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flexo.productionmonitor.dto.ProjectOrderRequest;
import com.flexo.productionmonitor.dto.ProjectOrderResponse;
import com.flexo.productionmonitor.service.ProjectOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/production-orders")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:*", allowedHeaders = "*", allowCredentials = "true") // Dopasuj do swojego setupu
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

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody ProjectOrderRequest request) {
        orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }


}