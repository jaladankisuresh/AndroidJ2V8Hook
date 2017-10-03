package com.imnotout.androidj2v8hook.ViewAdapters;

import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.imnotout.androidj2v8hook.BR;
import com.imnotout.androidj2v8hook.Models.AppBaseModels;
import com.imnotout.androidj2v8hook.Models.AppModels;
import com.imnotout.androidj2v8hook.R;
import com.imnotout.androidj2v8hook.Utils.RxBus;
import com.imnotout.androidj2v8hook.databinding.ListItemEstablishmentLayoutBinding;

import java.util.List;

public class EstablishmentsArrayAdapter
        extends RecyclerView.Adapter<EstablishmentsArrayAdapter.EstablishmentViewHolder> {

    private List<AppModels.Establishment> collection;

    public EstablishmentsArrayAdapter(List<AppModels.Establishment> collection) {
        this.collection = collection;
    }

    public static EstablishmentsArrayAdapter newInstance(List<AppModels.Establishment> collection) {
        return new EstablishmentsArrayAdapter(collection);
    }

    @Override
    public EstablishmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ListItemEstablishmentLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_establishment_layout, parent, false);
//        ViewStub stub_establishment_child = binding.stubEstablishmentChild.getViewStub();
//        if(viewType == AppBaseModels.EstablishmentType.HOTEL.getValue().hashCode()) {
//            stub_establishment_child.setLayoutResource(R.layout.list_item_hotel_layout);
//        }
//        else if(viewType == AppBaseModels.EstablishmentType.RESTAURANT.getValue().hashCode()) {
//            stub_establishment_child.setLayoutResource(R.layout.list_item_restaurant_layout);
//        }
//        else { //if(viewType == AppModels.EstablishmentType.THEATRE.getValue().hashCode()) {
//            stub_establishment_child.setLayoutResource(R.layout.list_item_theatre_layout);
//        }
//        stub_establishment_child.inflate();
        return new EstablishmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(EstablishmentViewHolder holder, int position) {
        final AppModels.Establishment establishmentItem = collection.get(position);
        holder.bind(establishmentItem);
    }

    @Override
    public int getItemViewType(int position) {
        return collection.get(position).getType().hashCode();
    }

    @Override
    public int getItemCount() {
        return collection == null ? 0 : collection.size();
    }

    public class EstablishmentViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private ListItemEstablishmentLayoutBinding binding;
        private AppModels.Establishment model;

        public EstablishmentViewHolder(ListItemEstablishmentLayoutBinding binding) {
            super(binding.getRoot());

            this.binding= binding;
        }

        public void bind(AppModels.Establishment model) {
            this.model = model;
            binding.setVariable(BR.mHolder, this);
            binding.setVariable(BR.model, model);
            binding.executePendingBindings();

            binding.imgEditOptions.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_edit_options:
                    PopupMenu establishmentOptionsPopup = new PopupMenu(v.getContext(), v);
                    establishmentOptionsPopup.setOnMenuItemClickListener(this);
                    establishmentOptionsPopup.inflate(R.menu.establishment_options_menu);
                    establishmentOptionsPopup.show();
                    break;
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_add_commment:
                    Bundle args = new Bundle();
                    args.putSerializable("model", model);
                    RxBus.publish(RxBus.MessageSubjectType.ESTABLISHMENT_ADD_COMMENT, args);
                    return true;
            }
            return false;
        }
    }
}
