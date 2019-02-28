package cn.ssijri.ssm.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ssijri.ssm.controller.validation.ValidGroup1;
import cn.ssijri.ssm.exception.CustomException;
import cn.ssijri.ssm.po.ItemsCustom;
import cn.ssijri.ssm.po.ItemsQueryVo;
import cn.ssijri.ssm.service.ItemsService;

/**
 * 商品的Controller
 * @author 银涛
 *
 */
@Controller
@RequestMapping("/items")
//为了更好的分类管理url，使用此根路径，完整路径为根路径加子路径
//比如商品列表：items/queryItems.action
public class ItemsController {

	@Autowired
	private ItemsService itemsService;
	
	/**
	 * 自定义方法绑定请求参数的Date类型
	 * 这么设置后，如果Date为空也不会报错，否则在校验为空时报错
	 * @param request
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor editor = new CustomDateEditor(df,true);
		binder.registerCustomEditor(Date.class,editor);
	}
	
    /**
     * 商品分类
     * ModelAttribute:itemtypes表示最终将方法【返回值】放在request中的key
     * @return
     */
    @ModelAttribute("itemtypes")
    public Map<String, String> getItemTypes() {

        Map<String, String> itemTypes = new HashMap<String, String>();
        itemTypes.put("101", "数码");
        itemTypes.put("102", "母婴");

        return itemTypes;
    }
	
	/**
	 * 商品查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//@RequestMapping实现对queryItems方法和url映射，一个方法对应一个url
	//建议将url和方法名写一样，便于维护
	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest request,ItemsQueryVo itemsQueryVo)throws Exception{
		//测试forward后request是否共享
	
		//调用service查询数据库，查询商品列表
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
		
		//返回ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		//相当于request的setAttribute，在jsp中通过itemsList获取数据
		modelAndView.addObject("itemsList",itemsList);
		//指定视图
		//下边的路径，如果在视图解析器中配置jsp的路径前缀和后缀，修改为items/itemsList
		//modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
        //下边的路径配置就可以不在程序中指定jsp路径的前缀和后缀
        modelAndView.setViewName("items/itemsList");
		return modelAndView;
	}
	
	//商品修改页面显示
	//@RequestMapping("/editItems")
	//限制Http请求的方法,出于安全性考虑，method少哪个就不让直接get或者post访问，可自行选择
//	@RequestMapping(value="/editItems",method= {RequestMethod.POST,RequestMethod.GET})
//	public ModelAndView editItems(@RequestParam(value="id") Integer items_id) throws Exception{
//		
//		//调用service根据商品id查询商品信息
//		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);
//		
//		//返回ModelAndView
//		ModelAndView modelAndView = new ModelAndView();
//		
//		//将商品信息放到model
//		modelAndView.addObject("itemsCustom",itemsCustom);
//		
//		//商品修改页面
//		modelAndView.setViewName("items/editItems");
//		return modelAndView;
//	}
	/**
	 * 商品修改页面显示
	 * @param model
	 * @param items_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editItems",method= {RequestMethod.POST,RequestMethod.GET})
	//@RequestParam里面指定request传入的参名和形参绑定 其中有几个属性 
	// 如required表示指定参数是否要传入 defaultValue为默认value值
	public String editItems(Model model,@RequestParam(value="id") Integer items_id) throws Exception{
		
		//调用service根据商品id查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);
		//判断商品是否为空，根据id没有查询到商品，抛出异常，提示用户信息
		if(null==itemsCustom) {
			throw new CustomException("商品id有误，查询不出该商品信息！");
		}
		//通过形参model将model数据加到页面
		//相当于modelAndView.addObject("itemsCustom",itemsCustom);
		model.addAttribute("itemsCustom",itemsCustom);
		return "items/editItems";
	}
	//查询商品信息用json输出 使用RESTful需要配置web.xml新的前端控制器
	//{id}表示将这个位置的参数传到@PathVariable指定名称中
	@RequestMapping(value="/itemsView/{id}")
	public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id)throws Exception{
		
		//用service查询商品
		ItemsCustom itemsCustom = itemsService.findItemsById(id);
		return itemsCustom;
	}
	
	//商品信息修改提交
//	@RequestMapping("/editItemsSubmit")
//	public ModelAndView editItemsSubmit(HttpServletRequest request,Integer id,ItemsCustom itemsCustom) throws Exception{
//		
//		//调用service更新商品信息，页面需要将商品信息传到此方法
//		//。。。
//		itemsService.updateItems(id,itemsCustom);
//		
//		//返回ModelAndView
//		ModelAndView modelAndView = new ModelAndView();
//		//返回一个成功页面
//		modelAndView.setViewName("success");
//		
//		return modelAndView;
//		
//	}
	/**
	 * 商品修改提交
	 * 在校验的pojo前添加Validated
	 * value= {ValidGroup1.class}指定使用校验分组
	 * 【注意】该校验分组中，日期类没有分组到第一组，所以不会被校验
	 * @param request
	 * @param id
	 * @param itemsCustom
	 * @param bindingResult 与@Validated配对出现形参顺序固定，为接受错误信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editItemsSubmit")
	public String editItemsSubmit(
			Model model,
			HttpServletRequest request,
			Integer id,
			@ModelAttribute("itemsCustom")
			@Validated(value= {ValidGroup1.class}) ItemsCustom itemsCustom,
			BindingResult bindingResult,
			MultipartFile items_pic//接受图片
			) throws Exception{
		
		//获取校验错误信息
		if(bindingResult.hasErrors()) {
			//输出错误信息
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for(ObjectError objectError:allErrors) {
				System.out.println(objectError.getDefaultMessage());
			}
			//将错误信息传到页面
			model.addAttribute("allErrors", allErrors);
			//不使用ModelAttribute，可以通过直接model注入,将pojo回显到页面
			//model.addAttribute("itemsCustom",itemsCustom);

			//出错则重新回到商品页面
			return "items/editItems";
		}
		//图片原始名称
		String originalFilename = items_pic.getOriginalFilename();
		//上传图片
		if(items_pic!=null&&originalFilename!=null&&originalFilename.length()>0) {
			//存储图片的物理路径
			String pic_path = "E:\\JAVA\\Tomcat\\apache-tomcat-9.0.8\\temp\\2012\\";
			
			//新的图片名称 随机数加原扩展名
			String newFileName = UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
			//新的图片
			File newFile = new File(pic_path+newFileName);
			//将内存中的数据写入磁盘
			items_pic.transferTo(newFile);
			//将新的图片名称写入itemsCustom
			itemsCustom.setPic(newFileName);
			
		}
		
		
		//调用service更新商品信息，页面需要将商品信息传到此方法
		itemsService.updateItems(id,itemsCustom);
		
		//重定向到商品的查询列表 url改变 reques不共享 因为在一个Controller里面所以不需要加根路径
		//return "redirect:queryItems.action";
		//forward页面转发 url不变，request可以共享
		return "forward:queryItems.action";
		//return "success";
		
	}	
	
	@RequestMapping(value="/deletItems")
	public String deletItems(Integer[] items_id) throws Exception{
		
		//调用service批量删除
		//。。。
		
		return "success";
	}
	
	/**
	 * 批量修改商品页面，将商品信息查询出来，在页面中可以编辑商品信息
	 * @param request
	 * @param itemsQueryVo
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/editItemsQuery")
    public ModelAndView editItemsQuery(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception {

        //调用service查找数据库，查询商品列表
        List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);

        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //相当于request的setAttribute方法,在jsp页面中通过itemsList取数据
        modelAndView.addObject("itemsList", itemsList);

        modelAndView.setViewName("items/editItemsQuery");

        return modelAndView;
    }
	
	/**
	 * 批量修改商品提交
	 * 通过ItemsQueryVo接收批量提交的商品信息，将商品信息存储到itemsQueryVo中itemsList属性中。
	 * @param itemsQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editItemsAllSubmit")
	public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo) throws Exception{
		
		//获取包装对象itemsQueryVo中的itemsList属性，并对其里面包含的各种属性操作
		//。。。
		
		return "success";
	}
	
}
