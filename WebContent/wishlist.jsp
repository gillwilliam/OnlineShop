<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<jsp:include page="Header.jsp"/>
<body>
	<jsp:include page="Navigation.jsp"/>
	<!-- BREADCRUMB -->
	<div id="breadcrumb">
		<div class="container">
			<ul class="breadcrumb">
				<li><a href="#">Home</a></li>
				<li class="active">Wishlist</li>
			</ul>
		</div>
	</div>
	<!-- /BREADCRUMB -->

<!-- section -->
<div class="section">
	<!-- container -->
	<div class="container">
		<!-- row -->
		<div class="row">
			<form id="checkout-form" class="clearfix">
				<div class="col-md-12">
					<div class="order-summary clearfix">
						<div class="section-title">
							<h3 class="title">Wishlist</h3>
						</div>
						<table class="shopping-cart-table table">
							<thead>
								<tr>
									<th>Product</th>
									<th></th>
									<th class="text-center">Price</th>
									<th class="text-center">Quantity</th>
									<th class="text-center">Total</th>
									<th class="text-right"></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="thumb"><img src="./img/thumb-product01.jpg" alt=""></td>
									<td class="details">
										<a href="#">Product Name Goes Here</a>
										<ul>
											<li><span>Size: XL</span></li>
											<li><span>Color: Camelot</span></li>
										</ul>
									</td>
									<td class="price text-center"><strong>$32.50</strong><br><del class="font-weak"><small>$40.00</small></del></td>
									<td class="qty text-center"><input class="input" type="number" value="1"></td>
									<td class="total text-center"><strong class="primary-color">$32.50</strong></td>
									<td class="text-right"><button class="main-btn icon-btn"><i class="fa fa-close"></i></button></td>
								</tr>
								<tr>
									<td class="thumb"><img src="./img/thumb-product01.jpg" alt=""></td>
									<td class="details">
										<a href="#">Product Name Goes Here</a>
										<ul>
											<li><span>Size: XL</span></li>
											<li><span>Color: Camelot</span></li>
										</ul>
									</td>
									<td class="price text-center"><strong>$32.50</strong></td>
									<td class="qty text-center"><input class="input" type="number" value="1"></td>
									<td class="total text-center"><strong class="primary-color">$32.50</strong></td>
									<td class="text-right"><button class="main-btn icon-btn"><i class="fa fa-close"></i></button></td>
								</tr>
								<tr>
									<td class="thumb"><img src="./img/thumb-product01.jpg" alt=""></td>
									<td class="details">
										<a href="#">Product Name Goes Here</a>
										<ul>
											<li><span>Size: XL</span></li>
											<li><span>Color: Camelot</span></li>
										</ul>
									</td>
									<td class="price text-center"><strong>$32.50</strong></td>
									<td class="qty text-center"><input class="input" type="number" value="1"></td>
									<td class="total text-center"><strong class="primary-color">$32.50</strong></td>
									<td class="text-right"><button class="main-btn icon-btn"><i class="fa fa-close"></i></button></td>
								</tr>
							</tbody>
						</table>
					</div>

				</div>
			</form>
		</div>
		<!-- /row -->
	</div>
	<!-- /container -->
</div>
<!-- /section -->

	<div class="section">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /section -->

	<!-- FOOTER -->
	<footer id="footer" class="section section-grey">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<!-- footer widget -->
				<div class="col-md-3 col-sm-6 col-xs-6">
					<div class="footer">
						<!-- footer logo -->
						<div class="footer-logo">
							<a class="logo" href="#">
		            <img src="./img/logo.png" alt="">
		          </a>
						</div>
						<!-- /footer logo -->

						<!-- footer social -->
						<ul class="footer-social">
							<li><a href="#"><i class="fa fa-facebook"></i></a></li>
							<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							<li><a href="#"><i class="fa fa-instagram"></i></a></li>
							<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
							<li><a href="#"><i class="fa fa-pinterest"></i></a></li>
						</ul>
						<!-- /footer social -->
					</div>
				</div>
				<!-- /footer widget -->

				<!-- footer widget -->
				<div class="col-md-3 col-sm-6 col-xs-6">
					<div class="footer">
						<h3 class="footer-header">My Account</h3>
						<ul class="list-links">
							<li><a href="#">My Account</a></li>
							<li><a href="#">My Wishlist</a></li>
							<li><a href="#">Checkout</a></li>
							<li><a href="#">Login</a></li>
						</ul>
					</div>
				</div>
				<!-- /footer widget -->

				<div class="clearfix visible-sm visible-xs"></div>

				<!-- footer widget -->
				<div class="col-md-3 col-sm-6 col-xs-6">
					<div class="footer">
						<h3 class="footer-header">Customer Service</h3>
						<ul class="list-links">
							<li><a href="#">About Us</a></li>
							<li><a href="#">Shiping & Return</a></li>
							<li><a href="#">Shiping Guide</a></li>
							<li><a href="#">FAQ</a></li>
						</ul>
					</div>
				</div>
				<!-- /footer widget -->

			</div>
			<!-- /row -->
			<hr>
			<!-- row -->
			<div class="row">
				<div class="col-md-8 col-md-offset-2 text-center">
					<!-- footer copyright -->
					<div class="footer-copyright">
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</div>
					<!-- /footer copyright -->
				</div>
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</footer>
	<!-- /FOOTER -->

	<!-- jQuery Plugins -->
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/slick.min.js"></script>
	<script src="js/nouislider.min.js"></script>
	<script src="js/jquery.zoom.min.js"></script>
	<script src="js/main.js"></script>

</body>

</html>
