import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


//parse data file
//prompt user for 2 id's
//try to find optimal route given 2 id's

public class Main {
    public static void main(String[] args) {
        Map m1 = new Map();
        
        Prompt prompt = Prompt.getInstance(m1);

        while (true) {
            prompt.promptMenu();
        }


       
    }
}

