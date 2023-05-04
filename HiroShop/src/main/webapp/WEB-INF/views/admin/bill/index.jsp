<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/all/taglib.jsp" %>  

<title>Hóa đơn</title>
<body>
	<div>
    	<h2 class="table-name">Hóa đơn</h2>
        <a href="<c:url value="/admin/createbill" />" class="create-btn">
        	Thêm mới
        	<i style="margin-left: 8px; font-size: 20px;" class="bi bi-plus-lg"></i>
        </a>
        <table class="table">
			<thead>
				<tr>
              		<th style="width: 20px;" scope="col"></th>
              		<th style="width: 20px;" scope="col"></th>
              		<th scope="col">Id</th>
              		<th scope="col">Tài khoản</th>
              		<th scope="col">Họ tên</th>
              		<th scope="col">Số điện thoai</th>
              		<th scope="col">Địa chỉ</th>
              		<th scope="col">Số lượng</th>
              		<th scope="col">Tổng tiền</th>
              		<th scope="col">Ghi chú</th>
            	</tr>
          	</thead>
			<tbody style="vertical-align: middle;">
          	
          	<c:forEach var="item" items="${ pagination }">
          		<tr>
              		<td><a href="<c:url value="/admin/editbill${ item.id }" />"><i class="bi bi-pencil-square"></i></a></i></td>
              		<td><a href="<c:url value="/admin/deletebill${ item.id }" />"><i class="bi bi-x-lg"></i></a></td>
              		<td>${ item.id }</td>
              		<td>${ item.username }</td>
              		<td>${ item.name }</td>
              		<td>${ item.phone }</td>
              		<td>${ item.address }</td>
              		<td>${ item.quantity }</td>
              		<td>
              			<fmt:formatNumber type="number" groupingUsed="true" value="${ item.total }" /> ₫
              		</td>
              		<td>${ item.note }</td>
            	</tr>
          	</c:forEach>
          	</tbody>
        </table>
        
        <c:if test="${ totaldata > 7 }">
        	<div class="pagination">
          		<a href="<c:url value="/admin/billP1" />">&laquo;</a>
          		
          		<c:forEach var="item" begin="1" end="${ paginationinfo.total }" varStatus="itemS">
          			<c:if test="${ itemS.index == paginationinfo.current }">
          				<a href="<c:url value="/admin/billP${ itemS.index }" />" class="active">${ itemS.index }</a>
          			</c:if>
          			<c:if test="${ itemS.index != paginationinfo.current }">
          				<a href="<c:url value="/admin/billP${ itemS.index }" />">${ itemS.index }</a>
          			</c:if>
          			<c:set var="lastindex" value="${ itemS.index }" />
          		</c:forEach>

          		<a href="<c:url value="/admin/billP${ lastindex }" />">&raquo;</a>
        	</div>
        </c:if>
        
      </div>
</body>