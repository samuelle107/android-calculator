package com.samuelle.calculator.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.samuelle.calculator.R;
import com.samuelle.calculator.model.Cell;
import com.samuelle.calculator.presenter.CalculatorPresenter;

public class CalculatorActivity extends AppCompatActivity implements CalculatorView {
    private CalculatorPresenter presenter;
    private TextView[] textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        presenter = new CalculatorPresenter(this);
        textViews = new TextView[] { findViewById(R.id.equationLineOne), findViewById(R.id.equationLineTwo) };
    }

    @Override
    public void updateTextView(String lineOne, String lineTwo) {
        this.textViews[0].setText(lineOne);
        this.textViews[1].setText(lineTwo);
    }

    public void onCellClick(View v) {
        String buttonText = ((Button) v).getText().toString();
        Cell cellType = Cell.valueOf(v.getTag().toString());

        presenter.onCellSelected(buttonText, cellType);
    }
}
