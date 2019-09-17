package com.samuelle.calculator.model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Equation {
    private StringBuilder equation;
    private StringBuilder result;

    public Equation() {
        equation = new StringBuilder();
        result = new StringBuilder();
    }

    public String getEquation() {
        return equation.toString();
    }

    public String getResult() {
        return result.toString();
    }

    public void calculateResult() {
        char lastChar = equation.charAt(equation.length() - 1);

        // If the last symbol in the equation is an operator, remove it.
        if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
            equation.setLength(equation.length() - 1);
        }

        // Convert the equation from a string to an Expression object.
        Expression e;
        try {
            e = new ExpressionBuilder(equation.toString()).build();
        } catch (Exception err) {
            e = new ExpressionBuilder("0").build();
        }

        // Evaluates the result
        try {
            result = new StringBuilder(Double.toString(e.evaluate()));
        } catch (Exception err) {
            result = new StringBuilder("0");
        }
    }

    // Adds a symbol to the end of the equation.  If the equation is already evaluated, clear the equation and result, then append the value
    public void appendValueToEquation(char value) {
        if (result.length() != 0) {
            equation = new StringBuilder();
            result = new StringBuilder();
        }

        equation.append(value);
    }

    // If there is a result, clear the result and equation.  If the length of equation is 0, do nothing, else, remove the last character from the equation
    public void removeLastCharFromEquation() {
        int equationLength = equation.length();
        int resultLength = result.length();

        if (resultLength != 0) {
            equation = new StringBuilder();
            result = new StringBuilder();
        } else {
            equation.setLength(equationLength == 0 ? 0 : equationLength - 1);
        }
    }

    // Clear the equation and result
    public void clearEquation() {
        equation = new StringBuilder();
        result = new StringBuilder();
    }

    // attempt to add an operator at the end of the equation
    // If there is a result, clear the result string, and replace the equation with the result
    public void appendOperatorToEquation(char operator) {
        if (result.length() != 0) {
            equation = new StringBuilder(result);
            result = new StringBuilder();
        }

        // If the last symbol is an operator, replace the operator with the new one, otherwise, add the operator to the end
        if (equation.length() != 0) {
            char lastChar = equation.charAt(equation.length() - 1);

            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                equation.setCharAt(equation.length() - 1, operator);
            } else {
                equation.append(operator);
            }
        }
    }

    public void addPlusMinusToEquation() {
        // If there is a result, replace the equation with the result and clear the result
        if (result.length() != 0) {
            equation = new StringBuilder(result);
            result = new StringBuilder();
        }

        // Attempts to find the last number in the equation from the right
        for (int i = equation.length() - 1; i >= 0; i--) {
            char currentChar = equation.charAt(i);

            // If the symbol before the last number is a plus, switch it to a -
            if (currentChar == '+') {
                equation.setCharAt(i, '-');

                break;
            } else if (currentChar == '-') {
                // If the first character is a -, remove the negative from the start of the equation
                if (i == 0) {
                    String tempEquation = equation.substring(1, equation.length());

                    equation = new StringBuilder(tempEquation);
                } else {
                    // if the - is followed after a * or /, then it will remove the -
                    if (equation.charAt(i - 1) == '*' || equation.charAt(i - 1) == '/') {
                        String leftSide = equation.substring(0, i);
                        String rightSide = equation.substring(i + 1, equation.length());

                        equation = new StringBuilder(leftSide + rightSide);
                    } else {
                        // change the - to a +
                        equation.setCharAt(i, '+');
                    }
                }

                break;
            } else if (currentChar == '*' || currentChar == '/') {
                // If the character is a + or -, then append a - after that operator
                String leftSide = equation.substring(0, i + 1);
                String rightSide = equation.substring(i + 1, equation.length());

                equation = new StringBuilder(leftSide + '-' + rightSide);

                break;
            } else if (i == 0) {
                // If the loop cannot find an operator before the last number, then it will add a - to the beginning of the equation
                String tempEquation = equation.substring(0, equation.length());

                equation = new StringBuilder('-' + tempEquation);
            }
        }
    }
}
