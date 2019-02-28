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
 * ��Ʒ��Controller
 * @author ����
 *
 */
@Controller
@RequestMapping("/items")
//Ϊ�˸��õķ������url��ʹ�ô˸�·��������·��Ϊ��·������·��
//������Ʒ�б�items/queryItems.action
public class ItemsController {

	@Autowired
	private ItemsService itemsService;
	
	/**
	 * �Զ��巽�������������Date����
	 * ��ô���ú����DateΪ��Ҳ���ᱨ��������У��Ϊ��ʱ����
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
     * ��Ʒ����
     * ModelAttribute:itemtypes��ʾ���ս�����������ֵ������request�е�key
     * @return
     */
    @ModelAttribute("itemtypes")
    public Map<String, String> getItemTypes() {

        Map<String, String> itemTypes = new HashMap<String, String>();
        itemTypes.put("101", "����");
        itemTypes.put("102", "ĸӤ");

        return itemTypes;
    }
	
	/**
	 * ��Ʒ��ѯ
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//@RequestMappingʵ�ֶ�queryItems������urlӳ�䣬һ��������Ӧһ��url
	//���齫url�ͷ�����дһ��������ά��
	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest request,ItemsQueryVo itemsQueryVo)throws Exception{
		//����forward��request�Ƿ���
	
		//����service��ѯ���ݿ⣬��ѯ��Ʒ�б�
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
		
		//����ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		//�൱��request��setAttribute����jsp��ͨ��itemsList��ȡ����
		modelAndView.addObject("itemsList",itemsList);
		//ָ����ͼ
		//�±ߵ�·�����������ͼ������������jsp��·��ǰ׺�ͺ�׺���޸�Ϊitems/itemsList
		//modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
        //�±ߵ�·�����þͿ��Բ��ڳ�����ָ��jsp·����ǰ׺�ͺ�׺
        modelAndView.setViewName("items/itemsList");
		return modelAndView;
	}
	
	//��Ʒ�޸�ҳ����ʾ
	//@RequestMapping("/editItems")
	//����Http����ķ���,���ڰ�ȫ�Կ��ǣ�method���ĸ��Ͳ���ֱ��get����post���ʣ�������ѡ��
//	@RequestMapping(value="/editItems",method= {RequestMethod.POST,RequestMethod.GET})
//	public ModelAndView editItems(@RequestParam(value="id") Integer items_id) throws Exception{
//		
//		//����service������Ʒid��ѯ��Ʒ��Ϣ
//		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);
//		
//		//����ModelAndView
//		ModelAndView modelAndView = new ModelAndView();
//		
//		//����Ʒ��Ϣ�ŵ�model
//		modelAndView.addObject("itemsCustom",itemsCustom);
//		
//		//��Ʒ�޸�ҳ��
//		modelAndView.setViewName("items/editItems");
//		return modelAndView;
//	}
	/**
	 * ��Ʒ�޸�ҳ����ʾ
	 * @param model
	 * @param items_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editItems",method= {RequestMethod.POST,RequestMethod.GET})
	//@RequestParam����ָ��request����Ĳ������βΰ� �����м������� 
	// ��required��ʾָ�������Ƿ�Ҫ���� defaultValueΪĬ��valueֵ
	public String editItems(Model model,@RequestParam(value="id") Integer items_id) throws Exception{
		
		//����service������Ʒid��ѯ��Ʒ��Ϣ
		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);
		//�ж���Ʒ�Ƿ�Ϊ�գ�����idû�в�ѯ����Ʒ���׳��쳣����ʾ�û���Ϣ
		if(null==itemsCustom) {
			throw new CustomException("��Ʒid���󣬲�ѯ��������Ʒ��Ϣ��");
		}
		//ͨ���β�model��model���ݼӵ�ҳ��
		//�൱��modelAndView.addObject("itemsCustom",itemsCustom);
		model.addAttribute("itemsCustom",itemsCustom);
		return "items/editItems";
	}
	//��ѯ��Ʒ��Ϣ��json��� ʹ��RESTful��Ҫ����web.xml�µ�ǰ�˿�����
	//{id}��ʾ�����λ�õĲ�������@PathVariableָ��������
	@RequestMapping(value="/itemsView/{id}")
	public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id)throws Exception{
		
		//��service��ѯ��Ʒ
		ItemsCustom itemsCustom = itemsService.findItemsById(id);
		return itemsCustom;
	}
	
	//��Ʒ��Ϣ�޸��ύ
//	@RequestMapping("/editItemsSubmit")
//	public ModelAndView editItemsSubmit(HttpServletRequest request,Integer id,ItemsCustom itemsCustom) throws Exception{
//		
//		//����service������Ʒ��Ϣ��ҳ����Ҫ����Ʒ��Ϣ�����˷���
//		//������
//		itemsService.updateItems(id,itemsCustom);
//		
//		//����ModelAndView
//		ModelAndView modelAndView = new ModelAndView();
//		//����һ���ɹ�ҳ��
//		modelAndView.setViewName("success");
//		
//		return modelAndView;
//		
//	}
	/**
	 * ��Ʒ�޸��ύ
	 * ��У���pojoǰ���Validated
	 * value= {ValidGroup1.class}ָ��ʹ��У�����
	 * ��ע�⡿��У������У�������û�з��鵽��һ�飬���Բ��ᱻУ��
	 * @param request
	 * @param id
	 * @param itemsCustom
	 * @param bindingResult ��@Validated��Գ����β�˳��̶���Ϊ���ܴ�����Ϣ
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
			MultipartFile items_pic//����ͼƬ
			) throws Exception{
		
		//��ȡУ�������Ϣ
		if(bindingResult.hasErrors()) {
			//���������Ϣ
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for(ObjectError objectError:allErrors) {
				System.out.println(objectError.getDefaultMessage());
			}
			//��������Ϣ����ҳ��
			model.addAttribute("allErrors", allErrors);
			//��ʹ��ModelAttribute������ͨ��ֱ��modelע��,��pojo���Ե�ҳ��
			//model.addAttribute("itemsCustom",itemsCustom);

			//���������»ص���Ʒҳ��
			return "items/editItems";
		}
		//ͼƬԭʼ����
		String originalFilename = items_pic.getOriginalFilename();
		//�ϴ�ͼƬ
		if(items_pic!=null&&originalFilename!=null&&originalFilename.length()>0) {
			//�洢ͼƬ������·��
			String pic_path = "E:\\JAVA\\Tomcat\\apache-tomcat-9.0.8\\temp\\2012\\";
			
			//�µ�ͼƬ���� �������ԭ��չ��
			String newFileName = UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
			//�µ�ͼƬ
			File newFile = new File(pic_path+newFileName);
			//���ڴ��е�����д�����
			items_pic.transferTo(newFile);
			//���µ�ͼƬ����д��itemsCustom
			itemsCustom.setPic(newFileName);
			
		}
		
		
		//����service������Ʒ��Ϣ��ҳ����Ҫ����Ʒ��Ϣ�����˷���
		itemsService.updateItems(id,itemsCustom);
		
		//�ض�����Ʒ�Ĳ�ѯ�б� url�ı� reques������ ��Ϊ��һ��Controller�������Բ���Ҫ�Ӹ�·��
		//return "redirect:queryItems.action";
		//forwardҳ��ת�� url���䣬request���Թ���
		return "forward:queryItems.action";
		//return "success";
		
	}	
	
	@RequestMapping(value="/deletItems")
	public String deletItems(Integer[] items_id) throws Exception{
		
		//����service����ɾ��
		//������
		
		return "success";
	}
	
	/**
	 * �����޸���Ʒҳ�棬����Ʒ��Ϣ��ѯ��������ҳ���п��Ա༭��Ʒ��Ϣ
	 * @param request
	 * @param itemsQueryVo
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/editItemsQuery")
    public ModelAndView editItemsQuery(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception {

        //����service�������ݿ⣬��ѯ��Ʒ�б�
        List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);

        //����ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //�൱��request��setAttribute����,��jspҳ����ͨ��itemsListȡ����
        modelAndView.addObject("itemsList", itemsList);

        modelAndView.setViewName("items/editItemsQuery");

        return modelAndView;
    }
	
	/**
	 * �����޸���Ʒ�ύ
	 * ͨ��ItemsQueryVo���������ύ����Ʒ��Ϣ������Ʒ��Ϣ�洢��itemsQueryVo��itemsList�����С�
	 * @param itemsQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editItemsAllSubmit")
	public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo) throws Exception{
		
		//��ȡ��װ����itemsQueryVo�е�itemsList���ԣ���������������ĸ������Բ���
		//������
		
		return "success";
	}
	
}
