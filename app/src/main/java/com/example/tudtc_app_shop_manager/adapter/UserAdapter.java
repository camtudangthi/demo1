package com.example.tudtc_app_shop_manager.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tudtc_app_shop_manager.DTO.UserDAO;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{
    private List<User> listUser;
    private Context context;
    private UserDAO userDAO;

    public UserAdapter(List<User> listUser, Context context) {
        this.listUser = listUser;
        this.context = context;
        this.userDAO = new UserDAO(context);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User khuyenMai = listUser.get(position);
        if (khuyenMai == null){
            return;
        }
        holder.tvFullName.setText(khuyenMai.getFullName());
        holder.tvName.setText(khuyenMai.getUserName());
        if (khuyenMai.isBlock()){
            holder.imBlock.setVisibility(View.VISIBLE);
        } else {
            holder.imBlock.setVisibility(View.GONE);
        }
        holder.imBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Nhắc nhở")
                        .setMessage("Bỏ chặn "+ khuyenMai.getFullName())
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (userDAO.upBlackList(khuyenMai.getUserName(), 0)==1){
                                    listUser.get(position).setBlock(false);
                                    notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Nhắc nhở")
                        .setMessage("Thêm "+ khuyenMai.getFullName()+" vào black list")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (userDAO.upBlackList(khuyenMai.getUserName(), 1)==1){
                                    listUser.get(position).setBlock(true);
                                    notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listUser != null){
            return listUser.size();
        }
        return 0;
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layout;
        private ImageView img, imBlock;
        private TextView tvFullName, tvName;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_item_user);
            img = itemView.findViewById(R.id.img_avt_user1);
            imBlock = itemView.findViewById(R.id.img_block);
            tvFullName = itemView.findViewById(R.id.tv_username1);
            tvName = itemView.findViewById(R.id.tv_full_name1);
        }
    }

    public void setData(List<User> listUser) {
        this.listUser = listUser;
        notifyDataSetChanged();
    }
}
