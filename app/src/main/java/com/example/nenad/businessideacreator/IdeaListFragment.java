package com.example.nenad.businessideacreator;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.nenad.businessideacreator.IdeaFragment.EASY;

public class IdeaListFragment extends Fragment{

    Button mOpenButton;
    Button mDeleteButton;

    private final String SHOWCASE_ID = "showcase_id";

    public static final int NEW_IDEA_REQUEST_CODE = 1;
    public static final String IDEA_INTENT = "com.nenad.idea_intent";

    private IdeaViewModel mIdeaViewModel;

    FloatingActionButton mAddIdeaFloatingActionButton;

    private int mDifficulty = EASY;
    private int mPotential = IdeaFragment.POTENTIAL_LOW;

    TextView mEmptyTextRecyclerViewTextView;
    TextView mEmptyTextBottomBarTextView;

    ConstraintLayout mShowcaseConstrain;


    private List<Idea> mIdeas;

    private void InitializeTextViews(View view){
        mEmptyTextRecyclerViewTextView = view.findViewById(R.id.empty_recycler_view);
        mEmptyTextBottomBarTextView = view.findViewById(R.id.empty_bottom_view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_ideas,container, false);

        InitializeTextViews(v);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        // Creating divider that divides recycler view items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));

        RecyclerView recyclerView = v.findViewById(R.id.items_list_recycler_view);
        final IdeaListAdapter adapter = new IdeaListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        mShowcaseConstrain = (ConstraintLayout) v.findViewById(R.id.showcase_constrain);

        mIdeaViewModel = ViewModelProviders.of(this)
                .get(IdeaViewModel.class);
        mIdeaViewModel.getAllIdeas().observe(this, new Observer<List<Idea>>() {
            @Override
            public void onChanged(@Nullable List<Idea> ideas) {
                adapter.setIdeas(ideas);
                mIdeas = ideas;

                setIsEmptyTextVisibility();
            }
        });


        mAddIdeaFloatingActionButton = (FloatingActionButton) v.findViewById(R.id.add_idea_fab);
        mAddIdeaFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1iew) {
                Intent intent = new Intent(getActivity(),NewIdeaActivity.class);
                startActivityForResult(intent,NEW_IDEA_REQUEST_CODE);
            }
        });


        // Deleting recycler view item on swipe
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                Idea idea = adapter.getIdeatAtPosition(position);
                deleteIdeaAlert(idea,adapter,position);
            }
        });

        helper.attachToRecyclerView(recyclerView);

        // Fill database with sample idea if user opened app for the first time
        checkFirstRun();

        setIsEmptyTextVisibility();

        startTutorial();

        return v;
    }

    private void startTutorial(){
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID); // SHOWCASE_ID - unique id, starts tutorial sequence only when user opens app for the first time

        sequence.setConfig(config);
        sequence.addSequenceItem(mAddIdeaFloatingActionButton,"To create new idea touch this button.", "GOT IT");
        sequence.addSequenceItem(mShowcaseConstrain,"To delete idea swipe on the side.", "GOT IT");
        sequence.start();
    }

    private void deleteIdeaAlert(final Idea idea, final IdeaListAdapter adapter, final int position){
        AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_Material_Dialog_Alert);
        }else{
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("DELETING IDEA")
                .setMessage("Are you sure you want to delete this idea?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toasty.info(getActivity(), idea.getTitle() + " deleted!").show();
                        mIdeaViewModel.delete(idea);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.notifyItemChanged(position);
            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            if(requestCode == NEW_IDEA_REQUEST_CODE){

                Idea idea = new Idea("title");
               idea = data.getParcelableExtra(IDEA_INTENT);

               mDifficulty = idea.getDifficulty();
               mPotential = idea.getPotential();

               mIdeaViewModel.insert(idea);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkFirstRun() {
        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Saved version code
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            // Normal run
            return;
        } else if (savedVersionCode == DOESNT_EXIST) {
            // On first install
            Idea idea = getSampleIdea();
            mIdeaViewModel.insert(idea);

        } else if (currentVersionCode > savedVersionCode) {
            // On app update
        }
        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }


    Idea getSampleIdea(){
        Idea idea = new Idea("Sample Idea");
        idea.setEntry(0);
        idea.setScale(0);
        idea.setControl(0);
        idea.setNeed(0);
        idea.setTime(0);
        idea.setNew(true);
        idea.setDifficulty(EASY);
        idea.setProblems("");
        idea.setEditable(false);
        return idea;
    }


    void setIsEmptyTextVisibility(){
        if(IdeasListIsEmpty()){
            showIsEmptyText();
        }else{
            hideIsEmptyText();
        }
    }

    private boolean IdeasListIsEmpty(){
        if(mIdeas != null) {
            if (mIdeas.size() == 0) {
                return true;
            }
        }
            return false;
    }

    private void hideIsEmptyText(){
        mEmptyTextRecyclerViewTextView.setVisibility(View.INVISIBLE);
        mEmptyTextBottomBarTextView.setVisibility(View.INVISIBLE);
    }

    private void showIsEmptyText(){
        mEmptyTextRecyclerViewTextView.setVisibility(View.VISIBLE);
        mEmptyTextBottomBarTextView.setVisibility(View.VISIBLE);
    }


}