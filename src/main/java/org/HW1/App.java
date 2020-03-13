package org.HW1;

import java.text.DecimalFormat;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while(true)
        {


            System.out.println("Enter an Expression:");
            String exp = scanner.nextLine();
            DecimalFormat numberFormat = new DecimalFormat("#.00000");
            System.out.println("The value of expression " + exp + " is: " + numberFormat.format(calculate(exp)));
        }
    }

    static Double calculate(String exp) {
        //System.out.println("'" + exp + "'");

        if (exp.equals(""))
            return 0.0;
        int ind;

        ind = exp.indexOf(")");
        if (ind != -1)
        {
            int lp = ind - 1;
            while (exp.charAt(lp) != '(')
                lp--;

            return calculate(exp.substring(0, lp) + calculate(exp.substring(lp + 1, ind)) + exp.substring(ind + 1));
        }

        ind = exp.indexOf("+");
        if (ind != -1)
            return calculate(exp.substring(0, ind).trim()) + calculate(exp.substring(ind + 1).trim());


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
