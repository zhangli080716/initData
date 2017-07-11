package com.example;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.com.zhd.utils.DateUtils;
import sample.jsp.H2Demo;


@RestController
@RequestMapping("/springboot")
public class HelloWorldController {
//	@Autowired
//    JdbcTemplate jdbcTemplate;
//    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
//    public String sayWorld(@PathVariable("name") String name) {
//        return "Hello " + name;
//    }
	String hello="zhangli";
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public List<User> getUser() {
    	List<User> userList=new ArrayList<User>(2);
    	User user=new User();
    	user.setAge(1);
    	user.setName("张力");
    	User user1=new User();
    	user1.setAge(2);
    	user1.setName("张力ss");
    	userList.add(user);
    	userList.add(user1);
        return userList;
    }
    @RequestMapping("/zeroException")
    public int zeroException(){
       return 100/0;
    }
//    @RequestMapping("/getdbInfo")
//    public String get(){
//    	//return jdbcTemplate.queryForObject("select name from person limit 1", String.class);
//    }
    @GetMapping("/helloJsp")
    public String helloJsp(Map<String,Object> map){
           System.out.println("HelloController.helloJsp().hello="+hello);
           map.put("hello",hello);
           return"helloJsp";
    }
    @RequestMapping(value = "/initData")
	public void initData() {
    	randomGenerateMeatData();
    	randomGenerateVegetablesData();
    }
    /**************************************************/
	public  void randomGenerateMeatData() {
		String[] testItem = { "沙丁胺醇", "盐酸克伦特罗", "莱克多巴胺" };
		String[] checkPlace = {"东城区","西城区","石景山区","丰台区","海淀区","朝阳区","门头沟区","房山区","通州区","顺义区","大兴区","昌平区","平谷区","延庆区","密云区","怀柔区"};
		int seNo=1;
		String[] dayTypeArr = new String[31];
		for (int i = 0; i < 31; i++) {
			dayTypeArr[i] = DateUtils.getbeforeNyyyyMMddHHmmss((dayTypeArr.length - i) - 1);
		}
		Statement stat=null;
		try {
			stat = H2Demo.getConnection().createStatement();
		for (String day : dayTypeArr) {
			for (String place : checkPlace) {
				for (int j = 0; j < testItem.length; j++) {
					int randomInt=new Random().nextInt(30);
					for (int k = 0; k < randomInt; k++) {
						String checkDate=day.split(" ")[0]+" "+randomDate("2017-06-01 06:00:00","2017-06-01 18:00:00").split(" ")[1];
						double cv=Double.valueOf(randomCheckValue(5));
						String batchNo=DateUtils.getbeforeN(day,10);
						String getTime=DateUtils.getbeforeNMinute(checkDate, 10);
						String resultData = "00" + seNo+++ "\t组织\t" + testItem[j] + "\t"+checkDate+"\t"+place+"\t系统"+"\t--\t"+cv+"\t"+(cv>3?"阳性":"阴性")+"\t"+batchNo+"\t"+getTime;
							StringBuffer  sql=new StringBuffer("INSERT INTO MEAT VALUES(");
							for (String tempVal : resultData.split("\t")) {
								sql.append("'"+tempVal+"',");
							}
							stat.execute(sql.deleteCharAt(sql.length()-1).append(")").toString());
						//System.out.println(sql.deleteCharAt(sql.length()-1).append(")").toString());
					}
				}
			 }
		  }	
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public  void randomGenerateVegetablesData() {
		String[] testItem = {"农药残留"};
		String[] itemNameArr = {"胡萝卜","油麦菜","大白菜","茄子","西葫芦","油菜","黄瓜","芹菜"};
		String[] checkPlace = {"东城区","西城区","石景山区","丰台区","海淀区","朝阳区","门头沟区","房山区","通州区","顺义区","大兴区","昌平区","平谷区","延庆区","密云区","怀柔区"};
		int seNo=1;
		String[] dayTypeArr = new String[31];
		for (int i = 0; i < 31; i++) {
			dayTypeArr[i] = DateUtils.getbeforeNyyyyMMddHHmmss((dayTypeArr.length - i) - 1);
		}
		Statement stat=null;
		try {
			stat = H2Demo.getConnection().createStatement();
		for (String day : dayTypeArr) {
			for (String place : checkPlace) {
				for (String itemName : itemNameArr) {
					int randomInt=new Random().nextInt(30);
					for (int k = 0; k < randomInt; k++) {
						String checkDate=day.split(" ")[0]+" "+randomDate("2017-06-01 06:00:00","2017-06-01 18:00:00").split(" ")[1];
						double cv=Double.valueOf(randomCheckValue(75));
						String batchNo=DateUtils.getbeforeN(day,10);
						String getTime=DateUtils.getbeforeNMinute(checkDate, 10);
						String resultData = "00" + seNo+++"\t"+itemName+ "\t组织\t" + testItem[0] + "\t"+checkDate+"\t"+"赛必达测试\t"+place+"\t"+cv+"\t"+(cv>50?"不合格":"合格")+"\t某超市"+"\t"+batchNo+"\t"+getTime;
						StringBuffer  sql=new StringBuffer("INSERT INTO VEGETABLES VALUES(");
						for (String tempVal : resultData.split("\t")) {
							sql.append("'"+tempVal+"',");
						}
					  stat.execute(sql.deleteCharAt(sql.length()-1).append(")").toString());
					//System.out.println(sql.deleteCharAt(sql.length()-1).append(")").toString());
					}
				}
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
//		for (int j = 0; j < itemName.length; j++) {
//			for (int i = 1; i < 31; i++) {
//				int randomInt=new Random().nextInt(30);
//				for (int k = 0; k < randomInt; k++) {
//					String checkDate=randomDate("2017-06-01","2017-06-30");
//					double cv=Double.valueOf(randomCheckValue(70));
//					String batchNo=DateUtils.getbeforeN(checkDate,10);
//					String getTime=DateUtils.getbeforeNMinute(checkDate, 10);
//					String resultData = "00" + seNo+++ "\t组织\t" + testItem[0] + "\t"+checkDate+"\t"+"赛必达测试\t"+checkPlace[new Random().nextInt(checkPlace.length)]+"\t"+itemName[j]+"\t"+cv+"\t"+(cv>50?"不合格":"合格")+"\t某超市"+"\t"+batchNo+"\t"+getTime;
//				    System.out.println(resultData);
//				}
//			}
//		}
	}
    private static String randomCheckValue(int round){
    	double num = 0 + Math.random() * round;
        DecimalFormat   df=new   DecimalFormat("#0.000");  
        return df.format(num);
    }
	private static String randomDate(String beginDate, String endDate) {

		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date start = format.parse(beginDate);// 构造开始日期

			Date end = format.parse(endDate);// 构造结束日期

			// getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。

			if (start.getTime() >= end.getTime()) {

				return null;

			}

			long date = random(start.getTime(), end.getTime());

			return DateUtils.getStrYYYYMMDDhhmm(new Date(date));

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	private static long random(long begin, long end) {

		long rtn = begin + (long) (Math.random() * (end - begin));

		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值

		if (rtn == begin || rtn == end) {

			return random(begin, end);

		}

		return rtn;

	}
}
