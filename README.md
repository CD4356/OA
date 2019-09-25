## <center>`OA办公自动化系统`


#### 简介:

&nbsp;&nbsp;&emsp; OA( Office Automation System)办公自动化系统是一个企业用来管理日常事务的系统，它一般用来管理各种流程（报销、请假. . .）审批，通讯录、日程、文件管理、通知、公告等功能。OA系统可以实现更加简单、规范、高效地管理，提高企业的整体管理水平。


#### 技术:

&nbsp;&nbsp;&emsp; 后端：spring + springmvc + mybatis<br>
&nbsp;&nbsp;&emsp; 前端：bootstrap<br>
&nbsp;&nbsp;&emsp; 数据库：mysql<br>

#### 开发环境:

&nbsp;&nbsp;&emsp;idea2018.25、jdk8、tomcat9、mysql5.7


<br><br>功能模块<br>
![cd4356](https://img-blog.csdnimg.cn/20190923105313228.jpg)


<br><br>数据库设计<br><br>
![cd4356](https://img-blog.csdnimg.cn/20190923180138373.png?)


<br><br>个人中心功能演示 <br><br>
![cd4356](https://img-blog.csdnimg.cn/20190924000529438.gif)


<br><br>基本模块功能演示<br><br>
![cd4356](https://img-blog.csdnimg.cn/20190924001544353.gif)

<br><br>报销单模块功能演示<br><br>
![cd4356](https://img-blog.csdnimg.cn/20190924004726544.gif)
<br>
<hr>

#### 登 陆<br><br>
![cd4356](https://img-blog.csdnimg.cn/20190924083602233.png)

<br>关注点：<br>
&nbsp;&nbsp;&emsp;&emsp;Spring拦截器对url请求进行拦截验证(<font color=gray>获取session来验证</font>)，用户未登陆 - - -> 重定向回登陆界面 - - -> 登陆（<font color=gray>登陆成功后将员工信息添加到session中，并跳转到后台的个人信息界面；失败重定向回登陆界面</font>）


<br><br>

#### 个人中心模块<br>

个人中心包含个人信息、密码设置、日志记录 和 登出功能<br>

关注点：<br>
* &emsp;获取session中员工信息，调用具体业务方法获取所需数据，然后对数据进行渲染。<br>
* &emsp;登出 - - -> 删除session（session.setAttribute(“xxx”,null)）<br>

* &emsp;注：日志记录是没有删除和修改这种需求的，但我忘了，加了删除功能，这是不合理的。


<br><br>

#### 基本模块<br>

基本模块包含：部门管理（部门信息、部门列表），员工管理（添加员工、员工列表）

基本模块其实就是对部门和员工的简单的增删改查操作

关注点：<br>
* &emsp;所有登陆进OA系统的员工都可以查看部门列表和员工列表信息，但对员工 和 部门的增、删、改操作只有管理员角色才有权限进行

<br><br>

#### 报销单模块<br>

报销单模块包含：添加报销单、个人报销单 和 待处理报销单
![cd4356](https://img-blog.csdnimg.cn/20190924181141595.jpg)

<br>添加报销单时，设置待处理人为创建人（状态 = 新创建）。点击提交后，待处理人改为员工所在部门的部门经理（状态 = 已提交）。部门经理通过审核后，如果报销金额 `>=10000` ，待处理人改为总经理，如果报销金额 `<10000` ，待处理人改为财务（状态 = 已通过）。财务审核后进行打款（状态 = 已打款）

<br><br>个人报销单 - - -> 根据创建人id来查询报销单信息

```xml
<select id="selectByCreateId" parameterType="String" resultMap="claimVoucher">
    select cv.*,ce.name cname,ce.post cpost,de.name dname,de.post dpost from claim_voucher cv
    left join employee ce on (ce.id=cv.create_id)
    left join employee de on (de.id=cv.next_deal_id)
    where cv.create_id=#{createId} order by cv.create_time desc
</select>
```

```java
public List<ClaimVoucher> getForSelf(String id) {
 	 return claimVoucherDao.selectByCreateId(id);
}
```

```java
@RequestMapping("/self")
public String self(HttpSession session, Map<String,Object> map){
    Employee employee = (Employee) session.getAttribute("employee");
    map.put("list",claimVoucherService.getForSelf(employee.getId()));
    return "claim_voucher_self";
}
```

<br><br>待处理报销单 - - -> 根据待处理人id来查询报销单信息

```xml
<select id="selectByNextDealId" parameterType="String" resultMap="claimVoucher">
    select cv.*,ce.name cname,ce.post cpost,de.name dname,de.post dpost from claim_voucher cv
    left join employee ce on (ce.id=cv.create_id)
    left join employee de on (de.id=cv.next_deal_id)
    where cv.next_deal_id=#{nextDealId} order by cv.create_time desc
</select>
```

```java
public List<ClaimVoucher> getForDeal(String id) {
	return claimVoucherDao.selectByNextDealId(id);
}
```

```java
@RequestMapping("/deal")
public String deal(HttpSession session, Map<String,Object> map){
    Employee employee = (Employee) session.getAttribute("employee");
    map.put("list",claimVoucherService.getForDeal(employee.getId()));
    return "claim_voucher_deal";
}
```

