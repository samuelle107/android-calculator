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
        // Sets the current view to a layout.  In this case, the calculator
        setContentView(R.layout.calculator);

        // Creates a new presenter object that references this view
        presenter = new CalculatorPresenter(this);
        // Links all the text views to the view
        textViews = new TextView[] { findViewById(R.id.equationLineOne), findViewById(R.id.equationLineTwo) };
    }

    @Override
    public void updateTextView(String lineOne, String lineTwo) {
        this.textViews[0].setText(lineOne);
        this.textViews[1].setText(lineTwo);
    }

    // The params is the view.  In this case, it is the button.
    public void onCellClick(View v) {
        // Store the button text and button type into variables
        String buttonText = ((Button) v).getText().toString();
        Cell cellType = Cell.valueOf(v.getTag().toString());

        // Send those values to the presenter
        presenter.onCellSelected(buttonText, cellType);
    }
}
