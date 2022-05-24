package com.example.restapi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restapi.api.ApiConfig;
import com.example.restapi.model.DeleteMahasiswaResponse;
import com.example.restapi.model.Mahasiswa;
import com.example.restapi.model.MahasiswaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.ArrayList;
import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {

    private ArrayList<Mahasiswa> localDataSet = new ArrayList<>();

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final Button delete;
        private final Button update;
        private final TextView email;
        private final TextView jurusan;
        private final TextView nrp;
        private Context context;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            context = view.getContext();

            textView = (TextView) view.findViewById(R.id.card_title);
            email = (TextView) view.findViewById(R.id.email);
            jurusan = (TextView) view.findViewById(R.id.jurusan);
            nrp = (TextView) view.findViewById(R.id.nrp);
            delete = (Button) view.findViewById(R.id.delete_button);
            update = (Button) view.findViewById(R.id.update_button);
        }


        public Button getDelete() {
            return delete;
        }

        public Button getUpdate() {
            return update;
        }

        public TextView getEmail() {
            return email;
        }

        public TextView getJurusan() {
            return jurusan;
        }

        public TextView getNrp() {
            return nrp;
        }
        public TextView getCardTitle() {
            return textView;
        }

    }

    public MahasiswaAdapter() {
        getData();
    }

    public void setItems(ArrayList<Mahasiswa> dataSet){
        localDataSet = dataSet;
        this.notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        TextView cardTitle = viewHolder.getCardTitle();
        TextView cardJurusan = viewHolder.getJurusan();
        TextView cardEmail = viewHolder.getEmail();
        TextView cardNrp = viewHolder.getNrp();
        TextView delete = viewHolder.getDelete();
        TextView update = viewHolder.getUpdate();
        Mahasiswa data = localDataSet.get(position);
        cardTitle.setText(data.getNama());
        cardJurusan.setText(data.getJurusan());
        cardEmail.setText(data.getEmail());
        cardNrp.setText(data.getNrp());
        delete.setOnClickListener(v -> {
            deleteMahasiswa(data.getId());
        });
        update.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), UpdateMahasiswaActivity.class);
            intent.putExtra("id", data.getId());
            intent.putExtra("nrp", data.getNrp());
            v.getContext().startActivity(intent);
        });
    }

    private void deleteMahasiswa(String id){
        Call<DeleteMahasiswaResponse> client =
                ApiConfig.getApiService().deleteMahasiswa(id);
        client.enqueue(new
                               Callback<DeleteMahasiswaResponse>() {
                                   @Override
                                   public void
                                   onResponse(Call<DeleteMahasiswaResponse> call,
                                              Response<DeleteMahasiswaResponse> response) {
                                       Log.d("test", "reposen");
                                       if (response.isSuccessful()){
                                           if (response.body() != null){
                                               Log.d("success", response.body().getId());
                                               getData();
                                           }
                                       } else {
                                           if (response.body() != null)
                                           {
                                               Log.e("fail", "onFailure: " +
                                                       response.message());
                                           }
                                       }
                                   }
                                   @Override
                                   public void

                                   onFailure(Call<DeleteMahasiswaResponse> call, Throwable t) {
                                       Log.e("Error Retrofit",

                                               "onFailure: " + t.getMessage());
                                   }
                               });
    }

    private void getData(){
        Call<MahasiswaResponse> client =
                ApiConfig.getApiService().getMahasiswa();
        client.enqueue(new
                               Callback<MahasiswaResponse>() {
                                   @Override
                                   public void
                                   onResponse(Call<MahasiswaResponse> call,
                                              Response<MahasiswaResponse> response) {
                                       if (response.isSuccessful()){
                                           if (response.body() != null){
                                               List<Mahasiswa> mahasiswaList = response.body().getData();
                                               setItems(new ArrayList<>(mahasiswaList));
                                           }
                                       } else {
                                           if (response.body() != null)
                                           {
                                               Log.e("", "onFailure: " +
                                                       response.message());
                                           }
                                       }
                                   }
                                   @Override
                                   public void

                                   onFailure(Call<MahasiswaResponse> call, Throwable t) {
                                       Log.e("Error Retrofit",

                                               "onFailure: " + t.getMessage());
                                   }
                               });
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

