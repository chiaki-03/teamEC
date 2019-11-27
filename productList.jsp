<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/radish.css">
<link rel="stylesheet" type="text/css" href="./css/productListTable.css">
<title>商品一覧画面</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<div id="contents">
	<h1>商品一覧画面</h1>

	<s:if test="keywordErrorMessageList != null && keywordErrorMessageList.size()>0">
		<div class="error">
		<div class="errorMessage">
		<s:iterator value="keywordErrorMessageList"><s:property /><br></s:iterator>
		</div>
		</div>
	</s:if>

	<s:elseif test="productInfoDTOList != null && productInfoDTOList.size()>0">
		<table class="productListTable">
			<s:iterator value="productInfoDTOList" status="st">
			<s:if test="#st.index%3 == 0"><tr></s:if>

			<td>
				<a href='<s:url action="ProductDetailsAction"><s:param name="productId" value="%{productId}"/></s:url>'>
					<img src='<s:property value="imageFilePath"/>/<s:property value="imageFileName"/>'class="itemImageBox"/><br>
					<s:property value="productName"/><br>
					<s:property value="productNameKana"/><br>
					<s:property value="price"/>円<br>
				</a>
			</td>
			<!-- この</tr>の警告は<tr>タグがifタグに囲まれて認識されていないので無視する、文法上問題なし -->
			<s:if test="#st.index%3 == 2"></tr></s:if>
			</s:iterator>
		</table>
	</s:elseif>

	<s:else>
		<div class="info">
			<h3>検索結果がありません。</h3>
		</div>
	</s:else>

</div>
</body>
</html>