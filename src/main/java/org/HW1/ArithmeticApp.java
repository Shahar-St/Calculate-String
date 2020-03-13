package org.HW1;

import java.util.Scanner;

public class ArithmeticApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an Expression:");     // The program assumes valid input
        String exp = scanner.nextLine();
        System.out.format("The value of expression " + exp + " is: %.5f\n", calculate(exp));
    }

    static Double calculate(String exp) {

        // parenthesis case
        int ind = exp.indexOf(")");
        if (ind != -1)
        {
            // ind is the index of the first ')' and the loop finds the matching '('
            int lp = ind - 1;
            while (exp.charAt(lp) != '(')
                lp--;
            // then we calculate the value within the parenthesis, adding it back to the expression and call recursively
            return calculate(exp.substring(0, lp) + calculate(exp.substring(lp + 1, ind)) + exp.substring(ind + 1));
        }

        ind = exp.indexOf("+");
        if (ind != -1)
            return calculate(exp.substring(0, ind).trim()) + calculate(exp.substring(ind + 1).trim());

        // - case: we only want to execute cases of num1 - num2 (not -"exp" or num1 *-num2)
        for (int i = 1; i < exp.length(); i = Math.max(i + 1, ind))
        {
            ind = exp.indexOf("-", i);
            if (ind != -1)
            {
                int j = ind - 1;
                while (exp.charAt(j) == ' ')
                    j--;

                if (exp.charAt(j) != '*' && exp.charAt(j) != '/')
                    return calculate(exp.substring(0, ind)) - calculate(exp.substring(ind + 1));
            }
        }
        // case of -num. (by the program structure and the if statement There won't be any other operators in the string)
        ind = exp.indexOf("-");
        if (ind != -1 && !exp.contains("*") && !exp.contains("/"))
            return -calculate(exp.substring(1));

        ind = exp.indexOf("*");
        if (ind != -1)
            return calculate(exp.substring(0, ind).trim()) * calculate(exp.substring(ind + 1).trim());

        ind = exp.indexOf("/");
        if (ind != -1)
            return calculate(exp.substring(0, ind).trim()) / calculate(exp.substring(ind + 1).trim());

        return Double.parseDouble(exp);
    }
}
