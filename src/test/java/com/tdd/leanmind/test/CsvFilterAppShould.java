package com.tdd.leanmind.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("spring")
public class CsvFilterAppShould {
    @Autowired
    MockMvc mvc;

    final String fileName = "invoices.csv";
    final String filepath = System.getProperty("java.io.tmpdir") + fileName;
    File csvFile;

    @Before
    public void setUp() {
        csvFile = new File(filepath);
    }

    @After
    public void tearDown() {
        csvFile.delete();
    }

    @Test
    public void display_lines_after_filtering_csv_file() throws Exception {
        List<String> lines = List.of(
                "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente",
                "1,02/05/2019,1000,810,19,,ACER Laptop,B76430134,",
                "2,03/12/2019,1000,2000,19,8,Lenovo Laptop,,78544372A");

        createCsv(lines);

        String pageSource = mvc.perform(
                MockMvcRequestBuilders.multipart(Configuration.WEBURL + "/postcsv")
                        .file(new MockMultipartFile(
                                "file", filepath,
                                "text/plain",
                                new FileInputStream(csvFile))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();


        assertThat(pageSource).contains(lines.get(0));
        assertThat(pageSource).contains(lines.get(1));
        assertThat(pageSource).doesNotContain(lines.get(2));
    }

    private void createCsv(List<String> lines) {
        try {
            PrintWriter printWriter = new PrintWriter(filepath);
            for (String line : lines) {
                printWriter.println(line);
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}