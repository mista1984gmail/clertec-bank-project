package com.example.clertecbankproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class LoadPropertiesService {
    private static final Logger logger = LoggerFactory.getLogger(LoadPropertiesService.class);
    private static final String PROPERTIES_FILE_NAME = "src/main/resources/application.yml";
    public static BigDecimal getInterestRate(String nameOfProperties){
        Map<String, Integer> data = new HashMap<>();
        try(InputStream inputStream= new FileInputStream(PROPERTIES_FILE_NAME)) {
            Yaml yaml = new Yaml();
            data = yaml.load(inputStream);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }
        return new BigDecimal(data.get(nameOfProperties));
    }
}
