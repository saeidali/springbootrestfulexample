package com.example.springbootrestfulexample.utils;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mapper.class);

    public <T> T convert(Object srcObj, Class<T> targetClass) {
        T response = null;
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        try {
            response = modelMapper.map(srcObj, targetClass);
        } catch (Exception e) {
            LOGGER.error("Mapper class,convert method :{}", e.getMessage());
        }
        return response;
    }

    public <S, T> List<T> convertToList(List<S> srcList, Class<T> targetClass) {
        List<T> response = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        try {
            srcList.forEach(source -> response.add(modelMapper.map(source, targetClass)));

        } catch (Exception e) {
            LOGGER.error("Mapper class,convertToList method :{}", e.getMessage());
        }

        return response;

    }

}
