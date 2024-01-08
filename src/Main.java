/*
test comment for marging
*/

import Calc.Calc;
import Calc.CustomException;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
     public static void main(String[] args) {
         String res = "";
         System.out.printf("Wellcome EasyPeasyCalc 1.0 || Please enter \"exit\" for ending\n");

         Scanner in = new Scanner(System.in);
         boolean finish = false;
         String inputString = "", outputString = "";
         Calc calc = new Calc();
         boolean stat = true, abnormalExit = false;

         ArrayList <String> alInputStrings = new ArrayList<>();
         //alInputStrings.add("1+1+1");
         alInputStrings.add("i+i");
         alInputStrings.add("-1+2");
         alInputStrings.add("i+iii");
         alInputStrings.add("i+iiii");
         alInputStrings.add("i+ix");
         //alInputStrings.add("v-x");
         alInputStrings.add("x*x");
         alInputStrings.add("x*ix");
         alInputStrings.add("ix*ix");
         alInputStrings.add("iv*ix");
         alInputStrings.add("vi*ix");
         alInputStrings.add("x/ii");
         alInputStrings.add("x/iii");
         String[] arrInputString = new String[alInputStrings.size()];
         alInputStrings.toArray(arrInputString);

         int sizeInputString=arrInputString.length;
         int arrIndex=0;


         while (!finish) {
             if(arrIndex<sizeInputString){
                 inputString = arrInputString[arrIndex];
                 arrIndex++;
             }
             else{
                 stat = false;
             }

             if (!stat) {
                 System.out.print("Enter expression for calculating and press enter: ");
                 inputString = in.nextLine();
             }

             if (inputString.compareToIgnoreCase("e") == 0) {
                 finish = true;
             } //ending

             if (inputString.compareToIgnoreCase("exit") == 0) {
                 finish = true;
             } //ending

             if (!finish) {
                 try {
                     //Разберем строку на элементы
                     outputString = calc.separate(inputString);
                 } catch (CustomException e) {
                     System.out.println(e.getMessage());
                     abnormalExit = true;
                     finish = true;
                     break;
                 }

                 try {
                     //рассчитываем результат
                     res = calc.calculate(inputString);
                 } catch (CustomException e) {
                     System.out.println(e.getMessage());
                     abnormalExit = true;
                     finish = true;
                     break;
                 }

                 System.out.println(": " + outputString + " = " + res);
             }
         }

         if (!abnormalExit) {
             System.out.println("Buy! User start exiting programs");
         } else {
             System.out.println("Program finished by Exception!");
         }
     }

}


