package org.HW1;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an Expression:");
        String exp = scanner.nextLine();
        System.out.println("The value of expression " + exp + " is: " + calculate(exp));
    }

    static Double operator(Object left, char op, Object right) {

        Double cal_left = 0.0, cal_right = 0.0;
        if (right instanceof String)
            cal_right = calculate((String) right);
        else if (right instanceof Double)
            cal_right = (Double) right;

        if (left instanceof String)
            cal_left = calculate((String) left);
        else if (right instanceof Double)
            cal_left = (Double) left;

        if (op == '+')
            return cal_left + cal_right;
        if (op == '-')
            return cal_left - cal_right;
        if (op == '*')
            return cal_left * cal_right;
        return cal_left / cal_right;
    }


    static Double calculate(String exp) {
        //System.out.println("'" + exp + "'");

        if (exp.equals(""))
            return 0.0;

        int ind = exp.indexOf(")");
        if (ind != -1)
        {
            int lp = ind - 1;
            while (exp.charAt(lp) != '(')
                lp--;

            if (lp == 0 && ind == exp.length() - 1)
                return calculate(exp.substring(1, exp.length() - 1));

            if (lp == 0)
            {
                Double left = calculate(exp.substring(1, ind));
                String right = exp.substring(ind + 1, exp.length() - 1).trim();
                char op = right.charAt(0);
                right = right.substring(1);
                return operator(left, op, right);
            }

            if (ind == exp.length() - 1)
            {
                String left = exp.substring(0, lp).trim();
                char op = left.charAt(left.length() - 1);
                left = left.substring(0, left.length() - 1);
                Double right = calculate(exp.substring(lp + 1, exp.length() - 1));
                return operator(right, op, left);
            }

            String left = exp.substring(0, lp).trim();
            char op1 = left.charAt(left.length() - 1);
            Double middle = calculate(exp.substring(lp + 1, ind));
            String right = exp.substring(ind + 1).trim();
            char op2 = right.charAt(0);
            if (op1 == '*' || op1 == '/')
                return operator(operator(left, op1, middle), op2, right);
            return operator(left, op1, String.valueOf(operator(middle, op2, right)));
        }

        ind = exp.indexOf("+");
        if (ind != -1)
            return calculate(exp.substring(0, ind).trim()) + calculate(exp.substring(ind + 1).trim());

        ind = exp.indexOf("-");
        if (ind != -1)
        {
            String left = exp.substring(0, ind).trim();
            String right = exp.substring(ind + 1).trim();
            char lastChar = left.charAt(left.length() - 1);
            if (lastChar == '*' || lastChar == '/')
                right = String.valueOf(calculate(right));
            return calculate(left) - calculate(right);
        }
        ind = exp.indexOf("*");
        if (ind != -1)
            return calculate(exp.substring(0, ind).trim()) * calculate(exp.substring(ind + 1).trim());

        ind = exp.indexOf("/");
        if (ind != -1)
            return calculate(exp.substring(0, ind).trim()) / calculate(exp.substring(ind + 1).trim());

        return Double.parseDouble(exp);
    }
}
