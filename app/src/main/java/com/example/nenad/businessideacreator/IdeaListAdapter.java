package com.example.nenad.businessideacreator;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class IdeaListAdapter extends RecyclerView.Adapter<IdeaListAdapter.IdeaViewHolder>{

    public static final String ARG_IDEA = "com.nenad.arg_idea";



    private Context mContext;

    class IdeaViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private Idea mIdea;
        private final TextView mTitleTextView;
        private final TextView mDifficultyTextView;
        private final TextView mPotentialTextView;
        private Button mViewDetailsButton;

        private IdeaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.item_title);
            mDifficultyTextView = itemView.findViewById(R.id.item_difficulty);
            mPotentialTextView = itemView.findViewById(R.id.item_potential);

            mViewDetailsButton = (Button) itemView.findViewById(R.id.view_details_button);
            mViewDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = NewIdeaActivity.newIntent(mContext,mIdea);
                    mContext.startActivity(intent);
                }
            });
        }
        @Override
        public void onClick(View view) {
        }
    }

    public Idea getIdeatAtPosition(int position){
        return mIdeas.get(position);
    }

    private final LayoutInflater mInflater;
    private List<Idea> mIdeas;

    IdeaListAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public IdeaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.idea_list_item,viewGroup,false);
        return new IdeaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IdeaViewHolder ideaViewHolder, int i) {
        if(mIdeas != null){
            Idea idea = mIdeas.get(i);
            ideaViewHolder.mIdea = idea;
            ideaViewHolder.mTitleTextView.setText(idea.getTitle());

            setItemDifficulty(idea.getDifficulty(),ideaViewHolder);
            setItemPotential(idea.getPotential(),ideaViewHolder);
        }else{
            ideaViewHolder.mTitleTextView.setText("No item!");
        }
    }

    // Setting text and text color for difficulty
    void setItemDifficulty(int difficulty, IdeaViewHolder ideaViewHolder){
        switch (difficulty){
            case IdeaFragment.EASY:
                ideaViewHolder.mDifficultyTextView.setText("Easy");
                ideaViewHolder.mDifficultyTextView.setTextColor(mContext.getResources().getColor(R.color.colorDarkBlue));
                break;
            case IdeaFragment.MEDIUM:
                ideaViewHolder.mDifficultyTextView.setText("Medium");
                ideaViewHolder.mDifficultyTextView.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                break;
            case IdeaFragment.HARD:
                ideaViewHolder.mDifficultyTextView.setText("Hard");
                ideaViewHolder.mDifficultyTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                break;
            default:
                return;

        }
    }

    void setItemPotential(int potential,IdeaViewHolder ideaViewHolder){
        switch (potential){
            case IdeaFragment.POTENTIAL_LOW:
                ideaViewHolder.mPotentialTextView.setText("Low");
                ideaViewHolder.mPotentialTextView.setTextColor(mContext.getResources().getColor(R.color.colorDarkBlue));
                break;
            case IdeaFragment.POTENTIAL_MEDIUM:
                ideaViewHolder.mPotentialTextView.setText("Medium");
                ideaViewHolder.mPotentialTextView.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                break;
            case IdeaFragment.POTENTIAL_HIGH:
                ideaViewHolder.mPotentialTextView.setText("High");
                ideaViewHolder.mPotentialTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                break;
            default:
                return;
        }
    }

    void setIdeas(List<Idea> ideas){
        mIdeas = ideas;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(mIdeas != null){
            return mIdeas.size();
        }else {
            return 0;
        }
    }

}
