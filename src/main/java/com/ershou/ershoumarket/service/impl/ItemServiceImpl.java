package com.ershou.ershoumarket.service.impl;

import com.ershou.ershoumarket.dao.ItemDoMapper;
import com.ershou.ershoumarket.dataobject.ItemDo;
import com.ershou.ershoumarket.service.ItemService;
import com.ershou.ershoumarket.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService
{
    @Autowired
    private ItemDoMapper itemDoMapper;

    private ItemDo convertItemDoFromItemModel(ItemModel itemModel)
    {
        if(itemModel==null)
        {
            return null;
        }
        ItemDo itemDo=new ItemDo();
        BeanUtils.copyProperties(itemModel,itemDo);
        return itemDo;
    }


    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel)
    {
//        转化itemmodel->dataobject
        ItemDo itemDo=this.convertItemDoFromItemModel(itemModel);
//        写入数据库
        itemDoMapper.insertSelective(itemDo);
        itemModel.setId(itemDo.getId());
//      返回创建完成的对象

        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem()
    {
        List<ItemDo> itemDoList = itemDoMapper.listItem();
        //将里面每个itemDO map成itemModel
        List<ItemModel> itemModelList=itemDoList.stream().map(itemDo -> {
            ItemModel itemModel=this.convertModelFromDataObject(itemDo);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id)
    {
        ItemDo itemDo=itemDoMapper.selectByPrimaryKey(id);
        if(itemDo==null)
        {
            return null;
        }
        ItemModel itemModel=convertModelFromDataObject(itemDo);
        return itemModel;
    }

    private ItemModel convertModelFromDataObject(ItemDo itemDo)
    {
        ItemModel itemModel=new ItemModel();
        BeanUtils.copyProperties(itemDo,itemModel);
        return itemModel;
    }
}
