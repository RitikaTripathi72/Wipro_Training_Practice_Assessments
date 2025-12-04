package com.wipro.practice;
import java.util.Scanner;

public class LogicalOperatorsExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean input1 = scanner.nextBoolean();
        boolean input2 = scanner.nextBoolean();
        
        // TODO: Write your code here
        boolean resultAND = input1 && input2;
        System.out.println(resultAND);

        boolean resultOR = input1 || input2;
        System.out.println(resultOR);
       
        scanner.close();
    }
}