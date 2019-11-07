package com.ershou.ershoumarket.controller;

import com.ershou.ershoumarket.controller.viewobject.ItemVO;
import com.ershou.ershoumarket.response.CommenReturnType;
import com.ershou.ershoumarket.service.ItemService;
import com.ershou.ershoumarket.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"},allowCredentials = "true",allowedHeaders = "*")
public class ItemController
{
    @Autowired
    private ItemService itemService;

    //创建商品的controller
    @RequestMapping(value = "/create",method = RequestMethod.POST,consumes = {"application/x-www-form-urlencoded"})
    @ResponseBody
    public CommenReturnType createItem(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "description")String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "imgUrl")String imgUrl
                                       )
    {
        //封装service请求来封装商品
        ItemModel itemModel=new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);

        ItemVO itemVO = convertVOFromModel(itemModelForReturn);

        return CommenReturnType.create(itemVO);
    }

    private  ItemVO convertVOFromModel(ItemModel itemModel)
    {
        //很多时候VO和Model不一样，这里虽然两者几乎一样但仍分层写
        if(itemModel==null)
        {
            return null;
        }
        ItemVO itemVO=new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        return itemVO;
    }

//    商品详情页
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public CommenReturnType getItem(@RequestParam(name = "id")Integer id)
    {
        ItemModel itemModel=itemService.getItemById(id);
        ItemVO itemVO=convertVOFromModel(itemModel);
        return CommenReturnType.create(itemVO);
    }

    //商品列表页面
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public CommenReturnType listItem()
    {
        List<ItemModel> itemModelList=itemService.listItem();

        //使用stream api将list内的itemModel转化为ItemVO
        List<ItemVO> itemVOList=itemModelList.stream().map(itemModel -> {
            ItemVO itemVO=this.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommenReturnType.create(itemVOList);
    }

}
