package as.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/*mybaits mapper
 * 
 */

public interface Mapper {
	
	
	public String getpwd(String username);
	
	public List<Student> getStulist(@Param(value="begin") int begin,@Param(value="num") int num);
	
	public int getTotalNum();
	
	public Student getStuInfo(int stuId);
	
	public String getStuName(String cardID);
	
	public int attRaw(@Param("cardID")String cardID, @Param("interId")int interId, @Param("name")String name);
	
	public void stuDel(int stuId);
	
	public  List<Student> getSearchResult(@Param(value="name")String name,@Param(value="cardID")String cardID);
	
	public String getMsg();
	
	public void updateMsg(String msg);
	




}
