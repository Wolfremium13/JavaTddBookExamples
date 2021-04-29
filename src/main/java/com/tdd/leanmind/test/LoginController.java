package com.tdd.leanmind.test;

import csvfilter.CsvFilter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {
    @GetMapping("/login")
    String showForm() {
        return "<h2>Login Form</h2>" +
                "<form>" +
                "<input type=\"text\" name=\"username\">" +
                "<input type=\"password\" name=\"password\">" +
                "<button id=\"submit\" type=\"submit\">Submit</button>" +
                "</form>";
    }

    @GetMapping("/csvform")
    String uploadResult() {
        return "<h2>File Form</h2>" +
                "<form enctype=\"multipart/form-data\" method=\"post\" action=\"/csvform\">" +
                "<input type=\"file\" name=\"file\" id=\"file\">" +
                "<button id=\"submit\" type=\"submit\">Submit</button>" +
                "</form>";
    }

    @PostMapping("/csvform")
    public @ResponseBody
    String showResult(@RequestPart("file") MultipartFile file) {
        List<String> fileResult = new ArrayList<>();

        InputStream in;
        BufferedReader br;
        try {
            in = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                fileResult.add(line);
            }

            br.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CsvFilter csvFilter = new CsvFilter();
        List<String> filteredResult = csvFilter.apply(fileResult);

        return String.join(System.lineSeparator(), filteredResult);
    }

}