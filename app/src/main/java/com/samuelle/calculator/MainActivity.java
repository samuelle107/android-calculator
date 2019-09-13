package com.samuelle.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.samuelle.calculator.model.Equation;

public class MainActivity extends AppCompatActivity {
    Equation equation;
    TextView lineOneView;
    TextView lineTwoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        equation = new Equation();
        lineOneView = findViewById(R.id.equationLineOne);
        lineTwoView = findViewById(R.id.equationLineTwo);
    }

    private void setText(String lineOne, String lineTwo) {
        lineOneView.setText(lineOne);
        lineTwoView.setText(lineTwo);
    }

    public void onCellClick(View v) {
        char value = ((Button) v).getText().toString().charAt(0);

        equation.appendValueToEquation(value);
        setText("", equation.getEquation());
    }

    public void onBackSpaceClick(View v) {
        equation.removeLastCharFromEquation();
        setText("", equation.getEquation());
    }

    public void onClearClick(View v) {
        equation.clearEquation();
        setText("", "");
    }

    public void onEqualClick(View v) {
        equation.calculateResult();
        setText(equation.getEquation(), equation.getResult());
    }

    public void onOperatorClick(View v) {
        char operator = ((Button) v).getText().toString().charAt(0);

        equation.appendOperatorToEquation(operator);
        setText("", equation.getEquation());
    }

    public void onPlusMinusClick(View v) {
        equation.addPlusMinusToEquation();
        setText("", equation.getEquation());
    }
}
