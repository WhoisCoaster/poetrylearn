package com.imyifeng.poetrylearn.poetrylearn.controller;

import com.imyifeng.poetrylearn.poetrylearn.model.Poem;
import com.imyifeng.poetrylearn.poetrylearn.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PoetryController {

    private final PoetryService poetryService;
    private List<Poem> currentPoems;
    private int currentIndex = 0;
    private int score = 0;
    private boolean usedHint = false;
    private String currentQuestion;

    @Autowired
    public PoetryController(PoetryService poetryService) {
        this.poetryService = poetryService;
    }

    @GetMapping("/")
    public String home() {
        resetGame();
        return "index";
    }

    @PostMapping("/start")
    public String startGame(@RequestParam String difficulty, Model model) {
        resetGame();
        currentPoems = poetryService.getRandomPoems(difficulty, 20);
        currentQuestion = poetryService.generateQuestion(currentPoems.get(currentIndex));
        
        // 分割诗句
        String[] parts = currentQuestion.split("_____", 2);
        model.addAttribute("questionBefore", parts[0]);
        model.addAttribute("questionAfter", parts.length > 1 ? parts[1] : "");
        
        // 获取答案长度
        String correctAnswer = poetryService.getAnswer(currentPoems.get(currentIndex), currentQuestion);
        model.addAttribute("answerLength", correctAnswer.length() + 1);
        
        return "game";
    }

    @PostMapping("/submit")
    public String submitAnswer(@RequestParam(required = false) String answer, Model model) {
        Poem currentPoem = currentPoems.get(currentIndex);
        String correctAnswer = poetryService.getAnswer(currentPoem, currentQuestion);

        if (answer != null && answer.equals(correctAnswer)) {
            score += usedHint ? 3 : 5;
            model.addAttribute("result", "正确！");
        } else {
            model.addAttribute("result", "错误！正确答案是：" + correctAnswer);
        }

        model.addAttribute("poemInfo", poetryService.getPoemInfo(currentPoem));
        model.addAttribute("score", score);
        usedHint = false;
        return "result";
    }

    @PostMapping("/next")
    public String nextQuestion(Model model) {
        currentIndex++;
        if (currentIndex >= currentPoems.size()) {
            model.addAttribute("score", score);
            return "final";
        }
        currentQuestion = poetryService.generateQuestion(currentPoems.get(currentIndex));
        
        // 分割诗句
        String[] parts = currentQuestion.split("_____", 2);
        model.addAttribute("questionBefore", parts[0]);
        model.addAttribute("questionAfter", parts.length > 1 ? parts[1] : "");
        
        // 获取答案长度
        String correctAnswer = poetryService.getAnswer(currentPoems.get(currentIndex), currentQuestion);
        model.addAttribute("answerLength", correctAnswer.length() + 1);
        
        return "game";
    }

    @PostMapping("/hint")
    public String getHint(Model model) {
        usedHint = true;
        Poem currentPoem = currentPoems.get(currentIndex);
        model.addAttribute("poemInfo", poetryService.getPoemInfo(currentPoem));
        
        // 重新传递分割后的诗句
        String[] parts = currentQuestion.split("_____", 2);
        model.addAttribute("questionBefore", parts[0]);
        model.addAttribute("questionAfter", parts.length > 1 ? parts[1] : "");
        
        // 获取答案长度
        String correctAnswer = poetryService.getAnswer(currentPoem, currentQuestion);
        model.addAttribute("answerLength", correctAnswer.length() + 1);
        
        return "game";
    }

    private void resetGame() {
        currentPoems = null;
        currentIndex = 0;
        score = 0;
        usedHint = false;
        currentQuestion = null;
    }
}