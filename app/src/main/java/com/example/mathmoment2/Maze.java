package com.example.mathmoment2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Maze extends AppCompatActivity {

    TextView[] textView = new TextView[49];
    int[] rectanglesInteger = new int[49];
    double[] rectanglesDoubles = new double[49];
    boolean[] posNeg = new boolean[49];
    boolean[] whereIsFigureOne = new boolean[49];
    double finalValue = 100;
    boolean[] isBlocked = new boolean[49];
    boolean[] answerQ = new boolean[49];
    private final Handler mHandler = new Handler();
    int figure2Loc = 43;
    double toBeUpdated = finalValue;
    boolean textBoxOn = false;
    boolean firstTime = true;
    boolean integerType = true;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.0");
    int diffLevel = 1;

    int diffLevelChange = 0;
    int goalSet = 200;
    int streak = 0;

    boolean[] posNegCopy = new boolean[49];
    boolean[] isBlockedCopy = new boolean[49];

    int CloudLoc = 0;
    boolean startCount = false;
    int count = 0;
    int RangeOfNum = 10;

    boolean fig1fig2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        Intent i = getIntent();
        int rangeOfNum = 0;
        int difficultyLevel;
        boolean typeOfNum = true;
        if (i != null) {
            typeOfNum = i.getBooleanExtra("TypeOfNumber", true);
            rangeOfNum = i.getIntExtra("Range", 10);
            difficultyLevel = i.getIntExtra("DifficultyLevel", 1);
            diffLevel = difficultyLevel;
            goalSet = i.getIntExtra("setGoal", 200);
            RangeOfNum = rangeOfNum;
        }

        for (int x = 0; x < rectanglesInteger.length; x++) {
            int y = (int) (Math.random() * (rangeOfNum - 1));
            rectanglesInteger[x] = y;
        }
        if (typeOfNum) {
            for (int x = 0; x < rectanglesInteger.length; x++) {
                int y = (int) (Math.random() * (rangeOfNum - 1));
                rectanglesInteger[x] = y;
            }
            integerType = true;
        } else {
            for (int x = 0; x < rectanglesDoubles.length; x++) {
                double y = (Math.random() * (rangeOfNum - 1));
                double ySimple = Double.parseDouble(decimalFormat.format(y));
                rectanglesDoubles[x] = ySimple;
            }
            integerType = false;
        }
        for (int z = 0; z < posNeg.length; z++) {
            int w = (int) (Math.random() * 2);
            if (w == 1) {
                posNeg[z] = true;
                posNegCopy[z] = true;
            } else if (w == 2) {
                posNeg[z] = false;
                posNegCopy[z] = false;
            }
        }
        whereIsFigureOne[48] = true;
        mazeCreation(integerType);
        Figure2();
        cloudCatch();
    }

    public void mazeCreation(boolean b) {

        for (int i = 0; i < textView.length; i++) {
            String textViewID = "textView" + i;
            int convertToID = getResources().getIdentifier(textViewID, "id", getPackageName());
            textView[i] = findViewById(convertToID);
            if (textView[i] != null) {
                if (b) {
                    if (rectanglesInteger[i] == 0 && i != 48) {
                        textView[i].setBackgroundColor(getResources().getColor(R.color.black));
                        isBlocked[i] = true;
                        isBlockedCopy[i] = true;
                    } else if (posNeg[i]) {
                        textView[i].setText("+" + rectanglesInteger[i]);
                    } else {
                        textView[i].setText("-" + rectanglesInteger[i]);
                    }
                } else {
                    if ((rectanglesDoubles[i] - 1) % 1 == 0 && i != 48) {
                        textView[i].setBackgroundColor(getResources().getColor(R.color.black));
                        isBlocked[i] = true;
                        isBlockedCopy[i] = true;
                    } else if (posNeg[i]) {
                        textView[i].setText("+" + rectanglesDoubles[i]);
                    } else {
                        textView[i].setText("-" + rectanglesDoubles[i]);
                    }
                }
            }

        }
        int NumOfMines = 0;
        if (diffLevel == 1) {
            NumOfMines = 8;
        } else if (diffLevel == 2) {
            NumOfMines = 12;
        } else if (diffLevel == 3) {
            NumOfMines = 17;
        }

        for (int a = 0; a < NumOfMines; a++) {
            int randomMineLoc = (int) (Math.random() * 49); // correct?
            if (answerQ[randomMineLoc] || isBlocked[randomMineLoc]) {
                while (answerQ[randomMineLoc]) {
                    randomMineLoc = (int) (Math.random() * 49);
                }
            } else {
                answerQ[randomMineLoc] = true;
            }
        }
    }

    public void submissionResult(View view) {
        EditText enterAnswers = findViewById(R.id.UserInput);
        ConstraintLayout grid = findViewById(R.id.Grid);
        TextView askQuestion = findViewById(R.id.AnswerBox);
        try {
            double typedResult = Double.parseDouble(decimalFormat.format(Double.parseDouble(String.valueOf(enterAnswers.getText()))));

            if (typedResult == Double.parseDouble(decimalFormat.format(finalValue))) {
                finalValue += 20;
                if (fig1fig2) {
                    toBeUpdated += 20;
                }
                grid.setBackgroundColor(getResources().getColor(R.color.green));
                streak++;
            } else {
                finalValue -= 20;
                if (fig1fig2) {
                    toBeUpdated -= -20;
                }
                grid.setBackgroundColor(getResources().getColor(R.color.red));
                streak = 0;
            }
            Button submission = findViewById(R.id.submissionButton);
            Button Arrow1 = findViewById(R.id.buttonUp);
            Button Arrow2 = findViewById(R.id.buttonDown);
            Button Arrow3 = findViewById(R.id.buttonRight);
            Button Arrow4 = findViewById(R.id.buttonLeft);
            TextView valueOr = findViewById(R.id.CurrValue);
            valueOr.setText(String.valueOf(finalValue));
            Arrow1.setVisibility(View.VISIBLE);
            Arrow2.setVisibility(View.VISIBLE);
            Arrow3.setVisibility(View.VISIBLE);
            Arrow4.setVisibility(View.VISIBLE);
            valueOr.setVisibility(View.VISIBLE);
            askQuestion.setVisibility(View.INVISIBLE);
            enterAnswers.setText("");
            enterAnswers.setVisibility(View.INVISIBLE);
            submission.setVisibility(View.INVISIBLE);

            textBoxOn = false;
            if (fig1fig2) {
                finalValue = Double.parseDouble(decimalFormat.format(toBeUpdated));
                fig1fig2 = false;
            }
            valueOr.setText(String.valueOf(finalValue));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_LONG).show();
        }
        InputMethodManager keyboard = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (finalValue >= goalSet) {
            Intent r = new Intent(this, WinningPage.class);
            r.putExtra("Result", true);
            r.putExtra("DifficultyLevel", diffLevel);
            r.putExtra("Range", RangeOfNum);
            r.putExtra("setGoal", goalSet);
            r.putExtra("TypeOfNumber", integerType);
            startActivity(r);
        }
        if (finalValue < 0) {
            Intent u = new Intent(this, WinningPage.class);
            u.putExtra("Result", false);
            u.putExtra("DifficultyLevel", diffLevel);
            u.putExtra("Range", RangeOfNum);
            u.putExtra("setGoal", goalSet);
            u.putExtra("TypeOfNumber", integerType);
            startActivity(u);
        }
        if (streak == 5) {
            finalValue += 50;
            Toast toast = Toast.makeText(this, "plus 50!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
        if (streak == 3) {
            diffLevelChange = 5;
            Toast toast = Toast.makeText(this, "The red bot has been stopped. You have 7 seconds...", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
           toast.show();
        }
        if (streak == 7) {
            Arrays.fill(posNeg, true);
            for (int i = 0; i < 48; i++) {
                if (textView[i] != null && i != 0 && integerType && !isBlocked[i]) {
                    textView[i].setText("+" + rectanglesInteger[i]);
                }
                else if (textView[i] != null && i != 0 && !integerType && !isBlocked[i]) {
                    textView[i].setText("+" + rectanglesDoubles[i]);
                }
            }
            Toast toast = Toast.makeText(this, "all blocks are positive! Go get them!!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(allPos(), 7, TimeUnit.SECONDS);
        }
    }

    public Runnable allPos() {

        return () -> {
            System.arraycopy(posNegCopy, 0, posNeg, 0, 48);
            for (int i = 0; i < 48; i++) {
                if (posNeg[i]) {
                    if (textView[i] != null && i != 0 && integerType && !isBlocked[i]) {
                        textView[i].setText("+" + rectanglesInteger[i]);
                    }
                    else if (textView[i] != null && i != 0 && !integerType && !isBlocked[i]) {
                        textView[i].setText("+" + rectanglesDoubles[i]);
                    }
                } else {
                    if (textView[i] != null && i != 0 && integerType && !isBlocked[i]) {
                        textView[i].setText("-" + rectanglesInteger[i]);
                    }
                    else if (textView[i] != null && i != 0 && !integerType && !isBlocked[i]) {
                        textView[i].setText("-" + rectanglesDoubles[i]);
                    }
                }
            }
        };
    }

    public void cloudCatch() {
        clouds.run();
    }
    private final Runnable clouds = new Runnable() {
        @Override
        public void run() {
            ImageView cloud = findViewById(R.id.cloud);
            int coorCloud = (int) (Math.random() * 49);
            if (textView[coorCloud] != null && !isBlocked[coorCloud]) {
                cloud.setY(textView[coorCloud].getY());
                cloud.setX(textView[coorCloud].getX());
            }
            CloudLoc = coorCloud;

            int figure1Loc = 0;
            for (int i = 1; i < 49; i++) {
                if (whereIsFigureOne[i]) {
                    figure1Loc = i;
                    break;
                }
            }
            if (textView[figure1Loc] == textView[CloudLoc]) {
                cloud.setVisibility(View.INVISIBLE);
                for (int i = 0; i < 49; i++) {
                    if (isBlocked[i]) {
                        textView[i].setBackgroundColor(getResources().getColor(R.color.Grey));
                    }
                    isBlocked[i] = false;
                }
                startCount = true;
            }

            if (startCount) {
                count++;
            }
            if (count == 3) {
                cloud.setVisibility(View.VISIBLE);
                for (int i = 0; i < 49; i++) {
                    isBlocked[i] = isBlockedCopy[i];
                    if (isBlocked[i]) {
                        textView[i].setBackgroundColor(getResources().getColor(R.color.black));
                    }
                }
                count = 0;
                startCount = false;
            }
            mHandler.postDelayed(this, 3000);
        }
    };



    public void directionUp(View view) {
        ConstraintLayout grid = findViewById(R.id.Grid);
        grid.setBackgroundColor(getResources().getColor(R.color.originalbackgroundC));
        ImageView figure1 = findViewById(R.id.Figure1);
        TextView value = findViewById(R.id.CurrValue);
        int z;
        for (int i = 0; i < textView.length; i++) {
            if (whereIsFigureOne[i]) {
                z = i - 6;
                if (z <= 0) {
                    z = i + 42;
                }
                if (!isBlocked[z]) {
                    whereIsFigureOne[i] = false;
                    whereIsFigureOne[z] = true;
                    if (answerQ[z] && posNeg[z]) {
                        TextView askQuestion = findViewById(R.id.AnswerBox);
                        EditText enterAnswers = findViewById(R.id.UserInput);
                        Button submission = findViewById(R.id.submissionButton);
                        Button Arrow1 = findViewById(R.id.buttonUp);
                        Button Arrow2 = findViewById(R.id.buttonDown);
                        Button Arrow3 = findViewById(R.id.buttonRight);
                        Button Arrow4 = findViewById(R.id.buttonLeft);
                        Arrow1.setVisibility(View.INVISIBLE);
                        Arrow2.setVisibility(View.INVISIBLE);
                        Arrow3.setVisibility(View.INVISIBLE);
                        Arrow4.setVisibility(View.INVISIBLE);
                        value.setVisibility(View.INVISIBLE);
                        askQuestion.setVisibility(View.VISIBLE);
                        enterAnswers.setVisibility(View.VISIBLE);
                        submission.setVisibility(View.VISIBLE);
                        if (integerType) {
                            askQuestion.setText(finalValue + " + " + rectanglesInteger[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue + rectanglesInteger[z];
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                        else {
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            askQuestion.setText(finalPrint + " + " + rectanglesDoubles[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue + rectanglesDoubles[z];
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                    } else if (answerQ[z] && !posNeg[z]) {
                        TextView askQuestion = findViewById(R.id.AnswerBox);
                        EditText enterAnswers = findViewById(R.id.UserInput);
                        Button submission = findViewById(R.id.submissionButton);
                        Button Arrow1 = findViewById(R.id.buttonUp);
                        Button Arrow2 = findViewById(R.id.buttonDown);
                        Button Arrow3 = findViewById(R.id.buttonRight);
                        Button Arrow4 = findViewById(R.id.buttonLeft);
                        Arrow1.setVisibility(View.INVISIBLE);
                        Arrow2.setVisibility(View.INVISIBLE);
                        Arrow3.setVisibility(View.INVISIBLE);
                        Arrow4.setVisibility(View.INVISIBLE);
                        value.setVisibility(View.INVISIBLE);
                        askQuestion.setVisibility(View.VISIBLE);
                        enterAnswers.setVisibility(View.VISIBLE);
                        submission.setVisibility(View.VISIBLE);
                        if (integerType) {
                            askQuestion.setText(finalValue + " - " + rectanglesInteger[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue - rectanglesInteger[z];
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                        else {
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            askQuestion.setText(finalPrint + " - " + rectanglesDoubles[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue - rectanglesDoubles[z];
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                    } else if (posNeg[z] && !answerQ[z]) {
                        if (integerType) {
                            finalValue = finalValue + rectanglesInteger[z];
                            value.setText(String.valueOf(finalValue));
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                        else {
                            finalValue = finalValue + rectanglesDoubles[z];
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            value.setText(String.valueOf(finalPrint));
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                    } else if (!answerQ[z] && !posNeg[z]) {
                        if (integerType) {
                            finalValue = finalValue - rectanglesInteger[z];
                            value.setText(String.valueOf(finalValue));
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                        else {
                            finalValue = finalValue - rectanglesDoubles[z];
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            value.setText(String.valueOf(finalPrint));
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                    }
                    break;
                }
            }
        }
        if (finalValue >= goalSet) {
            Intent r = new Intent(this, WinningPage.class);
            r.putExtra("Result", true);
            r.putExtra("DifficultyLevel", diffLevel);
            r.putExtra("Range", RangeOfNum);
            r.putExtra("setGoal", goalSet);
            r.putExtra("TypeOfNumber", integerType);
            startActivity(r);
        }
        if (finalValue < 0) {
            Intent u = new Intent(this, WinningPage.class);
            u.putExtra("Result", false);
            u.putExtra("DifficultyLevel", diffLevel);
            u.putExtra("Range", RangeOfNum);
            u.putExtra("setGoal", goalSet);
            u.putExtra("TypeOfNumber", integerType);
            startActivity(u);
        }

        int l = 0;
        for (int j = 0; j < 49; j++) {
            if (whereIsFigureOne[j]) {
                l = j;
            }
        }
        ImageView cloud = findViewById(R.id.cloud);
        if (textView[l] == textView[CloudLoc]) {
            cloud.setVisibility(View.INVISIBLE);
            for (int i = 0; i < 49; i++) {
                if (isBlocked[i]) {
                    textView[i].setBackgroundColor(getResources().getColor(R.color.Grey));
                }
                isBlocked[i] = false;
            }
            startCount = true;
        }
    }

    public void directionDown(View view) {
        ConstraintLayout grid = findViewById(R.id.Grid);
        grid.setBackgroundColor(getResources().getColor(R.color.originalbackgroundC));
        ImageView figure1 = findViewById(R.id.Figure1);
        TextView value = findViewById(R.id.CurrValue);
        int z;
        for (int i = 0; i < textView.length; i++) {
            if (whereIsFigureOne[i]) {
                z = i + 6;
                if (z > 48) {
                    z = i - 42;
                }
                if (!isBlocked[z]) {
                    whereIsFigureOne[i] = false;
                    whereIsFigureOne[z] = true;
                    if (answerQ[z] && posNeg[z]) {
                        TextView askQuestion = findViewById(R.id.AnswerBox);
                        EditText enterAnswers = findViewById(R.id.UserInput);
                        Button submission = findViewById(R.id.submissionButton);
                        Button Arrow1 = findViewById(R.id.buttonUp);
                        Button Arrow2 = findViewById(R.id.buttonDown);
                        Button Arrow3 = findViewById(R.id.buttonRight);
                        Button Arrow4 = findViewById(R.id.buttonLeft);
                        Arrow1.setVisibility(View.INVISIBLE);
                        Arrow2.setVisibility(View.INVISIBLE);
                        Arrow3.setVisibility(View.INVISIBLE);
                        Arrow4.setVisibility(View.INVISIBLE);
                        value.setVisibility(View.INVISIBLE);
                        askQuestion.setVisibility(View.VISIBLE);
                        enterAnswers.setVisibility(View.VISIBLE);
                        submission.setVisibility(View.VISIBLE);
                        if (integerType) {
                            askQuestion.setText(finalValue + " + " + rectanglesInteger[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue + rectanglesInteger[z];
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                        else {
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            askQuestion.setText(finalPrint + " + " + rectanglesDoubles[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue + rectanglesDoubles[z];
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                    } else if (answerQ[z] && !posNeg[z]) {
                        TextView askQuestion = findViewById(R.id.AnswerBox);
                        EditText enterAnswers = findViewById(R.id.UserInput);
                        Button submission = findViewById(R.id.submissionButton);
                        Button Arrow1 = findViewById(R.id.buttonUp);
                        Button Arrow2 = findViewById(R.id.buttonDown);
                        Button Arrow3 = findViewById(R.id.buttonRight);
                        Button Arrow4 = findViewById(R.id.buttonLeft);
                        Arrow1.setVisibility(View.INVISIBLE);
                        Arrow2.setVisibility(View.INVISIBLE);
                        Arrow3.setVisibility(View.INVISIBLE);
                        Arrow4.setVisibility(View.INVISIBLE);
                        value.setVisibility(View.INVISIBLE);
                        askQuestion.setVisibility(View.VISIBLE);
                        enterAnswers.setVisibility(View.VISIBLE);
                        submission.setVisibility(View.VISIBLE);
                        if (integerType) {
                            askQuestion.setText(finalValue + " - " + rectanglesInteger[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue - rectanglesInteger[z];
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                        else {
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            askQuestion.setText(finalPrint + " - " + rectanglesDoubles[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue - rectanglesDoubles[z];
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                    } else if (posNeg[z]) {
                        if (integerType) {
                            finalValue = finalValue + rectanglesInteger[z];
                            value.setText(String.valueOf(finalValue));
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                        else {
                            finalValue = finalValue + rectanglesDoubles[z];
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            value.setText(String.valueOf(finalPrint));
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                    } else {
                        if (integerType) {
                            finalValue = finalValue - rectanglesInteger[z];
                            value.setText(String.valueOf(finalValue));
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                        else {
                            finalValue = finalValue - rectanglesDoubles[z];
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            value.setText(String.valueOf(finalPrint));
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                    }
                    break;
                }
            }
        }
        if (finalValue >= goalSet) {
            Intent r = new Intent(this, WinningPage.class);
            r.putExtra("Result", true);
            r.putExtra("DifficultyLevel", diffLevel);
            r.putExtra("Range", RangeOfNum);
            r.putExtra("setGoal", goalSet);
            r.putExtra("TypeOfNumber", integerType);
            startActivity(r);
        }
        if (finalValue < 0) {
            Intent u = new Intent(this, WinningPage.class);
            u.putExtra("Result", false);
            u.putExtra("DifficultyLevel", diffLevel);
            u.putExtra("Range", RangeOfNum);
            u.putExtra("setGoal", goalSet);
            u.putExtra("TypeOfNumber", integerType);
            startActivity(u);
        }
        int l = 0;
        for (int j = 0; j < 49; j++) {
            if (whereIsFigureOne[j]) {
                l = j;
            }
        }
        ImageView cloud = findViewById(R.id.cloud);
        if (textView[l] == textView[CloudLoc]) {
            cloud.setVisibility(View.INVISIBLE);
            for (int i = 0; i < 49; i++) {
                if (isBlocked[i]) {
                    textView[i].setBackgroundColor(getResources().getColor(R.color.Grey));
                }
                isBlocked[i] = false;
            }
            startCount = true;
        }
    }

    public void directionRight(View view) {
        ConstraintLayout grid = findViewById(R.id.Grid);
        grid.setBackgroundColor(getResources().getColor(R.color.originalbackgroundC));
        ImageView figure1 = findViewById(R.id.Figure1);
        TextView value = findViewById(R.id.CurrValue);
        int z;
        for (int i = 0; i < textView.length; i++) {
            if (whereIsFigureOne[i]) {
                z = i + 1;
                if (z % 6 == 1) {
                    z = i - 5;
                }
                if (!isBlocked[z]) {
                    whereIsFigureOne[i] = false;
                    whereIsFigureOne[z] = true;
                    if (answerQ[z] && posNeg[z]) {
                        TextView askQuestion = findViewById(R.id.AnswerBox);
                        EditText enterAnswers = findViewById(R.id.UserInput);
                        Button submission = findViewById(R.id.submissionButton);
                        Button Arrow1 = findViewById(R.id.buttonUp);
                        Button Arrow2 = findViewById(R.id.buttonDown);
                        Button Arrow3 = findViewById(R.id.buttonRight);
                        Button Arrow4 = findViewById(R.id.buttonLeft);
                        Arrow1.setVisibility(View.INVISIBLE);
                        Arrow2.setVisibility(View.INVISIBLE);
                        Arrow3.setVisibility(View.INVISIBLE);
                        Arrow4.setVisibility(View.INVISIBLE);
                        value.setVisibility(View.INVISIBLE);
                        askQuestion.setVisibility(View.VISIBLE);
                        enterAnswers.setVisibility(View.VISIBLE);
                        submission.setVisibility(View.VISIBLE);
                        if (integerType) {
                            askQuestion.setText(finalValue + " + " + rectanglesInteger[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue + rectanglesInteger[z];
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                        else {
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            askQuestion.setText(finalPrint + " + " + rectanglesDoubles[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue + rectanglesDoubles[z];
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                    } else if (answerQ[z] && !posNeg[z]) {
                        TextView askQuestion = findViewById(R.id.AnswerBox);
                        EditText enterAnswers = findViewById(R.id.UserInput);
                        Button submission = findViewById(R.id.submissionButton);
                        Button Arrow1 = findViewById(R.id.buttonUp);
                        Button Arrow2 = findViewById(R.id.buttonDown);
                        Button Arrow3 = findViewById(R.id.buttonRight);
                        Button Arrow4 = findViewById(R.id.buttonLeft);
                        Arrow1.setVisibility(View.INVISIBLE);
                        Arrow2.setVisibility(View.INVISIBLE);
                        Arrow3.setVisibility(View.INVISIBLE);
                        Arrow4.setVisibility(View.INVISIBLE);
                        value.setVisibility(View.INVISIBLE);
                        askQuestion.setVisibility(View.VISIBLE);
                        enterAnswers.setVisibility(View.VISIBLE);
                        submission.setVisibility(View.VISIBLE);
                        if (integerType) {
                            askQuestion.setText(finalValue + " - " + rectanglesInteger[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue - rectanglesInteger[z];
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                        else {
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            askQuestion.setText(finalPrint + " - " + rectanglesDoubles[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue - rectanglesDoubles[z];
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                    } else if (posNeg[z]) {
                        if (integerType) {
                            finalValue = finalValue + rectanglesInteger[z];
                            value.setText(String.valueOf(finalValue));
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                        else {
                            finalValue = finalValue + rectanglesDoubles[z];
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            value.setText(String.valueOf(finalPrint));
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                    } else {
                        if (integerType) {
                            finalValue = finalValue - rectanglesInteger[z];
                            value.setText(String.valueOf(finalValue));
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                        else {
                            finalValue = finalValue - rectanglesDoubles[z];
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            value.setText(String.valueOf(finalPrint));
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                            float yCoordinate = textView[z].getY();
                            figure1.setY(yCoordinate);
                        }
                    }
                    break;
                }
            }
        }
        if (finalValue >= goalSet) {
            Intent r = new Intent(this, WinningPage.class);
            r.putExtra("Result", true);
            r.putExtra("DifficultyLevel", diffLevel);
            r.putExtra("Range", RangeOfNum);
            r.putExtra("setGoal", goalSet);
            r.putExtra("TypeOfNumber", integerType);
            startActivity(r);
        }
        if (finalValue < 0) {
            Intent u = new Intent(this, WinningPage.class);
            u.putExtra("Result", false);
            u.putExtra("DifficultyLevel", diffLevel);
            u.putExtra("Range", RangeOfNum);
            u.putExtra("setGoal", goalSet);
            u.putExtra("TypeOfNumber", integerType);
            startActivity(u);
        }
        int l = 0;
        for (int j = 0; j < 49; j++) {
            if (whereIsFigureOne[j]) {
                l = j;
            }
        }
        ImageView cloud = findViewById(R.id.cloud);
        if (textView[l] == textView[CloudLoc]) {
            cloud.setVisibility(View.INVISIBLE);
            for (int i = 0; i < 49; i++) {
                if (isBlocked[i]) {
                    textView[i].setBackgroundColor(getResources().getColor(R.color.Grey));
                }
                isBlocked[i] = false;
            }
            startCount = true;
        }
    }

    public void directionLeft(View view) {
        ConstraintLayout grid = findViewById(R.id.Grid);
        grid.setBackgroundColor(getResources().getColor(R.color.originalbackgroundC));
        ImageView figure1 = findViewById(R.id.Figure1);
        TextView value = findViewById(R.id.CurrValue);
        int z;
        for (int i = 0; i < textView.length; i++) {
            if (whereIsFigureOne[i] && !isBlocked[i - 1]) {
                z = i - 1;
                if (z % 6 == 0) {
                    z = i + 5;
                }
                if (!isBlocked[z]) {
                    whereIsFigureOne[i] = false;
                    whereIsFigureOne[z] = true;
                    if (answerQ[z] && posNeg[z]) {
                        TextView askQuestion = findViewById(R.id.AnswerBox);
                        EditText enterAnswers = findViewById(R.id.UserInput);
                        Button submission = findViewById(R.id.submissionButton);
                        Button Arrow1 = findViewById(R.id.buttonUp);
                        Button Arrow2 = findViewById(R.id.buttonDown);
                        Button Arrow3 = findViewById(R.id.buttonRight);
                        Button Arrow4 = findViewById(R.id.buttonLeft);
                        Arrow1.setVisibility(View.INVISIBLE);
                        Arrow2.setVisibility(View.INVISIBLE);
                        Arrow3.setVisibility(View.INVISIBLE);
                        Arrow4.setVisibility(View.INVISIBLE);
                        value.setVisibility(View.INVISIBLE);
                        askQuestion.setVisibility(View.VISIBLE);
                        enterAnswers.setVisibility(View.VISIBLE);
                        submission.setVisibility(View.VISIBLE);
                        if (integerType) {
                            askQuestion.setText(finalValue + " + " + rectanglesInteger[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue + rectanglesInteger[z];
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                        else {
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            askQuestion.setText(finalPrint + " + " + rectanglesDoubles[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue + rectanglesDoubles[z];
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                    } else if (answerQ[z] && !posNeg[z]) {
                        TextView askQuestion = findViewById(R.id.AnswerBox);
                        EditText enterAnswers = findViewById(R.id.UserInput);
                        Button submission = findViewById(R.id.submissionButton);
                        Button Arrow1 = findViewById(R.id.buttonUp);
                        Button Arrow2 = findViewById(R.id.buttonDown);
                        Button Arrow3 = findViewById(R.id.buttonRight);
                        Button Arrow4 = findViewById(R.id.buttonLeft);
                        Arrow1.setVisibility(View.INVISIBLE);
                        Arrow2.setVisibility(View.INVISIBLE);
                        Arrow3.setVisibility(View.INVISIBLE);
                        Arrow4.setVisibility(View.INVISIBLE);
                        value.setVisibility(View.INVISIBLE);
                        askQuestion.setVisibility(View.VISIBLE);
                        enterAnswers.setVisibility(View.VISIBLE);
                        submission.setVisibility(View.VISIBLE);
                        if (integerType) {
                            askQuestion.setText(finalValue + " - " + rectanglesInteger[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue - rectanglesInteger[z];
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                        else {
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            askQuestion.setText(finalPrint + " - " + rectanglesDoubles[z] + " = ");
                            textBoxOn = true;
                            finalValue = finalValue - rectanglesDoubles[z];
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                    } else if (posNeg[z]) {
                        if (integerType) {
                            finalValue = finalValue + rectanglesInteger[z];
                            value.setText(String.valueOf(finalValue));
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                        else {
                            finalValue = finalValue + rectanglesDoubles[z];
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            value.setText(String.valueOf(finalPrint));
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                    } else {
                        if (integerType) {
                            finalValue = finalValue - rectanglesInteger[z];
                            value.setText(String.valueOf(finalValue));
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                        else {
                            finalValue = finalValue - rectanglesDoubles[z];
                            double finalPrint = Double.parseDouble(decimalFormat.format(finalValue));
                            value.setText(String.valueOf(finalPrint));
                            float xCoordinate = textView[z].getX();
                            figure1.setX(xCoordinate);
                        }
                    }
                    break;
                }
            }
        }
        if (finalValue >= goalSet) {
            Intent r = new Intent(this, WinningPage.class);
            r.putExtra("Result", true);
            r.putExtra("DifficultyLevel", diffLevel);
            r.putExtra("Range", RangeOfNum);
            r.putExtra("setGoal", goalSet);
            r.putExtra("TypeOfNumber", integerType);
            startActivity(r);
        }
        if (finalValue < 0) {
            Intent u = new Intent(this, WinningPage.class);
            u.putExtra("Result", false);
            u.putExtra("DifficultyLevel", diffLevel);
            u.putExtra("Range", RangeOfNum);
            u.putExtra("setGoal", goalSet);
            u.putExtra("TypeOfNumber", integerType);
            startActivity(u);
        }
        int l = 0;
        for (int j = 0; j < 49; j++) {
            if (whereIsFigureOne[j]) {
                l = j;
            }
        }
        ImageView cloud = findViewById(R.id.cloud);
        if (textView[l] == textView[CloudLoc]) {
            cloud.setVisibility(View.INVISIBLE);
            for (int i = 0; i < 49; i++) {
                if (isBlocked[i]) {
                    textView[i].setBackgroundColor(getResources().getColor(R.color.Grey));
                }
                isBlocked[i] = false;
            }
            startCount = true;
        }
    }
    public void Figure2() {
        fig2.run();
        if (finalValue < 0) {
            Intent u = new Intent(this, WinningPage.class);
            u.putExtra("Result", false);
            u.putExtra("DifficultyLevel", diffLevel);
            u.putExtra("Range", RangeOfNum);
            u.putExtra("setGoal", goalSet);
            u.putExtra("TypeOfNumber", integerType);
            startActivity(u);
        }
    }

    private final Runnable fig2 = new Runnable() {
        @Override
        public void run() {
            ImageView figure2 = findViewById(R.id.Figure2);
            TextView value = findViewById(R.id.CurrValue);

            int figure1Loc = 0;
            for (int i = 1; i < 49; i++) {
                if (whereIsFigureOne[i]) {
                    figure1Loc = i;
                }
            }

            if ((figure2Loc%6 < figure1Loc%6 && figure2Loc%6!= 0) || (figure1Loc%6 == 0 && figure2Loc % 6 != figure1Loc && Math.abs(figure1Loc-figure2Loc) <= 5 && figure1Loc-figure2Loc != 0)) {
                if ((figure2Loc+1) % 6 != 1 && !firstTime) {
                    float xCoordinate = textView[figure2Loc + 1].getX();
                    figure2.setX(xCoordinate);
                    figure2Loc = figure2Loc+1;
                }
                firstTime = false;
            }
            else if (((figure2Loc/6)*6 +1 > (figure1Loc/6)*6 + 1) && figure2Loc-figure1Loc > 5) {
                if (figure2Loc -6 > 0) {
                    float yCoordinate = textView[figure2Loc-6].getY();
                    figure2.setY(yCoordinate);
                    figure2Loc = figure2Loc-6;
               }
            }
            else if ((figure2Loc/6)*6 + 1 < (figure1Loc/6)*6 + 1) {
                if (figure2Loc + 6 <= 48) {
                    float yCoordinate = textView[figure2Loc + 6].getY();
                    figure2.setY(yCoordinate);
                    figure2Loc = figure2Loc+6;
                }
            }
             else if ((figure2Loc%6 > figure1Loc%6) || (figure2Loc%6==0 && figure2Loc!=figure1Loc)) {
                if ((figure2Loc-1) % 6 >=1) {
                    float xCoordinate = textView[figure2Loc - 1].getX();
                    figure2.setX(xCoordinate);
                    figure2Loc = figure2Loc-1;
              }
            }
            else if (figure1Loc == figure2Loc) {
                System.out.println(figure1Loc + "+" + figure2Loc);
                if (!textBoxOn) {
                    finalValue = finalValue - 30;
                    value.setText(String.valueOf(finalValue));
                }
                else {
                    toBeUpdated = Double.parseDouble(decimalFormat.format(finalValue - 30));
                    fig1fig2 = true;
                    value.setText(String.valueOf(toBeUpdated));
                }
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    figure2.setY(textView[43].getY());
                    figure2.setX(textView[43].getX());
                    figure2Loc = 43;
            }
            if (diffLevelChange == 5) {
                mHandler.postDelayed(this, 7000);
                diffLevelChange = 0;
            }
            else if (diffLevel == 1) {
                mHandler.postDelayed(this, 1350);
            }
            else if (diffLevel == 2) {
                mHandler.postDelayed(this, 1000);
            }
            else if (diffLevel == 3){
                mHandler.postDelayed(this, 900);
            }
        }
    };
}