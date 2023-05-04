<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/all/taglib.jsp" %>

<!-- ======= Footer ======= -->
  <footer id="footer">
    <section id="clients" class="clients section-bg">
      <div class="container">

        <div class="row nowrap has-scrollbar" data-aos="zoom-in">
        
        <c:forEach var="item" items="${ companies }">
        	<div class="col-lg-3 col-md-4 col-6 d-flex align-items-center justify-content-center">
            	<img src="<c:url value="/assets/img/companies/${ item.image }" />" class="img-fluid" alt="" />
          	</div>
        </c:forEach>

        </div>

      </div>
    </section>

    <div class="footer-top">
      <div class="container">
        <div class="row">

          <div class="col-lg-3 col-md-6 footer-contact">
            <h3>HiroShop</h3>
            <p>
              Số 76 - HiroShop, T.T Chờ, Yên Phong, Bắc Ninh <br><br>
              <strong>Điện thoại:</strong> 0585880406<br>
              <strong>Email:</strong> hiroshop76@gmail.com<br>
            </p>
          </div>

          <div class="col-lg-3 col-md-6 footer-links">
            <h4>Sản phẩm</h4>
            <ul>
              <li><i class="bx bx-chevron-right"></i> <a href="<c:url value="/category1" />">Gundam</a></li>
              <li><i class="bx bx-chevron-right"></i> <a href="<c:url value="/category2" />">Genshin</a></li>
              <li><i class="bx bx-chevron-right"></i> <a href="<c:url value="/category3" />">Lego</a></li>
              <li><i class="bx bx-chevron-right"></i> <a href="<c:url value="/category4" />">Marvel</a></li>
              <li><i class="bx bx-chevron-right"></i> <a href="<c:url value="/category5" />">Transformers</a></li>
            </ul>
          </div>

          <div class="col-lg-3 col-md-6 footer-links">
            <h4>Thương hiệu</h4>
            <ul>
              <li><i class="bx bx-chevron-right"></i> <a href="<c:url value="/company1" />">Hoyoverse</a></li>
              <li><i class="bx bx-chevron-right"></i> <a href="<c:url value="/company2" />">Good Slime Company</a></li>
              <li><i class="bx bx-chevron-right"></i> <a href="<c:url value="/company3" />">Lego</a></li>
              <li><i class="bx bx-chevron-right"></i> <a href="<c:url value="/company4" />">Bandai</a></li>
              <li><i class="bx bx-chevron-right"></i> <a href="<c:url value="/company5" />">Threezero</a></li>
            </ul>
          </div>

          <div class="col-lg-3 col-md-6 footer-links">
            <h4>Mạng xã hội</h4>
            <p>Theo dõi tôi tại</p>
            <div class="social-links mt-3">
              <a href="#" class="twitter"><i class="bx bxl-twitter"></i></a>
              <a href="#" class="facebook"><i class="bx bxl-facebook"></i></a>
              <a href="#" class="instagram"><i class="bx bxl-instagram"></i></a>
              <a href="#" class="google-plus"><i class="bx bxl-skype"></i></a>
              <a href="#" class="linkedin"><i class="bx bxl-linkedin"></i></a>
            </div>
          </div>

        </div>
      </div>
    </div>

    <div class="container footer-bottom clearfix">
      <div class="copyright">
        &copy; Bản quyền của <strong><span>HiroShop</span></strong>. Tất cả quyền được bảo lưu
      </div>
      <div class="credits">
        <!-- All the links in the footer should remain intact. -->
        <!-- You can delete the links only if you purchased the pro version. -->
        <!-- Licensing information: https://bootstrapmade.com/license/ -->
        <!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/arsha-free-bootstrap-html-template-corporate/ -->
        Website thiết kế dựa trên <a href="https://bootstrapmade.com/">BootstrapMade</a>
      </div>
    </div>
  </footer><!-- End Footer -->