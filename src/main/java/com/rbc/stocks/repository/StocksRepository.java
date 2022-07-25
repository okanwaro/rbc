package com.rbc.stocks.repository;

import com.rbc.stocks.dto.StockRequestDTO;
import com.rbc.stocks.entity.StocksEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface StocksRepository extends JpaRepository<StockRequestDTO, Integer> {
    List<StockRequestDTO> findByStock(String name);
}
