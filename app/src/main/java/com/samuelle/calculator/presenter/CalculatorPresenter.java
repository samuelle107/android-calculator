package com.samuelle.calculator.presenter;

import com.samuelle.calculator.model.Cell;
import com.samuelle.calculator.model.Equation;
import com.samuelle.calculator.view.CalculatorView;

public class CalculatorPresenter {
    private Equation equation;
    private CalculatorView view;

    public CalculatorPresenter(CalculatorView view) {
        this.equation = new Equation();
        this.view = view;
    }

    public void onCellSelected(String buttonText, Cell cellType) {
        // Depending on the button that got typed, it will call the corresponding method from model to modify the equation and then update the view
        switch (cellType) {
            case OPERATOR: {
                equation.appendOperatorToEquation(buttonText.charAt(0));
                view.updateTextView("", equation.getEquation());

                break;
            }
            case PLUS_MINUS: {
                equation.addPlusMinusToEquation();
                view.updateTextView("", equation.getEquation());

                break;
            }
            case CLEAR: {
                equation.clearEquation();
                view.updateTextView("", "");

                break;
            }
            case DEL: {
                equation.removeLastCharFromEquation();
                view.updateTextView("", equation.getEquation());

                break;
            }
            case EQUAL: {
                equation.calculateResult();
                view.updateTextView(equation.getEquation(), equation.getResult());

                break;
            }
            default: {
                equation.appendValueToEquation(buttonText.charAt(0));
                view.updateTextView("", equation.getEquation());
            }
        }
    }
}
