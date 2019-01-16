package com.dudar.gymmvvm.views.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dudar.gymmvvm.R;
import com.dudar.gymmvvm.app.GymConst;
import com.dudar.gymmvvm.model.Equipment;
import com.dudar.gymmvvm.model.ExerciseCategory;
import com.dudar.gymmvvm.model.ExerciseWithImage;
import com.dudar.gymmvvm.model.Muscle;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Map<Integer, String> exerciseCategories = new HashMap<>();
    private Map<Integer, String> exerciseEquipment = new HashMap<>();
    private Map<Integer, String> exerciseMuscles = new HashMap<>();


    private List<ExerciseWithImage> exerciseWithImageList;

    private View.OnClickListener onItemClickListener;

    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;

    public ExerciseAdapter(List<ExerciseWithImage> exercisesWithImages,
                           RecyclerView recyclerView) {

        exerciseWithImageList = exercisesWithImages;

        // load more
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)&&!isLoading) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == GymConst.VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exercise_list_item, parent, false);
            context = parent.getContext();
            return new MemberViewHolder(itemView);
        } else if (viewType == GymConst.VIEW_TYPE_LOADING) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_item, parent, false);
            return new LoadingViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MemberViewHolder) {

            ExerciseWithImage curItem = exerciseWithImageList.get(position);
            MemberViewHolder memberHolder = (MemberViewHolder) holder;

            memberHolder.exerciseName.setText(curItem.getEx().getName());
            memberHolder.exerciseCategory.setText(exerciseCategories.get(curItem.getEx().getCategoryId()));

            memberHolder.exerciseEquipment.setText(getStringOfTitles(curItem.getEx().getEquipment(), exerciseEquipment));
            memberHolder.exerciseMuscles.setText(getStringOfTitles(curItem.getEx().getMuscles(),exerciseMuscles));

            if (!curItem.getImg().isEmpty()){
                String imageUrl = curItem.getImg().get(0).getImage();
                Picasso.get().load(imageUrl)
                        .fit()
                        .into(memberHolder.imgExercise);

            }else {
                memberHolder.imgExercise.setImageDrawable(context.getDrawable(R.drawable.placeholder));
            }

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingHolder = (LoadingViewHolder) holder;
            loadingHolder.progressBar.setIndeterminate(true);
        }
    }

    private String getStringOfTitles(List<Integer> list, Map<Integer, String> titles) {
        StringBuilder temp = new StringBuilder();
        if (!list.isEmpty()) {
            for (int item : list) {
                temp.append(titles.get(item)).append(", ");
            }
            return temp.toString().substring(0,temp.length()-2);
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return exerciseWithImageList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return exerciseWithImageList.get(position) == null ? GymConst.VIEW_TYPE_LOADING : GymConst.VIEW_TYPE_ITEM;
    }

    public void setItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        onLoadMoreListener = loadMoreListener;
    }

    public void updateInfo(List<ExerciseCategory> allCategories, List<Equipment> allEquipment, List<Muscle> allMuscles) {
        for (ExerciseCategory category : allCategories) {
            exerciseCategories.put(category.getId(), category.getName());
        }
        for (Equipment equip : allEquipment) {
            exerciseEquipment.put(equip.getId(), equip.getName());
        }
        for (Muscle muscle : allMuscles) {
            exerciseMuscles.put(muscle.getId(), muscle.getName());
        }
    }

    public void setCategories(List<ExerciseCategory> allCategories){
        for (ExerciseCategory category : allCategories) {
            exerciseCategories.put(category.getId(), category.getName());
        }
    }

    public void setMuscles(List<Muscle> allMuscles){
        for (Muscle muscle : allMuscles) {
            exerciseMuscles.put(muscle.getId(), muscle.getName());
        }
    }

    public void setEquipment(List<Equipment> allEquipment){
        for (Equipment equip : allEquipment) {
            exerciseEquipment.put(equip.getId(), equip.getName());
        }
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;
        TextView exerciseCategory;
        TextView exerciseEquipment;
        TextView exerciseMuscles;
        ImageView imgExercise;

        MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.txtExName);
            exerciseCategory = itemView.findViewById(R.id.txtExCategory);
            exerciseEquipment = itemView.findViewById(R.id.txtDetailEquipment);
            exerciseMuscles = itemView.findViewById(R.id.txtDetailMuscles);
            imgExercise = itemView.findViewById(R.id.imgEx);

            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
