package com.example.black.view;

import java.util.ArrayList;
import java.util.List;

import com.example.black.R;
import com.example.black.act.PublishComment;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.ImageLoaderUtil;
import com.example.black.lib.model.Company;
import com.example.black.lib.model.DarkTerraceActivity;
import com.example.black.lib.model.NoAttestationActivity;
import com.example.black.lib.model.NoStorageActivity;
import com.example.black.lib.model.QualifiedTerraceActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 排行榜适配器
 * 
 * @author admin
 * 
 */
public class RanklistAdapter extends BaseAdapter {
	private List<Company> rankingList;
	private Context context;
	private int RankingList_Type;
	private LayoutInflater inflater;

	public RanklistAdapter(Context context, List<Company> rankingList,int RankingList_Type) {
		this.context = context;
		this.rankingList = rankingList;
		this.RankingList_Type=RankingList_Type;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rankingList.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rankingList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_rankinglist_fragment_comment_list, null);
			holder = new ViewHolder();
			holder.rl_content = (LinearLayout) convertView.findViewById(R.id.rl_content);
			//最新评论key
			holder.tv_talk = (TextView) convertView.findViewById(R.id.tv_talk);
			//最新评论值
			holder.tc_comment_value=(TextView) convertView.findViewById(R.id.tc_comment_value);
			// 序号
			holder.tv_numbers = (TextView) convertView
					.findViewById(R.id.tv_numbers);
			// 图标
			holder.iv_images = (ImageView) convertView
					.findViewById(R.id.iv_images);
			// 名称
			holder.tv_names = (TextView) convertView
					.findViewById(R.id.tv_names);
			// 别名名称
			//holder.tv_alias_key = (TextView) convertView
					//.findViewById(R.id.tv_alias_key);
			// 别名值
			holder.tv_alias_value = (TextView) convertView
					.findViewById(R.id.tv_alias_value);
			// 操作数量
			holder.tv_submit_number = (TextView) convertView
					.findViewById(R.id.tv_submit_number);
			//holder.tv_ci = (TextView) convertView.findViewById(R.id.tv_ci);
			// 操作功能
			//holder.iv_get_dark = (ImageView) convertView
					//.findViewById(R.id.iv_get_dark);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Company rankingList2 = rankingList.get(position);
		if (holder!=null&&rankingList2 != null) {
			//String id = rankingList2.getId();
			String logo_url = rankingList2.getLogo_url();
			String company_name = rankingList2.getCompany_name();
			String alias_list = rankingList2.getAlias_list();
			String sub_com = rankingList2.getSub_com();
			
			holder.iv_images.setVisibility(View.INVISIBLE);
			
			if (logo_url != null && !"".equals(logo_url)) {
				DisplayImageOptions options = ImageLoaderUtil
						.init_DisplayImageOptions(0);
				ImageLoader.getInstance().displayImage(logo_url,
						holder.iv_images, options);
				holder.iv_images.setVisibility(View.VISIBLE);
			}else{
				holder.iv_images.setVisibility(View.GONE);
			}
			
			if (!"".equals((position+1))) {
				holder.tv_numbers.setText((position+1)+"");
			}
			//Log.i("jay_test", "排行榜次数------------->"+(position+1));
			if (company_name != null && !"".equals(company_name)) holder.tv_names.setText(company_name);
			if (alias_list != null && !"".equals(alias_list)) holder.tv_alias_value.setText(alias_list);
			
			switch (RankingList_Type) {
			case 2:
				// 评论
				holder.tv_submit_number.setText(rankingList2.getCom_amount()
						+ "");
				holder.tv_talk.setText("最新评论:");
				if (!TextUtils.isEmpty(sub_com)) {
					holder.tc_comment_value.setText(PublishComment.getCharSequence11(sub_com, context));
				}
				break;
			case 3:
				// 曝光
				holder.tv_submit_number.setText(rankingList2.getExp_amount()
						+ "");
				holder.tv_talk.setText("最新曝光:");
				if (!TextUtils.isEmpty(sub_com)) {
					holder.tc_comment_value.setText(PublishComment.getCharSequence11(sub_com, context));
				}
				break;
			default:
				break;
			}
			
			holder.rl_content.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (HttpUtil.isNetworkInfo(context) != null) {
						JumpToResultPage(rankingList2);
					}
				}
			});
		}		
		return convertView;
	}
	
	private void JumpToResultPage(Company company) {
		List<Company> list=new ArrayList<Company>();
		list.add(company);
		Intent intent = new Intent();
		intent.putExtra("search_key", company.getCompany_name());
		intent.putExtra("position", 0);
		intent.putExtra("mqtt_push_type", 2);
		intent.putParcelableArrayListExtra("companys", (ArrayList<? extends Parcelable>) list);
		switch (company.getAuth_level()) {
		// 黑平台
		case "006001":
			intent.setClass(context, DarkTerraceActivity.class);
			break;
		// 未验证
		case "006002":
			intent.setClass(context, NoAttestationActivity.class);
			break;
		// 合规
		case "006003":
			intent.setClass(context, QualifiedTerraceActivity.class);
			break;
		default:
			intent.setClass(context, NoStorageActivity.class);
			break;
		};
		context.startActivity(intent);
	}


	static class ViewHolder {
		LinearLayout rl_content;
		TextView tv_numbers, tv_names, tv_alias_value,
				tv_submit_number, tv_ci,tv_talk,tc_comment_value;
		ImageView iv_images, iv_get_dark;
		}
}
