package rjm.romek.facegame.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import static rjm.romek.facegame.R.drawable.*;

public class ImageAdapter extends BaseAdapter {
    private Context context;

    public ImageAdapter(Context c) {
        context = c;
    }

    public int getCount() {
        return faces.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(faces[position]);
        return imageView;
    }

    private Integer[] faces = {
            face_1, face_2, face_3, face_4, face_5, face_6, face_7, face_8, face_9,
            face_10, face_11, face_12, face_13, face_14, face_15, face_16, face_17, face_18, face_19,
            face_20, face_21, face_22, face_23, face_24, face_25, face_26, face_27, face_28, face_29,
            face_30, face_31, face_32, face_33, face_34, face_35, face_36, face_37, face_38, face_39,
            face_40, face_41, face_42, face_43, face_44, face_45, face_46, face_47, face_48, face_49,
            face_50, face_51, face_52, face_53, face_54, face_55, face_56, face_57, face_58, face_59,
            face_60, face_61, face_62, face_63, face_64, face_65, face_66, face_67, face_68, face_69,
            face_70, face_71, face_72, face_73, face_74, face_75, face_76, face_77, face_78, face_79,
            face_80
    };
}