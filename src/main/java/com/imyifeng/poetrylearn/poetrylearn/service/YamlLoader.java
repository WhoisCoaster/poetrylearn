package com.imyifeng.poetrylearn.poetrylearn.service;

import com.imyifeng.poetrylearn.poetrylearn.model.Poem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class YamlLoader {
    
    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    public List<Poem> loadPoems(String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource(filename);
        return yamlMapper.readValue(resource.getInputStream(), 
            yamlMapper.getTypeFactory().constructCollectionType(List.class, Poem.class));
    }
}