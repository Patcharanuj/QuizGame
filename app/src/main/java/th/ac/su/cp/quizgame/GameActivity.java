package th.ac.su.cp.quizgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import th.ac.su.cp.quizgame.model.WordItem;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mQuestionImageView;
    private Button[] mButtons = new Button[4];

    private String mAnswerWord;
    private Random mRandom;
    int score = 0;
    int i = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final TextView text = (TextView) findViewById(R.id.text_Score);

        mQuestionImageView = findViewById(R.id.question_image_view);
        mButtons[0] = findViewById(R.id.choice_1_button);
        mButtons[1] = findViewById(R.id.choice_2_button);
        mButtons[2] = findViewById(R.id.choice_3_button);
        mButtons[3] = findViewById(R.id.choice_4_button);

        mButtons[0].setOnClickListener(this);
        mButtons[1].setOnClickListener(this);
        mButtons[2].setOnClickListener(this);
        mButtons[3].setOnClickListener(this);

        mRandom = new Random();

        //เเสดงคำถามครั้งเเรก
        newQuiz();
        text.setText(score + " คะเเนน");


    }

    private void newQuiz() {
        List<WordItem> mItemList = new ArrayList<>(Arrays.asList(WordListActivity.items));

        // สุ่ม index ของคำตอบ (คำถาม)
        int answerIndex = mRandom.nextInt(mItemList.size());
        // เข้าถึง WordItem ตาม index ที่สุ่มได้
        WordItem item = mItemList.get(answerIndex);
        // แสดงรูปคำถาม
        mQuestionImageView.setImageResource(item.imageResId);

        mAnswerWord = item.word;

        // สุ่มตำแหน่งปุ่มที่จะแสดงคำตอบ
        int randomButton = mRandom.nextInt(4);
        // แสดงคำศัพท์ที่เป็นคำตอบ
        mButtons[randomButton].setText(item.word);
        // ลบ WordItem ที่เป็นคำตอบออกจาก list
        mItemList.remove(item);

        // เอา list ที่เหลือมา shuffle
        Collections.shuffle(mItemList);

        // เอาคำศัพท์จาก list ที่ตำแหน่ง 0 ถึง 3 มาแสดงที่ปุ่ม 3 ปุ่มที่เหลือ โดยข้ามปุ่มที่เป็นคำตอบ
        for (int i = 0; i < 4; i++) {
            if (i == randomButton) { // ถ้า i คือ index ของปุ่มคำตอบ ให้ข้ามไป
                continue;
            }
            mButtons[i].setText(mItemList.get(i).word);
        }
    }


    @Override
    public void onClick(final View view) {
        Button b = findViewById(view.getId());
        String buttonText = b.getText().toString();
        AlertDialog.Builder dialog = new AlertDialog.Builder(GameActivity.this);
        final TextView text = (TextView) findViewById(R.id.text_Score);


        if (i <= 5) {

            if (buttonText.equals(mAnswerWord)) {
                //Toast.makeText(GameActivity.this, "ถูกต้องค่ะ", Toast.LENGTH_SHORT).show();
                score++;

            } /*else {
                Toast.makeText(GameActivity.this, "ผิดค่ะ", Toast.LENGTH_SHORT).show();
            }*/

            text.setText(score + " คะเเนน");
        }
        if (i == 5) {

            dialog.setTitle("สรุปผล");
            dialog.setMessage("คุณได้ " + score + " คะเเนน\n\nคุณต้องการเล่นใหม่หรือไม่");

            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    newQuiz();
                    score = 0;
                    text.setText(score + " คะเเนน");

                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent n = new Intent(GameActivity.this, MainActivity.class);

                    startActivity(n);

                }
            });

            dialog.show();
            i = 0;


        }
        newQuiz();
        i++;


    }
}