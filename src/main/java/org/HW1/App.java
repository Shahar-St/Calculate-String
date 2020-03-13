package org.HW1;

import java.text.DecimalFormat;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an Expression:");
        String exp = scanner.nextLine();
        DecimalFormat numberFormat = new DecimalFormat("#.00000");
        System.out.println("The value of expression " + exp + " is: " + numberFormat.format(calculate(exp)));
    }

    static Double calculate(String exp) {

        // parenthesis case
        int ind = exp.indexOf(")");
        if (ind != -1)
        {
            // ind is the index of the firs ')' and the loop finds the matching '('
            int lp = ind - 1;
            while (exp.charAt(lp) != '(')
                lp--;
            // then we calculate the value within the parenthesis, adding it back to the expression and call recursively
            return calculate(exp.substring(0, lp) + calculate(exp.substring(lp + 1, ind)) + exp.substring(ind + 1));
        }

        ind = exp.indexOf("+");
        if (ind != -1)
            return calculate(exp.substring(0, ind).trim()) + calculate(exp.substring(ind + 1).trim());

        // - case: we look for all - and prioritizing all cases:
        // 1) case of num1 - num2, in this case we need to execute '-'
        // 2) case of num1 */ - num2 (i.e 5 * -2), in this case we need to prioritize * or /
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
        // 3) case of - in the beginning of the expression. (here we know there are no more - in the expression since we took care of them in the prev if
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
