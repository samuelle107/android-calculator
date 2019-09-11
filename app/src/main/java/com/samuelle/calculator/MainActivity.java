package com.samuelle.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    private StringBuilder equation;
    private StringBuilder result;
    TextView lineOneView;
    TextView lineTwoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        equation = new StringBuilder();
        result = new StringBuilder();

        lineOneView = findViewById(R.id.equationLineOne);
        lineTwoView = findViewById(R.id.equationLineTwo);
    }

    public void onCellClick(View v) {
        Button button = (Button) v;
        String value = button.getText().toString();

        if (result.length() != 0) {
            equation = new StringBuilder();
            result = new StringBuilder();

            lineOneView.setText("");
            lineTwoView.setText("");
        }

        equation.append(value);
        lineTwoView.setText(equation);
    }

    public void onBackSpaceClick(View v) {
        int length = equation.length();

        if (result.length() != 0) {
            equation = new StringBuilder();
            result = new StringBuilder();

            lineOneView.setText("");
            lineTwoView.setText("");
        } else {
            equation.setLength(length == 0 ? 0 : length - 1);
            lineTwoView.setText(equation);
        }
    }

    public void onClearClick(View v) {
        equation = new StringBuilder();

        lineOneView.setText("");
        lineTwoView.setText("");
    }

    public void onEqualClick(View v) {
        Expression e = new ExpressionBuilder(equation.toString()).build();
        try {
            result = new StringBuilder(Double.toString(e.evaluate()));
        } catch (Exception err) {
            result = new StringBuilder("0");
        }

        lineOneView.setText(equation);
        lineTwoView.setText(result);

        equation = new StringBuilder(result);
    }

    public void onOperatorClick(View v) {
        Button button = (Button) v;
        char operator = button.getText().toString().charAt(0);

        if (result.length() != 0) {
            equation = new StringBuilder(result);
            result = new StringBuilder();

            lineOneView.setText("");
            lineTwoView.setText(equation);
        }

        if (equation.length() != 0) {
            char lastChar = equation.charAt(equation.length() - 1);

            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                equation.setCharAt(equation.length() - 1, operator);
            } else {
                equation.append(operator);
            }
        }

        lineTwoView.setText(equation);
    }

    public void onPlusMinusClick(View v) {
        if (result.length() != 0) {
            equation = new StringBuilder(result);
            result = new StringBuilder();

            lineOneView.setText("");
            lineTwoView.setText(equation);
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

        lineTwoView.setText(equation);
    }
}
