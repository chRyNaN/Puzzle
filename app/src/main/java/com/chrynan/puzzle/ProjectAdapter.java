package com.chrynan.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chrynan.puzzle.interfaces.Callback;
import com.chrynan.puzzle.interfaces.Piece;
import com.chrynan.puzzle.model.Project;
import com.chrynan.puzzle.model.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chRyNaN on 12/19/2015. An adapter used to get and display the available Projects in a ListView.
 */
public class ProjectAdapter extends ArrayAdapter<Project> {
    //For multiple select ListViews
    private final SparseBooleanArray selectedItems;
    private boolean multiselect;

    public ProjectAdapter(Context context){
        super(context, 0);
        this.selectedItems = new SparseBooleanArray();
        this.multiselect = false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder h;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            h = new ViewHolder(convertView);
            convertView.setTag(h);
        }else{
            h = (ViewHolder) convertView.getTag();
            if(h == null){
                h = new ViewHolder(convertView);
            }
        }
        return bindViewHolder(h, getItem(position), position);
    }

    public View bindViewHolder(final ViewHolder h, final Project p, final int position){
        List<Bitmap> pictures = p.getPictures();
        if(pictures != null && pictures.size() > 0){
            h.image.setImageBitmap(pictures.get(0));
        }else{
            h.image.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.project_folder));
        }
        h.primaryText.setText(p.getName());
        h.secondaryText.setText(p.getDescription());
        final View base = h.getBaseView();
        base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(multiselect){
                    if(selectedItems.get(position)){
                        selectedItems.delete(position);
                        viewNotSelected(base);
                    }else {
                        selectedItems.append(position, true);
                        viewSelected(base);
                    }
                }else{
                    selectedItems.clear();
                    if(selectedItems.get(position)){
                        selectedItems.delete(position);
                        viewNotSelected(base);
                    }else {
                        selectedItems.append(position, true);
                        viewSelected(base);
                    }
                }
            }
        });
        if(selectedItems.get(position)){
            viewSelected(base);
        }else{
            viewNotSelected(base);
        }
        return base;
    }

    public void loadProjects(){
        Puzzle.with(getContext()).getAllProjects().then(new Callback() {
            @Override
            public void onError(Error error) {
                Toast.makeText(getContext(), "Error loading projects.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public <T extends Piece> void onSuccess(Result<T> result) {
                List<Project> projects = (List<Project>) result.getResultList();
                if (projects != null && projects.size() > 0) {
                    addAll(projects);
                    notifyDataSetChanged();
                }
            }
        });
    }

    private void viewSelected(View v){
        v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary_light));
    }

    private void viewNotSelected(View v){
        v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    public List<Project> getAllProjects(){
        List<Project> projects = new ArrayList<>();
        for(int i = 0; i < getCount(); i++){
            projects.add(getItem(i));
        }
        return projects;
    }

    public boolean isMultiselect(){
        return multiselect;
    }

    public void setMultiselect(boolean multiselect){
        this.multiselect = multiselect;
    }

    public Project getSelected(){
        List<Project> projects = getSelectedProjects();
        if(projects != null && projects.size() > 0){
            return projects.get(0);
        }
        return null;
    }

    public List<Project> getSelectedProjects(){
        List<Project> selectedProjects = new ArrayList<>();
        for(int i = 0; i < selectedItems.size(); i++){
            if(selectedItems.valueAt(i)){
                selectedProjects.add(getItem(selectedItems.keyAt(i)));
            }
        }
        return selectedProjects;
    }

    public void clearSelected(){
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        private View base;
        ImageView image;
        TextView primaryText;
        TextView secondaryText;

        public ViewHolder(View v){
            base = v;
            image = (ImageView) v.findViewById(R.id.icon);
            primaryText = (TextView) v.findViewById(R.id.primary_text);
            secondaryText = (TextView) v.findViewById(R.id.secondary_text);
        }

        public View getBaseView(){
            return base;
        }

    }

}
