package com.imyifeng.poetrylearn.poetrylearn.service;

import com.imyifeng.poetrylearn.poetrylearn.model.Poem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PoetryService {

    private final Map<String, List<Poem>> poemsMap;

    @Autowired
    public PoetryService(YamlLoader yamlLoader) throws Exception {
        this.poemsMap = new HashMap<>();
        loadAllPoems(yamlLoader);
    }

    private void loadAllPoems(YamlLoader yamlLoader) throws Exception {
        poemsMap.put("easy", yamlLoader.loadPoems("easy_mode.yml"));
        poemsMap.put("hard", yamlLoader.loadPoems("hard_mode.yml"));
        poemsMap.put("challenge", yamlLoader.loadPoems("challenge_mode.yml"));
    }

    public List<Poem> getRandomPoems(String difficulty, int count) {
        List<Poem> poems = new ArrayList<>(poemsMap.get(difficulty));
        Collections.shuffle(poems);
        return poems.subList(0, Math.min(count, poems.size()));
    }

    public String generateQuestion(Poem poem) {
        Poem.Line line = poem.getLines().get(ThreadLocalRandom.current().nextInt(poem.getLines().size()));
        boolean showFirstPart = ThreadLocalRandom.current().nextBoolean();
        
        return showFirstPart ? 
            line.getFirstPart() + "，_____" : 
            "_____，" + line.getSecondPart();
    }

    public String getAnswer(Poem poem, String question) {
        for (Poem.Line line : poem.getLines()) {
            if (question.contains(line.getFirstPart())) {
                return line.getSecondPart();
            } else if (question.contains(line.getSecondPart())) {
                return line.getFirstPart();
            }
        }
        return "";
    }

    public String getPoemInfo(Poem poem) {
        return "出自：" + poem.getTitle() + " - [" + poem.getDynasty() + "]" + poem.getAuthor();
    }
}