package me.blf.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileSearcherTest {
    private static FileSearcher fileSearcher;

    @BeforeAll
    void init(){
        fileSearcher = new FileSearcher("java", Path.of("/home"), ".log");
    }

    @Test
    void processSearching() {
        var filesLst = fileSearcher.processSearching();
        assertTrue(filesLst.size() > 0);
    }

    @Test
    void fileContainsPhrase() {
        //TODO: write test for "fileContainsPhrase" method
    }
}

//    тест на скорость
//    public static double getTime(String filePath, String searchPhrase) {
//        for (int i = 0; i < 2; i ++) { //прогрев JVM
//            findStringInFile(filePath, searchPhrase);
//        }
//        int count = 3; //первоначальное кол-во повтора выполнения testMethod
//
//        while(true) {
//            long begin =  System.nanoTime();
//
//            for (int i = 0; i < count; i ++)
//                findStringInFile(filePath, searchPhrase);
//
//            long end = System.nanoTime();
//
//            if ((end - begin) < 1000000000) { //Прогон тестов пока суммарное выполнения count раз
//                count *= 100000;              //testMethod`a не будет равно несколько секунд
//                continue;
//            }
//
//            System.out.println((double)(end - begin) / count);
//            return (double)(end - begin) / count;
//        }
//    }