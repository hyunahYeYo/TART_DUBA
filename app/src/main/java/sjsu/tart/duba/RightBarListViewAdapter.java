package sjsu.tart.duba;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RosieHyunahPark on 2018-07-31.
 */

public class RightBarListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<RightBarListViewItem> listViewItemList = new ArrayList<RightBarListViewItem>() ;

    // ListViewAdapter의 생성자
    public RightBarListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // Data Set(RIghtBarListViewItemList)에서 position에 위치한 데이터 참조 획득
        RightBarListViewItem listViewItem = listViewItemList.get(position);

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (listViewItem.getLocation().length() < 20) {
            convertView = inflater.inflate(R.layout.right_sidebar_item, parent, false);
        } else {
            convertView = inflater.inflate(R.layout.right_sidebar_item2, parent, false);
        }


        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView location = (TextView) convertView.findViewById(R.id.location);

        // 아이템 내 각 위젯에 데이터 반영
        location.setText(listViewItem.getLocation());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String location) {
        RightBarListViewItem item = new RightBarListViewItem();

        item.setLocation(location);

        listViewItemList.add(item);
    }
    public void deleteItem(long ind) {
        listViewItemList.remove(ind);
    }
    public void clearAllItems() {
        listViewItemList.clear();
    }
}
