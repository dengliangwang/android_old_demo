package com.jwk.kdjlgp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.haiwan.lantian.vhaiw.BKProduct;
import com.haiwan.lantian.vhaiw.HaiWan;
import com.haiwan.lantian.vhaiw.QGLog;
import com.haiwan.lantian.vhaiw.QZTradeDelegate;
import com.haiwan.lantian.vhaiw.YQConstants;
import com.haiwan.lantian.vhaiw.ZRError;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;




public class Product_List extends Activity implements QZTradeDelegate {

	private List<BKProduct> mProducts;
	private ListView listView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product__list);

		mProducts = new ArrayList<BKProduct>();
		HaiWan platform = HaiWan.shared();
		platform.setTradeDelegate(this);
		platform.getProductsFromStore(this);

		listView = (ListView) findViewById(R.id.product_list_view);

		ProductListAdapter productListAdapter = new ProductListAdapter(mProducts,this);
		listView.setAdapter(productListAdapter);

		Button button = (Button)findViewById(R.id.backtomainactivity);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Product_List.this, MainActivity.class);
				startActivity(intent);

			}
		});

	}

	@Override
	public void onActivityResult(int aRequestCode, int aResultCode, Intent aData)
	{

		final HaiWan aPlatform = HaiWan.shared();
		if (aPlatform.onActivityResult(this, aRequestCode, aResultCode, aData))
			return;

		super.onActivityResult(aRequestCode, aResultCode, aData);
	}



	public void requestProductsSuccess(BKProduct[] aProducts)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ������Ʒ��Ϣ�ɹ�����ȡ���Թ������Ʒ��Ϣ�б�
	     * δȡ����Ʒ��Ϣ�ģ���Ӧ��Ʒ����ֹ�������ʾ����ǰ�޷����򡱡�
	     */

			if (mProducts.size() > 0) {
				mProducts.clear();
			}



		for (int i = 0; i < aProducts.length; i++) {
			mProducts.add(aProducts[i]);
		}
		ProductListAdapter productListAdapter = (ProductListAdapter)listView.getAdapter();
		productListAdapter.setDta(mProducts);
		productListAdapter.notifyDataSetChanged();

		QGLog.info("Demo requestProductsSuccess: %s" + Arrays.toString(aProducts));



	}

	public void requestProductsFailure(ZRError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ������Ʒ��Ϣʧ�ܣ����������ԭ��
	     */

		QGLog.info("Demo requestProductsFailure: %s", aError);
	}

	public void buyProductSuccess(Map<String, Object> aArgs)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ����ɹ�����ȡ��Ӧ����Ʒ����
	     */
		BKProduct aProduct = null;

		Object aObj = aArgs.get(YQConstants.ARG_PRODUCT);
		if (null != aObj && aObj instanceof BKProduct)
		{
			aProduct = (BKProduct)aObj;
		}

		QGLog.info("Demo buyProductSuccess: %s", aProduct);
	}

	public void buyProductFailure(ZRError aError)
	{
	    /**
	     * ħ��ƽ̨����
	     *
	     * ����ʧ�ܣ����������ԭ��
	     */

		QGLog.info("Demo buyProductFailure: %s", aError);
	}


	@Override
	public void consumeFailure(@Nullable ZRError zrError) {

	}

	@Override
	public void consumeSuccess(@Nullable Map<String, ?> map) {

	}
}
