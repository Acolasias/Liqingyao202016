package com.bawei.lqy.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.lqy.R;
import com.bawei.lqy.model.bean.GsonBeanRight;
import com.bawei.lqy.utile.NetUtile;

import java.util.List;

import butterknife.BindView;

/**
 * Time:2020/1/6 0006上午 09:35202001
 * 邮箱:2094158527@qq.com
 * 作者:李庆瑶
 * 类功能:
 */
public class MyAdapterRight extends RecyclerView.Adapter<MyAdapterRight.MyViewHolder> {

    private List<GsonBeanRight.DataBean> list;

    public MyAdapterRight(List<GsonBeanRight.DataBean> list) {

        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.itemright, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        GsonBeanRight.DataBean dataBean = list.get(position);
        holder.tv1.setText(dataBean.getGoods_english_name());
        holder.tv2.setText(dataBean.getCurrency_price()+"");
        NetUtile.getInstance().getPhoto(dataBean.getGoods_thumb(),holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    String name = dataBean.getGoods_english_name();
                    onItemClickListener.onItemClick(name);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tv2)
        TextView tv2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            tv1=itemView.findViewById(R.id.tv1);
            tv2=itemView.findViewById(R.id.tv2);
        }
    }
    onItemClickListener onItemClickListener;

    public void setOnItemClickListener(MyAdapterRight.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(String name);
    }
}
