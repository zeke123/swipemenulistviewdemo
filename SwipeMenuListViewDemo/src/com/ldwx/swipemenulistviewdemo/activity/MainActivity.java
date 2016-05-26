package com.ldwx.swipemenulistviewdemo.activity;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.ldwx.swipemenulistviewdemo.R;
import com.ldwx.swipemenulistviewdemo.bean.People;
import com.ldwx.swipemenulistviewdemo.view.SwipeMenu;
import com.ldwx.swipemenulistviewdemo.view.SwipeMenuCreator;
import com.ldwx.swipemenulistviewdemo.view.SwipeMenuItem;
import com.ldwx.swipemenulistviewdemo.view.SwipeMenuListView;
import com.ldwx.swipemenulistviewdemo.view.SwipeMenuListView.OnMenuItemClickListener;

public class MainActivity extends Activity {
	
	private SwipeMenuListView mListView;
	private AppAdapter mAdapter;

	private List<People> mPeopleList = new ArrayList<People>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (SwipeMenuListView) findViewById(R.id.list_view);

		for (int i = 0; i < 20; i++) {
			People people = new People("周建"+i, 28);
			mPeopleList.add(people);
		}
		mAdapter = new AppAdapter(this);

		mListView.setAdapter(mAdapter);

		initMenuListView();

	}

	private void initMenuListView() {
		// 创建一个SwipeMenuCreator供ListView使用
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// 创建一个侧滑菜单
				SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
				// 给该侧滑菜单设置背景
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,0xCE)));
				// 设置宽度
				openItem.setWidth(dp2px(80));
				// 设置名称
				openItem.setTitle("打开");
				// 字体大小
				openItem.setTitleSize(18);
				// 字体颜色
				openItem.setTitleColor(Color.WHITE);
				// 加入到侧滑菜单中
				menu.addMenuItem(openItem);

				// 创建一个侧滑菜单
				SwipeMenuItem delItem = new SwipeMenuItem(getApplicationContext());
				// 给该侧滑菜单设置背景
				delItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F,0x25)));
				// 设置宽度
				delItem.setWidth(dp2px(80));
				// 设置图片
				delItem.setIcon(R.drawable.icon_del);
				// 加入到侧滑菜单中
				menu.addMenuItem(delItem);
			}
		};

		mListView.setMenuCreator(creator);

		// 侧滑菜单的相应事件
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu,
					int index) {
				switch (index) {
				case 0:// 第一个添加的菜单的响应时间(打开)					
					String name = mPeopleList.get(position).getName();
					Toast.makeText(getApplicationContext(), "打开了"+name,Toast.LENGTH_SHORT).show();
					break;
				case 1:// 第二个添加的菜单的响应时间(删除)
					mPeopleList.remove(position);
					mAdapter.notifyDataSetChanged();
					break;
		
				}
				return false;
			}
		});
	}

	public class AppAdapter extends BaseAdapter {

		private Context mContext;

		public AppAdapter(Context context) {
			mContext = context;
		}

		@Override
		public int getCount() {
			return mPeopleList.size();
		}

		@Override
		public Object getItem(int position) {
			return mPeopleList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item, null);
				holder = new ViewHolder();
				holder.iv_name = (TextView) convertView.findViewById(R.id.iv_name);
				holder.tv_age = (TextView) convertView.findViewById(R.id.tv_age);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.iv_name.setText(mPeopleList.get(position).getName());
			holder.tv_age.setText(mPeopleList.get(position).getAge()+"");
			return convertView;
		}

		class ViewHolder 
		{
			TextView iv_name;
			TextView tv_age;
		}
	}

	// 将dp转化为px
	private int dp2px(int dp)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,getResources().getDisplayMetrics());
	}

}
