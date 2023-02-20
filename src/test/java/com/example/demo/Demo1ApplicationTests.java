package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.http.server.PathContainer.Element;


@SpringBootTest
class Demo1ApplicationTests {


    @Test
    @DisplayName("")
    void testt123() throws Exception {

        String stringDate ="20221229";
        LocalDate baseDate = LocalDate.parse("2022-12-28");
        LocalDate confirmDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        //given
        boolean after = confirmDate.isAfter(baseDate) || confirmDate.isEqual(baseDate);
        System.out.println("after = " + after);


        //when

        //then

    }

    @Test
    @DisplayName("")
    void rightPath1() throws Exception {


        checkMatches("/profile/","/profile/*");
        checkMatches("/profile/b","/profile/*");
        checkMatches("/profile/b/","/profile/*");

        checkMatches("","/**");
        checkMatches("/profile","/profile/**");
        checkMatches("/profile/","/profile/**");
        checkMatches("/profile/","/profile");

        checkMatches("/a/b/c","/a/*/c");



        checkMatches("/a/b/","/**");
        checkMatches("/a/b/a","/*/*/*");

        //checkMatches("/a/b/c/d","/a/**");
        //checkMatches("/x/b/c/d","/**");
        //checkMatches3("/a/**");
        //checkMatches3("/a/*");

        //Assertions.assertTrue(elementsToString(toPathContainer("/a/b").elements()).equals("[/][a][/][b]"));


    }

    private String elementsToString(List<Element> elements) {
        StringBuilder s = new StringBuilder();
        for (Element element: elements) {
            s.append('[').append(element.value()).append(']');
        }
        return s.toString();
    }

    private void checkMatches3(String path) {
        boolean contains = path.contains("*");
        Assertions.assertTrue(contains);
    }


    private void checkMatches(String path,String uriTemplate) {
        PathPatternParser parser = new PathPatternParser();
        parser.setMatchOptionalTrailingSeparator(true);
        PathPattern p = parser.parse(uriTemplate);
        PathContainer pc = toPathContainer(path);
        Assertions.assertTrue(p.matches(pc));
    }

    private void checkNoMatch(String uriTemplate, String path) {
        PathPatternParser p = new PathPatternParser();
        PathPattern pattern = p.parse(uriTemplate);
        PathContainer PathContainer = toPathContainer(path);
        Assertions.assertTrue(!pattern.matches(PathContainer));
    }

    public static PathContainer toPathContainer(String path) {
        if (path == null) {
            return null;
        }
        return PathContainer.parsePath(path);
    }
}
