package com.fosu.jobapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fosu.jobapp.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/11.
 */

public class CompanyInfoFragment extends Fragment {
    private static final String TAG = "CompanyInfoFragment";

    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        expandTextView.setText("        腾讯成立于1998年11月，是目前中国领先的互联网增值服务提供商之一。成立10多年以来，腾讯一直秉承“一切以用户价值为依归”的经营理念，为亿级海量用户提供稳定优质的各类服务，始终处于稳健发展的状态。2004年6月16日，腾讯控股有限公司在香港联交所主板公开上市（股票代号700）。\n" +
                "\n" +
                "       通过互联网服务提升人类生活品质是腾讯的使命。目前，腾讯把“连接一切”作为战略目标，提供社交平台与数字内容两项核心服务。通过即时通信工具QQ、移动社交和通信服务微信和WeChat、门户网站腾讯网（QQ.com）、腾讯游戏、社交网络平台QQ空间等中国领先的网络平台，满足互联网用户沟通、资讯、娱乐和金融等方面的需求。截至2016年第二季度，QQ的月活跃帐户数达到8.99亿，最高同时在线帐户数达到2.47亿；微信和WeChat的合并月活跃帐户数达8.06亿。腾讯的发展深刻地影响和改变了数以亿计网民的沟通方式和生活习惯，并为中国互联网行业开创了更加广阔的应用前景。\n" +
                "\n" +
                "       面向未来，坚持自主创新，树立民族品牌是腾讯的长远发展规划。目前，腾讯50%以上员工为研发人员，拥有完善的自主研发体系，在存储技术、数据挖掘、多媒体、中文处理、分布式网络、无线技术六大方向都拥有了相当数量的专利，在全球互联网企业中专利申请和授权总量均位居前列。\n" +
                "\n" +
                "       成为最受尊敬的互联网企业是腾讯的远景目标。腾讯一直积极参与公益事业、努力承担企业社会责任、推动网络文明。2006年，腾讯成立了中国互联网首家慈善公益基金会——腾讯慈善公益基金会，并建立了腾讯公益网（gongyi.qq.com）。秉承“致力公益慈善事业，关爱青少年成长，倡导企业公民责任，推动社会和谐进步”的宗旨，腾讯的每一项产品与业务都拥抱公益，开放互联，并倡导所有企业一起行动，通过互联网领域的技术、传播优势，缔造“人人可公益，民众齐参与”的互联网公益新生态。");
    }
}
