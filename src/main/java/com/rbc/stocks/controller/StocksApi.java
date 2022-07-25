package com.rbc.stocks.controller;

import com.rbc.stocks.dto.StockRequestDTO;
import com.rbc.stocks.entity.StocksEntity;
import com.rbc.stocks.repository.StocksRepository;

import javax.validation.Valid;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import java.util.*;

@Validated
@RestController
@RequestMapping("api/stock-data")
public class StocksApi implements WebMvcConfigurer {

    Logger logger = LoggerFactory.getLogger(StocksApi.class);
    @Autowired
    StocksRepository stocksRepository;

    @GetMapping("/")
    public List<StockRequestDTO> getAllStocks() {
        return stocksRepository.findAll();
    }

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<StockRequestDTO> insertBulkStockData( StocksEntity stockRecord) {
        StockRequestDTO currentStockData = new StockRequestDTO();
        currentStockData.setQuarter(StockRequestDTO.checkIntegerEmpty(stockRecord.getStock()));
        currentStockData.setStock(stockRecord.getStock());
        try {
            currentStockData.setDate(StockRequestDTO.covertDates(stockRecord.getDate()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        currentStockData.setOpen(StockRequestDTO.getAmount(stockRecord.getOpen()));
        currentStockData.setHigh(StockRequestDTO.getAmount(stockRecord.getHigh()));
        currentStockData.setLow(StockRequestDTO.getAmount(stockRecord.getLow()));
        currentStockData.setClose(StockRequestDTO.getAmount(stockRecord.getClose()));
        currentStockData.setVolume(StockRequestDTO.checkLongEmpty(stockRecord.getVolume()));
        currentStockData.setPercentChangePrice(StockRequestDTO.checkDoubleEmpty(stockRecord.getPercentChangePrice()));
        currentStockData.setPercentChangeVolumeOverLastWk(StockRequestDTO.checkFloatEmpty(stockRecord.getPercentChangeVolumeOverLastWk()));
        currentStockData.setPreviousWeeksVolume(StockRequestDTO.checkLongEmpty(stockRecord.getPreviousWeeksVolume()));
        currentStockData.setNextWeeksOpen(StockRequestDTO.getAmount(stockRecord.getNextWeeksOpen()));
        currentStockData.setNextWeeksClose(StockRequestDTO.getAmount(stockRecord.getNextWeeksClose()));
        currentStockData.setPercentChangeNextWeeksPrice(StockRequestDTO.getAmount(stockRecord.getPercentChangeNextWeeksPrice()));
        currentStockData.setDaysToNextDividend(StockRequestDTO.checkIntegerEmpty(stockRecord.getDaysToNextDividend()));
        currentStockData.setPercentReturnNextDividend(StockRequestDTO.checkDoubleEmpty(stockRecord.getPercentReturnNextDividend()));

        return new ResponseEntity<StockRequestDTO>(stocksRepository.save(currentStockData),HttpStatus.CREATED);
    }


    @GetMapping("/{stockName}")
    public ResponseEntity<List<StockRequestDTO>> getStockByStockName(@PathVariable String stockName) {
        logger.info(String.valueOf(stocksRepository.findByStock(stockName)));
        return new ResponseEntity<List<StockRequestDTO>>(stocksRepository.findByStock(stockName),HttpStatus.OK);
    }

    @PostMapping(value = "/bulk-insert", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> insertBulkStockData(@RequestPart MultipartFile dataToInsert) {
        BufferedReader br;

        try {
            List<StockRequestDTO> allRecords = new ArrayList<>();
            String line;
//            StockRequestDTO currentStockData = new ;
            InputStream is = dataToInsert.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            br.readLine();
            while ((line = br.readLine()) != null) {
                StockRequestDTO currentStockData = new StockRequestDTO();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                while (lineScanner.hasNext()) {
                    currentStockData.setQuarter(StockRequestDTO.checkIntegerEmpty((lineScanner.next())));
                    currentStockData.setStock(lineScanner.next());
                    currentStockData.setDate(StockRequestDTO.covertDates(lineScanner.next()));
                    currentStockData.setOpen(StockRequestDTO.getAmount(lineScanner.next()));
                    currentStockData.setHigh(StockRequestDTO.getAmount(lineScanner.next()));
                    currentStockData.setLow(StockRequestDTO.getAmount(lineScanner.next()));
                    currentStockData.setClose(StockRequestDTO.getAmount(lineScanner.next()));
                    currentStockData.setVolume(StockRequestDTO.checkLongEmpty((lineScanner.next())));
                    currentStockData.setPercentChangePrice(StockRequestDTO.checkDoubleEmpty((lineScanner.next())));
                    currentStockData.setPercentChangeVolumeOverLastWk(StockRequestDTO.checkFloatEmpty((lineScanner.next())));
                    currentStockData.setPreviousWeeksVolume(StockRequestDTO.checkLongEmpty((lineScanner.next())));
                    currentStockData.setNextWeeksOpen(StockRequestDTO.getAmount(lineScanner.next()));
                    currentStockData.setNextWeeksClose(StockRequestDTO.getAmount(lineScanner.next()));
                    currentStockData.setPercentChangeNextWeeksPrice(StockRequestDTO.getAmount(lineScanner.next()));
                    currentStockData.setDaysToNextDividend(StockRequestDTO.checkIntegerEmpty((lineScanner.next())));
                    currentStockData.setPercentReturnNextDividend(StockRequestDTO.checkDoubleEmpty((lineScanner.next())));
                    allRecords.add(currentStockData);
                }

                lineScanner.close();
            }
              stocksRepository.saveAll(allRecords);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return new ResponseEntity("Stock record Inserted", HttpStatus.CREATED);
    }
}
