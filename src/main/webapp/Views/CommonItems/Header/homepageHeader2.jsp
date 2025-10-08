<%-- 
    Document   : homepageHeader2
    Created on : Oct 7, 2025, 8:43:53 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header class="header rs-nav">
    <div class="top-bar">
        <div class="container">
            <div class="row d-flex justify-content-between">
                <div class="topbar-left">
                    <ul>
                        <li><a href="faq-1.html"><i class="fa fa-question-circle"></i>FAQ</a></li>
                        <li><a href="javascript:;"><i class="fa fa-envelope-o"></i>humantech.support@website.com</a></li>
                    </ul>
                </div>
                <div class="topbar-right">
                    <ul>
                        <li>
                            <select class="header-lang-bx">
                                <option data-icon="flag flag-uk">English UK</option>
                                <option data-icon="flag flag-us">English US</option>
                            </select>
                        </li>
                        <li><a href="login.html">Login</a></li>
                        <li><a href="javascript:;">About Us</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="sticky-header navbar-expand-lg">
        <div class="menu-bar clearfix">
            <div class="container clearfix">
                <!-- Header Logo ==== -->
                <div class="menu-logo">
                    <a href="index.html"><img src="${pageContext.request.contextPath}/assets1/images/logo.png" alt=""></a>
                </div>
                <!-- Mobile Nav Button ==== -->
                <button class="navbar-toggler collapsed menuicon justify-content-end" type="button" data-toggle="collapse" data-target="#menuDropdown" aria-controls="menuDropdown" aria-expanded="false" aria-label="Toggle navigation">
                    <span></span>
                    <span></span>
                    <span></span>
                </button>
                <!-- Author Nav ==== -->
                <div class="secondary-menu">
                    <div class="secondary-inner">
                        <ul>
                            <li><a href="javascript:;" class="btn-link"><i class="fa fa-facebook"></i></a></li>
                            <li><a href="javascript:;" class="btn-link"><i class="fa fa-google-plus"></i></a></li>
                            <li><a href="javascript:;" class="btn-link"><i class="fa fa-linkedin"></i></a></li>
                            <!-- Search Button ==== -->
                            <li class="search-btn"><button id="quik-search-btn" type="button" class="btn-link"><i class="fa fa-search"></i></button></li>
                        </ul>
                    </div>
                </div>
                <!-- Search Box ==== -->
                <div class="nav-search-bar">
                    <form action="#">
                        <input name="search" value="" type="text" class="form-control" placeholder="Type to search">
                        <span><i class="ti-search"></i></span>
                    </form>
                    <span id="search-remove"><i class="ti-close"></i></span>
                </div>
                <!-- Navigation Menu ==== -->
                <div class="menu-links navbar-collapse collapse justify-content-start" id="menuDropdown">
                    <div class="menu-logo">
                        <a href="index.html"><img src="${pageContext.request.contextPath}/assets1/images/logo.png" alt=""></a>
                    </div>
                    <ul class="nav navbar-nav">	
                        <li><a href="javascript:;">Home</a></li>
                        <li><a href="javascript:;">Products</a></li>
                        <li><a href="javascript:;">News</a></li>
                        <li><a href="javascript:;">Careers</a></li>
                    </ul>
                    <div class="nav-social-link">
                        <a href="javascript:;"><i class="fa fa-facebook"></i></a>
                        <a href="javascript:;"><i class="fa fa-google-plus"></i></a>
                        <a href="javascript:;"><i class="fa fa-linkedin"></i></a>
                    </div>
                </div>
                <!-- Navigation Menu END ==== -->
            </div>
        </div>
    </div>
    <hr/>
</header>