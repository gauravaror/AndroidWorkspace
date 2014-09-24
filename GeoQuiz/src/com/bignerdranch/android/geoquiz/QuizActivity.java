package com.bignerdranch.android.geoquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends Activity {

	private Button mTrueButton;
	private Button mFalseButton;
	private	Button mNextButton;
	private Button mCheatButton;
	private boolean mIsCheater;

	
	private TextView mQuestionTextView;
	
	private	TrueFalse[] mQuestionBank = new TrueFalse[] {
	new
	 TrueFalse(R.string.question_oceans, true),
	new
	 TrueFalse(R.string.question_mideast, false),
	new
	 TrueFalse(R.string.question_africa, false),
	new
	 TrueFalse(R.string.question_americas, true),
	new
	 TrueFalse(R.string.question_asia, true),
	};
	
	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";

	private int mCurrentIndex = 0;
			
	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}

	private void checkAnswers(boolean answer) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int toastString;
		if (mIsCheater) {
			toastString = R.string.judgment_toast;
		} else {

		if(answer == answerIsTrue) {
			toastString = R.string.correct_toast;
		} else {
			toastString = R.string.incorrect_toast;
		}
		}
		Toast.makeText(QuizActivity.this,
    			toastString,
    			Toast.LENGTH_SHORT).show();
	}
	
	@TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	ActionBar actionBar = getActionBar();
        	actionBar.setSubtitle("Bodies of Water");
        	}

        mTrueButton = (Button)findViewById(R.id.true_button);
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        if(savedInstanceState != null) {
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        updateQuestion();

        mTrueButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	checkAnswers(true);

        }
        });
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	checkAnswers(true);
        }
        });
        
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	mIsCheater = false;
        	mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        	updateQuestion();
        }
        });
        
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(QuizActivity.this,CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(i,0);
				
			}
		});


    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (data == null) {
    		return;
    		}
    		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);

    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
    	super.onSaveInstanceState(saveInstanceState);
    	Log.d(TAG,"onSaveInstanceState Called");
    	saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);

    }
    
    @Override
    public void onStart() {
    super.onStart();
    Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
    super.onStop();
    Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
