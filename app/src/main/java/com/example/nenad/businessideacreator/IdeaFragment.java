package com.example.nenad.businessideacreator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import es.dmoral.toasty.Toasty;

public class IdeaFragment extends Fragment implements IOnBackPressed{

    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;

    public static final int POTENTIAL_LOW = 0;
    public static final int POTENTIAL_MEDIUM = 1;
    public static final int POTENTIAL_HIGH = 2;

    private static final String ARG_IDEA_INSTANCE = "idea_instance";

     Idea mIdea = new Idea("");

    private IdeaViewModel mIdeaViewModel;

    Button mNewThingButton;
    Button mImproveThingButton;

    Button mEasyButton;
    Button mMediumButton;
    Button mHardButton;

    Button mSaveButton;

    SeekBar mEntrySeekBar;
    SeekBar mControlSeekBar;
    SeekBar mScaleSeekBar;
    SeekBar mTimeSeekBar;
    SeekBar mNeedSeekBar;

    TextView mEntryTextView;
    TextView mControlTextView;
    TextView mScaleTextView;
    TextView mTimeTextView;
    TextView mNeedTextView;

    EditText mTitleEditText;
    EditText mProblemsEditText;

    private void InitIdeaParametars(){
        mIdea = new Idea("");
        mIdea.setEntry(0);
        mIdea.setScale(0);
        mIdea.setControl(0);
        mIdea.setNeed(0);
        mIdea.setTime(0);
        mIdea.setNew(true);
        mIdea.setDifficulty(EASY);
        mIdea.setProblems("");
        mIdea.setEditable(true);
        selectEasy();
        selectNew();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_new_item,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId()){
            case R.id.add_new_idea:
                createNewIdea();
                break;
        }
        return true;
    }

    private void setIdeaData(){
        mTitleEditText.setText(mIdea.getTitle());
        mProblemsEditText.setText(mIdea.getProblems());

        mEntryTextView.setText(Integer.toString(mIdea.getEntry()));
        mControlTextView.setText(Integer.toString(mIdea.getControl()));
        mScaleTextView.setText(Integer.toString(mIdea.getScale()));
        mTimeTextView.setText(Integer.toString(mIdea.getTime()));
        mNeedTextView.setText(Integer.toString(mIdea.getNeed()));

        mNeedSeekBar.setProgress(mIdea.getNeed());
        mControlSeekBar.setProgress(mIdea.getControl());
        mScaleSeekBar.setProgress(mIdea.getScale());
        mTimeSeekBar.setProgress(mIdea.getTime());
        mEntrySeekBar.setProgress(mIdea.getEntry());

       disableSeekBars();
   }

    private void disableEditTexts(){
        mTitleEditText.setEnabled(false);
        mProblemsEditText.setEnabled(false);
    }


    public static IdeaFragment newInstance(Idea idea){
        Bundle args = new Bundle();
        args.putParcelable(ARG_IDEA_INSTANCE,idea);

        IdeaFragment fragment = new IdeaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initializeTextViews(View v){
        mEntryTextView = (TextView) v.findViewById(R.id.entry_value);
        mControlTextView = (TextView) v.findViewById(R.id.control_value);
        mScaleTextView = (TextView) v.findViewById(R.id.scale_value);
        mTimeTextView =  (TextView) v.findViewById(R.id.time_value);
        mNeedTextView = (TextView) v.findViewById(R.id.need_value);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_idea,container,false);

//        Intent intent = getActivity().getIntent();

        Idea idea = getArguments().getParcelable(ARG_IDEA_INSTANCE);
        mIdea = idea;

        initializeTextViews(v);

        mTitleEditText = (EditText) v.findViewById(R.id.title_edit_text);
        mTitleEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    setEditTextsFocusOn();
                return false;
            }
        });

        mProblemsEditText = (EditText) v.findViewById(R.id.problems_edit_text);
        mProblemsEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    setEditTextsFocusOn();
                return false;
            }
        });


        mNewThingButton = (Button) v.findViewById(R.id.new_thing_button);
        mNewThingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(mIdea.getEditable() == true) {
                    selectNew();
                    mIdea.setNew(true);
                }
            }
        });

        mImproveThingButton = (Button) v.findViewById(R.id.improve_thing_button);
        mImproveThingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIdea.getEditable() == true) {
                    selectImprove();
                    mIdea.setNew(false);
                }
            }
        });


        mEasyButton = (Button) v.findViewById(R.id.easy_button);
        mEasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIdea.getEditable() == true) {
                    selectEasy();
                    mIdea.setDifficulty(EASY);
                }
            }
        });

        mMediumButton = (Button) v.findViewById(R.id.medium_button);
        mMediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIdea.getEditable() == true) {
                    selectMedium();
                    mIdea.setDifficulty(MEDIUM);
                }
            }
        });
        mHardButton = (Button) v.findViewById(R.id.hard_button);
        mHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIdea.getEditable() == true) {
                    selectHard();
                    mIdea.setDifficulty(HARD);
                }
            }
        });

        setButtonsPositions();
        mNeedSeekBar = (SeekBar) v.findViewById(R.id.need_seek_bar);
        mNeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setNeedSeekBarText(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setEditTextsFocusOff();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        mControlSeekBar = (SeekBar) v.findViewById(R.id.control_seek_bar);
        mControlSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setControlSeekBarText(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setEditTextsFocusOff();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mScaleSeekBar = (SeekBar) v.findViewById(R.id.scale_seek_bar);
        mScaleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setScaleSeekBarText(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setEditTextsFocusOff();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mTimeSeekBar = (SeekBar) v.findViewById(R.id.time_seek_bar);
        mTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setTimeSeekBarText(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setEditTextsFocusOff();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mEntrySeekBar = (SeekBar) v.findViewById(R.id.entry_seek_bar);
        mEntrySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setEntrySeekBarText(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setEditTextsFocusOff();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mSaveButton = (Button) v.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewIdea();
            }
        });

        if(mIdea == null){
            InitIdeaParametars();
        }

        // If idea is not editable that means we open an existing idea
        if(mIdea.getEditable() == true) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }else{
            setHasOptionsMenu(false);
            mSaveButton.setVisibility(View.INVISIBLE);
            setIdeaData();
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setEditTextsFocusOff();
            }

        return v;
    }

    private void createNewIdea(){
        setIdeaFiveCommandmants();
        setTitleAndProblems();
        setIdeaPotential();

        if(checkIfTitleIsEntered()) {
            disableIdeaEditing();
            finishIdea();
        }else{
            noTitleAlert();
        }
    }

    private void finishIdea(){
        Toasty.success(getContext(),"You created " + mIdea.getTitle()   + "!")
                .show();
        Intent intent = new Intent();
        intent.putExtra(IdeaListFragment.IDEA_INTENT, mIdea);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    private void disableIdeaEditing(){
        mIdea.setEditable(false);
    }

    private boolean checkIfTitleIsEntered(){
        if(mIdea.getTitle().equals("")){
            return false;
        }else{
            return true;
        }
    }

    private void disableSeekBars(){
        mNeedSeekBar.setEnabled(false);
        mEntrySeekBar.setEnabled(false);
        mScaleSeekBar.setEnabled(false);
        mControlSeekBar.setEnabled(false);
        mTimeSeekBar.setEnabled(false);
    }

    @Override
    public boolean onBackPressed() {
        if(mIdea.getEditable() == true){
            quitAlert();
            return true;
        }else{
            return false;
        }
    }


    @Override
    public boolean IsIdeaEditable() {
        if(mIdea.getEditable() == true){
            return true;
        }else{
            return false;
        }
    }

    private void setNeedSeekBarText(int i) {
        if (mIdea.getEditable() == true) {
            mNeedTextView.setText(Integer.toString(i));
        }
    }

    private void setControlSeekBarText(int i){
        if(mIdea.getEditable()) {
            mControlTextView.setText(Integer.toString(i));
        }
    }

    private void setScaleSeekBarText(int i){
        if(mIdea.getEditable()) {
            mScaleTextView.setText(Integer.toString(i));
        }
    }

    private void setTimeSeekBarText(int i){
        if(mIdea.getEditable()) {
            mTimeTextView.setText(Integer.toString(i));
        }
    }

    private void setEntrySeekBarText(int i){
        if(mIdea.getEditable()) {
            mEntryTextView.setText(Integer.toString(i));
        }
    }

    private void quitAlert(){
        AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_Material_Dialog_Alert);
        }else{
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("ATTENTION")
                .setMessage("Are you sure you want to decline this idea?")
                .setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                }).setNegativeButton(R.string.alert_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void noTitleAlert(){
        AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_Material_Dialog_Alert);
        }else{
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("NO TITLE")
                .setMessage("Please enter a title for your idea.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void selectNew(){

           mNewThingButton.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
            mNewThingButton.setTextColor(getResources().getColor(R.color.colorBlack));

            UnselectImprove();
    }

    private void selectImprove(){
            mImproveThingButton.setBackgroundColor(getResources().getColor(R.color.colorRed));
            mImproveThingButton.setTextColor(getResources().getColor(R.color.colorBlack));

            UnselectNew();
    }

    private void UnselectImprove(){
       mImproveThingButton.setBackgroundColor(0);
       mImproveThingButton.setTextColor(getResources().getColor(R.color.colorRed));
    }

    private void UnselectNew(){
        mNewThingButton.setBackgroundColor(0);
        mNewThingButton.setTextColor(getResources().getColor(R.color.colorDarkBlue));
    }

    private void setNewOrImproveButtons(){
        if(mIdea.getNew() == true){
            selectNew();
        }else{
            selectImprove();
        }
    }

    private void selectEasy(){
            mEasyButton.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
            mEasyButton.setTextColor(getResources().getColor(R.color.colorBlack));

            unselectMedium();
            unselectHard();
    }

    private void unSelectEasy(){
            mEasyButton.setBackgroundColor(0);
            mEasyButton.setTextColor(getResources().getColor(R.color.colorDarkBlue));
    }

    private void selectMedium(){
            mMediumButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            mMediumButton.setTextColor(getResources().getColor(R.color.colorBlack));

            unSelectEasy();
            unselectHard();
    }

    private void unselectMedium(){
            mMediumButton.setBackgroundColor(0);
            mMediumButton.setTextColor(getResources().getColor(R.color.colorGreen));
    }

    private void selectHard(){
           mHardButton.setBackgroundColor(getResources().getColor(R.color.colorRed));
           mHardButton.setTextColor(getResources().getColor(R.color.colorBlack));

            unSelectEasy();
            unselectMedium();
    }

    private void unselectHard(){
            mHardButton.setBackgroundColor(0);
            mHardButton.setTextColor(getResources().getColor(R.color.colorRed));
    }

    private void setDifficultyButtons(){
            if (mIdea != null) {
                switch (mIdea.getDifficulty()) {
                    case EASY:
                        selectEasy();
                        break;
                    case MEDIUM:
                        selectMedium();
                        break;
                    case HARD:
                        selectHard();
                        break;
                }
            }
    }

    private void setButtonsPositions(){
        if(mIdea != null){
                setNewOrImproveButtons();
                setDifficultyButtons();
        }
    }

    void setEditTextsFocusOff(){
            mTitleEditText.setFocusable(false);
            mProblemsEditText.setFocusable(false);
    }

    void setEditTextsFocusOn(){
        if(mIdea.getEditable() == false){
            return;
        }

            mTitleEditText.setFocusableInTouchMode(true);
            mTitleEditText.setFocusable(true);
            mProblemsEditText.setFocusableInTouchMode(true);
            mProblemsEditText.setFocusable(true);
    }

    private int StringToInt(String s){
        return Integer.parseInt(s);
    }

    private void setTitleAndProblems(){
        mIdea.setTitle(mTitleEditText.getText().toString());
        mIdea.setProblems(mProblemsEditText.getText().toString());
    }

    private void setIdeaFiveCommandmants() {
        mIdea.setEntry(StringToInt(mEntryTextView.getText().toString()));
        mIdea.setControl(StringToInt(mControlTextView.getText().toString()));
        mIdea.setTime(StringToInt(mTimeTextView.getText().toString()));
        mIdea.setScale(StringToInt(mScaleTextView.getText().toString()));
        mIdea.setNeed(StringToInt(mNeedTextView.getText().toString()));
    }

    private void setIdeaPotential(){
        int potential = 0;
        potential = mIdea.getEntry() + mIdea.getControl() + mIdea.getTime() + mIdea.getScale()
                + mIdea.getNeed();
        if(mIdea.getNew() == true){
            potential += 10;
        }

        if(potential < 17){
            mIdea.setPotential(POTENTIAL_LOW);
        }else if(potential < 40){
            mIdea.setPotential(POTENTIAL_MEDIUM);
        }else{
            mIdea.setPotential(POTENTIAL_HIGH);
        }
    }


}