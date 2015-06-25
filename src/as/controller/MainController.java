package as.controller;
/*所有的mvc控制器都在这个类里
 * 
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import as.service.*;
import as.model.*;


@Controller
public class MainController{
	@RequestMapping("login.do")
	public String gtLogin() {
		return "login";
	}
	
	//登陆
	@RequestMapping("index.do")
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String pwd,Model model)
	{
		LoginService ls = new LoginService();
		boolean loginFlag = ls.check(username,pwd);
		if (loginFlag) {
			return "index";
		} else {
			model.addAttribute("msg", "账号或密码错误,请重新登录");
			return "login";
		}

	}
	
	//跳转到学生名单界面
		@RequestMapping("stulist.do")
		public String gtstulist(Model model){
			
			return "stulist";
		}
		
    //请求学生名单，返回json
		@RequestMapping("getstuinfo.do")
		@ResponseBody
		public Map<Object, Object> getStuList(String page,String rows,
				@RequestParam(value="name", defaultValue="")String name,
				@RequestParam(value="cardID", defaultValue="")String cardID) {
			StuService ss = new StuService();
			if(!name.equals("") || !cardID.equals("")) {
				return ss.getSearchResult(name,cardID);
			}
			return ss.getStulist(page,rows);
		}
	
		//跳转到更新学生界面
		@RequestMapping("gtupdateStu.do")
		public String gtstuUpdate(String stuId,Model model){
			StuService ss = new StuService();
			model.addAttribute("stu",ss.getStuInfo(stuId));
			return "updateStu";
		}
		
		//更新学生信息
		@RequestMapping(value="updateStu.do",method=RequestMethod.POST)
		public void stuUpdate(@RequestBody Student s){
			StuService ss = new StuService();
			ss.updateStu(s);
		}
		
		//删除一个学生
		@RequestMapping("del.do")
		public String stuDel(String stuId){
			StuService ss = new StuService();
			ss.stuDel(stuId);
			return "stulist";
		}
		
		//批量删除学生
		@RequestMapping("dels.do")
		
		public String stusDel(@RequestBody Student[] stus){
			StuService ss = new StuService();
			ss.stusDel(stus);
			return "stulist";
		}
		
		
		//跳转到添加学生页面
		@RequestMapping("gtaddStu.do")
		public String gtaddStu(){
			return "addStu";
		}
		
		//提交添加请求
		@RequestMapping("addStu.do")
		public String addStu(@RequestBody Student s){
			StuService ss = new StuService();
			ss.addStu(s);
			return "addStu";
		}
		
		@RequestMapping("gtattinfo.do")
		public String gtAttinfo(){
			return "attinfo";
		}
		
		//请求打卡信息
		@RequestMapping("getattInfo.do")
		@ResponseBody
		public Map<Object, Object> getAttinfo(String page,String rows,
				@RequestParam(value="name", defaultValue="")String name,
				@RequestParam(value="cardID", defaultValue="")String cardID,
				@RequestParam(value="interId", defaultValue="")String interId) {
			AttService as = new AttService();
			if(!name.equals("") || !cardID.equals("")||!interId.equals("")) {
				return as.getSearchResult(name,cardID,interId);
			}
			return as.getStulist(page,rows);
		}
		
		//接收读卡器参数
		@RequestMapping(value="att.do",method=RequestMethod.POST)
		 public void getAttSign(HttpServletResponse response, HttpServletRequest req) throws IOException {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw= response.getWriter();
			
			String cardID = req.getParameter("cardId");
			String sInterId = req.getParameter("interId");
			
			AttService as = new AttService();
			String name = as.attGetName(cardID);
			
			System.out.println("name: " + name);
			
			if(name != null && !name.equals("")) {
				System.out.println("int: " + as.attStudent(cardID,sInterId,name));
				pw.write("1;" + name);
			} else {
				pw.write("2");
			}
			pw.flush();
			pw.close();
		}
		
		//跳转到短信编辑
		@RequestMapping("msg.do")
		public String gotoMsg(Model model){
			MsgService ms = new MsgService(); 
			model.addAttribute("msg",ms.getMsg());
			return "msg";
		}
		//短信编辑
		@RequestMapping("msgUpdate.do")
		public String msgupdate(@RequestBody Msg[] msg){
			MsgService ms = new MsgService();
			ms.updateMsg(msg[0].getMsg());
			return "msg";
		}
			

}
