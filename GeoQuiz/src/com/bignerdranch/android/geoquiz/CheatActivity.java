package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	
	public static final String EXTRA_ANSWER_IS_TRUE =
			"com.bignerdranch.android.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN =
			"com.bignerdranch.android.geoquiz.answer_shown";

	private boolean mAnswerTextIsTrue;
	private TextView mAnswerTextView;
	private TextView mApiVersionView;
	private Button mShowAnswer;

	private void setAnswerShownResult(boolean mAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, mAnswerShown);
		setResult(RESULT_OK,data);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
		mAnswerTextIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		setAnswerShownResult(false);
		mApiVersionView = (TextView)findViewById(R.id.apiVersionBox);
		
		mApiVersionView.append("Api Version "+getText(Build.VERSION.SDK_INT));
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			
		
			@Override
			public void onClick(View v) {
				if(mAnswerTextIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
				setAnswerShownResult(true);
			}
		});
	}
}
