package com.example.easycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Stack;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.media.MediaPlayer;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MediaPlayer mediaPlayer;
    TextView resultTv,solutionTv;
    MaterialButton buttonC,buttonBrackOpen,buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonAC,buttonDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = new MediaPlayer();
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);
        buttonC = findViewById(R.id.button_c);
        buttonBrackOpen = findViewById(R.id.button_open_bracket);
        buttonBrackClose = findViewById(R.id.button_close_bracket);
        buttonDivide = findViewById(R.id.button_divide);
        buttonMultiply = findViewById(R.id.button_multiply);
        buttonPlus = findViewById(R.id.button_plus);
        buttonMinus = findViewById(R.id.button_minus);
        buttonEquals = findViewById(R.id.button_equals);
        button0 = findViewById(R.id.button_0);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        buttonAC = findViewById(R.id.button_ac);
        buttonDot = findViewById(R.id.button_dot);
        setListeners();
    }
    private void setListeners() {
        buttonC.setOnClickListener(this);
        buttonBrackOpen.setOnClickListener(this);
        buttonBrackClose.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonAC.setOnClickListener(this);
        buttonDot.setOnClickListener(this);
    }
    int dot_count = 1;
    @Override
    public void onClick(View view) {

        MaterialButton button =(MaterialButton) view;
        String buttonText = button.getText().toString();

        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.tip);
        mediaPlayer.start();
//      String dataToCalulate = solutionTv.getText().toString();
            String currentText = resultTv.getText().toString();
            if(buttonText.equals("AC")){
                solutionTv.setText("");
                resultTv.setText("");
                dot_count =1;

        }else if (buttonText.equals("C")){
            if (!currentText.isEmpty() && currentText.charAt(currentText.length() - 1)=='.') { // Проверяем, что строка не пустая
                resultTv.setText(currentText.substring(0, currentText.length() - 1)); // Удаляем последний символ
                dot_count += 1;
            }else if (!currentText.isEmpty()) { // Проверяем, что строка не пустая
                resultTv.setText(currentText.substring(0, currentText.length() - 1)); // Удаляем последний символ
            }
        }else if(buttonText.equals("=")) {
            if (!currentText.isEmpty() && !isOperator(currentText.charAt(currentText.length() - 1)) ) { // Проверяем, что строка не пустая
                String expression = resultTv.getText().toString();
                String result = Double.toString(calculate(expression));
                solutionTv.setText(expression);
                resultTv.setText(result);
            }

        } else if (buttonText.equals("-")||buttonText.equals("+")||buttonText.equals("*")||buttonText.equals("/")) {
            if(!currentText.isEmpty() && !isOperator(currentText.charAt(currentText.length() - 1))){
                buttonText = view.getTag().toString();
                resultTv.append(buttonText);
                if (dot_count == 1) {
                    return; // Возвращаемся, не добавляя точку
                }else {
                dot_count+=1;}
            }

        } else if(buttonText.equals("(") ){
//            if(currentText.isEmpty() || !isNum(currentText.charAt(currentText.length() - 1))){
//                resultTv.append("1");
//            }
            if(currentText.isEmpty() || !isNum(currentText.charAt(currentText.length() - 1))){
                resultTv.append("1");
            }
            buttonText = view.getTag().toString();
            resultTv.append("*");
            resultTv.append(buttonText);



        }else if (buttonText.equals(".")) {
            if(!currentText.isEmpty() && dot_count > 0 && !isBrack(currentText.charAt(currentText.length() - 1)) && !isOperator(currentText.charAt(currentText.length() - 1))){
                buttonText = view.getTag().toString();
                resultTv.append(buttonText);
                dot_count-=1;
            }
        } else if(view instanceof MaterialButton) {
            buttonText = view.getTag().toString();
            if(currentText.length() >= 2 && !buttonText.equals(")")&& isCloseBrack(currentText.charAt(currentText.length()-1))  ){
                resultTv.append("*");
                resultTv.append(buttonText);
            }else {
                resultTv.append(buttonText);
            }
        }




    }
    private boolean isBrack(char c) {
        return c ==')'||c =='(';
    }
    private boolean isCloseBrack(char c) {
        return c ==')';
    }
    private boolean isNum(char c) {
        return c =='1'||c =='2'||c =='3'||c =='4'||c =='5'||c =='6'||c =='7'||c =='8'||c =='9'||c =='0';
    }
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c =='.';
    }
    public static double calculate(@NonNull String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int i = 0;
        while (i < expression.length()) {
            char currentChar = expression.charAt(i);
            if (currentChar == ' ') {
                i++;
                continue;
            }
            if (Character.isDigit(currentChar) || (currentChar == '-' && (i == 0 || !Character.isDigit(expression.charAt(i - 1))))) {
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.' || (expression.charAt(i) == '-' && operand.length() == 0))) {
                    operand.append(expression.charAt(i));
                    i++;
                }
                numbers.push(Double.parseDouble(operand.toString()));
            } else if (currentChar == '(') {
                operators.push(currentChar);
                i++;
            } else if (currentChar == ')') {
                while (operators.peek() != '(') {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop();
                i++;
            } else if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                while (!operators.isEmpty() && hasPrecedence(currentChar, operators.peek())) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(currentChar);
                i++;
            }
        }
        while (!operators.isEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }
        return numbers.pop();
    }

    private static boolean hasPrecedence(char operator1, char operator2) {
        return (operator2 != '(' && operator2 != ')') && (operator1 != '*' && operator1 != '/');
    }
    private static double applyOperation(char operator, double operand2, double operand1) {


        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0)
                    return 0;
                else
                    return operand1 / operand2;
        }
        return 0;
    }

    String getResult(String data){
        return "Calculated";
    }

}