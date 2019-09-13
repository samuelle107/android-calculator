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

        if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
            equation.setLength(equation.length() - 1);
        }

        Expression e = new ExpressionBuilder(equation.toString()).build();

        try {
            result = new StringBuilder(Double.toString(e.evaluate()));
        } catch (Exception err) {
            result = new StringBuilder("0");
        }
    }

    public void appendValueToEquation(char value) {
        if (result.length() != 0) {
            equation = new StringBuilder();
            result = new StringBuilder();
        }

        equation.append(value);
    }

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

    public void clearEquation() {
        equation = new StringBuilder();
        result = new StringBuilder();
    }

    public void appendOperatorToEquation(char operator) {
        if (result.length() != 0) {
            equation = new StringBuilder(result);
            result = new StringBuilder();
        }

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
        if (result.length() != 0) {
            equation = new StringBuilder(result);
            result = new StringBuilder();
        }

        for (int i = equation.length() - 1; i >= 0; i--) {
            char currentChar = equation.charAt(i);

            if (currentChar == '+') {
                equation.setCharAt(i, '-');

                break;
            } else if (currentChar == '-') {
                if (i == 0) {
                    String tempEquation = equation.substring(1, equation.length());

                    equation = new StringBuilder(tempEquation);
                } else {
                    if (equation.charAt(i - 1) == '*' || equation.charAt(i - 1) == '/') {
                        String leftSide = equation.substring(0, i);
                        String rightSide = equation.substring(i + 1, equation.length());

                        equation = new StringBuilder(leftSide + rightSide);
                    } else {
                        equation.setCharAt(i, '+');
                    }
                }

                break;
            } else if (currentChar == '*' || currentChar == '/') {
                String leftSide = equation.substring(0, i + 1);
                String rightSide = equation.substring(i + 1, equation.length());

                equation = new StringBuilder(leftSide + '-' + rightSide);

                break;
            } else if (i == 0) {
                String tempEquation = equation.substring(0, equation.length());

                equation = new StringBuilder('-' + tempEquation);
            }
        }
    }
}
