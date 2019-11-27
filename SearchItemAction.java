package com.internousdev.radish.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.radish.dao.MCategoryDAO;
import com.internousdev.radish.dao.ProductInfoDAO;
import com.internousdev.radish.dto.MCategoryDTO;
import com.internousdev.radish.dto.ProductInfoDTO;
import com.internousdev.radish.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class SearchItemAction extends ActionSupport implements SessionAware{

	private String keyword;
	private String categoryId;
	private Map<String,Object> session;
	private List<ProductInfoDTO> productInfoDTOList;
	private List<String> keywordErrorMessageList;

	public String execute(){

		InputChecker inputChecker=new InputChecker();

		//カテゴリIDがnullなら全てのカテゴリー
		if(categoryId==null){
			categoryId="1";
		}

		if(StringUtils.isBlank(keyword)){
			keyword="";//空欄だったら空文字
		}else{
			keyword=keyword.replaceAll("　", " ").replaceAll("\\s{2,}", " ").trim();
		}

		//入力チェック
		if(!keyword.equals("")){
			 keywordErrorMessageList = inputChecker.doCheck("検索ワード",keyword,0,50,true,true,true,true,true,true);
			 if(keywordErrorMessageList.size()>0){
				 return SUCCESS;//エラーの場合商品一覧画面へ
			 }
		}

		ProductInfoDAO productInfoDAO=new ProductInfoDAO();

		switch(categoryId){
		case "1":
			productInfoDTOList=productInfoDAO.getKeywordProductInfo(keyword.split(" "));
			break;
			//検索ワードで検索

			default:
				productInfoDTOList=productInfoDAO.getCategoryIdKeywordProductInfo(categoryId,keyword.split(" "));
			break;
		}

		if(!session.containsKey("mCategoryDTOList")){
			MCategoryDAO mCategoryDAO=new MCategoryDAO();
			List<MCategoryDTO>mCategoryDTOList=new ArrayList<MCategoryDTO>();
			mCategoryDTOList=mCategoryDAO.getMCategoryList();

			session.put("mCategoryDTOList",mCategoryDTOList);
		}

		return SUCCESS;

	}

	public String getKeyword(){
		return keyword;
	}

	public void setKeyword(String keyword){
		this.keyword=keyword;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setCategoryId(String categoryId){
		this.categoryId=categoryId;
	}

	public List<ProductInfoDTO> getProductInfoDTOList(){
		return productInfoDTOList;
	}

	public void setProductInfoDTOList(List<ProductInfoDTO> productInfoDTOList){
		this.productInfoDTOList=productInfoDTOList;
	}

	public List<String> getKeywordErrorMessageList(){
		return keywordErrorMessageList;
	}

	public void setKeywordErrorMessageList(List<String> keywordErrorMessageList){
		this.keywordErrorMessageList=keywordErrorMessageList;
	}

	public Map<String,Object> getSession(){
		return session;
	}

	@Override
	public void setSession(Map<String,Object> session){
		this.session=session;
	}

}
