package com.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Admins;
import com.entity.Goods;
import com.entity.Tops;
import com.entity.Types;
import com.entity.Users;
import com.service.AdminService;
import com.service.GoodService;
import com.service.OrderService;
import com.service.TopService;
import com.service.TypeService;
import com.service.UserService;
import com.util.PageUtil;
import com.util.SafeUtil;
import com.util.UploadUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final int rows = 10;

	@Autowired
	private AdminService adminService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodService goodService;
	@Autowired
	private TopService topService;
	@Autowired
	private TypeService typeService;

	@RequestMapping("/login")
	public String login(Admins admin, HttpServletRequest request, HttpSession session) {
		if (adminService.checkUser(admin.getUsername(), admin.getPassword())) {
			session.setAttribute("username", admin.getUsername());
			return "redirect:index";
		}
		request.setAttribute("msg", "用户名或密码错误!");
		return "/admin/login.jsp";
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("admin");
		return "/admin/login.jsp";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		request.setAttribute("msg", "恭喜你! 登录成功了");
		return "/admin/index.jsp";
	}

	@RequestMapping("/orderList")
	public String orderList(@RequestParam(required=false, defaultValue="0")byte status, HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") int page) {
		request.setAttribute("flag", 1);
		request.setAttribute("status", status);
		request.setAttribute("orderList", orderService.getList(status, page, rows));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, orderService.getTotal(status), page, rows));
		return "/admin/order_list.jsp";
	}

	@RequestMapping("/orderSearch")
	public String orderSearch(@RequestParam(required=false, defaultValue="0")int id, HttpServletRequest request) {
		if(id > 0) {
			request.setAttribute("flag", 1);
			request.setAttribute("orderList", orderService.getListById(id));
			return "/admin/order_list.jsp";
		}else {
			return "redirect:/admin/orderList";
		}
	}

	@RequestMapping("/orderDispose")
	public String orderDispose(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.dispose(id);
		return "redirect:orderList?flag=1&status="+status+"&page="+page;
	}

	@RequestMapping("/orderFinish")
	public String orderFinish(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.finish(id);
		return "redirect:orderList?flag=1&status="+status+"&page="+page;
	}

	@RequestMapping("/orderDelete")
	public String orderDelete(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.delete(id);
		return "redirect:orderList?flag=1&status="+status+"&page="+page;
	}

	@RequestMapping("/userList")
	public String userList(HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") int page) {
		request.setAttribute("flag", 2);
		request.setAttribute("userList", userService.getList(page, rows));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, userService.getTotal(), page, rows));
		return "/admin/user_list.jsp";
	}

	@RequestMapping("/userSearch")
	public String userSearch(String name, HttpServletRequest request) {
		request.setAttribute("flag", 2);
		request.setAttribute("userList", userService.getListByName(name));
		return "/admin/user_list.jsp";
	}

	@RequestMapping("/userAdd")
	public String userAdd(HttpServletRequest request) {
		request.setAttribute("flag", 2);
		return "/admin/user_add.jsp";
	}

	@RequestMapping("/userSave")
	public String userSave(Users user, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		if (userService.isExist(user.getUsername())) {
			request.setAttribute("msg", "用户名已存在!");
			return "/admin/user_add.jsp";
		}
		userService.add(user);
		return "redirect:userList?flag=2&page="+page;
	}

	@RequestMapping("/userRe")
	public String userRe(int id, HttpServletRequest request) {
		request.setAttribute("flag", 2);
		request.setAttribute("user", userService.get(id));
		return "/admin/user_reset.jsp";
	}

	@RequestMapping("/userReset")
	public String userReset(Users user, 
			@RequestParam(required=false, defaultValue="1") int page) {
		String password = SafeUtil.encode(user.getPassword());
		user = userService.get(user.getId());
		user.setPassword(password);
		userService.update(user);
		return "redirect:userList?flag=2&page="+page;
	}

	@RequestMapping("/userEdit")
	public String userEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 2);
		request.setAttribute("user", userService.get(id));
		return "/admin/user_edit.jsp";
	}

	@RequestMapping("/userUpdate")
	public String userUpdate(Users user, 
			@RequestParam(required=false, defaultValue="1") int page) {
		userService.update(user);
		return "redirect:userList?flag=2&page="+page;
	}

	@RequestMapping("/userDelete")
	public String userDelete(Users user, 
			@RequestParam(required=false, defaultValue="1") int page) {
		userService.delete(user);
		return "redirect:userList?flag=2&page="+page;
	}

	@RequestMapping("/goodList")
	public String goodList(@RequestParam(required=false, defaultValue="0")byte status, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		request.setAttribute("flag", 3);
		request.setAttribute("page", page);
		request.setAttribute("status", status);
		request.setAttribute("goodList", goodService.getList(status, page, rows));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, goodService.getTotal(status), page, rows));
		return "/admin/good_list.jsp";
	}

	@RequestMapping("/goodSearch")
	public String goodSearch(String name, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		request.setAttribute("flag", 3);
		request.setAttribute("goodList", goodService.getListByName(name, page, rows));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, goodService.getTotalByName(name), page, rows));
		return "/admin/good_list.jsp";
	}
	@RequestMapping("/goodAdd")
	public String goodAdd(HttpServletRequest request) {
		request.setAttribute("flag", 3);
		request.setAttribute("typeList", typeService.getList());
		return "/admin/good_add.jsp";
	}

	@RequestMapping("/goodSave")
	public String goodSave(String name, int price, String intro, int stock, int typeId, 
			MultipartFile cover, MultipartFile image1, MultipartFile image2, 
			@RequestParam(required=false, defaultValue="1") int page) throws Exception {
		Goods good = new Goods();
		good.setName(name);
		good.setPrice(price);
		good.setIntro(intro);
		good.setStock(stock);
		good.setTypeId(typeId);
		good.setCover(UploadUtil.fileUpload(cover));
		good.setImage1(UploadUtil.fileUpload(image1));
		good.setImage2(UploadUtil.fileUpload(image2));
		goodService.add(good);
		return "redirect:goodList?flag=3&page="+page;
	}

	@RequestMapping("/goodEdit")
	public String goodEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 3);
		request.setAttribute("typeList", typeService.getList());
		request.setAttribute("good", goodService.get(id));
		return "/admin/good_edit.jsp";
	}


	@RequestMapping("/goodUpdate")
	public String goodUpdate(int id, String name, int price, String intro, int stock, int typeId,  
			MultipartFile cover, MultipartFile image1, MultipartFile image2,
			@RequestParam(required=false, defaultValue="1") int page) throws Exception {
		Goods good = goodService.get(id);
		good.setName(name);
		good.setPrice(price);
		good.setIntro(intro);
		good.setStock(stock);
		good.setTypeId(typeId);
		if (Objects.nonNull(cover) && !cover.isEmpty()) {
			good.setCover(UploadUtil.fileUpload(cover));
		}
		if (Objects.nonNull(image1) && !image1.isEmpty()) {
			good.setImage1(UploadUtil.fileUpload(image1));
		}
		if (Objects.nonNull(image2) && !image2.isEmpty()) {
			good.setImage2(UploadUtil.fileUpload(image2));
		}
		goodService.update(good);
		return "redirect:goodList?flag=3&page="+page;
	}

	@RequestMapping("/goodDelete")
	public String goodDelete(int id, 
			@RequestParam(required=false, defaultValue="1") int page) {
		goodService.delete(id);
		return "redirect:goodList?flag=3&page="+page;
	}
	

	@RequestMapping("/topSave")
	public @ResponseBody String topSave(Tops tops, 
			@RequestParam(required=false, defaultValue="0")byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		int id = topService.add(tops);
		return id > 0 ? "ok" : null;
	}

	@RequestMapping("/topDelete")
	public @ResponseBody String topDelete(Tops tops, 
			@RequestParam(required=false, defaultValue="0")byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		boolean flag = topService.delete(tops);
		return flag ? "ok" : null;
	}

	@RequestMapping("/typeList")
	public String typeList(HttpServletRequest request) {
		request.setAttribute("flag", 4);
		request.setAttribute("typeList", typeService.getList());
		return "/admin/type_list.jsp";
	}
	

	@RequestMapping("/typeSearch")
	public String typeSearch(String name,HttpServletRequest request) {
		request.setAttribute("flag", 4);
		request.setAttribute("typeList", typeService.getList(name));
		return "/admin/type_list.jsp";
	}


	@RequestMapping("/typeSave")
	public String typeSave(Types type, 
			@RequestParam(required=false, defaultValue="1") int page) {
		typeService.add(type);
		return "redirect:typeList?flag=4&page="+page;
	}


	@RequestMapping("/typeEdit")
	public String typeUp(int id, HttpServletRequest request) {
		request.setAttribute("flag", 4);
		request.setAttribute("type", typeService.get(id));
		return "/admin/type_edit.jsp";
	}


	@RequestMapping("/typeUpdate")
	public String typeUpdate(Types type, 
			@RequestParam(required=false, defaultValue="1") int page) {
		typeService.update(type);
		return "redirect:typeList?flag=4&page="+page;
	}

	@RequestMapping("/typeDelete")
	public String typeDelete(Types type, 
			@RequestParam(required=false, defaultValue="1") int page) {
		typeService.delete(type);
		return "redirect:typeList?flag=4&page="+page;
	}


	@RequestMapping("/adminList")
	public String adminList(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		request.setAttribute("flag", 5);
		request.setAttribute("adminList", adminService.getList(page, rows));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, adminService.getTotal(), page, rows));
		return "/admin/admin_list.jsp";
	}

	@RequestMapping("/adminRe")
	public String adminRe(HttpServletRequest request, HttpSession session) {
		request.setAttribute("flag", 5);
		request.setAttribute("admin", adminService.getByUsername(String.valueOf(session.getAttribute("username"))));
		return "/admin/admin_reset.jsp";
	}

	@RequestMapping("/adminReset")
	public String adminReset(Admins admin, HttpServletRequest request) {
		request.setAttribute("flag", 5);
		if (adminService.get(admin.getId()).getPassword().equals(SafeUtil.encode(admin.getPassword()))) {
			admin.setPassword(SafeUtil.encode(admin.getPasswordNew()));
			adminService.update(admin);
			request.setAttribute("admin", admin);
			request.setAttribute("msg", "修改成功!");
		}else {
			request.setAttribute("msg", "原密码错误!");
		}
		return "/admin/admin_reset.jsp";
	}


	@RequestMapping("/adminSave")
	public String adminSave(Admins admin, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		if (adminService.isExist(admin.getUsername())) {
			request.setAttribute("msg", "用户名已存在!");
			return "/admin/admin_add.jsp";
		}
		adminService.add(admin);
		return "redirect:adminList?flag=5&page="+page;
	}


	@RequestMapping("/adminEdit")
	public String adminEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 5);
		request.setAttribute("admin", adminService.get(id));
		return "/admin/admin_edit.jsp";
	}

	@RequestMapping("/adminUpdate")
	public String adminUpdate(Admins admin, 
			@RequestParam(required=false, defaultValue="1") int page) {
		admin.setPassword(SafeUtil.encode(admin.getPassword()));
		adminService.update(admin);
		return "redirect:adminList?flag=5&page="+page;
	}

	@RequestMapping("/adminDelete")
	public String adminDelete(Admins admin, 
			@RequestParam(required=false, defaultValue="1") int page) {
		adminService.delete(admin);
		return "redirect:adminList?flag=5&page="+page;
	}

}
